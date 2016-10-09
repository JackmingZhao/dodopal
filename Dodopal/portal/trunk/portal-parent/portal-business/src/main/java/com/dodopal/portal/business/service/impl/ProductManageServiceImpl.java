package com.dodopal.portal.business.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.PrdProductYktBean;
import com.dodopal.portal.business.model.query.PrdProductYktQuery;
import com.dodopal.portal.business.service.ProductManageService;
import com.dodopal.portal.delegate.ProductManageDelegate;

@Service
public class ProductManageServiceImpl implements ProductManageService{

	private final static Logger log = LoggerFactory.getLogger(ProductManageServiceImpl.class);
	
	@Autowired
	private ProductManageDelegate delegate;
	
	//基于城市查询公交卡充值产品(分页)
	public DodopalResponse<DodopalDataPage<PrdProductYktBean>> findAvailableIcdcProductsByPage(PrdProductYktQuery query,String userType) {
		log.info("ProductManageServiceImpl method findAvailableIcdcProductsByPage is start:"+ query +userType);
		DodopalResponse<DodopalDataPage<PrdProductYktBean>> response = new DodopalResponse<DodopalDataPage<PrdProductYktBean>>();
		PrdProductYktQueryDTO queryDTO = new PrdProductYktQueryDTO();
		try {
			PropertyUtils.copyProperties(queryDTO, query);
			DodopalResponse<DodopalDataPage<PrdProductYktDTO>> dodopalResponse = delegate.findAvailableIcdcProductsByPage(queryDTO, userType);
			if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				ArrayList<PrdProductYktBean> beanList = new ArrayList<PrdProductYktBean>();
				if(dodopalResponse.getResponseEntity() !=null && CollectionUtils.isNotEmpty(dodopalResponse.getResponseEntity().getRecords())){
					for(PrdProductYktDTO dto : dodopalResponse.getResponseEntity().getRecords()){
						PrdProductYktBean bean = new PrdProductYktBean();
						PropertyUtils.copyProperties(bean, dto);
						bean.setProPrice(bean.getProPrice());
						bean.setProPayPrice(bean.getProPayPrice());
						beanList.add(bean);
					}
				}
				//后台传入总页数，总条数，当前页
				PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
				DodopalDataPage<PrdProductYktBean> pages = new DodopalDataPage<PrdProductYktBean>(page, beanList);
				response.setCode(dodopalResponse.getCode());
				response.setResponseEntity(pages);
			}else{
				response.setCode(dodopalResponse.getCode());
			}
			
		} catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		log.info("ProductManageServiceImpl method findAvailableIcdcProductsByPage is end:"+ query +userType);
		return response;
	}

	//基于城市查询公交卡充值产品
	public DodopalResponse<String> findAvailableIcdcProductsInCity(HttpServletResponse response,PrdProductYktQueryDTO query,String userType) {
		log.info("ProductManageServiceImpl method findAvailableIcdcProductsInCity is start:"+ query);
		
		DodopalResponse<List<PrdProductYktDTO>> changeRecordsAll = delegate.findAvailableIcdcProductsInCity(query,userType);
		List<PrdProductYktDTO> productDTO = changeRecordsAll.getResponseEntity();
		List<PrdProductYktBean> productList = new ArrayList<PrdProductYktBean>();
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
			if (CollectionUtils.isNotEmpty(productDTO)) {
				for(PrdProductYktDTO dto : productDTO){
					PrdProductYktBean productBean = new PrdProductYktBean();
					PropertyUtils.copyProperties(productBean, dto);
					productBean.setProPayPrice(productBean.getProPayPrice());
					productBean.setProPrice(productBean.getProPrice());
					productList.add(productBean);
				}
				
				ExcelModel excelModel = new ExcelModel("productManage");
	            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
			
			 if (productList != null && productList.size()>0){
				 List<List<String>> dataList = new ArrayList<List<String>>(productDTO.size());
					for (PrdProductYktBean data : productList) {
						List<String> temp = new ArrayList<String>();
						 /*产品编号*/
					    temp.add(data.getProCode());
					    /*产品名称*/
					    temp.add(data.getProName());
					    /*业务城市名称*/
					    temp.add(data.getCityName());
					    /*产品面价*/
					    temp.add(new DecimalFormat("0.00").format((double)data.getProPrice()/100));
					    /*产品成本价*/
					    temp.add(new DecimalFormat("0.00").format((double)data.getProPayPrice()/100));
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
		log.info("ProductManageServiceImpl method findAvailableIcdcProductsInCity is end userType="+userType);
		excelExport.setCode(ResponseCode.SUCCESS);
		return excelExport;
	}

}
