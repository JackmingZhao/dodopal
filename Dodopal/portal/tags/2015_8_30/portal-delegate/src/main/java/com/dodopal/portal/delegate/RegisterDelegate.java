package com.dodopal.portal.delegate;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：调用用户系统注册接口
 * @author lifeng
 */

public interface RegisterDelegate {
    /**
     * 检查手机号是否已注册
     * @param mobile
     * @return true:已注册
     */
    public DodopalResponse<Boolean> checkMobileExist(String mobile);

    /**
     * 检查用户名是否已注册
     * @param userName
     * @return true:已注册
     */
    public DodopalResponse<Boolean> checkUserNameExist(String userName);

    /**
     * 检查商户名称是否已注册
     * @param merName
     * @return
     */
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName);

    /**
     * 个人用户注册
     * @param merUserBean
     * @return
     */
    public DodopalResponse<MerchantUserDTO> registerUser(MerchantUserDTO merchantUserDTO, String dypwd, String serialNumber);

    /**
     * 商户注册
     * @param merUserBean 
     * merUserBean.merUserName：用户名
     * merUserBean.merUserPWD：密码
     * merUserBean.merUserMobile：用户手机号
     * @return 商户号
     */
    public DodopalResponse<String> registerMerchant(MerchantDTO merchantDTO, String dypwd, String serialNumber);
}
