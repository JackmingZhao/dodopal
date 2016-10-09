package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.AccountAdjustment;
import com.dodopal.oss.business.model.dto.AccountAdjustmentQuery;

public interface AccountAdjustmentMapper {

    
    public String selectAdjustmentCodeSeq();
    
    /**
     * 10.1 调账申请
     */
    public void applyAccountAdjustment(AccountAdjustment adjustment);

    /**
     * 10.3 调账查询 (分页)
     */
    public List<AccountAdjustment> findAccountAdjustmentByPage(AccountAdjustmentQuery query);

    /**
     * 10.3 调账查询 选择一条查询结果之后，点击“查看”按钮，系统以只读的方式显示申请单所有信息。
     */
    public AccountAdjustment findAccountAdjustment(String adjustmentId);

    /**
     * 10.4 调账修改
     */
    public void updatetAccountAdjustment(AccountAdjustment adjustment);

    /**
     * 10.5 调账删除
     */
    public void deleteAccountAdjustment(String[] adjustmentId);
    
    /**
     * 更新调账执行
     * @param adjustment
     */
    public void updateAuditAccountAdjustment(AccountAdjustment adjustment);
}
