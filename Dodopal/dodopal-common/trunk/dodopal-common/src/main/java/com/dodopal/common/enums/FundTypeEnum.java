package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum FundTypeEnum {

    FUND("0", "资金账户"), AUTHORIZED("1", "授信账户");

    private static final Map<String, FundTypeEnum> map = new HashMap<String, FundTypeEnum>(values().length);

    static {
        for (FundTypeEnum fundTypeEnum : values()) {
            map.put(fundTypeEnum.getCode(), fundTypeEnum);
            
        }
    }

    private String code;
    private String name;

    private FundTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static FundTypeEnum getFundTypeByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getFundTypeNameByCode(String code) {
        FundTypeEnum type = getFundTypeByCode(code);
        if(type != null) {
            return type.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }


}
