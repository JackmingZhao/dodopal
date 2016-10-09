package com.dodopal.common.enums;

/**
 * POS操作类型
 */
public enum PosStatusEnum {

    ACTIVATE("0", "启用"),

    STOP("1", "停用"),

    INVALID("2", "作废"),

    CONSUMER_DISABLE("3", "消费封锁"),

    CHARGE_DISABLE("4", "充值封锁");

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    PosStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /** 根据状态码获取其对应的枚举状态 **/
    public static PosStatusEnum parseByCode(String source) {
        for (PosStatusEnum codes : values()) {
            if (codes.code.equals(source)) {
                return codes;
            }
        }
        return null;
    }
}
