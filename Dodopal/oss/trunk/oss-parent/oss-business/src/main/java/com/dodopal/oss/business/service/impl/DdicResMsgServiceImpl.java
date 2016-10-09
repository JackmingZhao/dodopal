package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.dao.DdicResMsgMapper;
import com.dodopal.oss.business.dao.RoleMapper;
import com.dodopal.oss.business.model.DdicResMsg;
import com.dodopal.oss.business.model.dto.DdicResMsgQuery;
import com.dodopal.oss.business.service.DdicResMsgService;

@Service
public class DdicResMsgServiceImpl implements DdicResMsgService{
	
	@Autowired
	private DdicResMsgMapper ddicResMsgMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Transactional(readOnly = true)
	public DodopalDataPage<DdicResMsg> findDdicResMsgsByPage(DdicResMsgQuery ddicResMsgQuery) {
		List<DdicResMsg> result = ddicResMsgMapper.findDdicResMsgsByPage(ddicResMsgQuery);
        DodopalDataPage<DdicResMsg> pages = new DodopalDataPage<DdicResMsg>(ddicResMsgQuery.getPage(), result);
        return pages;
	}

	@Transactional
	public String saveOrUpdateDdicResMsg(DdicResMsg ddicResMsg) {

		if (DDPStringUtil.isNotPopulated(ddicResMsg.getId())) {
			ddicResMsgMapper.insertDdicResMsg(ddicResMsg);
		} else {
			ddicResMsgMapper.updateDdicResMsg(ddicResMsg);
		}
		return CommonConstants.SUCCESS;
	}

	@Transactional(readOnly = true)
	public DdicResMsg findDdicResMsgById(String id) {
		return ddicResMsgMapper.findDdicResMsgById(id);
	}

	@Transactional
	public void batchDelDdicResMsg(List<String> ids) {
		ddicResMsgMapper.batchDelDdicResMsg(ids);
	}

	@Override
	public List<DdicResMsg> findDdicResMsgs(DdicResMsgQuery ddicResMsgQuery) {
		List<DdicResMsg> result = ddicResMsgMapper.findDdicResMsgsByPage(ddicResMsgQuery);
		return result;
	}
	
	
}
