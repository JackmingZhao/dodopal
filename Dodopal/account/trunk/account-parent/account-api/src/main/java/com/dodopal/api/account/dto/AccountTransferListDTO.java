package com.dodopal.api.account.dto;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.common.model.BaseEntity;

/**
 * 账户转账接口参数
 * @author 袁越
 *
 */
public class AccountTransferListDTO extends BaseEntity{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1478789130933026665L;
    
    /**
     * 操作人ID
     */
    private String operateId;
    
    /**
     * 具体账户转账信息
     */
    private List<AccountTransferDTO> accountTransferListDTO = new ArrayList<AccountTransferDTO>();

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public List<AccountTransferDTO> getAccountTransferListDTO() {
        return accountTransferListDTO;
    }

    public void setAccountTransferListDTO(List<AccountTransferDTO> accountTransferListDTO) {
        this.accountTransferListDTO = accountTransferListDTO;
    }
}