package com.dodopal.oss.delegate;

import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 
 * @author hxc
 *
 */
public interface PaymentDelegate {

    //支付流水条件查询
    public DodopalResponse<DodopalDataPage<PaymentDTO>> findPaymentListByPage(PaymentQueryDTO queryDTO);
    
    //支付流水单个查询
    public DodopalResponse<PaymentDTO> findPayment(String id);
    
    // 调用自动转账接口  2016-01-04 追加
    public DodopalResponse<Boolean> autoTransfer(String merCode);
}
