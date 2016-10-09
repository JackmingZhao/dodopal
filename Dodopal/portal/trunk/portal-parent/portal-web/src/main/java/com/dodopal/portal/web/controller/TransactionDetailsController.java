package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.service.ManagementForSupplierService;

@Controller
@RequestMapping("/transactionDetails")
public class TransactionDetailsController extends CommonController{
	 
	@Autowired
	private ManagementForSupplierService managementForSupplierService;
	
    @RequestMapping("/detail")
    public ModelAndView SupplierMerchant(HttpServletRequest request,ModelMap modelMap) {
    	String merName = request.getParameter("merName");
    	String merCode = request.getParameter("merCode");
    	String mmerName = request.getParameter("mmerName");
    	String merUserName = request.getParameter("merUserName");
    	String merUserMobile = request.getParameter("merUserMobile");
    	
    	String _pageSize = request.getParameter("pageSize");
    	String _pageNo = request.getParameter("pageNo");
    	
    	
		modelMap.put("merName", merName);
		modelMap.put("merCode", merCode);
		modelMap.put("mmerName", mmerName);
		modelMap.put("merUserName", merUserName);
		modelMap.put("merUserMobile", merUserMobile);
		modelMap.put("yktCode", getYktCode(request.getSession()));
		modelMap.put("_pageSize", _pageSize);
		modelMap.put("_pageNo", _pageNo);
		
		
		modelMap.put("orderDateStart", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
		modelMap.put("orderDateEnd", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
		
        return new ModelAndView("prvd/merchant/transactionDetails");
    }
    
    //一卡通充值明细查询
    @RequestMapping("queryCardRechargeDetails")
    public @ResponseBody DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardRechargeDetails(HttpServletRequest request,@RequestBody ProductOrderQueryDTO queryDTO){
        DodopalResponse<DodopalDataPage<TransactionDetailsBean>> response = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
        queryDTO.setOrderDateStart(DateUtils.stringtoDate(DateUtils.dateToString(queryDTO.getOrderDateStart(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
        queryDTO.setOrderDateEnd(DateUtils.stringtoDate(DateUtils.dateToString(queryDTO.getOrderDateEnd(), DateUtils.DATE_FULL_STR), DateUtils.DATE_FULL_STR));
        response = managementForSupplierService.queryCardRechargeDetails(queryDTO);
        return response;
    }
    
    
    //一卡通充值明细查询
    @RequestMapping("queryCardConsumDetails")
    public @ResponseBody DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumDetails(HttpServletRequest request,@RequestBody YktCardConsumTradeDetailQueryDTO queryDTO){
    	DodopalResponse<DodopalDataPage<TransactionDetailsBean>> response = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
    	response = managementForSupplierService.queryCardConsumDetails(queryDTO);
    	return response;
    }
    
    @RequestMapping("/exportCardRechargeDetails")
    public @ResponseBody DodopalResponse<String>  exportCardRechargeDetails(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProductOrderQueryDTO queryDTO = new ProductOrderQueryDTO();
        String merName = request.getParameter("merName");
        String merCode = request.getParameter("merCode");
        String proOrderNum = request.getParameter("proOrderNum");
        String orderDateStart = request.getParameter("orderDateStart");
        String orderDateEnd = request.getParameter("orderDateEnd");
        String yktCode = request.getParameter("yktCode");
        queryDTO.setProOrderNum(proOrderNum);
        queryDTO.setOrderDateStart(DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR));
        queryDTO.setOrderDateEnd(DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR));
        queryDTO.setMerName(merName);
        queryDTO.setMerCode(merCode);
        queryDTO.setYktCode(yktCode);
        rep = managementForSupplierService.exportCardRechargeDetails(request,response, queryDTO);
        return rep;
    }
    
    @RequestMapping("/exportCardConsumDetails")
    public @ResponseBody DodopalResponse<String>  exportCardConsumDetails(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        YktCardConsumTradeDetailQueryDTO queryDTO = new YktCardConsumTradeDetailQueryDTO();
        String merName = request.getParameter("merName");
        String merCode = request.getParameter("merCode");
        String proOrderNum = request.getParameter("proOrderNum");
        String startDate = request.getParameter("orderDateStart");
        String endDate = request.getParameter("orderDateEnd");
        String yktCode = request.getParameter("yktCode");
        queryDTO.setOrderNo(proOrderNum);
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setMerName(merName);
        queryDTO.setMerCode(merCode);
        queryDTO.setYktCode(yktCode);
        rep = managementForSupplierService.exportCardConsumDetails(request,response,queryDTO);
        return rep;
    }
}
