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
	//用户输入的用户名
	public static final String SESSION_ERROR_LOGIN_NAME = "SESSION_ERROR_LOGIN_NAME";

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
	/**
	 * 账户充值
	 */
	public static final String DODOPAY_RECHARGE_URL="dodopal.recharge.url";

	/** 登录时用户名input对应的id */
	public static final String REG_J_USERNAME = "j_username";
	/** 个人注册成功后放入session的用户名、密码 */
	public static final String REG_USER_NAME = "regUserName";
	public static final String REG_USER_PWD = "regUserPWD";
	
	   
    //补单程序 日志服务器名称
    public static final String SERVER_LOG_NAME = "server.log.name";
    
    //补单程序 日志服务器路径
    public static final String SERVER_LOG_URL = "server.log.url";
}
