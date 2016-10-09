package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.RechargeStatisticsYktBean;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.bean.YktCardConsumStatisticsBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.bean.query.RechargeStatisticsYktQuery;
import com.dodopal.portal.business.service.RechargeForSupplierService;
import com.dodopal.portal.delegate.RechargeForSupplierDelegate;

@Service
public class RechargeForSupplierServiceImpl implements RechargeForSupplierService{
	
	private final static Logger log = LoggerFactory.getLogger(RechargeForSupplierServiceImpl.class);
	
	@Autowired
	private RechargeForSupplierDelegate rechargeForSupplierDelegate;

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>> queryCardRechargeForSupplier(
			RechargeStatisticsYktQuery query) {
		DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>> response = new DodopalResponse<DodopalDataPage<RechargeStatisticsYktBean>>();
		RechargeStatisticsYktQueryDTO queryDTO = new RechargeStatisticsYktQueryDTO();
		log.info("RechargeForSupplierServiceImpl queryCardRechargeForSupplier is start query:"+query);
		//pos号
		if(query.getProCode()!=null){
			queryDTO.setProCode(query.getProCode());
		}
		//启用标识
		if(query.getBind()!=null){
			queryDTO.setBind(query.getBind());
		}
		//商户名称
		if(query.getMerName()!=null){
			queryDTO.setMerName(query.getMerName());
		}
		//开始日期
        if (query.getStartDate()!=null) {
        	queryDTO.setStratDate(query.getStartDate());
        }
        //结束日期
        if (query.getEndDate()!=null) {
        	queryDTO.setEndDate(query.getEndDate());
        }
      //城市
        if (query.getCityName()!=null) {
        	queryDTO.setCityName(query.getCityName());
        }
      //通卡公司code
        if (query.getYktCode()!=null) {
        	queryDTO.setYktCode(query.getYktCode());
        }
      	 //分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        
		DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> dodopalResponse = rechargeForSupplierDelegate.queryCardRechargeForSupplier(queryDTO);
		List<RechargeStatisticsYktOrderDTO> list = dodopalResponse.getResponseEntity().getRecords();
		List<RechargeStatisticsYktBean> beanList = new ArrayList<RechargeStatisticsYktBean>();
		if(dodopalResponse.getResponseEntity()!=null && CollectionUtils.isNotEmpty(list)){
			for(RechargeStatisticsYktOrderDTO dto : list){
				RechargeStatisticsYktBean bean = new RechargeStatisticsYktBean();
				bean.setBind(dto.getBind());
				bean.setProCode(dto.getProCode());
				bean.setMerName(dto.getMerName());
				bean.setRechargeCount(dto.getRechargeCount());
				bean.setRechargeAmt((double)dto.getRechargeAmt()/100);
				beanList.add(bean);
			}
		}
		//后台传入总页数，总条数，当前页
        PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
        DodopalDataPage<RechargeStatisticsYktBean> pages = new DodopalDataPage<RechargeStatisticsYktBean>(page, beanList);
        response.setCode(dodopalResponse.getCode());
        response.setResponseEntity(pages);
        log.info("RechargeForSupplierServiceImpl queryCardRechargeForSupplier is end code:"+response.getCode());
		return response;
	}

	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardRechargeDetails(ProductOrderQueryBean query) {
		DodopalResponse<DodopalDataPage<TransactionDetailsBean>> response = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
		ProductOrderQueryDTO queryDTO = new ProductOrderQueryDTO();
		log.info("RechargeForSupplierServiceImpl queryCardRechargeDetails is start query:"+query);
		 // 订单编号
	    if(query.getProOrderNum()!=null && query.getProOrderNum()!=""){
			queryDTO.setProOrderNum(query.getProOrderNum());
		}
	    if(query.getOrderDateStart()!=null){
			queryDTO.setOrderDateStart(query.getOrderDateStart());
		}
	    if(query.getOrderDateEnd()!=null){
			queryDTO.setOrderDateEnd(query.getOrderDateEnd());
		}
	    // POS号
	    if(query.getPosCode()!=null && query.getPosCode()!=""){
			queryDTO.setPosCode(query.getPosCode());
		}
	    if(query.getMerName()!=null && query.getMerName()!=""){
			queryDTO.setMerName(query.getMerName());
		}
	    //通卡公司code
	    if(query.getYktCode()!=null && query.getYktCode()!=""){
			queryDTO.setYktCode(query.getYktCode());
		}
	    //分页参数
        if (query.getPage() != null) {
        	queryDTO.setPage(query.getPage());;
        }
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> dodopalResponse = rechargeForSupplierDelegate.queryCardRechargeDetails(queryDTO);
        List<ProductOrderDTO> list = dodopalResponse.getResponseEntity().getRecords();
        ArrayList<TransactionDetailsBean> beanList = new ArrayList<TransactionDetailsBean>();
        if(dodopalResponse.getResponseEntity()!=null && CollectionUtils.isNotEmpty(list)){
        	for(ProductOrderDTO dto : list){
        		TransactionDetailsBean bean = new TransactionDetailsBean();
				if(StringUtils.isBlank(dto.getProOrderNum())){
					bean.setOrderNo("");
				}else{
					bean.setOrderNo(dto.getProOrderNum());
				}
				if(dto.getProOrderDate() == null){
					bean.setProOrderDate("");
				}else{
					bean.setProOrderDate(DateUtils.dateToString(dto.getProOrderDate(), DateUtils.DATE_FULL_STR));
				}
				
				if(dto.getTxnAmt() == null || dto.getTxnAmt() == 0){
					bean.setTxnAmt("");
				}else{
					bean.setTxnAmt(String.format("%.2f", Double.valueOf(dto.getTxnAmt()) / 100));
				}
				if(StringUtils.isBlank(dto.getOrderCardno())){
					bean.setOrderCardno("");
				}else{
					bean.setOrderCardno(dto.getOrderCardno());
				}
				
				if(dto.getBlackAmt()==null || dto.getBlackAmt()==0){
					bean.setBlackAmt("");
				}else{
					bean.setBlackAmt(String.format("%.2f",dto.getBlackAmt().doubleValue()/100));
				}
				bean.setMerName(dto.getMerName());
				bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(dto.getProOrderState()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(dto.getProOrderState()).getName());
				beanList.add(bean);
			
        	}
        }
        //后台传入总页数，总条数，当前页
        PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse.getResponseEntity());
        DodopalDataPage<TransactionDetailsBean> pages = new DodopalDataPage<TransactionDetailsBean>(page, beanList);
        response.setCode(dodopalResponse.getCode());
        response.setResponseEntity(pages);
        log.info("RechargeForSupplierServiceImpl queryCardRechargeDetails is end code:"+response.getCode());
		return response;
	}

	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public DodopalResponse<Integer> startOrStopMerSupplier(String bind,
			List<String> codes) {
		DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            if (bind == null) {
                response.setCode(ResponseCode.ACTIVATE_ERROR);
                return response;
            }
            if (CollectionUtils.isEmpty(codes)) {
                response.setCode(ResponseCode.USERS_POS_CODE_NULL);
                return response;
            }
            response = rechargeForSupplierDelegate.startOrStopMerSupplier(bind, codes);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
	
	//导出
    @Override
    public DodopalResponse<String> exportRechargeForSupplier(HttpServletRequest request,HttpServletResponse response, RechargeStatisticsYktQueryDTO merCountQueryDTO) {
    	
    	DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        try {
        	merCountQueryDTO.setPage(new PageParameter(1,5000));
			DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> res = rechargeForSupplierDelegate.queryCardRechargeForSupplier(merCountQueryDTO);
			if(ResponseCode.SUCCESS.equals(res.getCode())){
				Map<String, String> index =new LinkedHashMap<String, String>();
				index.put("proCode", "POS号");
				index.put("bind", "启用标识");
				index.put("rechargeCount", "充值交易笔数");
				index.put("rechargeAmt", "充值交易金额（元）");
				index.put("merName", "当前绑定商户");
				List<RechargeStatisticsYktBean> beanList = null;
				if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
					List<RechargeStatisticsYktOrderDTO> list = res.getResponseEntity().getRecords();
					beanList =new ArrayList<RechargeStatisticsYktBean>(list.size());
					for (RechargeStatisticsYktOrderDTO dto : list) {
						RechargeStatisticsYktBean bean = new RechargeStatisticsYktBean();
						if(StringUtils.isBlank(dto.getProCode())){
							bean.setProCode("");
						}else{
							bean.setProCode(dto.getProCode());
						}
						bean.setBind(dto.getBind());
						if(StringUtils.isNotBlank(dto.getBind())){
							bean.setBind(dto.getBind());
						}
						if(StringUtils.isNotBlank(Long.toString(dto.getRechargeCount()))){
							bean.setRechargeCount(dto.getRechargeCount());
						}else{
							bean.setRechargeCount(0);
						}
						if(StringUtils.isNotBlank(Long.toString(dto.getRechargeAmt()))){
							double consumAmt = (double)dto.getRechargeAmt()/100;
							bean.setRechargeAmt(consumAmt);
						}else{
							bean.setRechargeAmt(0);
						}
						if(dto.getMerName()==null){
							bean.setMerName("");
						}else{
							bean.setMerName(dto.getMerName());
						}
						beanList.add(bean);
					}
					String code = ExcelUtil.excelExport(request, response, beanList, index, "供应商一卡通充值列表");
					dodopalResponse.setCode(code);
				}else{
					beanList = new ArrayList<RechargeStatisticsYktBean>();
					String code = ExcelUtil.excelExport(request, response, beanList, index, "供应商一卡通充值列表");
					dodopalResponse.setCode(code);
				}
			}else{
				dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
        return dodopalResponse;
    	
    }

	//详情导出
	public DodopalResponse<String> exportCardRechargeDetails(HttpServletRequest request,HttpServletResponse response, ProductOrderQueryDTO merCountQuery) {
		DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        try {
        	
        	merCountQuery.setPage(new PageParameter(1,5000));
        	DodopalResponse<DodopalDataPage<ProductOrderDTO>> res = rechargeForSupplierDelegate.queryCardRechargeDetails(merCountQuery);
			if(ResponseCode.SUCCESS.equals(res.getCode())){
				Map<String, String> index =new LinkedHashMap<String, String>();
				index.put("orderNo", "订单编号");
				index.put("merName", "商户名称");
				index.put("txnAmt", "充值金额（元）");
				index.put("orderCardno", "卡号");
				index.put("blackAmt", "充值后卡内余额（元）");
				index.put("proOrderState", "订单状态");
				index.put("proOrderDate", "充值时间");
				List<TransactionDetailsBean> beanList = null;
				if(res.getResponseEntity() != null && res.getResponseEntity().getRecords() != null && res.getResponseEntity().getRecords().size() > 0){
					List<ProductOrderDTO> list = res.getResponseEntity().getRecords();
					beanList =new ArrayList<TransactionDetailsBean>(list.size());
					for (ProductOrderDTO dto : list) {
		        		TransactionDetailsBean bean = new TransactionDetailsBean();
						if(StringUtils.isBlank(dto.getProOrderNum())){
							bean.setOrderNo("");
						}else{
							bean.setOrderNo(dto.getProOrderNum());
						}
						bean.setMerName(dto.getMerName());
						if(dto.getTxnAmt() == null || dto.getTxnAmt() == 0){
							bean.setTxnAmt("");
						}else{
							bean.setTxnAmt(String.format("%.2f", Double.valueOf(dto.getTxnAmt()) / 100));
						}
						if(StringUtils.isBlank(dto.getOrderCardno())){
							bean.setOrderCardno("");
						}else{
							bean.setOrderCardno(dto.getOrderCardno());
						}
						if(dto.getBlackAmt()==null || dto.getBlackAmt()==0){
							bean.setBlackAmt("");
						}else{
							bean.setBlackAmt(String.format("%.2f",dto.getBlackAmt().doubleValue()/100));
						}
						bean.setProOrderState(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(dto.getProOrderState()) == null? "":RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(dto.getProOrderState()).getName());
						if(dto.getProOrderDate() == null){
							bean.setProOrderDate("");
						}else{
							bean.setProOrderDate(DateUtils.dateToString(dto.getProOrderDate(), DateUtils.DATE_FULL_STR));
						}
						
						beanList.add(bean);
		        	}
					String code = ExcelUtil.excelExport(request, response, beanList, index, "供应商一卡通充值详情列表");
					dodopalResponse.setCode(code);
				}else{
					beanList = new ArrayList<TransactionDetailsBean>();
					String code = ExcelUtil.excelExport(request, response, beanList, index, "供应商一卡通充值详情列表");
					dodopalResponse.setCode(code);
				}
			}else{
				dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
			}
		
        	
        	
        	
        	
            ProductOrderQueryDTO merCountQueryDTO = new ProductOrderQueryDTO();
            PropertyUtils.copyProperties(merCountQueryDTO, merCountQuery);
            int exportMaxNum = 5000;
            merCountQueryDTO.setPage(new PageParameter(1, exportMaxNum));
            DodopalResponse<DodopalDataPage<ProductOrderDTO>> rtResponse = rechargeForSupplierDelegate.queryCardRechargeDetails(merCountQueryDTO);
            List<ProductOrderDTO> merCountDTOList = rtResponse.getResponseEntity().getRecords();
            List<TransactionDetailsBean> merCountBeanList = new ArrayList<TransactionDetailsBean>();
            
            ExcelModel excelModel = new ExcelModel("rechargeDetails");
            excelModel.setDataStartIndex(3); //数据开始行, 可以不设置,默认为3(第四行)
            if (CollectionUtils.isNotEmpty(merCountDTOList)) {
            for(ProductOrderDTO dto : merCountDTOList){
                TransactionDetailsBean bean = new TransactionDetailsBean();
                if(dto.getBlackAmt() != null || dto.getBlackAmt() != 0){
					bean.setBlackAmt(Double.toString((double)dto.getBlackAmt()/100));
				}
                bean.setOrderCardno(dto.getOrderCardno());
                bean.setOrderNo(dto.getProOrderNum());
                bean.setProOrderDate(DateUtils.dateToString(dto.getProOrderDate(), DateUtils.DATE_FULL_STR));
                bean.setProOrderState(dto.getProOrderState());
                bean.setTxnAmt(Double.toString((double)dto.getTxnAmt()/100));
                merCountBeanList.add(bean);
            }
             if (merCountBeanList != null && merCountBeanList.size()>0){
                 List<List<String>> dataList = new ArrayList<List<String>>(merCountDTOList.size());
                    for (TransactionDetailsBean data:merCountBeanList) {
                       // Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(data.getProOrderDate());
                        List<String> temp = new ArrayList<String>();
                        //订单编号
                        temp.add(data.getOrderNo());
                        //充值金额
                        temp.add(data.getTxnAmt());
                        //卡号
                        temp.add( data.getOrderCardno());
                        //充值卡内余额
                        temp.add(data.getBlackAmt());
                        //订单状态
                        temp.add(data.getProOrderState());
                        //充值时间
                        temp.add(data.getProOrderDate());
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
            }
        }
        catch (Exception e) {
        	dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
           e.printStackTrace();
        }
       
        return dodopalResponse;
    }

}
