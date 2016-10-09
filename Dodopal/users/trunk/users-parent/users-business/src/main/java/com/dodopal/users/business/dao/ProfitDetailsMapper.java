package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.ProfitDetails;
import com.dodopal.users.business.model.query.ProfitQuery;

public interface ProfitDetailsMapper {
	 public List<ProfitDetails> findProfitDetailsByPage(ProfitQuery query);
	 
	 public List<ProfitDetails> findProfitDetails(ProfitQuery query);
	 
}
