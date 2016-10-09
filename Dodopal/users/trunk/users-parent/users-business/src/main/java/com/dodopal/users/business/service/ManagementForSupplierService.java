package com.dodopal.users.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.MerCount;
import com.dodopal.users.business.model.query.MerCountQuery;

public interface ManagementForSupplierService {
    
    /**
     * 用户查询城市下所有的商户信息
     * @param merCountQuery
     * @return
     */
    public DodopalDataPage<MerCount> findMerCountByPage(MerCountQuery merCountQuery);

}
