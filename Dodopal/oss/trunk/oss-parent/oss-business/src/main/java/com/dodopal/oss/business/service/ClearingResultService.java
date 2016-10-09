package com.dodopal.oss.business.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.ClearingBank;
import com.dodopal.oss.business.model.ClearingBankTxn;
import com.dodopal.oss.business.model.ClearingMer;
import com.dodopal.oss.business.model.ClearingMerTxn;
import com.dodopal.oss.business.model.ClearingYkt;
import com.dodopal.oss.business.model.ClearingYktCity;
import com.dodopal.oss.business.model.dto.BankClearingResultQuery;
import com.dodopal.oss.business.model.dto.MerClearingResultQuery;
import com.dodopal.oss.business.model.dto.YktClearingResultQuery;

/**
 * 账户管理——清算管理（银行，供应商（通卡），商户） service
 * @author dodopal
 *
 */
public interface ClearingResultService {
    
    //***********************************************   银行清算管理 START    **************************************************//
    
    /**
     * 银行清算分页查询
     * @param queryDto
     * @return
     */
    public DodopalDataPage<ClearingBank> findBankClearingResultByPage(BankClearingResultQuery queryDto);
    
    /**
     * 具体银行业务类型清算查询
     * @param queryDto
     * @return
     */
    public List<ClearingBankTxn> getBankTxnClearingResult(BankClearingResultQuery queryDto);
    
    /**
     * 银行清算结果导出
     * @param response
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<String> excelExportBankClearingResult(HttpServletResponse response, BankClearingResultQuery prdOrderQuery);
    
    //***********************************************   银行清算管理 END    **************************************************//
    
    //***********************************************   供应商清算管理 START    **************************************************//
    
    /**
     * 供应商（通卡）清算分页查询
     * @param queryDto
     * @return
     */
    public DodopalDataPage<ClearingYkt> findYktClearingResultByPage(YktClearingResultQuery queryDto);
    
    /**
     * 具体供应商（通卡）业务城市清算查询
     * @param queryDto
     * @return
     */
    public List<ClearingYktCity> getYktCityClearingResult(YktClearingResultQuery queryDto);
    
    /**
     * 供应商（通卡）清算结果导出
     * @param response
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<String> excelExportYktClearingResult(HttpServletResponse response, YktClearingResultQuery prdOrderQuery);
    
    //***********************************************   供应商清算管理 END    **************************************************//
    
    //***********************************************   商户清算管理 START    **************************************************//
    
    /**
     * 商户清算分页查询
     * @param queryDto
     * @return
     */
    public DodopalDataPage<ClearingMer> findMerClearingResultByPage(MerClearingResultQuery queryDto);
    
    /**
     * 具体商户业务类型清算查询
     * @param queryDto
     * @return
     */
    public List<ClearingMerTxn> getMerTxnClearingResult(MerClearingResultQuery queryDto);

    /**
     * 商户清算结果导出
     * @param response
     * @param prdOrderQuery
     * @return
     */
    public DodopalResponse<String> excelExportMerClearingResult(HttpServletResponse response, MerClearingResultQuery prdOrderQuery);
    
    //***********************************************   商户清算管理 END    **************************************************//
    
    
    // ********************** excel 导出 ************************************* //
    
    /**
     * 银行清算导出
     * @author matianzuo 
     * @param req
     * @param response
     * @param prdOrderQuery
     * @param col
     * @return
     */
    public DodopalResponse<String> findBankClearingResultExportByPage(HttpServletRequest req, HttpServletResponse response, BankClearingResultQuery prdOrderQuery, Map<String, String> col);
    
    /**
     * 供应商清算导出
     * @author matianzuo
     * @param req
     * @param response
     * @param queryDto
     * @param col
     * @return
     */
    public DodopalResponse<String> excelExportYktClearingResultExport(HttpServletRequest req, HttpServletResponse response, YktClearingResultQuery queryDto, Map<String, String> col);
    
    /**
     * 商户清算导出
     * @author matianzuo 
     * @param req
     * @param response
     * @param queryDto
     * @param col
     * @return
     */
    public DodopalResponse<String> excelExportMerClearingResultExport(HttpServletRequest req, HttpServletResponse response, MerClearingResultQuery queryDto, Map<String, String> col);
}
