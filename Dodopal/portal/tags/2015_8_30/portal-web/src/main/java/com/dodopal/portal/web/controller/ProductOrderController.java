package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ProductOrderStatusEnum;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.ProductOrderDetailBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.service.ProductOrderService;

@Controller
@RequestMapping("/productOrder")
public class ProductOrderController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(ProductOrderController.class);

    @Autowired
    ProductOrderService productOrderService;
    
    @RequestMapping("/productOrderMgr")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("productOrder/productOrderMgmt");
    }
    /**
    * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
    * @param request
    * @param prdOrderQuery
    * @return
    */
    @RequestMapping(value = "/findProductOrderByPage", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductOrderBean>> findProductOrderByPage(HttpServletRequest request,@RequestBody ProductOrderQueryBean prdOrderQuery) {
        log.info("protal input ProductOrderController prdOrderQuery:"+prdOrderQuery);
        DodopalResponse<DodopalDataPage<ProductOrderBean>> response = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
        try {
            response = productOrderService.findProductOrderByPage(prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_ORDER_FIND_ERR);
        }
        return response;
    }
    /**
     * 订单查看 用户选择一条公交卡充值订单
     * @param model
     * @param request
     * @param proOrderNum
     * @return
     */
    @RequestMapping("/findProductOrderByCode")
    public @ResponseBody DodopalResponse<ProductOrderDetailBean> findProductOrderByCode(Model model, HttpServletRequest request, @RequestBody String proOrderNum) {
        log.info("protal input ProductOrderController proOrderNum:"+proOrderNum);
        DodopalResponse<ProductOrderDetailBean> response = new DodopalResponse<ProductOrderDetailBean>();
        try {
          response = productOrderService.findProductOrderByCode(proOrderNum);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_ORDER_FIND_BY_CODE_ERR);
        }
        return response;
    }

    /********************获取公交卡充值订单状态Start*****************************************/
    @RequestMapping("/findProductOrderList")
    public @ResponseBody DodopalResponse<List<ProductOrderBean>> findProductOrderList(Model model, HttpServletRequest request) {
        
        DodopalResponse<List<ProductOrderBean>> response= new DodopalResponse<List<ProductOrderBean>>();
        List<DdicVo> list = new ArrayList<DdicVo>();
        for(ProductOrderStatusEnum status : ProductOrderStatusEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return response;
    }
    /********************获取公交卡充值订单状态end*****************************************/
    
    
    /********************获取公交卡充值订单状态Start*****************************************/
    @RequestMapping("/findProductOrderStatus")
    public @ResponseBody List<DdicVo> findProductOrder(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for(ProductOrderStatusEnum status : ProductOrderStatusEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }
    /********************获取公交卡充值订单状态end*****************************************/
}
