package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.ProductYKTBDBean;
import com.dodopal.oss.business.bean.ProductYktLimitBatchInfoBean;
import com.dodopal.oss.business.bean.ProductYktLimitInfoBean;
import com.dodopal.oss.business.bean.query.ProductYKTBDFindBean;
import com.dodopal.oss.business.bean.query.ProductYktLimitBatchInfoFindBean;
import com.dodopal.oss.business.bean.query.ProductYktLimitInfoFindBean;
import com.dodopal.oss.business.model.BusinessCity;


public interface IcdcService {
    
    /**************************************************** 基础信息管理开始 *****************************************************/ 
    
    DodopalResponse<DodopalDataPage<ProductYKTBDBean>> findProductYktByPage(ProductYKTBDFindBean queryDto);
    
    DodopalResponse<ProductYKTBDBean> findProductYktById(String id);
    
    DodopalResponse<Integer>  startOrStopYkt(List<String> yktCode, String activate, String updateUser);
    
    DodopalResponse<Integer> saveOrUpdateYkt(ProductYKTBDBean productYKTBDBean);
    
    DodopalResponse<List<ProductYKTBDBean>> getProductYktListForExportExcel(ProductYKTBDFindBean queryDto);
    
    /**************************************************** 基础信息管理结束 *****************************************************/
    
    /**************************************************** 额度管理开始 *****************************************************/
    
    DodopalResponse<DodopalDataPage<ProductYktLimitInfoBean>> findProductYktLimitInfoByPage(ProductYktLimitInfoFindBean queryDto);
    
    DodopalResponse<ProductYktLimitInfoBean> findProductYktLimitInfoById(String id);
    
    DodopalResponse<Integer> saveProductYktLimitInfo(ProductYktLimitInfoBean productYktLimitInfoBean);
    
    DodopalResponse<List<ProductYktLimitInfoBean>> getProductYktLimitListForExportExcel(ProductYktLimitInfoFindBean queryDto);
    
    /**************************************************** 额度管理结束 *****************************************************/
    
    /**************************************************** 额度批次信息管理开始 *****************************************************/
    
    DodopalResponse<DodopalDataPage<ProductYktLimitBatchInfoBean>> findProductYktLimitBatchInfoByPage(ProductYktLimitBatchInfoFindBean queryDto);
    
    DodopalResponse<ProductYktLimitBatchInfoBean> findProductYktLimitBatchInfoById(String id);
    
    DodopalResponse<Integer> addProductYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean);
    
    DodopalResponse<Integer> deleteProductYktLimitBatchInfo(String id);
    
    DodopalResponse<Integer> saveProductYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean);
    
    DodopalResponse<Integer> aduitYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean);
    
    DodopalResponse<Integer> checkYktLimitBatchInfo(ProductYktLimitBatchInfoBean productYktLimitBatchInfoBean);
    
    DodopalResponse<List<ProductYktLimitBatchInfoBean>> getProductYktLimitBatchListForExportExcel(ProductYktLimitBatchInfoFindBean queryDto);
    
    /**************************************************** 额度批次信息管理结束 *****************************************************/

    
    /**
     * 通卡公司业务城市选项卡生成
     * @param param
     * @return
     */
    List<BusinessCity> getBusinessCity(String[] param);
    
    /**
     * 通卡公司业务城市选项卡生成
     * @param param
     * @return
     */
    List<BusinessCity> getBusinessCityByName(String param);
}
