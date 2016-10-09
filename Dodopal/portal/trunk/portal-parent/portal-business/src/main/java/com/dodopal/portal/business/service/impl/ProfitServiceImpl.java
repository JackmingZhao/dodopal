package com.dodopal.portal.business.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.users.dto.ProfitCollectDTO;
import com.dodopal.api.users.dto.ProfitDetailsDTO;
import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.ProfitCollectBean;
import com.dodopal.portal.business.bean.ProfitDetailsBean;
import com.dodopal.portal.business.model.query.ProfitQuery;
import com.dodopal.portal.business.service.ProfitService;
import com.dodopal.portal.delegate.ProfitDelegate;

@Service
public class ProfitServiceImpl implements ProfitService{
	private final static Logger log = LoggerFactory.getLogger(ProductManageServiceImpl.class);
	
	@Autowired
	private ProfitDelegate profitDelegate;
	
	//查询
	public DodopalResponse<DodopalDataPage<ProfitCollectBean>> findProfitCollectListByPage(ProfitQuery query) {
		log.info("ProfitServiceImpl findProfitCollectListByPage is start:"+query);

		DodopalResponse<DodopalDataPage<ProfitCollectBean>> response = new DodopalResponse<DodopalDataPage<ProfitCollectBean>>();
		ProfitQueryDTO queryDTO = new ProfitQueryDTO();
		try {
			if (StringUtils.isNotBlank(query.getStartDate())) {
	        	queryDTO.setStartDate(format(query.getStartDate()));
	        }
			if (StringUtils.isNotBlank(query.getEndDate())) {
	        	queryDTO.setEndDate(format(query.getEndDate()));
	        }
			if (StringUtils.isNotBlank(query.getMerCode())) {
	        	queryDTO.setMerCode(query.getMerCode());
	        }if (StringUtils.isNotBlank(query.getCollectDate())) {
	        	queryDTO.setCollectDate(query.getCollectDate());
	        }
			//分页参数
	        if (query.getPage() != null) {
	        	queryDTO.setPage(query.getPage());;
	        }
			DodopalResponse<DodopalDataPage<ProfitCollectDTO>> dodopalResponse = profitDelegate.findProfitCollectListByPage(queryDTO);
			if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				ArrayList<ProfitCollectBean> beanList = new ArrayList<ProfitCollectBean>();
				if(dodopalResponse.getResponseEntity() !=null && CollectionUtils.isNotEmpty(dodopalResponse.getResponseEntity().getRecords())){
					for(ProfitCollectDTO dto : dodopalResponse.getResponseEntity().getRecords()){
						ProfitCollectBean bean = new ProfitCollectBean();
						bean.setCollectDate(dto.getCollectDate());
						bean.setCollectTime(DateUtils.dateToString(dto.getCollectTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR) );
						bean.setCustomerCode(dto.getCustomerCode());
						bean.setCustomerName(dto.getCustomerName());
						bean.setBussinessType(dto.getBussinessType());
						bean.setTradeCount(dto.getTradeCount());
						bean.setTradeAmount((double)dto.getTradeAmount()/100);
						bean.setProfit((double)dto.getProfit()/100);
						beanList.add(bean);
					}
				}
				//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
				DodopalDataPage<ProfitCollectBean> pages = new DodopalDataPage<ProfitCollectBean>(page, beanList);
				response.setCode(dodopalResponse.getCode());
				response.setResponseEntity(pages);
			}else{
				response.setCode(dodopalResponse.getCode());
			}
			
		} catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		log.info("ProfitServiceImpl findProfitCollectListByPage is end:"+ query);
		return response;
	}

	//详情
	public DodopalResponse<DodopalDataPage<ProfitDetailsBean>> findProfitDetailsListByPage(ProfitQuery query) {
		log.info("ProfitServiceImpl findProfitDetailsListByPage is start:"+query);
		DodopalResponse<DodopalDataPage<ProfitDetailsBean>> response = new DodopalResponse<DodopalDataPage<ProfitDetailsBean>>();
		ProfitQueryDTO queryDTO = new ProfitQueryDTO();
		if (StringUtils.isNotBlank(query.getStartDate())) {
        	queryDTO.setStartDate(DateUtils.stringtoDate(query.getStartDate(), DateUtils.DATE_FORMAT_YYMMDD_STR));
        }
		if (StringUtils.isNotBlank(query.getEndDate())) {
        	queryDTO.setEndDate(DateUtils.stringtoDate(query.getEndDate(), DateUtils.DATE_FORMAT_YYMMDD_STR));
        }
		if (StringUtils.isNotBlank(query.getMerCode())) {
        	queryDTO.setMerCode(query.getMerCode());
        }if (StringUtils.isNotBlank(query.getCollectDate())) {
        	queryDTO.setCollectDate(query.getCollectDate());
        }
        //分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        try {
        	DodopalResponse<DodopalDataPage<ProfitDetailsDTO>> dodopalResponse = profitDelegate.findProfitDetailsListByPage(queryDTO);
        	if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				ArrayList<ProfitDetailsBean> beanList = new ArrayList<ProfitDetailsBean>();
				if(dodopalResponse.getResponseEntity() !=null && CollectionUtils.isNotEmpty(dodopalResponse.getResponseEntity().getRecords())){
					for(ProfitDetailsDTO dto : dodopalResponse.getResponseEntity().getRecords()){
						ProfitDetailsBean bean = new ProfitDetailsBean();
						PropertyUtils.copyProperties(bean, dto);
						beanList.add(bean);
					}
				}
				//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
				DodopalDataPage<ProfitDetailsBean> pages = new DodopalDataPage<ProfitDetailsBean>(page, beanList);
				response.setCode(dodopalResponse.getCode());
				response.setResponseEntity(pages);
			}else{
				response.setCode(dodopalResponse.getCode());
			}
		} catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
        log.info("ProfitServiceImpl findProfitDetailsListByPage is end:"+ query);
		return response;
	}

	//导出
	public DodopalResponse<String> exportProfit(HttpServletResponse response,ProfitQueryDTO queryDTO) {
		queryDTO.setPage(new PageParameter());
		DodopalResponse<List<ProfitCollectDTO>> transactionByPage = profitDelegate.findProfitCollect(queryDTO);
		List<ProfitCollectDTO> tranDTO = transactionByPage.getResponseEntity();
		List<ProfitCollectBean> tranList = new ArrayList<ProfitCollectBean>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
			if (CollectionUtils.isNotEmpty(tranDTO)) {
			for(ProfitCollectDTO dto : tranDTO){
				ProfitCollectBean bean = new ProfitCollectBean();
				bean.setCollectDate(dto.getCollectDate());
				bean.setCollectTime(DateUtils.dateToString(dto.getCollectTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR) );
				bean.setCustomerCode(dto.getCustomerCode());
				bean.setCustomerName(dto.getCustomerName());
				bean.setBussinessType(dto.getBussinessType());
				bean.setTradeCount(dto.getTradeCount());
				bean.setTradeAmount((double)dto.getTradeAmount()/100);
				bean.setProfit((double)dto.getProfit()/100);
				tranList.add(bean);
			}
			
			 ExcelModel excelModel = new ExcelModel("profitManage");
             excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
			 
			 if (tranList != null && tranList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(tranDTO.size());
					for (ProfitCollectBean data:tranList) {
						//HashMap<String, Object> temp = new HashMap<String, Object>();
						List<String> temp = new ArrayList<String>();
						 //汇总日期
						temp.add(data.getCollectDate());
						 //汇总时间
						//temp.add(data.getCollectTime());
						//商户编号
						//temp.add(data.getCustomerCode());
						 //商户名称
						//temp.add(data.getCustomerName());
						 //业务类型
						temp.add(data.getBussinessType());
						 // 交易笔数
						temp.add(data.getTradeCount()+"");
						 // 交易金额
						temp.add(data.getTradeAmount()+"");
						 // 分润
						temp.add(data.getProfit()+"");
						// 【添加数据行】
                        dataList.add(temp);
					}	
					// 【将数据集加入model】
                    excelModel.setDataList(dataList);
				}
			//excel 模板名称
			 	return ExcelUtil.excelExport(excelModel, response);
			}else{
				excelExport.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
			}
		} catch (Exception e) {
			excelExport.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		return excelExport;
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
}
