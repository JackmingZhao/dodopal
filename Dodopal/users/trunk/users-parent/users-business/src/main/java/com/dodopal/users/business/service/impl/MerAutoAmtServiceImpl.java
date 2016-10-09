package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerAutoAmtMapper;
import com.dodopal.users.business.model.MerAutoAmt;
import com.dodopal.users.business.service.MerAutoAmtService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class MerAutoAmtServiceImpl implements MerAutoAmtService {
	@Autowired
	private MerAutoAmtMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public MerAutoAmt findMerAutoAmtByMerCode(String merCode) {
		return mapper.findMerAutoAmtByMerCode(merCode);
	}

	@Override
	@Transactional
	public int addMerAutoAmt(MerAutoAmt merAutoAmt) {
		return mapper.addMerAutoAmt(merAutoAmt);
	}

	@Override
	@Transactional
	public int updateMerAutoAmt(MerAutoAmt merAutoAmt) {
		return mapper.updateMerAutoAmt(merAutoAmt);
	}

	@Override
	@Transactional
	public int deleteMerAutoAmtByMerCode(String merCode) {
		return mapper.deleteMerAutoAmtByMerCode(merCode);
	}

	@Override
	@Transactional
	public int batchDelMerAutoAmtByMerCodes(List<String> merCodes) {
		return mapper.batchDelMerAutoAmtByMerCodes(merCodes);
	}

}
