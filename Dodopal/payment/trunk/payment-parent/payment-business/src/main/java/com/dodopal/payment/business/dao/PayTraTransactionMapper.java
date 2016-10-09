package com.dodopal.payment.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.business.model.Merchant;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.model.query.TranscationRequest;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月22日 下午8:32:29
 */
public interface PayTraTransactionMapper {
    public List<PayTraTransaction> findPayTraTransactionByPage(PayTraTransactionQuery query);
    
    /**
     * 查询交易记录(分页)
     * @param query
     * @return List<PayTraTransaction>
     */
    public List<PayTraTransaction> findTraRecordByPage(PayTraTransactionQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月13日 下午5:34:07 
      * @version 1.0 
      * @parameter  提取商户额度报表查询分页
      * @since  
      * @return  
      */
    public List<PayTraTransaction> findMerCreditsByPage(PayTraTransactionQuery query);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年1月15日 上午11:50:01 
      * @version 1.0 
      * @parameter  提取商户额度报表导出查询
      * @since  
      * @return  
      */
    public List<PayTraTransaction> findMerCreditsExport(PayTraTransactionQuery query);
    
    public List<PayTraTransaction> findPayTraTransactionList(PayTraTransaction transactionQuery);

    public PayTraTransaction findPayTraTransactionById(@Param("id") String id);

    //保存交易流水信息
    public void addPaymentTransaction(PayTraTransaction paymentTransaction);

    //修改交易流水表记录状态(支付完成后)
    public void modifyPaymentTransaction(PayTraTransaction paymentTransaction);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updateTranStatus 
     * @Description: 更新交易流水的内外部状态
     * @param paymentTransaction    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void updateTranStatus(PayTraTransaction paymentTransaction);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayTraTransactionByTranCode 
     * @Description: 根据流水号查找交易信息
     * @param tranCode
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public PayTraTransaction findPayTraTransactionByTranCode(String tranCode);
    
    /** 
     * @Description: 根据旧交易流水号和交易类型查找交易流水数据
     * @param oldTranCode
     * @param tranType
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public PayTraTransaction findTranInfoByOldTranCode(@Param("oldTranCode")String oldTranCode, @Param("tranType")String tranType);
    
    /**
     * 账户首页查询最新十条交易记录
     * @param ext
     * @param merOrUserCode
     * @return
     */
    public List<PayTraTransaction> findPayTraTransactionByCode(@Param("ext")String ext, @Param("merOrUserCode")String merOrUserCode,@Param("createUser")String createUser);
    
    public List<PayTraTransaction> findMerchantTraRecordByPage(PayTraTransactionQuery query);
    
    /**
     * 根据上级商户号和相应的商户类型查找子商户
     * @param merParentCode
     * @param merTypes
     * @return
     */
    public List<Merchant>  findChildMerchantByParentCodeType(@Param("merParentCode")String merParentCode);

   
    
    /**
     * 门户系统  转账  生成交易流水
     * @param payTransferDTO 
     * @return
     */
    public DodopalResponse<Boolean> transferCreateTran(PayTraTransaction payTraTransaction);
    
    /**
     * 转账成功 根据tranCode交易流水号  修改交易状态（内、外部交易状态）
     * @param tranCode
     * @return
     */
    public void UpdateStatesByTransfer(PayTraTransaction paymentTransaction);
    
    /**
     * 根据tranCode交易流水号  修改交易状态（外部交易状态）
     * @param tranCode
     * @return
     */
    public void UpdateOutStatesByTransfer(PayTraTransaction paymentTransaction);
    
    
    /**
     * 转账成功 根据tranCode交易流水号  修改交易状态（内部交易状态）
     * @param tranCode
     * @return
     */
    public void UpdateInStatesByTransfer(PayTraTransaction paymentTransaction);
    
    /**
     * 查询所有交易记录
     * @return List<PayTraTransaction>
     */
    public List<PayTraTransaction> findTraRecordAll(PayTraTransactionQuery query);
    
    /**
     * 查询所有交易记录 oss系统导出
     * @return List<PayTraTransaction>
     */
    public List<PayTraTransaction> findTrasactionExport(PayTraTransactionQuery query);
    
    
    /**
     * 查询子商户交易记录
     * @param merParentCode
     * @return List<PayTraTransaction>
     */
    public List<PayTraTransaction> findTraRecordByMerParentCode(@Param("merParentCode")String merParentCode);
    
    /**
     * 查询交易记录（手机端和VC端）
     * @param requestDto
     * @return
     */
    public List<PayTraTransaction> queryPayTranscation(TranscationRequest requestDto);

    /**
     * @description 根据页面所传genId查询是否已经在数据库表中存在
     * @param genId
     * @return
     */
    public String getGenId(@Param("genId")String genId);
    
}
