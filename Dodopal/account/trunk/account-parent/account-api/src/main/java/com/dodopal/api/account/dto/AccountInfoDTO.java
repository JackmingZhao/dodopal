package com.dodopal.api.account.dto;

import java.util.List;

import com.dodopal.common.model.BaseEntity;

/**
 * 账户详细信息查询
 * @author guanjinglun
 *
 */
public class AccountInfoDTO extends BaseEntity {

    private static final long serialVersionUID = -7206126236800041581L;

    /*
     * 主账户所有信息
     */
    private AccountDTO accountDTO;

    /*
     * 资金账户除支付密码、支付密码是否启用、加密字段之外的所有信息；
     */
    private List<AccountInfoFundDTO> accountInfoFundDTOs;

    /*
     * 资金账户风控所有信息。
     */
    private List<AccountControlDTO> accountControlDTOs;

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public List<AccountInfoFundDTO> getAccountInfoFundDTOs() {
        return accountInfoFundDTOs;
    }

    public void setAccountInfoFundDTOs(List<AccountInfoFundDTO> accountInfoFundDTOs) {
        this.accountInfoFundDTOs = accountInfoFundDTOs;
    }

    public List<AccountControlDTO> getAccountControlDTOs() {
        return accountControlDTOs;
    }

    public void setAccountControlDTOs(List<AccountControlDTO> accountControlDTOs) {
        this.accountControlDTOs = accountControlDTOs;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
