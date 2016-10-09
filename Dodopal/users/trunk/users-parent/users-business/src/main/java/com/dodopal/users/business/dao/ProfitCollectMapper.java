package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.ProfitCollect;
import com.dodopal.users.business.model.query.ProfitQuery;

public interface ProfitCollectMapper {
	 public List<ProfitCollect> findProfitCollectByPage(ProfitQuery queryUser);
	 /**
	  * 查询
	  * @param queryUser
	  * @return
	  */
	 public List<ProfitCollect> findProfitCollect(ProfitQuery queryUser);
}
