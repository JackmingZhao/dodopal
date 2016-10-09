package com.dodopal.oss.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.ClearingBasic;
import com.dodopal.oss.business.model.ClearingBasicDataDTO;
import com.dodopal.oss.business.model.dto.ClearingInfoQuery;

public interface ClearingService {

    /**
     * 银行清分分页查询
     * @param queryDto
     * @return
     */
    public DodopalDataPage<ClearingBasic> findBankClearingByPage(ClearingInfoQuery queryDto);
    
    /**
     * 银行清分导出
     * @param response
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<String> excelExportBank(HttpServletResponse response, ClearingInfoQuery bankQuery);
    
    /**
     * 通卡清分分页查询
     * @param queryDto
     * @return
     */
    public DodopalDataPage<ClearingBasic> findYktClearingByPage(ClearingInfoQuery queryDto);
    
    /**
     * 通卡清分导出
     * @param response
     * @param bankQuery
     * @return
     */
    public DodopalResponse<String> excelExportYkt(HttpServletResponse response, ClearingInfoQuery bankQuery);

    /**
     * 商户清分分页查询
     * @param queryDto
     * @return
     */
    public DodopalDataPage<ClearingBasic> findMerClearingByPage(ClearingInfoQuery queryDto);
    
    /**
     * 商户清分导出
     * @param response
     * @param bankQuery
     * @return
     */
    public DodopalResponse<String> excelExportMer(HttpServletResponse response, ClearingInfoQuery bankQuery);

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
    public List<ClearingBasicDataDTO> excelExportBank(ClearingInfoQuery bankQuery);
    
    
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
    public List<ClearingBasicDataDTO> excelExportYkt(ClearingInfoQuery queryDto);
    
    
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
    public List<ClearingBasicDataDTO> excelExportMer(ClearingInfoQuery bankQuery);
}
