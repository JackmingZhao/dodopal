package com.dodopal.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dodopal.common.dao.AreaMapper;
import com.dodopal.common.model.Area;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.AreaFirstWordUtils;

/**
 * 省市县 地区区域
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService,InitializingBean {
	private static final Map<String, Area> areaMap = new HashMap<String, Area>();
	private static Map<String, List<Area>> areaMapList = new HashMap<String, List<Area>>();

    @Autowired
    private AreaMapper areaMapper;

    @Override
	public void afterPropertiesSet() throws Exception {
    	loadAllCityInfo();
    	putAreaListForFirstWorld();
	}
 
    @Transactional(readOnly = true)
    private void loadAllCityInfo() {
        List<Area> areaList = areaMapper.findAllCityInfo();
        if(!CollectionUtils.isEmpty(areaList)) {
        	for (Area areaTemp : areaList) {
                areaMap.put(areaTemp.getCityCode(), areaTemp);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Area> loadAllProvinces() {
        List<Area> provinces = areaMapper.loadAllProvinces();
        Area empty = new Area();
        empty.setCityCode("");
        empty.setCityName("--请选择--");
        provinces.add(0, empty);
        for (Area province : provinces) {
            if (province != null) {
                province.setSubAreas(loadSubArea(province.getCityCode()));
            }
        }
        return provinces;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Area> loadSubArea(String parentCode) {
        return areaMapper.loadSubArea(parentCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Area findCityByCityCode(String cityCode) {
    	if(StringUtils.isBlank(cityCode)){
    		return null;
    	}
    	if (areaMap.containsKey(cityCode)) {
			return areaMap.get(cityCode);
		}
		Area city = areaMapper.findCityByCityCode(cityCode);
		if (city != null) {
			areaMap.put(city.getCityCode(), city);
			return city;
		}
		return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Area> findAllCityInfo() {
        return areaMapper.findAllCityInfo();
    }

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<Area>> createMapForFirstWord() {
		if(null!=areaMapList){
			return areaMapList;
		}
	   List<Area> allArea = areaMapper.findAllCityInfo();
	   areaMapList = AreaFirstWordUtils.createAreaMap(allArea);
	   return areaMapList;
	}
	
	@Transactional(readOnly = true)
	private void putAreaListForFirstWorld(){
		List<Area> allArea = areaMapper.findAllCityInfo();
		areaMapList = AreaFirstWordUtils.createAreaMap(allArea);
	}

	@Override
	@Transactional(readOnly = true)
	public String findCityNameByCityCode(String cityCode) {
		if (areaMap.containsKey(cityCode)) {
			return areaMap.get(cityCode).getCityName();
		}
		return null;
	}

	
}
