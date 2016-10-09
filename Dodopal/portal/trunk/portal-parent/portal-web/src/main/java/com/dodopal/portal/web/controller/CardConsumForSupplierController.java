package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.TransactionDetailsBean;
import com.dodopal.portal.business.bean.YktCardConsumStatisticsBean;
import com.dodopal.portal.business.bean.query.RechargeStatisticsYktQuery;
import com.dodopal.portal.business.service.CardConsumForSupplierService;
import com.dodopal.portal.business.service.ManagementForSupplierService;

@Controller
@RequestMapping("/prvd")
public class CardConsumForSupplierController extends CommonController{
    
    @Autowired
    private CardConsumForSupplierService cardConsumSupplierService;
    @Autowired
    private ManagementForSupplierService managementForSupplierService;
    
    @RequestMapping("/cardConsumForSupplier")
    public ModelAndView ddpCard(ModelMap modelMap, HttpServletRequest request) {
    	modelMap.put("merName", StringUtils.isBlank(request.getParameter("merName")) ? "" : request.getParameter("merName"));
        modelMap.put("bind", StringUtils.isBlank(request.getParameter("bind")) ? "" : request.getParameter("bind"));
        modelMap.put("proCode", StringUtils.isBlank(request.getParameter("proCode")) ? "" : request.getParameter("proCode"));
        modelMap.put("_pageSize", StringUtils.isBlank(request.getParameter("_pageSize")) ? "" : request.getParameter("_pageSize"));
        modelMap.put("_pageNo", StringUtils.isBlank(request.getParameter("_pageNo")) ? "" : request.getParameter("_pageNo"));
        modelMap.put("flag", StringUtils.isBlank(request.getParameter("flag")) ? "" : request.getParameter("flag"));
        return new ModelAndView("prvd/cardConsumForSupplier");
    }
    //一卡通消费详情
   @RequestMapping("/cardConsumDetailForSupplier")
   public ModelAndView SupplierMerchantPos(ModelMap model,HttpServletRequest request) {
	String merName = request.getParameter("merName");
   	String bind = request.getParameter("bind");
    String proCode = request.getParameter("proCode");
   	String _pageSize = request.getParameter("pageSize");
   	String _pageNo = request.getParameter("pageNo");
   	model.put("bind", bind);
   	model.put("yktCode", getYktCode(request.getSession()));
   	model.put("_pageSize", _pageSize);
   	model.put("_pageNo", _pageNo);
    model.put("proCode", proCode);
    model.put("merName", merName);
    model.put("orderDateStart", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
    model.put("orderDateEnd", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
   return new ModelAndView("prvd/cardConsumDetailForSupplier");
   }
    @RequestMapping("/findCardConsumForSupplier")
    public @ResponseBody DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> queryCardConsumForSupplier(HttpServletRequest request,@RequestBody RechargeStatisticsYktQuery query){
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>>();
        String yktCode = getYktCode(request.getSession());
        query.setYktCode(yktCode);
//        String cityName = getCurrentCityName(request.getSession());
//        query.setCityName(cityName);
        try {
            response = cardConsumSupplierService.queryCardConsumForSupplier(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        
        return response;
    }
    //导出
    @RequestMapping("/exportCardConsumForSupp")
    public @ResponseBody DodopalResponse<String>  exportCardConsumList(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        RechargeStatisticsYktQueryDTO queryDTO = new RechargeStatisticsYktQueryDTO();
        String yktCode = getYktCode(request.getSession());
        
        String proCode = request.getParameter("proCode");
        String merName = request.getParameter("merName");
        String bind = request.getParameter("bind");
         //城市名称
       // String cityName = getCurrentCityName(request.getSession());
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        queryDTO.setProCode(proCode);
        queryDTO.setYktCode(yktCode);
        //queryDTO.setCityName(cityName);
        queryDTO.setMerName(merName);
        queryDTO.setStratDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setBind(bind);
        
        try {
            rep = cardConsumSupplierService.exportCardConsumForSupp(request,response,queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
    //一卡通消费详情查询
    @RequestMapping("/findCardConsumDetails")
    public @ResponseBody DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumDetails(HttpServletRequest request,@RequestBody YktCardConsumTradeDetailQueryDTO queryDTO){
        DodopalResponse<DodopalDataPage<TransactionDetailsBean>> response = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
        String yktCode = getYktCode(request.getSession());
        queryDTO.setYktCode(yktCode);
//        String cityName = getCurrentCityName(request.getSession());
//        queryDTO.setCityName("西安");
        try {
            response = managementForSupplierService.queryCardConsumDetails(queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    @RequestMapping("/exportCardConsumDetailsForSupp")
    public @ResponseBody DodopalResponse<String>  exportCardConsumDetailsForSupp(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        YktCardConsumTradeDetailQueryDTO queryDTO = new YktCardConsumTradeDetailQueryDTO();
        String proCode = request.getParameter("proCode");
        String merCode = request.getParameter("merCode");
        String orderNo = request.getParameter("proOrderNum");
        String startDate = request.getParameter("orderDateStart");
        String endDate = request.getParameter("orderDateEnd");
        String merName = request.getParameter("merName");
       // String cityName = getCurrentCityName(request.getSession());
        String yktCode = getYktCode(request.getSession());
       // queryDTO.setCityName(cityName);
        queryDTO.setYktCode(yktCode);
        queryDTO.setOrderNo(orderNo);
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setProCode(proCode);
        queryDTO.setMerCode(merCode);
        queryDTO.setMerName(merName);
        rep = cardConsumSupplierService.exportCardConsumDetailsForSupp(request,response,queryDTO);
        return rep;
    }
    
   //业务订单汇总一卡通消费
    @RequestMapping("/cardConsumCollect")
    public ModelAndView cardConsumCollect(Model model, HttpServletRequest request) {
    	model.addAttribute("orderDateStart", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
        model.addAttribute("orderDateEnd", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
        return new ModelAndView("merchant/childmerchantProCollect/cardConsumCollect");
    }
    //业务订单汇总一卡通消费详情
//    @RequestMapping("/cardConsumCollectDetails")
//    public ModelAndView cardConsumCollectDetails(Model model,HttpServletRequest request) {
//        String proCode = request.getParameter("proCode");
//        String merName = request.getParameter("merName");
//        String orderDate  = request.getParameter("orderDate");
//        String cityName = request.getParameter("cityName");
//        model.addAttribute("proCode", proCode);
//        model.addAttribute("merName", merName);
//        model.addAttribute("orderDate", orderDate);
//        model.addAttribute("cityName", cityName);
//        return new ModelAndView("merchant/childmerchantProCollect/cardConsumCollectDetails");
//    }
    
    
    //一卡通消费业务订单汇总
    @RequestMapping("/findCardConsumCollect")
    public @ResponseBody DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> findCardConsumCollect(HttpServletRequest request,@RequestBody RechargeStatisticsYktQuery query){
        DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>> response = new DodopalResponse<DodopalDataPage<YktCardConsumStatisticsBean>>();
       // String cityName = getCurrentCityName(request.getSession());
        String parentCode = getCurrentMerchantCode(request.getSession());
        //query.setCityName(cityName);
        query.setParentCode(parentCode);
        try {
            response = cardConsumSupplierService.findCardConsumCollectByPage(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        
        return response;
    }
    
  //一卡通消费详情查询
    @RequestMapping("/findCardConsumCollectDetails")
    public @ResponseBody DodopalResponse<DodopalDataPage<TransactionDetailsBean>> queryCardConsumCollectDetails(HttpServletRequest request,@RequestBody YktCardConsumTradeDetailQueryDTO queryDTO){
        DodopalResponse<DodopalDataPage<TransactionDetailsBean>> response = new DodopalResponse<DodopalDataPage<TransactionDetailsBean>>();
        try {
            response = managementForSupplierService.queryCardConsumCollectDetails(queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
  //业务订单汇总一卡通消费导出
    @RequestMapping("/exportCardConsumCollect")
    public @ResponseBody DodopalResponse<String>  exportCardConsumCollect(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        RechargeStatisticsYktQueryDTO queryDTO = new RechargeStatisticsYktQueryDTO();
        String parentCode = getCurrentMerchantCode(request.getSession());
        String states = request.getParameter("proOrderState");
        String merName = request.getParameter("merName");
         //城市名称
        //query.setCityName(cityName);
//        String cityName = getCurrentCityName(request.getSession());
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(parentCode!=null && parentCode!=""){
        	queryDTO.setParentCode(parentCode);
        }
        if(states!=null && states!=""){
        	queryDTO.setStates(states);
        }
        if(merName!=null && merName!=""){
        	 queryDTO.setMerName(merName);
        }
        if(startDate!=null && startDate!=""){
        	queryDTO.setStratDate(startDate);
        }
        if(endDate!=null && endDate!=""){
        	 queryDTO.setEndDate(endDate);
        }
//        queryDTO.setCityName(cityName);
        queryDTO.setPage(new PageParameter(1,100000));
        try {
            rep = cardConsumSupplierService.exportCardConsumCollect(request, response, queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
    
    //业务订单汇总一卡通消费详细导出
    @RequestMapping("/exportCardConsumCollectDetails")
    public @ResponseBody DodopalResponse<String>  exportCardConsumCollectDetails(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        YktCardConsumTradeDetailQueryDTO queryDTO = new YktCardConsumTradeDetailQueryDTO();
        String states = request.getParameter("statesDetail");
        //String cityName = request.getParameter("cityName");
        String startDate = request.getParameter("startDateDetail");
        String endDate = request.getParameter("endDateDetail");
        String merName = request.getParameter("merNameDetail");
        queryDTO.setMerName(merName);
        queryDTO.setStates(states);
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        rep = managementForSupplierService.exportCardConsumCollectDetails(request,response,queryDTO);
        return rep;
    }

}
