package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingYkt;
import com.dodopal.oss.business.model.dto.YktClearingResultQuery;


public interface ClearingYktMapper {
    
    public List<ClearingYkt> findYktClearingResultByPage(YktClearingResultQuery bankQuery);
    
    public List<ClearingYkt> getYktClearingResultForExport(YktClearingResultQuery bankQuery);
}
    
