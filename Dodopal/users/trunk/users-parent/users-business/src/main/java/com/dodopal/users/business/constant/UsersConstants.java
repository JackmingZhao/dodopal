package com.dodopal.users.business.constant;

public class UsersConstants {

	/**验证码地址*/
    public static final String SEND_MOB_MSG_RUL = "send.mob.msg.url";
    /**开户随机密码是否发送短信通知*/
    public static final String MERCHANT_REGISTER_SENDMSG = "merchant.register.sendmsg";
    /**开户随机密码是否发送短信到指定手机号*/
    public static final String MERCHANT_REGISTER_TEST_MOBILE = "merchant.register.test.mobile";

    /**查询来源*/
    
    //门户
    public static final String FIND_WEB = "0";
    
    //OSS
    public static final String FIND_OSS = "1";

    // 随机密码长度
    public static final int MER_USER_PWD_LENGTH = 8;
    
    // 正则
    //4-20位字符(字母、数字、下划线)，首位必须为字母
    public static final String REG_USER_NAME = "[a-zA-Z][a-zA-Z0-9_]{3,19}";
    
    // TODO:秘钥类型
    public static final String MER_KEY_TYPE_MD5 = "MD5";

    // 批次单管理人员权限CODE TODO:暂未确定
    public static final String BATCH_RCG_ORDER_MANAGE_FUN_CODE = "merchant.order.batchrc.manage";
}
