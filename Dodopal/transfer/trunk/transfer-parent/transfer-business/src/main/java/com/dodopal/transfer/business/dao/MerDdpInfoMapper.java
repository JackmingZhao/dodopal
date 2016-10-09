package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.MerDdpInfo;

public interface MerDdpInfoMapper {

    /**
     * 增加商户补充信息表数据
     * @param merDdpInfo
     */
    public void addMerDdpInfo(MerDdpInfo merDdpInfo);

}
