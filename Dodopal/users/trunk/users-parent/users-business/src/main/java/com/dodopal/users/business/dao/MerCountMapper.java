package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MerCount;
import com.dodopal.users.business.model.query.MerCountQuery;


public interface MerCountMapper {
    
    /**
     * 查询城市下商户信息
     * @param merCountQuery
     * @return
     */
   List<MerCount> findMerCountByPage(MerCountQuery merCountQuery);

}
