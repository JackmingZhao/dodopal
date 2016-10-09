package com.dodopal.oss.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.TraTransactionBean;
import com.dodopal.oss.business.model.dto.TraTransactionQuery;
import com.dodopal.oss.business.service.AccountRechargeExceptionHandleService;
import com.dodopal.oss.business.service.TraTransactionService;

/**
 * 异常交易流水（账户充值异常查询、查看、处理）
 * @author shenXiang
 */
@Controller
@RequestMapping("/payment")
public class TraTransactionExceptionController extends CommonController {
    
    @Autowired
    private TraTransactionService traTransactionService;
    
    @Autowired
    private ExpTempletService expTempletService;
    
    @Autowired
    private AccountRechargeExceptionHandleService accountRechargeExceptionHandleService;

    /**
     * 交易流水页面初始化
     * 
     * @param request
     * @param findMerUsers
     * @return
     */
    @RequestMapping("excepTransaction/traExcepFlow")
    public ModelAndView traflow(HttpServletRequest request) {
        return new ModelAndView("payment/transactionException/traExcepFlow");
    }

    /**
     * 交易流水列表信息分页查询
     * 
     * @param request
     * @param traQuery
     * @return
     */
    @RequestMapping("excepTransaction/findTraExcepFlow")
    public @ResponseBody DodopalResponse<DodopalDataPage<TraTransactionBean>> findTraFlow(HttpServletRequest request, @RequestBody TraTransactionQuery traQuery) {
        
        DodopalResponse<DodopalDataPage<TraTransactionBean>> response = new DodopalResponse<DodopalDataPage<TraTransactionBean>>();
        try {
            response = traTransactionService.findTraTransactionsByPage(traQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 交易流水详情信息查询
     * 
     * @param request
     * @param traCode 交易流水号
     * @return
     */
    @RequestMapping("excepTransaction/findTraExcepFlowByTraCode")
    public @ResponseBody DodopalResponse<TraTransactionBean> findTraFlowById(HttpServletRequest request, @RequestBody String id) {

        DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
        try {
            response = traTransactionService.findTraTransaction(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 人工处理异常交易流水（账户充值异常：已付款但未加值）
     * 
     * @param request
     * @param tranCode
     * @return
     */
    @RequestMapping("excepTransaction/exceptionHandle")
    public  @ResponseBody DodopalResponse<String> exceptionHandle(HttpServletRequest request, @RequestBody String tranCode) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        String operatorId = super.generateLoginUserId(request);
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        try {
            response = accountRechargeExceptionHandleService.exceptionHandle(tranCode, operatorId);
        }
        catch (DDPException e) {
            e.printStackTrace();
            response.setCode(e.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_OSS);
            log.setOrderNum("");
            log.setTranNum(tranCode);
            if(ResponseCode.SUCCESS.equals(response.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(TraTransactionExceptionController.class.toString());
            log.setMethodName("exceptionHandle");
            log.setInParas("异常交易流水号："+tranCode+"；操作员ID："+operatorId);
            log.setOutParas(JSONObject.toJSONString(response));
            log.setRespCode(response.getCode());
            log.setRespExplain(response.getMessage());
            log.setDescription("OSS系统人工处理状态异常的交易流水，处理账户加值异常。");
            log.setSource("");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        return response;
    } 
    
    
    /**
     * 导出异常交易流水（账户充值异常：已付款但未加值）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("excepTransaction/exportExceptionTransaction")
    public  @ResponseBody DodopalResponse<String> exportExceptionTransaction(HttpServletRequest request,HttpServletResponse response) {
        
        DodopalResponse<String> rep = new DodopalResponse<String>();
        TraTransactionQuery traQuery = new TraTransactionQuery();
        try {
        //交易流水号
        String tranCode = request.getParameter("tranCode");
        //订单号
        String orderNumber = request.getParameter("orderNumber");
        //商户名|用户名
        String merOrUserName = request.getParameter("merOrUserName");
        //业务类型
        String businessType = request.getParameter("businessType");
        //外部交易状态
        String tranOutStatus = request.getParameter("tranOutStatus");
        //内部交易状态
        String tranInStatus = request.getParameter("tranInStatus");
        
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
        
        //金额   范围 小
        String realMinTranMoney = request.getParameter("realMinTranMoney");
        //金额  范围 大
        String realMaxTranMoney = request.getParameter("realMaxTranMoney");
        
        
        traQuery.setTranCode(tranCode);
        traQuery.setOrderNumber(orderNumber);
        traQuery.setMerOrUserName(merOrUserName);
        traQuery.setBusinessType(businessType);
        traQuery.setTranOutStatus(tranOutStatus);
        traQuery.setTranInStatus(tranInStatus);
        traQuery.setRealMinTranMoney(realMinTranMoney);
        traQuery.setRealMaxTranMoney(realMaxTranMoney);
        
        int exportMaxNum = 5000;
        traQuery.setPage(new PageParameter(1, exportMaxNum)); 
        
        DodopalResponse<List<TraTransactionBean>> respon = traTransactionService.exportTransaction(traQuery);
        List<TraTransactionBean> list = respon.getResponseEntity();
        
        int resultSize = list.size();
        if(resultSize > 5000) {
           // logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
            rep.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return rep;
        }
        String sheetName = new String("异常交易流水表");
        
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
