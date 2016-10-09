package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * @author lifeng@dodopal.com
 */

public class MerchantUserExtend extends BaseEntity {
	private static final long serialVersionUID = -3050675805333653420L;
	/* 用户编码 */
	private String userCode;
	/* 老系统用户ID */
	private String oldUserId;
	/* 老系统用户类型，个人用户0，集团用户1，网点用户2，商户用户3 */
	private String oldUserType;
	/* 用户注册来源，详见表usersourcetb */
	private String usertype1;
	/* 通过活动注册的用户的活动ID，详见表usersourcetb */
	private String activeid;
	/* 微信号 */
	private String wechatid;
	/* 微信头像 */
	private String wechaticon;
	/* 职业 */
	private String occupation;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	public String getOldUserType() {
		return oldUserType;
	}

	public void setOldUserType(String oldUserType) {
		this.oldUserType = oldUserType;
	}

	public String getUsertype1() {
		return usertype1;
	}

	public void setUsertype1(String usertype1) {
		this.usertype1 = usertype1;
	}

	public String getActiveid() {
		return activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getWechaticon() {
		return wechaticon;
	}

	public void setWechaticon(String wechaticon) {
		this.wechaticon = wechaticon;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

}
