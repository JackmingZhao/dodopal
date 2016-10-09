package com.dodopal.users.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerGroupUserMapper;
import com.dodopal.users.business.model.MerGroupUser;
import com.dodopal.users.business.model.MerGroupUserFind;
import com.dodopal.users.business.service.MerGroupUserService;


@Service("merGroupUserService")
public class MerGroupUserServiceImpl implements MerGroupUserService {
	
	@Autowired
	private MerGroupUserMapper merGroupUserMapper;

	@Override
	@Transactional(readOnly = true)
	public List<MerGroupUser> findMerGpUsers(MerGroupUserFind findBean) {		
		return merGroupUserMapper.findMerGpUsers(findBean);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MerGroupUser> findMerGpUsersByPage(MerGroupUserFind findBean) {		
		return merGroupUserMapper.findMerGpUsersByPage(findBean);
	}

	@Override
	@Transactional(readOnly = true)
	public MerGroupUser findMerGpUserById(String gpUserId) {		
		return merGroupUserMapper.findMerGpUserById(gpUserId);
	}
	

	@Override
	@Transactional(readOnly = true)
	public MerGroupUser getMerGpUserByCarCode(MerGroupUserFind findBean) {		
		return merGroupUserMapper.getMerGpUserByCarCode(findBean);
	}
	

	@Override
	@Transactional(readOnly = true)
	public int getMerGpUserCount(MerGroupUserFind findBean){
		return merGroupUserMapper.getMerGpUserCount(findBean);
	}
	
	@Override
	@Transactional
	public void insertMerGpUsers(List<MerGroupUser> gpUsers) {	
		for(MerGroupUser itme:gpUsers){
			merGroupUserMapper.insertMerGpUser(itme);
		}
	}
	
	@Override
	@Transactional
	public void insertMerGpUser(MerGroupUser gpUser) {		
			merGroupUserMapper.insertMerGpUser(gpUser);
	}

	@Override
	@Transactional
	public int updateMerGpUser(MerGroupUser gpUser) {
		return merGroupUserMapper.updateMerGpUser(gpUser);
	}

	@Override
	@Transactional
	public int deleteMerGpUser(String gpUserId) {
		 return merGroupUserMapper.deleteMerGpUser(gpUserId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MerGroupUser> findMerGpUsersByUserName(String merCode, String merUserName) {
		return merGroupUserMapper.findMerGpUsersByUserName(merCode, merUserName);
	}

	
}
