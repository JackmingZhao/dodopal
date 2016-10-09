package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.CreatePayTransactionBean;
import com.dodopal.product.business.bean.TranscationListResultBean;
import com.dodopal.product.business.bean.TranscationRequestBean;
/**
 * 查询交易流水  （手机端和VC端）
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 */
public interface PayTransactionService {
    /**
     * 查询交易流水  （手机端和VC端）
     * @param requestDto
     * @return
     */
    DodopalResponse<List<TranscationListResultBean>> queryPayTranscation(TranscationRequestBean requestDto);

    
    /**
     * 创建交易流水（自助终端）
     * @param requestDto
     * @return
     */
    DodopalResponse<String> createTranscation(CreatePayTransactionBean requestDto);
}
