package com.dodopal.running.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.running.business.model.ClearingBasicData;


public interface ClearingBasicDataMapper {

    /** 
     * 根据订单交易号查询清分基础信息
     * 
     * @param orderNo 订单编号/交易流水号
     * @param customerNo 用户号
     * @return 
     */
    public ClearingBasicData getClearingBasicDataByOrderNoAndCustomerNo(@Param("orderNo")String orderNo, @Param("customerNo")String customerNo);
    
    /** 
     * 一卡通充值异常处理_账户加值（已支付，但账户加值失败）处理过后，更新清分基础信息表相关状态
     * （与客户清分状态 = 1；客户账户实际加值 = 订单金额；与客户清分日期 = sysdate）
     * 
     * @param orderNo
     * @return 
     */
    public int updateCustomerClearingStateAfterAccountRecharge(ClearingBasicData clearingBasicData);
    
    /** 
     * 一卡通充值异常处理_资金解冻过后，更新清分基础信息表状态 （与供应商清分状态=0；与客户清分状态=0；）
     * 
     * @param orderNo
     * @return 
     */
    public int updateCustomerClearingStateAfterAccountUnfreeze(ClearingBasicData clearingBasicData);
    
    
    /** 
     * 一卡通充值异常处理_资金扣款过后，更新清分基础信息表状态 （与客户清分状态=1；与客户清分日期=sysDate；商户实际分润=商户应得分润）
     * 
     * @param orderNo
     * @return 
     */
    public int updateCustomerClearingStateAfterAccountDeduct(ClearingBasicData clearingBasicData);
    
    /**
     *  更新  清分基础数据表  数据
		 与客户清分状态=1；
      	 与客户清分日期=sysDate
        DDP实际转给商户业务费用=ol.订单金额 - ol.DDP应收商户支付方式服务费
		与银行清分状态=1；
       	与银行清分日期=sysDate
	    DDP支付给银行的手续费 = ol.订单金额 *  ol. DDP与银行的手续费率
        DDP从银行实收业务费用 = ol.DDP从银行应收业务费用 -  ol.DDP支付给银行的手	续费
     * @param clearingBasicData
     * @return
     */
    public int updateClearingBasicData(ClearingBasicData clearingBasicData);
}
