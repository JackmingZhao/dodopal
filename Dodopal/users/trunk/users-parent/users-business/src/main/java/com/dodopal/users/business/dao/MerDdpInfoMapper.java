package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerDdpInfo;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerDdpInfoMapper {
    public MerDdpInfo findMerDdpInfoById(String id);

    public MerDdpInfo findMerDdpInfoByMerCode(String merCode);

    public List<MerDdpInfo> findMerDdpInfoByMerCodes(@Param("merCodes") List<String> merCodes);

    public int addMerDdpInfo(MerDdpInfo merDdpInfo);

    public int updateMerDdpInfo(MerDdpInfo merDdpInfo);

    public int deleteMerDdpInfo(String merCoder);

    public int batchDelMerDdpInfoByMerCodes(@Param("merCodes") List<String> merCodes);
}
