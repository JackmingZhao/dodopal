package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PaymentBean;
import com.dodopal.oss.business.model.dto.PayMentQuery;

/**
 * 
 * @author hxc
 *
 */
public interface PaymentService {

    //支付流水条件查询(分页)
    public DodopalResponse<DodopalDataPage<PaymentBean>> findPaymentListByPage(PayMentQuery query);
    
    //支付流水单个查询
    public DodopalResponse<PaymentBean> findPayment(String id);
}
