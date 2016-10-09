package com.dodopal.transfernew.business.service;

import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.model.transfer.MerchantUser;

public interface MerchantUserService {
    /**
     * 生成用户编号
     * 1位(是否为测试账户)+ 4位随机数 + 12位数据库sequence.
     * 99-个人;  1-正式商户用户; 2--测试商户用户; 3--正式个人用户;  4--个人测试用户
     * @param merType
     * @param classify
     * @return
     */
    public String generateMerUserCode(UserClassifyEnum userClassify);

    /**
     * 查找用户是否存在
     * @param merUser
     * @return
     */
    public boolean checkExist(String merUserName);

	/**
	 * 创建个人用户相关信息
	 * 
	 * @param merUser
	 * @return
	 */
	public DodopalResponse<String> addMerchantUserInfo(MerchantUser merUser);
}
