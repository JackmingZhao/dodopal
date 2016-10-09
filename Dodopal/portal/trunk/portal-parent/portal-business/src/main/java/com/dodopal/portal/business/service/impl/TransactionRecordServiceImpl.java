package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
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
		 //将queryCopy到Dto
        queryToDto(query, queryDTO);
		DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> payTraTransactionDTO = transactionRecordDelegate.findPayTraTransactionByPage(queryDTO);
		try {
			if (ResponseCode.SUCCESS.equals(payTraTransactionDTO.getCode())) {
				
				List<PayTraTransaction> TraTransactionBeanList = new ArrayList<PayTraTransaction>();
				
				if (payTraTransactionDTO.getResponseEntity() != null && CollectionUtils.isNotEmpty(payTraTransactionDTO.getResponseEntity().getRecords())) {
					for (PayTraTransactionDTO traDto : payTraTransactionDTO.getResponseEntity().getRecords()) {
						PayTraTransaction transaction = new PayTraTransaction();
						
						PropertyUtils.copyProperties(transaction, traDto);
						transaction.setAmountMoney((double)traDto.getAmountMoney()/100);
						transaction.setRealTranMoney((double)traDto.getRealTranMoney()/100);
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


	private void queryToDto(PayTraTransactionQuery query,PayTraTransactionQueryDTO queryDTO) {
		if (StringUtils.isNotBlank(query.getCreateDateStart())) {
        	queryDTO.setCreateDateStart(format(query.getCreateDateStart()));
        }
        
        if (StringUtils.isNotBlank(query.getCreateDateEnd())) {
        	queryDTO.setCreateDateEnd(format(query.getCreateDateEnd()));
        }
        
        if (StringUtils.isNotBlank(query.getRealMinTranMoney())) {
        	if(StringUtils.isNotBlank(query.getRealMaxTranMoney())){
        		if(Double.parseDouble(query.getRealMaxTranMoney()) < Double.parseDouble(query.getRealMinTranMoney())){
        			queryDTO.setRealMinTranMoney(new Double(Double.parseDouble(query.getRealMaxTranMoney())*100).longValue());
        		}else{
        			queryDTO.setRealMinTranMoney(new Double(Double.parseDouble(query.getRealMinTranMoney())*100).longValue());
        		}
        	}else{
        		queryDTO.setRealMinTranMoney(new Double(Double.parseDouble(query.getRealMinTranMoney())*100).longValue());
        	}
        }
        
        if (StringUtils.isNotBlank(query.getRealMaxTranMoney())) {
        	if(StringUtils.isNotBlank(query.getRealMinTranMoney())){
        		if(Double.parseDouble(query.getRealMaxTranMoney()) < Double.parseDouble(query.getRealMinTranMoney())){
        			queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(query.getRealMinTranMoney())*100).longValue());
        		}else{
        			queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(query.getRealMaxTranMoney())*100).longValue());
        		}
        	}else{
        		queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(query.getRealMaxTranMoney())*100).longValue());
        	}
        }
        
        if (StringUtils.isNotBlank(query.getPayWay())) {
        	queryDTO.setPayWay(query.getPayWay());
        }
        
        if (StringUtils.isNotBlank(query.getTranOutStatus())) {
        	queryDTO.setTranOutStatus(query.getTranOutStatus());
        }
        
        if (StringUtils.isNotBlank(query.getTranType())) {
        	queryDTO.setTranType(query.getTranType());
        }
        
        if (StringUtils.isNotBlank(query.getMerOrUserCode())) {
        	queryDTO.setMerCode(query.getMerOrUserCode());
        }
        
        if (StringUtils.isNotBlank(query.getUserType())) {
        	queryDTO.setUserType(query.getUserType());
        }
        if (StringUtils.isNotBlank(query.getCreateUser())) {
        	queryDTO.setCreateUser(query.getCreateUser());
        }
        if (StringUtils.isNotBlank(query.getOrderNumber())) {
        	queryDTO.setOrderNumber(query.getOrderNumber());
        }
      //分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
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
            traTransaction.setTranOutStatus(payDTO.getTranOutStatus());
            traTransaction.setComments(payDTO.getComments());
			response.setResponseEntity(traTransaction);
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		} 
		return response;
	}


	/**
	 * 导出
	 */
	public DodopalResponse<String> excelExport(HttpServletResponse response,PayTraTransactionQueryDTO queryDTO) {
		DodopalResponse<List<PayTraTransactionDTO>> transactionByPage = transactionRecordDelegate.findPayTraTransactionAll(queryDTO);
		List<PayTraTransactionDTO> tranDTO = transactionByPage.getResponseEntity();
		List<PayTraTransaction> tranList = new ArrayList<PayTraTransaction>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
		    ExcelModel excelModel = new ExcelModel("tranRecordList");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
			if (CollectionUtils.isNotEmpty(tranDTO)) {
			for(PayTraTransactionDTO dto : tranDTO){
				PayTraTransaction transaction = new PayTraTransaction();
				PropertyUtils.copyProperties(transaction, dto);
				transaction.setAmountMoney((double)dto.getAmountMoney()/100);
				transaction.setRealTranMoney((double)dto.getRealTranMoney()/100);
				tranList.add(transaction);
			}
			
		
			 
			 if (tranList != null && tranList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(tranDTO.size());
					for (PayTraTransaction data:tranList) {
						//HashMap<String, Object> temp = new HashMap<String, Object>();
						List<String> temp = new ArrayList<String>();
						//交易流水号
						temp.add(data.getTranCode());
						//业务名称
						temp.add(data.getBusinessType());
						//交易金额
						temp.add(new DecimalFormat("0.00").format(data.getRealTranMoney()));
						//支付方式
						temp.add(data.getPayWayName());
						//交易类型
						temp.add(data.getTranType());
						//交易状态
						temp.add(data.getTranOutStatus());
						//交易日期
						temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getCreateDate()));
						//sheet名称 -单号
//						sheetNames.add(data.getId());
//						
//						results.add(temp);
						// 【添加数据行】
                        dataList.add(temp);
					}	
					// 【将数据集加入model】
                    excelModel.setDataList(dataList);
				}
			//excel 模板名称
			 	return ExcelUtil.excelExport(excelModel, response);
			}else{
			    return ExcelUtil.excelExport(excelModel, response);
				//excelExport.setCode(ResponseCode.CARD_EXPORT_NULL_DATA);
			}
		} catch (Exception e) {
			excelExport.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		return excelExport;
	}


}
