package com.dodopal.api.users.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * 操作人信息
 * @author 
 */

public class OperUserDTO extends BaseEntity {

	private static final long serialVersionUID = 5963146723881085230L;
	
	/*操作人名称*/
    private String operName;

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	    
   
}
