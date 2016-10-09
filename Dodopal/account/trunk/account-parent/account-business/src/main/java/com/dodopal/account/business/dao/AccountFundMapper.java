package com.dodopal.account.business.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.account.business.model.AccountFund;

/**
 * 资金授信账户
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月24日
 *
 */
public interface AccountFundMapper {
    /**
     * 查询 门户首页 账户可用余额
     * @param aType 枚举（个人 、商户）
     * @param custNum 类型是商户则为商户号  类型是个人则为用户号
     * @return List<AccountFund>
     */
    public List<AccountFund> findAccountBalance(@Param("aType")String aType,@Param("custNum")String custNum) throws SQLException;
    
    /**
     * @description 新增资金授信表记录
     * @param fundAccount
     */
    public void addFundAccount(AccountFund fundAccount);

    /**
     * @description 根据客户类型和客户号查询客户的资金授信记录信息
     * @return
     */
    public AccountFund queryFundAccount(@Param("accountCode")String accountCode,@Param("fundType")String fundType) throws SQLException;

    public List<AccountFund> queryFundAccountList(@Param("accountCode")String accountCode);

    /**
     * @description 资金账户信息修改
     * @param accountFund
     */
    public int updateFundAccount(AccountFund accountFund);
    
    /**
     * 根据ACCOUNT_CODE查询资金授信账户信息
     * @param actCode
     * @return
     */
    public List<AccountFund> findAccountFundByActCode(String actCode);
    
    /**
     * 资金授信查询 add by shenxiang
     * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * @param custType
     * @param custNum
     * @return
     */
    public List<Map<String, String>> findAccountFundInfo(@Param("custType")String custType,@Param("custNum")String custNum);
    
    
    /**
     * 账户调帐——资金账户信息修改 add by shenxiang
     * @param accountFund
     */
    public int updateFundAccountForAccountAdjustment(AccountFund accountFund);
    
    /**
     * 修改资金账户信息 的 授信额度字段
     * @param accountFund
     */
    public int updateFundAccountCreditAmt(AccountFund accountFund);
	
	/**
     * 资金授信账户状态查询(用于更新)
     * @param custType
     * @param custNum
     * @param fundType
     * @return
     */
    public AccountFund queryAccountFundInfoByCustTypeAndCustNumForUpdate(@Param("custType")String custType,@Param("custNum")String custNum,@Param("fundType")String fundType);
    
    /**
     * 资金授信账户状态查询
     * @param custType
     * @param custNum
     * @param fundType
     * @return
     */
    public AccountFund queryAccountFundInfoByCustTypeAndCustNum(@Param("custType")String custType,@Param("custNum")String custNum,@Param("fundType")String fundType);
    
    /**
     * 禁用/启用账户
     * @param oper
     * @param fundAccountIds
     * @return
     */
    public void operateFundAccountsById(@Param("oper") String oper, @Param("fundAccountId") String fundAccountId, @Param("userId") String userId);
}
