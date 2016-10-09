package com.dodopal.portal.business.service;

import java.util.Map;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserUpBean;
import com.dodopal.portal.business.bean.MobileBean;

public interface AccountSecureService {
    /**
     * 手机发送验证码
     * @param moblieNum 手机号
     * @param codeType 短信类型  
     *              1：个人都都宝注册；
     *              4：个人变更终端设备；
     *              5：个人找回密码；
     *              6：网点客户端注册
     * @return
     */
    public DodopalResponse<Map<String, String>> send(String moblieNum);

    /**
     * 手机发送验证码
     * @param moblieNum 手机号
     * @return
     */
    public DodopalResponse<Map<String, String>> sendNoCheck(String moblieNum);

    /**
     * 手机验证码验证
     * @param moblieNum    手机号
     * @param dypwd        验证码
     * @param serialNumber 序号
     * @return
     */
    public DodopalResponse<String> moblieCodeCheck(MobileBean mobileBean);
    
    /**
     * 保存新的手机号
     * @param mobileBean 手机号验证
     * @param merCode 商户号
     * @return
     */
    public DodopalResponse<String> saveAccountMoblie(MobileBean mobileBean,MerchantUserBean merchUserBaen);
    
    /**
     * 效验老密码
     * @param merchantUserBean
     * @return
     */
    public DodopalResponse<String> validateMerUserPWD(MerchantUserUpBean merchantUserUpBean); 
    
    /**
     * 更新老密码
     * @param merchantUserBean
     * @return
     */
    public DodopalResponse<String> updateMerUserPWDById(MerchantUserUpBean merchantUserUpBean);
    /**
     * 查询签名密钥
     * @param merchantUserUpBean
     * @return
     */
    public DodopalResponse<MerchantUserUpBean> findMerMD5PayPWD(MerchantUserUpBean merchantUserUpBean);
    /**
     * 查询验签秘钥
     * @param merchantUserUpBean
     * @return
     */
    public DodopalResponse<MerchantUserUpBean> findMerMD5BackPayPWD(MerchantUserUpBean merchantUserUpBean);
    /**
     * 更新签名密钥
     * @param merchantUserUpBean
     * @return
     */
    public DodopalResponse<String> updateMerMD5PayPWD(MerchantUserUpBean merchantUserUpBean);
    /**
     * 更新验签密钥
     * @param merchantUserUpBean
     * @return
     */
    public DodopalResponse<String> updateMerMD5BackPayPWD(MerchantUserUpBean merchantUserUpBean);
    /**
     * 根据ID查询当前登录的信息
     * @param id
     * @return
     */
    public DodopalResponse<MerchantUserBean> findModifyPayInfoFlg(String id);
    /**
     * 更改是否弹出支付确认信息:0.为显示弹出信息；1.为不显示弹出信息,默认为0
     * @param merchUserBaen
     * @return
     */
    public DodopalResponse<String> upModifyPayInfoFlg(MerchantUserBean merchUserBaen);
}
