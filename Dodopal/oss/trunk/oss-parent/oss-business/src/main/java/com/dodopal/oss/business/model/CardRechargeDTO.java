package com.dodopal.oss.business.model;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;

/**
 * 5.4 一卡通充值 对应表：CLEARING_BASIC_DATA(清分基础数据表)、PRD_ORDER(产品库公交卡充值订单信息表)
 * @author Dave
 */

public class CardRechargeDTO extends  ClearingBasicDataDTO{

    private static final long serialVersionUID = -1194570368562830879L;
   
    /* 订单状态:
         0：待付款；
         1：已付款；
         2：充值失败；
         3：充值中；
         4：充值成功；
         5：充值可疑 
     */
    private String proOrderStates;
    /* 订单内部状态:
        00：订单创建成功；
        10：账户充值成功；
        11：账户充值失败；
        12：网银支付成功；
        20：资金冻结失败；
        21：申请充值密钥失败；
        22：卡片充值失败；
        23：资金解冻成功；
        24：资金解冻失败；
        25：网银支付失败；
        30：资金冻结成功；
        31：申请充值密钥成功；
        40：卡片充值成功；
        41：资金扣款成功；
        42：资金扣款失败；
        50：上传充值未知
     */
    private String proInnerStates;
    
   

    public String getProOrderStates() {
    	 if (StringUtils.isBlank(this.proOrderStates)) {
             return "";
         } else if (RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(this.proOrderStates) == null) {
             return "";
         } else {
             return RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(this.proOrderStates).getName();
         }
    }

    public String getProInnerStates() {
    	 if (StringUtils.isBlank(this.proInnerStates)) {
             return "";
         } else if (RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(this.proInnerStates) == null) {
             return "";
         } else {
             return RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(this.proInnerStates).getName();
         }
    }
}