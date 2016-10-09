package com.dodopal.oss.web.controller;

import java.util.Date;
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
import com.dodopal.oss.business.model.CardRecharge;
import com.dodopal.oss.business.model.CardRechargeDTO;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;
import com.dodopal.oss.business.service.CardRechargeService;

@Controller
@RequestMapping("/cardRecharge")
public class CardRechargeController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(CardRechargeController.class);
    
    @Autowired
    private CardRechargeService cardRechargeService;
    
    @Autowired
    private ExpTempletService expTempletService;

	/**
     * 一卡通异常  初始页面
     * @param request
     * @return
     */
    @RequestMapping("/init")
    public ModelAndView adjustment(HttpServletRequest request) {
        return new ModelAndView("finance/clearingDetail/abnormal/cardRecharge");
    }
    
    @RequestMapping("/findCardRechargeByPage")
    @ResponseBody
    public DodopalResponse<DodopalDataPage<CardRecharge>> findCardRechargeByPage(HttpServletRequest request, @RequestBody CardRechargeQuery query){
        DodopalResponse<DodopalDataPage<CardRecharge>> response = new DodopalResponse<DodopalDataPage<CardRecharge>>();
        try {
            DodopalDataPage<CardRecharge> cardRecharge = cardRechargeService.findCardRechargeByPage(query);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(cardRecharge);
        }
        catch (Exception e) {
            logger.error("调账一卡通充值查询发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 10.3 调账查询 查询调账单详情
     */
    @RequestMapping("/findCardRecharge")
    public @ResponseBody DodopalResponse<CardRecharge> viewAccountAdjustment(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<CardRecharge> response = new DodopalResponse<CardRecharge>();
        try {
            CardRecharge result = cardRechargeService.findCardRecharge(id);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询调账单详情时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    
  
    
    @RequestMapping("/exportExcelCardRecharge")
    public @ResponseBody DodopalResponse<String> exportExcelCardRecharge(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	String startDate = request.getParameter("sDate");
    	String endDate = request.getParameter("eDate");
    	String customerName = request.getParameter("customerName");
    	String orderNo = request.getParameter("orderNo");
    	Date sDate = null;
    	Date eDate = null;
    	if(StringUtils.isNotBlank(startDate)){
    		sDate = DateUtils.stringtoDate(startDate, DateUtils.DATE_SMALL_STR);
    	}
    	if(StringUtils.isNotBlank(endDate)){
    		eDate = DateUtils.stringtoDate(endDate, DateUtils.DATE_SMALL_STR);
    	}
    	
    	CardRechargeQuery query = new CardRechargeQuery();
    	query.seteDate(eDate);
    	query.setsDate(sDate);
    	query.setOrderNo(orderNo);
    	query.setCustomerName(customerName);
    	int count = cardRechargeService.findCardRechargeCount(query);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<CardRechargeDTO> list = cardRechargeService.findCardRechargeExcel(query);
    	String sheetName = new String("一卡通充值异常清分");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
    
    
    
    
    
    
    
}
