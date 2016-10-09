package com.dodopal.oss.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.MerchantUserBean;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.bean.query.PayConfigQuery;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.MerchantUserQuery;
import com.dodopal.oss.business.service.MerUserService;
import com.dodopal.oss.business.service.PayConfigService;
import com.dodopal.oss.business.service.PaymentService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月30日 上午11:53:05
 * 支付方式配置
 */
@Controller
@RequestMapping("/pay/payConfig")
public class PayConfigController extends CommonController{
    @Autowired
    PayConfigService payConfigService;
    @Autowired
    private ExpTempletService expTempletService;
    
    private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);
    
    @RequestMapping("payConfigList")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("payment/payConfig/payConfig");
    }
    
    @RequestMapping("findPayConfigByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<PayConfigBean>> findPayConfigByPage(HttpServletRequest request,@RequestBody PayConfigQuery payConfigQuery) {
         DodopalResponse<DodopalDataPage<PayConfigBean>> response = new DodopalResponse<DodopalDataPage<PayConfigBean>>();
        try {
            response = payConfigService.findPayConfigByPage(payConfigQuery);
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    } 
    
    @RequestMapping("viewPayConfig")
    public @ResponseBody DodopalResponse<PayConfigBean> viewPayConfig(HttpServletRequest request,@RequestBody String id) {
         DodopalResponse<PayConfigBean> response = new DodopalResponse<PayConfigBean>();
        try {
            response = payConfigService.findPayConfigById(id);
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    } 
    
    @RequestMapping("editPayConfig")
    public @ResponseBody DodopalResponse<Boolean> editPayConfig(HttpServletRequest request,@RequestBody PayConfigBean bean) {
         DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            HttpSession session = request.getSession();
            User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
            bean.setUpdateUser(user.getId());
            response = payConfigService.updatePayConfig(bean);
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    } 
    
    @RequestMapping("startPayConfig")
    public @ResponseBody DodopalResponse<Boolean> startPayConfig(HttpServletRequest request,@RequestBody String[] ids) {
         DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
         List <String> idList = Arrays.asList(ids);
        try {
            HttpSession session = request.getSession();
            User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
            response = payConfigService.batchActivatePayConfig(ActivateEnum.ENABLE.getCode(), idList, user.getId());
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    } 
    
    @RequestMapping("stopPayConfig")
    public @ResponseBody DodopalResponse<Boolean> stopPayConfig(HttpServletRequest request,@RequestBody String[] ids) {
         DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
         List <String> idList = Arrays.asList(ids);
        try {
            HttpSession session = request.getSession();
            User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
            response = payConfigService.batchActivatePayConfig(ActivateEnum.DISABLE.getCode(), idList, user.getId());
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    } 
    @RequestMapping("changeGateway")
    public @ResponseBody DodopalResponse<Boolean> changeGateway(HttpServletRequest request,@RequestBody Map <String,Object>map) {
         DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
         List idsList =  (List) map.get("ids");
         String payConfigId = (String) map.get("GatewayTypeId");
         String payId = DodopalAppVarPropsUtil.getStringProp(BankGatewayTypeEnum.getBankGatewayTypeByCode(payConfigId).toString());
        try {
            HttpSession session = request.getSession();
            User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
            response = payConfigService.updatePayConfigBankGateway(idsList, user.getId(), payId,BankGatewayTypeEnum.getBankGatewayTypeByCode(payConfigId));
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    } 
    
    @RequestMapping("exportExcelPayConfig")
    public @ResponseBody DodopalResponse<String> exportExcelPayConfig(HttpServletRequest req, HttpServletResponse res) {
    	DodopalResponse<String> resp = new DodopalResponse<String>();
    	
    	PayConfigQuery queryDTO = new PayConfigQuery();
    	 
    	String payTypeQuery = req.getParameter("payTypeQuery");
    	String payNameQuery = req.getParameter("payNameQuery");
    	String activateQuery = req.getParameter("activateQuery");
    	String afterProceRateDateStart = req.getParameter("afterProceRateDateStart");
    	String afterProceRateDateEnd = req.getParameter("afterProceRateDateEnd");
    	
    	try {
    		if(payTypeQuery!=null && payTypeQuery!=""){
    			queryDTO.setPayType(payTypeQuery);
    		}
    		if(payNameQuery!=null && payNameQuery!=""){
    			queryDTO.setPayWayName(payNameQuery);
    		}
    		if(activateQuery!=null && activateQuery!=""){
    			queryDTO.setActivate(activateQuery);
    		}
    		if(afterProceRateDateStart!=null && afterProceRateDateStart!=""){
    			queryDTO.setAfterProceRateDateStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(afterProceRateDateStart));
    		}
    		if(afterProceRateDateEnd!=null && afterProceRateDateEnd!=""){
    			queryDTO.setAfterProceRateDateEnd(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(afterProceRateDateEnd));
    		}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	int exportMaxNum = 5000;
    	queryDTO.setPage(new PageParameter(1, exportMaxNum));	// MikaelyanMikaelyanMikaelyanMikaelyan 1000
    	DodopalResponse<DodopalDataPage<PayConfigBean>> response = payConfigService.findPayConfigByPage(queryDTO);
    	List<PayConfigBean> list = response.getResponseEntity().getRecords();
    	int resultSize = list.size();
    	if(resultSize > 5000) {
    		logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
			resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
			return resp;
    	}
    	String sheetName = new String("支付方式配置");
    	
    	Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
    	String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
    	
		resp.setCode(resultCode);
    	
    	return resp;
    }
}
