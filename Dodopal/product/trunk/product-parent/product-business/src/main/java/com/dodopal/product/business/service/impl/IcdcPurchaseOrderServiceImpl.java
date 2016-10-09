package com.dodopal.product.business.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.product.dto.IcdcPurchaseDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearingMarkEnum;
import com.dodopal.common.enums.FundProcessResultEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.PurchaseOrderRedordStatesEnum;
import com.dodopal.common.enums.PurchaseOrderResultEnum;
import com.dodopal.common.enums.PurchaseOrderStatesEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.MoneyAlgorithmUtils;
import com.dodopal.product.business.bean.ConsumeOrder;
import com.dodopal.product.business.dao.ProductPurchaseOrderMapper;
import com.dodopal.product.business.dao.ProductPurchaseOrderRecordMapper;
import com.dodopal.product.business.model.ProductPurchaseOrder;
import com.dodopal.product.business.model.ProductPurchaseOrderRecord;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.IcdcPurchaseOrderService;
import com.dodopal.product.business.service.ProductYKTService;

/**
 * 产品库公交卡消费订单ServiceImpl
 * @author dodopal
 */
@Service
public class IcdcPurchaseOrderServiceImpl implements IcdcPurchaseOrderService {

    private Logger logger = LoggerFactory.getLogger(IcdcPurchaseOrderServiceImpl.class);

    @Autowired
    private AreaService areaService;
    @Autowired
    private ProductYKTService productYKTService;
    @Autowired
    private ProductPurchaseOrderMapper productPurchaseOrderMapper;
    @Autowired
    private ProductPurchaseOrderRecordMapper productPurchaseOrderRecordMapper;

    /**
     * 产品库公交卡消费主订单编号生成规则C + 20150428222211 +五位数据库cycle sequence（循环使用）
     */
    @Transactional(readOnly = true)
    private String getProductPurchaseOrderNum() {
        String prdOrderNum = "C";
        String timeStr = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        prdOrderNum += timeStr;
        return prdOrderNum + productPurchaseOrderMapper.getPrdPurchaseOrderNumSeq();
    }

    /**
     * 创建产品库收单记录
     */
    @Override
    @Transactional
    public DodopalResponse<String> bookIcdcPurchaseOrder(IcdcPurchaseDTO purchaseDto, Map<String, String> merInfoMap, ProductYKT productYKT) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        // *************************************      参数验证    START      ***********************************//
        // 应收金额
        long originalPrice;
        if (StringUtils.isBlank(purchaseDto.getReceivableAmt())) {
            logger.error("创建产品库收单记录，参数不合法：应收金额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        } else {
            try {
                originalPrice = Long.parseLong(purchaseDto.getReceivableAmt());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：应收金额:" + purchaseDto.getReceivableAmt());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 实收金额
        long receivedPrice;
        if (StringUtils.isBlank(purchaseDto.getReceivedAmt())) {
            logger.error("创建产品库收单记录，参数不合法：实收金额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        } else {
            try {
                receivedPrice = Long.parseLong(purchaseDto.getReceivedAmt());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：实收金额:" + purchaseDto.getReceivedAmt());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 商户折扣
        double merDiscount = 0;
        if (!StringUtils.isBlank(purchaseDto.getMerdiscount())) {
            try {
                merDiscount = Double.parseDouble(purchaseDto.getMerdiscount());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：商户折扣:" + purchaseDto.getMerdiscount());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 来源
        if (!SourceEnum.checkCodeExist(purchaseDto.getSource())) {
            logger.error("创建产品库收单记录，参数不合法：来源:" + purchaseDto.getSource());
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 操作人
        if (StringUtils.isBlank(purchaseDto.getUserid())) {
            logger.error("创建产品库收单记录，参数不合法：操作人为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 城市Code
        if (StringUtils.isBlank(purchaseDto.getCitycode())) {
            logger.error("创建产品库收单记录，参数不合法：城市编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 根据城市Code取城市名称
        Area area = areaService.findCityByCityCode(purchaseDto.getCitycode());
        if (area == null) {
            logger.error("创建产品库收单记录，参数不合法：数据库中没有该城市编号:" + purchaseDto.getCitycode());
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 客户编号
        if (StringUtils.isBlank(purchaseDto.getMercode())) {
            logger.error("创建产品库收单记录，参数不合法：客户编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 客户类型
        String customerType = merInfoMap.get("merType");
        // 客户名称
        String customerName = merInfoMap.get("merName");
        // 服务费费率类型
        String merServiceRateType = merInfoMap.get("rateType");
        // 服务费费率
        double merServiceRate = Double.parseDouble(merInfoMap.get("rate"));

        // 客户类型
        if (StringUtils.isBlank(customerType)) {
            logger.error("创建产品库收单记录，参数不合法：客户类型为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        
        // 客户名称
        if (StringUtils.isBlank(customerName)) {
            logger.error("创建产品库收单记录，参数不合法：客户名称为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 商户费率类型
        if (!RateTypeEnum.checkCodeExist(merServiceRateType)) {
            logger.error("创建产品库收单记录，参数不合法：服务费费率类型：" + merServiceRateType);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // **************************************      参数验证    END      *************************************//

        // 创建消费主订单
        ProductPurchaseOrder purchaseOrder = new ProductPurchaseOrder();
        purchaseOrder.setOrderNum(getProductPurchaseOrderNum());// 订单编号
        purchaseOrder.setOriginalPrice(originalPrice);// 应收金额-----------------------------------------------------------------------画面传值
        purchaseOrder.setReceivedPrice(receivedPrice);// 实收金额-----------------------------------------------------------------------画面传值
        purchaseOrder.setSource(purchaseDto.getSource());// 来源------------------------------------------------------------------------画面传值
        purchaseOrder.setUserId(purchaseDto.getUserid());// 操作人----------------------------------------------------------------------画面传值
        purchaseOrder.setCreateUser(purchaseDto.getUserid());// 创建人------------------------------------------------------------------画面传值
        purchaseOrder.setCustomerCode(purchaseDto.getMercode());// 客户编号-------------------------------------------------------------画面传值
        purchaseOrder.setCustomerType(customerType);// 客户类型----------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setCustomerName(customerName);// 客户名称----------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setServiceRateType(merServiceRateType);// 服务费率类型---------------------------------------------------商户合法性验证取得
        purchaseOrder.setServiceRate(merServiceRate);// 服务费率---------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setBusinessType(RateCodeEnum.YKT_PAYMENT.getCode());// 业务类型---------------------------------------固定值：03:一卡通消费
        //purchaseOrder.setMerRateType("");// 商户费率类型-----------------------------------------------------------------------------固定值：空
        //purchaseOrder.setMerRate(null);// 商户费率-----------------------------------------------------------------------------------固定值：空
        purchaseOrder.setMerGain(null);// 商户利润-------------------------------------------------------------------------------------固定值：空
        purchaseOrder.setPayGateway(productYKT.getYktCode());// 支付网关---------------------------------------------------------固定值：通卡code
        purchaseOrder.setPayType(PayTypeEnum.PAY_CARD.getCode());// 支付类型-------------------------------------------------固定值：1：一卡通支付
        purchaseOrder.setPayWay(purchaseDto.getCitycode());// 支付方式-----------------------------------------------------------固定值：城市code
        purchaseOrder.setStates(PurchaseOrderStatesEnum.PAID_WAIT.getCode());// 状态---------------------------------------------初始值：0:待支付
        //purchaseOrder.setOrderDate(new Date);// 订单时间-----------------------------------------------------------------------------数据库时间
        //purchaseOrder.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));// 订单日期------------------数据库日期
        purchaseOrder.setClearingMark(ClearingMarkEnum.CLEARING_NO.getCode());// 清算标识-----------------------------------------初始值0：未清算
        purchaseOrder.setFundProcessResult(FundProcessResultEnum.UNDONE.getCode());// 资金处理结果--------------------------------初始值0：未处理
        //purchaseOrder.setComments("");// 备注---------------------------------------------------------------------------------------初始值：空
        productPurchaseOrderMapper.addProductPurchaseOrder(purchaseOrder);

        // 创建收单记录
        ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
        orderRecord.setOrderNum(purchaseOrder.getOrderNum());// 订单编号--------------------------------------------------------关联消费主订单编号
        orderRecord.setCreateUser(purchaseDto.getUserid());// 创建人---------------------------------------------------------------------画面传值
        orderRecord.setCityCode(purchaseDto.getCitycode());// 城市CODE-------------------------------------------------------------------画面传值
        if (!StringUtils.isBlank(purchaseDto.getMerdiscount())) {
            orderRecord.setMerDiscount(merDiscount);// 商户折扣---------------------------------------------------------------------------画面传值
        } else {
            orderRecord.setMerDiscount(null);// 商户折扣----------------------------------------------------------------------------------画面传值
        }
        orderRecord.setCityName(area.getCityName());// 城市名称---------------------------------------------------------------由城市code取城市名称
        orderRecord.setYtkCode(productYKT.getYktCode());// 通卡公司CODE------------------------------------------------------------------通卡code
        orderRecord.setYtkName(productYKT.getYktName());// 通卡公司名称------------------------------------------------------------------通卡名称
        orderRecord.setYktPayRate(productYKT.getYktPayRate());// 通卡公司一卡通支付费率-----------------------------------------------通卡支付费率
        orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.ORDER_CREATION_SUCCESS.getCode());// 内部状态-------------初始值：00:订单创建成功
        //orderRecord.setBeforInnerStates("");// 前内部状态----------------------------------------------------------------------------初始值：空
        //orderRecord.setCardNum("");// 卡号-------------------------------------------------------------------------------------------初始值：空
        //orderRecord.setPosCode("");// POS号------------------------------------------------------------------------------------------初始值：空
        //orderRecord.setBefbal(0);// 支付前卡内金额------------------------------------------------------------------------------------初始值：空
        //orderRecord.setBlackAmt(0);// 支付后卡内金额----------------------------------------------------------------------------------初始值：空
        //orderRecord.setOrderDate("");// 订单时间-------------------------------------------------------------------------------------数据库时间
        //orderRecord.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));// 订单日期--------------------数据库日期
        orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑状态---------------------------------初始值：0:不可疑
        //orderRecord.setSuspiciousExplain("");// 可疑处理说明--------------------------------------------------------------------------初始值：空
        productPurchaseOrderRecordMapper.addProductPurchaseOrderRecord(orderRecord);

        response.setResponseEntity(purchaseOrder.getOrderNum());
        return response;
    }

    /**
     * 申请扣款初始化指令过程中验证收单记录
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<ConsumeOrder> validateIcdcPurchaseOrderWhenCheckCard(String purchaseOrderNum) {
        DodopalResponse<ConsumeOrder> response = new DodopalResponse<ConsumeOrder>();
        response.setCode(ResponseCode.SUCCESS);

        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("申请扣款初始化指令过程中验证收单记录，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder order = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord orderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        response.setResponseEntity(this.getConsumeOrderInfo(order, orderRecord));
        
        // 1.   根据订单号判断该订单是否存在。
        if (order == null || orderRecord == null) {
            logger.error("申请扣款初始化指令过程中,验证收单记录，失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }

        // 2.   该订单的状态应该为待支付--订单创建成功。
        if (!PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(order.getStates()) || !PurchaseOrderRedordStatesEnum.ORDER_CREATION_SUCCESS.getCode().equals(orderRecord.getInnerStates())) {
            logger.error("申请扣款初始化指令过程中,验证收单记录，失败：订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        return response;
    }

    /**
     * 6.3.2.2 申请扣款初始化指令过程更新产品库订单数据
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateIcdcPurchaseOrderWhenCheckCard(String purchaseOrderNum, String cardNum, String cardFaceno, String posId, int balanceBeforePurchase, String cardCheckResult) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(0);
        int updateNum = 0;

        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("申请扣款初始化指令过程中验证收单记录，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }
        if (StringUtils.isBlank(cardNum)) {
            logger.error("申请扣款初始化指令过程中验证收单记录，参数不合法：卡号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }
        if (StringUtils.isBlank(posId)) {
            logger.error("申请扣款初始化指令过程中验证收单记录，参数不合法：POS编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }
        if (balanceBeforePurchase < 0) {
            logger.error("申请扣款初始化指令过程中验证收单记录，参数不合法：支付前卡内金额:" + balanceBeforePurchase);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }
        if (StringUtils.isBlank(cardCheckResult)) {
            logger.error("申请扣款初始化指令过程中验证收单记录，参数不合法：申请扣款初始化指令响应结果为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder oldOrder = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord oldOrderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        // 1.   根据订单号判断该订单是否存在
        if (oldOrder == null || oldOrderRecord == null) {
            logger.error("申请扣款初始化指令过程中,更新产品库订单数据，失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        // 2.   该订单的状态应该为待支付--订单创建成功。
        if (!PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(oldOrder.getStates()) || !PurchaseOrderRedordStatesEnum.ORDER_CREATION_SUCCESS.getCode().equals(oldOrderRecord.getInnerStates())) {
            logger.error("申请扣款初始化指令过程中,更新产品库订单数据，失败：订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        // 3.    追加判断，收单前卡内金额需要大于应收金额
        if (oldOrder.getReceivedPrice() > balanceBeforePurchase) {
            logger.error("申请扣款初始化指令过程中,更新产品库订单数据，失败：卡内余额不足：收单前卡内金额("+balanceBeforePurchase+");实收金额("+oldOrder.getReceivedPrice()+")；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }

        if (ResponseCode.SUCCESS.equals(cardCheckResult)) {
            // *******************如果是000000，则将订单状态设置为：待支付—验卡成功；
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setCardNum(cardNum);
            orderRecord.setCardFaceno(cardFaceno);
            orderRecord.setPosCode(posId);
            orderRecord.setBefbal((long)balanceBeforePurchase);
            orderRecord.setBlackAmt(null);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.CARD_CHECK_SUCCESS.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            updateNum = productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenCheckCard(orderRecord);
        } else {
            // *******************如果不是000000，则将订单状态设置为：支付失败—验卡失败。
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_FAILURE.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            updateNum = productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setCardNum(cardNum);
            orderRecord.setCardFaceno(cardFaceno);
            orderRecord.setPosCode(posId);
            orderRecord.setBefbal((long)balanceBeforePurchase);
            orderRecord.setBlackAmt((long)balanceBeforePurchase);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.CARD_CHECK_FAILURE.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            updateNum += productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenCheckCard(orderRecord);
        }
        response.setResponseEntity(updateNum);
        return response;
    }

    /**
     * 6.3.3.1 申请扣款指令过程中验证收单记录
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<ConsumeOrder> validateIcdcPurchaseOrderWhenDeductCard(String purchaseOrderNum) {
        DodopalResponse<ConsumeOrder> response = new DodopalResponse<ConsumeOrder>();
        response.setCode(ResponseCode.SUCCESS);

        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("申请扣款指令过程中,验证收单记录，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder order = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord orderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        // 1.   根据订单号判断该订单是否存在。
        if (order == null || orderRecord == null) {
            logger.error("申请扣款指令过程中,验证收单记录，失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        
        // 2.   该订单的状态应该为待支付—验卡成功 、待支付-申请消费密钥成功、待支付-申请消费密钥失败。
        if(PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(order.getStates())){
            //主订单状态-待支付
            if(PurchaseOrderRedordStatesEnum.CARD_CHECK_SUCCESS.getCode().equals(orderRecord.getInnerStates()) || PurchaseOrderRedordStatesEnum.KEY_APPLY_SUCCESS.getCode().equals(orderRecord.getInnerStates()) || PurchaseOrderRedordStatesEnum.KEY_APPLY_FAILURE.getCode().equals(orderRecord.getInnerStates())){
                //子订单状态-验卡成功 、申请消费密钥成功、申请消费密钥失败
            }else{
                logger.error("申请扣款指令过程中,验证收单记录，失败：子订单状态不正确；主订单编号：" + purchaseOrderNum);
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
                return response;
            }
        }else{
            logger.error("申请扣款指令过程中,验证收单记录，失败：主订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        
        response.setResponseEntity(this.getConsumeOrderInfo(order, orderRecord));
        
        return response;
    }

    /**
     * 6.3.3.2 申请扣款指令过程更新产品库订单数据
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateIcdcPurchaseOrderWhenDeductCard(String purchaseOrderNum, String cardCheckResult) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(0);
        int updateNum = 0;

        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("申请扣款指令过程中,更新产品库订单数据，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        if (StringUtils.isBlank(cardCheckResult)) {
            logger.error("申请扣款指令过程中,更新产品库订单数据，参数不合法：申请扣款指令响应结果为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder oldOrder = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord oldOrderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        // 1.   根据订单号判断该订单是否存在。
        if (oldOrder == null || oldOrderRecord == null) {
            logger.error("申请扣款指令过程中,更新产品库订单数据，失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        // 2.   该订单的状态应该为待支付—验卡成功。
        if (!PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(oldOrder.getStates()) || !PurchaseOrderRedordStatesEnum.CARD_CHECK_SUCCESS.getCode().equals(oldOrderRecord.getInnerStates())) {
            logger.error("申请扣款指令过程中,更新产品库订单数据，失败：订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }

        // 如果是000000，则将订单状态设置为：待支付—申请消费密钥成功；如果不是000000，则将订单状态设置为：支付失败—申请消费密钥失败。
        if (ResponseCode.SUCCESS.equals(cardCheckResult)) {
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.KEY_APPLY_SUCCESS.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            orderRecord.setBlackAmt(null);
            updateNum = productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenDeductCard(orderRecord);
        } else {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_FAILURE.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            updateNum = productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.KEY_APPLY_FAILURE.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            orderRecord.setBlackAmt(oldOrderRecord.getBefbal());
            updateNum += productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenDeductCard(orderRecord);
        }
        response.setResponseEntity(updateNum);
        return response;
    }

    /**
     * 6.3.4.1 上传收单结果
     */
    @Override
    @Transactional
    public DodopalResponse<ConsumeOrder> validateIcdcPurchaseOrderWhenUploadResult(String purchaseOrderNum, String purchaseResult, String blackAmt) {
        DodopalResponse<ConsumeOrder> response = new DodopalResponse<ConsumeOrder>();
        response.setCode(ResponseCode.SUCCESS);
        
        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("上传收单结果过程，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        if (!PurchaseOrderResultEnum.checkCodeExist(purchaseResult)) {
            logger.error("上传收单结果过程，参数不合法：上传收单结果（0：成功；1：失败；2：未知）：" + purchaseResult);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        long blackMoney = 0;
        if (StringUtils.isNotBlank(blackAmt)) {
            try {
                blackMoney = Long.parseLong(blackAmt);
            }
            catch (Exception e) {
                logger.error("上传收单结果过程，参数不合法：收单后卡内余额：" + blackAmt);
                response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
                return response;
            }
        } else if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(purchaseResult) 
            || PurchaseOrderResultEnum.FAILURE.getCode().equals(purchaseResult)) {
            logger.error("上传收单结果过程，参数不合法：上传收单结果(0:成功；1:失败)"+purchaseResult+",但参数:收单后卡内余额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder oldOrder = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord oldOrderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        response.setResponseEntity(this.getConsumeOrderInfo(oldOrder, oldOrderRecord));
        
        // 1.   根据订单号判断该订单是否存在。
        if (oldOrder == null || oldOrderRecord == null) {
            logger.error("上传收单结果过程中,失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        // **********************************  判断DLL是否请求重发  START  ****************************************//

        // 如果产品库订单是最终状态，即为状态为：1：充值失败；2：充值成功；3：充值可疑
        // 以上情况，视为产品库上传结果成功，消费流程结束，响应dll参数code为“000000”。
        if (PurchaseOrderStatesEnum.PAID_FAILURE.getCode().equals(oldOrder.getStates()) 
            || PurchaseOrderStatesEnum.PAID_SUCCESS.getCode().equals(oldOrder.getStates()) 
            || PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode().equals(oldOrder.getStates())) {
            
            logger.info("上传收单结果,DLL重发请求,产品库成功响应。主订单编号:" + purchaseOrderNum);
            response.setCode(ResponseCode.SUCCESS);
            
            // 判断： 结果上传交易状态(0:成功 1:失败 2:未知) 与DBs数据表中订单主状态是否一致
            if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(purchaseResult) && !PurchaseOrderStatesEnum.PAID_SUCCESS.getCode().equals(oldOrder.getStates())
                || PurchaseOrderResultEnum.FAILURE.getCode().equals(purchaseResult) && !PurchaseOrderStatesEnum.PAID_FAILURE.getCode().equals(oldOrder.getStates())
                || PurchaseOrderResultEnum.SUSPICIOUS.getCode().equals(purchaseResult) && !PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode().equals(oldOrder.getStates())) {
                
                logger.info("icdcRechargeCard:上传收单结果,DLL重发请求,产品库响应可疑。DLL上传参数：结果上传交易状态(0:成功 1:失败 2:未知)："+purchaseResult+"；订单表主状态：1：充值失败；2：充值成功；3：充值可疑"+oldOrder.getStates()+"；订单编号:" + purchaseOrderNum);
                response.setCode(ResponseCode.PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT);
            }
            return response;
        }

        // ***********************************  判断DLL是否请求重发 END  *****************************************//
        
        // 2.   该订单的状态应该为待支付—申请扣款密钥成功。
        if (!PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(oldOrder.getStates()) || !PurchaseOrderRedordStatesEnum.KEY_APPLY_SUCCESS.getCode().equals(oldOrderRecord.getInnerStates())) {
            logger.error("上传收单结果过程中,失败：订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }

        // a)  如果扣款结果为“扣款成功”，则将订单状态改为：支付成功--扣款成功；
        // b)  如果扣款结果为“扣款失败”，则将订单状态改为：支付失败--扣款失败；
        // c)  如果扣款结果为“扣款未知”，则将订单状态改为：支付中--扣款未知，此种情况即为“可疑”。
        if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(purchaseResult)) {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_SUCCESS.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUCCESS.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());
            orderRecord.setBlackAmt(blackMoney);
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        } else if (PurchaseOrderResultEnum.FAILURE.getCode().equals(purchaseResult)) {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_FAILURE.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_FAILURE.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());
            orderRecord.setBlackAmt(blackMoney);
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        } else if (PurchaseOrderResultEnum.SUSPICIOUS.getCode().equals(purchaseResult)) {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUSPICIOUS.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_UNHANDLE.getCode());
            orderRecord.setBlackAmt(null);
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        }
        
        return response;
    }
    
    /**
     * 第一次进入申请扣款指令环节将“重发标志位”：置为1（产品库接收到DLL请求时，以此判断是否为重发请求） add by shenXiang 2015-11-10
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateResendSignByOrderNum(String purchaseOrderNum) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(0);
        int num = productPurchaseOrderRecordMapper.updateResendSignByOrderNum(purchaseOrderNum);
        if (num == 0) {
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }
    
    /**
     * 获取产品库消费（收单）信息
     * @param order 产品库收单主订单表信息
     * @param orderRecord 产品库收单子订单（记录）表信息
     * @return
     */
    private ConsumeOrder getConsumeOrderInfo(ProductPurchaseOrder order, ProductPurchaseOrderRecord orderRecord){
        ConsumeOrder consumeOrder = new ConsumeOrder();
        consumeOrder.setOrderNum(order.getOrderNum());// 订单编号
        consumeOrder.setOriginalPrice(order.getOriginalPrice());// 应收金额:(原本价格：单位：分)
        consumeOrder.setReceivedPrice(order.getReceivedPrice());// 实收金额:(实际收款价格：单位：分)
        consumeOrder.setCustomerCode(order.getCustomerCode());// 客户号:(个人编号或者商户号)
        consumeOrder.setCustomerName(order.getCustomerName());// 客户名称:(个人：个人名称；商户：商户名称)
        consumeOrder.setCustomerType(order.getCustomerType());// 客户类型
        consumeOrder.setBusinessType(order.getBusinessType());// 业务类型
        consumeOrder.setMerRateType(order.getMerRateType());// 商户费率类型
        consumeOrder.setMerRate(order.getMerRate());// 商户费率
        consumeOrder.setServiceRateType(order.getServiceRateType());// 服务费率类型
        consumeOrder.setServiceRate(order.getServiceRate());// 服务费率
        consumeOrder.setMerGain(order.getMerGain());// 商户利润
        consumeOrder.setOrderDate(order.getOrderDate());// 订单时间:式yyyy/MM/dd HH:MM:SS,实际为订单的创建时间。
        consumeOrder.setOrderDay(order.getOrderDay());// 订单日期:格式yyyyMMdd,
        consumeOrder.setPayGateway(order.getPayGateway());// 支付网关:一卡通收单时保存通卡code
        consumeOrder.setPayType(order.getPayType());// 支付类型:0:账户支付;1:一卡通支付;2:在线支付;3:银行支付
        consumeOrder.setPayWay(order.getPayWay());// 支付方式：一卡通收单时保存城市code
        consumeOrder.setStates(order.getStates());// 订单状态(展示外部状态)
        consumeOrder.setSource(order.getSource());// 来源
        consumeOrder.setClearingMark(order.getClearingMark());// 清算标志
        consumeOrder.setUserId(order.getUserId());// 操作员:用户的数据库IdD。
        consumeOrder.setComments(order.getComments());// 备注
        consumeOrder.setFundProcessResult(order.getFundProcessResult());// 资金处理结果:0:没有处理（默认值）；1：处理完毕
        consumeOrder.setCityCode(orderRecord.getCityCode());// 城市CODE
        consumeOrder.setYktPayRate(orderRecord.getYktPayRate());// 通卡公司一卡通支付费率（固定类型：千分比）
        consumeOrder.setMerDiscount(orderRecord.getMerDiscount());// 商户折扣
        consumeOrder.setCityName(orderRecord.getCityName());// 城市名称
        consumeOrder.setYtkName(orderRecord.getYtkName());// 通卡公司名称
        consumeOrder.setYtkCode(orderRecord.getYtkCode());// 通卡公司CODE
        consumeOrder.setCardNum(orderRecord.getCardNum());// 卡内号
        consumeOrder.setCardFaceno(orderRecord.getCardFaceno());// 卡面号
        consumeOrder.setPosCode(orderRecord.getPosCode());// POS号
        consumeOrder.setBefbal(orderRecord.getBefbal());// 支付前金额(在支付之前的公交卡内的余额。)
        consumeOrder.setBlackAmt(orderRecord.getBlackAmt());// 支付后金额(在支付之后的公交卡内的余额。)
        consumeOrder.setInnerStates(orderRecord.getInnerStates());// // 内部状态
        consumeOrder.setBeforInnerStates(orderRecord.getBeforInnerStates());// 前内部状态
        consumeOrder.setSuspiciousStates(orderRecord.getSuspiciousStates());// 可疑处理状态(0:不可疑:；1:可疑；)
        consumeOrder.setSuspiciousExplain(orderRecord.getSuspiciousExplain());// 可疑处理说明(OSS用户处理可疑订单的时候输入。)
        consumeOrder.setResendSign(orderRecord.getResendSign());// 重发标志位
        return consumeOrder;
    }
    
    /**
     * 北京V61消费上传结果
     */
    @Override
    public DodopalResponse<ConsumeOrder> validateBJOrderWhenUploadResultByV61(String purchaseOrderNum, String txnstat, String blackAmt) {
        DodopalResponse<ConsumeOrder> response = new DodopalResponse<ConsumeOrder>();
        response.setCode(ResponseCode.SUCCESS);
        
        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("上传收单结果过程，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        if (!PurchaseOrderResultEnum.checkCodeExist(txnstat)) {
            logger.error("上传收单结果过程，参数不合法：上传收单结果（0：成功；1：失败；2：未知）：" + txnstat);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        long blackMoney = 0;
        if (StringUtils.isNotBlank(blackAmt)) {
            try {
                blackMoney = Long.parseLong(blackAmt);
            }
            catch (Exception e) {
                logger.error("上传收单结果过程，参数不合法：收单后卡内余额：" + blackAmt);
                response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
                return response;
            }
        } else if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(txnstat) 
            || PurchaseOrderResultEnum.FAILURE.getCode().equals(txnstat)) {
            logger.error("上传收单结果过程，参数不合法：上传收单结果(0:成功；1:失败)"+txnstat+",但参数:收单后卡内余额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder oldOrder = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord oldOrderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        response.setResponseEntity(this.getConsumeOrderInfo(oldOrder, oldOrderRecord));
        
        // 1.   根据订单号判断该订单是否存在。
        if (oldOrder == null || oldOrderRecord == null) {
            logger.error("上传收单结果过程中,失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        // **********************************  判断DLL是否请求重发  START  ****************************************//

        // 如果产品库订单是最终状态，即为状态为：1：充值失败；2：充值成功；3：充值可疑
        // 以上情况，视为产品库上传结果成功，消费流程结束，响应dll参数code为“000000”。
        if (PurchaseOrderStatesEnum.PAID_FAILURE.getCode().equals(oldOrder.getStates()) 
            || PurchaseOrderStatesEnum.PAID_SUCCESS.getCode().equals(oldOrder.getStates()) 
            || PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode().equals(oldOrder.getStates())) {
            
            logger.info("上传收单结果,DLL重发请求,产品库成功响应。主订单编号:" + purchaseOrderNum);
            response.setCode(ResponseCode.SUCCESS);
            
            // 判断： 结果上传交易状态(0:成功 1:失败 2:未知) 与DB数据表中订单主状态是否一致
            if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(txnstat) && !PurchaseOrderStatesEnum.PAID_SUCCESS.getCode().equals(oldOrder.getStates())
                || PurchaseOrderResultEnum.FAILURE.getCode().equals(txnstat) && !PurchaseOrderStatesEnum.PAID_FAILURE.getCode().equals(oldOrder.getStates())
                || PurchaseOrderResultEnum.SUSPICIOUS.getCode().equals(txnstat) && !PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode().equals(oldOrder.getStates())) {
                
                logger.info("icdcRechargeCard:上传收单结果,DLL重发请求,产品库响应可疑。DLL上传参数：结果上传交易状态(0:成功 1:失败 2:未知)："+txnstat+"；订单表主状态：1：充值失败；2：充值成功；3：充值可疑"+oldOrder.getStates()+"；订单编号:" + purchaseOrderNum);
                response.setCode(ResponseCode.PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT);
            }
            return response;
        }

        // ***********************************  判断DLL是否请求重发 END  *****************************************//
        
        // 2.   该订单的状态应为以下各种情况：
        // 2.1      V61脱机消费：待支付—验卡成功
        // 2.2      V61联机消费：待支付—申请消费密钥成功（消费成功）
        if (!(PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(oldOrder.getStates()) && PurchaseOrderRedordStatesEnum.CARD_CHECK_SUCCESS.getCode().equals(oldOrderRecord.getInnerStates()))
            && !(PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(oldOrder.getStates()) && PurchaseOrderRedordStatesEnum.KEY_APPLY_SUCCESS.getCode().equals(oldOrderRecord.getInnerStates()))) {
            logger.error("上传收单结果过程中,失败：订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }

        // a)  如果扣款结果为“扣款成功”，则将订单状态改为：支付成功--扣款成功；
        // b)  如果扣款结果为“扣款失败”，则将订单状态改为：支付失败--扣款失败；
        // c)  如果扣款结果为“扣款未知”，则将订单状态改为：支付中--扣款未知，此种情况即为“可疑”。
        if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(txnstat)) {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_SUCCESS.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUCCESS.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());
            orderRecord.setBlackAmt(blackMoney);
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        } else if (PurchaseOrderResultEnum.FAILURE.getCode().equals(txnstat)) {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_FAILURE.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_FAILURE.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());
            orderRecord.setBlackAmt(blackMoney);
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        } else if (PurchaseOrderResultEnum.SUSPICIOUS.getCode().equals(txnstat)) {
            // 更新消费主订单
            ProductPurchaseOrder order = new ProductPurchaseOrder();
            order.setOrderNum(purchaseOrderNum);
            order.setStates(PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode());
            order.setUpdateUser(oldOrder.getCreateUser());
            productPurchaseOrderMapper.updatePurchaseOrder(order);
            // 更新收单记录
            ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
            orderRecord.setOrderNum(purchaseOrderNum);
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUSPICIOUS.getCode());
            orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_UNHANDLE.getCode());
            orderRecord.setBlackAmt(null);
            orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        }
        
        return response;
    }

    /**
     * 北京V71消费生单
     * 
     */
    @Override
    public DodopalResponse<String> bookBJPurchaseOrderForV71(IcdcPurchaseDTO purchaseDto, Map<String, String> merInfoMap, ProductYKT productYKT) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        
        // *************************************      参数验证    START      ***********************************//
        // 实收金额
        long receivedPrice;
        if (StringUtils.isBlank(purchaseDto.getReceivedAmt())) {
            logger.error("创建产品库收单记录，参数不合法：实收金额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        } else {
            try {
                receivedPrice = Long.parseLong(purchaseDto.getReceivedAmt());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：实收金额:" + purchaseDto.getReceivedAmt());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 来源
        if (!SourceEnum.checkCodeExist(purchaseDto.getSource())) {
            logger.error("创建产品库收单记录，参数不合法：来源:" + purchaseDto.getSource());
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 城市Code
        if (StringUtils.isBlank(purchaseDto.getCitycode())) {
            logger.error("创建产品库收单记录，参数不合法：城市编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 根据城市Code取城市名称
        Area area = areaService.findCityByCityCode(purchaseDto.getCitycode());
        if (area == null) {
            logger.error("创建产品库收单记录，参数不合法：数据库中没有该城市编号:" + purchaseDto.getCitycode());
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 客户编号
        if (StringUtils.isBlank(purchaseDto.getMercode())) {
            logger.error("创建产品库收单记录，参数不合法：客户编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 客户类型
        String customerType = merInfoMap.get("merType");
        // 客户名称
        String customerName = merInfoMap.get("merName");

        // 客户类型
        if (StringUtils.isBlank(customerType)) {
            logger.error("创建产品库收单记录，参数不合法：客户类型为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        
        // 客户名称
        if (StringUtils.isBlank(customerName)) {
            logger.error("创建产品库收单记录，参数不合法：客户名称为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // **************************************      参数验证    END      *************************************//

        // 创建消费主订单
        ProductPurchaseOrder purchaseOrder = new ProductPurchaseOrder();
        purchaseOrder.setOrderNum(getProductPurchaseOrderNum());// 订单编号
        //purchaseOrder.setOriginalPrice(originalPrice);// 应收金额-----------------------------------------------------北京V71上传结果时更新TODO
        purchaseOrder.setReceivedPrice(receivedPrice);// 实收金额-----------------------------------------------------------------------传值
        purchaseOrder.setSource(purchaseDto.getSource());// 来源------------------------------------------------------------------------传值
        purchaseOrder.setUserId(purchaseDto.getUserid());// 操作人----------------------------------------------------------------------传值
        purchaseOrder.setCreateUser(purchaseDto.getUserid());// 创建人:商户合法性验证取得商户管理员-----------------------------------传值
        purchaseOrder.setCustomerCode(purchaseDto.getMercode());// 客户编号-------------------------------------------------------------传值
        purchaseOrder.setCustomerType(customerType);// 客户类型------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setCustomerName(customerName);// 客户名称------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setServiceRateType(RateTypeEnum.PERMILLAGE.getCode());// 服务费率类型-------------------------------北京V71默认：千分比
        //purchaseOrder.setServiceRate(merServiceRate);// 服务费率------------------------------------------------------北京V71上传结果时更新TODO
        purchaseOrder.setMerRateType(RateTypeEnum.PERMILLAGE.getCode());// 商户费率类型-----------------------------------北京V71默认：千分比
        //purchaseOrder.setMerRate(null);// 商户费率--------------------------------------------------------------------北京V71上传结果时更新TODO
        purchaseOrder.setBusinessType(RateCodeEnum.YKT_PAYMENT.getCode());// 业务类型-----------------------------------固定值：03:一卡通消费
        //purchaseOrder.setMerGain(null);// 商户利润--------------------------------------------------------------------北京V71上传结果时更新TODO
        purchaseOrder.setPayGateway(productYKT.getYktCode());// 支付网关----------------------------------------------------固定值：通卡code
        purchaseOrder.setPayType(PayTypeEnum.PAY_CARD.getCode());// 支付类型--------------------------------------------固定值：1：一卡通支付
        purchaseOrder.setPayWay(purchaseDto.getCitycode());// 支付方式------------------------------------------------------固定值：城市code
        purchaseOrder.setStates(PurchaseOrderStatesEnum.PAID_WAIT.getCode());// 状态----------------------------------------初始值：0:待支付
        //purchaseOrder.setOrderDate(new Date);// 订单时间------------------------------------------------------------------------数据库时间
        //purchaseOrder.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));// 订单日期-------------数据库日期
        purchaseOrder.setClearingMark(ClearingMarkEnum.CLEARING_NO.getCode());// 清算标识-------------------------------------初始值0：未清算
        purchaseOrder.setFundProcessResult(FundProcessResultEnum.UNDONE.getCode());// 资金处理结果----------------------------初始值0：未处理
        purchaseOrder.setComments("V71操作员ID：" + purchaseDto.getCreateUser());// 备注-------------------------------------------初始值：空
        productPurchaseOrderMapper.addProductPurchaseOrder(purchaseOrder);

        // 创建收单记录
        ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
        orderRecord.setOrderNum(purchaseOrder.getOrderNum());// 订单编号---------------------------------------------------关联消费主订单编号
        orderRecord.setCreateUser(purchaseDto.getUserid());// 创建人:商户合法性验证取得商户管理员-----------------------------------传值
        orderRecord.setCityCode(purchaseDto.getCitycode());// 城市CODE------------------------------------------------------------------传值
        orderRecord.setMerDiscount(10.0);// 北京商户折扣默认值：10无折扣）----------------------------------------------------------------传值
        orderRecord.setCityName(area.getCityName());// 城市名称----------------------------------------------------------由城市code取城市名称
        orderRecord.setYtkCode(productYKT.getYktCode());// 通卡公司CODE-------------------------------------------------------------通卡code
        orderRecord.setYtkName(productYKT.getYktName());// 通卡公司名称--------------------------------------------------------------通卡名称
        orderRecord.setYktPayRate(productYKT.getYktPayRate());// 通卡公司一卡通支付费率-------------------------------------------通卡支付费率
        orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.ORDER_CREATION_SUCCESS.getCode());// 内部状态----------------00:订单创建成功
        //orderRecord.setBeforInnerStates("");// 前内部状态------------------------------------------------------------------------初始值：空
        //orderRecord.setCardNum("");// 卡号---------------------------------------------------------------------------------------初始值：空
        //orderRecord.setPosCode("");// POS号--------------------------------------------------------------------------------------初始值：空
        //orderRecord.setBefbal(0);// 支付前卡内金额--------------------------------------------------------------------------------初始值：空
        //orderRecord.setBlackAmt(0);// 支付后卡内金额------------------------------------------------------------------------------初始值：空
        //orderRecord.setOrderDate("");// 订单时间----------------------------------------------------------------------------------数据库时间
        //orderRecord.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));// 订单日期-----------------数据库日期
        orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑状态------------------------------初始值：0:不可疑
        //orderRecord.setSuspiciousExplain("");// 可疑处理说明-----------------------------------------------------------------------初始值：空
        productPurchaseOrderRecordMapper.addProductPurchaseOrderRecord(orderRecord);

        response.setResponseEntity(purchaseOrder.getOrderNum());
        return response;
    }

    
    /**
     * 北京V71消费：上传收单结果过程中验证收单记录并更新结果
     * 
     */
    @Override
    @Transactional
    public DodopalResponse<ConsumeOrder> validateBJOrderWhenUploadResultByV71(String purchaseOrderNum, String purchaseResult, String blackAmt, String amtreceivable, String userdiscount, String settldiscount) {
        DodopalResponse<ConsumeOrder> response = new DodopalResponse<ConsumeOrder>();
        response.setCode(ResponseCode.SUCCESS);
                
        // 商户费率(单位：千分比)
        if (StringUtils.isBlank(userdiscount)) {
            userdiscount = "1000";
        }
        Double merRate = 1000 - Double.parseDouble(userdiscount);
        
        // 服务费率(单位：千分比)
        if (StringUtils.isBlank(settldiscount)) {
            settldiscount = "1000";
        }
        Double serviceRate = 1000 - Double.parseDouble(settldiscount);
        
        // 商户利润(单位：分)
        long merGain = 0;
        if (StringUtils.isNotBlank(amtreceivable)) {
            if (Double.parseDouble(settldiscount) > Double.parseDouble(userdiscount)) {
                merGain = MoneyAlgorithmUtils.multiplyToIntValue(amtreceivable, String.valueOf((Double.parseDouble(settldiscount)- Double.parseDouble(userdiscount))/1000));
            } else {
                merGain = MoneyAlgorithmUtils.multiplyToIntValueAddOne(amtreceivable, String.valueOf((Double.parseDouble(settldiscount)- Double.parseDouble(userdiscount))/1000));
            }
        }
         
        if (StringUtils.isBlank(purchaseOrderNum)) {
            logger.error("上传收单结果过程，参数不合法：主订单编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        if (!PurchaseOrderResultEnum.checkCodeExist(purchaseResult)) {
            logger.error("上传收单结果过程，参数不合法：上传收单结果（0：成功；1：失败；2：未知）：" + purchaseResult);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        long blackMoney = 0;
        if (StringUtils.isNotBlank(blackAmt)) {
            try {
                blackMoney = Long.parseLong(blackAmt);
            }
            catch (Exception e) {
                logger.error("上传收单结果过程，参数不合法：收单后卡内余额：" + blackAmt);
                response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
                return response;
            }
        } else if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(purchaseResult) 
            || PurchaseOrderResultEnum.FAILURE.getCode().equals(purchaseResult)) {
            logger.error("上传收单结果过程，参数不合法：上传收单结果(0:成功；1:失败)"+purchaseResult+",但参数:收单后卡内余额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            return response;
        }

        ProductPurchaseOrder oldOrder = productPurchaseOrderMapper.getPurchaseOrderByOrderNum(purchaseOrderNum);
        ProductPurchaseOrderRecord oldOrderRecord = productPurchaseOrderRecordMapper.getPurchaseOrderRecordByOrderNum(purchaseOrderNum);

        response.setResponseEntity(this.getConsumeOrderInfo(oldOrder, oldOrderRecord));
        
        // 1.   根据订单号判断该订单是否存在。
        if (oldOrder == null || oldOrderRecord == null) {
            logger.error("上传收单结果过程中,失败：订单不存在；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }
        // **********************************  判断DLL是否请求重发  START  ****************************************//

        // 如果产品库订单是最终状态，即为状态为：1：充值失败；2：充值成功；3：充值可疑
        // 以上情况，视为产品库上传结果成功，消费流程结束，响应dll参数code为“000000”。
        if (PurchaseOrderStatesEnum.PAID_FAILURE.getCode().equals(oldOrder.getStates()) 
            || PurchaseOrderStatesEnum.PAID_SUCCESS.getCode().equals(oldOrder.getStates()) 
            || PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode().equals(oldOrder.getStates())) {
            
            logger.info("上传收单结果,DLL重发请求,产品库成功响应。主订单编号:" + purchaseOrderNum);
            response.setCode(ResponseCode.SUCCESS);
            
            // 判断： 结果上传交易状态(0:成功 1:失败 2:未知) 与DBs数据表中订单主状态是否一致
            if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(purchaseResult) && !PurchaseOrderStatesEnum.PAID_SUCCESS.getCode().equals(oldOrder.getStates())
                || PurchaseOrderResultEnum.FAILURE.getCode().equals(purchaseResult) && !PurchaseOrderStatesEnum.PAID_FAILURE.getCode().equals(oldOrder.getStates())
                || PurchaseOrderResultEnum.SUSPICIOUS.getCode().equals(purchaseResult) && !PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode().equals(oldOrder.getStates())) {
                
                logger.info("icdcRechargeCard:上传收单结果,DLL重发请求,产品库响应可疑。DLL上传参数：结果上传交易状态(0:成功 1:失败 2:未知)："+purchaseResult+"；订单表主状态：1：充值失败；2：充值成功；3：充值可疑"+oldOrder.getStates()+"；订单编号:" + purchaseOrderNum);
                response.setCode(ResponseCode.PRODUCT_UPLOAD_RECHARGE_RESULT_ORDER_STATE_DIFFERENT);
            }
            return response;
        }

        // ***********************************  判断DLL是否请求重发 END  *****************************************//
        
        // 2.   该订单的状态应该为待支付—申请扣款密钥成功。
        if (!PurchaseOrderStatesEnum.PAID_WAIT.getCode().equals(oldOrder.getStates()) || !PurchaseOrderRedordStatesEnum.KEY_APPLY_SUCCESS.getCode().equals(oldOrderRecord.getInnerStates())) {
            logger.error("上传收单结果过程中,失败：订单状态不正确；主订单编号：" + purchaseOrderNum);
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_ERROR);
            return response;
        }

        // a)  如果扣款结果为“扣款成功”，则将订单状态改为：支付成功--扣款成功；
        // b)  如果扣款结果为“扣款失败”，则将订单状态改为：支付失败--扣款失败；
        // c)  如果扣款结果为“扣款未知”，则将订单状态改为：支付中--扣款未知，此种情况即为“可疑”。
        
        ProductPurchaseOrder order = new ProductPurchaseOrder();
        order.setOrderNum(purchaseOrderNum);
        order.setUpdateUser(oldOrder.getCreateUser());
        if (StringUtils.isNotBlank(amtreceivable)) {
            order.setOriginalPrice(Long.parseLong(amtreceivable));
        }
        order.setMerGain(merGain);
        order.setServiceRate(serviceRate);
        order.setMerRate(merRate);
        
        ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
        orderRecord.setOrderNum(purchaseOrderNum);
        orderRecord.setBeforInnerStates(oldOrderRecord.getInnerStates());
        orderRecord.setBlackAmt(blackMoney);
        orderRecord.setUpdateUser(oldOrderRecord.getCreateUser());
        
        if (PurchaseOrderResultEnum.SUCCESS.getCode().equals(purchaseResult)) {
            // 更新消费主订单
            order.setStates(PurchaseOrderStatesEnum.PAID_SUCCESS.getCode());
            productPurchaseOrderMapper.updatePurchaseOrderForV71(order);
            // 更新收单记录
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUCCESS.getCode());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        } else if (PurchaseOrderResultEnum.FAILURE.getCode().equals(purchaseResult)) {
            // 更新消费主订单
            order.setStates(PurchaseOrderStatesEnum.PAID_FAILURE.getCode());
            productPurchaseOrderMapper.updatePurchaseOrderForV71(order);
            // 更新收单记录
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_FAILURE.getCode());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        } else if (PurchaseOrderResultEnum.SUSPICIOUS.getCode().equals(purchaseResult)) {
            // 更新消费主订单
            order.setStates(PurchaseOrderStatesEnum.PAID_SUSPICIOUS.getCode());
            productPurchaseOrderMapper.updatePurchaseOrderForV71(order);
            // 更新收单记录
            orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUSPICIOUS.getCode());
            orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_UNHANDLE.getCode());
            productPurchaseOrderRecordMapper.updatePurchaseOrderRecordWhenUploadResult(orderRecord);
        }
        
        return response;
    }

    /**
     * 北京V61消费生单
     */
    @Override
    public DodopalResponse<String> bookBJPurchaseOrderForV61(IcdcPurchaseDTO purchaseDto, Map<String, String> merInfoMap, ProductYKT productYKT, String userdiscount, String settldiscount) {
        
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        
        // *************************************      参数验证    START      ***********************************//
        // 应收金额
        long originalPrice;
        if (StringUtils.isBlank(purchaseDto.getReceivableAmt())) {
            logger.error("创建产品库收单记录，参数不合法：应收金额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        } else {
            try {
                originalPrice = Long.parseLong(purchaseDto.getReceivableAmt());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：应收金额:" + purchaseDto.getReceivableAmt());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 实收金额
        long receivedPrice;
        if (StringUtils.isBlank(purchaseDto.getReceivedAmt())) {
            logger.error("创建产品库收单记录，参数不合法：实收金额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        } else {
            try {
                receivedPrice = Long.parseLong(purchaseDto.getReceivedAmt());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：实收金额:" + purchaseDto.getReceivedAmt());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 商户折扣
        double merDiscount = 0;
        if (!StringUtils.isBlank(purchaseDto.getMerdiscount())) {
            try {
                merDiscount = Double.parseDouble(purchaseDto.getMerdiscount());
            }
            catch (Exception e) {
                logger.error("创建产品库收单记录，参数不合法：商户折扣:" + purchaseDto.getMerdiscount());
                e.printStackTrace();
                response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
                return response;
            }
        }
        // 来源
        if (!SourceEnum.checkCodeExist(purchaseDto.getSource())) {
            logger.error("创建产品库收单记录，参数不合法：来源:" + purchaseDto.getSource());
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 操作人
        if (StringUtils.isBlank(purchaseDto.getUserid())) {
            logger.error("创建产品库收单记录，参数不合法：操作人为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 城市Code
        if (StringUtils.isBlank(purchaseDto.getCitycode())) {
            logger.error("创建产品库收单记录，参数不合法：城市编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 根据城市Code取城市名称
        Area area = areaService.findCityByCityCode(purchaseDto.getCitycode());
        if (area == null) {
            logger.error("创建产品库收单记录，参数不合法：数据库中没有该城市编号:" + purchaseDto.getCitycode());
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }

        // 客户编号
        if (StringUtils.isBlank(purchaseDto.getMercode())) {
            logger.error("创建产品库收单记录，参数不合法：客户编号为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 客户类型
        String customerType = merInfoMap.get("merType");
        
        // 客户名称
        String customerName = merInfoMap.get("merName");

        // 客户类型
        if (StringUtils.isBlank(customerType)) {
            logger.error("创建产品库收单记录，参数不合法：客户类型为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
        // 客户名称
        if (StringUtils.isBlank(customerName)) {
            logger.error("创建产品库收单记录，参数不合法：客户名称为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_ORDER_PARAMETER_ERROR);
            return response;
        }
                
        // 商户费率(单位：千分比)
        if (StringUtils.isBlank(userdiscount)) {
            userdiscount = "1000";
        }
        Double merRate = 1000 - Double.parseDouble(userdiscount);
        
        // 服务费率(单位：千分比)
        if (StringUtils.isBlank(settldiscount)) {
            settldiscount = "1000";
        }
        Double serviceRate = 1000 - Double.parseDouble(settldiscount);
        
        // 商户利润(单位：分)
        long merGain = 0;
        if (Double.parseDouble(settldiscount) > Double.parseDouble(userdiscount)) {
            merGain = MoneyAlgorithmUtils.multiplyToIntValue(String.valueOf(originalPrice), String.valueOf((Double.parseDouble(settldiscount)- Double.parseDouble(userdiscount))/1000));
            merGain = MoneyAlgorithmUtils.multiplyToIntValue(String.valueOf(merGain), String.valueOf(merDiscount/10));
        } else {
            merGain = MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(originalPrice), String.valueOf((Double.parseDouble(settldiscount)- Double.parseDouble(userdiscount))/1000));
            merGain = MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(merGain), String.valueOf(merDiscount/10));
        }
        
        // **************************************      参数验证    END      *************************************//

        // 创建消费主订单
        ProductPurchaseOrder purchaseOrder = new ProductPurchaseOrder();
        purchaseOrder.setOrderNum(getProductPurchaseOrderNum());// 订单编号
        purchaseOrder.setOriginalPrice(originalPrice);// 应收金额-----------------------------------------------------------------------画面传值
        purchaseOrder.setReceivedPrice(receivedPrice);// 实收金额-----------------------------------------------------------------------画面传值
        purchaseOrder.setSource(purchaseDto.getSource());// 来源------------------------------------------------------------------------画面传值
        purchaseOrder.setUserId(purchaseDto.getUserid());// 操作人----------------------------------------------------------------------画面传值
        purchaseOrder.setCreateUser(purchaseDto.getUserid());// 创建人------------------------------------------------------------------画面传值
        purchaseOrder.setCustomerCode(purchaseDto.getMercode());// 客户编号-------------------------------------------------------------画面传值
        purchaseOrder.setCustomerType(customerType);// 客户类型----------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setCustomerName(customerName);// 客户名称----------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setServiceRateType(RateTypeEnum.PERMILLAGE.getCode());// 服务费率类型------------------------------------商户合法性验证取得
        purchaseOrder.setServiceRate(serviceRate);// 服务费率---------------------------------------------------------------商户合法性验证取得
        purchaseOrder.setBusinessType(RateCodeEnum.YKT_PAYMENT.getCode());// 业务类型---------------------------------------固定值：03:一卡通消费
        purchaseOrder.setMerRateType(RateTypeEnum.PERMILLAGE.getCode());// 商户费率类型-----------------------------------------------------------------------------固定值：空
        purchaseOrder.setMerRate(merRate);// 商户费率-----------------------------------------------------------------------------------固定值：空
        purchaseOrder.setMerGain(merGain);// 商户利润-------------------------------------------------------------------------------------固定值：空
        purchaseOrder.setPayGateway(productYKT.getYktCode());// 支付网关---------------------------------------------------------固定值：通卡code
        purchaseOrder.setPayType(PayTypeEnum.PAY_CARD.getCode());// 支付类型-------------------------------------------------固定值：1：一卡通支付
        purchaseOrder.setPayWay(purchaseDto.getCitycode());// 支付方式-----------------------------------------------------------固定值：城市code
        purchaseOrder.setStates(PurchaseOrderStatesEnum.PAID_WAIT.getCode());// 状态---------------------------------------------初始值：0:待支付
        //purchaseOrder.setOrderDate(new Date);// 订单时间-----------------------------------------------------------------------------数据库时间
        //purchaseOrder.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));// 订单日期------------------数据库日期
        purchaseOrder.setClearingMark(ClearingMarkEnum.CLEARING_NO.getCode());// 清算标识-----------------------------------------初始值0：未清算
        purchaseOrder.setFundProcessResult(FundProcessResultEnum.UNDONE.getCode());// 资金处理结果--------------------------------初始值0：未处理
        //purchaseOrder.setComments("");// 备注---------------------------------------------------------------------------------------初始值：空
        productPurchaseOrderMapper.addProductPurchaseOrder(purchaseOrder);

        // 创建收单记录
        ProductPurchaseOrderRecord orderRecord = new ProductPurchaseOrderRecord();
        orderRecord.setOrderNum(purchaseOrder.getOrderNum());// 订单编号--------------------------------------------------------关联消费主订单编号
        orderRecord.setCreateUser(purchaseDto.getUserid());// 创建人---------------------------------------------------------------------画面传值
        orderRecord.setCityCode(purchaseDto.getCitycode());// 城市CODE-------------------------------------------------------------------画面传值
        if (!StringUtils.isBlank(purchaseDto.getMerdiscount())) {
            orderRecord.setMerDiscount(merDiscount);// 商户折扣---------------------------------------------------------------------------画面传值
        } else {
            orderRecord.setMerDiscount(null);// 商户折扣----------------------------------------------------------------------------------画面传值
        }
        orderRecord.setCityName(area.getCityName());// 城市名称---------------------------------------------------------------由城市code取城市名称
        orderRecord.setYtkCode(productYKT.getYktCode());// 通卡公司CODE------------------------------------------------------------------通卡code
        orderRecord.setYtkName(productYKT.getYktName());// 通卡公司名称------------------------------------------------------------------通卡名称
        orderRecord.setYktPayRate(productYKT.getYktPayRate());// 通卡公司一卡通支付费率-----------------------------------------------通卡支付费率
        orderRecord.setInnerStates(PurchaseOrderRedordStatesEnum.ORDER_CREATION_SUCCESS.getCode());// 内部状态-------------初始值：00:订单创建成功
        //orderRecord.setBeforInnerStates("");// 前内部状态----------------------------------------------------------------------------初始值：空
        //orderRecord.setCardNum("");// 卡号-------------------------------------------------------------------------------------------初始值：空
        //orderRecord.setPosCode("");// POS号------------------------------------------------------------------------------------------初始值：空
        //orderRecord.setBefbal(0);// 支付前卡内金额------------------------------------------------------------------------------------初始值：空
        //orderRecord.setBlackAmt(0);// 支付后卡内金额----------------------------------------------------------------------------------初始值：空
        //orderRecord.setOrderDate("");// 订单时间-------------------------------------------------------------------------------------数据库时间
        //orderRecord.setOrderDay(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR));// 订单日期--------------------数据库日期
        orderRecord.setSuspiciousStates(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑状态---------------------------------初始值：0:不可疑
        //orderRecord.setSuspiciousExplain("");// 可疑处理说明--------------------------------------------------------------------------初始值：空
        productPurchaseOrderRecordMapper.addProductPurchaseOrderRecord(orderRecord);

        response.setResponseEntity(purchaseOrder.getOrderNum());
        return response;
    }
}

