package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.dto.ProductYktLimitBatchInfoDTO;
import com.dodopal.api.product.dto.ProductYktLimitInfoDTO;
import com.dodopal.api.product.dto.query.ProductYKTQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitBatchInfoQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitInfoQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcDelegate {
    
    /**************************************************** 基础信息管理开始 *****************************************************/
    
    public DodopalResponse<DodopalDataPage<ProductYKTDTO>> findProductYktByPage(ProductYKTQueryDTO dto);
    
    public DodopalResponse<ProductYKTDTO> findProductYktById(String id);
    
    public DodopalResponse<Integer> startOrStopYkt(List<String> yktCode, String activate, String updateUser);
    
    public DodopalResponse<Integer> addProductYkt(ProductYKTDTO productYkt);
    
    public DodopalResponse<Integer> updateProductYkt(ProductYKTDTO productYkt);
    
    public DodopalResponse<List<ProductYKTDTO>> getProductYktListForExportExcel(ProductYKTQueryDTO dto);
    
    /**************************************************** 基础信息管理结束 *****************************************************/
    
    /**************************************************** 额度管理开始 *****************************************************/
    
    public DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>> findProductYktLimitInfoByPage(ProductYktLimitInfoQueryDTO dto);
    
    public DodopalResponse<ProductYktLimitInfoDTO> findProductYktLimitInfoById(String id);
    
    public DodopalResponse<Integer> saveProductYktLimitInfo(ProductYktLimitInfoDTO productYktLimitInfoDTO);
    
    public DodopalResponse<List<ProductYktLimitInfoDTO>> getProductYktLimitListForExportExcel(ProductYktLimitInfoQueryDTO dto);
    
    /**************************************************** 额度管理结束 *****************************************************/
    
    /**************************************************** 额度批次信息管理开始 *****************************************************/
    
    public DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQueryDTO dto);
    
    public DodopalResponse<ProductYktLimitBatchInfoDTO> findProductYktLimitBatchInfoById(String id);
    
    public DodopalResponse<List<ProductYktLimitBatchInfoDTO>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoQueryDTO dto);
    
    public DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    public DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id);
    
    public DodopalResponse<Integer> saveProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    public DodopalResponse<Integer> auditProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    public DodopalResponse<Integer> checkProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO);
    
    /**************************************************** 额度批次信息管理开始 *****************************************************/

}
