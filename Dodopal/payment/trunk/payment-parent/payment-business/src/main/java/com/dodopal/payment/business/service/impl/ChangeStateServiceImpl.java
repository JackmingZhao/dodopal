/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.payment.business.service.impl;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.*;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.dao.*;
import com.dodopal.payment.business.gateway.BasePayment;
import com.dodopal.payment.business.gateway.BasePaymentUtil;
import com.dodopal.payment.business.model.*;
import com.dodopal.payment.business.service.ChangeStateService;
import com.dodopal.payment.business.util.SignUtils;
import com.dodopal.payment.delegate.MerchantDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by lenovo on 2015/8/14.
 */
@Service
public class ChangeStateServiceImpl implements ChangeStateService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private PayTraTransactionMapper tramapper;
    @Autowired
    private PayConfigMapper payConfigMapper;
    @Autowired
    private PayWayCommonMapper payWayCommonMapper;
    @Autowired
    private PayWayGeneralMapper generalMapper;
    @Autowired
    PayWayExternalMapper payWayExternalMapper;
    @Autowired
    MerchantDelegate merchantDelegate;

    @Transactional
    @Override
    public void changeState(Payment payment, PayTraTransaction payTraTransaction) {
        paymentMapper.modifyPayment(payment);
        tramapper.modifyPaymentTransaction(payTraTransaction);
    }

    @Transactional
    @Override
    public void insertDataInfo(Payment payment, PayTraTransaction payTraTransaction) {
        paymentMapper.addPayment(payment);
        tramapper.addPaymentTransaction(payTraTransaction);
    }

    /**
     * @param paySubmit
     * @param payConfig
     * @param basePayment
     * @param request
     * @param response
     * @return
     * @description 支付提交方法处理
     */
    @Transactional
    @Override
    public String getSubmitHtml(PaySubmit paySubmit, PayConfig payConfig, BasePayment basePayment, HttpServletRequest request, HttpServletResponse response) {
            String userCode = "";
            DodopalResponse<MerchantUserDTO> returnMessage = merchantDelegate.findMerUser(paySubmit.getCreateUser());
            if (ResponseCode.SUCCESS.equals(returnMessage.getCode()) && returnMessage.getResponseEntity() != null) {
                if (DelFlgEnum.DELETE.getCode().equals(returnMessage.getResponseEntity().getDelFlag())) {
                    throw new DDPException(ResponseCode.USERS_FIND_USER_ERR);
                }
                if (ActivateEnum.DISABLE.getCode().equals(returnMessage.getResponseEntity().getActivate())) {
                    throw new DDPException(ResponseCode.USERS_USER_DISABLE);
                }
                userCode = returnMessage.getResponseEntity().getUserCode();
            } else {
                throw new DDPException(ResponseCode.USERS_FIND_USER_ERR);
            }

            DodopalResponse<Map<String,Object>> validateMerchantUserForPaymen = merchantDelegate.validateMerchantUserForPayment(userCode);
            if(!ResponseCode.SUCCESS.equals(validateMerchantUserForPaymen.getCode())){
                throw new DDPException(validateMerchantUserForPaymen.getCode());
            }
        //根据交易编号获取支付配置信息
        payConfig = payConfigMapper.queryPayInfoByPayWayId(paySubmit.getPayId());
        //创建PayTraTransaction实体，并进行赋值操作
        PayTraTransaction pt = new PayTraTransaction();
        pt.setTranCode(paySubmit.getTranCode());
        pt.setTranName(paySubmit.getTranName());
        pt.setAmountMoney(Math.round(paySubmit.getTranMoney()));
        pt.setTranOutStatus(TranOutStatusEnum.TO_BE_PAID.getCode());
        pt.setTranInStatus(TranInStatusEnum.TO_DO.getCode());
        pt.setTranType(paySubmit.getTranName());
        pt.setMerOrUserCode(paySubmit.getMerOrUserCode());
        pt.setOrderNumber(paySubmit.getOrderNumber());
        pt.setUserType(paySubmit.getCustomerType());
        pt.setBusinessType(paySubmit.getBusinessType());
        pt.setSource(paySubmit.getSource());
        pt.setCommodity(paySubmit.getCommodityName());
        //实际交易金额，以分为单位存在数据库
        pt.setRealTranMoney(Math.round(paySubmit.getRealTranMoney()));
        //清算标志，新增交易流水默认是0:未清算
        pt.setClearFlag(ClearFlagEnum.NO.getCode());
        pt.setPayServiceFee(Math.round(paySubmit.getRealTranMoney()-paySubmit.getTranMoney()));
        pt.setPayServiceRate(paySubmit.getRate());
        pt.setPayProceRate(payConfig.getProceRate());
        String payProceFee = String.valueOf((double)paySubmit.getRealTranMoney()*payConfig.getProceRate()/1000);
        long ddpToBankFee = 0;
        if (payProceFee.contains(".")) {
            if (Double.valueOf(payProceFee)%1>0) {
                ddpToBankFee = Long.valueOf(payProceFee.substring(0, payProceFee.indexOf("."))) + 1;
            } else {
                ddpToBankFee = Long.valueOf(payProceFee.substring(0, payProceFee.indexOf(".")));
            }
        } else {
            ddpToBankFee = Long.valueOf(payProceFee);
        }
        pt.setPayProceFee(ddpToBankFee);//支付手续费
        pt.setPageCallbackUrl(paySubmit.getReturnUrl());
        pt.setServiceNoticeUrl(paySubmit.getNotifyUrl());
//        pt.setOrderDate(new Date());
        pt.setPayType(payConfig.getPayType());
        pt.setPayWay(paySubmit.getPayCommonId());
        pt.setPayWayName(payConfig.getPayWayName());
        pt.setCreateUser(paySubmit.getCreateUser());
        pt.setPayGateway(payConfig.getBankGatewayType());
        if(!"".equals(paySubmit.getPayServiceType())){
            pt.setPayServiceType(paySubmit.getPayServiceType());
        }
        pt.setGenId("".equals(paySubmit.getGenId())||paySubmit.getGenId()==null?"":paySubmit.getGenId());
        Payment payment = new Payment();
        payment.setTranCode(paySubmit.getTranCode());
        payment.setPayStatus(PayStatusEnum.TO_BE_PAID.getCode());
        payment.setPayType(payConfig.getPayType());
        payment.setPayWayKind(paySubmit.getPayWayKind());
        payment.setPayServiceRate(paySubmit.getRate());
        payment.setPayServiceFee(Math.round(paySubmit.getRealTranMoney() - paySubmit.getTranMoney()));
        payment.setPayMoney(Math.round(paySubmit.getRealTranMoney()));
        payment.setPayWayId(paySubmit.getPayWayId());//10000000000000000035支付宝余额支付//10000000000000000043招商银行//10000000000000000037工商银行//10000000000000000042 建设银行
        payment.setCreateUser(paySubmit.getCreateUser());
        if(RateCodeEnum.ACCT_RECHARGE.getCode().equals(paySubmit.getBusinessType())){
            String genId = paySubmit.getGenId();
            String checkGenId = tramapper.getGenId(genId);
            if(checkGenId==null||"".equals(checkGenId)){
                checkGenId="";
            }
            if(!genId.equals(checkGenId)){
                paymentMapper.addPayment(payment);
                tramapper.addPaymentTransaction(pt);
            }
        }else{
            paymentMapper.addPayment(payment);
            tramapper.addPaymentTransaction(pt);
        }
        //根据支付配置信息实例化支付类(支付宝或者财付通)
        BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
        //发送支付接口数据封装
        Map<String, String> signMap = basepayment.getParameterMap(payConfig, pt, request, response);
        //回填信息，更新表操作
        PayTraTransaction payTraTransaction = new PayTraTransaction();
        payTraTransaction.setTranCode(paySubmit.getTranCode());
        payTraTransaction.setTranOutStatus(TranOutStatusEnum.PAYMENT.getCode());
        payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
        payTraTransaction.setUpdateUser(paySubmit.getCreateUser());
        Payment payments = new Payment();
        payments.setTranCode(paySubmit.getTranCode());
        payments.setSendCiphertext(signMap.toString());
        payments.setPayStatus(PayStatusEnum.PAYMENT.getCode());
        payments.setUpdateUser(paySubmit.getCreateUser());
        paymentMapper.modifyPayment(payments);
        tramapper.modifyPaymentTransaction(payTraTransaction);
        //根据所传递过来的paywayid对常用支付信息表进行操作
        operateCommonInfo(paySubmit.getPayCommonId(), paySubmit.getPayWayKind(), paySubmit.getCustomerType(), paySubmit.getCreateUser(),userCode);
        String submitHtml = basepayment.buildRequest(signMap, basepayment.getPaymentUrl());
        return submitHtml;
    }
    @Transactional
    //对通用支付方式进行操作
    private void operateCommonInfo(String payCommonId, String payWayKind, String customerType, String userId,String userCode) {
        List<PayWayCommon> payWayCommonList = payWayCommonMapper.queryPayCommonList(userCode);
        //如果查询处的数据信息条数多于或等于5个
        if (payWayCommonList.size() >= 5) {
            String id = containsPayId(payWayCommonList, payCommonId);
            //已经存在此次支付的方式id
            if (id != null && !"".equals(id)) {
                //更改数据
                payWayCommonMapper.updateCommonInfo(id);
            } else {
                // 寻找试用次数最少的那个id
                String deleteid = getCommonId(payWayCommonList);
                List ids = new ArrayList();
                ids.add(deleteid);
                //删除一个，增加一个
                payWayCommonMapper.deletePayWayCommon(ids);
                PayWayCommon payWayCommon = new PayWayCommon();
                payWayCommon.setPayWayId(payCommonId);
                payWayCommon.setPayWayKind(payWayKind);
                payWayCommon.setUserType(customerType);
                payWayCommon.setUserCode(userCode);
                payWayCommon.setCreateUser(userId);
                payWayCommonMapper.insertPayWayCommon(payWayCommon);
            }
        } else {
            String id = containsPayId(payWayCommonList, payCommonId);
            if (id != null && !"".equals(id)) {
                //更改
                payWayCommonMapper.updateCommonInfo(id);
            } else {
                //新增
                PayWayCommon payWayCommon = new PayWayCommon();
                payWayCommon.setPayWayId(payCommonId);
                payWayCommon.setPayWayKind(payWayKind);
                payWayCommon.setUserType(customerType);
                payWayCommon.setUserCode(userCode);
                payWayCommon.setCreateUser(userId);
                payWayCommonMapper.insertPayWayCommon(payWayCommon);
            }
        }
    }

    @Transactional
    private String containsPayId(List<PayWayCommon> payCommonList, String payCommonId) {
        String id = null;
        for (PayWayCommon payWayCommon : payCommonList) {
            if (payCommonId.equals(payWayCommon.getPayWayId())) {
                id = payWayCommon.getId();
                break;
            }
        }
        return id;
    }

    @Transactional
    private String getCommonId(List<PayWayCommon> payCommonList) {
        PayWayCommon pwc = payCommonList.get(payCommonList.size() - 1);
        return pwc.getId();
    }
}
