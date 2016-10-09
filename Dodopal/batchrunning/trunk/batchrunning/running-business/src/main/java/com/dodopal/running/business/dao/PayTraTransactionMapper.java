package com.dodopal.running.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.running.business.model.PayTraTransaction;

/**
 * 处理异常交易流水（账户充值已付款、但账户加值失败）
 */
public interface PayTraTransactionMapper {
    
    /**  
     * 根据交易流水号查找交易信息
     * @param tranCode 交易流水号
     */
    public PayTraTransaction getPayTraTransactionByTranCode(String tranCode);
    
    /**  
     * 根据订单信息查找交易流水信息

     * @param orderNumber 充值订单编号
     * @param merOrUserCode 用户号
     * @param userType 用户类型
     * @param tranType 交易类型
     * @param businessType 业务类型
     */
    public PayTraTransaction getPayTraTransactionByOrder(@Param("orderNumber") String orderNumber, @Param("merOrUserCode") String merOrUserCode,  @Param("userType") String userType, @Param("tranType") String tranType, @Param("businessType") String businessType);
    
    /**  
     * 更新交易流水的内外部状态
     * @param payTransaction 交易流水信息
     */
    public int updateTranStatus(PayTraTransaction payTransaction);
    
    
    /**
     * 账户充值  异常交易流水补单程序  查询交易流水 
     * @param threshold 时间阀值
     * @return
     */
    public List<PayTraTransaction> findPayTraTransactionByList(@Param("threshold")int threshold);
    
}
