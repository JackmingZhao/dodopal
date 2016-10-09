package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.dto.ProductYktLimitBatchInfoDTO;
import com.dodopal.api.product.dto.ProductYktLimitInfoDTO;
import com.dodopal.api.product.dto.query.ProductYKTQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitBatchInfoQueryDTO;
import com.dodopal.api.product.dto.query.ProductYktLimitInfoQueryDTO;
import com.dodopal.api.product.facade.ProductYktFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.IcdcDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("IcdcDelegate")
public class IcdcDelegateImpl extends BaseDelegate implements IcdcDelegate {

    /**************************************************** 基础信息管理开始 *****************************************************/
    
    @Override
    public DodopalResponse<DodopalDataPage<ProductYKTDTO>> findProductYktByPage(ProductYKTQueryDTO dto) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductYktByPage(dto);
    }

    @Override
    public DodopalResponse<List<ProductYKTDTO>> getProductYktListForExportExcel(ProductYKTQueryDTO dto) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.getProductYktListForExportExcel(dto);
    }
    
    @Override
    public DodopalResponse<ProductYKTDTO> findProductYktById(String id) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductYktById(id);
    }

    @Override
    public DodopalResponse<Integer> startOrStopYkt(List<String> yktCodes, String activate, String updateUser) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.startOrStopYkt(yktCodes, activate, updateUser);
    }

    @Override
    public DodopalResponse<Integer> addProductYkt(ProductYKTDTO productYkt) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.addProductYkt(productYkt);
    }

    @Override
    public DodopalResponse<Integer> updateProductYkt(ProductYKTDTO productYkt) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.updateProductYkt(productYkt);
    }

    
    /**************************************************** 基础信息管理结束 *****************************************************/
    
    /**************************************************** 额度管理开始 *****************************************************/
    @Override
    public DodopalResponse<DodopalDataPage<ProductYktLimitInfoDTO>> findProductYktLimitInfoByPage(ProductYktLimitInfoQueryDTO dto) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductYktLimitInfoByPage(dto);
    }

    @Override
    public DodopalResponse<List<ProductYktLimitInfoDTO>> getProductYktLimitListForExportExcel(ProductYktLimitInfoQueryDTO dto) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.getProductYktLimitListForExportExcel(dto);
    }
    
    @Override
    public DodopalResponse<ProductYktLimitInfoDTO> findProductYktLimitInfoById(String id) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductYktLimitInfoById(id);
    }

    @Override
    public DodopalResponse<Integer> saveProductYktLimitInfo(ProductYktLimitInfoDTO productYktLimitInfoDTO) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.saveProductYktLimitInfo(productYktLimitInfoDTO);
    }
    /**************************************************** 额度管理结束 *****************************************************/

    /**************************************************** 额度批次信息管理开始 *****************************************************/
    
    @Override
    public DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoDTO>> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoQueryDTO dto) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductYktLimitBatchInfoByPage(dto);
    }
    
    @Override
    public DodopalResponse<ProductYktLimitBatchInfoDTO> findProductYktLimitBatchInfoById(String id) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.findProductYktLimitBatchInfoById(id);
    }
    
    @Override
    public DodopalResponse<List<ProductYktLimitBatchInfoDTO>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoQueryDTO dto) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.getProductYktLimitBatchListForExportExcel(dto);
    }
    
    @Override
    public DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.addProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
    }

    @Override
    public DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.deleteProductYktLimitBatchInfo(id);
    }
    
    @Override
    public DodopalResponse<Integer> saveProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.saveProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
    }

    @Override
    public DodopalResponse<Integer> auditProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.auditProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
    }

    @Override
    public DodopalResponse<Integer> checkProductYktLimitBatchInfo(ProductYktLimitBatchInfoDTO productYktLimitBatchInfoDTO) {
        ProductYktFacade facade =getFacade(ProductYktFacade.class,DelegateConstant.FACADE_PRODUCT_URL);
        return facade.checkProductYktLimitBatchInfo(productYktLimitBatchInfoDTO);
    }

    /**************************************************** 额度批次信息管理结束 *****************************************************/
}
