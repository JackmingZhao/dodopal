package com.dodopal.oss.web.controller;

import java.util.ArrayList;
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
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.TraTransactionBean;
import com.dodopal.oss.business.model.dto.MerCreditsQuery;
import com.dodopal.oss.business.service.TraTransactionService;

@Controller
@RequestMapping("/financeStatistics")
public class FinanceStatisticsController extends CommonController{
    private Logger logger = LoggerFactory.getLogger(FinanceStatisticsController.class);
    @Autowired
    private TraTransactionService traTransactionService;
    @Autowired
    private ExpTempletService expTempletService;
    
    @RequestMapping("toMerCredits")
    public ModelAndView toMerCredits(HttpServletRequest request) {
        return new ModelAndView("statistics/finance/merCredits/merCredits");
    }
    
    /**
     * 查询交易流水列表信息
     * @param request
     * @param traQuery
     * @return
     */
    @RequestMapping("findMerCredits")
    public @ResponseBody DodopalResponse<DodopalDataPage<TraTransactionBean>> findMerCredits(HttpServletRequest request, @RequestBody MerCreditsQuery traQuery) {
        DodopalResponse<DodopalDataPage<TraTransactionBean>> response = new DodopalResponse<DodopalDataPage<TraTransactionBean>>();
        try {
            response = traTransactionService.findMerCreditsNoHessionByPage(traQuery);
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("FinanceStatisticsController findMerCredits call an error",e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    
    @RequestMapping("exportMerCredits")
    public  @ResponseBody DodopalResponse<String> exportMerCredits(HttpServletRequest request,HttpServletResponse response) {
        
        DodopalResponse<String> rep = new DodopalResponse<String>();
        MerCreditsQuery traQuery = new MerCreditsQuery();
        try {
        //客户号
        String merOrUserName = request.getParameter("merOrUserName");
        //支付类型
        String payType = request.getParameter("payType");
        //客户类型
        String userType = request.getParameter("userType");
        
        //创建时间范围   开始
         String createDateStartString = request.getParameter("createDateStart");
        //创建时间范围   开始
         if(StringUtils.isNotBlank(createDateStartString)){
             Date createDateStart = DateUtils.stringtoDate(createDateStartString, DateUtils.DATE_SMALL_STR);
             traQuery.setCreateDateStart(createDateStart);
         }else{
             traQuery.setCreateDateStart(null);
         }
        
        //创建时间范围  结束
        String createDateEndString = request.getParameter("createDateEnd");
        if(StringUtils.isNotBlank(createDateEndString)){
            Date createDateEnd = DateUtils.stringtoDate(createDateEndString, DateUtils.DATE_SMALL_STR);
            traQuery.setCreateDateEnd(createDateEnd);
        }else{
            traQuery.setCreateDateEnd(null);
        }
        
        traQuery.setUserType(userType);
        traQuery.setPayType(payType);
        traQuery.setMerOrUserName(merOrUserName);
        
        int exportMaxNum = 5000;
        traQuery.setPage(new PageParameter(1, exportMaxNum)); 
        List<TraTransactionBean> list = new ArrayList<TraTransactionBean>();
        DodopalResponse<List<TraTransactionBean>> respon = traTransactionService.findMerCreditsNoHessionExport(traQuery);
        if(ResponseCode.SUCCESS.equals(respon.getCode())){
            list = respon.getResponseEntity();
        }
        
        int resultSize = list.size();
        if(resultSize > 5000) {
           // logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
            rep.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return rep;
        }
        String sheetName = new String("收商户额度款报表");
        
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
    
}
