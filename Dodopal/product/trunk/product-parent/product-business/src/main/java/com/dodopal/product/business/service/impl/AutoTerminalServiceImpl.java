package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.card.dto.TerminalParameter;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.LoadAndTradeOrder;
import com.dodopal.product.business.dao.PrdProductYktMapper;
import com.dodopal.product.business.dao.ProductOrderMapper;
import com.dodopal.product.business.dao.ProductYktCityInfoMapper;
import com.dodopal.product.business.model.AutoTerminalParameterResult;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.PayAway;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.Product;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.query.AutoTerminalParameterQuery;
import com.dodopal.product.business.service.AutoTerminalService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.delegate.MerchantDelegate;
import com.dodopal.product.delegate.PayDelegate;
import com.dodopal.product.delegate.PayRefundDelegate;
import com.dodopal.product.delegate.PayTransactionDelegate;

/**
 * 自助终端用Service接口实现类
 * @author dodopal
 *
 */
@Service
public class AutoTerminalServiceImpl implements AutoTerminalService {
    
    @Autowired
    LoadOrderService loadOrderService;
    @Autowired
    PayTransactionDelegate payTransactionDelegate;
    @Autowired
    PayDelegate payDelegate;
    @Autowired
    MerchantDelegate merchantDelegate;
    @Autowired
    ProductYktCityInfoMapper productCityInfoMapper;
    @Autowired
    PrdProductYktMapper productMapper;
    @Autowired
    ProductOrderMapper productOrderMapper;
    @Autowired
    PayRefundDelegate payRefundDelegate;
    
    private final static Logger log = LoggerFactory.getLogger(AutoTerminalServiceImpl.class);

    @Override
    public DodopalResponse<LoadAndTradeOrder> getLoadOrderAndTradeOrder(String tradeNum) {
        log.info("根据交易流水号获取圈存订单流水号：[{}]",tradeNum);
        DodopalResponse<LoadAndTradeOrder> dodopalResponse = new DodopalResponse<LoadAndTradeOrder>(); 
        LoadAndTradeOrder tradeOrder = new LoadAndTradeOrder();
        DodopalResponse<PayTraTransactionDTO> tranResult = payTransactionDelegate.findTranInfoByTranCode(tradeNum);
        log.info("调用hessian查找交易流水,返回码为：[{}]",tranResult.getCode());
        if(tranResult.isSuccessCode()){
            if(null!=tranResult.getResponseEntity()){
                //1.交易流水状态满足:外部 已支付  内部 ：账户扣款成功
                if(log.isInfoEnabled()){
                    log.info("交易流水的外部状态[{}],内部状态[{}]",tranResult.getResponseEntity().getTranOutStatus(),tranResult.getResponseEntity().getTranInStatus());
                }
                if(TranOutStatusEnum.HAS_PAID.getCode().equals(tranResult.getResponseEntity().getTranOutStatus())
                    &&TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(tranResult.getResponseEntity().getTranInStatus())){
                    //此时取ORDER_NUMBER做为圈存订单号调用取圈存订单信息,如果有圈存订单返回应答码’000000’,如果没有则返回’XXXXXX’.
                    dodopalResponse.setCode(ResponseCode.SUCCESS);
                    LoadAndTradeOrder loadOrderNum = getLoadOrderNumByLoadNum(tranResult.getResponseEntity().getOrderNumber());
                    if(null!=loadOrderNum){
                        //如果没有则返回’XXXXXX’.
                        tradeOrder.setOrderNumber(loadOrderNum.getOrderNumber());
                        tradeOrder.setAmont(loadOrderNum.getAmont());
                    }else{
                        dodopalResponse.setCode(ResponseCode.PRODUCT_YYYYYY);
                    }
                    if(log.isInfoEnabled()){
                        log.info("交易流水状态满足:外部 已支付  内部 ：账户扣款成功，返回错误码为[{}]：",dodopalResponse.getCode());
                    }
                    dodopalResponse.setResponseEntity(tradeOrder);
                    
                }else if(TranOutStatusEnum.HAS_PAID.getCode().equals(tranResult.getResponseEntity().getTranOutStatus())
                    &&TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(tranResult.getResponseEntity().getTranInStatus())){
                    //2..交易流水状态满足:外部 已支付  内部 ：非账户价值失败
                    //可以退款:XXXXXX
                    if(log.isInfoEnabled()){
                        log.info("交易流水状态满足:交易流水状态满足:外部 已支付  内部 ：非账户价值失败,可以退款:XXXXXX");
                    }
                    dodopalResponse.setCode(ResponseCode.PRODUCT_XXXXXX);
                }else if(!TranOutStatusEnum.HAS_PAID.getCode().equals(tranResult.getResponseEntity().getTranOutStatus())){
                    //失败,不可以退款,需终端机执行循环调用:
                    if(log.isInfoEnabled()){
                        log.info("失败,不可以退款,需终端机执行循环调用:");
                    }
                    dodopalResponse.setCode(ResponseCode.PRODUCT_YYYYYY);
                }
                tradeOrder.setTradeNum(tradeNum);
                tradeOrder.setPayid(tranResult.getResponseEntity().getPayWay());
                tradeOrder.setPaytype(tranResult.getResponseEntity().getPayType());
            } 
        }else{
            dodopalResponse.setCode(tranResult.getCode());
        }
        return dodopalResponse;
    }

    @Override
    public LoadAndTradeOrder getLoadOrderNumByLoadNum(String orderNumber) {
        LoadAndTradeOrder orderResult = new LoadAndTradeOrder();
        LoadOrder load = loadOrderService.getLoadOrderByLoadOrderNum(orderNumber);
        if(null!=load){
            if(load.getStatus().equals(LoadOrderStatusEnum.ORDER_SUCCESS.getCode())||
                load.getStatus().equals(LoadOrderStatusEnum.ORDER_FAILURE.getCode())){
                orderResult.setOrderNumber(load.getOrderNum());
                orderResult.setAmont(load.getLoadAmt()); 
                return orderResult;
            }
        }
        
        //load.getOrderNum();
//        PayTraTransactionQueryDTO queryDTO = new PayTraTransactionQueryDTO();
//        queryDTO.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
//        queryDTO.setOrderNumber(orderNumber);
//        DodopalResponse<List<PayTraTransactionDTO>> payTranResult = payTransactionDelegate.findPayTraTransactionList(queryDTO);
//        if(payTranResult.isSuccessCode()){
//            if(CollectionUtils.isNotEmpty(payTranResult.getResponseEntity())){
//                orderResult.setPayid(payTranResult.getResponseEntity().get(0).getPayWay());
//                orderResult.setTradeNum(payTranResult.getResponseEntity().get(0).getTranCode());
//            }
//        }else{
//            throw new DDPException(payTranResult.getCode());
//        }    
        return null;
    }

    
    /**
     * 终端参数下载
     */
    @Override
    public DodopalResponse<AutoTerminalParameterResult> parameterDownload(AutoTerminalParameterQuery queryDto) {
        
        DodopalResponse<AutoTerminalParameterResult> resultResponse = new DodopalResponse<AutoTerminalParameterResult>();
        AutoTerminalParameterResult parameterResult = new AutoTerminalParameterResult();
        resultResponse.setCode(ResponseCode.SUCCESS);
        resultResponse.setResponseEntity(parameterResult);
         
        // 获取商户号
        DodopalResponse<Map<String,String>> merResponse = null;
        try {
            // 产品库透传参数给卡服务进行验卡接口
            merResponse = merchantDelegate.getMerchantByPosCode(queryDto.getPosid());
        }
        catch (HessianRuntimeException e) {
            resultResponse.setCode(ResponseCode.PRODUCT_CALL_USERS_ERROR);
            return resultResponse;
        }
        if (!ResponseCode.SUCCESS.equals(merResponse.getCode())) {
            resultResponse.setCode(merResponse.getCode());
            return resultResponse;
        }
        Map<String,String> merInfoMap = merResponse.getResponseEntity();
        
        String merCode = merInfoMap.containsKey("merCode") ? merInfoMap.get("merCode") : "";
        String merType = merInfoMap.containsKey("merType") ? merInfoMap.get("merType") : "";
        parameterResult.setMercode(merCode);
        
        boolean extflg = false;
        if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
            extflg = true;
        }
        
        // 获取支付方式参数(支付方式类型：在线支付)
        DodopalResponse<List<PayWayDTO>> payWayDTOResponse = null;
        try {
            // 产品库透传参数给卡服务进行验卡接口
            payWayDTOResponse = payDelegate.findPayWay(extflg, merCode, PayTypeEnum.PAY_ONLINE.getCode());
        }
        catch (HessianRuntimeException e) {
            resultResponse.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
            return resultResponse;
        }
        if (!ResponseCode.SUCCESS.equals(payWayDTOResponse.getCode())) {
            resultResponse.setCode(payWayDTOResponse.getCode());
            return resultResponse;
        }
        List<PayWayDTO> PayWayDtoList = payWayDTOResponse.getResponseEntity();
        List<PayAway> listpayaway = new ArrayList<PayAway>();
        if (PayWayDtoList != null) {
            for (PayWayDTO payWayDTO : PayWayDtoList) {
                PayAway payAway = new PayAway();
                payAway.setPayid(payWayDTO.getId());
                payAway.setPayname(payWayDTO.getPayName());
                payAway.setPayprocerate(payWayDTO.getProceRate());
                payAway.setPayservicerate(payWayDTO.getPayServiceRate());
                payAway.setBankgatewaytype(payWayDTO.getBankGatewayType());
                listpayaway.add(payAway);
            }
            parameterResult.setListpayaway(listpayaway);
        }
        
        // 获取产品参数
        // 如果入参的版本号和服务端一样，此时返回参数不会有产品列表；如果送上来的版本号和服务端不一样，此时返回参数会有产品列表
        String proversion = productCityInfoMapper.getProversionByCityCode(queryDto.getCitycode());
        parameterResult.setProversion(proversion);
        if (!queryDto.getProversion().equals(proversion)) {
            List<PrdProductYkt> listproductykt = productMapper.getAvailableProductsByCity(queryDto.getCitycode());
            
            if (listproductykt != null && listproductykt.size() > 0) {
                List<Product> listproduct = new ArrayList<Product>();
                for (PrdProductYkt prdProductYkt: listproductykt) {
                    Product product = new Product();
                    product.setCitycode(prdProductYkt.getCityId());
                    product.setProcode(prdProductYkt.getProCode());
                    product.setProname(prdProductYkt.getProName());
                    product.setProprice(prdProductYkt.getProPrice());
                    listproduct.add(product);
                }
                parameterResult.setListproduct(listproduct);
            }
        }
        
        return resultResponse;
    }

    /**
     * 退款接口
     * @param tranCode 交易流水号
     * @param source   来源
     * @param userid   用户ID
     * @return
     */
    public DodopalResponse<String> sendRefund(String tranCode,String source,String userid){
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            // 根据交易流水号:取圈存订单信息
            DodopalResponse<PayTraTransactionDTO> tranInfoResponse = payTransactionDelegate.findTranInfoByTranCode(tranCode);
            if (!ResponseCode.SUCCESS.equals(tranInfoResponse.getCode()) || null == tranInfoResponse.getResponseEntity()) {

                log.error("自助终端退款接口：查询交易流水信息为空，入参交易流水号：" + tranCode);
                response.setCode(ResponseCode.PRODUCT_TRANINFO_NULL);// 交易流水信息不存在
                return response;
            }
            String loadOrderNum = tranInfoResponse.getResponseEntity().getOrderNumber();
            if (StringUtils.isBlank(loadOrderNum)) {

                log.error("自助终端退款接口：交易流水信息中圈存订单号为空，入参交易流水号：" + tranCode);
                response.setCode(ResponseCode.PRODUCT_LOADORDERNUM_NULL);// 圈存订单号不存在
                return response;
            }
            LoadOrder loadOrder = loadOrderService.getLoadOrderByLoadOrderNum(loadOrderNum);
            if (null == loadOrder) {

                log.error("自助终端退款接口：交易流水信息中圈存订单号为空，入参交易流水号：" + tranCode);
                response.setCode(ResponseCode.PRODUCT_LOADORDER_NULL);// 圈存订单不存在
                return response;
            }

            // 判断圈存订单状态为“已退款”，则无需继续执行。
            if (LoadOrderStatusEnum.REFUNDMENT.getCode().equals(loadOrder.getStatus())) {
                log.info("自助终端退款接口：圈存订单状态为已退款吧，圈存订单号：" + loadOrderNum + ",入参交易流水号：" + tranCode);
                response.setCode(ResponseCode.SUCCESS);
                return response;
            }
            // 判断圈存订单状态（0：已支付，2：充值失败，5：退款中），可作退款（修改圈存状态为：“退款中”）
            if (!(LoadOrderStatusEnum.ORDER_SUCCESS.getCode().equals(loadOrder.getStatus()) 
                || LoadOrderStatusEnum.ORDER_FAILURE.getCode().equals(loadOrder.getStatus()) 
                || LoadOrderStatusEnum.REFUNDMENT_ING.getCode().equals(loadOrder.getStatus()))) {

                log.error("自助终端退款接口：圈存订单状态不正确，圈存订单号：" + loadOrderNum + ",入参交易流水号：" + tranCode);
                response.setCode(ResponseCode.PRODUCT_LOADORDER_STATE_ERROR);// 圈存订单状态不正确
                return response;
            }

            // 根据圈存订单号取：创建时间最新的产品库订单
            List<ProductOrder> orderList = productOrderMapper.getOrderListByLoadOrderNum(loadOrderNum);
            if (orderList != null && orderList.size() > 0) {
                ProductOrder productOrder = orderList.get(0);

                // 若产品库订单状态为：非（3：充值中,4：充值成功,5：充值可疑）时：可退款
                if (RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState()) 
                    || RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState()) 
                    || RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())) {

                    log.error("自助终端退款接口：充值订单状态不正确，产品库订单号："+productOrder.getProOrderNum()+",圈存订单号：" + loadOrderNum + ",入参交易流水号：" + tranCode);
                    response.setCode(ResponseCode.PRODUCT_ORDER_STATE_ERROR);// 充值订单状态不正确
                    return response;
                }
            }

            // 修改圈存状态为：“退款中”
            loadOrderService.updateLoadOrderStatus(LoadOrderStatusEnum.REFUNDMENT_ING.getCode(), loadOrderNum, userid);
            
            // 调用支付交易，发送退款请求
            response = payRefundDelegate.sendRefund(tranCode, source, userid);

            // 退款成功：修改圈存订单状态：“已退款”
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                loadOrderService.updateLoadOrderStatus(LoadOrderStatusEnum.REFUNDMENT.getCode(), loadOrderNum, userid);
            }
            // 退款失败：暂不作处理（保留状态为“退款中”）

        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
        }
        return response;
    }
    
    @Override
    public TerminalParameter findTerminalParameter(String psamno) {
    	List<TerminalParameter> list = new ArrayList<TerminalParameter>();
         list = productMapper.findTerminalParameter(psamno);
        if(null!=list&&list.size()>0){            
            return list.get(0);
        }
        else{
        	return new TerminalParameter();
        }
    }
}
