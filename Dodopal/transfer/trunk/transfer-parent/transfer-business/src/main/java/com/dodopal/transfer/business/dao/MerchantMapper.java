package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.Merchant;

public interface MerchantMapper {
    //商户号
    public String getMerCodeSeq();
    //1.连锁商户数据插入新表merchant
    public int addMerchant(Merchant merchant);
    
    /**
     * @author Mikaelyan
     * @param merchant
     * @return
     */
    public int insertFromProxyInfoTb(Merchant merchant);
}
