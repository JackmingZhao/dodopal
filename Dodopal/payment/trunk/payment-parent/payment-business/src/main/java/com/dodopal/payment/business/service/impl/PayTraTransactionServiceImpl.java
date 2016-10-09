package com.dodopal.payment.business.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.business.dao.PayTraTransactionMapper;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;
import com.dodopal.payment.business.model.query.TranscationRequest;
import com.dodopal.payment.business.service.PayTraTransactionService;
import com.dodopal.payment.delegate.PayTraTransactionDelegate;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 下午4:17:13
 */
@Service
public class PayTraTransactionServiceImpl implements PayTraTransactionService {
    private final static Logger log = LoggerFactory.getLogger(PayTraTransactionServiceImpl.class);
    @Autowired
    private PayTraTransactionMapper tramapper;
    @Autowired
    PayTraTransactionDelegate payTraTransactionDelegate;

    @Transactional(readOnly = true)
    public DodopalDataPage<PayTraTransaction> findPayTraTransactionByPage(PayTraTransactionQuery query) {
        List<PayTraTransaction> result = tramapper.findPayTraTransactionByPage(query);
        DodopalDataPage<PayTraTransaction> pages = new DodopalDataPage<PayTraTransaction>(query.getPage(), result);
        return pages;
    }

    @Transactional(readOnly = true)
    public PayTraTransaction findPayTraTransactionById(String id) {
        if (StringUtils.isNotBlank(id)) {
            PayTraTransaction payTraTransaction = tramapper.findPayTraTransactionById(id);
            return payTraTransaction;
        }
        return null;
    }

    /**
     * @param paymentTransaction
     * @description 此方法用于保存交易流水信息
     */
    @Transactional
    public void addPaymentTransaction(PayTraTransaction paymentTransaction) {
        tramapper.addPaymentTransaction(paymentTransaction);
    }

    /**
     * @param paymentTransaction
     * @description 此方法用于支付回调成功后更改表字段状态
     * @comments 根据传入的id和state动态更改数据信息的状态位
     */
    @Override
    public void modifyPaymentTransaction(PayTraTransaction paymentTransaction) {
        tramapper.modifyPaymentTransaction(paymentTransaction);
    }

    @Override
    public List<PayTraTransaction> findPayTraTransactionList(PayTraTransaction query) {
        return tramapper.findPayTraTransactionList(query);
    }

    @Transactional(readOnly = true)
    public List<PayTraTransaction> findPayTraTransactionByCode(String ext, String merOrUserCode,String createUser) {
        return tramapper.findPayTraTransactionByCode(ext, merOrUserCode,createUser);
    }

    //查询子商户交易记录（分页）
    public DodopalDataPage<PayTraTransaction> findTraRecordByPage(PayTraTransactionQuery query) {
        List<PayTraTransaction> findTraRecordByPage = tramapper.findTraRecordByPage(query);
        DodopalDataPage<PayTraTransaction> page = new DodopalDataPage<PayTraTransaction>(query.getPage(), findTraRecordByPage);
        return page;
    }
    
    //查询商户额度提取分页
    public DodopalDataPage<PayTraTransaction> findMerCreditsByPage(PayTraTransactionQuery query) {

        List<PayTraTransaction> findTraRecordByPage = tramapper.findMerCreditsByPage(query);

        DodopalDataPage<PayTraTransaction> page = new DodopalDataPage<PayTraTransaction>(query.getPage(), findTraRecordByPage);
        return page;
    }
    
    //查询子商户交易记录（分页）
    public List<PayTraTransaction> findTraRecordByMerParentCode(String merParentCode) {

        List<PayTraTransaction> findTraRecordByPage = tramapper.findTraRecordByMerParentCode(merParentCode);
        return findTraRecordByPage;
    }

    //门户系统 转账  生成交易流水
    @Transactional
    public void transferCreateTran(PayTraTransaction payTraTransaction) {
        tramapper.transferCreateTran(payTraTransaction);
    }

    @Override
    //门户系统 转账  调用账户系统 生成资金变更记录
    public DodopalResponse<Boolean> transferAccount(AccountTransferListDTO accountTransferDTOList) {
        log.info("门户系统 转账  调用账户系统 生成资金变更记录 TraTransactionServiceImpl transferAccount accountTransferDTOList"+accountTransferDTOList.toString());
        DodopalResponse<Boolean>  response = new DodopalResponse<Boolean>();
        try {
           response = payTraTransactionDelegate.accountTransfer(accountTransferDTOList);
        }catch (HessianRuntimeException e) {
            log.debug("门户系统 转账  调用账户系统 生成资金变更记录 TraTransactionServiceImpl transferAccount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("门户系统 转账  调用账户系统 生成资金变更记录 TraTransactionServiceImpl transferAccount call Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return  response;
    }

    //转账成功 修改交易流水状态（内、外部交易状态）
    @Transactional
    public void UpdateStatesByTransfer(PayTraTransaction payTraTransaction) {
        
        tramapper.UpdateStatesByTransfer(payTraTransaction);
    }

    //转账失败 修改交易流水状态（内部交易状态）
    @Transactional
    public void UpdateInStatesByTransfer(PayTraTransaction payTraTransaction) {
        tramapper.UpdateInStatesByTransfer(payTraTransaction);
    }

	//查询所有交易记录
	public List<PayTraTransaction> findTraRecordAll(PayTraTransactionQuery query) {
		List<PayTraTransaction> result = tramapper.findTraRecordAll(query);
        return result;
	}
	
	 //查询所有交易记录 （oss系统 导出）
	@Transactional(readOnly = true)
    public List<PayTraTransaction> findTrasactionExport(PayTraTransactionQuery query) {
        List<PayTraTransaction> result = tramapper.findTrasactionExport(query);
        return result;
    }

    //查询交易记录（手机端和VC端）
    @Transactional(readOnly = true)
    public List<PayTraTransaction> queryPayTranscation(TranscationRequest request) {
        List<PayTraTransaction> result= tramapper.queryPayTranscation(request);
        return result;
    }

    @Override
    public List<PayTraTransaction> findMerCreditsExport(PayTraTransactionQuery query) {
        List<PayTraTransaction> result = tramapper.findMerCreditsExport(query);
        return result;
    }

}
