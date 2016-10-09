package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.PosLogBDBean;
import com.dodopal.oss.business.bean.query.PosLogBDFindBean;
import com.dodopal.oss.business.service.PosLogService;

/**
 * POSLog管理
 *
 */
@Controller
@RequestMapping("/basic")
public class PosLogController extends CommonController{

    @Autowired
    private PosLogService posLogService;

    
    @Autowired
    private ExpTempletService expTempletService;
    /**************************************************** POS操作日志开始 *****************************************************/
    /**
     * POS操作日志初始页面
     * @param request
     * @return
     */
    @RequestMapping("pos/log")
    public ModelAndView type(HttpServletRequest request) {
        return new ModelAndView("basic/pos/posLog");
    }

    @RequestMapping("pos/findPosLog")
    public @ResponseBody DodopalResponse<DodopalDataPage<PosLogBDBean>> findPosLog(HttpServletRequest request, @RequestBody PosLogBDFindBean logQueryDTO) {
        DodopalResponse<DodopalDataPage<PosLogBDBean>> response = new DodopalResponse<DodopalDataPage<PosLogBDBean>>();
        try {
            response = posLogService.findPosLogByPage(logQueryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    
    
    /**
     * 导出Pos操作日志
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("pos/exportPosLog")
    public  @ResponseBody DodopalResponse<String> exportPosLog(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        PosLogBDFindBean logQueryDTO = new PosLogBDFindBean();
        try{
        logQueryDTO.setMerName(request.getParameter("merName"));
        logQueryDTO.setOperName(request.getParameter("operName"));
        logQueryDTO.setSource(request.getParameter("source"));
        logQueryDTO.setCode(request.getParameter("code"));
        
        int exportMaxNum = 5000;
        logQueryDTO.setPage(new PageParameter(1, exportMaxNum)); 
        
        List<PosLogBDBean> list = new ArrayList<PosLogBDBean>();
        
        DodopalResponse<List<PosLogBDBean>> responseSult= posLogService.findPogLogsByList(logQueryDTO);
        
        if(ResponseCode.SUCCESS.equals(responseSult.getCode())){
             list = responseSult.getResponseEntity();
        }
        
        int resultSize = list.size();
        if(resultSize > 5000) {
           // logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
            rep.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return rep;
        }
        String sheetName = new String("POS操作日志");
        
        Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
        String resultCode = ExcelUtil.excelExport(request,response, list, col, sheetName);
        
        rep.setCode(resultCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
        }
      
        return rep;
    }  
    
    
    

    /**************************************************** POS操作日志结束 *****************************************************/
}
