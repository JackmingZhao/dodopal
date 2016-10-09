package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.BusinessCity;

public interface IcdcBusinessCityMapper {
    
    public List<BusinessCity> findAllCityByName(String cityName);
    
    public List<BusinessCity> findBusinessCityByName(String cityName);
    
    public List<BusinessCity> findAllCityByCityabridge(String[] param);
    
    public List<BusinessCity> findBusinessCityByCityabridge(String[] param);
    
}
    
