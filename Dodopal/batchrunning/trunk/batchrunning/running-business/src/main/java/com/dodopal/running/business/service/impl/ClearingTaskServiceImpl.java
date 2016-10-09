/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.running.business.service.impl;

import com.dodopal.common.enums.*;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DDPLog;
import com.dodopal.running.business.dao.ClearingTaskMapper;
import com.dodopal.running.business.model.*;
import com.dodopal.running.business.service.ClearingTaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lenovo on 2015/10/14.
 */
@Service
public class ClearingTaskServiceImpl implements ClearingTaskService {
    private static Logger log = Logger.getLogger(ClearingTaskServiceImpl.class);
    private DDPLog<ClearingTaskServiceImpl> ddpLog = new DDPLog<ClearingTaskServiceImpl>(ClearingTaskServiceImpl.class);
    @Autowired
    ClearingTaskMapper clearingTaskMapper;

    public String getYestoday() {
        return clearingTaskMapper.getYestoday();
    }

    /**
     * @return
     * @description 一卡通充值查询需要清分的数据
     */
    public List<ProductOrder> queryYKTRchargeClearingData() {
        return clearingTaskMapper.queryYKTRchargeClearingData();
    }

    /**
     * @return
     * @description 账户充值查询需要清分的数据
     */
    public List<PayTransaction> queryAccountRechargeClearingData() {
        return clearingTaskMapper.queryAccountRechargeClearingData();
    }

    /**
     * @return
     * @description 一卡通消费查询需要清分的数据
     */
    public List<Purchase> queryYKTPurchaseClearingData() {
        return clearingTaskMapper.queryYKTPurchaseClearingData();
    }

    /**
     * @param productOrder
     * @return
     * @throws SQLException
     * @descripton 一卡通充值清分基础表添加新纪录
     */
    @Transactional
    public void addYKTRechargeClearingData(ProductOrder productOrder) throws SQLException {
        ClearingDataModel clearingDataModel = getYKTRechageClearingDate(productOrder);
        if (clearingDataModel != null) {
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            clearingDataModel.setOrderDay(dateStr);
            log.info("======一卡通充值插入清分记录表数据开始==orderNumber====="+productOrder.getProOrderNum());
            int addColumn = clearingTaskMapper.addClearingData(clearingDataModel);
            log.info("======一卡通充值插入清分记录表数据结束=====");
            int updateFlag = clearingTaskMapper.updateProductOrderClearingFlag(clearingDataModel.getOrderNo(), ClearFlagEnum.YES.getCode());
        }
    }

    /**
     * @param payTransaction
     * @return
     * @throws SQLException
     * @descripton 账户充值清分基础表添加新纪录
     */
    @Transactional
    public void addAccountRechargeClearingData(PayTransaction payTransaction) throws SQLException {
        ClearingDataModel clearingDataModel = getAccountRechageClearingDate(payTransaction);
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        clearingDataModel.setOrderDay(dateStr);
        log.info("======账户充值插入清分记录表数据开始==trancode=====" + payTransaction.getTranCode());
        int addColumn = clearingTaskMapper.addClearingData(clearingDataModel);
        log.info("======账户充值插入清分记录表数据结束=======");
        int updateFlag = clearingTaskMapper.updateTransactionClearingFlag(clearingDataModel.getOrderNo(), ClearFlagEnum.YES.getCode());
    }

    /**
     * @param purchase
     * @return
     * @throws SQLException
     * @descripton 一卡通消费清分基础表添加新纪录
     */
    @Transactional
    public void addYKTPurchaseClearingData(Purchase purchase) throws SQLException {
        ClearingDataModel clearingDataModel = getYKTPurchaseClearingDate(purchase);
        if (clearingDataModel != null) {
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            clearingDataModel.setOrderDay(dateStr);
            log.info("======一卡通消费插入清分记录表数据开始==orderNum=====" + purchase.getOrderNum());
            int addColumn = clearingTaskMapper.addClearingData(clearingDataModel);
            log.info("======一卡通消费插入清分记录表数据结束=====");
            int updateFlag = clearingTaskMapper.updatePurchaseOrderClearingFlag(clearingDataModel.getOrderNo(), ClearFlagEnum.YES.getCode());
        }
    }

    /**
     * @param productOrder
     * @return
     * @description 获取新增清分基础表数据模型，根据不同状态设置不同的清分数据值
     */
    @Transactional
    public ClearingDataModel getYKTRechageClearingDate(ProductOrder productOrder) {
        ClearingDataModel clearingDataModel =null;
        //**资金扣款成功，且内部状态是资金扣款成功  对应文档 case1 三方都进行清分**
        if (RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState()) &&
                RechargeOrderInternalStatesEnum.FUND_DEDUCT_SUCCESS.getCode().equals(productOrder
                        .getProInnerState())) {
            clearingDataModel = new ClearingDataModel();
            clearingDataModel.setOrderNo(productOrder.getProOrderNum());//订单交易号
            clearingDataModel.setOrderDate(productOrder.getCreateDate());//订单时间
//        clearingDataModel.setOrderDay(productOrder.getProOrderDay());//订单日期
            clearingDataModel.setCustomerNo(productOrder.getMerCode());//商户编号
            if(MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())){
                clearingDataModel.setCustomerName(productOrder.getUserName());//商户名称
            }else{
                clearingDataModel.setCustomerName(productOrder.getMerName());//商户名称
            }
            clearingDataModel.setCustomerType(productOrder.getMerUserType());//商户类型
            clearingDataModel.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());//业务类型
            clearingDataModel.setCustomerRateType(productOrder.getMerRateType());//商户业务费率类型
            clearingDataModel.setCustomerRate(productOrder.getMerRate());//商户业务费率
            clearingDataModel.setOrderAmount(productOrder.getTxnAmt());//订单金额
            clearingDataModel.setOrderFrom(productOrder.getSource());//订单来源
            clearingDataModel.setOrderCircle(productOrder.getLoadFlag());//是否圈存订单
            clearingDataModel.setPayWay(productOrder.getPayWay());//支付方式
            clearingDataModel.setPayType(productOrder.getPayType());//支付类型
            clearingDataModel.setServiceRateType(productOrder.getPayServiceType());//服务费率类型
            clearingDataModel.setServiceRate(productOrder.getPayServiceRate());//服务费率
            clearingDataModel.setSupplierCode(productOrder.getYktCode());//供应商
            //根据一卡通code拿到一卡通的名称
            String supplierName = clearingTaskMapper.getSupplierName(productOrder.getYktCode());
            clearingDataModel.setSupplierName(supplierName);//供应商名称
            clearingDataModel.setCityCode(productOrder.getCityCode());//城市代码
            clearingDataModel.setCityName(productOrder.getCityName());//城市名称
            clearingDataModel.setDdpToCustomerRealFee(0);//DDP实际转给商户业务费用
            //根据订单表pay_way字段拿到支付网关类型 对应pay_config表bank_gateway_type
            PayWay payWay = null;
            if (MerTypeEnum.EXTERNAL.getCode().equals(productOrder.getMerUserType())) {
                payWay = clearingTaskMapper.getGateWayByExternal(productOrder.getPayWay());
            } else {
                payWay = clearingTaskMapper.getGateWayByGeneral(productOrder.getPayWay());
            }
            clearingDataModel.setPayGateway(payWay.getBankGatewayType());//支付网关
            clearingDataModel.setPayWayName(payWay.getPayWayName());//支付方式名称
            String customerCode = "";
            String tranType = "";
            if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                customerCode = productOrder.getUserCode();
            } else {
                customerCode = productOrder.getMerCode();
            }
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                tranType = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();
            } else {
                tranType = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            //计算商户应得分润
            long customerShouldProfit = 0;
            //如果费率类型是千分比
            if (RateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getMerRateType())) {
                String amount = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getMerRate() / 1000);
                if (amount.contains(".")) {
                    customerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                } else {
                    customerShouldProfit = Long.valueOf(amount);
                }
                //如果费率类型是单笔
            } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getMerRateType())) {
                customerShouldProfit = Math.round(productOrder.getMerRate());
            }
            clearingDataModel.setCustomerShouldProfit(customerShouldProfit);//商户应得分润
            //计算商户应收商户支付方式服务费
            long ddpGetMerchantPayFee = 0;
            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getPayServiceType())) {
                String amount = String.valueOf((double) (productOrder.getTxnAmt() - customerShouldProfit) * productOrder.getPayServiceRate() / 1000);
                if (amount.contains(".")) {
                    if (Double.valueOf(amount)%1>0) {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf("."))) + 1;
                    } else {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                    }
                } else {
                    ddpGetMerchantPayFee = Long.valueOf(amount);
                }
            } else if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getPayServiceType())) {
                ddpGetMerchantPayFee = Math.round(productOrder.getPayServiceRate());
            }
            clearingDataModel.setDdpGetMerchantPayFee(ddpGetMerchantPayFee);//DDP应收商户支付方式服务费
            //商户实际支付金额
            long customerRealPayAmount = productOrder.getTxnAmt() - customerShouldProfit + ddpGetMerchantPayFee;
            clearingDataModel.setCustomerRealPayAmount(customerRealPayAmount);//商户实际支付金额
            //根据客户号订单号和交易类型拿到支付费率，用来计算和银行之间的清分费用
            String payRate = clearingTaskMapper.getPayRate(customerCode, productOrder.getProOrderNum(), tranType);
            if (payRate == null) {
                throw new DDPException("10000");
            }
            clearingDataModel.setDdpBankRate(Double.valueOf(payRate));//DDP与银行的手续费率
            //DDP支付给银行的手续费
            String amountToBank = String.valueOf((double) customerRealPayAmount * Double.valueOf(payRate)/1000);
            long ddpToBankFee = 0;
            if (amountToBank.contains(".")) {
                if (Double.valueOf(amountToBank)%1>0) {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf("."))) + 1;
                } else {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf(".")));
                }
            } else {
                ddpToBankFee = Long.valueOf(amountToBank);
            }
            clearingDataModel.setDdpToBankFee(ddpToBankFee);//DDP支付给银行的手续费
            clearingDataModel.setDdpFromBankShouldFee(customerRealPayAmount);//DDP从银行应收业务费用
            clearingDataModel.setDdpFromBankRealFee(customerRealPayAmount - ddpToBankFee);//DDP从银行实收业务费用
            clearingDataModel.setDdpToSupplierShouldAmount(productOrder.getTxnAmt());//DDP应支付供应商本金
            clearingDataModel.setDdpSupplierRate(productOrder.getYktRechargeRate());//DDP与供应商之间的费率
            //供应商应支付都都宝反点 DDP应支付供应商本金 * DDP与供应商之间的费率
            long supplierToDdpShouldRebate = 0;
            String rebate = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getYktRechargeRate() / 1000);
            if (rebate.contains(".")) {
                supplierToDdpShouldRebate = Long.valueOf(rebate.substring(0, rebate.indexOf(".")));
            } else {
                supplierToDdpShouldRebate = Long.valueOf(rebate);
            }
            clearingDataModel.setSupplierToDdpShouldRebate(supplierToDdpShouldRebate);//供应商应支付DDP返点
            //此处商户实际分润应该等于商户应得分润 - 直接下级商户的应得分润
            clearingDataModel.setCustomerRealProfit(customerShouldProfit);//商户实际分润
            clearingDataModel.setDdpToSupplierRealAmount(productOrder.getTxnAmt() - supplierToDdpShouldRebate);//DDP实际支付供应商本金
            clearingDataModel.setSupplierToDdpRealRebate(supplierToDdpShouldRebate);//供应商实际支付DDP返点
            clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与供应商清分状态
            clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
            clearingDataModel.setSupplierClearingTime(getYestoday());//与供应商清分时间
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setCustomerSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与客户结算状态
            clearingDataModel.setCustomerClearingTime(getYestoday());//与客户清分时间
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与银行清分状态
            } else {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与银行清分状态
                clearingDataModel.setBankClearingTime(getYestoday());//与银行清分时间
            }
            clearingDataModel.setBankSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与银行结算状态
            if (MerTypeEnum.AGENT_MER.getCode().equals(productOrder.getMerUserType()) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(productOrder.getMerUserType()) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(productOrder.getMerUserType())) {
                clearingDataModel.setTopMerchantProfitFlag("1");//上级商户分润计算状态
            } else {
                clearingDataModel.setTopMerchantProfitFlag("0");//上级商户分润计算状态
            }
            clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分

        }
        //**订单状态是充值可疑或者充已付款 或者(订单状态是充值中 并且 (订单内部状态是申请充值密钥成功 或者 资金冻结成功))
        // 或者 (订单状态是充值失败 并且 (订单内部状态是 资金冻结失败 或者 申请充值密钥失败 或者 卡片充值失败))或者 资金解冻成功
        //或者资金解冻失败
        // 对应文档case 2 只与银行清**
        if(RechargeOrderStatesEnum.RECHARGE_SUSPICIOUS.getCode().equals(productOrder.getProOrderState())||
                (RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState())&& RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_SUCCESS.getCode().equals(productOrder.getProInnerState()))){
            clearingDataModel = new ClearingDataModel();
            clearingDataModel.setOrderNo(productOrder.getProOrderNum());//订单交易号
            clearingDataModel.setOrderDate(productOrder.getCreateDate());//订单时间
//        clearingDataModel.setOrderDay(productOrder.getProOrderDay());//订单日期
            clearingDataModel.setCustomerNo(productOrder.getMerCode());//商户编号
            if(MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())){
                clearingDataModel.setCustomerName(productOrder.getUserName());//商户名称
            }else{
                clearingDataModel.setCustomerName(productOrder.getMerName());//商户名称
            }
            clearingDataModel.setCustomerType(productOrder.getMerUserType());//商户类型
            clearingDataModel.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());//业务类型
            clearingDataModel.setCustomerRateType(productOrder.getMerRateType());//商户业务费率类型
            clearingDataModel.setCustomerRate(productOrder.getMerRate());//商户业务费率
            clearingDataModel.setOrderAmount(productOrder.getTxnAmt());//订单金额
            clearingDataModel.setOrderFrom(productOrder.getSource());//订单来源
            clearingDataModel.setOrderCircle(productOrder.getLoadFlag());//是否圈存订单
            clearingDataModel.setPayWay(productOrder.getPayWay());//支付方式
            clearingDataModel.setPayType(productOrder.getPayType());//支付类型
            clearingDataModel.setServiceRateType(productOrder.getPayServiceType());//服务费率类型
            clearingDataModel.setServiceRate(productOrder.getPayServiceRate());//服务费率
            clearingDataModel.setSupplierCode(productOrder.getYktCode());//供应商
            //根据一卡通code拿到一卡通的名称
            String supplierName = clearingTaskMapper.getSupplierName(productOrder.getYktCode());
            clearingDataModel.setSupplierName(supplierName);//供应商名称
            clearingDataModel.setCityCode(productOrder.getCityCode());//城市代码
            clearingDataModel.setCityName(productOrder.getCityName());//城市名称
            clearingDataModel.setDdpToCustomerRealFee(0);//DDP实际转给商户业务费用
            //根据订单表pay_way字段拿到支付网关类型 对应pay_config表bank_gateway_type
            PayWay payWay = null;
            if (MerTypeEnum.EXTERNAL.getCode().equals(productOrder.getMerUserType())) {
                payWay = clearingTaskMapper.getGateWayByExternal(productOrder.getPayWay());
            } else {
                payWay = clearingTaskMapper.getGateWayByGeneral(productOrder.getPayWay());
            }
            clearingDataModel.setPayGateway(payWay.getBankGatewayType());//支付网关
            clearingDataModel.setPayWayName(payWay.getPayWayName());//支付方式名称
            String customerCode = "";
            String tranType = "";
            if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                customerCode = productOrder.getUserCode();
            } else {
                customerCode = productOrder.getMerCode();
            }
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                tranType = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();
            } else {
                tranType = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            //计算商户应得分润
            long customerShouldProfit = 0;
            //如果费率类型是千分比
            if (RateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getMerRateType())) {
                String amount = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getMerRate() / 1000);
                if (amount.contains(".")) {
                    customerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                } else {
                    customerShouldProfit = Long.valueOf(amount);
                }
                //如果费率类型是单笔
            } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getMerRateType())) {
                customerShouldProfit = Math.round(productOrder.getMerRate());
            }
            clearingDataModel.setCustomerShouldProfit(customerShouldProfit);//商户应得分润
            //计算商户应收商户支付方式服务费
            long ddpGetMerchantPayFee = 0;
            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getPayServiceType())) {
                String amount = String.valueOf((double) (productOrder.getTxnAmt() - customerShouldProfit) * productOrder.getPayServiceRate() / 1000);
                if (amount.contains(".")) {
                    if (Double.valueOf(amount)%1>0) {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf("."))) + 1;
                    } else {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                    }
                } else {
                    ddpGetMerchantPayFee = Long.valueOf(amount);
                }
            } else if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getPayServiceType())) {
                ddpGetMerchantPayFee = Math.round(productOrder.getPayServiceRate());
            }
            clearingDataModel.setDdpGetMerchantPayFee(ddpGetMerchantPayFee);//DDP应收商户支付方式服务费
            //商户实际支付金额
            long customerRealPayAmount = productOrder.getTxnAmt() - customerShouldProfit + ddpGetMerchantPayFee;
            clearingDataModel.setCustomerRealPayAmount(customerRealPayAmount);//商户实际支付金额
            //根据客户号订单号和交易类型拿到支付费率，用来计算和银行之间的清分费用
            String payRate = clearingTaskMapper.getPayRate(customerCode, productOrder.getProOrderNum(), tranType);
            if (payRate == null) {
                throw new DDPException("10000");
            }
            clearingDataModel.setDdpBankRate(Double.valueOf(payRate));//DDP与银行的手续费率
            //DDP支付给银行的手续费
            String amountToBank = String.valueOf((double) customerRealPayAmount * Double.valueOf(payRate)/1000);
            long ddpToBankFee = 0;
            if (amountToBank.contains(".")) {
//                long tempAmount = Long.valueOf(amountToBank.substring(amountToBank.indexOf(".") + 1, amountToBank.indexOf(".") + 2));
                if (Double.valueOf(amountToBank)%1>0) {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf("."))) + 1;
                } else {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf(".")));
                }
            } else {
                ddpToBankFee = Long.valueOf(amountToBank);
            }
            clearingDataModel.setDdpToBankFee(ddpToBankFee);//DDP支付给银行的手续费
            clearingDataModel.setDdpFromBankShouldFee(customerRealPayAmount);//DDP从银行应收业务费用
            clearingDataModel.setDdpFromBankRealFee(customerRealPayAmount - ddpToBankFee);//DDP从银行实收业务费用
            clearingDataModel.setDdpToSupplierShouldAmount(productOrder.getTxnAmt());//DDP应支付供应商本金
            clearingDataModel.setDdpSupplierRate(productOrder.getYktRechargeRate());//DDP与供应商之间的费率
            //供应商应支付都都宝反点 DDP应支付供应商本金 * DDP与供应商之间的费率
            long supplierToDdpShouldRebate = 0;
            String rebate = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getYktRechargeRate() / 1000);
            if (rebate.contains(".")) {
                supplierToDdpShouldRebate = Long.valueOf(rebate.substring(0, rebate.indexOf(".")));
            } else {
                supplierToDdpShouldRebate = Long.valueOf(rebate);
            }
            clearingDataModel.setSupplierToDdpShouldRebate(supplierToDdpShouldRebate);//供应商应支付DDP返点
            clearingDataModel.setCustomerRealProfit(0);//商户实际分润
            clearingDataModel.setDdpToSupplierRealAmount(0);//DDP实际支付供应商本金
            clearingDataModel.setSupplierToDdpRealRebate(0);//供应商实际支付DDP返点
            clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode());//与供应商清分状态
            clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setCustomerSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与客户结算状态
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与银行清分状态
            } else {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与银行清分状态
                clearingDataModel.setBankClearingTime(getYestoday());//与银行清分时间
            }
            clearingDataModel.setBankSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与银行结算状态
            if (MerTypeEnum.AGENT_MER.getCode().equals(productOrder.getMerUserType()) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(productOrder.getMerUserType()) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(productOrder.getMerUserType())) {
                clearingDataModel.setTopMerchantProfitFlag("1");//上级商户分润计算状态
            } else {
                clearingDataModel.setTopMerchantProfitFlag("0");//上级商户分润计算状态
            }
            clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分
        }
        //**订单状态是充值成功 并且 (内部状态是卡片充值成功 或者 资金扣款失败) 对应文档 case 3
        // 只与银行和供应商进行清分**
        if (RechargeOrderStatesEnum.RECHARGE_SUCCESS.getCode().equals(productOrder.getProOrderState()) &&
                (RechargeOrderInternalStatesEnum.CARD_RECHARGE_SUCCESS.getCode().equals(productOrder.getProInnerState()) ||
                        RechargeOrderInternalStatesEnum.FUND_DEDUCT_FAILED.getCode().equals(productOrder.getProInnerState()))) {
            clearingDataModel = new ClearingDataModel();
            clearingDataModel.setOrderNo(productOrder.getProOrderNum());//订单交易号
            clearingDataModel.setOrderDate(productOrder.getCreateDate());//订单时间
//        clearingDataModel.setOrderDay(productOrder.getProOrderDay());//订单日期
            clearingDataModel.setCustomerNo(productOrder.getMerCode());//商户编号
            if(MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())){
                clearingDataModel.setCustomerName(productOrder.getUserName());//商户名称
            }else{
                clearingDataModel.setCustomerName(productOrder.getMerName());//商户名称
            }
            clearingDataModel.setCustomerType(productOrder.getMerUserType());//商户类型
            clearingDataModel.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());//业务类型
            clearingDataModel.setCustomerRateType(productOrder.getMerRateType());//商户业务费率类型
            clearingDataModel.setCustomerRate(productOrder.getMerRate());//商户业务费率
            clearingDataModel.setOrderAmount(productOrder.getTxnAmt());//订单金额
            clearingDataModel.setOrderFrom(productOrder.getSource());//订单来源
            clearingDataModel.setOrderCircle(productOrder.getLoadFlag());//是否圈存订单
            clearingDataModel.setPayWay(productOrder.getPayWay());//支付方式
            clearingDataModel.setPayType(productOrder.getPayType());//支付类型
            clearingDataModel.setServiceRateType(productOrder.getPayServiceType());//服务费率类型
            clearingDataModel.setServiceRate(productOrder.getPayServiceRate());//服务费率
            clearingDataModel.setSupplierCode(productOrder.getYktCode());//供应商
            //根据一卡通code拿到一卡通的名称
            String supplierName = clearingTaskMapper.getSupplierName(productOrder.getYktCode());
            clearingDataModel.setSupplierName(supplierName);//供应商名称
            clearingDataModel.setCityCode(productOrder.getCityCode());//城市代码
            clearingDataModel.setCityName(productOrder.getCityName());//城市名称
            clearingDataModel.setDdpToCustomerRealFee(0);//DDP实际转给商户业务费用
            //根据订单表pay_way字段拿到支付网关类型 对应pay_config表bank_gateway_type
            PayWay payWay =null;
            if (MerTypeEnum.EXTERNAL.getCode().equals(productOrder.getMerUserType())) {
                payWay = clearingTaskMapper.getGateWayByExternal(productOrder.getPayWay());
            } else {
                payWay = clearingTaskMapper.getGateWayByGeneral(productOrder.getPayWay());
            }
            clearingDataModel.setPayGateway(payWay.getBankGatewayType());//支付网关
            clearingDataModel.setPayWayName(payWay.getPayWayName());//支付方式名称
            String customerCode = "";
            String tranType = "";
            if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                customerCode = productOrder.getUserCode();
            } else {
                customerCode = productOrder.getMerCode();
            }
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                tranType = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();
            } else {
                tranType = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            //计算商户应得分润
            long customerShouldProfit = 0;
            //如果费率类型是千分比
            if (RateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getMerRateType())) {
                String amount = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getMerRate() / 1000);
                if (amount.contains(".")) {
                    customerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                } else {
                    customerShouldProfit = Long.valueOf(amount);
                }
                //如果费率类型是单笔
            } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getMerRateType())) {
                customerShouldProfit = Math.round(productOrder.getMerRate());
            }
            clearingDataModel.setCustomerShouldProfit(customerShouldProfit);//商户应得分润
            //计算商户应收商户支付方式服务费
            long ddpGetMerchantPayFee = 0;
            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getPayServiceType())) {
                String amount = String.valueOf((double) (productOrder.getTxnAmt() - customerShouldProfit) * productOrder.getPayServiceRate() / 1000);
                if (amount.contains(".")) {
//                    long tempAmount = Long.valueOf(amount.substring(amount.indexOf(".") + 1, amount.indexOf(".") + 2));
                    if (Double.valueOf(amount)%1>0) {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf("."))) + 1;
                    } else {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                    }
                } else {
                    ddpGetMerchantPayFee = Long.valueOf(amount);
                }
            } else if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getPayServiceType())) {
                ddpGetMerchantPayFee = Math.round(productOrder.getPayServiceRate());
            }
            clearingDataModel.setDdpGetMerchantPayFee(ddpGetMerchantPayFee);//DDP应收商户支付方式服务费
            //商户实际支付金额
            long customerRealPayAmount = productOrder.getTxnAmt() - customerShouldProfit + ddpGetMerchantPayFee;
            clearingDataModel.setCustomerRealPayAmount(customerRealPayAmount);//商户实际支付金额
            //根据客户号订单号和交易类型拿到支付费率，用来计算和银行之间的清分费用
            String payRate = clearingTaskMapper.getPayRate(customerCode, productOrder.getProOrderNum(), tranType);
            if (payRate == null) {
                throw new DDPException("10000");
            }
            clearingDataModel.setDdpBankRate(Double.valueOf(payRate));//DDP与银行的手续费率
            //DDP支付给银行的手续费
            String amountToBank = String.valueOf((double) customerRealPayAmount * Double.valueOf(payRate)/1000);
            long ddpToBankFee = 0;
            if (amountToBank.contains(".")) {
//                long tempAmount = Long.valueOf(amountToBank.substring(amountToBank.indexOf(".") + 1, amountToBank.indexOf(".") + 2));
                if (Double.valueOf(amountToBank)%1>0) {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf("."))) + 1;
                } else {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf(".")));
                }
            } else {
                ddpToBankFee = Long.valueOf(amountToBank);
            }
            clearingDataModel.setDdpToBankFee(ddpToBankFee);//DDP支付给银行的手续费
            clearingDataModel.setDdpFromBankShouldFee(customerRealPayAmount);//DDP从银行应收业务费用
            clearingDataModel.setDdpFromBankRealFee(customerRealPayAmount - ddpToBankFee);//DDP从银行实收业务费用
            clearingDataModel.setDdpToSupplierShouldAmount(productOrder.getTxnAmt());//DDP应支付供应商本金
            clearingDataModel.setDdpSupplierRate(productOrder.getYktRechargeRate());//DDP与供应商之间的费率
            //供应商应支付都都宝反点 DDP应支付供应商本金 * DDP与供应商之间的费率
            long supplierToDdpShouldRebate = 0;
            String rebate = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getYktRechargeRate() / 1000);
            if (rebate.contains(".")) {
                supplierToDdpShouldRebate = Long.valueOf(rebate.substring(0, rebate.indexOf(".")));
            } else {
                supplierToDdpShouldRebate = Long.valueOf(rebate);
            }
            clearingDataModel.setSupplierToDdpShouldRebate(supplierToDdpShouldRebate);//供应商应支付DDP返点
            clearingDataModel.setCustomerRealProfit(0);//商户实际分润
            clearingDataModel.setDdpToSupplierRealAmount(productOrder.getTxnAmt() - supplierToDdpShouldRebate);//DDP实际支付供应商本金
            clearingDataModel.setSupplierToDdpRealRebate(supplierToDdpShouldRebate);//供应商实际支付DDP返点
            clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与供应商清分状态
            clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
            clearingDataModel.setSupplierClearingTime(getYestoday());//与供应商清分时间
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setCustomerSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与客户结算状态
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与银行清分状态
            } else {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与银行清分状态
                clearingDataModel.setBankClearingTime(getYestoday());//与银行清分时间
            }
            clearingDataModel.setBankSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与银行结算状态
            if (MerTypeEnum.AGENT_MER.getCode().equals(productOrder.getMerUserType()) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(productOrder.getMerUserType()) || MerTypeEnum.CHAIN_STORE_MER.getCode().equals(productOrder.getMerUserType())) {
                clearingDataModel.setTopMerchantProfitFlag("1");//上级商户分润计算状态
            } else {
                clearingDataModel.setTopMerchantProfitFlag("0");//上级商户分润计算状态
            }
            clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分
        }
        //对应文档case 4 只于银行进行清分
        if(RechargeOrderStatesEnum.PAID.getCode().equals(productOrder.getProOrderState())||
                (RechargeOrderStatesEnum.RECHARGE.getCode().equals(productOrder.getProOrderState())&& RechargeOrderInternalStatesEnum.FUND_FROZEN_SUCCESS.getCode().equals(productOrder.getProInnerState()))||(RechargeOrderStatesEnum.RECHARGE_FAILURE.getCode().equals(productOrder.getProOrderState())
                &&(RechargeOrderInternalStatesEnum.FUND_FROZEN_FAILED.getCode().equals(productOrder.getProInnerState())|| RechargeOrderInternalStatesEnum.APPLY_RECHARGE_SECRET_KEY_FAILED.getCode().equals(productOrder.getProInnerState())
                || RechargeOrderInternalStatesEnum.CARD_RECHARGE_FAILED.getCode().equals(productOrder.getProInnerState())))|| RechargeOrderInternalStatesEnum.FUND_UNBOLCK_SUCCESS.getCode().equals(productOrder.getProInnerState())
                || RechargeOrderInternalStatesEnum.FUND_UNBOLCK_FAILED.getCode().equals(productOrder.getProInnerState())){
            clearingDataModel = new ClearingDataModel();
            clearingDataModel.setOrderNo(productOrder.getProOrderNum());//订单交易号
            clearingDataModel.setOrderDate(productOrder.getCreateDate());//订单时间
//        clearingDataModel.setOrderDay(productOrder.getProOrderDay());//订单日期
            clearingDataModel.setCustomerNo(productOrder.getMerCode());//商户编号
            if(MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())){
                clearingDataModel.setCustomerName(productOrder.getUserName());//商户名称
            }else{
                clearingDataModel.setCustomerName(productOrder.getMerName());//商户名称
            }
            clearingDataModel.setCustomerType(productOrder.getMerUserType());//商户类型
            clearingDataModel.setBusinessType(RateCodeEnum.YKT_RECHARGE.getCode());//业务类型
            clearingDataModel.setCustomerRateType(productOrder.getMerRateType());//商户业务费率类型
            clearingDataModel.setCustomerRate(productOrder.getMerRate());//商户业务费率
            clearingDataModel.setOrderAmount(productOrder.getTxnAmt());//订单金额
            clearingDataModel.setOrderFrom(productOrder.getSource());//订单来源
            clearingDataModel.setOrderCircle(productOrder.getLoadFlag());//是否圈存订单
            clearingDataModel.setPayWay(productOrder.getPayWay());//支付方式
            clearingDataModel.setPayType(productOrder.getPayType());//支付类型
            clearingDataModel.setServiceRateType(productOrder.getPayServiceType());//服务费率类型
            clearingDataModel.setServiceRate(productOrder.getPayServiceRate());//服务费率
            clearingDataModel.setSupplierCode(productOrder.getYktCode());//供应商
            //根据一卡通code拿到一卡通的名称
            String supplierName = clearingTaskMapper.getSupplierName(productOrder.getYktCode());
            clearingDataModel.setSupplierName(supplierName);//供应商名称
            clearingDataModel.setCityCode(productOrder.getCityCode());//城市代码
            clearingDataModel.setCityName(productOrder.getCityName());//城市名称
            clearingDataModel.setDdpToCustomerRealFee(0);//DDP实际转给商户业务费用
            //根据订单表pay_way字段拿到支付网关类型 对应pay_config表bank_gateway_type
            PayWay payWay = null;
            if (MerTypeEnum.EXTERNAL.getCode().equals(productOrder.getMerUserType())) {
                payWay = clearingTaskMapper.getGateWayByExternal(productOrder.getPayWay());
            } else {
                payWay = clearingTaskMapper.getGateWayByGeneral(productOrder.getPayWay());
            }
            clearingDataModel.setPayGateway(payWay.getBankGatewayType());//支付网关
            clearingDataModel.setPayWayName(payWay.getPayWayName());//支付方式名称
            String customerCode = "";
            String tranType = "";
            if (MerTypeEnum.PERSONAL.getCode().equals(productOrder.getMerUserType())) {
                customerCode = productOrder.getUserCode();
            } else {
                customerCode = productOrder.getMerCode();
            }
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                tranType = TranTypeEnum.ACCOUNT_CONSUMPTION.getCode();
            } else {
                tranType = TranTypeEnum.ACCOUNT_RECHARGE.getCode();
            }
            //计算商户应得分润
            long customerShouldProfit = 0;
            //如果费率类型是千分比
            if (RateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getMerRateType())) {
                String amount = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getMerRate() / 1000);
                if (amount.contains(".")) {
                    customerShouldProfit = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                } else {
                    customerShouldProfit = Long.valueOf(amount);
                }
                //如果费率类型是单笔
            } else if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getMerRateType())) {
                customerShouldProfit = Math.round(productOrder.getMerRate());
            }
            clearingDataModel.setCustomerShouldProfit(customerShouldProfit);//商户应得分润
            //计算商户应收商户支付方式服务费
            long ddpGetMerchantPayFee = 0;
            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(productOrder.getPayServiceType())) {
                String amount = String.valueOf((double) (productOrder.getTxnAmt() - customerShouldProfit) * productOrder.getPayServiceRate() / 1000);
                if (amount.contains(".")) {
                    if (Double.valueOf(amount)%1>0) {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf("."))) + 1;
                    } else {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                    }
                } else {
                    ddpGetMerchantPayFee = Long.valueOf(amount);
                }
            } else if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(productOrder.getPayServiceType())) {
                ddpGetMerchantPayFee = Math.round(productOrder.getPayServiceRate());
            }
            clearingDataModel.setDdpGetMerchantPayFee(ddpGetMerchantPayFee);//DDP应收商户支付方式服务费
            //商户实际支付金额
            long customerRealPayAmount = productOrder.getTxnAmt() - customerShouldProfit + ddpGetMerchantPayFee;
            clearingDataModel.setCustomerRealPayAmount(customerRealPayAmount);//商户实际支付金额
            //根据客户号订单号和交易类型拿到支付费率，用来计算和银行之间的清分费用
            String payRate = clearingTaskMapper.getPayRate(customerCode, productOrder.getProOrderNum(), tranType);
            if (payRate == null) {
                throw new DDPException("10000");
            }
            clearingDataModel.setDdpBankRate(Double.valueOf(payRate));//DDP与银行的手续费率
            //DDP支付给银行的手续费
            String amountToBank = String.valueOf((double) customerRealPayAmount * Double.valueOf(payRate)/1000);
            long ddpToBankFee = 0;
            if (amountToBank.contains(".")) {
//                long tempAmount = Long.valueOf(amountToBank.substring(amountToBank.indexOf(".") + 1, amountToBank.indexOf(".") + 2));
                if (Double.valueOf(amountToBank)%1>0) {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf("."))) + 1;
                } else {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf(".")));
                }
            } else {
                ddpToBankFee = Long.valueOf(amountToBank);
            }
            clearingDataModel.setDdpToBankFee(ddpToBankFee);//DDP支付给银行的手续费
            clearingDataModel.setDdpFromBankShouldFee(customerRealPayAmount);//DDP从银行应收业务费用
            clearingDataModel.setDdpFromBankRealFee(customerRealPayAmount - ddpToBankFee);//DDP从银行实收业务费用
            clearingDataModel.setDdpToSupplierShouldAmount(productOrder.getTxnAmt());//DDP应支付供应商本金
            clearingDataModel.setDdpSupplierRate(productOrder.getYktRechargeRate());//DDP与供应商之间的费率
            //供应商应支付都都宝反点 DDP应支付供应商本金 * DDP与供应商之间的费率
            long supplierToDdpShouldRebate = 0;
            String rebate = String.valueOf((double) productOrder.getTxnAmt() * productOrder.getYktRechargeRate() / 1000);
            if (rebate.contains(".")) {
                supplierToDdpShouldRebate = Long.valueOf(rebate.substring(0, rebate.indexOf(".")));
            } else {
                supplierToDdpShouldRebate = Long.valueOf(rebate);
            }
            clearingDataModel.setSupplierToDdpShouldRebate(supplierToDdpShouldRebate);//供应商应支付DDP返点
            clearingDataModel.setCustomerRealProfit(0);//商户实际分润
            clearingDataModel.setDdpToSupplierRealAmount(0);//DDP实际支付供应商本金
            clearingDataModel.setSupplierToDdpRealRebate(0);//供应商实际支付DDP返点
            clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与供应商清分状态
            clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setCustomerSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与客户结算状态
            if (PayTypeEnum.PAY_ACT.getCode().equals(productOrder.getPayType())) {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与银行清分状态
            } else {
                clearingDataModel.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与银行清分状态
                clearingDataModel.setBankClearingTime(getYestoday());//与银行清分时间
            }
            clearingDataModel.setBankSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与银行结算状态
            clearingDataModel.setTopMerchantProfitFlag("0");//上级商户分润计算状态

            clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分
        }
//        --clearingDataModel.setCustomerAccountShouldAmount();//商户账户应加值
//        --clearingDataModel.setCustomerAccountRealAmount();//商户账户实际加值
//        --clearingDataModel.setSubMerchantCode();//下级商户编号
//        --clearingDataModel.setSubMerchantName();//下级商户名称
//        --clearingDataModel.setSubMerchantShouldProfit();//下级商户应得分润
//        --clearingDataModel.setBankSettlementFlag();//与银行结算状态
//        --clearingDataModel.setBankSettlementTime();//与银行结算时间
//        --clearingDataModel.setSupplierSettlementFlag();//与供应商结算状态
//        --clearingDataModel.setSupplierSettlementTime();//与供应商结算时间
//        --clearingDataModel.setCustomerSettlementFlag();//与客户结算状态
//        --clearingDataModel.setCustomerSettlementTime();//与客户结算时间
        return clearingDataModel;
    }

    @Transactional
    public ClearingDataModel getAccountRechageClearingDate(PayTransaction paytransaction) {
        ClearingDataModel clearingDataModel = new ClearingDataModel();
        clearingDataModel.setOrderNo(paytransaction.getTranCode());//订单交易号
        clearingDataModel.setOrderDate(paytransaction.getCreateDate());//订单时间
        clearingDataModel.setCustomerNo(paytransaction.getMerOrUserCode());//商户编号
        clearingDataModel.setCustomerName(paytransaction.getMerUserName());//商户名称
        clearingDataModel.setCustomerType(paytransaction.getMerUserType());//商户类型
        clearingDataModel.setBusinessType(RateCodeEnum.ACCT_RECHARGE.getCode());//业务类型
        clearingDataModel.setOrderAmount(paytransaction.getAmountMoney());//订单金额
        clearingDataModel.setCustomerRealPayAmount(paytransaction.getRealTranMoney());//商户实际交易金额
        clearingDataModel.setDdpToCustomerRealFee(0);//DDP实际转给商户业务费用
        clearingDataModel.setCustomerAccountShouldAmount(paytransaction.getAmountMoney()); //账户应加值
        clearingDataModel.setOrderFrom(paytransaction.getSource());//订单来源
        if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(paytransaction.getTranInStatus())) {
            clearingDataModel.setCustomerAccountRealAmount(paytransaction.getAmountMoney()); //账户实际加值
        }
        if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(paytransaction.getTranInStatus())) {
            clearingDataModel.setCustomerAccountRealAmount(0); //账户实际加值
        }

        clearingDataModel.setDdpGetMerchantPayFee(paytransaction.getPayServiceFee()); //DDP应收商户支付方式服务费
        clearingDataModel.setPayGateway(paytransaction.getPayGateway()); // 支付网关
        clearingDataModel.setPayType(paytransaction.getPayType()); //支付类型
        clearingDataModel.setPayWay(paytransaction.getPayWay()); //支付方式
        clearingDataModel.setServiceRateType(paytransaction.getPayServiceType()); //服务费率类型
        clearingDataModel.setServiceRate(paytransaction.getPayServiceRate()); //服务费率
        clearingDataModel.setDdpBankRate(paytransaction.getPayProceRate()); //DDP与银行的手续费率
        clearingDataModel.setDdpToBankFee(paytransaction.getPayProceFee()); //DDP支付给银行的手续费
        clearingDataModel.setDdpFromBankShouldFee(paytransaction.getRealTranMoney()); //DDP从银行应收业务费用
        clearingDataModel.setDdpFromBankRealFee(paytransaction.getRealTranMoney() - paytransaction.getPayProceFee()); //DDP从银行实收业务费用=DDP从银行应收业务费用 - DDP支付给银行的手续费
        clearingDataModel.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode()); //与银行清分状态
        clearingDataModel.setBankSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与银行结算状态
        clearingDataModel.setBankClearingTime(getYestoday()); //银行清分日期
        clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode()); //与供应商清分状态
        clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
        if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_SUCCESS.getCode().equals(paytransaction.getTranInStatus())) {
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setCustomerClearingTime(getYestoday());//与客户清分日期
        }
        if (TranInStatusEnum.ACCOUNT_VALUE_ADDED_FAIL.getCode().equals(paytransaction.getTranInStatus())) {
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode());//与客户清分状态
        }
        clearingDataModel.setCustomerSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与客户结算状态
        clearingDataModel.setTopMerchantProfitFlag("0"); //
        clearingDataModel.setPayWayName(paytransaction.getPayWayName());
        clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分
        return clearingDataModel;
    }

    @Transactional
    public ClearingDataModel getYKTPurchaseClearingDate(Purchase purchase) {
        ClearingDataModel clearingDataModel=null;
        // 内部状态 扣款成功
        if (PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUCCESS.getCode().equals(purchase.getInnerStates())) {
            clearingDataModel = new ClearingDataModel();
            clearingDataModel.setOrderNo(purchase.getOrderNum());//订单号
            clearingDataModel.setOrderDate(purchase.getCreateDate()); //订单时间
            clearingDataModel.setCustomerNo(purchase.getCustomerCode()); //商户编号
            clearingDataModel.setCustomerName(purchase.getCustomerName()); //商户名称
            clearingDataModel.setCustomerType(purchase.getCustomerType()); //商户类型
            clearingDataModel.setBusinessType(RateCodeEnum.YKT_PAYMENT.getCode()); //业务类型
            clearingDataModel.setCustomerRateType(purchase.getMerRateType()); //商户业务费率类型
            clearingDataModel.setCustomerRate(purchase.getMerRate()); //商户业务费率
            clearingDataModel.setOrderAmount(purchase.getReceivedPrice()); //订单金额
            clearingDataModel.setOrderFrom(purchase.getSource());//订单来源
            //计算ddp应收商户支付方式服务费
            long ddpGetMerchantPayFee = 0;
            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(purchase.getServiceRateType())) {
                String amount = String.valueOf((double) purchase.getReceivedPrice() * purchase.getServiceRate() / 1000);
                if (amount.contains(".")) {
//                    long tempAmount = Long.valueOf(amount.substring(amount.indexOf(".") + 1, amount.indexOf(".") + 2));
                    if (Double.valueOf(amount)%1>0) {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf("."))) + 1;
                    } else {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                    }
                } else {
                    ddpGetMerchantPayFee = Long.valueOf(amount);
                }
            } else if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(purchase.getServiceRateType())) {
                ddpGetMerchantPayFee = Math.round(purchase.getServiceRate());
            }
            clearingDataModel.setDdpGetMerchantPayFee(ddpGetMerchantPayFee);//ddp应收商户支付方式服务费
            clearingDataModel.setPayType(purchase.getPayType());//支付类型
            clearingDataModel.setPayWay(purchase.getPayWay());//支付方式
            clearingDataModel.setServiceRateType(purchase.getServiceRateType());//服务费率类型
            clearingDataModel.setServiceRate(purchase.getServiceRate());//服务费率
            clearingDataModel.setDdpBankRate(purchase.getYktPayRate());//ddp 与银行的手续费率
            clearingDataModel.setDdpFromBankShouldFee(purchase.getReceivedPrice()); //DDP从银行应收业务费用
            clearingDataModel.setSupplierCode(purchase.getYktCode());//供应商code
            clearingDataModel.setSupplierName(purchase.getYktName());//供应商名称
            clearingDataModel.setCityCode(purchase.getCityCode());//城市code
            clearingDataModel.setCityName(purchase.getCityName());//城市name
            clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与供应商清分状态
            clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
            clearingDataModel.setTopMerchantProfitFlag("0");//上级商户分润计算状态
            clearingDataModel.setDdpToCustomerRealFee(purchase.getReceivedPrice() - ddpGetMerchantPayFee); //DDP实际转给商户业务费用
            clearingDataModel.setPayGateway(purchase.getYktCode());//支付网关
            //DDP支付给银行的手续费
            String amountToBank = String.valueOf((double) purchase.getReceivedPrice() * purchase.getYktPayRate()/1000);
            long ddpToBankFee = 0;
            if (amountToBank.contains(".")) {
//                long tempAmount = Long.valueOf(amountToBank.substring(amountToBank.indexOf(".") + 1, amountToBank.indexOf(".") + 2));
                if (Double.valueOf(amountToBank)%1>0) {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf("."))) + 1;
                } else {
                    ddpToBankFee = Long.valueOf(amountToBank.substring(0, amountToBank.indexOf(".")));
                }
            } else {
                ddpToBankFee = Long.valueOf(amountToBank);
            }
            clearingDataModel.setDdpToBankFee(ddpToBankFee);//DDP支付给银行的手续费率
            clearingDataModel.setDdpFromBankRealFee(purchase.getReceivedPrice() - ddpToBankFee);//DDP从银行实收业务费用
            clearingDataModel.setBankClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode()); //与银行清分状态
            clearingDataModel.setBankSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与银行的结算状态
            clearingDataModel.setBankClearingTime(getYestoday()); //与银行清分日期
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.ALREADY_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setCustomerSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与客户结算状态
            clearingDataModel.setCustomerClearingTime(getYestoday());//与客户清分日期
            clearingDataModel.setPayWayName(purchase.getCityName());//支付方式名称
            clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分
        }
        //内部状态 申请消费密钥成功 或者 扣款未知
        if (PurchaseOrderRedordStatesEnum.KEY_APPLY_SUCCESS.getCode().equals(purchase.getInnerStates()) || PurchaseOrderRedordStatesEnum.DEDUCT_MONEY_SUSPICIOUS.getCode().equals(purchase.getInnerStates())) {
            clearingDataModel = new ClearingDataModel();
            clearingDataModel.setOrderNo(purchase.getOrderNum());//订单号
            clearingDataModel.setOrderDate(purchase.getCreateDate()); //订单时间
            clearingDataModel.setCustomerNo(purchase.getCustomerCode()); //商户编号
            clearingDataModel.setCustomerName(purchase.getCustomerName()); //商户名称
            clearingDataModel.setCustomerType(purchase.getCustomerType()); //商户类型
            clearingDataModel.setBusinessType(RateCodeEnum.YKT_PAYMENT.getCode()); //业务类型
            clearingDataModel.setCustomerRateType(purchase.getMerRateType()); //商户业务费率类型
            clearingDataModel.setCustomerRate(purchase.getMerRate()); //商户业务费率
            clearingDataModel.setOrderAmount(purchase.getReceivedPrice()); //订单金额
            clearingDataModel.setOrderFrom(purchase.getSource());//订单来源
            //计算ddp应收商户支付方式服务费
            long ddpGetMerchantPayFee = 0;
            if (ServiceRateTypeEnum.PERMILLAGE.getCode().equals(purchase.getServiceRateType())) {
                String amount = String.valueOf((double) purchase.getReceivedPrice() * purchase.getServiceRate() / 1000);
                if (amount.contains(".")) {
//                    long tempAmount = Long.valueOf(amount.substring(amount.indexOf(".") + 1, amount.indexOf(".") + 2));
                    if (Double.valueOf(amount)%1>0) {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf("."))) + 1;
                    } else {
                        ddpGetMerchantPayFee = Long.valueOf(amount.substring(0, amount.indexOf(".")));
                    }
                } else {
                    ddpGetMerchantPayFee = Long.valueOf(amount);
                }
            } else if (ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(purchase.getServiceRateType())) {
                ddpGetMerchantPayFee = Math.round(purchase.getServiceRate());
            }
            clearingDataModel.setDdpGetMerchantPayFee(ddpGetMerchantPayFee);//ddp应收商户支付方式服务费
            clearingDataModel.setPayType(purchase.getPayType());//支付类型
            clearingDataModel.setPayWay(purchase.getPayWay());//支付方式
            clearingDataModel.setServiceRateType(purchase.getServiceRateType());//服务费率类型
            clearingDataModel.setServiceRate(purchase.getServiceRate());//服务费率
            clearingDataModel.setDdpBankRate(purchase.getYktPayRate());//ddp 与银行的手续费率
            clearingDataModel.setDdpFromBankShouldFee(purchase.getReceivedPrice()); //DDP从银行应收业务费用
            clearingDataModel.setSupplierCode(purchase.getYktCode());//供应商code
            clearingDataModel.setSupplierName(purchase.getYktName());//供应商名称
            clearingDataModel.setCityCode(purchase.getCityCode());//城市code
            clearingDataModel.setCityName(purchase.getCityName());//城市name
            clearingDataModel.setSupplierClearingFlag(ClearingFlagEnum.NO_CLEARING.getCode());//与供应商清分状态
            clearingDataModel.setSupplierSettlementFlag(SettlementFlagEnum.NOT_SETTLEMENT.getCode());//与供应商结算状态
            clearingDataModel.setTopMerchantProfitFlag("0");//上级商户分润计算状态
            clearingDataModel.setDdpToCustomerRealFee(0); //DDP实际转给商户业务费用
            clearingDataModel.setDdpToBankFee(0);//DDP支付给银行的手续费率
            clearingDataModel.setDdpFromBankRealFee(0);//DDP从银行实收业务费用
            clearingDataModel.setBankClearingFlag(ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode()); //与银行清分状态
            clearingDataModel.setCustomerClearingFlag(ClearingFlagEnum.YES_BUT_NOT_CLEARING.getCode());//与客户清分状态
            clearingDataModel.setPayWayName(purchase.getCityName());//支付方式名称
            clearingDataModel.setPayGateway(purchase.getYktCode());//支付网关
            clearingDataModel.setDataFlag("0");//分润标识，标识数据来源清分
        }
        return clearingDataModel;
    }
}
