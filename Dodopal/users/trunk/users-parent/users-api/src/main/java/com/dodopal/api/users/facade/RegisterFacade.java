package com.dodopal.api.users.facade;

import java.util.Map;

import com.dodopal.api.users.dto.CommonUserDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface RegisterFacade {
    /**
     * 校验手机号是否已注册
     * @param mobile
     * @return
     */
    public DodopalResponse<Boolean> checkMobileExist(String mobile);

    /**
     * 校验手机号是否已注册(排除传入的商户号对应的)
     * @param mobile
     * @param merCode
     * @return
     */
    public DodopalResponse<Boolean> checkMobileExist(String mobile, String merCode);

    /**
     * 校验用户名是否已注册
     * @param userName
     * @return
     */
    public DodopalResponse<Boolean> checkUserNameExist(String userName);

    /**
     * 校验用户名是否已注册(排除传入的商户号对应的)
     * @param userName
     * @param merCode
     * @return
     */
    public DodopalResponse<Boolean> checkUserNameExist(String userName, String merCode);

    /**
     * 校验商户名是否已注册
     * @param merName
     * @return
     */
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName);

    /**
     * 校验商户名是否已注册(排除传入的商户号对应的)
     * @param merName
     * @param merCode
     * @return
     */
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName, String merCode);

    /**
     * 个人注册（门户）
     * @param merUserDTO
     * @return
     */
    public DodopalResponse<MerchantUserDTO> registerUser(MerchantUserDTO merUserDTO, String dypwd, String serialNumber);

    /**
     * 个人注册（手机端）
     * @param mobile
     * @param userName
     * @param password
     * @return
     */
    public DodopalResponse<Boolean> registerUserForMobile(String mobile, String userName, String password, String source, String cityCode);

    /**
     * 商户注册（门户）
     * @param merchant
     * @return
     */
    public DodopalResponse<String> registerMerchant(MerchantDTO merchant, String dypwd, String serialNumber);

    /**
     * 通用个人注册 
     * @param user
     * @return
     */
    public DodopalResponse<Map<String,String>> registerComUser(CommonUserDTO user);
    
    /**
     * 商户注册（VC端）
     * @param merchant
     * @return
     */
    public DodopalResponse<String> registerMerchantForVC(MerchantDTO merchant);
}
