package com.dodopal.product.business.bean;

import java.io.Serializable;

/**
 * 查询账户余额（手机 、VC端）
 * @author lenovo
 *
 */
public class AccountBean  implements Serializable{
    
    private static final long serialVersionUID = -8359426019647913060L;

    /**
     * 资金类别
     */
    private String fundtype;
    
    /**
     * 资金总金额 用户账户总金额 单位“分”=可用余额+冻结金额
     */
    private long totalbalance;
    
    /**
     * 资金账户 可用余额  用户账户实际可以支配的金额单位“分”
     */
    private long availablebalance;
    
    /**
     * 资金账户 冻结金额 用户账户已经冻结不可支配的金额单位“分”
     */
    private long frozenamount;

    /**
     * 授信总金额 用户账户授信总金额 单位“分”=授信可用余额+授信冻结金额
     */
    private long authtotalbalance;
    
    /**
     *授信账户可用余额    用户授信账户实际可以支配的金额单位“分”
     */
    private long authavailablebalance ;
    
    /**
     *授信账户 冻结金额  用户授信账户已经冻结不可支配的金额单位“分”
     */
    private long authfrozenamount ;

  

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public long getTotalbalance() {
        return totalbalance;
    }

    public void setTotalbalance(long totalbalance) {
        this.totalbalance = totalbalance;
    }

    public long getAvailablebalance() {
        return availablebalance;
    }

    public void setAvailablebalance(long availablebalance) {
        this.availablebalance = availablebalance;
    }

    public long getFrozenamount() {
        return frozenamount;
    }

    public void setFrozenamount(long frozenamount) {
        this.frozenamount = frozenamount;
    }

    public long getAuthtotalbalance() {
        return authtotalbalance;
    }

    public void setAuthtotalbalance(long authtotalbalance) {
        this.authtotalbalance = authtotalbalance;
    }

    public long getAuthavailablebalance() {
        return authavailablebalance;
    }

    public void setAuthavailablebalance(long authavailablebalance) {
        this.authavailablebalance = authavailablebalance;
    }

    public long getAuthfrozenamount() {
        return authfrozenamount;
    }

    public void setAuthfrozenamount(long authfrozenamount) {
        this.authfrozenamount = authfrozenamount;
    }
    

}
