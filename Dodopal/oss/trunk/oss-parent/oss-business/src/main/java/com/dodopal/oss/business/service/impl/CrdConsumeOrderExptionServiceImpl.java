package com.dodopal.oss.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.enums.ClearingFlagEnum;
import com.dodopal.common.enums.ConsumeOrderInternalStatesEnum;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.MoneyAlgorithmUtils;
import com.dodopal.oss.business.dao.ClearingBasicDataMapper;
import com.dodopal.oss.business.dao.CrdConsumeOrderExptionMapper;
import com.dodopal.oss.business.model.ClearingBasicData;
import com.dodopal.oss.business.model.CrdConsumeOrderExption;
import com.dodopal.oss.business.service.CrdConsumeOrderExptionService;

@Service
public class CrdConsumeOrderExptionServiceImpl implements CrdConsumeOrderExptionService {
	
	@Autowired
	private CrdConsumeOrderExptionMapper consumeOrderExptionMapper ;
	
	@Autowired
	private ClearingBasicDataMapper clearingBasicDataMapper;
	
	@Override
	public void updateCrdConsumeOrder(CrdConsumeOrderExption consumeOrderExption) {
	    
        CrdConsumeOrderExption exption = consumeOrderExptionMapper.findPrdPurchaseOrderByOrderNum(consumeOrderExption.getOrderNo());
        CrdConsumeOrderExption recordExption = consumeOrderExptionMapper.findPurchaseOrderRecordByOrdeerNum(consumeOrderExption.getOrderNo());
        
		if("2".equals(consumeOrderExption.getStates())){//成功
		
			StringBuffer buffer = new StringBuffer("处理一卡通消费异常:操作员:"+consumeOrderExption.getUserName()+"于"+DateUtils.getNow());
			buffer.append("判定一卡通消费成功,处理前主订单状态:"+exption.getStates()+"_"+ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(exption.getStates()).getName()+";");
			buffer.append("处理前内部状态:"+recordExption.getInnerStates()+"_"+ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(recordExption.getInnerStates()).getName()+";");
			buffer.append("处理后主订单状态:"+ConsumeOrderStatesEnum.PAID_SUCCESS.getCode()+"_"+ConsumeOrderStatesEnum.PAID_SUCCESS.getName()+";");
			buffer.append("处理后内部状态:"+ConsumeOrderInternalStatesEnum.DEDUCT_SUCCESS.getCode()+"_"+ConsumeOrderInternalStatesEnum.DEDUCT_SUCCESS.getName());
			
			consumeOrderExption.setStates(ConsumeOrderStatesEnum.PAID_SUCCESS.getCode());
			consumeOrderExption.setInnerStates(ConsumeOrderInternalStatesEnum.DEDUCT_SUCCESS.getCode());
			consumeOrderExption.setComments(buffer.toString());
			
			consumeOrderExptionMapper.updatePrdPurchaseOrderAfterHandleException(consumeOrderExption);
			consumeOrderExptionMapper.updatePrdPurchaseOrderRecordAfterHandleException(consumeOrderExption);
			
			ClearingBasicData clearingBasicData = clearingBasicDataMapper.getClearingBasicDataByOrderNoAndCustomerNo(consumeOrderExption.getOrderNo(), consumeOrderExption.getCustomerNo());
			if(clearingBasicData != null ){
				
				clearingBasicData.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与供应商清分状态
				long ddpToCustomerRealFee = clearingBasicData.getOrderAmount() - clearingBasicData.getDdpGetMerchantPayFee();//DDP实际转给商户业务费用
				clearingBasicData.setDdpToCustomerRealFee(ddpToCustomerRealFee);
				clearingBasicData.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与银行清分状态=1
				
				// DDP支付给银行的手续费 = ol.订单金额 *  ol. DDP与银行的手续费率   DDP支付给银行的手续费不满0.01按0.01算，例0.001=0.01
				long ddpToBankFee =MoneyAlgorithmUtils.multiplyToIntValueAddOne(String.valueOf(clearingBasicData.getOrderAmount()), String.valueOf(clearingBasicData.getDdpBankRate()/1000));
				clearingBasicData.setDdpToBankFee(ddpToBankFee);
				
				//DDP从银行实收业务费用 = ol.DDP从银行应收业务费用 -  ol.DDP支付给银行的手续费
				long ddpFromBankRealFee = clearingBasicData.getDdpFromBankShouldFee() -clearingBasicData.getDdpToBankFee();
				clearingBasicData.setDdpFromBankRealFee(ddpFromBankRealFee);
				clearingBasicData.setCustomerNo(consumeOrderExption.getCustomerNo());
				clearingBasicData.setOrderNo(consumeOrderExption.getOrderNo());
				clearingBasicData.setBankClearingTime(DateUtils.getNow());
				clearingBasicData.setCustomerClearingTime(DateUtils.getNowDate());
				
				clearingBasicDataMapper.updateClearingBasicData(clearingBasicData);
			}
			
		}else if("1".equals(consumeOrderExption.getStates())){//失败
		    
		    StringBuffer buffer = new StringBuffer("处理一卡通消费异常:操作员:"+consumeOrderExption.getUserName()+"于"+DateUtils.getNow());
            buffer.append("判定一卡通消费失败,处理前主订单状态:"+exption.getStates()+"_"+ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(exption.getStates()).getName()+";");
            buffer.append("处理前内部状态:"+recordExption.getInnerStates()+"_"+ConsumeOrderInternalStatesEnum.getConsumeOrderInternalStatesEnumByCode(recordExption.getInnerStates()).getName()+";");
            buffer.append("处理后主订单状态:"+ConsumeOrderStatesEnum.PAID_FAILURE.getCode()+"_"+ConsumeOrderStatesEnum.PAID_FAILURE.getName()+";");
            buffer.append("处理后内部状态:"+ConsumeOrderInternalStatesEnum.DEDUCT_FAILED.getCode()+"_"+ConsumeOrderInternalStatesEnum.DEDUCT_FAILED.getName());
            
			consumeOrderExption.setStates(ConsumeOrderStatesEnum.PAID_FAILURE.getCode());
			consumeOrderExption.setInnerStates(ConsumeOrderInternalStatesEnum.DEDUCT_FAILED.getCode());
			consumeOrderExption.setComments(buffer.toString());
			
			consumeOrderExptionMapper.updatePrdPurchaseOrderAfterHandleException(consumeOrderExption);
			consumeOrderExptionMapper.updatePrdPurchaseOrderRecordAfterHandleException(consumeOrderExption);
			
			ClearingBasicData clearingBasicData = clearingBasicDataMapper.getClearingBasicDataByOrderNoAndCustomerNo(consumeOrderExption.getOrderNo(), consumeOrderExption.getCustomerNo());
			if(clearingBasicData != null ){
				
				clearingBasicData.setCustomerClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与供应商清分状态
				clearingBasicData.setBankClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与银行清分状态=1
				clearingBasicData.setCustomerNo(consumeOrderExption.getCustomerNo());
				clearingBasicData.setOrderNo(consumeOrderExption.getOrderNo());
				
				clearingBasicDataMapper.updateClearingBasicData(clearingBasicData);
			}
		}
		
	}

}
