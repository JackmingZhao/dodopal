package com.dodopal.users.business.service;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface RegisterService {
    /**
     * 检查用户手机号是否存在
     * @param mobile
     * @return
     */
    public boolean checkMobileExist(String mobile, String merCode);

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    public boolean checkUserNameExist(String userName, String merCode);

    /**
     * 检查商户名是否存在
     * @param merName
     * @return
     */
    public boolean checkMerchantNameExist(String merName, String merCode);

}
