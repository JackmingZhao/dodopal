package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.AccountChange;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.model.query.FundChangeQuery;
import com.dodopal.portal.business.service.AccountChangeService;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.MerchantService;

@Controller
@RequestMapping("/ddp")
public class AccountChangeController extends CommonController{

	private final static Logger log = LoggerFactory.getLogger(AccountChangeController.class);
	
	@Autowired
	private AccountChangeService accountChangeService;
	@Autowired
    MerchantService merchantService;
	@Autowired
    AccountSetService accountSetService;
	
	@RequestMapping("/showAccountChange")
    public ModelAndView showAccountChange(Model model,HttpServletRequest request) {
        return new ModelAndView("ddp/accountChange");
    }
	
	//资金变更记录
	@RequestMapping("/find")
	public @ResponseBody DodopalResponse<DodopalDataPage<AccountChange>> findFundsChangeRecordsByPage(HttpServletRequest request,@RequestBody FundChangeQuery query){
		DodopalResponse<DodopalDataPage<AccountChange>> response = new DodopalResponse<DodopalDataPage<AccountChange>>();
		String id = getCurrentAcId(request.getSession());
		String createUser = "";
		String userCode = "";
		String merUserFlag = "";
		String merType = getMerType(request.getSession());
		try { 
			if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
                userCode = getCurrentUserCode(request.getSession());
            }else {
                userCode = getCurrentMerchantCode(request.getSession());
                DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
                merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
            }
			//判断用户是否为操作员
			if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
				createUser = getCurrentUserId(request.getSession());
			}
			query.setAcid(id);
			query.setCreateUser(createUser);
			response = accountChangeService.findFundsChangeRecordsByPage(query);
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	
	 //导出
    @RequestMapping("/ddpAmtExport")
    public @ResponseBody DodopalResponse<String>  exportTranRecordOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        FundChangeQueryDTO queryDTO = new FundChangeQueryDTO();
        
        String merType = getMerType(request.getSession());
        String userCode = "";
        String createUser = "";
        String merUserFlag = "";
        if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
            userCode = getCurrentUserCode(request.getSession());
        }else {
            userCode = getCurrentMerchantCode(request.getSession());
            DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
            merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
        }
        //判断用户是否为操作员
        if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
        	createUser = getCurrentUserId(request.getSession());
        }
        
        String id = getCurrentAcId(request.getSession());
        //变动类型
        String changeType = request.getParameter("changeType");
        
        //最小交易金额范围
        String changeAmountMin = request.getParameter("changeAmountMin");
        
       //最大交易金额范围
        String changeAmountMax = request.getParameter("changeAmountMax");

        //开始时间
        String startDate = request.getParameter("startDate");
        
        //结束时间
        String endDate = request.getParameter("endDate");
        
        try {
    		if(id != null && id !=  ""){
    			queryDTO.setAcid(id);
    		}
        	//最小交易金额范围
    		if(changeAmountMin != null && changeAmountMin !=  ""){
    			queryDTO.setChangeAmountMin(new Double(Double.parseDouble(changeAmountMin)*100).longValue());
    		}
    		//最大交易金额范围
    		if(changeAmountMax != null && changeAmountMax !=  ""){
    			queryDTO.setChangeAmountMax(new Double(Double.parseDouble(changeAmountMax)*100).longValue());
    		}
    		//开始时间
        	if(startDate != null && startDate !=  ""){
        		queryDTO.setStartDate(DateUtils.stringtoDate(startDate, "yyyy-MM-dd"));
        	}
        	//结束时间
    		if(endDate != null && endDate !=  ""){
    			queryDTO.setEndDate(DateUtils.stringtoDate(endDate, "yyyy-MM-dd"));
    		}
    		//变动类型
    		if(changeType != null && changeType !=  ""){
    			queryDTO.setChangeType(changeType);
    		}
    		if(createUser != null && createUser !=  ""){
    			queryDTO.setCreateUser(createUser);
    		}
        	rep = accountChangeService.excelExport(response,queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
    
    /********************获取变动类型Start*****************************************/
    @RequestMapping("/findChangeType")
    public @ResponseBody List<DdicVo> findChangeType(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for(AccTranTypeEnum status : AccTranTypeEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }
    /********************获取变动类型end*****************************************/
}
