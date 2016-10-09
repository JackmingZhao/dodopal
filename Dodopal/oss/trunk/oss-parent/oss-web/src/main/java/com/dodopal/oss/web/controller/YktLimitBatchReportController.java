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
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.YktLimitBatchReportForExport;
import com.dodopal.oss.business.bean.query.YktLimitBatchReportQuery;
import com.dodopal.oss.business.service.IcdcService;
import com.dodopal.oss.business.service.YktLimitBatchReportService;

/**
 * 数据中心-财务报表-购买通卡额度明细
 * @author 
 *
 */
@Controller
@RequestMapping("statistics/finance/report")
public class YktLimitBatchReportController extends CommonController {
    
    private Logger logger = LoggerFactory.getLogger(YktLimitBatchReportController.class);
    
    @Autowired
    private IcdcService icdcService;
    
    @Autowired
    private YktLimitBatchReportService yktLimitBatchReportService;
    
    @Autowired
    private ExpTempletService expTempletService;
    
    /**************************************************** 额度购买批次信息管理开始 *****************************************************/
    /**
     * 额度购买批次信息管理初始页面
     * @param request
     * @return
     */
    @RequestMapping("/tongCardCredits")
    public ModelAndView initBatchInfo(HttpServletRequest request) {
        return new ModelAndView("statistics/finance/yktLimitBatchReport");
    }
    
    /**
     * 查询 __财务报表__购买通卡额度明细
     * @param request
     * @return
     */
    @RequestMapping("findYktLimitBatchReportByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>> findProductYktLimitBatchInfoByPage(HttpServletRequest request,@RequestBody YktLimitBatchReportQuery findDto) {
        DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>> response = new DodopalResponse<DodopalDataPage<YktLimitBatchReportForExport>>();
        try {
            response = yktLimitBatchReportService.findYktLimitBatchReportByPage(findDto);
        }
        catch (Exception e) {
            logger.error("查询 __财务报表__购买通卡额度明细："+e.getMessage(),e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 导出 __财务报表__购买通卡额度明细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("exportYktLimitBatchReport")
    public @ResponseBody DodopalResponse<String> exportYktLimitBatchInfo(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = new DodopalResponse<String>();
        try {
            YktLimitBatchReportQuery queryDto = new YktLimitBatchReportQuery();
            queryDto.setYktName(request.getParameter("yktName"));
            queryDto.setFinancialPayDateStart(DateUtils.stringtoDate(request.getParameter("financialPayDateStart"), DateUtils.DATE_SMALL_STR));
            queryDto.setFinancialPayDateEnd(DateUtils.stringtoDate(request.getParameter("financialPayDateEnd"), DateUtils.DATE_SMALL_STR));
            queryDto.setYktAddLimitDateStart(DateUtils.stringtoDate(request.getParameter("yktAddLimitDateStart"), DateUtils.DATE_SMALL_STR));
            queryDto.setYktAddLimitDateEnd(DateUtils.stringtoDate(request.getParameter("yktAddLimitDateEnd"), DateUtils.DATE_SMALL_STR));
       
            DodopalResponse<List<YktLimitBatchReportForExport>> list = yktLimitBatchReportService.getYktLimitBatchReportForExport(queryDto);
            exportResponse.setCode(list.getCode());
            
            if (!ResponseCode.SUCCESS.equals(list.getCode())) {
                return exportResponse;
            }
            
            String sheetName = new String("购买通卡额度明细");
            
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            String resultCode = ExcelUtil.excelExport(request, response, list.getResponseEntity(), col, sheetName);
            
            exportResponse.setCode(resultCode);
            
            return exportResponse;
        }
        catch (Exception e) {
            logger.error("导出 __财务报表__购买通卡额度明细：" + e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }
     
}

