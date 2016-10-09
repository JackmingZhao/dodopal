package com.dodopal.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 卡服务消费交易状态
 * @author hxc
 *
 */
public enum CardConsumeStatesEnum {
    CARD_ORDER_CREATE_SUCCESS("1000000001", "创建成功"),
    CARD_ORDER_APPLY_READCARD_KEY_SUCCESS("1000000003", "申请读卡密钥成功"),
    CARD_ORDER_APPLY_READCARD_KEY_ERROR("1000000004", "申请读卡密钥失败"),
    CARD_ORDER_APPLY_RECHARGE_KEY("1000000005", "申请读卡密钥"),
    CARD_ORDER_APPLY_RECHARGE_KEY_SUCCESS("1000000007", "申请消费密钥成功"),
    CARD_ORDER_APPLY_RECHARGE_KEY_ERROR("1000000006", "申请消费密钥失败"),
    CARD_ORDER_RECHARGE_SUCCESS("1000000008", "卡片消费成功"),
    CARD_ORDER_RECHARGE_ERROR("1000000009", "卡片消费失败"),
    CARD_ORDER_RECHARGE_UNKNOW("1000000010", "卡片消费未知"),
    CARD_ORDER_UPLOAD_RESULT("1000000012", "消费结果上传申请"),
    CARD_ORDER_UPLOAD_RESULT_SUCCESS("1000000018", "一卡通确认消费成功"),
    CARD_ORDER_UPLOAD_RESULT_UNKNOW("1000000016", "一卡通未确认"),
    CARD_ORDER_APPLY_REVERSE("1000000011", "一卡通冲正申请"),
    CARD_ORDER_APPLY_REVERSE_SUCCESS("1000000019", "一卡通确认冲正成功"),
    CARD_ORDER_APPLY_REVERSE_ERROR("1000000017", "一卡通确认冲正失败");

    private String code;

    private String name;

    private static final Map<String, CardConsumeStatesEnum> map = new HashMap<String, CardConsumeStatesEnum>(values().length);

    static {
        for (CardConsumeStatesEnum cardOrderStates : values()) {
            map.put(cardOrderStates.getCode(), cardOrderStates);
        }
    }

    
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

    CardConsumeStatesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public static CardConsumeStatesEnum getCardOrderStatesByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return map.get(code);
    }

    public static String getNameByCode(String code) {
        if(!map.containsKey(code)) {
            return null;
        }
        return map.get(code).getName();
    }
}
