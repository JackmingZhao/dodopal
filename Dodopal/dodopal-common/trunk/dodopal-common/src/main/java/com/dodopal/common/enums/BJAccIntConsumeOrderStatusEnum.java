package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public enum BJAccIntConsumeOrderStatusEnum {
    CARD_ORDER_CREATE_SUCCESS("1000000001", "创建订单成功"),
    CARD_CONSUME_APPLY_SUCCESS("1000000007","消费申请成功"),
    CARD_CONSUME_APPLY_FAIL("1000000006","消费申请失败"),
    CARD_ORDER_CONSUME_SUCCESS("1000000008", "消费成功"),
    CARD_ORDER_CONSUME_ERROR("1000000009", "消费失败"),
    
    CARD_ORDER_APPLY_REVERSE("1000000011", "消费冲正申请"),
    CARD_ORDER_UPLOAD_RESULT("1000000012", "消费结果申请"),
    
    CARD_ORDER_APPLY_REVERSE_SUCCESS("1000000019", "一卡通消费确认冲正成功"),
    CARD_ORDER_APPLY_REVERSE_UNKNOW("1000000017", "一卡通消费冲正未确认"),
    CARD_ORDER_UPLOAD_RESULT_SUCCESS("1000000018", "一卡通消费确认成功"),
    CARD_ORDER_UPLOAD_RESULT_UNKNOW("1000000016", "一卡通消费未确认"),
    
    CARD_ORDER_CONSUME_REVOKE_APPLY("1000000013", "消费撤销申请"),
    
    CARD_ORDER_CONSUME_REVOKE_APPLY_SUCCESS("1000000015", "一卡通撤销确认成功"),
    CARD_ORDER_CONSUME_REVOKE_APPLY_UNKNOW("1000000014", "一卡通撤销未确认");

    private static final Map<String, BJAccIntConsumeOrderStatusEnum> map = new HashMap<String, BJAccIntConsumeOrderStatusEnum>(values().length);

    static {
        for (BJAccIntConsumeOrderStatusEnum temp : values()) {
            map.put(temp.getCode(), temp);
        }
    }

    private String code;
    private String name;

    private BJAccIntConsumeOrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BJAccIntConsumeOrderStatusEnum getEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static boolean contains(String code) {
        return map.containsKey(code);
    }

    public static String getNameByCode(String code) {
        BJAccIntConsumeOrderStatusEnum temp = getEnumByCode(code);
        if (temp != null) {
            return temp.getName();
        }
        return "";
    }
}
