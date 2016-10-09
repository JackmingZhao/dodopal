package com.dodopal.payment.business.facadeImpl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.facade.PayServiceRateFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayServiceRate;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.service.PayConfigService;
import com.dodopal.payment.business.service.PayServiceRateService;
import com.dodopal.payment.business.service.PayWayGeneralService;

/**
 * 获取支付方式服务费率类型和支付方式服务费
 * @author xiongzhijing@dodopal.com
 * @version 2015年10月10日
 */
@Service("payServiceRateFacade")
public class PayServiceRateFacadeImpl implements PayServiceRateFacade {
    private static Logger logger = Logger.getLogger(PayServiceRateFacadeImpl.class);

    @Autowired
    private PayServiceRateService payServiceRateService;

    @Autowired
    private PayWayGeneralService payWayGeneralService;

    @Autowired
    private PayConfigService payConfigService;

    //获取支付服务费和费率类型
    public DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId, String busType, long amout) {
        logger.info("获取支付服务费和费率类型  PayServiceRateFacadeImpl  findPayServiceRate  支付方式id(通用) payWayId:" + payWayId + ",业务类型  busType：" + busType + ",金额 amout:" + amout);
        DodopalResponse<PayServiceRateDTO> response = new DodopalResponse<PayServiceRateDTO>();
        //校验支付方式id(通用)
        if (StringUtils.isBlank(payWayId)) {
            response.setCode(ResponseCode.PAY_CONFIG_ID_NULL);
            return response;
        }
        //校验业务类型
        if (StringUtils.isBlank(busType)) {
            response.setCode(ResponseCode.PAY_BUSINESS_TYPE_ERROR);
            return response;
        }
        //校验金额
        if (amout <= 0) {
            response.setCode(ResponseCode.PAY_AMOUNT_ERROR);
            return response;
        }

        //根据支付方式id(通用) 查询支付方式配置表，最终获取支付方式配置表id
        try {
            //根据支付方式id（通用）查询支付方式信息
            PayWayGeneral payWayGeneral = payWayGeneralService.queryPayConfigIdByPayWayId(payWayId);

            //根据支付方式id(通用) 查询到支付方式不为空
            if (payWayGeneral != null) {

                //支付类型为 在线支付  or 账户支付
                if ((payWayGeneral.getPayType().equals(PayTypeEnum.PAY_ONLINE.getCode())) || (payWayGeneral.getPayType().equals(PayTypeEnum.PAY_ACT.getCode()))) {

                    //根据查询到的 支付方式配置表id 和  业务类型   查询支付方式服务费率表的记录
                    List<PayServiceRate> payServiceRateList = payServiceRateService.findPayServiceRate(payWayGeneral.getPayConfigId(), busType);

                    if (CollectionUtils.isNotEmpty(payServiceRateList) && payServiceRateList != null) {

                        for (PayServiceRate payServiceRate : payServiceRateList) {
                            //支付服务费率类型  单笔
                            if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRate.getPayServiceType())) {

                                if (payServiceRate.getAmounrStart() < amout  && amout  <= payServiceRate.getAmounrEnd()) {
                                    PayServiceRateDTO payServiceRateDTO = new PayServiceRateDTO();
                                    payServiceRateDTO.setRate(payServiceRate.getRate());
                                    payServiceRateDTO.setRateType(payServiceRate.getPayServiceType());
                                    response.setResponseEntity(payServiceRateDTO);
                                    response.setCode(ResponseCode.SUCCESS);
                                    return response;
                                }
                            }
                            //支付服务费率类型  千分比
                            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRate.getPayServiceType())) {

                                PayServiceRateDTO payServiceRateDTO = new PayServiceRateDTO();
                                payServiceRateDTO.setRate(payServiceRate.getRate());
                                payServiceRateDTO.setRateType(payServiceRate.getPayServiceType());
                                response.setResponseEntity(payServiceRateDTO);
                                response.setCode(ResponseCode.SUCCESS);
                                return response;

                            }

                        }
                    } else {
                        //根据查询到的 支付方式配置表id 和  业务类型   查询支付方式服务费率表的记录为空
                        response.setCode(ResponseCode.PAY_SERVICE_RATE_EMPTY);
                        return response;
                    }

                    //支付类型是  银行支付
                } else if (payWayGeneral.getPayType().equals(PayTypeEnum.PAY_BANK.getCode())) {

                    //银行支付 如果是 支付宝银行   需要   支付宝  在支付方式配置表的 id
                    if (payWayGeneral.getBankGateWayType().equals(BankGatewayTypeEnum.GW_ALI.getCode())) {

                        //根据支付宝 在支付方式配置表的数据库id  和业务类型  查询  支付方式服务费表的记录
                        List<PayServiceRate> payServiceRateList = payServiceRateService.findPayServiceRate(DodopalAppVarPropsUtil.getStringProp(PaymentConstants.GW_ALI), busType);
                        if (CollectionUtils.isNotEmpty(payServiceRateList) && payServiceRateList != null ) {

                            for (PayServiceRate payServiceRate : payServiceRateList) {
                                //支付服务费率类型  单笔
                                if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRate.getPayServiceType())) {

                                    if (payServiceRate.getAmounrStart() < amout  && amout <= payServiceRate.getAmounrEnd()) {
                                        PayServiceRateDTO payServiceRateDTO = new PayServiceRateDTO();
                                        payServiceRateDTO.setRate(payServiceRate.getRate());
                                        payServiceRateDTO.setRateType(payServiceRate.getPayServiceType());
                                        response.setResponseEntity(payServiceRateDTO);
                                        response.setCode(ResponseCode.SUCCESS);
                                        return response;
                                    }

                                }
                                //支付服务费率类型  千分比
                                if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRate.getPayServiceType())) {

                                    PayServiceRateDTO payServiceRateDTO = new PayServiceRateDTO();
                                    payServiceRateDTO.setRate(payServiceRate.getRate());
                                    payServiceRateDTO.setRateType(payServiceRate.getPayServiceType());
                                    response.setResponseEntity(payServiceRateDTO);
                                    response.setCode(ResponseCode.SUCCESS);
                                    return response;

                                }

                            }
                        }

                        //银行支付 如果是 财付通银行   需要   支付宝  在支付方式配置表的 id
                    } else if (payWayGeneral.getBankGateWayType().equals(BankGatewayTypeEnum.GW_TENG.getCode())) {

                        //根据财付通 在支付方式配置表的数据库id  和业务类型  查询  支付方式服务费表的记录
                        List<PayServiceRate> payServiceRateList = payServiceRateService.findPayServiceRate(DodopalAppVarPropsUtil.getStringProp(PaymentConstants.GW_TENG), busType);
                        if (CollectionUtils.isNotEmpty(payServiceRateList) && payServiceRateList != null ) {
                            for (PayServiceRate payServiceRate : payServiceRateList) {
                                //支付服务费率类型  单笔
                                if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(payServiceRate.getPayServiceType())) {

                                    if (payServiceRate.getAmounrStart() < amout  && amout  <= payServiceRate.getAmounrEnd()) {
                                        PayServiceRateDTO payServiceRateDTO = new PayServiceRateDTO();
                                        payServiceRateDTO.setRate(payServiceRate.getRate());
                                        payServiceRateDTO.setRateType(payServiceRate.getPayServiceType());
                                        response.setResponseEntity(payServiceRateDTO);
                                        response.setCode(ResponseCode.SUCCESS);
                                        return response;
                                    }
                                }
                                //支付服务费率类型  千分比
                                if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(payServiceRate.getPayServiceType())) {

                                    PayServiceRateDTO payServiceRateDTO = new PayServiceRateDTO();
                                    payServiceRateDTO.setRate(payServiceRate.getRate());
                                    payServiceRateDTO.setRateType(payServiceRate.getPayServiceType());
                                    response.setResponseEntity(payServiceRateDTO);
                                    response.setCode(ResponseCode.SUCCESS);
                                    return response;

                                }

                            }
                        }

                    }

                }

            } else {
                response.setCode(ResponseCode.PAY_CONFIG_NULL);
                return response;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logger.debug("获取支付服务费和费率类型  PayServiceRateFacadeImpl  findPayServiceRate  call Exception:" + e);
            // TODO: handle exception
        }

        return response;
    }

}
