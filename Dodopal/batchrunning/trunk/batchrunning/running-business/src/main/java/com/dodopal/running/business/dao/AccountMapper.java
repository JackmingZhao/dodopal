package com.dodopal.running.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.running.business.model.Account;


public interface AccountMapper {

    /**
     * 根据客户号和客户类型得到主账户
     * @param customerType 客户类型
     * @param customerNo 客户号
     * @return
     */
    public Account getAccountInfo(@Param("customerType")String customerType,@Param("customerNo")String customerNo);
  
}
