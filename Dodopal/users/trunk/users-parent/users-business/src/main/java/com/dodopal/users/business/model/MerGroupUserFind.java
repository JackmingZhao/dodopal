package com.dodopal.users.business.model;


import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.model.QueryBase;
import com.dodopal.common.util.DDPStringUtil;

/**
 * 集团用户查询条件
 * @author 
 */

public class MerGroupUserFind extends QueryBase {		
   
	private static final long serialVersionUID = 1653528731016114801L;

	/**商户编码*/
    private String merCode;
    
	/**员工姓名*/
    private String gpUserName;
    
    /**所在部门ID*/
    private String depId;
    
    /**公交卡号*/
    private String cardCode;
   
    /** 在职状态: 0:在职，1：离职*/
    private String empType;    
    

	public String getMerCode() {
		return DDPStringUtil.trim(merCode);
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getGpUserName() {
		return DDPStringUtil.trim(gpUserName);
	}

	public void setGpUserName(String gpUserName) {
		this.gpUserName = gpUserName;
	}

	public String getDepId() {
		return DDPStringUtil.trim(depId);
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getCardCode() {
		return DDPStringUtil.trim(cardCode);
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getEmpType() {
		return DDPStringUtil.trim(empType);
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}
    
   
}