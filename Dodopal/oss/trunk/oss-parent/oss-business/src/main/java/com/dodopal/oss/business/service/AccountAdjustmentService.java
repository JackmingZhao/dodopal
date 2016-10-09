package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.AccountAdjustment;
import com.dodopal.oss.business.model.CustomerAccount;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.AccountAdjustmentQuery;
import com.dodopal.oss.business.model.dto.CustomerAccountQuery;

public interface AccountAdjustmentService {

    /**
     * 10.1 调账申请
     * 授权用户在调账管理主界面上，点击“申请”按钮，可以触发该操作。

     *用户在申请单详细信息页面上填写必要的信息（参考调账申请单）。
     *1.  选择调账方式；
     *2.  选择客户类型；
     *3.  根据选择的客户类型，在弹出窗口查询个人或者企业（请调用通用的企业和个人查询窗口页面）。选择了查询结果之后，自动关联客户号，客户名称。如果是个人，自动关联账户类型（资金账户）和账户号（资金账户号）；如果是企业，且主账户上的资金类别字段为“资金”， 自动关联账户类型（资金账户）和账户号（资金账户号）；如果是企业，且主账户上的资金类别为“授信”，则显示账户类型给用户选择。
     *4.  根据上步骤的结果，决定是否选择账户类型，一旦账户类型指定，自动关联账户号（可能是资金账户号，也可能是授信账户号）。
     *5.  根据实际情况填写其他信息。
     *6.  点击保存按钮，系统会根据调账申请单的定义校验数据正确性。成功保存之后，回到调账单主界面。

     */
    DodopalResponse<String> applyAccountAdjustment(AccountAdjustment adjustment);
    
    /**
     * 10.2 调账执行（批量执行）
     * 
     * 授权用户可以在调账管理主界面上选择一条或多条申请单，
     * 如果点击“执行”按钮，可以触发该操作。
     * 1.  检查该申请单的状态是否为：未审批，否则提示：此状态不能进行审核。
     * 2.  进入申请单详细信息页面，审批人可以根据实际情况决定：执行、拒绝。
     * 3.  如果是拒绝，必须填写审核说明。
     * 4.  Hessian方式 调用账户调账接口： 账户调帐
     * 5、根据接口响应码更新申请单：调帐成功或调帐失败、审核人、审核时间、完成时间、审核说明。
     * 如果点击“批量执行”按钮，可以触发该操作。
     * 1、  检查该申请单的状态是否为：未审批，否则提示：请选择未审批的申请单。
     * 2、  弹出框形式：
     * 内容：审核说明
     * 按钮：执行、拒绝、取消
     * 2.1点击执行：触发以下操作
     * Hessian方式 调用账户调账接口： 账户调帐
     * 根据接口响应码更新申请单：调帐成功或调帐失败、审核人、
     * 审核时间、完成时间、审核说明。
     * 2.2 点击拒绝：触发以下操作
     * 必须填写审核说明。
     * 更新申请单：审核不通过、审核人、审核时间、审核说明。
     * 2.3 点击取消：弹出框关闭，数据库不做任何变动
     * 
     */
    DodopalResponse<Boolean>  approveAccountAdjustment( AccountAdjustment[] adjustments, User user);
    
    
    /**
     * 10.3 调账查询
     * 
     * 授权用户在调账管理主界面上，点击“查询”按钮，可以触发该操作。
     */
    DodopalDataPage<AccountAdjustment> findAccountAdjustmentByPage(AccountAdjustmentQuery query);
    
    /**
     * 10.3 调账查询
     * 
     * 选择一条查询结果之后，点击“查看”按钮，系统以只读的方式显示申请单所有信息。
     */
    AccountAdjustment findAccountAdjustment(String adjustmentId);
    
    /**
     * 10.4 调账修改
     * 
     * 授权用户在调账管理主界面上，选择一条记录，点击“修改”按钮，可以触发该操作。
     * 1.  检查该申请单的状态是否为：未审批、审核不通过，否则提示：此状态不能进行修改。
     * 2.  进入申请单详细信息页面，只能修改以下字段：调账金额、调账原因。
     * 
     */
    DodopalResponse<String> updatetAccountAdjustments(AccountAdjustment adjustment);
    
    /**
     * 10.5 调账删除
     * 授权用户在调账管理主界面上，选择一条记录，点击“删除“该操作。
     * 1.  检查该申请单的状态是否为：未审批，否则提示：此状态不能进行删除。
     * 2.  系统提示用户是否确认删除，一旦确认，就物理删除。
     * 
     */
    DodopalResponse<String> deleteAccountAdjustment(String[] adjustmentId);
    
    DodopalResponse<Boolean>  rejectAccountAdjustment(AccountAdjustment[] adjustments, User user);
    
    DodopalResponse<DodopalDataPage<CustomerAccount>> findCustomerAccount(CustomerAccountQuery customerAccountQuery);
    
    /**
     * 资金授信查询 add by shenxiang
     * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * 
     * @param custType(客户类型：个人、商户)
     * @param custNum(类型是商户：商户号；类型是个人：用户编号)
     * @return
     */
    DodopalResponse<FundAccountInfoDTO> findFundAccountInfo(CustomerAccountQuery customerAccountQuery);
    
    /**
     * 10.3 调账查询
     * 
     * 授权用户在调账管理主界面上，点击“导出”按钮，可以触发该操作。
     */
    List<AccountAdjustment> findAccountAdjustment(AccountAdjustmentQuery aaq);
}
