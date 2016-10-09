package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.PrdProductYktInfoForIcdcRecharge;
import com.dodopal.product.business.model.query.PrdProductYktQuery;

public interface PrdProductYktService {
    
    /**
     * 根据条件查询一卡通充值产品列表(分页)
     * @param merchant
     * @return
     */
    public DodopalDataPage<PrdProductYkt> findPrdProductYktByPage(PrdProductYktQuery productYktQuery);
    
    /**
     * 获取一卡通充值产品导出列表信息
     * @param merchant
     * @return
     */
    public DodopalResponse<List<PrdProductYkt>> getIcdcPrductListForExportExcel(PrdProductYktQuery productYktQuery);
    
    /**
     * 根据产品编号查询一卡通充值产品信息
     * @param proCode
     * @return
     */
    public PrdProductYkt findPrdProductYktByProCode(String proCode);
    
    /**
     * 新增一卡通充值产品
     * @param productYkt
     * @return
     */
    public int savePrdProductYkt(PrdProductYkt productYkt);
    
    /**
     * 检查一卡通充值产品是否存在
     * @param productYkt
     * @return
     */
    public boolean checkExist(PrdProductYkt productYkt);
    
    
    /**
     * 获取一卡通产品sequences
     * @return
     */
    public String getProCode();
    
    
    /**
     * 获取一卡通产品名称
     * @param cityId
     * @param proPrice
     * @return
     */
    public String getProName(String cityId,double proPrice);
    
    
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
    public int updatePrdProductYktStatus(String proStatus, List<String> proCodes, String updateUser);
    
    
    /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public List<PrdProductYkt> findAvailableIcdcProductsInCity(String cityId);
    
    
    /**
     * 基于城市查询公交卡充值产品(分页)
     * @param cityId
     * @return
     */
    public DodopalDataPage<PrdProductYkt> findAvailableIcdcProductsInCityByPage(PrdProductYktQuery productYktQuery);
    
    /**
     * 查询商户签约城市公交卡充值产品
     * @param merchantNum
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYkt>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId);
    
    /**
     * 查询商户签约城市公交卡充值产品
     * @param productYktQuery
     * @return
     */
    public DodopalDataPage<PrdProductYkt> findAvailableIcdcProductsForMerchantByPage(PrdProductYktQuery productYktQuery);
    
    /**
     * 7.3  生单接口调用:7.3.3    检验公交卡充值产品合法性
     * 
     * @param proCode
     * @return  如果验证通过，则返回000000。同时返回：一卡通代码、城市代码、公交卡最大允许金额。
     *          如果验证失败，请参考“功能描述”中的定义。
     *          155001:城市尚未启用，请联系客服人员。155002:该产品已下架，请重新选择产品。
     */
    public DodopalResponse<PrdProductYktInfoForIcdcRecharge> validateProductForIcdcRecharge(String proCode);
    
    /**
     *  根据城市获取该城市的自定义产品信息
     * @param cityId
     * @return
     */
    public DodopalResponse<PrdProductYktInfoForIcdcRecharge> getProductInfoByCityId(String cityId);
    
}
