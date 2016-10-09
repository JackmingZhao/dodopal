package com.dodopal.payment.business.facadeImpl;

import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.CreateTranDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.facade.PayRetundFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearFlagEnum;
import com.dodopal.common.enums.PayStatusEnum;
import com.dodopal.common.enums.PayWayKindEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.business.dao.PayTraTransactionMapper;
import com.dodopal.payment.business.gateway.BasePayment;
import com.dodopal.payment.business.gateway.BasePaymentUtil;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.service.PayConfigService;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.business.service.PayWayGeneralService;
import com.dodopal.payment.business.service.PaymentService;

@Service("payRetundFacade")
public class PayRetundFacadeImpl  implements PayRetundFacade {
	private static Logger log = LoggerFactory.getLogger(PayFacadeImpl.class);
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private PayWayGeneralService wayGeneralService;
	
    @Autowired
    private PayConfigService payConfigService;
    
    @Autowired
    PayWayGeneralService payWayGeneralService;
    
    @Autowired
    private PayTranService payTranService;
    
    @Autowired
    private PayTraTransactionMapper tramapper;
    
	@Override
	public DodopalResponse<String> sendRefund(String tradCode, String source, String operId) {
        
        DodopalResponse<String>  reponse = new DodopalResponse<String>();
        reponse.setCode(ResponseCode.SUCCESS);
        try{
            
        //1、判断参数是否为空，原交易流水号、来源、操作员
        if (StringUtils.isBlank(tradCode)) {
            log.error("退款接口入参不正：原交易流水号为空："+tradCode);
            reponse.setCode(ResponseCode.PAYRETUND_PARA_TRADCODE_ERROR);
            return reponse;
        }
        if (!SourceEnum.checkCodeExist(source)) {
            log.error("退款接口入参不正：来源不正："+source);
            reponse.setCode(ResponseCode.PAYRETUND_PARA_SOURCE_ERROR);
            return reponse;
        }
        if (StringUtils.isBlank(operId)) {
            log.error("退款接口入参不正：操作员为空："+operId);
            reponse.setCode(ResponseCode.PAYRETUND_PARA_OPERATOEID_ERROR);
            return reponse;
        }

        log.info("退款交易流水号 = " + tradCode );
        
        Payment payment = paymentService.queryPaymentInfo(tradCode);
        
        PayWayGeneral payWayGeneral = wayGeneralService.queryPayConfigId(payment.getPayWayId());
        
        log.info("退款支付方式ID = " + payWayGeneral.getPayConfigId() +" 支付方式名称 = " + payWayGeneral.getPayWayName());
        
        //根据payWayId获取支付配置信息
        PayConfig payConfig = payConfigService.queryPayInfoByPayWayId(payWayGeneral.getPayConfigId());
        
        //根据支付配置信息实例化支付类(支付宝或者财付通)
        BasePayment basepayment = BasePaymentUtil.getPayment(payConfig);
        
        // 查询原交易流水信息
        PayTraTransaction pt = payTranService.findTranInfoByTranCode(tradCode);
        
        //判断原交易是否是成的支付状态（外部状态非已支付状态不能 退款），如果不是 返回错误码 不能退款
        if (!TranOutStatusEnum.HAS_PAID.getCode().equals(pt.getTranOutStatus())) {
            
            log.error("退款接口：判断原交易流水状态："+TranOutStatusEnum.getTranOutStatusByCode(pt.getTranOutStatus()).getName()+"（非已支付状态，不能退款），原交易流水号："+pt.getTranCode());
            reponse.setCode(ResponseCode.PAYRETUND_TRADSTATUS__ERROR);// 原交易流水状态非法
            return reponse;
        }
        
        // 生成退款交易流水 
        /*
                            注意点:
                            退款交易流水号 = 新创建
                            退款交易流水的原交易流水 oldTranCode = tradCode
                            交易 类型 = TranTypeEnum.REFUND.getCode()              退款
                            外部状态 = TranOutStatusEnum.TO_BE_REFUNDED.getCode() 待退款
                            内部状态 = TranInStatusEnum.TO_DO.getCode()           待处理
                           来源 = source
                           操作员=operId
                           其他 值来源 原交易流水
        **/
        String  reFundTranCode = payTranService.createPayTranCode(TranTypeEnum.REFUND.getCode(),"");
        PayTraTransaction newPaymentTransaction = new PayTraTransaction();
        PropertyUtils.copyProperties(newPaymentTransaction, pt);
        newPaymentTransaction.setTranCode(reFundTranCode);// 退款交易流水号
        newPaymentTransaction.setOldTranCode(pt.getTranCode());// 退款交易流水的原交易流水 oldTranCode = tradCode
        newPaymentTransaction.setTranType(TranTypeEnum.REFUND.getCode());// 交易 类型 = 退款
        newPaymentTransaction.setTranOutStatus(TranOutStatusEnum.TO_BE_REFUNDED.getCode());// 外部状态 = 待退款
        newPaymentTransaction.setTranInStatus(TranInStatusEnum.TO_DO.getCode());// 内部状态 = 待处理
        newPaymentTransaction.setSource(source);// 来源 = source
        newPaymentTransaction.setOperatorId(operId);// 操作员=operId
        tramapper.addPaymentTransaction(newPaymentTransaction);
        
        String returnCode =  basepayment.sendRefund(payConfig,tradCode, pt.getAmountMoney(), payment.getTradeNo(),payTranService);
        
        //请求退款：T:退款成功，F：退款失败  
        if ("F".equals(returnCode)) {
            reponse.setCode(ResponseCode.PAYRETUND_FAILED); 
        }
        
        }catch(Exception ex){
            ex.printStackTrace();
            reponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return reponse;
    }

	
	@Override
    public DodopalResponse<String> createPayTraTransaction(CreateTranDTO dto) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        try {
            //1、  验证
            String checkResult = checkCreateTranDTO(dto);
            if (StringUtils.isNotBlank(checkResult)) {
                dodopalResponse.setCode(checkResult);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl createPayTraTransaction 开始校验基本参数的结果[{}],message:[{}]", checkResult,dodopalResponse.getMessage());
                }
                return dodopalResponse;
            }
            PayTranDTO payTranDTO = new PayTranDTO();
            payTranDTO.setCusTomerCode(dto.getCustcode());
            payTranDTO.setCusTomerType(dto.getCusttype());
            //校验客户号的合法性
            String userCodeCheck = payTranService.checkMerOrUserCode(payTranDTO);
            if (log.isDebugEnabled()) {
                log.debug("PayFacadeImpl createPayTraTransaction 校验客户号的合法性为[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
            }
            if (!ResponseCode.SUCCESS.equals(userCodeCheck)) {
                dodopalResponse.setCode(userCodeCheck);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl createPayTraTransaction 校验客户号的合法性[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
                }
                return dodopalResponse;
            }

            //根据支付ID 取到通用支付方式
            PayWayGeneral payWay = payWayGeneralService.findPayGeneralById(dto.getPayid());
            if(null==payWay){
                dodopalResponse.setCode(ResponseCode.PAY_WAY_NOT_FIND);
                return dodopalResponse;
                
            }
            PayConfig  payconfig =   payConfigService.findPayConfigById(payWay.getPayConfigId());

            /**
             * 3、 组装数据 交易流水号规则 T + 业务代码 + 城市编码+ 20150428222211（时间戳） +六位数据库cycle
             * sequence（循环使用）
             */
            PayTraTransaction payTraTransaction = new PayTraTransaction();
            Payment payment = new Payment();
            //外部状态 =  『0：待支付』
            payTraTransaction.setTranOutStatus(TranOutStatusEnum.TO_BE_PAID.getCode());
            //『1：处理中』
            payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
            //生成交易流水
            String tranCode = payTranService.createPayTranCode(dto.getBusinessType(), "");
            payTraTransaction.setTranCode(tranCode);
            //支付类型
            payTraTransaction.setPayType(payWay.getPayType());
            //订单时间
            payTraTransaction.setOrderDate(new Date());
            //支付方式id
            payTraTransaction.setPayWay(dto.getPayid());
            //支付方式名称
            payTraTransaction.setPayWayName(payWay.getPayWayName());
            //交易类型
            payTraTransaction.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
            //服务费率
            payTraTransaction.setPayServiceRate(0);
            //服务费率*交易金额 转为分
            payTraTransaction.setPayServiceFee(0);
            //用户类型
            payTraTransaction.setUserType(dto.getCusttype());
            //手续费率
            payTraTransaction.setPayProceRate(dto.getPayprocerate());
            //手续费率 × 交易金额（注意转为分）
            payTraTransaction.setPayProceFee(Math.round((dto.getPayprocerate() / 10) * dto.getAmont()));
            //实际交易金额 = 交易金额 + 支付手续费（注意转为分）
            payTraTransaction.setRealTranMoney(Math.round((dto.getPayprocerate() / 10) * dto.getAmont())+dto.getAmont());
            
            // 清算标志位 =  『0：未清算』
            payTraTransaction.setClearFlag(ClearFlagEnum.NO.getCode());
            //客户号
            payTraTransaction.setMerOrUserCode(dto.getCustcode());
            //原交易金额
            payTraTransaction.setAmountMoney(dto.getAmont());
            //来源
            payTraTransaction.setSource(dto.getSource());
            //业务类型代码
            payTraTransaction.setBusinessType(dto.getBusinessType());
            //商品名称
            payTraTransaction.setCommodity(dto.getGoodsName());
            //支付类型
            payTraTransaction.setPayType(payconfig.getPayType());
            //订单号
            payTraTransaction.setOrderNumber(dto.getOrderNum());
            //通知URL
            payTraTransaction.setServiceNoticeUrl(dto.getNotifyUrl());
            
//            payTraTransaction.setServiceNoticeUrl(transactionDTO.getNotifyUrl());
            payment.setPayType(payconfig.getPayType());
            payment.setTranCode(tranCode);
            //支付状态 = 『0：待支付』
            payment.setPayStatus(PayStatusEnum.TO_BE_PAID.getCode());
            //支付类型 = 『0：账户支付』
            payment.setPayType(payconfig.getPayType());
            //        支付方式分类 ：根据参数『外接商户标识』
            //        外接商户标识 =  ture 为 “GW_OUT”
            //        外接商户标识 =  false为 “GW_ALL”       
            payment.setPayWayKind(PayWayKindEnum.GW_ALL.getCode());
            
            payment.setPayWayId(dto.getPayid());
            //服务费率
            payment.setPayServiceRate(0);
            //支付服务费 = 交易金额 × T. 服务费率（注意转为分）
            payment.setPayServiceFee(0);
            //支付金额 = 实际交易金额
            payment.setPayMoney(Math.round((dto.getPayprocerate() / 10) * dto.getAmont())+dto.getAmont());

            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl createPayTraTransaction 生成交易流水支付流水数据完成，现在进行事务提交");
            }
            payTranService.savePaymentAndPayTran(payment, payTraTransaction);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl createPayTraTransaction 交易流水支付流水数据事务提交完成");
            }

            dodopalResponse.setResponseEntity(tranCode);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl createPayTraTransaction 更新交易流水 提交事务");
            }
//            payTranService.updatePayStatusAndPayTranStatus(payment, payTraTransaction);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("PayFacadeImpl's createPayTraTransaction call error", e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        //7、根据上面步骤处理：返回对应的响应码：如果成功返回：“000000”
        return dodopalResponse;
    }
    
    public String checkCreateTranDTO(CreateTranDTO dto){
        if (dto.getAmont() < 0 || dto.getAmont() >= 100000000l) {
            return ResponseCode.PAY_AMOUNT_ERROR;
        }
        if(StringUtils.isBlank(dto.getSource())){
            return ResponseCode.PAY_SOURCE_ERROR;
        }
        if(StringUtils.isBlank(dto.getCustcode())){
            return ResponseCode.PAY_MER_OR_USER_NULL;
        }
        if(StringUtils.isBlank(dto.getCusttype())){
            return ResponseCode.PAY_CUSTOMER_TYPE_ERROR;
        } 
        if(StringUtils.isBlank(dto.getPayid())){
            return ResponseCode.PAY_CONFIG_ID_NULL;
        }
        if(StringUtils.isBlank(dto.getBusinessType())){
            return ResponseCode.PAY_BUSINESS_TYPE_ERROR;
        }
        if(StringUtils.isBlank(dto.getGoodsName())){
            return ResponseCode.PAY_GOODS_NAME_NULL;
        }
        
        return null;
    }
}
