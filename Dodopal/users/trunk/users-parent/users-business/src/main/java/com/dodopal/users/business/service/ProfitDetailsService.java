package com.dodopal.users.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.ProfitDetails;
import com.dodopal.users.business.model.query.ProfitQuery;

public interface ProfitDetailsService {
	public DodopalDataPage<ProfitDetails> findProfitDetailsByPage(ProfitQuery query);
}
