package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明 ：来源
 * @author lifeng
 */

public enum SourceEnum {
    PORTAL("0", "门户"),
    OSS("1", "OSS"),
    PHONE("2", "手机端"), 
    VC("3", "客户端"),
    MERMACHINE("4", "商用机"),
    EXT_DLL("5", "DLL外接"),
    AN_PHONE("6", "安卓手机端"),
    IOS_PHONE("7", "IOS手机端"),
    EXT_LOAD("8","外接圈存"),
    TRANSFER("9", "迁移用户"),
    BUFFET("10", "自助终端");
    private static final Map<String, SourceEnum> map = new HashMap<String, SourceEnum>(values().length);

    static {
        for (SourceEnum sourceEnum : values()) {
            map.put(sourceEnum.getCode(), sourceEnum);
        }
    }

    private String code;
    private String name;

    private SourceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SourceEnum getSourceByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static String getSourceNameByCode(String code) {
        SourceEnum source = getSourceByCode(code);
        if(source != null) {
            return source.getName();
        }
        return "";
    }

    public static boolean checkCodeExist(String code) {
        return map.containsKey(code);
    }

    /**
     * 判断是否为手机端
     * @param code
     * @return
     */
    public static boolean isPhone(String code) {
    	if(StringUtils.isBlank(code)) {
    		return false;
    	}
    	return PHONE.getCode().equals(code) || AN_PHONE.getCode().equals(code) || IOS_PHONE.getCode().equals(code);
    }
}
