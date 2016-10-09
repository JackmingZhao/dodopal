package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 城市额度购买：审核流程状态
 * @author dodopal
 *
 */
public enum AuditStateEnum {

    UN_AUDIT("0", "未审核"), AUDIT_SUCCESS("1", "审核通过"), AUDIT_FAILURE("2", "审核拒绝");

    private static final Map<String, AuditStateEnum> map = new HashMap<String, AuditStateEnum>(values().length);

    static {
        for (AuditStateEnum state : values()) {
            map.put(state.getCode(), state);
        }
    }

    private String code;
    private String name;

    private AuditStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static AuditStateEnum getAuditStateEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
    
    public static String getAuditStateNameByCode(String code) {
        AuditStateEnum state = getAuditStateEnumByCode(code);
        if(state != null) {
            return state.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

}
