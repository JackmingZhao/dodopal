package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.MerKeyType;

public interface MerKeyTypeMapper {
    /**
     * 增加商户秘钥信息
     * @param merDdpInfo
     */
    public int addMerKeyType(MerKeyType merKeyType);

}
