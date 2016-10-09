package com.dodopal.account.business.service;

import java.sql.SQLException;
import java.util.List;

import com.dodopal.account.business.model.Account;
import com.dodopal.account.business.model.AccountChange;
import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.model.AccountMainInfo;
import com.dodopal.account.business.model.query.AccountChangeQuery;
import com.dodopal.account.business.model.query.AccountInfoListQuery;
import com.dodopal.account.business.model.query.FundChangeQuery;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.common.model.DodopalDataPage;

public interface AccountQueryService {
    /**
     * 查询门户首页可用余额
     * @param aType 个人 or 企业
     * @param custNum 用户号 or 商户号
     * @return
     */
    public List<AccountFund> findAccountBalance(String aType,String custNum)throws SQLException;
    /**
     * 查询资金变更记录（分页）
     * @param query
     * @return
     */
    public DodopalDataPage<AccountChange> findFundsChangeRecordsByPage(FundChangeQuery query);
    
    /**
     * 根据主键id查询主账户信息
     * @param acid
     * @return
     */
    public Account findAccountByAcid(String acid);
    
    /**
     * 根据ACCOUNT_CODE查询资金授信账户信息
     * @param actCode
     * @return
     */
    public List<AccountFund> findAccountFundByActCode(String actCode);
    
    /**
     * 根据FUND_ACCOUNT_CODE查询资金账户风控信息
     * @param fundActCode
     * @return
     */
    public List<AccountControl> findAccountControlByActCode(List<String> fundActCodes);
    
    /**
     * 资金授信查询 add by shenxiang
     * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * 
     * @param custType
     * @param custNum
     * @return
     */
    public FundAccountInfoDTO findFundAccountInfo(String custType, String custNum);
    
    /**
     * 资金授信账户信息列表查询
     * @param accountInfoListQuery
     * @return
     */
    public DodopalDataPage<AccountMainInfo> findAccountInfoListByPage(AccountInfoListQuery accountInfoListQuery);
    
    /**
     * 查询资金变更记录
     * @param query
     * @return  List<AccountChange>
     */
    public List<AccountChange> findFundsChangeRecordsAll(FundChangeQuery query);
    
    
    /**
     * 查询资金变更记录（手机、VC端接入）
     * @param queryRequest
     * @return List<AccountChange>
     */
    public List<AccountChange> queryAccountChangeByPhone(AccountChangeQuery queryRequest);
    
    /**
     * 导出账户数据
     * @param accountInfoListQuery
     * @return
     */
    public List<AccountMainInfo> expAccountInfo(AccountInfoListQuery accountInfoListQuery);
}
