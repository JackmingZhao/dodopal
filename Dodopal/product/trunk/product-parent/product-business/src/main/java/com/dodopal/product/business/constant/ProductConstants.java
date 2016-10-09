package com.dodopal.product.business.constant;


public class ProductConstants {


    //一卡通充值产品大类
    public static final String procuct_ykt_type = "10";

	public static final String COMMA = ",";
	
	public static final String EMPTY = "";
	
	public static final String USER_DEFINED = "自定义产品";
	
	// 标准产品面额
	public static final int[] PRODUCT_STANDARD_CARD_DENOMINATION = {50, 100 ,200 ,300 ,400, 500}; 
	
	// 自定义产品面额
	public static final int PRODUCT__USER_DEFINED_CARD_DENOMINATION = -1; 
	
	/**
	 * 城市code
	 */
	public static final String CREAT_ICDCPAY_MAP_KEY_CITYCODE = "cityCode";
	
	/**
	 * 城市名称
	 */
	public static final String CREAT_ICDCPAY_MAP_KEY_CITYNAME = "cityName";
	
	/**
	 * 费率
	 */
	public static final String CREAT_ICDCPAY_MAP_KEY_RATE = "rate";
	
	/**
	 * 当前账号
	 */
	public static final String CARD_RECHARGE_USERNAME = "cardUserName";
	
	/**
	 * 当前用户号
	 */
	public static final String CARD_RECHARGE_USERCODE = "cardUserCode";
	/**
	 * 当前登录人
	 */
	public static final String CARD_LOGIN_USER = "loginUser";
	
	/**
	 * 商户未开通城市页面提示语
	 */
	public static final String CARD_CITY_NULL = "请先开通业务城市";

    /**
     * 当前选择的城市
     */
    public static final String CARD_RECHARGE_CITY= "rechargeCity";
    
    /**
     * 商户类型
     */
    public static final String CARD_MER_TYPE= "loginMerType";
    
    /**
     * 外接商户
     */
    public static final String EXTERNAL= "EXTERNAL";
    
    /**
     * 外接商户用户的操作人id
     */
    public static final String EXTERNAL_OPERATORID= "externalOperatorId";
    
    
    /**
     * 外接商户登录的用户code
     */
    public static final String EXTERNAL_CARD_RECHARGE_USERCODE = "externalCardUserCode";
    
    /**
     * 外接商户登录的用户code
     */
    public static final String EXTERNAL_CARD_RECHARGE_USERNAME = "externalCardUserName";
    
    /**
     * 外接商户页面内嵌当前登录人（外接商户）
     */
    public static final String EXTERNAL_CARD_LOGIN_USER = "externalloginUser";
    
    /**
     * 北京城市code
     */
    public static final String BJ_CITY_CODE = "1110";
}

