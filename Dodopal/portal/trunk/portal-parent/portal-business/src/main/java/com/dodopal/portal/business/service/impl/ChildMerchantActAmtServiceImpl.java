package com.dodopal.portal.business.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.dodopal.api.account.dto.AccountChildMerchantDTO;
import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.AccountChange;
import com.dodopal.portal.business.bean.AccountChildMerchantBean;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.query.AccountChildMerchantQuery;
import com.dodopal.portal.business.service.ChildMerchantActAmtService;
import com.dodopal.portal.delegate.ChildMerchantActAmtDelegate;
@Service
public class ChildMerchantActAmtServiceImpl implements ChildMerchantActAmtService{

	private final static Logger log = LoggerFactory.getLogger(ChildMerchantActAmtServiceImpl.class);

	@Autowired
	private ChildMerchantActAmtDelegate actAmtDelegate;
	
	//查询（分页）
	public DodopalResponse<DodopalDataPage<AccountChildMerchantBean>> findAccountChildMerByPage(AccountChildMerchantQuery query) {
		log.info("ChildMerchantActAmtServiceImpl method findAccountChildMerByPage is start:"+ query);
		AccountChildMerchantQueryDTO queryDTO = new AccountChildMerchantQueryDTO();
		DodopalResponse<DodopalDataPage<AccountChildMerchantBean>> response = new DodopalResponse<DodopalDataPage<AccountChildMerchantBean>>();
		
		//商户名称
		if (StringUtils.isNotBlank(query.getMerName())) {
			queryDTO.setMerName(query.getMerName());
        }
		//上级商户编号
		if(StringUtils.isNotBlank(query.getMerParentCode())){
			queryDTO.setMerParentCode(query.getMerParentCode());
		}
		//账户类别
		if(StringUtils.isNotBlank(query.getFundType())){
			queryDTO.setFundType(query.getFundType());
		}
		//分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>> findAccountChildMerByPage = actAmtDelegate.findAccountChildMerByPage(queryDTO);
        
		try {
			if(ResponseCode.SUCCESS.equals(findAccountChildMerByPage.getCode())){
				List<AccountChildMerchantBean> list = new ArrayList<AccountChildMerchantBean>();
				if(findAccountChildMerByPage.getResponseEntity()!= null && CollectionUtils.isNotEmpty(findAccountChildMerByPage.getResponseEntity().getRecords())){
					for(AccountChildMerchantDTO merchantDTO  :findAccountChildMerByPage.getResponseEntity().getRecords()){
						AccountChildMerchantBean merchantBean = new AccountChildMerchantBean();
						DtoToBean(merchantDTO, merchantBean);
						list.add(merchantBean);
					}
				}
				
				//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(findAccountChildMerByPage.getResponseEntity());
				DodopalDataPage<AccountChildMerchantBean> pages = new DodopalDataPage<AccountChildMerchantBean>(page, list);
				response.setCode(findAccountChildMerByPage.getCode());
				response.setResponseEntity(pages);
			}else{
				response.setCode(findAccountChildMerByPage.getCode());
			}
			
			
		} catch (Exception e) {
			response.setCode(findAccountChildMerByPage.getCode());
		}
		log.info("ChildMerchantActAmtServiceImpl method findAccountChildMerByPage is end:"+ query);
		return response;
	}

	private void DtoToBean(AccountChildMerchantDTO merchantDTO,AccountChildMerchantBean merchantBean) {
		//商品名称
		merchantBean.setMerName(merchantDTO.getMerName());
		//商户编号
		merchantBean.setMerCode(merchantDTO.getMerCode());
		//商户类型
		merchantBean.setMerType(merchantDTO.getMerType());
		//商户 可用余额
		merchantBean.setMerMoney((double)merchantDTO.getMerMoney()/100);
		//商户 总余额
		merchantBean.setMerMoneySum((double)merchantDTO.getMerMoneySum()/100);
		//商户 冻结金额
		merchantBean.setFrozenAmount((double)merchantDTO.getFrozenAmount()/100);
		//所属上级商户编码
		merchantBean.setMerParentCode(merchantDTO.getMerParentCode());
		merchantBean.setId(merchantDTO.getId());
	}

	//导出
	public DodopalResponse<String> excelExport(AccountChildMerchantQueryDTO queryDTO,HttpServletResponse response) {
		log.info("ChildMerchantActAmtServiceImpl method findAccountChildMerByList is start:"+ queryDTO);
		
		DodopalResponse<List<AccountChildMerchantDTO>> changeRecordsAll = actAmtDelegate.findAccountChildMerByList(queryDTO);
		List<AccountChildMerchantDTO> tranDTO = changeRecordsAll.getResponseEntity();
		List<AccountChildMerchantBean> tranList = new ArrayList<AccountChildMerchantBean>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
		    ExcelModel excelModel = new ExcelModel("accountBalanceChange");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
			if (CollectionUtils.isNotEmpty(tranDTO)) {
				for(AccountChildMerchantDTO dto : tranDTO){
					AccountChildMerchantBean transaction = new AccountChildMerchantBean();
					DtoToBean(dto, transaction);
					tranList.add(transaction);
				}
			
			
			 if (tranList != null && tranList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(tranDTO.size());
					for (AccountChildMerchantBean data:tranList) {
						List<String> temp = new ArrayList<String>();
						//商户名称
						temp.add(data.getMerName());
						//商户 可用余额
						temp.add(new DecimalFormat("0.00").format(data.getMerMoney()));
						//商户 总余额
						temp.add(new DecimalFormat("0.00").format(data.getMerMoneySum()));
						//商户 冻结金额
						temp.add(new DecimalFormat("0.00").format(data.getFrozenAmount()));
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
		log.info("ChildMerchantActAmtServiceImpl method findAccountChildMerByList is end code="+ excelExport.getCode());
		excelExport.setCode(ResponseCode.SUCCESS);
		return excelExport;
	}
}
