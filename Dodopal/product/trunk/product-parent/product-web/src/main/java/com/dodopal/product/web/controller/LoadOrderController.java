package com.dodopal.product.web.controller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.IcdcLoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderExtResponseDTO;
import com.dodopal.api.product.dto.LoadOrderQueryResponseDTO;
import com.dodopal.api.product.dto.LoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderResponseDTO;
import com.dodopal.api.product.dto.LoadOrderResponseDTO2;
import com.dodopal.api.product.dto.LoadOrdersResponseDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.LoadOrderStatusEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.LoadOrderRecord;
import com.dodopal.product.business.model.query.ProLoadOrderQuery;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.MerchantUserService;

@Controller
@RequestMapping("/loadOrder")
public class LoadOrderController {

    private Logger logger = LoggerFactory.getLogger(LoadOrderController.class);

    @Autowired
    LoadOrderService loadOrderService;
    @Autowired
    MerchantUserService merUserService;
    @Autowired
    CityFindService cityFindService;

    @RequestMapping("/loadOrderView")
    public ModelAndView cardEncryptMgmt(HttpServletRequest request) {
        return new ModelAndView("loadOrder/loadOrderMgmt");
    }

    /**
     * 外接商户__创建公交卡充值圈存订单
     * @param request
     * @param loadOrderRequestDTO
     * @return
     */
    @RequestMapping("/extBookLoadOrder")
    @ResponseBody
    public String extBookLoadOrder(HttpServletRequest request) {
        String resultXml = "";
        LoadOrderResponseDTO result = new LoadOrderResponseDTO();
        try {
            LoadOrderRequestDTO loadOrderRequestDTO = new LoadOrderRequestDTO();
            loadOrderRequestDTO.setSourceOrderNum(request.getParameter("sourceOrderNum"));
            loadOrderRequestDTO.setCardNum(request.getParameter("cardNum"));
            loadOrderRequestDTO.setMerchantNum(request.getParameter("merchantNum"));
            loadOrderRequestDTO.setProductNum(request.getParameter("productNum"));
            loadOrderRequestDTO.setCityCode(request.getParameter("cityCode"));
            loadOrderRequestDTO.setChargeAmt(request.getParameter("chargeAmt"));
            loadOrderRequestDTO.setSourceOrderTime(request.getParameter("sourceOrderTime"));
            loadOrderRequestDTO.setSignType(request.getParameter("signType"));
            loadOrderRequestDTO.setSignData(request.getParameter("signData"));
            loadOrderRequestDTO.setSignCharset(request.getParameter("signCharset"));

            DodopalResponse<LoadOrderResponseDTO> response = loadOrderService.bookExtMerLoadOrder(loadOrderRequestDTO);
            result.setResponseCode(response.getCode());
            if (response.getResponseEntity() != null) {
                result.setOrderNum(response.getResponseEntity().getOrderNum());
                result.setSourceOrderNum(response.getResponseEntity().getSourceOrderNum());
            }
        }
        catch (Exception e) {
            logger.error("在DDP平台中创建公交卡充值圈存订单出错:" + e.getMessage());
            result.setResponseCode(ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR);
        }
        try {
            JAXBContext context = JAXBContext.newInstance(LoadOrderResponseDTO.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(result, writer);
            resultXml = writer.toString();
        }
        catch (JAXBException e) {
            logger.error("在DDP平台中创建公交卡充值圈存订单返回处理时出错:" + e.getMessage());
            e.printStackTrace();
            resultXml = ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR;
        }
        return resultXml;
    }

    /**
     * 6.1 查询公交卡充值圈存订单
     * @param request
     * @return
     */
    @RequestMapping("/findLoadOrder")
    @ResponseBody
    public String findLoadOrder(HttpServletRequest request) {
        String resultXml;
        try {
            DodopalResponse<List<LoadOrderResponseDTO2>> orderList = loadOrderService.findLoadOrder();
            LoadOrdersResponseDTO orders = new LoadOrdersResponseDTO();
            orders.getLoadOrders().addAll((orderList.getResponseEntity()));

            JAXBContext context = JAXBContext.newInstance(LoadOrdersResponseDTO.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(orders, writer);
            resultXml = writer.toString();
        }
        catch (Exception e) {
            logger.error("在DDP平台中查询订单失败:", e);
            resultXml = ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR;
        }
        return resultXml;
    }

    /**
     * 6.3 根据外接商户的订单号查询圈存订单状态
     * @param request
     * @param loadOrderRequestDTO
     * @return
     */
    @RequestMapping("/findLoadOrderStatus")
    @ResponseBody
    public String findLoadOrderStatus(HttpServletRequest request) {
        String resultXml = "";
        LoadOrderExtResponseDTO result = new LoadOrderExtResponseDTO();
        try {
            LoadOrderRequestDTO loadOrderRequestDTO = new LoadOrderRequestDTO();
            loadOrderRequestDTO.setSourceOrderNum(request.getParameter("sourceOrderNum"));
            loadOrderRequestDTO.setMerchantNum(request.getParameter("merchantNum"));
            loadOrderRequestDTO.setSignType(request.getParameter("signType"));
            loadOrderRequestDTO.setSignData(request.getParameter("signData"));
            loadOrderRequestDTO.setSignCharset(request.getParameter("signCharset"));

            DodopalResponse<LoadOrderExtResponseDTO> response = loadOrderService.findLoadOrderStatus(loadOrderRequestDTO);
            result.setResponseCode(response.getCode());
            if (response.getResponseEntity() != null) {
                result.setOrderNum(response.getResponseEntity().getOrderNum());
                result.setSourceOrderNum(response.getResponseEntity().getSourceOrderNum());
                result.setSourceOrderTime(response.getResponseEntity().getSourceOrderTime());
                result.setCardNum(response.getResponseEntity().getCardNum());
                result.setOrderStatus(response.getResponseEntity().getOrderStatus());
            }
            JAXBContext context = JAXBContext.newInstance(LoadOrderExtResponseDTO.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(result, writer);
            resultXml = writer.toString();
        }
        catch (Exception e) {
            logger.error("在DDP平台中创建公交卡充值圈存订单出错:", e);
            result.setResponseCode(ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR);
        }
        return resultXml;
    }

    /**
     * 6.2 根据卡号获取可用于一卡通充值的圈存订单 产品库内部调用 原则上不需要提供http 接口，这里为了测试需要提交http 接口
     * 注：该http接口被用于产品库一卡通充值页面获取圈存订单，请勿删除 备注时间2015年10月12日12:05:22
     * @param cardNum
     * @return
     */
    @RequestMapping("/findAvailableLoadOrdersByCardNum")
    public @ResponseBody
    DodopalResponse<List<LoadOrderQueryResponseDTO>> findAvailableLoadOrdersByCardNum(HttpServletRequest request, @RequestBody String cardNum) {
        DodopalResponse<List<LoadOrderQueryResponseDTO>> response;
        try {
            response = loadOrderService.findAvailableLoadOrdersByCardNum(cardNum);
        }
        catch (Exception e) {
            logger.error("在DDP平台中查询订单失败:", e);
            response = new DodopalResponse<List<LoadOrderQueryResponseDTO>>();
            response.setCode(ResponseCode.PRODUCT_LOADORDER_QUERY_ERROR);
        }
        return response;
    }

    /**
     * 个人一卡通圈存开始
     */

    @RequestMapping("/personBookLoadOrder")
    public @ResponseBody
    DodopalResponse<String> personBookLoadOrder(HttpServletRequest request, @RequestBody IcdcLoadOrderRequestDTO orderRequest) {
        MerchantUserBean userBean = (MerchantUserBean) request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
        
        DodopalResponse<String> dodopalResult = new DodopalResponse<String>();
        if (null == userBean) {
            dodopalResult.setCode(ResponseCode.LOGIN_TIME_OUT);
            return dodopalResult;
        }
        try {
            orderRequest.setCustomerCode(userBean.getUserCode());
            orderRequest.setSource(SourceEnum.PORTAL.getCode());
            DodopalResponse<LoadOrder> loadOrderRes = loadOrderService.bookLoadOrder(orderRequest);
            if (ResponseCode.SUCCESS.equals(loadOrderRes.getCode())) {
                dodopalResult.setResponseEntity(loadOrderRes.getResponseEntity().getOrderNum());
            }
            dodopalResult.setCode(loadOrderRes.getCode());
        }
        catch (Exception e) {
            logger.error("创建公交卡充值圈存订单出错:", e);
            dodopalResult.setCode(ResponseCode.PRODUCT_LOADORDER_BOOK_ERROR);
        }
        return dodopalResult;
    }

    @RequestMapping("/toLoadOrder")
    public ModelAndView toLoadOrder(HttpServletRequest request) {
        return new ModelAndView("applicationCenter/loadOrder/loadOrder");
    }

    /**
     * 个人圈存订单查询初始化
     * @param request
     * @return
     */
    @RequestMapping("/loadOrderList")
    public ModelAndView loadOrderList(HttpServletRequest request) {
        return new ModelAndView("applicationCenter/loadOrder/loadOrderList");
    }

    /**
     * 个人圈存订单查询(分页)
     * @param request
     * @param prdOrderQuery
     * @return
     */
    @RequestMapping("/findLoadOrderByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<LoadOrderRecord>> findLoadOrderByPage(HttpServletRequest request, @RequestBody ProLoadOrderQuery prdOrderQuery) {
        DodopalResponse<DodopalDataPage<LoadOrderRecord>> response = new DodopalResponse<DodopalDataPage<LoadOrderRecord>>();
        try {
            HttpSession session = request.getSession();
            //session超时，请重新登录 
            if (StringUtils.isBlank((String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))) {
                response.setCode(ResponseCode.LOGIN_TIME_OUT);
                return response;
            }
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            prdOrderQuery.setUserCode(merUserBean.getUserCode());
            response = loadOrderService.findLoadOrderListByPage(prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_RECHARGE_ORDER_FIND_ERR);
        }
        return response;
    }

    @RequestMapping("/excelLoadOrder")
    public @ResponseBody
    DodopalResponse<String> excelLoadOrder(Model model, HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProLoadOrderQuery prdOrderQuery = new ProLoadOrderQuery();
        HttpSession session = request.getSession();
        if (StringUtils.isBlank((String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))) {
            rep.setCode(ResponseCode.LOGIN_TIME_OUT);
            return rep;
        }
        String loadOrderNum = request.getParameter("loadOrderNum");
        String loadOrderState = request.getParameter("loadOrderState");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cardNo = request.getParameter("cardNo");
        String cityCode = request.getParameter("cityCode");
        String txnAmtStart = request.getParameter("txnAmtStart");
        String txnAmtEnd = request.getParameter("txnAmtEnd");
        try {
            if (StringUtils.isNotBlank(loadOrderNum)) {
                prdOrderQuery.setLoadOrderNum(loadOrderNum);
            }
            if (StringUtils.isNotBlank(loadOrderState)) {
                prdOrderQuery.setLoadOrderState(loadOrderState);
            }
            if (StringUtils.isNotBlank(startDate)) {
                prdOrderQuery.setStartDate(DateUtils.stringtoDate(startDate, DateUtils.DATE_SMALL_STR));
            }
            if (StringUtils.isNotBlank(endDate)) {
                prdOrderQuery.setEndDate(DateUtils.stringtoDate(endDate, DateUtils.DATE_SMALL_STR));
            }
            if (StringUtils.isNotBlank(cardNo)) {
                prdOrderQuery.setCardNo(cardNo);
            }
            if (StringUtils.isNotBlank(cityCode)) {
                prdOrderQuery.setCityCode(cityCode);
            }
            if (StringUtils.isNotBlank(txnAmtStart)) {
                prdOrderQuery.setTxnAmtStart(txnAmtStart);
            }
            if (StringUtils.isNotBlank(txnAmtEnd)) {
                prdOrderQuery.setTxnAmtEnd(txnAmtEnd);
            }
            MerchantUserBean merUserBean = (MerchantUserBean) session.getAttribute(ProductConstants.CARD_LOGIN_USER);
            prdOrderQuery.setUserCode(merUserBean.getUserCode());
            rep = loadOrderService.excelLoadOrder(response, prdOrderQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }

    //查询圈存订单状态
    @RequestMapping("/findProOrderStatus")
    public @ResponseBody
    List<DdicVo> findProOrderStatus(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for (LoadOrderStatusEnum status : LoadOrderStatusEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }

    //查询业务城市
    @RequestMapping("/findCitys")
    public @ResponseBody
    List<Area> findCitys(HttpServletRequest request) {
        List<Area> areaList = cityFindService.getAllBusinessCity();
        return areaList;
    }

    /**
     * 个人一卡通圈存结束
     */

}
