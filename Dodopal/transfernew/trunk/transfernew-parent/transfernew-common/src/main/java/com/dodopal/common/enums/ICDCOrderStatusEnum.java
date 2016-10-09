package com.dodopal.common.enums;

/**
 * 一卡通订单状态
 */
public enum ICDCOrderStatusEnum {

    ORDER_SUCCESS("0", "下单成功"),

    CHARGE_SUCCESS("1", "充值成功"),

    ORDER_FAILURE("2", "充值失败"),

    REFUNDMENT("3", "已退款"),
    
    ORDER_CREATE("4", "创建订单成功");

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

    ICDCOrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /** 根据状态码获取其对应的枚举状态 **/
    public static ICDCOrderStatusEnum parseByCode(String source) {
        for (ICDCOrderStatusEnum codes : values()) {
            if (codes.code.equals(source)) {
                return codes;
            }
        }
        return null;
    }
}
