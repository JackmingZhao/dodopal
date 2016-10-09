package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerDdpInfoMapper;
import com.dodopal.users.business.model.MerDdpInfo;
import com.dodopal.users.business.service.MerDdpInfoService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerDdpInfoServiceImpl implements MerDdpInfoService {
    @Autowired
    private MerDdpInfoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public MerDdpInfo findMerDdpInfoById(String id) {
        return mapper.findMerDdpInfoById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MerDdpInfo findMerDdpInfoByMerCode(String merCode) {
        return mapper.findMerDdpInfoByMerCode(merCode);
    }

    @Override
    @Transactional(readOnly = true)
	public List<MerDdpInfo> findMerDdpInfoByMerCodes(List<String> merCodes) {
		return mapper.findMerDdpInfoByMerCodes(merCodes);
	}

    @Override
    @Transactional
    public int addMerDdpInfo(MerDdpInfo merDdpInfo) {
        return mapper.addMerDdpInfo(merDdpInfo);
    }

    @Override
    @Transactional
    public int updateMerDdpInfo(MerDdpInfo merDdpInfo) {
        return mapper.updateMerDdpInfo(merDdpInfo);
    }

    @Override
    @Transactional
    public int deleteMerDdpInfo(String merCode) {
        return mapper.deleteMerDdpInfo(merCode);
    }

    @Override
    @Transactional
    public int batchDelMerDdpInfoByMerCodes(List<String> merCodes) {
        return mapper.batchDelMerDdpInfoByMerCodes(merCodes);
    }

}
