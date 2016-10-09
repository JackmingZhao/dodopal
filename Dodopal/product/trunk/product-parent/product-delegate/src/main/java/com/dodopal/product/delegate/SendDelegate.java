package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author lifeng@dodopal.com
 */

public interface SendDelegate {
	/**
	 * 发送4位验证码（不校验手机是否已经存在）
	 * 
	 * @param mobileNum
	 * @return
	 */
	public DodopalResponse<Map<String, String>> sendNoCheck(String mobileNum);

	/**
	 * 短信发送（会校验手机号是否已存在）
	 * 
	 * @param mobileNum
	 * @param codeType
	 * @return
	 */
	public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType);

	/**
	 * 手机验证码验证
	 * 
	 * @param moblieNum
	 * @param dypwd
	 *            验证码
	 * @param serialNumber
	 *            序号
	 * @return
	 */
	public DodopalResponse<String> mobileCodeCheck(String mobileNum, String dypwd, String serialNumber);
}
