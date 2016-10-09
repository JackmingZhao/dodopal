package com.dodopal.oss.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.oss.business.model.ClearingBank;
import com.dodopal.oss.business.model.ClearingBankTxn;
import com.dodopal.oss.business.model.ClearingMer;
import com.dodopal.oss.business.model.ClearingMerTxn;
import com.dodopal.oss.business.model.ClearingYkt;
import com.dodopal.oss.business.model.ClearingYktCity;
import com.dodopal.oss.business.model.dto.BankClearingResultQuery;
import com.dodopal.oss.business.model.dto.MerClearingResultQuery;
import com.dodopal.oss.business.model.dto.YktClearingResultQuery;
import com.dodopal.oss.business.service.ClearingResultService;

/**
 * 财务管理-清算管理-（银行、供应商（通卡）、商户）
 * @author
 */
@Controller
@RequestMapping("/finance/clearingResult")
public class ClearingResultController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(IcdcOrderController.class);

    @Autowired
    private ClearingResultService clearingResultService;
    @Autowired
    private ExpTempletService expTempletService;

    //***********************************************   银行清算管理 START    **************************************************//
    /**
     * 银行清算初始页面
     * @param request
     * @return
     */
    @RequestMapping("bank/init")
    public ModelAndView initBankClearingResult(HttpServletRequest request) {
        return new ModelAndView("finance/clearingResult/bankClearingResult");
    }

    /**
     * 银行清算分页查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("bank/findBankClearingResultByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ClearingBank>> findBankClearingResultByPage(HttpServletRequest request, @RequestBody BankClearingResultQuery queryDto) {
        DodopalResponse<DodopalDataPage<ClearingBank>> response = new DodopalResponse<DodopalDataPage<ClearingBank>>();
        try {
            DodopalDataPage<ClearingBank> result = clearingResultService.findBankClearingResultByPage(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询银行清算结果出错：" + e.getMessage(), e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 具体银行业务类型清算查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("bank/getBankTxnClearingResult")
    public @ResponseBody
    DodopalResponse<List<ClearingBankTxn>> getBankTxnClearingResult(HttpServletRequest request, @RequestBody BankClearingResultQuery queryDto) {
        DodopalResponse<List<ClearingBankTxn>> response = new DodopalResponse<List<ClearingBankTxn>>();
        try {
            List<ClearingBankTxn> result = clearingResultService.getBankTxnClearingResult(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询银行清算结果业务类型明细出错：" + e.getMessage(), e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 银行清算结果导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("bank/exportBankClearingResult")
    public @ResponseBody
    DodopalResponse<String> exportBankClearingResult(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = null;
        try {
            BankClearingResultQuery bankQuery = new BankClearingResultQuery();
            bankQuery.setClearingDayStart(request.getParameter("clearingDayStart"));
            bankQuery.setClearingDayEnd(request.getParameter("clearingDayEnd"));
            exportResponse = clearingResultService.excelExportBankClearingResult(response, bankQuery);
        }
        catch (DDPException ddpException) {
            logger.error("银行清算结果导出出错：" + ddpException.getMessage(), ddpException);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ddpException.getCode());
        }
        catch (Exception e) {
            logger.error("银行清算结果导出出错：" + e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }

    //***********************************************   银行清算管理 END    **************************************************//

    //***********************************************   供应商清算管理 START    ************************************************//
    /**
     * 供应商清算初始页面
     * @param request
     * @return
     */
    @RequestMapping("supplier/init")
    public ModelAndView initSupplierClearingResult(HttpServletRequest request) {
        return new ModelAndView("finance/clearingResult/yktClearingResult");
    }

    /**
     * 供应商清算查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("supplier/findYktClearingResultByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ClearingYkt>> findYktClearingResultByPage(HttpServletRequest request, @RequestBody YktClearingResultQuery queryDto) {
        DodopalResponse<DodopalDataPage<ClearingYkt>> response = new DodopalResponse<DodopalDataPage<ClearingYkt>>();
        try {
            DodopalDataPage<ClearingYkt> result = clearingResultService.findYktClearingResultByPage(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询供应商清算结果出错：" + e.getMessage(), e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 供应商清算城市查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("supplier/getYktCityClearingResult")
    public @ResponseBody
    DodopalResponse<List<ClearingYktCity>> getYktCityClearingResult(HttpServletRequest request, @RequestBody YktClearingResultQuery queryDto) {
        DodopalResponse<List<ClearingYktCity>> response = new DodopalResponse<List<ClearingYktCity>>();
        try {
            List<ClearingYktCity> result = clearingResultService.getYktCityClearingResult(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询供应商城市清算明细结果出错：" + e.getMessage(), e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 供应商清算结果导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("supplier/exportYktClearingResult")
    public @ResponseBody
    DodopalResponse<String> exportYktClearingResult(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = null;
        try {
            YktClearingResultQuery bankQuery = new YktClearingResultQuery();
            bankQuery.setClearingDayStart(request.getParameter("clearingDayStart"));
            bankQuery.setClearingDayEnd(request.getParameter("clearingDayEnd"));
            exportResponse = clearingResultService.excelExportYktClearingResult(response, bankQuery);
        }
        catch (DDPException ddpException) {
            logger.error("供应商清算结果导出出错：" + ddpException.getMessage(), ddpException);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ddpException.getCode());
        }
        catch (Exception e) {
            logger.error("供应商清算结果导出出错：" + e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }

    //***********************************************   供应商清算管理 END    ************************************************//

    //***********************************************   商户清算管理 START    **************************************************//
    /**
     * 商户清算初始页面
     * @param request
     * @return
     */
    @RequestMapping("merchant/init")
    public ModelAndView initMerchantClearingResult(HttpServletRequest request) {
        return new ModelAndView("finance/clearingResult/merClearingResult");
    }

    /**
     * 商户清算查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("merchant/findMerClearingResultByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<ClearingMer>> findMerClearingResultByPage(HttpServletRequest request, @RequestBody MerClearingResultQuery queryDto) {
        DodopalResponse<DodopalDataPage<ClearingMer>> response = new DodopalResponse<DodopalDataPage<ClearingMer>>();
        try {
            DodopalDataPage<ClearingMer> result = clearingResultService.findMerClearingResultByPage(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询商户清算出错：" + e.getMessage(), e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 商户清算业务类型查询
     * @param request
     * @param queryDto
     * @return
     */
    @RequestMapping("merchant/getMerTxnClearingResult")
    public @ResponseBody
    DodopalResponse<List<ClearingMerTxn>> getMerTxnClearingResult(HttpServletRequest request, @RequestBody MerClearingResultQuery queryDto) {
        DodopalResponse<List<ClearingMerTxn>> response = new DodopalResponse<List<ClearingMerTxn>>();
        try {
            List<ClearingMerTxn> result = clearingResultService.getMerTxnClearingResult(queryDto);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询商户清算业务类型明细出错：" + e.getMessage(), e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 商户清算结果导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("merchant/exportMerClearingResult")
    public @ResponseBody
    DodopalResponse<String> exportMerClearingResult(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> exportResponse = null;
        try {
            MerClearingResultQuery bankQuery = new MerClearingResultQuery();
            bankQuery.setClearingDayStart(request.getParameter("clearingDayStart"));
            bankQuery.setClearingDayEnd(request.getParameter("clearingDayEnd"));
            exportResponse = clearingResultService.excelExportMerClearingResult(response, bankQuery);
        }
        catch (DDPException ddpException) {
            logger.error("商户清算结果导出出错：" + ddpException.getMessage(), ddpException);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ddpException.getCode());
        }
        catch (Exception e) {
            logger.error("商户清算结果导出出错：" + e.getMessage(), e);
            exportResponse = new DodopalResponse<String>();
            exportResponse.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return exportResponse;
    }

    //***********************************************   商户清算管理 END    **************************************************//
    
    /**
     * 银行清算 Excel导出 BY 马天佐
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("bank/exportExcelInfo")
    public @ResponseBody DodopalResponse<String> exportExcelBankCleaning(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> ddpres = new DodopalResponse<String>();
    	
    	BankClearingResultQuery queryDto = new BankClearingResultQuery();
    	queryDto.setClearingDayStart(req.getParameter("clearingDayStart"));
    	queryDto.setClearingDayEnd(req.getParameter("clearingDayEnd"));
    	
    	int exportMaxNum = 5000;
    	queryDto.setPage(new PageParameter(1, 1));	// 先用一页一条 的查询 查出总条数  数据小的话就不用调两次了  只调一次  
    	int totalNum = clearingResultService.findBankClearingResultByPage(queryDto).getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
    		ddpres.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
    		return ddpres;
    	}
    	queryDto.setPage(new PageParameter(1, exportMaxNum));
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));

    	ddpres = clearingResultService.findBankClearingResultExportByPage(req, res, queryDto, col);
    	return ddpres;
    }
    
    /**
     * @author 马天佐
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("supplier/exportExcelInfo")
    public @ResponseBody DodopalResponse<String> exportExcelSupplierCleaning(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> ddpres = new DodopalResponse<String>();
    	
    	YktClearingResultQuery queryDto = new YktClearingResultQuery();
    	queryDto.setClearingDayStart(req.getParameter("clearingDayStart"));
    	queryDto.setClearingDayEnd(req.getParameter("clearingDayEnd"));

    	int exportMaxNum = 5000;
    	queryDto.setPage(new PageParameter(1, 1));	// 先用一页一条 的查询 查出总条数  数据小的话就不用调两次了  只调一次  
    	int totalNum = clearingResultService.findYktClearingResultByPage(queryDto).getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
    		ddpres.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
    		return ddpres;
    	}
    	queryDto.setPage(new PageParameter(1, exportMaxNum));
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	ddpres = clearingResultService.excelExportYktClearingResultExport(req, res, queryDto, col);
    	
    	return ddpres;
    }

    /**
     * @author Mikaelyan
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("merchant/exportExcelInfo")
    public @ResponseBody DodopalResponse<String> exportExcelMerCleaning(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> ddpres = new DodopalResponse<String>();
    	
    	MerClearingResultQuery queryDto = new MerClearingResultQuery();
    	queryDto.setClearingDayStart(req.getParameter("clearingDayStart"));
    	queryDto.setClearingDayEnd(req.getParameter("clearingDayEnd"));
    	
    	int exportMaxNum = 5000; 
    	queryDto.setPage(new PageParameter(1, 1));	// 先用一页一条 的查询 查出总条数  数据小的话就不用调两次了  只调一次  
    	int totalNum = clearingResultService.findMerClearingResultByPage(queryDto).getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
    		ddpres.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
    		return ddpres;
    	}
    	queryDto.setPage(new PageParameter(1, exportMaxNum));
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	ddpres = clearingResultService.excelExportMerClearingResultExport(req, res, queryDto, col);
    	
    	return ddpres;
    }
    

}
