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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.ChildMerChantRecordService;
import com.dodopal.portal.business.service.ChildMerchantService;
import com.dodopal.portal.business.service.MerchantService;
import com.dodopal.portal.business.service.TransactionRecordService;

@Controller
@RequestMapping("/merchantChildTranlist")
public class ChildMerChantRecordController extends CommonController {

	private final static Logger log = LoggerFactory.getLogger(ChildMerChantRecordController.class);
	
	@Autowired
	private ChildMerChantRecordService recordService;
	
	@Autowired
	private ChildMerchantService childMerchantService;
	
	@Autowired
    private TransactionRecordService traRecordService;
	@Autowired
    private   MerchantService merchantService;
	@Autowired
    AccountSetService accountSetService;
	
	@RequestMapping("/childMerchantRecord")
    public ModelAndView verifiedMgmt(Model model,HttpServletRequest request) {
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
        return new ModelAndView("merchant/childmerchantRecord/childMerchantRecord");
    }
	
	@RequestMapping(value  = "/find", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<DodopalDataPage<PayTraTransaction>> findTraRecordByPage(HttpServletRequest request,@RequestBody PayTraTransactionQuery traquery){
		traquery.setMerParentCode(getCurrentMerchantCode(request.getSession()));
		DodopalResponse<DodopalDataPage<PayTraTransaction>> response = new DodopalResponse<DodopalDataPage<PayTraTransaction>>();
		String createUser = "";
		try {
			DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
    		String merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
    		//判断用户是否为操作员
    		if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
    			createUser = getCurrentUserId(request.getSession());
    		}
    		traquery.setCreateUser(createUser);
			response = recordService.findTraRecordByPage(traquery);
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
		}
		return response;
		
	}
	
	
	/**
     * 查询单条子商户信息
     * @param model
     * @param request
     * @param merCode
     * @return
     */
    @RequestMapping("/childfindMerchant")
    public ModelAndView childfindMerchantByCode(Model model, HttpServletRequest request, @RequestParam String tranCode) {
        DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
        try {
            response = traRecordService.findTranInfoByTranCode(tranCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        TraTransactionBean payTraTransaction = response.getResponseEntity();
        model.addAttribute("payTraTransaction", payTraTransaction);
        return new ModelAndView("merchant/childmerchantRecord/childMerchantRecordView");
    }
    
    @RequestMapping("/childfindMerchantView")
    public @ResponseBody DodopalResponse<TraTransactionBean> childfindMerchant(Model model, HttpServletRequest request, @RequestBody String tranCode) {
        DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
        try {
            response = traRecordService.findTranInfoByTranCode(tranCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
  //导出
    @RequestMapping("/merChildTanlistExport")
    public @ResponseBody DodopalResponse<String>  exportTranRecordOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        PayTraTransactionQueryDTO queryDTO = new PayTraTransactionQueryDTO();
        String createUser = "";
        //所属上级商户编码
        String merPreCode=super.getCurrentMerchantCode(request.getSession());
        //网点名称
        String merOrUserName = request.getParameter("merOrUserName");
        //开始日期
        String createDateStart = request.getParameter("createDateStart");
        //结束日期
        String createDateEnd = request.getParameter("createDateEnd");
        //交易名称
        String tranName = request.getParameter("tranName");
        //交易类型
        String tranType = request.getParameter("tranType");
        //交易流水号
        String tranCode = request.getParameter("tranCode");
        //交易状态
        String tranOutStatus = request.getParameter("tranOutStatus");
        //最小金额
        String realMinTranMoney = request.getParameter("realMinTranMoney");
        //最大金额
        String realMaxTranMoney = request.getParameter("realMaxTranMoney");
        //订单号
        String orderNumber = request.getParameter("orderNumber");
        
        DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
		String merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
		//判断用户是否为操作员
		if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
			createUser = getCurrentUserId(request.getSession());
		}
        
        try {
        	if(merPreCode != null && merPreCode !=  ""){
    			queryDTO.setMerParentCode(merPreCode);
    		}
        	if(merOrUserName != null && merOrUserName !=  ""){
    			queryDTO.setMerOrUserName(merOrUserName);
    		}
        	if(createDateStart != null && createDateStart !=  ""){
        		queryDTO.setCreateDateStart(DateUtils.stringtoDate(createDateStart, "yyyy-MM-dd"));
        	}
    		if(createDateEnd != null && createDateEnd !=  ""){
    			queryDTO.setCreateDateEnd(DateUtils.stringtoDate(createDateEnd, "yyyy-MM-dd"));
    		}
    		if(tranName != null && tranName !=  ""){
    			queryDTO.setTranName(tranName);
    		}
    		if(tranType != null && tranType !=  ""){
    			queryDTO.setTranType(tranType);
    		}
    		if(tranCode != null && tranCode !=  ""){
    			queryDTO.setTranCode(tranCode);
    		}
    		if(tranOutStatus != null && tranOutStatus !=  ""){
    			queryDTO.setTranOutStatus(tranOutStatus);
    		}
    		if(realMinTranMoney != null && realMinTranMoney !=  ""){
    			queryDTO.setRealMinTranMoney(Long.parseLong(realMinTranMoney)*100);
    		}
    		if(realMaxTranMoney != null && realMaxTranMoney !=  ""){
    			queryDTO.setRealMaxTranMoney(Long.parseLong(realMaxTranMoney)*100);
    		}
    		if(createUser != null && createUser !=  ""){
    			queryDTO.setCreateUser(createUser);
    		}
    		if(orderNumber != null && orderNumber !=  ""){
    			queryDTO.setOrderNumber(orderNumber);
    		}
        	rep = recordService.excelExport(response,queryDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
    
    /********************获取交易状态Start*****************************************/
    @RequestMapping("/findTranOutStatus")
    public @ResponseBody List<DdicVo> findTranOutStatus(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for(TranOutStatusEnum status : TranOutStatusEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }
    /********************获取交易状态end*****************************************/
}
