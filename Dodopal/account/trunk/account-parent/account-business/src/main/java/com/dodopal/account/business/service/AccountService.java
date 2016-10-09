/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.service;

import com.dodopal.account.business.model.*;
import com.dodopal.api.account.dto.AccountTransferDTO;
import com.dodopal.common.model.DodopalResponse;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2015/8/21.
 */
public interface AccountService {
    /**
     * @description 创建账户信息
     * @param account
     * @param fundAccount
     * @param accountControl
     */
    public void createAccount(Account account,AccountFund fundAccount,AccountControl accountControl,AccountControl accountControl1,AccountFund fundAccount1,String accType)throws SQLException;
    /**
     * 获取sequence序列的下一个值
     */
    public String getSequenceNextId();

    /**
     *@description 根据账户类型和账户号查询资金授信记录信息(根据ACCOUNT表和ACCOUNT_FUND表)
     * @param accountCode 主账户编号
     * @return AccountFund
     */
    public AccountFund queryFundAccount(String accountCode,String fundType) throws SQLException;
    /**
     * @description 根据资金授信账户编号查询风险控制信息
     * @param fundAccountCode 资金授信编号
     * @return AccountControl
     */
    public AccountControl queryAccountControl(String fundAccountCode);
    /**
     * @description 根据资金授信账户编号查询资金变动信息
     * @param fundAccountCode 资金授信编号
     * @return AccountChangeSum
     */
    public AccountChangeSum queryAccountChangeSum(String fundAccountCode,String today);
    /**
     * @description 充值功能实现
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     */
    public void accountRecharge(String custType, String custNum,String tradeNum, long amount,String operateId) throws SQLException;
    /**
     * @description 资金冻结功能实现
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     */
    public void accountFreeze(String custType, String custNum,String tradeNum, long amount,String operateId)throws SQLException;

    /**
     * @description 资金冻结功能实现
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     */
    public void accountUnfreeze(String custType, String custNum,String tradeNum, long amount,String operateId)throws SQLException;
    /**
     * @description 资金账户扣款功能实现
     * @param custType 客户类型
     * @param custNum 客户编号
     * @param tradeNum 交易流水号
     * @param amount 金额
     * @return
     */
    public void accountDeduct(String custType, String custNum,String tradeNum, long amount,String operateId)throws SQLException;

    /**
     * @description
     * @param merType
     * @return
     */
    public AccountControllerDefault queryControlDefault(String merType);

    /**
     * @description  账户充值时风控检查
     * @param custType
     * @param custNum
     * @param amount
     * @throws SQLException
     */
    public void checkRecharge(String custType, String custNum,long amount)throws SQLException;
    
    /**
     * @description  查询风控阀值
     * @param custType
     * @param custNum
     * @param amount
     * @return
     */
    public AccountControl queryAccountControl(String custType, String custNum, long amount);
}