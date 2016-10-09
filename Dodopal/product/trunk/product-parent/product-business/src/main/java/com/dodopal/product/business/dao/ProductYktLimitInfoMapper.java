package com.dodopal.product.business.dao;

import java.util.List;

import com.dodopal.product.business.model.ProductYktLimitBatchInfo;
import com.dodopal.product.business.model.ProductYktLimitInfo;
import com.dodopal.product.business.model.query.ProductYktLimitBatchInfoQuery;
import com.dodopal.product.business.model.query.ProductYktLimitInfoQuery;

public interface ProductYktLimitInfoMapper {
    
    public int addProductYktLimitInfo(ProductYktLimitInfo productYktLimitInfo);
    
    public int updateProductYktLimitInfo(ProductYktLimitInfo productYktLimitInfo);
    
    public List<ProductYktLimitInfo> findProductYktLimitInfoByPage(ProductYktLimitInfoQuery query);
    
    public int getCountForProductYktLimitExportExcel(ProductYktLimitInfoQuery query);
    
    public List<ProductYktLimitInfo> getListForProductYktLimitExportExcel(ProductYktLimitInfoQuery query);
    
    public ProductYktLimitInfo findProductYktLimitInfoById(String limitId);
    
    public List<ProductYktLimitBatchInfo> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQuery query);
    
    public int getCountForProductYktBatchLimitExportExcel(ProductYktLimitBatchInfoQuery query);
    
    public List<ProductYktLimitBatchInfo> getListForProductYktBatchLimitExportExcel(ProductYktLimitBatchInfoQuery query);
    
    public ProductYktLimitBatchInfo findProductYktLimitBatchInfoById(String id);
    
    public int getNextBatchCodeByYktCode(String yktCode);
    
    public int addProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    public int deleteProductYktLimitBatchInfo(String id);
    
    public int updateProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    public int auditProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    public int checkProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    public ProductYktLimitInfo findYktLimitInfoByYktCode(String limitId);
    
    public int updateYktLimitInfoAfterAuditBatch(ProductYktLimitInfo productYktLimitInfo);
}
