package com.dodopal.oss.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.service.LoadOrderService;

@Controller
@RequestMapping("/loadOrder")
public class BookLoadOrderController {
    
    @Autowired
    LoadOrderService  loadOrderService;
    
    @RequestMapping("/loadOrderView")
    public ModelAndView cardEncryptMgmt(HttpServletRequest request) {
        return new ModelAndView("loadOrder/loadOrderMgmt");
    }
    
    
    @RequestMapping("/findLoadOrder")
    public @ResponseBody DodopalResponse<String> findLoadOrder(HttpServletRequest request) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response= loadOrderService.findLoadOrder();
        return response;
    }
    
    
    @RequestMapping("/bookLoadOrder")
    public @ResponseBody DodopalResponse<String> bookLoadOrder(HttpServletRequest request, @RequestBody LoadOrderRequestDTO loadOrderRequestDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response = loadOrderService.bookLoadOrder(loadOrderRequestDTO);
        return response;
    }
   
    
    //6.3   根据外接商户的订单号查询圈存订单状态
    @RequestMapping("/findLoadOrderStatus")
    public @ResponseBody DodopalResponse<String> findLoadOrderStatus(HttpServletRequest request, @RequestBody LoadOrderRequestDTO loadOrderRequestDTO) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response = loadOrderService.findLoadOrderStatus(loadOrderRequestDTO);
        return response;
    }
}
