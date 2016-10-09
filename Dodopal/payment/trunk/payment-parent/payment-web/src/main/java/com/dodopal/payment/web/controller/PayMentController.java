/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayStatusEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.gateway.AliPayMode;
import com.dodopal.payment.business.gateway.BasePayment;
import com.dodopal.payment.business.gateway.BasePaymentUtil;
import com.dodopal.payment.business.gateway.RSASignatureUtil;
import com.dodopal.payment.business.gateway.httpClient.HttpProtocolHandler;
import com.dodopal.payment.business.gateway.httpClient.HttpRequest;
import com.dodopal.payment.business.gateway.httpClient.HttpResponse;
import com.dodopal.payment.business.gateway.httpClient.HttpResultType;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayServiceRate;
import com.dodopal.payment.business.model.PaySubmit;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.service.AccountManagementService;
import com.dodopal.payment.business.service.ChangeStateService;
import com.dodopal.payment.business.service.PayCommonService;
import com.dodopal.payment.business.service.PayConfigService;
import com.dodopal.payment.business.service.PayServiceRateService;
import com.dodopal.payment.business.service.PayTraTransactionService;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.business.service.PayWayExternalService;
import com.dodopal.payment.business.service.PayWayGeneralService;
import com.dodopal.payment.business.service.PaymentService;
import com.dodopal.payment.business.util.SignUtils;

/**
 * @description 支付请求处理Controller	
 * Created by lenovo on 2015/8/5.=
 */
@Controller
public class PayMentController extends CommonController {
    private static Logger log = Logger.getLogger(PayMentController.class);
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PayConfigService payConfigService;
    @Autowired
    private ChangeStateService changeStateService;
    @Autowired
    private PayTranService payTranService;
    @Autowired
    private PayWayGeneralService wayGeneralService;
    @Autowired
    private PayWayExternalService payWayExternalService;
    @Autowired
    private AccountManagementService accountManagementService;
    @Autowired
    private PayTraTransactionService payTraTransactionService;
    @Autowired
    private PayCommonService payCommonService;
    @Autowired
    private PayServiceRateService payServiceRateService;
    @Autowired
    private PayFacade payFacade;

    /**
     * @param request
     * @return ModelAndView
     * @description 支付初始化信息页面展示
     * @comments 登录系统掉用paymentAction定向到支付发送请求页面
     */
    @RequestMapping("/payment")
    public ModelAndView payment(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("payment");
        return mv;
    }

    /**
     * @param request
     * @return ModelAndView
     * @description 调用支付接口完成支付请求
     * @comments 此处我们只要按照要求把参数进行有效封装通过URL
     * 传递给支付宝,我们不用关心支付的内部逻辑，只在回调内进行自己的业务逻辑封装
     * (所传参数 来源，客户类型，金额，支付方式Id，商户类型，商品名称，业务类型)
     */
    @RequestMapping("/pay")
    public ModelAndView submitPay(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("submitPay开始======================" + request);
        }
        DodopalResponse<String> returnInfo = new DodopalResponse<String>();
        ModelAndView mv = new ModelAndView();
        //校验参数不为空
        if (StringUtils.isBlank(request.getParameter("tranMoney"))) {
            returnInfo.setCode(ResponseCode.TRAN_MONEY_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("commodityName"))) {
            returnInfo.setCode(ResponseCode.COMMODITYNAME_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("customerType"))) {
            returnInfo.setCode(ResponseCode.CUSTOMERTYPE_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("customerNo"))) {
            returnInfo.setCode(ResponseCode.CUSTOMERNO_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("businessType"))) {
            returnInfo.setCode(ResponseCode.BUSINESSTYPE_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("source"))) {
            returnInfo.setCode(ResponseCode.SOURCE_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("merchantType"))) {
            returnInfo.setCode(ResponseCode.MERCHANTTYPE_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("payWayId"))) {
            returnInfo.setCode(ResponseCode.PAYWAYID_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        if (StringUtils.isBlank(request.getParameter("realTranMoney"))) {
            returnInfo.setCode(ResponseCode.REALTRANMONEY_NULL);
            returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
//        if (StringUtils.isBlank(request.getParameter("sign"))) {
//            mv.setViewName("failure");
//            mv.addObject("errorMessage", "参数格式错误，sign签名为空");
//            return mv;
//        }
        String submitHtml = "";
        try {
            //交易编号
            String tranCode = request.getParameter("tranCode");
            //交易名称
            String tranName = request.getParameter("tranName");
            //金额
            String tranMoney = request.getParameter("tranMoney");
            double tranAmount = Double.parseDouble(tranMoney);
            //实际交易金额
            String realTranMoney = request.getParameter("realTranMoney");
            double realTranAmount = Double.parseDouble(realTranMoney);
            //商品名称
            String commodityName = request.getParameter("commodityName");
            //客户类型
            String customerType = request.getParameter("customerType");
            //客户号
            String merOrUserCode = request.getParameter("customerNo");
            //订单号
            String orderNumber = request.getParameter("orderNumber");
            //业务类型
            String businessType = request.getParameter("businessType");
            //来源
            String source = request.getParameter("source");
            //商户类型，是否外接
            String merchantType = request.getParameter("merchantType");
            //支付方式id
            String payWayId = request.getParameter("payWayId");
            //返回路径
            String returnUrl = request.getParameter("returnUrl");
            //回调路径
            String notifyUrl = request.getParameter("notifyUrl");
            //是否需要进行混合支付 0 非混合 1 混合 混合支付首先拿到总金额 和账户里的金额进行相减，不够的进行支付加值到账户
            String isMix = request.getParameter("isMixed");
            String operateId = request.getParameter("operateId");
            String sign = request.getParameter("sign");
            if (log.isInfoEnabled()) {
                log.info("=======传入签名========" + sign);
            }
            String genId = request.getParameter("genId");
            //验证签名
            if (!"".equals(sign) && sign != null) {
                Map<String, String> signMap = new LinkedHashMap<String, String>();
                signMap.put("tranCode", tranCode);
                signMap.put("tranName", tranName);
                signMap.put("tranMoney", tranMoney);
                signMap.put("realTranMoney", realTranMoney);
                signMap.put("commodityName", commodityName);
                signMap.put("customerType", customerType);
                signMap.put("customerNo", merOrUserCode);
                signMap.put("orderNumber", orderNumber);
                signMap.put("businessType", businessType);
                signMap.put("source", source);
                signMap.put("merchantType", merchantType);
                signMap.put("payWayId", payWayId);
                signMap.put("returnUrl", returnUrl);
                signMap.put("notifyUrl", notifyUrl);
                signMap.put("isMixed", isMix);
                signMap.put("operateId", operateId);
                String checkSign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
                if (log.isInfoEnabled()) {
                    log.info("=======新数据签名========" + checkSign);
                }
                if (!sign.equals(checkSign)) {
                    returnInfo.setCode(ResponseCode.SIGNE_EROR);
                    returnInfo.setNewMessage(ResponseCode.PARAMETER_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
            }
            if (log.isInfoEnabled()) {
                log.info("isMix======================" + isMix);
            }
            if (log.isInfoEnabled()) {
                log.info("submitPay接收参数结束======================");
            }
            if ("1".equals(isMix)) {
                DodopalResponse<AccountFundDTO> balance = accountManagementService.findAccountBalance(customerType, merOrUserCode);
                AccountFundDTO balanceDto = balance.getResponseEntity();
                if (balanceDto == null) {
                    returnInfo.setCode(ResponseCode.ACCOUNTBALANCE_NULL);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                if (log.isInfoEnabled()) {
                    log.info("查询出的可用余额为" + balanceDto.getAccountMoney());
                }
//            BigDecimal bd = new BigDecimal(tranMoney-balanceDto.getAccountMoney());
//            tranMoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                realTranAmount = realTranAmount - Math.round(balanceDto.getAccountMoney() * 100);
                tranAmount = realTranAmount;
                if (log.isInfoEnabled()) {
                    log.info("混合支付时需要支付的金额为" + realTranMoney);
                }
            }
            //外接支付表或通用支付表的configid
            String payId = "";
            //外接支付表或通用支付表id
            String payCommonId = "";
            //支付方式类型
            String payWayKind = "";
            //获取服务费率
            double rate = 0;
            String payServiceType = "";
            if ("true".equals(merchantType)) {
                //外接支付表取出pay_config_id
                PayWayExternal payWayExternal = payWayExternalService.queryPayConfigId(payWayId);
                if (payWayExternal == null) {
                    returnInfo.setCode(ResponseCode.PAYWAY_NULL);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                payId = payWayExternal.getPayConfigId();
                payCommonId = payWayExternal.getId();
                rate = payWayExternal.getPayServiceRate();
                if (ActivateEnum.DISABLE.getCode().equals(payWayExternal.getActivate())) {
                    returnInfo.setCode(ResponseCode.PAYWAY_DISABLED);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                if ("".equals(payId) || payId == null) {
                    returnInfo.setCode(ResponseCode.PAYWAY_ERROR);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                payWayKind = "GW_OUT";
                payServiceType = ServiceRateTypeEnum.PERMILLAGE.getCode();
            } else {
                //通用支付表取出pay_config_id
                PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payWayId);
                if (payWayGeneral == null) {
                    returnInfo.setCode(ResponseCode.PAYWAY_NULL);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                if (MerUserTypeEnum.PERSONAL.getCode().equals(customerType)) {
                    rate = 0;
                    payServiceType = ServiceRateTypeEnum.PERMILLAGE.getCode();
                } else {
                    PayServiceRate payServiceRate = payServiceRateService.getServiceTypeAndRate(payWayGeneral, businessType, Math.round(tranAmount));
                    if (payServiceRate == null) {
                        returnInfo.setCode(ResponseCode.NO_FEE);
                        returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                        mv.setViewName("failure");
                        mv.addObject("errorMessage", returnInfo.getMessage());
                        return mv;
                    }
                    rate = payServiceRate.getRate();
                    payServiceType = payServiceRate.getPayServiceType();
                }

                payId = payWayGeneral.getPayConfigId();
                payCommonId = payWayGeneral.getId();
                if (ActivateEnum.DISABLE.getCode().equals(payWayGeneral.getActivate())) {
                    returnInfo.setCode(ResponseCode.PAYWAY_DISABLED);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                if ("".equals(payId) || payId == null) {
                    returnInfo.setCode(ResponseCode.PAYWAY_ERROR);
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                payWayKind = "GW_ALL";
            }
            //根据交易编号获取支付配置信息
            PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payId);
            if (payConfig == null) {
                returnInfo.setCode(ResponseCode.PAY_CONFIG_NULL);
                returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                mv.setViewName("failure");
                mv.addObject("errorMessage", returnInfo.getMessage());
                return mv;
            }
            //用来存储到数据库的金额 前端已经做过处理，这里将不在进行处理
//        long amountMoney = Math.round(tranMoney * 10000);
            //计算服务费
//        BigDecimal bd = new BigDecimal(tranMoney * rate);
            //服务费四舍五入 两位小数
//        double realServiceMoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //服务费以分存入数据库
//        long serviceMoney = Math.round(realServiceMoney * 100);
            if (tranCode == null || "".equals(tranCode)) {
                tranCode = payTranService.createPayTranCode(businessType, "");
            }
            if (orderNumber == null || "".equals(orderNumber)) {
                orderNumber = tranCode;
            }
            if (RateCodeEnum.ACCT_RECHARGE.getCode().equals(businessType)) {
                tranName = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            if ((RateCodeEnum.YKT_RECHARGE.getCode().equals(businessType) && (PayTypeEnum.PAY_ONLINE.getCode().equals(payConfig.getPayType()) || PayTypeEnum.PAY_BANK.getCode().equals(payConfig.getPayType())))) {
                tranName = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            if ((RateCodeEnum.YKT_RECHARGE.getCode().equals(businessType) && (PayTypeEnum.PAY_ACT.getCode().equals(payConfig.getPayType())))) {
                tranName = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();
            }
            if ((RateCodeEnum.IC_LOAD.getCode().equals(businessType) && (PayTypeEnum.PAY_ONLINE.getCode().equals(payConfig.getPayType()) || PayTypeEnum.PAY_BANK.getCode().equals(payConfig.getPayType())))) {
                tranName = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            if ((RateCodeEnum.IC_LOAD.getCode().equals(businessType) && PayTypeEnum.PAY_ACT.getCode().equals(payConfig.getPayType()))) {
                tranName = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();
            }
            PaySubmit paySubmit = new PaySubmit();
            paySubmit.setTranCode(tranCode);
            paySubmit.setTranName(tranName);
            paySubmit.setTranMoney(tranAmount);
            paySubmit.setRealTranMoney(realTranAmount);
            paySubmit.setCommodityName(commodityName);
            paySubmit.setCustomerType(customerType);
//            paySubmit.setAmountMoney(amountMoney);
            paySubmit.setMerOrUserCode(merOrUserCode);
            paySubmit.setOrderNumber(orderNumber);
            paySubmit.setBusinessType(businessType);
            paySubmit.setSource(source);
            paySubmit.setMerchantType(merchantType);
            paySubmit.setPayWayId(payWayId);
            paySubmit.setPayId(payId);
            paySubmit.setPayCommonId(payCommonId);
            paySubmit.setPayWayKind(payWayKind);
//          paySubmit.setServiceMoney(serviceMoney);
            paySubmit.setRate(rate);
//            paySubmit.setRealServiceMoney(realServiceMoney);
            paySubmit.setReturnUrl(returnUrl);
            paySubmit.setNotifyUrl(notifyUrl);
            paySubmit.setCreateUser(operateId);
            paySubmit.setPayServiceType(payServiceType);
            paySubmit.setGenId(genId);
            //如果是一卡通充值，账户支付方式直接返回
            if (TranTypeEnum.ACCOUNT_CONSUMPTION.getCode().equals(tranName) && RateCodeEnum.YKT_RECHARGE.getCode().equals(businessType)) {
                Map<String, String> signMap = new LinkedHashMap<String, String>();
                long timemills = System.currentTimeMillis();
                signMap.put("orderNum", orderNumber);
                signMap.put("notify_time", String.valueOf(timemills));
                String returnsign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
                signMap.put("sign", returnsign);
                signMap.put("businessType", businessType);
                String sbHtml = BasePayment.buildRequest(signMap, returnUrl);
                mv.setViewName("paysubmit");
                mv.addObject("submitHtml", sbHtml);
                return mv;
            }
            //如果是圈存并且是账户支付直接进行冻结扣款操作
            if ((RateCodeEnum.IC_LOAD.getCode().equals(businessType) && (PayTypeEnum.PAY_ACT.getCode().equals(payConfig.getPayType())))) {
                DodopalResponse<Boolean> re = freezeAndDeductBeforePay(paySubmit);
                if (!ResponseCode.SUCCESS.equals(re.getCode())) {
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
                Map<String, String> signMap = new LinkedHashMap<String, String>();
                long timemills = System.currentTimeMillis();
                signMap.put("orderNum", orderNumber);
                signMap.put("notify_time", String.valueOf(timemills));
                String returnsign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
                signMap.put("sign", returnsign);
                signMap.put("businessType", businessType);
                String sbHtml = BasePayment.buildRequest(signMap, returnUrl);
                mv.setViewName("paysubmit");
                mv.addObject("submitHtml", sbHtml);
                return mv;
            }
            DodopalResponse<String> returnMessage = accountManagementService.checkRecharge(customerType, merOrUserCode, Math.round(realTranAmount));
            if (!ResponseCode.SUCCESS.equals(returnMessage.getCode())) {
                returnInfo.setCode(returnMessage.getCode());
                returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                mv.setViewName("failure");
                mv.addObject("errorMessage", returnInfo.getMessage());
                mv.addObject("fengkongfazhi", returnMessage.getMessage());
                return mv;
            }
            //根据支付配置信息实例化支付类(支付宝或者财付通)
            BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
            //创建提交支付表单
            submitHtml = changeStateService.getSubmitHtml(paySubmit, payConfig, basepayment, request, response);
            if (log.isInfoEnabled()) {
                log.info("提交表单为====" + submitHtml);
            }
        } catch (DDPException ex) {
            ex.printStackTrace();
            if (ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR.equals(ex.getCode())) {
                log.info("============账户充值连接账户系统异常======");
                mv.setViewName("failure");
                mv.addObject("errorMessage", "连接账户系统异常");
            } else {
                returnInfo.setCode(ex.getCode());
                returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                mv.setViewName("failure");
                mv.addObject("errorMessage", returnInfo.getMessage());
            }
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            if (log.isInfoEnabled()) {
                log.info("========支付提交失败============" + e.getMessage());
            }
            returnInfo.setCode(ResponseCode.PAY_SUBMIT_ERROR);
            returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        mv.setViewName("paysubmit");
        mv.addObject("submitHtml", submitHtml);
        return mv;
    }

    /**
     * @param request
     * @return ModelAndView
     * @description 提供支付请求确认以后(第三方)调用
     * @comments 获取支付宝返回数据，验证，业务逻辑的状态位回填 ，跳转自定义成功页面
     */
    @RequestMapping("/payreturn")
//    @RequestMapping(value = "/payreturn", method = RequestMethod.GET)
    public ModelAndView payreturn(HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            log.info("支付返回方法开始");
            log.info("========payreturn=====打印交易编号==========  " + request.getParameter("paymentTranNo"));
        }
        ModelAndView mv = new ModelAndView();
        DodopalResponse<String> returnInfo = new DodopalResponse<String>();
        //获取传递回来的交易编号
        String tradCode = request.getParameter("paymentTranNo");
        Payment payment = paymentService.queryPaymentInfo(tradCode);
        String payWayId = "";
        String trade_no = "";
        if ("GW_ALL".equals(payment.getPayWayKind())) {
            PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayGeneral.getPayConfigId();
        } else {
            PayWayExternal payWayExternal = payWayExternalService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayExternal.getPayConfigId();
        }
        //根据payWayId获取支付配置信息
        PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayId);
        //根据支付配置信息实例化支付类(支付宝或者财付通)
        BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
        //获取返回的签名信息，用来回填表返回签名信息字段
        String sign = request.getParameter("sign");
        //获取支付状态
        String tradeStatus = "";
        if ("0".equals(payConfig.getBankGatewayType())) {
            tradeStatus = request.getParameter("trade_status");
            //支付宝回吐交易流水号
            trade_no = request.getParameter("trade_no");
        } else {
            tradeStatus = request.getParameter("trade_state");
            //财付通回吐交易流水号
            trade_no = request.getParameter("transaction_id");
        }
        if (log.isInfoEnabled()) {
            log.info("[payreturn返回状态为======" + tradeStatus + "]" + "[交易流水号" + request.getParameter("paymentTranNo") + "]");
        }
        //获取返回
        Map requestParams = request.getParameterMap();
        //根据流水号查询交易流水信息
        PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
        if (log.isInfoEnabled()) {
            log.info("==========交易流水状态==============" + pt.getTranInStatus());
        }
        //对返回的数据进行验签操作
        try {
            if (basepayment.verifySign(payConfig, sign, requestParams)) {
                if (basepayment.isPaySuccess(request)) {
                    if (TranTypeEnum.ACCOUNT_RECHARGE.getCode().equals(pt.getTranName())) {
                        if (!TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus()) && !TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                            PayTraTransaction payTraTransaction = new PayTraTransaction();
                            payTraTransaction.setTranCode(tradCode);
                            payTraTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
                            payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
                            Payment payments = new Payment();
                            payments.setTranCode(tradCode);
                            payments.setPageCallbackStatus(tradeStatus);
                            payments.setPageCallbackDate(new Date());
                            payments.setAcceptCiphertext(JSON.toJSONString(requestParams, true));
                            payments.setPayStatus(PayStatusEnum.PAID_SUCCESS.getCode());
                            payments.setTradeNo(trade_no);
                            changeStateService.changeState(payments, payTraTransaction);
                            //调用账户充值接口
                            DodopalResponse<String> returnMessage = new DodopalResponse<String>();
                            try {
                                returnMessage = accountManagementService.accountRecharge(pt.getUserType(), pt.getMerOrUserCode(), pt.getTranCode(), pt.getAmountMoney(), pt.getCreateUser());
                            } catch (DDPException ex) {
                                ex.printStackTrace();
                                log.info("==================DDPEXCEPTION==========" + ex.getCode() == null || "".equals(ex.getCode()) ? "" : ex.getCode());
                                if (ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR.equals(ex.getCode())) {
                                    log.info("============账户充值连接账户系统异常======");
                                    PayTraTransaction ptt = new PayTraTransaction();
                                    ptt.setTranCode(tradCode);
                                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                    payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                    mv.setViewName("failure");
                                    mv.addObject("message", "账户加值失败");
                                    mv.addObject("errorMessage", "连接账户系统异常");
                                } else {
                                    returnInfo.setCode(ex.getCode());
                                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                                    mv.setViewName("failure");
                                    mv.addObject("message", "账户加值失败");
                                    mv.addObject("errorMessage", returnInfo.getMessage());
                                }
                                return mv;
                            }
                            if (ResponseCode.SUCCESS.equals(returnMessage.getCode()) || ResponseCode.ACCOUNT_CHANGE_EXIT.equals(returnMessage.getCode())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    String sbHtml = getReturnHtml(pt);
                                    mv.addObject("redirectHtml", sbHtml);
                                    mv.setViewName("payresult");
                                    return mv;
                                }
                                //如果是圈存，直接走支付网关冻结扣款接口
                                if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                                    DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                                    if (!ResponseCode.SUCCESS.equals(re.getCode())) {
                                        mv.setViewName("failure");
                                        mv.addObject("message", "圈存失败");
                                        mv.addObject("errorMessage", returnInfo.getMessage());
                                        return mv;
                                    }
                                    String sbHtml = getReturnHtml(pt);
                                    mv.addObject("message", "圈存成功");
                                    mv.addObject("redirectHtml", sbHtml);
                                    mv.setViewName("payresult");
                                    return mv;
                                }
                                mv.addObject("message","账户加值成功");
                                mv.setViewName("payresult");
                                return mv;
                            } else {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                returnInfo.setCode(ResponseCode.ACCOUNT_VALUE_ADDED_FAIL);
                                returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                                mv.setViewName("failure");
                                mv.addObject("message", "账户加值失败");
                                mv.addObject("errorMessage", returnInfo.getMessage());
                                return mv;
                            }
                        } else {
                            if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    Map<String, String> signMap = new LinkedHashMap<String, String>();
                                    long timemills = System.currentTimeMillis();
                                    signMap.put("orderNum", pt.getOrderNumber());
                                    signMap.put("notify_time", String.valueOf(timemills));
                                    String returnsign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
                                    signMap.put("sign", returnsign);
                                    signMap.put("businessType", pt.getBusinessType());
                                    String sbHtml = BasePayment.buildRequest(signMap, pt.getPageCallbackUrl());
                                    mv.addObject("redirectHtml", sbHtml);
                                    mv.setViewName("payresult");
                                    return mv;
                                }
                                //如果是圈存，直接走支付网关冻结扣款接口
                                if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                                    DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                                    if (!ResponseCode.SUCCESS.equals(re.getCode())) {
                                        mv.setViewName("failure");
                                        mv.addObject("message", "圈存失败");
                                        mv.addObject("errorMessage", returnInfo.getMessage());
                                        return mv;
                                    }
                                    String sbHtml = getReturnHtml(pt);
                                    mv.addObject("message", "圈存成功");
                                    mv.addObject("redirectHtml", sbHtml);
                                    mv.setViewName("payresult");
                                    return mv;
                                }
                                mv.addObject("message","账户加值成功");
                                mv.setViewName("payresult");
                                return mv;
                            }
                            if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                returnInfo.setCode(ResponseCode.ACCOUNT_VALUE_ADDED_FAIL);
                                returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                                mv.setViewName("failure");
                                mv.addObject("message", "账户加值失败");
                                mv.addObject("errorMessage", returnInfo.getMessage());
                                return mv;
                            }
                        }
                    }
                } else {
                    returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                    mv.setViewName("failure");
                    mv.addObject("errorMessage", returnInfo.getMessage());
                    return mv;
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("=========支付返回验签失败===============");
                }
                returnInfo.setCode(ResponseCode.CHECKSIGN_ERROR);
                returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
                mv.setViewName("failure");
                mv.addObject("errorMessage", returnInfo.getMessage());
                return mv;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付返回处理失败", e);
            returnInfo.setCode(ResponseCode.PAY_HANDLE_ERROR);
            returnInfo.setNewMessage(ResponseCode.PAY_ERROR);
            mv.setViewName("failure");
            mv.addObject("errorMessage", returnInfo.getMessage());
            return mv;
        }
        return mv;
    }

    /**
     * @param request
     * @return void
     * @description 提供支付以后(第三方)通知调用
     * @comments 获取支付宝返回数据，验证，业务逻辑的状态位回填
     */
    @RequestMapping("/paynotify")
//    @RequestMapping(value = "/paynotify", method = RequestMethod.POST)
    public void paynotify(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        if (log.isInfoEnabled()) {
            log.info("========paynotify=====支付宝服务器端通知开始==========  ");
            log.info("========paynotify=====打印交易编号==========  " + request.getParameter("paymentTranNo"));
            log.info("支付回调通知方法开始================");
            log.info("=========交易流水号=============" + request.getParameter("paymentTranNo"));
        }
        //获取传递回来的交易编号
        String tradCode = request.getParameter("paymentTranNo");
        //根据交易编号查询出支付方式
        Payment payment = paymentService.queryPaymentInfo(tradCode);
        String payWayId = "";
        if ("GW_ALL".equals(payment.getPayWayKind())) {
            PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayGeneral.getPayConfigId();
        } else {
            PayWayExternal payWayExternal = payWayExternalService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayExternal.getPayConfigId();

        }
        if (log.isInfoEnabled()) {
            log.info("==========paynotify根据payWayId获取payConfig==============" + payWayId);
        }
        //根据payWayId获取支付配置信息
        PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayId);
        if (log.isInfoEnabled()) {
            log.info("==========paynotify根据payConfig获取BasePayment开始==============");
        }
        //根据支付配置信息实例化支付类(支付宝或者财付通)
        BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
        if (log.isInfoEnabled()) {
            log.info("==========paynotify根据payConfig获取BasePayment结束==============");
        }
        //获取返回的签名信息，用来回填表返回签名信息字段
        String sign = request.getParameter("sign");
        if (log.isInfoEnabled()) {
            log.info("========paynotify支付网关类型===========" + payConfig.getBankGatewayType());
        }
        //获取支付状态
        String tradeStatus = "";
        Date noticeDate = null;
        String trade_no ="";
        if ("0".equals(payConfig.getBankGatewayType())) {
            tradeStatus = request.getParameter("trade_status");
            trade_no = request.getParameter("trade_no");
            //获取通知返回时间
            String returnDate = request.getParameter("notify_time");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            noticeDate = sdf.parse(returnDate);
        } else {
            tradeStatus = request.getParameter("trade_state");
            trade_no = request.getParameter("transaction_id");
            noticeDate = new Date();
        }
        if (log.isInfoEnabled()) {
            log.info("[paynotify返回状态为======" + tradeStatus + "]" + "[交易流水号" + request.getParameter("paymentTranNo") + "]");
        }

        if (log.isInfoEnabled()) {
            log.info("=======paynotify获取返回参数开始==========");
        }
        //获取返回
        Map requestParams = request.getParameterMap();
        if (log.isInfoEnabled()) {
            log.info("=======paynotify获取返回参数结束==========");
            log.info("=======paynotify查询paytransaction开始==========");
        }
        PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
        if (log.isInfoEnabled()) {
            log.info("=========paynotify查询paytransaction结束============");
        }
        if (log.isInfoEnabled()) {
            log.info("==========paynotify交易流水状态==============" + pt.getTranInStatus());
        }
        try {
            //对返回的数据进行验签操作
            if (basepayment.verifySign(payConfig, sign, requestParams)) {
                if (basepayment.isPaySuccess(request)) {
                    //根据流水号查询交易流水信息
                    if (log.isInfoEnabled()) {
                        log.info("=========paynotify=tranName===========" + pt.getTranName());
                    }
                    if (TranTypeEnum.ACCOUNT_RECHARGE.getCode().equals(pt.getTranName())) {
                        if (!TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus()) && !TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                            if (log.isInfoEnabled()) {
                                log.info("paynotify======进入账户加值方法前==================");
                            }
                            PayTraTransaction payTraTransaction = new PayTraTransaction();
                            payTraTransaction.setTranCode(tradCode);
                            payTraTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
                            payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
                            Payment payments = new Payment();
                            payments.setTranCode(tradCode);
                            payments.setServiceNoticeStatus(tradeStatus);
                            payments.setServiceNoticeDate(noticeDate);
                            payments.setAcceptCiphertext(JSON.toJSONString(requestParams, true));
                            payments.setPayStatus(PayStatusEnum.PAID_SUCCESS.getCode());
                            payments.setTradeNo(trade_no);
                            changeStateService.changeState(payments, payTraTransaction);
                            //调用账户充值接口
                            DodopalResponse<String> returnMessage = new DodopalResponse<String>();
                            try {
                                if (log.isInfoEnabled()) {
                                    log.info("paynotify======账户加值方法开始==================");
                                }
                                returnMessage = accountManagementService.accountRecharge(pt.getUserType(), pt.getMerOrUserCode(), pt.getTranCode(), pt.getAmountMoney(), pt.getCreateUser());
                                if (log.isInfoEnabled()) {
                                    log.info("paynotify======账户加值方法结束返回==================" + returnMessage.getCode());
                                }
                            } catch (DDPException ex) {
                                if (log.isInfoEnabled()) {
                                    log.info("==================DDPEXCEPTION==========" + ex.getCode() == null || "".equals(ex.getCode()) ? "" : ex.getCode());
                                }
                                ex.printStackTrace();
                                if (ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR.equals(ex.getCode())) {
                                    if (log.isInfoEnabled()) {
                                        log.info("============账户充值连接账户系统异常======");
                                    }
                                    PayTraTransaction ptt = new PayTraTransaction();
                                    ptt.setTranCode(tradCode);
                                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                    payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                    if (log.isInfoEnabled()) {
                                        log.info("==================账户连接异常回调产品库处理开始============");
                                        log.info("==================pt.getBusinessType()============" + pt.getBusinessType());
                                    }
                                    if (!RateCodeEnum.ACCT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                        //更改订单状态，调用产品库
                                        httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                    }
                                } else {
                                    returnMessage.setCode(ex.getCode());
                                }
                                return;
                            }
                            if (ResponseCode.SUCCESS.equals(returnMessage.getCode()) || ResponseCode.ACCOUNT_CHANGE_EXIT.equals(returnMessage.getCode())) {
                                if (log.isInfoEnabled()) {
                                    log.info("=======账户加值成功处理========");
                                }
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                //发送http请求调用url处理函数
                                if (log.isInfoEnabled()) {
                                    log.info("==================账户加值成功走的调用产品库============");
                                    log.info("========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    //更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                }
                                //如果是圈存，直接走支付网关冻结扣款接口
                                if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                                    DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                                    if (ResponseCode.SUCCESS.equals(re.getCode())) {
                                        //更改订单状态，调用产品库
                                        httpToProduct(pt, ResponseCode.SUCCESS);
                                    }
                                }
                            } else {
                                if (log.isInfoEnabled()) {
                                    log.info("=======账户加值失败处理========");
                                }
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                if (log.isInfoEnabled()) {
                                    log.info("==================账户加值失败走的调用产品库============");
                                    log.info("========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    //发送http请求调用url处理函数,更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                }
                            }
                        } else {
                            if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                if (log.isInfoEnabled()) {
                                    log.info("=======后账户加值成功处理========");
                                    log.info("========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    if (log.isInfoEnabled()) {
                                        log.info("==================后走账户充值成功走的调用产品库============");
                                    }
                                    //发送http请求调用url处理函数,更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                }
                                //如果是圈存，直接走支付网关冻结扣款接口
                                if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                                    DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                                    if (ResponseCode.SUCCESS.equals(re.getCode())) {
                                        //更改订单状态，调用产品库
                                        httpToProduct(pt, ResponseCode.SUCCESS);
                                    }
                                }
                            }
                            if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                if (log.isInfoEnabled()) {
                                    log.info("=======后账户加值失败处理========");
                                    log.info("========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    //发送http请求调用url处理函数
                                    if (log.isInfoEnabled()) {
                                        log.info("==================后走账户充值失败走的调用产品库============");
                                        log.info("==================回调产品库处理开始============");
                                    }
                                    //更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                }
                            }
                        }
                    }
                    String strHtml = "success";
                    if ("0".equals(payConfig.getBankGatewayType())) {
                        response.getWriter().write(strHtml);
                    } else {
                        PrintWriter out = response.getWriter();
                        out.print(strHtml);
                        out.flush();
                        out.close();
                    }
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("=======paynotify返回验签失败===========" + tradCode);
                }
            }
        } catch (DDPException e) {
            log.error("回写success失败，失败原因:", e);
            e.printStackTrace();

        } catch (IOException e) {
            log.error("回写success失败，失败原因:", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付回调处理发生错误，错误原因:", e);
        }
    }

    /**
     * @param request
     * @return void
     * @description 提供手机端支付以后(第三方)通知调用
     * @comments 获取支付宝返回数据，验证，业务逻辑的状态位回填
     */
    @RequestMapping("/mobilepaynotify")
    //@RequestMapping(value = "/mobilepaynotify", method = RequestMethod.POST)
    public void mobilepaynotify(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        if (log.isInfoEnabled()) {
            log.info("支付回调通知方法开始================");
        }

        log.info(request.getRequestURL().toString());
        log.info("mobilepaynotify第三方回调开始：：====================");
        if (true) {
            log.info("debug: reqeust information.....");
            log.info("accept :" + request.getHeader("accept"));
            log.info("RemoteHost :" + request.getRemoteHost());
            log.info("url :");
            log.info("	" + request.getRequestURL());
            Enumeration<String> enu = request.getParameterNames();
            if (enu.hasMoreElements()) {
                log.info("parameter :");
            }
            while (enu.hasMoreElements()) {
                String element = (String) enu.nextElement();
                log.info("	" + element + " : " + request.getParameter(element));

            }
        }

        //获取传递回来的交易编号
        String tradCode = request.getParameter("paymentTranNo");
        //根据交易编号查询出支付方式
        Payment payment = paymentService.queryPaymentInfo(tradCode);
        String payWayId = "";
        if ("GW_ALL".equals(payment.getPayWayKind())) {
            PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayGeneral.getPayConfigId();
        } else {
            PayWayExternal payWayExternal = payWayExternalService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayExternal.getPayConfigId();

        }
        if (log.isInfoEnabled()) {
            log.info("==========mobilepaynotify根据payWayId获取payConfig==============" + payWayId);
        }
        //根据payWayId获取支付配置信息
        PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayId);
        String sign = request.getParameter("sign");
        //获取支付状态 TODO 确认手机端支付标识
        String tradeStatus = request.getParameter("trade_status");
        String trade_no = request.getParameter("trade_no");
        if (log.isInfoEnabled()) {
            log.info("mobilepaynotify回调返回状态为======" + tradeStatus);
        }
        //获取通知返回时间
        String returnDate = request.getParameter("notify_time");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date noticeDate = sdf.parse(returnDate);
        //获取返回
        Map requestParams = request.getParameterMap();
        PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
        try {
            //对返回的数据进行验签操作
            if (RSASignatureUtil.verify(RSASignatureUtil.TEST_PUBLICK_KEY, sign, SignUtils.createLinkString(requestParams, new String[]{"sign_type", "sign", "paymentTranNo"}), "UTF-8")) {
                //判断支付成功还是失败
                AliPayMode ali = new AliPayMode();
                if (ali.isPaySuccess(request)) {
                    //根据流水号查询交易流水信息
                    if (TranTypeEnum.ACCOUNT_RECHARGE.getCode().equals(pt.getTranType())) {
                        if (!TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus()) && !TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                            PayTraTransaction payTraTransaction = new PayTraTransaction();
                            payTraTransaction.setTranCode(tradCode);
                            payTraTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
                            payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
                            Payment payments = new Payment();
                            payments.setTranCode(tradCode);
                            payments.setServiceNoticeStatus(tradeStatus);
                            payments.setServiceNoticeDate(noticeDate);
                            payments.setAcceptCiphertext(JSON.toJSONString(requestParams, true));
                            payments.setPayStatus(PayStatusEnum.PAID_SUCCESS.getCode());
                            payments.setTradeNo(trade_no);
                            changeStateService.changeState(payments, payTraTransaction);
                            //调用账户充值接口
                            DodopalResponse<String> returnMessage = new DodopalResponse<String>();
                            try {
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify======账户加值方法开始==================");
                                }
                                returnMessage = accountManagementService.accountRecharge(pt.getUserType(), pt.getMerOrUserCode(), pt.getTranCode(), pt.getAmountMoney(), pt.getCreateUser());
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify======账户加值方法结束返回==================" + returnMessage.getCode());
                                }
                            } catch (DDPException ex) {
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify==================DDPEXCEPTION==========" + ex.getCode() == null || "".equals(ex.getCode()) ? "" : ex.getCode());
                                }
                                ex.printStackTrace();
                                if (ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR.equals(ex.getCode())) {
                                    if (log.isInfoEnabled()) {
                                        log.info("mobilepaynotify============账户充值连接账户系统异常======");
                                    }
                                    PayTraTransaction ptt = new PayTraTransaction();
                                    ptt.setTranCode(tradCode);
                                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                    payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                    if (log.isInfoEnabled()) {
                                        log.info("mobilepaynotify==================账户连接异常回调产品库处理开始============");
                                        log.info("mobilepaynotify==================pt.getBusinessType()============" + pt.getBusinessType());
                                    }
                                    if (!RateCodeEnum.ACCT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                        //更改订单状态，调用产品库
                                        httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                    }
                                } else {
                                    returnMessage.setCode(ex.getCode());
                                }
                                return;
                            }
                            if (ResponseCode.SUCCESS.equals(returnMessage.getCode()) || ResponseCode.ACCOUNT_CHANGE_EXIT.equals(returnMessage.getCode())) {
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify=======账户加值成功处理========");
                                }
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                //发送http请求调用url处理函数
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify==================账户加值成功走的调用产品库============");
                                    log.info("mobilepaynotify========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    //更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                }
                                //如果是圈存，直接走支付网关冻结扣款接口
                                if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                                    DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                                    if (ResponseCode.SUCCESS.equals(re.getCode())) {
                                        //更改订单状态，调用产品库
                                        httpToProduct(pt, ResponseCode.SUCCESS);
                                    }
                                }
                            } else {
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify=======账户加值失败处理========");
                                }
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify==================账户加值失败走的调用产品库============");
                                    log.info("mobilepaynotify========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    //发送http请求调用url处理函数,更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                }
                            }
                        } else {
                            if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                payTraTransactionService.UpdateInStatesByTransfer(ptt);
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify=======后账户加值成功处理========");
                                    log.info("mobilepaynotify========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    if (log.isInfoEnabled()) {
                                        log.info("mobilepaynotify==================后走账户充值成功走的调用产品库============");
                                    }
                                    //发送http请求调用url处理函数,更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                                }
                                //如果是圈存，直接走支付网关冻结扣款接口
                                if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                                    DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                                    if (ResponseCode.SUCCESS.equals(re.getCode())) {
                                        //更改订单状态，调用产品库
                                        httpToProduct(pt, ResponseCode.SUCCESS);
                                    }
                                }
                            }
                            if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                                PayTraTransaction ptt = new PayTraTransaction();
                                ptt.setTranCode(tradCode);
                                ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                if (log.isInfoEnabled()) {
                                    log.info("mobilepaynotify=======后账户加值失败处理========");
                                    log.info("mobilepaynotify========pt.getBusinessType()=======" + pt.getBusinessType());
                                }
                                if (RateCodeEnum.YKT_RECHARGE.getCode().equals(pt.getBusinessType())) {
                                    //发送http请求调用url处理函数
                                    if (log.isInfoEnabled()) {
                                        log.info("mobilepaynotify==================后走账户充值失败走的调用产品库============");
                                        log.info("mobilepaynotify==================回调产品库处理开始============");
                                    }
                                    //更改订单状态，调用产品库
                                    httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                                }
                            }
                        }
                    }
                    String strHtml = "success";
                    if ("0".equals(payConfig.getBankGatewayType())) {
                        response.getWriter().write(strHtml);
                    } else {
                        PrintWriter out = response.getWriter();
                        out.print(strHtml);
                        out.flush();
                        out.close();
                    }
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("=======mobilepaynotify返回验签失败===========" + tradCode);
                }
            }
        } catch (DDPException e) {
            log.error("mobilepaynotify回写success失败，失败原因:", e);
            e.printStackTrace();

        } catch (IOException e) {
            log.error("mobilepaynotify回写success失败，失败原因:", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("mobilepaynotify支付回调处理发生错误，错误原因:", e);
        }
    }
     
    /**
     * @param request
     * @return void
     * @description 自助端支付以后(第三方)通知调用
     * @comments 获取支付宝返回数据，验证，业务逻辑的状态位回填
     */
    @RequestMapping("/buffetPayNotify")
	public void buffetPayNotify(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		if (log.isInfoEnabled()) {
			log.info("自助终端支付回调通知方法开始================");
		}

		log.info(request.getRequestURL().toString());
		log.info("buffetPayNotify第三方回调开始：：====================");
		if (true) {
			log.info("debug: reqeust information.....");
			log.info("accept :" + request.getHeader("accept"));
			log.info("RemoteHost :" + request.getRemoteHost());
			log.info("url :");
			log.info("	" + request.getRequestURL());
			Enumeration<String> enu = request.getParameterNames();
			if (enu.hasMoreElements()) {
				log.info("parameter :");
			}
			while (enu.hasMoreElements()) {
				String element = (String) enu.nextElement();
				log.info("	" + element + " : " + request.getParameter(element));

			}
		}
		try {
			// 获取传递回来的交易编号
			String tradCode = request.getParameter("paymentTranNo");
			if (!DDPStringUtil.isPopulated(tradCode)) {
				// ==============================自助终端微信扫码支付通知=============================================================================
				WxPayBean wxPayBean = new WxPayBean();
				Map<String, String> signMap = new HashMap<String, String>();
				// 微信支付通知
				log.info("微信支付后台通知: 通知开始------------------------");

				// 解析dom与组装签名Map
				dom(request, response, wxPayBean, signMap);
				// 通讯成功
				if ("SUCCESS".equals(wxPayBean.getReturn_code())) {
					tradCode = wxPayBean.getAttach();
					// 根据交易流水取到支付流水
					Payment payment = paymentService.queryPaymentInfo(tradCode);
					String payWayId = "";
					PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payment.getPayWayId());
					payWayId = payWayGeneral.getPayConfigId();
					if (log.isInfoEnabled()) {
						log.info("==========buffetPayNotify根据payWayId获取payConfig=============="+ payWayId);
					}

					// 根据payWayId获取支付配置信息
					PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayId);
					String sign = wxPayBean.getSign();
					// 根据支付配置信息实例化支付类(支付宝或者财付通或微信)
					BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
					PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
					// 验签
					if (basepayment.verifySign(payConfig, sign, signMap)) {
						// 参数基本校验失败
						String checkrs = checkOrder(wxPayBean);
						//
						if (!ResponseCode.SUCCESS.equals(checkrs)) {
							log.info("微信支付后台通知: 参数校验失败,错误码checkrs="+ checkrs);
						}
						String tradeNo = wxPayBean.getTransaction_id();
						String tradeStatus = wxPayBean.getResult_code();
						String enddate = wxPayBean.getTime_end();
						Date noticeDate = DateUtils.stringtoDate(enddate,DateUtils.DATE_FULL_STR);
						//DateUtils.stringtoDate(enddate, DateUtils.DATE_FULL_STR);
						// 支付结果处理
						DodopalResponse<String> payre = payResult(pt, tradeNo,tradeStatus, noticeDate, signMap, response);
						if (ResponseCode.SUCCESS.equals(payre.getCode())) {
							getXmlstr(response, "SUCCESS", "OK");
						}
					}

				} else {
					// 通讯失败
					log.info("微信支付后台通知: 通知通讯失败,return_code="
							+ wxPayBean.getReturn_code());
					getXmlstr(response, "FAIL", "通讯失败");
				}

		    // ==============================自助终端支付宝扫码支付通知=============================================================================

			} else {

				// 根据交易编号查询出支付方式
				Payment payment = paymentService.queryPaymentInfo(tradCode);
				String payWayId = "";

				PayWayGeneral payWayGeneral = wayGeneralService
						.queryPayConfigId(payment.getPayWayId());
				payWayId = payWayGeneral.getPayConfigId();

				if (log.isInfoEnabled()) {
					log.info("==========buffetPayNotify根据payWayId获取payConfig=============="+ payWayId);
				}
				// 根据payWayId获取支付配置信息
				PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayId);
				String sign = request.getParameter("sign");
				String tradeStatus = request.getParameter("trade_status");
				String trade_no = request.getParameter("trade_no");
				if (log.isInfoEnabled()) {
					log.info("buffetPayNotify回调返回状态为======" + tradeStatus);
				}
				// 获取通知返回时间
				String returnDate = request.getParameter("notify_time");
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//Date noticeDate = sdf.parse(returnDate);
				Date noticeDate = DateUtils.stringtoDate(returnDate,DateUtils.DATE_FULL_STR);
				// 获取返回
				Map requestParams = request.getParameterMap();
				// 根据支付配置信息实例化支付类(支付宝或者财付通或微信)
				BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
				PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
				// 对返回的数据进行验签操作
				if (basepayment.verifySign(payConfig, sign, requestParams) || 
					(RSASignatureUtil.verify(RSASignatureUtil.public_zizhu_key, sign, SignUtils.createLinkString(requestParams, new String[]{"sign_type", "sign", "paymentTranNo"}), "UTF-8")) ) {
					// 判断支付成功还是失败
					AliPayMode ali = new AliPayMode();
					if (ali.isPaySuccess(request)) {
						// 支付结果处理
						DodopalResponse<String> payre = payResult(pt, trade_no,tradeStatus, noticeDate, requestParams,response);
						if (ResponseCode.SUCCESS.equals(payre.getCode())) {
							response.getWriter().write("success");
						}
					}
				} else {
					if (log.isInfoEnabled()) {
						log.info("=======mobilepaynotify返回验签失败==========="+ tradCode);
					}
				}

			}
		} catch (DDPException e) {
			log.error("buffetPayNotify回写success失败，失败原因:", e);
			e.printStackTrace();

		} catch (IOException e) {
			log.error("buffetPayNotify回写success失败，失败原因:", e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("buffetPayNotify支付回调处理发生错误，错误原因:", e);
		}
	}

    /**
     * @param request
     * @return void
     * @description 退款后台通知调用
     * @comments 获取支付宝返回数据，验证，业务逻辑的状态位回填
     */
    @RequestMapping("/payRefundedNotify")
    public void payRefundedNotify(HttpServletRequest request, HttpServletResponse response) throws ParseException {
    	if (log.isInfoEnabled()) {
            log.info("========payRefundedNotify=====支付宝退款服务器端通知开始==========  ");
            log.info("========payRefundedNotify=====打印退款交易编号==========  " + request.getParameter("paymentTranNo"));
            log.info("退款回调通知方法开始================");
            log.info("=========退款交易流水号=============" + request.getParameter("paymentTranNo"));
        }

        log.info(request.getRequestURL().toString());
        log.info("payRefundedNotify第三方回调开始：：====================");
        if (true) {
            log.info("debug: reqeust information.....");
            log.info("accept :" + request.getHeader("accept"));
            log.info("RemoteHost :" + request.getRemoteHost());
            log.info("url :");
            log.info("	" + request.getRequestURL());
            Enumeration<String> enu = request.getParameterNames();
            if (enu.hasMoreElements()) {
                log.info("parameter :");
            }
            while (enu.hasMoreElements()) {
                String element = (String) enu.nextElement();
                log.info("	" + element + " : " + request.getParameter(element));

            }
        }        
        
        //获取传递回来的交易编号
        String tradCode = request.getParameter("paymentTranNo");
        //根据交易编号查询出支付方式
        Payment payment = paymentService.queryPaymentInfo(tradCode);
        String payWayId = "";
        if ("GW_ALL".equals(payment.getPayWayKind())) {
            PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayGeneral.getPayConfigId();
        } else {
            PayWayExternal payWayExternal = payWayExternalService.queryPayConfigId(payment.getPayWayId());
            payWayId = payWayExternal.getPayConfigId();

        }
        if (log.isInfoEnabled()) {
            log.info("==========paynotify根据payWayId获取payConfig==============" + payWayId);
        }
        //根据payWayId获取支付配置信息
        PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayId);
        if (log.isInfoEnabled()) {
            log.info("==========paynotify根据payConfig获取BasePayment开始==============");
        }
        //根据支付配置信息实例化支付类(支付宝或者财付通)
        BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
        if (log.isInfoEnabled()) {
            log.info("==========paynotify根据payConfig获取BasePayment结束==============");
        }
        //获取返回的签名信息，用来回填表返回签名信息字段
        String sign = request.getParameter("sign");
        if (log.isInfoEnabled()) {
            log.info("========paynotify支付网关类型===========" + payConfig.getBankGatewayType());
        }
        //获取支付状态
        String tradeStatus = "";
        Date noticeDate = null;
        if ("0".equals(payConfig.getBankGatewayType())) {
            
            //获取通知返回时间
            String returnDate = request.getParameter("notify_time");
            String notify_type =request.getParameter("notify_type");
            String sign_type = request.getParameter("sign_type");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            noticeDate = sdf.parse(returnDate);
            log.info("========退款通知======notify_time=====" + returnDate);
            log.info("========退款通知======notify_type=====" + notify_type);
            log.info("========退款通知======sign_type=====" + sign_type);
        } else {
            tradeStatus = request.getParameter("trade_state");
            noticeDate = new Date();
        }
        if (log.isInfoEnabled()) {
            log.info( "[交易流水号" + request.getParameter("paymentTranNo") + "]");
        }

        if (log.isInfoEnabled()) {
            log.info("=======退款通知====获取返回参数开始==========");
        }
        //获取返回
        Map requestParams = request.getParameterMap();
        if (log.isInfoEnabled()) {
            log.info("=======退款通知====获取返回参数结束==========");
            log.info("=======退款通知===查询paytransaction开始==========");
        }
        //查询原交易流水信息
        PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
        if (log.isInfoEnabled()) {
            log.info("=========退款通知===查询paytransaction结束============");
        }
        if (log.isInfoEnabled()) {
            log.info("==========退款通知=====交易流水状态==============" + pt.getTranInStatus());
        }
        try {
            //对返回的数据进行验签操作
            if (basepayment.verifySign(payConfig, sign, requestParams)) {
            	
            	log.info("==========退款通知=====成功==============");
            	
                //查询退款交易流水信息:修改退款交易流水 外部状态、内部状态
            	PayTraTransaction newPayTraTransaction = payTranService.findTranInfoByOldTranCode(pt.getTranCode(), TranTypeEnum.REFUND.getCode());
            	
            	if (null != newPayTraTransaction) {
                    newPayTraTransaction.setTranOutStatus(TranOutStatusEnum.HAS_REFUND.getCode());//  外部状态 =  已退款
                    newPayTraTransaction.setTranInStatus(TranInStatusEnum.REFUND_SUCCESS.getCode());//  内部状态 = 退款成功
                    payTranService.updateStatesByTransfer(newPayTraTransaction);
            	}
                
                //原交易流水关闭
                pt.setTranOutStatus(TranOutStatusEnum.CLOSE.getCode());// 外部状态 = 关闭
                payTranService.updateOutStatesByTransfer(pt);
            	
            	 String strHtml = "success";
                 if ("0".equals(payConfig.getBankGatewayType())) {
                     response.getWriter().write(strHtml);
                 } else {
                     PrintWriter out = response.getWriter();
                     out.print(strHtml);
                     out.flush();
                     out.close();
                 }             
            	
            } else {
                if (log.isInfoEnabled()) {
                    log.info("=======退款通知====返回验签失败===========" + tradCode);
                }
            }
        } catch (DDPException e) {
            log.error("回写success失败，失败原因:", e);
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付回调处理发生错误，错误原因:", e);
        }
               

    }
    
    //圈存直接调用冻结和扣款操作
    private DodopalResponse<Boolean> freezeAndDeduct(PayTraTransaction pt) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        PayTranDTO transactionDTO = new PayTranDTO();
        //用户编号 商户号/个人用户号
        transactionDTO.setCusTomerCode(pt.getMerOrUserCode());
        //用户类型
        transactionDTO.setCusTomerType(pt.getUserType());
        //订单号
        transactionDTO.setOrderCode(pt.getOrderNumber());
        //业务类型
        transactionDTO.setBusinessType(pt.getBusinessType());
        //商品名称
        transactionDTO.setGoodsName(pt.getCommodity());
        //来源
        transactionDTO.setSource(pt.getSource());
        //订单时间
        transactionDTO.setOrderDate(pt.getOrderDate());
        //城市编码
        //-- transactionDTO.setCityCode();
        //外接标识
        //--transactionDTO.setExtFlg();
        //交易金额
        transactionDTO.setAmount(pt.getAmountMoney());
        //操作员
        transactionDTO.setOperatorId(pt.getCreateUser());
        //支付方式ID
        //--transactionDTO.setPayId();
        //交易类型
        transactionDTO.setTranType(pt.getTranType());
        //notify_url
        transactionDTO.setNotifyUrl(pt.getServiceNoticeUrl());
        dodopalResponse = payFacade.loadOrderDeductAccountAmt(transactionDTO);
        return dodopalResponse;
    }

    ////如果是圈存并且账户支付，在提交支付前直接调用冻结和扣款操作
    private DodopalResponse<Boolean> freezeAndDeductBeforePay(PaySubmit paySubmit) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        PayTranDTO transactionDTO = new PayTranDTO();
        //用户编号 商户号/个人用户号
        transactionDTO.setCusTomerCode(paySubmit.getMerOrUserCode());
        //用户类型
        transactionDTO.setCusTomerType(paySubmit.getCustomerType());
        //订单号
        transactionDTO.setOrderCode(paySubmit.getOrderNumber());
        //业务类型
        transactionDTO.setBusinessType(paySubmit.getBusinessType());
        //商品名称
        transactionDTO.setGoodsName(paySubmit.getCommodityName());
        //来源
        transactionDTO.setSource(paySubmit.getSource());
//        订单时间
//        transactionDTO.setOrderDate(paySubmit.getOrderDate());
        //城市编码
        //-- transactionDTO.setCityCode();
        //外接标识
        //--transactionDTO.setExtFlg();
        //交易金额
        transactionDTO.setAmount(paySubmit.getTranMoney());
        //操作员
        transactionDTO.setOperatorId(paySubmit.getCreateUser());
        //支付方式ID
        //--transactionDTO.setPayId();
        //交易类型
        transactionDTO.setTranType(paySubmit.getTranName());
        //notify_url
        transactionDTO.setNotifyUrl(paySubmit.getReturnUrl());
        dodopalResponse = payFacade.loadOrderDeductAccountAmt(transactionDTO);
        return dodopalResponse;
    }

    //http发送，更改订单状态处理
    private static void httpToProduct(PayTraTransaction pt, String state) {
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest req = new HttpRequest(HttpResultType.BYTES);
        req.setCharset("UTF-8");
        req.setUrl(pt.getServiceNoticeUrl());
        long timemills = System.currentTimeMillis();
        Map<String, String> signMap = new LinkedHashMap<String, String>();
        signMap.put("orderNum", pt.getOrderNumber());
        signMap.put("notify_time", String.valueOf(timemills));
        signMap.put("ResponseCode", state);
        String returnsign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("orderNum", pt.getOrderNumber());
        reqParams.put("notify_time", String.valueOf(timemills));
        //账户加值失败
        reqParams.put("ResponseCode", state);
        reqParams.put("sign", returnsign);
        reqParams.put("businessType", pt.getBusinessType());
        if (log.isInfoEnabled()) {
            log.info("===============reqParams==========" + reqParams.toString());
        }
        req.setParameters(generatNameValuePair(reqParams));
        HttpResponse res = httpProtocolHandler.execute(req);
        if (log.isInfoEnabled()) {
            log.info("==================回调产品库处理结束============");
        }
    }

    //返回url调用处理方法
    private String getReturnHtml(PayTraTransaction pt) {
        Map<String, String> signMap = new LinkedHashMap<String, String>();
        long timemills = System.currentTimeMillis();
        signMap.put("orderNum", pt.getOrderNumber());
        signMap.put("notify_time", String.valueOf(timemills));
        String returnsign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
        signMap.put("sign", returnsign);
        signMap.put("businessType", pt.getBusinessType());
        String sbHtml = BasePayment.buildRequest(signMap, pt.getPageCallbackUrl());
        return sbHtml;
    }

    //http请求发送，参数格式化
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[(i++)] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }
    
    /**
     * 微信支付结果 dom解析
     * @param request
     * @param response
     * @param wxPayBean 解析对象
     * @param paramsmap 解析Map封装
     */
    private  void dom(HttpServletRequest request, HttpServletResponse response,WxPayBean wxPayBean ,Map<String, String> paramsmap){
    	try{
	    	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			log.info("微信支付后台通知WxPayReMessageAction: 通知的请求报文=" + sb.toString());
	
		    Document doc = null;
			doc = DocumentHelper.parseText(sb.toString()); // 将字符串转为XML
			Element root = doc.getRootElement(); // 获取XML根节点
			// 解析第一部分报文
			wxPayBean.setReturn_code(root.elementTextTrim("return_code"));
			wxPayBean.setReturn_msg (root.elementTextTrim("return_msg"));
			wxPayBean.setTime_end(root.elementTextTrim("time_end"));
			wxPayBean.setResult_code(root.elementTextTrim("result_code"));
			
			log.info("微信支付后台通知WxPayReMessageAction: 报文解析结果1,return_code="+ wxPayBean.getReturn_code() + ",return_msg=" + wxPayBean.getReturn_msg());
   
			if ("SUCCESS".equals(wxPayBean.getReturn_code())) {
				// 通讯成功后解析第二部分报文
				wxPayBean.setAppid(root.elementTextTrim("appid"));
				wxPayBean.setMch_id(root.elementTextTrim("mch_id"));
				wxPayBean.setDevice_info(root.elementTextTrim("device_info"));
				wxPayBean.setNonce_str(root.elementTextTrim("nonce_str"));
				wxPayBean.setSign(root.elementTextTrim("sign"));
				wxPayBean.setErr_code(root.elementTextTrim("err_code"));
				wxPayBean.setErr_code_des(root.elementTextTrim("err_code_des"));
				wxPayBean.setOpenid (root.elementTextTrim("openid"));
				log.info("微信支付后台通知: 报文解析结果2,appid=" + wxPayBean.getAppid()
						+ ",mch_id=" + wxPayBean.getMch_id() + ",device_info=" + wxPayBean.getDevice_info()
						+ ",nonce_str=" + wxPayBean.getNonce_str() + ",sign=" + wxPayBean.getSign() + ",result_code="
						+ wxPayBean.getResult_code() + ",err_code=" + wxPayBean.getErr_code() + ",err_code_des="
						+ wxPayBean.getErr_code_des() + ",openid=" + wxPayBean.getOpenid());
				
				
				// 解析第三部分报文
				wxPayBean.setIs_subscribe(root.elementTextTrim("is_subscribe"));
				wxPayBean.setTrade_type(root.elementTextTrim("trade_type"));
				wxPayBean.setBank_type(root.elementTextTrim("bank_type"));
				wxPayBean.setTotal_fee(root.elementTextTrim("total_fee"));
				wxPayBean.setCoupon_fee(root.elementTextTrim("coupon_fee"));
				wxPayBean.setFee_type(root.elementTextTrim("fee_type"));
				wxPayBean.setTransaction_id(root.elementTextTrim("transaction_id"));
				wxPayBean.setOut_trade_no( root.elementTextTrim("out_trade_no"));
				wxPayBean.setAttach(root.elementTextTrim("attach"));
			
				log.info("微信支付后台通知WxPayReMessageAction: 报文解析结果3,is_subscribe="
						+ wxPayBean.getIs_subscribe() + ",trade_type=" + wxPayBean.getTrade_type()
						+ ",bank_type=" + wxPayBean.getBank_type() + ",total_fee=" + wxPayBean.getTotal_fee()
						+ ",coupon_fee=" + wxPayBean.getCoupon_fee() + ",fee_type=" + wxPayBean.getFee_type()
						+ ",transaction_id=" + wxPayBean.getTransaction_id() + ",out_trade_no="
						+ wxPayBean.getOut_trade_no() + ",attach=" + wxPayBean.getAttach() + ",time_end="
						+ wxPayBean.getTime_end());
				
			}
			//组装签名数据
			//paramsmap = new HashMap<String, String>();
			for (int i = 0; i < root.content().size(); i++) {
				Element el = (Element)root.content().get(i);
				paramsmap.put(el.getName(), el.getText());
			}
			
    	}catch(IOException ex){
    		
    	}catch(DocumentException ex){
    		
    	}
    }

    /**
     * 响应微信结果处理
     * @param response
     * @param returncode
     * @param returnmsg
     * @return
     */
	private  String getXmlstr(HttpServletResponse response,String returncode, String returnmsg) {
		StringBuffer sb = new StringBuffer();
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			sb.append("<xml>");
			sb.append("<return_code>" + returncode + "</return_code>");
			sb.append("<return_msg>" + returnmsg + "</return_msg>");
			sb.append("</xml");
			pw.println(sb.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 微信通知参数验证
	 * @param wxPayBean
	 * @return
	 */
	private String checkOrder(WxPayBean wxPayBean) {
		String re = ResponseCode.SUCCESS;

		if (!DDPStringUtil.isPopulated(wxPayBean.getAppid())) {
			re = "000022";// 公众账号ID为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getMch_id())) {
			re = "000022";// 商户号为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getNonce_str())) {
			re = "000022";// 随机字符串为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getSign())) {
			re = "000022";// 签名为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getResult_code())) {
			re = "000022";// 业务结果为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getOpenid())) {
			re = "000022";// 用户标示为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getIs_subscribe())) {
			re = "000022";// 是否关注公众账号为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getTrade_type())) {
			re = "000022";// 交易类型为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getBank_type())) {
			re = "000022";// 付款银行为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getTotal_fee())) {
			re = "000022";// 充值金额为空
			return re;
		} else {
			if (wxPayBean.getTotal_fee().equals("0")) {
				re = "000003"; // 充值金额为0
				return re;
			} 
			if (!checkParttern(wxPayBean.getTotal_fee())) {
				re = "000006";// 金额格式不对（整数或小数）
				return re;
			}
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getTransaction_id())) {
			re = "000022";// 微信支付订单号为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getOut_trade_no())) {
			re = "000022";// 商户订单号为空
			return re;
		}
		if (!DDPStringUtil.isPopulated(wxPayBean.getTime_end())) {
			re = "000022";// 支付完成时间为空
			return re;
		}
		return re;
	}
	
	/**
	 * 验证必须为数字（整数或小数）
	 * 
	 * @return
	 */
	private boolean checkParttern(String num) {
		// 表达式的功能：验证必须为数字（整数或小数）
		String pattern = "[0-9]+(.[0-9]+)?";
		// String pattern = "^[0-9]*$";//验证为整数
		// 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
		// (.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}
		
	/**
	 *  支付结果处理
	 * @param pt
	 * @param tradeNo
	 * @param tradeStatus
	 * @param noticeDate
	 * @param reMap
	 * @param response
	 * @return
	 * @throws IOException
	 */
    private DodopalResponse<String> payResult(PayTraTransaction pt ,String tradeNo,String tradeStatus,Date noticeDate,Map<String,String> reMap,HttpServletResponse response) throws IOException{
    	 DodopalResponse<String> returnMessage = new DodopalResponse<String>();
        //根据流水号查询交易流水信息
        if (TranTypeEnum.ACCOUNT_RECHARGE.getCode().equals(pt.getTranType())) {
            if (!TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus()) && !TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                PayTraTransaction payTraTransaction = new PayTraTransaction();
                payTraTransaction.setTranCode(pt.getTranCode());
                payTraTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
                payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
                Payment payments = new Payment();
                payments.setTranCode(pt.getTranCode());
                payments.setServiceNoticeStatus(tradeStatus);
                payments.setServiceNoticeDate(noticeDate);
                payments.setAcceptCiphertext(JSON.toJSONString(reMap, true));
                payments.setPayStatus(PayStatusEnum.PAID_SUCCESS.getCode());
                payments.setTradeNo(tradeNo);
                changeStateService.changeState(payments, payTraTransaction);
                //调用账户充值接口                
                try {
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify======账户加值方法开始==================");
                    }
                    returnMessage = accountManagementService.accountRecharge(pt.getUserType(), pt.getMerOrUserCode(), pt.getTranCode(), pt.getAmountMoney(), pt.getCreateUser());
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify======账户加值方法结束返回==================" + returnMessage.getCode());
                    }
                } catch (DDPException ex) {
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify==================DDPEXCEPTION==========" + ex.getCode() == null || "".equals(ex.getCode()) ? "" : ex.getCode());
                    }
                    ex.printStackTrace();
                    if (ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR.equals(ex.getCode())) {
                        if (log.isInfoEnabled()) {
                            log.info("buffetPayNotify============账户充值连接账户系统异常======");
                        }
                        PayTraTransaction ptt = new PayTraTransaction();
                        ptt.setTranCode(pt.getTranCode());
                        ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                        payTraTransactionService.UpdateInStatesByTransfer(ptt);
                        if (log.isInfoEnabled()) {
                            log.info("buffetPayNotify==================账户连接异常回调产品库处理开始============");
                            log.info("buffetPayNotify==================pt.getBusinessType()============" + pt.getBusinessType());
                        }
                        
                    } else {
                        returnMessage.setCode(ex.getCode());
                    }
                    return returnMessage;
                }
                if (ResponseCode.SUCCESS.equals(returnMessage.getCode()) || ResponseCode.ACCOUNT_CHANGE_EXIT.equals(returnMessage.getCode())) {
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify=======账户加值成功处理========");
                    }
                    PayTraTransaction ptt = new PayTraTransaction();
                    ptt.setTranCode(pt.getTranCode());
                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                    payTraTransactionService.UpdateInStatesByTransfer(ptt);
                    //发送http请求调用url处理函数
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify==================账户加值成功走的调用产品库============");
                        log.info("buffetPayNotify========pt.getBusinessType()=======" + pt.getBusinessType());
                    }                               
                    //如果是圈存，直接走支付网关冻结扣款接口
                    if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                        DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                        returnMessage.setCode(re.getCode());
                        if (ResponseCode.SUCCESS.equals(re.getCode())) {
                        	//更改圈存订单状态，后台调用产品库
                            httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                        	
                        }
                    }
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify=======账户加值失败处理========");
                    }
                    PayTraTransaction ptt = new PayTraTransaction();
                    ptt.setTranCode(pt.getTranCode());
                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                    payTraTransactionService.UpdateInStatesByTransfer(ptt);
                    if (log.isInfoEnabled()) {
                        log.info("buffetPayNotify==================账户加值失败走的调用产品库============");
                        log.info("buffetPayNotify========pt.getBusinessType()=======" + pt.getBusinessType());
                    }
                   
                }
            } else {
                if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(pt.getTranInStatus())) {
                    PayTraTransaction ptt = new PayTraTransaction();
                    ptt.setTranCode(pt.getTranCode());
                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                    payTraTransactionService.UpdateInStatesByTransfer(ptt);
                    if (log.isInfoEnabled()) {
                        log.info("mobilepaynotify=======后账户加值成功处理========");
                        log.info("mobilepaynotify========pt.getBusinessType()=======" + pt.getBusinessType());
                    }
                    
                    //如果是圈存，直接走支付网关冻结扣款接口
                    if (RateCodeEnum.IC_LOAD.getCode().equals(pt.getBusinessType())) {
                        DodopalResponse<Boolean> re = freezeAndDeduct(pt);
                        returnMessage.setCode(re.getCode());
                        if (ResponseCode.SUCCESS.equals(re.getCode())) {
                        	//更改圈存订单状态，调用产品库
                            httpToProduct(pt, TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode());
                        }
                    }
                }
                if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(pt.getTranInStatus())) {
                    PayTraTransaction ptt = new PayTraTransaction();
                    ptt.setTranCode(pt.getTranCode());
                    ptt.setTranInStatus(TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode());
                    if (log.isInfoEnabled()) {
                        log.info("mobilepaynotify=======后账户加值失败处理========");
                        log.info("mobilepaynotify========pt.getBusinessType()=======" + pt.getBusinessType());
                    }
                   
                }
            }
        }
        return returnMessage;
    
    }

}