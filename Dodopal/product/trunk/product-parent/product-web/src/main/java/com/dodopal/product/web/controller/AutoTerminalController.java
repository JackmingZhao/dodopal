package com.dodopal.product.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.TerminalParameter;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.product.dto.IcdcLoadOrderRequestDTO;
import com.dodopal.api.product.dto.LoadOrderQueryResponseDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.bean.CreatePayTransactionBean;
import com.dodopal.product.business.bean.LoadAndTradeOrder;
import com.dodopal.product.business.model.AutoTerminalParameterResult;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.query.AutoTerminalParameterQuery;
import com.dodopal.product.business.service.AutoTerminalService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.PayTransactionService;
import com.dodopal.product.delegate.PayTransactionDelegate;
import com.dodopal.product.web.param.AutoTerminalParaDownloadRequestDTO;
import com.dodopal.product.web.param.AutoTerminalParaDownloadResponse;
import com.dodopal.product.web.param.CreatePayTransactionRequest;
import com.dodopal.product.web.param.CreatePayTransactionResponse;
import com.dodopal.product.web.param.LoadOrderRequest;
import com.dodopal.product.web.param.LoadOrderResponse;
import com.dodopal.product.web.param.PayRefundRequest;

/**
 * 自助终端Controller
 */
@Controller
@RequestMapping("/buffet")
public class AutoTerminalController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AutoTerminalController.class);

    @Autowired
    private AutoTerminalService service;
    @Autowired
    private PayTransactionService payTransactionService;
    @Autowired
    private LoadOrderService loadOrderService;
    @Autowired
    PayTransactionDelegate payTransactionDelegate;
    
    /**
     * 终端参数下载
     */
    @RequestMapping("/parameterDownload")
    public @ResponseBody
    AutoTerminalParaDownloadResponse parameterDownload(HttpServletRequest request) {

        AutoTerminalParaDownloadResponse response = new AutoTerminalParaDownloadResponse();
        response.setRespcode(ResponseCode.SUCCESS);

        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isErrorEnabled()) {
                    logger.error("自助终端参数下载接口：接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("自助终端参数下载接口:接收到的jsondata参数:" + jsondata);
            }

            // 转换jsondata
            AutoTerminalParaDownloadRequestDTO requestDto = convertJsonToRequest(jsondata, AutoTerminalParaDownloadRequestDTO.class);

            // 通用参数校验
            baseCheck(requestDto);

            // 验签
            signCheck(key, requestDto);

            // 查询参数设置
            AutoTerminalParameterQuery queryDto = new AutoTerminalParameterQuery();
            queryDto.setCitycode(requestDto.getCitycode());
            queryDto.setPosid(requestDto.getPosid());
            queryDto.setPostype(requestDto.getPostype());
            queryDto.setProversion(requestDto.getProversion());

            // 获取终端参数
            DodopalResponse<AutoTerminalParameterResult> serviceResult = service.parameterDownload(queryDto);
            
            //获取终端 机具参数
            TerminalParameter ter = service.findTerminalParameter(requestDto.getPsamno());

            if (ResponseCode.SUCCESS.equals(serviceResult.getCode())) {

                AutoTerminalParameterResult resultDto = serviceResult.getResponseEntity();
                response.setParno(null);
                response.setListpars(null);
                response.setMercode(resultDto.getMercode());
                response.setMername(ter.getMchntname());
                response.setNetname(ter.getNetname());
                response.setProversion(resultDto.getProversion());

                String[] productArray = {"procode", "proname", "proprice", "citycode"};
                response.setListproduct(super.listToString(resultDto.getListproduct(), productArray, "|", "^"));

                String[] payawayArray = {"payid", "payname", "payservicerate", "payprocerate", "bankgatewaytype"};
                response.setListpayaway(super.listToString(resultDto.getListpayaway(), payawayArray, "|", "^"));

            } else {
                response.setRespcode(serviceResult.getCode());
            }

            // 签名
            sign(key, response);
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
            logger.info("自助终端参数下载接口：返回完整报文体：" + JSONObject.toJSONString(response));
        }
        return response;
    }

    /**
     * 退款接口
     */
    @RequestMapping("/sendRefund")
    public @ResponseBody
    CreatePayTransactionResponse sendRefund(HttpServletRequest request) {
        CreatePayTransactionResponse response = new CreatePayTransactionResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("退款接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("退款接口:接收到的jsondata参数:" + jsondata);
            }
            PayRefundRequest payRefundRequest = convertJsonToRequest(jsondata, PayRefundRequest.class);// 转换jsondata
            baseCheck(payRefundRequest);// 通用参数校验
            payRefundCheck(payRefundRequest);// 业务入参校验
            signCheck(key, payRefundRequest);// 验签

            //退款
            DodopalResponse<String> rTResponse = service.sendRefund(payRefundRequest.getPaymenttranNo(), payRefundRequest.getSource(), payRefundRequest.getUserid());

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())) {
                response.setPaymenttranNo(payRefundRequest.getPaymenttranNo());
            }
            response.setRespcode(rTResponse.getCode());
            //TODO 签名
            sign(key, response);
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
            logger.info("退款接口:返回：" + JSONObject.toJSONString(response));
        }
        return response;
    }

    /**
     * 根据交易流水号获取圈存订单信息
     */
    @RequestMapping("/getOrderByTradeOrder")
    public @ResponseBody
    LoadOrderResponse getOrderByTradeOrder(HttpServletRequest request) {
        LoadOrderResponse response = new LoadOrderResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("根据交易流水号获取圈存订单接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("根据交易流水号获取圈存订单接口:接收到的jsondata参数:" + jsondata);
            }
            PayRefundRequest loadOrder = convertJsonToRequest(jsondata, PayRefundRequest.class);// 转换jsondata
            baseCheck(loadOrder);// 通用参数校验
            getLoadOrderByTranCodeCheck(loadOrder);// 业务入参校验
            signCheck(key, loadOrder);// 验签

            //圈存订单号
            DodopalResponse<LoadAndTradeOrder> loadOrderNum = service.getLoadOrderAndTradeOrder(loadOrder.getPaymenttranNo());

            if (ResponseCode.SUCCESS.equals(loadOrderNum.getCode())) {
                response.setLoadorderNum(loadOrderNum.getResponseEntity().getOrderNumber());
                response.setTradeNum(loadOrderNum.getResponseEntity().getTradeNum());
         
                response.setPayid(StringUtils.trimToEmpty(loadOrderNum.getResponseEntity().getPayid()));
                response.setPaytype(loadOrderNum.getResponseEntity().getPaytype());
                response.setAmont(loadOrderNum.getResponseEntity().getAmont());
            }
            response.setRespcode(loadOrderNum.getCode());

            //TODO 签名
            sign(key, response);
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
            logger.info("根据交易流水号获取圈存订单接口:返回：" + JSONObject.toJSONString(response));
        }
        return response;
    }
    /**
     * 根据卡号获取圈存订单信息
     */
    @RequestMapping("/getOrderByCardNum")
    public @ResponseBody
    LoadOrderResponse getOrderByCardNum(HttpServletRequest request) {
        LoadOrderResponse response = new LoadOrderResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("根据卡号获取圈存订单接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("根据卡号获取圈存订单接口:接收到的jsondata参数:" + jsondata);
            }
            LoadOrderRequest loadOrder = convertJsonToRequest(jsondata, LoadOrderRequest.class);// 转换jsondata
            baseCheck(loadOrder);// 通用参数校验
            getLoadOrderCheck(loadOrder);// 业务入参校验
            signCheck(key, loadOrder);// 验签

            //圈存订单号
            DodopalResponse<List<LoadOrderQueryResponseDTO>> loadOrderNum = loadOrderService.findAvailableLoadOrdersByCardNum(loadOrder.getCardNum());

            if (ResponseCode.SUCCESS.equals(loadOrderNum.getCode()) && loadOrderNum.getResponseEntity() != null && loadOrderNum.getResponseEntity().size() > 0) {
                response.setLoadorderNum(loadOrderNum.getResponseEntity().get(0).getOrderNum());
                PayTraTransactionQueryDTO queryDTO = new PayTraTransactionQueryDTO();
                queryDTO.setOrderNumber(loadOrderNum.getResponseEntity().get(0).getOrderNum());
                queryDTO.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
                DodopalResponse<List<PayTraTransactionDTO>> payTranResult = payTransactionDelegate.findPayTraTransactionList(queryDTO);
                if(payTranResult.isSuccessCode()){
                    if(CollectionUtils.isNotEmpty(payTranResult.getResponseEntity())){
                        response.setPayid(StringUtils.trimToEmpty(payTranResult.getResponseEntity().get(0).getPayWay()));
                        response.setTradeNum(payTranResult.getResponseEntity().get(0).getTranCode());
                        response.setPaytype(payTranResult.getResponseEntity().get(0).getPayType());
                        response.setAmont(loadOrderNum.getResponseEntity().get(0).getChargeAmt());
                  }
                }   
            }
            response.setRespcode(loadOrderNum.getCode());

            //TODO 签名
            sign(key, response);
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
            logger.info("根据卡号获取圈存订单接口:返回：" + JSONObject.toJSONString(response));
        }
        return response;
    }
    

    /**
     * 生成交易流水(自助终端)
     */
    @RequestMapping("/createTranscation")
    public @ResponseBody
    CreatePayTransactionResponse createTranscation(HttpServletRequest request) {
        CreatePayTransactionResponse response = new CreatePayTransactionResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("创建交易流水:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("创建交易流水:接收到的jsondata参数:" + jsondata);
            }
            CreatePayTransactionRequest createPayTransactionRequest = convertJsonToRequest(jsondata, CreatePayTransactionRequest.class);// 转换jsondata
            baseCheck(createPayTransactionRequest);// 通用参数校验
            queryTranscationRequestCheck(createPayTransactionRequest);// 业务入参校验
            signCheck(key, createPayTransactionRequest);// 验签

            IcdcLoadOrderRequestDTO load = new IcdcLoadOrderRequestDTO();
            load.setCardNum(createPayTransactionRequest.getCardnum());
            load.setProductNum(createPayTransactionRequest.getProcode());
            load.setCustomerCode(createPayTransactionRequest.getCustcode());
            load.setSource(createPayTransactionRequest.getSource());
            //TODO 生成圈存订单
            DodopalResponse<LoadOrder> responseOrder = loadOrderService.bookLoadOrderForPaySys(load);
            if (!ResponseCode.SUCCESS.equals(responseOrder.getCode())) {
                response.setRespcode(responseOrder.getCode());
                return response;
            }
            //查询条件
            CreatePayTransactionBean requestBean = new CreatePayTransactionBean();
            requestBean.setAmont(Long.valueOf(createPayTransactionRequest.getAmont()));
            requestBean.setCardnum(createPayTransactionRequest.getCardnum());
            requestBean.setCustcode(createPayTransactionRequest.getCustcode());
            requestBean.setCusttype(createPayTransactionRequest.getCusttype());
            requestBean.setPayid(createPayTransactionRequest.getPayid());
            requestBean.setPayprocerate(Double.valueOf(createPayTransactionRequest.getPayprocerate()));
            requestBean.setPayservicerate(Double.valueOf(createPayTransactionRequest.getPayservicerate()));
            requestBean.setProcode(createPayTransactionRequest.getProcode());
            requestBean.setReqdate(createPayTransactionRequest.getReqdate());
            requestBean.setSource(createPayTransactionRequest.getSource());
            requestBean.setBusinessType(RateCodeEnum.IC_LOAD.getCode());
            requestBean.setGoodsName(RateCodeEnum.IC_LOAD.getCode() + createPayTransactionRequest.getAmont());

            //设置圈存订单号
            requestBean.setOrderNum(responseOrder.getResponseEntity().getOrderNum());
            //设置城市号
            requestBean.setCitycode(responseOrder.getResponseEntity().getCityCode());
            // 查询
            DodopalResponse<String> rTResponse = payTransactionService.createTranscation(requestBean);

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())) {
                response.setRespcode(rTResponse.getCode());
                response.setPaymenttranNo(rTResponse.getResponseEntity());
            } else {
                response.setRespcode(rTResponse.getCode());
            }
            //TODO 签名
            sign(key, response);
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
            logger.info("创建交易流水接口:返回：" + JSONObject.toJSONString(response));
        }
        return response;
    }

    /**
     * 生成交易流水（自助终端）参数校验
     * @param request
     */
    private void queryTranscationRequestCheck(CreatePayTransactionRequest request) {
        // 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = request.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_TRANNO_NULL);
        }
        // 客户号    String[40]   不为空
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }

    }

    /**
     * 退款验证
     * @param request
     */
    private void payRefundCheck(PayRefundRequest request) {
        // 退款交易流水号
        String tranNo = request.getPaymenttranNo();
        if (!DDPStringUtil.existingWithLength(tranNo, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_TRANNO_NULL);
        }
        // 来源
        String source = request.getSource();
        if (!SourceEnum.checkCodeExist(source)) {
            throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SOURCE_ERROR);
        }

    }

    /**
     * 根据卡号获取圈存订单信息参数校验
     * @param request
     */
    private void getLoadOrderCheck(LoadOrderRequest request) {
        // 退款交易流水号
        String cardNum = request.getCardNum();
        if (!DDPStringUtil.isPopulated(cardNum)) {
            throw new DDPException(ResponseCode.USERS_CARD_CODE_NOT_EMPTY);
        }
        // 城市CODE
        String cityCode = request.getCityCode();
        if (!DDPStringUtil.isPopulated(cityCode)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CITY_CODE_NULL);
        }

        // 商户号CODE
        String merCode = request.getMercode();
        if (!DDPStringUtil.isPopulated(merCode)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }

    }

    /**
     * 获取圈存订单信息参数校验
     * @param request
     */
    private void getLoadOrderByTranCodeCheck(PayRefundRequest request) {
        // 交易流水号
        String tranNo = request.getPaymenttranNo();
        if (!DDPStringUtil.existingWithLength(tranNo, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_TRANNO_NULL);
        }
    }

}
