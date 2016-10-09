package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ConsumeOrderInternalStatesEnum
 * @Description: 产品库一卡通消费 (内部)订单状态枚举
 * @author dodopal
 * @date 2015年10月19日
 */
public enum ConsumeOrderInternalStatesEnum {
    
    ORDER_CREATION_SUCCESS("00", "订单创建成功"), 
    
    CHECK_CARD_SUCCESS("01", "验卡成功"), 
    
    APPLY_CONSUME_SECRET_KEY_SUCCESS("02", "申请消费密钥成功"), 
    
    CHECK_CARD_FAILED("10", "验卡失败"), 
   
    APPLY_CONSUME_SECRET_KEY_FAILED("11", "申请消费密钥失败"), 
    
    DEDUCT_FAILED("12", "扣款失败"),
    DEDUCT_SUCCESS("20", "扣款成功"), 
    DEDUCT_UNKNOWE("30", "扣款未知");
  
 
    private static final Map<String, ConsumeOrderInternalStatesEnum> map = new HashMap<String, ConsumeOrderInternalStatesEnum>(values().length);

    static {
        for (ConsumeOrderInternalStatesEnum consumeOrderInternalStatesEnum : values()) {
            map.put(consumeOrderInternalStatesEnum.getCode(), consumeOrderInternalStatesEnum);
        }
    }

    private String code;
    private String name;

    private ConsumeOrderInternalStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ConsumeOrderInternalStatesEnum getConsumeOrderInternalStatesEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
    
    public static String getConsumeOrderInternalStatesNameByCode(String code) {
        ConsumeOrderInternalStatesEnum state = getConsumeOrderInternalStatesEnumByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

}
