package com.dodopal.account.business.facadeImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.util.DodopalAppVarPropsUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.account.business.constant.AccountConstants;
import com.dodopal.account.business.model.Account;
import com.dodopal.account.business.model.AccountControl;
import com.dodopal.account.business.model.AccountControllerDefault;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountAdjustmentService;
import com.dodopal.account.business.service.AccountFundService;
import com.dodopal.account.business.service.AccountService;
import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.api.account.dto.AccountTransferDTO;
import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPLog;
import com.dodopal.common.util.DDPStringUtil;

@Service("accountManagementFacade")
public class AccountManagementFacadeImpl implements AccountManagementFacade {
    //logger 日志
    private static Logger log = Logger.getLogger(AccountManagementFacadeImpl.class);
    private DDPLog<AccountManagementFacadeImpl> ddpLog = new DDPLog<>(AccountManagementFacadeImpl.class);
    @Autowired
    AccountService accountService;
    @Autowired
    private AccountFundService accountFundService;
    @Autowired
    AccountAdjustmentService accountAdjustmentService;

    /**
     * @param aType   客户类型
     * @param custNum 客户号
     * @param accType 账户类型
     * @return
     * @description 创建账户
     */
    @Override
    public DodopalResponse<String> createAccount(String aType, String custNum, String accType, String merType,String operateId) {
        log.info("创建账户方法开始=============================");
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        SysLog logs = new SysLog();
        logs.setServerName(CommonConstants.SYSTEM_NAME_ACCOUNT);
        logs.setInParas("客户类型：" + aType + "；客户号:" + custNum);
        try {
            //1 参数验证
            //客户类型
            if (!DDPStringUtil.existingWithLength(aType, 1) ||
                    null == MerUserTypeEnum.getMerUserUserTypeByCode(aType)) {
                dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
                return dodopalResponse;
            }
            //客户号
            if (!DDPStringUtil.existingWithLength(custNum, 40)) {
                dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
                return dodopalResponse;
            }
            //资金类型
            if (!DDPStringUtil.existingWithLength(accType, 1) ||
                    null == FundTypeEnum.getFundTypeByCode(aType)) {
                dodopalResponse.setCode(ResponseCode.ACC_BALANCE_TYPE_ERROR);
                return dodopalResponse;
            }
            if (!MerTypeEnum.contains(merType)) {
                //商户号不合法
                dodopalResponse.setCode(ResponseCode.ACC_MERCODE_ERROR);
                return dodopalResponse;
            }
            //2、组装数据
            //资金授信账户
            String seqId = accountService.getSequenceNextId();
            String preId = AccountConstants.ACC_A + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;
            //2.1主账户
            Account account = new Account();
            account.setAccountCode(preId);
            account.setFundType(accType);
            account.setCustomerNo(custNum);
            account.setCustomerType(aType);
            account.setCreateUser(operateId);
            AccountFund fundAccount = new AccountFund();
            //2.2资金账户号 = "F" + 资金类别 + 主账户号
            String fundAccountId = AccountConstants.ACC_F + FundTypeEnum.FUND.getCode() + preId;
            fundAccount.setFundAccountCode(fundAccountId);
            fundAccount.setAccountCode(preId);
            fundAccount.setFundType(FundTypeEnum.FUND.getCode());
            fundAccount.setSumTotalAmount(0);
            fundAccount.setTotalBalance(0);
            fundAccount.setAvailableBalance(0);
            fundAccount.setFrozenAmount(0);
            fundAccount.setLastChangeAmount(0);
            fundAccount.setBeforeChangeTotalAmount(0);
            fundAccount.setBeforeChangeAvailableAmount(0);
            fundAccount.setBeforeChangeFrozenAmount(0);
            fundAccount.setState(AccStatusEnum.ENABLE.getCode());
            fundAccount.setCreateUser(operateId);
            //2.3授信账户
            AccountFund fundAccount1 = null;
            AccountControl accountControl1 = null;
            if (FundTypeEnum.AUTHORIZED.getCode().equals(accType)) {
                fundAccount1 = new AccountFund();
                //授信账户号 = "F" + 资金类别 + 主账户号
                fundAccount1.setFundAccountCode(AccountConstants.ACC_F + FundTypeEnum.AUTHORIZED.getCode() + preId);
                fundAccount1.setAccountCode(preId);
                fundAccount1.setFundType(accType);
                fundAccount1.setSumTotalAmount(0);
                fundAccount1.setTotalBalance(0);
                fundAccount1.setAvailableBalance(0);
                fundAccount1.setFrozenAmount(0);
                fundAccount1.setLastChangeAmount(0);
                fundAccount1.setBeforeChangeTotalAmount(0);
                fundAccount1.setBeforeChangeAvailableAmount(0);
                fundAccount1.setBeforeChangeFrozenAmount(0);
                fundAccount1.setState(AccStatusEnum.ENABLE.getCode());
                fundAccount1.setCreateUser(operateId);
                AccountControllerDefault accountControllerDefault = accountService.queryControlDefault(merType);
                long creditAmt = accountControllerDefault.getCreditAmt();
                fundAccount1.setCreditAmt(creditAmt);
            }
            //3 风控数据组装
            //根据merType查询到风控默认所需要设置的数据
            AccountControllerDefault accountControllerDefault = accountService.queryControlDefault(merType);
            //3.1 资金风控
            AccountControl accountControl = new AccountControl();
            accountControl.setFundAccountCode(fundAccount.getFundAccountCode());
            accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
            accountControl.setCreateUser(operateId);
            //3.2 授信风控
            if (FundTypeEnum.AUTHORIZED.getCode().equals(accType)) {
                accountControl1 = new AccountControl();
                accountControl1.setFundAccountCode(fundAccount1.getFundAccountCode());
                accountControl1.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
                accountControl1.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
                accountControl1.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
                accountControl1.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
                accountControl1.setDayTransferMax(accountControllerDefault.getDayTransferMax());
                accountControl1.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
                accountControl1.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
                accountControl1.setCreateUser(operateId);
            }
            accountService.createAccount(account, fundAccount, accountControl, accountControl1, fundAccount1, accType);
        } catch (SQLException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.DATABASE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            logs.setRespCode(ResponseCode.DATABASE_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.ACC_ADD_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            logs.setRespCode(ResponseCode.ACC_ADD_ERROR);
        } finally {
            /*SysLog syslog = new SysLog();
            syslog.setInParas(JSONObject.toJSONString(aType) + "|" + JSONObject.toJSONString(accType) + "|" + JSONObject.toJSONString(custNum));
            syslog.setTradeStart(System.currentTimeMillis());
            syslog.setRespCode(dodopalResponse.getCode());
            syslog.setRespExplain("创建账户接口");
            syslog.setDescription("创建账户接口");
            syslog.setOrderNum("");
            syslog.setClassName(this.getClass().getName());
            syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            syslog.setOutParas(JSONObject.toJSONString(dodopalResponse));
            ddpLog.info(syslog);*/
            ActivemqLogPublisher.publishLog2Queue(logs, DodopalAppVarPropsUtil.getStringProp("server.log.name"), DodopalAppVarPropsUtil.getStringProp("server.log.url"));
        }
        log.info("创建账户方法结束=============================" + dodopalResponse.getCode());
        return dodopalResponse;
    }

    /**
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     * @description 账户充值dodopalResponse
     */
    @Override
    public DodopalResponse<String> accountRecharge(String custType, String custNum, String tradeNum, long amount,String operateId) {
        log.info("账户充值方法开始=============================");
        SysLog logs = new SysLog();
        logs.setServerName(CommonConstants.SYSTEM_NAME_ACCOUNT);
        logs.setInParas("客户类型：" + custType + "；客户号:" + custNum);
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        //1 数据验证
        //客户类型验证
        if (!DDPStringUtil.existingWithLength(custType, 1) ||
                null == MerUserTypeEnum.getMerUserUserTypeByCode(custType)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
            return dodopalResponse;
        }
        //客户号验证
        if (!DDPStringUtil.existingWithLength(custNum, 40)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
            return dodopalResponse;
        }
        //交易流水号验证
        if (!DDPStringUtil.existingWithLength(tradeNum, 40)) {
            //交易流水不合法
            dodopalResponse.setCode(ResponseCode.ACC_TRAN_NUM_ERROR);
            return dodopalResponse;
        }
        //金额验证
        if (amount <= 0) {
            //金额不合法
            dodopalResponse.setCode(ResponseCode.ACC_AMOUNT_ERROR);
            return dodopalResponse;
        }
        AccountControl control = accountService.queryAccountControl(custType, custNum, amount);
        try {
            //账户充值 资金变动表新增记录，资金授信表和资金变动累计表更改记录
            accountService.accountRecharge(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.DATABASE_ERROR);
            logs.setRespCode(ResponseCode.DATABASE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } catch (DDPException e) {
            e.printStackTrace();
            //捕获service方法中throw的异常code
            dodopalResponse.setCode(e.getCode());
            logs.setRespCode(e.getCode());
            logs.setStatckTrace(e.getStackTrace().toString());
            if(ResponseCode.ACC_RECHARGE_DAY_ONE_ERROR.equals(e.getCode())){
            	dodopalResponse.setMessage("超过日充值交易单笔限额(元):"+new DecimalFormat().format((double)control.getDayRechargeSingleLimit()/100)+"");
            }else if(ResponseCode.ACC_RECHARGE_DAY_SUM_ERROR.equals(e.getCode())){
            	dodopalResponse.setMessage("超过日充值交易累计限额(元):"+new DecimalFormat().format((double)control.getDayRechargeSumLimit()/100)+"");
            }else{
            	dodopalResponse.setMessage(e.getMessage());
            }
            return dodopalResponse;
        } catch (Exception e) {
            e.printStackTrace();
            //账户充值失败
            e.getMessage();
            dodopalResponse.setCode(ResponseCode.ACC_RECHARGE_ERROR);
            logs.setRespCode(ResponseCode.ACC_RECHARGE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } finally {
            /*syslog.setInParas(JSONObject.toJSONString(custType) + "|" + JSONObject.toJSONString(custNum) + "|" + JSONObject.toJSONString(tradeNum) + "|" + JSONObject.toJSONString(amount));
            syslog.setTradeStart(System.currentTimeMillis());
            syslog.setRespCode(dodopalResponse.getCode());
            syslog.setRespExplain("账户充值");
            syslog.setDescription("账户充值");
            syslog.setOrderNum(tradeNum);
            syslog.setClassName(this.getClass().getName());
            syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            syslog.setOutParas(JSONObject.toJSONString(dodopalResponse));
            ddpLog.info(syslog);*/
            ActivemqLogPublisher.publishLog2Queue(logs, DodopalAppVarPropsUtil.getStringProp("server.log.name"), DodopalAppVarPropsUtil.getStringProp("server.log.url"));
        }
        log.info("账户充值方法结束=============================" + dodopalResponse.getCode());
        return dodopalResponse;
    }

    /**
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     * @description 账户冻结接口实现
     */
    public DodopalResponse<String> accountFreeze(String custType, String custNum, String tradeNum, long amount,String operateId) {
        log.info("账户冻结方法开始=============================");
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        SysLog logs = new SysLog();
        logs.setServerName(CommonConstants.SYSTEM_NAME_ACCOUNT);
        logs.setInParas("客户类型：" + custType + "；客户号:" + custNum);
        //1 数据验证
        //客户类型验证
        if (!DDPStringUtil.existingWithLength(custType, 1) ||
                null == MerUserTypeEnum.getMerUserUserTypeByCode(custType)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
            return dodopalResponse;
        }
        //客户号验证
        if (!DDPStringUtil.existingWithLength(custNum, 40)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
            return dodopalResponse;
        }
        //交易流水号验证
        if (!DDPStringUtil.existingWithLength(tradeNum, 40)) {
            //交易流水不合法
            dodopalResponse.setCode(ResponseCode.ACC_TRAN_NUM_ERROR);
            return dodopalResponse;
        }
        //金额验证
        if (amount <= 0) {
            //金额不合法
            dodopalResponse.setCode(ResponseCode.ACC_AMOUNT_ERROR);
            return dodopalResponse;
        }
        AccountControl control = accountService.queryAccountControl(custType, custNum, amount);
        try {
            //账户充值 资金变动表新增记录，资金授信表和资金变动累计表更改记录
            accountService.accountFreeze(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.DATABASE_ERROR);
            logs.setRespCode(ResponseCode.DATABASE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } catch (DDPException e) {
            e.printStackTrace();
            //捕获service方法中throw的异常code
            dodopalResponse.setCode(e.getCode());
            if(ResponseCode.ACC_CONSUM_DAY_ONE_ERROR.equals(e.getCode())){
            	dodopalResponse.setMessage("超过日消费交易单笔限额(元):"+new DecimalFormat().format((double)control.getDayConsumeSingleLimit()/100)+"");
            }else if(ResponseCode.ACC_CONSUM_DAY_SUM_ERROR.equals(e.getCode())){
            	dodopalResponse.setMessage("超过日消费交易累计限额(元):"+new DecimalFormat().format((double)control.getDayConsumeSumLimit()/100)+"");
            }else{
            	dodopalResponse.setMessage(e.getMessage());
            }
            return dodopalResponse;
        } catch (Exception e) {
            e.printStackTrace();
            //账户冻结失败
            dodopalResponse.setCode(ResponseCode.ACC_FREEZE_ERROR);
            return dodopalResponse;
        } finally {
            /*SysLog syslog = new SysLog();
            syslog.setInParas(JSONObject.toJSONString(custType) + "|" + JSONObject.toJSONString(custNum) + "|" + JSONObject.toJSONString(tradeNum) + "|" + JSONObject.toJSONString(amount));
            syslog.setTradeStart(System.currentTimeMillis());
            syslog.setRespCode(dodopalResponse.getCode());
            syslog.setRespExplain("账户冻结");
            syslog.setDescription("账户冻结");
            syslog.setOrderNum(tradeNum);
            syslog.setClassName(this.getClass().getName());
            syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            syslog.setOutParas(JSONObject.toJSONString(dodopalResponse));
            ddpLog.info(syslog);*/
            ActivemqLogPublisher.publishLog2Queue(logs, DodopalAppVarPropsUtil.getStringProp("server.log.name"), DodopalAppVarPropsUtil.getStringProp("server.log.url"));
        }
        log.info("账户冻结方法结束=============================" + dodopalResponse.getCode());
        return dodopalResponse;
    }

    /**
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     * @description 账户解冻接口实现
     */
    @Override
    public DodopalResponse<String> accountUnfreeze(String custType, String custNum, String tradeNum, long amount,String operateId) {
        log.info("账户解冻方法开始=============================");
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        SysLog logs = new SysLog();
        logs.setServerName(CommonConstants.SYSTEM_NAME_ACCOUNT);
        logs.setInParas("客户类型：" + custType + "；客户号:" + custNum);
        //1 数据验证
        //客户类型验证
        if (!DDPStringUtil.existingWithLength(custType, 1) ||
                null == MerUserTypeEnum.getMerUserUserTypeByCode(custType)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
            return dodopalResponse;
        }
        //客户号验证
        if (!DDPStringUtil.existingWithLength(custNum, 40)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
            return dodopalResponse;
        }
        //交易流水号验证
        if (!DDPStringUtil.existingWithLength(tradeNum, 40)) {
            //交易流水不合法
            dodopalResponse.setCode(ResponseCode.ACC_TRAN_NUM_ERROR);
            return dodopalResponse;
        }
        //金额验证
        if (amount <= 0) {
            //金额不合法
            dodopalResponse.setCode(ResponseCode.ACC_AMOUNT_ERROR);
            return dodopalResponse;
        }
        try {
            //账户充值 资金变动表新增记录，资金授信表和资金变动累计表更改记录
            accountService.accountUnfreeze(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.DATABASE_ERROR);
            logs.setRespCode(ResponseCode.DATABASE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } catch (DDPException e) {
            e.printStackTrace();
            //捕获service方法中throw的异常code
            dodopalResponse.setCode(e.getCode());
            logs.setRespCode(e.getCode());
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } catch (Exception e) {
            e.printStackTrace();
            //账户解冻失败
            dodopalResponse.setCode(ResponseCode.ACC_UNFREEZE_ERROR);//TODO  资金账户解冻失败
            logs.setRespCode(ResponseCode.ACC_UNFREEZE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } finally {
            /*SysLog syslog = new SysLog();
            syslog.setInParas(JSONObject.toJSONString(custType) + "|" + JSONObject.toJSONString(custNum) + "|" + JSONObject.toJSONString(tradeNum) + "|" + JSONObject.toJSONString(amount));
            syslog.setTradeStart(System.currentTimeMillis());
            syslog.setRespCode(dodopalResponse.getCode());
            syslog.setRespExplain("账户解冻");
            syslog.setDescription("账户解冻");
            syslog.setOrderNum(tradeNum);
            syslog.setClassName(this.getClass().getName());
            syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            syslog.setOutParas(JSONObject.toJSONString(dodopalResponse));
            ddpLog.info(syslog);*/
            ActivemqLogPublisher.publishLog2Queue(logs, DodopalAppVarPropsUtil.getStringProp("server.log.name"), DodopalAppVarPropsUtil.getStringProp("server.log.url"));
        }
        log.info("账户解冻方法结束=============================" + dodopalResponse.getCode());
        return dodopalResponse;
    }

    /**
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     * @description 账户扣款接口实现
     */
    @Override
    public DodopalResponse<String> accountDeduct(String custType, String custNum, String tradeNum, long amount,String operateId) {
        log.info("账户扣款方法结束=============================");
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        SysLog logs = new SysLog();
        logs.setServerName(CommonConstants.SYSTEM_NAME_ACCOUNT);
        logs.setInParas("客户类型：" + custType + "；客户号:" + custNum);
        //1 数据验证
        //客户类型验证
        if (!DDPStringUtil.existingWithLength(custType, 1) ||
                null == MerUserTypeEnum.getMerUserUserTypeByCode(custType)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
            return dodopalResponse;
        }
        //客户号验证
        if (!DDPStringUtil.existingWithLength(custNum, 40)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
            return dodopalResponse;
        }
        //交易流水号验证
        if (!DDPStringUtil.existingWithLength(tradeNum, 40)) {
            //交易流水不合法
            dodopalResponse.setCode(ResponseCode.ACC_TRAN_NUM_ERROR);
            return dodopalResponse;
        }
        //金额验证
        if (amount <= 0) {
            //金额不合法
            dodopalResponse.setCode(ResponseCode.ACC_AMOUNT_ERROR);
            return dodopalResponse;
        }
        try {
            //账户充值 资金变动表新增记录，资金授信表和资金变动累计表更改记录
            accountService.accountDeduct(custType, custNum, tradeNum, amount,operateId);
        } catch (SQLException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.DATABASE_ERROR);
            logs.setRespCode(ResponseCode.DATABASE_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } catch (DDPException e) {
            e.printStackTrace();
            //捕获service方法中throw的异常code
            dodopalResponse.setCode(e.getCode());
            logs.setRespCode(e.getCode());
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } catch (Exception e) {
            e.printStackTrace();
            //账户扣款失败
            dodopalResponse.setCode(ResponseCode.ACC_ACCOUNT_DEDUCT_ERROR);
            logs.setRespCode(ResponseCode.ACC_ACCOUNT_DEDUCT_ERROR);
            logs.setStatckTrace(e.getStackTrace().toString());
            return dodopalResponse;
        } finally {
            /*SysLog syslog = new SysLog();
            syslog.setInParas(JSONObject.toJSONString(custType) + "|" + JSONObject.toJSONString(custNum) + "|" + JSONObject.toJSONString(tradeNum) + "|" + JSONObject.toJSONString(amount));
            syslog.setTradeStart(System.currentTimeMillis());
            syslog.setRespCode(dodopalResponse.getCode());
            syslog.setRespExplain("账户扣款");
            syslog.setDescription("账户扣款");
            syslog.setOrderNum(tradeNum);
            syslog.setClassName(this.getClass().getName());
            syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            syslog.setOutParas(JSONObject.toJSONString(dodopalResponse));
            ddpLog.info(syslog);*/
            ActivemqLogPublisher.publishLog2Queue(logs, DodopalAppVarPropsUtil.getStringProp("server.log.name"), DodopalAppVarPropsUtil.getStringProp("server.log.url"));
        }
        log.info("账户扣款方法结束=============================" + dodopalResponse.getCode());
        return dodopalResponse;
    }

    /**
     * 9 账户调帐 add by shenxiang
     * 授权用户登录OSS系统后，对未审批的调账单进行审批，通过后调用此接口。 支持批量。
     *
     * @param approveDTOList
     * @return
     */
    @Override
    public DodopalResponse<Boolean> accountAdjustment(AccountAdjustmentApproveListDTO approveDTOList) {
        // TODO 日志
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            response = accountAdjustmentService.accountAdjustment(approveDTOList);
        } catch (DDPException ddpex) {
            response.setCode(ddpex.getCode());
            response.setMessage(ddpex.getMessage());
        } catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 11 账户转帐
     *
     * @param accountTransferListDTO
     * @return
     * @author 袁越
     * @description 转账指的是：将指定的金额从一个源资金账户中，转入到另一个或者多个指定的资金账户中。
     */
    @Override
    public DodopalResponse<Boolean> accountTransfer(AccountTransferListDTO accountTransferListDTO) {
        DodopalResponse<Boolean> response = null;
        try {
            response = accountFundService.accountTransfer(accountTransferListDTO);
        } catch (DDPException e) {
            response = new DodopalResponse<Boolean>();
            response.setResponseEntity(false);
            response.setCode(e.getCode());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response = new DodopalResponse<Boolean>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        return response;
    }

    /**
     * 禁用/启用账户
     *
     * @param oper
     * @param fundAccountIds
     * @return
     */
    @Override
    public DodopalResponse<String> operateFundAccountsById(String oper, List<String> fundAccountIds, String userId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            accountFundService.operateFundAccountsById(oper, fundAccountIds, userId);
        } catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<String> checkRecharge(String custType, String custNum, long amount) {
        log.info("账户充值风控检查开始=============================");
        DodopalResponse<String> dodopalResponse = new DodopalResponse<String>();
        dodopalResponse.setCode(ResponseCode.SUCCESS);
        //1 数据验证
        //客户类型验证
        if (!DDPStringUtil.existingWithLength(custType, 1) ||
                null == MerUserTypeEnum.getMerUserUserTypeByCode(custType)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
            return dodopalResponse;
        }
        //客户号验证
        if (!DDPStringUtil.existingWithLength(custNum, 40)) {
            dodopalResponse.setCode(ResponseCode.ACC_CUSTOMER_CODE_ERROR);
            return dodopalResponse;
        }
        //金额验证
        if (amount <= 0) {
            //金额不合法
            dodopalResponse.setCode(ResponseCode.ACC_AMOUNT_ERROR);
            return dodopalResponse;
        }
        AccountControl control = accountService.queryAccountControl(custType, custNum, amount);
        try {
            accountService.checkRecharge(custType, custNum, amount);
        } catch (SQLException e) {
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.DATABASE_ERROR);
        } catch (DDPException e) {
            e.printStackTrace();
            //捕获service方法中throw的异常code
            log.info("========检查风控返回ddpexceptioncode========="+e.getCode());
            dodopalResponse.setCode(e.getCode());
            if(ResponseCode.ACC_RECHARGE_DAY_ONE_ERROR.equals(e.getCode())){
            	dodopalResponse.setMessage("超过日充值交易单笔限额(元):"+new DecimalFormat("0.00").format((double)control.getDayRechargeSingleLimit()/100)+"") ;
            }else if(ResponseCode.ACC_RECHARGE_DAY_SUM_ERROR.equals(e.getCode())){
            	dodopalResponse.setMessage("超过日充值交易累计限额(元):"+new DecimalFormat("0.00").format((double)control.getDayRechargeSumLimit()/100)+"");
            }/*else{
            	dodopalResponse.setMessage(e.getMessage());
            }*/
            return dodopalResponse;
        } catch (Exception e) {
            e.printStackTrace();
            //账户充值失败
            e.getMessage();
            dodopalResponse.setCode(ResponseCode.ACC_CHECKRECHARGE_ERROR);
            return dodopalResponse;
        }
        log.info("账户充值风控检查结束=============================" + dodopalResponse.getCode());
        return dodopalResponse;
    }
}
