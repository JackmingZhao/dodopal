package com.dodopal.portal.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.MerchantExtendBean;
import com.dodopal.portal.business.bean.ProxyCardAddBean;
import com.dodopal.portal.business.bean.ProxyCountAllByIDBean;
import com.dodopal.portal.business.bean.ProxyOffLineOrderTbBean;
import com.dodopal.portal.business.bean.query.ProxyCardAddQuery;
import com.dodopal.portal.business.bean.query.ProxyOffLineOrderTbQuery;
import com.dodopal.portal.business.service.MerchantService;
import com.dodopal.portal.business.service.ProxyCardAddService;

@Controller
@RequestMapping("/tran")
public class ProxyCardAddController extends CommonController {

    @Autowired
    ProxyCardAddService proxyCardAddService;

    @Autowired
    MerchantService merchantService;

    @RequestMapping("/cardTradeList")
    public ModelAndView showCardTradeList(Model model, HttpServletRequest request) {
        String merCode = getCurrentMerchantCode(request.getSession());
        String proxyid = "";
        try {
            
            DodopalResponse<MerchantExtendBean> rep = merchantService.findMerchantExtend(merCode);
            if (rep.isSuccessCode()) {
                proxyid = rep.getResponseEntity().getOldMerchantId();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("proxyid", proxyid);
        return new ModelAndView("tranRecord/proxyCardAdd");
    }

    @RequestMapping("/offLineTradeList")
    public ModelAndView showOffLineTradeList(Model model, HttpServletRequest request) {
        String merCode = getCurrentMerchantCode(request.getSession());
        String proxyid = "";
        try {
            DodopalResponse<MerchantExtendBean> rep = merchantService.findMerchantExtend(merCode);
            if (rep.isSuccessCode()) {
                proxyid = rep.getResponseEntity().getOldMerchantId();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("proxyid", proxyid);
        return new ModelAndView("tranRecord/offLineTradeList");
    }

    //查询（分页） 城市一卡通充值记录
    @RequestMapping("/findCardTradeListByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProxyCardAddBean>> findCardTradeListByPage(HttpServletRequest request, @RequestBody ProxyCardAddQuery proxyCardAddQuery) {
        DodopalResponse<DodopalDataPage<ProxyCardAddBean>> response = new DodopalResponse<DodopalDataPage<ProxyCardAddBean>>();
        try {
           
            if(checkDate(proxyCardAddQuery)){
               response = proxyCardAddService.cardTradeList(getProxyCardAddQuery(proxyCardAddQuery));
            }else{
                response.setCode(ResponseCode.PORTAL_TIME_ERR);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }



    //查询  城市一卡通充值记录 统计
    @RequestMapping("/findCardTradeListCount")
    public @ResponseBody DodopalResponse<ProxyCountAllByIDBean> findCardTradeListCount(HttpServletRequest request, @RequestBody ProxyCardAddQuery proxyCardAddQuery) {
        DodopalResponse<ProxyCountAllByIDBean> response = new DodopalResponse<ProxyCountAllByIDBean>();
        try {
            
            if(checkDate(proxyCardAddQuery)){
               response = proxyCardAddService.findCardTradeListCount(getProxyCardAddQuery(proxyCardAddQuery));
            }
       

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    
    //导出 城市一卡通充值记录
    @RequestMapping("/proxyCardAddExport")
    public @ResponseBody DodopalResponse<String> proxyCardAddExport(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            ProxyCardAddQuery queryDTO = new ProxyCardAddQuery();
            queryDTO.setPosid(request.getParameter("posId"));
            queryDTO.setProxyid(request.getParameter("proxyid"));
            queryDTO.setStatus(request.getParameter("orderState"));
            queryDTO.setStartdate(request.getParameter("createDateStart"));
            queryDTO.setEnddate(request.getParameter("createDateEnd"));
            queryDTO.setCardno(request.getParameter("cardNo"));
            queryDTO.setProxyorderno(request.getParameter("orderNo"));
            queryDTO.setTimeRadio(request.getParameter("time"));
            queryDTO = getProxyCardAddQuery(queryDTO);
            int exportMaxNum = 100000;
            queryDTO.setPage(new PageParameter(1, exportMaxNum));
            DodopalResponse<DodopalDataPage<ProxyCardAddBean>> rtResponse = proxyCardAddService.cardTradeList(queryDTO);

            List<ProxyCardAddBean> proxyCardAddBeanList = new ArrayList<ProxyCardAddBean>();

            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("proxyorderno", "订单号");
            index.put("proxyname", "网点名称");
            index.put("cardno", "卡号");
            index.put("posid", "POS号");
            index.put("txndate", "交易日期");
            index.put("txntime", "交易时间");

            index.put("befsurpluslimit", "交易前剩余额度");
            index.put("txnamt", "交易金额");
            index.put("sumamt", "优惠券使用金额");
            index.put("paidamt", "实收金额");
            index.put("rebatesamt", "返利金额");
            index.put("aftsurpluslimit", "交易后剩余额度");

            index.put("status", "订单状态");
            index.put("remarks", "POS备注");

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                proxyCardAddBeanList = rtResponse.getResponseEntity().getRecords();
            }
            String code = ExcelUtil.excelExport(request, response, proxyCardAddBeanList, index, "城市一卡通充值记录");
            rep.setCode(code);
        }
        catch (HessianRuntimeException e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return rep;
    }

    //查询（分页） 城市一卡通消费记录
    @RequestMapping("/findOffLineTradeListByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>> findOffLineTradeListByPage(HttpServletRequest request, @RequestBody ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {
        DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>> response = new DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>>();
        try {
            if(checkDate(proxyOffLineOrderTbQuery)){
                response = proxyCardAddService.offLineTradeList(getProxyOffLineOrderTbQueryQuery(proxyOffLineOrderTbQuery));
            }else{
                response.setCode(ResponseCode.PORTAL_TIME_ERR);
            }
          
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

   
   

    //查询  城市一卡通消费记录 统计
    @RequestMapping("/findoffLineTradeListCount")
    public @ResponseBody DodopalResponse<ProxyCountAllByIDBean> findoffLineTradeListCount(HttpServletRequest request, @RequestBody ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {
        DodopalResponse<ProxyCountAllByIDBean> response = new DodopalResponse<ProxyCountAllByIDBean>();
        try {
            if(checkDate(proxyOffLineOrderTbQuery)){
                response = proxyCardAddService.findoffLineTradeListCount(getProxyOffLineOrderTbQueryQuery(proxyOffLineOrderTbQuery));
            }
           
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    //导出
    @RequestMapping("/offLineTradeListExport")
    public @ResponseBody DodopalResponse<String> offLineTradeListExport(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            ProxyOffLineOrderTbQuery queryDTO = new ProxyOffLineOrderTbQuery();
            queryDTO.setPosid(request.getParameter("posId"));
            queryDTO.setProxyid(request.getParameter("proxyid"));
            queryDTO.setStatus(request.getParameter("status"));
            queryDTO.setStartdate(request.getParameter("createDateStart"));
            queryDTO.setEnddate(request.getParameter("createDateEnd"));
            queryDTO.setTimeRadio(request.getParameter("time"));
            queryDTO = getProxyOffLineOrderTbQueryQuery(queryDTO);
            int exportMaxNum = 100000;
            queryDTO.setPage(new PageParameter(1, exportMaxNum));
            DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>> rtResponse = proxyCardAddService.offLineTradeList(queryDTO);
            List<ProxyOffLineOrderTbBean> proxyOffLineOrderTbBeanList = new ArrayList<ProxyOffLineOrderTbBean>();
            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("mchnitorderid", "订单号");
            index.put("checkcardno", "卡号");
            index.put("checkcardposid", "POS号");
            index.put("facevalue", "消费金额");
            index.put("sale", "折扣");
            index.put("amt", "实付金额");
            index.put("proamt", "原有金额");
            index.put("blackamt", "卡余额");
            index.put("posremark", "POS备注");
            index.put("ordertime", "交易时间");
            index.put("orderstates", "订单状态");
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                proxyOffLineOrderTbBeanList = rtResponse.getResponseEntity().getRecords();
            }
            String code = ExcelUtil.excelExport(request, response, proxyOffLineOrderTbBeanList, index, "城市一卡通消费记录");
            rep.setCode(code);
        }
        catch (HessianRuntimeException e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return rep;
    }
    
    
    
    public ProxyCardAddQuery getProxyCardAddQuery(ProxyCardAddQuery object){
        Date tDay = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String timeRadio = object.getTimeRadio();
        if (StringUtils.isNotBlank(timeRadio)) {
            if ("7".equals(timeRadio)) {
                calendar.setTime(tDay);
                calendar.add(calendar.DATE, -7);
                ((ProxyCardAddQuery) object).setStartdate(sdf.format(calendar.getTime()));
                ((ProxyCardAddQuery) object).setEnddate(sdf.format(tDay));

            } else if ("30".equals(timeRadio)) {
                calendar.setTime(tDay);
                calendar.add(calendar.MONTH, -1);
                object.setStartdate(sdf.format(calendar.getTime()));
                object.setEnddate(sdf.format(tDay));
            } else if ("90".equals(timeRadio)) {
                calendar.setTime(tDay);
                calendar.add(calendar.MONTH, -3);
                object.setStartdate(sdf.format(calendar.getTime()));
                object.setEnddate(sdf.format(tDay));
            }
        }else if(StringUtils.isBlank(object.getStartdate()) && StringUtils.isBlank(object.getEnddate())){
            object.setStartdate(sdf.format(tDay));
            object.setEnddate(sdf.format(tDay));
        }
        
        return object;
    }
 
    public ProxyOffLineOrderTbQuery getProxyOffLineOrderTbQueryQuery(ProxyOffLineOrderTbQuery object){
        Date tDay = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String timeRadio = object.getTimeRadio();
        if (StringUtils.isNotBlank(timeRadio)) {
            if ("7".equals(timeRadio)) {
                calendar.setTime(tDay);
                calendar.add(calendar.DATE, -7);
                object.setStartdate(sdf.format(calendar.getTime()));
                object.setEnddate(sdf.format(tDay));

            } else if ("30".equals(timeRadio)) {
                calendar.setTime(tDay);
                calendar.add(calendar.MONTH, -1);
                object.setStartdate(sdf.format(calendar.getTime()));
                object.setEnddate(sdf.format(tDay));
            } else if ("90".equals(timeRadio)) {
                calendar.setTime(tDay);
                calendar.add(calendar.MONTH, -3);
                object.setStartdate(sdf.format(calendar.getTime()));
                object.setEnddate(sdf.format(tDay));
            }
        }else if(StringUtils.isBlank(object.getStartdate()) && StringUtils.isBlank(object.getEnddate())){
            object.setStartdate(sdf.format(tDay));
            object.setEnddate(sdf.format(tDay));
        }
        
        
        return object;
        
    }
    
    
    
    private boolean checkDate(ProxyCardAddQuery proxyCardAddQuery) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        try {
            if(StringUtils.isNotBlank(proxyCardAddQuery.getStartdate()) && StringUtils.isNotBlank(proxyCardAddQuery.getEnddate())){
            
            Date date1 = sdf.parse(proxyCardAddQuery.getStartdate());
            Date date2 = sdf.parse(proxyCardAddQuery.getEnddate());
            if((date2.getTime()-date1.getTime())/(24*60*60*1000)>3){
               return false;
              }else{
                return true;
              }
            }else if(StringUtils.isNotBlank(proxyCardAddQuery.getStartdate()) && StringUtils.isBlank(proxyCardAddQuery.getEnddate())){
                proxyCardAddQuery.setEnddate(proxyCardAddQuery.getStartdate());
                return true;
            }else if(StringUtils.isBlank(proxyCardAddQuery.getStartdate()) && StringUtils.isNotBlank(proxyCardAddQuery.getEnddate())){
                proxyCardAddQuery.setStartdate(proxyCardAddQuery.getEnddate());
                return true;
            }else{
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
      
    }
    
    
    private boolean checkDate(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        try {
            if(StringUtils.isNotBlank(proxyOffLineOrderTbQuery.getStartdate()) && StringUtils.isNotBlank(proxyOffLineOrderTbQuery.getEnddate())){
            
            Date date1 = sdf.parse(proxyOffLineOrderTbQuery.getStartdate());
            Date date2 = sdf.parse(proxyOffLineOrderTbQuery.getEnddate());
            if((date2.getTime()-date1.getTime())/(24*60*60*1000)>3){
               return false;
              }else{
                return true;
              }
            }else if(StringUtils.isNotBlank(proxyOffLineOrderTbQuery.getStartdate()) && StringUtils.isBlank(proxyOffLineOrderTbQuery.getEnddate())){
                proxyOffLineOrderTbQuery.setEnddate(proxyOffLineOrderTbQuery.getStartdate());
                return true;
            }else if(StringUtils.isBlank(proxyOffLineOrderTbQuery.getStartdate()) && StringUtils.isNotBlank(proxyOffLineOrderTbQuery.getEnddate())){
                proxyOffLineOrderTbQuery.setStartdate(proxyOffLineOrderTbQuery.getEnddate());
                return true;
            }else{
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
