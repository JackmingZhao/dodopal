package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderResultEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.query.LoadOrderQuery;
import com.dodopal.product.business.service.BJnfcMobileRechargeService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductOrderService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.BJnfcMobileRechargeDelegate;

/**
 * 北京NFC手机端一卡通充值业务流程接口实现类
 * @author dodopal
 */
@Service
public class BJnfcMobileRechargeServiceImpl implements BJnfcMobileRechargeService {

    private final static Logger log = LoggerFactory.getLogger(BJnfcMobileRechargeServiceImpl.class);

    @Autowired
    private BJnfcMobileRechargeDelegate bjnfcMobileRechargeDelegate;
    @Autowired
    private ProductOrderService productOrderService;
    @Autowired
    private LoadOrderService loadOrderService;
    @Autowired
    private ProductYKTService productYKTService;
    @Autowired
    private PrdProductYktService prdProductYktService;

    /**
     * 用户终端信息登记
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> userTeminalRegister(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> response = null;
        try {
            response = bjnfcMobileRechargeDelegate.userTeminalRegister(crdDTO);
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "调用卡服务Hessian链接异常");
        }
        return response;
    }

    /**
     * 充值验卡接口
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeValidateCard(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        
        // 交易类型为"0"，查询。非0时，生单
        if (!"0".equals(crdDTO.getTxntype())) {
            
            //  判断是否圈存订单
            String loadOrderNum = crdDTO.getLoadordernum();
            String loadFlag = LoadFlagEnum.LOAD_YES.getCode();
            if (StringUtils.isBlank(loadOrderNum)) {
                loadFlag = LoadFlagEnum.LOAD_NO.getCode();
            }

            //  调用产品库内部方法,检验公交卡充值合法性(a.如果为圈存订单则需要检验圈存订单合法性.b.如果不是圈存订单,则调用产品库公交卡产品验证方法)
            DodopalResponse<PrdProductYktInfoForIcdcRecharge> preCheckResponse = preCheckBeforeCreateIcdcOrder(loadFlag, crdDTO.getProductcode(), crdDTO.getLoadordernum());
            if (!ResponseCode.SUCCESS.equals(preCheckResponse.getCode())) {
                response.setCode(preCheckResponse.getCode());
                response.setMessage(preCheckResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
            PrdProductYktInfoForIcdcRecharge validatePrdProduct = preCheckResponse.getResponseEntity();
            crdDTO.setProductcode(validatePrdProduct.getProCode());
            crdDTO.setTxnamt(String.valueOf(validatePrdProduct.getProPrice()));
            crdDTO.setYktcode(validatePrdProduct.getYktCode());
            crdDTO.setCitycode(validatePrdProduct.getCityCode());
            
            //  产品库主订单生成之前校验本次公交卡充值后卡内金额是否超过卡内最大允许金额
            long txnamt = validatePrdProduct.getProPrice();// 一卡通充值金额
            long yktCardMaxLimit = 0;// 卡内最大允许金额(验卡环节，卡服务从城市前置获取的值，DLL需要透传过来)
            if (!StringUtils.isBlank(crdDTO.getCardlimit())) {
                yktCardMaxLimit = Long.parseLong(crdDTO.getCardlimit());
            }
            long befbal = 0;// 交易前卡余额
            if (!StringUtils.isBlank(crdDTO.getBefbal())) {
                befbal = Long.parseLong(crdDTO.getBefbal());
            }
            //  交易后卡余额不能超过卡内最大允许金额
            if (yktCardMaxLimit < (txnamt + befbal)) {
                response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_LIMIT_ERROR);
                response.setMessage("充值金额与卡内余额之和超过卡内限额:交易金额:" + txnamt + ";交易前卡余额:" + befbal + "卡内最大限额：" + yktCardMaxLimit);
                response.setResponseEntity(crdDTO);
                return response;
            }
            
           //   检验通卡公司的合法性
            DodopalResponse<ProductYKT> yktResponse = productYKTService.validateYktServiceNormalForIcdcRecharge(crdDTO.getYktcode());
            if (!ResponseCode.SUCCESS.equals(yktResponse.getCode())) {
                response.setCode(yktResponse.getCode());
                response.setMessage(yktResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
            ProductYKT validateYktResponse = yktResponse.getResponseEntity(); 
            
            //  调用用户系统,验证当前个人是否合法
            DodopalResponse<Map<String, String>> merCheckResponse = null;
            try {
                merCheckResponse = bjnfcMobileRechargeDelegate.validatePersonalUserForNfcRecharge(crdDTO.getUserid());
            }
            catch (HessianRuntimeException e) {
                throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR, "调用用户系统校验个人合法性失败:Hessian异常");
            }
            if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
                response.setCode(merCheckResponse.getCode());
                response.setMessage("个人合法性校验未通过:" + merCheckResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
            Map<String, String> merInfoMap = merCheckResponse.getResponseEntity();
            
            //  调用产品库创建订单接口
            ProductOrder prdOrder = new ProductOrder();
            try {
                prdOrder = createPersonalPrdOrder(prdOrder, crdDTO, validatePrdProduct, validateYktResponse, merInfoMap);
            }
            catch (DDPException ddpException) {
                response.setCode(ddpException.getCode());
                response.setMessage("产品库生单参数不合法:" + ddpException.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
            DodopalResponse<Integer> bookOrderResponse = productOrderService.bookIcdcOrder(prdOrder);

            if (!ResponseCode.SUCCESS.equals(bookOrderResponse.getCode()) || bookOrderResponse.getResponseEntity() == 0) {
                response.setCode(bookOrderResponse.getCode());
                response.setMessage("产品库订单创建失败:" + bookOrderResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
        }
       
        //  调用卡服务验卡查询接口
        try {
            response = bjnfcMobileRechargeDelegate.chargeValidateCard(crdDTO);
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "调用卡服务Hessian链接异常");
        }

        return response;
    }

    /**
     * 充值申请起始接口
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeStart(BJCrdSysOrderDTO crdDTO) {
        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        
        String icdcOrderNum = crdDTO.getPrdordernum();
        
        log.info("充值申请起始接口,第一步,查询产品库订单信息,订单编号:" + icdcOrderNum);
        DodopalResponse<ProductOrder> findOrderResponse = productOrderService.getProductOrderByProOrderNum(icdcOrderNum);
        ProductOrder productOrder = findOrderResponse.getResponseEntity();
        if (productOrder == null ) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
            response.setMessage("非法订单:订单不存在。订单编号:" + icdcOrderNum);
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        // 2. 该订单的状态和支付大类是否符合以下的要求。
        // 2.1 如果是圈存，则主状态应为已付款
        // 2.2 如果不是圈存且支付类型是账户，则主状态应为待付款
        // 2.3 如果不是圈存且支付类型不是账户，则主状态应为已付款
        String orderStateDB = productOrder.getProOrderState();
        String payType = productOrder.getPayType();
        String loadFlag = productOrder.getLoadFlag();
        if (!(LoadFlagEnum.LOAD_YES.getCode().equals(loadFlag) && RechargeOrderStatesEnum.PAID.getCode().equals(orderStateDB) 
            || LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag) && PayTypeEnum.PAY_ACT.getCode().equals(payType) && RechargeOrderStatesEnum.UNPAID.getCode().equals(orderStateDB) 
            || LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag) && !PayTypeEnum.PAY_ACT.getCode().equals(payType) && RechargeOrderStatesEnum.PAID.getCode().equals(orderStateDB))) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
            response.setMessage("非法订单：订单状态不正确。订单编号:" + icdcOrderNum);
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        // 从订单上获取
        crdDTO.setUsercode(productOrder.getUserCode());
        crdDTO.setMercode(productOrder.getMerCode());
        crdDTO.setUserid(productOrder.getUserId());
        crdDTO.setLoadordernum(productOrder.getLoadOrderNum());
        crdDTO.setTxnamt(String.valueOf(productOrder.getTxnAmt()));
        crdDTO.setYktcode(productOrder.getYktCode());
        crdDTO.setCitycode(productOrder.getCityCode());
        crdDTO.setProductcode(productOrder.getProCode());
        crdDTO.setSource(productOrder.getSource());
        crdDTO.setLoadordernum(productOrder.getLoadOrderNum());
        if (StringUtils.isBlank(productOrder.getPayType())) {
            crdDTO.setPaytype(productOrder.getPayType().toString());
        }
        if (StringUtils.isBlank(productOrder.getPayWay())) {
            crdDTO.setPayway(productOrder.getPayWay().toString());
        }
        
        //  调用用户系统,验证当前个人是否合法
        DodopalResponse<Map<String, String>> merCheckResponse = null;
        try {
            merCheckResponse = bjnfcMobileRechargeDelegate.validatePersonalUserForNfcRecharge(crdDTO.getUserid());
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR, "调用用户系统校验个人合法性失败:Hessian异常");
        }
        if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
            response.setCode(merCheckResponse.getCode());
            response.setMessage("个人合法性校验未通过:" + merCheckResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        //3.根据来源判断是否为圈存订单发起的充值,如果不是则调用支付网关提供的冻结资金接口,并且根据返回结果调用"产品库更新订单状态接口"(资金冻结)
        if (LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag)) {
            PayTranDTO payTranDTO = new PayTranDTO();
            payTranDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
            payTranDTO.setCusTomerCode(crdDTO.getUsercode());
            payTranDTO.setOrderCode(crdDTO.getPrdordernum()); 
            payTranDTO.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());
            payTranDTO.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
            payTranDTO.setGoodsName(productOrder.getProName());
            payTranDTO.setAmount(productOrder.getReceivedPrice());
            payTranDTO.setSource(crdDTO.getSource());
            payTranDTO.setOrderDate(productOrder.getProOrderDate());
            payTranDTO.setCityCode(crdDTO.getCitycode());
            payTranDTO.setPayId(productOrder.getPayWay());
            payTranDTO.setExtFlg(false);
            payTranDTO.setOperatorId(productOrder.getUserId());

            DodopalResponse<Boolean> freezeAmtResponse = null;
            try {
                freezeAmtResponse = bjnfcMobileRechargeDelegate.freezeAccountAmt(payTranDTO);
            }
            catch (HessianRuntimeException e) {
                response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                response.setMessage("调用支付交易冻结资金接口失败:Hessian异常");
                response.setResponseEntity(crdDTO);
                return response;
            }
            DodopalResponse<Integer> updateStatusResponse = productOrderService.updateOrderStatusWhenBlockFund(crdDTO.getPrdordernum(), freezeAmtResponse.getCode());
            
            if (updateStatusResponse.getCode().equals(freezeAmtResponse.getCode()) 
                && !ResponseCode.SUCCESS.equals(freezeAmtResponse.getCode())
                && updateStatusResponse.getResponseEntity() > 0) {
                
                // 资金冻结失败，产品库订单状态更新成功，充值流程正常终止。
                response.setCode(freezeAmtResponse.getCode());
                response.setMessage("支付交易冻结资金失败:" + freezeAmtResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            } else if (!updateStatusResponse.getCode().equals(freezeAmtResponse.getCode())
                && !ResponseCode.SUCCESS.equals(freezeAmtResponse.getCode())
                && updateStatusResponse.getResponseEntity() == 0) {
                
                // 资金冻结失败，产品库订单状态更新失败，充值流程非正常终止。
                response.setCode(freezeAmtResponse.getCode());
                response.setMessage("支付交易冻结资金失败:" + freezeAmtResponse.getMessage()+",更新订单状态也失败:响应码:"+updateStatusResponse.getCode() + "响应消息:" + updateStatusResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }  else if (!updateStatusResponse.getCode().equals(freezeAmtResponse.getCode())
                && ResponseCode.SUCCESS.equals(freezeAmtResponse.getCode())
                && updateStatusResponse.getResponseEntity() == 0) {
                
                // 资金冻结成功，产品库订单状态更新失败，充值流程非正常终止。
                response.setCode(updateStatusResponse.getCode());
                response.setMessage("支付交易冻结资金成功,但更新订单状态失败:" + updateStatusResponse.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
        }
        
        //  调用卡服务充值申请起始接口
        DodopalResponse<BJCrdSysOrderDTO> loadOrderFunResponse = null;
        try {
            loadOrderFunResponse = bjnfcMobileRechargeDelegate.chargeStart(crdDTO);
        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            response.setResponseEntity(crdDTO);
            return response;
        }
        if (null == loadOrderFunResponse || null == loadOrderFunResponse.getResponseEntity()) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage("产品库调用卡服务提供的充值申请接口:卡服务返回结果实体为：NULL");
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        if (!ResponseCode.SUCCESS.equals(loadOrderFunResponse.getCode())) {

            // 设置 '支付网关资金解冻'接口参数
            if (LoadFlagEnum.LOAD_NO.getCode().equals(productOrder.getLoadFlag())) {
                PayTranDTO transactionDTO = new PayTranDTO();
                transactionDTO.setOrderCode(icdcOrderNum);
                if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                    transactionDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
                    transactionDTO.setCusTomerCode(crdDTO.getUsercode());
                } else {
                    transactionDTO.setCusTomerType(MerUserTypeEnum.MERCHANT.getCode());
                    transactionDTO.setCusTomerCode(crdDTO.getMercode());
                }
                transactionDTO.setOperatorId(productOrder.getUserId());

                // 调用'支付网关资金解冻'接口
                DodopalResponse<Boolean> unfreeAmtResponse = null;
                try {
                    unfreeAmtResponse = bjnfcMobileRechargeDelegate.unfreezeAccountAmt(transactionDTO);
                }
                catch (HessianRuntimeException e) {
                    response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                    response.setResponseEntity(crdDTO);
                    return response;
                }

                DodopalResponse<Integer> updatePorductForUnBlockResult = productOrderService.updateOrderStatusWhenUnbolckFund(icdcOrderNum, unfreeAmtResponse.getCode());

                if (updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode()) && !ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode()) && updatePorductForUnBlockResult.getResponseEntity() > 0) {

                    // 资金解冻失败，产品库订单状态更新成功，充值流程正常终止。
                    response.setCode(unfreeAmtResponse.getCode());
                    response.setMessage("支付交易解冻资金失败:" + unfreeAmtResponse.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                } else if (!updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode()) && !ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode()) && updatePorductForUnBlockResult.getResponseEntity() == 0) {

                    // 资金解冻失败，产品库订单状态更新失败，充值流程非正常终止。
                    response.setCode(unfreeAmtResponse.getCode());
                    response.setMessage("支付交易解冻资金失败:" + unfreeAmtResponse.getMessage() + ",更新订单状态也失败:响应码:" + updatePorductForUnBlockResult.getCode() + "响应消息:" + updatePorductForUnBlockResult.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                } else if (!updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode()) && ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode()) && updatePorductForUnBlockResult.getResponseEntity() == 0) {

                    // 资金解冻成功，产品库订单状态更新失败，充值流程非正常终止。
                    response.setCode(updatePorductForUnBlockResult.getCode());
                    response.setMessage("支付交易解冻资金成功,但更新订单状态失败:" + updatePorductForUnBlockResult.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }
        response.setCode(loadOrderFunResponse.getCode());
        response.setResponseEntity(loadOrderFunResponse.getResponseEntity());
        return response;
    }

    /**
     * 充值申请后续接口
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeFollow(BJCrdSysOrderDTO crdDTO) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();

        String icdcOrderNum = crdDTO.getPrdordernum();

        log.info("充值申请后续接口,第一步,查询产品库订单信息,订单编号:" + icdcOrderNum);
        DodopalResponse<ProductOrder> findOrderResponse = productOrderService.getProductOrderByProOrderNum(icdcOrderNum);
        ProductOrder productOrder = findOrderResponse.getResponseEntity();
        if (productOrder == null ) {
            throw new DDPException(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST, "非法订单:订单不存在。订单编号:" + icdcOrderNum);
        }
        crdDTO.setUsercode(productOrder.getUserCode());
        crdDTO.setMercode(productOrder.getMerCode());
        crdDTO.setUserid(productOrder.getUserId());
        crdDTO.setLoadordernum(productOrder.getLoadOrderNum());
        crdDTO.setTxnamt(String.valueOf(productOrder.getTxnAmt()));
        crdDTO.setYktcode(productOrder.getYktCode());
        crdDTO.setCitycode(productOrder.getCityCode());

        // 重发业务判断标志位（默认为第一次请求）
        boolean requestAgain = false;
        if ("0".equals(productOrder.getResendSign())) {
            
            // 判断产品库接到DLL第一次请求，更新订单重发标志位：置为1
            DodopalResponse<Integer> updateResponse= productOrderService.updateReSignByProOrderNum(icdcOrderNum);
            if (!ResponseCode.SUCCESS.equals(updateResponse.getCode()) || updateResponse.getResponseEntity() == 0) {
                throw new DDPException(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST, "非法订单:订单不存在。订单编号:" + icdcOrderNum);
            }
        } else if ("1".equals(productOrder.getResendSign())){
            
            // 重发标志位为1，则认为本次请求为DLL重发请求
            log.info("充值申请后续接口,产品库重发标志位为1,判断本次重发请求.产品库透传卡服务结果,不修改订单状态。订单编号:" + icdcOrderNum);
            requestAgain = true;
        }
        
        log.info("充值申请后续接口,第二步,调用卡服务提供的充值申请后续接口。订单编号:" + icdcOrderNum);
        DodopalResponse<BJCrdSysOrderDTO> loadOrderFunResponse = null;
        try {
            loadOrderFunResponse = bjnfcMobileRechargeDelegate.chargeFollow(crdDTO);// 是否DLL重发请求，卡服务内部做判断
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "产品库调用卡服务接口:Hessian异常");
        }
        
        if (null == loadOrderFunResponse || null == loadOrderFunResponse.getResponseEntity()) {
            throw new DDPException(ResponseCode.UNKNOWN_ERROR, "产品库调用卡服务提供的充值申请后续接口:卡服务返回结果实体为：NULL");
        }
        
        // 透传业务判断标志位
        boolean passBusinessSign = false;
        if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(loadOrderFunResponse.getResponseEntity().getTradeendflag())) {
            log.info("充值申请后续接口,卡服务充值申请结果tradeendflag==0,判断本次请求为透传请求.产品库透传卡服务结果,不去修改订单状态。订单编号:" + icdcOrderNum);
            passBusinessSign = true;
        }
        
        // 卡服务判断交易结束（tradeendflag==1）
        if (!requestAgain && !passBusinessSign) {
            
            log.info("充值申请后续接口,第三步,产品库更新公交卡充值订单充值密钥状态。订单编号:" + icdcOrderNum+",卡服务获取充值密钥响应码:"+loadOrderFunResponse.getCode());
            // 充值申请接口,第三步,调用产品库更新公交卡充值订单充值密钥状态接口修改订单状态
            DodopalResponse<Integer> updateStatusResponse = productOrderService.updateOrderStatusWhenRetrieveRechargeKey(icdcOrderNum, loadOrderFunResponse.getCode());
            
            if (!updateStatusResponse.getCode().equals(loadOrderFunResponse.getCode())
                && !ResponseCode.SUCCESS.equals(loadOrderFunResponse.getCode())
                && updateStatusResponse.getResponseEntity() == 0) {
                
                // 卡服务充值申请密钥失败，产品库订单状态更新失败，充值流程非正常终止。
                returnResonse.setCode(loadOrderFunResponse.getCode());
                returnResonse.setMessage("卡服务充值申请密钥失败:响应码:"+loadOrderFunResponse.getCode()+",响应消息:"+ loadOrderFunResponse.getMessage()+";更新订单状态也失败:响应码:"+updateStatusResponse.getCode() + "响应消息:" + updateStatusResponse.getMessage() + ";订单编号:" + icdcOrderNum);
                returnResonse.setResponseEntity(crdDTO);
                return returnResonse;
            }  else if (!updateStatusResponse.getCode().equals(loadOrderFunResponse.getCode())
                && ResponseCode.SUCCESS.equals(loadOrderFunResponse.getCode())
                && updateStatusResponse.getResponseEntity() == 0) {
                
                // 卡服务充值申请密钥成功，产品库订单状态更新失败，充值流程非正常终止。
                returnResonse.setCode(updateStatusResponse.getCode());
                returnResonse.setMessage("卡服务充值申请密钥成功,但更新订单状态失败:响应码:"+updateStatusResponse.getCode() +"响应消息:"+ updateStatusResponse.getMessage()+ ";订单编号:" + icdcOrderNum);
                returnResonse.setResponseEntity(crdDTO);
                return returnResonse;
            } else if (updateStatusResponse.getCode().equals(loadOrderFunResponse.getCode()) 
                && !ResponseCode.SUCCESS.equals(loadOrderFunResponse.getCode())
                && updateStatusResponse.getResponseEntity() > 0) {
    
                // 设置 '支付网关资金解冻'接口参数
                if (LoadFlagEnum.LOAD_NO.getCode().equals(productOrder.getLoadFlag())) {
                    
                    //  卡服务充值申请密钥失败，产品库订单状态更新成功，充值失败。非圈存订单解冻资金，流程正常终止。
                    log.info("充值申请后续接口,第四步,充值申请密钥失败,开始调用'支付网关资金解冻'接口。订单编号:" + icdcOrderNum);
                    
                    PayTranDTO transactionDTO = new PayTranDTO();
                    transactionDTO.setOrderCode(icdcOrderNum);
                    if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                        transactionDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
                        transactionDTO.setCusTomerCode(crdDTO.getUsercode());
                    } else {
                        transactionDTO.setCusTomerType(MerUserTypeEnum.MERCHANT.getCode());
                        transactionDTO.setCusTomerCode(crdDTO.getMercode());
                    }
                    transactionDTO.setOperatorId(productOrder.getUserId());

                    // 调用'支付网关资金解冻'接口
                    DodopalResponse<Boolean> unfreeAmtResponse = null;
                    try {
                        unfreeAmtResponse = bjnfcMobileRechargeDelegate.unfreezeAccountAmt(transactionDTO);
                    }
                    catch (HessianRuntimeException e) {
                        returnResonse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                        returnResonse.setMessage("产品库调用'支付网关资金解冻'接口:Hessian异常");
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    }

                    log.info("充值申请接口,第五步,更新公交卡充值订单资金变动状态(资金冻解状态)");
                    DodopalResponse<Integer> updatePorductForUnBlockResult = productOrderService.updateOrderStatusWhenUnbolckFund(icdcOrderNum, unfreeAmtResponse.getCode());

                    if (updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode()) 
                        && !ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode())
                        && updatePorductForUnBlockResult.getResponseEntity() > 0) {
                        
                        // 资金解冻失败，产品库订单状态更新成功，充值流程正常终止。
                        returnResonse.setCode(unfreeAmtResponse.getCode());
                        returnResonse.setMessage("支付交易解冻资金失败:" + unfreeAmtResponse.getMessage());
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    } else if (!updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode())
                        && !ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode())
                        && updatePorductForUnBlockResult.getResponseEntity() == 0) {
                        
                        // 资金解冻失败，产品库订单状态更新失败，充值流程非正常终止。
                        returnResonse.setCode(unfreeAmtResponse.getCode());
                        returnResonse.setMessage("支付交易解冻资金失败:" + unfreeAmtResponse.getMessage()+",更新订单状态也失败:响应码:"+updatePorductForUnBlockResult.getCode() + "响应消息:" + updatePorductForUnBlockResult.getMessage());
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    }  else if (!updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode())
                        && ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode())
                        && updatePorductForUnBlockResult.getResponseEntity() == 0) {
                        
                        // 资金解冻成功，产品库订单状态更新失败，充值流程非正常终止。
                        returnResonse.setCode(updatePorductForUnBlockResult.getCode());
                        returnResonse.setMessage("支付交易解冻资金成功,但更新订单状态失败:" + updatePorductForUnBlockResult.getMessage());
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    }
                }
            } 
        }
        returnResonse.setCode(loadOrderFunResponse.getCode());
        returnResonse.setResponseEntity(loadOrderFunResponse.getResponseEntity());
        return returnResonse;
    }

    /**
     * 充值结果上传
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeResultUp(BJCrdSysOrderDTO crdDTO) {

        DodopalResponse<BJCrdSysOrderDTO> returnResonse = new DodopalResponse<BJCrdSysOrderDTO>();

        String icdcOrderNum = crdDTO.getPrdordernum();// 产品库订单号
        String txnstat = crdDTO.getTxnstat();// 结果上传交易状态0：成功 1：失败 2：未知
        String blackAmt = crdDTO.getBlackamt();//结果上传卡内余额

        log.info("上传结果接口,第一步,查询产品库订单信息,订单编号:" + icdcOrderNum);
        DodopalResponse<ProductOrder> findOrderResponse = productOrderService.getProductOrderByProOrderNum(icdcOrderNum);
        ProductOrder productOrder = findOrderResponse.getResponseEntity();
        if (productOrder == null) {
            throw new DDPException(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST, "非法订单:订单不存在。订单编号:" + icdcOrderNum);
        }
        crdDTO.setUsercode(productOrder.getUserCode());
        crdDTO.setMercode(productOrder.getMerCode());
        crdDTO.setUserid(productOrder.getUserId());
        crdDTO.setLoadordernum(productOrder.getLoadOrderNum());
        crdDTO.setYktcode(productOrder.getYktCode());
        crdDTO.setCitycode(productOrder.getCityCode());

        // 产品库业务标志位：（重发业务判断标志/上传取消充值标志）
        boolean businessFlg = false;
        
        // 如果产品库订单是最终状态，即为状态为：2：充值失败；4：充值成功；5：充值可疑
        // 以上情况，视为产品库上传结果成功，一卡通充值流程结束，响应码为“000000”。
        if (RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState()) 
            || RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState()) 
            || RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())) {
            
            log.info("上传充值结果接口,产品库成功响应,本次请求为重发请求,产品库不做具体业务，透传卡服务。订单编号:" + icdcOrderNum);
            businessFlg = true;
        } else {
            
            // 取消交易不判断状态 2016-05-27
            if (!RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(crdDTO.getTxnstat())) {
                
                // 校验产品库订单状态
                if (!(RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState()) 
                    && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode().equals(productOrder.getProInnerState()))) {
                    returnResonse.setCode(ResponseCode.PRODUCT_UPDATE_RESULT_STATUS_ORDER_STATUS_ERROR);
                    returnResonse.setResponseEntity(crdDTO);
                    return returnResonse;
                }
                // 校验圈存订单状态
                String loadOrderNum = productOrder.getLoadOrderNum();
                if (StringUtils.isNotBlank(loadOrderNum)) {
                    LoadOrder loadOrder = loadOrderService.getLoadOrderByLoadOrderNum(loadOrderNum);
                    
                    if (!LoadOrderStatusEnum.RECHARGE_ING.getCode().equals(loadOrder.getStatus())) {
                        returnResonse.setCode(ResponseCode.PRODUCT_LOADORDER_PARAM_INVALID);
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    }
                    
                }
            }
        }
        
        log.info("上传结果接口,第三步,调用卡服务上传结果接口,订单编号:" + icdcOrderNum + ",loadOrderNum:" + crdDTO.getLoadordernum() + ",结果上传交易状态(0:成功 1:失败 2:未知):" + txnstat);
        DodopalResponse<BJCrdSysOrderDTO> chargeResultUpResponse = null;
        try {
            chargeResultUpResponse = bjnfcMobileRechargeDelegate.chargeResultUp(crdDTO);
        }
        catch (HessianRuntimeException e) {
            returnResonse.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }
        
        // 根据卡服务上传结果的状态更新产品库订单（圈存订单），并执行账户操作
        if (!businessFlg && ResponseCode.SUCCESS.equals(chargeResultUpResponse.getCode())) {

            // 更新产品库订单与圈存订单状态
            DodopalResponse<Integer> updatePorductResult = productOrderService.updateOrderStatusForResultUpload(icdcOrderNum, RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode(), blackAmt);

            if (ResponseCode.SUCCESS.equals(updatePorductResult.getCode())) {

                if (LoadFlagEnum.LOAD_NO.getCode().equals(productOrder.getLoadFlag())) {
                    PayTranDTO transactionDTO = new PayTranDTO();
                    transactionDTO.setOrderCode(icdcOrderNum);
                    if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                        transactionDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
                        transactionDTO.setCusTomerCode(crdDTO.getUsercode());
                    } else {
                        transactionDTO.setCusTomerType(MerUserTypeEnum.MERCHANT.getCode());
                        transactionDTO.setCusTomerCode(crdDTO.getMercode());
                    }
                    transactionDTO.setOperatorId(productOrder.getUserId());

                    DodopalResponse<Boolean> deductAmtResponse = null;
                    try {
                        deductAmtResponse = bjnfcMobileRechargeDelegate.deductAccountAmt(transactionDTO);
                    }
                    catch (HessianRuntimeException e) {
                        returnResonse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    }

                    DodopalResponse<Integer> updatePorductForDeduckResult = productOrderService.updateOrderStatusWhendeduckFund(icdcOrderNum, deductAmtResponse.getCode());

                    if (updatePorductForDeduckResult.getCode().equals(deductAmtResponse.getCode()) && !ResponseCode.SUCCESS.equals(deductAmtResponse.getCode()) && updatePorductForDeduckResult.getResponseEntity() > 0) {

                        // 资金扣款失败，产品库订单状态更新成功，充值流程正常终止。
                        returnResonse.setCode(deductAmtResponse.getCode());
                        returnResonse.setMessage("支付交易扣款资金失败:" + deductAmtResponse.getMessage());
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    } else if (!updatePorductForDeduckResult.getCode().equals(deductAmtResponse.getCode()) && !ResponseCode.SUCCESS.equals(deductAmtResponse.getCode()) && updatePorductForDeduckResult.getResponseEntity() == 0) {

                        // 资金扣款失败，产品库订单状态更新失败，充值流程非正常终止。
                        returnResonse.setCode(deductAmtResponse.getCode());
                        returnResonse.setMessage("支付交易扣款资金失败:" + deductAmtResponse.getMessage() + ",更新订单状态也失败:响应码:" + updatePorductForDeduckResult.getCode() + "响应消息:" + updatePorductForDeduckResult.getMessage());
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    } else if (!updatePorductForDeduckResult.getCode().equals(deductAmtResponse.getCode()) && ResponseCode.SUCCESS.equals(deductAmtResponse.getCode()) && updatePorductForDeduckResult.getResponseEntity() == 0) {

                        // 资金扣款成功，产品库订单状态更新失败，充值流程非正常终止。
                        returnResonse.setCode(updatePorductForDeduckResult.getCode());
                        returnResonse.setMessage("支付交易扣款资金成功,但更新订单状态失败:" + updatePorductForDeduckResult.getMessage());
                        returnResonse.setResponseEntity(crdDTO);
                        return returnResonse;
                    }
                }

            }
        } else {

            log.error("上传结果接口,第三步,调用卡服务充值校验更新接口,失败,订单编号:" + icdcOrderNum + ",响应码:" + chargeResultUpResponse.getCode() + ",响应消息:" + chargeResultUpResponse.getMessage());
            returnResonse.setCode(chargeResultUpResponse.getCode());
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }

        returnResonse.setCode(chargeResultUpResponse.getCode());
        returnResonse.setResponseEntity(chargeResultUpResponse.getResponseEntity());

        return returnResonse;
    }
    
    //*****************************************************     私   有   方   法        *****************************************************************//
    /**
     * 检验公交卡充值合法性(生单私有方法)
     */
    @Transactional(readOnly = true)
    private DodopalResponse<PrdProductYktInfoForIcdcRecharge> preCheckBeforeCreateIcdcOrder(String loadFlag, String productCode, String loadOrderNum) {
        DodopalResponse<PrdProductYktInfoForIcdcRecharge> response = new DodopalResponse<PrdProductYktInfoForIcdcRecharge>();
        response.setCode(ResponseCode.SUCCESS);

        long loadTxnAmt = 0;// 圈存订单的金额
        long customerPayAmt = 0;// 圈存订单的商户实付金额
        long customerGain = 0;// 圈存订单的商户利润

        // a:圈存订单则需要检验圈存订单合法性
        if (LoadFlagEnum.LOAD_YES.getCode().equals(loadFlag)) {
            LoadOrderQuery loadOrder = new LoadOrderQuery();
            loadOrder.setOrderNum(loadOrderNum);
            DodopalResponse<LoadOrderDTO> loadOrderResponse = loadOrderService.validateLoadOrderForIcdcRecharge(loadOrder);
            if (ResponseCode.SUCCESS.equals(loadOrderResponse.getCode())) {
                productCode = loadOrderResponse.getResponseEntity().getProductCode();
                loadTxnAmt = loadOrderResponse.getResponseEntity().getLoadAmt();
                customerPayAmt = loadOrderResponse.getResponseEntity().getCustomerPayAmt();
                customerGain = loadOrderResponse.getResponseEntity().getCustomerGain();
            } else {
                response.setCode(loadOrderResponse.getCode());
                response.setMessage("圈存订单合法性未通过:" + loadOrderResponse.getMessage());
                return response;
            }
        }

        // b.检验公交卡充值产品合法性
        if (StringUtils.isBlank(productCode)) {
            response.setCode(ResponseCode.PRODUCT_YKT_PRO_CODE_NULL);
            response.setMessage("公交卡充值产品合法性未通过:一卡通产品编号不能为空");
            return response;
        }
        DodopalResponse<PrdProductYktInfoForIcdcRecharge> validatePrdResponse = prdProductYktService.validateProductForIcdcRecharge(productCode);
        if (!ResponseCode.SUCCESS.equals(validatePrdResponse.getCode())) {
            response.setCode(validatePrdResponse.getCode());
            response.setMessage("公交卡充值产品合法性未通过:" + validatePrdResponse.getMessage());
            return response;
        }

        // 当订单来自圈存订单的时候，充值金额从圈存订单表中取的
        if (LoadFlagEnum.LOAD_YES.getCode().equals(loadFlag)) {
            validatePrdResponse.getResponseEntity().setProPrice(loadTxnAmt);
            validatePrdResponse.getResponseEntity().setCustomerPayAmt(customerPayAmt);
            validatePrdResponse.getResponseEntity().setCustomerGain(customerGain);
        }
        response.setResponseEntity(validatePrdResponse.getResponseEntity());

        return response;
    }


    /**
     * NFC手机个人端：生单私有方法
     */
    private ProductOrder createPersonalPrdOrder(ProductOrder prdOrder, BJCrdSysOrderDTO crdDTO, PrdProductYktInfoForIcdcRecharge validatePrdProduct, ProductYKT validateYktResponse, Map<String, String> merInfoMap) {

        String userCode = merInfoMap.containsKey("userCode") ? merInfoMap.get("userCode") : "";
        String userName = merInfoMap.containsKey("userName") ? merInfoMap.get("userName") : "";
        
        prdOrder.setOrderCardno(crdDTO.getTradecard());// 卡内号
        prdOrder.setCardFaceno(crdDTO.getCardfaceno());// 卡面号
        prdOrder.setPosCode(crdDTO.getPosid());// POS号
        prdOrder.setPayType(crdDTO.getPaytype());// 支付类型
        prdOrder.setPayWay(crdDTO.getPayway());// 支付方式
        prdOrder.setUserId(crdDTO.getUserid());// 操作员id
        prdOrder.setUserCode(userCode);// 用户号
        prdOrder.setUserName(userName);// 操作员名称
        prdOrder.setCreateUser(crdDTO.getUserid());// 创建人
        prdOrder.setSource(crdDTO.getSource());// 来源
        prdOrder.setCityCode(crdDTO.getCitycode());// 城市code
        prdOrder.setYktCode(crdDTO.getYktcode());// 通卡公司code
        prdOrder.setCityName(validatePrdProduct.getCityName());// 城市名称
        prdOrder.setYktName(validateYktResponse.getYktName());// 通卡公司名称
        prdOrder.setYktRechargeRate(validateYktResponse.getYktRechargeRate());// 通卡公司一卡通充值费率（固定类型：千分比）
        prdOrder.setTxnAmt(Long.valueOf(crdDTO.getTxnamt()));// 充值金额（产品面额或圈存订单金额）
        prdOrder.setProCode(crdDTO.getProductcode());// 产品编号
        prdOrder.setProName(validatePrdProduct.getProName());// 产品名称
        prdOrder.setBefbal(Long.valueOf(crdDTO.getBefbal()));// 充值前金额
        
        //  NFC手机端：一些默认值设定  START   **********************************//
        prdOrder.setMerUserType(MerTypeEnum.PERSONAL.getCode());// NFC端：默认：个人
        prdOrder.setMerCode(userCode);// 客户号：个人用户code
        prdOrder.setMerName(userName);// 客户：个人名称
        prdOrder.setMerRate(0d);// 费率：个人用户默认：0
        prdOrder.setMerRateType(RateTypeEnum.PERMILLAGE.getCode());// 费率类型：个人用户默认：千分比

        if (StringUtils.isBlank(crdDTO.getLoadordernum())) {
            prdOrder.setPayServiceRate(0d);// 服务费率：个人用户默认：0
            prdOrder.setPayServiceType(RateTypeEnum.PERMILLAGE.getCode());// 服务费率类型：个人用户默认：千分比
        }
        //  NFC手机端：一些默认值设定  END   **********************************//
        
        // 判断是否圈存订单
        String loadFlag = LoadFlagEnum.LOAD_YES.getCode();
        if (StringUtils.isBlank(crdDTO.getLoadordernum())) {
            loadFlag = LoadFlagEnum.LOAD_NO.getCode();
        }
        prdOrder.setLoadOrderNum(crdDTO.getLoadordernum());// 圈存订单号
        prdOrder.setLoadFlag(loadFlag);// 圈存标识

        long rechargeAmt = validatePrdProduct.getProPrice();// 充值金额(产品面额)
        
        long productMoney = 0;//    待考虑
        if (StringUtils.isBlank(crdDTO.getLoadordernum())) {
            productMoney = validatePrdProduct.getProPrice();//非圈存：取值产品面额，用于计算商户的进货价
        } else {
            productMoney = validatePrdProduct.getCustomerPayAmt();//圈存：取值圈存订单实付金额，用于计算商户的进货价
        }
        
        // 个人进货价(DDP出货价)
        prdOrder.setMerPurchaasePrice(productMoney);
        
        // 实付（收）金额
        prdOrder.setReceivedPrice(productMoney);
        
        // 个人利润---->面价 - 实付（收）金额
        prdOrder.setMerGain(rechargeAmt - prdOrder.getReceivedPrice());
    
        return prdOrder;
    }

}
