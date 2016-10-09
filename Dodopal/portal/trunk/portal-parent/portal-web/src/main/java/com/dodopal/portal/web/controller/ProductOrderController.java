package com.dodopal.portal.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.portal.business.service.ProductOrderService;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Controller
@RequestMapping("/productOrder")
public class ProductOrderController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(ProductOrderController.class);

    @Autowired
    ProductOrderService productOrderService;
    
//    @RequestMapping("/productOrderMgr")
//    public ModelAndView verifiedMgmt(HttpServletRequest request) {
//        return new ModelAndView("productOrder/productOrderMgmt");
//    }
   
    /********************卡服务订单标准查询start*****************************************/
    @RequestMapping("/productOrderMgr")
    public String toCardRecharge(HttpServletRequest request,RedirectAttributes attr) {
        PortalUserDTO dto = super.getLoginUser(request.getSession());
        Map map = new HashMap();
        if(log.isDebugEnabled()){
            log.info("查询卡服务充值订单");
        }
        attr.addAttribute("userName", dto.getMerUserName());  
        attr.addAttribute("userType", dto.getMerType());
        String proUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.FACADE_PRODUCT_URL);
        String [] urlArr = proUrl.split("/product-web/");
        return "redirect:"+urlArr[0]+"/product-web/productOrder/productOrderMgr";
    }
    /********************卡服务订单标准查询start*****************************************/
    
}
