package com.dodopal.common.enums;

/**
 *在职状态
 */
public enum EmpTypeEnum {

    EMP("0", "在职"),

    QUIT("1", "离职");

   
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

    EmpTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /** 根据状态码获取其对应的枚举状态 **/
    public static EmpTypeEnum parseByCode(String source) {
        for (EmpTypeEnum codes : values()) {
            if (codes.code.equals(source)) {
                return codes;
            }
        }
        return null;
    }
}
