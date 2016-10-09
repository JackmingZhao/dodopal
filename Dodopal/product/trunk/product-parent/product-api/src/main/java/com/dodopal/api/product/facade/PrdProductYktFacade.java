package com.dodopal.api.product.facade;

import java.util.List;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface PrdProductYktFacade {
    
    /**
     * 查询一卡通充值产品信息（分页）
     * @param productYktQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findPrdProductYktByPage(PrdProductYktQueryDTO productYktQueryDTO);
    
    /**
     * 获取一卡通充值产品导出信息
     * @param productYktQueryDTO
     * @return
     */
    public DodopalResponse<List<PrdProductYktDTO>> getIcdcPrductListForExportExcel(PrdProductYktQueryDTO productYktQueryDTO);
    
    /**
     * 根据产品编号查询一卡通充值产品信息
     * @param proCode
     * @return
     */
    public DodopalResponse<PrdProductYktDTO> findPrdProductYktByProCode(String proCode);
    
    /**
     * 新增一卡通充值产品
     * @param productYktDTO
     * @return
     */
    public DodopalResponse<Integer> addPrdProductYkt(PrdProductYktDTO productYktDTO);
    
    
    /**
     * 检查一卡通充值产品是否存在
     * @param yktCode
     * @param cityId
     * @param proPrice
     * @return
     */
    public DodopalResponse<Boolean> checkPrdProductYktExist(String yktCode, String cityId, int proPrice);
    
    
    /**
     * 修改一卡通充值产品
     * @param productYktDTO
     * @return
     */
    public DodopalResponse<Integer> updatePrdProductYkt(PrdProductYktDTO productYktDTO);
    
    
    
    /**
     * 批量上/下架一卡通充值产品
     * @param proStatus
     * @param proCodes
     * @param updateUser
     * @return
     */
    public DodopalResponse<Integer> updatePrdProductYktStatus(String proStatus, List<String> proCodes, String updateUser);
    
    
    /**
     * 基于城市查询公交卡充值产品
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsInCity(String cityId);
    
    /**
     * 基于城市查询公交卡充值产品(分页)
     * @param cityId
     * @return
     */
    public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findAvailableIcdcProductsInCityByPage(PrdProductYktQueryDTO productYktQueryDTO);
    
    /**
     * 查询商户签约城市公交卡充值产品
     * @param merchantNum
     * @param cityId
     * @return
     */
    public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsForMerchant(String merchantNum, String cityId);
    
    /**
     * 查询商户签约城市公交卡充值产品(分页)
     * @param productYktQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findAvailableIcdcProductsForMerchantByPage(PrdProductYktQueryDTO productYktQueryDTO);
}
