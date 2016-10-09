package com.dodopal.running.business.service;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.running.business.model.PayTraTransaction;

public interface TraTransactionService {

    /**
     * 账户充值  异常交易流水补单程序  查询交易流水 
     * @param threshold 时间阀值
     * @return
     */
    public List<PayTraTransaction> findPayTraTransactionByList(@Param("threshold")int threshold);

}

