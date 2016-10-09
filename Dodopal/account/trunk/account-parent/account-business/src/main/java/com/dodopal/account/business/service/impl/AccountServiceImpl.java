/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.service.impl;

import com.dodopal.account.business.constant.AccountConstants;
import com.dodopal.account.business.dao.*;
import com.dodopal.account.business.model.*;
import com.dodopal.account.business.service.AccountService;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.util.DDPLog;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Created by lenovo on 2015/8/21.
 */
@Service
public class AccountServiceImpl implements AccountService {
    //    private static Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    //logger 日志
    private static Logger log = Logger.getLogger(AccountServiceImpl.class);
    private DDPLog<AccountServiceImpl> ddpLog = new DDPLog<>(AccountServiceImpl.class);
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    AccountFundMapper fundAccountMapper;
    @Autowired
    AccountControlMapper accountControlMapper;
    @Autowired
    AccountChangeSumMapper accountChangeSumMapper;
    @Autowired
    AccountChangeMapper accountChangeMapper;

    /**
     * @param account
     * @param fundAccount
     * @param accountControl
     * @description 创建账户信息
     */
    @Transactional
    @Override
    public void createAccount(Account account, AccountFund fundAccount, AccountControl accountControl, AccountControl accountControl1, AccountFund fundAccount1, String accType) {
        log.info("AccountService创建账户接口createAccount开始========================");
        //根据accType 资金账户类型判断走那个分支，如果是授信账户，资金授信账户表添加两天数据
        if (FundTypeEnum.AUTHORIZED.getCode().equals(accType)) {
            accountMapper.addAccount(account);
            fundAccountMapper.addFundAccount(fundAccount);
            fundAccountMapper.addFundAccount(fundAccount1);
            accountControlMapper.addAccountControl(accountControl);
            accountControlMapper.addAccountControl(accountControl1);
        } else {
            accountMapper.addAccount(account);
            fundAccountMapper.addFundAccount(fundAccount);
            accountControlMapper.addAccountControl(accountControl);
        }
        log.info("AccountService创建账户接口createAccount结束========================");
    }

    /**
     * 获取sequence序列的下一个值
     */
    @Override
    public String getSequenceNextId() {
        return accountMapper.getSequenceNextId();
    }

    /**
     * @param accountCode 主账户编号
     * @return AccountFund
     * @description 根据账户类型和账户号查询资金授信记录信息(根据ACCOUNT表和ACCOUNT_FUND表)
     */
    @Override
    @Transactional
    public AccountFund queryFundAccount(String accountCode, String fundType) throws SQLException {
        return fundAccountMapper.queryFundAccount(accountCode, fundType);
    }

    /**
     * @param fundAccountCode
     * @return AccountControl
     * @description 根据资金授信账户编号查询风险控制信息
     */
    @Override
    public AccountControl queryAccountControl(String fundAccountCode) {
        return accountControlMapper.queryAccountControl(fundAccountCode);
    }

    /**
     * @param fundAccountCode
     * @return AccountChangeSum
     * @description 根据资金授信账户编号查询资金变动信息
     */
    @Override
    public AccountChangeSum queryAccountChangeSum(String fundAccountCode, String today) {
        return accountChangeSumMapper.queryAccountChangeSum(fundAccountCode, today);
    }

    /**
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     * @description 充值功能实现
     */
    @Transactional
    @Override
    public void accountRecharge(String custType, String custNum, String tradeNum, long amount, String operateId) throws SQLException {
        log.info("AccountService账户充值接口accountRecharge开始========================");
        //根据客户类型和客户号得到主账户code
        Account at = accountMapper.queryAccoutCode(custType, custNum);
        if (at == null) {
            log.info("AccountService账户充值接口accountRecharge查询主账户为空");
            //主账户信息不存在
            throw new DDPException(ResponseCode.ACC_ACCOUNT_NOTEXIT);
        }

        String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        /**************************************** 充值金额先 填 授信账户已使用额度**********************修改 add by xiong */
        if (FundTypeEnum.AUTHORIZED.getCode().equals(at.getFundType())) {

            //授信账户
            AccountFund accountFund1 = fundAccountMapper.queryFundAccount(at.getAccountCode(), FundTypeEnum.AUTHORIZED.getCode());

            if (accountFund1 == null) {
                log.info("AccountService账户充值接口accountRecharge查询授信账户不存在");
                //授信账户信息不存在
                throw new DDPException(ResponseCode.ACC_FUNDAUTHACCOUNT_NONEXISTENT_RROR);
            }
            //根据资金授信编号查询当天资金变动累计信息
            AccountChangeSum accountChangeSum1 = accountChangeSumMapper.queryAccountChangeSum(accountFund1.getFundAccountCode(), dateStr);
            if (accountChangeSum1 == null) {
                accountChangeSum1 = new AccountChangeSum();
                accountChangeSum1.setFundAccountCode(accountFund1.getFundAccountCode());
                accountChangeSum1.setDayConsumeSum(0);
                accountChangeSum1.setDayRechargeSum(0);
                accountChangeSum1.setDayTranTimes(0);
                accountChangeSum1.setDayTranLimit(0);
                accountChangeSum1.setAccountChangeDate(dateStr);
                accountChangeSum1.setCreateUser(operateId);
                addAccountChangeSum(accountChangeSum1);
            }
            //授信账户总额度 - 授信账户可用余额 - 授信账户冻结金额 >0
            //账户充值的钱必须先充授信账户
            long accountFundAmount = accountFund1.getCreditAmt() - accountFund1.getAvailableBalance() - accountFund1.getFrozenAmount();
            if (accountFundAmount > 0) {
                //充值金额小于等于授信已使用额度，则将充值金额全部加到授信账户。
                if (amount <= accountFundAmount) {
                    //更改表记录字段值操作
                    changeAccountFundInfo(accountFund1, accountChangeSum1, tradeNum, amount, AccTranTypeEnum.ACC_RECHARGE.getCode(), null, operateId);
                    // amount = 0;
                    return;

                } else {
                    //充值金额大于授信账户已使用的额度，则一部分用来填满授信账户额度，一部分充资金账户。
                    amount = amount - accountFundAmount;
                    //更改表记录字段值操作
                    changeAccountFundInfo(accountFund1, accountChangeSum1, tradeNum, accountFundAmount, AccTranTypeEnum.ACC_RECHARGE.getCode(), null, operateId);

                }
            }

        }

        /************************************************************************************** 修改 add by xiong end */

        AccountFund accountFund = fundAccountMapper.queryFundAccount(at.getAccountCode(), FundTypeEnum.FUND.getCode());
        if (accountFund == null) {
            log.info("AccountService账户充值接口accountRecharge查询授信账户不存在");
            //资金授信账户信息不存在
            throw new DDPException(ResponseCode.ACC_FUNDAUTHACCOUNT_NONEXISTENT_RROR);
        }

        //根据资金授信编号查询当天资金变动累计信息
        AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(accountFund.getFundAccountCode(), dateStr);
        if (accountChangeSum == null) {
            accountChangeSum = new AccountChangeSum();
            accountChangeSum.setFundAccountCode(accountFund.getFundAccountCode());
            accountChangeSum.setDayConsumeSum(0);
            accountChangeSum.setDayRechargeSum(0);
            accountChangeSum.setDayTranTimes(0);
            accountChangeSum.setDayTranLimit(0);
            accountChangeSum.setAccountChangeDate(dateStr);
            accountChangeSum.setCreateUser(operateId);
            addAccountChangeSum(accountChangeSum);
        }
        //根据资金授信编号查询账户风控信息
        AccountControl accountControl = accountControlMapper.queryAccountControl(accountFund.getFundAccountCode());
        //资金账户风控检查
        String returnMessage = allowedToRecharge(accountFund, accountChangeSum, accountControl, amount);
        log.info("AccountService账户充值接口accountRecharge检查风控返回===" + returnMessage);
        //对资金账户进行风险检查，通过修改表记录字段值
        if (AccountConstants.CHECK_SUCCESS.equals(returnMessage)) {
            log.info("AccountService账户充值接accountRecharge口更改各个表信息开始");
            //更改表记录字段值操作
            changeInfo(accountFund, accountChangeSum, tradeNum, amount, AccTranTypeEnum.ACC_RECHARGE.getCode(), null, operateId);
            log.info("AccountService账户充值接口accountRecharge更改各个表信息结束");
        } else {
            log.info("AccountService账户充值接口accountRecharge更改各个表信息错误，====" + returnMessage);
            throw new DDPException(returnMessage);
        }
        log.info("AccountService账户充值接口accountRecharge结束========================");
    }

    /**
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     * @description 资金冻结功能实现
     */
    @Transactional
    @Override
    public void accountFreeze(String custType, String custNum, String tradeNum, long amount, String operateId) throws SQLException {
        log.info("AccountService账户冻结接口accountFreeze开始========");
        //根据客户类型和客户编号查询主账户编号
        Account at = accountMapper.queryAccoutCode(custType, custNum);
        if (at == null) {
            log.info("AccountService账户冻结接口accountFreeze查询主账户不存在");
            //主账户信息不存在
            throw new DDPException(ResponseCode.ACC_ACCOUNT_NOTEXIT);
        }
        //根据主账户编号查询资金授信账户信息
        List<AccountFund> accountFundList = fundAccountMapper.queryFundAccountList(at.getAccountCode());
        log.info("AccountService账户冻结接口accountFreeze查询资金账户信息条数========" + accountFundList.size());
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        //-1.资金授信账户信息进行验证
        if (accountFundList.size() == 0) {
            log.info("AccountService账户冻结接口accountFreeze查询授信账户不存在");
            //资金授信账户信息不存在
            throw new DDPException(ResponseCode.ACC_FUNDAUTHACCOUNT_NONEXISTENT_RROR);
        }
        //如果是一条账户且是资金账户
        if (accountFundList.size() == 1) {
            AccountFund af = accountFundList.get(0);
            //判断查询出的账户是否资金账户
            if (!FundTypeEnum.FUND.getCode().equals(af.getFundType())) {
                //资金授信账户异常
                throw new DDPException(ResponseCode.ACC_FUNDAUTHACCOUNT_ERROR);
            }
            AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(af.getFundAccountCode(), dateStr);
            if (accountChangeSum == null) {
                accountChangeSum = new AccountChangeSum();
                accountChangeSum.setFundAccountCode(af.getFundAccountCode());
                accountChangeSum.setDayConsumeSum(0);
                accountChangeSum.setDayRechargeSum(0);
                accountChangeSum.setDayTranTimes(0);
                accountChangeSum.setDayTranLimit(0);
                accountChangeSum.setAccountChangeDate(dateStr);
                accountChangeSum.setCreateUser(operateId);
                addAccountChangeSum(accountChangeSum);
            }
            AccountControl accountControl = accountControlMapper.queryAccountControl(af.getFundAccountCode());
            String message = allowedToConsume(af, accountChangeSum, accountControl, amount);
            log.info("AccountService账户冻结接口accountFreeze风控检查返回========" + message);
            //判断冻结金额是否超出可用余额
            if (af.getAvailableBalance() - amount < 0) {
                log.info("AccountService账户冻结接口accountFreeze资金账户可用余额不够");
                //资金账户可用余额不够
                throw new DDPException(ResponseCode.ACC_ACC_BALANCE_NOT_ENOUGH);
            }
            if (AccountConstants.CHECK_SUCCESS.equals(message)) {
                changeInfo(af, accountChangeSum, tradeNum, amount, AccTranTypeEnum.ACC_FREEZE.getCode(), null, operateId);
            } else {
                throw new DDPException(message);
            }
        }
        if (accountFundList.size() == 2) {
            //承载资金账户信息
            AccountFund fundAccount = null;
            //承载授信账户信息
            AccountFund authAccount = null;
            //资金变动累计(资金)
            AccountChangeSum fundAccountChangeSum = null;
            //资金风险控制(资金)
            AccountControl fundAccountControl = null;
            //资金变动累计(授信)
            AccountChangeSum authAccountChangeSum = null;
            //资金变动累计(授信)
            AccountControl authAccountControl = null;
            //判断哪一条是资金账户，哪一条是授信账户
            if (FundTypeEnum.FUND.getCode().equals(accountFundList.get(0).getFundType())) {
                fundAccount = accountFundList.get(0);
                authAccount = accountFundList.get(1);
            } else {
                authAccount = accountFundList.get(0);
                fundAccount = accountFundList.get(1);
            }
            fundAccountChangeSum = accountChangeSumMapper.queryAccountChangeSum(fundAccount.getFundAccountCode(), dateStr);
            if (fundAccountChangeSum == null) {
                fundAccountChangeSum = new AccountChangeSum();
                fundAccountChangeSum.setFundAccountCode(fundAccount.getFundAccountCode());
                fundAccountChangeSum.setDayConsumeSum(0);
                fundAccountChangeSum.setDayRechargeSum(0);
                fundAccountChangeSum.setDayTranTimes(0);
                fundAccountChangeSum.setDayTranLimit(0);
                fundAccountChangeSum.setAccountChangeDate(dateStr);
                fundAccountChangeSum.setCreateUser(operateId);
                addAccountChangeSum(fundAccountChangeSum);
            }
            fundAccountControl = accountControlMapper.queryAccountControl(fundAccount.getFundAccountCode());
            authAccountChangeSum = accountChangeSumMapper.queryAccountChangeSum(authAccount.getFundAccountCode(), dateStr);
            if (authAccountChangeSum == null) {
                authAccountChangeSum = new AccountChangeSum();
                authAccountChangeSum.setFundAccountCode(authAccount.getFundAccountCode());
                authAccountChangeSum.setDayConsumeSum(0);
                authAccountChangeSum.setDayRechargeSum(0);
                authAccountChangeSum.setDayTranTimes(0);
                authAccountChangeSum.setDayTranLimit(0);
                authAccountChangeSum.setAccountChangeDate(dateStr);
                authAccountChangeSum.setCreateUser(operateId);
                addAccountChangeSum(authAccountChangeSum);
            }
            authAccountControl = accountControlMapper.queryAccountControl(authAccount.getFundAccountCode());
            //资金账户信息风控检查
            String fundReturnMessage = allowedToConsume(fundAccount, fundAccountChangeSum, fundAccountControl, amount);
            log.info("AccountService账户冻结接口accountFreeze资金账户风控检查返回========" + fundReturnMessage);
            //授信账户信息风控检查
            String authReturnMessage = allowedToConsume(authAccount, authAccountChangeSum, authAccountControl, amount);
            log.info("AccountService账户冻结接口accountFreeze授信账户风控检查返回========" + authReturnMessage);
            //如果资金账户风控没有通过，检查授信账户风控
            if (!AccountConstants.CHECK_SUCCESS.equals(fundReturnMessage)) {
                //授信账户风控信息没有通过
                if (!AccountConstants.CHECK_SUCCESS.equals(authReturnMessage)) {
                    throw new DDPException(authReturnMessage);
                }
            }
            //如果资金账户风控没有通过，检查授信账户风控
            if (!AccountConstants.CHECK_SUCCESS.equals(fundReturnMessage)) {
                //授信账户风控通过
                if (AccountConstants.CHECK_SUCCESS.equals(authReturnMessage)) {
                    if (authAccount.getAvailableBalance() - amount < 0) {
                        //授信账户可用余额不够
                        throw new DDPException(ResponseCode.ACC_AUT_BALANCE_NOT_ENOUGH);
                    }
                    log.info("AccountService账户冻结接口accountFreeze更改表信息开始");
                    changeInfo(authAccount, authAccountChangeSum, tradeNum, amount, AccTranTypeEnum.ACC_FREEZE.getCode(), null, operateId);
                    log.info("AccountService账户冻结接口accountFreeze更改表信息结束");
                }
            }
            //如果资金账户风控信息通过
            if (AccountConstants.CHECK_SUCCESS.equals(fundReturnMessage)) {
                if (fundAccount.getAvailableBalance() >= amount) {
                    changeInfo(fundAccount, fundAccountChangeSum, tradeNum, amount, AccTranTypeEnum.ACC_FREEZE.getCode(), null, operateId);
                }
            }
            //如果资金账户风控通过，但是可用余额不足处理
            if (AccountConstants.CHECK_SUCCESS.equals(fundReturnMessage)) {
                if (fundAccount.getAvailableBalance() < amount) {
                    //授信账户所需要冻结金额 = 金额-资金账户可用余额
                    long authAmount = amount - fundAccount.getAvailableBalance();
                    //根据授信账户所需冻结金额进行风险检查
                    String aginReturnMessage = allowedToConsume(authAccount, authAccountChangeSum, authAccountControl, authAmount);
                    if (!AccountConstants.CHECK_SUCCESS.equals(aginReturnMessage)) {
                        throw new DDPException(aginReturnMessage);
                    } else {
                        if (authAccount.getAvailableBalance() - authAmount < 0) {
                            //授信账户可用余额不够
                            throw new DDPException(ResponseCode.ACC_AUT_BALANCE_NOT_ENOUGH);
                        }
                        log.info("AccountService账户冻结接口accountFreeze更改表信息开始");
                        //进行表记录信息更改
                        if (fundAccount.getAvailableBalance() != 0) {
                            //进行表记录信息更改
                            changeInfo(fundAccount, fundAccountChangeSum, tradeNum, fundAccount.getAvailableBalance(), AccTranTypeEnum.ACC_FREEZE.getCode(), null, operateId);
                        }
                        changeInfo(authAccount, authAccountChangeSum, tradeNum, authAmount, AccTranTypeEnum.ACC_FREEZE.getCode(), null, operateId);
                        log.info("AccountService账户冻结接口accountFreeze更改表信息结束");
                    }
                }
            }
        }
        log.info("AccountService账户冻结接口accountFreeze结束============");
    }

    /**
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     * @description 资金解冻功能实现
     */
    @Transactional
    @Override
    public void accountUnfreeze(String custType, String custNum, String tradeNum, long amount, String operateId) throws SQLException {
        log.info("AccountService账户解冻接口accountUnfreeze开始");
        //根据客户类型和客户号查询祝账户编号
        Account at = accountMapper.queryAccoutCode(custType, custNum);
        if (at == null) {
            log.info("AccountService账户解冻接口accountUnfreeze主账户信息不存在");
            //主账户信息不存在
            throw new DDPException(ResponseCode.ACC_ACCOUNT_NOTEXIT);
        }
        //根据交易流水号和资金变动类型查询资金变动记录
        List<AccountChange> accountChangesList = accountChangeMapper.queryAccountChange(tradeNum, AccTranTypeEnum.ACC_FREEZE.getCode());
        log.info("AccountService账户解冻接口accountUnfreeze查询出变动信息条数" + accountChangesList.size());
        if (accountChangesList.size() == 0) {
            //非法的资金解冻操作
            throw new DDPException(ResponseCode.ACC_ILLEGAL_UNFREEZE_ERROR);
        }
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        //如果能查询出一条信息
        if (accountChangesList.size() == 1) {
            if (accountChangesList.get(0).getChangeAmount() != amount) {
                log.info("AccountService账户解冻接变动表变动金额不符");
                //非法的资金解冻操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_UNFREEZE_ERROR);
            }
            AccountFund af = fundAccountMapper.queryFundAccount(at.getAccountCode(), accountChangesList.get(0).getFundType());
            //根据资金账户id
            AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(af.getFundAccountCode(), dateStr);
            if (af.getFrozenAmount() - amount < 0) {
                log.info("AccountService账户解冻接口accountUnfreeze资金账户解冻金额和变动金额不符");
                //非法的资金解冻操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_UNFREEZE_ERROR);
            }
            changeInfo(af, accountChangeSum, tradeNum, amount, AccTranTypeEnum.ACC_UNFREEZE.getCode(), accountChangesList.get(0), operateId);
        }
        //如果查询出来两条记录，一条是资金账户，一条是授信账户
        if (accountChangesList.size() == 2) {
            if (accountChangesList.get(0).getChangeAmount() + accountChangesList.get(1).getChangeAmount() > amount) {
                log.info("AccountService账户解冻接口accountUnfreeze资金授信账户解冻金额和变动金额不符");
                //非法的资金解冻操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_UNFREEZE_ERROR);
            }
            //根据祝账户编号查询资金授信账户信息
            List<AccountFund> accountFundList = fundAccountMapper.queryFundAccountList(at.getAccountCode());
            AccountFund fundAccount = null;
            AccountFund authAccount = null;
            AccountChange fundAccountChange = null;
            AccountChange authAccountChange = null;
            AccountChangeSum fundAccountChangeSum = null;
            AccountChangeSum authAccountChangeSum = null;
            //判断哪一条是资金账户，哪一条是授信账户
            if (FundTypeEnum.FUND.getCode().equals(accountFundList.get(0).getFundType())) {
                fundAccount = accountFundList.get(0);
                authAccount = accountFundList.get(1);
            } else {
                authAccount = accountFundList.get(0);
                fundAccount = accountFundList.get(1);
            }
            if (FundTypeEnum.FUND.getCode().equals(accountChangesList.get(0).getFundType())) {
                fundAccountChange = accountChangesList.get(0);
                authAccountChange = accountChangesList.get(1);
            } else {
                authAccountChange = accountChangesList.get(0);
                fundAccountChange = accountChangesList.get(1);
            }
            fundAccountChangeSum = accountChangeSumMapper.queryAccountChangeSum(fundAccount.getFundAccountCode(), dateStr);
            authAccountChangeSum = accountChangeSumMapper.queryAccountChangeSum(authAccount.getFundAccountCode(), dateStr);
            if (fundAccount.getFrozenAmount() - fundAccountChange.getChangeAmount() < 0) {
                log.info("AccountService账户解冻接口accountUnfreeze资金解冻金额和变动金额不符");
                //非法的资金解冻操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_UNFREEZE_ERROR);
            }
            if (authAccount.getFrozenAmount() - authAccountChange.getChangeAmount() < 0) {
                log.info("AccountService账户解冻接口accountUnfreeze授信解冻金额和变动金额不符");
                //非法的资金解冻操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_UNFREEZE_ERROR);
            }
            //分别对两条账户进行解冻操作
            if (FundTypeEnum.FUND.getCode().equals(accountChangesList.get(0).getFundType())) {
                changeInfo(fundAccount, fundAccountChangeSum, tradeNum, fundAccountChange.getChangeAmount(), AccTranTypeEnum.ACC_UNFREEZE.getCode(), accountChangesList.get(0), operateId);
                changeInfo(authAccount, authAccountChangeSum, tradeNum, authAccountChange.getChangeAmount(), AccTranTypeEnum.ACC_UNFREEZE.getCode(), accountChangesList.get(1), operateId);
            }
            if (FundTypeEnum.AUTHORIZED.getCode().equals(accountChangesList.get(0).getFundType())) {
                changeInfo(fundAccount, fundAccountChangeSum, tradeNum, fundAccountChange.getChangeAmount(), AccTranTypeEnum.ACC_UNFREEZE.getCode(), accountChangesList.get(0), operateId);
                changeInfo(authAccount, authAccountChangeSum, tradeNum, authAccountChange.getChangeAmount(), AccTranTypeEnum.ACC_UNFREEZE.getCode(), accountChangesList.get(1), operateId);
            }
        }
        log.info("AccountService账户解冻接口accountUnfreeze结束========");
    }

    /**
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @description 账户扣款功能实现
     */
    @Transactional
    @Override
    public void accountDeduct(String custType, String custNum, String tradeNum, long amount, String operateId) throws SQLException {
        log.info("AccountService账户解冻接口accountDeduct开始========");
        //根据客户类型和客户号查询祝账户编号
        Account at = accountMapper.queryAccoutCode(custType, custNum);
        if (at == null) {
            log.info("AccountService账户扣款接口accountDeduct主账户不存在");
            //主账户信息不存在
            throw new DDPException(ResponseCode.ACC_ACCOUNT_NOTEXIT);
        }
        //根据交易流水号和资金变动类型查询资金变动记录
        List<AccountChange> accountChangesList = accountChangeMapper.queryAccountChange(tradeNum, AccTranTypeEnum.ACC_FREEZE.getCode());
        log.info("AccountService账户扣款接口accountDeduct查询的变动条数" + accountChangesList.size());
        if (accountChangesList.size() == 0) {
            //非法的资金扣款操作
            throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
        }
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        //如果能查询出一条信息
        if (accountChangesList.size() == 1) {
            if (accountChangesList.get(0).getChangeAmount() != amount) {
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            AccountFund af = fundAccountMapper.queryFundAccount(at.getAccountCode(), accountChangesList.get(0).getFundType());
            //根据资金账户id
            AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(af.getFundAccountCode(), dateStr);
            AccountControl accountControl = accountControlMapper.queryAccountControl(af.getFundAccountCode());
            if (af.getTotalBalance() - amount < 0) {
                log.info("AccountService账户扣款接口accountDeduct账户余额不足");
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            if (af.getFrozenAmount() - amount < 0) {
                log.info("AccountService账户扣款接口accountDeduct资金扣款金额和变动金额不符");
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            changeInfo(af, accountChangeSum, tradeNum, amount, AccTranTypeEnum.ACC_CONSUME.getCode(), accountChangesList.get(0), operateId);
        }
        //如果查询出来两条记录，一条是资金账户，一条是授信账户
        if (accountChangesList.size() == 2) {
            if (accountChangesList.get(0).getChangeAmount() + accountChangesList.get(1).getChangeAmount() > amount) {
                log.info("AccountService账户扣款接口accountDeduct资金扣款金额和变动金额不符");
                //非法的扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            //根据祝账户编号查询资金授信账户信息
            List<AccountFund> accountFundList = fundAccountMapper.queryFundAccountList(at.getAccountCode());
            AccountFund fundAccount = null;
            AccountFund authAccount = null;
            AccountChange fundAccountchange = null;
            AccountChange authAccountchange = null;
            AccountChangeSum fundAccountChangeSum = null;
            AccountChangeSum authAccountChangeSum = null;
            //判断哪一条是资金账户，哪一条是授信账户
            if (FundTypeEnum.FUND.getCode().equals(accountFundList.get(0).getFundType())) {
                fundAccount = accountFundList.get(0);
                authAccount = accountFundList.get(1);
            } else {
                authAccount = accountFundList.get(0);
                fundAccount = accountFundList.get(1);
            }
            if (FundTypeEnum.FUND.getCode().equals(accountChangesList.get(0).getFundType())) {
                fundAccountchange = accountChangesList.get(0);
                authAccountchange = accountChangesList.get(1);
            } else {
                authAccountchange = accountChangesList.get(0);
                fundAccountchange = accountChangesList.get(1);
            }
            fundAccountChangeSum = accountChangeSumMapper.queryAccountChangeSum(fundAccount.getFundAccountCode(), dateStr);
            authAccountChangeSum = accountChangeSumMapper.queryAccountChangeSum(authAccount.getFundAccountCode(), dateStr);
            if (fundAccount.getTotalBalance() - fundAccountchange.getChangeAmount() < 0) {
                log.info("AccountService账户扣款接口accountDeduct资金余额金额不足");
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            if (authAccount.getTotalBalance() - authAccountchange.getChangeAmount() < 0) {
                log.info("AccountService账户扣款接口accountDeduct授信余额金额不足");
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            if (fundAccount.getFrozenAmount() - fundAccountchange.getChangeAmount() < 0) {
                log.info("AccountService账户扣款接口accountDeduct资金冻结的金额和变动金额不符");
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            if (authAccount.getFrozenAmount() - authAccountchange.getChangeAmount() < 0) {
                log.info("AccountService账户扣款接口accountDeduct授信冻结的金额和变动金额不符");
                //非法的资金扣款操作
                throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
            }
            //分别对两条账户进行扣款操作
            if (FundTypeEnum.FUND.getCode().equals(accountChangesList.get(0).getFundType())) {
                changeInfo(fundAccount, fundAccountChangeSum, tradeNum, fundAccountchange.getChangeAmount(), AccTranTypeEnum.ACC_CONSUME.getCode(), accountChangesList.get(0), operateId);
                changeInfo(authAccount, authAccountChangeSum, tradeNum, authAccountchange.getChangeAmount(), AccTranTypeEnum.ACC_CONSUME.getCode(), accountChangesList.get(1), operateId);
            }
            if (FundTypeEnum.AUTHORIZED.getCode().equals(accountChangesList.get(0).getFundType())) {
                changeInfo(fundAccount, fundAccountChangeSum, tradeNum, fundAccountchange.getChangeAmount(), AccTranTypeEnum.ACC_CONSUME.getCode(), accountChangesList.get(0), operateId);
                changeInfo(authAccount, authAccountChangeSum, tradeNum, authAccountchange.getChangeAmount(), AccTranTypeEnum.ACC_CONSUME.getCode(), accountChangesList.get(1), operateId);
            }
        }
        log.info("AccountService账户解冻接口accountDeduct结束========");
    }

    /**
     * @param merType
     * @return
     * @description 根据客户类型查询
     */
    @Override
    public AccountControllerDefault queryControlDefault(String merType) {
        return accountControlMapper.queryControlDefault(merType);
    }

    /**
     * @param accountFund
     * @param amount
     * @return
     * @description 是否允许账户充值
     */
    @Transactional
    public String allowedToRecharge(AccountFund accountFund, AccountChangeSum accountChangeSum, AccountControl accountControl, long amount) {
        long checkAmount = 0;
        String message = AccountConstants.CHECK_SUCCESS;
        if (accountFund == null) {
            //非法充值，没有资金账户
            message = ResponseCode.ACC_BLAN_ERROR;
            return message;
        }
        if (AccStatusEnum.DISABLE.getCode().equals(accountFund.getState())) {
            //资金账户已经禁用，不能充值
            message = ResponseCode.ACC_BLAN_DISABLE;
            return message;
        }
        if (accountControl.getDayRechargeSingleLimit() < amount) {
            //超过日充值交易单笔限额
            message = ResponseCode.ACC_RECHARGE_DAY_ONE_ERROR;
            return message;
        }
        if (accountChangeSum != null) {
            checkAmount = accountChangeSum.getDayRechargeSum();
        }
        if (accountControl.getDayRechargeSumLimit() < checkAmount + amount) {
            //超过日充值交易累计限额
            message = ResponseCode.ACC_RECHARGE_DAY_SUM_ERROR;
            return message;
        }
        return message;
    }

    /**
     * @param accountFund
     * @param amount
     * @return
     * @description 是否允许账户消费
     */
    @Transactional
    public String allowedToConsume(AccountFund accountFund, AccountChangeSum accountChangeSum, AccountControl accountControl, long amount) {
        String message = AccountConstants.CHECK_SUCCESS;
        //判断账户是否被禁用
        if (AccStatusEnum.DISABLE.getCode().equals(accountFund.getState())) {
            //资金授信账户已经禁用，不能进行消费
            message = ResponseCode.ACC_FUNDAUTHACCOUNT_DISABLE_ERROR;
            return message;
        }
        if (accountControl.getDayConsumeSingleLimit() < amount) {
            //超过日消费交易单笔限额
            message = ResponseCode.ACC_CONSUM_DAY_ONE_ERROR;
            return message;
        }
        if (accountControl.getDayConsumeSumLimit() < accountChangeSum.getDayConsumeSum() + amount) {
            //超过日消费交易累计限额
            message = ResponseCode.ACC_CONSUM_DAY_SUM_ERROR;
            return message;
        }
        return message;
    }

    /********************************************* 账户充值 先填授信账户的已使用额度，有多再加到资金账户**************修改 add by xiong */

    /**
     * 增加授信账户资金变更记录 （充值金额小于 授信账户 已使用的金额，直接将充值金额加到授信账户）
     * @param accountFund1
     * @param tradeNum
     * @param amount
     * @param operateId
     */

    //增加授信账户资金变更记录   （充值金额小于 授信账户 已使用的金额，直接将充值金额加到授信账户）
    @Transactional
    private void changeAccountFundInfo(AccountFund accountFund, AccountChangeSum accountChangeSum, String tradeNum, long amount, String changeType, AccountChange ac, String operateId) {
        {
            log.info("变动信息更改开始=======================");
            log.info("变动类型" + changeType);
            if (accountFund != null) {
                log.info("资金变动源数据" + accountFund.toString());
            }

            log.info("订单号" + tradeNum);
            log.info("变动金额" + amount);
            if (ac != null) {
                log.info("变动记录源信息" + ac.toString());
            }
            if (accountChangeMapper.checkAccountChangeRecordExsit(tradeNum, changeType, accountFund.getFundAccountCode())) {
                //存在相应的资金变动记录
                throw new DDPException(ResponseCode.ACCOUNT_CHANGE_EXIT);
            }
            //-1 组装数据
            //--1.1 更改资金累计表记录数据组装
            AccountChangeSum acs = null;
            //充值
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            acs = new AccountChangeSum();
            acs.setFundAccountCode(accountFund.getFundAccountCode());
            //日充值交易累计限额 + amount
            acs.setDayRechargeSum(accountChangeSum.getDayRechargeSum() + amount);
            //日消费累
            acs.setDayConsumeSum(accountChangeSum.getDayConsumeSum());
            acs.setAccountChangeDate(dateStr);
            acs.setUpdateUser(operateId);

            //--1.2 新增资金变动表信息数据组装
            AccountChange accountChange = new AccountChange();
            //资金授信账户号
            accountChange.setFundAccountCode(accountFund.getFundAccountCode());
            //资金类别
            accountChange.setFundType(accountFund.getFundType());
            //交易流水号
            accountChange.setTranCode(tradeNum);
            //账户交易类型
            accountChange.setChangeType(changeType);
            //变动金额
            accountChange.setChangeAmount(amount);
            //变动前账户总余额
            accountChange.setBeforeChangeAmount(accountFund.getTotalBalance());
            //变动前可用余额
            accountChange.setBeforeChangeAvailableAmount(accountFund.getAvailableBalance());
            //变动前冻结金额
            accountChange.setBeforeChangeFrozenAmount(accountFund.getFrozenAmount());
            accountChange.setCreateUser(operateId);

            //--1.3 资金授信表信息更改数据组装
            AccountFund accountFund1 = new AccountFund();
            //资金授信账户号
            accountFund1.setFundAccountCode(accountFund.getFundAccountCode());
            //变动前可用余额
            accountFund1.setBeforeChangeAvailableAmount(accountFund.getAvailableBalance());
            //变动前冻结金额
            accountFund1.setBeforeChangeFrozenAmount(accountFund.getFrozenAmount());
            //最近一次变动金额
            accountFund1.setLastChangeAmount(amount);

            //总余额 = 变动前总金额 + amount
            accountFund1.setTotalBalance(accountFund.getTotalBalance() + amount);

            //累计总金额 = 变动前累计总金额 + amount
            accountFund1.setSumTotalAmount(accountFund.getSumTotalAmount() + amount);

            //变动前账户总余额
            accountFund1.setBeforeChangeTotalAmount(accountFund.getTotalBalance());

            //冻结金额   不变
            accountFund1.setFrozenAmount(accountFund.getFrozenAmount());

            //可用金额 = 变动前可用余额 + amount
            accountFund1.setAvailableBalance(accountFund.getAvailableBalance() + amount);

            //-2数据更改
            //--2.1资金账户信息修改
            fundAccountMapper.updateFundAccount(accountFund1);
            //--2.2资金变动信息新增
            accountChangeMapper.addAccountChange(accountChange);

            //--2.3资金变动累计表信息修改
            accountChangeSumMapper.updateAccountChangeSum(acs);

            log.info("变动信息更改结束=======================");
        }
    }

    /******************************************************************************************* 修改 add by xiong */

    /**
     * @param accountFund
     * @param accountChangeSum
     * @param tradeNum
     * @param amount
     * @description 更改表信息
     */
    @Transactional
    //根据变动类型 为字段设定不同的值
    public void changeInfo(AccountFund accountFund, AccountChangeSum accountChangeSum, String tradeNum, long amount, String changeType, AccountChange ac, String operateId) {
        {
            log.info("变动信息更改开始=======================");
            log.info("变动类型" + changeType);
            if (accountFund != null) {
                log.info("资金变动源数据" + accountFund.toString());
            }
            if (accountChangeSum != null) {
                log.info("累计信息源数据" + accountChangeSum.toString());
            }
            log.info("订单号" + tradeNum);
            log.info("变动金额" + amount);
            if (ac != null) {
                log.info("变动记录源信息" + ac.toString());
            }
            if (accountChangeMapper.checkAccountChangeRecordExsit(tradeNum, changeType, accountFund.getFundAccountCode())) {
                //存在相应的资金变动记录
                throw new DDPException(ResponseCode.ACCOUNT_CHANGE_EXIT);
            }

            //-1 组装数据
            //--1.1 更改资金累计表记录数据组装
            AccountChangeSum acs = null;
            //充值
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType)) {
                String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                acs = new AccountChangeSum();
                acs.setFundAccountCode(accountFund.getFundAccountCode());
                //日充值交易累计限额 + amount
                acs.setDayRechargeSum(accountChangeSum.getDayRechargeSum() + amount);
                //日消费累
                acs.setDayConsumeSum(accountChangeSum.getDayConsumeSum());
                acs.setAccountChangeDate(dateStr);
                acs.setUpdateUser(operateId);
            }
            //冻结
            if (AccTranTypeEnum.ACC_FREEZE.getCode().equals(changeType)) {
                String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                acs = new AccountChangeSum();
                acs.setFundAccountCode(accountFund.getFundAccountCode());
                //日消费累计限额 + amount
                acs.setDayConsumeSum(accountChangeSum.getDayConsumeSum() + amount);
                //日充值累计
                acs.setDayRechargeSum(accountChangeSum.getDayRechargeSum());
                acs.setAccountChangeDate(dateStr);
                acs.setUpdateUser(operateId);
            }
            //解冻
            if (AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(changeType)) {
                String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                acs = new AccountChangeSum();
                acs.setFundAccountCode(accountFund.getFundAccountCode());
                //日充值交易累计限额 + amount
                acs.setDayConsumeSum(accountChangeSum.getDayConsumeSum() - amount);
                acs.setDayRechargeSum(accountChangeSum.getDayRechargeSum());
                acs.setAccountChangeDate(dateStr);
                acs.setUpdateUser(operateId);
            }
            //--1.2 新增资金变动表信息数据组装
            AccountChange accountChange = new AccountChange();
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType) || AccTranTypeEnum.ACC_FREEZE.getCode().equals(changeType)) {
                //资金授信账户号
                accountChange.setFundAccountCode(accountFund.getFundAccountCode());
                //资金类别
                accountChange.setFundType(accountFund.getFundType());
                //交易流水号
                accountChange.setTranCode(tradeNum);
                //账户交易类型
                accountChange.setChangeType(changeType);
                //变动金额
                accountChange.setChangeAmount(amount);
                //变动前账户总余额
                accountChange.setBeforeChangeAmount(accountFund.getTotalBalance());
                //变动前可用余额
                accountChange.setBeforeChangeAvailableAmount(accountFund.getAvailableBalance());
                //变动前冻结金额
                accountChange.setBeforeChangeFrozenAmount(accountFund.getFrozenAmount());
                accountChange.setCreateUser(operateId);
            }
            if (AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(changeType) || AccTranTypeEnum.ACC_CONSUME.getCode().equals(changeType)) {
                accountChange.setFundAccountCode(ac.getFundAccountCode());
                //资金类别
                accountChange.setFundType(ac.getFundType());
                //交易流水号
                accountChange.setTranCode(tradeNum);
                //账户交易类型
                accountChange.setChangeType(changeType);
                //变动金额
                accountChange.setChangeAmount(ac.getChangeAmount());
                //变动前账户总余额
                accountChange.setBeforeChangeAmount(accountFund.getTotalBalance());
                //变动前可用余额
                accountChange.setBeforeChangeAvailableAmount(accountFund.getAvailableBalance());
                //变动前冻结金额
                accountChange.setBeforeChangeFrozenAmount(accountFund.getFrozenAmount());
                accountChange.setCreateUser(operateId);
            }
            //--1.3 资金授信表信息更改数据组装
            AccountFund accountFund1 = new AccountFund();
            //资金授信账户号
            accountFund1.setFundAccountCode(accountFund.getFundAccountCode());
            //变动前可用余额
            accountFund1.setBeforeChangeAvailableAmount(accountFund.getAvailableBalance());
            //变动前冻结金额
            accountFund1.setBeforeChangeFrozenAmount(accountFund.getFrozenAmount());
            accountFund1.setLastChangeAmount(amount);
            //充值
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType)) {
                //总余额 = 变动前总金额 + amount
                accountFund1.setTotalBalance(accountFund.getTotalBalance() + amount);
            }
            //冻结解冻
            if (AccTranTypeEnum.ACC_FREEZE.getCode().equals(changeType) || AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(changeType)) {
                //总余额 = 总金额
                accountFund1.setTotalBalance(accountFund.getTotalBalance());
            }
            //扣款
            if (AccTranTypeEnum.ACC_CONSUME.getCode().equals(changeType)) {
                //总余额 = 变动前总金额 - amount
                accountFund1.setTotalBalance(accountFund.getTotalBalance() - ac.getChangeAmount());
            }
            //充值
            //累计总金额 = 变动前累计总金额  除了充值其它不变
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType)) {
                //累计总金额 = 变动前累计总金额 + amount
                accountFund1.setSumTotalAmount(accountFund.getSumTotalAmount() + amount);
            } else {
                //累计总金额 = 变动前累计总金额
                accountFund1.setSumTotalAmount(accountFund.getSumTotalAmount());
            }
            //变动前账户总余额
            accountFund1.setBeforeChangeTotalAmount(accountFund.getTotalBalance());
            //充值冻结金额不变
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType)) {
                //冻结金额   不变
                accountFund1.setFrozenAmount(accountFund.getFrozenAmount());
            }
            //冻结  冻结金额加上需要冻结的金额
            if (AccTranTypeEnum.ACC_FREEZE.getCode().equals(changeType)) {
                //冻结金额   之前冻结金额加上现在需要冻结的金额
                accountFund1.setFrozenAmount(accountFund.getFrozenAmount() + amount);
            }
            //解冻和扣款 减去变动金额
            if (AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(changeType) || AccTranTypeEnum.ACC_CONSUME.getCode().equals(changeType)) {
                //冻结金额   冻结金额减去变动金额
                accountFund1.setFrozenAmount(accountFund.getFrozenAmount() - ac.getChangeAmount());
            }
            //充值
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType)) {
                //可用金额 = 变动前可用余额 + amount
                accountFund1.setAvailableBalance(accountFund.getAvailableBalance() + amount);
            }
            //冻结
            if (AccTranTypeEnum.ACC_FREEZE.getCode().equals(changeType)) {
                //可用金额 = 变动前可用余额 - amount
                accountFund1.setAvailableBalance(accountFund.getAvailableBalance() - amount);
            }
            //解冻
            if (AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(changeType)) {
                //可用金额 = 变动前可用余额 + 变动金额
                accountFund1.setAvailableBalance(accountFund.getAvailableBalance() + ac.getChangeAmount());
            }
            //扣款
            if (AccTranTypeEnum.ACC_CONSUME.getCode().equals(changeType)) {
                //可用金额 = accountFund1变动前可用余额
                accountFund1.setAvailableBalance(accountFund.getAvailableBalance());
            }
            accountFund1.setUpdateUser(operateId);
            //加密字段  加密方式  = 资金账户号、账户累计总金额、可用余额、冻结金额、支付密码、账户加密密钥 进行联合MD5加密。
            //            String sign = accountFund.getCiphertext();
            //            String signKey = CommonConstants.KEY;
            //            String _input_charset = "UTF-8";
            //            if(!"".equals(sign) && sign!=null){
            //                Map<String, String> signMap = new LinkedHashMap<String, String>();
            //                signMap.put("fundAccountCode", accountFund.getFundAccountCode());
            //                signMap.put("sumTotalAmount", String.valueOf(accountFund.getSumTotalAmount()));
            //                signMap.put("availableBalance", String.valueOf(accountFund.getAvailableBalance()));
            //                signMap.put("frozenAmount", String.valueOf(accountFund.getFrozenAmount()));
            //                signMap.put("payPassword", accountFund.getPayPassword());
            //                signMap.put("signKey", signKey);
            //                log.info("============原有sign-"+sign);
            //                String toSign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), signKey, _input_charset);
            //                log.info("============新    sign-"+toSign);
            //                if(!toSign.equals(sign)){
            //                    log.info("加密字段不匹配");
            //
            //                    throw new DDPException(ResponseCode.SIGN_ERROR);
            //                }
            //            }
            //            Map<String, String> tosignMap = new LinkedHashMap<String, String>();
            //            tosignMap.put("fundAccountCode", accountFund1.getFundAccountCode());
            //            tosignMap.put("sumTotalAmount", String.valueOf(accountFund1.getSumTotalAmount()));
            //            tosignMap.put("availableBalance", String.valueOf(accountFund1.getAvailableBalance()));
            //            tosignMap.put("frozenAmount", String.valueOf(accountFund1.getFrozenAmount()));
            //             tosignMap.put("payPassword", accountFund1.getPayPassword());
            //            tosignMap.put("signKey", signKey);
            //            String saveSign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(tosignMap)), signKey, _input_charset);
            //            accountFund1.setCiphertext(saveSign);
            //-2数据更改
            //--2.1资金账户信息修改
            fundAccountMapper.updateFundAccount(accountFund1);
            //--2.2资金变动信息新增
            accountChangeMapper.addAccountChange(accountChange);
            if (AccTranTypeEnum.ACC_RECHARGE.getCode().equals(changeType) || AccTranTypeEnum.ACC_FREEZE.getCode().equals(changeType) || AccTranTypeEnum.ACC_UNFREEZE.getCode().equals(changeType)) {
                //--2.3资金变动累计表信息修改
                accountChangeSumMapper.updateAccountChangeSum(acs);
            }
            log.info("变动信息更改结束=======================");
        }
    }

    /**
     * @param accountChangeSum
     * @description 充值首次，如果相应的累计变动表中没有记录，则新增一条记录
     */
    @Transactional
    public void addAccountChangeSum(AccountChangeSum accountChangeSum) {
        accountChangeSumMapper.addAccountChangeSum(accountChangeSum);
    }

    @Transactional
    public AccountControl queryAccountControl(String custType, String custNum, long amount) {
        //根据客户类型和客户号得到主账户code
        Account at = accountMapper.queryAccoutCode(custType, custNum);
        AccountFund accountFund = null;
        AccountChangeSum accountChangeSum = null;
        AccountControl accountControl = null;
        try {
            accountFund = fundAccountMapper.queryFundAccount(at.getAccountCode(), FundTypeEnum.FUND.getCode());
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            //根据资金授信编号查询当天资金变动累计信息
            accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(accountFund.getFundAccountCode(), dateStr);
            //根据资金授信编号查询账户风控信息
            accountControl = accountControlMapper.queryAccountControl(accountFund.getFundAccountCode());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return accountControl;
    }

    /**
     * @description 账户充值风控检查
     * @param custType
     * @param custNum
     * @param amount
     * @throws SQLException
     */
    @Override
    @Transactional
    public void checkRecharge(String custType, String custNum, long amount) throws SQLException {
        log.info("AccountService账户充值风控检查接口checkRecharge开始========================");
        //根据客户类型和客户号得到主账户code
        Account at = accountMapper.queryAccoutCode(custType, custNum);
        if (at == null) {
            log.info("AccountService账户充值风控检查接口checkRecharge查询主账户为空");
            //主账户信息不存在
            throw new DDPException(ResponseCode.ACC_ACCOUNT_NOTEXIT);
        }
        AccountFund accountFund = fundAccountMapper.queryFundAccount(at.getAccountCode(), FundTypeEnum.FUND.getCode());
        if (accountFund == null) {
            log.info("AccountService账户充值风控检查接口checkRecharge查询授信账户不存在");
            //资金授信账户信息不存在
            throw new DDPException(ResponseCode.ACC_FUNDAUTHACCOUNT_NONEXISTENT_RROR);
        }
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        //根据资金授信编号查询当天资金变动累计信息
        AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSum(accountFund.getFundAccountCode(), dateStr);
        //根据资金授信编号查询账户风控信息
        AccountControl accountControl = accountControlMapper.queryAccountControl(accountFund.getFundAccountCode());
        if (accountControl == null) {
            log.info("AccountService账户充值接口checkRecharge查询风控信息不存在");
            //资金授信账户信息不存在
            throw new DDPException(ResponseCode.ACC_TRAN_CONTROL_EMPTY);
        }
        //资金账户风控检查
        String returnMessage = allowedToRecharge(accountFund, accountChangeSum, accountControl, amount);
        log.info("=========检查风控返回code==========" + returnMessage);
        if (!AccountConstants.CHECK_SUCCESS.equals(returnMessage)) {
            log.info("AccountService账户充值接口checkRecharge查询风控信息不通过");
            throw new DDPException(returnMessage);
        }
        log.info("AccountService账户充值风控检查接口checkRecharge检查风控返回===" + returnMessage);
    }
}
