package com.dodopal.oss.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.model.AcctReChargeClearing;
import com.dodopal.oss.business.model.AcctReChargeClearingDTO;
import com.dodopal.oss.business.model.CardConsumeClearing;
import com.dodopal.oss.business.model.CardConsumeClearingDTO;
import com.dodopal.oss.business.model.dto.AcctReChargeClearingQuery;
import com.dodopal.oss.business.model.dto.CardConsumeClearingQuery;
import com.dodopal.oss.business.service.ClearingDetailService;

/**
 * 财务管理-清分管理-异常管理
 * 账户充值异常
 * 一卡通充值
 * 一卡通消费
 * @author 
 */
@Controller
@RequestMapping("/finance/clearingDetail")
public class ClearingDetailController extends CommonController{
    private Logger logger = LoggerFactory.getLogger(ClearingDetailController.class);
    @Autowired
    private ClearingDetailService clearingDetailService;
    @Autowired
    private ExpTempletService expTempletService;
    
    private Map<String, String> carMap = new HashMap<String,String>();
    private Map<String, String> acctMap = new HashMap<String, String>();
    
    
    public ClearingDetailController() {
    	carMap.put("orderDate","订单时间");      
    	carMap.put("orderNo","订单号");         
    	carMap.put("orderAmount","收单金额(元)"); 
    	carMap.put("tranOutStatus","订单状态");  
    	carMap.put("tranInStatus","订单内部状态"); 
    	carMap.put("customerName","客户名称");   
    	carMap.put("supplierName","通卡公司");   
    	
    	acctMap.put("orderDate","交易时间");           
    	acctMap.put("orderNo","交易号");              
    	acctMap.put("orderAmount","交易金额(元)");      
    	acctMap.put("tranOutStatus","交易状态");       
    	acctMap.put("tranInStatus","交易内部状态");      
    	acctMap.put("customerName","客户名称");        
    	acctMap.put("payGateway","银行网关");          
    	acctMap.put("payWayName","银行名称");          
	}

	/**
     * 账户充值清分主界面
     * @param request
     * @return
     */
    @RequestMapping("/abnormal/accountRecharge")
    public ModelAndView basic(HttpServletRequest request) {
        return new ModelAndView("finance/clearingDetail/abnormal/accountRecharge");
    }

    /**
     * 账户充值清分按条件分页查找
     * @param request
     * @param query   查询参数
     * @return
     */
    @RequestMapping("/abnormal/queryAcctRechargeClearingPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<AcctReChargeClearing>> queryAcctRechargeClearingPage(HttpServletRequest request, @RequestBody AcctReChargeClearingQuery query) {
        DodopalResponse<DodopalDataPage<AcctReChargeClearing>> response = new DodopalResponse<DodopalDataPage<AcctReChargeClearing>>();
        try {
            response = clearingDetailService.queryAcctRechargeClearingPage(query);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("查询账户充值清分异常出错："+e.getMessage(), e);
            
        }
        return response;
    }

    /**
     * 根据账户充值交易号查询清分信息
     * @param request
     * @param code    
     * @return
     */
    @RequestMapping("/abnormal/queryAcctRechargeClearingDetails")

    public @ResponseBody DodopalResponse<AcctReChargeClearing> queryAcctRechargeClearingDetails(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<AcctReChargeClearing> response = new DodopalResponse<AcctReChargeClearing>();
        try {
            response = clearingDetailService.queryAcctRechargeClearingDetails(id);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error("查询账户充值清分异常详情出错："+e.getMessage(), e);
        }
        return response;
    }
    
    /**
     * 一卡通消费清分主界面
     * @param request
     * @return
     */
    @RequestMapping("/abnormal/cardConsumeClearing")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("finance/clearingDetail/abnormal/cardConsumeClearing");
    }

    /**
     * 一卡通消费清分异常按条件分页查找
     * @param request
     * @param query   查询参数
     * @return
     */
    @RequestMapping("/abnormal/queryCardConsumeClearingPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<CardConsumeClearing>> queryCardConsumeClearingPage(HttpServletRequest request, @RequestBody CardConsumeClearingQuery query) {
        DodopalResponse<DodopalDataPage<CardConsumeClearing>> response = new DodopalResponse<DodopalDataPage<CardConsumeClearing>>();
        try {
            response = clearingDetailService.queryCardConsumeClearingPage(query);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * 根据一卡通订单号查询清分信息
     * @param request
     * @param code    
     * @return
     */
    @RequestMapping("/abnormal/queryCardConsumeClearingDetails")

    public @ResponseBody DodopalResponse<CardConsumeClearing> queryCardConsumeClearingDetails(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<CardConsumeClearing> response = new DodopalResponse<CardConsumeClearing>();
        try {
            response = clearingDetailService.queryCardConsumeClearingDetails(id);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            logger.error(e.getMessage(), e);
        }
        return response;
    }
    
    
  //卡通消费清分
    @RequestMapping("/abnormal/exportCardConsumeClearingExcel")
    public @ResponseBody DodopalResponse<String> exportCardConsumeClearingExcel(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	String startDate = request.getParameter("clearingStartDate");
    	String endDate = request.getParameter("clearingEndDate");
    	String customerName = request.getParameter("customerName");
    	String orderNo = request.getParameter("orderNo");
    	Date clearingStartDate = null;
    	Date clearingEndDate = null;
    	if(StringUtils.isNotBlank(startDate)){
    		clearingStartDate = DateUtils.stringtoDate(startDate, DateUtils.DATE_SMALL_STR);
    	}
    	if(StringUtils.isNotBlank(endDate)){
    		clearingEndDate = DateUtils.stringtoDate(endDate, DateUtils.DATE_SMALL_STR);
    	}
    	
    	CardConsumeClearingQuery query = new CardConsumeClearingQuery();
    	query.setClearingStartDate(clearingStartDate);
    	query.setClearingEndDate(clearingEndDate);
    	query.setOrderNo(orderNo);
    	query.setCustomerName(customerName);
    	int count = clearingDetailService.findCardConsumeClearingCount(query);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<CardConsumeClearingDTO> list = clearingDetailService.findCardConsumeClearingExcel(query);
    	String sheetName = new String("一卡通消费异常清分");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName,carMap);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
    
    
    
    //账户充值异常清分
    @RequestMapping("/abnormal/exportExcelAcctRechargeClearing")
    public @ResponseBody DodopalResponse<String> exportExcelAcctRechargeClearing(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	String startDate = request.getParameter("clearingStartDate");
    	String endDate = request.getParameter("clearingEndDate");
    	String customerName = request.getParameter("customerName");
    	String orderNo = request.getParameter("orderNo");
    	Date clearingStartDate = null;
    	Date clearingEndDate = null;
    	if(StringUtils.isNotBlank(startDate)){
    		clearingStartDate = DateUtils.stringtoDate(startDate, DateUtils.DATE_SMALL_STR);
    	}
    	if(StringUtils.isNotBlank(endDate)){
    		clearingEndDate = DateUtils.stringtoDate(endDate, DateUtils.DATE_SMALL_STR);
    	}
    	
    	AcctReChargeClearingQuery query = new AcctReChargeClearingQuery();
    	query.setClearingStartDate(clearingStartDate);
    	query.setClearingEndDate(clearingEndDate);
    	query.setCustomerName(customerName);
    	query.setOrderNo(orderNo);
    	int count = clearingDetailService.queryAcctRechargeClearingCount(query);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<AcctReChargeClearingDTO> list = clearingDetailService.queryAcctRechargeClearingExcel(query);
    	String sheetName = new String("账户充值异常清分");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName,acctMap);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
    
    
    
    
    
}
