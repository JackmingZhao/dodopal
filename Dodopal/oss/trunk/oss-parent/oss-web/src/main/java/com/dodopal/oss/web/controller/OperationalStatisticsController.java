package com.dodopal.oss.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.model.CityCusReport;
import com.dodopal.oss.business.model.CityInfo;
import com.dodopal.oss.business.model.dto.CityCusReportQuery;
import com.dodopal.oss.business.service.OperationalStatisticsService;

/**
 * @author Mikaelyan
 * 运营报表
 */
@Controller
@RequestMapping("/operationalReport")
public class OperationalStatisticsController extends CommonController {
	
	private Logger logger = LoggerFactory.getLogger(OperationalStatisticsController.class);
	@Autowired
	private OperationalStatisticsService oprStaServ;
    @Autowired
    private ExpTempletService expTempletService;
	
	/**
	 * @return 城市客户组统计页面  *^_^*
	 */
	@RequestMapping("cityCustomerStatistics")
	public ModelAndView OperationalReportView() {
		return new ModelAndView("statistics/oper/cityCustomer");
	}
	
	/**
	 * 城市每月运营统计列表
	 * @param req
	 * @param que
	 * @return
	 */
	@RequestMapping("findCityCusRepStas")
	public @ResponseBody DodopalResponse<DodopalDataPage<CityCusReport>> findCityCusRepStasByPage(HttpServletRequest req, @RequestBody CityCusReportQuery que) {
		DodopalResponse<DodopalDataPage<CityCusReport>> res = new DodopalResponse<DodopalDataPage<CityCusReport>>();
		if(que.getQueryDate() == null || "".equals(que.getQueryDate())) {
			que.setQueryDate(DateUtils.getYear(new Date())+"");
		}
		if(que.getCityCode() == "-1" || "-1".equalsIgnoreCase(que.getCityCode())) {
			que.setCityCode("");
		}
		try {
			DodopalDataPage<CityCusReport> ccr = oprStaServ.findCityCusRepStasByPage(que);
			res.setCode(ResponseCode.SUCCESS);
            res.setResponseEntity(ccr);
		}
        catch (Exception e) {
            logger.error("统计运营管理发生错误: "+e.getMessage(), e);
            res.setCode(ResponseCode.UNKNOWN_ERROR);
        }
		return res;
	}
	
	// 返给页面一个系统时间
	@RequestMapping("getSysTime")
	public @ResponseBody DodopalResponse<String> getSysTime() {
		DodopalResponse<String> res = new DodopalResponse<String>();
		String sysTime = DateUtils.dateToString(new Date(), "yyyyMMdd");
		res.setResponseEntity(sysTime);
		res.setCode(ResponseCode.SUCCESS);
		return res;
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
	
	@RequestMapping("exportExcelInfo")
	public @ResponseBody DodopalResponse<String> exportExcelInfo(HttpServletRequest req, HttpServletResponse res) {
		DodopalResponse<String> ddpres = new DodopalResponse<String>();
		CityCusReportQuery que = new CityCusReportQuery();
		que.setCityCode(req.getParameter("cityCode"));
		que.setQueryDate(req.getParameter("queryDate"));
		if(que.getQueryDate() == null || "".equals(que.getQueryDate())) {
			que.setQueryDate(DateUtils.getYear(new Date())+"");
		}
		if(que.getCityCode() == "-1" || "-1".equalsIgnoreCase(que.getCityCode())) {
			que.setCityCode("");
		}
    	int exportMaxNum = 5000;
    	que.setPage(new PageParameter(1, 1));
    	int totalNum = oprStaServ.findCityCusRepStasByPage(que).getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
    		ddpres.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return ddpres;
    	}
    	que.setPage(new PageParameter(1, 5000));
    	List<CityCusReport> entityList = oprStaServ.findCityCusRepStasByPage(que).getRecords();
    	String sheetName = new String("各城市客户群统计");
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req, res, entityList, col, sheetName);
    	ddpres.setCode(resultCode);
		return ddpres;
	}
	
}
