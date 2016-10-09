package com.dodopal.users.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.dao.MerLimitExtractConfirmMapper;
import com.dodopal.users.business.model.MerLimitExtractConfirm;
import com.dodopal.users.business.model.query.MerLimitExtractConfirmQuery;
import com.dodopal.users.business.service.MerLimitExtractConfirmService;
@Service
public class MerLimitExtractConfirmServiceImpl implements MerLimitExtractConfirmService {
    private final static Logger log = LoggerFactory.getLogger(MerLimitExtractConfirmServiceImpl.class);
	@Autowired
	private MerLimitExtractConfirmMapper merLimitExtractConfirmMapper;
	
	@Override
	public void insertMerLimitExtractConfirm(MerLimitExtractConfirm merLimitExtractConfirm) {
		// TODO Auto-generated method stub
		merLimitExtractConfirmMapper.insertMerLimitExtractConfirm(merLimitExtractConfirm);
	}

	@Override
	public int findMerLimitExtractConfirmByCode(MerLimitExtractConfirm merLimitExtractConfirm) {
		// TODO Auto-generated method stub
		return merLimitExtractConfirmMapper.findMerLimitExtractConfirmByCode(merLimitExtractConfirm);
	}

    @Override
    public DodopalDataPage<MerLimitExtractConfirm> findMerLimitExtractConfirmByPage(MerLimitExtractConfirmQuery merLimitExtractConfirmQuery) {
        log.info("users api query findMerLimitExtractConfirmByPage:"+merLimitExtractConfirmQuery);
        List<MerLimitExtractConfirm> listMerLimt = merLimitExtractConfirmMapper.findMerLimitExtractConfirmByPage(merLimitExtractConfirmQuery);
        DodopalDataPage<MerLimitExtractConfirm> pages = new DodopalDataPage<MerLimitExtractConfirm>(merLimitExtractConfirmQuery.getPage(), listMerLimt);
        return pages;
    }

    @Override
    public DodopalResponse<Integer> updateMerLimitExtractConfirmByCode(MerLimitExtractConfirm merLimitExtractConfirm) {
            int  merLimitCount= merLimitExtractConfirmMapper.updateMerLimitExtractConfirmByCode(merLimitExtractConfirm);
            DodopalResponse<Integer> respone = new DodopalResponse<Integer>();
            respone.setResponseEntity(merLimitCount);
            return respone;
    }

}
