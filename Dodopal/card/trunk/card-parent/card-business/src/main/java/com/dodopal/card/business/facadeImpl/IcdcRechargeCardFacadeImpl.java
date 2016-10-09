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
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.IcdcRechargeCardFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.log.SysLogHelper;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.service.IcdcRechargeService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTradeStartFlagEnum;
import com.dodopal.common.model.DodopalResponse;

@Service("icdcRechargeCardFacade")
public class IcdcRechargeCardFacadeImpl implements IcdcRechargeCardFacade {
    @Autowired
    private IcdcRechargeService icdcRechargeService;
    @Autowired
    private CardTopupService cardTopupService;
    @Autowired
    private CityFrontSocketService cityFrontSocketService;
    @Autowired
    private SysLogHelper sysLogHelper;
    @Autowired
    private LogHelper logHelper;

    /**
     * @Description: 
     * 1.通过传入的产品库订单号，找到对应的卡服务订单记录，没找到并且订单状态是否是创建订单成功(1000000001)，否则进行异常处理进行异常处理
     * ; 2.卡服务订单号获得卡服务订单信息apdu指令域(checkyktkey)并对圈存初始化指令进行交易金额的拼接、 卡号
     * (cardinnerno)、卡面号(cardfaceno)、交易流水号(txnseq)、 交易日期(txndate)、交易时间
     * (交易时间)、设备号编号(posid)时更新卡服务订单状态:申请读卡密钥成功(1000000003
     * )，前卡服务订单状态:创建订单成功(1000000001)， 交易应答码(respcode)，商户订单号(merordercode);
     * 3.其他为异常情况，申请读卡密钥失败(1000000004),前卡服务订单状态:创建订单成功(1000000001)
     * ，交易应答码(respcode)，商户订单号(merordercode);
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> getLoadInitFun(CrdSysOrderDTO crdDTO) {
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        String inPras = JSONObject.toJSONString(crdDTO);
        String statckTrace = "";
        DodopalResponse<CrdSysOrderDTO> response = null;
        CrdSysOrderDTO crdSysOrderDTO = new CrdSysOrderDTO();
        try {
            // 1.检查入参
            if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
                response = new DodopalResponse<CrdSysOrderDTO>();
                response.setResponseEntity(crdDTO);
                response.setCode(ResponseCode.CARD_PRDORDERNUM_NULL);
                return response;
            }
            if (StringUtils.isBlank(crdDTO.getUserid())) {
                response = new DodopalResponse<CrdSysOrderDTO>();
                response.setResponseEntity(crdDTO);
                response.setCode(ResponseCode.CARD_USERID_NULL);
                return response;
            }
            if (StringUtils.isBlank(crdDTO.getTxnamt())) {
                response = new DodopalResponse<CrdSysOrderDTO>();
                response.setResponseEntity(crdDTO);
                response.setCode(ResponseCode.CARD_TXNAMT_NULL);
                return response;
            }
            if (StringUtils.isBlank(crdDTO.getTradecard())) {
                response = new DodopalResponse<CrdSysOrderDTO>();
                response.setResponseEntity(crdDTO);
                response.setCode(ResponseCode.CARD_TRADECARD_NULL);
                return response;
            }
            BeanUtils.copyProperties(crdDTO, crdSysOrderDTO);
            // 2.充值验卡获得圈存初始化指令方法
            response = icdcRechargeService.getLoadInitFun(crdSysOrderDTO);
            response.setResponseEntity(crdSysOrderDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response = new DodopalResponse<CrdSysOrderDTO>();
            response.setResponseEntity(crdDTO);
            response.setCode(ResponseCode.SYSTEM_ERROR);
            respexplain.append(e.getMessage());
            statckTrace = e.getStackTrace().toString();
        }
        finally {
            // 记录日志
            String respex = respexplain.toString();
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值]圈存初始化(外部调用)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值]圈存初始化(外部调用)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    /**
     * @Description: 此方法通过一系列的方法调用完成圈存指令的下发 1.调用checkOrderRechargeFun方法
     * 2.调用rechargeUpdOrderFun方法 3.调用rechargeBeforeSuspiciousFun方法
     * 4.调用rechargeKeyCityFrontFun方法 5.调用rechargeUpdOrderFun方法
     * 6.调用rechargeAfterSuspiciousFun方法
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(CrdSysOrderDTO crdDTO) {
        // 日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        // 日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        // 日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        //入参
        String inPras = JSONObject.toJSONString(crdDTO);
        String statckTrace = "";

        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //校验空值
            String checkEmptyCode = checkLoadOrderEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                crdDTO.setRespcode(checkEmptyCode);
                response.setResponseEntity(crdDTO);
                response.setCode(checkEmptyCode);
                logRespcode = checkEmptyCode;
                respexplain.append(response.getMessage());
            } else {
                //1.校验订单
                Map<String, Object> map = checkOrderRechargeFun(crdDTO);
                String checkOrderCode = map.get("retCode") + "";
                String crdOrderNum = map.get("crdOrderNum") + "";
                String crdOrderState = map.get("crdOrderState") + "";
                if (!ResponseCode.SUCCESS.equals(checkOrderCode)) {
                    crdDTO.setRespcode(checkOrderCode);
                    response.setResponseEntity(crdDTO);
                    response.setCode(checkOrderCode);
                    logRespcode = checkOrderCode;
                    respexplain.append(response.getMessage());
                } else {//校验成功
                    respexplain.append("[1.startFlag=" + crdDTO.getTradestartflag() + "]");
                    //如果交易开始标志(tradeStartFlag)为透传(0)
                    if (CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode().equals(crdDTO.getTradestartflag())) {
                        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000005
                            CrdSysOrderDTO retCrdDTO = rechargeKeyCityFrontFun(crdDTO);
                            if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//向城市前置申请充值失败
                                //如果申请失败,交易结束标志不为1
                                if (!CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {
                                    retCrdDTO.setTradestartflag(CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode());
                                    retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                                }
                                response.setCode(retCrdDTO.getRespcode());
                                response.setResponseEntity(retCrdDTO);
                                logRespcode = retCrdDTO.getRespcode();
                                respexplain.append("向城市前置申请充值秘钥失败");
                                //5.申请后更新订单
                                retCrdDTO.setCrdordernum(crdOrderNum);
                                retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                String updEndCode = rechargeUpdOrderAfter(retCrdDTO);
                                if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                    response.setCode(updEndCode);
                                    response.setResponseEntity(retCrdDTO);
                                    logRespcode = updEndCode;
                                    respexplain.append(response.getMessage());
                                } else {
                                    response.setResponseEntity(retCrdDTO);
                                }
                            } else {//申请成功
                                    //结束标志为透传(0),直接透传回前端
                                if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag())) {
                                    response.setCode(retCrdDTO.getRespcode());
                                    response.setResponseEntity(retCrdDTO);
                                } else if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {//结束标志为 结束(1)
                                    //5.申请后更新订单
                                    retCrdDTO.setCrdordernum(crdOrderNum);
                                    retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                    String updEndCode = rechargeUpdOrderAfter(retCrdDTO);
                                    if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                        response.setCode(updEndCode);
                                        response.setResponseEntity(retCrdDTO);
                                        logRespcode = updEndCode;
                                        respexplain.append(response.getMessage());
                                    } else {
                                        response.setResponseEntity(retCrdDTO);
                                    }
                                }
                            }
                        } else {
                            crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
                            response.setResponseEntity(crdDTO);
                            response.setCode(ResponseCode.CARD_ORDER_STATES_ERROR);
                            logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                            respexplain.append(response.getMessage());
                        }
                    } else {//tradeStartFlag为1、2时
                        respexplain.append(",[2.orderState=" + crdOrderState + "]");
                        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000007,重发
                            //按照卡服务充值订单号查询对应的卡服务补充信息表中的充值密钥(CHARGE_KEY)返回产品库
                            String chargeKey = cardTopupService.reSendOrderByCrdnum(crdOrderNum);
                            String[] apdu = JSONObject.parseObject(chargeKey, String[].class);
                            //设置交易结束标志为2
                            //                            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_RESEND.getCode());
                            crdDTO.setApdu(apdu);
                            crdDTO.setRespcode(ResponseCode.SUCCESS);
                            response.setResponseEntity(crdDTO);
                            respexplain.append("重新获取圈存指令成功");
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
                            respexplain.append(response.getMessage());
                        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000006,状态不对
                            //设置交易结束标志为2
                            //                            crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_RESEND.getCode());
                            //重发计数器
                            cardTopupService.updateKeyCountByCrdnum(crdOrderNum);
                            //查询城市前置返回错误码
                            String cityErrorCode = cardTopupService.findCrdSysOrderByPrdnum(crdDTO.getPrdordernum()).getRespCode();
                            crdDTO.setRespcode(cityErrorCode);
                            response.setResponseEntity(crdDTO);
                            response.setCode(cityErrorCode);
                            logRespcode = cityErrorCode;
                            respexplain.append("卡服务订单状态不正确(申请秘钥失败)");
                        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000003,正常流程
                            crdDTO.setCrdordernum(crdOrderNum);
                            //2.申请前更新订单
                            String updStartCode = rechargeUpdOrderFun(crdDTO);
                            if (!ResponseCode.SUCCESS.equals(updStartCode)) {//更新订单失败
                                crdDTO.setRespcode(updStartCode);
                                response.setResponseEntity(crdDTO);
                                response.setCode(updStartCode);
                                logRespcode = updStartCode;
                                respexplain.append(response.getMessage());
                            } else {//更新订单成功
                                //3.可疑处理
                                rechargeBeforeSuspiciousFun(crdDTO);
                                //4.调用城市前置申请圈存
                                CrdSysOrderDTO retCrdDTO = rechargeKeyCityFrontFun(crdDTO);
                                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//向城市前置申请充值失败
                                    //如果申请失败,交易结束标志不为1
                                    if (!CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {
                                        retCrdDTO.setTradestartflag(CardTradeStartFlagEnum.TRADE_START_FLAG_TRANSPARENT.getCode());
                                        retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                                    }
                                    response.setCode(retCrdDTO.getRespcode());
                                    response.setResponseEntity(retCrdDTO);
                                    logRespcode = retCrdDTO.getRespcode();
                                    respexplain.append("向城市前置申请充值秘钥失败");
                                    //5.申请后更新订单
                                    retCrdDTO.setCrdordernum(crdOrderNum);
                                    retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                    String updEndCode = rechargeUpdOrderAfter(retCrdDTO);
                                    if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                        response.setCode(updEndCode);
                                        response.setResponseEntity(retCrdDTO);
                                        logRespcode = updEndCode;
                                        respexplain.append(response.getMessage());
                                    } else {
                                        response.setResponseEntity(retCrdDTO);
                                    }
                                } else {//申请成功
                                        //结束标志为透传(0),直接透传回前端
                                    if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag())) {
                                        response.setCode(retCrdDTO.getRespcode());
                                        response.setResponseEntity(retCrdDTO);
                                    } else if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag())) {//结束标志为 结束(1)
                                        //5.申请后更新订单
                                        retCrdDTO.setCrdordernum(crdOrderNum);
                                        retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                                        String updEndCode = rechargeUpdOrderAfter(retCrdDTO);
                                        if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                                            response.setCode(updEndCode);
                                            response.setResponseEntity(retCrdDTO);
                                            logRespcode = updEndCode;
                                            respexplain.append(response.getMessage());
                                        } else {
                                            response.setResponseEntity(retCrdDTO);
                                        }
                                    }
                                }
                                //6.可疑处理
                                rechargeAfterSuspiciousFun(retCrdDTO);
                            }
                        } else {//订单状态不正确
                            crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
                            response.setResponseEntity(crdDTO);
                            response.setCode(ResponseCode.CARD_ORDER_STATES_ERROR);
                            logRespcode = ResponseCode.CARD_ORDER_STATES_ERROR;
                            respexplain.append(response.getMessage());
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e);
            statckTrace = e.getMessage();
        }
        finally {
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]充值申请(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]充值申请(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    private String checkLoadOrderEmpty(CrdSysOrderDTO crdDTO) {
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
        }
        return ResponseCode.SUCCESS;
    }

    /**
     * @Description: 结果上传校验更新订单方法 1.调用uplodResultChkFun方法完成订单的校验
     * 2.当txnstat=0(成功)
     * 并且apdudata非全FFFFFFFF,更新卡服务订单状态：卡片充值成功(1000000008)、前卡服务订单状态:
     * 申请充值密钥成功(1000000007)、 特殊域信息(resultyktmw)、blackamt= txnamt+ befbal;
     * 3.当txnstat=1(失败) 并且apdudata全FFFFFFFF,更新卡服务订单状态：卡片充值失败(1000000009)
     * 、前卡服务订单状态: 申请充值密钥成功(1000000007)、 特殊域信息(resultyktmw)、blackamt=befbal;
     * 4.当txnstat=2(未知) 并且apdudata全FFFFFFFF,更新卡服务订单状态：卡片充值未知(1000000010
     * )、前卡服务订单状态：申请充值密钥成功(1000000007) 、 特殊域信息(resultyktmw), blackamt=null;
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadResultChkUdpOrderFun(CrdSysOrderDTO crdDTO) {
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        String inPras = JSONObject.toJSONString(crdDTO);
        String statckTrace = "";
        DodopalResponse<CrdSysOrderDTO> response = null;
        CrdSysOrderDTO crdSysOrderDTO = new CrdSysOrderDTO();
        try {
            //todo 加空值校验
            BeanUtils.copyProperties(crdDTO, crdSysOrderDTO);
            // 1.结果上传校验方法
            String resCode = uploadResultChkFun(crdSysOrderDTO);
            if (resCode.equals(ResponseCode.SUCCESS)) {//校验成功
                // 2.更新卡服务订单状态等信息
                response = icdcRechargeService.uploadResultChkUdpOrderFun(crdSysOrderDTO);
                response.setResponseEntity(crdSysOrderDTO);
            } else {//校验失败
                response = new DodopalResponse<CrdSysOrderDTO>();
                response.setResponseEntity(crdDTO);
                response.setCode(resCode);
            }
            respexplain.append(response.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            respexplain.append(e.getMessage());
            response = new DodopalResponse<CrdSysOrderDTO>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
            statckTrace = e.getStackTrace().toString();
        }
        finally {
            // 记录日志
            String respex = respexplain.toString();
            logRespcode = response.getCode();
            crdDTO.setCrdordernum("");
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值]结果上传校验更新(外部调用)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值]结果上传校验更新(外部调用)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    /**
     * @Description: 此方法通过一系列的方法调用充值结果的上传 1.调用uplodResultUpdOrderFun方法
     * 2.调用uplodResultCityFrontFun方法 3.调用uplodResultUpdOrderFun方法
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadResultFun(CrdSysOrderDTO crdDTO) {
        CrdSysOrderDTO retSysOrderDTO = null;
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);
        String statckTrace = "";
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        CrdSysOrderDTO crdSysOrderDTO = new CrdSysOrderDTO();
        try {
            BeanUtils.copyProperties(crdDTO, crdSysOrderDTO);
            CrdSysOrder crdSysOrder = icdcRechargeService.queryCardSysOrderByPrdId(crdSysOrderDTO.getPrdordernum());
            //1.根据结果上传交易状态更新数据库卡服务订单状态
            crdSysOrderDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
            String rs = uploadResultUpdOrderFunFirst(crdSysOrderDTO);
            //2.判断更新结果
            if (ResponseCode.SUCCESS.equals(rs)) {//更新成功
                retSysOrderDTO = uploadResultCityFrontFun(crdSysOrderDTO);
                crdSysOrderDTO.setRespcode(retSysOrderDTO.getRespcode());
                //update by guanjl for 不把城市前置返回的txnstat回填到DTO中 2015-09-22 start
                //                crdSysOrderDTO.setTxnstat(retSysOrderDTO.getTxnstat());
                //update by guanjl for 不把城市前置返回的txnstat回填到DTO中 2015-09-22 end
                crdSysOrderDTO.setMessagetype(retSysOrderDTO.getMessagetype());
                crdSysOrderDTO.setVer(retSysOrderDTO.getVer());
                crdSysOrderDTO.setTradeendflag(retSysOrderDTO.getTradeendflag());
                crdSysOrderDTO.setTradestartflag(retSysOrderDTO.getTradestartflag());
                if (!ResponseCode.SUCCESS.equals(retSysOrderDTO.getRespcode())) {
                    if (StringUtils.isBlank(retSysOrderDTO.getRespcode())) {
                        response.setCode(ResponseCode.CARD_FRONTRESP_NULL);
                        return response;
                    } else {
                        response.setCode(retSysOrderDTO.getRespcode());
                        return response;
                    }
                }
            } else {
                if (rs != null) {
                    response.setCode(rs);
                } else {
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                }
                return response;
            }
            rs = uploadResultUpdOrderFunSecond(crdSysOrderDTO);
            if (rs != null) {
                response.setCode(rs);
            } else {
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
            respexplain.append(response.getMessage());
            response.setResponseEntity(crdSysOrderDTO);//响应体
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            respexplain.append(e.getMessage());
            response = new DodopalResponse<CrdSysOrderDTO>();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
            statckTrace = e.getMessage();
        }
        finally {
            // 记录日志
            crdDTO.setCrdordernum("");
            String respex = respexplain.toString();
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值]结果上传(外部调用)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradeStart, logRespcode, respex, "[充值]结果上传(外部调用)", crdSysOrderDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    /**
     * @Title: checkOrderRecharge
     * @Description: 1.通过传入的产品库订单号，找到对应的卡服务订单记录，没找到进行异常处理
     * 2.当开始标志为开始时判断订单状态是否是申请读卡密钥成功
     * (1000000003)，否则进行异常处理，当开始标志为透传时判断订单状态是否是申请充值密钥(1000000005) ，否则进行异常处理
     * 3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号
     * (posid)、交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致，否则进行异常处理
     * @param 所需参数 :
     * @return 返回参数:
     */
    private Map<String, Object> checkOrderRechargeFun(CrdSysOrderDTO crdDTO) {
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
                    respexplain.append("[limit=" + limit + ",txnamt=" + txnamt + ",befamt=" + befamt + "]");
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

        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]充值申请-校验订单", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(map), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return map;
    }

    /**
     * 比较前端参数与订单是否相同
     * @param order
     * @param crdDTO
     * @return
     */
    private String compareOrder(CrdSysOrder order, CrdSysOrderDTO crdDTO) {
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

    //校验入参与订单记录 for 昆山
    private String compareOrderForUpload(CrdSysOrder order, CrdSysOrderDTO crdDTO) {
        //判断传入的参数卡号前端读出的(tradecard)
        if (!crdDTO.getTradecard().equals(order.getCheckCardNo())) {
            return ResponseCode.CARD_TRADECARD_ERROR;
        } else if (!crdDTO.getYktcode().equals(order.getYktCode())) {//一卡通编号 (yktcode)
            return ResponseCode.CARD_YKTCODE_ERROR;
        } else if (!crdDTO.getCitycode().equals(order.getCityCode())) {//城市代码 (citycode)
            return ResponseCode.CARD_CITYCODE_ERROR;
        } else if (!crdDTO.getPosid().equals(order.getPosCode())) {//设备编号 (posid)
            return ResponseCode.CARD_POSID_ERROR;
        } else if (Long.valueOf(crdDTO.getTxnamt()) != order.getTxnAmt()) {//交易金额 (txnamt)
            return ResponseCode.CARD_TXNAMT_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

    /**
     * @Title: rechargeBeforeSuspicious
     * @Description: 申请充值密钥前可疑处理 1.拼装申请前和申请后的交易记录 2.更新拼装后的值
     * @param 所需参数 :
     * @return 返回参数:
     */
    private String rechargeBeforeSuspiciousFun(CrdSysOrderDTO crdDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Title: rechargeKeyCityFront
     * @Description: 此方法向城市前置申请充值密钥 1.根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
     * 2.调用发给城市前置的报文接口(CardRechargeService.rechargeKeySendFun)
     * @param 所需参数 :
     * @return 返回参数:
     */
    private CrdSysOrderDTO rechargeKeyCityFrontFun(CrdSysOrderDTO crdDTO) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        CrdSysOrderDTO retCrdDTO = new CrdSysOrderDTO();

        String ip = "";
        String port = "";
        try {
            //根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            ip = retMap.get("IP") + "";
            port = retMap.get("PROT") + "";
            //调用发给城市前置的报文接口
            retCrdDTO = cardTopupService.rechargeKeySendFun(ip, Integer.valueOf(port), crdDTO);
            //将入参的特属域 返回给前端 2015-09-14 start
            retCrdDTO.setSpecdata(crdDTO.getSpecdata());
            //将入参的特属域 返回给前端 2015-09-14 end
            respexplain.append("[1.ip=" + ip + ",port=" + port + "]");
            respexplain.append(",[2.respcode=" + retCrdDTO.getRespcode() + "]");

            if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                logRespcode = retCrdDTO.getRespcode();
            }
        }
        catch (Exception e) {
            logRespcode = ResponseCode.SYSTEM_ERROR;
            respexplain.append(e);
        }

        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]充值申请-城市前置", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCrdDTO;
    }

    /**
     * @Title: rechargeAfterSuspicious
     * @Description: 申请充值密钥后可疑处理 1.拼装申请后的交易记录 2.更新拼装后的值
     * @param 所需参数 :
     * @return 返回参数:
     */
    private String rechargeAfterSuspiciousFun(CrdSysOrderDTO crdDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @Title: rechargeUpdOrder
     * @Description: 申请充值密钥前更新订单状态
     */
    private String rechargeUpdOrderFun(CrdSysOrderDTO crdDTO) {
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
            respexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum());
            cardTopupService.updateCrdSysOrderByCrdnum(crdDTO);
        }
        catch (Exception e) {
            respexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }

        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]充值申请-更新订单(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCode;
    }

    /**
     * 申请圈存后更新订单状态
     * @param crdDTO
     * @return
     */
    private String rechargeUpdOrderAfter(CrdSysOrderDTO crdDTO) {
        // 日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        // 日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        // 日志响应码
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        try {
            respexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum());
            cardTopupService.updateCrdSysOrderAfterByCrdnum(crdDTO);
        }
        catch (Exception e) {
            respexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }
        logHelper.recordLogFun(inPras, tradeStart, logRespcode, respexplain.toString(), "[充值]充值申请-更新订单(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCode;
    }

    /**
     * @Title: uplodResultChk
     * @Description: 结果上传校验 1.查询订单是否存在，否则进行异常处理。
     * 2.判断订单状态是否正常，否则进行异常处理(判断订单状态是否是申请充值密钥成功 (1000000007))，否则进行异常处理
     * 3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、
     * 交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致，否则进行异常处理
     * @param 所需参数 :
     * @return 返回参数:
     */
    private String uploadResultChkFun(CrdSysOrderDTO crdDTO) {
        // 1.判断入参
        if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
            return ResponseCode.CARD_PRDORDERNUM_NULL;
        }
        // 2.查询订单是否存在
        int count = icdcRechargeService.queryCrdSysOrderCountByPrdOrderId(crdDTO.getPrdordernum());
        if (count < 1) {
            return ResponseCode.SYSTEM_ERROR;
        }

        CrdSysOrder crdSysOrder = null;
        crdSysOrder = icdcRechargeService.queryCardSysOrderByPrdId(crdDTO.getPrdordernum());
        crdDTO.setCrdordernum(crdSysOrder.getCrdOrderNum());
        // 3.判断订单状态
        String cardOrderStatus = crdSysOrder.getCrdOrderStates();
        if (StringUtils.isNotBlank(cardOrderStatus)) {
            if (!cardOrderStatus.equals(CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode())) {
                return ResponseCode.CARD_ORDER_STATES_ERROR;
            }
        } else {
            return ResponseCode.SYSTEM_ERROR;
        }
        // 4.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、 交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致
        String checkRs = "";
        // 昆山特殊处理，昆山透支卡不校验交易前卡余额
        if (CardConstants.KUNSHAN_YKTCODE.equals(crdDTO.getYktcode())) {
            checkRs = compareOrderForUpload(crdSysOrder, crdDTO);
        } else {
            checkRs = compareOrder(crdSysOrder, crdDTO);
        }
        if (!ResponseCode.SUCCESS.equals(checkRs)) {
            return checkRs;
        }
        return ResponseCode.SUCCESS;
    }

    /**
     * @Title: rechargeKeyCityFront
     * @Description: 调用城市前置上传充值结果 1.根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
     * 2.调用发给城市前置的报文接口(CardRechargeService.uplodResultSendFun)
     * @param 所需参数 :
     * @return 返回参数:
     */
    private CrdSysOrderDTO uploadResultCityFrontFun(CrdSysOrderDTO crdDTO) {
        //        Map<String, Object> map = new HashMap<String, Object>();
        //        map.put("yktCode", crdDTO.getYktcode());
        //        map.put("cityId", crdDTO.getCitycode());
        Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
        String ip = retMap.get("IP") + "";
        String port = retMap.get("PROT") + "";
        // 调用发给城市前置的报文接口
        CrdSysOrderDTO retCrdDTO = cityFrontSocketService.sendCityFrontSocket(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        return retCrdDTO;
    }

    /**
     * @Title: uplodResultUpdOrderFunFirst
     * @Description: 结果上传调用前置前后更新订单方法 1.向城市前置上送充值结果前
     * 1.1当txnstat=0(成功),更新卡服务订单状态：
     * 充值结果上传申请(1000000012),前卡服务订单状态:卡片充值成功(1000000008); 1.2.当txnstat
     * =1(失败)更新卡服务订单状态：一卡通冲正申请(1000000011),前卡服务订单状态:卡片充值失败 (1000000009);
     * 1.3当txnstat=2(未知)充值未知状态时直接发送未知上报给城市前置此处不更新订单状态
     * @param 所需参数 :
     * @return 返回参数:
     */
    private String uploadResultUpdOrderFunFirst(CrdSysOrderDTO crdDTO) {

        String txnStat = crdDTO.getTxnstat();//卡服务订单交易状态
        int updateCount = 0;//更新记录条数
        // 1.检查入参
        if (StringUtils.isBlank(txnStat)) {
            return ResponseCode.CARD_TXNSTAT_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getCrdordernum())) {
            return ResponseCode.CARD_CARDORDERNUM_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getUserid())) {
            return ResponseCode.CARD_USERID_NULL;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        Map<String, Object> value = new HashMap<String, Object>(1);
        value.put("RESULT_SEND_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        // 2.向城市前置上送充值结果
        if (txnStat.equals("0")) {//成功
            // 3.更新卡服务订单状态
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT.getCode());
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
            if (updateCount < 1) {
                return ResponseCode.SYSTEM_ERROR;
            } else {
                return ResponseCode.SUCCESS;
            }

        } else if (txnStat.equals("1")) {//失败
            // 3.更新卡服务订单状态
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode());
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
            if (updateCount < 1) {
                return ResponseCode.SYSTEM_ERROR;
            } else {
                return ResponseCode.SUCCESS;
            }
        } else if (txnStat.equals("2")) {//未知
            return ResponseCode.SUCCESS;
        }
        return ResponseCode.SYSTEM_ERROR;
    }

    /**
     * @Title: uplodResultUpdOrderFunSecond
     * @Description: 向城市前置上送充值结果返回后 2.1.当txnstat=0(成功)
     * 2.1.1城市前置返回成功:更新卡服务订单状态：一卡通确认充值成功
     * (1000000018),前卡服务订单状态:充值结果上传申请(1000000012), 交易应答码(respcode),
     * 交易步骤(tradestep), 交易步骤版本(tradestepver), 交易结束标志(tradeendflag);
     * 2.1.2城市前置返回失败:更新卡服务订单状态：一卡通未确认(1000000016
     * ),前卡服务订单状态:充值结果上传申请(1000000012), 交易应答码(respcode), 交易步骤(tradestep),
     * 交易步骤版本(tradestepver), 交易结束标志(tradeendflag); 2.2当txnstat=1(失败)
     * 2.2.1城市前置返回成功:更新卡服务订单状态
     * ：一卡通确认冲正成功(1000000019),前卡服务订单状态:一卡通冲正申请(1000000011), 交易应答码(respcode),
     * 交易步骤(tradestep), 交易步骤版本(tradestepver), 交易结束标志(tradeendflag);
     * 2.2.2城市前置返回失败:更新卡服务订单状态:一卡通确认冲正失败(1000000017
     * ),前卡服务订单状态:一卡通冲正申请(1000000011), 交易应答码(respcode), 交易步骤(tradestep),
     * 交易步骤版本(tradestepver), 交易结束标志(tradeendflag);
     * @param 所需参数 :
     * @return 返回参数:
     */
    private String uploadResultUpdOrderFunSecond(CrdSysOrderDTO crdDTO) {
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

        Map<String, Object> value = new HashMap<String, Object>(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = simpleDateFormat.format(new Date());
        value.put("RESULT_RES_CARD_DATE", now);
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        // 2.判断上传充值结果
        if (txnStat.equals("0")) {//成功
            if (crdDTO.getRespcode().equals(ResponseCode.SUCCESS)) {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());
            } else {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode());
            }
        } else if (txnStat.equals("1")) {//失败
            if (crdDTO.getRespcode().equals(ResponseCode.SUCCESS)) {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());
            } else {
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode());
            }
        } else {//未知
            return ResponseCode.SUCCESS;
        }
        // 3.更新卡服务订单状态
        value.put("RESP_CODE", crdDTO.getRespcode());
        value.put("TRADE_STEP", crdDTO.getMessagetype());
        value.put("TRADE_STEP_VER", crdDTO.getVer());
        value.put("TRADE_END_FLAG", crdDTO.getTradeendflag());
        value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
        value.put("UPDATE_USER", crdDTO.getUserid());
        updateCount = icdcRechargeService.updateCardSysOrderByCrdOrderId(value);
        if (updateCount < 1) {
            return ResponseCode.SYSTEM_ERROR;
        }
        return ResponseCode.SUCCESS;
    }
}
