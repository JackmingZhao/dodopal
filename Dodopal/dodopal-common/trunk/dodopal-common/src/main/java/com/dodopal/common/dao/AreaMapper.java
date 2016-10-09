package com.dodopal.common.dao;

import java.util.List;

import com.dodopal.common.model.Area;

public interface AreaMapper {

    public List<Area> loadAllProvinces();

    public List<Area> loadSubArea(String parentCode);

    public Area findCityByCityCode(String cityCode);

    public List<Area> findAllCityInfo();
}
