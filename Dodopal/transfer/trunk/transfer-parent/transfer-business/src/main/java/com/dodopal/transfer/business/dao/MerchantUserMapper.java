package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.target.MerchantUser;

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
    public void addMerchantUser(MerchantUser merchantUser);


    /**
     * @author Mikaelyan
     * 增加商户用户记录
     * @param merchantUser
     */
    public void insertMerchantUser(MerchantUser merchantUser);
    
}
