package com.dodopal.product.business.service;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.model.query.ProductYKTQuery;

public interface ProductYKTService {
    
    /**
     * 一卡通充值__检验通卡合法性（通卡公司的停启用状态、充值业务的停启用状态、充值限制时间段）
     * 
     * @param 通卡code
     * @return
     */
    public DodopalResponse<ProductYKT> validateYktServiceNormalForIcdcRecharge(String yktCode);
    
    /**
     * 一卡通消费__检验通卡合法性（通卡公司的停启用状态、消费业务的停启用状态、消费限制时间段）
     * 
     * @param 通卡code
     * @return
     */
    public DodopalResponse<ProductYKT> validateYktServiceNormalForIcdcConsume(String yktCode);
    
    /**
     * 查询通卡公司(分页)
     * 
     * @param ProductYKTQuery 查询条件
     * @return
     */
    public DodopalDataPage<ProductYKT> findProductYktByPage(ProductYKTQuery query);
    
    /**
     * 获取通卡公司导出信息
     * 
     * @param ProductYKTQuery 查询条件
     * @return
     */
    public DodopalResponse<List<ProductYKT>> getProductYktListForExportExcel(ProductYKTQuery query);
    
    /**
     * 查询通卡公司详细信息
     * 
     * @param yktId 通卡公司ID
     * @return
     */
    public ProductYKT findProductYktById(String id);
	
    /**
     * 停启用通卡公司
     * 
     * @param activate 启用/停用状态
     * @param yktCodes 一卡通代码
     * @param updateUser 更新者
     * @return
     */
    public int batchUpdateYktActivate(String activate, List<String> yktCodes, String updateUser);
    
    /**
     * 新增通卡公司
     * 
     * @param ProductYKT 通卡公司
     * @return
     */
    public int addProductYkt(ProductYKT productYKT);
    
    /**
     * 更新通卡公司
     * 
     * @param ProductYKTDTO 通卡公司
     * @return
     */
    public int updateProductYkt(ProductYKT productYKT);
    
    /**
     * 检查通卡公司名称是否已存在
     * 
     * @param productYKT
     * @return
     */
    public boolean checkYktNameExsit(ProductYKT productYKT);
    
    /**
     * 检查通卡公司CODE是否已存在
     * 
     * @param productYKT
     * @return
     */
    public boolean checkYktCodeExsit(ProductYKT productYKT);
    
    /**
     * 保存通卡公司信息时，检查其业务城市是否已经被占用
     * 
     * @param yktCode
     * @param cityIds
     * @return
     */
    public boolean checkYktBusinessCityExsit(String yktCode, String[] cityIds);
    
    /**
     * 获取充通卡公司信息
     * 
     * @return
     */
    public List<ProductYKT> getIsRechargeYktMap(String type);
    
    /**
     * 根据通卡公司code获取其业务城市信息
     * 
     * @param yktCode
     * @return
     */
    public List<ProductYKT> getBusinessCityByYktCode(String yktCode);
    
    /**
     * 获取通卡公司的支付、充值费率信息
     * 
     * @return
     */
    public List<ProductYKT> getAllYktBusinessRateList();
    
    /**
     * 保存开通支付业务的通卡公司信息时调用支付交易接口
     * 
     * @param list
     * @return
     */
    public DodopalResponse<Boolean> icdcPayCreate(List<Map<String,Object>> list);
    
    /**
     * 由业务城市code获取其通卡公司信息
     * 
     * @return
     */
    public ProductYKT getYktInfoByBusinessCityCode(String cityCode);
    
    
    /**
     * @param cityCode
     * @param proversion
     * @return true 版本号变更
     */
    public boolean CheckProversionChange(String cityCode,String proversion);
    
    /**
     * @param cityCode 根据
     * @return
     */
    public String getProversionByCityCode(String cityCode); 
}
