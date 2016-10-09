package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.LoadFlagEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.oss.business.bean.ExceptionOrderHandleDto;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.dao.AccountChangeMapper;
import com.dodopal.oss.business.dao.AccountFundMapper;
import com.dodopal.oss.business.dao.AccountMapper;
import com.dodopal.oss.business.dao.ClearingBasicDataMapper;
import com.dodopal.oss.business.dao.LoadOrderMapper;
import com.dodopal.oss.business.dao.PayTraTransactionMapper;
import com.dodopal.oss.business.dao.ProductOrderMapper;
import com.dodopal.oss.business.model.Account;
import com.dodopal.oss.business.model.AccountChange;
import com.dodopal.oss.business.model.AccountFund;
import com.dodopal.oss.business.model.ClearingBasicData;
import com.dodopal.oss.business.model.PayTraTransaction;
import com.dodopal.oss.business.model.ProductOrder;
import com.dodopal.oss.business.service.RechargeOrderExceptionHandleService;
import com.dodopal.oss.delegate.PaymentDelegate;

/**
 * 一卡通充值订单异常处理ServiceImpl
 * @author shenXiang
 */
@Service
public class RechargeOrderExceptionHandleServiceImpl implements RechargeOrderExceptionHandleService {

    private Logger logger = LoggerFactory.getLogger(RechargeOrderExceptionHandleServiceImpl.class);

    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private LoadOrderMapper loadOrderMapper;
    @Autowired
    private PayTraTransactionMapper payTraTransactionMapper;
    @Autowired
    private ClearingBasicDataMapper clearingBasicDataMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFundMapper accountFundMapper;
    @Autowired
    private AccountChangeMapper accountChangeMapper;

    @Autowired
    private PaymentDelegate paymentDelegate;
    
    /**
     * 充值订单异常处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DodopalResponse<String> exceptionHandle(ExceptionOrderHandleDto orderHandleDto, String operatorName) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS); 

        String orderNum = orderHandleDto.getOrderNum();// 订单编号
        String judgeResult = orderHandleDto.getJudgeResult();// 处理结果（0：失败——需要执行资金解冻流程；1：成功——需要执行资金扣款流程）
        
        
        // ****************************************     1    查询充值订单，获取订单状态          ****************************************//
        
        ProductOrder productOrder = productOrderMapper.getProductOrderByProOrderNum(orderNum);
        
        if (productOrder == null) {
            throw new DDPException(ResponseCode.OSS_RECHARGE_ORDER_NULL, "订单编号："+orderNum+"，该订单记录不存在。");
        }
        productOrder.setUpdateUser(orderHandleDto.getUpdateUser());
        
        // ****************************************     2    根据订单状态，做异常区分，分别处理         *********************************//
            
        if (RechargeOrderStatesEnum.PAID.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.ACCOUNT_RECHARGE_FAILED.getCode().equals(productOrder.getProInnerState())
            && RechargeOrderInternalStatesEnum.ONLINE_BANKING_PAY_SUCCESS.getCode().equals(productOrder.getProBeforInnerState())) {
            
            // 订单状态为以下状态时，执行对应用户账户加值处理。
            // 已付款_账户充值失败_网银支付成功
            response = this.accountRechargeHandle(productOrder, operatorName);
            
        } else if (RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_FAILED.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.CARD_RECHARGE_FAILED.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.FUND_UNBOLCK_FAILED.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.FUND_FROZEN_SUCCESS.getCode().equals(productOrder.getProInnerState())
            ) {
            
            // 订单状态为以下状态时，执行资金解冻流程。
            // 充值失败_申请充值密钥失败    OR  充值失败_卡片充值失败     OR  充值失败_资金解冻失败     OR  充值中_冻结资金成功
            response = this.accountUnfreezeHandle(productOrder, operatorName);
            
        } else if (RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.CARD_RECHARGE_SUCCESS.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.FUND_DEDUCT_FAILED.getCode().equals(productOrder.getProInnerState())
            ) {
            
            // 订单状态为以下状态时，执行资金扣款流程。
            // 充值成功_卡片充值成功    OR  充值成功_资金扣款失败
            response = this.accountDeductHandle(productOrder, operatorName);
            
        } else if (RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.RECHARGE_RESULT_UNKNOWN.getCode().equals(productOrder.getProInnerState())
            ) {
            
            // 订单状态为以下状态时，作为可疑订单处理（OSS操作员判断充值是否成功后，执行相应处理）：
            // 充值中_申请充值密钥成功     OR  充值可疑_上传充值未知的结果
            if ("0".equals(judgeResult)) {
                
                // 经人工判断，该订单充值失败，需要资金解冻
                response = this.accountUnfreezeHandle(productOrder, operatorName);
            } else if ("1".equals(judgeResult)) {
                
                // 经人工判断，该订单充值成功，需要资金扣款
                response = this.accountDeductHandle(productOrder, operatorName);
            }
        }
        
        return response;
    }

    /**
     * 账户加值异常处理（网银支付时，用户账户未加值成功：订单状态：已付款_账户充值失败_网银支付成功）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DodopalResponse<String> accountRechargeHandle(ProductOrder productOrder, String operatorName) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS); 
        
        // *******************************       1.  查询交易流水判断“网银支付成功”是否成功             ***********************************//
        
        String orderNum = productOrder.getProOrderNum();// 充值订单编号
        String merOrUserCode = productOrder.getMerCode();// 用户号
        String userType = productOrder.getMerUserType();// 用户类型
        if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
            userType = MerUserTypeEnum.PERSONAL.getCode();
            merOrUserCode = productOrder.getUserCode();
        } else {
            userType = MerUserTypeEnum.MERCHANT.getCode();
            merOrUserCode = productOrder.getMerCode();
        }
        String tranType = TranTypeEnum.ACCOUNT_RECHARGE.getCode();// 交易类型：1：账户充值
        String businessType = RateCodeEnum.YKT_RECHARGE.getCode();// 业务类型：01：一卡通充值
        
        PayTraTransaction oldPayTransaction = payTraTransactionMapper.getPayTraTransactionByOrder(orderNum, merOrUserCode, userType, tranType, businessType);
        if (oldPayTransaction == null) {
            
            throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_NULL, "异常处理充值订单，账户加值：订单编号："+orderNum+"，对应的交易流水不存在。");
            
        } else if (!TranOutStatusEnum.HAS_PAID.getCode().equals(oldPayTransaction.getTranOutStatus()) 
            || TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(oldPayTransaction.getTranInStatus())) {
            StringBuffer logBuffer = new StringBuffer();
            logBuffer.append("异常处理充值订单，账户加值。");
            logBuffer.append("订单编号："+orderNum+"，对应的交易流水状态不正。");
            logBuffer.append("外部状态："+oldPayTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(oldPayTransaction.getTranOutStatus()).getName());
            logBuffer.append("；内部状态："+oldPayTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(oldPayTransaction.getTranInStatus()).getName());
            logger.error(logBuffer.toString());
            
            throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_STATE_ERROR, logBuffer.toString());
        }
        
        //************************************************      2.  账户加值            *************************************************//
        
        String tranCode = oldPayTransaction.getTranCode();// 交易流水号
        
        // 2.1  查询主账户信息：（根据客户类型，客户编号）
        Account account = accountMapper.getAccountInfo(oldPayTransaction.getUserType(), oldPayTransaction.getMerOrUserCode());
        if (account == null) {
            
            throw new DDPException(ResponseCode.OSS_ACCOUNT_NULL, "交易流水号：" + tranCode + "，客户类型：" + oldPayTransaction.getUserType() + "，客户编号：" + oldPayTransaction.getMerOrUserCode() + "对应的主账户信息不存在。");
        }

        // 2.2  查询资金账户账户信息：（根据主账户编号，账户类型：资金账户）
        AccountFund oldAccountFund = accountFundMapper.getFundAccountInfo(account.getAccountCode(), FundTypeEnum.FUND.getCode());
        if (oldAccountFund == null) {
            
            throw new DDPException(ResponseCode.OSS_ACCOUNT_FUND_NULL, "交易流水号：" + tranCode + "，主账户编号：" + account.getAccountCode() + "对应的资金账户信息不存在。");
        }
        
        // 2.3  追加检查（处理过后）总余额是否超过数据库限额
        long amt = oldAccountFund.getTotalBalance() + oldPayTransaction.getAmountMoney();
        if (amt > OSSConstants.TOTALBALANCE_MAX) {
            
            throw new DDPException(ResponseCode.OSS_TOTAL_AMOUNT_OUTNUMBER_ERROR, "交易流水号:" + tranCode + "对应账户总余额超过数据库可容纳的最大限额");
        }
        
        // 2.4  追加检查（处理过后）累计总金额是否超过数据库限额
        long sumAmt = oldAccountFund.getSumTotalAmount() + oldPayTransaction.getAmountMoney();
        if (sumAmt > OSSConstants.TOTALBALANCE_SUM_MAX) {
            
            throw new DDPException(ResponseCode.OSS_SUM_TOTAL_AMOUNT_OUTNUMBER_ERROR, "交易流水号:" + tranCode + "对应账户累计总金额超过数据库可容纳的最大限额");
        }
        
        // 2.5  新增资金变动表记录：（根据交易流水号，变动类型：账户充值，资金类别：资金账户）
        boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(tranCode, AccTranTypeEnum.ACC_RECHARGE.getCode(), FundTypeEnum.FUND.getCode());
        if (exsit) {
            
            throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_RECORD_EXSIT, "交易流水号:" + tranCode + "对应的账户资金加值变动记录已存在。");
        }
        AccountChange accountChange = new AccountChange();
        accountChange.setChangeAmount(oldPayTransaction.getAmountMoney());// 变动金额
        accountChange.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金账户号
        accountChange.setFundType(FundTypeEnum.FUND.getCode());// 资金类别
        accountChange.setBeforeChangeAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=当前账户总余额
        accountChange.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额==当前账户可用余额
        accountChange.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额==当前账户冻结余额
        accountChange.setChangeType(AccTranTypeEnum.ACC_RECHARGE.getCode());// 变动类型
        accountChange.setTranCode(tranCode);// 交易流水号
        accountChange.setCreateUser(productOrder.getUpdateUser());// 操作员ID
        accountChangeMapper.addAccountChange(accountChange);

        // 2.6  更新资金账户账户信息
        AccountFund accountFund = new AccountFund();
        accountFund.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额=可用余额
        accountFund.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额=冻结金额
        accountFund.setBeforeChangeTotalAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=账户总余额
        accountFund.setFrozenAmount(oldAccountFund.getFrozenAmount());// 冻结金额
        accountFund.setAvailableBalance(oldAccountFund.getAvailableBalance() + oldPayTransaction.getAmountMoney());// 可用金额
        accountFund.setTotalBalance(oldAccountFund.getTotalBalance() + oldPayTransaction.getAmountMoney());// 总余额
        accountFund.setSumTotalAmount(oldAccountFund.getSumTotalAmount() + oldPayTransaction.getAmountMoney());// 累计总金额
        accountFund.setLastChangeAmount(oldPayTransaction.getAmountMoney());// 最近一次变动金额
        accountFund.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金授信编号
        accountFund.setUpdateUser(productOrder.getUpdateUser());// 操作员ID
        accountFundMapper.updateFundAccount(accountFund);
        
        
        //******************    3、处理交易流水表，修改交易状态(已支付，账户加值成功)   ********************//
        
        PayTraTransaction payTransaction = new PayTraTransaction();
        payTransaction.setTranCode(tranCode);
        payTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
        payTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
        payTransaction.setUpdateUser(productOrder.getUpdateUser());
        StringBuffer transactionCommentsBF = new StringBuffer();
        transactionCommentsBF.append("处理一卡通充值异常：网银支付成功，但账户加值失败。");
        transactionCommentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户加值成功，更改交易流水状态。");
        transactionCommentsBF.append("处理前状态：");
        transactionCommentsBF.append(oldPayTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(oldPayTransaction.getTranOutStatus()).getName());
        transactionCommentsBF.append("；处理前内部状态：");
        transactionCommentsBF.append(oldPayTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(oldPayTransaction.getTranInStatus()).getName());
        transactionCommentsBF.append("；处理后状态：");
        transactionCommentsBF.append(payTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(payTransaction.getTranOutStatus()).getName());
        transactionCommentsBF.append("；处理后内部状态：");
        transactionCommentsBF.append(payTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(payTransaction.getTranInStatus()).getName());
        payTransaction.setComments(transactionCommentsBF.toString());
        payTraTransactionMapper.updateTranStatus(payTransaction);
        
        
        //******************    4、更改订单状态（已支付、账户充值成功、账户充值失败）   ********************//
        
        ProductOrder newProductOrder = new ProductOrder();
        newProductOrder.setProOrderNum(productOrder.getProOrderNum());
        newProductOrder.setProOrderState(RechargeOrderStatesEnum.PAID.getCode());// 主订单状态：已支付
        newProductOrder.setProBeforInnerState(productOrder.getProInnerState());;// 前内部订单状态：账户充值失败
        newProductOrder.setProInnerState(RechargeOrderInternalStatesEnum.ACCOUNT_RECHARGE_SUCCESS.getCode());// 内部订单状态：账户充值成功
        newProductOrder.setBlackAmt(productOrder.getBefbal());// 充值后金额
        newProductOrder.setProSuspiciousState(SuspiciousStatesEnum.SUSPICIOUS_NO.getCode());// 可疑状态：不可疑
        newProductOrder.setUpdateUser(productOrder.getUpdateUser());
        StringBuffer orderCommentsBF = new StringBuffer();
        orderCommentsBF.append("处理一卡通充值异常：网银支付成功，但账户加值失败。");
        orderCommentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户加值成功，更改订单状态。");
        orderCommentsBF.append("处理前主状态：");
        orderCommentsBF.append(productOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrder.getProOrderState()).getName());
        orderCommentsBF.append("；处理前内部状态：");
        orderCommentsBF.append(productOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProInnerState()).getName());
        orderCommentsBF.append("；处理前前内部状态：");
        orderCommentsBF.append(productOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProBeforInnerState()).getName());
        orderCommentsBF.append("；处理后主状态：");
        orderCommentsBF.append(newProductOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(newProductOrder.getProOrderState()).getName());
        orderCommentsBF.append("；处理后内部状态：");
        orderCommentsBF.append(newProductOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProInnerState()).getName());
        orderCommentsBF.append("；处理后前内部状态：");
        orderCommentsBF.append(newProductOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProBeforInnerState()).getName());
        newProductOrder.setComments(orderCommentsBF.toString());
        productOrderMapper.updateOrderStatusAfterHandleException(newProductOrder);
        
        return response;
    }

    /**
     * 资金解冻流程
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DodopalResponse<String> accountUnfreezeHandle(ProductOrder productOrder, String operatorName) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS); 
        
        // 判断该订单是否为可疑订单
        String suspiciousStates = SuspiciousStatesEnum.SUSPICIOUS_NO.getCode();// 不可疑
        
        if (RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.RECHARGE_RESULT_UNKNOWN.getCode().equals(productOrder.getProInnerState())
            ) {
            suspiciousStates = SuspiciousStatesEnum.SUSPICIOUS_HANDLE.getCode();// 可疑已处理
        }
        
        String orderNum = productOrder.getProOrderNum();// 充值订单编号
        String merOrUserCode = productOrder.getMerCode();// 用户号
        String userType = productOrder.getMerUserType();// 用户类型
        if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
            userType = MerUserTypeEnum.PERSONAL.getCode();
            merOrUserCode = productOrder.getUserCode();
        } else {
            userType = MerUserTypeEnum.MERCHANT.getCode();
            merOrUserCode = productOrder.getMerCode();
        }
        String tranType = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();// 交易类型：3:账户消费
        String businessType = RateCodeEnum.YKT_RECHARGE.getCode();// 业务类型：01：一卡通充值
        
        
        if (LoadFlagEnum.LOAD_YES.getCode().equals(productOrder.getLoadFlag())) {
            
            //*********************     1.1 圈存订单处理：更改圈存订单状态           ***********************//
            
            if (StringUtils.isNotBlank(productOrder.getLoadOrderNum())) {
                loadOrderMapper.updateLoadOrderStatus(LoadOrderStatusEnum.ORDER_FAILURE.getCode(), productOrder.getLoadOrderNum(), operatorName); 
            }
            
            //*********************     1.2 圈存订单处理：更改产品库订单状态           ***********************//
            
            if (!RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState())) {
                ProductOrder newProductOrder = new ProductOrder();
                newProductOrder.setProOrderNum(productOrder.getProOrderNum());
                newProductOrder.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());// 主订单状态：充值失败
                newProductOrder.setProInnerState(RechargeOrderInternalStatesEnum.CARD_RECHARGE_FAILED.getCode());// 内部订单状态：卡片充值失败
                newProductOrder.setProBeforInnerState(productOrder.getProInnerState());;// 前内部订单状态：此次修改之前的内部状态
                newProductOrder.setBlackAmt(productOrder.getBefbal());// 充值后金额
                newProductOrder.setProSuspiciousState(suspiciousStates);
                newProductOrder.setUpdateUser(productOrder.getUpdateUser());
                StringBuffer orderCommentsBF = new StringBuffer();
                orderCommentsBF.append("处理一卡通充值异常：");
                orderCommentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理圈存产品库异常订单，更改订单状态。");
                orderCommentsBF.append("处理前主状态：");
                orderCommentsBF.append(productOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrder.getProOrderState()).getName());
                orderCommentsBF.append("；处理前内部状态：");
                orderCommentsBF.append(productOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProInnerState()).getName());
                orderCommentsBF.append("；处理前前内部状态：");
                orderCommentsBF.append(productOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProBeforInnerState()).getName());
                orderCommentsBF.append("；处理后主状态：");
                orderCommentsBF.append(newProductOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(newProductOrder.getProOrderState()).getName());
                orderCommentsBF.append("；处理后内部状态：");
                orderCommentsBF.append(newProductOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProInnerState()).getName());
                orderCommentsBF.append("；处理后前内部状态：");
                orderCommentsBF.append(newProductOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProBeforInnerState()).getName());
                newProductOrder.setComments(orderCommentsBF.toString());
                newProductOrder.setProSuspiciousExplain(newProductOrder.getComments());
                productOrderMapper.updateOrderStatusAfterHandleException(newProductOrder);
            }
        } else {
            
            //*********************     2. 非圈存订单处理:账户处理资金解冻           ***********************//
            
            // 2.1  获取交易流水 
            PayTraTransaction oldPayTransaction = payTraTransactionMapper.getPayTraTransactionByOrder(orderNum, merOrUserCode, userType, tranType, businessType);
            if (oldPayTransaction == null) {
                
                throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_NULL, "异常处理充值订单，资金解冻：充值订单编号："+orderNum+"，对应的交易流水不存在。");
                
            } else if (!(TranInStatusEnum.ACCOUNT_FROZEN_SUCCESS.getCode().equals(oldPayTransaction.getTranInStatus())
                || TranInStatusEnum.ACCOUNT_UNFROZEN_FAIL.getCode().equals(oldPayTransaction.getTranInStatus()))) {
                
                throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_STATE_ERROR, "异常处理充值订单，资金解冻：充值订单编号："+orderNum+"，对应的交易流水状态不正（既不是账户冻结成功，也不是账户解冻失败）。");
                
            }
            
            String tranCode = oldPayTransaction.getTranCode();// 交易流水号
            
            // 2.2  查询主账户信息：（根据客户类型，客户编号）
            Account account = accountMapper.getAccountInfo(oldPayTransaction.getUserType(), oldPayTransaction.getMerOrUserCode());
            if (account == null) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_NULL, "交易流水号：" + tranCode + "，客户类型：" + oldPayTransaction.getUserType() + "，客户编号：" + oldPayTransaction.getMerOrUserCode() + "对应的主账户信息不存在。");
                
            }

            // 2.3  查询资金账户账户信息：（根据主账户编号，账户类型：资金账户）
            AccountFund oldAccountFund = accountFundMapper.getFundAccountInfo(account.getAccountCode(), FundTypeEnum.FUND.getCode());
            if (oldAccountFund == null) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_FUND_NULL, "交易流水号：" + tranCode + "，主账户编号：" + account.getAccountCode() + "对应的资金账户信息不存在。");
                
            }
            
            // 2.4  查询授信账户账户信息：（根据主账户编号，账户类型：授信账户）
            AccountFund oldAccountAuthorized = accountFundMapper.getFundAccountInfo(account.getAccountCode(), FundTypeEnum.AUTHORIZED.getCode());
            
            
            // *********************************   2.5  遍历冻结记录，逐一解冻        START     ***************************//

            // 根据（交易流水号）和（变动类型：冻结）的查询资金变动记录
            List<AccountChange> oldAccountChangeList = accountChangeMapper.getAccountChangeRecordList(tranCode, AccTranTypeEnum.ACC_FREEZE.getCode());
            
            if (CollectionUtils.isEmpty(oldAccountChangeList)) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_FREEZE_RECORD_NULL, "交易流水号：" + tranCode + "账户资金解冻处理前，验证账户冻结记录是否存在：对应的资金冻结记录不存在。");
                
            } else if (oldAccountChangeList.size() == 1 && FundTypeEnum.AUTHORIZED.getCode().equals(oldAccountChangeList.get(0).getFundType())) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_FREEZE_RECORD_NULL, "交易流水号：" + tranCode + "账户资金解冻处理前，验证账户冻结记录是否存在：账户类型为资金的账户冻结记录不存在。");
                
            }
            
           
            // 遍历冻结记录，解冻前CHECK数据
            for (AccountChange oldAccountChange : oldAccountChangeList) {
                
                //  资金类型账户
                if (FundTypeEnum.FUND.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 追加检查（解冻处理过后）可用余额是否超过数据库限额
                    long newAvailableAmt = oldAccountFund.getAvailableBalance() + oldAccountChange.getChangeAmount();
                    if (newAvailableAmt > OSSConstants.TOTALBALANCE_MAX) {
                        
                        throw new DDPException(ResponseCode.OSS_AVAILABLE_BALANCE_OUTNUMBER_ERROR, "交易流水号:" + tranCode + "对应资金类型的账户可用余额超过数据库可容纳的最大限额");
                        
                    }
                    
                    // 追加检查（解冻处理过后）冻结金额是否为负值
                    long newFrozenAmt = oldAccountFund.getFrozenAmount() - oldAccountChange.getChangeAmount();
                    if (newFrozenAmt < 0) {
                        
                        throw new DDPException(ResponseCode.OSS_FROZEN_AMOUNT_NOT_ENOUGH_ERROR, "交易流水号:" + tranCode + "对应资金账户冻结金额小于解冻金额");
                        
                    }
                    
                    // 检查资金变动表记录是否存在：（根据交易流水号，变动类型：解冻，资金类别：资金账户）
                    boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(tranCode, AccTranTypeEnum.ACC_UNFREEZE.getCode(), FundTypeEnum.FUND.getCode());
                    if (exsit) {
                        
                        throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_UNFREEZE_RECORD_EXSIT, "交易流水号:" + tranCode + "对应资金账户资金解冻变动记录已存在。");
                        
                    }
                    
                } else if (FundTypeEnum.AUTHORIZED.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 授信类型账户
                    if (oldAccountAuthorized == null) {
                        
                        throw new DDPException(ResponseCode.OSS_ACCOUNT_AUTHORIZED_NULL, "交易流水号：" + tranCode + "，主账户编号：" + account.getAccountCode() + "授信账户资金变动记录存在，但对应的授信账户信息不存在。");
                        
                    }
                    
                    // 追加检查（解冻处理过后）可用余额是否超过数据库限额
                    long newAvailableAmt = oldAccountAuthorized.getAvailableBalance() + oldAccountChange.getChangeAmount();
                    if (newAvailableAmt > OSSConstants.TOTALBALANCE_MAX) {
                        
                        throw new DDPException(ResponseCode.OSS_AVAILABLE_BALANCE_OUTNUMBER_ERROR, "交易流水号:" + tranCode + "对应授信账户可用余额超过数据库可容纳的最大限额");
                        
                    }
                    
                    // 追加检查（解冻处理过后）冻结金额是否为负值
                    long newFrozenAmt = oldAccountAuthorized.getFrozenAmount() - oldAccountChange.getChangeAmount();
                    if (newFrozenAmt < 0) {
                        
                        throw new DDPException(ResponseCode.OSS_FROZEN_AMOUNT_NOT_ENOUGH_ERROR, "交易流水号:" + tranCode + "对应授信账户冻结金额小于解冻金额");
                        
                    }
                    
                    // 检查资金变动表记录是否存在：（根据交易流水号，变动类型：解冻，资金类别：授信账户）
                    boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(tranCode, AccTranTypeEnum.ACC_UNFREEZE.getCode(), FundTypeEnum.AUTHORIZED.getCode());
                    if (exsit) {
                        
                        throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_UNFREEZE_RECORD_EXSIT, "交易流水号:" + tranCode + "对应授信账户资金解冻变动记录已存在。");
                        
                    }
                }
            }
            
            // 遍历冻结记录，逐一解冻
            for (AccountChange oldAccountChange : oldAccountChangeList) {
                
                if (FundTypeEnum.FUND.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 新增资金变动表记录：（交易流水号，变动类型：解冻）
                    AccountChange accountChange = new AccountChange();
                    accountChange.setChangeAmount(oldAccountChange.getChangeAmount());// 变动金额
                    accountChange.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金账户号
                    accountChange.setFundType(FundTypeEnum.FUND.getCode());// 资金类别
                    accountChange.setBeforeChangeAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=当前账户总余额
                    accountChange.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额==当前账户可用余额
                    accountChange.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额==当前账户冻结余额
                    accountChange.setChangeType(AccTranTypeEnum.ACC_UNFREEZE.getCode());// 变动类型
                    accountChange.setTranCode(tranCode);// 交易流水号
                    accountChange.setCreateUser(productOrder.getUpdateUser());// 操作员ID
                    accountChangeMapper.addAccountChange(accountChange);
                    
                    // 更新资金账户账户信息
                    AccountFund accountFund = new AccountFund();
                    accountFund.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额=可用余额
                    accountFund.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额=冻结金额
                    accountFund.setBeforeChangeTotalAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=账户总余额
                    accountFund.setFrozenAmount(oldAccountFund.getFrozenAmount() - oldAccountChange.getChangeAmount());// 冻结金额
                    accountFund.setAvailableBalance(oldAccountFund.getAvailableBalance() + oldAccountChange.getChangeAmount());// 可用金额
                    accountFund.setTotalBalance(oldAccountFund.getTotalBalance());// 总余额
                    accountFund.setSumTotalAmount(oldAccountFund.getSumTotalAmount());// 累计总金额
                    accountFund.setLastChangeAmount(oldAccountChange.getChangeAmount());// 最近一次变动金额
                    accountFund.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金授信编号
                    accountFund.setUpdateUser(productOrder.getUpdateUser());// 操作员ID
                    accountFundMapper.updateFundAccount(accountFund);
                    
                } else if (FundTypeEnum.AUTHORIZED.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 新增资金变动表记录：（交易流水号，变动类型：解冻）
                    AccountChange accountChange = new AccountChange();
                    accountChange.setChangeAmount(oldAccountChange.getChangeAmount());// 变动金额
                    accountChange.setFundAccountCode(oldAccountAuthorized.getFundAccountCode());// 资金账户号
                    accountChange.setFundType(FundTypeEnum.AUTHORIZED.getCode());// 资金类别
                    accountChange.setBeforeChangeAmount(oldAccountAuthorized.getTotalBalance());// 变动前账户总余额=当前账户总余额
                    accountChange.setBeforeChangeAvailableAmount(oldAccountAuthorized.getAvailableBalance());// 变动前可用余额==当前账户可用余额
                    accountChange.setBeforeChangeFrozenAmount(oldAccountAuthorized.getFrozenAmount());// 变动前冻结金额==当前账户冻结余额
                    accountChange.setChangeType(AccTranTypeEnum.ACC_UNFREEZE.getCode());// 变动类型
                    accountChange.setTranCode(tranCode);// 交易流水号
                    accountChange.setCreateUser(productOrder.getUpdateUser());// 操作员ID
                    accountChangeMapper.addAccountChange(accountChange);
                    
                    // 更新授信账户账户信息
                    AccountFund accountFund = new AccountFund();
                    accountFund.setBeforeChangeAvailableAmount(oldAccountAuthorized.getAvailableBalance());// 变动前可用余额=可用余额
                    accountFund.setBeforeChangeFrozenAmount(oldAccountAuthorized.getFrozenAmount());// 变动前冻结金额=冻结金额
                    accountFund.setBeforeChangeTotalAmount(oldAccountAuthorized.getTotalBalance());// 变动前账户总余额=账户总余额
                    accountFund.setFrozenAmount(oldAccountAuthorized.getFrozenAmount() - oldAccountChange.getChangeAmount());// 冻结金额
                    accountFund.setAvailableBalance(oldAccountAuthorized.getAvailableBalance() + oldAccountChange.getChangeAmount());// 可用金额
                    accountFund.setTotalBalance(oldAccountAuthorized.getTotalBalance());// 总余额
                    accountFund.setSumTotalAmount(oldAccountAuthorized.getSumTotalAmount());// 累计总金额
                    accountFund.setLastChangeAmount(oldAccountChange.getChangeAmount());// 最近一次变动金额
                    accountFund.setFundAccountCode(oldAccountAuthorized.getFundAccountCode());// 资金授信编号
                    accountFund.setUpdateUser(productOrder.getUpdateUser());// 操作员ID
                    accountFundMapper.updateFundAccount(accountFund);
                }
            }
            
            
            //******************    3、非圈存订单处理:处理交易流水表，修改交易状态(已关闭、资金解冻成功)   ********************//
            
            PayTraTransaction payTransaction = new PayTraTransaction();
            payTransaction.setTranCode(tranCode);
            payTransaction.setTranOutStatus(TranOutStatusEnum.CLOSE.getCode());
            payTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_UNFROZEN_SUCCESS.getCode());
            payTransaction.setUpdateUser(productOrder.getUpdateUser());
            StringBuffer commentsBF = new StringBuffer();
            commentsBF.append("处理一卡通充值异常：");
            commentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户资金解冻成功，更改交易流水状态。");
            commentsBF.append("处理前状态：");
            commentsBF.append(oldPayTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(oldPayTransaction.getTranOutStatus()).getName());
            commentsBF.append("；处理前内部状态：");
            commentsBF.append(oldPayTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(oldPayTransaction.getTranInStatus()).getName());
            commentsBF.append("；处理后状态：");
            commentsBF.append(payTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(payTransaction.getTranOutStatus()).getName());
            commentsBF.append("；处理后内部状态：");
            commentsBF.append(payTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(payTransaction.getTranInStatus()).getName());
            payTransaction.setComments(commentsBF.toString());
            payTraTransactionMapper.updateTranStatus(payTransaction);
            
            
            // *****************   4、非圈存订单处理:更改订单状态（充值失败、资金解冻成功、此次修改之前的内部状态）  ************//
            
            ProductOrder newProductOrder = new ProductOrder();
            newProductOrder.setProOrderNum(productOrder.getProOrderNum());
            newProductOrder.setProOrderState(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode());// 主订单状态：充值失败
            newProductOrder.setProInnerState(RechargeOrderInternalStatesEnum.FUND_UNBOLCK_SUCCESS.getCode());// 内部订单状态：资金解冻成功
            newProductOrder.setProBeforInnerState(productOrder.getProInnerState());;// 前内部订单状态：此次修改之前的内部状态
            newProductOrder.setBlackAmt(productOrder.getBefbal());// 充值后金额
            newProductOrder.setProSuspiciousState(suspiciousStates);
            newProductOrder.setUpdateUser(productOrder.getUpdateUser());
            StringBuffer orderCommentsBF = new StringBuffer();
            orderCommentsBF.append("处理一卡通充值异常：");
            orderCommentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户资金解冻成功，更改订单状态。");
            orderCommentsBF.append("处理前主状态：");
            orderCommentsBF.append(productOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrder.getProOrderState()).getName());
            orderCommentsBF.append("；处理前内部状态：");
            orderCommentsBF.append(productOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProInnerState()).getName());
            orderCommentsBF.append("；处理前前内部状态：");
            orderCommentsBF.append(productOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProBeforInnerState()).getName());
            orderCommentsBF.append("；处理后主状态：");
            orderCommentsBF.append(newProductOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(newProductOrder.getProOrderState()).getName());
            orderCommentsBF.append("；处理后内部状态：");
            orderCommentsBF.append(newProductOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProInnerState()).getName());
            orderCommentsBF.append("；处理后前内部状态：");
            orderCommentsBF.append(newProductOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProBeforInnerState()).getName());
            newProductOrder.setComments(orderCommentsBF.toString());
            newProductOrder.setProSuspiciousExplain(newProductOrder.getComments());
            productOrderMapper.updateOrderStatusAfterHandleException(newProductOrder);
        }
        
        
        // *****************   5、账户资金解冻更改清分记录（与供应商清分状态=0；与客户清分状态=0；）  ************//
        
        ClearingBasicData clearingBasicData = clearingBasicDataMapper.getClearingBasicDataByOrderNoAndCustomerNo(orderNum, merOrUserCode);
        if (clearingBasicData != null) {
            ClearingBasicData newClearingBasicData = new ClearingBasicData();
            newClearingBasicData.setOrderNo(clearingBasicData.getOrderNo());
            newClearingBasicData.setCustomerNo(clearingBasicData.getCustomerNo());
            newClearingBasicData.setSupplierClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());
            newClearingBasicData.setCustomerClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());
            clearingBasicDataMapper.updateCustomerClearingStateAfterAccountUnfreeze(newClearingBasicData);
        }
        
        return response;
    }

    /**
     * 资金扣款流程
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DodopalResponse<String> accountDeductHandle(ProductOrder productOrder, String operatorName) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS); 
        
        // 判断该订单是否为可疑订单
        String suspiciousStates = SuspiciousStatesEnum.SUSPICIOUS_NO.getCode();// 不可疑
        
        if (RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode().equals(productOrder.getProInnerState())
            || RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())
            && RechargeOrderInternalStatesEnum.RECHARGE_RESULT_UNKNOWN.getCode().equals(productOrder.getProInnerState())
            ) {
            suspiciousStates = SuspiciousStatesEnum.SUSPICIOUS_HANDLE.getCode();// 可疑已处理
        }
        
        String orderNum = productOrder.getProOrderNum();// 充值订单编号
        String merOrUserCode = productOrder.getMerCode();// 用户号
        String userType = productOrder.getMerUserType();// 用户类型
        if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
            userType = MerUserTypeEnum.PERSONAL.getCode();
            merOrUserCode = productOrder.getUserCode();
        } else {
            userType = MerUserTypeEnum.MERCHANT.getCode();
            merOrUserCode = productOrder.getMerCode();
        }
        String tranType = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();// 交易类型：3:账户消费
        String businessType = RateCodeEnum.YKT_RECHARGE.getCode();// 业务类型：01：一卡通充值
        
        if (LoadFlagEnum.LOAD_YES.getCode().equals(productOrder.getLoadFlag())) {
            
            //*********************     1.1 圈存订单更改状态           ***********************//
            
            if (StringUtils.isNotBlank(productOrder.getLoadOrderNum())) {
                loadOrderMapper.updateLoadOrderStatus(LoadOrderStatusEnum.CHARGE_SUCCESS.getCode(), productOrder.getLoadOrderNum(), productOrder.getUpdateUser()); 
            }
            
            //*********************     1.2 圈存订单处理：更改产品库订单状态           ***********************//
            
            if (!RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState())) {
                ProductOrder newProductOrder = new ProductOrder();
                newProductOrder.setProOrderNum(productOrder.getProOrderNum());
                newProductOrder.setProOrderState(RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode());// 主订单状态：充值成功
                newProductOrder.setProInnerState(RechargeOrderInternalStatesEnum.CARD_RECHARGE_SUCCESS.getCode());// 内部订单状态：卡片充值成功
                newProductOrder.setProBeforInnerState(productOrder.getProInnerState());;// 前内部订单状态：此次修改之前的内部状态
                newProductOrder.setBlackAmt(productOrder.getBefbal() + productOrder.getTxnAmt());// 充值后金额
                newProductOrder.setProSuspiciousState(suspiciousStates);
                newProductOrder.setUpdateUser(productOrder.getUpdateUser());
                StringBuffer orderCommentsBF = new StringBuffer();
                orderCommentsBF.append("处理一卡通充值异常：");
                orderCommentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理圈存产品库异常订单，更改订单状态。");
                orderCommentsBF.append("处理前主状态：");
                orderCommentsBF.append(productOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrder.getProOrderState()).getName());
                orderCommentsBF.append("；处理前内部状态：");
                orderCommentsBF.append(productOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProInnerState()).getName());
                orderCommentsBF.append("；处理前前内部状态：");
                orderCommentsBF.append(productOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProBeforInnerState()).getName());
                orderCommentsBF.append("；处理后主状态：");
                orderCommentsBF.append(newProductOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(newProductOrder.getProOrderState()).getName());
                orderCommentsBF.append("；处理后内部状态：");
                orderCommentsBF.append(newProductOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProInnerState()).getName());
                orderCommentsBF.append("；处理后前内部状态：");
                orderCommentsBF.append(newProductOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProBeforInnerState()).getName());
                newProductOrder.setComments(orderCommentsBF.toString());
                newProductOrder.setProSuspiciousExplain(newProductOrder.getComments());
                productOrderMapper.updateOrderStatusAfterHandleException(newProductOrder);
            }
        } else {
            
            //*********************     2. 非圈存订单处理:账户处理资金扣款           ***********************//
            
            // 2.1  获取交易流水 
            PayTraTransaction oldPayTransaction = payTraTransactionMapper.getPayTraTransactionByOrder(orderNum, merOrUserCode, userType, tranType, businessType);
            if (oldPayTransaction == null) {
                
                throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_NULL, "异常处理充值订单，资金扣款：充值订单编号："+orderNum+"，对应的交易流水不存在。");
                
            } else if (!(TranInStatusEnum.ACCOUNT_DEBIT_FAIL.getCode().equals(oldPayTransaction.getTranInStatus())
                || TranInStatusEnum.ACCOUNT_FROZEN_SUCCESS.getCode().equals(oldPayTransaction.getTranInStatus()))) {
                
                throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_STATE_ERROR, "异常处理充值订单，资金扣款：充值订单编号："+orderNum+"，对应的交易流水状态不正（既不是账户扣款失败，也不是资金冻结成功）。");
                
            }
            
            String tranCode = oldPayTransaction.getTranCode();// 交易流水号
            
            // 2.2  查询主账户信息：（根据客户类型，客户编号）
            Account account = accountMapper.getAccountInfo(oldPayTransaction.getUserType(), oldPayTransaction.getMerOrUserCode());
            if (account == null) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_NULL, "交易流水号：" + tranCode + "，客户类型：" + oldPayTransaction.getUserType() + "，客户编号：" + oldPayTransaction.getMerOrUserCode() + "对应的主账户信息不存在。");
                
            }

            // 2.3  查询资金账户账户信息：（根据主账户编号，账户类型：资金账户）
            AccountFund oldAccountFund = accountFundMapper.getFundAccountInfo(account.getAccountCode(), FundTypeEnum.FUND.getCode());
            if (oldAccountFund == null) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_FUND_NULL, "交易流水号：" + tranCode + "，主账户编号：" + account.getAccountCode() + "对应的资金账户信息不存在。");
                
            }
            
            // 2.4  查询授信账户账户信息：（根据主账户编号，账户类型：授信账户）
            AccountFund oldAccountAuthorized = accountFundMapper.getFundAccountInfo(account.getAccountCode(), FundTypeEnum.AUTHORIZED.getCode());
            
            
            // *********************************   2.5  遍历冻结记录，逐一扣款        START     ***************************//

            // 根据（交易流水号）和（变动类型：冻结）的查询资金变动记录
            List<AccountChange> oldAccountChangeList = accountChangeMapper.getAccountChangeRecordList(tranCode, AccTranTypeEnum.ACC_FREEZE.getCode());
            
            if (CollectionUtils.isEmpty(oldAccountChangeList)) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_FREEZE_RECORD_NULL, "交易流水号：" + tranCode + "账户资金扣款处理前，验证账户冻结记录是否存在：对应的资金冻结记录不存在。");
                
            } else if (oldAccountChangeList.size() == 1 && FundTypeEnum.AUTHORIZED.getCode().equals(oldAccountChangeList.get(0).getFundType())) {
                
                throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_FREEZE_RECORD_NULL, "交易流水号：" + tranCode + "账户资金扣款处理前，验证账户冻结记录是否存在：账户类型为资金的账户冻结记录不存在。");
                
            }
            
            // 遍历冻结记录，扣款前CHECK数据
            for (AccountChange oldAccountChange : oldAccountChangeList) {
                
                //  资金类型账户
                if (FundTypeEnum.FUND.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 追加检查（扣款处理过后）冻结金额是否为负值
                    long newFrozenAmt = oldAccountFund.getFrozenAmount() - oldAccountChange.getChangeAmount();
                    if (newFrozenAmt < 0) {
                        
                        throw new DDPException(ResponseCode.OSS_DEDUCT_FROZEN_AMOUNT_NOT_ENOUGH_ERROR, "交易流水号:" + tranCode + "对应资金账户冻结金额小于扣款金额");
                        
                    }
                   
                    // 检查资金变动表记录是否存在：（根据交易流水号，变动类型：扣款，资金类别：资金账户）
                    boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(tranCode, AccTranTypeEnum.ACC_CONSUME.getCode(), FundTypeEnum.FUND.getCode());
                    if (exsit) {
                        
                        throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_DEDUCT_RECORD_EXSIT, "交易流水号:" + tranCode + "对应资金账户资金扣款变动记录已存在。");

                    }
                    
                } else if (FundTypeEnum.AUTHORIZED.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 授信类型账户
                    if (oldAccountAuthorized == null) {
                        
                        throw new DDPException(ResponseCode.OSS_ACCOUNT_AUTHORIZED_NULL, "交易流水号：" + tranCode + "，主账户编号：" + account.getAccountCode() + "授信账户资金变动记录存在，但对应的授信账户信息不存在。");
                        
                    }
                    
                    // 追加检查（扣款处理过后）冻结金额是否为负值
                    long newFrozenAmt = oldAccountAuthorized.getFrozenAmount() - oldAccountChange.getChangeAmount();
                    if (newFrozenAmt < 0) {
                        
                        throw new DDPException(ResponseCode.OSS_DEDUCT_FROZEN_AMOUNT_NOT_ENOUGH_ERROR, "交易流水号:" + tranCode + "对应授信账户冻结金额小于扣款金额");
                        
                    }
                                   
                    // 检查资金变动表记录是否存在：（根据交易流水号，变动类型：解冻，资金类别：授信账户）
                    boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(tranCode, AccTranTypeEnum.ACC_CONSUME.getCode(), FundTypeEnum.AUTHORIZED.getCode());
                    if (exsit) {
                        
                        throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_DEDUCT_RECORD_EXSIT, "交易流水号:" + tranCode + "对应授信账户资金扣款变动记录已存在。");
                        
                    }
                }
            }
            
            // 遍历冻结记录，逐一扣款
            for (AccountChange oldAccountChange : oldAccountChangeList) {
                
                if (FundTypeEnum.FUND.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 新增资金变动表记录：（根据交易流水号，变动类型：扣款，资金类别：资金账户）
                    AccountChange accountChange = new AccountChange();
                    accountChange.setChangeAmount(oldAccountChange.getChangeAmount());// 变动金额
                    accountChange.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金账户号
                    accountChange.setFundType(FundTypeEnum.FUND.getCode());// 资金类别
                    accountChange.setBeforeChangeAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=当前账户总余额
                    accountChange.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额==当前账户可用余额
                    accountChange.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额==当前账户冻结余额
                    accountChange.setChangeType(AccTranTypeEnum.ACC_CONSUME.getCode());// 变动类型
                    accountChange.setTranCode(tranCode);// 交易流水号
                    accountChange.setCreateUser(productOrder.getUpdateUser());// 操作员ID
                    accountChangeMapper.addAccountChange(accountChange);

                    // 更新资金账户账户信息
                    AccountFund accountFund = new AccountFund();
                    accountFund.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额=可用余额
                    accountFund.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额=冻结金额
                    accountFund.setBeforeChangeTotalAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=账户总余额
                    accountFund.setFrozenAmount(oldAccountFund.getFrozenAmount() - oldAccountChange.getChangeAmount());// 冻结金额
                    accountFund.setAvailableBalance(oldAccountFund.getAvailableBalance());// 可用金额
                    accountFund.setTotalBalance(oldAccountFund.getTotalBalance() - oldAccountChange.getChangeAmount());// 总余额
                    accountFund.setSumTotalAmount(oldAccountFund.getSumTotalAmount());// 累计总金额
                    accountFund.setLastChangeAmount(oldAccountChange.getChangeAmount());// 最近一次变动金额
                    accountFund.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金授信编号
                    accountFund.setUpdateUser(productOrder.getUpdateUser());// 操作员ID
                    accountFundMapper.updateFundAccount(accountFund);
                    
                } else if (FundTypeEnum.AUTHORIZED.getCode().equals(oldAccountChange.getFundType())) {
                    
                    // 新增资金变动表记录：（根据交易流水号，变动类型：扣款，资金类别：授信账户）
                    AccountChange accountChange = new AccountChange();
                    accountChange.setChangeAmount(oldAccountChange.getChangeAmount());// 变动金额
                    accountChange.setFundAccountCode(oldAccountFund.getFundAccountCode());// 资金账户号
                    accountChange.setFundType(FundTypeEnum.AUTHORIZED.getCode());// 资金类别
                    accountChange.setBeforeChangeAmount(oldAccountFund.getTotalBalance());// 变动前账户总余额=当前账户总余额
                    accountChange.setBeforeChangeAvailableAmount(oldAccountFund.getAvailableBalance());// 变动前可用余额==当前账户可用余额
                    accountChange.setBeforeChangeFrozenAmount(oldAccountFund.getFrozenAmount());// 变动前冻结金额==当前账户冻结余额
                    accountChange.setChangeType(AccTranTypeEnum.ACC_CONSUME.getCode());// 变动类型
                    accountChange.setTranCode(tranCode);// 交易流水号
                    accountChange.setCreateUser(productOrder.getUpdateUser());// 操作员ID
                    accountChangeMapper.addAccountChange(accountChange);

                    // 更新授信账户账户信息
                    AccountFund accountFund = new AccountFund();
                    accountFund.setBeforeChangeAvailableAmount(oldAccountAuthorized.getAvailableBalance());// 变动前可用余额=可用余额
                    accountFund.setBeforeChangeFrozenAmount(oldAccountAuthorized.getFrozenAmount());// 变动前冻结金额=冻结金额
                    accountFund.setBeforeChangeTotalAmount(oldAccountAuthorized.getTotalBalance());// 变动前账户总余额=账户总余额
                    accountFund.setFrozenAmount(oldAccountAuthorized.getFrozenAmount() - oldAccountChange.getChangeAmount());// 冻结金额
                    accountFund.setAvailableBalance(oldAccountAuthorized.getAvailableBalance());// 可用金额
                    accountFund.setTotalBalance(oldAccountAuthorized.getTotalBalance() - oldAccountChange.getChangeAmount());// 总余额
                    accountFund.setSumTotalAmount(oldAccountAuthorized.getSumTotalAmount());// 累计总金额
                    accountFund.setLastChangeAmount(oldAccountChange.getChangeAmount());// 最近一次变动金额
                    accountFund.setFundAccountCode(oldAccountAuthorized.getFundAccountCode());// 资金授信编号
                    accountFund.setUpdateUser(productOrder.getUpdateUser());// 操作员ID
                    accountFundMapper.updateFundAccount(accountFund);
                }
               
            }
            // ******************   扣款成功后，调用自动转账接口  2016-01-04 追加 start   ********************//
            
            paymentDelegate.autoTransfer(merOrUserCode);
            
            // *****************    扣款成功后，调用自动转账接口  2016-01-04 追加 end     ********************//
            
            
            //******************    3、非圈存订单处理:处理交易流水表， 更改交易流水状态（已支付、资金扣款成功）        ********************//
            
            PayTraTransaction payTransaction = new PayTraTransaction();
            payTransaction.setTranCode(tranCode);
            payTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
            payTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_DEBIT_SUCCESS.getCode());
            payTransaction.setUpdateUser(productOrder.getUpdateUser());
            StringBuffer commentsBF = new StringBuffer();
            commentsBF.append("处理一卡通充值异常：");
            commentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户资金扣款成功，更改交易流水状态。");
            commentsBF.append("处理前状态：");
            commentsBF.append(oldPayTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(oldPayTransaction.getTranOutStatus()).getName());
            commentsBF.append("；处理前内部状态：");
            commentsBF.append(oldPayTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(oldPayTransaction.getTranInStatus()).getName());
            commentsBF.append("；处理后状态：");
            commentsBF.append(payTransaction.getTranOutStatus()+"_"+TranOutStatusEnum.getTranOutStatusByCode(payTransaction.getTranOutStatus()).getName());
            commentsBF.append("；处理后内部状态：");
            commentsBF.append(payTransaction.getTranInStatus()+"_"+TranInStatusEnum.getTranInStatusByCode(payTransaction.getTranInStatus()).getName());
            payTransaction.setComments(commentsBF.toString());
            payTraTransactionMapper.updateTranStatus(payTransaction);
            
            
            // *****************   4、非圈存订单处理:更改订单状态（充值成功、资金扣款成功、此次修改之前的内部状态）      ************//
            
            ProductOrder newProductOrder = new ProductOrder();
            newProductOrder.setProOrderNum(productOrder.getProOrderNum());
            newProductOrder.setProOrderState(RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode());// 主订单状态：充值成功
            newProductOrder.setProInnerState(RechargeOrderInternalStatesEnum.FUND_DEDUCT_SUCCESS.getCode());// 内部订单状态：资金扣款成功
            newProductOrder.setProBeforInnerState(productOrder.getProInnerState());;// 前内部订单状态：此次修改之前的内部状态
            newProductOrder.setBlackAmt(productOrder.getBefbal() + productOrder.getTxnAmt());// 充值后金额
            newProductOrder.setProSuspiciousState(suspiciousStates);
            newProductOrder.setUpdateUser(productOrder.getUpdateUser());
            StringBuffer orderCommentsBF = new StringBuffer();
            orderCommentsBF.append("处理一卡通充值异常：");
            orderCommentsBF.append("操作员：" + operatorName + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户资金扣款成功，更改订单状态。");
            orderCommentsBF.append("处理前主状态：");
            orderCommentsBF.append(productOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrder.getProOrderState()).getName());
            orderCommentsBF.append("；处理前内部状态：");
            orderCommentsBF.append(productOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProInnerState()).getName());
            orderCommentsBF.append("；处理前前内部状态：");
            orderCommentsBF.append(productOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(productOrder.getProBeforInnerState()).getName());
            orderCommentsBF.append("；处理后主状态：");
            orderCommentsBF.append(newProductOrder.getProOrderState()+"_"+RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(newProductOrder.getProOrderState()).getName());
            orderCommentsBF.append("；处理后内部状态：");
            orderCommentsBF.append(newProductOrder.getProInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProInnerState()).getName());
            orderCommentsBF.append("；处理后前内部状态：");
            orderCommentsBF.append(newProductOrder.getProBeforInnerState()+"_"+RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(newProductOrder.getProBeforInnerState()).getName());
            newProductOrder.setComments(orderCommentsBF.toString());
            newProductOrder.setProSuspiciousExplain(newProductOrder.getComments());
            productOrderMapper.updateOrderStatusAfterHandleException(newProductOrder);
        }
        
        // *****************   5、更改清分记录（与客户清分状态=1；与客户清分日期=sysDate；商户实际分润=商户应得分润）  ************//
        
        ClearingBasicData clearingBasicData = clearingBasicDataMapper.getClearingBasicDataByOrderNoAndCustomerNo(orderNum, merOrUserCode);
        if (clearingBasicData != null) {
            ClearingBasicData newClearingBasicData = new ClearingBasicData();
            newClearingBasicData.setOrderNo(clearingBasicData.getOrderNo());
            newClearingBasicData.setCustomerNo(clearingBasicData.getCustomerNo());
            newClearingBasicData.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());
            newClearingBasicData.setCustomerRealProfit(clearingBasicData.getCustomerShouldProfit());
            if (ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode().equals(clearingBasicData.getSupplierClearingFlag())) {
                newClearingBasicData.setSupplierClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());
                newClearingBasicData.setSupplierToDdpRealRebate(clearingBasicData.getSupplierToDdpShouldRebate());
                newClearingBasicData.setDdpToSupplierRealAmount(clearingBasicData.getDdpToSupplierShouldAmount() - clearingBasicData.getSupplierToDdpShouldRebate());
            }
            clearingBasicDataMapper.updateCustomerClearingStateAfterAccountDeduct(newClearingBasicData);
        }
        
        return response;
    }

}
