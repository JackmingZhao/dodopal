/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.running.business.model;

/**
 * Created by lenovo on 2015/10/31.
 */
public class PayWay {
    private String bankGatewayType;//银行网关
    private String payWayName;//支付方式名称

    public String getBankGatewayType() {
        return bankGatewayType;
    }

    public void setBankGatewayType(String bankGatewayType) {
        this.bankGatewayType = bankGatewayType;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }
}
