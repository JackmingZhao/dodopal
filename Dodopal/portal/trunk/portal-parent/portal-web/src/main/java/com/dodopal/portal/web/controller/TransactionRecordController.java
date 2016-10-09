package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PayTraTransaction;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.bean.UserCardRecord;
import com.dodopal.portal.business.model.query.PayTraTransactionQuery;
import com.dodopal.portal.business.model.query.UserCardRecordQuery;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.CardService;
import com.dodopal.portal.business.service.TransactionRecordService;

@Controller
@RequestMapping("/tran")
public class TransactionRecordController extends CommonController {
    private final static Logger log = LoggerFactory.getLogger(TransactionRecordController.class);

    @Autowired
    private TransactionRecordService recordService;
    @Autowired
    AccountSetService accountSetService;
    @Autowired
    private CardService cardService;

    @RequestMapping("/record")
    public ModelAndView showRecord(Model model, HttpServletRequest request) {
    	//商户编号
		String userCode = request.getParameter("merCode");
        String merType = getMerType(request.getSession());
		if(userCode!=null){
            String userType = "";
            if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
                userType = MerUserTypeEnum.PERSONAL.getCode();
            }else {
                userType = MerUserTypeEnum.MERCHANT.getCode();
            }
			model.addAttribute("userCode", userCode);
            model.addAttribute("userType",userType);
			model.addAttribute("tranType",TranTypeEnum.ACCOUNT_RECHARGE.getCode());
		}
        return new ModelAndView("tranRecord/transactionRecord");
    }
    
    //详情查询
    @RequestMapping("/tranDetail")
    public ModelAndView findTranRecordByCode(Model model, HttpServletRequest request ,@RequestParam String tranCode) {
        DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
        try {
            response = recordService.findTranInfoByTranCode(tranCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        TraTransactionBean payTraTransaction = response.getResponseEntity();
        model.addAttribute("payTraTransaction", payTraTransaction);
        return new ModelAndView("tranRecord/tranDetail");
    }

    @RequestMapping("/tranView")
    public @ResponseBody DodopalResponse<TraTransactionBean> findTranRecordView(Model model, HttpServletRequest request ,@RequestBody String tranCode) {
        DodopalResponse<TraTransactionBean> response = new DodopalResponse<TraTransactionBean>();
        try {
            response = recordService.findTranInfoByTranCode(tranCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    //查询（分页）
    @RequestMapping("/findPayTraTransactionByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<PayTraTransaction>> findPayTraTransactionByPage(HttpServletRequest request, @RequestBody PayTraTransactionQuery query) {
        log.info("TransactionRecordController findPayTraTransactionByPage:" + query);
        String merType = getMerType(request.getSession());
        String userCode = "";
        String userType = "";
        String createUser = "";
        String merUserFlag = "";
        if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
            userType = MerUserTypeEnum.PERSONAL.getCode();
            userCode = getCurrentUserCode(request.getSession());
        }else {
            userType = MerUserTypeEnum.MERCHANT.getCode();
            userCode = getCurrentMerchantCode(request.getSession());
            DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
            merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
        }
        //判断用户是否为操作员
        if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
        	createUser = getCurrentUserId(request.getSession());
        }
        DodopalResponse<DodopalDataPage<PayTraTransaction>> response = new DodopalResponse<DodopalDataPage<PayTraTransaction>>();
        query.setMerOrUserCode(userCode	);
        query.setUserType(userType);
        query.setCreateUser(createUser);
        try {
            response = recordService.findPayTraTransactionByPage(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        return response;
    }

    //导出
    @RequestMapping("/tranRecordExport")
    public @ResponseBody DodopalResponse<String>  exportTranRecordOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        PayTraTransactionQueryDTO queryDTO = new PayTraTransactionQueryDTO();
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        String merType = getMerType(request.getSession());
        String userCode = "";
        String userType = "";
        String createUser = "";
        String merUserFlag ="";
        String id = getCurrentUserId(request.getSession());
        if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
            userType = MerUserTypeEnum.PERSONAL.getCode();
            userCode = getCurrentUserCode(request.getSession());
        }else {
            userType = MerUserTypeEnum.MERCHANT.getCode();
            userCode = getCurrentMerchantCode(request.getSession());
            DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(id);
            merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
        }
        
        //判断用户是否为操作员
        if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
        	createUser = getCurrentUserId(request.getSession());
        }
            
         //创建时间结束
        String createDateEnd = request.getParameter("createDateEnd");
         //创建时间开始
        String createDateStart = request.getParameter("createDateStart");
         //最小金额
        String realMinTranMoney = request.getParameter("realMinTranMoney");
         //最大金额
        String realMaxTranMoney = request.getParameter("realMaxTranMoney");
         //支付方式
        String payWay = request.getParameter("payWay");
         //交易类型
        String tranType = request.getParameter("tranType");
         //外部交易状态
        String tranOutStatus = request.getParameter("tranOutStatus");
      //订单号
        String orderNumber = request.getParameter("orderNumber");
        
        try {
    		//最小交易金额范围
    		if(realMinTranMoney != null && realMinTranMoney !=  ""){
    			queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(realMinTranMoney)*100).longValue());
    		}
    		//最大交易金额范围
    		if(realMaxTranMoney != null && realMaxTranMoney !=  ""){
    			queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(realMaxTranMoney)*100).longValue());
    		}
    		//用户号
    		if(userCode != null && userCode !=  ""){
    			queryDTO.setMerCode(userCode);
    		}
    		//支付方式
    		if(payWay != null && payWay !=  ""){
    			queryDTO.setPayWay(payWay);
    		}
    		 //交易类型
    		if(tranType != null && tranType !=  ""){
    			queryDTO.setTranType(tranType);
    		}
    		//外部交易状态
    		if(tranOutStatus != null && tranOutStatus !=  ""){
    			queryDTO.setTranOutStatus(tranOutStatus);
    		}
    		//开始时间
        	if(createDateStart != null && createDateStart !=  ""){
        		queryDTO.setCreateDateStart(DateUtils.stringtoDate(createDateStart, "yyyy-MM-dd"));
        	}
        	//结束时间
    		if(createDateEnd != null && createDateEnd !=  ""){
    			queryDTO.setCreateDateEnd(DateUtils.stringtoDate(createDateEnd, "yyyy-MM-dd"));
    		}
    		if(createUser != null && createUser !=  ""){
    			queryDTO.setCreateUser(createUser);
    		}
    		if(userType != null && userType !=  ""){
    			queryDTO.setUserType(userType);
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

    /********************获取交易类型Start*****************************************/
    @RequestMapping("/findTranTypeStatus")
    public @ResponseBody List<DdicVo> findTranTypeStatus(Model model, HttpServletRequest request) {
        List<DdicVo> list = new ArrayList<DdicVo>();
        for(TranTypeEnum status : TranTypeEnum.values()) {
            DdicVo ddic = new DdicVo();
            ddic.setCode(status.getCode());
            ddic.setName(status.getName());
            list.add(ddic);
        }
        return list;
    }
    /********************获取交易类型end*****************************************/
    
    /**
     * @author Mika
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/cardRecord")
    public ModelAndView showCardRecord() {
    	return new ModelAndView("tranRecord/cardRecord");
    }
    
    /**
     * @author Mika
     * 查询个人绑定卡的充值消费记录  商户不能查
     * @param request
     * @param query
     * @return
     */
    @RequestMapping("findCardRecordByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<UserCardRecord>> findCardRecordByPage(HttpServletRequest request, @RequestBody UserCardRecordQuery query) {
    	DodopalResponse<DodopalDataPage<UserCardRecord>> response = new DodopalResponse<DodopalDataPage<UserCardRecord>>();
    	String userId = getCurrentUserId(request.getSession());
    	String userName = getCurrentUserName(request.getSession());
    	String merType = getMerType(request.getSession());
    	try {
    		if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
    			query.setUserId(userId);
    			query.setUserName(userName);
    			response = cardService.findUserCardRecordByPage(query);
    		}else {
    			response.setCode(ResponseCode.SYSTEM_ERROR); 	// 
    			response.setMessage("只能个人用户有查询卡片交易记录");
    			return response;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		response.setCode(ResponseCode.SYSTEM_ERROR);
    	}
    	return response;
    }
    
    /**
     * @author Mika
     * 查询个人绑定卡交易记录 一单的详情(充值或消费)
     * @param request
     * @param typeOrderNum
     * @return
     */
    @RequestMapping("cardRecordView")
    public @ResponseBody DodopalResponse<UserCardRecord> findCardRecordView(HttpServletRequest request, @RequestBody String typeOrderNum) {
    	DodopalResponse<UserCardRecord> response = new DodopalResponse<UserCardRecord>();
    	String type = typeOrderNum.substring(0, 2);
		String orderNum = typeOrderNum.substring(2);
    	try {
    		response = cardService.findCardRecordInfoByTypeOrderNum(type, orderNum);
    	}catch(Exception e) {
    		e.printStackTrace();
    		response.setCode(ResponseCode.SYSTEM_ERROR);
    	}
    	return response;
    }
    
    /**
     * @author Mika
     *  门户个人 绑定卡交易记录 导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("cardRecordExport")
    public DodopalResponse<String> exportCardRecord(HttpServletRequest request, HttpServletResponse response) {
    	DodopalResponse<String> rep = new DodopalResponse<String>();
    	UserCardRecordQueryDTO queryDTO = new UserCardRecordQueryDTO();
    	queryDTO.setStartDate(StringUtils.isBlank(request.getParameter("startDate"))? "" : request.getParameter("startDate"));
    	queryDTO.setEndDate(StringUtils.isBlank(request.getParameter("endDate"))? "" : request.getParameter("endDate"));
    	queryDTO.setUserId(getCurrentUserId(request.getSession()));
    	queryDTO.setUserName(getCurrentUserName(request.getSession()));
    	queryDTO.setPage(new PageParameter(1, 5000));
    	rep = cardService.excelExport(request, response, queryDTO);
    	return rep;
    }
    
    
}
