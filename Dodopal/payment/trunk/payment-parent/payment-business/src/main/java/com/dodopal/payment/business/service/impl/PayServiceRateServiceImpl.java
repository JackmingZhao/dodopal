package com.dodopal.payment.business.service.impl;

import java.util.List;

import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.model.PayWayGeneral;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.payment.business.dao.PayServiceRateMapper;
import com.dodopal.payment.business.model.PayServiceRate;
import com.dodopal.payment.business.service.PayServiceRateService;

/**
 * 查询支付服务费率
 */
@Service
public class PayServiceRateServiceImpl implements PayServiceRateService {

    @Autowired
    private PayServiceRateMapper payServiceRateMapper;

    //查询支付服务费率
    @Transactional(readOnly = true)
    public List<PayServiceRate> findPayServiceRate(String payConfigId, String business) {
        return payServiceRateMapper.findPayServiceRate(payConfigId, business);
    }

    @Override
    public PayServiceRate getServiceTypeAndRate(PayWayGeneral payWayGeneral, String bussinessType, long amount) {
        {
            PayServiceRate payServiceRateDTO = new PayServiceRate();
            try {
                //支付类型为 在线支付  or 账户支付
                if ((payWayGeneral.getPayType().equals(PayTypeEnum.PAY_ONLINE.getCode())) || (payWayGeneral.getPayType().equals(PayTypeEnum.PAY_ACT.getCode()))) {
                    //根据查询到的 支付方式配置表id 和  业务类型   查询支付方式服务费率表的记录
                    List<PayServiceRate> payServiceRateList = payServiceRateMapper.findPayServiceRate(payWayGeneral.getPayConfigId(), bussinessType);
                    if (CollectionUtils.isNotEmpty(payServiceRateList) &&  payServiceRateList != null) {
                        for (PayServiceRate payServiceRate : payServiceRateList) {
                            //支付服务费率类型  单笔
                            if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRate.getPayServiceType())) {

                                if (payServiceRate.getAmounrStart() < amount && amount <= payServiceRate.getAmounrEnd()) {
                                    payServiceRateDTO.setRate(payServiceRate.getRate());
                                    payServiceRateDTO.setPayServiceType(payServiceRate.getPayServiceType());
                                    return payServiceRateDTO;
                                }
                            }
                            //支付服务费率类型  千分比
                            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRate.getPayServiceType())) {
                                payServiceRateDTO.setRate(payServiceRate.getRate());
                                payServiceRateDTO.setPayServiceType(payServiceRate.getPayServiceType());
                                return payServiceRateDTO;
                            }
                        }
                    }
                    //支付类型是  银行支付
                } else if (payWayGeneral.getPayType().equals(PayTypeEnum.PAY_BANK.getCode())) {
                    //银行支付 如果是 支付宝银行   需要   支付宝  在支付方式配置表的 id
                    if (payWayGeneral.getBankGateWayType().equals(BankGatewayTypeEnum.GW_ALI.getCode())) {
                        //根据支付宝 在支付方式配置表的数据库id  和业务类型  查询  支付方式服务费表的记录
                        List<PayServiceRate> payServiceRateList = payServiceRateMapper.findPayServiceRate(DodopalAppVarPropsUtil.getStringProp(PaymentConstants.GW_ALI), bussinessType);
                        if (CollectionUtils.isNotEmpty(payServiceRateList) &&  payServiceRateList != null) {
                            for (PayServiceRate payServiceRate : payServiceRateList) {
                                //支付服务费率类型  单笔
                                if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRate.getPayServiceType())) {
                                    if (payServiceRate.getAmounrStart() < amount && amount <= payServiceRate.getAmounrEnd()) {
                                        payServiceRateDTO.setRate(payServiceRate.getRate());
                                        payServiceRateDTO.setPayServiceType(payServiceRate.getPayServiceType());
                                        return payServiceRateDTO;
                                    }
                                }
                                //支付服务费率类型  千分比
                                if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRate.getPayServiceType())) {
                                    payServiceRateDTO.setRate(payServiceRate.getRate());
                                    payServiceRateDTO.setPayServiceType(payServiceRate.getPayServiceType());
                                    return payServiceRateDTO;
                                }
                            }
                        }
                        //银行支付 如果是 财付通银行   需要   支付宝  在支付方式配置表的 id
                    } else if (payWayGeneral.getBankGateWayType().equals(BankGatewayTypeEnum.GW_TENG.getCode())) {
                        //根据财付通 在支付方式配置表的数据库id  和业务类型  查询  支付方式服务费表的记录
                        List<PayServiceRate> payServiceRateList = payServiceRateMapper.findPayServiceRate(DodopalAppVarPropsUtil.getStringProp(PaymentConstants.GW_TENG), bussinessType);
                        if (CollectionUtils.isNotEmpty(payServiceRateList) && payServiceRateList != null) {
                            for (PayServiceRate payServiceRate : payServiceRateList) {
                                //支付服务费率类型  单笔
                                if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRate.getPayServiceType())) {

                                    if (payServiceRate.getAmounrStart() < amount && amount <= payServiceRate.getAmounrEnd()) {
                                        payServiceRateDTO.setRate(payServiceRate.getRate());
                                        payServiceRateDTO.setPayServiceType(payServiceRate.getPayServiceType());
                                        return payServiceRateDTO;
                                    }
                                }
                                //支付服务费率类型  千分比
                                if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRate.getPayServiceType())) {

                                    payServiceRateDTO.setRate(payServiceRate.getRate());
                                    payServiceRateDTO.setPayServiceType(payServiceRate.getPayServiceType());
                                    return payServiceRateDTO;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return payServiceRateDTO;
        }
    }


}
