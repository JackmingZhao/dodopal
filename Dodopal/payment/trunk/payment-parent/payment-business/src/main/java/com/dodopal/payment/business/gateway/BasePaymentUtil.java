/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.gateway;

import com.dodopal.payment.business.model.PayConfig;
import org.apache.log4j.Logger;

/**
 * Created by lenovo on 2015/8/10.
 */
public class BasePaymentUtil {
    private static Logger log = Logger.getLogger(BasePaymentUtil.class);
    /**
     * @description 根据支付产品标识获取支付产品对象
     * @return BasePayment
     */
    public static BasePayment getPayment(PayConfig paymentConfig) {
        if(log.isInfoEnabled()){
            log.info("支付通道类实例化开始=============");
        }
        BasePayment basePayment = null;
        try {
            basePayment = (BasePayment) Class.forName(paymentConfig.getPayChannelMark()).newInstance();
        } catch (Exception e) {
            log.error("getPayment实例化出错",e);
            e.printStackTrace();
        }
        return basePayment;
    }
}
