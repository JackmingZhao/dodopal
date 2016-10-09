package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

/**
 * @author lifeng@dodopal.com
 */

public class PersonalHisOrderQueryDTO extends QueryBase {
	private static final long serialVersionUID = 6087829922447109310L;

	private String userid;
	private String startdate;
	private String enddate;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}
