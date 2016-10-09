package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.users.business.dao.PosLogMapper;
import com.dodopal.users.business.model.PosLog;
import com.dodopal.users.business.model.PosLogQuery;
import com.dodopal.users.business.service.PosLogService;

@Service("posLogService")
public class PosLogServiceImpl implements PosLogService {

	@Autowired
	private PosLogMapper posLogMapper;
	
	@Override
	public List<PosLog> findPosLogList(PosLogQuery findBean) {
		
		return posLogMapper.findPosLogListByPage(findBean);
	}

    @Override
    public List<PosLog> findPosLogByList(PosLogQuery findBean) {
        return posLogMapper.findPosLogByList(findBean);
    }

}
