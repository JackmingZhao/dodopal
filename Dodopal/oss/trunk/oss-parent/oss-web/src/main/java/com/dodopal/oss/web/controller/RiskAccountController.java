package com.dodopal.oss.web.controller;

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

import com.dodopal.api.account.dto.AccountControllerCustomerDTO;
import com.dodopal.api.account.dto.query.AccountControllerQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.service.AccountControllerService;

/**
 * 12.5 资金账户风控管理
 */
@Controller
@RequestMapping("/account")
public class RiskAccountController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);

    @Autowired
    private AccountControllerService accountControllerService;
    
    @Autowired
    private ExpTempletService expTempletService;

    /**
     * 初始页面
     * @param request
     * @return
     */
    @RequestMapping("/riskController/account")
    public ModelAndView adjustment(HttpServletRequest request) {
        return new ModelAndView("account/riskController/account");
    }

    /**
     * 资金账户风控查询
     */
    @RequestMapping("/riskController/findAccounts")
    public @ResponseBody DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> findAccounts(HttpServletRequest request, @RequestBody AccountControllerQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> response;
        try {
            return accountControllerService.findAccountRiskControllerItemListByPage(queryDTO);
        }
        catch (Exception e) {
            response = new DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>>();
            logger.error("资金账户风控查询时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 资金账户风控更新
     */
    @RequestMapping("/riskController/updateAccountRiskControllerItem")
    public @ResponseBody DodopalResponse<String> updateAccountRiskControllerItem(HttpServletRequest request, @RequestBody AccountControllerCustomerDTO acctController) {
        DodopalResponse<String> response;
        try {
            return accountControllerService.updateAccountRiskControllerItem(acctController, generateLoginUser(request));
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            logger.error("资金账户风控更新时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping("/riskController/exportExcelAccControl")
    public @ResponseBody DodopalResponse<String> exportExcelAccControl(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	AccountControllerQueryDTO queryDTO = new AccountControllerQueryDTO();
    	queryDTO.setCustName(req.getParameter("customerName"));
    	queryDTO.setCustNum(req.getParameter("customerNo"));
    	queryDTO.setCustomerType(req.getParameter("customerType"));
    	
    	int exportMaxNum = 5000;
    	queryDTO.setPage(new PageParameter(1, 1));	// 先用一页一条 的查询 查出总条数  数据小的话就不用调两次了  只调一次  
    	int totalNum = accountControllerService.findAccountRiskControllerItemListByPage(queryDTO).getResponseEntity().getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	queryDTO.setPage(new PageParameter(1, exportMaxNum));
    	DodopalResponse<DodopalDataPage<AccountControllerCustomerDTO>> entityList = accountControllerService.findAccountRiskControllerItemListByPage(queryDTO);
    	
    	String sheetName = new String("资金帐户风控");
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	resp = ExcelUtil.ddpExcelExport(req, res, entityList, col, sheetName);
    	
    	return resp;
    }
    
}
