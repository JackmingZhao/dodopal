package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.ProfitCollect;
import com.dodopal.users.business.model.query.ProfitQuery;

public interface ProfitCollectService {

    public DodopalDataPage<ProfitCollect> findProfitCollectByPage(ProfitQuery query);

    public List<ProfitCollect> findProfitCollect(ProfitQuery query);
}
