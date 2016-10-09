package com.dodopal.card.business.facadeImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.IcdcConsumeCardFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.log.SysLogHelper;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.service.CardConsumeService;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTradeStartFlagEnum;
import com.dodopal.common.enums.CardTxnStatEnum;
import com.dodopal.common.model.DodopalResponse;

@Service("icdcConsumeCardFacade")
public class IcdcConsumeCardFacadeImpl implements IcdcConsumeCardFacade {

    @Autowired
    private LogHelper logHelper;
    @Autowired
    private SysLogHelper sysLogHelper;

    @Autowired
    private CardTopupService cardTopupService;

    @Autowired
    private CardConsumeService cardConsumeService;

    @Autowired
    private CityFrontSocketService cityFrontSocketService;

    /**
     * 消费验卡(外部调用)
     * @Description: 1.校验产品库订单号查询卡服务订单是否存在。存在异常返回 2.校验消费金额是否大于卡内余额。是异常返回。
     * 3.完成验卡查询在此方法中调用城市前置消费验卡方法完成对城市前置系统的报文交互。4.城市前置返回验卡成功创建订单
     * @param crdDTO
     * @return
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);
        //堆栈信息
        String statckTrace = "";

        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();
        try {
            //校验入参空值
            String checkEmptyCode = checkQueryEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //1.校验产品库订单号查询卡服务订单是否存在
                String prdExist = checkOrderCreateFun(crdDTO.getPrdordernum());
                if (!ResponseCode.SUCCESS.equals(prdExist)) {
                    setErrorResponse(crdDTO, response, prdExist);
                    logRespcode = prdExist;
                    logRespexplain.append(response.getMessage());
                } else {
                    //2.校验消费金额是否大于卡内余额
                    String checkTxnamtCode = checkTxnamt(crdDTO);
                    if (!ResponseCode.SUCCESS.equals(checkTxnamtCode)) {
                        setErrorResponse(crdDTO, response, checkTxnamtCode);
                        logRespcode = checkTxnamtCode;
                        logRespexplain.append(response.getMessage());
                    } else {
                        //3.与城市前置交互
                        retCrdDTO = queryCheckCardCityFrontConsFun(crdDTO);
                        if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//城市前置返回交易应答码失败
                            setErrorResponse(retCrdDTO, response, retCrdDTO.getRespcode());
                            logRespcode = retCrdDTO.getRespcode();
                            logRespexplain.append("与城市前置交互失败");
                        } else {
                            if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag())) {//如果为透传(0)
                                response.setResponseEntity(retCrdDTO);
                                logRespexplain.append(response.getMessage());
                            } else {
                                //4.创建订单
                                String createOrderCode = createCrdOrderCardConsFun(retCrdDTO);
                                retCrdDTO.setRespcode(createOrderCode);
                                response.setResponseEntity(retCrdDTO);
                                response.setCode(createOrderCode);
                                logRespcode = createOrderCode;
                                logRespexplain.append(response.getMessage());
                            }
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
            logRespexplain.append(e.getCause());
            statckTrace = e.getMessage();
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]验卡查询(外部调用)", retCrdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
            sysLogHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]验卡查询(外部调用)", retCrdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    /**
     * 消费申请获得扣款指令方法(外部调用)
     * @param crdDTO
     * @return
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> getDeductOrderConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);
        //堆栈信息
        String statckTrace = "";
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //校验空值
            String checkEmptyCode = checkLoadOrderEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //1.校验订单
                Map<String, Object> map = checkOrderDeductConsFun(crdDTO);
                String checkOrderCode = map.get("retCode") + "";
                String crdOrderNum = map.get("crdOrderNum") + "";
                String crdOrderState = map.get("crdOrderState") + "";
                crdDTO.setCrdordernum(crdOrderNum);
                if (!ResponseCode.SUCCESS.equals(checkOrderCode)) {
                    setErrorResponse(crdDTO, response, checkOrderCode);
                    logRespcode = checkOrderCode;
                    logRespexplain.append(response.getMessage());
                } else {//校验成功
                    logRespexplain.append("[1.startFlag=" + crdDTO.getTradestartflag() + "]");
                    //如果交易开始标志(tradeStartFlag)为透传(0)
                    if (CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode().equals(crdDTO.getTradestartflag())) {
                        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000005
                            //向城市前置发送请求
                            CrdSysOrderDTO retCrdDTO = deductKeyCityFrontConsFun(crdDTO);
                            if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//申请消费秘钥失败
                                //如果申请失败,交易结束标志不为1
                                if (!CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {
                                    retCrdDTO.setTradestartflag(CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode());
                                    retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                                }
                                logRespcode = retCrdDTO.getRespcode();
                                logRespexplain.append("向城市前置申请消费秘钥失败");
                                //5.更新订单(后)
                                retCrdDTO.setCrdordernum(crdOrderNum);
                                retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                String updEndCode = deductUpdOrderConsAfter(retCrdDTO);
                                if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                    setErrorResponse(crdDTO, response, updEndCode);
                                    logRespcode = updEndCode;
                                    logRespexplain.append(response.getMessage());
                                } else {
                                    response.setCode(retCrdDTO.getRespcode());
                                    response.setResponseEntity(retCrdDTO);
                                }
                            } else {//申请成功
                                //结束标志为透传(0),直接透传回前端
                                if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag())) {
                                    response.setCode(retCrdDTO.getRespcode());
                                    response.setResponseEntity(retCrdDTO);
                                } else if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {//结束标志为 结束(1)
                                    //5.更新订单(后)
                                    retCrdDTO.setCrdordernum(crdOrderNum);
                                    retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                    String updEndCode = deductUpdOrderConsAfter(retCrdDTO);
                                    if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                        setErrorResponse(crdDTO, response, updEndCode);
                                        logRespcode = updEndCode;
                                        logRespexplain.append(response.getMessage());
                                    } else {
                                        response.setCode(retCrdDTO.getRespcode());
                                        response.setResponseEntity(retCrdDTO);
                                    }
                                }
                            }
                        } else {
                            setErrorResponse(crdDTO, response, ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR);
                            logRespcode = ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR;
                            logRespexplain.append(response.getMessage());
                        }
                    } else {//tradeStartFlag为1、2时
                        logRespexplain.append(",[2.orderState=" + crdOrderState + "]");
                        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000007,重发
                            //按照卡服务充值订单号查询对应的卡服务补充信息表中的充值密钥(CHARGE_KEY)返回产品库
                            String chargeKey = cardTopupService.reSendOrderByCrdnum(crdOrderNum);
                            String[] apdu = JSONObject.parseObject(chargeKey, String[].class);
                            //设置交易结束标志为2
                            //                            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_RESEND.getCode());
                            crdDTO.setCrdordernum(crdOrderNum);
                            crdDTO.setApdu(apdu);
                            crdDTO.setRespcode(ResponseCode.SUCCESS);
                            response.setResponseEntity(crdDTO);
                            logRespexplain.append("重新获取消费指令成功");
                        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000005,返回超时等待
                            //设置交易结束标志为2
                            //                            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_RESEND.getCode());
                            //重发计数器
                            cardTopupService.updateKeyCountByCrdnum(crdOrderNum);
                            //返回前端超时等待应答码
                            crdDTO.setRespcode(ResponseCode.CARD_RESEND_WARN);
                            response.setResponseEntity(crdDTO);
                            response.setCode(ResponseCode.CARD_RESEND_WARN);
                            logRespcode = ResponseCode.CARD_RESEND_WARN;
                            logRespexplain.append(response.getMessage());
                        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000006,状态不对
                            //设置交易结束标志为2
                            //                            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_RESEND.getCode());
                            //重发计数器
                            cardTopupService.updateKeyCountByCrdnum(crdOrderNum);
                            //查询城市前置返回错误码
                            String cityErrorCode = cardConsumeService.findCrdSysConsOrderByPrdnum(crdDTO.getPrdordernum()).getRespCode();
                            crdDTO.setRespcode(cityErrorCode);
                            response.setResponseEntity(crdDTO);
                            response.setCode(cityErrorCode);
                            logRespcode = cityErrorCode;
                            logRespexplain.append("卡服务订单状态不正确(申请秘钥失败)");
                        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000003,正常流程
                            //2.更新订单(前)
                            String updStartCode = deductUpdOrderConsFun(crdDTO);
                            if (!ResponseCode.SUCCESS.equals(updStartCode)) {//更新订单失败
                                setErrorResponse(crdDTO, response, updStartCode);
                                logRespcode = updStartCode;
                                logRespexplain.append(response.getMessage());
                            } else {
                                //3.可疑处理
                                deductBeforeSuspiciousConsFun();
                                //4.调用前置申请扣款指令
                                CrdSysOrderDTO retCrdDTO = deductKeyCityFrontConsFun(crdDTO);
                                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//申请消费秘钥失败
                                    //如果申请失败,交易结束标志不为1
                                    if (!CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {
                                        retCrdDTO.setTradestartflag(CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode());
                                        retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                                    }
                                    response.setCode(retCrdDTO.getRespcode());
                                    response.setResponseEntity(retCrdDTO);
                                    logRespcode = retCrdDTO.getRespcode();
                                    logRespexplain.append("向城市前置申请消费秘钥失败");
                                    //5.更新订单(后)
                                    retCrdDTO.setCrdordernum(crdOrderNum);
                                    retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                    String updEndCode = deductUpdOrderConsAfter(retCrdDTO);
                                    if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                        setErrorResponse(crdDTO, response, updEndCode);
                                        logRespcode = updEndCode;
                                        logRespexplain.append(response.getMessage());
                                    } else {
                                        response.setResponseEntity(retCrdDTO);
                                    }
                                } else {//申请成功
                                    //结束标志为透传(0),直接透传回前端
                                    if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag())) {
                                        response.setCode(retCrdDTO.getRespcode());
                                        response.setResponseEntity(retCrdDTO);
                                    } else if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {//结束标志为 结束(1)
                                        //5.更新订单(后)
                                        retCrdDTO.setCrdordernum(crdOrderNum);
                                        retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                        String updEndCode = deductUpdOrderConsAfter(retCrdDTO);
                                        if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                            setErrorResponse(crdDTO, response, updEndCode);
                                            logRespcode = updEndCode;
                                            logRespexplain.append(response.getMessage());
                                        } else {
                                            response.setCode(retCrdDTO.getRespcode());
                                            response.setResponseEntity(retCrdDTO);
                                        }
                                    }
                                }
                                //6.消费申请后可疑处理
                                deductAfterSuspiciousConsFun();
                            }
                        } else {//卡服务订单状态不正确
                            setErrorResponse(crdDTO, response, ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR);
                            logRespcode = ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR;
                            logRespexplain.append(response.getMessage());
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
            statckTrace = e.getMessage();
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
            sysLogHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }

        // logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return response;
    }

    /**
     * 消费结果上传方法(外部调用)
     * @param crdDTO
     * @return
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadResultConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);
        //堆栈信息
        String statckTrace = "";

        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //校验空值
            String checkEmptyCode = checkUploadEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //1.结果上传校验更新订单
                String chkUpdCode = uploadResultChkUdpOrderConsFun(crdDTO);
                if (!ResponseCode.SUCCESS.equals(chkUpdCode)) {
                    setErrorResponse(crdDTO, response, chkUpdCode);
                    logRespcode = chkUpdCode;
                    logRespexplain.append("上传校验更新订单失败");
                } else {//2.开始上传
                    //2.1上传前更新订单
                    String beforeUpdCode = uploadResultUpdOrderBefConsFun(crdDTO);
                    if (!ResponseCode.SUCCESS.equals(beforeUpdCode)) {
                        setErrorResponse(crdDTO, response, beforeUpdCode);
                        logRespcode = chkUpdCode;
                        logRespexplain.append("上传前更新订单失败");
                    } else {
                        //2.2上传结果到城市前置
                        CrdSysOrderDTO retCrdDTO = uploadResultCityFrontConsFun(crdDTO);
                        response.setResponseEntity(retCrdDTO);
                        if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                            setErrorResponse(retCrdDTO, response, retCrdDTO.getRespcode());
                            logRespcode = retCrdDTO.getRespcode();
                            logRespexplain.append("调用城市前置成功,返回应答码异常");
                        }
                        //2.3上传后更新订单
                        String afterUpdCode = uploadResultUpdOrderAfterConsFun(retCrdDTO);
                        if (!ResponseCode.SUCCESS.equals(afterUpdCode)) {
                            setErrorResponse(retCrdDTO, response, retCrdDTO.getRespcode());
                            logRespcode = retCrdDTO.getRespcode();
                            logRespexplain.append("上传后更新订单失败");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
            statckTrace = e.getMessage();
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
            sysLogHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }

        // logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return response;
    }

    /**
     * 前端失败校验方法(外部调用)
     * @param crdDTO
     * @return
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> frontFailReportConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //校验空值
            String checkEmptyCode = checkFailUploadEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                //1.校验订单状态
                String checkOrderCode = checkOrderState(crdDTO.getPrdordernum());
                if (!ResponseCode.SUCCESS.equals(checkOrderCode)) {
                    setErrorResponse(crdDTO, response, checkOrderCode);
                    logRespcode = checkEmptyCode;
                    logRespexplain.append(response.getMessage());
                } else {
                    //2.更新订单状态
                    updOrderFailReport(crdDTO);
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]失败上报(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return response;
    }

    /*
     * 失败上报更新订单
     */
    private void updOrderFailReport(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String updCode = ResponseCode.SUCCESS;

        CrdSysConsOrder consOrder = new CrdSysConsOrder();
        consOrder.setProOrderNum(crdDTO.getPrdordernum());//产品库订单号
        consOrder.setUpdateUser(crdDTO.getUserid());
        consOrder.setRespCode(crdDTO.getRespcode());
        consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());//卡片充值失败(1000000009)
        cardConsumeService.updateConsOrderFailReport(consOrder);

        logRespcode = updCode;
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]失败上报-更新订单", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(updCode), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
    }

    /*
     * 前端失败上报校验订单
     */
    private String checkOrderState(String prdNum) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String checkOrderCode = ResponseCode.SUCCESS;

        String crdNum = "";

        //根据产品库订单号查询卡服务订单数量
        int count = cardConsumeService.checkCrdConsOrderByPrdId(prdNum);
        if (count != 1) {
            checkOrderCode = ResponseCode.CARD_CONSUME_PRDNUM_COUNT_ERROR;
            logRespexplain.append("[1.prdCount=" + count + ",订单数量异常]");
        } else {
            CrdSysConsOrder order = cardConsumeService.findCrdSysConsOrderByPrdnum(prdNum);
            crdNum = order.getCrdOrderNum();
            logRespexplain.append(",[2.crdOrderStates=" + order.getCrdOrderStates() + "]");
            if (!CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(order.getCrdOrderStates())) {
                //2.判断订单状态是否是申请读卡密钥成功(1000000003)时，否则进行异常处理
                checkOrderCode = ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR;
            }
        }
        logRespcode = checkOrderCode;
        //记录日志
        logHelper.recordLogFun(prdNum, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]失败上报-校验订单", crdNum, prdNum, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), checkOrderCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return checkOrderCode;
    }

    /*
     * 失败校验空值
     */
    private String checkFailUploadEmpty(CrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_CONSUME_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getRespcode())) {
            return ResponseCode.CARD_CONSUME_RESPCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getUserid())) {
            return ResponseCode.CARD_CONSUME_USERID_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 验卡空值校验
     */
    private String checkQueryEmpty(CrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_CONSUME_YKTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCitycode())) {
            return ResponseCode.CARD_CONSUME_CITYCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_CONSUME_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getBefbal())) {
            return ResponseCode.CARD_CONSUME_BEFBAL_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_CONSUME_TXNAMT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getMercode())) {
            return ResponseCode.CARD_CONSUME_MERCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getUsercode())) {
            return ResponseCode.CARD_CONSUME_USERCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getUserid())) {
            return ResponseCode.CARD_CONSUME_USERID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_CONSUME_TRADECARD_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCardtype())) {
            return ResponseCode.CARD_CONSUME_CARDTYPE_NULL;
        } else if (crdDTO.getSpecdata() != null) {
            if (StringUtils.isBlank(crdDTO.getSpecdata().getUid())) {
                return ResponseCode.CARD_CONSUME_UID_NULL;
            }
        } else if (StringUtils.isBlank(crdDTO.getPostype())) {
            return ResponseCode.CARD_CONSUME_POSTYPE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_CONSUME_POSID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getSource())) {
            return ResponseCode.CARD_CONSUME_SOURCE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getChargetype())) {
            return ResponseCode.CARD_CONSUME_CHARGETYPE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getMessagetype())) {
            return ResponseCode.CARD_CONSUME_TRADESTEP_NULL;
        } else if (StringUtils.isBlank(crdDTO.getVer())) {
            return ResponseCode.CARD_CONSUME_VER_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 校验产品库订单号查询卡服务订单是否存在
     */
    private String checkOrderCreateFun(String proOrderNum) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;

        String prdExist = ResponseCode.SUCCESS;
        //根据产品库订单号查询卡服务订单数量
        int count = cardConsumeService.checkCrdConsOrderByPrdId(proOrderNum);
        if (count > 0) {
            prdExist = ResponseCode.CARD_CONSUME_PRDORDERNUM_EXIST;
            logRespcode = ResponseCode.CARD_CONSUME_PRDORDERNUM_EXIST;
            logRespexplain.append("[1.prdCount=" + count + ",产品库订单号已存在]");
        }
        //记录日志
        logHelper.recordLogFun(proOrderNum, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]验卡查询-校验产品库订单", "", proOrderNum, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), prdExist, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return prdExist;
    }

    /*
     * 校验消费金额是否大于卡内余额
     */
    private String checkTxnamt(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String checkTxnamtCode = ResponseCode.SUCCESS;

        long txnamt = Long.valueOf(crdDTO.getTxnamt());
        long befbal = Long.valueOf(crdDTO.getBefbal());
        logRespexplain.append("[1.txnamt=" + txnamt + "]");
        logRespexplain.append(",[2.befbal=" + befbal + "]");
        if (txnamt > befbal) {
            checkTxnamtCode = ResponseCode.CARD_TXNAMT_BEFBAL_ERROR;
            logRespcode = ResponseCode.CARD_TXNAMT_BEFBAL_ERROR;
            logRespexplain.append(",消费金额大于卡内余额");
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]验卡查询-校验消费金额", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), checkTxnamtCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return checkTxnamtCode;
    }

    /*
     * 验卡调用城市前置
     */
    private CrdSysOrderDTO queryCheckCardCityFrontConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();
        try {
            logRespexplain.append("[1.yktCode=" + crdDTO.getYktcode() + ",citycode=" + crdDTO.getCitycode() + "]");
            //根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                BeanUtils.copyProperties(crdDTO, retCrdDTO);
                retCrdDTO.setRespcode(ResponseCode.CARD_CONSUME_YKTINFO_ERROR);
                logRespcode = ResponseCode.CARD_CONSUME_YKTINFO_ERROR;
            } else {
                logRespexplain.append("[2.ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + "]");
                //调用城市前置接口
                retCrdDTO = cityFrontSocketService.sendCityFrontSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                    logRespcode = retCrdDTO.getRespcode();
                } else {
                    retCrdDTO.setSpecdata(crdDTO.getSpecdata());
                }
            }
        }
        catch (Exception e) {
            BeanUtils.copyProperties(crdDTO, retCrdDTO);
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]验卡查询-调用城市前置", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCrdDTO;
    }

    /*
     * 订单创建方法
     */
    private String createCrdOrderCardConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        //卡服务订单编号
        String crdOrderNum = "";
        String createOrderCode = ResponseCode.SUCCESS;
        try {
            crdOrderNum = cardConsumeService.createCrdSysConsOrder(crdDTO);
            crdDTO.setCrdordernum(crdOrderNum);
            if (StringUtils.isBlank(crdOrderNum)) {
                createOrderCode = ResponseCode.CARD_CONSUME_CREATE_ORDER_ERROR;
                logRespexplain.append("创建订单失败");
            }
        }
        catch (Exception e) {
            createOrderCode = ResponseCode.CARD_CONSUME_CREATE_ORDER_ERROR;
            logRespexplain.append(e.getMessage());
        }
        logRespcode = createOrderCode;
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]验卡查询-创建订单", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), createOrderCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return createOrderCode;
    }

    /*
     * 申请消费校验空值
     */
    private String checkLoadOrderEmpty(CrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_CONSUME_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCitycode())) {
            return ResponseCode.CARD_CONSUME_CITYCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_CONSUME_YKTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_CONSUME_TRADECARD_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_CONSUME_POSID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getBefbal())) {
            return ResponseCode.CARD_CONSUME_BEFBAL_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_CONSUME_TXNAMT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradestartflag())) {
            return ResponseCode.CARD_CONSUME_TRADESTARTFLAG_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 消费申请卡服务校验订单方法
     */
    private Map<String, Object> checkOrderDeductConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        //卡服务订单号
        String crdOrderNum = "";
        String crdOrderState = "";

        try {
            //1.通过传入的产品库订单号，找到对应的卡服务订单记录，没找到进行异常处理
            int count = cardConsumeService.checkCrdConsOrderByPrdId(crdDTO.getPrdordernum());
            logRespexplain.append("[1.prdCount=" + count + "]");
            if (count != 1) {//异常
                retCode = ResponseCode.CARD_CONSUME_PRDNUM_COUNT_ERROR;
            } else {
                CrdSysConsOrder order = cardConsumeService.findCrdSysConsOrderByPrdnum(crdDTO.getPrdordernum());
                crdOrderNum = order.getCrdOrderNum();
                crdOrderState = order.getCrdOrderStates();
                String compareCode = compareOrder(order, crdDTO);
                if (!ResponseCode.SUCCESS.equals(compareCode)) {
                    //3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致，否则进行异常处理
                    retCode = compareCode;
                }
            }
        }
        catch (Exception e) {
            retCode = ResponseCode.SYSTEM_ERROR;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("retCode", retCode);
        map.put("crdOrderNum", crdOrderNum);
        map.put("crdOrderState", crdOrderState);

        logRespcode = retCode;

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请-校验订单", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(map), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return map;
    }

    /*
     * 比较前端参数与订单是否相同
     */
    private String compareOrder(CrdSysConsOrder order, CrdSysOrderDTO crdDTO) {
        if (!crdDTO.getTradecard().equals(order.getCheckCardNo())) {//tradecard
            return ResponseCode.CARD_CONSUME_TRADECARD_ERROR;
        } else if (!crdDTO.getYktcode().equals(order.getYktCode())) {//一卡通编号 (yktcode)
            return ResponseCode.CARD_CONSUME_YKTCODE_ERROR;
        } else if (!crdDTO.getCitycode().equals(order.getCityCode())) {//城市代码 (citycode)
            return ResponseCode.CARD_CONSUME_CITYCODE_ERROR;
        } else if (!crdDTO.getPosid().equals(order.getPosCode())) {//设备编号 (posid)
            return ResponseCode.CARD_CONSUME_POSID_ERROR;
        } else if (Integer.valueOf(crdDTO.getBefbal()) != order.getBefbal().intValue()) {//交易前卡余额 (befbal)
            return ResponseCode.CARD_CONSUME_BEFBAL_ERROR;
        } else if (Integer.valueOf(crdDTO.getTxnamt()) != order.getTxnAmt().intValue()) {//交易金额 (txnamt)
            return ResponseCode.CARD_CONSUME_TXNAMT_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    /*
     * 申请消费更新卡服务订单
     */
    private String deductUpdOrderConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        try {
            logRespexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum() + "],[3.tradestartflag=" + crdDTO.getTradestartflag() + "],[4.tradeendflag=" + crdDTO.getTradeendflag() + "]");
            cardConsumeService.updateCrdSysConsOrderByCrdnum(crdDTO);
        }
        catch (Exception e) {
            logRespexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请-更新订单(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCode;
    }

    private String deductUpdOrderConsAfter(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        try {
            logRespexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum() + "],[3.tradestartflag=" + crdDTO.getTradestartflag() + "],[4.tradeendflag=" + crdDTO.getTradeendflag() + "]");
            cardConsumeService.updateCrdSysConsOrderAfterByCrdnum(crdDTO);
        }
        catch (Exception e) {
            logRespexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请-更新订单(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCode;
    }

    /**
     * 消费申请前可疑处理方法
     */
    private void deductBeforeSuspiciousConsFun() {
    }

    /**
     * 调用前置申请消费秘钥
     */
    private CrdSysOrderDTO deductKeyCityFrontConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();

        //        Map<String, Object> map = new HashMap<String, Object>();
        try {
            logRespexplain.append("[1.yktCode=" + crdDTO.getYktcode() + ",citycode=" + crdDTO.getCitycode() + "]");
            //            map.put("yktCode", crdDTO.getYktcode());
            //            map.put("cityId", crdDTO.getCitycode());
            //根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                BeanUtils.copyProperties(crdDTO, retCrdDTO);
                retCrdDTO.setRespcode(ResponseCode.CARD_CONSUME_YKTINFO_ERROR);
                logRespcode = ResponseCode.CARD_CONSUME_YKTINFO_ERROR;
            } else {
                logRespexplain.append("[2.ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + "]");
                //调用城市前置接口
                retCrdDTO = cityFrontSocketService.sendCityFrontSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                    logRespcode = retCrdDTO.getRespcode();
                } else {
                    retCrdDTO.setSpecdata(crdDTO.getSpecdata());
                }
            }
        }
        catch (Exception e) {
            BeanUtils.copyProperties(crdDTO, retCrdDTO);
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]消费申请-申请消费秘钥", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCrdDTO;
    }

    /**
     * 消费申请后可疑处理方法
     */
    private void deductAfterSuspiciousConsFun() {
    }

    /*
     * 申请消费校验空值
     */
    private String checkUploadEmpty(CrdSysOrderDTO crdDTO) {
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_CONSUME_PRDORDERNUM_NULL;
        } else if (StringUtils.isBlank(crdDTO.getCitycode())) {
            return ResponseCode.CARD_CONSUME_CITYCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getYktcode())) {
            return ResponseCode.CARD_CONSUME_YKTCODE_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_CONSUME_TRADECARD_NULL;
        } else if (StringUtils.isBlank(crdDTO.getPosid())) {
            return ResponseCode.CARD_CONSUME_POSID_NULL;
        } else if (StringUtils.isBlank(crdDTO.getBefbal())) {
            return ResponseCode.CARD_CONSUME_BEFBAL_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnamt())) {
            return ResponseCode.CARD_CONSUME_TXNAMT_NULL;
        } else if (StringUtils.isBlank(crdDTO.getTxnstat())) {
            return ResponseCode.CARD_CONSUME_TXNSTAT_NULL;
        } else if (crdDTO.getSpecdata() == null) {
            return ResponseCode.CARD_CONSUME_SPECDATA_NULL;
        } else if (StringUtils.isBlank(crdDTO.getSpecdata().getApdudata())) {
            return ResponseCode.CARD_CONSUME_APDUDATA_NULL;
        } else if (StringUtils.isBlank(crdDTO.getMessagetype())) {
            return ResponseCode.CARD_CONSUME_TRADESTEP_NULL;
        } else if (StringUtils.isBlank(crdDTO.getVer())) {
            return ResponseCode.CARD_CONSUME_VER_NULL;
        }
        return ResponseCode.SUCCESS;
    }

    /**
     * 消费结果上传校验更新订单方法
     */
    private String uploadResultChkUdpOrderConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String chkUpdCode = ResponseCode.SUCCESS;
        String crdOrderNum = "";
        try {
            //1.通过传入的产品库订单号，找到对应的卡服务订单记录，没找到进行异常处理
            int count = cardConsumeService.checkCrdConsOrderByPrdId(crdDTO.getPrdordernum());
            logRespexplain.append("[1.prdCount=" + count + "]");
            if (count != 1) {//异常
                chkUpdCode = ResponseCode.CARD_CONSUME_PRDNUM_COUNT_ERROR;
            } else {
                CrdSysConsOrder order = cardConsumeService.findCrdSysConsOrderByPrdnum(crdDTO.getPrdordernum());
                crdOrderNum = order.getCrdOrderNum();
                crdDTO.setCrdordernum(crdOrderNum);
                logRespexplain.append(",[2.crdOrderStates=" + order.getCrdOrderStates() + "]");
                if (!CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(order.getCrdOrderStates())) {
                    //2.判断订单状态是否是申请扣款密钥成功(1000000007)，否则进行异常处理
                    chkUpdCode = ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR;
                } else {
                    //3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致，否则进行异常处理
                    String compareCode = compareOrder(order, crdDTO);
                    if (!ResponseCode.SUCCESS.equals(compareCode)) {
                        chkUpdCode = compareCode;
                        logRespexplain.append(",[入参与卡服务订单不一致]");
                    } else {
                        //4.更新订单
                        String updCode = updConsOrderUpload(crdDTO);
                        if (!ResponseCode.SUCCESS.equals(updCode)) {
                            chkUpdCode = updCode;
                            logRespexplain.append(",[更新订单失败]");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            chkUpdCode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }

        logRespcode = chkUpdCode;

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传-校验更新", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), chkUpdCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return chkUpdCode;
    }

    /*
     * 上传更新订单
     */
    private String updConsOrderUpload(CrdSysOrderDTO crdDTO) {
        String updCode = ResponseCode.SUCCESS;
        String txnstat = crdDTO.getTxnstat();//交易状态
        String apduData = crdDTO.getSpecdata().getApdudata();

        CrdSysConsOrder order = new CrdSysConsOrder();
        CrdSysSupplement supplement = new CrdSysSupplement();

        order.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
        order.setCardTac(apduData);//TAC
        supplement.setCrdOrderNum(crdDTO.getCrdordernum());
        if (StringUtils.isNotBlank(crdDTO.getUserid())) {
            order.setUpdateUser(crdDTO.getUserid());
            supplement.setUpdateUser(crdDTO.getUserid());
        }
        supplement.setResultYktmw(JSONObject.toJSONString(crdDTO.getSpecdata()));//特属域

        //当txnstat=0(成功)并且apdudata非全FFFFFFFFFFFFFFFF
        if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnstat) && !CardConstants.CARD_APDUDATA_FAIL.equals(apduData)) {
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode());//1000000008卡片扣款成功
            //            order.setBlackAmt(Integer.valueOf(crdDTO.getBefbal()) + Integer.valueOf(crdDTO.getTxnamt()));//blackamt=txnamt+befbal
            order.setBlackAmt(Integer.valueOf(crdDTO.getBlackamt()));
        } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnstat) && CardConstants.CARD_APDUDATA_FAIL.equals(apduData)) {//当txnstat=1(失败)并且apdudata全FFFFFFFFFFFFFFFF
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());//1000000009卡片扣款失败
            //            order.setBlackAmt(Integer.valueOf(crdDTO.getBefbal()));
            order.setBlackAmt(Integer.valueOf(crdDTO.getBlackamt()));
        } else if (CardTxnStatEnum.TXN_STAT_UNKNOW.getCode().equals(txnstat) && CardConstants.CARD_APDUDATA_FAIL.equals(apduData)) {//当txnstat=2(未知)并且apdudata全FFFFFFFFFFFFFFFF
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());//1000000010卡片扣款未知
        } else {
            updCode = ResponseCode.CARD_CONSUME_TXNSTAT_APDUDATA_ERROR;
            return updCode;
        }
        //更新卡服务及补充表
        cardConsumeService.updateConsOrderChkUpd(order, supplement);
        return updCode;
    }

    /**
     * 上传前更新订单方法
     */
    private String uploadResultUpdOrderBefConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String firstUpdCode = ResponseCode.SUCCESS;

        try {
            String txnStat = crdDTO.getTxnstat();//卡服务订单交易状态
            CrdSysConsOrder consOrder = new CrdSysConsOrder();
            consOrder.setTradeStep(crdDTO.getMessagetype());//消息类型
            consOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//版本号
            consOrder.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
            consOrder.setUpdateUser(crdDTO.getUserid());
            if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnStat)) {//当txnstat=0(成功)
                consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT.getCode());//扣款结果上传申请(1000000012)
                //更新订单
                cardConsumeService.updateConsOrderBefore(consOrder);
            } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnStat)) {//txnstat=1(失败)
                consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode());//一卡通冲正申请(1000000011)
                cardConsumeService.updateConsOrderBefore(consOrder);
            }//txnstat=2(未知)透传
        }
        catch (Exception e) {
            firstUpdCode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append("上传前更新订单失败");
            logRespexplain.append(e);
        }
        logRespcode = firstUpdCode;
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传-上传前更新", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(firstUpdCode), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return firstUpdCode;
    }

    /**
     * 调用前置消费结果上传方法
     */
    private CrdSysOrderDTO uploadResultCityFrontConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();

        //        Map<String, Object> map = new HashMap<String, Object>();
        try {
            logRespexplain.append("[1.yktCode=" + crdDTO.getYktcode() + ",citycode=" + crdDTO.getCitycode() + "]");
            //            map.put("yktCode", crdDTO.getYktcode());
            //            map.put("cityId", crdDTO.getCitycode());
            //根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                BeanUtils.copyProperties(crdDTO, retCrdDTO);
                retCrdDTO.setRespcode(ResponseCode.CARD_CONSUME_YKTINFO_ERROR);
                logRespcode = ResponseCode.CARD_CONSUME_YKTINFO_ERROR;
            } else {
                logRespexplain.append("[2.ip=" + retMap.get("IP") + ",port=" + retMap.get("PROT") + "]");
                //调用城市前置接口
                retCrdDTO = cityFrontSocketService.sendCityFrontSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                    logRespcode = retCrdDTO.getRespcode();
                }
            }
        }
        catch (Exception e) {
            BeanUtils.copyProperties(crdDTO, retCrdDTO);
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传-上传消费结果", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCrdDTO;
    }

    /*
     * 消费结果上传调用前置后更新订单方法
     */
    private String uploadResultUpdOrderAfterConsFun(CrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String afterUpdCode = ResponseCode.SUCCESS;

        try {
            String txnStat = crdDTO.getTxnstat();//卡服务订单交易状态

            CrdSysConsOrder consOrder = new CrdSysConsOrder();
            consOrder.setTradeStep(crdDTO.getMessagetype());//消息类型
            consOrder.setTradeStepVer(crdDTO.getVer());//版本号
            consOrder.setCrdOrderNum(BJStringUtil.removeVerZero(crdDTO.getCrdordernum()));//卡服务订单号
            consOrder.setUpdateUser(crdDTO.getUserid());
            consOrder.setTradeEndFlag(Integer.valueOf(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode()));//交易结束标志
            consOrder.setRespCode(crdDTO.getRespcode());//交易应答码

            if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnStat)) {//当txnstat=0(成功)
                if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {//城市前置返回成功
                    consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());//一卡通确认扣款成功(1000000018)
                } else {//城市前置返回失败
                    consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode());//一卡通未确认(1000000016)
                }
                //更新订单
                cardConsumeService.updateConsOrderAfter(consOrder);
            } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnStat)) {//txnstat=1(失败)
                if (ResponseCode.SUCCESS.equals(crdDTO.getRespcode())) {//城市前置返回成功
                    consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());//一卡通确认冲正成功(1000000019)
                } else {//城市前置返回失败
                    consOrder.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode());//一卡通冲正失败(1000000017)
                }
                cardConsumeService.updateConsOrderAfter(consOrder);
            }//txnstat=2(未知)透传
        }
        catch (Exception e) {
            afterUpdCode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append("上传前更新订单失败");
            logRespexplain.append(e);
        }
        logRespcode = afterUpdCode;
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费]结果上传-上传后更新", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(afterUpdCode), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return afterUpdCode;
    }

    private void setErrorResponse(CrdSysOrderDTO crdDTO, DodopalResponse<CrdSysOrderDTO> response, String errorCode) {
        crdDTO.setRespcode(errorCode);
        response.setResponseEntity(crdDTO);
        response.setCode(errorCode);
    }

}
