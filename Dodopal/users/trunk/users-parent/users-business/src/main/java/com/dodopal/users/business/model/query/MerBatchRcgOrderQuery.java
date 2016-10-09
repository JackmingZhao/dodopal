package com.dodopal.users.business.model.query;

import com.dodopal.api.users.dto.query.MerBatchRcgOrderQueryDTO;

/**
 * @author lifeng@dodopal.com
 */

public class MerBatchRcgOrderQuery extends MerBatchRcgOrderQueryDTO {
	private static final long serialVersionUID = 1539703292655617008L;

	private String merUserName;

	public String getMerUserName() {
		return merUserName;
	}

	public void setMerUserName(String merUserName) {
		this.merUserName = merUserName;
	}

}
