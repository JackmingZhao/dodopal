package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.transfer.MerchantUser;

public interface MerchantUserMapper {
    /**
     * 用户编码使用的sequence
     * @return
     */
    public String getMerUserCodeSeq();

    /**
     * 查找用户是否存在(用户名)
     * @param merUser
     * @return
     */
    public boolean checkExist(@Param("merUserName")String merUserName);

    /**
     * 增加商户用户记录
     * @param merchantUser
     */
    public int addMerchantUser(MerchantUser merchantUser);

}
