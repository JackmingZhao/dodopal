package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingBasic;
import com.dodopal.oss.business.model.ClearingBasicDataDTO;
import com.dodopal.oss.business.model.dto.ClearingInfoQuery;

public interface ClearingMapper {
    public List<ClearingBasic> findBankClearingByPage(ClearingInfoQuery queryDto);
    
    public List<ClearingBasic> exportBankClearing(ClearingInfoQuery queryDto);

    public List<ClearingBasic> findYktClearingByPage(ClearingInfoQuery queryDto);
    
    public List<ClearingBasic> exportYktClearing(ClearingInfoQuery queryDto);

    public List<ClearingBasic> findMerClearingByPage(ClearingInfoQuery queryDto);
    
    public List<ClearingBasic> exportMerClearing(ClearingInfoQuery queryDto);
    
    
    /***************************************      2015年12月24日 11:47:04     ********************************************************************/
    /**
     * 银行清分总数
     * @param queryDto
     * @return
     */
    public int findBankClearingCount(ClearingInfoQuery queryDto);
    
    /**
     * 银行清分Excel导出
     * @param response
     * @param prdOrderQuery
     * @return
     */
    public List<ClearingBasicDataDTO> excelExportBankClearing(ClearingInfoQuery bankQuery);
    
    
    /**
     * 通卡清分总数
     * @param queryDto
     * @return
     */
    public int findYktClearingCount(ClearingInfoQuery queryDto);
    
    /**
     * 通卡清分Excel导出
     * @param queryDto
     * @return
     */
    public List<ClearingBasicDataDTO> excelExportYktClearing(ClearingInfoQuery queryDto);
    
    
    /**
     * 商户清分总数
     * @param response
     * @param bankQuery
     * @return
     */
    public int findMerClearingCount(ClearingInfoQuery bankQuery);
    /**
     * 商户清分Excel导出
     * @param response
     * @param bankQuery
     * @return
     */
    public List<ClearingBasicDataDTO> excelExportMerClearing(ClearingInfoQuery bankQuery);
}
