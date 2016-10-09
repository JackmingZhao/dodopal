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

import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.AccountChange;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;
import com.dodopal.portal.business.service.ChildMerChantRecordService;
import com.dodopal.portal.delegate.ChildMerChantRecordDelegate;

@Service
public class ChildMerChantRecordServiceImpl implements ChildMerChantRecordService{

	@Autowired
	private ChildMerChantRecordDelegate delegate;
	
	private final static Logger log = LoggerFactory.getLogger(ChildMerChantRecordServiceImpl.class);
	/**
	 * 查询子商户交易记录
	 */
	public DodopalResponse<DodopalDataPage<PayTraTransaction>> findTraRecordByPage(
			PayTraTransactionQuery query) {
		log.info("findTraRecordByPage this query:"+query);
		DodopalResponse<DodopalDataPage<PayTraTransaction>> response = new DodopalResponse<DodopalDataPage<PayTraTransaction>>();
		PayTraTransactionQueryDTO queryDTO = new PayTraTransactionQueryDTO();
		 //网点名称
		if (StringUtils.isNotBlank(query.getMerOrUserName())) {
        	queryDTO.setMerOrUserName(query.getMerOrUserName());
        }
		//开始日期
        if (StringUtils.isNotBlank(query.getCreateDateStart())) {
        	queryDTO.setCreateDateStart(format(query.getCreateDateStart()));
        }
        //结束日期
        if (StringUtils.isNotBlank(query.getCreateDateEnd())) {
        	queryDTO.setCreateDateEnd(format(query.getCreateDateEnd()));
        }
        //最小金额
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
        //最大金额
        if (StringUtils.isNotBlank(query.getRealMaxTranMoney())) {
        	//queryDTO.setRealMaxTranMoney(Long.parseLong(query.getRealMaxTranMoney())*100);
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
        //交易名称
        if (StringUtils.isNotBlank(query.getTranName())) {
        	queryDTO.setTranName(query.getTranName());
        }
        //交易状态
        if (StringUtils.isNotBlank(query.getTranOutStatus())) {
        	queryDTO.setTranOutStatus(query.getTranOutStatus());
        }
        //交易类型
        if (StringUtils.isNotBlank(query.getTranType())) {
        	queryDTO.setTranType(query.getTranType());
        }
        //交易流水号
        if (StringUtils.isNotBlank(query.getTranCode())) {
        	queryDTO.setTranCode(query.getTranCode());
        }
        //所属上级商户编码
        if (StringUtils.isNotBlank(query.getMerParentCode())) {
        	queryDTO.setMerParentCode(query.getMerParentCode());
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
        DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> payTraTransactionDTO = delegate.findTraRecordByPage(queryDTO);
        try{
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
              log.error("ChildMerChantRecordServiceImpl findTraRecordByPage throws:" ,e);
              response.setCode(payTraTransactionDTO.getCode());
		}
		return response;
	}
	
	
	//转换日期
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


	//导出
	public DodopalResponse<String> excelExport(HttpServletResponse response,PayTraTransactionQueryDTO queryDTO) {
		DodopalResponse<List<PayTraTransactionDTO>> changeRecordsAll = delegate.findTraRecordByMerParentCode(queryDTO);
		List<PayTraTransactionDTO> tranDTO = changeRecordsAll.getResponseEntity();
		List<PayTraTransaction> tranList = new ArrayList<PayTraTransaction>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
		    ExcelModel excelModel = new ExcelModel("childAccountRecord");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
			if (CollectionUtils.isNotEmpty(tranDTO)) {
			for(PayTraTransactionDTO dto : tranDTO){
				PayTraTransaction transaction = new PayTraTransaction();
				PropertyUtils.copyProperties(transaction, dto);
				tranList.add(transaction);
			}
			
		
			
			 if (tranList != null && tranList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(tranDTO.size());
					for (PayTraTransaction data:tranList) {
						List<String> temp = new ArrayList<String>();
						//网点名称
						temp.add(data.getMerOrUserName());
						//交易流水号
						temp.add(data.getTranCode());
						//交易名称
						temp.add(data.getBusinessType());
						//交易金额
						temp.add(new DecimalFormat("0.00").format((double)data.getRealTranMoney()/100));
						//支付方式
						temp.add(data.getPayWay());
						//交易类型
						temp.add(data.getTranType());
						//交易状态
						temp.add(data.getTranOutStatus());
						//交易日期
						temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getCreateDate()) );
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
