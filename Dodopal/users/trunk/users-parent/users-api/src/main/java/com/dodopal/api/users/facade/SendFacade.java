package com.dodopal.api.users.facade;


import java.util.Map;

import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;

public interface SendFacade {
	
	/**
	 * 手机发送验证码
	 * @param mobileNum 手机号
	 * @param codeType 短信类型  
	 *              1：个人都都宝注册；
	 * 				4：个人变更终端设备；
	 * 				5：个人找回密码；
	 * 				6：网点客户端注册
	 * @return
	 * 			dypwd：验证码
	 * 			pwdseq：序号
	 */
	public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType);

	/**
     * 手机发送验证码
     * @param mobileNum 手机号
     * @return
     */
    public DodopalResponse<Map<String, String>> sendNoCheck(String mobileNum);

	/**
	 * 手机验证码验证
	 * @param mobileNum    手机号
	 * @param dypwd        验证码
	 * @param serialNumber 序号
	 * @return
	 */
	public DodopalResponse<String> moblieCodeCheck(String mobileNum,String dypwd,String serialNumber);

}

