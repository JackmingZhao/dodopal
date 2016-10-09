package com.dodopal.card.business.facadeImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.facade.BJIcdcConsumeCardV71Facade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.service.BJCardConsumeService;
import com.dodopal.card.business.service.BJCardTopupService;
import com.dodopal.card.business.service.BJCityFrontSocketService;
import com.dodopal.card.business.service.CardConsumeService;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTxnStatEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;

@Service("bJIcdcConsumeCardV71Facade")
public class BJIcdcConsumeCardV71FacadeImpl implements BJIcdcConsumeCardV71Facade {

    @Autowired
    private LogHelper logHelper;

    @Autowired
    private CardTopupService cardTopupService;

    @Autowired
    private CardConsumeService cardConsumeService;

    @Autowired
    private CityFrontSocketService cityFrontSocketService;

    @Autowired
    private BJCityFrontSocketService bjCityFrontSocketService;

    @Autowired
    private BJCardConsumeService bjCardConsumeService;

    @Autowired
    private BJCardTopupService bjCardTopupService;

    /*
     * 消费验卡(外部调用)
     * 1.校验产品库订单号查询卡服务订单是否存在。存在异常返回
     * 2.校验消费金额是否大于卡内余额。是异常返回。
     * 3.完成验卡查询在此方法中调用城市前置消费验卡方法完成对城市前置系统的报文交互。
     * 4.城市前置返回验卡成功创建订单
     * 5.组装数据返回前端
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardCons(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();
        String offlineFlag = "";
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
                                retCrdDTO.setOfflineflag(offlineFlag);
                                //4.创建订单
                                retCrdDTO.setOfflineflag("0");//联机消费
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
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]验卡查询(外)", retCrdDTO.getCrdordernum(), retCrdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return response;
    }

    /*
     * 消费申请获得扣款指令方法(外部调用)
     * 1.校验订单
     * 2.校验订单
     * 3.向城市前置发送报文
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> getDeductOrderCons(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
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
                logRespexplain.append(response.getMessage());
            } else {
                //2.校验订单
                Map<String, Object> map = checkOrderDeductConsFun(crdDTO);
                String checkOrderCode = map.get("retCode") + "";
                String crdOrderNum = map.get("crdOrderNum") + "";
                String crdOrderState = map.get("crdOrderState") + "";
                String txnSeq = map.get("txnSeq") + "";
                String userId = map.get("userId") + "";
                crdDTO.setCrdordernum(crdOrderNum);
                crdDTO.setTxnseq(txnSeq);
                crdDTO.setUserid(userId);
                if (!ResponseCode.SUCCESS.equals(checkOrderCode)) {
                    setErrorResponse(crdDTO, response, checkOrderCode);
                    logRespcode = checkOrderCode;
                    logRespexplain.append(response.getMessage());
                } else {//校验成功
                    logRespexplain.append("[1.orderState=" + crdOrderState + "]");
                    //3.向城市前置发送报文
                    retDTO = sendCharge(crdDTO, crdOrderState);
                    if (!ResponseCode.SUCCESS.equals(retDTO.getRespcode())) {
                        setErrorResponse(retDTO, response, retDTO.getRespcode());
                        logRespcode = retDTO.getRespcode();
                        logRespexplain.append("与城市前置交互失败" + response.getMessage());
                    } else {
                        response.setResponseEntity(retDTO);
                    }
                }
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71][联机]消费申请(外)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
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
        if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000005,透传
            //更新重发计数器
            cardTopupService.updateKeyCountByCrdnum(crdNum);
            //更新通讯流水号
            cardTopupService.updatePosTranSeqConsByCrdnum(crdNum, BJStringUtil.format10to16(crdDTO.getPostransseq()));
            //向前置发送报文
            retCrdDTO = deductKeyCityFrontConsFun(crdDTO);
            return retCrdDTO;
        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(crdOrderState)) {//卡服务订单状态为1000000003,正常流程
            //1.申请前更新订单
            String updStartCode = deductUpdOrderConsFun(crdDTO);
            if (!ResponseCode.SUCCESS.equals(updStartCode)) {//更新订单失败
                crdDTO.setRespcode(updStartCode);
                return crdDTO;
            } else {//更新订单成功
                //2.调用城市前置申请圈存
                retCrdDTO = deductKeyCityFrontConsFun(crdDTO);
                return retCrdDTO;
            }
        } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(crdOrderState)) {//07 查询DB中记录的秘钥,下发
            String chargeKey = cardTopupService.reSendOrderByCrdnum(crdDTO.getCrdordernum());
            crdDTO.getSpecdata().setCipheraction(chargeKey);
            return crdDTO;
        } else {//订单状态不正确
            crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
        }
        return crdDTO;
    }

    /*
     * 向城市前置申请消费秘钥
     * 1.根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
     * 2.调用发给城市前置的报文接口(CardRechargeService.rechargeKeySendFun)
     */
    private BJCrdSysOrderDTO deductKeyCityFrontConsFun(BJCrdSysOrderDTO crdDTO) {
        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();
        try {
            //更新通讯流水号
            bjCardTopupService.updateSamsigninfo(crdDTO.getPosid(), crdDTO.getPosseq(), crdDTO.getPostransseq());
            //根据citycode、yktcode获得访问城市前置具体的ip地址和端口号
            Map<String, Object> retMap = cardTopupService.queryYktIpAndPort(crdDTO.getYktcode());
            String ip = retMap.get("IP") + "";
            String port = retMap.get("PROT") + "";
            if (retMap == null || StringUtils.isBlank(ip) || StringUtils.isBlank(port)) {
                BeanUtils.copyProperties(crdDTO, retCrdDTO);
                retCrdDTO.setRespcode(ResponseCode.CARD_CONSUME_YKTINFO_ERROR);
                return retCrdDTO;
            } else {
                //交易前卡余额转16进制
                crdDTO.setBefbal(Integer.toHexString(Integer.valueOf(crdDTO.getBefbal())));
                //调用发给城市前置的报文接口
                retCrdDTO = bjCityFrontSocketService.sendBJCityFrontSocket(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {//向城市前置申请充值失败
                    if (StringUtils.isBlank(retCrdDTO.getTradeendflag())) {
                        retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                    }
                    //011003和000047错误码需要锁卡
                    if ("011003".equals(retCrdDTO.getRespcode()) || "000047".equals(retCrdDTO.getRespcode())) {
                        retCrdDTO.setBlackflag("1");
                    }
                    //3.申请后更新订单
                    String updEndCode = deductUpdOrderConsAfter(retCrdDTO);
                    if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                        retCrdDTO.setRespcode(updEndCode);
                    }
                    return retCrdDTO;
                } else {//返回应答码为成功
                    String endStr = retCrdDTO.getSpecdata().getNextstep().substring(4, 12);
                    //结束标志为透传(0),且0600FFFF,直接透传回前端
                    if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(retCrdDTO.getTradeendflag()) || !"0500FFFF".equals(endStr)) {
                        return retCrdDTO;
                    } else if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(retCrdDTO.getTradeendflag()) && "0500FFFF".equals(endStr)) {//结束标志为 结束(1)
                        //5.申请后更新订单
                        retCrdDTO.setCrdordernum(crdDTO.getCrdordernum());
                        retCrdDTO.setPrdordernum(crdDTO.getPrdordernum());
                        String updEndCode = deductUpdOrderConsAfter(retCrdDTO);
                        if (!ResponseCode.SUCCESS.equals(updEndCode)) {//更新订单失败
                            retCrdDTO.setRespcode(updEndCode);
                        }
                        return retCrdDTO;
                    }
                }
            }
        }
        catch (Exception e) {
            retCrdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        return retCrdDTO;
    }

    /**
     * 消费结果上传方法(外部调用)
     * @param crdDTO
     * @return
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultCons(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        try {
            //校验空值
            String checkEmptyCode = checkUploadEmpty(crdDTO);
            if (!ResponseCode.SUCCESS.equals(checkEmptyCode)) {
                setErrorResponse(crdDTO, response, checkEmptyCode);
                logRespcode = checkEmptyCode;
                logRespexplain.append(response.getMessage());
            } else {
                // 校验
                String chkUpdCode = uploadResultChkUdpOrderConsFun(crdDTO);
                //1.结果上传校验更新订单
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
                        BJCrdSysOrderDTO retCrdDTO = uploadResultCityFrontConsFun(crdDTO);
                        response.setResponseEntity(retCrdDTO);
                        if (!ResponseCode.SUCCESS.equals(retCrdDTO.getRespcode())) {
                            if (StringUtils.isBlank(retCrdDTO.getTradeendflag())) {
                                retCrdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                            }
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
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71][联机]结果上传(外)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return response;
    }

    /*
     * 验卡空值校验
     */
    private String checkQueryEmpty(BJCrdSysOrderDTO crdDTO) {
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
        } else if (StringUtils.isBlank(crdDTO.getTradecard())) {
            return ResponseCode.CARD_CONSUME_TRADECARD_NULL;
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
        } else if (StringUtils.isBlank(crdDTO.getSamno())) {
            return ResponseCode.CARD_SAM_NULL;
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
        logHelper.recordLogFun(proOrderNum, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]验卡查询-校验产品库订单", "", proOrderNum, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), prdExist, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return prdExist;
    }

    /*
     * 校验消费金额是否大于卡内余额
     */
    private String checkTxnamt(BJCrdSysOrderDTO crdDTO) {
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

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]验卡查询-校验消费金额", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), checkTxnamtCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return checkTxnamtCode;
    }

    /*
     * 验卡调用城市前置
     */
    private BJCrdSysOrderDTO queryCheckCardCityFrontConsFun(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();
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
                retCrdDTO = bjCityFrontSocketService.sendBJCityFrontSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
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

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]验卡查询-调用城市前置", "", crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCrdDTO;
    }

    /*
     * 订单创建方法
     */
    private String createCrdOrderCardConsFun(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        //卡服务订单编号
        String crdOrderNum = "";
        String createOrderCode = ResponseCode.SUCCESS;
        try {
            crdOrderNum = bjCardConsumeService.createCrdSysConsOrder(crdDTO);
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
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]验卡查询-创建订单", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), createOrderCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return createOrderCode;
    }

    /*
     * 申请消费校验空值
     */
    private String checkLoadOrderEmpty(BJCrdSysOrderDTO crdDTO) {
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
    private Map<String, Object> checkOrderDeductConsFun(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        //卡服务订单号
        String crdOrderNum = "";
        String crdOrderState = "";
        String txnSeq = "";
        String userId = "";

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
                txnSeq = order.getTxnSeq() + "";
                userId = order.getCreateUser();
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
        map.put("txnSeq", txnSeq);
        map.put("userId", userId);

        logRespcode = retCode;

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]消费申请-校验订单", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(map), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return map;
    }

    /*
     * 比较前端参数与订单是否相同
     */
    private String compareOrder(CrdSysConsOrder order, BJCrdSysOrderDTO crdDTO) {
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
     * 申请前消费更新卡服务订单
     */
    private String deductUpdOrderConsFun(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        try {
            logRespexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum() + "],[3.tradestartflag=" + crdDTO.getTradestartflag() + "],[4.tradeendflag=" + crdDTO.getTradeendflag() + "]");
            bjCardConsumeService.updateCrdSysConsOrderByCrdnum(crdDTO);
        }
        catch (Exception e) {
            logRespexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]消费申请-更新订单(前)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCode;
    }

    private String deductUpdOrderConsAfter(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String retCode = ResponseCode.SUCCESS;
        try {
            logRespexplain.append("[1.crdordernum=" + crdDTO.getCrdordernum() + "],[2.prdordernum=" + crdDTO.getPrdordernum() + "],[3.tradestartflag=" + crdDTO.getTradestartflag() + "],[4.tradeendflag=" + crdDTO.getTradeendflag() + "]");
            bjCardConsumeService.updateCrdSysConsOrderAfterByCrdnum(crdDTO);
        }
        catch (Exception e) {
            logRespexplain.append(e);
            retCode = ResponseCode.SYSTEM_ERROR;
            logRespcode = ResponseCode.SYSTEM_ERROR;
        }

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]消费申请-更新订单(后)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), retCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCode;
    }

    /*
     * 申请消费校验空值
     */
    private String checkUploadEmpty(BJCrdSysOrderDTO crdDTO) {
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
    private String uploadResultChkUdpOrderConsFun(BJCrdSysOrderDTO crdDTO) {
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
                crdDTO.setTxnseq(order.getTxnSeq() + "");
                crdDTO.setUserid(order.getCreateUser());
                logRespexplain.append(",[2.crdOrderStates=" + order.getCrdOrderStates() + "]");
                //北京结果上传重发06,07,08,09,11,12,17,16状态皆可通过
                if (CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_RECHARGE_KEY_ERROR.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE.getCode().equals(order.getCrdOrderStates())
                    || CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_ERROR.getCode().equals(order.getCrdOrderStates())) {
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
                } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(order.getCrdOrderStates()) || CardOrderStatesEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode().equals(order.getCrdOrderStates())) {
                    //18,19状态,上传正常终止,返回前端
                    return ResponseCode.SUCCESS;
                } else if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(order.getCrdOrderStates())) {
                    //03 ,上传异常终止
                    return ResponseCode.SUCCESS;
                } else {
                    chkUpdCode = ResponseCode.CARD_CONSUME_ORDER_STATES_ERROR;
                }
            }
        }
        catch (Exception e) {
            chkUpdCode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e);
        }

        logRespcode = chkUpdCode;

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]结果上传-校验更新", crdOrderNum, crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), chkUpdCode, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return chkUpdCode;
    }

    /*
     * 上传更新订单
     */
    private String updConsOrderUpload(BJCrdSysOrderDTO crdDTO) {
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
        String lastResultTime = DateUtils.dateToStrLongs(new Date());
        supplement.setLastResultTime(lastResultTime);

        //当txnstat=0(成功)并且apdudata非全FFFFFFFFFFFFFFFF
        if (CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(txnstat) && !CardConstants.BJ_CARD_APDUDATA_FAIL.equals(apduData)) {
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode());//1000000008卡片扣款成功
            //            order.setBlackAmt(Integer.valueOf(crdDTO.getBefbal()) + Integer.valueOf(crdDTO.getTxnamt()));//blackamt=txnamt+befbal
            order.setBlackAmt(Integer.valueOf(crdDTO.getBlackamt()));
        } else if (CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(txnstat) && CardConstants.BJ_CARD_APDUDATA_FAIL.equals(apduData)) {//当txnstat=1(失败)并且apdudata全FFFFFFFFFFFFFFFF
            order.setCrdOrderStates(CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());//1000000009卡片扣款失败
            //            order.setBlackAmt(Integer.valueOf(crdDTO.getBefbal()));
            order.setBlackAmt(Integer.valueOf(crdDTO.getBlackamt()));
        } else if (CardTxnStatEnum.TXN_STAT_UNKNOW.getCode().equals(txnstat) && CardConstants.BJ_CARD_APDUDATA_FAIL.equals(apduData)) {//当txnstat=2(未知)并且apdudata全FFFFFFFFFFFFFFFF
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
    private String uploadResultUpdOrderBefConsFun(BJCrdSysOrderDTO crdDTO) {
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
            consOrder.setPosTransSeq(BJStringUtil.format10to16(crdDTO.getPostransseq()));//通讯流水号
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
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]结果上传-上传前更新", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(firstUpdCode), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return firstUpdCode;
    }

    /**
     * 调用前置消费结果上传方法
     */
    private BJCrdSysOrderDTO uploadResultCityFrontConsFun(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        BJCrdSysOrderDTO retCrdDTO = new BJCrdSysOrderDTO();

        try {
            //更新通讯流水号
            bjCardTopupService.updateSamsigninfo(crdDTO.getPosid(), crdDTO.getPosseq(), crdDTO.getPostransseq());
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
                retCrdDTO = bjCityFrontSocketService.sendBJCityFrontSocket(retMap.get("IP") + "", Integer.valueOf(retMap.get("PROT") + ""), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
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

        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]结果上传-上传消费结果", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(retCrdDTO), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return retCrdDTO;
    }

    /*
     * 消费结果上传调用前置后更新订单方法
     */
    private String uploadResultUpdOrderAfterConsFun(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);

        String afterUpdCode = ResponseCode.SUCCESS;

        try {
            String txnStat = crdDTO.getTxnstat();//卡服务订单交易状态

            CrdSysConsOrder consOrder = new CrdSysConsOrder();
            consOrder.setTradeStep(crdDTO.getMessagetype());//消息类型
            consOrder.setTradeStepVer(BJStringUtil.removeVerZero(crdDTO.getVer()));//版本号
            consOrder.setCrdOrderNum(crdDTO.getCrdordernum());//卡服务订单号
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
        logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][V71]结果上传-上传后更新", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(afterUpdCode), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        return afterUpdCode;
    }

    private void setErrorResponse(BJCrdSysOrderDTO crdDTO, DodopalResponse<BJCrdSysOrderDTO> response, String errorCode) {
        crdDTO.setRespcode(errorCode);
        response.setResponseEntity(crdDTO);
        response.setCode(errorCode);
    }
}
