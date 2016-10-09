package com.dodopal.account.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.account.business.constant.AccountConstants;
import com.dodopal.account.business.dao.AccountChangeMapper;
import com.dodopal.account.business.dao.AccountChangeSumMapper;
import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.account.business.model.AccountChange;
import com.dodopal.account.business.model.AccountChangeSum;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountFundService;
import com.dodopal.account.business.service.AccountRiskControllerService;
import com.dodopal.api.account.dto.AccountTransferDTO;
import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DDPLog;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;

@Service
public class AccountFundServiceImpl implements AccountFundService {
    @Autowired
    private AccountFundMapper accountFundMapper;
    @Autowired
    private AccountChangeMapper accountChangeMapper;
    @Autowired
    private AccountChangeSumMapper accountChangeSumMapper;
    @Autowired
    private AccountRiskControllerService accountRiskControllerService;
    //日志类
    private DDPLog<AccountFundServiceImpl> ddpLog = new DDPLog<>(AccountFundServiceImpl.class);

    /**
     * 账户转账(资金账户1：N资金账户的转入转出)
     * @author 袁越
     * @param accountTransferListDTO
     * @return 响应体 true：成功，false：失败。
     */
    @SuppressWarnings("finally")
	@Override
    @Transactional
    public DodopalResponse<Boolean> accountTransfer(AccountTransferListDTO accountTransferListDTO) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String operateId = null;// 操作员id
        List<AccountTransferDTO> accountTransferDTOList = null;// 详细的账户转账信息
        long total = 0L;//总额
        int totalCount = 0;
        String sourceAccoutNum = null;//出账方资金账户编号
        String sourceAccoutType = null;//出账方资金账户类型
        String nowDate = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDD_STR);
        try {
            //检查入参
            if (accountTransferListDTO == null 
                || DDPStringUtil.isNotPopulated(accountTransferListDTO.getOperateId()) 
                || accountTransferListDTO.getAccountTransferListDTO() == null 
                || accountTransferListDTO.getAccountTransferListDTO().size() == 0) {
                response.setCode(ResponseCode.ACC_TRAN_PARAM_EMPTY);
                throw new DDPException(ResponseCode.ACC_TRAN_PARAM_EMPTY, response.getMessage());
            }

            operateId = accountTransferListDTO.getOperateId();
            accountTransferDTOList = accountTransferListDTO.getAccountTransferListDTO();
            //1.进行遍历检查
            for (AccountTransferDTO accountTransferDTO : accountTransferDTOList) {
                //2.入参合法性检查
                response = accountTransferCheckParam(accountTransferDTO);
                if (!response.getResponseEntity()) {
                    throw new DDPException(response.getCode(), response.getMessage());
                }
                //3.转出
                if (AccTranTypeEnum.ACC_TRANSFER_OUT.getCode().equals(accountTransferDTO.getTransferType())) {
                    //3.1统计总额
                    total += accountTransferDTO.getAmount();
                    totalCount += 1;
                    if (sourceAccoutNum == null || sourceAccoutType == null) {
                        sourceAccoutNum = accountTransferDTO.getSourceCustNum();
                        sourceAccoutType = accountTransferDTO.getSourceCustType();
                    }
                    //3.转入
                } else if (AccTranTypeEnum.ACC_TRANSFER_IN.getCode().equals(accountTransferDTO.getTransferType())) {
                    //3.1验证入账账户合法性
                    AccountFund accountFund = accountFundMapper.queryAccountFundInfoByCustTypeAndCustNumForUpdate(accountTransferDTO.getSourceCustType(), accountTransferDTO.getSourceCustNum(), FundTypeEnum.FUND.getCode());
                    if (accountFund == null) {
                       response.setCode(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
                        throw new DDPException(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR, response.getMessage());
                    }
                    if (!AccStatusEnum.ENABLE.getCode().equals(accountFund.getState())) {
                        response.setCode(ResponseCode.ACC_TARGETCUST_IN_STATE_ERROR);
                        //response.setMessage("资金账户号"+accountFund.getFundAccountCode()+"的账户状态禁用");
                        throw new DDPException(ResponseCode.ACC_TARGETCUST_IN_STATE_ERROR, response.getMessage());
                    }
                }
            }
            //6.对账户资金转出风险控制
            DodopalResponse<Boolean> checkRs = accountRiskControllerService.allowedToTransfer(sourceAccoutType, sourceAccoutNum, total, accountTransferListDTO.getOperateId(),totalCount);
            if (!checkRs.getResponseEntity()) {
                response.setCode(checkRs.getCode());
                response.setMessage(checkRs.getMessage());
                throw new DDPException(response.getCode(), response.getMessage());
            }
            //7.遍历添加资金变动记录
            for (AccountTransferDTO accountTransferDTO : accountTransferDTOList) {
                AccountChange accountChange = new AccountChange();
                //7.1出账账户记录
                if (AccTranTypeEnum.ACC_TRANSFER_OUT.getCode().equals(accountTransferDTO.getTransferType())) {
                    if (!accountTransferDTO.getSourceCustNum().equals(sourceAccoutNum) 
                        || !accountTransferDTO.getSourceCustType().equals(sourceAccoutType)) {
                        response.setCode(ResponseCode.ACC_SOURCEACCOUNT_NOT_SAME);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                    AccountFund sourceAccountFund = accountFundMapper.queryAccountFundInfoByCustTypeAndCustNumForUpdate(sourceAccoutType, sourceAccoutNum, FundTypeEnum.FUND.getCode());
                    if (sourceAccountFund == null) {
                        response.setCode(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }

                    //8.1增加资金变动信息
                    accountChange.setChangeAmount(accountTransferDTO.getAmount());
                    accountChange.setChangeType(accountTransferDTO.getTransferType());
                    accountChange.setFundAccountCode(sourceAccountFund.getFundAccountCode());
                    accountChange.setFundType(sourceAccountFund.getFundType());
                    accountChange.setTranCode(accountTransferDTO.getTradeNum());
                    accountChange.setBeforeChangeAmount(sourceAccountFund.getTotalBalance());
                    accountChange.setBeforeChangeAvailableAmount(sourceAccountFund.getAvailableBalance());
                    accountChange.setBeforeChangeFrozenAmount(sourceAccountFund.getFrozenAmount());
                    accountChange.setComments(accountTransferDTO.getComments());
                    accountChange.setCreateUser(operateId);
                    accountChangeMapper.addAccountChange(accountChange);
                    //8.2增加资金变动累计信息
                    AccountChangeSum accountChangeSum = accountChangeSumMapper.queryAccountChangeSumByDateAndCodeForUpdate(sourceAccountFund.getFundAccountCode(), nowDate);
                    if (accountChangeSum == null) {
                        accountChangeSum = new AccountChangeSum(sourceAccountFund.getFundAccountCode(), nowDate, 0, 0, 0, 0, nowDate);
                        accountChangeSum.setCreateUser(operateId);
                        accountChangeSum.setCreateDate(new Date());
                    }
                    long dayTranLimit = accountChangeSum.getDayTranLimit();
                    long dayTranTimes = accountChangeSum.getDayTranTimes();
                    dayTranLimit += accountTransferDTO.getAmount();// 日累计转出金额
                    dayTranTimes += 1;// 日累计转出次数
                    accountChangeSum.setDayTranLimit(dayTranLimit);
                    accountChangeSum.setDayTranTimes(dayTranTimes);
                    accountChangeSum.setUpdateUser(operateId);
                    accountChangeSum.setUpdateDate(new Date());
                    int count = accountChangeSumMapper.updateAccountChangeSumByDateAndCode(accountChangeSum);
                    if (count != 1) {
                        response.setCode(ResponseCode.SYSTEM_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                    //8.3资金授信账户金额调整
                    sourceAccountFund.setBeforeChangeAvailableAmount(sourceAccountFund.getAvailableBalance());
                    sourceAccountFund.setBeforeChangeFrozenAmount(sourceAccountFund.getFrozenAmount());
                    sourceAccountFund.setBeforeChangeTotalAmount(sourceAccountFund.getTotalBalance());
                    sourceAccountFund.setLastChangeAmount(total);
                    sourceAccountFund.setUpdateUser(operateId);
                    long totalBalance = sourceAccountFund.getAvailableBalance() - accountTransferDTO.getAmount();
                    if (totalBalance < 0L) {
                        response.setCode(ResponseCode.ACC_ACC_BALANCE_NOT_ENOUGH);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                    sourceAccountFund.setAvailableBalance(sourceAccountFund.getAvailableBalance() - accountTransferDTO.getAmount());
                    sourceAccountFund.setTotalBalance(sourceAccountFund.getTotalBalance() - accountTransferDTO.getAmount());
                    sourceAccountFund.setFrozenAmount(sourceAccountFund.getFrozenAmount());
                    count = accountFundMapper.updateFundAccount(sourceAccountFund);
                    if (count != 1) {
                        response.setCode(ResponseCode.SYSTEM_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                    //7.2入账账户记录
                } else if (AccTranTypeEnum.ACC_TRANSFER_IN.getCode().equals(accountTransferDTO.getTransferType())) {
                    AccountFund targetAccountFund = accountFundMapper.queryAccountFundInfoByCustTypeAndCustNumForUpdate(accountTransferDTO.getSourceCustType(), accountTransferDTO.getSourceCustNum(), FundTypeEnum.FUND.getCode());
                    //AccountFund accountFundClone = null;
                    if (targetAccountFund == null) {
                        response.setCode(ResponseCode.ACC_ILLEGAL_DEDUCT_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }

                    //校验金额
                    if (targetAccountFund.getTotalBalance() + accountTransferDTO.getAmount() > AccountConstants.TOTALBALANCE_MAX) {
                        response.setCode(ResponseCode.ACC_TRAN_TOTALFUND_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                    if (targetAccountFund.getSumTotalAmount() + accountTransferDTO.getAmount() > AccountConstants.TOTALBALANCE_SUM_MAX) {
                        response.setCode(ResponseCode.ACC_TRAN_SUMTOTALFACCOUNT_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                    //8.1增加资金变动信息
                    accountChange.setTranCode(accountTransferDTO.getTradeNum());
                    accountChange.setChangeAmount(accountTransferDTO.getAmount());
                    accountChange.setChangeType(accountTransferDTO.getTransferType());
                    accountChange.setFundAccountCode(targetAccountFund.getFundAccountCode());
                    accountChange.setFundType(targetAccountFund.getFundType());
                    accountChange.setBeforeChangeAmount(targetAccountFund.getTotalBalance());
                    accountChange.setBeforeChangeAvailableAmount(targetAccountFund.getAvailableBalance());
                    accountChange.setBeforeChangeFrozenAmount(targetAccountFund.getFrozenAmount());
                    accountChange.setComments(accountTransferDTO.getComments());
                    accountChange.setCreateUser(operateId);
                    accountChangeMapper.addAccountChange(accountChange);
                    //8.2资金授信账户金额调整
                    targetAccountFund.setBeforeChangeAvailableAmount(targetAccountFund.getAvailableBalance());
                    targetAccountFund.setBeforeChangeTotalAmount(targetAccountFund.getTotalBalance());
                    targetAccountFund.setBeforeChangeFrozenAmount(targetAccountFund.getFrozenAmount());
                    long totalBalance = targetAccountFund.getTotalBalance() + accountTransferDTO.getAmount();
                    targetAccountFund.setLastChangeAmount(accountTransferDTO.getAmount());
                    targetAccountFund.setTotalBalance(totalBalance);
                    targetAccountFund.setUpdateUser(operateId);
                    targetAccountFund.setAvailableBalance(targetAccountFund.getAvailableBalance() + accountTransferDTO.getAmount());
                    targetAccountFund.setSumTotalAmount(targetAccountFund.getSumTotalAmount() + accountTransferDTO.getAmount());
                    targetAccountFund.setFrozenAmount(targetAccountFund.getFrozenAmount());
                    int count = accountFundMapper.updateFundAccount(targetAccountFund);
                    if (count != 1) {
                        response.setCode(ResponseCode.SYSTEM_ERROR);
                        throw new DDPException(response.getCode(), response.getMessage());
                    }
                } else {
                    response.setCode(ResponseCode.ACC_TRAN_PARAM_ERROR);
                    throw new DDPException(response.getCode(), response.getMessage());
                }
            }
            response.setResponseEntity(true);
            response.setCode(ResponseCode.SUCCESS);
            return response;
        }
        finally {
            SysLog syslog = new SysLog();
            syslog.setInParas(JSONObject.toJSONString(accountTransferDTOList));
            syslog.setTradeStart(System.currentTimeMillis());
            syslog.setRespCode(response.getCode());
            syslog.setRespExplain("授信资金账户转账");
            syslog.setDescription("授信资金账户转账");
            syslog.setClassName(this.getClass().getName());
            syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
            syslog.setOutParas(JSONObject.toJSONString(response));
            ddpLog.info(syslog);
//            return response;
        }

    }

    /**
     * 账户转账转账参数验证
     * @param accountTransferDTO
     * @return
     */
    private DodopalResponse<Boolean> accountTransferCheckParam(AccountTransferDTO accountTransferDTO) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setResponseEntity(true);
        if (!DDPStringUtil.existingWithLength(accountTransferDTO.getSourceCustNum(), 40)) {
            response.setCode(ResponseCode.ACC_SOURCECUST_NUM_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        if (!DDPStringUtil.existingWithLength(accountTransferDTO.getSourceCustType(), 1)) {
            response.setCode(ResponseCode.ACC_SOURCECUST_TYPE_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        if (!DDPStringUtil.existingWithLength(accountTransferDTO.getTradeNum(), 40)) {
            response.setCode(ResponseCode.ACC_TRADE_NUM_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        if (accountTransferDTO.getAmount() <= 0L) {
            response.setCode(ResponseCode.ACC_AMMOUNT_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        // 交易流水的交易类型转化为账户的交易类型
        if (TranTypeEnum.TURN_INTO.getCode().equals(accountTransferDTO.getTransferType())) {
            accountTransferDTO.setTransferType(AccTranTypeEnum.ACC_TRANSFER_IN.getCode());
        } else if (TranTypeEnum.TURN_OUT.getCode().equals(accountTransferDTO.getTransferType())) {
            accountTransferDTO.setTransferType(AccTranTypeEnum.ACC_TRANSFER_OUT.getCode());
        } else {
            response.setCode(ResponseCode.ACC_TRAN_PARAM_ERROR);
            response.setResponseEntity(false);
            return response;
        }
        return response;
    }

    /**
     * 禁用/启用账户
     * @param oper
     * @param fundAccountIds
     * @return
     */
    @Transactional
    @Override
    public void operateFundAccountsById(String oper, List<String> fundAccountIds, String userId) {
        for (String fundId : fundAccountIds) {
            accountFundMapper.operateFundAccountsById(oper, fundId, userId);
        }
    }
}
