package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.service.ManagementForSupplierService;
import com.dodopal.portal.delegate.ManagementForSupplierDelegate;

@Service
public class ManagementForSupplierServiceImpl implements ManagementForSupplierService {

	@Autowired
	private ManagementForSupplierDelegate managementForSupplierDelegate;
	
	@Override
	public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO) {
		DodopalResponse<DodopalDataPage<TransactionDetailsBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
		
		DodopalResponse<DodopalDataPage<ProductOrderDTO>> response=managementForSupplierDelegate.queryCardRechargeDetails(queryDTO);
		if(ResponseCode.SUCCESS.equals(response.getCode())){
			if(response.getResponseEntity() != null && response.getResponseEntity().getRecords() != null && response.getResponseEntity().getRecords().size() > 0){
				List<ProductOrderDTO> list = response.getResponseEntity().getRecords();
				List<TransactionDetailsBean> beanList = new ArrayList<TransactionDetailsBean>(list.size());
				for (ProductOrderDTO productOrderDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(productOrderDTO.getProOrderNum())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(productOrderDTO.getProOrderNum());
					}
					if(productOrderDTO.getProOrderDate() == null){
						bean.setProOrderDate("");
					}else{
						bean.setProOrderDate(DateUtils.dateToString(productOrderDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					}
					
					if(productOrderDTO.getTxnAmt() == null || productOrderDTO.getTxnAmt() == 0){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(productOrderDTO.getTxnAmt()) / 100));
					}
					if(StringUtils.isBlank(productOrderDTO.getOrderCardno())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(productOrderDTO.getOrderCardno());
					}
					
					if(productOrderDTO.getBlackAmt()==null || productOrderDTO.getBlackAmt()==0){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f",productOrderDTO.getBlackAmt().doubleValue()/100));
					}
					
					bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrderDTO.getProOrderState()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrderDTO.getProOrderState()).getName());
					beanList.add(bean);
				}
				
				PageParameter page = DodopalDataPageUtil.convertPageInfo(response.getResponseEntity());
				DodopalDataPage<TransactionDetailsBean> pages = new DodopalDataPage<TransactionDetailsBean>(page, beanList);
				dodopalResponse.setResponseEntity(pages);
				dodopalResponse.setCode(ResponseCode.SUCCESS);
			}
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumDetails(YktCardConsumTradeDetailQueryDTO query) {
		DodopalResponse<DodopalDataPage<TransactionDetailsBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
		DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>  response = managementForSupplierDelegate.queryCardConsumDetails(query);
		if(ResponseCode.SUCCESS.equals(response.getCode())){
			if(response.getResponseEntity() != null && response.getResponseEntity().getRecords() != null && response.getResponseEntity().getRecords().size() > 0){
				List<YktCardConsumTradeDetailDTO> list = response.getResponseEntity().getRecords();
				List<TransactionDetailsBean> beanList = new ArrayList<TransactionDetailsBean>(list.size());
				for (YktCardConsumTradeDetailDTO cardConsumTradeDetailDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOrderNo())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(cardConsumTradeDetailDTO.getOrderNo());
					}
					
					bean.setProOrderDate(DateUtils.dateToString(cardConsumTradeDetailDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getCardNo())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(cardConsumTradeDetailDTO.getCardNo());
					}
					
					if(cardConsumTradeDetailDTO.getBefbal()==null){
						bean.setBefbal("");
					}else{
						bean.setBefbal(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getBefbal())/100));
					}
					if(cardConsumTradeDetailDTO.getBlackAmt()==null){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getBlackAmt())/100));
					}
					
					bean.setMerName(cardConsumTradeDetailDTO.getMerName());
					bean.setCityName(cardConsumTradeDetailDTO.getCityName());
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getPosCode())){
						bean.setPosCode("");
					}else{
						bean.setPosCode(cardConsumTradeDetailDTO.getPosCode());
					}
					if(cardConsumTradeDetailDTO.getOriginalPrice()==null){
						bean.setOriginalPrice("");
					}else{
						bean.setOriginalPrice(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getOriginalPrice())/100));
					}
					//bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					bean.setProOrderState(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					beanList.add(bean);
				}
				
				PageParameter page = DodopalDataPageUtil.convertPageInfo(response.getResponseEntity());
				DodopalDataPage<TransactionDetailsBean> pages = new DodopalDataPage<TransactionDetailsBean>(page, beanList);
				dodopalResponse.setResponseEntity(pages);
				dodopalResponse.setCode(ResponseCode.SUCCESS);
			}
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}
		
		
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<String> exportCardRechargeDetails(HttpServletRequest request,HttpServletResponse res,ProductOrderQueryDTO queryDTO) {
		DodopalResponse<String> response2 = new DodopalResponse<String>();
		queryDTO.setPage(new PageParameter(1,5000));
		DodopalResponse<DodopalDataPage<ProductOrderDTO>> response=managementForSupplierDelegate.queryCardRechargeDetails(queryDTO);
		if(ResponseCode.SUCCESS.equals(response.getCode())){
			Map<String, String> index =new LinkedHashMap<String, String>();
			String sheetName = "一卡通充值交易记录-"+queryDTO.getMerName();
			index.put("orderNo", "订单编号");
			index.put("txnAmt", "充值金额(元)");
			index.put("orderCardno", "卡号");
			index.put("blackAmt", "充值后卡内余额(元)");
			index.put("proOrderState", "订单状态");
			index.put("proOrderDate", "充值时间");
			List<TransactionDetailsBean> beanList = null;
			if(response.getResponseEntity() != null && response.getResponseEntity().getRecords() != null && response.getResponseEntity().getRecords().size() > 0){
				List<ProductOrderDTO> list = response.getResponseEntity().getRecords();
				beanList = new ArrayList<TransactionDetailsBean>(list.size());
				for (ProductOrderDTO productOrderDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(productOrderDTO.getProOrderNum())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(productOrderDTO.getProOrderNum());
					}
					if(productOrderDTO.getProOrderDate() == null){
						bean.setProOrderDate("");
					}else{
						bean.setProOrderDate(DateUtils.dateToString(productOrderDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					}
					
					if(productOrderDTO.getTxnAmt() == null || productOrderDTO.getTxnAmt() == 0){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(productOrderDTO.getTxnAmt()) / 100));
					}
					if(StringUtils.isBlank(productOrderDTO.getOrderCardno())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(productOrderDTO.getOrderCardno());
					}
					
					if(productOrderDTO.getBlackAmt()==null || productOrderDTO.getBlackAmt()==0){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f",productOrderDTO.getBlackAmt().doubleValue()/100));
					}
					
					bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrderDTO.getProOrderState()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(productOrderDTO.getProOrderState()).getName());
					beanList.add(bean);
				}
				
				
				String code = ExcelUtil.excelExport(request, res, beanList, index, sheetName);
				response2.setCode(code);
			}else{
				beanList = new ArrayList<TransactionDetailsBean>();
				String code = ExcelUtil.excelExport(request, res, beanList, index, sheetName);
				response2.setCode(code);
			}
		}else{
			response2.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response2;
	}

	@Override
	public DodopalResponse<String> exportCardConsumDetails(HttpServletRequest request,HttpServletResponse response,
			YktCardConsumTradeDetailQueryDTO queryDTO) {
		DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
		queryDTO.setPage(new PageParameter(1,5000));
		DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>  res = managementForSupplierDelegate.queryCardConsumDetails(queryDTO);
		if(ResponseCode.SUCCESS.equals(res.getCode())){
			String sheetName = "";
			Map<String, String> index =new LinkedHashMap<String, String>();
			sheetName = "一卡通消费交易记录-"+queryDTO.getMerName();
			index.put("orderNo", "订单编号");
			index.put("txnAmt", "消费金额(元)");
			index.put("orderCardno", "卡号");
			index.put("blackAmt", "	消费后卡内余额(元)");
			index.put("proOrderState", "订单状态");
			index.put("proOrderDate", "消费时间");
			List<TransactionDetailsBean> beanList = null;
			if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
				List<YktCardConsumTradeDetailDTO> list = res.getResponseEntity().getRecords();
				beanList =new ArrayList<TransactionDetailsBean>(list.size());
				for (YktCardConsumTradeDetailDTO cardConsumTradeDetailDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOrderNo())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(cardConsumTradeDetailDTO.getOrderNo());
					}
					
					bean.setProOrderDate(DateUtils.dateToString(cardConsumTradeDetailDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getCardNo())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(cardConsumTradeDetailDTO.getCardNo());
					}
					
					if(cardConsumTradeDetailDTO.getBlackAmt()==null){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getBlackAmt())/100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getMerName())){
						bean.setMerName("");
					}else{
						bean.setMerName(cardConsumTradeDetailDTO.getMerName());
					}
					bean.setProOrderState(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					//bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					beanList.add(bean);
				}
				String code = ExcelUtil.excelExport(request, response, beanList, index, sheetName);
				dodopalResponse.setCode(code);
			}else{
				beanList = new ArrayList<TransactionDetailsBean>();
				String code = ExcelUtil.excelExport(request, response, beanList, index, sheetName);
				dodopalResponse.setCode(code);
			}
		}else{
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	//业务订单汇总一卡通消费详细导出
	public DodopalResponse<String> exportCardConsumCollectDetails(HttpServletRequest request, HttpServletResponse response,
			YktCardConsumTradeDetailQueryDTO queryDTO) {
		DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
		queryDTO.setPage(new PageParameter(1,100000));
		DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>  res = managementForSupplierDelegate.queryCardConsumCollectDetailsByPage(queryDTO);
		if(ResponseCode.SUCCESS.equals(res.getCode())){
			String sheetName = "业务订单汇总一卡通消费详情记录";
			Map<String, String> index =new LinkedHashMap<String, String>();
			index.put("orderNo", "订单编号");
			index.put("merName", "商户名称");
			index.put("cityName", "城市");
			index.put("originalPrice", "应付金额（元）");
			index.put("txnAmt", "实付金额（元）");
			index.put("befbal", "消费金额(元)");
			index.put("blackAmt", "消费后卡内余额（元）");
			index.put("orderCardno", "卡号");
			index.put("posCode", "POS号");
			index.put("proOrderState", "订单状态");
			index.put("proOrderDate", "消费时间");
		    index.put("posComments", "POS备注");
			List<TransactionDetailsBean> beanList = null;
			if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
				List<YktCardConsumTradeDetailDTO> list = res.getResponseEntity().getRecords();
				beanList =new ArrayList<TransactionDetailsBean>(list.size());
				for (YktCardConsumTradeDetailDTO cardConsumTradeDetailDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOrderNo())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(cardConsumTradeDetailDTO.getOrderNo());
					}
					bean.setCityName(cardConsumTradeDetailDTO.getCityName());
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOriginalPrice())){
						bean.setOriginalPrice("");
					}else{
						bean.setOriginalPrice(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getOriginalPrice()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getBefbal())){
						bean.setBefbal("");
					}else{
						bean.setBefbal(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getBefbal()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getBlackAmt())){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getBlackAmt()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getCardNo())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(cardConsumTradeDetailDTO.getCardNo());
					}
					
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getMerName())){
	                        bean.setMerName("");
	                    }else{
	                        bean.setMerName(cardConsumTradeDetailDTO.getMerName());
	                    }
	                    
				    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getPosCode())){
                           bean.setPosCode("");
                       }else{
                           bean.setPosCode(cardConsumTradeDetailDTO.getPosCode());
                       }
				    
				    if(StringUtils.isBlank(cardConsumTradeDetailDTO.getPosComments())){
                        bean.setPosComments("");
                    }else{
                        bean.setPosComments(cardConsumTradeDetailDTO.getPosComments());
                    }
					bean.setProOrderState(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					bean.setProOrderDate(DateUtils.dateToString(cardConsumTradeDetailDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					beanList.add(bean);
				}
				String code = ExcelUtil.excelExport(request, response, beanList, index, sheetName);
				dodopalResponse.setCode(code);
			}else{
				beanList = new ArrayList<TransactionDetailsBean>();
				String code = ExcelUtil.excelExport(request, response, beanList, index, sheetName);
				dodopalResponse.setCode(code);
			}
		}else{
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	/**
     * 一卡通消费业务订单汇总详细查询
     * @param query
     * @return
     */
	public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumCollectDetails(YktCardConsumTradeDetailQueryDTO query) {
		DodopalResponse<DodopalDataPage<TransactionDetailsBean>> dodopalResponse = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
		DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>>  response = managementForSupplierDelegate.queryCardConsumCollectDetailsByPage(query);
		if(ResponseCode.SUCCESS.equals(response.getCode())){
			if(response.getResponseEntity() != null && response.getResponseEntity().getRecords() != null && response.getResponseEntity().getRecords().size() > 0){
				List<YktCardConsumTradeDetailDTO> list = response.getResponseEntity().getRecords();
				List<TransactionDetailsBean> beanList = new ArrayList<TransactionDetailsBean>(list.size());
				for (YktCardConsumTradeDetailDTO cardConsumTradeDetailDTO : list) {
					TransactionDetailsBean bean = new TransactionDetailsBean();
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getOrderNo())){
						bean.setOrderNo("");
					}else{
						bean.setOrderNo(cardConsumTradeDetailDTO.getOrderNo());
					}
					
					bean.setProOrderDate(DateUtils.dateToString(cardConsumTradeDetailDTO.getProOrderDate(), DateUtils.DATE_FULL_STR));
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getConsumeAmt())){
						bean.setTxnAmt("");
					}else{
						bean.setTxnAmt(String.format("%.2f", Double.valueOf(cardConsumTradeDetailDTO.getConsumeAmt()) / 100));
					}
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getCardNo())){
						bean.setOrderCardno("");
					}else{
						bean.setOrderCardno(cardConsumTradeDetailDTO.getCardNo());
					}
					
					if(cardConsumTradeDetailDTO.getBefbal()==null){
						bean.setBefbal("");
					}else{
						bean.setBefbal(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getBefbal())/100));
					}
					if(cardConsumTradeDetailDTO.getBlackAmt()==null){
						bean.setBlackAmt("");
					}else{
						bean.setBlackAmt(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getBlackAmt())/100));
					}
					bean.setPosComments(cardConsumTradeDetailDTO.getPosComments());
					bean.setMerName(cardConsumTradeDetailDTO.getMerName());
					bean.setCityName(cardConsumTradeDetailDTO.getCityName());
					if(StringUtils.isBlank(cardConsumTradeDetailDTO.getPosCode())){
						bean.setPosCode("");
					}else{
						bean.setPosCode(cardConsumTradeDetailDTO.getPosCode());
					}
					if(cardConsumTradeDetailDTO.getOriginalPrice()==null){
						bean.setOriginalPrice("");
					}else{
						bean.setOriginalPrice(String.format("%.2f",Double.valueOf(cardConsumTradeDetailDTO.getOriginalPrice())/100));
					}
					//bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					bean.setProOrderState(ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()) == null? "":ConsumeOrderStatesEnum.getConsumeOrderStatesEnumByCode(cardConsumTradeDetailDTO.getOrderStates()).getName());
					beanList.add(bean);
				}
				
				PageParameter page = DodopalDataPageUtil.convertPageInfo(response.getResponseEntity());
				DodopalDataPage<TransactionDetailsBean> pages = new DodopalDataPage<TransactionDetailsBean>(page, beanList);
				dodopalResponse.setResponseEntity(pages);
				dodopalResponse.setCode(ResponseCode.SUCCESS);
			}
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}
		
		
		return dodopalResponse;
	}
}
