package com.dodopal.card.business.facadeImpl;

import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.AreaBlankListParameter;
import com.dodopal.api.card.dto.BJDiscountDTO;
import com.dodopal.api.card.dto.BlankListParameter;
import com.dodopal.api.card.dto.ConsumeCardTypeParameter;
import com.dodopal.api.card.dto.ConsumeDiscountParameter;
import com.dodopal.api.card.dto.GrayListParameter;
import com.dodopal.api.card.dto.IncrementBlankListParameter;
import com.dodopal.api.card.dto.ParameterList;
import com.dodopal.api.card.dto.PosSignInOutDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.api.card.dto.SignParameter;
import com.dodopal.api.card.dto.SpecialModel;
import com.dodopal.api.card.dto.SubPeriodDiscountParameter;
import com.dodopal.api.card.dto.TerminalMenuParameter;
import com.dodopal.api.card.dto.TerminalParameter;
import com.dodopal.api.card.facade.SignInSignOutCardFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.model.query.ParameterQuery;
import com.dodopal.card.business.service.BJCardTopupService;
import com.dodopal.card.business.service.BJCityFrontSocketService;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.ParameterService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SpecialModelParamNOEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

@Service("signInSignOutCardFacade")
public class SignInSignOutCardFacadeImpl implements SignInSignOutCardFacade {

    @Autowired
    private LogHelper logHelper;

    @Autowired
    private CardTopupService cardTopupService;

    @Autowired
    private BJCardTopupService bjcardTopupService;

    @Autowired
    private BJCityFrontSocketService bjCityFrontSocketService;

    @Autowired
    private ParameterService parameterService;

    /*
     * 签到
     * 1.校验空值
     * 2.根据cityCode查询城市前置ip，port
     * 3.与城市前置交互
     * 4.查询签到签退表数据,返回前端
     */
    @Override
    public DodopalResponse<SignParameter> SignIn(SignParameter signParameter) {
        //日志开始时间
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应吗描述
        StringBuffer logRespexplain = new StringBuffer();
        //日志响应吗
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(signParameter);

        DodopalResponse<SignParameter> response = new DodopalResponse<SignParameter>();
        response.setCode(ResponseCode.SUCCESS);
        //返回对象
        SignParameter retSignParameter = new SignParameter();

        try {
            //1.校验空值
            String emptyCode = checkSignInEmpty(signParameter);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                setErrorResponse(signParameter, response, emptyCode);
                logRespcode = emptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //2.根据cityCode查询城市前置ip，port
                Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(signParameter.getCitycode());
                logRespexplain.append("签到POS=" + signParameter.getPosid() + ",POS类型=" + signParameter.getPostype() + ",[cityCode=" + signParameter.getCitycode());
                logRespexplain.append(",ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + "]");
                //3.向城市前置发送签到报文
                retSignParameter = bjCityFrontSocketService.sendSignInJsonSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), signParameter, CardConstants.CARD_LOG_TXNTYPE_OTHER);
                if (!ResponseCode.SUCCESS.equals(retSignParameter.getRespcode())) {//失败
                    setErrorResponse(signParameter, response, retSignParameter.getRespcode());
                    logRespcode = retSignParameter.getRespcode();
                    logRespexplain.append(",与城市前置交互失败");
                } else {//成功
                    //4.根据posId查询签到签退数据
                    SamSignInOffTb inOffTb = bjcardTopupService.querySamInOffTbByPosId(signParameter.getPosid());
                    if (inOffTb == null || StringUtils.isBlank(inOffTb.getDownloadFlag())) {
                        throw new DDPException(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR);
                    }
                    //具体参数下载标志
                    String downloadFlag = inOffTb.getDownloadFlag();
                    logRespexplain.append(",DownloadFlag:" + downloadFlag);
                    //查询商户号
                    String merCode = bjcardTopupService.queryMerCodeByPosNo(signParameter.getPosid());
                    logRespexplain.append(",merCode:" + merCode);
                    retSignParameter.setMercode(merCode);
                    //处理特属域数据
                    downloadFlag = jointDowndloadPram(downloadFlag, merCode);
                    retSignParameter.setCrdm(downloadFlag);
                    logRespexplain.append(",crdm:" + downloadFlag);
                    //拼装秘钥数据
                    String keySet = bjcardTopupService.queryKeyset(merCode);
                    logRespexplain.append(",keySet:" + keySet);
                    if (StringUtils.isBlank(keySet)) {
                        throw new DDPException(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR, "查询秘钥数据失败");
                    }
                    retSignParameter.setKeyset(keySet);
                    String cert = "0";
                    retSignParameter.setCert(cert);
                    response.setResponseEntity(retSignParameter);
                    logRespexplain.append(response.getMessage());
                }
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            response.setResponseEntity(retSignParameter);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            retSignParameter.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(signParameter);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][V71]签到(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }
        return response;
    }

    /*
     * 组装特属域数据
     */
    private String jointDowndloadPram(String downloadFlag, String merCode) {
        //1-32，是通卡的参数：直接读取samsigninoff表中的DOWNLOADFLAG字段，补齐32后补0
        while (downloadFlag.length() < 32) {
            downloadFlag += "0";
        }
        //33-64参数，需要去其他地方取，现在只是取的是折扣信息，新系统有折扣信息，则需要判断当前终端是否有此参数，有的话给1，没的话给0
        //TODO 33位 为 0
        downloadFlag += "0";
        //34位
        int count = Integer.valueOf(bjcardTopupService.queryMerDiscountByMerCode(merCode));
        if (count > 0) {
            downloadFlag += "1";
        } else {
            downloadFlag += "0";
        }
        //补齐64位
        while (downloadFlag.length() < 64) {
            downloadFlag += "0";
        }
        return downloadFlag;
    }

    //签到校验空值
    private String checkSignInEmpty(SignParameter signParameter) {
        if (StringUtils.isBlank(signParameter.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        }
        if (StringUtils.isBlank(signParameter.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        }
        if (StringUtils.isBlank(signParameter.getPostype())) {
            return ResponseCode.CARD_POSTYPE_NULL;
        }
        if (StringUtils.isBlank(signParameter.getMercode())) {
            return ResponseCode.CARD_MERCODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    //签退校验空值
    private String checkSignOutEmpty(SignParameter signParameter) {
        if (StringUtils.isBlank(signParameter.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        }
        if (StringUtils.isBlank(signParameter.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        }
        if (StringUtils.isBlank(signParameter.getPostype())) {
            return ResponseCode.CARD_POSTYPE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    //校验V61签到空值
    private String checkSignInEmptyV61(PosSignInOutDTO dto) {
        if (StringUtils.isBlank(dto.getYktcode())) {
            return ResponseCode.CARD_YKTCODE_NULL;
        }
        if (StringUtils.isBlank(dto.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        }
        if (StringUtils.isBlank(dto.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        }
        if (StringUtils.isBlank(dto.getSamno())) {
            return ResponseCode.CARD_SAM_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String checkDiscountEmpty(BJDiscountDTO dto) {
        if (StringUtils.isBlank(dto.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        }
        if (StringUtils.isBlank(dto.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private void setErrorResponse(SignParameter signParameter, DodopalResponse<SignParameter> response, String errorCode) {
        signParameter.setRespcode(errorCode);
        response.setResponseEntity(signParameter);
        response.setCode(errorCode);
    }

    private void setErrorResponse(PosSignInOutDTO signInOutDTO, DodopalResponse<PosSignInOutDTO> response, String errorCode) {
        signInOutDTO.setRespcode(errorCode);
        response.setResponseEntity(signInOutDTO);
        response.setCode(errorCode);
    }

    private void setErrorResponse(BJDiscountDTO signInOutDTO, DodopalResponse<BJDiscountDTO> response, String errorCode) {
        signInOutDTO.setRespcode(errorCode);
        response.setResponseEntity(signInOutDTO);
        response.setCode(errorCode);
    }

    /*
     *  签退 
     *  1.校验空值
     *  2.根据cityCode查询城市前置ip，port 
     *  3.与城市前置交互
     *  4.查询商户号
     */
    @Override
    public DodopalResponse<SignParameter> SignOut(SignParameter signParameter) {
        //日志开始时间
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应吗描述
        StringBuffer logRespexplain = new StringBuffer();
        //日志响应吗
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(signParameter);

        DodopalResponse<SignParameter> response = new DodopalResponse<SignParameter>();
        response.setCode(ResponseCode.SUCCESS);
        //返回对象
        SignParameter retSignParameter = new SignParameter();

        try {
            //1.校验空值
            String emptyCode = checkSignOutEmpty(signParameter);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                setErrorResponse(signParameter, response, emptyCode);
                logRespcode = emptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //2.根据cityCode查询城市前置ip，port
                Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(signParameter.getCitycode());
                logRespexplain.append("签退POS=" + signParameter.getPosid() + ",POS类型=" + signParameter.getPostype());
                logRespexplain.append(",[cityCode=" + signParameter.getCitycode() + ",ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT"));
                //3.向城市前置发送报文
                retSignParameter = bjCityFrontSocketService.sendSignInJsonSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), signParameter, CardConstants.CARD_LOG_TXNTYPE_OTHER);
                if (!ResponseCode.SUCCESS.equals(retSignParameter.getRespcode())) {
                    setErrorResponse(signParameter, response, retSignParameter.getRespcode());
                    logRespcode = retSignParameter.getRespcode();
                    logRespexplain.append("与城市前置交互失败");
                } else {
                    //4.查询商户号
                    String merCode = bjcardTopupService.queryMerCodeByPosNo(signParameter.getPosid());
                    if (StringUtils.isNotBlank(merCode)) {
                        retSignParameter.setMercode(merCode);
                    }
                    response.setResponseEntity(retSignParameter);
                    logRespexplain.append(response.getMessage());
                }
            }

        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            retSignParameter.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(retSignParameter);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][V71]签退(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }
        return response;
    }

    /*
     *  参数下载 
     */
    @SuppressWarnings("rawtypes")
    @Override
    public DodopalResponse<ParameterList> downloadParameter(ParameterList parameterList) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(parameterList);

        DodopalResponse<ParameterList> result = new DodopalResponse<ParameterList>();
        try {
            SpecialModel model = parameterList.getCrdm();
            model.setSamno(parameterList.getSamno());
            //检查查询参数下载时提供的关键参数
            String checkResult = checkDownLoadParam(model);
            if (StringUtils.isNotBlank(checkResult)) {
                result.setCode(checkResult);
                return result;
            }
            //构造query查询类
            ParameterQuery query = createParamPage(model);
            String queryStr = JSONObject.toJSONString(query);
            logRespexplain.append("[" + parameterList.getCrdm().getParno() + SpecialModelParamNOEnum.getByCode(parameterList.getCrdm().getParno()).getName() + "]" + queryStr);

            if (StringUtils.isBlank(parameterList.getPosid())) {
                throw new DDPException(ResponseCode.CARD_POSID_NULL);
            }

            SamSignInOffTb inOffTb = bjcardTopupService.querySamInOffTbByPosId(parameterList.getPosid());

            if (inOffTb == null || StringUtils.isBlank(inOffTb.getDownloadFlag())) {
                throw new DDPException(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR);
            }

            if (SpecialModelParamNOEnum.p01.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<BlankListParameter> dodopalPageResult = parameterService.findBlankListParameterByPage(query);//01黑名单参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p02.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<ConsumeCardTypeParameter> dodopalPageResult = parameterService.findConsumeCardTypeParameterByPage(query);//02消费可用卡类型参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p03.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<TerminalParameter> dodopalPageResult = parameterService.findTerminalParameterByPage(query);//03终端运营参数（机具运营参数）
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p04.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<AreaBlankListParameter> dodopalPageResult = parameterService.findAreaBlankListParameterByPage(query);//04区域黑名单参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p05.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<IncrementBlankListParameter> dodopalPageResult = parameterService.findIncrementBlankListParameterByPage(query);//05增量黑名单参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p06.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<TerminalMenuParameter> dodopalPageResult = parameterService.findTerminalMenuParameterByPage(query);//06终端菜单参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p07.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<GrayListParameter> dodopalPageResult = parameterService.findGrayListParameterByPage(query);//07灰名单参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
                //构造返回参数
                if (Integer.parseInt(model.getReqindex()) > dodopalPageResult.getRows()) {
                    parameterService.updateDownloadFlag(model.getParno(), inOffTb.getDownloadFlag(), parameterList.getPosid());
                }
            } else if (SpecialModelParamNOEnum.p33.getCode().equals(parameterList.getCrdm().getParno())) {
                DodopalDataPage<ConsumeDiscountParameter> dodopalPageResult = parameterService.findConsumeDiscountParameterByPage(query);//33消费折扣参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
            } else if (SpecialModelParamNOEnum.p34.getCode().equals(parameterList.getCrdm().getParno())) {
                String merCode = bjcardTopupService.queryMerCodeByPosNo(parameterList.getPosid());
                query.setMerCode(merCode);
                logRespexplain.append(",[merCode=" + merCode + "]");
                DodopalDataPage<SubPeriodDiscountParameter> dodopalPageResult = parameterService.findSubPeriodDiscountParameterByPage(query);//34分时段消费折扣参数
                if (dodopalPageResult.getRecords().size() == 0) {
                    result.setCode(ResponseCode.RECORD_NULL);
                    logRespcode = ResponseCode.RECORD_NULL;
                    return result;
                }
                createSpecialModelResult(dodopalPageResult, model);
            }
            //特殊域塞值
            parameterList.setCrdm(model);
            result.setCode(ResponseCode.SUCCESS);
            result.setResponseEntity(parameterList);
        }
        catch (DDPException e) {
            result.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            result.setResponseEntity(parameterList);
            e.printStackTrace();
        }
        catch (Exception e) {
            result.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getMessage());
            logRespexplain.append(e.getCause());
            result.setResponseEntity(parameterList);
            e.printStackTrace();
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][V71]参数下载(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(result), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }
        return result;
    }

    /**
     * @return 校验下载参数的特殊域
     */
    @SuppressWarnings("rawtypes")
    private String checkDownLoadParam(SpecialModel model) {
        if (null != model) {
            if (StringUtils.isEmpty(model.getParno())) {
                return ResponseCode.CARD_DOWNLOAD_PARNO_NULL;
            }
            if (StringUtils.isEmpty(model.getReqindex())) {
                return ResponseCode.CARD_DOWNLOAD_REQINDEX_NULL;
            }
            if (StringUtils.isEmpty(model.getReqtotal())) {
                return ResponseCode.CARD_DOWNLOAD_REQTOTAL_NULL;
            }
        } else {
            return ResponseCode.CARD_DOWNLOAD_SPECIALMODEL_NULL;
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> void createSpecialModelResult(DodopalDataPage<T> dodopalPageResult, SpecialModel model) {

        //各个参数编号对应的对象集合
        model.setListPars(dodopalPageResult.getRecords());
        //该类参数总记录条数
        model.setTotal(String.valueOf(dodopalPageResult.getRows()));
        //下载索引位置
        String reqindex = model.getReqindex();
        //一页下载数
        String reqtotal = model.getReqtotal();
        //下载索引
        String index = String.valueOf(Integer.parseInt(reqindex) + Integer.parseInt(reqtotal));
        model.setReqindex(index);
        if (CollectionUtils.isNotEmpty(dodopalPageResult.getRecords())) {
            //本次发送记录数
            model.setSize(String.valueOf(dodopalPageResult.getRecords().size()));
            System.out.println("本次发送记录数" + String.valueOf(dodopalPageResult.getRecords().size()));
            //剩余记录条数
            int leftRe = dodopalPageResult.getRows() - Integer.parseInt(index) + 1;
            if (leftRe > 0) {
                model.setSurplus(String.valueOf(leftRe));
            } else {
                model.setSurplus("0");
            }

        } else {
            //本次发送记录数
            model.setSize("0");
            //剩余记录条数
            model.setSurplus("0");

        }
    }

    /**
     * @return 构造query
     */
    @SuppressWarnings("rawtypes")
    private ParameterQuery createParamPage(SpecialModel model) {
        PageParameter page = new PageParameter();
        //记录下载开始索引
        page.setPageNo((Integer.valueOf(model.getReqindex()) / Integer.valueOf(model.getReqtotal()) + 1));
        //一次下载的记录条数
        page.setPageSize(Integer.valueOf(model.getReqtotal()));
        ParameterQuery query = new ParameterQuery();
        query.setPage(page);
        query.setSamno(model.getSamno());
        return query;
    }

    /*
     * 批量结果上传(外)
     */
    @Override
    public DodopalResponse<ReslutDataParameter> batchUploadResult(ReslutDataParameter parameter) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(parameter);//入参
        DodopalResponse<ReslutDataParameter> response = new DodopalResponse<ReslutDataParameter>();
        
        response.setCode(ResponseCode.SUCCESS);
        try {
            if (parameter.getCrdm() == null || CollectionUtils.isEmpty(parameter.getCrdm().getFilerecords())) {
                response.setCode(ResponseCode.CARD_OFFLINE_RECORDS_NULL);
                parameter.setRespcode(ResponseCode.CARD_OFFLINE_RECORDS_NULL);
                response.setResponseEntity(parameter);
                logRespcode = ResponseCode.CARD_OFFLINE_RECORDS_NULL;
                logRespexplain.append(response.getMessage());
            } else {
                logRespexplain.append("[总记录数:" + parameter.getCrdm().getTotalrecnum() + "],[本次记录数:" + parameter.getCrdm().getRecnum() + "],[剩余记录数:" + parameter.getCrdm().getLeftnum() + "]");
                String retStr = bjcardTopupService.batchUploadResult(parameter);
                if (StringUtils.isNotBlank(retStr)) {
                    logRespexplain.append(retStr);
                }
                parameter.setRespcode(ResponseCode.SUCCESS);
                response.setResponseEntity(parameter);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            parameter.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(parameter);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            logRespexplain.append(e.getMessage());
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][V71]批量上传(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return response;
    }

    /*
     * v61手动签到签退
     * 1.校验空值
     * 2.发送签到报文到城市前置
     * 3.返回前端
     */
    @Override
    public DodopalResponse<PosSignInOutDTO> posSignInOutForV61(PosSignInOutDTO posSignInOutDTO) {
        //日志开始时间
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应吗描述
        StringBuffer logRespexplain = new StringBuffer();
        //日志响应吗
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(posSignInOutDTO);

        DodopalResponse<PosSignInOutDTO> response = new DodopalResponse<PosSignInOutDTO>();
        response.setCode(ResponseCode.SUCCESS);

        PosSignInOutDTO retDto = new PosSignInOutDTO();

        String signInOutStr = "";

        if ("5005".equals(posSignInOutDTO.getMessagetype())) {
            signInOutStr = "签到";
        } else if ("5007".equals(posSignInOutDTO.getMessagetype())) {
            signInOutStr = "签退";
        }

        try {
            //1.校验空值
            String emptyCode = checkSignInEmptyV61(posSignInOutDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                setErrorResponse(posSignInOutDTO, response, emptyCode);
                logRespcode = emptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //根据cityCode查询城市前置ip，port
                Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(posSignInOutDTO.getYktcode());
                logRespexplain.append("[POS号=" + posSignInOutDTO.getPosid() + "]");
                logRespexplain.append(",[cityCode=" + posSignInOutDTO.getCitycode() + ",yktcode=" + posSignInOutDTO.getYktcode() + ",ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + "]");
                //发送前拼装数据
                SignParameter signParameter = fixSignParameter(posSignInOutDTO);
                //2.向城市前置发送报文
                SignParameter retSignParameter = bjCityFrontSocketService.sendSignInJsonSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), signParameter, CardConstants.CARD_LOG_TXNTYPE_OTHER);
                retDto = fixPosSignInOutDTO(retSignParameter);
                if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                    setErrorResponse(retDto, response, retDto.getRespcode());
                    logRespcode = retDto.getRespcode();
                    logRespexplain.append(",与城市前置交互失败");
                } else {
                    response.setResponseEntity(retDto);
                    logRespexplain.append(response.getMessage());
                }
            }

        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            posSignInOutDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(posSignInOutDTO);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][V61]" + signInOutStr + "(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }
        return response;
    }

    private SignParameter fixSignParameter(PosSignInOutDTO posSignInOutDTO) {
        SignParameter signParameter = new SignParameter();
        signParameter.setMessagetype(posSignInOutDTO.getMessagetype());
        signParameter.setVer(posSignInOutDTO.getVer());
        signParameter.setSysdatetime(posSignInOutDTO.getSysdatetime());
        signParameter.setCitycode(posSignInOutDTO.getCitycode());
        signParameter.setPosid(posSignInOutDTO.getPosid());
        signParameter.setSamno(posSignInOutDTO.getSamno());
        signParameter.setReserved(posSignInOutDTO.getReserved());
        return signParameter;
    }

    private PosSignInOutDTO fixPosSignInOutDTO(SignParameter parameter) {
        PosSignInOutDTO posSignInOutDTO = new PosSignInOutDTO();
        posSignInOutDTO.setMessagetype(parameter.getMessagetype());
        posSignInOutDTO.setVer(parameter.getVer());
        posSignInOutDTO.setSysdatetime(parameter.getSysdatetime());
        posSignInOutDTO.setCitycode(parameter.getCitycode());
        posSignInOutDTO.setPosid(parameter.getPosid());
        posSignInOutDTO.setSamno(parameter.getSamno());
        posSignInOutDTO.setReserved(parameter.getReserved());
        posSignInOutDTO.setRespcode(parameter.getRespcode());
        return posSignInOutDTO;
    }

    /*
     * 查询优惠信息(外)
     * 1.校验空值
     * 2.调用城市前置
     * 3.返回前端
     */
    @Override
    public DodopalResponse<BJDiscountDTO> queryDiscountAmt(BJDiscountDTO bjDiscountDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(bjDiscountDTO);//入参

        DodopalResponse<BJDiscountDTO> response = new DodopalResponse<BJDiscountDTO>();
        response.setCode(ResponseCode.SUCCESS);

        BJDiscountDTO retDto = new BJDiscountDTO();
        try {
            //1.校验空值
            String emptyCode = checkDiscountEmpty(bjDiscountDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                setErrorResponse(bjDiscountDTO, response, emptyCode);
                logRespcode = emptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                logRespexplain.append("[1.校验空值成功]");
                //根据cityCode查询城市前置ip，port
                Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(bjDiscountDTO.getCitycode());
                if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                    throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                }
                String ip = retMap.get("IP") + "";
                String port = retMap.get("PROT") + "";
                logRespexplain.append(",[cityCode=" + bjDiscountDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                //2.向城市前置发送报文
                retDto = bjCityFrontSocketService.sendDiscountSocket(ip, Integer.valueOf(port), bjDiscountDTO, CardConstants.CARD_LOG_TXNTYPE_OTHER);
                if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                    setErrorResponse(retDto, response, retDto.getRespcode());
                    logRespcode = retDto.getRespcode();
                    logRespexplain.append(",[2.与城市前置交互失败]");
                } else {
                    response.setResponseEntity(retDto);
                    logRespexplain.append(",[2.与城市前置交互成功]");
                }
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            bjDiscountDTO.setRespcode(e.getCode());
            response.setResponseEntity(bjDiscountDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            bjDiscountDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(bjDiscountDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][V71]查询优惠(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }
        return response;
    }
}
