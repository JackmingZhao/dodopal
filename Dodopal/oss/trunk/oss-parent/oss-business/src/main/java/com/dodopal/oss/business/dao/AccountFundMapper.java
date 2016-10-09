package com.dodopal.oss.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.oss.business.model.AccountFund;

/**
 * 资金授信账户
 */
public interface AccountFundMapper {

    /**
     * @description 根据主账户编号和账户资金类型查询客户的资金授信记录信息
     * @return
     */
    public AccountFund getFundAccountInfo(@Param("accountCode")String accountCode,@Param("fundType")String fundType);

    /**
     * @description 资金账户信息修改
     * @param accountFund
     */
    public int updateFundAccount(AccountFund accountFund);
	
}
