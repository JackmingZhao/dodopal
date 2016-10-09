package com.dodopal.oss.web.controller;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.ProductConsumeOrder;
import com.dodopal.oss.business.bean.ProductConsumeOrderDetail;
import com.dodopal.oss.business.bean.query.ProductConsumeOrderQuery;
import com.dodopal.oss.business.model.CrdConsumeOrderExption;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.CrdConsumeOrderExptionService;
import com.dodopal.oss.business.service.IcdcPrdService;

/**
 * 异常公交卡消费订单（查询、查看、异常处理）
 */
@Controller
@RequestMapping("/product/exceptionOrder/crdConsumeOrder")
public class IcdcConsumptionExpController extends CommonController {
	 private Logger logger = LoggerFactory.getLogger(IcdcConsumptionExpController.class);
	 @Autowired
    private IcdcPrdService icdcPrdService;
	
	@Autowired
    private CrdConsumeOrderExptionService consumeOrderExptionService;
    @Autowired
    private ExpTempletService expTempletService;
    
	@RequestMapping("init")
	public ModelAndView init(){
		 return new ModelAndView("product/exceptionOrder/crdConsumeOrder");
	}
	
    /**
     * 一卡通消费异常订单 查询分页 
     * @param request
     * @param prdOrderQuery
     * @return
     */
     @RequestMapping("/findProductConsumeOrder")
     public @ResponseBody DodopalResponse<DodopalDataPage<ProductConsumeOrder>> findProductConsumeOrderByPage(HttpServletRequest request,@RequestBody ProductConsumeOrderQuery prdConsumeOrderQuery) {
         DodopalResponse<DodopalDataPage<ProductConsumeOrder>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrder>>();
         try {
             response = icdcPrdService.findProductConsumeOrdersExptionByPage(prdConsumeOrderQuery);
         }
         catch (Exception e) {
        	 logger.error("一卡通消费异常订单列表查询出错 "+e.getMessage(), e);
             e.printStackTrace();
             response.setCode(ResponseCode.PRODUCT_ORDER_FIND_ERR);
         }
         return response;
     }
    

     /**
      * 一卡通消费异常订单 查看详情
      * @param request
      * @param orderNum
      * @return
      */
     @RequestMapping("/findProductConsumeOrderByCode")
     public @ResponseBody DodopalResponse<ProductConsumeOrderDetail> findProductConsumeOrderByCode(HttpServletRequest request, @RequestBody String orderNum) {
         DodopalResponse<ProductConsumeOrderDetail> response = new DodopalResponse<ProductConsumeOrderDetail>();
         try {
           response = icdcPrdService.findProductConsumeOrderDetails(orderNum);
         }
         catch (Exception e) {
             e.printStackTrace();
             logger.error("查看一卡通消费异常订单明细出错!"+e.getMessage(), e);
             response.setCode(ResponseCode.PORTAL_PRODUCT_CONSUME_ORDER_FIND_ERR);
         }
         return response;
     }
     
     /**
      * 一卡通消费异常订单 异常处理
      * @param request
      * @param orderNum
      * @return
      */
    @RequestMapping("exceptionHandle")
    public @ResponseBody DodopalResponse<String> exceptionHandle(HttpServletRequest request,@RequestBody CrdConsumeOrderExption consumeOrderExption){
    	DodopalResponse<String> response = new DodopalResponse<String>();
    	SysLog log = new SysLog();
        User user = generateLoginUser(request);
        String operatorName = user.getLoginName()+"（"+user.getName()+"）";
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
    	try {
			if(consumeOrderExption == null){
				response.setCode(ResponseCode.UNKNOWN_ERROR);
				response.setMessage("订单编号不存在");
			}else{
			    consumeOrderExption.setUserName(operatorName);
				consumeOrderExptionService.updateCrdConsumeOrder(consumeOrderExption);
				response.setCode(ResponseCode.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("一卡通订单异常处理出错:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            log.setStatckTrace(e.getStackTrace().toString());
		}finally{
            log.setServerName(CommonConstants.SYSTEM_NAME_OSS);
            log.setOrderNum(consumeOrderExption.getOrderNo());
            log.setTranNum("");
            if(ResponseCode.SUCCESS.equals(response.getCode())){
                log.setTradeType(0);
            }else{
                log.setTradeType(1);
            }
            log.setClassName(IcdcConsumptionExpController.class.toString());
            log.setMethodName("exceptionHandle");
            log.setInParas("一卡通订单异常充值订单编号："+consumeOrderExption.getOrderNo()+"；操作员:"+operatorName);
            log.setOutParas(JSONObject.toJSONString(response));
            log.setRespCode(response.getCode());
            log.setRespExplain(response.getMessage());
            log.setDescription("OSS系统处理状态异常的一卡通订单异常");
            log.setSource("");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd()-log.getTradeStart());
            
            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
    	return response;
    }
    
    /**
     * 一卡通消费异常订单 异常导出
     * @param request
     * @param orderNum
     * @return
     */
    @RequestMapping("/exportPrdExpOrder")
    public @ResponseBody
    DodopalResponse<String> exportPrdExpOrder(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProductConsumeOrderQuery orderQuery = new ProductConsumeOrderQuery();
        String orderNum = request.getParameter("orderNum");
        if (StringUtils.isNotBlank(orderNum)) {
            orderQuery.setOrderNum(orderNum);
        }
        String orderDateStart = request.getParameter("orderDateStart");
        if (StringUtils.isNotBlank(orderDateStart)) {
            Date startDate = DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR);
            orderQuery.setOrderDateStart(startDate);
        }
        String orderDateEnd = request.getParameter("orderDateEnd");
        if (StringUtils.isNotBlank(orderDateEnd)) {
            Date endDate = DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR);
            orderQuery.setOrderDateEnd(endDate);
        }
        String cardNum = request.getParameter("cardNum");
        if (StringUtils.isNotBlank(cardNum)) {
            orderQuery.setCardNum(cardNum);
        }
        String yktCode = request.getParameter("yktCode");
        if (StringUtils.isNotBlank(yktCode)) {
            orderQuery.setYktCode(yktCode);
        }
        String merType = request.getParameter("merType");
        if (StringUtils.isNotBlank(merType)) {
            orderQuery.setCustomerType(merType);
        }
        String innerStates = request.getParameter("innerStates");
        if (StringUtils.isNotBlank(innerStates)) {
            orderQuery.setInnerStates(innerStates);
        }
        String merName = request.getParameter("merName");
        if (StringUtils.isNotBlank(merName)) {
            orderQuery.setMerName(merName);
        }
        String txnAmtStart = request.getParameter("txnAmtStart");
        if (StringUtils.isNotBlank(txnAmtStart)) {
            double amtStart = Double.valueOf(txnAmtStart) * 100;
            orderQuery.setTxnAmtStart(amtStart + "");
        }
        String txnAmtEnd = request.getParameter("txnAmtEnd");
        if (StringUtils.isNotBlank(txnAmtEnd)) {
            double amtEnd = Double.valueOf(txnAmtEnd) * 100;
            orderQuery.setTxnAmtEnd(amtEnd + "");
        }
        String states = request.getParameter("states");
        if (StringUtils.isNotBlank(states)) {
            orderQuery.setStates(states);
        }
        String posCode = request.getParameter("posCode");
        if (StringUtils.isNotBlank(posCode)) {
            orderQuery.setPosCode(posCode);
        }
        try {
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            DodopalResponse<List<ProductConsumeOrder>> expResponse = icdcPrdService.excelExceptionProductOrderExport(orderQuery);
            if (ResponseCode.SUCCESS.equals(expResponse.getCode())) {
                String resultCode = ExcelUtil.excelExport(request, response, expResponse.getResponseEntity(), col, "一卡通消费异常订单");
                rep.setCode(resultCode);
            } else {
                rep.setCode(expResponse.getCode());
            }
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return rep;
    }
    
    /**
     * 获取通卡公司combox下拉列表信息
     * @param request
     * @return
     */
    @RequestMapping("/findAllYktNames")
    public @ResponseBody List<Map<String, String>> getIcdcNames(HttpServletRequest request,@RequestParam(value = "activate", required = false) String activate) {
        DodopalResponse<List<Map<String, String>>> rs = null;
        List<Map<String, String>> jsonData = new ArrayList<>();
        rs = icdcPrdService.queryIcdcNames(activate);
        List<Map<String, String>> mapList = rs.getResponseEntity();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> value = new HashMap<>();
            Map<String, String> map = mapList.get(i);
            Set<String> k = map.keySet();
            for (String key : k) {
                value.put("id", key);
                value.put("name", map.get(key));
                jsonData.add(value);
                break;
            }
        }
        return jsonData;
    }
}
