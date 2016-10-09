package com.dodopal.api.merdevice.crdConstants;



public class CardServiceConstants {
	 
	 public static int MSG_DESC_LEN = 58;
	 
	 public static int LEFT_PAD_SPACE = 1;
	 public static int LEFT_PAD_ZERO = 2;
	 public static int RIGHT_PAD_SPACE = 3;
	 public static int RIGHT_PAD_ZERO = 4;
	 public static int LEFT_CUT = 5;
	 public static int RIGHT_CUT = 6;
	 public static int AUTO = 7;
	 
	 public static String CHECK_CARD_MSG_UP = "1101";
	 public static String CHECK_CARD_MSG_DOWN = "1102";
	 
	 public static String PRE_PROCESS_CHARGE_MSG_UP = "2011";
	 public static String PRE_PROCESS_CHARGE_MSG_DOWN = "2012";
	 
	 public static String APPLY_RECHARGE_KEYT_MSG_UP = "2101";
	 public static String APPLY_RECHARGE_KEYT_MSG_DOWN = "2102";
	 
	 public static String UPLOAD_RECHARGE_RST_MSG_UP = "2201";
	 public static String UPLOAD_RECHARGE_RST_MSG_DOWN = "2202";
	 
	 public static String PRE_PROCESS_PAY_MSG_UP = "3011";
	 public static String PRE_PROCESS_PAY_MSG_DOWN = "3012";
	 
	 public static String APPLY_PAY_KEYT_MSG_UP = "3101";
	 public static String APPLY_PAY_KEYT_MSG_DOWN = "3102";
	 
	 public static String UPLOAD_PAY_RST_MSG_UP = "3201";
	 public static String UPLOAD_PAY_RST_MSG_DOWN = "3202";
	 
	 public static String VALID_RESPONSE_CODE = "000000";
	 
	 public static String MSG_SECRET_KEY = "1A2B4X3Y";
	 
	 public static String BUSCARD_CHECK_SERVICE = "busCardCheck";
	 public static String BUSCARD_PAY_SERVICE = "busCardPay";
	 public static String BUSCARD_RECHARGE_SERVICE = "busCardRecharge";
	 
	 public static String TRANSACTION_CHANNEL_CHECK_OUT_COUNTER = "1";
	 public static String TRANSACTION_CHANNEL_WEB = "2";
	 public static String TRANSACTION_CHANNEL_VC = "3";
	 public static String TRANSACTION_CHANNEL_NONE = "4";

}
