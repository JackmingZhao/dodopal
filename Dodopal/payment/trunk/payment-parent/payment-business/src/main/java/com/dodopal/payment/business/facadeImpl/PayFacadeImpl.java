package com.dodopal.payment.business.facadeImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.payment.dto.PayTranDTO;
import com.dodopal.api.payment.dto.PayTransferDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ClearFlagEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayStatusEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.PayWayKindEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TranInStatusEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.payment.business.constant.PaymentConstants;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.PayWay;
import com.dodopal.payment.business.model.PayWayGeneral;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.service.AccountManagementService;
import com.dodopal.payment.business.service.PayConfigService;
import com.dodopal.payment.business.service.PayTraTransactionService;
import com.dodopal.payment.business.service.PayTranService;
import com.dodopal.payment.business.service.PayWayGeneralService;
import com.dodopal.payment.business.service.PayWayService;
import com.dodopal.payment.delegate.PaymentDelegate;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月13日 下午1:44:30
 */
@Service("payFacade")
public class PayFacadeImpl implements PayFacade {
    //PAY_USER_CODE_NULL
    private final static Logger log = LoggerFactory.getLogger(PayFacadeImpl.class);

    @Autowired
    private PayWayService payWayService;
    @Autowired
    private PayTraTransactionService payTraTransactionService;
    @Autowired
    private PayTranService payTranService;
    @Autowired
    private AccountManagementService accountManagementService;
    
    @Autowired
    PayWayGeneralService payWayGeneralService;
    
    @Autowired
    PayConfigService payConfigService;

    /***************************** 2015-12-08 11:37:31  begin**********************/
    @Autowired
    PaymentDelegate paymentDelegate;
    @Autowired
    PayTraTransactionFacade payTraTransactionFacade;
    /***************************** end*********************************************/
    
    public DodopalResponse<List<PayWayDTO>> findPayWay(boolean ext, String ...merCode) {
        log.info("PayFacadeImpl findPayWay merCode:"+merCode+",ext:"+ext);
        DodopalResponse<List<PayWayDTO>> response = new DodopalResponse<List<PayWayDTO>>();
        List<PayWay> payWayList = new ArrayList<PayWay>();
        if (ext) {
            if (StringUtils.isBlank(merCode[0])) {
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl's findPayWay responseCode is [{}]", ResponseCode.PAY_MER_CODE_NULL);
                }
                response.setCode(ResponseCode.PAY_MER_CODE_NULL);
            } else {
                try {
                    payWayList = payWayService.findPayWayExternal(merCode[0]);
                    response.setCode(ResponseCode.SUCCESS);
                }
                catch (Exception e) {
                    log.error("PayFacadeImpl findPayWay payWayService.findPayWayExternal(merCode) throws:"+e);
                    e.printStackTrace();
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                }
            }
        } else {
            try {
                payWayList = payWayService.findPayWayGeneral(merCode[1]);
                response.setCode(ResponseCode.SUCCESS);
            }
            catch (Exception e) {
                log.error("PayFacadeImpl findPayWay payWayService.findPayWayGeneral() throws:"+e);
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
        }
        try {
            List<PayWayDTO> payWayDTOList = new ArrayList<PayWayDTO>();
            if (CollectionUtils.isNotEmpty(payWayList)) {
                for (PayWay payWay : payWayList) {
                    PayWayDTO payWayDTO = new PayWayDTO();
                    PropertyUtils.copyProperties(payWayDTO, payWay);
                    payWayDTOList.add(payWayDTO);
                }
                response.setResponseEntity(payWayDTOList);

            }
        }
        catch (Exception e) {
            log.error("PayFacadeImpl findPayWay copyProperties throws:"+e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }

        return response;
    }

    /**
     * 常用支付方式查询 ext =[ True ：外接商户 False：非外接商户]
     */
    public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext, String userCode) {
        DodopalResponse<List<PayWayDTO>> response = new DodopalResponse<List<PayWayDTO>>();
        List<PayWay> payWayList = new ArrayList<PayWay>();
        if (ext) {
            if (StringUtils.isBlank(userCode)) {
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl's findCommonPayWay responseCode is [{}]", ResponseCode.PAY_MER_CODE_NULL);
                }
                response.setCode(ResponseCode.PAY_MER_CODE_NULL);
            } else {
                try {
                    payWayList = payWayService.findCommonExternal(userCode);
                    response.setCode(ResponseCode.SUCCESS);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                }
            }
        } else {
            try {
                payWayList = payWayService.findCommonGeneral(userCode);
                response.setCode(ResponseCode.SUCCESS);
            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
        }
        try {
            List<PayWayDTO> payWayDTOList = new ArrayList<PayWayDTO>();
            if (CollectionUtils.isNotEmpty(payWayList)) {
                for (PayWay payWay : payWayList) {
                	if(payWay != null){
                		PayWayDTO payWayDTO = new PayWayDTO();
                		PropertyUtils.copyProperties(payWayDTO, payWay);
                		payWayDTOList.add(payWayDTO);
                	}
                }
               
                response.setResponseEntity(payWayDTOList);

            }
        }
        catch (Exception e) {
            
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /* 
     * 资金冻结接口 
     */
    public DodopalResponse<Boolean> freezeAccountAmt(PayTranDTO transactionDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        SysLog sysLog = new SysLog();
        sysLog.setServerName(CommonConstants.SYSTEM_NAME_PAYMENT);
        sysLog.setClassName(PayFacadeImpl.class.toString());
        sysLog.setMethodName("freezeAccountAmt");
        sysLog.setDescription("资金冻结接口 ");
        sysLog.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR )));
        sysLog.setOrderNum(transactionDTO.getOrderCode());
        try {
            //1、  验证
            String checkResult = checkPayTranDTO(transactionDTO);
            if (StringUtils.isNotBlank(checkResult)) {
                dodopalResponse.setCode(checkResult);
                dodopalResponse.setResponseEntity(false);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 开始校验基本参数的结果[{}],message:[{}]", checkResult,dodopalResponse.getMessage());
                }
                return dodopalResponse;
            }
            //校验客户号的合法性
            String userCodeCheck = payTranService.checkMerOrUserCode(transactionDTO);
            if (log.isDebugEnabled()) {
                log.debug("PayFacadeImpl freezeAccountAmt 校验客户号的合法性为[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
            }
            if (!ResponseCode.SUCCESS.equals(userCodeCheck)) {
                dodopalResponse.setCode(userCodeCheck);
                dodopalResponse.setResponseEntity(false);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 校验客户号的合法性[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
                }
                return dodopalResponse;
            }
            
            transactionDTO.setAmount(transactionDTO.getAmount()/100);
            //交易类型 账户冻结-账户
            PayTraTransaction transactionQuery = new PayTraTransaction();
            transactionQuery.setUserType(transactionDTO.getCusTomerType());
            transactionQuery.setMerOrUserCode(transactionDTO.getCusTomerCode());
            transactionQuery.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
            transactionQuery.setOrderNumber(transactionDTO.getOrderCode());
            /**
             * 2取得原交易流水(比如：用户用第三方支付时)一定会生成一条[账户充值]的交易流水.
             * 参数：客户号、客户类型、订单号、交易类型=账户充值
             */
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 方法开始取得原交易流水 参数为 userType :[{}], merOrUserCode:[{}],TranType:[{}],setOrderNumber:[{}] ", transactionDTO.getCusTomerType(), transactionDTO.getCusTomerCode(), TranTypeEnum.ACCOUNT_RECHARGE.getCode(), transactionDTO.getOrderCode());
            }
            List<PayTraTransaction> oldTranList = payTraTransactionService.findPayTraTransactionList(transactionQuery);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 取得的交易流水的list条数为[{}]条", oldTranList.size());
            }
            PayWay payWay = null;
            if (CollectionUtils.isNotEmpty(oldTranList)) {
                //A取到交易流水 判断交易流水 外状态=『3已支付』 且 内部状态 = 『10账户加值成功』
                PayTraTransaction oldTran = oldTranList.get(0);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 原的流水数据[{}]", ReflectionToStringBuilder.toString(oldTran, ToStringStyle.MULTI_LINE_STYLE));
                }
                if (TranOutStatusEnum.HAS_PAID.getCode().equals(oldTran.getTranOutStatus()) && TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(oldTran.getTranInStatus())) {
                    payWay = findPayWay(transactionDTO, payWay);
                } else {
                    //错误信息=非法交易
                    if (log.isInfoEnabled()) {
                        log.info("PayFacadeImpl freezeAccountAmt 原的流水数据状态不合法");
                    }
                    dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                    dodopalResponse.setResponseEntity(false);
                    return dodopalResponse;
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 未找到原交易流水，客户为账户支付");
                }
                //B、    取不到交易流水：【程序继续3】（代表客户是用账户支付）
                payWay = findPayWay(transactionDTO, payWay);
            }
            //如取不到支付方式：错误信息：请联系管理员，配置账户支付类型的支付方式。
            if (null == payWay) {
                if (log.isWarnEnabled()) {
                    log.warn("PayFacadeImpl freezeAccountAmt 未取到支付方式，请联系管理员，配置账户支付类型的支付方式");
                }
                dodopalResponse.setCode(ResponseCode.PAY_PAYTYPE_NULL_ERROR);
                dodopalResponse.setResponseEntity(false);
                return dodopalResponse;
            }
            /**
             * 3、 组装数据 交易流水号规则 T + 业务代码 + 城市编码+ 20150428222211（时间戳） +六位数据库cycle
             * sequence（循环使用）
             */
            PayTraTransaction payTraTransaction = new PayTraTransaction();
            Payment payment = new Payment();
            String tranCode = createData(transactionDTO, oldTranList, payWay, payTraTransaction, payment);

            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 生成交易流水支付流水数据完成，现在进行事务提交");
            }
            payTranService.savePaymentAndPayTran(payment, payTraTransaction);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 交易流水支付流水数据事务提交完成");
            }
            
            // 执行自动转账(2016/04/22 提至资金冻结接口   add by shenXiang) 
            String code = transactionDTO.getCusTomerCode();
            //客户类型为个人时不进行自动调账2016年5月11日14:46:41
            if(!MerUserTypeEnum.PERSONAL.getCode().equals(transactionDTO.getCusTomerType())){
                autoTransfer(transactionDTO.getCusTomerCode());
                DodopalResponse<String> response = paymentDelegate.getIsAuto(transactionDTO.getCusTomerCode());
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 是否共享额度"+response.getResponseEntity());
                }
                if(ResponseCode.SUCCESS.equals(response.getCode())){
                    if("2".equals(response.getResponseEntity())){
                        if (log.isInfoEnabled()) {
                            log.info("PayFacadeImpl freezeAccountAmt 共享额度上级商户号"+code);
                        }
                        code = paymentDelegate.getParentId(transactionDTO.getCusTomerCode()).getResponseEntity();
                    }
                }
            }
           
            
            // 5、   调用账户系统资金冻结接口（接口待开发，先返回成功）
            Date oldMethodDate = new Date();
            
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 调用账户系统资金冻结接口开始");
            }
            DodopalResponse<String> status =  accountManagementService.accountFreeze(transactionDTO.getCusTomerType(), code, tranCode, Math.round(transactionDTO.getAmount()*100),transactionDTO.getOperatorId());//ResponseCode.SUCCESS;
            Date nowMethodDate = new Date();
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 调用账户系统资金冻结接口结束,接口返回的Code为[{}]; message:[{}],用时 [{}] 毫秒", status.getCode(), status.getMessage(),nowMethodDate.getTime() - oldMethodDate.getTime());
            }
            if (ResponseCode.SUCCESS.equals(status.getCode())) {
                //更新交易流水：外部状态 = 『3已支付』
                //内部状态 = 『3账户冻结成功』
                //支付流水状态：支付中
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 冻结成功 更新交易流水 外部状态 = 『3已支付』内部状态 = 『3账户冻结成功』 支付流水状态：支付中");
                }
                payTraTransaction.setTranOutStatus(TranOutStatusEnum.HAS_PAID.getCode());
                payTraTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_FROZEN_SUCCESS.getCode());
                payTraTransaction.setUpdateUser(transactionDTO.getOperatorId());
                payment.setPayStatus(PayStatusEnum.PAYMENT.getCode());
                payment.setUpdateUser(transactionDTO.getOperatorId());

            } else {
                //冻结失败：更新交易流水：外部状态不更新
                //内部状态 = 『4账户冻结失败』
                //支付流水状态：支付失败
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl freezeAccountAmt 冻结失败：更新交易流水：外部状态不更新 内部状态 = 『4账户冻结失败』 支付流水状态：支付失败");
                }
                payTraTransaction.setUpdateUser(transactionDTO.getOperatorId());
                payTraTransaction.setTranInStatus(TranInStatusEnum.ACCOUNT_FROZEN_FAIL.getCode());
                payment.setPayStatus(PayStatusEnum.PAID_FAIL.getCode());
                payment.setUpdateUser(transactionDTO.getOperatorId());
            }
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl freezeAccountAmt 更新交易流水 提交事务");
            }
            payTranService.updatePayStatusAndPayTranStatus(payment, payTraTransaction);
            dodopalResponse.setCode(status.getCode());
            sysLog.setTranNum(payTraTransaction.getTranCode());
            sysLog.setInParas(ReflectionToStringBuilder.toString(payTraTransaction, ToStringStyle.MULTI_LINE_STYLE));

        }catch(DDPException e){
            if (log.isErrorEnabled()) {
                log.error("DDPException ", e);
            }
            sysLog.setStatckTrace(e.getStackTrace().toString());
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(e.getCode());
            return dodopalResponse;
        }catch(HessianRuntimeException e){
        	  e.printStackTrace();
        	  sysLog.setStatckTrace(e.getStackTrace().toString());
              if (log.isErrorEnabled()) {
                  log.error("HessianRuntimeException 账户异常了", e);
              }
              dodopalResponse.setResponseEntity(false);
              dodopalResponse.setCode(ResponseCode.HESSIAN_ERROR);
              return dodopalResponse;
        }catch (Exception e) {
            e.printStackTrace();
            sysLog.setStatckTrace(e.getStackTrace().toString());
            if (log.isErrorEnabled()) {
                log.error("PayFacadeImpl's freezeAccountAmt call error", e);
            }
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }finally{
           
            sysLog.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR )));
            sysLog.setTradeRrack(sysLog.getTradeEnd()-sysLog.getTradeStart());
            
        	sysLog.setRespCode(dodopalResponse.getCode());
            sysLog.setRespExplain(dodopalResponse.getMessage());
            ActivemqLogPublisher.publishLog2Queue(sysLog, DodopalAppVarPropsUtil.getStringProp(PaymentConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(PaymentConstants.SERVER_LOG_URL));
        }
      
        //7、根据上面步骤处理：返回对应的响应码：如果成功返回：“000000”
        return dodopalResponse;
    }

    private String createData(PayTranDTO transactionDTO, List<PayTraTransaction> oldTranList, PayWay payWay, PayTraTransaction payTraTransaction, Payment payment) {
        //外部状态 =  『0：待支付』
        payTraTransaction.setTranOutStatus(TranOutStatusEnum.TO_BE_PAID.getCode());
        //『1：处理中』
        payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
        //生成交易流水
        String tranCode = payTranService.createPayTranCode(transactionDTO.getBusinessType(), transactionDTO.getCityCode());
        payTraTransaction.setTranCode(tranCode);
        //『0：账户支付』
        payTraTransaction.setPayType(PayTypeEnum.PAY_ACT.getCode());
        //订单时间
        payTraTransaction.setOrderDate(transactionDTO.getOrderDate());
        //支付方式名称
        payTraTransaction.setPayWayName(payWay.getPayName());
        //支付方式ID
        payTraTransaction.setPayWay(transactionDTO.getPayId()); 
        //支付网关
        payTraTransaction.setPayGateway(payWay.getBankGatewayType()); 
        //账户消费
        payTraTransaction.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());
        //服务费率
        payTraTransaction.setPayServiceRate(payWay.getPayServiceRate());
        //服务费率*交易金额 转为分
        payTraTransaction.setPayServiceFee(Math.round((payWay.getPayServiceRate() / 1000) * transactionDTO.getAmount() * 100));
        //用户类型
        payTraTransaction.setUserType(transactionDTO.getCusTomerType());
        //手续费率
        payTraTransaction.setPayProceRate(payWay.getProceRate());
        //手续费率 × 交易金额（注意转为分）
        payTraTransaction.setPayProceFee(Math.round((payWay.getProceRate() / 1000) * transactionDTO.getAmount() * 100));
        //实际交易金额 = 交易金额 + 支付手续费（注意转为分）
        payTraTransaction.setRealTranMoney(Math.round((payWay.getProceRate() / 1000) * transactionDTO.getAmount() * 100 + (transactionDTO.getAmount() * 100)));
        //原交易流水号 = 【2】取得交易流水号
        //原交易流水号
        if (CollectionUtils.isNotEmpty(oldTranList)) {
            payTraTransaction.setOldTranCode(oldTranList.get(0).getTranCode());
        }
        payTraTransaction.setCreateUser(transactionDTO.getOperatorId());
        // 清算标志位 =  『0：未清算』
        payTraTransaction.setClearFlag(ClearFlagEnum.NO.getCode());
        //客户号
        payTraTransaction.setMerOrUserCode(transactionDTO.getCusTomerCode());
        //原交易金额
        payTraTransaction.setAmountMoney(Math.round(transactionDTO.getAmount() * 100));
        //来源
        payTraTransaction.setSource(transactionDTO.getSource());
        //业务类型代码
        payTraTransaction.setBusinessType(transactionDTO.getBusinessType());
        //商品名称
        payTraTransaction.setCommodity(transactionDTO.getGoodsName());
        //订单号
        payTraTransaction.setOrderNumber(transactionDTO.getOrderCode());
        payment.setCreateUser(transactionDTO.getOperatorId());
        payment.setTranCode(tranCode);
        //支付状态 = 『0：待支付』
        payment.setPayStatus(PayStatusEnum.TO_BE_PAID.getCode());
        //支付类型 = 『0：账户支付』
        payment.setPayType(PayTypeEnum.PAY_ACT.getCode());
        //        支付方式分类 ：根据参数『外接商户标识』
        //        外接商户标识 =  ture 为 “GW_OUT”
        //        外接商户标识 =  false为 “GW_ALL”
        if (transactionDTO.isExtFlg()) {
            payment.setPayWayKind(PayWayKindEnum.GW_OUT.getCode());
        } else {
            payment.setPayWayKind(PayWayKindEnum.GW_ALL.getCode());
        }
        payment.setPayWayId(payWay.getId());
        //服务费率
        payment.setPayServiceRate(payWay.getPayServiceRate());
        //支付服务费 = 交易金额 × T. 服务费率（注意转为分）
        payment.setPayServiceFee(Math.round((payWay.getPayServiceRate() / 1000) * transactionDTO.getAmount() * 100));
        //支付金额 = 实际交易金额
        payment.setPayMoney(Math.round(transactionDTO.getAmount() * 100));
        //4、  生成交易流水、支付流水（事物提交）
        return tranCode;
    }
    
    

    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayWay
     * @Description: 获取支付方式
     * @param transactionDTO
     * @param payWay
     * @return 设定文件 PayWay 返回类型
     * @throws
     */
    private PayWay findPayWay(PayTranDTO transactionDTO, PayWay payWay) {
        if (log.isDebugEnabled()) {
            if (transactionDTO.isExtFlg())
                log.debug("PayFacadeImpl findPayWay 外接商户获取支付方式 ");
            else
                log.debug("PayFacadeImpl findPayWay 非外接商户获取支付方式 ");
        }
        if (transactionDTO.isExtFlg()) {
            List<PayWay> payWayList = payWayService.findPayWayByPayType(transactionDTO.getCusTomerCode(), PayTypeEnum.PAY_ACT.getCode());
            if (CollectionUtils.isNotEmpty(payWayList)) {
                payWay = payWayList.get(0);
            }
        } else {
            List<PayWay> payWayList = payWayService.findPayWayByPayType(null, PayTypeEnum.PAY_ACT.getCode());
            if (CollectionUtils.isNotEmpty(payWayList)) {
                payWay = payWayList.get(0);
            }
        }
        return payWay;
    }

    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: checkPayTranDTO
     * @Description: 校验参数
     * @param transactionDTO
     * @return 设定文件 String 返回类型
     * @throws
     */
    private String checkPayTranDTO(PayTranDTO transactionDTO) {
        if (log.isDebugEnabled()) {
            log.debug("PayFacadeImpl  checkPayTranDTO 进入校验参数的方法 方法参数为:[{}]", ReflectionToStringBuilder.toString(transactionDTO, ToStringStyle.MULTI_LINE_STYLE));
        }
        // 业务类型
        String businessType = transactionDTO.getBusinessType();
        if (!DDPStringUtil.existingWithLength(businessType, 20)) {
            return ResponseCode.PAY_BUSINESS_TYPE_ERROR;
        }
        if (!DDPStringUtil.existingWithLength(transactionDTO.getCusTomerType(), 1) || null == MerUserTypeEnum.getMerUserUserTypeByCode(transactionDTO.getCusTomerType())) {
            return ResponseCode.PAY_CUSTOMER_TYPE_ERROR;
        }
        if (!DDPStringUtil.existingWithLength(transactionDTO.getCusTomerCode(), 40)) {
            return ResponseCode.PAY_MER_OR_USER_NULL;
        }
        if (!DDPStringUtil.existingWithLength(transactionDTO.getSource(), 2) || null == SourceEnum.getSourceByCode(transactionDTO.getSource())) {
            return ResponseCode.PAY_SOURCE_ERROR;
        }
        if (transactionDTO.getAmount() < 0 || transactionDTO.getAmount() >= 100000000l) {
            return ResponseCode.PAY_AMOUNT_ERROR;
        }
//        if (StringUtils.isBlank(transactionDTO.getOperatorId())) {
//            return ResponseCode.PAY_OPERATOR_ID_NULL;
//        }
        // 非账户充值需要校验以下参数
        if(!RateCodeEnum.ACCT_RECHARGE.getCode().equals(businessType)) {
        	if (!DDPStringUtil.existingWithLength((transactionDTO.getOrderCode()), 40)) {
                return ResponseCode.PAY_ORDER_CODE_NULL;
            }
            if (!DDPStringUtil.existingWithLength(transactionDTO.getGoodsName(), 100)) {
                return ResponseCode.PAY_GOODS_NAME_NULL;
            }
//            if (!DDPStringUtil.existingWithLength(transactionDTO.getCityCode(), 10)) {
//                return ResponseCode.PAY_ICDC_CITYCODE_NULL;
//            }
            if(TranTypeEnum.getTranTypeByCode(transactionDTO.getTranType()) == null) {
            	return ResponseCode.PAY_TRAN_TYPE_ERROR;
            }
        }

        if (log.isInfoEnabled()) {
            log.info("PayFacadeImpl checkPayTranDTO 进入校验参数的方法结束，校验通过");
        }
        return null;
    }

    /**
     * 资金解冻接口
     */
    public DodopalResponse<Boolean> unfreezeAccountAmt(PayTranDTO transactionDTO) {
    	if (log.isInfoEnabled()) {
            log.info("PayFacadeImpl deductAccountAmt  进入资金解冻接口");
        }
    	/**
    	 * 新日志系统
    	 */
        SysLog sysLog = new SysLog();
        sysLog.setServerName(CommonConstants.SYSTEM_NAME_PAYMENT);
        sysLog.setClassName(PayFacadeImpl.class.toString());
        sysLog.setMethodName("unfreezeAccountAmt");
        sysLog.setDescription("资金解冻接口 ");
        sysLog.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR )));
        
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        try {
            String checkResult = checkUnfreezeAndDeductDTO(transactionDTO);
            if (StringUtils.isNotBlank(checkResult)) {
                dodopalResponse.setCode(checkResult);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl unfreezeAccountAmt 开始校验基本参数的结果[{}]", checkResult);
                }
                dodopalResponse.setResponseEntity(false);
                return dodopalResponse;
            }
            transactionDTO.setAmount(transactionDTO.getAmount()/100);
            //参数：客户类型、客户号、订单号，交易类型 = 『2账户消费』
            String userCodeCheck = payTranService.checkMerOrUserCode(transactionDTO);
            if (log.isDebugEnabled()) {
                log.debug("PayFacadeImpl  unfreezeAccountAmt 校验客户号的合法性为[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
            }
            if (!ResponseCode.SUCCESS.equals(userCodeCheck)) {
                dodopalResponse.setCode(userCodeCheck);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl unfreezeAccountAmt 校验客户号的合法性[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
                }
                dodopalResponse.setResponseEntity(false);
                return dodopalResponse;
            }

            //交易类型 账户冻结-账户
            PayTraTransaction transactionQuery = new PayTraTransaction();
            transactionQuery.setUserType(transactionDTO.getCusTomerType());
            transactionQuery.setMerOrUserCode(transactionDTO.getCusTomerCode());
            transactionQuery.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());
            transactionQuery.setOrderNumber(transactionDTO.getOrderCode());
            /**
             * 2取得原交易流水(比如：用户用第三方支付时)一定会生成一条[账户充值]的交易流水.
             * 参数：客户号、客户类型、订单号、交易类型=账户充值
             */
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl unfreezeAccountAmt 方法开始取得原交易流水 参数为 userType :[{}], merOrUserCode:[{}],TranType:[{}],setOrderNumber:[{}] ", transactionDTO.getCusTomerType(), transactionDTO.getCusTomerCode(), TranTypeEnum.ACCOUNT_RECHARGE.getCode(), transactionDTO.getOrderCode());
            }
            List<PayTraTransaction> oldTranList = payTraTransactionService.findPayTraTransactionList(transactionQuery);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl unfreezeAccountAmt 取得的交易流水的list条数为[{}]条", oldTranList.size());
            }
            /**
             * A、如果取到交易流水
             */
            if (CollectionUtils.isNotEmpty(oldTranList)) {
                PayTraTransaction oldTran = oldTranList.get(0);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl unfreezeAccountAmt 原的流水数据[{}]", ReflectionToStringBuilder.toString(oldTran, ToStringStyle.MULTI_LINE_STYLE));
                }
                /**
                 * 判断交易流水状态：外部状态 = 『3已支付』且内部状态 = 『3资金冻结成功』 外部状态 != 『3已支付』或内部状态
                 * != 『3资金冻结成功』
                 */
                sysLog.setTranNum(oldTran.getTranCode());
                sysLog.setInParas(ReflectionToStringBuilder.toString(transactionDTO, ToStringStyle.MULTI_LINE_STYLE));

                
                if (TranOutStatusEnum.HAS_PAID.getCode().equals(oldTran.getTranOutStatus()) && TranInStatusEnum.ACCOUNT_FROZEN_SUCCESS.getCode().equals(oldTran.getTranInStatus())) {
                    String code = transactionDTO.getCusTomerCode();
                  //客户类型为个人时不进行自动调账2016年5月11日14:46:41
                    if(!MerUserTypeEnum.PERSONAL.getCode().equals(transactionDTO.getCusTomerType())){
                        DodopalResponse<String> response = paymentDelegate.getIsAuto(transactionDTO.getCusTomerCode());
                        if (log.isInfoEnabled()) {
                            log.info("PayFacadeImpl freezeAccountAmt 是否共享额度"+response.getResponseEntity());
                        }
                        if(ResponseCode.SUCCESS.equals(response.getCode())){
                            if("2".equals(response.getResponseEntity())){
                                if (log.isInfoEnabled()) {
                                    log.info("PayFacadeImpl freezeAccountAmt 共享额度上级商户号"+code);
                                }
                                code = paymentDelegate.getParentId(transactionDTO.getCusTomerCode()).getResponseEntity();
                            }
                        }
                    }
                    //3 调用账户系统：资金解冻接口
                    DodopalResponse<String> status = accountManagementService.accountUnfreeze(transactionDTO.getCusTomerType(), code, oldTran.getTranCode(), Math.round(oldTran.getAmountMoney()),transactionDTO.getOperatorId());//ResponseCode.SUCCESS;
                    /**
                     * 资金解冻成功：更新交易流水状态：外部状态 = 『8 关闭』 内部状态 = 『5 资金解冻成功』
                     * 更新支付流水：支付状态 = 『4 支付失败』
                     */
                    if (log.isInfoEnabled()) {
                        log.info("PayFacadeImpl unfreezeAccountAmt 账户资金解冻返回Code:[{}] message:[{}]",status.getCode(),status.getMessage());
                    }
                    if (ResponseCode.SUCCESS.equals(status.getCode())) {
                        //外部状态 = 『8 关闭』
                        oldTran.setTranOutStatus(TranOutStatusEnum.CLOSE.getCode());
                        oldTran.setUpdateUser(transactionDTO.getOperatorId());

                        //『5 资金解冻成功』
                        oldTran.setTranInStatus(TranInStatusEnum.ACCOUNT_UNFROZEN_SUCCESS.getCode());
                        if (log.isInfoEnabled()) {
                            log.info("PayFacadeImpl unfreezeAccountAmt 修改交易支付流水 外部状态=关闭，资金解冻成功");
                        }
                        List<Payment> paymentList = payTranService.findPaymentInfoByTranCode(oldTran.getTranCode());
                        if (CollectionUtils.isNotEmpty(paymentList)) {
                            Payment oldpayment = paymentList.get(0);
                            oldpayment.setUpdateUser(transactionDTO.getOperatorId());

                            oldpayment.setPayStatus(PayStatusEnum.PAID_FAIL.getCode());
                            if (log.isInfoEnabled()) {
                                log.info("PayFacadeImpl unfreezeAccountAmt 修改交易支付流水，提交事务");
                            }
                            //更新状态
                            payTranService.updatePayStatusAndPayTranStatus(oldpayment, oldTran);
                            dodopalResponse.setResponseEntity(true);
                            dodopalResponse.setCode(ResponseCode.SUCCESS);
                        } else {
                            //错误信息=非法交易
                            if (log.isInfoEnabled()) {
                                log.info("PayFacadeImpl unfreezeAccountAmt 未查到支付流水信息");
                            }
                            dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                            dodopalResponse.setResponseEntity(false);
                            return dodopalResponse;
                        }
                    } else {
                        /**
                         * 资金解冻失败：更新交易流水状态：外部状态不更新 内部状态资 = 『6资金解冻失败』
                         */
                        //『6资金解冻失败』
                    	oldTran.setUpdateUser(transactionDTO.getOperatorId());
                        oldTran.setTranInStatus(TranInStatusEnum.ACCOUNT_UNFROZEN_FAIL.getCode());
                        if (log.isInfoEnabled()) {
                            log.info("PayFacadeImpl unfreezeAccountAmt 修改交易支付流水 资金解冻失败 并提交事务");
                        }
                        //更新状态
                        payTranService.updatePayStatusAndPayTranStatus(null, oldTran);
                        dodopalResponse.setCode(status.getCode());
                        dodopalResponse.setResponseEntity(false);
                    }
                } else {
                    //错误信息=非法交易
                    if (log.isInfoEnabled()) {
                        log.info("PayFacadeImpl unfreezeAccountAmt 原的流水数据状态不合法");
                    }
                    dodopalResponse.setResponseEntity(false);
                    dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                    return dodopalResponse;
                }
            } else {
                //B、  如果取不到交易流水：程序结束，错误信息：非法交易
                //错误信息=非法交易
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl unfreezeAccountAmt 原的流水数据状态不合法");
                }
                dodopalResponse.setResponseEntity(false);
                dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                return dodopalResponse;
            }
        }catch(DDPException e){
              if (log.isErrorEnabled()) {
                  log.error("DDPException ", e);
              }
              sysLog.setStatckTrace(e.getStackTrace().toString());
              dodopalResponse.setResponseEntity(false);
              dodopalResponse.setCode(e.getCode());
              return dodopalResponse;
        }catch(HessianRuntimeException e){
      	  e.printStackTrace();
      	  sysLog.setStatckTrace(e.getStackTrace().toString());
          if (log.isErrorEnabled()) {
              log.error("HessianRuntimeException 账户异常了", e);
          }
          dodopalResponse.setResponseEntity(false);
          dodopalResponse.setCode(ResponseCode.HESSIAN_ERROR);
          return dodopalResponse;
        }catch (Exception e) {
        	sysLog.setStatckTrace(e.getStackTrace().toString());
            e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("PayFacadeImpl's unfreezeAccountAmt call error", e);
            }
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }finally{
            sysLog.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR )));
            sysLog.setTradeRrack(sysLog.getTradeEnd()-sysLog.getTradeStart());
            
        	sysLog.setRespCode(dodopalResponse.getCode());
            sysLog.setRespExplain(dodopalResponse.getMessage());
            ActivemqLogPublisher.publishLog2Queue(sysLog, DodopalAppVarPropsUtil.getStringProp(PaymentConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(PaymentConstants.SERVER_LOG_URL));
        }

        return dodopalResponse;
    }

    private String checkUnfreezeAndDeductDTO(PayTranDTO transactionDTO) {
        if (log.isDebugEnabled()) {
            log.debug("PayFacadeImpl checkUnfreezeDTO 进入校验参数的方法 方法参数为:[{}]", ReflectionToStringBuilder.toString(transactionDTO, ToStringStyle.MULTI_LINE_STYLE));
        }
        if (!DDPStringUtil.existingWithLength(transactionDTO.getCusTomerType(), 1) || null == MerUserTypeEnum.getMerUserUserTypeByCode(transactionDTO.getCusTomerType())) {
            return ResponseCode.PAY_CUSTOMER_TYPE_ERROR;
        }
        if (!DDPStringUtil.existingWithLength(transactionDTO.getCusTomerCode(), 40)) {
            return ResponseCode.PAY_MER_OR_USER_NULL;
        }
        if (!DDPStringUtil.existingWithLength((transactionDTO.getOrderCode()), 40)) {
            return ResponseCode.PAY_ORDER_CODE_NULL;
        }
//        if (StringUtils.isBlank(transactionDTO.getOperatorId())) {
//            return ResponseCode.PAY_OPERATOR_ID_NULL;
//        }
        if (log.isInfoEnabled()) {
            log.info("PayFacadeImpl checkUnfreezeDTO 进入校验参数的方法结束，校验通过");
        }
        return null;
    }

    @Override
    public DodopalResponse<Boolean> deductAccountAmt(PayTranDTO transactionDTO) {
    	if (log.isInfoEnabled()) {
            log.info("PayFacadeImpl deductAccountAmt  进入资金扣款接口");
        }
    	/**
    	 * 新日志系统
    	 */
        SysLog sysLog = new SysLog();
        sysLog.setServerName(CommonConstants.SYSTEM_NAME_PAYMENT);
        sysLog.setClassName(PayFacadeImpl.class.toString());
        sysLog.setMethodName("deductAccountAmt");
        sysLog.setDescription("资金扣款接口 ");
        sysLog.setTradeStart(Long.parseLong(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR )));

        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        try {
            String checkResult = checkUnfreezeAndDeductDTO(transactionDTO);
            if (StringUtils.isNotBlank(checkResult)) {
                dodopalResponse.setCode(checkResult);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl deductAccountAmt  开始校验基本参数的结果[{}],message:[{}]", checkResult,dodopalResponse.getMessage());
                }
                dodopalResponse.setResponseEntity(false);
                return dodopalResponse;
            }
            //参数：客户类型、客户号、订单号，交易类型 = 『2账户消费』
            String userCodeCheck = payTranService.checkMerOrUserCode(transactionDTO);
            if (log.isDebugEnabled()) {
                log.debug("PayFacadeImpl deductAccountAmt unfreezeAccountAmt 校验客户号的合法性为[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
            }
            if (!ResponseCode.SUCCESS.equals(userCodeCheck)) {
                dodopalResponse.setCode(userCodeCheck);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl deductAccountAmt 校验客户号的合法性[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
                }
                dodopalResponse.setResponseEntity(false);
                return dodopalResponse;
            }

            //交易类型 账户冻结-账户
            PayTraTransaction transactionQuery = new PayTraTransaction();
            transactionQuery.setUserType(transactionDTO.getCusTomerType());
            transactionQuery.setMerOrUserCode(transactionDTO.getCusTomerCode());
            transactionQuery.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());
            transactionQuery.setOrderNumber(transactionDTO.getOrderCode());
            /**
             * 2取得原交易流水(比如：用户用第三方支付时)一定会生成一条[账户充值]的交易流水.
             * 参数：客户号、客户类型、订单号、交易类型=账户充值
             */
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl 方法开始取得原交易流水 参数为 userType :[{}], merOrUserCode:[{}],TranType:[{}],setOrderNumber:[{}] ", transactionDTO.getCusTomerType(), transactionDTO.getCusTomerCode(), TranTypeEnum.ACCOUNT_RECHARGE.getCode(), transactionDTO.getOrderCode());
            }
            List<PayTraTransaction> oldTranList = payTraTransactionService.findPayTraTransactionList(transactionQuery);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl 取得的交易流水的list条数为[{}]条", oldTranList.size());
            }
            /*
             * A、如果取到交易流水
             */
            if (CollectionUtils.isNotEmpty(oldTranList)) {
                PayTraTransaction oldTran = oldTranList.get(0);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl unfreezeAccountAmt 原的流水数据[{}]", ReflectionToStringBuilder.toString(oldTran, ToStringStyle.MULTI_LINE_STYLE));
                }
                
                sysLog.setTranNum(oldTran.getTranCode());
                sysLog.setInParas(ReflectionToStringBuilder.toString(transactionDTO, ToStringStyle.MULTI_LINE_STYLE));

                /**
                 * 判断交易流水状态：外部状态 = 『3已支付』且内部状态 = 『3资金冻结成功』
                 *  外部状态 != 『3已支付』或内部状态
                 * != 『3资金冻结成功』
                 */
                if (TranOutStatusEnum.HAS_PAID.getCode().equals(oldTran.getTranOutStatus()) && TranInStatusEnum.ACCOUNT_FROZEN_SUCCESS.getCode().equals(oldTran.getTranInStatus())) {
                    String code = transactionDTO.getCusTomerCode();
                    //客户类型为个人时不进行自动调账2016年5月11日14:46:41
                    if(!MerUserTypeEnum.PERSONAL.getCode().equals(transactionDTO.getCusTomerType())){
                        DodopalResponse<String> response = paymentDelegate.getIsAuto(transactionDTO.getCusTomerCode());
                        if (log.isInfoEnabled()) {
                            log.info("PayFacadeImpl freezeAccountAmt 是否共享额度"+response.getResponseEntity());
                        }
                        if(ResponseCode.SUCCESS.equals(response.getCode())){
                            if("2".equals(response.getResponseEntity())){
                                if (log.isInfoEnabled()) {
                                    log.info("PayFacadeImpl freezeAccountAmt 共享额度上级商户号"+code);
                                }
                                code = paymentDelegate.getParentId(transactionDTO.getCusTomerCode()).getResponseEntity();
                            }
                        }
                    }
                    //3 调用账户系统：资金扣款接口
                    DodopalResponse<String> status = accountManagementService.accountDeduct(transactionDTO.getCusTomerType(), code, oldTran.getTranCode(), Math.round(oldTran.getAmountMoney()),transactionDTO.getOperatorId());//ResponseCode.SUCCESS;
                    /**
                     * 更新交易流水状态：外部状态不更新 内部状态 = 『7资金扣款成功』 更新支付流水：支付状态 = 『3 支付成功』
                     */
                    if (ResponseCode.SUCCESS.equals(status.getCode())) {
                    	if (log.isInfoEnabled()) {
                           log.info("PayFacadeImpl deductAccountAmt 修改交易支付流水 外部状态不更新 内部状态 = 『7资金扣款成功』更新支付流水：支付状态 = 『3 支付成功』");
                        }
                        //『7资金扣款成功』
                    	oldTran.setUpdateUser(transactionDTO.getOperatorId());
                        oldTran.setTranInStatus(TranInStatusEnum.ACCOUNT_DEBIT_SUCCESS.getCode());
                        List<Payment> paymentList = payTranService.findPaymentInfoByTranCode(oldTran.getTranCode());
                        if (CollectionUtils.isNotEmpty(paymentList)) {
                            Payment oldpayment = paymentList.get(0);
                            //『3 支付成功』
                            oldpayment.setPayStatus(PayStatusEnum.PAID_SUCCESS.getCode());
                            //更新状态
                            oldpayment.setUpdateUser(transactionDTO.getOperatorId());
                            payTranService.updatePayStatusAndPayTranStatus(oldpayment, oldTran);
                            dodopalResponse.setResponseEntity(true);
                            dodopalResponse.setCode(ResponseCode.SUCCESS);
                                                        
                            // 执行自动转账(2016/04/22 提至资金冻结接口)
                            // autoTransfer(transactionDTO.getCusTomerCode());
                        } else {
                        
                            //错误信息=非法交易
                            if (log.isInfoEnabled()) {
                                log.info("PayFacadeImpl unfreezeAccountAmt 未查到支付流水信息");
                            }
                            dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                            dodopalResponse.setResponseEntity(false);
                            return dodopalResponse;
                        }
                    } else {
                        /**
                         * 资金扣款失败：更新交易流水状态：外部状态不更新 内部状态资 = 『8资金扣款失败』
                         */
                    	if (log.isInfoEnabled()) {
                            log.info("PayFacadeImpl deductAccountAmt 资金扣款失败：更新交易流水状态：外部状态不更新 内部状态资 = 『8资金扣款失败』");
                         }
                        //『8资金扣款失败』
                    	oldTran.setUpdateUser(transactionDTO.getOperatorId());
                        oldTran.setTranInStatus(TranInStatusEnum.ACCOUNT_DEBIT_FAIL.getCode());
                        //更新状态
                        payTranService.updatePayStatusAndPayTranStatus(null, oldTran);
                        dodopalResponse.setResponseEntity(false);
                        dodopalResponse.setCode(status.getCode());
                    }
                }else if(TranInStatusEnum.ACCOUNT_DEBIT_SUCCESS.getCode().equals(oldTran.getTranInStatus())){
                    //此次操作，交易流水表中扣款成功的话则重复扣款 不做操作2016年3月14日15:26:13
                    dodopalResponse.setResponseEntity(false);
                    //重复扣款
                    dodopalResponse.setCode(ResponseCode.PAY_DEDUCT_AGAIN_ERROR);
                    return dodopalResponse;
                } else {
                    //错误信息=非法交易
                    if (log.isInfoEnabled()) {
                        log.info("PayFacadeImpl unfreezeAccountAmt 原的流水数据状态不合法");
                    }
                    dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                    dodopalResponse.setResponseEntity(false);
                    return dodopalResponse;
                }
            } else {
                //B、  如果取不到交易流水：程序结束，错误信息：非法交易
                //错误信息=非法交易
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl unfreezeAccountAmt 原的流水数据状态不合法");
                }
                dodopalResponse.setCode(ResponseCode.PAY_ILLEGAL_TRADE);
                dodopalResponse.setResponseEntity(false);
                return dodopalResponse;
            }
        }catch(DDPException e){
              if (log.isErrorEnabled()) {
                  log.error("DDPException ", e);
              }
              dodopalResponse.setResponseEntity(false);
              dodopalResponse.setCode(e.getCode());
              return dodopalResponse;
        }catch(HessianRuntimeException e){
        	  e.printStackTrace();
              if (log.isErrorEnabled()) {
                  log.error("HessianRuntimeException 账户异常了", e);
              }
              dodopalResponse.setResponseEntity(false);
              dodopalResponse.setCode(ResponseCode.HESSIAN_ERROR);
              return dodopalResponse;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("PayFacadeImpl's unfreezeAccountAmt call error", e);
            }
            dodopalResponse.setResponseEntity(false);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }finally{
            sysLog.setTradeEnd(Long.parseLong(DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR )));
            sysLog.setTradeRrack(sysLog.getTradeEnd()-sysLog.getTradeStart());
            
        	sysLog.setRespCode(dodopalResponse.getCode());
            sysLog.setRespExplain(dodopalResponse.getMessage());
            ActivemqLogPublisher.publishLog2Queue(sysLog, DodopalAppVarPropsUtil.getStringProp(PaymentConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(PaymentConstants.SERVER_LOG_URL));
        }

        return dodopalResponse;
    }

    /* 
     *手机端支付
     */
    public DodopalResponse<String> mobilepay(PayTranDTO transactionDTO) {
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        try {
            //1、  验证
            String checkResult = checkPayTranDTO(transactionDTO);
            if (StringUtils.isNotBlank(checkResult)) {
                dodopalResponse.setCode(checkResult);
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl mobilepay 开始校验基本参数的结果[{}],message:[{}]", checkResult,dodopalResponse.getMessage());
                }
                return dodopalResponse;
            }
            //校验客户号的合法性
            String userCodeCheck = payTranService.checkMerOrUserCode(transactionDTO);
            if (log.isDebugEnabled()) {
                log.debug("PayFacadeImpl mobilepay 校验客户号的合法性为[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
            }
            if (!ResponseCode.SUCCESS.equals(userCodeCheck)) {
                dodopalResponse.setCode(userCodeCheck);
              
                if (log.isInfoEnabled()) {
                    log.info("PayFacadeImpl mobilepay 校验客户号的合法性[{}],message:[{}]", userCodeCheck,dodopalResponse.getMessage());
                }
                return dodopalResponse;
            }

          
            //根据支付ID 取到通用支付方式
            PayWayGeneral payWay = payWayGeneralService.findPayGeneralById(transactionDTO.getPayId());
            //根据
            PayConfig  payconfig =   payConfigService.findPayConfigById(payWay.getPayConfigId());
           
            /**
             * 3、 组装数据 交易流水号规则 T + 业务代码 + 城市编码+ 20150428222211（时间戳） +六位数据库cycle
             * sequence（循环使用）
             */
            PayTraTransaction payTraTransaction = new PayTraTransaction();
            Payment payment = new Payment();
            String tranCode = createMobilepayData(transactionDTO, payTraTransaction,payconfig, payment);

            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl mobilepay 生成交易流水支付流水数据完成，现在进行事务提交");
            }
            payTranService.savePaymentAndPayTran(payment, payTraTransaction);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl mobilepay 交易流水支付流水数据事务提交完成");
            }

            dodopalResponse.setResponseEntity(tranCode);
            if (log.isInfoEnabled()) {
                log.info("PayFacadeImpl mobilepay 更新交易流水 提交事务");
            }
//            payTranService.updatePayStatusAndPayTranStatus(payment, payTraTransaction);
        }catch (Exception e) {
            e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("PayFacadeImpl's mobilepay call error", e);
            }
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        //7、根据上面步骤处理：返回对应的响应码：如果成功返回：“000000”
        return dodopalResponse;
    }
    
    private String createMobilepayData(PayTranDTO transactionDTO, PayTraTransaction payTraTransaction,PayConfig  payWay, Payment payment) {
        //外部状态 =  『0：待支付』
        payTraTransaction.setTranOutStatus(TranOutStatusEnum.TO_BE_PAID.getCode());
        //『1：处理中』
        payTraTransaction.setTranInStatus(TranInStatusEnum.PROCESSED.getCode());
        //生成交易流水
        String tranCode = payTranService.createPayTranCode(transactionDTO.getBusinessType(), transactionDTO.getCityCode());
        payTraTransaction.setTranCode(tranCode);
        //支付类型
        payTraTransaction.setPayType(payWay.getPayType());
        //订单时间
        payTraTransaction.setOrderDate(transactionDTO.getOrderDate());
        //支付方式名称
        payTraTransaction.setPayWay(transactionDTO.getPayId());
        //支付方式名称
        payTraTransaction.setPayWayName(payWay.getPayWayName());
        //交易类型
        payTraTransaction.setTranType(transactionDTO.getTranType());
        //TODO 个人手机端 服务费为0
        //服务费率
        payTraTransaction.setPayServiceRate(0);
        //服务费率*交易金额 转为分
        payTraTransaction.setPayServiceFee(0);
        //用户类型
        payTraTransaction.setUserType(transactionDTO.getCusTomerType());
        //TODO 个人手机端 手续费率0
        //手续费率
        payTraTransaction.setPayProceRate(0);
        //手续费率 × 交易金额（注意转为分）
        payTraTransaction.setPayProceFee(0);
        //实际交易金额 = 交易金额 + 支付手续费（注意转为分）
        payTraTransaction.setRealTranMoney(0);
//        //原交易流水号 = 【2】取得交易流水号
//        //原交易流水号
//        if (CollectionUtils.isNotEmpty(oldTranList)) {
//            payTraTransaction.setOldTranCode(oldTranList.get(0).getTranCode());
//        }
        payTraTransaction.setCreateUser(transactionDTO.getOperatorId());
        // 清算标志位 =  『0：未清算』
        payTraTransaction.setClearFlag(ClearFlagEnum.NO.getCode());
        //客户号
        payTraTransaction.setMerOrUserCode(transactionDTO.getCusTomerCode());
        //原交易金额
        payTraTransaction.setAmountMoney(Math.round(transactionDTO.getAmount()));
        //来源
        payTraTransaction.setSource(transactionDTO.getSource());
        //业务类型代码
        payTraTransaction.setBusinessType(transactionDTO.getBusinessType());
        //商品名称
        payTraTransaction.setCommodity(transactionDTO.getGoodsName());
        //订单号
       if(RateCodeEnum.ACCT_RECHARGE.getCode().equals(transactionDTO.getBusinessType())){           
          payTraTransaction.setOrderNumber(tranCode);
           
       }else{
          payTraTransaction.setOrderNumber(transactionDTO.getOrderCode());
       }
       payTraTransaction.setServiceNoticeUrl(transactionDTO.getNotifyUrl());
       
        payment.setCreateUser(transactionDTO.getOperatorId());
        payment.setTranCode(tranCode);
        //支付状态 = 『0：待支付』
        payment.setPayStatus(PayStatusEnum.TO_BE_PAID.getCode());
        //支付类型 = 『0：账户支付』
        payment.setPayType(payWay.getPayType());
        //        支付方式分类 ：根据参数『外接商户标识』
        //        外接商户标识 =  ture 为 “GW_OUT”
        //        外接商户标识 =  false为 “GW_ALL”       
        payment.setPayWayKind(PayWayKindEnum.GW_ALL.getCode());
        
        payment.setPayWayId(transactionDTO.getPayId());
        //服务费率
        payment.setPayServiceRate(0);
        //支付服务费 = 交易金额 × T. 服务费率（注意转为分）
        payment.setPayServiceFee(0);
        //支付金额 = 实际交易金额
        payment.setPayMoney(Math.round(transactionDTO.getAmount() * 100));
        //4、  生成交易流水、支付流水（事物提交）
        return tranCode;
    }
    
    /***********************************************    以下是  自动转账  业务处理流程 2015-12-08 11:45:39    *************************************************************************************/
	@Override
	public DodopalResponse<Boolean> autoTransfer(String merCode) {
		DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<>();
		try {
			 if(StringUtils.isBlank(merCode)){
				 if (log.isInfoEnabled()) {
	                 log.info("PayFacadeImpl autoTransfer  merCode(商户号) 为空 或者null");
	             }
				 dodopalResponse.setResponseEntity(false);
				 dodopalResponse.setCode(ResponseCode.PAY_MER_CODE_NULL);
				 return dodopalResponse;
			 }
			 if (log.isInfoEnabled()) {
                 log.info("PayFacadeImpl autoTransfer 商户的合法性,当前商户号为	:"+merCode);
	         }
			// TODO 1.商户合法性校验
			DodopalResponse<Map<String, String>> response = paymentDelegate.checkMerInfo(merCode);
			if (log.isInfoEnabled()) {
				log.info("PayFacadeImpl autoTransfer 商户的合法性返回值	:" + JSONObject.toJSONString(response));
			}
			if(ResponseCode.SUCCESS.equals(response.getCode())){
				//	商户合法性校验通过
				Map<String, String> map =response.getResponseEntity();
				String parentMerCode = map.get("parentMerCode");		//接口返回		String	上级商户编号
				String parentMerUserId = map.get("parentMerUserId");	//接口返回		String	上级商户管理员ID
				String merUserId = map.get("merUserId");				//接口返回		String	当前商户管理员ID
				String thresholdAmt = map.get("thresholdAmt");			//接口返回		String	额度阀值
				//String transferAmt = map.get("transferAmt");
				String autoThresholdAmt = map.get("transferAmt");		//接口返回		String	自动转账到额度阀值
				//TODO 2.获取当前商户可用余额 资金授信账户信息
				DodopalResponse<AccountFundDTO> accResponse = paymentDelegate.findAccountBalance(MerUserTypeEnum.MERCHANT.getCode(), merCode);
				if (log.isInfoEnabled()) {
					log.info("PayFacadeImpl autoTransfer 获取当前商户可用余额 资金授信账户信息	:" + JSONObject.toJSONString(accResponse));
				}
				if(ResponseCode.SUCCESS.equals(accResponse.getCode())){
					//TODO 3.校验当前商户账户可用余额是否小于阀值 通过商户code和商户类型，调用账户系统接口取得资金账户可用余额　
					if(accResponse.getResponseEntity().getAccountMoney() <= Double.valueOf(thresholdAmt)/100){
						if (log.isInfoEnabled()) {
							log.info("当前商户账户可用余额:"+accResponse.getResponseEntity().getAccountMoney()+",阀值为:"+Double.valueOf(thresholdAmt)/100+";满足转账条件开始转账..");
						}
						double transferAmt  = Double.valueOf(autoThresholdAmt)/100-accResponse.getResponseEntity().getAccountMoney() ;//转账金额
						//1.获取上级商户剩余额度
						DodopalResponse<AccountFundDTO> parentResponse = paymentDelegate.findAccountBalance(MerUserTypeEnum.MERCHANT.getCode(), parentMerCode);
						if(ResponseCode.SUCCESS.equals(parentResponse.getCode())){
							double parentAccountMoney = parentResponse.getResponseEntity().getAccountMoney();//上级商户剩余额度
							if(parentAccountMoney < transferAmt){//上级商户剩余额度小于转账金额时
								transferAmt = parentAccountMoney;
							}
						}else{
							if (log.isInfoEnabled()) {
								log.info("PayFacadeImpl autoTransfer 获取上级商户剩余额度失败" + JSONObject.toJSONString(parentResponse));
							}
							dodopalResponse.setResponseEntity(false);
						}
						List<PayTransferDTO> payTransferDTOList = new ArrayList<PayTransferDTO>(2);
						//TODO 4.上级商户 ->参数封装
						PayTransferDTO payTransferDTO = new PayTransferDTO();
						payTransferDTO.setAmount(transferAmt);					//转账的金额，单位为分。必须为正整数
						payTransferDTO.setBusinessType(RateCodeEnum.AUTO_TRANSFER.getCode());	//业务类型: 主动转账:code 97
						payTransferDTO.setComments("自动转出"); 									//备注说明信息
						payTransferDTO.setCreateUser(parentMerUserId);							//上级商户的管理员ID
						payTransferDTO.setSourceCustType(MerUserTypeEnum. MERCHANT.getCode());	//枚举：个人、企业 表示转出账户的客户类型。当前值为:1
						payTransferDTO.setSourceCustNum(parentMerCode);							//上级商户code
						payTransferDTO.setTransferFlag(TranTypeEnum.TURN_OUT.getCode());		//当前为:7 转出
						payTransferDTO.setTargetCustType(MerUserTypeEnum. MERCHANT.getCode());	//枚举：个人、企业表示转入账户的客户类型。当前值为:1
						payTransferDTO.setTargetCustNum(merCode);								//表示转入账户的客户号
						
						if (log.isInfoEnabled()) {
							log.info("PayFacadeImpl autoTransfer 1.上级商户参数封装	:" + JSONObject.toJSONString(payTransferDTO));
						}
						//TODO 5.直营网点商户
						PayTransferDTO payTransfer = new PayTransferDTO();
						payTransfer.setAmount(transferAmt);
						payTransfer.setBusinessType(RateCodeEnum.AUTO_TRANSFER.getCode());		//业务类型: 主动转账:code 97
						payTransfer.setComments("自动转入"); 									//备注说明信息
						payTransfer.setCreateUser(merUserId);									//直营网点商户的管理员ID
						payTransfer.setSourceCustType( MerUserTypeEnum. MERCHANT.getCode());	//枚举：个人、企业 表示转出账户的客户类型。当前值为:1
						payTransfer.setSourceCustNum(merCode);									//当前商户code
						payTransfer.setTransferFlag(TranTypeEnum.TURN_INTO.getCode());			//当前为:9 转入
						payTransfer.setTargetCustType( MerUserTypeEnum. MERCHANT.getCode());	//枚举：个人、企业表示转入账户的客户类型。当前值为:1
						payTransfer.setTargetCustNum(parentMerCode);							//表示转入账户的客户号

						if (log.isInfoEnabled()) {
							log.info("PayFacadeImpl autoTransfer 2.直营网点商户参数封装	:" + JSONObject.toJSONString(payTransfer));
						}
						
						payTransferDTOList.add(payTransferDTO);
						payTransferDTOList.add(payTransfer);
						DodopalResponse<Boolean>  response2 = payTraTransactionFacade.transfercreateTran(payTransferDTOList);
						if(ResponseCode.SUCCESS.equals(response2.getCode())){
							dodopalResponse.setResponseEntity(true);
							if (log.isInfoEnabled()) {
								log.info("PayFacadeImpl autoTransfer 转账结束,本次转账成功:返回信息: "+JSONObject.toJSONString(response2));
							}
						}else{
							dodopalResponse.setResponseEntity(false);
							if (log.isInfoEnabled()) {
								log.info("PayFacadeImpl autoTransfer 转账结束,本次转账失败,返回信息: "+JSONObject.toJSONString(response2));
							}
						}
					}else{
						if (log.isInfoEnabled()) {
							log.info("当前商户账户可用余额'>'阀值,当前可用余额为:"+accResponse.getResponseEntity().getAvailableBalance()+",阀值为:"+thresholdAmt);
						}
					}
				}else{
					if (log.isInfoEnabled()) {
						log.info("PayFacadeImpl autoTransfer 获取当前商户可用余额 资金授信账户信息失败	:" + JSONObject.toJSONString(accResponse));
					}
					dodopalResponse.setResponseEntity(false);
				}
			}
//			else{
//				if (log.isInfoEnabled()) {
//					log.info("PayFacadeImpl autoTransfer 验证商户合法性失败	:" + JSONObject.toJSONString(response));
//				}
//				dodopalResponse.setResponseEntity(false);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("PayFacadeImpl's autoTransfer call error", e);
			}
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}


	/************************************************************** 自动转账 结束  ***************************************************************************/
	
	/** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2015年12月29日 下午2:44:37 
	  * @version 1.0 一卡通圈存订单扣款接口
	  * @parameter  
	  * @since  
	  * @return  
	  */
	@Override
    public DodopalResponse<Boolean> loadOrderDeductAccountAmt(PayTranDTO transactionDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        
        PayTraTransaction transactionQuery = new PayTraTransaction();
        transactionQuery.setUserType(transactionDTO.getCusTomerType());
        transactionQuery.setMerOrUserCode(transactionDTO.getCusTomerCode());
        //transactionQuery.setTranType(TranTypeEnum.ACCOUNT_RECHARGE.getCode());
        transactionQuery.setOrderNumber(transactionDTO.getOrderCode());
        if (log.isInfoEnabled()) {
            log.info("PayFacadeImpl loadOrderDeductAccountAmt 方法开始取得原交易流水 参数为 userType :[{}], merOrUserCode:[{}],TranType:[{}],setOrderNumber:[{}] ", transactionDTO.getCusTomerType(), transactionDTO.getCusTomerCode(), TranTypeEnum.ACCOUNT_RECHARGE.getCode(), transactionDTO.getOrderCode());
        }
        List<PayTraTransaction> oldTranList = payTraTransactionService.findPayTraTransactionList(transactionQuery);
        if (log.isInfoEnabled()) {
            log.info("PayFacadeImpl loadOrderDeductAccountAmt 取得的交易流水的list条数为[{}]条", oldTranList.size());
        }
        if (CollectionUtils.isNotEmpty(oldTranList)) {
            if(oldTranList.size()>1){
                log.info("PayFacadeImpl loadOrderDeductAccountAmt 之前已经进入方法体内一次，此次为支付宝通知接口回调  程序终止");
                dodopalResponse.setCode(ResponseCode.SUCCESS);
                return dodopalResponse;
            }
        }
        PayTranDTO deductDTO  = new PayTranDTO();
        deductDTO.setCusTomerType(transactionDTO.getCusTomerType());
        deductDTO.setCusTomerCode(transactionDTO.getCusTomerCode());
        deductDTO.setOrderCode(transactionDTO.getOrderCode());
        deductDTO.setTranType(TranTypeEnum.ACCOUNT_CONSUMPTION.getCode());
        deductDTO.setOperatorId(transactionDTO.getOperatorId());
        dodopalResponse =   this.freezeAccountAmt(transactionDTO);
        if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
            dodopalResponse = this.deductAccountAmt(deductDTO);
        }
        return dodopalResponse;
    }

    
}
