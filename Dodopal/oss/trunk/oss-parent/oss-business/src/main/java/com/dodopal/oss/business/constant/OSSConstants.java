package com.dodopal.oss.business.constant;

public class OSSConstants {

    public static final String MENU_TYPE_BUTTON = "button";

    //登录系统出错放到session中的错误信息的key
    public static final String SESSION_ERROR_LOGIN_MSG = "SESSION_ERROR_LOGIN_MSG";

    //登录系统的用户
    public static final String SESSION_USER = "SESSION_USER";

    //当前登录用户对应的角色关联的所有Operation
    public static final String ALL_FUNCTIONS_IN_SESSION = "ALL_FUNCTIONS_IN_SESSION";
    
    //系统的超级管理员用户
    public static final String ADMIN_USER = "admin";
    
    //系统的超级管理员用户角色
    public static final String ADMIN_ROLE = "admin";
    
    public static final String POS_BIND = "0";
    
    public static final String QUERY_BUTTON = ".query";
    
    //补单程序 日志服务器名称
    public static final String SERVER_LOG_NAME = "server.log.name";
    
    //补单程序 日志服务器路径
    public static final String SERVER_LOG_URL = "server.log.url";

   
    //****************   OSS 处理异常交易流水 产生资金授信账户变动追加CHECK数据库最大容量      START     ******************//
    //资金授信账户总金额最大值
    public static final long TOTALBALANCE_MAX = 9999999999L;
    //资金授信账户充值累计金额最大值
    public static final long TOTALBALANCE_SUM_MAX = 999999999999L;
    //资金授信账户每日转账累计金额最大值
    public static final long TOTALBALANCE_TRAN_SUM_MAX = 9999999999L;
    //****************   OSS 处理异常交易流水 产生资金授信账户变动追加CHECK数据库最大容量      ENDrAr      ******************//
    
    //验证码
	public static final String SESSION_KAPTCHATYPE_LOGIN = "login";

}
