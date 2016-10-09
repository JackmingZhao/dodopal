package com.dodopal.payment.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.payment.business.dao.PayWayCommonMapper;
import com.dodopal.payment.business.model.PayWayCommon;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.dao.PaymentMapper;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.model.query.PaymentQuery;
import com.dodopal.payment.business.service.PaymentService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午8:54:46
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private PayWayCommonMapper payWayCommonMapper;

    @Transactional(readOnly = true)
    public DodopalDataPage<Payment> findPaymentByPage(PaymentQuery query) {
        List<Payment> result = paymentMapper.findPayMentByPage(query);
        DodopalDataPage<Payment> pages = new DodopalDataPage<Payment>(query.getPage(), result);
        return pages;
    }

    @Override
    public Payment findPaymentById(String id) {
        if(StringUtils.isNotBlank(id)){
            Payment payment = paymentMapper.findPaymentById(id);
            return payment;
        }
        return null;
    }

    /**
     * @description  此方法用于保存支付流水信息
     * @param payment
     */
    public void addPayment(Payment payment) {
        paymentMapper.addPayment(payment);
    }

    /**
     * @description 此方法用于支付回调成功后更改表字段状态
     */
    @Override
    public void modifyPayment(Payment payment) {
        paymentMapper.modifyPayment(payment);
    }

    /**
     * @description 根据tranCode查询出支付流水信息
     * @param tranCode
     * @return
     */
    @Override
    public Payment queryPaymentInfo(String tranCode) {
        return paymentMapper.queryPaymentInfo(tranCode);
    }
    @Transactional
    @Override
    public void operateCommonInfo(String payCommonId,String payWayKind,String customerType,String merOrUserCode) {
        List<PayWayCommon> payWayCommonList = payWayCommonMapper.queryPayCommonList(merOrUserCode);
        //如果查询处的数据信息条数多于或等于5个
        if(payWayCommonList.size()>=5){
            //已经存在此次支付的方式id
            if(containsPayId(payWayCommonList,payCommonId)) {
                //更改数据
                payWayCommonMapper.updateCommonInfo(payCommonId);
            }else{
               // 寻找试用次数最少的那个id
                String id = getCommonId(payWayCommonList);
                List ids = new ArrayList();
                ids.add(id);
                //删除一个，增加一个
                payWayCommonMapper.deletePayWayCommon(ids);
                PayWayCommon payWayCommon = new PayWayCommon();
                payWayCommon.setPayWayId(payCommonId);
                payWayCommon.setPayWayKind(payWayKind);
                payWayCommon.setUserType(customerType);
                payWayCommon.setUserType(merOrUserCode);
                payWayCommonMapper.insertPayWayCommon(payWayCommon);
            }
        }else{
            if(containsPayId(payWayCommonList,payCommonId)){
                //更改
                payWayCommonMapper.updateCommonInfo(payCommonId);
            }else{
                //新增
                PayWayCommon payWayCommon = new PayWayCommon();
                payWayCommon.setPayWayId(payCommonId);
                payWayCommon.setPayWayKind(payWayKind);
                payWayCommon.setUserType(customerType);
                payWayCommon.setUserType(merOrUserCode);
                payWayCommonMapper.insertPayWayCommon(payWayCommon);
            }
        }
    }
    public boolean containsPayId(List<PayWayCommon> payCommonList,String payCommonId){
        boolean flag = false;
        for(PayWayCommon payWayCommon:payCommonList){
            if(payCommonId.equals(payWayCommon.getPayWayId())){
                flag=true;
                break;
            }
        }
        return true;
    }
    public String getCommonId(List<PayWayCommon> payCommonList){
        PayWayCommon pwc = payCommonList.get(payCommonList.size()-1);
        return pwc.getId();
    }
}
