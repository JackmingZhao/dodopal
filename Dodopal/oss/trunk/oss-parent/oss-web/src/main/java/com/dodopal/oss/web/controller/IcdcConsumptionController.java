package com.dodopal.oss.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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

import com.dodopal.api.product.dto.ProductConsumerOrderForExport;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PurchaseOrderRedordStatesEnum;
import com.dodopal.common.enums.PurchaseOrderStatesEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.ProductConsumeOrder;
import com.dodopal.oss.business.bean.ProductConsumeOrderDetail;
import com.dodopal.oss.business.bean.query.ProductConsumeOrderQuery;
import com.dodopal.oss.business.service.IcdcPrdService;

/**
 * 一卡通消费收单
 * @author hxc
 *
 */
@Controller
@RequestMapping("/product/buscardbusiness")
public class IcdcConsumptionController extends CommonController{
	
    private final static Logger log = LoggerFactory.getLogger(IcdcPrdController.class);
    
    @Autowired
    private IcdcPrdService icdcPrdService;

    @Autowired
    private ExpTempletService expTempletService;
	    
    /**
     * 主界面
     * @param request
     * @return
     */
    @RequestMapping("/icdcAcq")
    public ModelAndView basic(HttpServletRequest request) {
        return new ModelAndView("product/buscardbusiness/icdcAcq");
    }
    
    
    /**
     * 收单记录 查询 
     * @param request
     * @param prdOrderQuery
     * @return
     */
     @RequestMapping("/findProductConsumeOrder")
     public @ResponseBody DodopalResponse<DodopalDataPage<ProductConsumeOrder>> findProductConsumeOrderByPage(HttpServletRequest request,@RequestBody ProductConsumeOrderQuery prdConsumeOrderQuery) {
         DodopalResponse<DodopalDataPage<ProductConsumeOrder>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrder>>();
         try {
             response = icdcPrdService.findProductConsumeOrderByPage(prdConsumeOrderQuery);
         }
         catch (Exception e) {
             e.printStackTrace();
             response.setCode(ResponseCode.PRODUCT_ORDER_FIND_ERR);
         }
         return response;
     }
     
     
     /**
      * 一卡通消费  收单记录查询   记录详情查看
      * @param request
      * @param orderNum
      * @return
      */
     @RequestMapping("/findProductConsumeOrderByCode")
     public @ResponseBody DodopalResponse<ProductConsumeOrderDetail> findProductConsumeOrderByCode(HttpServletRequest request, @RequestBody String orderNum) {
         log.info("IcdcConsumptionController findProductConsumeOrderByCode orderNum:"+orderNum);
         DodopalResponse<ProductConsumeOrderDetail> response = new DodopalResponse<ProductConsumeOrderDetail>();
         try {
           response = icdcPrdService.findProductConsumeOrderDetails(orderNum);
         }
         catch (Exception e) {
             e.printStackTrace();
             response.setCode(ResponseCode.PORTAL_PRODUCT_CONSUME_ORDER_FIND_ERR);
         }
         return response;
     }
     
     /**
      *查询一卡通消费   收单记录导出 
      * @param model
      * @param request
      * @param response
      * @return
      */
     @RequestMapping("/excelProductConsumeOrder")
     public @ResponseBody DodopalResponse<String> excelProductConsumeOrder(HttpServletRequest request, HttpServletResponse response) {
         DodopalResponse<String> exportResponse = new DodopalResponse<String>();
         
         ProductConsumeOrderQuery prdConsumeOrderQuery = new ProductConsumeOrderQuery();
         String orderNum =request.getParameter("orderNum");
         String states = request.getParameter("states");
         String innerStates = request.getParameter("innerStates");
         String orderDateStart = request.getParameter("orderDateStart");
         String orderDateEnd = request.getParameter("orderDateEnd");
         String cardNum = request.getParameter("cardNum");
         String merName = request.getParameter("merName");
         String merType = request.getParameter("merType");
         String yktCode = request.getParameter("yktCode");
         String txnAmtStart = request.getParameter("txnAmtStart");
         String txnAmtEnd = request.getParameter("txnAmtEnd");
         String posCode = request.getParameter("posCode");
         String suspiciouStates = request.getParameter("suspiciouStates");
         
         try {
             if (StringUtils.isNotBlank(orderDateStart)) {
                 prdConsumeOrderQuery.setOrderDateStart(new SimpleDateFormat("yyyy-MM-dd").parse(orderDateStart));
             }
             if (StringUtils.isNotBlank(orderDateEnd)) {
                 prdConsumeOrderQuery.setOrderDateEnd(new SimpleDateFormat("yyyy-MM-dd").parse(orderDateEnd));
             }
             if (StringUtils.isNotBlank(merName)) {
                 prdConsumeOrderQuery.setMerName(merName);
             }
             if (StringUtils.isNotBlank(merType)) {
                 prdConsumeOrderQuery.setCustomerType(merType);
             }
             if (StringUtils.isNotBlank(yktCode)) {
                 prdConsumeOrderQuery.setYktCode(yktCode);
             }
             if (StringUtils.isNotBlank(txnAmtStart)) {
                 prdConsumeOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart)*100).toString());
             }
             if (StringUtils.isNotBlank(txnAmtEnd)) {
                 prdConsumeOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd)*100).toString());
             }
             if (StringUtils.isNotBlank(posCode)) {
                 prdConsumeOrderQuery.setPosCode(posCode);
             }
             if (StringUtils.isNotBlank(orderNum)) {
                 prdConsumeOrderQuery.setOrderNum(orderNum);
             }
             if (StringUtils.isNotBlank(cardNum)) {
                 prdConsumeOrderQuery.setCardNum(cardNum);
             }
             if (StringUtils.isNotBlank(suspiciouStates)) {
                 prdConsumeOrderQuery.setSuspiciouStates(suspiciouStates);
             }
             if (PurchaseOrderStatesEnum.checkCodeExist(states)) {
                 prdConsumeOrderQuery.setStates(states);
             }
             if (PurchaseOrderRedordStatesEnum.checkCodeExist(innerStates)) {
                 prdConsumeOrderQuery.setInnerStates(innerStates);
             }

             DodopalResponse<List<ProductConsumerOrderForExport>> list = icdcPrdService.getConsumerOrderListForExportExcel(prdConsumeOrderQuery);
             exportResponse.setCode(list.getCode());
             
             if (!ResponseCode.SUCCESS.equals(list.getCode())) {
                 return exportResponse;
             }
             List<ProductConsumerOrderForExport>  listExport = list.getResponseEntity();
             
             String sheetName = new String("一卡通消费订单信息");
             
             Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
             String resultCode = ExcelUtil.excelExport(request, response, listExport, col, sheetName);
             
             exportResponse.setCode(resultCode);
             
             return exportResponse;
         }
         catch (Exception e) {
             log.error(e.getMessage(), e);
             exportResponse = new DodopalResponse<String>();
             exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
             exportResponse.setMessage(e.getMessage());
         }
         return exportResponse;
     }
}
