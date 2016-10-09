package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.MerchantUserExtend;

public interface MerchantUserExtendMapper {

    /**
     * 增加商户用户扩展表的记录
     * @param merchantUserExtend
     */
   public int addMerchantUserExtend(MerchantUserExtend merchantUserExtend);

}
