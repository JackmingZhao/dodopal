package com.dodopal.running.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.running.business.constant.RunningConstants;
import com.dodopal.running.business.dao.AccountChangeMapper;
import com.dodopal.running.business.dao.AccountFundMapper;
import com.dodopal.running.business.dao.AccountMapper;
import com.dodopal.running.business.dao.ClearingBasicDataMapper;
import com.dodopal.running.business.dao.PayTraTransactionMapper;
import com.dodopal.running.business.model.Account;
import com.dodopal.running.business.model.AccountChange;
import com.dodopal.running.business.model.AccountFund;
import com.dodopal.running.business.model.ClearingBasicData;
import com.dodopal.running.business.model.PayTraTransaction;
import com.dodopal.running.business.service.AccountRechargeExceptionHandleService;

/**
 * 账户充值异常处理ServiceImpl
 * @author shenXiang
 */
@Service
public class AccountRechargeExceptionHandleServiceImpl implements AccountRechargeExceptionHandleService {

    private Logger logger = LoggerFactory.getLogger(AccountRechargeExceptionHandleServiceImpl.class);

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

    /**
     * 账户充值异常处理
     */
    @Override
    @Transactional
    public DodopalResponse<String> exceptionHandle(String tranCode, String operatorId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);

        //***********************************    1、查询交易流水信息（账户充值：已支付，但账户加值失败）   ********************//
        
        PayTraTransaction oldPayTransaction = payTraTransactionMapper.getPayTraTransactionByTranCode(tranCode);
        if (oldPayTransaction == null) {
            logger.error("交易流水号：" + tranCode + "对应的交易流水信息不存在。");
            throw new DDPException(ResponseCode.OSS_PAY_TRATRANSACTION_NULL);
        }
        
        //********************************************      2、账户加值            **********************************************//
        
        // 2.1  查询主账户信息：（根据客户类型，客户编号）
        Account account = accountMapper.getAccountInfo(oldPayTransaction.getUserType(), oldPayTransaction.getMerOrUserCode());
        if (account == null) {
            logger.error("交易流水号：" + tranCode + "，客户类型：" + oldPayTransaction.getUserType() + "，客户编号：" + oldPayTransaction.getMerOrUserCode() + "对应的主账户信息不存在。");
            throw new DDPException(ResponseCode.OSS_ACCOUNT_NULL);
        }

        // 2.2  查询资金账户账户信息：（根据主账户编号，账户类型：资金账户）
        AccountFund oldAccountFund = accountFundMapper.getFundAccountInfo(account.getAccountCode(), FundTypeEnum.FUND.getCode());
        if (oldAccountFund == null) {
            logger.error("交易流水号：" + tranCode + "，主账户编号：" + account.getAccountCode() + "对应的资金账户信息不存在。");
            throw new DDPException(ResponseCode.OSS_ACCOUNT_FUND_NULL);
        }

        // 2.3  追加检查（处理过后）总余额是否超过数据库限额
        long amt = oldAccountFund.getTotalBalance() + oldPayTransaction.getAmountMoney();
        if (amt > RunningConstants.TOTALBALANCE_MAX) {
            logger.error("交易流水号:" + tranCode + "对应账户总余额超过数据库可容纳的最大限额");
            throw new DDPException(ResponseCode.OSS_TOTAL_AMOUNT_OUTNUMBER_ERROR);
        }
        
        // 2.4  追加检查（处理过后）累计总金额是否超过数据库限额
        long sumAmt = oldAccountFund.getSumTotalAmount() + oldPayTransaction.getAmountMoney();
        if (sumAmt > RunningConstants.TOTALBALANCE_SUM_MAX) {
            logger.error("交易流水号:" + tranCode + "对应账户累计总金额超过数据库可容纳的最大限额");
            throw new DDPException(ResponseCode.OSS_SUM_TOTAL_AMOUNT_OUTNUMBER_ERROR);
        }
        
        // 2.5  新增资金变动表记录：（根据交易流水号，变动类型：账户充值，资金类别：资金账户）
        boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(tranCode, AccTranTypeEnum.ACC_RECHARGE.getCode(), FundTypeEnum.FUND.getCode());
        if (exsit) {
            logger.error("交易流水号:" + tranCode + "对应的账户充值资金变动记录已存在。");
            throw new DDPException(ResponseCode.OSS_ACCOUNT_CHANGE_RECORD_EXSIT);
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
        accountChange.setCreateUser(operatorId);// 操作员ID
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
        accountFund.setUpdateUser(operatorId);// 操作员ID
        accountFundMapper.updateFundAccount(accountFund);
        
        
        //******************    3、处理交易流水表，修改交易状态(已支付，账户加值成功)   ********************//
        
        PayTraTransaction payTransaction = new PayTraTransaction();
        payTransaction.setTranCode(tranCode);
        payTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
        payTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
        payTransaction.setUpdateUser(operatorId);
        payTransaction.setComments("操作员：" + operatorId + "于" + DateUtils.getCurrDate(DateUtils.DATE_FULL_STR) + "处理账户加值异常。");
        int updateNum = payTraTransactionMapper.updateTranStatus(payTransaction);

        
        // *****************   4、账户充值清分记录（与客户清分状态 = 1:已经清分；客户账户实际加值 = 订单金额；与客户清分日期 = sysdate）  ************//
        
        if (updateNum > 0) {
            ClearingBasicData clearingBasicData = clearingBasicDataMapper.getClearingBasicDataByOrderNoAndCustomerNo(tranCode, oldPayTransaction.getMerOrUserCode());
            if (clearingBasicData != null) {
                ClearingBasicData newClearingBasicData = new ClearingBasicData();
                newClearingBasicData.setOrderNo(clearingBasicData.getOrderNo());
                newClearingBasicData.setCustomerNo(clearingBasicData.getCustomerNo());
                newClearingBasicData.setCustomerAccountRealAmount(oldPayTransaction.getAmountMoney());
                newClearingBasicData.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());
                clearingBasicDataMapper.updateCustomerClearingStateAfterAccountRecharge(newClearingBasicData);
            }
        }
        
        return response;
    }

}
