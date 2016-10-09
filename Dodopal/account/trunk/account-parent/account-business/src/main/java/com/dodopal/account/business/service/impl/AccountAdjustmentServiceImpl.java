package com.dodopal.account.business.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.account.business.bean.AccountFundAdjustInfoDTO;
import com.dodopal.account.business.constant.AccountConstants;
import com.dodopal.account.business.dao.AccountAdjustmentMapper;
import com.dodopal.account.business.dao.AccountChangeMapper;
import com.dodopal.account.business.dao.AccountFundMapper;
import com.dodopal.account.business.model.AccountChange;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.service.AccountAdjustmentService;
import com.dodopal.account.delegate.AccountAdjustmentDelegate;
import com.dodopal.api.account.dto.AccountAdjustmentApproveDTO;
import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccStatusEnum;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;

@Service
public class AccountAdjustmentServiceImpl implements AccountAdjustmentService {

    @Autowired
    private AccountAdjustmentMapper accountAdjustmentMapper;
    @Autowired
    public AccountChangeMapper accountChangeMapper;
    @Autowired
    public AccountFundMapper accountFundMapper;
    @Autowired
    private AccountAdjustmentDelegate accountAdjustmentDelegate;
    
    /**
     * 9 账户调帐
     */
    @Override
    @Transactional
    public DodopalResponse<Boolean> accountAdjustment(AccountAdjustmentApproveListDTO accountAdjustmentApproveListDTO) {
        // TODO 日志
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        response.setCode(ResponseCode.SUCCESS);
        
        if (accountAdjustmentApproveListDTO == null
            || accountAdjustmentApproveListDTO.getOperateId() == null
            || CollectionUtils.isEmpty(accountAdjustmentApproveListDTO.getApproveDTOs())) {
            response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
            return response;
        }
        
        List<AccountAdjustmentApproveDTO> approveDTOList = accountAdjustmentApproveListDTO.getApproveDTOs();
       
        // 校验参数
        // (a)首先校验list的各个DTO中每个字段是否提供，且合法，否则：调账参数不正确。
        // (b)检查账户的合法性，如果目标账户禁用（不合法），则错误信息：C．调账单号 + 对应的调账账户异常
        for (AccountAdjustmentApproveDTO approveDTO : approveDTOList) {
            // custType    String  1   Y   枚举，类型：个人、商户
            if (null == MerUserTypeEnum.getMerUserUserTypeByCode(approveDTO.getCustType())) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            // custNum  String  40  Y   类型是商户：商户号；类型是个人：用户编号
            if (!DDPStringUtil.existingWithLength(approveDTO.getCustNum(), 40)) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            // tradeNum String  40  Y   调账申请单号
            if (!DDPStringUtil.existingWithLength(approveDTO.getTradeNum(), 40)) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            // amount   long        Y   充值的金额，单位为分。必须为正整数。
            if (approveDTO.getAmount() <= 0) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            // fundType String  1   Y   账户类型：资金、授信
            if (null == FundTypeEnum.getFundTypeByCode(approveDTO.getFundType())) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            // accTranType  String  1   Y   账户交易类型：正调账、反调账
            if (!(AccTranTypeEnum.ACC_PT_AD.getCode().equals(approveDTO.getAccTranType())
                    || AccTranTypeEnum.ACC_NEG_AD.getCode().equals(approveDTO.getAccTranType()))) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            
            // 追加检查参数（调账单号定位的资金授信账户）与（custType、 custNum、fundType定位的资金授信账户）是否一一对应，错误信息：调账参数不正确。
            AccountFundAdjustInfoDTO fundInfoDTO = accountAdjustmentMapper.getAccountFundInfoByAdjustCode(approveDTO.getTradeNum());
            if (fundInfoDTO == null
                    || !approveDTO.getCustType().equals(fundInfoDTO.getCustomerType())
                    || !approveDTO.getCustNum().equals(fundInfoDTO.getCustomerNo())
                    || !approveDTO.getFundType().equals(fundInfoDTO.getFundType())
                    || approveDTO.getAmount() != fundInfoDTO.getAccountAdjustAmount()
                    || !approveDTO.getAccTranType().equals(fundInfoDTO.getAccountAdjustType())) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }

            // 检查账户的合法性，如果目标账户禁用（不合法），则错误信息：C．调账单号 + 对应的调账账户异常
            if (!AccStatusEnum.ENABLE.getCode().equals(fundInfoDTO.getState())) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_STATE_DISABLE);
                response.setMessage("调账单号:" + approveDTO.getTradeNum() + "对应的调账账户状态禁用（不合法）");
                return response;
            }
            
            // 追加检查用户或商户状态合法性
            DodopalResponse<Map<String, Object>> userResponse =  accountAdjustmentDelegate.validateMerchantForPayment(MerUserTypeEnum.getMerUserUserTypeByCode(approveDTO.getCustType()), approveDTO.getCustNum());
            if (!ResponseCode.SUCCESS.equals(userResponse.getCode())) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_STATE_DISABLE);
                response.setMessage("调账单号:" + approveDTO.getTradeNum() + "对应的调账账户状态禁用（不合法）");
                return response;
            }
            
        }

        // 遍历list查出各个资金账户信息生成资金授信账户MAP
        Map<String, AccountAdjustmentApproveDTO> accountFundMap = new HashMap<String, AccountAdjustmentApproveDTO>();
        for (AccountAdjustmentApproveDTO approveDTO : approveDTOList) {
            // 根据custType、 custNum、fundType定位到一条资金授信账户记录
            AccountFund accountFund = accountFundMapper.queryAccountFundInfoByCustTypeAndCustNum(approveDTO.getCustType(), approveDTO.getCustNum(), approveDTO.getFundType());
            if (accountFund == null) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_PARAMETER_ERROR);
                return response;
            }
            if (!accountFundMap.containsKey(accountFund.getFundAccountCode())) {
                accountFundMap.put(accountFund.getFundAccountCode(), approveDTO);
            }
        }
        // 遍历资金授信账户MAP中各个资金授信账户信息并诸个for update锁行处理
        Iterator<Entry<String, AccountAdjustmentApproveDTO>> iter = accountFundMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, AccountAdjustmentApproveDTO> entry = iter.next();
            //String key = entry.getKey();
            AccountAdjustmentApproveDTO value = entry.getValue();
            // for update锁行处理
            accountFundMapper.queryAccountFundInfoByCustTypeAndCustNumForUpdate(value.getCustType(), value.getCustNum(), value.getFundType());
        }

        // 遍历list执行DB操作
        for (AccountAdjustmentApproveDTO approveDTO : approveDTOList) {

            // 根据custType、 custNum、fundType定位到一条资金授信账户记录
            AccountFund accountFundInfo = accountFundMapper.queryAccountFundInfoByCustTypeAndCustNum(approveDTO.getCustType(), approveDTO.getCustNum(), approveDTO.getFundType());

            if (AccTranTypeEnum.ACC_NEG_AD.getCode().equals(approveDTO.getAccTranType()) 
                && approveDTO.getAmount() > accountFundInfo.getAvailableBalance()) {
                
                // 追加检查（反调账）调账金额是否超过账户可调限度（可用余额） 
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_AMOUNT_ERROR);
                response.setMessage("调账单号:" + approveDTO.getTradeNum() + "对应账户可用余额不足本次调账");
                throw new DDPException(response.getCode(), response.getMessage());
            } else if (AccTranTypeEnum.ACC_PT_AD.getCode().equals(approveDTO.getAccTranType())) {
                
                // 追加检查（正调账）总余额是否超过数据库限额
                long amt = accountFundInfo.getTotalBalance() + approveDTO.getAmount();
                if (amt > AccountConstants.TOTALBALANCE_MAX) {
                    response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_TOTAL_AMOUNT_OUTNUMBER_ERROR);
                    response.setMessage("调账单号:" + approveDTO.getTradeNum() + "对应账户总余额超过数据库可容纳的最大限额");
                    throw new DDPException(response.getCode(), response.getMessage());
                }
                
                // 追加检查（正调账）累计总金额是否超过数据库限额
                long sumAmt = accountFundInfo.getSumTotalAmount() + approveDTO.getAmount();
                if (sumAmt > AccountConstants.TOTALBALANCE_SUM_MAX) {
                    response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_SUM_TOTAL_AMOUNT_OUTNUMBER_ERROR);
                    response.setMessage("调账单号:" + approveDTO.getTradeNum() + "对应账户累计总金额超过数据库可容纳的最大限额");
                    throw new DDPException(response.getCode(), response.getMessage());
                }
            }

            // 查各个调账申请单对应的资金变动记录(TRAN_CODE, CHANGE_TYPE, FUND_TYP)是否已经存在
            boolean exsit = accountChangeMapper.checkAccountChangeRecordExsit(approveDTO.getTradeNum(), approveDTO.getAccTranType(), approveDTO.getFundType());
            if (exsit) {
                response.setCode(ResponseCode.ACC_ACCOUNT_ADJUSTMENT_CHANGE_RECORD_ERROR);
                response.setMessage("调账单号:" + approveDTO.getTradeNum() + "重复调账（对应的资金变动记录已经存在）");
                throw new DDPException(response.getCode(), response.getMessage());
            }

            // 创建资金出账变动记录，在下面的表格中：
            AccountChange accountChange = new AccountChange();
            accountChange.setChangeAmount(approveDTO.getAmount());// 变动金额
            accountChange.setFundAccountCode(accountFundInfo.getFundAccountCode());// 资金账户号
            accountChange.setFundType(accountFundInfo.getFundType());// 资金类别
            accountChange.setBeforeChangeAmount(accountFundInfo.getTotalBalance());// 变动前账户总余额=当前账户总余额
            accountChange.setBeforeChangeAvailableAmount(accountFundInfo.getAvailableBalance());// 变动前可用余额==当前账户可用余额
            accountChange.setBeforeChangeFrozenAmount(accountFundInfo.getFrozenAmount());// 变动前冻结金额==当前账户冻结余额
            accountChange.setChangeType(approveDTO.getAccTranType());// 变动类型
            accountChange.setTranCode(approveDTO.getTradeNum());// 交易流水号(调账：指的是调账申请单号；分润：资金账户号+时间戳（如果超过40，截取前40位）。其他：交易流水号)
            accountChange.setCreateUser(accountAdjustmentApproveListDTO.getOperateId());// 操作员ID
            accountChangeMapper.addAccountChange(accountChange);

            //  资金授信账户中金额的相应调整如下：
            AccountFund accountFund = new AccountFund();
            accountFund.setBeforeChangeAvailableAmount(accountFundInfo.getAvailableBalance());// 变动前可用余额=可用余额
            accountFund.setBeforeChangeFrozenAmount(accountFundInfo.getFrozenAmount());// 变动前冻结金额=冻结金额
            accountFund.setBeforeChangeTotalAmount(accountFundInfo.getTotalBalance());// 变动前账户总余额=账户总余额
            accountFund.setFrozenAmount(accountFundInfo.getFrozenAmount());// 冻结金额

            if (AccTranTypeEnum.ACC_PT_AD.getCode().equals(approveDTO.getAccTranType())) {
                // 正调账：= 金额 + c.amount
                accountFund.setAvailableBalance(accountFundInfo.getAvailableBalance() + approveDTO.getAmount());// 可用金额
                accountFund.setTotalBalance(accountFundInfo.getTotalBalance() + approveDTO.getAmount());// 总余额
                accountFund.setSumTotalAmount(accountFundInfo.getSumTotalAmount() + approveDTO.getAmount());// 累计总金额
            } else if (AccTranTypeEnum.ACC_NEG_AD.getCode().equals(approveDTO.getAccTranType())) {
                // 反调账：= 金额 - c.amount
                accountFund.setAvailableBalance(accountFundInfo.getAvailableBalance() - approveDTO.getAmount());// 可用金额
                accountFund.setTotalBalance(accountFundInfo.getTotalBalance() - approveDTO.getAmount());// 总余额
                //accountFund.setSumTotalAmount(accountFundInfo.getSumTotalAmount() - approveDTO.getAmount());// 累计总金额
                accountFund.setSumTotalAmount(accountFundInfo.getSumTotalAmount());// 累计总金额
            }
            accountFund.setLastChangeAmount(approveDTO.getAmount());// 最近一次变动金额
            accountFund.setFundAccountCode(accountFundInfo.getFundAccountCode());// 资金授信编号(更新用主KEY)
            accountFund.setUpdateUser(accountAdjustmentApproveListDTO.getOperateId());// 操作员ID
            accountFundMapper.updateFundAccountForAccountAdjustment(accountFund);
        }
        return response;
    }

}
