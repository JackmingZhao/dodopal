package com.dodopal.product.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.api.card.dto.TerminalParameter;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.query.PrdProductYktQuery;


public interface PrdProductYktMapper {

    /** 
     * 查找一卡通产品列表
     * @param productYkt
     * @return 
     */
    public List<PrdProductYkt> findPrdProductYkt(PrdProductYkt productYkt);
    
    
    /**
     * 检查一卡通产品是否存在
     * @param productYkt
     * @return
     */
    public boolean checkExist(PrdProductYkt productYkt);
    
    /**
     * 获取一卡通产品sequences
     * @return
     */
    public String getProCodeSeq();
    
    
    /**
     * 获取一卡通业务城市名称
     * @return
     */
    public String getCityName(String cityId);
    
    /**
     * 新增一卡通充值产品
     * @param productYkt
     * @return
     */
    public int insertPrdProductYkt(PrdProductYkt productYkt);
    
    
    /**
     * 修改一卡通充值产品
     * @param productYkt
     * @return
     */
    public int updatePrdProductYkt(PrdProductYkt productYkt);
    
    
    
    /**
     * 批量上/下架一卡通充值产品
     * @param proStatus
     * @param proCodes
     * @param updateUser
     * @return
     */
    public int updatePrdProductYktStatus(@Param("proStatus") String proStatus, @Param("proCodes") List<String> proCodes, @Param("updateUser") String updateUser);
        
    
    /**
     * 一卡通充值产品查询
     * @param productYktQuery
     * @return
     */
    public List<PrdProductYkt> findPrdProductYktListPage(PrdProductYktQuery productYktQuery);
    
    /**
     * 获取一卡通产品导出结果个数
     * @param productYktQuery
     * @return
     */
    public int getCountForIcdcPrductExportExcel(PrdProductYktQuery productYktQuery);
    
    /**
     * 获取一卡通产品导出结果集
     * @param productYktQuery
     * @return
     */
    public List<PrdProductYkt> getListForIcdcPrductExportExcel(PrdProductYktQuery productYktQuery);
    
    /**
     * 根据产品编号查询一卡通充值产品信息
     * @param proCode
     * @return
     */
    public PrdProductYkt findPrdProductYktByProCode(String proCode);
    
    
    /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public List<PrdProductYkt> findAvailableIcdcProductsInCity(@Param("cityId")String cityId);
    
    /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public List<PrdProductYkt> getAvailableProductsByCity(@Param("cityId")String cityId);
    
    /**
     * 基于城市查询公交卡充值产品(分页)
     * @param cityId
     * @return
     */
    public List<PrdProductYkt> findAvailableIcdcProductsInCityByPage(PrdProductYktQuery productYktQuery);
    
    /**
     * 查询商户签约城市公交卡充值产品
     * @param merchantNum
     * @param cityId
     * @return
     */
    public List<PrdProductYkt> findAvailableIcdcProductsForMerchant(@Param("merchantNum")String merchantNum, @Param("cityId")String cityId, @Param("yktCode")String ciyktCodetyId);
    
    /**
     * 查询商户签约城市公交卡充值产品(分页)
     * @param productYktQuery
     * @return
     */
    public List<PrdProductYkt> findAvailableIcdcProductsForMerchantByPage(PrdProductYktQuery productYktQuery);
    
    /**
     * 检验公交卡充值产品合法性:
     * 
     * @param proCode
     * @return
     */
    public PrdProductYktInfoForIcdcRecharge getProductInfoForIcdcRecharge(String proCode);
    
    
    /**
     * 根据城市ID获取当前城市的自定义产品信息:
     * 
     * @param proCode
     * @return
     */
    public PrdProductYktInfoForIcdcRecharge getProductInfoByCityId(String cityId);
    
    /**
     * 获取机具参数
     * @param psamNO
     * @return
     */
    public List<TerminalParameter> findTerminalParameter(String psamno);
    
    
}
