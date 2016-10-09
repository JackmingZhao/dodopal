package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum SpecialModelParamNOEnum {
    p01("01", "黑名单参数"),
    p02("02", "消费可用卡类型参数"),
    p03("03", "终端运营参数"),
    p04("04", "区域黑名单参数"),
    p05("05", "增量黑名单参数"),
    p06("06", "终端菜单参数"),
    p07("07", "灰名单参数"),
    p33("33", "消费折扣参数"),
    p34("34", "分时段消费折扣参数");
    
    private static final Map<String, SpecialModelParamNOEnum> map = new HashMap<String, SpecialModelParamNOEnum>(values().length);

    static {
        for (SpecialModelParamNOEnum SpecialModelParamNOEnum : values()) {
            map.put(SpecialModelParamNOEnum.getCode(), SpecialModelParamNOEnum);
        }
    }

    private String code;
    private String name;

    private SpecialModelParamNOEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SpecialModelParamNOEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

}
