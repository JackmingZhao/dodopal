package com.dodopal.common.service;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.Area;

/**
 * 省市县 地区区域
 */
public interface AreaService {

    public List<Area> loadAllProvinces();

    public List<Area> loadSubArea(String cityCode);

    public Area findCityByCityCode(String cityCode);

    public List<Area> findAllCityInfo();
    
    public Map<String,List<Area>> createMapForFirstWord();

    public String findCityNameByCityCode(String cityCode);
}
