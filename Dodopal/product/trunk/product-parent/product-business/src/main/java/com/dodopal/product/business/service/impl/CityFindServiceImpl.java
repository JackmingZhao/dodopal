package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.dao.ProductYktCityInfoMapper;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.delegate.CityFindDelegate;

@Service
public  class CityFindServiceImpl implements CityFindService{
 private final static Logger log = LoggerFactory.getLogger(CityFindServiceImpl.class);
    
    @Autowired
    CityFindDelegate cityFindDelegate;
    @Autowired
    ProductYktCityInfoMapper productYktCityInfoMapper;
    /**
     * 城市列表查询
     * @param cityBean
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<List<Area>> findYktCitys(MerUserTypeEnum custType, String custCode){
        log.info("findYktCitys:城市列表查询:"+custCode);
        DodopalResponse<List<Area>> resultResponse = new DodopalResponse<List<Area>>();
        try{
            
            resultResponse = cityFindDelegate.findYktCitys(custType,custCode);
            if (ResponseCode.SUCCESS.equals(resultResponse.getCode())) {
                List<Area> recityto = resultResponse.getResponseEntity();
                if (recityto != null) {
                     resultResponse.setCode(ResponseCode.SUCCESS);
                     resultResponse.setResponseEntity(recityto);
                }
            }else{
                resultResponse.setCode(resultResponse.getCode());
            }
        }catch(HessianRuntimeException e){
            log.error("findYktCitys:城市列表查询,Hessian链接异常", e);
            e.printStackTrace();
            resultResponse.setCode(ResponseCode.PRODUCT_CALL_CARD_ERROR);
        }catch (Exception e) {
            log.error("findYktCitys:城市列表查询,系统错误", e);
            e.printStackTrace();
            resultResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return resultResponse;
    }
	@Override
	public List<Area> getAllBusinessCity() {
		List<Map<String, String>> list = productYktCityInfoMapper.getAllBusinessCity();
		List<Area> areaList = new ArrayList<Area>();
		if(CollectionUtils.isNotEmpty(list)){
			for(Map<String, String> tempMap:list){
				Area area = new Area();
				area.setCityCode(tempMap.get("CITYID"));
				area.setCityName(tempMap.get("CITYNAME"));
				areaList.add(area);
			}
		}
		return areaList;
	}
}
