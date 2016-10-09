package com.dodopal.payment.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.model.query.PaymentQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午8:54:23
 */
public interface PaymentService {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPaymentByPage 
     * @Description: 查询支付流水信息（分页）
     * @param query
     * @return    设定文件 
     * DodopalDataPage<Payment>    返回类型 
     * @throws 
     */
    public DodopalDataPage<Payment> findPaymentByPage(PaymentQuery query);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPaymentById 
     * @Description: 查询支付流水详情
     * @param id
     * @return    设定文件 
     * Payment    返回类型 
     * @throws 
     */
    public Payment findPaymentById(String id);

    /**
     * @description  此方法用于保存支付流水信息
     * @param payment
     */
    public void addPayment(Payment payment);
    /**
     * @param payment
     * @description 此方法用于支付回调成功后更改表字段状态
     */
    public void modifyPayment(Payment payment);

    /**
     * @description 根据tranCode查询出支付流水信息
     * @param tranCode
     * @return
     */
    public Payment queryPaymentInfo(String tranCode);

    /**
     * @description 根据所传递的payId
     * @param payCommonId
     * * @param payWayKind
     */
    public void operateCommonInfo(String payCommonId,String payWayKind,String customerType,String merOrUserCode);
}
