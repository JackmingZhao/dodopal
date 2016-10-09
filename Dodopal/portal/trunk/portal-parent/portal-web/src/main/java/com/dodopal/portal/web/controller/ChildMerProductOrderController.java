package com.dodopal.portal.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProductOrderBean;
import com.dodopal.portal.business.bean.ProductOrderDetailBean;
import com.dodopal.portal.business.bean.query.ProductOrderQueryBean;
import com.dodopal.portal.business.service.ProductOrderService;

@Controller
@RequestMapping("/childMerProductOrder")
public class ChildMerProductOrderController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(ChildMerProductOrderController.class);

    @Autowired
    ProductOrderService productOrderService;
    /**************************************************** 子商户业务订单管理开始 *****************************************************/
    @RequestMapping("/childMerProductOrderMgr")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("merchant/childmerchantProductOrder/childMerProductOrder");
    }
    
    /**
     * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     * @param request
     * @param prdOrderQuery
     * @return
     */
     @RequestMapping(value = "/findChildMerProductOrderByPage", method = {RequestMethod.GET, RequestMethod.POST})
     public @ResponseBody DodopalResponse<DodopalDataPage<ProductOrderBean>> findChildMerProductOrderByPage(HttpServletRequest request,@RequestBody ProductOrderQueryBean prdOrderQuery) {
         log.info("protal input ProductOrderController prdOrderQuery:"+prdOrderQuery);
         DodopalResponse<DodopalDataPage<ProductOrderBean>> response = new DodopalResponse<DodopalDataPage<ProductOrderBean>>();
         prdOrderQuery.setMerCode(super.getCurrentMerchantCode(request.getSession()));
         try {
             response = productOrderService.findChildMerProductOrderByPage(prdOrderQuery);
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
     @RequestMapping("/findChildMerProductOrderByCode")
     public @ResponseBody DodopalResponse<ProductOrderDetailBean> findChildMerProductOrderByCode(Model model, HttpServletRequest request, @RequestBody String proOrderNum) {
         log.info("protal input ProductOrderController proOrderNum:"+proOrderNum);
         DodopalResponse<ProductOrderDetailBean> response = new DodopalResponse<ProductOrderDetailBean>();
         try {
           response = productOrderService.findChildMerProductOrderByCode(proOrderNum);
         }
         catch (Exception e) {
             e.printStackTrace();
             response.setCode(ResponseCode.PRODUCT_ORDER_FIND_BY_CODE_ERR);
         }
         return response;
     }

     /********************获取公交卡充值订单状态Start*****************************************/
     @RequestMapping("/findProductOrderStatus")
     public @ResponseBody List<DdicVo> findProductOrder(Model model, HttpServletRequest request) {
         List<DdicVo> list = new ArrayList<DdicVo>();
         for(RechargeOrderStatesEnum status : RechargeOrderStatesEnum.values()) {
             DdicVo ddic = new DdicVo();
             ddic.setCode(status.getCode());
             ddic.setName(status.getName());
             list.add(ddic);
         }
         return list;
     }
     /********************获取公交卡充值订单状态end*****************************************/
     
     
     /********************查询获取公交卡充值订单导出Start*****************************************/
     @RequestMapping("/excelProductOrder")
     public @ResponseBody DodopalResponse<String> excelProductOrder(Model model, HttpServletRequest request, HttpServletResponse response) {
         DodopalResponse<String> rep = new DodopalResponse<String>();
         ProductOrderQueryDTO prdOrderQuery = new ProductOrderQueryDTO();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
         //日期范围
         String orderDateStart = request.getParameter("orderDateStart");
         String orderDateEnd = request.getParameter("orderDateEnd");
          //订单编号
         String proOrderNum = request.getParameter("proOrderNum");
         //城市
         String cityName = request.getParameter("cityName");
         //金额范围
         String txnAmtStart = request.getParameter("txnAmtStart");
         String txnAmtEnd = request.getParameter("txnAmtEnd");
         //POS号
         String posCode = request.getParameter("posCode");
         //商户名称
         String merName= request.getParameter("merName");
         //订单状态
         String proOrderState = request.getParameter("proOrderState");
         try {
             if (proOrderNum != null && proOrderNum != "") {
                 prdOrderQuery.setProOrderNum(proOrderNum);
             }
             if (orderDateStart != null && orderDateStart != "") {
                 prdOrderQuery.setOrderDateStart(sdf.parse(orderDateStart));
             }
             if (orderDateEnd != null && orderDateEnd != "") {
                 prdOrderQuery.setOrderDateEnd(sdf.parse(orderDateEnd));
             }
             if (cityName != null && cityName != "") {
                 prdOrderQuery.setCityName(cityName);
             }
             if (txnAmtStart != null && txnAmtStart != "") {
                 prdOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart)*100).toString());
             }
             if (txnAmtEnd != null && txnAmtEnd != "") {
                 prdOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd)*100).toString());
             }
             if (posCode != null && posCode != "") {
                 prdOrderQuery.setPosCode(posCode);
             }
             if (merName != null && merName != "") {
                 prdOrderQuery.setMerName(merName);
             }
             if (RechargeOrderStatesEnum.checkCodeExist(proOrderState)) {
                 prdOrderQuery.setProOrderState(proOrderState);
             }
             prdOrderQuery.setMerCode(super.getCurrentMerchantCode(request.getSession()));
             rep = productOrderService.childProductOrderExcelExport(response, prdOrderQuery);
             
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         return rep;
     }
     /********************查询获取公交卡充值订单导出end*****************************************/
}
