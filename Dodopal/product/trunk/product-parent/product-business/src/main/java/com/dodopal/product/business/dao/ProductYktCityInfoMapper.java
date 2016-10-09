package com.dodopal.product.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.query.ProductYKTQuery;

public interface ProductYktCityInfoMapper {
	
    
    public List<ProductYKT> findProductYktCityInfo(ProductYKTQuery query);
    
    public int addYktCityRelationBatch(List<ProductYKT> list); 
    
    public int moveYktCityRelationBatch(String yktCode); 
    
    public int getYktCityRelationCountByCityIds(@Param("yktCode") String yktCode, @Param("array")String[] cityIds); 
    
    public List<ProductYKT> getBusinessCityByYktCode(String yktCode);
	
    public int getProversion(@Param("cityCode") String cityCode,@Param("proversion") String proversion);
    
    public List<Map<String, String>> getAllBusinessCity();
    
    public String getProversionByCityCode(@Param("cityCode") String cityCode);
    
    public int updateProversion(@Param("cityCode") String cityCode);
    
    public int addProversion(@Param("cityCode") String cityCode);
    
    public boolean checkExistVersion(@Param("cityCode") String cityCode);
    
}
