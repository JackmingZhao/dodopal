package com.dodopal.oss.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.model.AccountAdjustment;
import com.dodopal.oss.business.model.CustomerAccount;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.AccountAdjustmentQuery;
import com.dodopal.oss.business.model.dto.CustomerAccountQuery;
import com.dodopal.oss.business.service.AccountAdjustmentService;

/**
 * 调账管理
 */
@Controller
@RequestMapping("/account")
public class AccountAdjustmentController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(AccountAdjustmentController.class);

    @Autowired
    private AccountAdjustmentService adjustmentService;
    @Autowired
    private ExpTempletService expTempletService;
    /**
     * 调账管理 初始页面
     * @param request
     * @return
     */
    @RequestMapping("/accountMgmt/adjustment")
    public ModelAndView adjustment(HttpServletRequest request) {
        return new ModelAndView("account/accountMgmt/adjustment");
    }

    /**
     * 10.1 调账申请
     */
    @RequestMapping("/accountMgmt/applyAccountAdjustment")
    public @ResponseBody DodopalResponse<String> applyAccountAdjustment(HttpServletRequest request, @RequestBody AccountAdjustment adjustment) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = generateLoginUser(request);
            adjustment.setAccountAdjustApplyUser(user.getId());
            adjustment.setCreateUser(user.getId());
            response = adjustmentService.applyAccountAdjustment(adjustment);
        }
        catch (Exception e) {
            logger.error("调账申请时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 10.2 调账执行（批量执行）
     */
    @RequestMapping("/accountMgmt/approveAccountAdjustment")
    public @ResponseBody DodopalResponse<Boolean> approveAccountAdjustment(HttpServletRequest request, @RequestBody AccountAdjustment[] adjustment) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            User user = generateLoginUser(request);
            response = adjustmentService.approveAccountAdjustment(adjustment, user);
        }
        catch (HessianRuntimeException e) {
            logger.error("调账执行（批量执行）时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.ACC_SYSTEM_ERROR);
        }
        catch (Exception e) {
            logger.error("调账执行（批量执行）时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    /**
     * 调账拒绝（批量拒绝）
     * @param request
     * @param adjustment
     * @return
     */
    @RequestMapping("/accountMgmt/rejectAccountAdjustment")
    public @ResponseBody DodopalResponse<Boolean> rejectAccountAdjustment(HttpServletRequest request, @RequestBody AccountAdjustment[] adjustment) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            User user = generateLoginUser(request);
            response = adjustmentService.rejectAccountAdjustment(adjustment, user);
        }
        catch (Exception e) {
            logger.error("调账拒绝时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 10.3 调账查询
     */
    @RequestMapping("/accountMgmt/findAdjustments")
    public @ResponseBody DodopalResponse<DodopalDataPage<AccountAdjustment>> findAdjustments(HttpServletRequest request, @RequestBody AccountAdjustmentQuery query) {
        DodopalResponse<DodopalDataPage<AccountAdjustment>> response = new DodopalResponse<DodopalDataPage<AccountAdjustment>>();
        try {
            DodopalDataPage<AccountAdjustment> adjustments = adjustmentService.findAccountAdjustmentByPage(query);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(adjustments);
        }
        catch (Exception e) {
            logger.error("调账查询时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 10.3 调账查询 查询调账单详情
     */
    @RequestMapping("/accountMgmt/viewAccountAdjustment")
    public @ResponseBody DodopalResponse<AccountAdjustment> viewAccountAdjustment(HttpServletRequest request, @RequestBody String adjustmentId) {
        DodopalResponse<AccountAdjustment> response = new DodopalResponse<AccountAdjustment>();
        try {
            AccountAdjustment result = adjustmentService.findAccountAdjustment(adjustmentId);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(result);
        }
        catch (Exception e) {
            logger.error("查询调账单详情时发生错误:"+e.getMessage(), e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 10.4 调账修改 授权用户在调账管理主界面上，选择一条记录，点击“修改”按钮，可以触发该操作。 1.
     * 检查该申请单的状态是否为：未审批、审核不通过，否则提示：此状态不能进行修改。 2. 进入申请单详细信息页面，只能修改以下字段：调账金额、调账原因。
     */
    @RequestMapping("/accountMgmt/updatetAccountAdjustments")
    public @ResponseBody DodopalResponse<String> updatetAccountAdjustments(HttpServletRequest request, @RequestBody AccountAdjustment adjustment) {
        DodopalResponse<String> response;
        try {
            User user = generateLoginUser(request);
            adjustment.setUpdateUser(user.getId());
            response = adjustmentService.updatetAccountAdjustments(adjustment);
        }
        catch (Exception e) {
            logger.error("调账修改时发生错误:"+e.getMessage(), e);
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * 10.5 调账删除
     */
    @RequestMapping("/accountMgmt/deleteAccountAdjustment")
    public @ResponseBody DodopalResponse<String> deleteAccountAdjustment(HttpServletRequest request, @RequestBody String[] adjustmentId) {
        DodopalResponse<String> response;
        try {
            response = adjustmentService.deleteAccountAdjustment(adjustmentId);
        }
        catch (Exception e) {
            logger.error("调账删除时发生错误:"+e.getMessage(), e);
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    @RequestMapping("accountMgmt/findCustomerAccount")
    public @ResponseBody DodopalResponse<DodopalDataPage<CustomerAccount>> findCustomerAccount(HttpServletRequest request, @RequestBody CustomerAccountQuery customerAccountQuery){  
        DodopalResponse<DodopalDataPage<CustomerAccount>> response;
        try {
            response= adjustmentService.findCustomerAccount(customerAccountQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response = new DodopalResponse<DodopalDataPage<CustomerAccount>>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    @RequestMapping("accountMgmt/findFundAccountInfo")
    public @ResponseBody DodopalResponse<FundAccountInfoDTO> findFundAccountInfo(HttpServletRequest request, @RequestBody CustomerAccountQuery customerAccountQuery){  
        DodopalResponse<FundAccountInfoDTO> response;
        try {
            response= adjustmentService.findFundAccountInfo(customerAccountQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
            response = new DodopalResponse<FundAccountInfoDTO>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    @RequestMapping("accountMgmt/exportExcelAdjustment")
    public @ResponseBody DodopalResponse<String> exportExcelAdjustment(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> ddpres = new DodopalResponse<String>();
    	
    	AccountAdjustmentQuery aaq = new AccountAdjustmentQuery();
    	aaq.setCustomerNo(req.getParameter("customerNo"));
    	aaq.setCustomerName(req.getParameter("customerName"));

    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		String accountAdjustApplyStartDate = req.getParameter("accountAdjustApplyStartDate");
    		if(accountAdjustApplyStartDate != null  && !"".equals(accountAdjustApplyStartDate)) {
    			aaq.setAccountAdjustApplyStartDate(sdf.parse(accountAdjustApplyStartDate));
    		}
    		String accountAdjustApplyEndDate = req.getParameter("accountAdjustApplyEndDate");
    		if( !"".equals(accountAdjustApplyEndDate) && null != accountAdjustApplyEndDate) {
    			aaq.setAccountAdjustApplyEndDate(sdf.parse(accountAdjustApplyEndDate));
    		}
    		String accountAdjustAuditStartDate = req.getParameter("accountAdjustAuditStartDate");
    		if( !"".equals(accountAdjustAuditStartDate) && null != accountAdjustAuditStartDate) {
    			aaq.setAccountAdjustAuditStartDate(sdf.parse(accountAdjustAuditStartDate));
    		}
    		String accountAdjustAuditEndDate = req.getParameter("accountAdjustAuditEndDate");
    		if( !"".equals(accountAdjustAuditEndDate) && null != accountAdjustAuditEndDate) {
    			aaq.setAccountAdjustAuditEndDate(sdf.parse(accountAdjustAuditEndDate));
    		}
		} catch (ParseException e) {
			logger.error("Excel导出错误: 页面上_传过来的_日期字符串转化错误~!");
			e.printStackTrace();
		}
    	aaq.setAccountAdjustType(req.getParameter("accountAdjustType"));
    	aaq.setFundType(req.getParameter("fundType"));
    	aaq.setAccountAdjustState(req.getParameter("accountAdjustState"));
    	aaq.setAccountAdjustAuditUser(req.getParameter("accountAdjustAuditUser"));
    	aaq.setAccountAdjustApplyUser(req.getParameter("accountAdjustApplyUser"));
    	int exportMaxNum = 5000;
    	aaq.setPage(new PageParameter(1, 1));
    	int totalNum = adjustmentService.findAccountAdjustmentByPage(aaq).getRows();
    	if(totalNum > exportMaxNum) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + totalNum);
    		ddpres.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return ddpres;
    	}
    	aaq.setPage(new PageParameter(1, 5000));
    	List<AccountAdjustment> entityList = adjustmentService.findAccountAdjustmentByPage(aaq).getRecords();
    	
    	String sheetName = new String("调帐管理");
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req, res, entityList, col, sheetName);
    	ddpres.setCode(resultCode);
    	return ddpres;
    }
    
    
}
