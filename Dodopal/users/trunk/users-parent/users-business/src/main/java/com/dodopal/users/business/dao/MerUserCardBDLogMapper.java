package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.MerUserCardBDLog;
import com.dodopal.users.business.model.query.UserCardLogQuery;

public interface MerUserCardBDLogMapper {
    
    public void addUserCodeCordBindlog (MerUserCardBDLog cardLog);
    
    /**
     * 查询
     * @param query
     * @return DodopalDataPage<MerUserCardBDLog>
     */
    public List<MerUserCardBDLog> findUserCardBDLogByPage(UserCardLogQuery query);

    public List<MerUserCardBDLog> getExportExcelUserCardLog(UserCardLogQuery query);

}
