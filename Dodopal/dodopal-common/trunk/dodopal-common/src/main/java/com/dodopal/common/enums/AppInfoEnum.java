package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：子系统信息
 * @author lifeng
 */

public enum AppInfoEnum {
    OSS("OSS", "OSS后台管理系统"),
    USERS("USERS", "用户系统"),
    PORTAL("PORTAL", "门户系统");
    private static final Map<String, AppInfoEnum> map = new HashMap<String, AppInfoEnum>(values().length);

    static {
        for (AppInfoEnum appInfoEnum : values()) {
            map.put(appInfoEnum.getCode(), appInfoEnum);
        }
    }

    private String code;
    private String name;

    private AppInfoEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static AppInfoEnum getAppInfoByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }
}
