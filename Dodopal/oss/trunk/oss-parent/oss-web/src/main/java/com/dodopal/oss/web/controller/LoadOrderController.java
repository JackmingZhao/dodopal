package com.dodopal.oss.web.controller;

import java.util.ArrayList;
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

import com.dodopal.api.product.dto.query.LoadOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.LoadOrderBean;
import com.dodopal.oss.business.service.LoadOrderService;

/**
 * 圈存订单
 */
@Controller
@RequestMapping("/product")
public class LoadOrderController {

    private Logger logger = LoggerFactory.getLogger(LoadOrderController.class);
    
    @Autowired
    LoadOrderService loadOrderService;
    
    @Autowired
    private ExpTempletService expTempletService;

    @RequestMapping("/loadOrder/icdcCharge")
    public ModelAndView cardEncryptMgmt(HttpServletRequest request) {
        return new ModelAndView("product/loadOrder/icdcCharge");
    }

    /**
     * 6.4  查询公交卡充值圈存订单
     */
    @RequestMapping("/loadOrder/findLoadOrders")
    public @ResponseBody DodopalResponse<DodopalDataPage<LoadOrderBean>> findLoadOrders(HttpServletRequest request, @RequestBody LoadOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<LoadOrderBean>> response = new DodopalResponse<DodopalDataPage<LoadOrderBean>>();
        try {
            response = loadOrderService.findLoadOrders(queryDto);
        } catch (Exception e) {
            logger.error("查询公交卡充值圈存订单时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    /**
     * 查询公交卡充值圈存订单 查看详情
     */
    @RequestMapping("/loadOrder/viewLoadOrder")
    public @ResponseBody DodopalResponse<LoadOrderBean> viewLoadOrder(HttpServletRequest request, @RequestBody String orderNum) {
        DodopalResponse<LoadOrderBean> response = new DodopalResponse<LoadOrderBean>();
        try {
            DodopalResponse<LoadOrderBean> orders = loadOrderService.findLoadOrderByOrderNum(orderNum);
            if (ResponseCode.SUCCESS.equals(orders.getCode())) {
                response.setResponseEntity(orders.getResponseEntity());
            }
            response.setCode(orders.getCode());
        } catch (Exception e) {
            logger.error("查询公交卡充值圈存订单时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping("/loadOrder/exportLoadOrder")
    public  @ResponseBody DodopalResponse<String> exportLoadOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        LoadOrderQueryDTO queryDto = new LoadOrderQueryDTO();
        try {
        String orderNum = request.getParameter("orderNum");
        String sourceOrderNum = request.getParameter("sourceOrderNum");
        String cardNum = request.getParameter("cardNum");
        String orderStatus = request.getParameter("orderStatus");
        String yktCode = request.getParameter("yktCode");
        String merchantName = request.getParameter("merchantName");
        queryDto.setOrderNum(orderNum);
        queryDto.setSourceOrderNum(sourceOrderNum);
        queryDto.setCardNum(cardNum);
        queryDto.setMerchantName(merchantName);
        queryDto.setOrderStatus(orderStatus);
        queryDto.setYktCode(yktCode);
        
        int exportMaxNum = 5000;
        queryDto.setPage(new PageParameter(1, exportMaxNum)); 
        List<LoadOrderBean> list = new ArrayList<LoadOrderBean>();
        DodopalResponse<List<LoadOrderBean>> respon = loadOrderService.findLoadOrdersExport(queryDto);
        if(ResponseCode.SUCCESS.equals(respon.getCode())){
            list = respon.getResponseEntity();
        }
        
        int resultSize = list.size();
        if(resultSize > 5000) {
            rep.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
            return rep;
        }
        String sheetName = new String("圈存订单");
        
        Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
        List<String> lastLine = new ArrayList<String>();
        String resultCode = ExcelUtil.excelExportLastLine(request,response, list, col, sheetName,lastLine);
        
        rep.setCode(resultCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
        }
      
        return rep;
    }  
}
