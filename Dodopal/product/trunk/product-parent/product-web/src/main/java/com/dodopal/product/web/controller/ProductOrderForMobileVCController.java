package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.model.QueryProductOrderResult;
import com.dodopal.product.business.model.QueryProductOrderStatesResult;
import com.dodopal.product.business.model.query.QueryProductOrderRequest;
import com.dodopal.product.business.model.query.QueryProductOrderStatesRequest;
import com.dodopal.product.business.service.ProductOrderForMobileVCService;
import com.dodopal.product.web.mobileBean.QueryProductOrderResultDTO;
import com.dodopal.product.web.param.QueryProductOrderRequestDTO;
import com.dodopal.product.web.param.QueryProductOrderResponse;
import com.dodopal.product.web.param.QueryProductOrderStatesRequestDTO;
import com.dodopal.product.web.param.QueryProductOrderStatesResponse;

/**
 * 公交卡充值订单相关：移动端、VC端接口
 * @author dodopal
 */
@Controller
@RequestMapping("/productOrderForMobileVC")
public class ProductOrderForMobileVCController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ProductOrderForMobileVCController.class);

    @Autowired
    private ProductOrderForMobileVCService service;

    /**
     * 3.6 公交卡充值订单查询(移动端、VC端接口)
     */
    @RequestMapping("/queryProductOrder")
    public @ResponseBody QueryProductOrderResponse queryProductOrder(HttpServletRequest request) {
        QueryProductOrderResponse response = new QueryProductOrderResponse();
        response.setRespcode(ResponseCode.SUCCESS);
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("queryProductOrder:公交卡充值订单查询接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("queryProductOrder:公交卡充值订单查询接口:接收到的jsondata参数:" + jsondata);
            }
            // 转换jsondata
            QueryProductOrderRequestDTO queryProductOrderRequest = convertJsonToRequest(jsondata, QueryProductOrderRequestDTO.class);
            // 通用参数校验
            baseCheck(queryProductOrderRequest);
            // 业务入参校验
            queryProductOrderBPCheck(queryProductOrderRequest);
            // 验签
            signCheck(key, queryProductOrderRequest);
            // 查询参数设置
            QueryProductOrderRequest requestDto = new QueryProductOrderRequest();
            requestDto.setCustcode(queryProductOrderRequest.getCustcode());
            requestDto.setCusttype(queryProductOrderRequest.getCusttype());
            requestDto.setEndamt(queryProductOrderRequest.getEndamt());
            requestDto.setEnddate(queryProductOrderRequest.getEnddate());
            requestDto.setOrdernum(queryProductOrderRequest.getOrdernum());
            requestDto.setOrderstates(queryProductOrderRequest.getOrderstates());
            requestDto.setStratamt(queryProductOrderRequest.getStratamt());
            requestDto.setStratdate(queryProductOrderRequest.getStratdate());
            requestDto.setUserid(queryProductOrderRequest.getUserid());
            // 查询
            List<QueryProductOrderResult> serviceResponse = service.queryProductOrder(requestDto);
            if (serviceResponse != null) {
                List<QueryProductOrderResultDTO> resultDTOs = new ArrayList<QueryProductOrderResultDTO>();
                for (QueryProductOrderResult dto : serviceResponse) {
                    QueryProductOrderResultDTO resultDTO = new QueryProductOrderResultDTO();
                    PropertyUtils.copyProperties(resultDTO, dto);
                    resultDTO.setOrderstates(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(resultDTO.getOrderstates()).getName());
                    resultDTOs.add(resultDTO);
                }
                response.setResultDTOs(resultDTOs);
            }
            // 签名（返回值list不用签名）
            // sign(key, response);
        }
        catch (DDPException e) {
            response.setRespcode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isInfoEnabled()) {
            logger.info("queryProductOrder:公交卡充值订单查询接口:返回respcode:" + response.getRespcode());
        }
        return response;
    }

    /**
     * 3.12 订单状态查询(移动端、VC端接口)
     */
    @RequestMapping("/queryProductOrderStates")
    public @ResponseBody QueryProductOrderStatesResponse queryProductOrderStates(HttpServletRequest request) {
        QueryProductOrderStatesResponse response = new QueryProductOrderStatesResponse();
        response.setRespcode(ResponseCode.SUCCESS);
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("queryProductOrderStates:订单状态查询接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("queryProductOrderStates:订单状态查询接口:接收到的jsondata参数:" + jsondata);
            }
            // 转换jsondata
            QueryProductOrderStatesRequestDTO queryProductOrderStatesRequest = convertJsonToRequest(jsondata, QueryProductOrderStatesRequestDTO.class);
            // 通用参数校验
            baseCheck(queryProductOrderStatesRequest);
            // 业务参数校验
            queryProductOrderStatesBPCheck(queryProductOrderStatesRequest);
            // 验签
            signCheck(key, queryProductOrderStatesRequest);
            // 查询参数设置
            QueryProductOrderStatesRequest requestDto = new QueryProductOrderStatesRequest();
            requestDto.setCustcode(queryProductOrderStatesRequest.getCustcode());
            requestDto.setOrdernum(queryProductOrderStatesRequest.getOrdernum());
            // 查询
            QueryProductOrderStatesResult serviceResponse = service.queryProductOrderStates(requestDto);
            if (serviceResponse != null) {
                response.setProinnerstates(RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesEnumByCode(serviceResponse.getProinnerstates()).getName());
                response.setProorderstates(RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(serviceResponse.getProorderstates()).getName());
            }
            // 签名(成功签名，失败不用)
            if (ResponseCode.SUCCESS.equals(response.getRespcode())) {
                sign(key, response);
            }
        }
        catch (DDPException e) {
            response.setRespcode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isInfoEnabled()) {
            logger.info("queryProductOrderStates:订单状态查询接口:返回respcode:" + response.getRespcode());
        }
        return response;
    }

    /**
     * 3.6 公交卡充值订单查询接口 业务入参检查
     * @param request
     */
    private void queryProductOrderBPCheck(QueryProductOrderRequestDTO request) {
        // 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = request.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTTYPE_ERROR);
        }
        // 客户号    String[40]   不为空
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }
        // 用户id    String[20]   不为空
        String userid = request.getUserid();
        if (!DDPStringUtil.existingWithLength(userid, 20)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_USERID_NULL);
        }
    }

    /**
     * 3.12 订单状态查询接口 业务入参检查
     * @param request
     */
    private void queryProductOrderStatesBPCheck(QueryProductOrderStatesRequestDTO request) {
        // 客户号：String[40] 不为空 （都都宝平台生成的业务主键：个人用户：用户号usercode；商户用户：商户号 mercode）   
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }
        // 订单号：String[40] 不为空  (都都宝平台订单号)   
        String ordernum = request.getOrdernum();
        if (!DDPStringUtil.existingWithLength(ordernum, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_ORDERNUM_NULL);
        }
    }

}