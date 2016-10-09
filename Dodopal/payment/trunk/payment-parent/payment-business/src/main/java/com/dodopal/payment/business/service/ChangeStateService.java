/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.service;

import com.dodopal.payment.business.gateway.BasePayment;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PaySubmit;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.Payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2015/8/14.
 */
public interface ChangeStateService {
    //更改表记录状态
    public void changeState(Payment payment,PayTraTransaction payTraTransaction);

    //新增流水信息
    public void insertDataInfo(Payment payment,PayTraTransaction payTraTransaction);

    //支付提交前处理
    public String getSubmitHtml(PaySubmit paySubmit,PayConfig payConfig,BasePayment basePayment,HttpServletRequest request, HttpServletResponse response);
}