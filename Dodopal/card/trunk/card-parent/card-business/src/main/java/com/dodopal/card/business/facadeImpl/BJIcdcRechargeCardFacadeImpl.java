package com.dodopal.card.business.facadeImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.facade.BJIcdcRechargeCardFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.model.TlpPosMenuTb;
import com.dodopal.card.business.service.BJCardTopupService;
import com.dodopal.card.business.service.BJCityFrontSocketService;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.service.IcdcRechargeService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTradeStartFlagEnum;
import com.dodopal.common.enums.CardTxnStatEnum;
import com.dodopal.common.model.DodopalResponse;

@Service("bjIcdcRechargeCardFacade")
public class BJIcdcRechargeCardFacadeImpl implements BJIcdcRechargeCardFacade {
    @Autowired
    private LogHelper logHelper;

    @Autowired
    private CardTopupService cardTopupService;

    @Autowired
    private BJCardTopupService bjCardTopupService;

    @Autowired
    private CityFrontSocketService cityFrontSocketService;

    @Autowired
    private BJCityFrontSocketService bjCityFrontSocketService;

    @Autowired
    private IcdcRechargeService icdcRechargeService;

    /*
     * 验卡查询接口(外部调用) 
     * 1.校验空值 
     * 2.获取sam卡签到信息
     * 3.比对入参与sam卡信息
     * 4.获取pos菜单信息
     * 5.向前置发送验卡请求
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCard(BJCrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //1.校验空值
            String checkEmptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                //2.根据pos号获取sam卡签到信息
                SamSignInOffTb inOffTb = bjCardTopupService.querySamInOffTbByPosId(crdDTO.getPosid());
                //3.校验sam卡签到信息数据
                String checkSamCode = BJStringUtil.checkSamInfo(crdDTO.getSamno(), inOffTb);
                if (!ResponseCode.SUCCESS.equals(checkSamCode)) {
                    setErrorResponse(crdDTO, response, checkSamCode);
                    logRespcode = checkSamCode;
                    respexplain.append(response.getMessage());
                } else {
                    //posic流水号，pos通讯流水号格式转换
                    crdDTO.setPosseq(BJStringUtil.format16to10(inOffTb.getPosIcSeq()));
                    crdDTO.setPostransseq(BJStringUtil.format16to10(inOffTb.getPosCommSeq()));
                    crdDTO.setBatchno(inOffTb.getBatchNo());//通卡批次号
                    crdDTO.setMchntid(inOffTb.getMchntId());//通卡商户号
                    //TODO 4.获取pos菜单信息
                    //5.向城市前置发送验卡报文
                    BJCrdSysOrderDTO retCrdDTO = queryCheckCardCityFront(crdDTO);
                    if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//城市前置返回交易应答码失败
                        response.setCode(retCrdDTO.getRespcode());
                        logRespcode = retCrdDTO.getRespcode();
                        respexplain.append("与城市前置交互失败");
                    }
                    response.setResponseEntity(retCrdDTO);
                }
            }
        }
        catch (Exception e) {
            respexplain.append(",[error=" + e.getCause() + "]");
            setErrorResponse(crdDTO, response, ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }
        finally {
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]验卡查询(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 验卡查询校验空值
     */
    private String checkQueryEmpty(BJCrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_YKTCODE_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getSamno())) {
            return ResponseCode.CARD_SAM_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private void setErrorResponse(BJCrdSysOrderDTO crdDTO, DodopalResponse<BJCrdSysOrderDTO> response, String errorCode) {
        crdDTO.setRespcode(errorCode);
        response.setResponseEntity(crdDTO);
        response.setCode(errorCode);
    }

    /*
     * 调用城市前置
     */
    private BJCrdSysOrderDTO queryCheckCardCityFront(BJCrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        String inpras = JSONObject.toJSONString(crdDTO);

        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();

        try {
            //如果黑名单标志为1,则不向前置发送报文
            if (StringUtils.isNotBlank(crdDTO.getBlackflag()) && "1".equals(crdDTO.getBlackflag())) {
                crdDTO.setRespcode(ResponseCode.SUCCESS);
                respexplain.append("前端判断为黑名单卡，不与前置交互");
                BeanUtils.copyProperties(crdDTO, retCrdDTO);
                retCrdDTO.setTxndate(DateUtil.getDateForLong());
                retCrdDTO.setTxntime(DateUtil.getTimeShortHHmmss());
            } else {
                //根据yktcode获得访问城市前置具体的ip地址、端口号、卡内限额
                Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
                respexplain.append("[1.ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + ",cardLimit=" + retMap.get("CARDLIMIT") + "]");
                //把取出的卡内限额传给城市前置,之后以城市前置返回的为准
                crdDTO.setCardlimit(retMap.get("CARDLIMIT") + "");

                //取服务器日期 yyyyMMdd
                crdDTO.setTxndate(DateUtil.getDateForLong());
                //取服务器时间 HHmmss
                crdDTO.setTxntime(DateUtil.getTimeShortHHmmss());

                //调用城市前置接口
                retCrdDTO = bjCityFrontSocketService.sendBJCityFrontSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                    if ("011003".equals(retCrdDTO.getRespcode())) {//011003错误码为黑名单卡需要锁卡
                        retCrdDTO.setBlackflag("1");//黑名单标志
                    }
                    logRespcode = retCrdDTO.getRespcode();
                }
                respexplain.append(",[2.城市前置返回respcode=" + retCrdDTO.getRespcode() + ",cardLimit=" + retCrdDTO.getCardlimit() + "]");
            }
        }
        catch (Exception e) {
            BeanUtils.copyProperties(crdDTO, retCrdDTO);
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e);
        }
        finally {
            logHelper.recordLogFun(inpras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]验卡查询-城市前置", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retCrdDTO;
    }

    /*
     * 创建订单方法(外部调用)
     * 1.校验空值
     * 2.校验充值金额与卡内余额之和是否超过卡限额
     * 3.根据产品库提供的订单号查询卡服务订单是否存在
     * 4.创建订单并返回卡服务订单编号
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> createCrdOrderCard(BJCrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //卡服务订单编号
        String crdOrderNum = "";

        try {
            //1.校验空值
            String checkEmptyCode = checkCreateEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {//有空值返回异常
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                respexplain.append("[1.prdordernum=" + crdDTO.getPrdordernum() + "]");
                //2.校验充值金额与卡内余额之和是否超过卡限额
                String checkCardLimitCode = checkCardLimit(crdDTO);
                if (!ResponseCode.SUCCESS.equals(checkCardLimitCode)) {
                    setErrorResponse(crdDTO, response, ResponseCode.CARD_LIMIT_ERROR);
                    respexplain.append(",[limit=" + crdDTO.getCardlimit() + ",txnamt=" + crdDTO.getTxnamt() + ",befamt=" + crdDTO.getBefbal() + "]");
                    logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                } else {
                    //3.根据产品库提供的订单号查询卡服务订单是否存在
                    String prdExist = checkOrderCreate(crdDTO.getPrdordernum());
                    respexplain.append(",[2.prdExist=" + prdExist + "]");
                    if (!ResponseCode.SUCCESS.equals(prdExist)) {//产品库号码存在异常
                        setErrorResponse(crdDTO, response, prdExist);
                        logRespcode = prdExist;
                    } else {
                        //4.产品库号码不存在,创建订单并返回卡服务订单编号
                        crdOrderNum = bjCardTopupService.createCrdSysOrder(crdDTO);
                        respexplain.append(",[3.crdOrderNum=" + crdOrderNum + "]");
                        if (StringUtils.isNotBlank(crdOrderNum)) {
                            crdDTO.setCrdordernum(crdOrderNum);
                            response.setResponseEntity(crdDTO);
                        } else {
                            setErrorResponse(crdDTO, response, ResponseCode.SYSTEM_ERROR);
                            logRespcode = ResponseCode.SYSTEM_ERROR;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            setErrorResponse(crdDTO, response, ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(",[error=" + e.getCause() + e.getMessage().substring(0, 400) + "]");
        }
        finally {
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]创建订单(外)", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 创建订单，校验卡内余额
     */
    private String checkCardLimit(BJCrdSysOrderDTO crdDTO) {
        String retCode = ResponseCode.SUCCESS;
        long limit = Long.valueOf(crdDTO.getCardlimit());
        long txnamt = Long.valueOf(crdDTO.getTxnamt());
        long befamt = Long.valueOf(crdDTO.getBefbal());
        if ((befamt + txnamt) > limit) {
            retCode = ResponseCode.CARD_LIMIT_ERROR;
        }
        return retCode;
    }

    private String checkCreateEmpty(BJCrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getProductcode())) {
            return ResponseCode.CARD_PRODUCTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getUsercode())) {
            return ResponseCode.CARD_USERCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_YKTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCardinnerno())) {
            return ResponseCode.CARD_CARDINNERNO_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCardfaceno())) {
            return ResponseCode.CARD_CARDFACENO_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_TRADECARD_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCardtype())) {
            return ResponseCode.CARD_CARDTYPE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPostype())) {
            return ResponseCode.CARD_POSTYPE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getBefbal())) {
            return ResponseCode.CARD_BEFBAL_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_TXNAMT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getSource())) {
            return ResponseCode.CARD_SOURCE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnseq())) {
            return ResponseCode.CARD_TXNSEQ_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxndate())) {
            return ResponseCode.CARD_TXNDATE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxntime())) {
            return ResponseCode.CARD_TXNTIME_NULL;
        } else if (StringUtils.isBlank(crdDTO.getChargetype())) {
            return ResponseCode.CARD_CHARGETYPE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCardlimit())) {
            return ResponseCode.CARD_LIMIT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPostransseq())) {
            return ResponseCode.CARD_POSTRANSEQ_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 校验产品库订单号是否存在
     */
    private String checkOrderCreate(String proOrderNum) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        String prdExist = ResponseCode.SUCCESS;
        try {
            respexplain.append("[1.proNum=" + proOrderNum + "]");
            //根据产品库订单号查询卡服务订单
            int count = cardTopupService.checkCrdOrderByPrdId(proOrderNum);
            if (count > 0) {
                prdExist = ResponseCode.CARD_PRDORDERNUM_EXIST;
                logRespcode = ResponseCode.CARD_PRDORDERNUM_EXIST;
                respexplain.append(",[2.prdCount=" + count + ",产品库订单号已存在]");
            } else {
                respexplain.append(",[2.prdCount=" + count + ",产品库订单号不存在,校验成功]");
            }
        }
        catch (Exception e) {
            prdExist = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e.getMessage());
        }

        logHelper.recordLogFun(proOrderNum, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]创建订单-校验订单", "", proOrderNum, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), prdExist, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return prdExist;
    }

    /*
     * getApdu
     * 1.校验空值
     * 2.查询菜单数据
     * 3.查询卡服务订单数据
     * 4.返回前端
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getApdu(BJCrdSysOrderDTO crdDTO) {
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        String inPras = JSONObject.toJSONString(crdDTO);
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();
        try {
            // 1.校验空值
            String emptyCode = checkGetApudEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emptyCode)) {
                setErrorResponse(crdDTO, response, emptyCode);
                logRespcode = emptyCode;
                respexplain.append(response.getMessage());
            } else {
                // 2.查询菜单数据
                TlpPosMenuTb posMenuTb = bjCardTopupService.queryTlpPosMenuTb(crdDTO.getSamno());
                //校验订单数据
                String checkMenuCode = BJStringUtil.checkMenu(posMenuTb);
                if (!ResponseCode.SUCCESS.equals(checkMenuCode)) {
                    setErrorResponse(crdDTO, response, checkMenuCode);
                    logRespcode = checkMenuCode;
                    respexplain.append(response.getMessage());
                } else {
                    crdDTO.setBjtktradestat(posMenuTb.getTxnstatus());
                    crdDTO.setBjtktradetype(posMenuTb.getTxntype());
                    crdDTO.getSpecdata().setNextstep(posMenuTb.getMenufristactionset());
                    respexplain.append("[获取posMenu数据成功," + "samno=" + crdDTO.getSamno() + ",txntype=" + crdDTO.getBjtktradetype() + ",txnstat=" + crdDTO.getBjtktradestat() + ",nextstep=" + crdDTO.getSpecdata().getNextstep() + "]");
                    // 3.查询卡服务订单数据
                    retDTO = bjCardTopupService.getLoadInitFun(crdDTO);
                    if (!ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {
                        setErrorResponse(crdDTO, response, retDTO.getRespcode());
                        logRespcode = retDTO.getRespcode();
                        respexplain.append(response.getMessage());
                    } else {
                        respexplain.append("[查询卡服务订单数据成功]");
                        response.setResponseEntity(retDTO);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setResponseEntity(crdDTO);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            respexplain.append(e.getMessage());
        }
        finally {
            // 记录日志
            String respex = respexplain.toString();
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值][BJ][V61]圈存初始化(外)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    private String checkGetApudEmpty(BJCrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getUserid())) {
            return ResponseCode.CARD_USERID_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_TXNAMT_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_TRADECARD_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getSamno())) {
            return ResponseCode.CARD_SAM_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 申请充值密钥(外部调用)
     * 1.校验空值
     * 2.校验订单
     * 3.向前置发送报文
     * 4.组装数据返回前端
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getLoadOrder(BJCrdSysOrderDTO crdDTO) {
        // 日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        // 日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        // 日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        BJCrdSysOrderDTO retDTO = new BJCrdSysOrderDTO();
        try {
            //1.校验空值
            String checkEmptyCode = checkLoadOrderEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                respexplain.append("[1.校验空值成功]");
                //2.校验订单
                Map<String, Object> map = checkOrderRechargeFun(crdDTO);
                String checkOrderCode = map.get("retCode") + "";
                String crdOrderNum = map.get("crdOrderNum") + "";
                String crdOrderState = map.get("crdOrderState") + "";
                crdDTO.setCrdordernum(crdOrderNum);
                if (!ResponseCode.SUCCESS.equals(checkOrderCode)) {
                    setErrorResponse(crdDTO, response, checkOrderCode);
                    logRespcode = checkOrderCode;
                    respexplain.append(response.getMessage());
                } else {//校验成功
                    respexplain.append(",[2.校验订单成功" + ",orderState=" + crdOrderState + "]");
                    //3.向城市前置发送报文
                    retDTO = sendCharge(crdDTO, crdOrderState);
                    if (!ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {
                        setErrorResponse(retDTO, response, retDTO.getRespcode());
                        logRespcode = retDTO.getRespcode();
                        respexplain.append(response.getMessage());
                        respexplain.append(",[3.与城市前置交互失败]");
                    } else {

                        //                        retDTO.setBefbal(Integer.valueOf(retDTO.getBefbal(), 16) + "");
                        retDTO.setBefbal(BJStringUtil.toBigInt16(retDTO.getBefbal()));
                        response.setResponseEntity(retDTO);
                    }
                }
            }
        }
        catch (Exception e) {
            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//设置结束标志为1
            setErrorResponse(crdDTO, response, ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e);
        }
        finally {
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]充值申请(外)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 1.申请前更新订单
     * 2.发送报文
     */
    private BJCrdSysOrderDTO sendCharge(BJCrdSysOrderDTO crdDTO, String crdOrderState) {
        String crdNum = crdDTO.getCrdordernum();
        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();
        //卡服务订单状态为1000000005,且交易开始标志为(0)透传,向前置透传
        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(crdOrderState) && CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode().equals(crdDTO.getTradestartflag())) {
            //更新重发计数器
            cardTopupService.updateKeyCountByCrdnum(crdNum);
            //更新通讯流水号
            cardTopupService.updatePosTranSeqByCrdnum(crdNum, BJStringUtil.format10to16(crdDTO.getPostransseq()));
            //向前置发送报文
            retCrdDTO = rechargeKeyCityFrontFun(crdDTO);
            return retCrdDTO;
        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000003,正常流程
            //1.申请前更新订单
            String updStartCode = rechargeUpdOrderFun(crdDTO);
            if (!ResponseCode.SUCCESS.equals(updStartCode)) {//更新订单失败
                crdDTO.setRespcode(updStartCode);
                return crdDTO;
            } else {//更新订单成功
                //2.调用城市前置申请圈存
                retCrdDTO = rechargeKeyCityFrontFun(crdDTO);
                return retCrdDTO;
            }
        } else {//订单状态不正确
            crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
        }
        return crdDTO;
    }

    /**
     * 申请圈存后更新订单状态
     * @param crdDTO
     * @return
     */
    private String rechargeUpdOrderAfter(BJCrdSysOrderDTO crdDTO) {
        // 日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        // 日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        // 日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        try {
            respexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum() + "]");
            bjCardTopupService.updateCrdSysOrderAfterByCrdnum(crdDTO);
        }
        catch (Exception e) {
            respexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }
        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]充值申请-更新订单(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCode;
    }

    /*
     * 向城市前置申请充值密钥 
     * 1.根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
     * 2.调用发给城市前置的报文接口(CardRechargeService.rechargeKeySendFun)
     */
    private BJCrdSysOrderDTO rechargeKeyCityFrontFun(BJCrdSysOrderDTO crdDTO) {
        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();
        try {
            //更新通讯流水号
            bjCardTopupService.updateSamsigninfo(crdDTO.getPosid(), crdDTO.getPosseq(), crdDTO.getPostransseq());
            //根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            String ip = retMap.get("IP") + "";
            String port = retMap.get("PROT") + "";
            //交易前卡余额转16进制
            crdDTO.setBefbal(Integer.toHexString(Integer.valueOf(crdDTO.getBefbal())));
            //调用发给城市前置的报文接口
            retCrdDTO = bjCityFrontSocketService.sendBJCityFrontSocket(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);

            if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//向城市前置申请充值失败
                if (StringUtils.isBlank(retCrdDTO.getTradeendflag())) {
                    retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                }
                //011003和000047错误码需要锁卡
                if ("011003".equals(retCrdDTO.getRespcode()) || "000047".equals(retCrdDTO.getRespcode())) {
                    retCrdDTO.setBlackflag("1");
                }
                //3.申请后更新订单
                String updEndCode = rechargeUpdOrderAfter(retCrdDTO);
                if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                    retCrdDTO.setRespcode(updEndCode);
                }
                return retCrdDTO;
            } else {//返回应答码为成功
                String endStr = retCrdDTO.getSpecdata().getNextstep().substring(4, 12);
                //结束标志为透传(0),且0600FFFF,直接透传回前端
                if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag()) || !"0600FFFF".equals(endStr)) {
                    return retCrdDTO;
                } else if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag()) && "0600FFFF".equals(endStr)) {//结束标志为 结束(1)
                    //5.申请后更新订单
                    retCrdDTO.setCrdordernum(crdDTO.getCrdordernum());
                    retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                    String updEndCode = rechargeUpdOrderAfter(retCrdDTO);
                    if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                        retCrdDTO.setRespcode(updEndCode);
                    }
                    return retCrdDTO;
                }
            }
        }
        catch (Exception e) {
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        return retCrdDTO;
    }

    /**
     * 申请充值密钥前更新订单状态
     */
    private String rechargeUpdOrderFun(BJCrdSysOrderDTO crdDTO) {
        // 日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        // 日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        // 日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);
        String retCode = ResponseCode.SUCCESS;
        try {
            respexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum() + "]");
            bjCardTopupService.updateCrdSysOrderByCrdnum(crdDTO);
        }
        catch (Exception e) {
            respexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }
        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]充值申请-更新订单(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCode;
    }

    private String checkLoadOrderEmpty(BJCrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_YKTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_TRADECARD_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getBefbal())) {
            return ResponseCode.CARD_BEFBAL_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_TXNAMT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradestartflag())) {
            return ResponseCode.CARD_TRADESTARTFLAG_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPostransseq())) {
            return ResponseCode.CARD_POSTRANSEQ_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosseq())) {
            return ResponseCode.CARD_POSSEQ_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private Map<String, Object> checkOrderRechargeFun(BJCrdSysOrderDTO crdDTO) {
        // 日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        // 日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        // 日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        //卡服务订单号
        String crdOrderNum = "";
        String crdOrderState = "";
        try {
            //1.通过传入的产品库订单号，找到对应的卡服务订单记录，没找到进行异常处理
            String prdExist = cardTopupService.checkPrdnumExistByid(crdDTO.getPrdordernum());
            respexplain.append("[1.prdExist=" + prdExist + "]");
            if (!ResponseCode.SUCCESS.equals(prdExist)) {//异常
                retCode = prdExist;
                logRespcode = prdExist;
            } else {//找到对应的订单记录
                CrdSysOrder order = cardTopupService.findCrdSysOrderByPrdnum(crdDTO.getPrdordernum());
                crdOrderNum = order.getCrdOrderNum();
                crdOrderState = order.getCrdOrderStates();
                long limit = Long.valueOf(order.getCardLimit());//卡内限额
                long txnamt = Long.valueOf(crdDTO.getTxnamt());//充值金额
                long befamt = Long.valueOf(crdDTO.getBefbal());//卡内余额
                if ((befamt + txnamt) > limit) {//充值金额与卡内余额之和超过卡内限额
                    respexplain.append(",[limit=" + limit + ",txnamt=" + txnamt + ",befamt=" + befamt + "]");
                    retCode = ResponseCode.CARD_LIMIT_ERROR;
                    logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                } else {
                    String compareCode = compareOrder(order, crdDTO);
                    if (!ResponseCode.SUCCESS.equals(compareCode)) {
                        //3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致，否则进行异常处理
                        retCode = compareCode;
                        logRespcode = compareCode;
                    }
                }
            }
        }
        catch (Exception e) {
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("retCode", retCode);
        map.put("crdOrderNum", crdOrderNum);
        map.put("crdOrderState", crdOrderState);

        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值][BJ][V61]充值申请-校验订单", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(map), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return map;
    }

    private String compareOrder(CrdSysOrder order, BJCrdSysOrderDTO crdDTO) {
        //判断传入的参数卡号前端读出的(tradecard)
        if (!crdDTO.getTradecard().equals(order.getCheckCardNo())) {
            return ResponseCode.CARD_TRADECARD_ERROR;
        } else if (!crdDTO.getYktcode().equals(order.getYktCode())) {//一卡通编号 (yktcode)
            return ResponseCode.CARD_YKTCODE_ERROR;
        } else if (!crdDTO.getCitycode().equals(order.getCityCode())) {//城市代码 (citycode)
            return ResponseCode.CARD_CITYCODE_ERROR;
        } else if (!crdDTO.getPosid().equals(order.getPosCode())) {//设备编号 (posid)
            return ResponseCode.CARD_POSID_ERROR;
        } else if (Long.valueOf(crdDTO.getBefbal()) != order.getBefbal()) {//交易前卡余额 (befbal)
            return ResponseCode.CARD_BEFBAL_ERROR;
        } else if (Long.valueOf(crdDTO.getTxnamt()) != order.getTxnAmt()) {//交易金额 (txnamt)
            return ResponseCode.CARD_TXNAMT_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    /**
     * 结果上传前更新校验(外部调用)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultChkUdpOrder(BJCrdSysOrderDTO crdDTO) {
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        String inPras = JSONObject.toJSONString(crdDTO);
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            String checkEmptyCode = befUploadCheckEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                respexplain.append("[1.校验空值成功]");
                // 1.结果上传校验方法
                String resCode = uploadResultChkFun(crdDTO);
                if (!ResponseCode.SUCCESS.equals(resCode)) {//校验失败
                    setErrorResponse(crdDTO, response, resCode);
                    logRespcode = resCode;
                    respexplain.append(response.getMessage());
                } else {//校验成功
                    respexplain.append(",[2.校验订单成功]");
                    // 2.更新卡服务订单状态等信息
                    String updCode = bjCardTopupService.uploadResultChkUdpOrderFun(crdDTO);
                    if (!ResponseCode.SUCCESS.equals(updCode)) {
                        setErrorResponse(crdDTO, response, updCode);
                    } else {//更新成功
                        respexplain.append(",[3.更新订单成功]");
                        response.setResponseEntity(crdDTO);
                    }
                }
                respexplain.append(response.getMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            respexplain.append(e.getMessage());
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            // 记录日志
            String respex = respexplain.toString();
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值][BJ][V61]结果上传校验更新(外)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    private String befUploadCheckEmpty(BJCrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCitycode())) {
            return ResponseCode.CARD_CITYCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_YKTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_TRADECARD_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_POSID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getBefbal())) {
            return ResponseCode.CARD_BEFBAL_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_TXNAMT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradestartflag())) {
            return ResponseCode.CARD_TRADESTARTFLAG_NULL;
        } else if (StringUtils.isBlank(JSONObject.toJSONString(crdDTO.getSpecdata()))) {
            return ResponseCode.CARD_SPECDATA_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnstat())) {
            return ResponseCode.CARD_TXNSTAT_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    private String uploadResultChkFun(BJCrdSysOrderDTO crdDTO) {
        // 1.查询订单是否存在
        int count = icdcRechargeService.queryCrdSysOrderCountByPrdOrderId(crdDTO.getPrdordernum());
        if (count != 1) {
            return ResponseCode.CARD_PRDORDERNUM_NOT_EXIST;
        }
        CrdSysOrder crdSysOrder = icdcRechargeService.queryCardSysOrderByPrdId(crdDTO.getPrdordernum());
        crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
        // 2.判断订单状态
        //北京结果上传重发05,06,07,08,09,11,12,17,16状态皆可通过
        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode().equals(crdSysOrder.getCrdOrderStates())
            || CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode().equals(crdSysOrder.getCrdOrderStates())
            || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode().equals(crdSysOrder.getCrdOrderStates())) {
            // 3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、 交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致
            String checkRs = compareOrder(crdSysOrder, crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkRs)) {
                return checkRs;
            }
        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode().equals(crdSysOrder.getCrdOrderStates())) {
            //18,19状态,上传正常终止,返回前端
            return ResponseCode.CARD_UPLOAD_NORMAL_END;
        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(crdSysOrder.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode().equals(crdSysOrder.getCrdOrderStates())) {
            //03,10,上传异常终止
            return ResponseCode.CARD_UPLOAD_ERROR_END;
        } else {
            return ResponseCode.CARD_ORDER_STATES_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 结果上传(外部调用)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResult(BJCrdSysOrderDTO crdDTO) {
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        BJCrdSysOrderDTO crdSysOrderDTO = new BJCrdSysOrderDTO();
        try {
            //校验空值
            String emtpyCode = checkUploadEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(emtpyCode)) {
                setErrorResponse(crdDTO, response, emtpyCode);
                logRespcode = emtpyCode;
                respexplain.append(response.getMessage());
            } else {
                //查询北京前置ip,port
                Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
                String ip = retMap.get("IP") + "";
                String port = retMap.get("PROT") + "";
                respexplain.append("[1.校验空值成功,ip=" + ip + ",port" + port + "]");
                //如果交易不为未知
                if (!CardTxnStatEnum.TXN_STAT_UNKNOW.getCode().equals(crdDTO.getTxnstat())) {
                    BeanUtils.copyProperties(crdDTO, crdSysOrderDTO);
                    //查询卡服务订单
                    CrdSysOrder crdSysOrder = icdcRechargeService.queryCardSysOrderByPrdId(crdSysOrderDTO.getPrdordernum());
                    // 加解密校验
                    String specdata = crdDTO.getSpecdata().getNextstep();
                    String cipheraction = crdDTO.getSpecdata().getCipheraction();
                    String encryptCode = BJStringUtil.checkEncrypt(specdata, cipheraction);
                    crdSysOrderDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
                    //校验失败
                    if (!ResponseCode.SUCCESS.equals(encryptCode)) {
                        setErrorResponse(crdDTO, response, ResponseCode.CARD_UPLOAD_ERROR_END);
                        logRespcode = ResponseCode.CARD_UPLOAD_ERROR_END;
                        respexplain.append("加解密校验失败");
                        //校验失败更新卡服务订单状态
                        encryptResultUpdOrderFunFirst(crdSysOrderDTO);
                    } else {
                        respexplain.append(",[加解密校验成功]");
                        //1.根据结果上传交易状态更新数据库卡服务订单状态
                        crdSysOrderDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
                        String updCode = uploadResultUpdOrderFunFirst(crdSysOrderDTO);
                        //2.判断更新结果
                        if (!ResponseCode.SUCCESS.equals(updCode)) {
                            setErrorResponse(crdSysOrderDTO, response, updCode);
                            logRespcode = updCode;
                            respexplain.append(response.getMessage());
                        } else {//更新成功
                            //更新通讯流水号
                            bjCardTopupService.updateSamsigninfo(crdDTO.getPosid(), crdDTO.getPosseq(), crdDTO.getPostransseq());
                            //向城市前置发送结果上传报文
                            BJCrdSysOrderDTO retSysOrderDTO = bjCityFrontSocketService.sendBJCityFrontSocket(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
                            crdSysOrderDTO.setRespcode(retSysOrderDTO.getRespcode());
                            crdSysOrderDTO.setMessagetype(retSysOrderDTO.getMessagetype());
                            crdSysOrderDTO.setVer(retSysOrderDTO.getVer());
                            crdSysOrderDTO.setTradeendflag(retSysOrderDTO.getTradeendflag());
                            crdSysOrderDTO.setTradestartflag(retSysOrderDTO.getTradestartflag());
                            if (retSysOrderDTO.getSpecdata() != null) {
                                if (StringUtils.isNotBlank(retSysOrderDTO.getSpecdata().getCipheraction())) {
                                    crdSysOrderDTO.getSpecdata().setCipheraction(retSysOrderDTO.getSpecdata().getCipheraction());
                                }
                                if (StringUtils.isNotBlank(retSysOrderDTO.getSpecdata().getNextstep())) {
                                    crdSysOrderDTO.getSpecdata().setNextstep(retSysOrderDTO.getSpecdata().getNextstep());
                                }
                            }
                            if (!ResponseCode.SUCCESS.equals(retSysOrderDTO.getRespcode())) {
                                if (StringUtils.isBlank(crdSysOrderDTO.getTradeendflag())) {
                                    crdSysOrderDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                                }
                                if (StringUtils.isBlank(retSysOrderDTO.getRespcode())) {
                                    setErrorResponse(retSysOrderDTO, response, ResponseCode.CARD_FRONTRESP_NULL);
                                    logRespcode = ResponseCode.CARD_FRONTRESP_NULL;
                                    respexplain.append(response.getMessage());
                                } else {
                                    setErrorResponse(retSysOrderDTO, response, retSysOrderDTO.getRespcode());
                                    logRespcode = retSysOrderDTO.getRespcode();
                                    respexplain.append("与城市前置交互失败");
                                }
                            }
                            //结果上传后更新订单
                            String updndCode = uploadResultUpdOrderFunSecond(crdSysOrderDTO);
                            if (!ResponseCode.SUCCESS.equals(updndCode)) {
                                setErrorResponse(crdSysOrderDTO, response, updndCode);
                                respexplain.append(response.getMessage());
                            } else {
                                response.setResponseEntity(crdSysOrderDTO);//响应体
                            }
                        }
                    }
                } else {
                    //交易状态为未知,直接透传
                    BJCrdSysOrderDTO retSysOrderDTO = bjCityFrontSocketService.sendBJCityFrontSocket(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
                    if (!ResponseCode.SUCCESS.equals(retSysOrderDTO.getRespcode())) {
                        setErrorResponse(retSysOrderDTO, response, retSysOrderDTO.getRespcode());
                        logRespcode = retSysOrderDTO.getRespcode();
                        respexplain.append("与城市前置交互失败");
                    } else {
                        response.setResponseEntity(retSysOrderDTO);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            respexplain.append(e.getMessage());
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            // 记录日志
            String respex = respexplain.toString();
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值][BJ][V61]结果上传(外)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 结果上传前更新订单
     */
    private String uploadResultUpdOrderFunFirst(BJCrdSysOrderDTO crdDTO) {
        String txnStat = crdDTO.getTxnstat();//卡服务订单交易状态
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        Map<String, Object> value = new HashMap<String, Object>(1);
        value.put("RESULT_SEND_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        value.put("UPDATE_USER", crdDTO.getUserid());
        if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnStat)) {//0:成功
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT.getCode());//1000000012
        } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnStat)) {//1:失败
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode());//1000000011
        } else if (CardTxnStatEnum.TXN_STAT_UNKNOW.equals(txnStat)) {//2:未知
            return ResponseCode.SUCCESS;
        }
        value.put("POS_TRANS_SEQ", BJStringUtil.format10to16(crdDTO.getPostransseq()));
        //结果上传后更新订单
        int updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
        if (updateCount != 1) {
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 结果上传后更新订单
     */
    private String uploadResultUpdOrderFunSecond(BJCrdSysOrderDTO crdDTO) {
        String txnStat = crdDTO.getTxnstat();//服务订单交易状态
        int updateCount = 0;//更新记录条数
        // 1.检查入参
        if (StringUtils.isBlank(txnStat)) {
            return ResponseCode.CARD_TXNSTAT_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getRespcode())) {
            return ResponseCode.CARD_RESPCODE_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_TRADECARD_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getMessagetype())) {
            return ResponseCode.CARD_TRADESTEP_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getVer())) {
            return ResponseCode.CARD_TRADESTEPVER_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getTradeendflag())) {
            return ResponseCode.CARD_TRADEENDFLAG_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getCrdordernum())) {
            return ResponseCode.CARD_CARDORDERNUM_NULL;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        Map<String, Object> value = new HashMap<String, Object>(1);
        // 2.判断上传充值结果
        if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnStat)) {//0:成功
            if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());//1000000018
            } else {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode());//1000000016
            }
        } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnStat)) {//1:失败
            if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());//1000000019
            } else {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode());//1000000017
            }
        } else {//2:未知
            return ResponseCode.SUCCESS;
        }
        // 3.更新卡服务订单状态
        value.put("RESULT_RES_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("RESP_CODE", crdDTO.getRespcode());
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("TRADE_END_FLAG", crdDTO.getTradeendflag());
        value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        value.put("UPDATE_USER", crdDTO.getUserid());
        updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
        if (updateCount != 1) {
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    private String checkUploadEmpty(BJCrdSysOrderDTO crdDTO) {
        // 1.检查入参
        if (StringUtils.isBlank(crdDTO.getTxnstat())) {
            return ResponseCode.CARD_TXNSTAT_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getCrdordernum())) {
            return ResponseCode.CARD_CARDORDERNUM_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getUserid())) {
            return ResponseCode.CARD_USERID_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 加解密校验失败更新订单
     */
    private String encryptResultUpdOrderFunFirst(BJCrdSysOrderDTO crdDTO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        Map<String, Object> value = new HashMap<String, Object>(1);
        value.put("RESULT_SEND_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        value.put("UPDATE_USER", crdDTO.getUserid());
        String txnStat = crdDTO.getTxnstat();
        if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnStat)) {//0:成功
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode());//1000000016
        } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnStat)) {//1:失败
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode());//1000000017
        }
        value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());//1000000010
        value.put("POS_TRANS_SEQ", BJStringUtil.format10to16(crdDTO.getPostransseq()));
        //更新订单
        int updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
        if (updateCount != 1) {
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }
}
