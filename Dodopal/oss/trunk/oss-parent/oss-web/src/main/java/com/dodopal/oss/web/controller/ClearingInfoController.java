package com.dodopal.oss.web.controller;

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
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.model.ClearingBasic;
import com.dodopal.oss.business.model.ClearingBasicDataDTO;
import com.dodopal.oss.business.model.dto.ClearingInfoQuery;
import com.dodopal.oss.business.service.ClearingService;

@Controller
@RequestMapping("finance/clearingDetail")
public class ClearingInfoController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(ClearingInfoController.class);

    @Autowired
    private ClearingService clearingService;

    @Autowired
    private ExpTempletService expTempletService;
    /**
     * 银行清分初始界面
     * @param request
     * @return
     */
    @RequestMapping("bank/init")
    public ModelAndView initBankClearingResult(HttpServletRequest request) {
        return new ModelAndView("finance/clearingDetail/clearingBank");
    }

    /**
     * 银行清分分页查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("bank/findBankClearingByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ClearingBasic>> findBankClearingByPage(HttpServletRequest request, @RequestBody ClearingInfoQuery queryDto) {
        DodopalResponse<DodopalDataPage<ClearingBasic>> response = new DodopalResponse<DodopalDataPage<ClearingBasic>>();
        try {
            DodopalDataPage<ClearingBasic> result = clearingService.findBankClearingByPage(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 银行导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("bank/exportBank")
    public @ResponseBody
    DodopalResponse<String> exportBank(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = null;
        try {
            ClearingInfoQuery bankQuery = new ClearingInfoQuery();
            bankQuery.setClearingDayFrom(request.getParameter("clearingDayFrom"));
            bankQuery.setClearingDayTo(request.getParameter("clearingDayTo"));
            bankQuery.setClearingFlag(request.getParameter("clearingFlag"));
            exportResponse = clearingService.excelExportBank(response, bankQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }

    /**
     * 通卡清分初始界面
     * @param request
     * @return
     */
    @RequestMapping("ykt/init")
    public ModelAndView initYktClearingResult(HttpServletRequest request) {
        return new ModelAndView("finance/clearingDetail/clearingYkt");
    }

    /**
     * 通卡清分分页查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("ykt/findYktClearingByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ClearingBasic>> findYktClearingByPage(HttpServletRequest request, @RequestBody ClearingInfoQuery queryDto) {
        DodopalResponse<DodopalDataPage<ClearingBasic>> response = new DodopalResponse<DodopalDataPage<ClearingBasic>>();
        try {
            DodopalDataPage<ClearingBasic> result = clearingService.findYktClearingByPage(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 一卡通导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("ykt/exportYkt")
    public @ResponseBody
    DodopalResponse<String> exportYkt(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = null;
        try {
            ClearingInfoQuery bankQuery = new ClearingInfoQuery();
            bankQuery.setClearingDayFrom(request.getParameter("clearingDayFrom"));
            bankQuery.setClearingDayTo(request.getParameter("clearingDayTo"));
            bankQuery.setClearingFlag(request.getParameter("clearingFlag"));
            exportResponse = clearingService.excelExportYkt(response, bankQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }

    /**
     * 商户清分初始界面
     * @param request
     * @return
     */
    @RequestMapping("mer/init")
    public ModelAndView initMerClearingResult(HttpServletRequest request) {
        return new ModelAndView("finance/clearingDetail/clearingMer");
    }

    /**
     * 商户清分分页查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("mer/findMerClearingByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ClearingBasic>> findMerClearingByPage(HttpServletRequest request, @RequestBody ClearingInfoQuery queryDto) {
        DodopalResponse<DodopalDataPage<ClearingBasic>> response = new DodopalResponse<DodopalDataPage<ClearingBasic>>();
        try {
            DodopalDataPage<ClearingBasic> result = clearingService.findMerClearingByPage(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 商户导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("mer/exportMer")
    public @ResponseBody
    DodopalResponse<String> exportMer(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = null;
        try {
            ClearingInfoQuery bankQuery = new ClearingInfoQuery();
            bankQuery.setClearingDayFrom(request.getParameter("clearingDayFrom"));
            bankQuery.setClearingDayTo(request.getParameter("clearingDayTo"));
            bankQuery.setClearingFlag(request.getParameter("clearingFlag"));
            exportResponse = clearingService.excelExportMer(response, bankQuery);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }
    
    //商户清分
    @RequestMapping("mer/exportExcelMer")
    public @ResponseBody DodopalResponse<String> exportExcelMer(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	String clearingDayFrom = request.getParameter("clearingDayFrom");
    	String clearingDayTo = request.getParameter("clearingDayTo");
    	String clearingFlag = request.getParameter("clearingFlag");
    	
    	ClearingInfoQuery query = new ClearingInfoQuery();
    	query.setClearingDayFrom(clearingDayFrom);
    	query.setClearingDayTo(clearingDayTo);
    	query.setClearingFlag(clearingFlag);
    	
    	int count = clearingService.findMerClearingCount(query);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<ClearingBasicDataDTO> list = clearingService.excelExportMer(query);
    	String sheetName = new String("商户清分");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
    
  //银行清分
    @RequestMapping("bank/exportExcelBank")
    public @ResponseBody DodopalResponse<String> exportExcelBank(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	ClearingInfoQuery query = new ClearingInfoQuery();
    	query.setClearingDayFrom(request.getParameter("clearingDayFrom"));
    	query.setClearingDayTo(request.getParameter("clearingDayTo"));
    	query.setClearingFlag(request.getParameter("clearingFlag"));
    	
    	int count = clearingService.findBankClearingCount(query);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<ClearingBasicDataDTO> list = clearingService.excelExportBank(query);
    	String sheetName = new String("银行清分");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
    
    //通卡清分
    @RequestMapping("ykt/exportExcelYkt")
    public @ResponseBody DodopalResponse<String> exportExcelYkt(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	String clearingDayFrom = request.getParameter("clearingDayFrom");
    	String clearingDayTo = request.getParameter("clearingDayTo");
    	String clearingFlag = request.getParameter("clearingFlag");
    	
    	ClearingInfoQuery query = new ClearingInfoQuery();
    	query.setClearingDayFrom(clearingDayFrom);
    	query.setClearingDayTo(clearingDayTo);
    	query.setClearingFlag(clearingFlag);
    	
    	int count = clearingService.findYktClearingCount(query);
    	int exportMaxNum = 50000;
    	if(count > exportMaxNum){
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + count);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	
    	List<ClearingBasicDataDTO> list = clearingService.excelExportYkt(query);
    	String sheetName = new String("供应商清分");
    	
    	Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
    	
    	resp.setCode(resultCode);
    	
    	return resp;
    }
    //sdassdass
}
