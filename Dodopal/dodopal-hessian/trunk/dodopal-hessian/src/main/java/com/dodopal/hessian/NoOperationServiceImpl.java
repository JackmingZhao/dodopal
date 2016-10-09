/*
 * Sdo.com Inc.
 * Copyright (c) 2009 All Rights Reserved.
 */
package com.dodopal.hessian;

import org.springframework.stereotype.Service;


/**
 * Hessian用于检测接口是否可用的Service.
 *
 *
 */
@Service("noOperationService")
public class NoOperationServiceImpl implements NoOperationService {

	/* (non-Javadoc)
	 * @see com.sdo.netbar.netbarsys.common.hessian.NoOperationService#nop()
	 */
	public void nop() {
		//不需要实现.
	}

}
