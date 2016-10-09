package com.dodopal.users.business.service;

import java.util.Map;

import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface SendService {
    /**
     * 手机发送验证码
     * @param mobileNum 手机号
     * @param codeType 短信类型
     * @return
     */
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType);

    /**
     * 手机发送验证码
     * @param mobileNum 手机号
     * @param codeType 短信类型
     * @param dypwd 验证码内容
     * @return
     */
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, String dypwd);

    /**
     * 手机发送验证码
     * @param mobileNum 手机号
     * @param codeType 短信类型
     * @param checkExist 是否校验手机号
     * @return
     */
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, boolean checkExist);

    /**
     * 手机发送验证码
     * @param mobileNum 手机号
     * @param codeType 短信类型
     * @param dypwd 验证码内容
     * @param checkExist 是否校验手机号
     * @return
     */
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, String dypwd, boolean checkExist);

    /**
     * 手机验证码验证
     * @param moblieNum 手机号
     * @param dypwd 验证码内容
     * @param serialNumber 序号
     * @return
     */
    public DodopalResponse<String> moblieCodeCheck(String moblieNum, String dypwd, String serialNumber);
}
