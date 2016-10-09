package com.dodopal.portal.business.constant;

public class PortalConstants {

	//门户
	public static final String FIND_WEB = "0";

	//OSS
	public static final String FIND_OSS = "1";

	public static final String DODOPAL_RESPONSE = "DODOPAL_RESPONSE";

	/**
	 * session key
	 */

	//登录系统出错放到session中的错误信息的key
	public static final String SESSION_ERROR_LOGIN_MSG = "SESSION_ERROR_LOGIN_MSG";

	// 当前登录用户
	public static final String SESSION_USER = "sessionUser";

	public static final String SESSION_KAPTCHATYPE_LOGIN = "login";

	public static final String SESSION_KAPTCHATYPE_PERSON_REGISTER = "personRegister";

	public static final String SESSION_KAPTCHATYPE_MER_REGISTER = "merRegister";

	public static final String SESSION_KAPTCHATYPE_PERSON_RESETPWD = "personResetPwd";
	//重置密码的新密码
	public static final String PWDRESET = "RESETPWD";

	public static final String ACTIVE = "0";

	public static final String INACTIVE = "1";

	//密码的序列
	public static final String PWDSEQ = "PWDSEQ";

	//找回手机号
	public static final String PWDMOBILE = "PWDMOBILE";

	//验证码状态 0 登录成功
	public static final String AUTHCODESTATUS = "AUTHCODESTATUS";

	//最后一次发送的时间
	public static final String LASTSENDTIME = "LASTSENDTIME";

	/**
	 * 重置账号
	 */
	public static final String RESETNAME = "RESETNAME";
	/**
	 * session key end
	 */

	public static final String LOGIN_NAME = "LoginName";

	public static final String SERIAL_NUMBER = "serialNumber";

	public static final String RESET_PWD_SUCCESS = "恭喜，设置密码成功！";
	public static final String RESET_PWD_FAIL = "对不起，设置密码失败！";

	public static final String ALL_FUNCTIONS_IN_SESSION = "ALL_FUNCTIONS_IN_SESSION";
	
	public static final String DODOPAY_RECHARGE_URL="dodopal.recharge.url";
	
}
