package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.product.dto.LoadOrderDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.RechargeOrderResultEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.MoneyAlgorithmUtils;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.query.LoadOrderQuery;
import com.dodopal.product.business.service.IcdcRechargeService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductOrderService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.IcdcRechargeDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service
public class IcdcRechargeServiceImpl implements IcdcRechargeService {

    private final static Logger log = LoggerFactory.getLogger(IcdcRechargeServiceImpl.class);

    @Autowired
    private IcdcRechargeDelegate icdcRechargeDelegate;
    @Autowired
    private ProductOrderService productOrderService;
    @Autowired
    private LoadOrderService loadOrderService;
    @Autowired
    private PrdProductYktService prdProductYktService;
    @Autowired
    private ProductYKTService productYKTService;

    /**
     * 验卡接口
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<CrdSysOrderDTO> preCheckCardQueryInPrd(CrdSysOrderDTO crdDTO) {
        DodopalResponse<CrdSysOrderDTO> response = null;
        try {
            // 产品库透传参数给卡服务进行验卡接口
            response = icdcRechargeDelegate.queryCheckCardFun(crdDTO);
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "调用卡服务验卡接口queryCheckCardFun接口,Hessian链接异常");
        }
        response.setCode(response.getCode());
        response.setResponseEntity(response.getResponseEntity());
        return response;
    }

    /**
     * 生单接口
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> createIcdcRechargeOrder(CrdSysOrderDTO crdDTO) {
        
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        
        // 判断是否圈存订单
        String loadOrderNum = crdDTO.getLoadordernum();
        String loadFlag = LoadFlagEnum.LOAD_YES.getCode();
        if (StringUtils.isBlank(loadOrderNum)) {
            loadFlag = LoadFlagEnum.LOAD_NO.getCode();
        }

        //1.调用产品库内部方法,检验公交卡充值合法性(a.如果为圈存订单则需要检验圈存订单合法性.b.如果不是圈存订单,则调用产品库公交卡产品验证方法)
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
        
        //****** START 产品库主订单生成之前校验本次公交卡充值后卡内金额是否超过卡内最大允许金额   add by xshen 20150827 ****//
        long txnamt = validatePrdProduct.getProPrice();// 一卡通充值金额
        long yktCardMaxLimit = 0;// 卡内最大允许金额(验卡环节，卡服务从城市前置获取的值，DLL需要透传过来)
        if (!StringUtils.isBlank(crdDTO.getCardlimit())) {
            yktCardMaxLimit = Long.parseLong(crdDTO.getCardlimit());
        }
        long befbal = 0;// 交易前卡余额
        if (!StringUtils.isBlank(crdDTO.getBefbal())) {
            befbal = Long.parseLong(crdDTO.getBefbal());
        }
        // 交易后卡余额不能超过卡内最大允许金额
        if (yktCardMaxLimit < (txnamt + befbal)) {
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_LIMIT_ERROR);
            response.setMessage("充值金额与卡内余额之和超过卡内限额:交易金额:" + txnamt + ";交易前卡余额:" + befbal + "卡内最大限额：" + yktCardMaxLimit);
            response.setResponseEntity(crdDTO);
            return response;
        }
        //****** END 产品库主订单生成之前校验本次公交卡充值后卡内金额是否超过卡内最大允许金额 add by xshen 20150827 ****//
        
        
       // ***********   检验通卡公司的合法性  add by shenXiang 2015-12-01 start   ***********//
        DodopalResponse<ProductYKT> yktResponse = productYKTService.validateYktServiceNormalForIcdcRecharge(crdDTO.getYktcode());
        if (!ResponseCode.SUCCESS.equals(yktResponse.getCode())) {
            response.setCode(yktResponse.getCode());
            response.setMessage(yktResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        ProductYKT validateYktResponse = yktResponse.getResponseEntity(); 
        // ***********   检验通卡公司的合法性  add by shenXiang 2015-12-01 end   ***********//
        
        
        //2.调用用户系统,验证当前商户是否合法
        DodopalResponse<Map<String, String>> merCheckResponse = null;
        try {
            merCheckResponse = icdcRechargeDelegate.validateMerchantForIcdcRecharge(crdDTO.getMercode(), crdDTO.getUserid(), crdDTO.getPosid(), crdDTO.getYktcode(), crdDTO.getSource());
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR, "调用用户系统校验商户合法性失败:Hessian异常");
        }
        if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
            response.setCode(merCheckResponse.getCode());
            response.setMessage("商户合法性校验未通过:" + merCheckResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        Map<String, String> merInfoMap = merCheckResponse.getResponseEntity();

        
        //***********************  非圈存订单，获取订单参数：支付服务费率与服务费率类型     add by shenXiang 2015-11-05  START ***************************//
        PayServiceRateDTO payServiceRateDTO =  null;
        if (StringUtils.isBlank(loadOrderNum)) {
            String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
            if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
                
                // **** 外接商户的支付方式ID获取自“商户合法性验证”返回参数 add by shenXiang 2015-11-24  START  *************//
                String payWayExtId = merInfoMap.containsKey("payWayExtId") ? merInfoMap.get("payWayExtId") : "";
                crdDTO.setPayway(payWayExtId);
                crdDTO.setPaytype(PayTypeEnum.PAY_ACT.getCode());
                // **** 外接商户的支付方式ID获取自“商户合法性验证”返回参数 add by shenXiang 2015-11-24  END *************//
                
                //2.1 外接支付方式、调用支付交易系统，获取支付服务费率；服务费率类型:2_千分比（固定值）
                DodopalResponse<PayAwayDTO> getPayyExternalServiceRateResponse = null;
                PayAwayDTO payAwayDTO = null;
                try {
                    getPayyExternalServiceRateResponse = icdcRechargeDelegate.findPayExternalById(crdDTO.getPayway());
                    payAwayDTO = getPayyExternalServiceRateResponse.getResponseEntity();
                }
                catch (HessianRuntimeException e) {
                    throw new DDPException(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR, "调用支付交易系统获取支付服务费率与服务费率类型失败:Hessian异常");
                }
                if (!ResponseCode.SUCCESS.equals(getPayyExternalServiceRateResponse.getCode()) || payAwayDTO == null) {
                    response.setCode(getPayyExternalServiceRateResponse.getCode());
                    response.setMessage("获取支付服务费率与服务费率类型失败:" + response.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
                payServiceRateDTO = new PayServiceRateDTO();
                payServiceRateDTO.setRate(payAwayDTO.getPayServiceRate());
                payServiceRateDTO.setRateType(ServiceRateTypeEnum.PERMILLAGE.getCode());
                
            } else if (MerTypeEnum.PERSONAL.getCode().equals(merType)){
                
                //2.2 个人用户、支付服务费率类型：2_千分比（固定值）；服务费率：0（固定值）
                payServiceRateDTO = new PayServiceRateDTO();
                payServiceRateDTO.setRate(0);
                payServiceRateDTO.setRateType(ServiceRateTypeEnum.PERMILLAGE.getCode());
                
            } else {
                
                //2.3 通用支付方式、调用支付交易系统，获取支付服务费率；服务费率类型
                DodopalResponse<PayServiceRateDTO> getPayServiceRateResponse = null;
                try {
                    getPayServiceRateResponse = icdcRechargeDelegate.findPayServiceRate(crdDTO.getPayway(), RateCodeEnum.YKT_RECHARGE.getCode(), Long.parseLong(crdDTO.getTxnamt()));
                    payServiceRateDTO = getPayServiceRateResponse.getResponseEntity();
                }
                catch (HessianRuntimeException e) {
                    throw new DDPException(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR, "调用支付交易系统获取支付服务费率与服务费率类型失败:Hessian异常");
                }
                if (!ResponseCode.SUCCESS.equals(getPayServiceRateResponse.getCode()) || payServiceRateDTO == null) {
                    response.setCode(getPayServiceRateResponse.getCode());
                    response.setMessage("获取支付服务费率与服务费率类型失败:" + getPayServiceRateResponse.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }
        //***********************  非圈存订单，获取订单参数：支付服务费率与服务费率类型     add by shenXiang 2015-11-05  END ***************************//
        
        //3.调用产品库创建订单接口
        ProductOrder prdOrder = new ProductOrder();
        try {
            prdOrder = createPrdOrder(prdOrder, crdDTO, validatePrdProduct, validateYktResponse, merInfoMap, payServiceRateDTO);
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
        
        //***********************  来源：移动端，调用支付交易系统，生成交易流水。报文体设置交易流水号     add by xshen 2015-10-30  START ***************************//
		if (SourceEnum.isPhone(crdDTO.getSource()) && !PayTypeEnum.PAY_ACT.getCode().equals(prdOrder.getPayType())) {
            //  来源：移动端，调用支付交易系统，生成交易流水            
            PayTranDTO payTranDTO = new PayTranDTO();
            if (MerTypeEnum.PERSONAL.getCode().equals(prdOrder.getMerUserType())) {
                payTranDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
                payTranDTO.setCusTomerCode(crdDTO.getUsercode());
            } else {
                payTranDTO.setCusTomerType(MerUserTypeEnum.MERCHANT.getCode());
                payTranDTO.setCusTomerCode(crdDTO.getMercode());
            }
            payTranDTO.setOrderCode(prdOrder.getProOrderNum()); 
            payTranDTO.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());
            payTranDTO.setGoodsName(prdOrder.getProName());
            payTranDTO.setAmount(prdOrder.getMerPurchaasePrice());
            payTranDTO.setSource(crdDTO.getSource());
            payTranDTO.setOrderDate(prdOrder.getProOrderDate());
            payTranDTO.setCityCode(crdDTO.getCitycode());
            payTranDTO.setPayId(crdDTO.getPayway());
            payTranDTO.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());

            String proUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PRODUCT_URL);
            //payTranDTO.setNotifyUrl(proUrl+"/callBackOrder?orderNum="+prdOrder.getProOrderNum()+"&cityCode="+crdDTO.getCitycode());
            payTranDTO.setNotifyUrl(proUrl+"/callBackOrder");

            if (MerTypeEnum.EXTERNAL.getCode().equals(prdOrder.getMerUserType())) {
                payTranDTO.setExtFlg(true);
            } else {
                payTranDTO.setExtFlg(false);
            }
            payTranDTO.setOperatorId(prdOrder.getUserId());
            DodopalResponse<String> getTranNumByMobilepay = null;
            try {
                getTranNumByMobilepay = icdcRechargeDelegate.mobilepay(payTranDTO);
            }
            catch (HessianRuntimeException e) {
                throw new DDPException(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR, "移动端一卡通充值，生单环节调用支付交易系统生成交易流水失败:Hessian异常");
            }
            if (!ResponseCode.SUCCESS.equals(getTranNumByMobilepay.getCode()) || StringUtils.isBlank(getTranNumByMobilepay.getResponseEntity())) {
                response.setCode(getTranNumByMobilepay.getCode());
                response.setMessage("移动端一卡通充值，生单环节生成交易流水（获取交易流水号）失败:" + getTranNumByMobilepay.getMessage());
                response.setResponseEntity(crdDTO);
                return response;
            }
            crdDTO.setPaymentTranNo(getTranNumByMobilepay.getResponseEntity());
        }
        //***********************  来源：移动端，调用支付交易系统，生成交易流水 。报文体设置交易流水号    add by xshen 2015-10-30  END   ***************************//
        
        //4.调用卡服务创建订单口
		crdDTO.setUserid(prdOrder.getUserId());
        crdDTO.setPrdordernum(prdOrder.getProOrderNum());
        DodopalResponse<CrdSysOrderDTO> crdOrderResponse = null;
        try {
            crdOrderResponse = icdcRechargeDelegate.createCrdOrderCardFun(crdDTO);
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "调用卡服务创建订单失败:Hessian异常");
        }

        if (!ResponseCode.SUCCESS.equals(crdOrderResponse.getCode())) {
            response.setCode(crdOrderResponse.getCode());
            response.setMessage("卡服务订单创建失败:" + crdOrderResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        
        response.setCode(crdOrderResponse.getCode());
        response.setResponseEntity(crdOrderResponse.getResponseEntity());
        return response;
    }

    /**
     * 获取圈存初始化指令
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> retrieveApdu(CrdSysOrderDTO crdDTO) {
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        
        //1.调用产品库验证产品库订单
        DodopalResponse<ProductOrder> validOrderResponse = productOrderService.validIcdcOrderWhenRetrievingApdu(crdDTO.getTradecard(), crdDTO.getPrdordernum(), crdDTO.getMerordercode(), crdDTO.getMercode());

        if (!ResponseCode.SUCCESS.equals(validOrderResponse.getCode())) {
            response.setCode(validOrderResponse.getCode());
            response.setMessage("充值验卡环节中验证产品库订单失败:" + validOrderResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }

        // 从订单上获取
        ProductOrder prdOrder = validOrderResponse.getResponseEntity();
        crdDTO.setProductcode(prdOrder.getProCode());
        if (StringUtils.isNotBlank(prdOrder.getPayType())) {
            crdDTO.setPaytype(prdOrder.getPayType().toString());
        }
        if (StringUtils.isNotBlank(prdOrder.getPayWay())) {
            crdDTO.setPayway(prdOrder.getPayWay().toString());
        }
        crdDTO.setTxnamt(String.valueOf(prdOrder.getTxnAmt()));
        crdDTO.setUserid(prdOrder.getUserId());
        crdDTO.setSource(prdOrder.getSource());
        crdDTO.setUsercode(prdOrder.getUserCode());
        crdDTO.setMercode(prdOrder.getMerCode());
        crdDTO.setLoadordernum(prdOrder.getLoadOrderNum());
        crdDTO.setYktcode(prdOrder.getYktCode());
        crdDTO.setCitycode(prdOrder.getCityCode());

        //2.调用用户系统,验证当前商户是否合法(validateMerchantForIcdcRecharge)
        DodopalResponse<Map<String, String>> merCheckResponse = null;
        try {
            merCheckResponse = icdcRechargeDelegate.validateMerchantForIcdcRecharge(crdDTO.getMercode(), crdDTO.getUserid(), crdDTO.getPosid(), crdDTO.getYktcode(), crdDTO.getSource());
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR, "调用用户系统校验商户合法性失败:Hessian异常");
        }
        if (!ResponseCode.SUCCESS.equals(merCheckResponse.getCode())) {
            response.setCode(merCheckResponse.getCode());
            response.setMessage("商户合法性校验未通过:" + merCheckResponse.getMessage());
            response.setResponseEntity(crdDTO);
            return response;
        }
        Map<String, String> merCheckMap = merCheckResponse.getResponseEntity();
        String merType = merCheckMap.containsKey("merType") ? merCheckMap.get("merType").toString() : MerTypeEnum.PERSONAL.getCode();

        
        // ****************     商户类型为外接商户，且来源为外接时；追加判断外部订单号不为空 add by shenXiang 2015-11-26  start    ************//
        if (MerTypeEnum.EXTERNAL.getCode().equals(merType) && SourceEnum.EXT_DLL.getCode().equals(crdDTO.getSource())) {
            if (!DDPStringUtil.isPopulated(crdDTO.getMerordercode())) {
                response.setCode(ResponseCode.PRODUCT_RECHARGE_EXTERNAL_ORDERNUM_IS_NULL);
                response.setResponseEntity(crdDTO);
                return response;
            } else if (!DDPStringUtil.existingWithLength(crdDTO.getMerordercode(), 64)) {
                response.setCode(ResponseCode.PRODUCT_RECHARGE_EXTERNAL_ORDERNUM_OVER_LENGTH);
                response.setResponseEntity(crdDTO);
                return response;
            }
        }
        // ****************     商户类型为外接商户，且来源为外接时；追加判断外部订单号不为空 add by shenXiang 2015-11-26  end    ************//

        
        //3.根据来源判断是否为圈存订单发起的充值,如果不是则调用支付网关提供的冻结资金接口,并且根据返回结果调用"产品库更新订单状态接口"(资金冻结)
        String loadFlag = prdOrder.getLoadFlag();
        if (LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag)) {
            PayTranDTO payTranDTO = new PayTranDTO();
            if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
                payTranDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
                payTranDTO.setCusTomerCode(crdDTO.getUsercode());
            } else {
                payTranDTO.setCusTomerType(MerUserTypeEnum.MERCHANT.getCode());
                payTranDTO.setCusTomerCode(crdDTO.getMercode());
            }
            payTranDTO.setOrderCode(crdDTO.getPrdordernum()); 
            payTranDTO.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());
            payTranDTO.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
            payTranDTO.setGoodsName(prdOrder.getProName());
            payTranDTO.setAmount(prdOrder.getReceivedPrice());
            payTranDTO.setSource(crdDTO.getSource());
            payTranDTO.setOrderDate(prdOrder.getProOrderDate());
            payTranDTO.setCityCode(crdDTO.getCitycode());
            payTranDTO.setPayId(prdOrder.getPayWay());
            if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
                payTranDTO.setExtFlg(true);
            } else {
                payTranDTO.setExtFlg(false);
            }
            payTranDTO.setOperatorId(prdOrder.getUserId());

            DodopalResponse<Boolean> freezeAmtResponse = null;
            try {
                freezeAmtResponse = icdcRechargeDelegate.freezeAccountAmt(payTranDTO);
            }
            catch (HessianRuntimeException e) {
                throw new DDPException(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR, "调用支付交易冻结资金接口失败:Hessian异常");
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

        // 4.调用卡服务提供的获取APDU接口
        DodopalResponse<CrdSysOrderDTO> loadInitResponse = null;
        try {
            loadInitResponse = icdcRechargeDelegate.getLoadInitFun(crdDTO);
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "调用卡服务获取APDU接口失败:Hessian异常");
        }
        
        // 4.1如果调用卡服务的APDU接口失败，如果不是圈存，则去调用资金解冻接口，更新订单资金解冻状态。
        if (!ResponseCode.SUCCESS.equals(loadInitResponse.getCode())) {
            
            if (LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag)) {
                PayTranDTO payTranDTO = new PayTranDTO();
                if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
                    payTranDTO.setCusTomerType(MerUserTypeEnum.PERSONAL.getCode());
                    payTranDTO.setCusTomerCode(crdDTO.getUsercode());
                } else {
                    payTranDTO.setCusTomerType(MerUserTypeEnum.MERCHANT.getCode());
                    payTranDTO.setCusTomerCode(crdDTO.getMercode());
                }
                payTranDTO.setOrderCode(crdDTO.getPrdordernum());
                payTranDTO.setOperatorId(prdOrder.getUserId());

                DodopalResponse<Boolean> unfreeAmtResponse = null;
                try {
                    unfreeAmtResponse = icdcRechargeDelegate.unfreezeAccountAmt(payTranDTO);
                }
                catch (HessianRuntimeException e) {
                    throw new DDPException(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR, "调用支付交易资金解冻接口失败:Hessian异常");
                }
                DodopalResponse<Integer> updatePorductForUnBlockResult = productOrderService.updateOrderStatusWhenUnbolckFund(crdDTO.getPrdordernum(), unfreeAmtResponse.getCode());
                                
                if (updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode()) 
                    && !ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode())
                    && updatePorductForUnBlockResult.getResponseEntity() > 0) {
                    
                    // 资金解冻失败，产品库订单状态更新成功，充值流程正常终止。
                    response.setCode(unfreeAmtResponse.getCode());
                    response.setMessage("支付交易解冻资金失败:" + unfreeAmtResponse.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                } else if (!updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode())
                    && !ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode())
                    && updatePorductForUnBlockResult.getResponseEntity() == 0) {
                    
                    // 资金解冻失败，产品库订单状态更新失败，充值流程非正常终止。
                    response.setCode(unfreeAmtResponse.getCode());
                    response.setMessage("支付交易解冻资金失败:" + unfreeAmtResponse.getMessage()+",更新订单状态也失败:响应码:"+updatePorductForUnBlockResult.getCode() + "响应消息:" + updatePorductForUnBlockResult.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                }  else if (!updatePorductForUnBlockResult.getCode().equals(unfreeAmtResponse.getCode())
                    && ResponseCode.SUCCESS.equals(unfreeAmtResponse.getCode())
                    && updatePorductForUnBlockResult.getResponseEntity() == 0) {
                    
                    // 资金解冻成功，产品库订单状态更新失败，充值流程非正常终止。
                    response.setCode(updatePorductForUnBlockResult.getCode());
                    response.setMessage("支付交易解冻资金成功,但更新订单状态失败:" + updatePorductForUnBlockResult.getMessage());
                    response.setResponseEntity(crdDTO);
                    return response;
                }
            }
        }

        response.setCode(loadInitResponse.getCode());
        response.setResponseEntity(loadInitResponse.getResponseEntity());
        return response;
    }

    /**
     * 充值申请接口
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> icdcRechargeCard(CrdSysOrderDTO crdDTO) {

        DodopalResponse<CrdSysOrderDTO> returnResonse = new DodopalResponse<CrdSysOrderDTO>();

        String icdcOrderNum = crdDTO.getPrdordernum();

        log.info("充值申请接口,第一步,查询产品库订单信息,订单编号:" + icdcOrderNum);
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

        // ******************  产品库接收到DLL请求时，获取重发标志位（0：第一次；1：重发） add by shenXiang 2015-11-10  START    ********************//
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
            log.info("充值申请接口,产品库重发标志位为1,判断本次请求为DLL重发请求.产品库透传卡服务结果,不修改订单状态。订单编号:" + icdcOrderNum);
            requestAgain = true;
        }
        // ******************  产品库接收到DLL请求时，获取重发标志位（0：第一次；1：重发）add by shenXiang 2015-11-10   END    ********************//
        
        
        log.info("充值申请接口,第二步,调用卡服务提供的充值申请接口。订单编号:" + icdcOrderNum);
        // 充值申请接口,第二步,调用卡服务提供的充值申请接口
        DodopalResponse<CrdSysOrderDTO> loadOrderFunResponse = null;
        try {
            loadOrderFunResponse = icdcRechargeDelegate.getLoadOrderFun(crdDTO);// 是否DLL重发请求，卡服务内部做判断
        }
        catch (HessianRuntimeException e) {
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR, "产品库调用卡服务提供的充值申请接口:Hessian异常");
        }
        
        if (null == loadOrderFunResponse || null == loadOrderFunResponse.getResponseEntity()) {
            throw new DDPException(ResponseCode.UNKNOWN_ERROR, "产品库调用卡服务提供的充值申请接口:卡服务返回结果实体为：NULL");
        }
        
        // **********************************  根据卡服务交易结束标志位tradeendflag(0:交易透传；1:交易结束)，判断产品库是否继续做业务    START  *************************//
        // 透传业务判断标志位
        boolean passBusinessSign = false;
        if (CardTradeEndFlagEnum.TRADE_END_FLAG_TRANSPARENT.getCode().equals(loadOrderFunResponse.getResponseEntity().getTradeendflag())) {
            log.info("充值申请接口,卡服务充值申请结果tradeendflag==0,判断本次请求为透传请求.产品库透传卡服务结果,不去修改订单状态。订单编号:" + icdcOrderNum);
            passBusinessSign = true;
        }
        // **********************************  根据卡服务交易结束标志位tradeendflag(0:交易透传；1:交易结束)，判断产品库是否继续做业务   END  ******************************************//
        
        
        // 卡服务判断交易结束（tradeendflag==1）
        if (!requestAgain && !passBusinessSign) {
            
            log.info("充值申请接口,第三步,产品库更新公交卡充值订单充值密钥状态。订单编号:" + icdcOrderNum+",卡服务获取充值密钥响应码:"+loadOrderFunResponse.getCode());
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
    
                //  卡服务充值申请密钥失败，产品库订单状态更新成功，充值失败。非圈存订单解冻资金，流程正常终止。
                log.info("icdcRechargeCard:充值申请接口,第四步,充值申请密钥失败,开始调用'支付网关资金解冻'接口。订单编号:" + icdcOrderNum);
                
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
                        unfreeAmtResponse = icdcRechargeDelegate.unfreezeAccountAmt(transactionDTO);
                    }
                    catch (HessianRuntimeException e) {
                        log.error("充值申请接口,第四步,产品库调用'支付网关资金解冻'接口:Hessian异常", e);
                        // 注：不抛出异常,不回滚事务。
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
     * 公交卡充值——上传结果接口
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> uploadIcdcRechargeResult(CrdSysOrderDTO crdDTO) {

        DodopalResponse<CrdSysOrderDTO> returnResonse = new DodopalResponse<CrdSysOrderDTO>();

        String icdcOrderNum = crdDTO.getPrdordernum();// 产品库订单号
        String txnstat = crdDTO.getTxnstat();// 结果上传交易状态0：成功 1：失败 2：未知
        String blackAmt = crdDTO.getBlackamt();//结果上传卡内余额

        // **********************************  查询取得产品库订单信息  START  ****************************************//

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

        // **********************************  查询取得产品库订单信息  END  ************************************//
        // **********************************  判断DLL是否请求重发  START  ****************************************//

        // 如果产品库订单是最终状态，即为状态为：2：充值失败；4：充值成功；5：充值可疑
        // 以上情况，视为产品库上传结果成功，一卡通充值流程结束，响应码为“000000”。
        if (RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState()) 
            || RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState()) 
            || RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())) {
            
            log.info("上传充值结果接口,DLL重发请求,产品库成功响应。订单编号:" + icdcOrderNum);
            returnResonse.setCode(ResponseCode.SUCCESS);
            
            // 判断： 结果上传交易状态(0:成功 1:失败 2:未知) 与DB数据表中订单主状态是否一致
            // 以上情况，DLL重发请求可疑，记录LOG日志，返回错误码“155052”，重发上传充值结果时，订单状态与DLL充值结果参数不匹配。
            if (RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode().equals(txnstat) && !RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState())
                || RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(txnstat) && !RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState())
                || RechargeOrderResultEnum.RECHARGE_SUSPICIOUS.getCode().equals(txnstat) && !RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())) {
                
                log.info("上传充值结果接口,DLL重发请求,产品库响应可疑。DLL上传参数：结果上传交易状态(0:成功 1:失败 2:未知)："+txnstat+"；订单表主状态：2：充值失败；4：充值成功；5：充值可疑"+productOrder.getProOrderState()+"；订单编号:" + icdcOrderNum);
                returnResonse.setCode(ResponseCode.PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT);
            }
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }

        // ***********************************  判断DLL是否请求重发 END  *****************************************//
        //************************************* 产品库初步更改订单状态   START  ***********************************//

        log.info("上传结果接口,第二步,调用产品库更新订单充值结果的状态接口,订单编号:" + icdcOrderNum + ",结果上传交易状态(0:成功 1:失败 2:未知):" + txnstat+ ",结果上传充值后卡内余额:" + blackAmt);
        DodopalResponse<Integer> updatePorductResult = productOrderService.updateOrderStatusForResultUpload(icdcOrderNum, txnstat, blackAmt);

        if (!ResponseCode.SUCCESS.equals(updatePorductResult.getCode())) {
            throw new DDPException(updatePorductResult.getCode(), "产品库更新订单充值结果的状态失败:响应消息："+updatePorductResult.getMessage()+",订单编号:" + icdcOrderNum);
        }

        //********************************  产品库初步更改订单状态  END    *************************************//
        //********************************  卡服务充值校验更新 START *******************************************//

        log.info("上传结果接口,第三步,调用卡服务充值校验更新接口,订单编号:" + icdcOrderNum + ",loadOrderNum:" + crdDTO.getLoadordernum() + ",结果上传交易状态(0:成功 1:失败 2:未知):" + txnstat);
        DodopalResponse<CrdSysOrderDTO> uplodResultChkUdpOrderFunResponse = null;
        try {
            uplodResultChkUdpOrderFunResponse = icdcRechargeDelegate.uplodResultChkUdpOrderFun(crdDTO);
        }
        catch (HessianRuntimeException e) {
            log.error("上传结果接口,产品库调用'卡服务充值校验更新接口':Hessian异常", e);
            returnResonse.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }

        if (!ResponseCode.SUCCESS.equals(uplodResultChkUdpOrderFunResponse.getCode())) {

            log.error("上传结果接口,第三步,调用卡服务充值校验更新接口,失败,订单编号:" + icdcOrderNum + ",响应码:" + uplodResultChkUdpOrderFunResponse.getCode() + ",响应消息:" + uplodResultChkUdpOrderFunResponse.getMessage());
            returnResonse.setCode(uplodResultChkUdpOrderFunResponse.getCode());
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }

        //*******************************************  卡服务充值校验更新 END ****************************************************//
        //************* 非圈存订单   产品库上传结果资金响应处理（调用支付网关的资金扣款、解冻，同时更改订单状态） START ****************//

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

            if (RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode().equals(txnstat)) {

                log.info("上传结果接口,第四步(结果上传交易状态0：成功),开始调用'支付网关资金扣款'接口,订单编号:" + icdcOrderNum);
                DodopalResponse<Boolean> deductAmtResponse = null;
                try {
                    deductAmtResponse = icdcRechargeDelegate.deductAccountAmt(transactionDTO);
                }
                catch (HessianRuntimeException e) {
                    log.error("上传结果接口,进入调用支付交易提供的资金扣款接口,Hessian链接异常", e);
                    returnResonse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                    returnResonse.setResponseEntity(crdDTO);
                    return returnResonse;
                }

                log.info("上传结果接口,第五步,更新公交卡充值订单资金变动状态(资金扣款状态),订单编号:" + icdcOrderNum + ",资金扣款结果响应码:" + deductAmtResponse.getCode());
                DodopalResponse<Integer> updatePorductForDeduckResult = productOrderService.updateOrderStatusWhendeduckFund(icdcOrderNum, deductAmtResponse.getCode());

                if (updatePorductForDeduckResult.getCode().equals(deductAmtResponse.getCode()) 
                    && !ResponseCode.SUCCESS.equals(deductAmtResponse.getCode())
                    && updatePorductForDeduckResult.getResponseEntity() > 0) {
                    
                    // 资金扣款失败，产品库订单状态更新成功，充值流程正常终止。
                    returnResonse.setCode(deductAmtResponse.getCode());
                    returnResonse.setMessage("支付交易扣款资金失败:" + deductAmtResponse.getMessage());
                    returnResonse.setResponseEntity(crdDTO);
                    return returnResonse;
                } else if (!updatePorductForDeduckResult.getCode().equals(deductAmtResponse.getCode())
                    && !ResponseCode.SUCCESS.equals(deductAmtResponse.getCode())
                    && updatePorductForDeduckResult.getResponseEntity() == 0) {
                    
                    // 资金扣款失败，产品库订单状态更新失败，充值流程非正常终止。
                    returnResonse.setCode(deductAmtResponse.getCode());
                    returnResonse.setMessage("支付交易扣款资金失败:" + deductAmtResponse.getMessage()+",更新订单状态也失败:响应码:"+updatePorductForDeduckResult.getCode() + "响应消息:" + updatePorductForDeduckResult.getMessage());
                    returnResonse.setResponseEntity(crdDTO);
                    return returnResonse;
                }  else if (!updatePorductForDeduckResult.getCode().equals(deductAmtResponse.getCode())
                    && ResponseCode.SUCCESS.equals(deductAmtResponse.getCode())
                    && updatePorductForDeduckResult.getResponseEntity() == 0) {
                    
                    // 资金扣款成功，产品库订单状态更新失败，充值流程非正常终止。
                    returnResonse.setCode(updatePorductForDeduckResult.getCode());
                    returnResonse.setMessage("支付交易扣款资金成功,但更新订单状态失败:" + updatePorductForDeduckResult.getMessage());
                    returnResonse.setResponseEntity(crdDTO);
                    return returnResonse;
                }
            } else if (RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(txnstat)) {

                log.info("上传结果接口,第四步(结果上传交易状态1：失败),开始调用'支付网关资金解冻'接口,订单编号:" + icdcOrderNum);
                DodopalResponse<Boolean> unfreeAmtResponse = null;
                try {
                    unfreeAmtResponse = icdcRechargeDelegate.unfreezeAccountAmt(transactionDTO);
                }
                catch (HessianRuntimeException e) {
                    log.error("上传结果接口,调用'支付网关资金解冻'接口,Hessian链接异常", e);
                    returnResonse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                    returnResonse.setResponseEntity(crdDTO);
                    return returnResonse;
                }

                log.info("上传结果接口,第五步,更新公交卡充值订单资金变动状态(资金冻解状态),订单编号:" + icdcOrderNum + ",资金解冻结果:" + unfreeAmtResponse.getCode());
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
            } else {
                // 结果上传交易状态2：未知 ,则不需要调用支付网关接口   
                log.info("上传结果接口,第四步(结果上传交易状态2:未知),定性为可疑订单,产品库订单号:" + icdcOrderNum);
            }
        }

        //**************** 非圈存订单   产品库上传结果资金响应处理（调用支付网关的资金扣款、解冻，同时更改订单状态） START ****************//
        //**************** 卡服务上传结果响应处理（调用卡服务提供的充值结果接口上传充值结果给卡服务） START *****************************//

        log.info("上传结果接口,第六步,调用卡服务提供的上传充值结果接口,订单编号:" + icdcOrderNum + ",结果上传交易状态(0:成功 1:失败 2:未知):" + txnstat);
        DodopalResponse<CrdSysOrderDTO> uploadResultFunResponse = null;
        try {
            uploadResultFunResponse = icdcRechargeDelegate.uploadResultFun(crdDTO);
        }
        catch (HessianRuntimeException e) {
            log.error("上传结果接口,进入调用卡服务提供的充值结果接口,Hessian链接异常", e);
            returnResonse.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }

        //**************** 卡服务上传结果响应处理（调用卡服务提供的充值结果接口上传充值结果给卡服务） END ****************************//

        log.info("上传结果接口,第六步,调用卡服务提供的上传充值结果接口返回,响应码:" + uploadResultFunResponse.getCode() + ",响应消息:" + uploadResultFunResponse.getMessage() + ",订单编号:" + icdcOrderNum);
        returnResonse.setCode(uploadResultFunResponse.getCode());
        returnResonse.setResponseEntity(uploadResultFunResponse.getResponseEntity());

        return returnResonse;
    }

    /**
     * 公交卡充值——前端充值失败接口
     */
    @Override
    @Transactional
    public DodopalResponse<CrdSysOrderDTO> frontFailReportFun(CrdSysOrderDTO crdDTO) {

        DodopalResponse<CrdSysOrderDTO> returnResonse = new DodopalResponse<CrdSysOrderDTO>();

        String icdcOrderNum = crdDTO.getPrdordernum();

        // **********************************  查询取得产品库订单信息  START  ****************************************//

        log.info("前端充值失败接口,第一步,检索取得产品库订单信息,icdcOrderNum:" + icdcOrderNum);
        DodopalResponse<ProductOrder> findOrderResponse = productOrderService.getProductOrderByProOrderNum(icdcOrderNum);
        ProductOrder productOrder = findOrderResponse.getResponseEntity();
        if (productOrder == null) {
            throw new DDPException(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST, "非法订单:订单不存在。订单编号:" + icdcOrderNum);
        }
        crdDTO.setUsercode(productOrder.getUserCode());
        crdDTO.setMercode(productOrder.getMerCode());
        crdDTO.setUserid(productOrder.getUserId());
        crdDTO.setLoadordernum(productOrder.getLoadOrderNum());
        crdDTO.setCitycode(productOrder.getCityCode());
        crdDTO.setYktcode(productOrder.getYktCode());

        // **********************************  查询取得产品库订单信息  END  ****************************************//
        // **********************************  卡服务验证是否真的失败  START  ****************************************//

        log.info("前端充值失败接口,第二步,调用卡服务验证订单接口,订单编号:" + icdcOrderNum);
        DodopalResponse<CrdSysOrderDTO> cardFrontFailReportFunResponse = null;
        try {
            cardFrontFailReportFunResponse = icdcRechargeDelegate.frontFailReportFun(crdDTO);
        }
        catch (HessianRuntimeException e) {
            log.error("进入调用卡服务验证订单接口,Hessian链接异常", e);
            throw new DDPException(ResponseCode.PRODUCT_CALL_CARD_ERROR);
        }

        if (!ResponseCode.SUCCESS.equals(cardFrontFailReportFunResponse.getCode())) {
            
            log.error("前端充值失败接口,第二步,调用卡服务验证订单接口,失败,响应码:" + cardFrontFailReportFunResponse.getCode()+",响应消息:"+cardFrontFailReportFunResponse.getMessage() + ",订单编号:" + icdcOrderNum);
            returnResonse.setCode(cardFrontFailReportFunResponse.getCode());
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;
        }

        // **********************************  卡服务验证是否真的失败  END  ****************************************//
        // **********************************  产品库更新订单状态  START  ****************************************//

        log.info("前端充值失败接口,第三步,前端充值失败接口调用方法：更新公交卡充值订单前端判断失败结果状态,订单编号:" + icdcOrderNum);
        DodopalResponse<Integer> updatePorductResult = productOrderService.frontFailOrderStatus(icdcOrderNum);

        if (!ResponseCode.SUCCESS.equals(updatePorductResult.getCode())) {
            
            log.error("前端充值失败接口,第三步,更新公交卡充值订单前端判断失败结果状态,失败,响应码:" + updatePorductResult.getCode()+",响应消息:"+ updatePorductResult.getMessage() + ",订单编号:" + icdcOrderNum);
            returnResonse.setCode(updatePorductResult.getCode());
            returnResonse.setResponseEntity(crdDTO);
            return returnResonse;

        }
        
        // **************************************  产品库更新订单状态  END  ****************************************//
        // *****************************  非圈存订单  资金账户解冻并更新产品库订单状态  START  ***********************//

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

            log.info("前端充值失败接口,第四步,开始调用'支付网关资金解冻'接口,订单编号:" + icdcOrderNum);
            DodopalResponse<Boolean> unfreeAmtResponse = null;
            try {
                unfreeAmtResponse = icdcRechargeDelegate.unfreezeAccountAmt(transactionDTO);
            }
            catch (HessianRuntimeException e) {
                log.error("前端充值失败接口,进入调用支付交易提供的资金解冻接口,Hessian链接异常", e);
                returnResonse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
                returnResonse.setResponseEntity(crdDTO);
                return returnResonse;
            }

            log.info("前端充值失败接口,第五步,更新公交卡充值订单资金变动状态(资金冻解状态),订单编号:" + icdcOrderNum + ",资金解冻结果:" + unfreeAmtResponse.getCode());
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
        // *****************************  非圈存订单  资金账户解冻并更新产品库订单状态  END  ***********************//
        
        returnResonse.setCode(cardFrontFailReportFunResponse.getCode());
        returnResonse.setResponseEntity(crdDTO);
        return returnResonse;
    }

    //*********************************************************************************************************************************************//
    //*****************************************************     私   有   方   法        ************************  *****************************************//
    //*********************************************************************************************************************************************//

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
     * 生单私有方法
     */
    private ProductOrder createPrdOrder(ProductOrder prdOrder, CrdSysOrderDTO crdDTO, PrdProductYktInfoForIcdcRecharge validatePrdProduct, ProductYKT validateYktResponse, Map<String, String> merInfoMap, PayServiceRateDTO payServiceRateDTO) {
        String merName = merInfoMap.containsKey("merName") ? merInfoMap.get("merName") : "";
        String merUserNickName = merInfoMap.containsKey("merUserNickName") ? merInfoMap.get("merUserNickName") : "";
        String rateType = merInfoMap.containsKey("rateType") ? merInfoMap.get("rateType") : "";
        double rate = merInfoMap.containsKey("rate") ? Double.parseDouble(merInfoMap.get("rate")) : 0d;
        String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
        String userCode = merInfoMap.containsKey("userCode") ? merInfoMap.get("userCode") : "";
        String pageCallbackUrl = merInfoMap.containsKey("pageCallbackUrl") ? merInfoMap.get("pageCallbackUrl") : "";
        String serviceNoticeUrl = merInfoMap.containsKey("serviceNoticeUrl") ? merInfoMap.get("serviceNoticeUrl") : "";

        prdOrder.setOrderCardno(crdDTO.getTradecard());// 卡内号
        prdOrder.setCardFaceno(crdDTO.getCardfaceno());// 卡面号
        prdOrder.setPosCode(crdDTO.getPosid());// POS号
        prdOrder.setPayType(crdDTO.getPaytype());// 支付类型
        prdOrder.setPayWay(crdDTO.getPayway());// 支付方式

        // TODO 部门ID:生成订单的时候，首先根据商户号判断商户类型是不是集团，如果是，则根据批次单充值项ID（放在外接商户订单号字段中）来定位到集团的人员，从而可以得知部门的数据库ID。
        prdOrder.setDepId("");

        prdOrder.setUserId(crdDTO.getUserid());// 操作员id
        prdOrder.setUserCode(userCode);// 用户号
        prdOrder.setUserName(merUserNickName);// 操作员名称
        prdOrder.setCreateUser(crdDTO.getUserid());// 创建人

        // 商户类型为外接商户，且来源source为外接时
        if (MerTypeEnum.EXTERNAL.getCode().equals(merType) && SourceEnum.EXT_DLL.getCode().equals(crdDTO.getSource())) {

            // 外接商户管理员的id和name
            String extUserId = merInfoMap.containsKey("userId") ? merInfoMap.get("userId") : "";
            String extUserName = merInfoMap.containsKey("userName") ? merInfoMap.get("userName") : "";
            
            prdOrder.setUserId(extUserId);// 外接商户在都都宝平台的系统管理员id
            prdOrder.setUserCode(userCode);// 外接商户在都都宝平台的系统管理员code
            prdOrder.setUserName(extUserName);// 外接商户在都都宝平台的系统管理员name
            prdOrder.setExtUserCode(crdDTO.getUsercode());// 外接商户的操作员由外接页面设置JS参数usercode而来
            prdOrder.setCreateUser(extUserId);// 创建人:外接商户在都都宝平台的系统管理员id
        }

        // 个人类型：临时将商户名(客户名)设为个人用户名
        if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
            merName = merUserNickName;
        }
        prdOrder.setPageCallBackURL(pageCallbackUrl);// 页面回调通知外接商户
        prdOrder.setServiceNoticeURL(serviceNoticeUrl);// 服务器回调通知外接商户

        prdOrder.setMerOrderNum(crdDTO.getMerordercode());// 外部订单号
        prdOrder.setSource(crdDTO.getSource());// 来源

        prdOrder.setCityCode(crdDTO.getCitycode());// 城市code
        prdOrder.setYktCode(crdDTO.getYktcode());// 通卡公司code
        prdOrder.setCityName(validatePrdProduct.getCityName());// 城市名称（冗余）
        prdOrder.setYktName(validateYktResponse.getYktName());// 通卡公司名称（冗余）

        prdOrder.setYktRechargeRate(validateYktResponse.getYktRechargeRate());// 通卡公司一卡通充值费率（固定类型：千分比）

        prdOrder.setTxnAmt(Long.valueOf(crdDTO.getTxnamt()));// 充值金额（产品面额或圈存订单金额）
        prdOrder.setProCode(crdDTO.getProductcode());// 产品编号
        prdOrder.setProName(validatePrdProduct.getProName());// 产品名称（冗余）
        prdOrder.setBefbal(Long.valueOf(crdDTO.getBefbal()));// 充值前金额
        prdOrder.setMerUserType(merType);// 客户类型(包括个人与商户)
        if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
            prdOrder.setMerCode(userCode);// 客户号（个人时保存个人用户code）
        } else {
            prdOrder.setMerCode(crdDTO.getMercode());// 客户号（商户时保存商户code）
        }
        prdOrder.setMerName(merName);// 客户（包括个人与商户）名称
        prdOrder.setMerRate(rate);// 商户费率(个人用户而言为0)
        prdOrder.setMerRateType(rateType);// 商户费率类型

        // 非圈存订单，设置支付服务费率与服务费率类型
        if (StringUtils.isBlank(crdDTO.getLoadordernum())) {
            prdOrder.setPayServiceType(payServiceRateDTO.getRateType());// 服务费率类型
            prdOrder.setPayServiceRate(payServiceRateDTO.getRate());// 服务费率
        }

        // 判断是否圈存订单
        String loadFlag = LoadFlagEnum.LOAD_YES.getCode();
        if (StringUtils.isBlank(crdDTO.getLoadordernum())) {
            loadFlag = LoadFlagEnum.LOAD_NO.getCode();
        }
        prdOrder.setLoadOrderNum(crdDTO.getLoadordernum());// 圈存订单号
        prdOrder.setLoadFlag(loadFlag);// 圈存标识

        long rechargeAmt = validatePrdProduct.getProPrice();// 充值金额(产品面额)
        
        long productMoney = 0;// TODO 待考虑
        if (StringUtils.isBlank(crdDTO.getLoadordernum())) {
            productMoney = validatePrdProduct.getProPrice();//非圈存：取值产品面额，用于计算商户的进货价
        } else {
            productMoney = validatePrdProduct.getCustomerPayAmt();//圈存：取值圈存订单实付金额，用于计算商户的进货价
        }
        
        if (RateTypeEnum.PERMILLAGE.getCode().equals(rateType)) {

            double realRate = 1 - rate / 1000;

            // 商户费率类型为“千分比(‰)”时，追加判断费率是否大于1.
            if (realRate < 0) {
                throw new DDPException(ResponseCode.PRODUCT_CREATE_ORDER_RATE_ERROR, "当前商户费率(千分比(‰)):" + rate / 1000 + "大于1");
            }

            // 遵循金额算法相乘有小数则取整+1
            long merPurchaasePrice = MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(productMoney), String.valueOf(realRate));

            // 商户进货价(DDP出货价)
            prdOrder.setMerPurchaasePrice(merPurchaasePrice);
        } else {

            // 费率类型为“单笔返点金额(元)”时，追加判断订单产品面额（充值金额）不能小于当前费率（单笔返点金额(元)）
            long merPurchaasePrice = productMoney - (int) rate;
            if (merPurchaasePrice < 0) {
                throw new DDPException(ResponseCode.PRODUCT_CREATE_ORDER_RATE_ERROR, "当前商户费率(单笔返点金额(元):" + (int) rate + ")大于充值金额:" + validatePrdProduct.getProPrice());
            }

            // 商户进货价(DDP出货价)
            prdOrder.setMerPurchaasePrice(merPurchaasePrice);
        }
        
        long receivedPrice = prdOrder.getMerPurchaasePrice();
        
        //********************************  针对外接商户，加收取服务费  add by shenXiang 2015-11-30 start   *********************************//
        if (MerTypeEnum.EXTERNAL.getCode().equals(merType) && StringUtils.isNotBlank(payServiceRateDTO.getRateType())) {
            if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRateDTO.getRateType())) {
                
                // 服务费费率类型: 单笔（元）
                receivedPrice += (long)payServiceRateDTO.getRate();
                
            } else if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRateDTO.getRateType())) {
                
                // 服务费费率类型: 费率（千分比）(遵循金额算法相乘有小数则取整+1)
                receivedPrice += MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(productMoney), String.valueOf(payServiceRateDTO.getRate()/ 1000));
            }
        }
        //********************************  针对外接商户，加收取服务费  add by shenXiang 2015-11-30 end   *********************************//
        
        // 实付（收）金额
        prdOrder.setReceivedPrice(receivedPrice);
        
        // 商户利润---->面价 - 实付（收）金额
        prdOrder.setMerGain(rechargeAmt - prdOrder.getReceivedPrice());
    
        return prdOrder;
    }

}
