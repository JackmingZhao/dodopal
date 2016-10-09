package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum AccountAdjustmentStateEnum {

    UN_APPROVE("0", "未审批"), SUCCESS("1", "调账成功"), FAILURE("2", "调账失败"), DIS_APPROVE("3", "审批不通过");

    private static final Map<String, AccountAdjustmentStateEnum> map = new HashMap<String, AccountAdjustmentStateEnum>(values().length);

    static {
        for (AccountAdjustmentStateEnum state : values()) {
            map.put(state.getCode(), state);

        }
    }

    private String code;
    private String name;

    private AccountAdjustmentStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static AccountAdjustmentStateEnum getAdjustmentStateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getAdjustmentStateNameByCode(String code) {
        AccountAdjustmentStateEnum state = getAdjustmentStateByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
