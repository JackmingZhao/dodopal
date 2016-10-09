package com.dodopal.api.account.facade;

import java.util.List;

import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountManagementFacade {
    /**
     * @description 创建账户
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param accType  账户类型:0 资金;1 授信
     * @return 响应码
     */
    public DodopalResponse<String> createAccount(String custType,String custNum,String accType,String merType,String operateId);

    /**
     * @description 账户充值
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
      * @return 响应码
     */
    public DodopalResponse<String> accountRecharge(String custType,String custNum,String tradeNum,long amount,String operateId);

    /**
     * @description 账户冻结接口实现
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     */
    public DodopalResponse<String> accountFreeze(String custType, String custNum,String tradeNum, long amount,String operateId);
    /**
     * @description 账户解冻接口实现
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     */
    public DodopalResponse<String> accountUnfreeze(String custType, String custNum,String tradeNum, long amount,String operateId);
    /**
     * @description 账户扣款接口实现
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param tradeNum 交易流水号
     * @param amount   交易金额
     * @return 响应码
     */
    public DodopalResponse<String> accountDeduct(String custType, String custNum, String tradeNum, long amount,String operateId);
    
    /**
     * 9 账户调帐 add by shenxiang
     * 
     * @description 授权用户登录OSS系统后，对未审批的调账单进行审批，通过后调用此接口。 支持批量。
     * @param approveListDTO 
     * @return
     */
    public DodopalResponse<Boolean> accountAdjustment(AccountAdjustmentApproveListDTO approveListDTO);

    /**
     * 11 账户转帐
     * @author 袁越
     * @description 转账指的是：将指定的金额从一个源资金账户中，转入到另一个或者多个指定的资金账户中。
     * @param accountTransferListDTO 
     * @return
     */
    public DodopalResponse<Boolean> accountTransfer(AccountTransferListDTO accountTransferListDTO);
    
    /**
     * 禁用/启用账户
     * @param oper
     * @param fundAccountIds
     * @return
     */
    public DodopalResponse<String> operateFundAccountsById(String oper, List<String> fundAccountIds, String userId);

    /**
     * @description  账户风控检查
     * @param custType
     * @param custNum
     * @param amount
     * @return
     */
    public DodopalResponse<String> checkRecharge(String custType,String custNum,long amount);
}