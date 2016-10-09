package com.dodopal.portal.web.controller;

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

import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.ProfitCollectBean;
import com.dodopal.portal.business.bean.ProfitDetailsBean;
import com.dodopal.portal.business.model.query.ProfitQuery;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.ProfitService;

@Controller
@RequestMapping("/ddp")
public class ProfitController extends CommonController{

	private final static Logger log = LoggerFactory.getLogger(ProfitController.class);
	
	@Autowired
	private ProfitService profitService;
	@Autowired
    AccountSetService accountSetService;
	
	
	@RequestMapping("/profitManage")
	public ModelAndView show(Model model,HttpServletRequest request){
		return new ModelAndView("ddp/profitManage");
	}
	
	@RequestMapping("/toProfitManage")
	public @ResponseBody DodopalResponse<DodopalDataPage<ProfitCollectBean>> findProfitCollectListByPage(HttpServletRequest request,@RequestBody ProfitQuery query){
		DodopalResponse<DodopalDataPage<ProfitCollectBean>> response = new DodopalResponse<DodopalDataPage<ProfitCollectBean>>();
		String merType = getMerType(request.getSession());
		String userCode = "";
		try {
			if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
                userCode = getCurrentUserCode(request.getSession());
            }else {
                userCode = getCurrentMerchantCode(request.getSession());
            }
			query.setMerCode(userCode);
			response = profitService.findProfitCollectListByPage(query);
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	@RequestMapping("/toProfitDetails")
	public @ResponseBody DodopalResponse<DodopalDataPage<ProfitDetailsBean>> findProfitDetailsListByPage(HttpServletRequest request,@RequestBody ProfitQuery query){
		DodopalResponse<DodopalDataPage<ProfitDetailsBean>> response = new DodopalResponse<DodopalDataPage<ProfitDetailsBean>>();
		String merType = getMerType(request.getSession());
		String userCode = "";
		try {
			if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
                userCode = getCurrentUserCode(request.getSession());
            }else {
                userCode = getCurrentMerchantCode(request.getSession());
            }
			query.setMerCode(userCode);
			response = profitService.findProfitDetailsListByPage(query);
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	//导出
    @RequestMapping("/profitExport")
    public @ResponseBody DodopalResponse<String>  exportTranRecordOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        ProfitQueryDTO query = new ProfitQueryDTO();
        String merType = getMerType(request.getSession());
		String userCode = "";
		if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
            userCode = getCurrentUserCode(request.getSession());
        }else {
            userCode = getCurrentMerchantCode(request.getSession());
        }
		query.setMerCode(userCode);
         //创建时间结束
        String createDateEnd = request.getParameter("createDateEnd");
         //创建时间开始
        String createDateStart = request.getParameter("createDateStart");
        
        try {
    		//开始时间
        	if(createDateStart != null && createDateStart !=  ""){
        		query.setStartDate(DateUtils.stringtoDate(createDateStart, "yyyy-MM-dd"));
        	}
        	//结束时间
    		if(createDateEnd != null && createDateEnd !=  ""){
    			query.setEndDate(DateUtils.stringtoDate(createDateEnd, "yyyy-MM-dd"));
    		}
        	rep = profitService.exportProfit(response, query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
}
