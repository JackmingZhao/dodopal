package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProductConsumeOrderDTO;
import com.dodopal.api.product.dto.ProductConsumeOrderDetailDTO;
import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.ProductOrderDetailDTO;
import com.dodopal.api.product.dto.query.ProductConsumeOrderQueryDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ConsumeOrderStatesEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.service.ProductOrderService;

@Controller
@RequestMapping("/productOrder")
public class ProductOrderController extends CommonController {
    private final static Logger log = LoggerFactory.getLogger(ProductOrderController.class);

    @Autowired
    ProductOrderService productOrderService;

    @RequestMapping("/productOrderMgr")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            HttpSession session = request.getSession();
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            response = productOrderService.findMerchantByMerCode(merUserBean.getMerCode());
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                session.setAttribute(ProductConstants.CARD_MER_TYPE, response.getResponseEntity());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_ORDER_FIND_BY_CODE_ERR);
        }
        return new ModelAndView("productOrder/productOrderMgmt");
    }

    //------------------------------------------------------------------------
    //---------------------一卡通公交卡充值订单查询-----------------------------------
    //---------------------------分割线-----------------------------------------
    //------------------------------------------------------------------------
    /**
     * 订单查询 产品库提供标准的订单查询页面给终端商户（各种网点）和个人。
     * @param request
     * @param prdOrderQuery
     * @return
     */
    @RequestMapping(value = "/findProductOrderByPage", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductOrderDTO>> findProductOrderByPage(HttpServletRequest request, @RequestBody ProductOrderQueryDTO prdOrderQuery) {
        log.info("protal input ProductOrderController prdOrderQuery:" + prdOrderQuery);
        DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductOrderDTO>>();
        try {
            HttpSession session = request.getSession();
            //session超时，请重新登录 
            if (StringUtils.isBlank((String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))) {
                response.setCode(ResponseCode.LOGIN_TIME_OUT);
                return response;
            }

            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            prdOrderQuery.setMerCode(merUserBean.getMerCode());
            prdOrderQuery.setUserCode(merUserBean.getUserCode());
            response = productOrderService.findAdminProductOrdersByPage(prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_RECHARGE_ORDER_FIND_ERR);
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
    public @ResponseBody DodopalResponse<ProductOrderDetailDTO> findProductOrderByCode(Model model, HttpServletRequest request, @RequestBody String proOrderNum) {
        log.info("protal input ProductOrderController proOrderNum:" + proOrderNum);
        DodopalResponse<ProductOrderDetailDTO> response = new DodopalResponse<ProductOrderDetailDTO>();
        try {
            response = productOrderService.findProductOrderDetails(proOrderNum);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_ORDER_FIND_BY_CODE_ERR);
        }
        return response;
    }

    /******************** 查询获取公交卡充值订单导出Start *****************************************/
  /*  @RequestMapping("/excelProductOrderSet")
    public @ResponseBody DodopalResponse<String> excelProductOrder(Model model, HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProductOrderQueryDTO prdOrderQuery = new ProductOrderQueryDTO();
        String proOrderNum = request.getParameter("proOrderNum");
        String orderDateStart = request.getParameter("orderDateStart");
        String orderDateEnd = request.getParameter("orderDateEnd");
        String cityName = request.getParameter("cityName");
        String txnAmtStart = request.getParameter("txnAmtStart");
        String txnAmtEnd = request.getParameter("txnAmtEnd");
        String posCode = request.getParameter("posCode");
        String merOrderNum = request.getParameter("merOrderNum");
        String orderCardno = request.getParameter("orderCardno");
        String proOrderState = request.getParameter("proOrderState");
        try {
            if (proOrderNum != null && proOrderNum != "") {
                prdOrderQuery.setProOrderNum(proOrderNum);
            }
            if (orderDateStart != null && orderDateStart != "") {
                prdOrderQuery.setOrderDateStart(DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR));
            }
            if (orderDateEnd != null && orderDateEnd != "") {
                prdOrderQuery.setOrderDateEnd(DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR));
            }
            if (cityName != null && cityName != "") {
                prdOrderQuery.setCityName(cityName);
            }
            if (txnAmtStart != null && txnAmtStart != "") {
                prdOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart) * 100).toString());
            }
            if (txnAmtEnd != null && txnAmtEnd != "") {
                prdOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd) * 100).toString());
            }
            if (posCode != null && posCode != "") {
                prdOrderQuery.setPosCode(posCode);
            }
            if (merOrderNum != null && merOrderNum != "") {
                prdOrderQuery.setMerOrderNum(merOrderNum);
            }
            if (orderCardno != null && orderCardno != "") {
                prdOrderQuery.setOrderCardno(orderCardno);
            }
            if (RechargeOrderStatesEnum.checkCodeExist(proOrderState)) {
                prdOrderQuery.setProOrderState(proOrderState);
            }
            HttpSession session = request.getSession();
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            prdOrderQuery.setMerCode(merUserBean.getMerCode());
            prdOrderQuery.setUserCode(merUserBean.getUserCode());
            rep = productOrderService.excelProDuctExport(response, prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }*/

    //导出 城市一卡通充值记录
    @RequestMapping("/excelProductOrder")
    public @ResponseBody DodopalResponse<String> excelProductOrderSet(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            // DodopalResponse<String> rep = new DodopalResponse<String>();
            ProductOrderQueryDTO prdOrderQuery = new ProductOrderQueryDTO();
            String proOrderNum = request.getParameter("proOrderNum").trim();
            String orderDateStart = request.getParameter("orderDateStart");
            String orderDateEnd = request.getParameter("orderDateEnd");
            String cityName = request.getParameter("cityName").trim();
            String txnAmtStart = request.getParameter("txnAmtStart");
            String txnAmtEnd = request.getParameter("txnAmtEnd");
            String posCode = request.getParameter("posCode").trim();
            String merOrderNum = request.getParameter("merOrderNum");
            String orderCardno = request.getParameter("orderCardno").trim();
            String proOrderState = request.getParameter("proOrderState");
            if (proOrderNum != null && proOrderNum != "") {
                prdOrderQuery.setProOrderNum(proOrderNum);
            }
            if (orderDateStart != null && orderDateStart != "") {
                prdOrderQuery.setOrderDateStart(DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR));
            }
            if (orderDateEnd != null && orderDateEnd != "") {
                prdOrderQuery.setOrderDateEnd(DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR));
            }
            if (cityName != null && cityName != "") {
                prdOrderQuery.setCityName(cityName);
            }
            if (txnAmtStart != null && txnAmtStart != "") {
                prdOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart) * 100).toString());
            }
            if (txnAmtEnd != null && txnAmtEnd != "") {
                prdOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd) * 100).toString());
            }
            if (posCode != null && posCode != "") {
                prdOrderQuery.setPosCode(posCode);
            }
            if (merOrderNum != null && merOrderNum != "") {
                prdOrderQuery.setMerOrderNum(merOrderNum);
            }
            if (orderCardno != null && orderCardno != "") {
                prdOrderQuery.setOrderCardno(orderCardno);
            }
            if (RechargeOrderStatesEnum.checkCodeExist(proOrderState)) {
                prdOrderQuery.setProOrderState(proOrderState);
            }
            HttpSession session = request.getSession();
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            prdOrderQuery.setMerCode(merUserBean.getMerCode());
            prdOrderQuery.setUserCode(merUserBean.getUserCode());

            // rep = productOrderService.excelConsumeOrderExport(response, prdConsumeOrderQuery);
            int exportMaxNum = 100000;
            prdOrderQuery.setPage(new PageParameter(1, exportMaxNum));

            DodopalResponse<DodopalDataPage<ProductOrderDTO>> rtResponse = productOrderService.findAdminProductOrdersByPage(prdOrderQuery);

            List<ProductOrderDTO> productOrderDTOList = new ArrayList<ProductOrderDTO>();

            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("proOrderNum", "订单编号");
            index.put("merOrderNum", "外部订单号");
            index.put("merName", "商户名称");
            index.put("orderCardno", "卡号");
            index.put("posCode", "POS号");
            index.put("cityName", "城市");
            index.put("showTxnAmt", "充值金额");
            index.put("showReceivedPrice", "实付金额");
            
            index.put("merPurchaasePrice", "成本价");
            index.put("showMerGain", "商户利润");
            index.put("showBefbal", "充值前金额");
            index.put("showBlackAmt", "充值后金额");
            index.put("userName", "操作员");
            index.put("proOrderStateView", "订单状态");
            index.put("proOrderDate", "订单时间");
            index.put("posComments", "POS备注");
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                productOrderDTOList = rtResponse.getResponseEntity().getRecords();
            }
            String code = ExcelUtil.excelExport(request, response, productOrderDTOList, index, "城市一卡通充值订单记录");
            rep.setCode(code);
        }
        catch (HessianRuntimeException e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return rep;
    }

    /******************** 查询获取公交卡充值订单导出end *****************************************/

    /******************** 获取公交卡充值订单状态Start *****************************************/
    @RequestMapping("/findProductOrderStatus")
    public @ResponseBody List<DdicVo> findProductOrder(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for (RechargeOrderStatesEnum status : RechargeOrderStatesEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }

    /******************** 获取公交卡充值订单状态end *****************************************/

    /******************** 一卡通消费 *****************************************/
    /**
     * 一卡通消费 收单记录
     * @param request
     * @return
     */
    @RequestMapping("/productConsumeOrderMgr")
    public ModelAndView productConsumeOrderMgr(HttpServletRequest request) {
        return new ModelAndView("productOrder/productConsumeOrderMgr");
    }

    /**
     * 收单记录 查询
     * @param request
     * @param prdOrderQuery
     * @return
     */
    @RequestMapping("/findProductConsumeOrder")
    public @ResponseBody DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> findProductConsumeOrderByPage(HttpServletRequest request, @RequestBody ProductConsumeOrderQueryDTO prdConsumeOrderQuery) {
        log.info("protal input findProductConsumeOrderByPage prdConsumeOrderQuery:" + prdConsumeOrderQuery);
        DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> response = new DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>>();

        try {
            if (StringUtils.isBlank((String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))) {
                response.setCode(ResponseCode.LOGIN_TIME_OUT);
                return response;
            }
            HttpSession session = request.getSession();
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);

            String merUserFlag = merUserBean.getMerUserFlag();
            String userId = "";
            //商户号
            String customerCode = merUserBean.getMerCode();
            if (MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)) {
                userId = merUserBean.getId();
            }

            if (prdConsumeOrderQuery.getTxnAmtStart() != null && prdConsumeOrderQuery.getTxnAmtStart() != "") {
                prdConsumeOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(prdConsumeOrderQuery.getTxnAmtStart()) * 100).toString());
            }
            if (prdConsumeOrderQuery.getTxnAmtEnd() != null && prdConsumeOrderQuery.getTxnAmtEnd() != "") {
                prdConsumeOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(prdConsumeOrderQuery.getTxnAmtEnd()) * 100).toString());
            }

            prdConsumeOrderQuery.setCustomerCode(customerCode);
            prdConsumeOrderQuery.setUserId(userId);
            response = productOrderService.findProductConsumeOrderByPage(prdConsumeOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_CONSUME_ORDER_FIND_ERR);
        }
        return response;
    }

    //查询一卡通消费   收单记录导出
    /*   @RequestMapping("/excelproductConsumeOrder")
    public @ResponseBody DodopalResponse<String> excelProductConsumeOrder(Model model, HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProductConsumeOrderQueryDTO prdConsumeOrderQuery = new ProductConsumeOrderQueryDTO();
        String orderDateStart = request.getParameter("orderDateStart");
        String orderDateEnd = request.getParameter("orderDateEnd");
        String cityName = request.getParameter("cityName");
        String txnAmtStart = request.getParameter("txnAmtStart");
        String txnAmtEnd = request.getParameter("txnAmtEnd");
        String posCode = request.getParameter("posCode");
        String orderNum = request.getParameter("orderNum");
        String cardNum = request.getParameter("cardNum");
        String states = request.getParameter("states");

        try {

            if (orderDateStart != null && orderDateStart != "") {
                prdConsumeOrderQuery.setOrderDateStart(DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR));
            }
            if (orderDateEnd != null && orderDateEnd != "") {
                prdConsumeOrderQuery.setOrderDateEnd(DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR));
            }
            if (cityName != null && cityName != "") {
                prdConsumeOrderQuery.setCityName(cityName);
            }
            if (txnAmtStart != null && txnAmtStart != "") {
                prdConsumeOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart) * 100).toString());
            }
            if (txnAmtEnd != null && txnAmtEnd != "") {
                prdConsumeOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd) * 100).toString());
            }
            if (posCode != null && posCode != "") {
                prdConsumeOrderQuery.setPosCode(posCode);
            }
            if (orderNum != null && orderNum != "") {
                prdConsumeOrderQuery.setOrderNum(orderNum);
            }
            if (cardNum != null && cardNum != "") {
                prdConsumeOrderQuery.setCardNum(cardNum);
            }
            if (ConsumeOrderStatesEnum.checkCodeExist(states)) {
                prdConsumeOrderQuery.setStates(states);
            }
            HttpSession session = request.getSession();
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            String customerCode = merUserBean.getMerCode();
            String merUserFlag = merUserBean.getMerUserFlag();
            String userId = "";
            if (MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)) {
                userId = merUserBean.getId();
            }
            prdConsumeOrderQuery.setCustomerCode(customerCode);
            prdConsumeOrderQuery.setUserId(userId);
            rep = productOrderService.excelConsumeOrderExport(response, prdConsumeOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }*/

    //导出 城市一卡通消费收单记录
    @RequestMapping("/excelproductConsumeOrder")
    public @ResponseBody DodopalResponse<String> excelproductConsumeOrder(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        try {
            // DodopalResponse<String> rep = new DodopalResponse<String>();
            ProductConsumeOrderQueryDTO prdConsumeOrderQuery = new ProductConsumeOrderQueryDTO();
            String orderDateStart = request.getParameter("orderDateStart");
            String orderDateEnd = request.getParameter("orderDateEnd");
            String cityName = request.getParameter("cityName").trim();
            String txnAmtStart = request.getParameter("txnAmtStart");
            String txnAmtEnd = request.getParameter("txnAmtEnd");
            String posCode = request.getParameter("posCode").trim();
            String orderNum = request.getParameter("orderNum").trim();
            String cardNum = request.getParameter("cardNum").trim();
            String states = request.getParameter("states");

            if (orderDateStart != null && orderDateStart != "") {
                prdConsumeOrderQuery.setOrderDateStart(DateUtils.stringtoDate(orderDateStart, DateUtils.DATE_SMALL_STR));
            }
            if (orderDateEnd != null && orderDateEnd != "") {
                prdConsumeOrderQuery.setOrderDateEnd(DateUtils.stringtoDate(orderDateEnd, DateUtils.DATE_SMALL_STR));
            }
            if (cityName != null && cityName != "") {
                prdConsumeOrderQuery.setCityName(cityName);
            }
            if (txnAmtStart != null && txnAmtStart != "") {
                prdConsumeOrderQuery.setTxnAmtStart(new Double(Double.parseDouble(txnAmtStart) * 100).toString());
            }
            if (txnAmtEnd != null && txnAmtEnd != "") {
                prdConsumeOrderQuery.setTxnAmtEnd(new Double(Double.parseDouble(txnAmtEnd) * 100).toString());
            }
            if (posCode != null && posCode != "") {
                prdConsumeOrderQuery.setPosCode(posCode);
            }
            if (orderNum != null && orderNum != "") {
                prdConsumeOrderQuery.setOrderNum(orderNum);
            }
            if (cardNum != null && cardNum != "") {
                prdConsumeOrderQuery.setCardNum(cardNum);
            }
            if (ConsumeOrderStatesEnum.checkCodeExist(states)) {
                prdConsumeOrderQuery.setStates(states);
            }
            HttpSession session = request.getSession();
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            String customerCode = merUserBean.getMerCode();
            String merUserFlag = merUserBean.getMerUserFlag();
            String userId = "";
            if (MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)) {
                userId = merUserBean.getId();
            }
            prdConsumeOrderQuery.setCustomerCode(customerCode);
            prdConsumeOrderQuery.setUserId(userId);

            // rep = productOrderService.excelConsumeOrderExport(response, prdConsumeOrderQuery);
            int exportMaxNum = 100000;
            prdConsumeOrderQuery.setPage(new PageParameter(1, exportMaxNum));
            DodopalResponse<DodopalDataPage<ProductConsumeOrderDTO>> rtResponse = productOrderService.findProductConsumeOrderByPage(prdConsumeOrderQuery);
            //DodopalResponse<DodopalDataPage<ProxyCardAddBean>> rtResponse = proxyCardAddService.cardTradeList(queryDTO);

            List<ProductConsumeOrderDTO> productConsumeOrderDTOList = new ArrayList<ProductConsumeOrderDTO>();

            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("orderNum", "订单号");
            index.put("cardNum", "卡号");
            index.put("posCode", "POS号");
            index.put("cityName", "城市");
            index.put("originalPrice", "应收金额");
            index.put("receivedPrice", "实收金额");
            index.put("befbal", "支付前金额");
            index.put("blackAmt", "支付后金额");
            index.put("States", "订单状态");
            index.put("proOrderDate", "订单时间");
            index.put("userName", "操作员");
            index.put("comments", "POS备注");
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                productConsumeOrderDTOList = rtResponse.getResponseEntity().getRecords();
            }
            String code = ExcelUtil.excelExport(request, response, productConsumeOrderDTOList, index, "城市一卡通消费收单记录");
            rep.setCode(code);
        }
        catch (HessianRuntimeException e) {
            e.printStackTrace();
            rep.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return rep;
    }

    /**
     * 一卡通消费 查询订单状态
     * @param model
     * @param request
     * @return
     */

    @RequestMapping("/findProductConsumeOrderStatus")
    public @ResponseBody List<DdicVo> findProductConsumeOrderStatus(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for (ConsumeOrderStatesEnum status : ConsumeOrderStatesEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }

    /**
     * 一卡通消费 收单记录查询 记录详情查看
     * @param model
     * @param request
     * @param proOrderNum
     * @return
     */
    @RequestMapping("/findProductConsumeOrderByCode")
    public @ResponseBody DodopalResponse<ProductConsumeOrderDetailDTO> findProductConsumeOrderByCode(Model model, HttpServletRequest request, @RequestBody String orderNum) {
        log.info("protal input findProductConsumeOrderByCode orderNum:" + orderNum);
        DodopalResponse<ProductConsumeOrderDetailDTO> response = new DodopalResponse<ProductConsumeOrderDetailDTO>();
        try {
            response = productOrderService.findProductConsumeOrderDetails(orderNum);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PORTAL_PRODUCT_CONSUME_ORDER_FIND_ERR);
        }
        return response;
    }

    /******************** 一卡通消费* end ****************************************/
}
