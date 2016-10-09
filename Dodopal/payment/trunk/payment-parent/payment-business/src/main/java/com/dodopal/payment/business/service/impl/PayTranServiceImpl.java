package com.dodopal.payment.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.payment.business.dao.PayTraTransactionMapper;
import com.dodopal.payment.business.dao.PayWayMapper;
import com.dodopal.payment.business.dao.PaymentMapper;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.delegate.MerchantDelegate;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月15日 上午10:44:25
 */
@Service
public class PayTranServiceImpl implements PayTranService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PayTraTransactionMapper tramapper;
    
    @Autowired
    private PayWayMapper payWayMapper;
    
    @Autowired
    private MerchantDelegate merchantDelegate;
    
    @Transactional
    public String checkMerOrUserCode(PayTranDTO payTranDTO) {
        return  merchantDelegate.validateMerchantForPayment(MerUserTypeEnum.getMerUserUserTypeByCode(payTranDTO.getCusTomerType()), payTranDTO.getCusTomerCode()).getCode();
    }

    @Override
    //交易流水号规则 T + 业务代码 + 城市编码+ 20150428222211（时间戳） +六位数据库cycle sequence（循环使用）
    public String createPayTranCode(String businessType,String cityCode) {
        cityCode ="";
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        buffer.append("T").append(businessType).append(businessType).append(dateFormat.format(new Date())).append(payWayMapper.getPayTranCodeSeq());
        return buffer.toString();
    }

    @Transactional
    public void savePaymentAndPayTran(Payment payment, PayTraTransaction paymentTransaction) {
        paymentMapper.addPayment(payment);
        tramapper.addPaymentTransaction(paymentTransaction);
    }

    @Transactional
    public void updatePayStatusAndPayTranStatus(Payment payment, PayTraTransaction paymentTransaction) {
        if(null!=payment)
            paymentMapper.updatePayStatus(payment);
        if(null!=paymentTransaction)
            tramapper.updateTranStatus(paymentTransaction);
    }

    @Override
    public PayTraTransaction findTranInfoByTranCode(String tranCode) {
        return tramapper.findPayTraTransactionByTranCode(tranCode);
    }

    @Override
    public PayTraTransaction findTranInfoByOldTranCode(String oldTranCode, String tranType) {
        return tramapper.findTranInfoByOldTranCode(oldTranCode, tranType);
    }
    
    @Override
    public List<Payment> findPaymentInfoByTranCode(String tranCode) {
        return paymentMapper.findPaymentByTranCode(tranCode);
    }

    @Override
    @Transactional
    public void updateStatesByTransfer(PayTraTransaction paymentTransaction) {
        tramapper.UpdateStatesByTransfer(paymentTransaction);
    }

    @Override
    @Transactional
    public void updateOutStatesByTransfer(PayTraTransaction paymentTransaction) {
        tramapper.UpdateOutStatesByTransfer(paymentTransaction);
    }
    
}
