package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingMerTxn;
import com.dodopal.oss.business.model.dto.MerClearingResultQuery;


public interface ClearingMerTxnMapper {
    
    public List<ClearingMerTxn> getMerTxnClearingResult(MerClearingResultQuery bankQuery);
    
}
    
