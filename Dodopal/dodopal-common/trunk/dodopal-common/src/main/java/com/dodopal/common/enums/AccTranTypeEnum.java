package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 账户交易类型
 */
public enum AccTranTypeEnum {
    ACC_RECHARGE("0", "充值"), 
    ACC_FREEZE("1", "冻结"), 
    ACC_UNFREEZE("2", "解冻"), 
    ACC_CONSUME("3", "扣款"), 
    ACC_TRANSFER_IN("4", "转入"), 
    ACC_TRANSFER_OUT("5", "转出"), 
    ACC_REFUND("6", "退款"), 
    ACC_PT_AD("7", "正调账"), 
    ACC_NEG_AD("8", "反调账");

    private static final Map<String, AccTranTypeEnum> map = new HashMap<String, AccTranTypeEnum>(values().length);

    static {
        for (AccTranTypeEnum statusEnum : values()) {
            map.put(statusEnum.getCode(), statusEnum);
        }
    }

    private String code;
    private String name;

    private AccTranTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static AccTranTypeEnum getActivateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getTranTypeNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        AccTranTypeEnum type = map.get(code);
        if(type != null) {
            return type.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }
}
