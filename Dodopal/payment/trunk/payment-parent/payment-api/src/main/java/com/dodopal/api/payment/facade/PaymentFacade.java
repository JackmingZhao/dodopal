package com.dodopal.api.payment.facade;


import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface PaymentFacade {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayMentByPage 
     * @Description: 查询支付流水（分页）
     * @param queryDTO
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PaymentDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<PaymentDTO>> findPayMentByPage(PaymentQueryDTO queryDTO);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPaymentInfoById 
     * @Description: 根据id查询支付流水
     * @param id
     * @return    设定文件 
     * DodopalResponse<PaymentDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<PaymentDTO> findPaymentInfoById(String id);
}