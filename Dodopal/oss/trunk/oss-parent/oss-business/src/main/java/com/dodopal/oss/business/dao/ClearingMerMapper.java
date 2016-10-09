package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingMer;
import com.dodopal.oss.business.model.dto.MerClearingResultQuery;


public interface ClearingMerMapper {
    
    public List<ClearingMer> findMerClearingResultByPage(MerClearingResultQuery bankQuery);
    
    public List<ClearingMer> getMerClearingResultForExport(MerClearingResultQuery bankQuery);
    
}
    
