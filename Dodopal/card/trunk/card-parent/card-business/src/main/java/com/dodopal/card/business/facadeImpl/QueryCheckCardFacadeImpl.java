package com.dodopal.card.business.facadeImpl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.QueryCheckCardFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.log.SysLogHelper;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;

@Service("queryCheckCardFacade")
public class QueryCheckCardFacadeImpl implements QueryCheckCardFacade {
    @Autowired
    private LogHelper logHelper;

    @Autowired
    private SysLogHelper sysLogHelper;

    @Autowired
    private CardTopupService cardTopupService;

    /**
     * @Title: 验卡查询接口 queryCheckCard
     * @Description: 完成验卡查询在此方法中调用城市前置验卡方法完成对城市前置系统的报文交互
     * 1.调用queryCheckCardCityFrontFun方法
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardFun(CrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);
        //堆栈信息
        String statckTrace = "";

        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //校验空值
            String checkEmptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                crdDTO.setRespcode(checkEmptyCode);
                response.setResponseEntity(crdDTO);
                response.setCode(checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                respexplain.append("[1.yktcode=" + crdDTO.getYktcode() + "]");
                //调用城市前置验卡方法
                CrdSysOrderDTO retCrdDTO = queryCheckCardCityFrontFun(crdDTO);
                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//城市前置返回交易应答码失败
                    response.setCode(retCrdDTO.getRespcode());
                    logRespcode = retCrdDTO.getRespcode();
                }
                response.setResponseEntity(retCrdDTO);
            }
        }
        catch (Exception e) {
            respexplain.append(",[2.error=" + e.getCause() + "]");
            logRespcode = ResponseCode.SYSTEM_ERROR;
            response.setCode(ResponseCode.SYSTEM_ERROR);
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
            statckTrace = e.getMessage();
        }
        finally {
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]验卡查询(外部调用)", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]验卡查询(外部调用)", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    /**
     * @Description: 1.调用创建订单校验方法（checkOrderCreateFun）; 2.此方法完成卡服务端订单的生成
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> createCrdOrderCardFun(CrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);
        //堆栈信息
        String statckTrace = "";

        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //卡服务订单编号
        String crdOrderNum = "";

        try {
            //校验空值
            String checkEmptyCode = checkCreateEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {//有空值返回异常
                crdDTO.setRespcode(checkEmptyCode);
                response.setResponseEntity(crdDTO);
                response.setCode(checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                respexplain.append("[1.prdordernum=" + crdDTO.getPrdordernum() + "]");
                long limit = Long.valueOf(crdDTO.getCardlimit());
                long txnamt = Long.valueOf(crdDTO.getTxnamt());
                long befamt = Long.valueOf(crdDTO.getBefbal());
                //校验充值金额与卡内余额之和是否超过卡限额
                if ((befamt + txnamt) > limit) {
                    crdDTO.setRespcode(ResponseCode.CARD_LIMIT_ERROR);
                    response.setResponseEntity(crdDTO);
                    response.setCode(ResponseCode.CARD_LIMIT_ERROR);
                    respexplain.append(",[limit=" + limit + ",txnamt=" + txnamt + ",befamt=" + befamt + "]");
                    logRespcode = ResponseCode.CARD_LIMIT_ERROR;
                } else {
                    //根据产品库提供的订单号查询卡服务订单是否存在
                    String prdExist = checkOrderCreateFun(crdDTO.getPrdordernum());
                    respexplain.append(",[2.prdExist=" + prdExist + "]");
                    if (!ResponseCode.SUCCESS.equals(prdExist)) {//产品库号码存在异常
                        response.setCode(prdExist);
                        crdDTO.setRespcode(prdExist);
                        response.setResponseEntity(crdDTO);
                        logRespcode = prdExist;
                    } else {//产品库号码不存在,创建订单并返回卡服务订单编号
                        crdOrderNum = cardTopupService.createCrdSysOrder(crdDTO);
                        respexplain.append(",[3.crdOrderNum=" + crdOrderNum + "]");
                        if (StringUtils.isNotBlank(crdOrderNum)) {
                            crdDTO.setCrdordernum(crdOrderNum);
                            response.setResponseEntity(crdDTO);
                        } else {
                            response.setCode(ResponseCode.SYSTEM_ERROR);
                            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
                            response.setResponseEntity(crdDTO);
                            logRespcode = ResponseCode.SYSTEM_ERROR;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(",[error=" + e.getCause() + "]");
            statckTrace = e.getMessage();
        }
        finally {
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]创建订单(外部调用)", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]创建订单(外部调用)", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    /*
     * 查询校验空值
     */
    private String checkQueryEmpty(CrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_YKTCODE_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 创建订单校验空值方法
     */
    private String checkCreateEmpty(CrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getProductcode())) {
            return ResponseCode.CARD_PRODUCTCODE_NULL;
        }/* else if (StringUtils.isBlank(crdDTO.getMercode())) {
            return ResponseCode.CARD_MERCODE_NULL;
         }*/else if (StringUtils.isBlank(crdDTO.getUsercode())) {
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
        } else if (crdDTO.getApdu() == null || crdDTO.getApdu().length == 0) {
            return ResponseCode.CARD_APDU_NULL;
        } else if (StringUtils.isBlank(crdDTO.getChargetype())) {
            return ResponseCode.CARD_CHARGETYPE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCardlimit())) {
            return ResponseCode.CARD_LIMIT_NULL;
        } /*else if (StringUtils.isBlank(crdDTO.getLoadflag())) {
            return ResponseCode.CARD_LOADFLAG_NULL;
          }*/
        return ResponseCode.SUCCESS;
    }

    /**
     * @Title: queryCheckCardCityFront
     * @Description:
     * 调用城市前置验卡方法：城市前置提供给卡服务调用，用于获得城市前置返回读卡密钥(CPU卡此处圈存初始化指令中不包括交易金额)、卡号、卡面号、
     * 交易流水号、交易日期、交易时间、卡内允许最大金额。 1.根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
     * 2.调用发给城市前置的报文接口(CardRechargeService.queryCheckCardSendFun)
     * @param 所需参数:
     * @return 返回参数:
     */
    private CrdSysOrderDTO queryCheckCardCityFrontFun(CrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        String inpras = JSONObject.toJSONString(crdDTO);

        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();

        try {
            //根据yktcode获得访问城市前置具体的ip地址、端口号、卡内限额
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            respexplain.append("[1.ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + ",cardLimit=" + retMap.get("CARDLIMIT") + "]");
            //把取出的卡内限额传给城市前置,之后以城市前置返回的为准
            crdDTO.setCardlimit(retMap.get("CARDLIMIT") + "");
            //调用城市前置接口
            retCrdDTO = cardTopupService.queryCheckCardSendFun(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO);
            if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                logRespcode = retCrdDTO.getRespcode();
            }
            respexplain.append(",[2.城市前置返回respcode=" + retCrdDTO.getRespcode() + ",cardLimit=" + retCrdDTO.getCardlimit() + "]");
        }
        catch (Exception e) {
            BeanUtils.copyProperties(crdDTO, retCrdDTO);
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e);
        }
        finally {
            logHelper.recordLogFun(inpras, tradeStart, logRespcode, respexplain.toString(), "[充值]验卡查询-调用城市前置", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return retCrdDTO;
    }

    /**
     * @Title: checkOrderCreate
     * @Description: 校验产品库订单号查询卡服务订单是否存在。存在异常返回
     * @param 所需参数:
     * @return 返回参数:
     */
    private String checkOrderCreateFun(String proOrderNum) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        String prdExist = ResponseCode.SUCCESS;
        try {
            //根据产品库订单号查询卡服务订单
            int count = cardTopupService.checkCrdOrderByPrdId(proOrderNum);
            if (count > 0) {
                prdExist = ResponseCode.CARD_PRDORDERNUM_EXIST;
                logRespcode = ResponseCode.CARD_PRDORDERNUM_EXIST;
                respexplain.append("[1.prdCount=" + count + ",产品库订单号已存在]");
            }
        }
        catch (Exception e) {
            prdExist = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e.getMessage());
        }

        logHelper.recordLogFun(proOrderNum, tradeStart, logRespcode, respexplain.toString(), "[充值]创建订单-校验订单", "", proOrderNum, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), prdExist, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return prdExist;
    }

}
