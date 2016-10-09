package com.dodopal.common.dao;

import java.util.List;

import com.dodopal.common.model.SysOperation;


public interface SysOperationMapper {

	public SysOperation findOperation(String code);
	
	public List<SysOperation> findOperations();
	
}
