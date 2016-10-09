package com.dodopal.card.business.facadeImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.facade.BJAccIntConsumeDiscountFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.model.BJAccountIntegralOrder;
import com.dodopal.card.business.model.SamSignInOffTb;
import com.dodopal.card.business.service.BJAccountIntegralOrderService;
import com.dodopal.card.business.service.BJCardTopupService;
import com.dodopal.card.business.service.BJCityFrontSocketService;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BJAccIntConsumeOrderStatusEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.CardTxnStatEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
@Service("bjAccIntConsumeDiscountFacade")
public class BJAccIntConsumeDiscountFacadeImpl implements BJAccIntConsumeDiscountFacade{
    private final static Logger log = LoggerFactory.getLogger(BJAccIntConsumeDiscountFacadeImpl.class);

    @Autowired
    BJAccountIntegralOrderService bjAccIntOrderService; 
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private BJCityFrontSocketService frontService;
    @Autowired
    private CardTopupService cardTopupService;
    @Autowired
    private BJCardTopupService bjCardTopupService;
    /* 
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> applyAccountConsume(BJAccountConsumeDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String cardOrderNum = null;
        String inPras = JSONObject.toJSONString(crdDTO);
        DodopalResponse<BJAccountConsumeDTO> dodopalResult =  new DodopalResponse<BJAccountConsumeDTO>();
        try{
            
            String checkResult = checkAccountDTO(crdDTO);
            if(StringUtils.isNotBlank(checkResult)){
                logRespexplain.append(",[checkParam返回错误码:"+checkResult+"]");
                log.info(",[checkParam返回错误码:"+checkResult+"]");
                dodopalResult.setCode(checkResult);
                return dodopalResult;
            }
            BJAccountIntegralOrder orderQuery = new BJAccountIntegralOrder();
            orderQuery.setProOrderNum(crdDTO.getProordernum());
            orderQuery.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
            List<BJAccountIntegralOrder> orderList = bjAccIntOrderService.findBJAccountIntegralOrder(orderQuery);
            logRespexplain.append(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            log.info(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            if(CollectionUtils.isEmpty(orderList)){
                //生成账户消费的产品库订单，添加订单状态为订单创建成功
                
                changeAccModel(crdDTO, order);
                if(null!=crdDTO.getCrdm()){
                    order.setSpecialConsome(crdDTO.getCrdm().toString());
                }
                
                //业务类型
                order.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
                
                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CREATE_SUCCESS.getCode());
                logRespexplain.append(",[生成账户消费的产品库订单，添加订单状态为订单创建成功"+order.toString()+"]");
                BJAccountIntegralOrder accIntOrder = bjAccIntOrderService.createBJAccountIntegralOrder(order);
                cardOrderNum = accIntOrder.getCrdAccIntOrderNum();
            }else{
                BJAccountIntegralOrder oldOrder = orderList.get(0);
                cardOrderNum = oldOrder.getCrdAccIntOrderNum();
                if(!BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CREATE_SUCCESS.getCode().equals(oldOrder.getCrdOrderStates())){
                    dodopalResult.setCode(ResponseCode.CARD_ORDER_STATES_ERROR);
                    dodopalResult.setResponseEntity(crdDTO);
                    return dodopalResult;
                }
            }
            
            //根据cityCode查询城市前置ip，port
            Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
            if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
            }
            String ip = retMap.get("IP") + "";
            String port = retMap.get("PROT") + "";
            logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
            //2.向城市前置发送报文
            BJAccountConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForAccCons(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
            logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
            log.info(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
            dodopalResult.setResponseEntity(frontConsDTO);
            dodopalResult.setCode(frontConsDTO.getRespcode());
            if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                if(StringUtils.isNotBlank(frontConsDTO.getAccnum())&&Integer.valueOf(frontConsDTO.getAccnum())>0){
                    frontConsDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode());
                    return dodopalResult;
                }
                order = new BJAccountIntegralOrder();
                order.setRespCode(frontConsDTO.getRespcode());
                if(CollectionUtils.isNotEmpty(orderList)){
                    order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                }else{
                    order.setCrdBeforeorderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CREATE_SUCCESS.getCode());
                }
                order.setProOrderNum(crdDTO.getProordernum());
                //根据返回交易应答码并且结束标志为1时更新订单状态为账户消费申请成功或账户消费申请失败
                order.setSpecialConsomeBack(frontConsDTO.getCrdm().toString());
//                order.setCrdBeforeorderStates(crdBeforeorderStates);
                if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                    order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_FAIL.getCode());
                }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                    order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_SUCCESS.getCode());
                }
                logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                log.info(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                bjAccIntOrderService.updateBJAccountIntegralOrder(order);
            }
        }catch(DDPException e){
            logRespexplain.append(",[error=" + e.getCause() + "]");
            log.error("BJAccIntConsumeDiscountFacadeImpl applyAccountConsume call an error:",e);
            crdDTO.setRespcode(e.getCode());
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(e.getCode());
        }catch(Exception e){
            e.printStackTrace();
            log.error("BJAccIntConsumeDiscountFacadeImpl applyAccountConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(ResponseCode.SYSTEM_ERROR);
        }finally {
            log.info(logRespexplain.toString());
            logRespcode = dodopalResult.getCode();
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][优惠][账户]消费申请(外)", cardOrderNum, crdDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(dodopalResult), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return dodopalResult;
    }

    
    
    @Override
    public DodopalResponse<BJAccountConsumeDTO> revokeAccountConsume(BJAccountConsumeDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        DodopalResponse<BJAccountConsumeDTO> dodopalResult =  new DodopalResponse<BJAccountConsumeDTO>();
        dodopalResult.setCode(ResponseCode.SUCCESS);
        dodopalResult.setResponseEntity(crdDTO);
        String cardOrderNum = null;
        String inPras = JSONObject.toJSONString(crdDTO);
        try{
            String checkResult = checkAccountDTO(crdDTO);
            if(StringUtils.isNotBlank(checkResult)){
                logRespexplain.append(",[checkParam返回错误码:"+checkResult+"]");
                log.info(",[checkParam返回错误码:"+checkResult+"]");
                dodopalResult.setCode(checkResult);
                return dodopalResult;
            }
            
            BJAccountIntegralOrder orderQuery = new BJAccountIntegralOrder();
            orderQuery.setProOrderNum(crdDTO.getProordernum());
            orderQuery.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
            List<BJAccountIntegralOrder> orderList = bjAccIntOrderService.findBJAccountIntegralOrder(orderQuery);
            logRespexplain.append(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            log.info(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            if(CollectionUtils.isNotEmpty(orderList)){
                BJAccountIntegralOrder interOrder = orderList.get(0);
                cardOrderNum = interOrder.getCrdAccIntOrderNum();
                logRespexplain.append(",[卡服务数据："+interOrder.toString()+"]");
                log.info(",[卡服务数据："+interOrder.toString()+"]");
                if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())){
                    logRespexplain.append(",[一卡通撤销确认成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                    log.info(",[一卡通撤销确认成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                    return dodopalResult;
                }
                //校验订单合法性 卡服务订单状态为账户消费成功、一卡通账户消费撤销申请、一卡通撤销未确认
                if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                    ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY.getCode().equals(interOrder.getCrdOrderStates())
                    ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                    ){
                   // 并且POS机签退之前可以撤销，否则是不被允许进行撤销;
                    //根据pos号获取sam卡签到信息
                    SamSignInOffTb inOffTb = bjCardTopupService.querySamInOffTbByPosId(crdDTO.getPoscode());
                    //签退的判断条件
                    logRespexplain.append(",[签退标志signOffFlag:"+inOffTb.getSignOffFlag()+"]");
                    log.info(",[签退标志signOffFlag:"+inOffTb.getSignOffFlag()+"]");
                    if("1".equals(inOffTb.getSignOffFlag())){
                       dodopalResult.setCode(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR); 
                       return dodopalResult;
                    }
                    if("0".equals(inOffTb.getSignOffFlag())){//pos未签退时
                        if(Long.valueOf(inOffTb.getSignOffDate())>Long.valueOf(interOrder.getTranDateTime())){
                            logRespexplain.append(",[签退标志时间不在交易时间前："+inOffTb.getSignOffDate()+"]");
                            log.info(",[签退标志时间不在交易时间前："+inOffTb.getSignOffDate()+"]");
                            dodopalResult.setCode(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR); 
                            return dodopalResult;
                        }
                        //根据cityCode查询城市前置ip，port
                        Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
                        if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                            throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                        }
                        String ip = retMap.get("IP") + "";
                        String port = retMap.get("PROT") + "";
                        logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                        //2.向城市前置发送报文
                        BJAccountConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForAccCons(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                        logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        log.info(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                            order.setRespCode(frontConsDTO.getRespcode());
                            if(CollectionUtils.isNotEmpty(orderList)){
                                order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                            }
                            order.setSpecialRevoke(crdDTO.getCrdm().toString());
                            order.setProOrderNum(crdDTO.getProordernum());
                            order.setSpecialRevokeBack(frontConsDTO.getCrdm().toString());
                            // 前置的返回交易应答码并且结束标志为1时更新卡服务订单状态为一卡通撤销确认成功或一卡通撤销未确认
                            if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW.getCode());
                            }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS.getCode());
                            }
                            logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            log.info(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            bjAccIntOrderService.updateBJAccountIntegralOrder(order);
                        }
                        dodopalResult.setCode(frontConsDTO.getRespcode());
                        dodopalResult.setResponseEntity(frontConsDTO);
                    }
                }
            }
        }catch(DDPException e){
            log.error("BJAccIntConsumeDiscountFacadeImpl revokeAccountConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(e.getCode());
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(e.getCode());
        }catch(Exception e){
            log.error("BJAccIntConsumeDiscountFacadeImpl revokeAccountConsume call an error:",e);
            e.printStackTrace();
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(ResponseCode.SYSTEM_ERROR);
        }finally {
            log.info(logRespexplain.toString());
            logRespcode = dodopalResult.getCode();
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][优惠][账户]消费撤销(外)", cardOrderNum, crdDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(dodopalResult), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return dodopalResult;
    }
    @Override
    public DodopalResponse<BJAccountConsumeDTO> uploadAccountConsume(BJAccountConsumeDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String cardOrderNum = null;
        String inPras = JSONObject.toJSONString(crdDTO);
        DodopalResponse<BJAccountConsumeDTO> response = new DodopalResponse<BJAccountConsumeDTO>();
        response.setResponseEntity(crdDTO);
        response.setCode(ResponseCode.SUCCESS);
        String checkResult = checkAccountDTO(crdDTO);
        if(StringUtils.isNotBlank(checkResult)){
            logRespexplain.append(",[checkParam返回错误码:"+checkResult+"]");
            log.info(",[checkParam返回错误码:"+checkResult+"]");
            response.setCode(checkResult);
            return response;
        }
        try{
            BJAccountIntegralOrder orderQuery = new BJAccountIntegralOrder();
            if(StringUtils.isNotBlank(crdDTO.getProordernum())){
                orderQuery.setProOrderNum(crdDTO.getProordernum());
            }
            if(null!=crdDTO.getCrdm()){
                orderQuery.setTxnSeqNo(crdDTO.getCrdm().getTxnseqno());
                orderQuery.setPosSeq(crdDTO.getCrdm().getPostranseq());
            }
            orderQuery.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
            log.info("查询卡服务订单号的参数："+orderQuery.toString());
            List<BJAccountIntegralOrder> orderList = bjAccIntOrderService.findBJAccountIntegralOrder(orderQuery);
            logRespexplain.append(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            log.info(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            if(CollectionUtils.isNotEmpty(orderList)){
                BJAccountIntegralOrder interOrder = orderList.get(0);
                cardOrderNum = interOrder.getCrdAccIntOrderNum();
                logRespexplain.append(",[卡服务数据："+interOrder.toString()+"]");
                //卡服务当接到产品库的成功的请求时，检查订单的合法性,卡服务检查状态要符合
                if(CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(crdDTO.getTxnstat())){
                    logRespexplain.append(",[交易状态txnstat:"+crdDTO.getTxnstat()+"]");
                    //当状态是一卡通账户消费确认成功直接返回全零应答码不需调用卡服务接口，
                    if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())){
                        logRespexplain.append(",[一卡通消费确认成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                        log.info(",[一卡通消费确认成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                        return response;
                    }
                    //账户消费申请成功,账户消费成功, 一卡通账户消费结果申请, 一卡通账户消费未确认
                    if(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                        ){
                        //其余状态按阶段更新卡服务订单状态调用城市前置接口进行结果上传根据城市
                        //根据cityCode查询城市前置ip，port
                        Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
                        if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                            throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                        }
                        String ip = retMap.get("IP") + "";
                        String port = retMap.get("PROT") + "";
                        logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                        //2.向城市前置发送报文
                        BJAccountConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForAccCons(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                        logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        log.info(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                            order.setRespCode(frontConsDTO.getRespcode());
                            if(CollectionUtils.isNotEmpty(orderList)){
                                order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                            }
                            if(null!=crdDTO.getCrdm()){
                                order.setSpecialRevoke(crdDTO.getCrdm().toString());
                            }
                            if(null!=frontConsDTO.getCrdm()){
                                order.setSpecialConsomeBack(frontConsDTO.getCrdm().toString());
                            }   
                            order.setProOrderNum(crdDTO.getProordernum());
                            
    //                        order.setCrdBeforeorderStates(crdBeforeorderStates);
                            // 前置的返回交易应答码并且结束标志为1时更新卡服务订单状态为一卡通账户消费确认成功或一卡通账户消费未确认
                            if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode());
                            }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());
                            }
                            logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            log.info(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            bjAccIntOrderService.updateBJAccountIntegralOrder(order);
                        }
                        response.setResponseEntity(frontConsDTO);
                        response.setCode(frontConsDTO.getRespcode());
                    }
                }
               /**
                * 卡服务当接到产品库的失败的请求时，检查订单的合法性,卡服务检查状态要符合
                * (账户消费申请成功, 账户消费失败, 一卡通账户消费冲正申请, 一卡通账户消费冲正未确认, 一卡通账户消费确认冲正成功),
                *  当状态是一卡通账户消费确认冲正成功直接返回全零应答码不需调用卡服务接口，
                */
                //4.3 接到无订单号的取消交易订单，先根据约定的唯一属性找到对应的卡服务订单号没有找到订单直接返回全零和结束标志为1.
                // 当找到对应的订单处理方法如同方法4.2
                else if(CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(crdDTO.getTxnstat())||CardTxnStatEnum.TXN_STAT_CANCEL.getCode().equals(crdDTO.getTxnstat())){
                    //当状态是一卡通账户消费确认成功直接返回全零应答码不需调用卡服务接口，
                    logRespexplain.append(",[交易状态txnstat:"+crdDTO.getTxnstat()+"]");
                    log.info(",[交易状态txnstat:"+crdDTO.getTxnstat()+"]");
                    if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())){
                        logRespexplain.append(",[一卡通消费确认冲正成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                        log.info(",[一卡通消费确认冲正成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                        return response;
                    }
                    
                    if(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_ERROR.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ){
                        //其余状态按阶段更新卡服务订单状态调用城市前置接口进行结果上传根据城市
                        //根据cityCode查询城市前置ip，port
                        Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
                        if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                            throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                        }
                        String ip = retMap.get("IP") + "";
                        String port = retMap.get("PROT") + "";
                        logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                        //2.向城市前置发送报文
                        BJAccountConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForAccCons(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                        
                        logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                            order.setRespCode(frontConsDTO.getRespcode());
                            if(CollectionUtils.isNotEmpty(orderList)){
                                order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                            }
                            if(null!=crdDTO.getCrdm()){
                                order.setSpecialRevoke(crdDTO.getCrdm().toString());
                            }
                            if(null!=frontConsDTO.getCrdm()){
                                order.setSpecialConsomeBack(frontConsDTO.getCrdm().toString());
                            } 
                            order.setProOrderNum(crdDTO.getProordernum());
                            // 返回交易应答码并且结束标志为1时更新卡服务订单状态为一卡通账户消费确认冲正成功或一卡通账户消费冲正未确认
                            if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_UNKNOW.getCode());
                            }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());
                            }
                            logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            bjAccIntOrderService.updateBJAccountIntegralOrder(order);
                        }
                        response.setCode(frontConsDTO.getRespcode());
                        response.setResponseEntity(frontConsDTO);
                    }
                }
                
            }else{
              //4.3 接到无订单号的取消交易订单，先根据约定的唯一属性找到对应的卡服务订单号没有找到订单直接返回全零和结束标志为1.
                // 当找到对应的订单处理方法如同方法4.2
                if(CardTxnStatEnum.TXN_STAT_CANCEL.getCode().equals(crdDTO.getTxnstat())){
                    //接到无订单号的取消交易订单，先根据约定的唯一属性找到对应的卡服务订单号没有找到订单直接返回全零和结束标志为1.
                    logRespexplain.append(",[交易状态txnstat:"+crdDTO.getTxnstat()+"没有找到订单直接返回全零和结束标志为1]");
                    crdDTO.setRespcode(ResponseCode.SUCCESS);
                    crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }catch(DDPException e){
            log.error("BJAccIntConsumeDiscountFacadeImpl uploadAccountConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            response.setCode(e.getCode());
        }catch(Exception e){
            log.error("BJAccIntConsumeDiscountFacadeImpl uploadAccountConsume call an error:",e);
            e.printStackTrace();
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }finally {
            log.info(logRespexplain.toString());
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][优惠][账户]消费上送(外)", cardOrderNum, crdDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return response;
    }

    @Override
    public DodopalResponse<BJIntegralConsumeDTO> applyIntegralConsume(BJIntegralConsumeDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String cardOrderNum = null;
        String inPras = JSONObject.toJSONString(crdDTO);
        DodopalResponse<BJIntegralConsumeDTO> dodopalResult =  new DodopalResponse<BJIntegralConsumeDTO>();
        try{
            String checkResult = checkIntegralDTO(crdDTO);
            if(StringUtils.isNotBlank(checkResult)){
                logRespexplain.append(",[checkParam返回错误码:"+checkResult+"]");
                dodopalResult.setCode(checkResult);
                return dodopalResult;
            }
            BJAccountIntegralOrder orderQuery = new BJAccountIntegralOrder();
            orderQuery.setProOrderNum(crdDTO.getProordernum());
            orderQuery.setBusinessType(RateCodeEnum.YKT_CONSUME_POINT.getCode());
            List<BJAccountIntegralOrder> orderList = bjAccIntOrderService.findBJAccountIntegralOrder(orderQuery);
            logRespexplain.append(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            if(CollectionUtils.isEmpty(orderList)){
                //生成账户消费的产品库订单，添加订单状态为订单创建成功，
                changePointModel(crdDTO, order);
                //业务类型
                order.setBusinessType(RateCodeEnum.YKT_CONSUME_POINT.getCode());
                if(null!=crdDTO.getCrdm()){
                    order.setSpecialConsome(crdDTO.getCrdm().toString());
                }
                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CREATE_SUCCESS.getCode());
                // order.setCrdBeforeorderStates();
                logRespexplain.append(",[生成积分消费的产品库订单，添加订单状态为订单创建成功"+order.toString()+"]");
                BJAccountIntegralOrder accIntOrder = bjAccIntOrderService.createBJAccountIntegralOrder(order);
                cardOrderNum = accIntOrder.getCrdAccIntOrderNum();
            }else{
                BJAccountIntegralOrder oldOrder = orderList.get(0);
                cardOrderNum = oldOrder.getCrdAccIntOrderNum();
                if(!BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CREATE_SUCCESS.getCode().equals(oldOrder.getCrdOrderStates())){
                    dodopalResult.setCode(ResponseCode.CARD_ORDER_STATES_ERROR);
                    dodopalResult.setResponseEntity(crdDTO);
                    return dodopalResult;
                }
            }
            //根据cityCode查询城市前置ip，port
            Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
            if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
            }
            String ip = retMap.get("IP") + "";
            String port = retMap.get("PROT") + "";
            logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
            //2.向城市前置发送报文
            BJIntegralConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForIntegral(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
            logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
            dodopalResult.setResponseEntity(frontConsDTO);
            dodopalResult.setCode(frontConsDTO.getRespcode());
            if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                order = new BJAccountIntegralOrder();
                order.setRespCode(frontConsDTO.getRespcode());
                if(CollectionUtils.isNotEmpty(orderList)){
                    order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                }else{
                    order.setCrdBeforeorderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CREATE_SUCCESS.getCode());
                }
                order.setProOrderNum(crdDTO.getProordernum());
                //根据返回交易应答码并且结束标志为1时更新订单状态为账户消费申请成功或账户消费申请失败
                if(null!=frontConsDTO.getCrdm()){
                    order.setSpecialConsomeBack(frontConsDTO.getCrdm().toString());
                }   
                if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                    order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_FAIL.getCode());
                }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                    order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_SUCCESS.getCode());
                }
                logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                
                bjAccIntOrderService.updateBJAccountIntegralOrder(order);
            }
            
        }catch(DDPException e){
            logRespexplain.append(",[error=" + e.getCause() + "]");
            log.error("BJAccIntConsumeDiscountFacadeImpl applyIntegralConsume call an error:",e);
            crdDTO.setRespcode(e.getCode());
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(e.getCode());
        }catch(Exception e){
            log.error("BJAccIntConsumeDiscountFacadeImpl applyIntegralConsume call an error:",e);
            e.printStackTrace();
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(ResponseCode.SYSTEM_ERROR);
        }finally {
            log.info(logRespexplain.toString());
            logRespcode = dodopalResult.getCode();
            
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][优惠][积分]消费申请(外)", cardOrderNum, crdDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(dodopalResult), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return dodopalResult;
    }

    @Override
    public DodopalResponse<BJIntegralConsumeDTO> revokeIntegralConsume(BJIntegralConsumeDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        DodopalResponse<BJIntegralConsumeDTO> dodopalResult =  new DodopalResponse<BJIntegralConsumeDTO>();
        dodopalResult.setCode(ResponseCode.SUCCESS);
        dodopalResult.setResponseEntity(crdDTO);
        String cardOrderNum = null;
        String inPras = JSONObject.toJSONString(crdDTO);
        try{
            String checkResult = checkIntegralDTO(crdDTO);
            if(StringUtils.isNotBlank(checkResult)){
                logRespexplain.append(",[checkParam返回错误码:"+checkResult+"]");
                dodopalResult.setCode(checkResult);
                return dodopalResult;
            }
            BJAccountIntegralOrder orderQuery = new BJAccountIntegralOrder();
            orderQuery.setProOrderNum(crdDTO.getProordernum());
            orderQuery.setBusinessType(RateCodeEnum.YKT_CONSUME_POINT.getCode());
            List<BJAccountIntegralOrder> orderList = bjAccIntOrderService.findBJAccountIntegralOrder(orderQuery);
            logRespexplain.append(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            if(CollectionUtils.isNotEmpty(orderList)){
                BJAccountIntegralOrder interOrder = orderList.get(0);
                cardOrderNum = interOrder.getCrdAccIntOrderNum();
                logRespexplain.append(",[卡服务数据："+interOrder.toString()+"]");
                if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())){
                    logRespexplain.append(",[一卡通撤销确认成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                    return dodopalResult;
                }
                //校验订单合法性(状态为积分消费成功、一卡通积分消费撤销申请、一卡通撤销未确认)
                if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                    ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY.getCode().equals(interOrder.getCrdOrderStates())
                    ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                    ){
                   // 并且POS机签退之前可以撤销，否则是不被允许进行撤销;
                    //根据pos号获取sam卡签到信息
                    SamSignInOffTb inOffTb = bjCardTopupService.querySamInOffTbByPosId(crdDTO.getPoscode());
                    //签退的判断条件
                    logRespexplain.append(",[签退标志signOffFlag:"+inOffTb.getSignOffFlag()+"]");
                    if("1".equals(inOffTb.getSignOffFlag())){
                       dodopalResult.setCode(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR); 
                       crdDTO.setRespcode(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR);
                       dodopalResult.setResponseEntity(crdDTO);
                       return dodopalResult;
                    }
                    if("0".equals(inOffTb.getSignOffFlag())){//pos未签退时
                        if(Long.valueOf(inOffTb.getSignOffDate())>Long.valueOf(interOrder.getTranDateTime())){
                            logRespexplain.append(",[签退标志时间不在交易时间前："+inOffTb.getSignOffDate()+"]");
                            dodopalResult.setCode(ResponseCode.CARD_SAM_SIGN_IN_OFF_ERROR); 
                            return dodopalResult;
                        }
                        //根据cityCode查询城市前置ip，port
                        Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
                        if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                            throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                        }
                        String ip = retMap.get("IP") + "";
                        String port = retMap.get("PROT") + "";
                        logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                        //2.向城市前置发送报文
                        BJIntegralConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForIntegral(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                        logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        
                        if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                            order.setRespCode(frontConsDTO.getRespcode());
                            if(CollectionUtils.isNotEmpty(orderList)){
                                order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                            }
                            if(null!=crdDTO.getCrdm()){
                                order.setSpecialRevoke(crdDTO.getCrdm().toString());
                            }
                            if(null!=frontConsDTO.getCrdm()){
                                order.setSpecialRevokeBack(frontConsDTO.getCrdm().toString());
                            }   
                            order.setProOrderNum(crdDTO.getProordernum());
                            // 前置的返回交易应答码并且结束标志为1时更新卡服务订单状态为一卡通撤销确认成功或一卡通撤销未确认
                            if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW.getCode());
                            }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS.getCode());
                            }
                            logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            bjAccIntOrderService.updateBJAccountIntegralOrder(order);
                        }
                        dodopalResult.setCode(frontConsDTO.getRespcode());
                        dodopalResult.setResponseEntity(frontConsDTO);
                    }
                }
            }
        }catch(DDPException e){
            log.error("BJAccIntConsumeDiscountFacadeImpl revokeIntegralConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(e.getCode());
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(e.getCode());
        }catch(Exception e){
            e.printStackTrace();
            log.error("BJAccIntConsumeDiscountFacadeImpl revokeIntegralConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            dodopalResult.setResponseEntity(crdDTO);
            dodopalResult.setCode(ResponseCode.SYSTEM_ERROR);
        }finally {
            log.info(logRespexplain.toString());
            logRespcode = dodopalResult.getCode();
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][优惠][积分]消费撤销(外)", cardOrderNum, crdDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(dodopalResult), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return dodopalResult;
    }


    @Override
    public DodopalResponse<BJIntegralConsumeDTO> uploadIntegralConsume(BJIntegralConsumeDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());
        StringBuffer logRespexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        String inPras = JSONObject.toJSONString(crdDTO);
        DodopalResponse<BJIntegralConsumeDTO> response = new DodopalResponse<BJIntegralConsumeDTO>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(crdDTO);
        String cardOrderNum = null;
        String checkResult = checkIntegralDTO(crdDTO);
        if(StringUtils.isNotBlank(checkResult)){
            logRespexplain.append(",[checkParam返回错误码"+checkResult+"]");
            response.setCode(checkResult);
            return response;
        }
        try{
            BJAccountIntegralOrder orderQuery = new BJAccountIntegralOrder();
            if(StringUtils.isNotBlank(crdDTO.getProordernum())){
                orderQuery.setProOrderNum(crdDTO.getProordernum());
            }
            if(null!=crdDTO.getCrdm()){
                orderQuery.setTxnSeqNo(crdDTO.getCrdm().getTxnseqno());
                orderQuery.setPosSeq(crdDTO.getCrdm().getPostranseq());
            }
            orderQuery.setBusinessType(RateCodeEnum.YKT_CONSUME_ACOUNT.getCode());
            log.info("查询卡服务订单号的参数："+orderQuery.toString());
            List<BJAccountIntegralOrder> orderList = bjAccIntOrderService.findBJAccountIntegralOrder(orderQuery);
            logRespexplain.append(",[根据产品库订单号查询出卡服务记录Size:"+orderList.size()+"]");
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            if(CollectionUtils.isNotEmpty(orderList)){
                BJAccountIntegralOrder interOrder = orderList.get(0);
                cardOrderNum = interOrder.getCrdAccIntOrderNum();
                logRespexplain.append(",[卡服务数据："+interOrder.toString()+"]");
                //卡服务当接到产品库的成功的请求时，检查订单的合法性,卡服务检查状态要符合
                if(CardTxnStatEnum.TXN_STAT_SUCCESS.getCode().equals(crdDTO.getTxnstat())){
                    //当状态是一卡通账户消费确认成功直接返回全零应答码不需调用卡服务接口，
                    if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())){
                        logRespexplain.append(",[一卡通消费确认成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                        return response;
                    }
                    //账户消费申请成功,账户消费成功, 一卡通账户消费结果申请, 一卡通账户消费未确认
                    if(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                        ){
                        logRespexplain.append(",[交易状态txnstat:"+crdDTO.getTxnstat()+"]");
                        //其余状态按阶段更新卡服务订单状态调用城市前置接口进行结果上传根据城市
                        //根据cityCode查询城市前置ip，port
                        Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
                        if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                            throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                        }
                        String ip = retMap.get("IP") + "";
                        String port = retMap.get("PROT") + "";
                        logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                        //2.向城市前置发送报文
                        BJIntegralConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForIntegral(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                        logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                            order.setRespCode(frontConsDTO.getRespcode());
                            if(CollectionUtils.isNotEmpty(orderList)){
                                order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                            }
                            if(null!=crdDTO.getCrdm()){
                                order.setSpecialRevoke(crdDTO.getCrdm().toString());
                            }
                            if(null!=frontConsDTO.getCrdm()){
                                order.setSpecialRevokeBack(frontConsDTO.getCrdm().toString());
                            }   
                            order.setProOrderNum(crdDTO.getProordernum());
    //                        order.setCrdBeforeorderStates(crdBeforeorderStates);
                            // 前置的返回交易应答码并且结束标志为1时更新卡服务订单状态为一卡通账户消费确认成功或一卡通账户消费未确认
                            if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_UNKNOW.getCode());
                            }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_UPLOAD_RESULT_SUCCESS.getCode());
                            }
                            
                            logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            bjAccIntOrderService.updateBJAccountIntegralOrder(order);
                        }
                        response.setResponseEntity(frontConsDTO);
                        response.setCode(frontConsDTO.getRespcode());
                    }
                }
               /**
                * 卡服务当接到产品库的失败的请求时，检查订单的合法性,卡服务检查状态要符合
                * (账户消费申请成功, 账户消费失败, 一卡通账户消费冲正申请, 一卡通账户消费冲正未确认, 一卡通账户消费确认冲正成功),
                *  当状态是一卡通账户消费确认冲正成功直接返回全零应答码不需调用卡服务接口，
                */
                else if(CardTxnStatEnum.TXN_STAT_FAIL.getCode().equals(crdDTO.getTxnstat())||CardTxnStatEnum.TXN_STAT_CANCEL.getCode().equals(crdDTO.getTxnstat())){
                    logRespexplain.append(",[交易状态txnstat:"+crdDTO.getTxnstat()+"]");
                    //当状态是一卡通账户消费确认成功直接返回全零应答码不需调用卡服务接口，
                    if(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())){
                        logRespexplain.append(",[一卡通消费确认冲正成功卡服务直接返回交易应答码成功无需再调用城市前置]");
                        return response;
                    }
                    
                    if(BJAccIntConsumeOrderStatusEnum.CARD_CONSUME_APPLY_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_CONSUME_ERROR.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_UNKNOW.getCode().equals(interOrder.getCrdOrderStates())
                        ||BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode().equals(interOrder.getCrdOrderStates())
                        ){
                        //其余状态按阶段更新卡服务订单状态调用城市前置接口进行结果上传根据城市
                        //根据cityCode查询城市前置ip，port
                        Map<String, Object> retMap = cardTopupService.queryCityIpAndPort(crdDTO.getCitycode());
                        if (retMap == null || StringUtils.isBlank(retMap.get("IP") + "") || StringUtils.isBlank(retMap.get("PROT") + "")) {
                            throw new DDPException(ResponseCode.SYSTEM_ERROR, "查询城市前置IP失败");
                        }
                        String ip = retMap.get("IP") + "";
                        String port = retMap.get("PROT") + "";
                        logRespexplain.append(",[cityCode=" + crdDTO.getCitycode() + ",ip=" + ip + ",port=" + port + "]");
                        //2.向城市前置发送报文
                        BJIntegralConsumeDTO frontConsDTO = frontService.sendBJCityFrontSocketForIntegral(ip, Integer.valueOf(port), crdDTO, CardConstants.CARD_LOG_TXNTYPE_CONSUME);
                        logRespexplain.append(",[前置返回报文dto:"+frontConsDTO.toString()+"]");
                        if(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode().equals(frontConsDTO.getTradeendflag())){
                            order.setRespCode(frontConsDTO.getRespcode());
                            if(CollectionUtils.isNotEmpty(orderList)){
                                order.setCrdBeforeorderStates(orderList.get(0).getCrdOrderStates());
                            }
                            if(null!=crdDTO.getCrdm()){
                                order.setSpecialRevoke(crdDTO.getCrdm().toString());
                            }
                            if(null!=frontConsDTO.getCrdm()){
                                order.setSpecialRevokeBack(frontConsDTO.getCrdm().toString());
                            }   
                            order.setProOrderNum(crdDTO.getProordernum());
                            // 返回交易应答码并且结束标志为1时更新卡服务订单状态为一卡通账户消费确认冲正成功或一卡通账户消费冲正未确认
                            if (!ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_UNKNOW.getCode());
                            }else if(ResponseCode.SUCCESS.equals(frontConsDTO.getRespcode())) {
                                order.setCrdOrderStates(BJAccIntConsumeOrderStatusEnum.CARD_ORDER_APPLY_REVERSE_SUCCESS.getCode());
                            }
                            logRespexplain.append(",[更新订单状态: "+BJAccIntConsumeOrderStatusEnum.getNameByCode(order.getCrdOrderStates())+"更新参与字段为："+order.toString()+"]");
                            bjAccIntOrderService.updateBJAccountIntegralOrder(order);
                        }
                        response.setResponseEntity(frontConsDTO);
                        response.setCode(frontConsDTO.getRespcode());
                    }
                } 
            }else{
                if(CardTxnStatEnum.TXN_STAT_CANCEL.getCode().equals(crdDTO.getTxnstat())){
                    //接到无订单号的取消交易订单，先根据约定的唯一属性找到对应的卡服务订单号没有找到订单直接返回全零和结束标志为1.
                    logRespexplain.append(",[交易状态txnstat:"+crdDTO.getTxnstat()+"没有找到订单直接返回全零和结束标志为1]");
                    crdDTO.setRespcode(ResponseCode.SUCCESS);
                    crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }catch(DDPException e){
            log.error("BJAccIntConsumeDiscountFacadeImpl uploadIntegralConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            response.setCode(e.getCode());
        }catch(Exception e){
            e.printStackTrace();
            log.error("BJAccIntConsumeDiscountFacadeImpl uploadIntegralConsume call an error:",e);
            logRespexplain.append(",[error=" + e.getCause() + "]");
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }finally {
            log.info(logRespexplain.toString());
            logRespcode = response.getCode();
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[消费][BJ][优惠][积分]消费上送(外)", cardOrderNum, crdDTO.getProordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CONSUME);
        }
        return response;
    }

    private void changePointModel(BJIntegralConsumeDTO crdDTO, BJAccountIntegralOrder order) {
        order.setAccSeq(crdDTO.getAccseq());
        order.setBatchId(crdDTO.getBatchid());
        order.setCardNo(crdDTO.getCardno());
        order.setComSeq(crdDTO.getComseq());
        order.setCreateUser(crdDTO.getCreateUser()); 
        order.setCityCode(crdDTO.getCitycode());
        order.setDateTime(crdDTO.getDatetime());
        order.setIcseq(crdDTO.getIcseq());
        order.setMerType(crdDTO.getMertype());
        order.setMerCode(crdDTO.getMercode());
        order.setOperId(crdDTO.getOperid());
        order.setPosCode(crdDTO.getPoscode());
        order.setPosType(crdDTO.getPostype());
        order.setProCode(crdDTO.getProcode());
        order.setProOrderNum(crdDTO.getProordernum());
        order.setReserved(crdDTO.getReserved());
        order.setRespCode(crdDTO.getRespcode());
        order.setSettDate(crdDTO.getSettdate());
        order.setPreautheAmt(crdDTO.getPreautheamt());
        //交易时间
        order.setTranDateTime(DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
        if(null!=crdDTO.getCrdm()){
            order.setPosSeq(crdDTO.getCrdm().getPostranseq());
            order.setTxnType(crdDTO.getCrdm().getTxntype());
            order.setTxnDate(crdDTO.getCrdm().getTxndate());
            order.setTxnTime(crdDTO.getCrdm().getTxntime());
            order.setCardBal(crdDTO.getCrdm().getCardbal());
            order.setTxnSeqNo(crdDTO.getCrdm().getTxnseqno());
        }
    }
    private void changeAccModel(BJAccountConsumeDTO crdDTO, BJAccountIntegralOrder order) {
        order.setAccInfo(JSONObject.toJSONString(crdDTO.getAccinfo()));
        order.setAccNum(crdDTO.getAccnum());
        order.setAccountNo(crdDTO.getAccountno());
        order.setAccSeq(crdDTO.getAccseq());
        order.setBatchId(crdDTO.getBatchid());
        order.setCardNo(crdDTO.getCardno());
        order.setComSeq(crdDTO.getComseq());
        order.setCreateUser(crdDTO.getCreateUser()); 
        order.setCityCode(crdDTO.getCitycode());
        order.setDateTime(crdDTO.getDatetime());
        order.setIcseq(crdDTO.getIcseq());
        order.setMerType(crdDTO.getMertype());
        order.setMerCode(crdDTO.getMercode());
        order.setOperId(crdDTO.getOperid());
        order.setPosCode(crdDTO.getPoscode());
        order.setPosType(crdDTO.getPostype());
        order.setPriviMsg(crdDTO.getPrivimsg());
        order.setProCode(crdDTO.getProcode());
        order.setProOrderNum(crdDTO.getProordernum());
        order.setReserved(crdDTO.getReserved());
        order.setRespCode(crdDTO.getRespcode());
        order.setSettDate(crdDTO.getSettdate());
        order.setTxnAmt(crdDTO.getTxnamt());
        //交易时间
        order.setTranDateTime(DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
        if(null!=crdDTO.getCrdm()){
            order.setPosSeq(crdDTO.getCrdm().getPostranseq());
            order.setTxnType(crdDTO.getCrdm().getTxntype());
            order.setTxnDate(crdDTO.getCrdm().getTxndate());
            order.setTxnTime(crdDTO.getCrdm().getTxntime());
            order.setCardBal(crdDTO.getCrdm().getCardbal());
            order.setTxnSeqNo(crdDTO.getCrdm().getTxnseqno());
        }
    }
    
    private String checkAccountDTO(BJAccountConsumeDTO crdDTO){
        if(StringUtils.isBlank(crdDTO.getCitycode())){//城市为空
            return ResponseCode.CARD_CONSUME_CITYCODE_NULL;
        }
        if(StringUtils.isBlank(crdDTO.getPoscode())){//posId为空
            return ResponseCode.CARD_CONSUME_POSID_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getPostype())) {
            return ResponseCode.CARD_CONSUME_POSTYPE_NULL;
        }
        return null;
    }
    private String checkIntegralDTO(BJIntegralConsumeDTO crdDTO){
        if(StringUtils.isBlank(crdDTO.getCitycode())){//城市为空
            return ResponseCode.CARD_CONSUME_CITYCODE_NULL;
        }
        if(StringUtils.isBlank(crdDTO.getPoscode())){//posId为空
            return ResponseCode.CARD_CONSUME_POSID_NULL;
        }
        if (StringUtils.isBlank(crdDTO.getPostype())) {
            return ResponseCode.CARD_CONSUME_POSTYPE_NULL;
        }
        return null;
    }
    
}
