package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;
import com.dodopal.portal.business.service.TransactionRecordService;
import com.dodopal.portal.delegate.TransactionRecordDelegate;

@Service
public class TransactionRecordServiceImpl implements TransactionRecordService{

	private final static Logger log = LoggerFactory.getLogger(TransactionRecordServiceImpl.class);
	
	@Autowired
	private TransactionRecordDelegate transactionRecordDelegate;
	
	/**
	 * 商户交易记录查询
	 */
	public DodopalResponse<DodopalDataPage<PayTraTransaction>> findPayTraTransactionByPage(
			PayTraTransactionQuery query) {
		log.info("findPayTraTransactionByPage this queryDTO:"+query);
		DodopalResponse<DodopalDataPage<PayTraTransaction>> response = new DodopalResponse<DodopalDataPage<PayTraTransaction>>();
		PayTraTransactionQueryDTO queryDTO = new PayTraTransactionQueryDTO();
		 
        if (StringUtils.isNotBlank(query.getCreateDateStart())) {
        	queryDTO.setCreateDateStart(format(query.getCreateDateStart()));
        }
        
        if (StringUtils.isNotBlank(query.getCreateDateEnd())) {
        	queryDTO.setCreateDateEnd(format(query.getCreateDateEnd()));
        }
        
        if (StringUtils.isNotBlank(query.getRealMinTranMoney())) {
        	long parseLong = Long.parseLong(query.getRealMinTranMoney());
        	queryDTO.setRealMinTranMoney(parseLong);
        }
        
        if (StringUtils.isNotBlank(query.getRealMaxTranMoney())) {
        	queryDTO.setRealMaxTranMoney(Long.parseLong(query.getRealMaxTranMoney()));
        }
        
        if (StringUtils.isNotBlank(query.getTranName())) {
        	queryDTO.setTranName(query.getTranName());
        }
        
        if (StringUtils.isNotBlank(query.getTranOutStatus())) {
        	queryDTO.setTranOutStatus(query.getTranOutStatus());
        }
        
        if (StringUtils.isNotBlank(query.getTranType())) {
        	queryDTO.setTranType(query.getTranType());
        }
        
      //分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        
		DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> payTraTransactionDTO = transactionRecordDelegate.findPayTraTransactionByPage(queryDTO);
		try {
			if (ResponseCode.SUCCESS.equals(payTraTransactionDTO.getCode())) {
				
				List<PayTraTransaction> TraTransactionBeanList = new ArrayList<PayTraTransaction>();
				
				if (payTraTransactionDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(payTraTransactionDTO.getResponseEntity().getRecords())) {
					for (PayTraTransactionDTO traDto : payTraTransactionDTO.getResponseEntity().getRecords()) {
						PayTraTransaction transaction = new PayTraTransaction();
						
						PropertyUtils.copyProperties(transaction, traDto);
						transaction.setAmountMoney(traDto.getAmountMoney()/100);
						transaction.setRealTranMoney(traDto.getRealTranMoney()/100);
						TraTransactionBeanList.add(transaction);
						
					}
				} 
				//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(payTraTransactionDTO.getResponseEntity());
				DodopalDataPage<PayTraTransaction> pages = new DodopalDataPage<PayTraTransaction>(page, TraTransactionBeanList);
				response.setCode(payTraTransactionDTO.getCode());
				response.setResponseEntity(pages);
			}else {
				response.setCode(payTraTransactionDTO.getCode());
			}
			
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			  e.printStackTrace();
              log.error("AccountTraRecordServiceImpl findPayTraTransactionByPage throws:" ,e);
              response.setCode(payTraTransactionDTO.getCode());
		}
		return response;
	}
	
	
	public  Date format(String date){
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date response = new Date();
		try {
			response = fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}


	/**
	 * 根据tranCode查询交易记录信息
	 */
	public DodopalResponse<TraTransactionBean> findTranInfoByTranCode(
			String tranCode) {
		DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
		DodopalResponse<PayTraTransactionDTO> tranCode2 = transactionRecordDelegate.findTranInfoByTranCode(tranCode);
		TraTransactionBean traTransaction = new TraTransactionBean();
		DecimalFormat df = new DecimalFormat("0.00"); 
		PayTraTransactionDTO payDTO = tranCode2.getResponseEntity();
		 String amountMoney = "0.00";
         String realTranMoney = "0.00";
         //优惠金额
         String  favourableMoney = "0.00";
		try {
			//PropertyUtils.copyProperties(traTransaction, tranCode2.getResponseEntity());
			favourableMoney = df.format((double)(payDTO.getAmountMoney()-payDTO.getRealTranMoney())/100);
			amountMoney = df.format((double)payDTO.getAmountMoney()/100);
             //将实际交易金额格式化为两位小数（0.00）并处理成单位为（元），数据库存的是（分）
            realTranMoney = df.format((double)payDTO.getRealTranMoney()/100);
            
            traTransaction.setAmountMoney(amountMoney);
            traTransaction.setRealTranMoney(realTranMoney);
            traTransaction.setFavourableMoney(favourableMoney);
            traTransaction.setTranCode(payDTO.getTranCode());
            traTransaction.setTranName(payDTO.getTranName());
            traTransaction.setOrderNumber(payDTO.getOrderNumber());
            traTransaction.setOrderDate(payDTO.getOrderDate());
            traTransaction.setBusinessType(payDTO.getBusinessType());
            traTransaction.setTranType(payDTO.getTranType());
            traTransaction.setCreateDate(payDTO.getCreateDate());
            
			response.setResponseEntity(traTransaction);
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		} 
		return response;
	}

}
