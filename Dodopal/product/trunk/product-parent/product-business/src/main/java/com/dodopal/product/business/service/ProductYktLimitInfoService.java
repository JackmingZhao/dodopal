package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.ProductYktLimitBatchInfo;
import com.dodopal.product.business.model.ProductYktLimitInfo;
import com.dodopal.product.business.model.query.ProductYktLimitBatchInfoQuery;
import com.dodopal.product.business.model.query.ProductYktLimitInfoQuery;

public interface ProductYktLimitInfoService {
    
    /**
     * 增加额度信息（在添加通卡公司的时候默认追加）
     * @param queryDTO 查询条件
     * @return
     */
    public int addProductYktLimitInfo(ProductYktLimitInfo limitInfo);
    
    /**
     * 查询额度信息(分页)
     * @param queryDTO 查询条件
     * @return
     */
    public DodopalDataPage<ProductYktLimitInfo> findProductYktLimitInfoByPage(ProductYktLimitInfoQuery query);
    
    /**
     * 获取额度导出信息
     * @param queryDTO 查询条件
     * @return
     */
    public DodopalResponse<List<ProductYktLimitInfo>> getProductYktLimitListForExportExcel(ProductYktLimitInfoQuery query);
    
    /**
     * 查询额度信息明细
     * @param limitId 额度信息ID
     * @return
     */
    public ProductYktLimitInfo findProductYktLimitInfoById(String limitId);
    
    /**
     * 修改额度信息明细
     * @param ProductYktLimitInfoDTO 额度信息
     * @return
     */
    public int updateProductYktLimitInfo(ProductYktLimitInfo limitInfo);
    
    /**
     * 查询批次申请单(分頁)
     * @param ProductYktLimitBatchInfoQueryDTO 查询条件
     * @return
     */
    public DodopalDataPage<ProductYktLimitBatchInfo> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQuery query);
    
    /**
     * 查看批次申请单信息详情
     * @param id 查询条件
     * @return
     */
    public ProductYktLimitBatchInfo findProductYktLimitBatchInfoById(String id);
    
    /**
     * 导出批次申请单信息
     * @param ProductYktLimitBatchInfoQueryDTO 查询条件
     * @return
     */
    public DodopalResponse<List<ProductYktLimitBatchInfo>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoQuery query);
    
    /**
     * 追加批次申请单
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    public DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    /**
     * 删除批次申请单
     * @param id 
     * @return
     */
    public DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id);
    
    /**
     * 修改批次申请单
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    public DodopalResponse<Integer> updateProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    /**
     * 审核批次申请单
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    public DodopalResponse<Integer> auditProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
    
    
    /**
     * 复核批次申请单
     * @param ProductYktLimitBatchInfoDTO 批次信息
     * @return
     */
    public DodopalResponse<Integer> checkProductYktLimitBatchInfo(ProductYktLimitBatchInfo limitbatchInfo);
}
