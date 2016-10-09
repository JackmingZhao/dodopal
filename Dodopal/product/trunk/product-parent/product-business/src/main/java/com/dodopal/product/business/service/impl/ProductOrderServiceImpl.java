package com.dodopal.product.business.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderForExport;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearingMarkEnum;
import com.dodopal.common.enums.FundProcessResultEnum;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderResultEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.dao.LoadOrderMapper;
import com.dodopal.product.business.dao.ProductOrderMapper;
import com.dodopal.product.business.model.ProductConsumeOrder;
import com.dodopal.product.business.model.ProductConsumeOrderDetail;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.ProductOrderService;
import com.dodopal.product.delegate.ProductOrderDelegate;

/**
 * 产品库公交卡充值订单ServiceImpl
 * @author dodopal
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private Logger logger = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private LoadOrderMapper loadOrderMapper;

    @Autowired
    private ProductOrderDelegate prdDeletegate;

    @Autowired
    private MerchantUserService merUserService;

    /**
     * 生单接口调用方法:创建公交卡充值产品库订单
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> bookIcdcOrder(ProductOrder order) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(0);
        // 产品库订单编号:全局唯一（O + 20150428222211 +五位数据库cycle sequence（循环使用））    全局唯一    产品库自动生成。
        order.setProOrderNum(generateProductOrderNum());

        // 判断订单是否来时圈存
        if (LoadFlagEnum.LOAD_YES.getCode().equals(order.getLoadFlag())) {
            order.setProOrderState(RechargeOrderStatesEnum.PAID.getCode());// 状态：默认是已付款
            order.setProInnerState(RechargeOrderInternalStatesEnum.ORDER_CREATION_SUCCESS.getCode());// 内部状态：订单创建成功
            order.setProBeforInnerState("");// 前内部状态：默认空
        } else {
            order.setProOrderState(RechargeOrderStatesEnum.UNPAID.getCode());// 状态：默认是待付款
            order.setProInnerState(RechargeOrderInternalStatesEnum.ORDER_CREATION_SUCCESS.getCode());// 内部状态：订单创建成功
            order.setProBeforInnerState("");// 前内部状态：默认空
        }
        order.setProSuspiciousState(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑处理状态——默认不可疑“0”
        order.setProSuspiciousExplain("");// 可疑状态处理——默认空
        order.setClearingMark(ClearingMarkEnum.CLEARING_NO.getCode());// 清算标志---默认未清算
        order.setComments("");// 备注——默认为空
        order.setFundProcessResult(FundProcessResultEnum.UNDONE.getCode());// 资金处理结果——默认为0
        int num = productOrderMapper.addProductOrder(order);
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 获取圈存初始化指令调用接口方法:充值验卡环节中验证产品库订单
     */
    @Override
    @Transactional
    public DodopalResponse<ProductOrder> validIcdcOrderWhenRetrievingApdu(String cardNum, String icdcOrderNum, String externalOrderNum, String merchantNum) {
        DodopalResponse<ProductOrder> response = new DodopalResponse<ProductOrder>();
        response.setCode(ResponseCode.SUCCESS);

        // cardNum   String  32  Y
        if (!DDPStringUtil.existingWithLength(cardNum, 32)) {
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("卡号：不能为空或长度不能大于32位");
            return response;
        }
        // icdcOrderNum   String  25  Y  
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("订单编号：不能为空或长度不能大于25位");
            return response;
        }

        // 1. 根据订单号判断该订单是否存在
        response = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(response.getCode())) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
            response.setMessage("非法订单:订单编号不存在数据库中");
            return response;
        }

        // 2. 该订单的状态和支付大类是否符合以下的要求。
        // 2.1 如果是圈存，则主状态应为已付款
        // 2.2 如果不是圈存且支付类型是账户，则主状态应为待付款
        // 2.3 如果不是圈存且支付类型不是账户，则主状态应为已付款
        ProductOrder orderDB = response.getResponseEntity();
        String orderStateDB = orderDB.getProOrderState();
        String payType = orderDB.getPayType();
        String loadFlag = orderDB.getLoadFlag();
        if (!(LoadFlagEnum.LOAD_YES.getCode().equals(loadFlag) && RechargeOrderStatesEnum.PAID.getCode().equals(orderStateDB) 
            || LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag) && PayTypeEnum.PAY_ACT.getCode().equals(payType) && RechargeOrderStatesEnum.UNPAID.getCode().equals(orderStateDB) 
            || LoadFlagEnum.LOAD_NO.getCode().equals(loadFlag) && !PayTypeEnum.PAY_ACT.getCode().equals(payType) && RechargeOrderStatesEnum.PAID.getCode().equals(orderStateDB))) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_STATUS_ERROR);
            response.setMessage("非法订单：订单状态不正确");
            return response;
        }

        // 5. 验证订单中的卡号与DLL上传的卡号是否一致。
        if (!cardNum.equals(orderDB.getOrderCardno())) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_CARD_ERROR);
            response.setMessage("非法的卡号：卡号不一致");
            return response;
        }

        // 6. 如果提供了外部订单号，根据参数中的商户编号(merchantNum)和外部订单号(externalOrderNum)，去查询数据库 中是否存在记录。
        if (DDPStringUtil.isPopulated(externalOrderNum)) {
            if (!productOrderMapper.checkProductOrderExistByExternalOrderNum(externalOrderNum, merchantNum)) {
                // 如果不存在，根据参数中提供的订单编号（icdcOrderNum），将此订单上的外部订单号字段更新为参数externalOrderNum的值。
                productOrderMapper.updateProductOrderExternalOrderNum(externalOrderNum, icdcOrderNum);
            } else {
                // 如果存在，说明是外接商户重复下单，给出下面的错误码。
                response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_TWICE_ERROR);
                response.setMessage("外接商户重复下单");
                return response;
            }
        }
        return response;
    }

    /**
     * 更新公交卡充值订单充值密钥状态
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateOrderStatusWhenRetrieveRechargeKey(String icdcOrderNum, String getRechargeKeyResult) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(getRechargeKeyResult);
        response.setResponseEntity(0);

        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("一卡通充值订单编号:不能为空或长度不能大于25位.产品库订单号:" + icdcOrderNum);
            return response;
        }

        // 1.   根据订单号判断该订单是否存在
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            logger.error("非法订单:订单编号不存在");
            response.setCode(ResponseCode.PRODUCT_UPDATE_RECHARGEKEY_STATUS_ORDER_NOT_EXIST);
            response.setMessage("非法订单:订单编号不存在.产品库订单号:" + icdcOrderNum);
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        // 2.   更新订单状态（主状态，内部状态，前内部状态）
        //      订单的当前内部状态
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        int num = 0;
        String loadOrderNum = orderDB.getResponseEntity().getLoadOrderNum();
        if (ResponseCode.SUCCESS.equals(getRechargeKeyResult)) {
            // 如果是获取充值密钥成功，则更改订单状态如下：主状态：充值中；内部状态：申请充值密钥成功；前内部状态：注意：订单的当前内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            num = productOrderMapper.updateProductOrderStates(orderStatus);
            // 圈存充值订单：修改圈存订单状态：“充值中”
            if (LoadFlagEnum.LOAD_YES.getCode().equals(orderDB.getResponseEntity().getLoadFlag()) && StringUtils.isNotBlank(loadOrderNum)) {
                loadOrderMapper.updateLoadOrderStatus(LoadOrderStatusEnum.RECHARGE_ING.getCode(), loadOrderNum, orderDB.getResponseEntity().getCreateUser());
            }
        } else {
            // 如果是获取充值密钥失败，则更改订单状态如下：主状态：充值失败；内部状态：申请充值密钥失败；前内部状态：注意：订单的当前内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_FAILED.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            orderStatus.setBlackAmt(orderDB.getResponseEntity().getBefbal());
            num = productOrderMapper.updateProductOrderFailedStates(orderStatus);
            // 圈存充值订单：修改圈存订单状态：“充值失败”
            if (LoadFlagEnum.LOAD_YES.getCode().equals(orderDB.getResponseEntity().getLoadFlag()) && StringUtils.isNotBlank(loadOrderNum)) {
                loadOrderMapper.updateLoadOrderStatus(LoadOrderStatusEnum.ORDER_FAILURE.getCode(), loadOrderNum, updateUser);
            }
        }
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 更新公交卡充值订单结果状态
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateOrderStatusForResultUpload(String icdcOrderNum, String rechargeResult, String blackAmt) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);

        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            logger.error("一卡通充值订单编号:不能为空或长度不能大于25位");
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("一卡通充值订单编号:不能为空或长度不能大于25位");
            return response;
        }
        // result   String   Y
        if (!RechargeOrderResultEnum.checkCodeExist(rechargeResult)) {
            logger.error("充值结果:参数不正('0':充值成功；'1':充值失败；'2':充值可疑)" + rechargeResult);
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("充值结果:参数不正('0':充值成功；'1':充值失败；'2':充值可疑)" + rechargeResult);
            return response;
        }

        // 充值后金额
        long blackMoney = 0;
        if (StringUtils.isNotBlank(blackAmt)) {
            try {
                blackMoney = Long.parseLong(blackAmt);
            }
            catch (Exception e) {
                logger.error("更新公交卡充值订单结果状态，参数不合法：充值后卡内余额：" + blackAmt);
                response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
                response.setMessage("更新公交卡充值订单结果状态，参数不合法：充值后卡内余额：" + blackAmt);
                return response;
            }
        } else if (RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode().equals(rechargeResult) || RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(rechargeResult)) {
            logger.error("更新公交卡充值订单结果状态，参数不合法：上传充值结果(0:成功；1:失败)" + rechargeResult + ",但参数:充值后卡内余额为空");
            response.setCode(ResponseCode.PRODUCT_PURCHASE_PARAMETER_ERROR);
            response.setMessage("更新公交卡充值订单结果状态，参数不合法：上传充值结果(0:成功；1:失败)" + rechargeResult + ",但参数:充值后卡内余额为空");
            return response;
        }

        // 1. 根据订单号判断该订单是否存在。
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            logger.error("一卡通充值订单不存在");
            response.setCode(ResponseCode.PRODUCT_UPDATE_RESULT_STATUS_ORDER_NOT_EXIST);
            response.setMessage("一卡通充值订单不存在");
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        // 2. 如果订单存在，判断订单的当前状态是否为：充值中—申请充值密钥成功。
        String proOrderState = orderDB.getResponseEntity().getProOrderState();
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        
        // 取消交易不判断状态 2016-05-27
        if (!RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(rechargeResult)) {
            
            if (!(RechargeOrderStatesEnum.RECHARGE.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode().equals(proInnerState))) {
                logger.error("一卡通订单状态错误");
                response.setCode(ResponseCode.PRODUCT_UPDATE_RESULT_STATUS_ORDER_STATUS_ERROR);
                response.setMessage("一卡通订单状态错误");
                return response;
            }
        }

        // 3. 如果订单存在且状态合法，则更新一卡通充值订单状态：
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        if (RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode().equals(rechargeResult)) {
            // 3.1   DLL上传结果:充值成功--->>主状态:充值成功;内部状态:卡片充值成功;前内部状态:申请充值密钥成功
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.CARD_RECHARGE_SUCCESS.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            orderStatus.setProSuspiciousState(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());//可疑状态：0：不可疑

            // 更新充值订单：充值后金额
            orderStatus.setBlackAmt(blackMoney);

        } else if (RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(rechargeResult)) {
            // 3.2   DLL上传结果:充值失败--->>主状态:充值失败;内部状态:卡片充值失败;前内部状态:申请充值密钥成功
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.CARD_RECHARGE_FAILED.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            orderStatus.setProSuspiciousState(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());//可疑状态：0：不可疑

            // 更新充值订单：充值后金额
            orderStatus.setBlackAmt(blackMoney);

        } else if (RechargeOrderResultEnum.RECHARGE_SUSPICIOUS.getCode().equals(rechargeResult)) {
            // 3.3   DLL上传结果:充值可疑--->>主状态:充值可疑;内部状态:上传充值未知;前内部状态:申请充值密钥成功
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.RECHARGE_RESULT_UNKNOWN.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            orderStatus.setProSuspiciousState(SuspiciousStatesEnum.SUSPICIOUS_UNHANDLE.getCode());//可疑状态：1：可疑未处理

            // 更新充值订单：充值后金额(如果是充值未知或者其他，则：空)
            orderStatus.setBlackAmt(null);
        }

        int num = productOrderMapper.updateOrderStatusForResultUpload(orderStatus);

        // 4. 判断是否这笔公交卡充值是不是基于圈存订单，如果是，更改圈存订单的状态
        String loadOrderNum = orderDB.getResponseEntity().getLoadOrderNum();
        if (StringUtils.isNotBlank(loadOrderNum) && LoadFlagEnum.LOAD_YES.getCode().equals(orderDB.getResponseEntity().getLoadFlag())) {
            String loadOrderStatus = ProductConstants.EMPTY;
            if (RechargeOrderResultEnum.RECHARGE_SUCCESS.getCode().equals(rechargeResult)) {
                
                // 4.1   DLL上传结果:充值成功--->>状态:充值成功
                loadOrderStatus = LoadOrderStatusEnum.CHARGE_SUCCESS.getCode();
                loadOrderMapper.updateLoadOrderStatus(loadOrderStatus, loadOrderNum, updateUser);
            } else if (RechargeOrderResultEnum.RECHARGE_FAILURE.getCode().equals(rechargeResult)) {
                
                // 4.2   DLL上传结果:充值失败--->>状态:充值失败
                loadOrderStatus = LoadOrderStatusEnum.ORDER_FAILURE.getCode();
                loadOrderMapper.updateLoadOrderStatus(loadOrderStatus, loadOrderNum, updateUser);
            }
        }

        response.setResponseEntity(num);
        return response;
    }

    /**
     * 更新公交卡充值订单前端判断失败结果状态
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> frontFailOrderStatus(String icdcOrderNum) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(0);

        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            logger.error("一卡通充值订单编号:不能为空或长度不能大于25位");
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            return response;
        }
        // 1.   根据订单号判断该订单是否存在。
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            logger.error("一卡通充值订单不存在");
            response.setCode(ResponseCode.PRODUCT_FRONT_FAIL_ORDER_STATUS_ORDER_NOT_EXIST);
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        // 以上判断通过之后，更新订单状态如下（这里不需要判断当前订单的状态，因为卡服务做过判断）：主状态:充值失败;内部状态:卡片充值失败;前内部状态:注意：为该订单当前的内部状态
        // 该订单当前的内部状态
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());
        orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.CARD_RECHARGE_FAILED.getCode());
        orderStatus.setProBeforInnerState(proInnerState);
        orderStatus.setBlackAmt(orderDB.getResponseEntity().getBefbal());
        int num = productOrderMapper.updateProductOrderFailedStates(orderStatus);

        // 同时，判断是否这笔公交卡充值是不是基于圈存订单，如果是，更改圈存订单的状态为：充值失败
        String loadOrderNum = orderDB.getResponseEntity().getLoadOrderNum();
        if (StringUtils.isNotBlank(loadOrderNum) && LoadFlagEnum.LOAD_YES.getCode().equals(orderDB.getResponseEntity().getLoadFlag())) {
            loadOrderMapper.updateLoadOrderStatus(LoadOrderStatusEnum.ORDER_FAILURE.getCode(), loadOrderNum, updateUser);
        }
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 产品库订单资金变动状态修改（内部调用）资金冻结状态
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateOrderStatusWhenBlockFund(String icdcOrderNum, String blockResult) {

        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(blockResult);
        response.setResponseEntity(0);

        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("一卡通充值订单编号:不能为空或长度不能大于25位");
            return response;
        }
        // 1.   根据订单号判断该订单是否存在
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            response.setCode(ResponseCode.PRODUCT_BLOCK_FUND_RONT_ORDER_NOT_EXIST);
            response.setMessage("非法订单");
            return response;
        }
        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        // 2.   先判断该订单的状态和支付类型是否满足以下条件
        // 支付类型:账户支付;主状态:待付款;内部状态:订单创建成功
        // 支付类型:网银;主状态:已付款;内部状态:账户充值成功
        String payType = orderDB.getResponseEntity().getPayType();
        String proOrderState = orderDB.getResponseEntity().getProOrderState();
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        if (!(PayTypeEnum.PAY_ACT.getCode().equals(payType) && RechargeOrderStatesEnum.UNPAID.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.ORDER_CREATION_SUCCESS.getCode().equals(proInnerState) || !PayTypeEnum.PAY_ACT.getCode().equals(payType) && RechargeOrderStatesEnum.PAID.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.ACCOUNT_RECHARGE_SUCCESS.getCode().equals(proInnerState))) {
            response.setCode(ResponseCode.PRODUCT_BLOCK_FUND_RONT_ORDER_STATUS_ERROR);
            response.setMessage("非法订单状态");
            return response;
        }
        // 以上判断成功后，判断资金冻结状态
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        int num = 0;
        if (!ResponseCode.SUCCESS.equals(blockResult)) {
            // 如果是资金冻结失败，则更改订单状态如下：主状态:充值失败;内部状态:资金冻结失败;前内部状态:注意：为该订单当前的内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.FUND_FROZEN_FAILED.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            orderStatus.setBlackAmt(orderDB.getResponseEntity().getBefbal());
            num = productOrderMapper.updateProductOrderFailedStates(orderStatus);
        } else {
            // 如果冻结资金成功，则更改订单状态如下：主状态:充值中;内部状态:资金冻结成功;前内部状态:注意：为该订单当前的内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.FUND_FROZEN_SUCCESS.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
            num = productOrderMapper.updateProductOrderStates(orderStatus);
        }
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 产品库订单资金变动状态修改（内部调用）资金解冻状态
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateOrderStatusWhenUnbolckFund(String icdcOrderNum, String unblockResult) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(unblockResult);
        response.setResponseEntity(0);

        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            response.setMessage("一卡通充值订单编号:不能为空或长度不能大于25位");
            return response;
        }
        // 1.   根据订单号判断该订单是否存在
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            response.setCode(ResponseCode.PRODUCT_UNBLOCK_FUND_RONT_ORDER_NOT_EXIST);
            response.setMessage("非法订单");
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        // 2.   先判断订单状态是否满足以下条件：
        // 主状态:充值中;内部状态:资金资金冻结成功
        // 主状态:充值失败;内部状态:申请充值密钥失败
        // 主状态:充值失败;内部状态:卡片充值失败
        String proOrderState = orderDB.getResponseEntity().getProOrderState();
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        if (!(RechargeOrderStatesEnum.RECHARGE.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.FUND_FROZEN_SUCCESS.getCode().equals(proInnerState) || RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_FAILED.getCode().equals(proInnerState) || RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.CARD_RECHARGE_FAILED.getCode().equals(proInnerState))) {
            response.setCode(ResponseCode.PRODUCT_UNBLOCK_FUND_RONT_ORDER_STATUS_ERROR);
            response.setMessage("非法订单状态");
            return response;
        }
        // 以上判断成功后，判断资金解冻状态
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        orderStatus.setBlackAmt(orderDB.getResponseEntity().getBefbal());
        if (!ResponseCode.SUCCESS.equals(unblockResult)) {
            // 如果是资金解冻失败，则更改订单状态如下：主状态:充值失败;内部状态:资金解冻失败;前内部状态:注意：为该订单当前的内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.FUND_UNBOLCK_FAILED.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
        } else {
            // 如果冻结解冻成功，则更改订单状态如下：主状态:充值失败;内部状态:资金解冻成功;前内部状态:注意：为该订单当前的内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.FUND_UNBOLCK_SUCCESS.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
        }

        int num = productOrderMapper.updateProductOrderFailedStates(orderStatus);
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 产品库订单资金变动状态修改 （内部调用）资金扣款状态
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateOrderStatusWhendeduckFund(String icdcOrderNum, String deductResult) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(deductResult);
        response.setResponseEntity(0);

        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            logger.error("一卡通充值订单编号:不能为空或长度不能大于25位");
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            return response;
        }
        // 1.   根据订单号判断该订单是否存在
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            logger.error("非法订单");
            response.setCode(ResponseCode.PRODUCT_DEDUCK_FUND_ORDER_NOT_EXIST);
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        // 2.   先判断订单状态是否满足以下条件：
        // 主状态:充值成功;内部状态:卡片充值成功
        String proOrderState = orderDB.getResponseEntity().getProOrderState();
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        if (!(RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.CARD_RECHARGE_SUCCESS.getCode().equals(proInnerState))) {
            logger.error("非法订单状态");
            response.setCode(ResponseCode.PRODUCT_DEDUCK_FUND_ORDER_STATUS_ERROR);
            return response;
        }
        // 以上判断成功后，判断资金扣款状态
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        if (!ResponseCode.SUCCESS.equals(deductResult)) {
            // 如果是资金扣款失败，则更改订单状态如下：主状态:充值成功;内部状态:资金扣款失败;前内部状态:注意：为该订单当前的内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.FUND_DEDUCT_FAILED.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
        } else {
            // 如果冻结扣款成功，则更改订单状态如下：主状态:充值成功;内部状态:资金扣款成功;前内部状态:注意：为该订单当前的内部状态
            orderStatus.setProOrderState(RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.FUND_DEDUCT_SUCCESS.getCode());
            orderStatus.setProBeforInnerState(proInnerState);
        }
        int num = productOrderMapper.updateProductOrderStates(orderStatus);
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 网银支付失败，更新公交卡充值订单资金变动状态(一卡通充值选择非账户支付方式网银支付失败后)
     */
    @Override
    @Transactional
    public DodopalResponse<ProductOrder> updateOrderStatusWhenOnlinePayment(String icdcOrderNum) {
        DodopalResponse<ProductOrder> response = new DodopalResponse<ProductOrder>();
        response.setCode(ResponseCode.SUCCESS);
        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            logger.error("一卡通充值订单编号:不能为空或长度不能大于25位");
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            return response;
        }
        // 1.   根据订单号判断该订单是否存在
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            logger.error("非法订单");
            response.setCode(ResponseCode.PRODUCT_ONLINE_PAY_ORDER_NOT_EXIST);
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        response.setResponseEntity(orderDB.getResponseEntity());

        // 2.   先判断订单状态是否满足以下条件：
        // 主状态:待付款;内部状态：订单创建成功
        String proOrderState = orderDB.getResponseEntity().getProOrderState();
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        if (!(RechargeOrderStatesEnum.UNPAID.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.ORDER_CREATION_SUCCESS.getCode().equals(proInnerState))) {
            logger.error("非法订单状态");
            response.setCode(ResponseCode.PRODUCT_ONLINE_PAY_ORDER_STATUS_ERROR);
            return response;
        }

        // 以上判断成功后，修改订单状态如下：
        // 主状态:待付款;内部状态：网银支付失败;前内部状态:注意：为该订单当前的内部状态(订单创建成功)
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        orderStatus.setProOrderState(RechargeOrderStatesEnum.UNPAID.getCode());
        orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.ONLINE_BANKING_PAY_FAILED.getCode());
        orderStatus.setProBeforInnerState(proInnerState);
        orderStatus.setBlackAmt(orderDB.getResponseEntity().getBefbal());
        productOrderMapper.updateProductOrderFailedStates(orderStatus);
        return response;
    }

    /**
     * 账户充值结果，更新公交卡充值订单资金变动状态(一卡通充值选择非账户支付方式网银支付成功后)
     */
    @Override
    @Transactional
    public DodopalResponse<ProductOrder> updateOrderStatusWhenAccountRecharge(String icdcOrderNum, String accountRechargeResult) {
        DodopalResponse<ProductOrder> response = new DodopalResponse<ProductOrder>();
        response.setCode(accountRechargeResult);
        // icdcOrderNum   String  25  Y
        if (!DDPStringUtil.existingWithLength(icdcOrderNum, 25)) {
            logger.error("一卡通充值订单编号:不能为空或长度不能大于25位");
            response.setCode(ResponseCode.PRODUCT_CARD_RECHARGE_PARAMETER_ERROR);
            return response;
        }
        // 1.   根据订单号判断该订单是否存在
        DodopalResponse<ProductOrder> orderDB = this.getProductOrderByProOrderNum(icdcOrderNum);
        if (!ResponseCode.SUCCESS.equals(orderDB.getCode())) {
            logger.error("非法订单");
            response.setCode(ResponseCode.PRODUCT_ACCOUNT_RECHARGE_ORDER_NOT_EXIST);
            return response;
        }

        // 状态更新人即为订单创建人
        String updateUser = orderDB.getResponseEntity().getCreateUser();

        response.setResponseEntity(orderDB.getResponseEntity());

        // 2.   先判断订单状态是否满足以下条件：
        // 主状态:待付款;内部状态：订单创建成功
        String proOrderState = orderDB.getResponseEntity().getProOrderState();
        String proInnerState = orderDB.getResponseEntity().getProInnerState();
        if (!(RechargeOrderStatesEnum.UNPAID.getCode().equals(proOrderState) && RechargeOrderInternalStatesEnum.ORDER_CREATION_SUCCESS.getCode().equals(proInnerState))) {
            logger.error("非法订单状态");
            response.setCode(ResponseCode.PRODUCT_ACCOUNT_RECHARGE_ORDER_STATUS_ERROR);
            return response;
        }

        // 以上判断成功后，判断账户充值结果更新状态
        ProductOrder orderStatus = new ProductOrder();
        orderStatus.setUpdateUser(updateUser);
        orderStatus.setProOrderNum(orderDB.getResponseEntity().getProOrderNum());
        if (!ResponseCode.SUCCESS.equals(accountRechargeResult)) {
            // 如果账户充值失败，则更改订单状态如下：主状态:已付款;内部状态:账户充值失败;前内部状态:网银支付成功
            orderStatus.setProOrderState(RechargeOrderStatesEnum.PAID.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.ACCOUNT_RECHARGE_FAILED.getCode());
            orderStatus.setProBeforInnerState(RechargeOrderInternalStatesEnum.ONLINE_BANKING_PAY_SUCCESS.getCode());
            orderStatus.setBlackAmt(orderDB.getResponseEntity().getBefbal());
            productOrderMapper.updateProductOrderFailedStates(orderStatus);
        } else {
            // 如果账户充值成功，则更改订单状态如下：主状态:已付款;内部状态:账户充值成功;前内部状态:网银支付成功
            orderStatus.setProOrderState(RechargeOrderStatesEnum.PAID.getCode());
            orderStatus.setProInnerState(RechargeOrderInternalStatesEnum.ACCOUNT_RECHARGE_SUCCESS.getCode());
            orderStatus.setProBeforInnerState(RechargeOrderInternalStatesEnum.ONLINE_BANKING_PAY_SUCCESS.getCode());
            productOrderMapper.updateProductOrderStates(orderStatus);
        }

        return response;
    }

    /**
     * 产品库公交卡充值订单编号生成规则O + 20150428222211 +五位数据库cycle sequence（循环使用）
     */
    @Transactional(readOnly = true)
    private String generateProductOrderNum() {
        String prdOrderNum = "O";
        String timeStr = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        prdOrderNum += timeStr;
        return prdOrderNum + productOrderMapper.getPrdOrderCodeSeq();
    }

    /**
     * 根据订单号查询该订单
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<ProductOrder> getProductOrderByProOrderNum(String proOrderNum) {
        DodopalResponse<ProductOrder> response = new DodopalResponse<ProductOrder>();
        response.setCode(ResponseCode.SUCCESS);
        ProductOrder order = productOrderMapper.getProductOrderByProOrderNum(proOrderNum);
        if (order == null) {
            response.setCode(ResponseCode.PRODUCT_CHECK_CARD_ORDER_NOT_EXIST);
        }
        response.setResponseEntity(order);
        return response;
    }

    /**
     * 第一次进入申请充值密钥环节将“重发标志位”：置为1（产品库接收到DLL请求时，以此判断是否为重发请求） add by shenXiang
     * 2015-11-10
     */
    @Override
    @Transactional
    public DodopalResponse<Integer> updateReSignByProOrderNum(String proOrderNum) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setResponseEntity(0);
        response.setCode(ResponseCode.SUCCESS);
        int num = productOrderMapper.updateReSignByProOrderNum(proOrderNum);
        if (num == 0) {
            response.setCode(ResponseCode.PRODUCT_UPDATE_RECHARGEKEY_STATUS_ORDER_NOT_EXIST);
        }
        response.setResponseEntity(num);
        return response;
    }

    //******************************************************************************************************************************************//
    //*****************************************************     华丽分割线           *****************************************************************//
    //******************************************************************************************************************************************//

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.1 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            if (CommonConstants.SYSTEM_NAME_OSS.equals(prdOrderQuery.getSource()) || prdOrderQuery.getUserCode() != null) {
                List<ProductOrder> orders = productOrderMapper.findProductOrdersByPage(prdOrderQuery);
                List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
                if (CollectionUtils.isNotEmpty(orders)) {
                    for (ProductOrder order : orders) {
                        ProductOrderDTO dto = convertPrdOrderToDto(order);
                        orderList.add(dto);
                    }
                }
                DodopalDataPage<ProductOrderDTO> pages = new DodopalDataPage<ProductOrderDTO>(prdOrderQuery.getPage(), orderList);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(pages);
            } else {
                response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_USER_CODE_ERROR);
            }
        }
        catch (Exception e) {
            logger.error("产品库中公交卡充值订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    private ProductOrderDTO convertPrdOrderToDto(ProductOrder order) {
        ProductOrderDTO dto = new ProductOrderDTO();
        dto.setId(order.getId());
        dto.setProOrderNum(order.getProOrderNum());
        dto.setCityName(order.getCityName());
        dto.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));
        dto.setShowReceivedPrice(String.format("%.2f", order.getReceivedPrice() == null ? 0 : (double) order.getReceivedPrice() / 100));
        dto.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
        dto.setShowBlackAmt(String.format("%.2f", order.getBlackAmt() == null ? 0 : (double) order.getBlackAmt() / 100));
        dto.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
        dto.setPosCode(order.getPosCode());
        dto.setProOrderState(order.getProOrderState());
        dto.setOrderCardno(order.getOrderCardno());
        dto.setProOrderDate(order.getProOrderDate());
        dto.setMerName(order.getMerName());
        dto.setProInnerState(order.getProInnerState());
        dto.setPayType(order.getPayType());
        dto.setPayWay(order.getPayWay());
        dto.setMerOrderNum(order.getMerOrderNum());
        dto.setMerCode(order.getMerCode());
        dto.setMerUserType(order.getMerUserType());
        dto.setPosComments(order.getPosComments());
        return dto;
    }

    private ProductOrderDTO convertPrdOrderToDtoForExport(ProductOrder order) {
        ProductOrderDTO dto = new ProductOrderDTO();
        dto.setId(order.getId());
        dto.setProOrderNum(order.getProOrderNum());
        dto.setCityName(order.getCityName());
        dto.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));
        dto.setShowReceivedPrice(String.format("%.2f", order.getReceivedPrice() == null ? 0 : (double) order.getReceivedPrice() / 100));
        dto.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
        dto.setShowBlackAmt(String.format("%.2f", order.getBlackAmt() == null ? 0 : (double) order.getBlackAmt() / 100));
        dto.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
        dto.setPosCode(order.getPosCode());
        dto.setProOrderState(order.getProOrderState());
        dto.setOrderCardno(order.getOrderCardno());
        dto.setProOrderDate(order.getProOrderDate());
        dto.setMerName(order.getMerName());
        dto.setProInnerState(order.getProInnerState());
        dto.setPayType(order.getPayType());
        dto.setPayWay(order.getPayWay());
        dto.setMerOrderNum(order.getMerOrderNum());
        dto.setMerCode(order.getMerCode());
        dto.setProSuspiciousExplain(order.getProSuspiciousExplain());
        dto.setProSuspiciousState(order.getProSuspiciousState());
        dto.setClearingMark(order.getClearingMark());
        dto.setPayTypeView(order.getPayType());
        dto.setUserName(order.getUserName());
        dto.setProCode(order.getProCode());
        dto.setProName(order.getProName());
        dto.setMerPurchaasePriceView(String.format("%.2f", order.getMerPurchaasePrice() == null ? 0 : (double) order.getMerPurchaasePrice() / 100));
        dto.setMerUserType(order.getMerUserType());
        return dto;
    }

    /**
     * 一卡通充值订单查询（管理员查询操作员）
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findAdminProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            if (CommonConstants.SYSTEM_NAME_OSS.equals(prdOrderQuery.getSource()) || prdOrderQuery.getUserCode() != null) {
                //1.判断当前登录的是不是管理员
                DodopalResponse<MerchantUserBean> listBeanRep = merUserService.findUserInfoByUserCode(prdOrderQuery.getUserCode());
                //List<String> userCodes =new ArrayList<String>();
                //userCodes.add(prdOrderQuery.getUserCode());
                if (MerUserTypeEnum.MERCHANT.getCode().equals(listBeanRep.getResponseEntity().getMerUserType())) {
                    prdOrderQuery.setMerCode(listBeanRep.getResponseEntity().getMerCode());
                    if (MerUserFlgEnum.COMMON.getCode().equals(listBeanRep.getResponseEntity().getMerUserFlag())) {
                        prdOrderQuery.setUserCode(prdOrderQuery.getUserCode());
                    } else if (MerUserFlgEnum.ADMIN.getCode().equals(listBeanRep.getResponseEntity().getMerUserFlag())) {
                        prdOrderQuery.setUserCode("");
                    }
                } else if (MerUserTypeEnum.PERSONAL.getCode().equals(listBeanRep.getResponseEntity().getMerUserType())) {
                    prdOrderQuery.setUserCode(prdOrderQuery.getUserCode());
                }

                List<ProductOrder> orders = productOrderMapper.findAdminProductOrdersByPage(prdOrderQuery);
                List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
                if (CollectionUtils.isNotEmpty(orders)) {
                    for (ProductOrder order : orders) {
                        ProductOrderDTO dto = new ProductOrderDTO();
                        dto.setId(order.getId());
                        dto.setProOrderNum(order.getProOrderNum());
                        dto.setCityName(order.getCityName());
                        dto.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));
                        dto.setShowReceivedPrice(order.getReceivedPrice() == null ? "" : String.format("%.2f", (double) order.getReceivedPrice() / 100));
                        dto.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
                        dto.setShowBlackAmt(order.getBlackAmt() == null ? "" : String.format("%.2f", (double) order.getBlackAmt() / 100));
                        //  dto.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
                        dto.setPosCode(order.getPosCode());
                        dto.setProOrderState(order.getProOrderState());
                        dto.setOrderCardno(order.getOrderCardno());
                        dto.setProOrderDate(order.getProOrderDate());
                        dto.setMerName(order.getMerName());
                        dto.setProInnerState(order.getProInnerState());
                        if (RechargeOrderInternalStatesEnum.FUND_DEDUCT_SUCCESS.getCode().equals(order.getProInnerState())) {
                            dto.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
                        } else {
                            dto.setShowMerGain("0.00");
                        }
                        dto.setMerOrderNum(order.getMerOrderNum());
                        dto.setPayType(order.getPayType());
                        dto.setPayWay(order.getPayWay());
                        if (RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(order.getProOrderState())) {
                            // 明确充值失败的订单，卡充值后金额应为充值前金额
                            dto.setShowBlackAmt(dto.getShowBefbal());
                        } else if (RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(order.getProOrderState())) {
                            // 可疑订单，卡充值后金额不展示
                            dto.setShowBlackAmt("");
                        }
                        //pos备注
                        dto.setPosComments(order.getPosComments());
                        dto.setUserName(order.getUserName());//操作员

                        orderList.add(dto);
                    }
                }
                DodopalDataPage<ProductOrderDTO> pages = new DodopalDataPage<ProductOrderDTO>(prdOrderQuery.getPage(), orderList);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(pages);
            } else {
                response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_USER_CODE_ERROR);
            }
        }
        catch (Exception e) {
            logger.error("产品库中公交卡充值订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<ProductOrderForExport>> findProductOrdersForExport(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductOrderForExport>> response = new DodopalResponse<List<ProductOrderForExport>>();

        try {
            int exportCount = productOrderMapper.getCountProductOrdersForExport(prdOrderQuery);
            if (exportCount > ExcelUtil.EXPORT_MAX_COUNT) {
                response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                return response;
            }

            List<ProductOrderForExport> orders = productOrderMapper.getListProductOrdersForExport(prdOrderQuery);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(orders);
        }
        catch (Exception e) {
            logger.error("产品库中公交卡充值订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 5.2 产品库中公交卡充值订单 --5.2.2 订单查看 用户选择一条公交卡充值订单，点击“查看”按钮，页面向用户展示详情。
     * 订单编号、商户名称、产品编号
     * 、产品名称、充值金额、城市、实付金额、成本价（商户进货价）、订单时间、商户利润（这个字段对个人用户不要显示）、卡号、充值前金额
     * 、充值后金额、充值前账户可用余额、充值后账户可用余额、订单状态、外部订单号（仅限于外部商户）、操作员名称、POS编号、POS备注。
     */
    @Transactional(readOnly = true)
    @Override
    public DodopalResponse<ProductOrderDetailDTO> findProductOrderDetails(String proOrderNum) {
        DodopalResponse<ProductOrderDetailDTO> response = new DodopalResponse<ProductOrderDetailDTO>();
        try {
            if (DDPStringUtil.isNotPopulated(proOrderNum)) {
                response.setCode(ResponseCode.PRODUCT_ORDER_NUMBER_EMPTY);
            } else {
                ProductOrder order = productOrderMapper.findProductOrderDetails(proOrderNum);
                order.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
                order.setShowBlackAmt(order.getBlackAmt() == null ? "" : String.format("%.2f", (double) order.getBlackAmt() / 100));
                order.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
                order.setShowMerPurchaasePrice(order.getMerPurchaasePrice() == null ? "" : String.format("%.2f", (double) order.getMerPurchaasePrice() / 100));
                order.setShowReceivedPrice(order.getReceivedPrice() == null ? "" : String.format("%.2f", (double) order.getReceivedPrice() / 100));
                order.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));

                ProductOrderDetailDTO orderDetail = new ProductOrderDetailDTO();
                if (order != null) {
                    PropertyUtils.copyProperties(orderDetail, order);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(orderDetail);
            }
        }
        catch (Exception e) {
            logger.error("产品库中公交卡充值订单查看详细信息出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_QUERY_ERROR);
        }
        return response;
    }

    /**
     * Title:一卡通充值业务订单导出 Time:2015-10-16 14:01 Name:Joe
     */
    @Override
    public DodopalResponse<String> excelProDuctExport(HttpServletResponse response, ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<String> reponse = new DodopalResponse<String>();
        try {
            if (prdOrderQuery.getUserCode() != null) {
                //1.判断当前登录的是不是管理员
                DodopalResponse<MerchantUserBean> listBeanRep = merUserService.findUserInfoByUserCode(prdOrderQuery.getUserCode());
                //List<String> userCodes =new ArrayList<String>();
                //userCodes.add(prdOrderQuery.getUserCode());
                if (MerUserTypeEnum.MERCHANT.getCode().equals(listBeanRep.getResponseEntity().getMerUserType())) {
                    prdOrderQuery.setMerCode(listBeanRep.getResponseEntity().getMerCode());
                    if (MerUserFlgEnum.COMMON.getCode().equals(listBeanRep.getResponseEntity().getMerUserFlag())) {
                        prdOrderQuery.setUserCode(prdOrderQuery.getUserCode());
                    } else {
                        prdOrderQuery.setUserCode("");
                    }
                } else if (MerUserTypeEnum.PERSONAL.getCode().equals(listBeanRep.getResponseEntity().getMerUserType())) {
                    prdOrderQuery.setUserCode(prdOrderQuery.getUserCode());
                }
                List<ProductOrder> lstData = productOrderMapper.findProductOrdersExcel(prdOrderQuery);
                if (CollectionUtils.isNotEmpty(lstData)) {
                    for (ProductOrder order : lstData) {
                        order.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));
                        order.setShowReceivedPrice(order.getReceivedPrice() == null ? "" : String.format("%.2f", (double) order.getReceivedPrice() / 100));
                        order.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
                        order.setShowBlackAmt(order.getBlackAmt() == null ? "" : String.format("%.2f", (double) order.getBlackAmt() / 100));
                        order.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
                        order.setShowMerPurchaasePrice(order.getMerPurchaasePrice() == null ? "" : String.format("%.2f", (double) order.getMerPurchaasePrice() / 100));
                        order.setShowProOrderState(order.getProOrderState());
                        if (RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(order.getProOrderState())) {
                            // 明确充值失败的订单，卡充值后金额应为充值前金额
                            order.setShowBlackAmt(order.getShowBefbal());
                        } else if (RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(order.getProOrderState())) {
                            // 可疑订单，卡充值后金额不展示
                            order.setShowBlackAmt("");
                        }
                    }
                }

                //【写法二，使用自定义模板，需指定模板名称，并指定数据开始行】
                ExcelModel excelModel = new ExcelModel("productOrderList");
                excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
                if (CollectionUtils.isNotEmpty(lstData)) {
                    List<List<String>> dataList = new ArrayList<List<String>>(lstData.size());
                    for (ProductOrder data : lstData) {
                        List<String> rowList = new ArrayList<String>();
                        // 订单编号
                        rowList.add(data.getProOrderNum());
                        //外部订单号
                        rowList.add(data.getMerOrderNum());
                        // 商户名称
                        rowList.add(data.getMerName());
                        // 卡号
                        rowList.add(data.getOrderCardno());
                        // POS号
                        rowList.add(data.getPosCode());
                        // 城市
                        rowList.add(data.getCityName());
                        // 充值金额
                        rowList.add(data.getShowTxnAmt());
                        // 实付金额
                        rowList.add(data.getShowReceivedPrice());
                        // 成本价
                        rowList.add(data.getShowMerPurchaasePrice());
                        // 商户利润
                        rowList.add(data.getShowMerGain());
                        // 充值前金额
                        rowList.add(data.getShowBefbal());
                        // 充值后金额
                        rowList.add(data.getShowBlackAmt());
                        // 订单状态
                        rowList.add(data.getShowProOrderStateView());
                        // 订单时间
                        rowList.add(DateUtils.formatDate(data.getProOrderDate(), DateUtils.DATE_FULL_STR));
                        // 操作员
                        rowList.add(data.getUserName());
                        // 【添加数据行】
                        dataList.add(rowList);
                    }
                    // 【将数据集加入model】
                    excelModel.setDataList(dataList);
                }
                return ExcelUtil.excelExport(excelModel, response);
            }
            reponse.setCode(ResponseCode.SUCCESS);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return reponse;
    }

    //-------------------------------------------------------子商户业务订单---------------------------------
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findChildMerProductOrdersByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            if (prdOrderQuery == null) {
                prdOrderQuery = new ProductOrderQueryDTO(); //默认查询全部
            }
            List<ProductOrder> orders = productOrderMapper.findChildMerProductOrdersByPage(prdOrderQuery);
            List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
            if (CollectionUtils.isNotEmpty(orders)) {
                for (ProductOrder order : orders) {
                    ProductOrderDTO dto = new ProductOrderDTO();
                    order.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));
                    order.setShowReceivedPrice(String.format("%.2f", order.getReceivedPrice() == null ? 0 : (double) order.getReceivedPrice() / 100));
                    order.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
                    order.setShowBlackAmt(String.format("%.2f", order.getBlackAmt() == null ? 0 : (double) order.getBlackAmt() / 100));
                    order.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
                    PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            DodopalDataPage<ProductOrderDTO> pages = new DodopalDataPage<ProductOrderDTO>(prdOrderQuery.getPage(), orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("产品库中公交卡充值订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 子商户业务订单导出
     */
    @Override
    public List<ProductOrderDTO> findChildMerProductOrderExcel(ProductOrderQueryDTO prdOrderQuery) {
        List<ProductOrder> orders = productOrderMapper.findChildProductOrdersExcel(prdOrderQuery);
        List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
        try {
            if (CollectionUtils.isNotEmpty(orders)) {
                for (ProductOrder order : orders) {
                    ProductOrderDTO dto = new ProductOrderDTO();
                    dto.setId(order.getId());
                    dto.setProOrderNum(order.getProOrderNum());
                    dto.setCityName(order.getCityName());
                    dto.setShowTxnAmt(String.format("%.2f", (double) order.getTxnAmt() / 100));
                    dto.setShowReceivedPrice(String.format("%.2f", order.getReceivedPrice() == null ? 0 : (double) order.getReceivedPrice() / 100));
                    dto.setShowBefbal(String.format("%.2f", (double) order.getBefbal() / 100));
                    dto.setShowBlackAmt(String.format("%.2f", order.getBlackAmt() == null ? 0 : (double) order.getBlackAmt() / 100));
                    dto.setShowMerGain(String.format("%.2f", (double) order.getMerGain() / 100));
                    dto.setPosCode(order.getPosCode());
                    dto.setProOrderState(order.getProOrderState());
                    dto.setOrderCardno(order.getOrderCardno());
                    dto.setProOrderDate(order.getProOrderDate());
                    dto.setMerName(order.getMerName());
                    dto.setProInnerState(order.getProInnerState());
                    dto.setPosComments(order.getPosComments());
                    orderList.add(dto);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public DodopalResponse<String> findMerchantByMerCode(String merCode) {
        DodopalResponse<MerchantDTO> prdDto = prdDeletegate.findMerchantUserByMerCode(merCode);
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            if (ResponseCode.SUCCESS.equals(prdDto.getCode())) {
                String merType = prdDto.getResponseEntity().getMerType();
                rep.setResponseEntity(merType);
                rep.setCode(prdDto.getCode());
            }
        }
        catch (Exception e) {
            rep.setCode(prdDto.getCode());
            e.printStackTrace();
        }
        return rep;
    }

    //查询 一卡通消费  收单记录(分页)
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(ProductConsumeOrderQueryDTO prdConsumeOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>>();
        //金额格式化 保留两位小数
        DecimalFormat df = new DecimalFormat("0.00");

        try {
            List<ProductConsumeOrder> prdConsumeorders = productOrderMapper.findProductConsumeOrdersByPage(prdConsumeOrderQuery);
            List<ProductConsumeOrderDTO> orderList = new ArrayList<ProductConsumeOrderDTO>();

            if (CollectionUtils.isNotEmpty(prdConsumeorders)) {
                for (ProductConsumeOrder order : prdConsumeorders) {
                    ProductConsumeOrderDTO dto = new ProductConsumeOrderDTO();
                    beanToDto(df, order, dto);
                    //PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            DodopalDataPage<ProductConsumeOrderDTO> pages = new DodopalDataPage<ProductConsumeOrderDTO>(prdConsumeOrderQuery.getPage(), orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中公交卡 消费订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    private void beanToDto(DecimalFormat df, ProductConsumeOrder order, ProductConsumeOrderDTO dto) {
        dto.setId(order.getId());
        //状态
        dto.setStates(order.getStates());
        //内部状态
        dto.setInnerStates(order.getInnerStates());
        //商户名称
        dto.setMerName(order.getMerName());
        dto.setMerType(order.getCustomerType());
        //商户折扣
        dto.setMerDiscount(order.getMerDiscount());
        //城市名称
        dto.setCityName(order.getCityName());
        //订单号
        dto.setOrderNum(order.getOrderNum());
        //pos号
        dto.setPosCode(order.getPosCode());
        //通卡公司名称
        dto.setYktName(order.getYktName());
        //操作员姓名
        dto.setUserName(order.getUserName());
        //订单时间
        dto.setProOrderDate(order.getProOrderDate());
        dto.setProOrderDay(order.getProOrderDay());
        //
        dto.setBefbal(order.getBefbal() == null ? order.getBefbal() : df.format(Double.valueOf(order.getBefbal()) / 100));
        dto.setBlackAmt(order.getBlackAmt() == null ? order.getBlackAmt() : df.format(Double.valueOf(order.getBlackAmt()) / 100));
        dto.setMerGain(df.format((double) order.getMerGain() / 100));
        dto.setOriginalPrice(df.format((double) order.getOriginalPrice() / 100));
        dto.setReceivedPrice(df.format((double) order.getReceivedPrice() / 100));
        dto.setMerRate(order.getMerRate());
        dto.setCardNum(order.getCardNum());
        dto.setCustomerCode(order.getCustomerCode());
        dto.setComments(order.getComments());//pos备注
        dto.setYktDisCountSign(order.getYktDisCountSign());
    }
    
    private void beanToDtoForExport(DecimalFormat df, ProductConsumeOrder order, ProductConsumeOrderDTO dto) {
        dto.setId(order.getId());
        //状态
        dto.setStates(order.getStates());
        //内部状态
        dto.setInnerStates(order.getInnerStates());
        //商户名称
        dto.setMerName(order.getMerName());
        //商户折扣
        dto.setMerDiscount(order.getMerDiscount());
        //城市名称
        dto.setCityName(order.getCityName());
        //订单号
        dto.setOrderNum(order.getOrderNum());
        //pos号
        dto.setPosCode(order.getPosCode());
        //通卡公司名称
        dto.setYktName(order.getYktName());
        //操作员姓名
        dto.setUserName(order.getUserName());
        //订单时间
        dto.setProOrderDate(order.getProOrderDate());
        dto.setProOrderDay(order.getProOrderDay());
        //
        dto.setBefbal(order.getBefbal() == null ? order.getBefbal() : df.format(Double.valueOf(order.getBefbal()) / 100));
        dto.setBlackAmt(order.getBlackAmt() == null ? order.getBlackAmt() : df.format(Double.valueOf(order.getBlackAmt()) / 100));
        dto.setMerGain(df.format((double) order.getMerGain() / 100));
        dto.setOriginalPrice(df.format((double) order.getOriginalPrice() / 100));
        dto.setReceivedPrice(df.format((double) order.getReceivedPrice() / 100));
        dto.setMerRate(order.getMerRate());
        dto.setCardNum(order.getCardNum());
        dto.setCustomerCode(order.getCustomerCode());
        dto.setPayServiceRate(order.getPayServiceRate());
        dto.setPayServiceType(order.getPayServiceType());
    }

    //一卡通消费 收单记录（导出）
    public DodopalResponse<String> excelConsumeOrderExport(HttpServletResponse response, ProductConsumeOrderQueryDTO prdConsumeOrderQuery) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        //金额格式化 保留两位小数
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            List<ProductConsumeOrder> prdConsumeList = productOrderMapper.findProductConsumeOrdersByList(prdConsumeOrderQuery);
            //findProductConsumeOrdersByPage
            List<ProductConsumeOrderDTO> orderList = new ArrayList<ProductConsumeOrderDTO>();

            if (CollectionUtils.isNotEmpty(prdConsumeList)) {
                for (ProductConsumeOrder order : prdConsumeList) {
                    ProductConsumeOrderDTO dto = new ProductConsumeOrderDTO();
                    beanToDto(df, order, dto);
                    //PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }

            //【写法二，使用自定义模板，需指定模板名称，并指定数据开始行】
            ExcelModel excelModel = new ExcelModel("consumeOrderList");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
            if (CollectionUtils.isNotEmpty(orderList)) {

                List<List<String>> dataList = new ArrayList<List<String>>(orderList.size());
                for (ProductConsumeOrderDTO data : orderList) {
                    List<String> rowList = new ArrayList<String>();
                    // 订单编号
                    rowList.add(data.getOrderNum());
                    //卡号
                    rowList.add(data.getCardNum());
                    //pos号
                    rowList.add(data.getPosCode());
                    // 城市
                    rowList.add(data.getCityName());
                    // 应收金额
                    rowList.add(data.getOriginalPrice());
                    // 实收金额
                    rowList.add(data.getReceivedPrice());
                    // 支付前金额
                    rowList.add(data.getBefbal());
                    // 支付后金额
                    rowList.add(data.getBlackAmt());
                    // 订单状态
                    rowList.add(data.getStates());
                    // 订单时间
                    rowList.add(DateUtils.formatDate(data.getProOrderDate(), DateUtils.DATE_FULL_STR));
                    //pos备注
                    rowList.add(data.getComments());
                    // 【添加数据行】
                    dataList.add(rowList);
                }
                // 【将数据集加入model】
                excelModel.setDataList(dataList);
            }

            return ExcelUtil.excelExport(excelModel, response);

        }
        catch (Exception e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return rep;
    }

    //一卡通消费，订单记录详情查看
    public DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderDetails(String orderNum) {
        DodopalResponse<ProductConsumeOrderDetailDTO> response = new DodopalResponse<ProductConsumeOrderDetailDTO>();
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            if (DDPStringUtil.isNotPopulated(orderNum)) {
                response.setCode(ResponseCode.PRODUCT_ORDER_NUMBER_EMPTY);
            } else {
                ProductConsumeOrderDetail order = productOrderMapper.findProductConsumeOrderDetails(orderNum);
                ProductConsumeOrderDetailDTO orderDTO = new ProductConsumeOrderDetailDTO();
                //商户名称
                orderDTO.setMerName(order.getMerName());
                //通卡公司名称
                orderDTO.setYktName(order.getYktName());
                //订单编号
                orderDTO.setOrderNum(order.getOrderNum());
                //业务城市
                orderDTO.setCityName(order.getCityName());
                //pos号
                orderDTO.setPosCode(order.getPosCode());
                //订单状态
                orderDTO.setStates(order.getStates());
                //内部状态
                orderDTO.setInnerStates(order.getInnerStates());
                //卡号
                orderDTO.setCardNum(order.getCardNum());

                //商户服务费率类型 单笔
                if (ServiceRateTypeEnum.SINGLE_AMOUNT.getName().equals(order.getServiceRateType())) {
                    //商户服务费率
                    orderDTO.setServiceRate(order.getServiceRate() / 100);
                } else {
                    //商户服务费率
                    orderDTO.setServiceRate(order.getServiceRate());
                }
                //商户服务费率类型
                orderDTO.setServiceRateType(order.getServiceRateType());
                //商户折扣
                orderDTO.setMerDiscount(order.getMerDiscount());
                
                orderDTO.setMerRate((1000-order.getMerRate())/100);//折扣
                //操作员
                orderDTO.setUserName(order.getUserName());
                //订单时间
                orderDTO.setProOrderDate(order.getProOrderDate());
                //订单日期
                orderDTO.setProOrderDay(order.getProOrderDay());

                orderDTO.setBefbal(order.getBefbal() == null ? order.getBefbal() : df.format(Double.valueOf(order.getBefbal()) / 100));
                orderDTO.setBlackAmt(order.getBlackAmt() == null ? order.getBlackAmt() : df.format(Double.valueOf(order.getBlackAmt()) / 100));
                orderDTO.setOriginalPrice(df.format((double) order.getOriginalPrice() / 100));
                orderDTO.setReceivedPrice(df.format((double) order.getReceivedPrice() / 100));
                orderDTO.setComments(order.getComments());
                orderDTO.setPosComments(order.getPosComments());//pos备注
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(orderDTO);
            }
        }
        catch (Exception e) {
            logger.error("产品库中公交卡消费订单查看详细信息出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_QUERY_ERROR);
        }
        return response;
    }

    /**
     * OSS异常充值订单查询分页 add by shenXiang 2015-11-05
     */
    @Override
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findExceptionProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            if (CommonConstants.SYSTEM_NAME_OSS.equals(prdOrderQuery.getSource()) || prdOrderQuery.getUserCode() != null) {
                List<ProductOrder> orders = productOrderMapper.findExceptionProductOrderByPage(prdOrderQuery);
                List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
                if (CollectionUtils.isNotEmpty(orders)) {
                    for (ProductOrder order : orders) {
                        ProductOrderDTO dto = convertPrdOrderToDto(order);
                        orderList.add(dto);
                    }
                }
                DodopalDataPage<ProductOrderDTO> pages = new DodopalDataPage<ProductOrderDTO>(prdOrderQuery.getPage(), orderList);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(pages);
            } else {
                response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_USER_CODE_ERROR);
            }
        }
        catch (Exception e) {
            logger.error("OSS异常充值订单分页查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    //查询 一卡通消费  收单记录(分页)
    @Transactional(readOnly = true)
    @Override
    public DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrdersExptionByPage(ProductConsumeOrderQueryDTO prdConsumeOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>>();
        //金额格式化 保留两位小数
        DecimalFormat df = new DecimalFormat("0.00");

        try {
            List<ProductConsumeOrder> prdConsumeorders = productOrderMapper.findProductConsumeOrdersExptionByPage(prdConsumeOrderQuery);
            List<ProductConsumeOrderDTO> orderList = new ArrayList<ProductConsumeOrderDTO>(prdConsumeorders.isEmpty() ? prdConsumeorders.size() : 0);

            if (CollectionUtils.isNotEmpty(prdConsumeorders)) {
                for (ProductConsumeOrder order : prdConsumeorders) {
                    ProductConsumeOrderDTO dto = new ProductConsumeOrderDTO();
                    beanToDto(df, order, dto);
                    //PropertyUtils.copyProperties(dto, order);
                    orderList.add(dto);
                }
            }
            DodopalDataPage<ProductConsumeOrderDTO> pages = new DodopalDataPage<ProductConsumeOrderDTO>(prdConsumeOrderQuery.getPage(), orderList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中公交卡 消费订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 充值异常导出
     */
    @Override
    public DodopalResponse<List<ProductOrderDTO>> findProductExceptionOrdersForExport(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductOrderDTO>> response = new DodopalResponse<List<ProductOrderDTO>>();
        try {
            if (CommonConstants.SYSTEM_NAME_OSS.equals(prdOrderQuery.getSource()) || prdOrderQuery.getUserCode() != null) {
                List<ProductOrder> orders = productOrderMapper.findProductExceptionOrdersForExport(prdOrderQuery);
                List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
                if (CollectionUtils.isNotEmpty(orders)) {
                    if (orders.size() > ExcelUtil.EXPORT_MAX_COUNT) {
                        response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                        return response;
                    } else {
                        for (ProductOrder order : orders) {
                            ProductOrderDTO dto = convertPrdOrderToDtoForExport(order);
                            orderList.add(dto);
                        }
                        response.setCode(ResponseCode.SUCCESS);
                        response.setResponseEntity(orderList);
                    }
                } else {
                    response.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
                }
            } else {
                response.setCode(ResponseCode.PRODUCT_ORDER_DETAIL_USER_CODE_ERROR);
            }
        }
        catch (Exception e) {
            logger.error("OSS异常充值订单导出出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<List<ProductConsumeOrderDTO>> excelExceptionProductOrderExport(ProductConsumeOrderQueryDTO prdOrderQuery) {
        DodopalResponse<List<ProductConsumeOrderDTO>> response = new DodopalResponse<List<ProductConsumeOrderDTO>>();
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            List<ProductConsumeOrder> orders = productOrderMapper.excelExceptionProductOrderExport(prdOrderQuery);
            List<ProductConsumeOrderDTO> orderList = new ArrayList<ProductConsumeOrderDTO>();
            if (CollectionUtils.isNotEmpty(orders)) {
                if (orders.size() > ExcelUtil.EXPORT_MAX_COUNT) {
                    response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                    return response;
                } else {
                    for (ProductConsumeOrder order : orders) {
                        ProductConsumeOrderDTO dto = new ProductConsumeOrderDTO();
                        beanToDtoForExport(df, order, dto);
                        orderList.add(dto);
                    }
                    response.setCode(ResponseCode.SUCCESS);
                    response.setResponseEntity(orderList);
                }
            } else {
                response.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
            }
        }
        catch (Exception e) {
            logger.error("OSS异常充值订单导出出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * OSS导出查询 一卡通消费 收单记录
     */
    @Override
    public DodopalResponse<List<ProductConsumerOrderForExport>> getConsumerOrderListForExportExcel(ProductConsumeOrderQueryDTO prdOrderQuery) {

        DodopalResponse<List<ProductConsumerOrderForExport>> response = new DodopalResponse<List<ProductConsumerOrderForExport>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            int count = productOrderMapper.getCountForConsumerOrderExportExcel(prdOrderQuery);
            if (count > ExcelUtil.EXPORT_MAX_COUNT) {
                response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                return response;
            }
            List<ProductConsumerOrderForExport> byList = productOrderMapper.getListForConsumerOrderExportExcel(prdOrderQuery);
            response.setResponseEntity(byList);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("产品库中公交卡 消费订单导出查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<DodopalDataPage<ProductOrderDTO>> findRechargeProductOrderByPage(ProductOrderQueryDTO prdOrderQuery) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
          
                List<ProductOrder> orders = productOrderMapper.findRechargeProductOrderByPage(prdOrderQuery);
                List<ProductOrderDTO> orderList = new ArrayList<ProductOrderDTO>();
                if (CollectionUtils.isNotEmpty(orders)) {
                    for (ProductOrder order : orders) {
                        ProductOrderDTO dto = convertPrdOrderToDto(order);
                        orderList.add(dto);
                    }
                }
                DodopalDataPage<ProductOrderDTO> pages = new DodopalDataPage<ProductOrderDTO>(prdOrderQuery.getPage(), orderList);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(pages);
        }
        catch (Exception e) {
            logger.error("产品库中公交卡充值订单查询出错", e);
            response.setCode(ResponseCode.PRODUCT_ORDER_QUERY_ERROR);
        }
        return response;
    }

}
