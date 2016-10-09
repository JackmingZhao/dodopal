package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ProductIcdc;

public interface ProductIcdcMapper {
    //查询通卡公司名称
    public List<ProductIcdc> findProductIcdcNames(ProductIcdc productIcdc);
    //查询通卡公司内容
    public List<ProductIcdc> findProductIcdcbByCode(String[] productCodes);
}
