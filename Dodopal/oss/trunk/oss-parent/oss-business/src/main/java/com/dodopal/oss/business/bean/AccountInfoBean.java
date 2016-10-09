package com.dodopal.oss.business.bean;

import java.util.List;

import com.dodopal.common.model.BaseEntity;

/**
 * 账户详细信息查询
 * @author guanjinglun
 */
public class AccountInfoBean extends BaseEntity {

    private static final long serialVersionUID = 8384818575822416400L;

    /*
     * 主账户所有信息
     */
    private AccountBean accountBean;

    /*
     * 资金账户除支付密码、支付密码是否启用、加密字段之外的所有信息；
     */
    private List<AccountInfoFundBean> accountInfoFundBeans;

    /*
     * 资金账户风控所有信息。
     */
    private List<AccountControlBean> accountControlBeans;

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

    public List<AccountInfoFundBean> getAccountInfoFundBeans() {
        return accountInfoFundBeans;
    }

    public void setAccountInfoFundBeans(List<AccountInfoFundBean> accountInfoFundBeans) {
        this.accountInfoFundBeans = accountInfoFundBeans;
    }

    public List<AccountControlBean> getAccountControlBeans() {
        return accountControlBeans;
    }

    public void setAccountControlBeans(List<AccountControlBean> accountControlBeans) {
        this.accountControlBeans = accountControlBeans;
    }

}
