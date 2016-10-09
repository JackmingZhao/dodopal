package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 通卡公司账户积分消费订单记录状态枚举
 * @author dodopal
 */
public enum YktAPCOStatesEnum {
    
    CREATION_SUCCESS("00", "订单创建成功"), // 订单创建成功
    
    CONSUME_APPLY_SUCCESS("10", "消费申请成功"),// 账户、积分消费申请成功
    
    CONSUME_APPLY_FAILED("11", "消费申请失败"), // 账户、积分消费申请失败
    
    CONSUME_SUCCESS("20", "消费成功"),// 账户、积分消费成功
    
    CONSUME_FAILED("21", "消费失败"), // 账户、积分消费失败
    
    CONSUME_CANCEL_SUCCESS("30", "消费撤销成功");// 账户、积分消费撤销成功
  
 
    private static final Map<String, YktAPCOStatesEnum> map = new HashMap<String, YktAPCOStatesEnum>(values().length);

    static {
        for (YktAPCOStatesEnum yktAPCOStatesEnum : values()) {
            map.put(yktAPCOStatesEnum.getCode(), yktAPCOStatesEnum);
        }
    }

    private String code;
    private String name;

    private YktAPCOStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static YktAPCOStatesEnum getStatesEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
    
    public static String getStatesNameByCode(String code) {
        YktAPCOStatesEnum state = getStatesEnumByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

}
