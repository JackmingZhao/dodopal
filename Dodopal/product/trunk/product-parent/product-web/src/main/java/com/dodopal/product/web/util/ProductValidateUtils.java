package com.dodopal.product.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DDPStringUtil;

/**
 * @author lifeng@dodopal.com
 */

public class ProductValidateUtils {
	// 4-20位字符(字母、数字、下划线)，首位必须为字母
	private static final String REG_USER_NAME = "[a-zA-Z][a-zA-Z0-9_]{3,19}";

	/**
	 * 校验用户名，4-20位字符(字母、数字、下划线)，首位必须为字母
	 * 
	 * @param merUserName
	 */
	public static void validateMerUserName(String merUserName) {
		if (StringUtils.isBlank(merUserName)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_USER_NAME_NULL);
		}
		Pattern pattern = Pattern.compile(REG_USER_NAME);
		Matcher matcher = pattern.matcher(merUserName);
		if (!matcher.matches()) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_USER_NAME_ERROR);
		}
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 */
	public static void validateMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_MER_USER_MOBILE_NULL);
		}
		if (!DDPStringUtil.isMobile(mobile)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_MER_USER_MOBILE_ERROR);
		}
	}

	/**
	 * 校验用户密码
	 * 
	 * @param userPwd
	 */
	public static void validateUserPwd(String userPwd) {
		if (StringUtils.isBlank(userPwd) || userPwd.length() != 32) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_PASSWORD_ERROR);
		}
	}
}
