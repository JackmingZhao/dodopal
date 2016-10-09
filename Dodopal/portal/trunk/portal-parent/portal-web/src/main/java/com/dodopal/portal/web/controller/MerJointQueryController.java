package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.query.ChargeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ConsumptionOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.ChargeOrder;
import com.dodopal.portal.business.bean.ConsumptionOrder;
import com.dodopal.portal.business.bean.ConsumptionOrderCount;
import com.dodopal.portal.business.service.MerJointQueryService;
@Controller
@RequestMapping("/merJointQuery")
public class MerJointQueryController extends CommonController{
    @Autowired
    MerJointQueryService jointQueryService;
    
    @RequestMapping("/toChargeOrderPage")
    public ModelAndView toChargeOrderPage(Model model,HttpServletRequest request) {
        return new ModelAndView("tranRecord/historyTran/merchant/rechargeOrderQuery");
    }
    
    @RequestMapping("/toConsumptionOrderPage")
    public ModelAndView toConsumptionOrderPage(Model model,HttpServletRequest request) {
        return new ModelAndView("tranRecord/historyTran/merchant/consumerOrderQuery");
    }
    
    @RequestMapping("/toConsumptionOrderCountPage")
    public ModelAndView toConsumptionOrderCountPage(Model model,HttpServletRequest request) {
        return new ModelAndView("tranRecord/historyTran/merchant/consumerOrderCount");
    }
    
    
    @RequestMapping("/findChargeOrderList")
    public  @ResponseBody DodopalResponse<DodopalDataPage<ChargeOrder>>  findChargeOrderList(HttpServletRequest request,@RequestBody ChargeOrderQueryDTO orderQuery) {
        DodopalResponse<DodopalDataPage<ChargeOrder>> dodopalResponse = new DodopalResponse<DodopalDataPage<ChargeOrder>>();
        orderQuery.setMchnitid(getCurrentMerchantCode(request.getSession()));
        dodopalResponse = jointQueryService.findChargeOrderByPage(orderQuery);
        return dodopalResponse;
    }
    
    @RequestMapping("/findConsumptionOrderList")
    public  @ResponseBody DodopalResponse<DodopalDataPage<ConsumptionOrder>>  findConsumptionOrderList(HttpServletRequest request,@RequestBody ConsumptionOrderQueryDTO orderQuery) {
        DodopalResponse<DodopalDataPage<ConsumptionOrder>> dodopalResponse = new DodopalResponse<DodopalDataPage<ConsumptionOrder>>();
        orderQuery.setMchnitid(getCurrentMerchantCode(request.getSession()));
        dodopalResponse = jointQueryService.findConsumptionOrderByPage(orderQuery);
        return dodopalResponse;
    }
    
    @RequestMapping("/findConsumptionOrderCountList")
    public  @ResponseBody DodopalResponse<DodopalDataPage<ConsumptionOrderCount>>  findConsumptionOrderCountList(HttpServletRequest request,@RequestBody ChargeOrderQueryDTO orderQuery) {
        DodopalResponse<DodopalDataPage<ConsumptionOrderCount>> dodopalResponse = new DodopalResponse<DodopalDataPage<ConsumptionOrderCount>>();
        orderQuery.setMchnitid(getCurrentMerchantCode(request.getSession()));
        dodopalResponse = jointQueryService.findConsumptionOrderCountByPage(orderQuery);
        return dodopalResponse;
    }
    
    @RequestMapping("/exportConsumptionOrderCount")
    public  @ResponseBody DodopalResponse<String>  exportConsumptionOrderCount(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        ChargeOrderQueryDTO orderQuery = new ChargeOrderQueryDTO();
        String startDay = (String)request.getParameter("startdate");
        String enddate = (String)request.getParameter("enddate");
        if(StringUtils.isNotBlank(startDay)){
            orderQuery.setStartdate(DateUtils.stringtoDate(startDay,DateUtils.DATE_SMALL_STR));
        }
        if(StringUtils.isNotBlank(enddate)){
            orderQuery.setEnddate(DateUtils.stringtoDate(enddate,DateUtils.DATE_SMALL_STR));
        }
        orderQuery.setMchnitid(getCurrentMerchantCode(request.getSession()));
        dodopalResponse = jointQueryService.exportConsumptionOrderCount(request,response,orderQuery);
        return dodopalResponse;
    }
}
