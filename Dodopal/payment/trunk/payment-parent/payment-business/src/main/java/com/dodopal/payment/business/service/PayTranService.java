package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.Payment;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月15日 上午10:43:47
 */
public interface PayTranService {
    /** 
     * @author dingkuiyuan@dodopal.com 
     * @Title: freezeCapital 
     * @Description: 资金冻结接口 
     * @param transactionDTO 
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String checkMerOrUserCode(PayTranDTO payTranDTO);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: createPayTranCode 
     * @Description: 生成交易流水号
     * @param businessType
     * @param cityCode
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String createPayTranCode(String businessType,String cityCode);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: savePaymentAndPayTran 
     * @Description: 保存交易与支付流水表
     * @param payment
     * @param paymentTransaction    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void savePaymentAndPayTran(Payment payment,PayTraTransaction paymentTransaction);

    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updatePayStatusAndPayTranStatus 
     * @Description:  更新交易与支付流水表的支付状态  交易外部状态 交易内部状态
     * 设定文件 
     * void    返回类型 
     * @throws 
     */
    public void updatePayStatusAndPayTranStatus(Payment payment,PayTraTransaction paymentTransaction);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findTranInfoByTranCode 
     * @Description: 根据交易流水号查找交易流水数据
     * @param TranCode
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public PayTraTransaction findTranInfoByTranCode(String tranCode);
    
    /** 
     * @Description: 根据旧交易流水号和交易类型查找交易流水数据
     * @param oldTranCode
     * @param tranType
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public PayTraTransaction findTranInfoByOldTranCode(String oldTranCode, String tranType);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findTranInfoByTranCode 
     * @Description: 根据交易流水号更新交易流水状态(内外部状态)
     * @param TranCode
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public void updateStatesByTransfer(PayTraTransaction paymentTransaction);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findTranInfoByTranCode 
     * @Description: 根据交易流水号更新交易流水外部状态
     * @param TranCode
     * @return    设定文件 
     * PayTraTransaction    返回类型 
     * @throws 
     */
    public void updateOutStatesByTransfer(PayTraTransaction paymentTransaction);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title:  findPaymentInfoByTranCode
     * @Description: 根据交易流水号查询支付流水信息
     * @param TranCode
     * @return    设定文件 
     * Payment    返回类型 
     * @throws 
     */
    public List<Payment> findPaymentInfoByTranCode(String TranCode);

}
