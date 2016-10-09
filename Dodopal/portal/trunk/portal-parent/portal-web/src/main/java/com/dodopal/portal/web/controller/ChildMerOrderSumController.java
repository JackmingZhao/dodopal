package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.ChildRechargeOrderSumBean;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.query.ChildRechargeOrderSumQuery;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.service.ProductOrderService;

/**
 * 子商户业务订单汇总--一卡通充值
 * @author xiongzhijing@dodopal.com
 */

@Controller
@RequestMapping("/childMerProductOrder")
public class ChildMerOrderSumController extends CommonController {
    //private final static Logger log = LoggerFactory.getLogger(ChildMerOrderSumController.class);

    @Autowired
    ProductOrderService productOrderService;

    @RequestMapping("/childMerProductOrderSum")
    public ModelAndView childMerProductOrderSum(Model model,HttpServletRequest request) {
        model.addAttribute("orderDateStart", DateUtils.getCurrentDateSub(-7, DateUtils.DATE_SMALL_STR));
        model.addAttribute("orderDateEnd", DateUtils.getCurrDate(DateUtils.DATE_SMALL_STR));
        return new ModelAndView("merchant/childMerchantOrderSum/childMerOrderSum");
    }


    //查询 子商户一卡通充值  业务订单汇总 信息
    @RequestMapping("/findRechargeForChildOrderSum")
    public @ResponseBody DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>> findRechargeForChildOrderSum(HttpServletRequest request, @RequestBody ChildRechargeOrderSumQuery query) {
        DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>> response = new DodopalResponse<DodopalDataPage<ChildRechargeOrderSumBean>>();
        try {
            String merCode = getCurrentMerchantCode(request.getSession());
            query.setMerCode(merCode);
            response = productOrderService.queryRechargeForChildOrderSum(query);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    //导出
    @RequestMapping("/exportRechargeForChildOrderSum")
    public @ResponseBody DodopalResponse<String> exportRechargeForChildOrderSum(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ChildRechargeOrderSumQuery queryDTO = new ChildRechargeOrderSumQuery();
        try {
            //商户编号
            String merCode = getCurrentMerchantCode(request.getSession());
            //子商户名称
            String merName = request.getParameter("merName");
            //充值订单状态
            String proOrderState = request.getParameter("proOrderState");
            //开始日期
            String startDate = request.getParameter("startDate");
            //结束日期
            String endDate = request.getParameter("endDate");

            queryDTO.setMerCode(merCode);
            queryDTO.setMerName(merName);
            queryDTO.setProOrderState(proOrderState);
            queryDTO.setStartDate(startDate);
            queryDTO.setEndDate(endDate);

            rep = productOrderService.excelExport(request,response, queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
    
    
    
    

    //查询 子商户一卡通充值  业务订单汇总 详细信息
    @RequestMapping("/findChildOrderSumDetail")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductOrderBean>> findChildOrderSumDetail(HttpServletRequest request, @RequestBody ProductOrderQueryBean query) {
        DodopalResponse<DodopalDataPage<ProductOrderBean>> response = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
        try {
            response = productOrderService.findRechargeProductOrderByPage(query);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    

    // 业务订单汇总 详细信息 导出 
    @RequestMapping("/exportChildOrderSumDetail")
    public @ResponseBody DodopalResponse<String> exportChildOrderSumDetail(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProductOrderQueryBean queryDTO = new ProductOrderQueryBean();
        try {
            //商户编号
            String merCode = request.getParameter("merCodeDetail");
            //商户名称
            String merName = request.getParameter("merNameDetail");
            //开始时间
            String orderDateStart = request.getParameter("tradeDateStartDetail");
            //结束时间
            String orderDateEnd = request.getParameter("tradeDateEndDetail");
            //订单状态
            String  proOrderState = request.getParameter("orderStateDetail");
            //pos编号
//            String posCode = request.getParameter("orderPosCodeDetail");
//            //交易日期
//            String prdOrderDay = request.getParameter("tradeDateDetail");
//            //城市名称
//            String cityName = request.getParameter("cityNameDetail");

            queryDTO.setMerCode(merCode);
            queryDTO.setMerName(merName);
            queryDTO.setOrderDateStart(DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR));
            queryDTO.setOrderDateEnd(DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR));
            queryDTO.setProOrderState(proOrderState);
            //queryDTO.setPrdOrderDay(prdOrderDay);
            //queryDTO.setCityName(cityName);
            rep = productOrderService.excelExportSumDetail(request,response, queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
}
