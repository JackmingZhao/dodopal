package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.CreateTranDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.TranscationListResultDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.dto.query.TranscationRequestDTO;
import com.dodopal.common.model.DodopalResponse;

/**
 * 3.20 查询交易记录 （手机端和VC端接入）  
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 */
public interface PayTransactionDelegate {
    
    DodopalResponse<List<TranscationListResultDTO>> queryPayTranscation(TranscationRequestDTO requestDto);

    /**
     * 根据tranCode查询交易流水信息
     * @param tranCode
     * @return DodopalResponse<PayTraTransactionDTO>
     */
    DodopalResponse<PayTraTransactionDTO> findTranInfoByTranCode(String tranCode);

    /**
     * 创建交易流水 （自助终端）
     * @param transcationRequestDTO
     * @return
     */
    DodopalResponse<String> createTranscation(CreateTranDTO createTranDTO);
    
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月20日 下午7:57:14 
      * @version 1.0 
      * @parameter  
      * @描述 取交易流水信息
      * @return  
      */
    DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionList(PayTraTransactionQueryDTO queryDTO);

    
}
