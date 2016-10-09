package com.dodopal.portal.delegate;

import java.util.Map;

import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;

public interface AccountSecureDelegate {
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
    public DodopalResponse<Map<String, String>> send(String moblieNum,MoblieCodeTypeEnum codeType);

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
    public DodopalResponse<String> moblieCodeCheck(String moblieNum,String dypwd,String serialNumber);
    
    /**
     * 保存手机号码
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Boolean> saveMerUserMoblie(MerchantUserDTO merchantUserDTO);
    /**
     * 效验当前密码是否正确
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Boolean> validateMerUserPWD(String id,String merUserPWD);
    /**
     * 更新密码
     * @param id
     * @param merUserPWD
     * @param newMerUserPWD
     * @return
     */
    public DodopalResponse<Boolean> updateMerUserPWDById(String id,String merUserPWD,String newMerUserPWD);

    /**
     * 查询验签和签名密钥
     * @param merKeyTypeDTO
     * @param enum1
     * @return
     */
    public DodopalResponse<MerKeyTypeDTO> findMerMD5PayPWDOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,MerKeyTypeMD5PwdEnum enum1);
    
    /**
     * 更新验签和签名密钥
     * @param merKeyTypeDTO
     * @param oldPWD
     * @param enum1
     * @return
     */
    public DodopalResponse<Boolean> updateMerMD5PayPwdOrBackPayPWD(MerKeyTypeDTO merKeyTypeDTO,String oldPWD,MerKeyTypeMD5PwdEnum enum1);
   
    
    /**
     * 根据ID查询当前用户信息
     * @param id
     * @return
     */
     public DodopalResponse<MerchantUserDTO> findModifyPayInfoFlg(String id);
    
    /**
    * 更改是否弹出支付确认信息:0.为显示弹出信息；1.为不显示弹出信息,默认为0
    * @param merKeyTypeDTO
    * @return
    */
    public DodopalResponse<Boolean> upModifyPayInfoFlg(MerchantUserDTO merchantUserDTO);
        
}
