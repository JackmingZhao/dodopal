package com.dodopal.common.service;

import org.springframework.stereotype.Service;

@Service("sysOperationService")
public interface SysOperationService {

	public boolean isUpdated(String code);
	
}
