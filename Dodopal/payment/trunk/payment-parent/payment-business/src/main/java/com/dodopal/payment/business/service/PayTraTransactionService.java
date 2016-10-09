package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.model.query.TranscationRequest;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午4:15:25
 */
public interface PayTraTransactionService {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayTraTransactionByPage 
     * @Description: 查询交易流水信息（分页）
     * @param query
     * @return    设定文件 
     * DodopalDataPage<PayTraTransaction>    返回类型 
     * @throws 
     */
    public DodopalDataPage<PayTraTransaction> findPayTraTransactionByPage(PayTraTransactionQuery query);
    
    /**
     * 查询所有交易记录
     * @return
     */
    public List<PayTraTransaction> findTraRecordAll(PayTraTransactionQuery query);
    
    /**
     * 查询所有交易记录 （oss系统 导出）
     * @return
     */
    public List<PayTraTransaction> findTrasactionExport(PayTraTransactionQuery query);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayTraTransactionById 
     * @Description: 根据id查询交易流水详情
     * @param id
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public PayTraTransaction findPayTraTransactionById(String id);
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayTraTransactionList 
     * @Description: 查询交易流水
     * @param query
     * @return    设定文件 
     * List<PayTraTransaction>    返回类型 
     * @throws 
     */
    public List<PayTraTransaction> findPayTraTransactionList(PayTraTransaction query);

    /**
     * @description 此方法用于保存交易流水信息
     */
    public void addPaymentTransaction(PayTraTransaction paymentTransaction);
    /**
     * @param paymentTransaction
     * @description 此方法用于支付回调成功后更改表字段状态
     */
    public void modifyPaymentTransaction(PayTraTransaction paymentTransaction);
    
    
    /**
     * 账户首页查询最新十条交易记录
     * @param ext
     * @param merOrUserCode
     * @param createUser 区分操作员和管理员
     * @return
     */
    public List<PayTraTransaction> findPayTraTransactionByCode(String ext, String merOrUserCode,String createUser);
    
    /**
     * 查询子商户交易记录
     * @param query
     * @return DodopalDataPage<PayTraTransaction>
     */
    public DodopalDataPage<PayTraTransaction> findTraRecordByPage(PayTraTransactionQuery query);
    
    /**
     * 查询商户额度提取分页（财务报表导出）
     * @param query
     * @return DodopalDataPage<PayTraTransaction>
     */
    public DodopalDataPage<PayTraTransaction> findMerCreditsByPage(PayTraTransactionQuery query);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月15日 上午11:51:40 
      * @version 1.0 
      * @parameter  查询商户额度提取报表导出查询
      * @since  
      * @return  
      */
    public List<PayTraTransaction> findMerCreditsExport(PayTraTransactionQuery query);

    /**
     * 门户系统 转账  生成交易流水
     * @param payTransferDTO
     * @return
     */
    public void transferCreateTran(PayTraTransaction payTraTransaction);
    
    /**
     * 门户转账 调用  账户系统的 生成资金变更记录
     * @param payTransferDTOList
     * @return
     */
    public DodopalResponse<Boolean> transferAccount(AccountTransferListDTO accountTransferDTOList);
   
    /**
     * 转账成功修改交易流水状态(内、外部交易状态)
     * @param tranCode 交易流水号
     * @return
     */
    public void UpdateStatesByTransfer(PayTraTransaction payTraTransaction);
    
    /**
     * 转账成功 修改交易状态（内部交易状态）
     * @param tranCode
     * @return
     */
    public void UpdateInStatesByTransfer(PayTraTransaction payTraTransaction);
    
    /**
     * 查询子商户交易记录
     * @param merParentCode
     * @return List<PayTraTransaction>
     */
    public List<PayTraTransaction> findTraRecordByMerParentCode(String merParentCode);
    
    /**
     * 查询交易记录（手机端和VC端）
     * @param requestDto
     * @return
     */
    public List<PayTraTransaction> queryPayTranscation(TranscationRequest request);

}
