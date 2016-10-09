package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.query.ProductYKTQuery;

public interface ProductYKTMapper {
	
    public List<ProductYKT> findProductYktByPage(ProductYKTQuery query);
    
    public int getCountForProductYktExportExcel(ProductYKTQuery query);
    
    public List<ProductYKT> getListForProductYktExportExcel(ProductYKTQuery query);
    
    public List<ProductYKT> findProductYktById(String id);
    
    public int getYktCountByName(ProductYKT productYKT);
    
    public int getYktCountByCode(ProductYKT productYKT);
	
    public int batchUpdateYktActivate(@Param("productYKT") ProductYKT productYKT, @Param("yktCodes") List<String> yktCodes);
    
    public int addProductYkt(ProductYKT productYKT);
    
    public int updateProductYkt(ProductYKT productYKT);
    
    public List<ProductYKT> getIsRechargeYktMap(@Param("activate")String activate);
    
    public List<ProductYKT> getAllYktBusinessRateList();
    
    public ProductYKT getYktInfoByBusinessCityCode(String cityCode);
    
    public ProductYKT getYktInfoByYktCode(String yktCode);
    //更新供应商商户号
    public int updatePrdMerCode(ProductYKT productYKT);
}
