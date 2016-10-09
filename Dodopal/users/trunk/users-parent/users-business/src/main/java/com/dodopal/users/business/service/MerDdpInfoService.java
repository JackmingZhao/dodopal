package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerDdpInfo;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerDdpInfoService {
    public MerDdpInfo findMerDdpInfoById(String id);

    public MerDdpInfo findMerDdpInfoByMerCode(String merCode);

    public List<MerDdpInfo> findMerDdpInfoByMerCodes(List<String> merCodes);

    public int addMerDdpInfo(MerDdpInfo merDdpInfo);

    public int updateMerDdpInfo(MerDdpInfo merDdpInfo);

    public int deleteMerDdpInfo(String merCode);

    public int batchDelMerDdpInfoByMerCodes(List<String> merCodes);
}
