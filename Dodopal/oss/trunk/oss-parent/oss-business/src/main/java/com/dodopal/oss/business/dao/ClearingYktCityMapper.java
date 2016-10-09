package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingYktCity;
import com.dodopal.oss.business.model.dto.YktClearingResultQuery;


public interface ClearingYktCityMapper {
    
    public List<ClearingYktCity> getYktCityClearingResult(YktClearingResultQuery bankQuery);
    
}
    
