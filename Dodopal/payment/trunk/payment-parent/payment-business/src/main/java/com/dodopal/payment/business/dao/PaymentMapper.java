package com.dodopal.payment.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.model.Test;
import com.dodopal.payment.business.model.query.PaymentQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月22日 下午8:34:35
 */
public interface PaymentMapper {
    
    public List<Payment> findPayMentByPage(PaymentQuery query);
    public Payment findPaymentById(@Param("id")String id);
    //保存支付流水信息
    public void addPayment(Payment payment);
    //修改交易流水表记录(支付完成后)
    public void modifyPayment(Payment payment);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updatePayStatus 
     * @Description: 修改支付状态
     * @param payment    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void updatePayStatus(Payment payment);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPaymentByTranCode 
     * @Description: 根据交易流水号查找支付信息
     * @param tranCode
     * @return    设定文件 
     * Payment    返回类型 
     * @throws 
     */
    public List<Payment> findPaymentByTranCode(String tranCode);

    public Payment queryPaymentInfo(@Param("tranCode")String tranCode);
}
