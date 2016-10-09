package com.dodopal.portal.business.service.impl;

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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountChildMerchantDTO;
import com.dodopal.api.account.dto.ChildMerchantAccountChangeDTO;
import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.AccountChildMerchantBean;
import com.dodopal.portal.business.bean.ChildMerchantAccountChangeBean;
import com.dodopal.portal.business.bean.query.ChildMerFundChangeQuery;
import com.dodopal.portal.business.service.ChildMerchantActListService;
import com.dodopal.portal.delegate.ChildMerchantActListDelegate;

@Service
public class ChildMerchantActListServiceImpl implements ChildMerchantActListService{

	private final static Logger log = LoggerFactory.getLogger(ChildMerchantActListServiceImpl.class);

	@Autowired
	private ChildMerchantActListDelegate actListDelegate;
	
	//根据上级商户编号 查询 子商户的资金变更记录(分页)
	public DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>> findAccountChangeChildMerByPage(
			ChildMerFundChangeQuery query) {
		log.info("ChildMerchantActListServiceImpl method findAccountChangeChildMerByPage is start" +query);
		DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>> response = new DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>>();
		ChildMerFundChangeQueryDTO queryDTO = new ChildMerFundChangeQueryDTO();
		
		//上级商户编号
		if(StringUtils.isNotBlank(query.getMerParentCode())){
			queryDTO.setMerParentCode(query.getMerParentCode());
		}
		//直营网点 商户编号
		if(StringUtils.isNotBlank(query.getMerCode())){
			queryDTO.setMerCode(query.getMerCode());
		}
		//直营网点名称
		if(StringUtils.isNotBlank(query.getMerName())){
			queryDTO.setMerName(query.getMerName());
		}
		//资金变更记录数据库id
		if(StringUtils.isNotBlank(query.getaId())){
			queryDTO.setaId(query.getaId());
		}
		//资金类别
		if(StringUtils.isNotBlank(query.getFundType())){
			queryDTO.setFundType(query.getFundType());
		}
		//变动类型
		if(StringUtils.isNotBlank(query.getChangeType())){
			queryDTO.setChangeType(query.getChangeType());
		}
		//最小交易金额范围
		if(StringUtils.isNotBlank(query.getChangeAmountMin())){
			//queryDTO.setChangeAmountMin(Long.parseLong(query.getChangeAmountMin())*100);
			if(StringUtils.isNotBlank(query.getChangeAmountMax())){
        		if(Double.parseDouble(query.getChangeAmountMax()) < Double.parseDouble(query.getChangeAmountMin())){
        			queryDTO.setChangeAmountMin(new Double(Double.parseDouble(query.getChangeAmountMax())*100).longValue());
        		}else{
        			queryDTO.setChangeAmountMin(new Double(Double.parseDouble(query.getChangeAmountMin())*100).longValue());
        		}
        	}else{
        		queryDTO.setChangeAmountMin(new Double(Double.parseDouble(query.getChangeAmountMin())*100).longValue());
        	}
		}
		//最大交易金额范围
		if(StringUtils.isNotBlank(query.getChangeAmountMax())){
//			queryDTO.setChangeAmountMax(Long.parseLong(query.getChangeAmountMax())*100);
			if(StringUtils.isNotBlank(query.getChangeAmountMin())){
        		if(Double.parseDouble(query.getChangeAmountMax()) < Double.parseDouble(query.getChangeAmountMin())){
        			queryDTO.setChangeAmountMax(new Double(Double.parseDouble(query.getChangeAmountMin())*100).longValue());
        		}else{
        			queryDTO.setChangeAmountMax(new Double(Double.parseDouble(query.getChangeAmountMax())*100).longValue());
        		}
        	}else{
        		queryDTO.setChangeAmountMax(new Double(Double.parseDouble(query.getChangeAmountMax())*100).longValue());
        	}
		}
		//开始时间
		if(StringUtils.isNotBlank(query.getStartDate())){
			queryDTO.setStartDate(format(query.getStartDate()));
		}
		//结束时间
		if(StringUtils.isNotBlank(query.getEndDate())){
			queryDTO.setEndDate(format(query.getEndDate()));
		}
		if (StringUtils.isNotBlank(query.getCreateUser())) {
        	queryDTO.setCreateUser(query.getCreateUser());
        }
		//分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        
		DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>> dodopalResponse = actListDelegate.findAccountChangeChildMerByPage(queryDTO);
		try {
			if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				ArrayList<ChildMerchantAccountChangeBean> list = new ArrayList<ChildMerchantAccountChangeBean>();
				if(dodopalResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(dodopalResponse.getResponseEntity().getRecords())){
					for(ChildMerchantAccountChangeDTO changeDTO : dodopalResponse.getResponseEntity().getRecords()){
						ChildMerchantAccountChangeBean changeBean = new ChildMerchantAccountChangeBean();
						DtoToBean(changeDTO, changeBean);
					    list.add(changeBean);
					}
				}
				
				//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
				DodopalDataPage<ChildMerchantAccountChangeBean> pages = new DodopalDataPage<ChildMerchantAccountChangeBean>(page, list);
				response.setCode(dodopalResponse.getCode());
				response.setResponseEntity(pages);
				
			}else{
				response.setCode(dodopalResponse.getCode());
			}
			
			
		} catch (Exception e) {
			response.setCode(dodopalResponse.getCode());
		}
		log.info("ChildMerchantActListServiceImpl method findAccountChangeChildMerByPage is end" +query);
		return response;
	}

	

	//根据上级商户编号 查询 子商户的资金变更记录(导出)
	public DodopalResponse<String> findAccountChangeChildMerByList(HttpServletResponse response,ChildMerFundChangeQueryDTO query) {
		log.info("ChildMerchantActListServiceImpl method findAccountChangeChildMerByList is start:"+ query);
		
		DodopalResponse<List<ChildMerchantAccountChangeDTO>> changeRecordsAll = actListDelegate.findAccountChangeChildMerByList(query);
		List<ChildMerchantAccountChangeDTO> tranDTO = changeRecordsAll.getResponseEntity();
		List<ChildMerchantAccountChangeBean> tranList = new ArrayList<ChildMerchantAccountChangeBean>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
		    ExcelModel excelModel = new ExcelModel("accountActList");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
			if (CollectionUtils.isNotEmpty(tranDTO)) {
				for(ChildMerchantAccountChangeDTO dto : tranDTO){
					ChildMerchantAccountChangeBean transaction = new ChildMerchantAccountChangeBean();
					DtoToBean(dto, transaction);
					tranList.add(transaction);
				}
//			 List<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();		
//			 List<String> sheetNames=new ArrayList<String>();
			
             
			 if (tranList != null && tranList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(tranDTO.size());
					for (ChildMerchantAccountChangeBean data:tranList) {
						//HashMap<String, Object> temp = new HashMap<String, Object>();
						List<String> temp = new ArrayList<String>();
						//网点名称
						temp.add(data.getMerName());
						//账户类别
						temp.add(data.getFundType());
						//变更前账户总余额
						temp.add(new DecimalFormat("0.00").format(data.getBeforeChangeAmount()));
						//变动金额
						temp.add(new DecimalFormat("0.00").format(data.getChangeAmount()));
						//变更前账户余额
						temp.add(new DecimalFormat("0.00").format(data.getBeforeChangeAvailableAmount()));
						//资金流向
						temp.add(data.getChangeType());
						//资金变更时间
						temp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getCreateDate()));
						//sheet名称 -单号
						//sheetNames.add(data.getId());
						// 【添加数据行】
                        dataList.add(temp);
						//results.add(temp);
					}		
					// 【将数据集加入model】
                    excelModel.setDataList(dataList);
				}
			 return ExcelUtil.excelExport(excelModel, response);
//		 	Map<String, Object> beans = new HashMap<String, Object>();
//			beans.put("results", results);	
//			beans.put("sheetNames", sheetNames);
//			//excel 模板名称
//			String modelName = "accountActList";	
//			excelExport = ExcelUtil.excelExport(modelName,beans,response);
			}else{
			    return ExcelUtil.excelExport(excelModel, response);
				//excelExport.setCode(ResponseCode.CARD_EXPORT_NULL_DATA);
			}
		} catch (Exception e) {
			excelExport.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		log.info("ChildMerchantActListServiceImpl method findAccountChangeChildMerByList is end code="+ excelExport.getCode());
		excelExport.setCode(ResponseCode.SUCCESS);
		return excelExport;
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
	
	
	private void DtoToBean(ChildMerchantAccountChangeDTO changeDTO,
			ChildMerchantAccountChangeBean changeBean) {
		// 商户编号
		changeBean.setMerCode(changeDTO.getMerCode());
		 //商户名称
		changeBean.setMerName(changeDTO.getMerName());
		 //商户类型
		changeBean.setMerType(changeDTO.getMerType());
		//所属上级商户编码
		changeBean.setMerParentCode(changeDTO.getMerParentCode());
		//资金账户号
		changeBean.setFundAccountCode(changeDTO.getFundAccountCode());
		//资金类别
		changeBean.setFundType(changeDTO.getFundType());
		//交易流水号
		changeBean.setTranCode(changeDTO.getTranCode());
		//变动类型
		changeBean.setChangeType(changeDTO.getChangeType());
		//变动金额
		changeBean.setChangeAmount((double)changeDTO.getChangeAmount()/100);
		//变动前账户总余额
		changeBean.setBeforeChangeAmount((double)changeDTO.getBeforeChangeAmount()/100);
		//变动前可用余额
		changeBean.setBeforeChangeAvailableAmount((double)changeDTO.getBeforeChangeAvailableAmount()/100);
		//变动前冻结金额
		changeBean.setBeforeChangeFrozenAmount((double)changeDTO.getBeforeChangeFrozenAmount()/100);
		//变动日期
		//changeBean.setChangeDate(changeDTO.getChangeDate());
		changeBean.setCreateDate(changeDTO.getCreateDate());
	}
}
