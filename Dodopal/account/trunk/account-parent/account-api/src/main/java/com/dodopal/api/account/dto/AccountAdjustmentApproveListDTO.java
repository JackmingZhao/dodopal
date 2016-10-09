package com.dodopal.api.account.dto;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.common.model.BaseEntity;

/**
 * 9    账户调帐输入参数
 * 
 * @author dodopal
 *
 */
public class AccountAdjustmentApproveListDTO extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5255694593811508042L;
    
    /**
     * 操作人ID
     */
    private String operateId;
    
    /**
     * 调账信息
     */
    private List<AccountAdjustmentApproveDTO> approveDTOs = new ArrayList<AccountAdjustmentApproveDTO>();
    

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public List<AccountAdjustmentApproveDTO> getApproveDTOs() {
        return approveDTOs;
    }

    public void setApproveDTOs(List<AccountAdjustmentApproveDTO> approveDTOs) {
        this.approveDTOs = approveDTOs;
    }

}
