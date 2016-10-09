package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * 额度提取管理 提取状态
 * Name: JOE
 * Time:2016-01-05
 * @author dodopal
 *
 */
public enum MerLimitExtractConfirmEnum {
    TO_BE_CONFIRM("0", "待确认"),
    CONFIRM("1", "已确认"), 
    REJECT("2", "已取消"), 
    CANCEL("3", "已拒绝");

    private static final Map<String, MerLimitExtractConfirmEnum> map = new HashMap<String, MerLimitExtractConfirmEnum>(values().length);

    static {
        for (MerLimitExtractConfirmEnum merLimitExtractConfirmEnum : values()) {
            map.put(merLimitExtractConfirmEnum.getCode(), merLimitExtractConfirmEnum);
        }
    }

    private String code;
    private String name;

    private MerLimitExtractConfirmEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static MerLimitExtractConfirmEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
