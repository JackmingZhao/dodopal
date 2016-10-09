package com.dodopal.portal.web.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.ChildMerchantAccountChangeBean;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.query.ChildMerFundChangeQuery;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.ChildMerchantActListService;
import com.dodopal.portal.business.service.MerchantService;

/**
 * 资金变更记录
 * @author hxc
 *
 */
@Controller
@RequestMapping("/merchantChildAct")
public class ChildMerchantActListController extends CommonController{

	@Autowired
	private ChildMerchantActListService actListService;
	@Autowired
    MerchantService merchantService;
	@Autowired
    AccountSetService accountSetService;
	
	@RequestMapping("/list")
	public ModelAndView show(Model model,HttpServletRequest request){
		//网点名称
		String merName = request.getParameter("merName");
		//商户编号
		String merCode = request.getParameter("merCode");
		if(merName!=null){
			model.addAttribute("merName", merName);
		}
		if(merCode!=null){
			model.addAttribute("merCode", merCode);
		}
		return new ModelAndView("merchant/childMerchantAccount/chlidAccountChange");
	}
	
	//查询（分页）
	@RequestMapping("/findChlidAccountChange")
	public @ResponseBody DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>> findChlidAccountChange(HttpServletRequest request,@RequestBody ChildMerFundChangeQuery query){
		String merParentCode = "";
		DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>> response = new DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>>();
		//上级商户编号
		merParentCode = getCurrentMerchantCode(request.getSession());
		String createUser = "";
		String userCode = "";
		String merType = getMerType(request.getSession());
		query.setMerParentCode(merParentCode);
		try {
			if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
                userCode = getCurrentUserCode(request.getSession());
            }else {
                userCode = getCurrentMerchantCode(request.getSession());
            }
			DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
    		String merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
    		 //判断用户是否为操作员
    		 if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
    			 createUser = getCurrentUserId(request.getSession());
    		 }
    		 query.setCreateUser(createUser);
    		 
			response = actListService.findAccountChangeChildMerByPage(query);
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	} 
	
	//导出
    @RequestMapping("/accountActListFormExport")
    public @ResponseBody DodopalResponse<String>  exportAccountRecordOrder(HttpServletRequest request,HttpServletResponse response) {
    	DodopalResponse<String> rep = new DodopalResponse<String>();
    	ChildMerFundChangeQueryDTO query = new ChildMerFundChangeQueryDTO();
    	
    	 String merType = getMerType(request.getSession());
         String userCode = "";
         String createUser = "";
         if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
             userCode = getCurrentUserCode(request.getSession());
         }else {
             userCode = getCurrentMerchantCode(request.getSession());
         }
         DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
 		 String merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
 		//判断用户是否为操作员
 		if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
 			createUser = getCurrentUserId(request.getSession());
 		}
    	
        //上级商户编号
        String  merParentCode = getCurrentMerchantCode(request.getSession());
        //直营网点 商户编号
        String merCode = request.getParameter("merCode");
        //直营网点名称
        String merName = request.getParameter("merName");
        //账户类别
        String fundType = request.getParameter("fundType");
        //变动类型
        String changeType  = request.getParameter("changeType");
        //最小交易金额范围
        String changeAmountMin = request.getParameter("changeAmountMin");
        //最大交易金额范围
        String changeAmountMax = request.getParameter("changeAmountMax");
        //开始时间
        String startDate = request.getParameter("startDate");
        //结束时间
        String endDate = request.getParameter("endDate");
        try {
        	//上级商户编号
    		if(merParentCode != null && merParentCode !=  ""){
    			query.setMerParentCode(merParentCode);
    		}
    		//直营网点 商户编号
    		if(merCode != null && merCode !=  ""){
    			query.setMerCode(merCode);
    		}
    		//直营网点名称
    		if(merName != null && merName !=  ""){
    			query.setMerName(merName);
    		}
    		//资金类别
    		if(fundType != null && fundType !=  ""){
    			query.setFundType(fundType);
    		}
    		//变动类型
    		if(changeType != null && changeType !=  ""){
    			query.setChangeType(changeType);
    		}
    		//最小交易金额范围
    		if(changeAmountMin != null && changeAmountMin !=  ""){
    			query.setChangeAmountMin(Long.parseLong(changeAmountMin)*100);
    		}
    		//最大交易金额范围
    		if(changeAmountMax != null && changeAmountMax !=  ""){
    			query.setChangeAmountMax(Long.parseLong(changeAmountMax)*100);
    		}
    		//开始时间
    		if(startDate != null && startDate !=  ""){
    			query.setStartDate(DateUtils.stringtoDate(startDate,"yyyy-MM-dd"));
    		}
    		//结束时间
    		if(endDate != null && endDate !=  ""){
    			query.setEndDate(DateUtils.stringtoDate(endDate,"yyyy-MM-dd"));
    		}
    		if(createUser != null && createUser !=  ""){
    			query.setCreateUser(createUser);
    		}
        	rep = actListService.findAccountChangeChildMerByList(response, query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
}
