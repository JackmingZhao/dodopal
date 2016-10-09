package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.MerDdpInfo;

public interface MerDdpInfoMapper {

    /**
     * 增加商户补充信息表数据
     * @param merDdpInfo
     */
    public int addMerDdpInfo(MerDdpInfo merDdpInfo);

}
