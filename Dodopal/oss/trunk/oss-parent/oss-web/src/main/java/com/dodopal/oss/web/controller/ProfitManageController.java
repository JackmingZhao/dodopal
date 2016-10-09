package com.dodopal.oss.web.controller;

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
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.ProfitCollectBean;
import com.dodopal.oss.business.bean.ProfitDetailsBean;
import com.dodopal.oss.business.bean.query.ProfitQuery;
import com.dodopal.oss.business.service.ProfitService;

@Controller
@RequestMapping("/finance/profitManage")
public class ProfitManageController extends CommonController{

	private Logger logger = LoggerFactory.getLogger(ProfitManageController.class);
    @Autowired
    private ProfitService profitService;
    @Autowired
    private ExpTempletService expTempletService;
    
    @RequestMapping("profitInfo")
    public ModelAndView profitInfo(HttpServletRequest request) {
        return new ModelAndView("finance/profitManage/profitCollect");
    }
    @RequestMapping("findProfitCollectListByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProfitCollectBean>> findProfitCollectListByPage(HttpServletRequest request,@RequestBody ProfitQuery query) {
    	DodopalResponse<DodopalDataPage<ProfitCollectBean>> response = new DodopalResponse<DodopalDataPage<ProfitCollectBean>>();
        try {
          response = profitService.findProfitCollectListByPage(query);
        }catch (Exception e) {
          response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    @RequestMapping("findProfitDetailsListByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProfitDetailsBean>> findProfitDetailsListByPage(HttpServletRequest request,@RequestBody ProfitQuery query) {
    	DodopalResponse<DodopalDataPage<ProfitDetailsBean>> response = new DodopalResponse<DodopalDataPage<ProfitDetailsBean>>();
        try {
          response = profitService.findProfitDetailsListByPage(query);
        }catch (Exception e) {
          response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    @RequestMapping("exportExcelInfo")
    public @ResponseBody DodopalResponse<String> getExportExcelInfo(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	ProfitQuery query = new ProfitQuery();
    	query.setMerName(req.getParameter("merName"));
    	if(StringUtils.isNotBlank(req.getParameter("startDate"))) {
    		query.setStartDate(DateUtils.stringtoDate(req.getParameter("startDate"), DateUtils.DATE_SMALL_STR));
    	}
    	if(StringUtils.isNotBlank(req.getParameter("endDate"))) {
    		query.setEndDate(DateUtils.stringtoDate(req.getParameter("endDate"), DateUtils.DATE_SMALL_STR));
    	}
    	int exportMaxNum = 5000;
    	query.setPage(new PageParameter(1, 1));	// 先用一页一条 的查询 查出总条数  数据小的话就不用调两次了  只调一次  
    	int totalNum = profitService.findProfitCollectListByPage(query).getResponseEntity().getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	query.setPage(new PageParameter(1, exportMaxNum));
    	DodopalResponse<DodopalDataPage<ProfitCollectBean>> entityList = profitService.findProfitCollectListByPage(query);
    	
    	String sheetName = new String("分润信息");
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	resp = ExcelUtil.ddpExcelExport(req, res, entityList, col, sheetName);
    	return resp;
    }
    
}
