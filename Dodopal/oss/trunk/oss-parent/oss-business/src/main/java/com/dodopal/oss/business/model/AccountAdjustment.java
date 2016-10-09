package com.dodopal.oss.business.model;

import java.math.BigDecimal;
import java.util.Date;

import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.AccountAdjustmentStateEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 2.6 调账申请单 对应表ACCOUNT_ADJUSTMENT
 * @author Mango
 */
public class AccountAdjustment extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;

    //  调账单编号   只读  20150428222211 +五位数据库cycle sequence（循环使用）   长度<40
    private String accountAdjustCode;
    //调账方式    单选框 用户选择    长度=1    正调账、反调账
    private String accountAdjustType;
    // 客户类型    单选框 用户选择    长度=1    个人、企业
    private String customerType;
    // 客户号 只读  通过查询界面进行选择  长度<40
    private String customerNo;

    // 客户名称    只读  根据选择的客户号自动复制    长度<128 冗余保存
    private String customerName;

    /**
     * // 账户类型 单选框 1. 对于个人账户，只显示一个“资金账户”的单选框。 2.
     * 对于企业客户，如果这个企业没有授信账户，则只显示一个“资金账户”单选框。 3.
     * 对于企业客户，如果这个企业有授信账户，则同时显示“资金账户”和“授信账户”两个单选框给用户选择。 长度=1, 必须提供
     * 账户类型分为：资金账户和授信账户。 1. 对于个人而言，只有资金账户。 2.
     * 对于企业，至少有一个资金账户，可能有一个授信账户。这个可以通过主账户上的“资金类别”字段区分。
     */
    private String fundType;

    //  账户号 只读  根据用户在UI上选择的客户以及账户类型，自动显示对应的账户号。 长度<40   
    private String fundAccountCode;

    //    调账金额    文本框 正数  必须提供    UI上单位为元，但是数据库中的单位为分。
    private long accountAdjustAmount;

    //    调账原因    文本框 人工填写调账原因    必须提供 长度<256  
    private String accountAdjustReason;
    //    状态  只读  系统根据流程自动设置。 状态  未审批、调账成功、调账失败、审批不通过 注：调账成功、调账失败是在审批通过后才会有的状态
    private String accountAdjustState;

    // 申请人 只读  自动设置为当前登录人  必须提供    这个是在填写申请单的时候设置。
    private String accountAdjustApplyUser;

    //    申请时间    只读  自动设置为系统时间   必须提供    yyyy-MM-dd HH:MM:SS
    //    这个是在填写申请单的时候设置。
    private Date accountAdjustApplyDate;

    //    审核人 只读  自动设置为当前登录人      注意这个是在审核操作的时候设置。
    private String accountAdjustAuditUser;
    //    审核说明    TA  用户输入    长度<256
    private String accountAdjustAuditExplain;

    //    审核时间    只读  审核不通过自动更新       
    private Date accountAdjustAuditDate;
    //    完成时间    只读  完整帐户调账（帐户操作）之后，系统自动记录的服务器时间。格式：yyyy-MM-dd HH:mm:ss。 完成时间    Y
    private Date completeDate;
    public String getAccountAdjustCode() {
        return accountAdjustCode;
    }
    public void setAccountAdjustCode(String accountAdjustCode) {
        this.accountAdjustCode = accountAdjustCode;
    }
    public String getAccountAdjustType() {
        return accountAdjustType;
    }
    public void setAccountAdjustType(String accountAdjustType) {
        this.accountAdjustType = accountAdjustType;
    }
    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    public String getCustomerNo() {
        return customerNo;
    }
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getFundType() {
        return fundType;
    }
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
    public String getFundAccountCode() {
        return fundAccountCode;
    }
    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }
    public long getAccountAdjustAmount() {
        return accountAdjustAmount;
    }
    public void setAccountAdjustAmount(long accountAdjustAmount) {
        this.accountAdjustAmount = accountAdjustAmount;
    }
    public String getAccountAdjustReason() {
        return accountAdjustReason;
    }
    public void setAccountAdjustReason(String accountAdjustReason) {
        this.accountAdjustReason = accountAdjustReason;
    }
    public String getAccountAdjustState() {
        return accountAdjustState;
    }
    public void setAccountAdjustState(String accountAdjustState) {
        this.accountAdjustState = accountAdjustState;
    }
    public String getAccountAdjustApplyUser() {
        return accountAdjustApplyUser;
    }
    public void setAccountAdjustApplyUser(String accountAdjustApplyUser) {
        this.accountAdjustApplyUser = accountAdjustApplyUser;
    }
    public Date getAccountAdjustApplyDate() {
        return accountAdjustApplyDate;
    }
    public void setAccountAdjustApplyDate(Date accountAdjustApplyDate) {
        this.accountAdjustApplyDate = accountAdjustApplyDate;
    }
    public String getAccountAdjustAuditUser() {
        return accountAdjustAuditUser;
    }
    public void setAccountAdjustAuditUser(String accountAdjustAuditUser) {
        this.accountAdjustAuditUser = accountAdjustAuditUser;
    }
    public String getAccountAdjustAuditExplain() {
        return accountAdjustAuditExplain;
    }
    public void setAccountAdjustAuditExplain(String accountAdjustAuditExplain) {
        this.accountAdjustAuditExplain = accountAdjustAuditExplain;
    }
    public Date getAccountAdjustAuditDate() {
        return accountAdjustAuditDate;
    }
    public void setAccountAdjustAuditDate(Date accountAdjustAuditDate) {
        this.accountAdjustAuditDate = accountAdjustAuditDate;
    }
    public Date getCompleteDate() {
        return completeDate;
    }
    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getAdjustmentId() {
        return super.getId();
    }
    
    public String getFundTypeStr() {
        return FundTypeEnum.getFundTypeNameByCode(fundType);
    }

    public String getAccountAdjustTypeStr() {
        return AccTranTypeEnum.getTranTypeNameByCode(accountAdjustType);
    }

    public String getAccountAdjustStateStr() {
        return AccountAdjustmentStateEnum.getAdjustmentStateNameByCode(accountAdjustState);
    }
    
    public String getAccountAdjustAmountStr() {
        BigDecimal amt = new BigDecimal(accountAdjustAmount);
        return amt.divide(new BigDecimal(100)).toPlainString();
    }
    
    public String getCustomerTypeStr() {
        return MerUserTypeEnum.getMerUserTypeNameByCode(customerType);
    }
    
}
