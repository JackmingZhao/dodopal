package com.dodopal.oss.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.dodopal.oss.business.model.CityInfo;
import com.dodopal.oss.business.model.CustomerRecharge;
import com.dodopal.oss.business.model.dto.CustomerRechargeQuery;
import com.dodopal.oss.business.service.CustomerRechargeService;
import com.dodopal.oss.business.service.OperationalStatisticsService;

/**
 * 客户充值明细统计 控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/statistics/finance/report")
public class CustomerRechargeController extends CommonController{
	 private Logger logger = LoggerFactory.getLogger(CustomerRechargeController.class);
	@Autowired
	private CustomerRechargeService customerRechargeService;
	@Autowired
	private OperationalStatisticsService oprStaServ;
	
	@Autowired
	private ExpTempletService expTempletService;
	
	@RequestMapping("/customerRecharge")
    public ModelAndView customerRecharge(HttpServletRequest request,ModelMap modelMap) {
		
		modelMap.put("startDate", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
		modelMap.put("endDate", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));

		
        return new ModelAndView("statistics/finance/customerRecharge");
    }
	
	@RequestMapping("/findCustomerRechargeByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<CustomerRecharge>> findCustomerRechargeByPage(HttpServletRequest request,@RequestBody CustomerRechargeQuery customerRechargeQuery){
		DodopalResponse<DodopalDataPage<CustomerRecharge>> response = new DodopalResponse<DodopalDataPage<CustomerRecharge>>();
		try {
			DodopalDataPage<CustomerRecharge> dataPage = customerRechargeService.findCustomerRechargeList(customerRechargeQuery);
			response.setResponseEntity(dataPage);
			response.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			logger.error("客户充值明细统计发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
		}
		return response;
	}
	
	@RequestMapping("exportExcelCustomerRecharge")
	 public @ResponseBody DodopalResponse<String> exportExcelCustomerRecharge(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	String merName = request.getParameter("merName");
    	String cityCode = request.getParameter("cityCode");
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	
    	
    	CustomerRechargeQuery customerRechargeQuery = new CustomerRechargeQuery();
    	customerRechargeQuery.setCityCode(cityCode);
    	customerRechargeQuery.setEndDate(endDate);
    	customerRechargeQuery.setMerName(merName);
    	customerRechargeQuery.setStartDate(startDate);
    	
    	int count = customerRechargeService.findCustomerRechargeCount(customerRechargeQuery);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<CustomerRecharge> list = customerRechargeService.findCardRechargeExcel(customerRechargeQuery);
    	String sheetName = new String("客户充值明细统计");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
	
	// 获取统计运营管理城市信息
		@RequestMapping("getCityInfo") 	// 应该用户可以看到的城市map与 权限 无关
		public @ResponseBody DodopalResponse<List<CityInfo>> getCityInfo() { 	// 应该用户可以看到的城市map与权限无关
			DodopalResponse<List<CityInfo>> res = new DodopalResponse<List<CityInfo>>();
			try {
				List<CityInfo>  cityInfo =  oprStaServ.getCityInfo();
				res.setCode(ResponseCode.SUCCESS);
	            res.setResponseEntity(cityInfo);
			}catch(Exception e) {
				logger.error("获取统计运营管理城市信息发生错误: "+e.getMessage(), e);
	            res.setCode(ResponseCode.UNKNOWN_ERROR);
			}
			return res;
		}
	
	
	public static void main(String[] args) {

	     System.out.println(DateUtils.getCurrentDateSub(7, DateUtils.DATE_SMALL_STR));
	}
}
