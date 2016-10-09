package com.dodopal.portal.web.controller;

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
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.PurchaseOrderBean;
import com.dodopal.portal.business.bean.RechargeOrderBean;
import com.dodopal.portal.business.bean.query.PurchaseOrderQuery;
import com.dodopal.portal.business.bean.query.RechargeOrderQuery;
import com.dodopal.portal.business.service.ProductOrderService;

@Controller
@RequestMapping("/businessOrder")
public class BusinessOrderController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(BusinessOrderController.class);
    @Autowired
    ProductOrderService orderService;
    
    @RequestMapping("/toBusinessOrderPage")
    public ModelAndView toBusinessOrderPage(HttpServletRequest request) {
        return new ModelAndView("merchant/businessOrder/cardRechargeOrderSumForNetwork");
    }
    
    @RequestMapping("/findCardRechargeOrderSum")
    public @ResponseBody DodopalResponse<RechargeOrderBean> findCardRechargeOrderSum (HttpServletRequest request, @RequestBody RechargeOrderQuery query) {
        DodopalResponse<RechargeOrderBean> response = new DodopalResponse<RechargeOrderBean> ();
        try {
            query.setMerCode(getCurrentMerchantCode(request.getSession()));
            response = orderService.sumRechargeOrder(query);
        } catch (DDPException e) {
            log.error("BusinessOrderController findCardRechargeOrderSum 查找调用产品库hessian异常");
            response.setCode(ResponseCode.PORTAL_PRODUCT_HESSIAN_ERR);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BusinessOrderController findCardRechargeOrderSum throws error",e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @RequestMapping("/findCardRechargeOrderPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<RechargeOrderBean>> findCardRechargeOrderPage (HttpServletRequest request, @RequestBody RechargeOrderQuery query) {
        DodopalResponse<DodopalDataPage<RechargeOrderBean>> response = new DodopalResponse<DodopalDataPage<RechargeOrderBean>> ();
        try {
            query.setMerCode(getCurrentMerchantCode(request.getSession()));
            response = orderService.findRechargeOrderByPage(query);
        } catch (DDPException e) {
            log.error("BusinessOrderController findCardRechargeOrderPage 查找调用产品库hessian异常");
            response.setCode(ResponseCode.PORTAL_PRODUCT_HESSIAN_ERR);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BusinessOrderController findCardRechargeOrderPage throws error",e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    
    
    @RequestMapping("/viewCardRechargeOrderPage")
    public ModelAndView viewCardRechargeOrderPage(HttpServletRequest request) {
        return new ModelAndView("merchant/businessOrder/viewCardConsumeOrderSumForNetwork");
    }
    
    
    @RequestMapping("/exportFindRechargeOrder")
    public  @ResponseBody DodopalResponse<String>  exportFindRechargeOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        RechargeOrderQuery orderQuery = new RechargeOrderQuery();
        String startDay = (String)request.getParameter("startdate");/** 交易日期开始*/
        String enddate = (String)request.getParameter("enddate");/** 交易日期结束*/
        String proOrderState = (String)request.getParameter("proOrderState");/** 充值订单状态(0：成功；1：失败；2：可疑)*/
        orderQuery.setMerCode(getCurrentMerchantCode(request.getSession()));
        if(StringUtils.isNotBlank(startDay)){
            orderQuery.setOrderDateStart(DateUtils.stringtoDate(startDay,DateUtils.DATE_SMALL_STR));
        }
        if(StringUtils.isNotBlank(enddate)){
            orderQuery.setOrderDateEnd(DateUtils.stringtoDate(enddate,DateUtils.DATE_SMALL_STR));
        }
        orderQuery.setProOrderState(proOrderState);
        dodopalResponse = orderService.exportFindRechargeOrder(request,response,orderQuery);
        return dodopalResponse;
    }
    
    @RequestMapping("/toCardConsumeOrderPage")
    public ModelAndView toCardConsumeOrderPage(HttpServletRequest request) {
        return new ModelAndView("merchant/businessOrder/cardConsumeOrderSumForNetwork");
    }
    
    
    
    @RequestMapping("/findCardConsumeOrderSum")
    public @ResponseBody DodopalResponse<PurchaseOrderBean> findCardConsumeOrderSum (HttpServletRequest request, @RequestBody PurchaseOrderQuery query) {
        DodopalResponse<PurchaseOrderBean> response = new DodopalResponse<PurchaseOrderBean> ();
        try {
            query.setMerCode(getCurrentMerchantCode(request.getSession()));
            response = orderService.sumPurchaseOrder(query);
        } catch (DDPException e) {
            log.error("BusinessOrderController findCardConsumeOrderSum 查找调用产品库hessian异常");
            response.setCode(ResponseCode.PORTAL_PRODUCT_HESSIAN_ERR);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BusinessOrderController findCardConsumeOrderSum throws error",e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @RequestMapping("/findCardConsumeOrderPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<PurchaseOrderBean>> findCardConsumeOrderPage (HttpServletRequest request, @RequestBody PurchaseOrderQuery query) {
        DodopalResponse<DodopalDataPage<PurchaseOrderBean>> response = new DodopalResponse<DodopalDataPage<PurchaseOrderBean>> ();
        try {
            query.setMerCode(getCurrentMerchantCode(request.getSession()));
            response = orderService.findPurchaseOrderByPage(query);
        } catch (DDPException e) {
            log.error("BusinessOrderController findCardConsumeOrderPage 查找调用产品库hessian异常");
            response.setCode(ResponseCode.PORTAL_PRODUCT_HESSIAN_ERR);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BusinessOrderController findCardConsumeOrderPage throws error",e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    @RequestMapping("/exportFindCardConsumeOrder")
    public  @ResponseBody DodopalResponse<String>  exportFindCardConsumeOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        PurchaseOrderQuery orderQuery = new PurchaseOrderQuery();
        String startDay = (String)request.getParameter("startdate");/** 交易日期开始*/
        String enddate = (String)request.getParameter("enddate");/** 交易日期结束*/
        String proOrderState = (String)request.getParameter("proOrderState");/** 充值订单状态(0：成功；1：失败；2：可疑)*/
        orderQuery.setMerCode(getCurrentMerchantCode(request.getSession()));
        if(StringUtils.isNotBlank(startDay)){
            orderQuery.setOrderDateStart(DateUtils.stringtoDate(startDay,DateUtils.DATE_SMALL_STR));
        }
        if(StringUtils.isNotBlank(enddate)){
            orderQuery.setOrderDateEnd(DateUtils.stringtoDate(enddate,DateUtils.DATE_SMALL_STR));
        }
        orderQuery.setProOrderState(proOrderState);
        dodopalResponse = orderService.exportPurchaseOrder(request, response, orderQuery);
        return dodopalResponse;
    }
    
}
