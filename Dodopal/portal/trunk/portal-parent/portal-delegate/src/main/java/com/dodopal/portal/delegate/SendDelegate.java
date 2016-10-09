package com.dodopal.portal.delegate;

import java.util.Map;

import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface SendDelegate {

    /**
     * 短信发送
     * @param moblieNum 手机号
     * @param codeType 短信类型
     * @return 
     * key1:dypwd 验证码 
     * key2:pwdseq 序号
     */
    public DodopalResponse<Map<String, String>> send(String moblieNum, MoblieCodeTypeEnum codeType);

    /**
     * 短信发送验证码（不校验手机号是否注册）
     * @param moblieNum 手机号
     * @return 
     * key1:dypwd 验证码 
     * key2:pwdseq 序号
     */
    public DodopalResponse<Map<String, String>> sendNoCheck(String moblieNum);

    /**
     * 手机验证码验证
     * @param moblieNum 手机号
     * @param dypwd 验证码
     * @param serialNumber 序号
     * @return
     */
    public DodopalResponse<String> moblieCodeCheck(String moblieNum, String dypwd, String serialNumber);
}
