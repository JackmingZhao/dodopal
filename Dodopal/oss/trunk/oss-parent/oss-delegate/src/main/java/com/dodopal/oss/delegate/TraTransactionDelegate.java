package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface TraTransactionDelegate {

    /**
     * 查询交易流水
     * @param traDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findPayTraTransactionByPage(PayTraTransactionQueryDTO traDTO);

    /**
     * 查询交易流水详情
     * @param traCode
     * @return
     */
    public DodopalResponse<PayTraTransactionDTO> findTraTransaction(String id);
    
    /**
     * 查询交易记录（导出）
     * @return
     */
    public DodopalResponse<List<PayTraTransactionDTO>> findTrasactionExport(PayTraTransactionQueryDTO queryDTO);
    
    /**
     * 查询商户额度提取分页（财务报表导出）
     * @param traDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findMerCreditsByPage(PayTraTransactionQueryDTO traDTO);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月15日 下午1:42:21 
      * @version 1.0 
      * @parameter  
      * @since  
      * @return  
      */
    public DodopalResponse<List<PayTraTransactionDTO>> findMerCreditsExport(PayTraTransactionQueryDTO queryDTO);

    
}
