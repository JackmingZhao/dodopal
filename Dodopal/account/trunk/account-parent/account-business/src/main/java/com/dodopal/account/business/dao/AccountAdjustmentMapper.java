package com.dodopal.account.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.account.business.bean.AccountFundAdjustInfoDTO;

public interface AccountAdjustmentMapper {

    /**
     * 9    账户调帐 用  根据调账申请号取得对应资金授信账户及调账申请单信息     add by shenxiang
     * 
     * @param adjustCode 调账申请单号
     * @return 
     */
    public AccountFundAdjustInfoDTO getAccountFundInfoByAdjustCode(@Param("adjustCode")String adjustCode);
    
}
