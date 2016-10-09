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
import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.ExceptionOrderHandleDto;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.IcdcPrdService;
import com.dodopal.oss.business.service.ProductOrderService;
import com.dodopal.oss.business.service.RechargeOrderExceptionHandleService;

/**
 * 异常公交卡充值订单（查询、查看、异常处理）
 */
@Controller
@RequestMapping("/product/exceptionOrder/rechargeOrder")
public class IcdcOrderExpController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(IcdcOrderExpController.class);

    @Autowired
    ProductOrderService productOrderService;
    @Autowired
    private IcdcPrdService icdcPrdService;
    @Autowired
    RechargeOrderExceptionHandleService rechargeOrderExceptionHandleService;
    @Autowired
    private ExpTempletService expTempletService;

    @RequestMapping("/init")
    public ModelAndView init(HttpServletRequest request) {
        return new ModelAndView("product/exceptionOrder/productRechargeOrder");
    }

    /**
     * 订单查询
     */
    @RequestMapping("/findProductOrderByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrderByPage(HttpServletRequest request, @RequestBody ProductOrderQueryDTO queryDto) {
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            response = productOrderService.findExceptionProductOrderByPage(queryDto);
        }
        catch (HessianRuntimeException e) {
            logger.error("查询公交卡充值订单时发生错误:", e);
            response.setCode(ResponseCode.OSS_PRD_CONNECT_ERROR);
        }
        catch (Exception e) {
            logger.error("查询公交卡充值订单时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 订单查看
     */
    @RequestMapping("/viewProductOrderDetails")
    public @ResponseBody
    DodopalResponse<ProductOrderDetailDTO> viewProductOrderDetails(HttpServletRequest request, @RequestBody String proOrderNum) {
        DodopalResponse<ProductOrderDetailDTO> response = new DodopalResponse<ProductOrderDetailDTO>();
        try {
            response = productOrderService.findProductOrderDetails(proOrderNum);
        }
        catch (HessianRuntimeException e) {
            logger.error("查询公交卡充值订单时发生错误:", e);
            response.setCode(ResponseCode.OSS_PRD_CONNECT_ERROR);
        }
        catch (Exception e) {
            logger.error("查询公交卡充值订单时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 异常订单处理
     */
    @RequestMapping("/exceptionHandle")
    public @ResponseBody
    DodopalResponse<String> exceptionHandle(HttpServletRequest request, @RequestBody ExceptionOrderHandleDto orderHandleDto) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        User operator = super.generateLoginUser(request);
        orderHandleDto.setUpdateUser(operator.getId());
        String operatorName = operator.getName();
        SysLog log = new SysLog();
        log.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
        try {
            response = rechargeOrderExceptionHandleService.exceptionHandle(orderHandleDto, operatorName);
        }
        catch (DDPException e) {
            logger.error("产品库充值订单异常处理出错:" + e.getMessage());
            response.setCode(e.getCode());
            log.setStatckTrace(e.getMessage());
        }
        catch (Exception e) {
            logger.error("产品库充值订单异常处理出错:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            log.setStatckTrace(e.getStackTrace().toString());
        }
        finally {
            log.setServerName(CommonConstants.SYSTEM_NAME_OSS);
            log.setOrderNum(orderHandleDto.getOrderNum());
            log.setTranNum("");
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                log.setTradeType(0);
            } else {
                log.setTradeType(1);
            }
            log.setClassName(IcdcOrderExpController.class.toString());
            log.setMethodName("exceptionHandle");
            log.setInParas("产品库异常充值订单编号：" + orderHandleDto.getOrderNum() + "；鉴定结果（0：失败——需要执行资金解冻流程；1：成功——需要执行资金扣款流程）:" + orderHandleDto.getJudgeResult() + "；操作员:" + operatorName);
            log.setOutParas(JSONObject.toJSONString(response));
            log.setRespCode(response.getCode());
            log.setRespExplain(response.getMessage());
            log.setDescription("OSS系统处理状态异常的一卡通充值订单");
            log.setSource("");
            log.setIpAddress("");
            log.setLogDate(new Date());
            log.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSSSSS_STR)));
            log.setTradeRrack(log.getTradeEnd() - log.getTradeStart());

            // 记录日志
            ActivemqLogPublisher.publishLog2Queue(log, DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(CommonConstants.SERVER_LOG_URL));
        }
        return response;
    }

    @RequestMapping("/exportPrdExpOrder")
    public @ResponseBody
    DodopalResponse<String> exportPrdExpOrder(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();

        ProductOrderQueryDTO orderQuery = new ProductOrderQueryDTO();
        String proOrderNum = request.getParameter("proOrderNum");
        if (StringUtils.isNotBlank(proOrderNum)) {
            orderQuery.setProOrderNum(proOrderNum);
        }
        String proOrderState = request.getParameter("proOrderState");
        if (StringUtils.isNotBlank(proOrderState)) {
            orderQuery.setProOrderState(proOrderState);
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
        String orderCardno = request.getParameter("orderCardno");
        if (StringUtils.isNotBlank(orderCardno)) {
            orderQuery.setOrderCardno(orderCardno);
        }
        String yktCode = request.getParameter("yktCode");
        if (StringUtils.isNotBlank(yktCode)) {
            orderQuery.setYktCode(yktCode);
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
        String posCode = request.getParameter("posCode");
        if (StringUtils.isNotBlank(posCode)) {
            orderQuery.setPosCode(posCode);
        }
        String merOrderNum = request.getParameter("merOrderNum");
        if (StringUtils.isNotBlank(merOrderNum)) {
            orderQuery.setMerOrderNum(merOrderNum);
        }
        String proInnerState = request.getParameter("proInnerState");
        if (StringUtils.isNotBlank(proInnerState)) {
            orderQuery.setProInnerState(proInnerState);
        }
        String merName = request.getParameter("merName");
        if (StringUtils.isNotBlank(merName)) {
            orderQuery.setMerName(merName);
        }
        String merType = request.getParameter("merType");
        if (StringUtils.isNotBlank(merType)) {
            orderQuery.setMerType(merType);
        }
        String payType = request.getParameter("payType");
        if (StringUtils.isNotBlank(payType)) {
            orderQuery.setPayType(payType);
        }
        orderQuery.setSource("OSS");
        try {
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            DodopalResponse<List<ProductOrderDTO>> expResponse = productOrderService.excelExceptionProductOrderExport(orderQuery);
            if (ResponseCode.SUCCESS.equals(expResponse.getCode())) {
                String resultCode = ExcelUtil.excelExport(request, response, expResponse.getResponseEntity(), col, "一卡通充值异常订单");
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
