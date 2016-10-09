package com.dodopal.portal.web.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.users.dto.MerLimitExtractConfirmDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerLimitExtractConfirmEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountFundBean;
import com.dodopal.portal.business.bean.DirectMerChantBean;
import com.dodopal.portal.business.model.PortalTransfer;
import com.dodopal.portal.business.service.DdpService;
import com.dodopal.portal.business.service.MerLimitExtractConfirmService;
import com.dodopal.portal.business.service.TransferService;

/**
 * 账户转账（连锁商户）
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月31日
 *
 */
@Controller
@RequestMapping("/ddp")
public class TransferController extends CommonController{
    @Autowired
    DdpService ddpService;
    @Autowired
    TransferService transferService;
    
    @Autowired
    MerLimitExtractConfirmService merLimitExtractConfirmService;
    
    
    
    @RequestMapping("/transfer")
    public ModelAndView ddpTransfer(Model model,HttpServletRequest request) {
        //商户类型
        String merType = getMerType(request.getSession());
        String aType="";  //个人or企业
        String merOrUserName = "";
        String custNum = ""; //用户号or商户号
        String availableMoney = "0.00";//可用余额
        String frozenMoney = "0.00";  //冻结金额
        String accountMoney = "0.00"; //资金账户可用余额
        String accountFuntMoney = "0.00";   //授信账户可用余额    
        String accountFrozenAmount = "0.00"; //资金账户 冻结金额
        String accountFundFrozenAmount = "0.00"; //授信账户 冻结金额
        
        HttpSession session = request.getSession();
        int bindCardCount = 0;
        String fundType = "";//主账户的资金类别
        String accountCode = "";//主账户编号
        String Acid = "";//主账户的数据库id

        //金额格式化 保留两位小数
        DecimalFormat df = new DecimalFormat("0.00");  
        
        if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
            aType = MerUserTypeEnum.PERSONAL.getCode();
            custNum = getCurrentUserCode(request.getSession());
        }else{
            aType = MerUserTypeEnum.MERCHANT.getCode();
            custNum = getCurrentMerchantCode(request.getSession());
        }
        merOrUserName = getCurrentUserName(session);
        try {
            DodopalResponse<AccountFundBean> response = ddpService.findAccountBalance(aType, custNum);
            if(response.getResponseEntity()!=null){
                AccountFundBean accountFundBean = response.getResponseEntity();
                availableMoney =df.format(accountFundBean.getAvailableBalance());
                frozenMoney = df.format(accountFundBean.getFrozenAmount());
                accountMoney = df.format(accountFundBean.getAccountMoney());
                accountFuntMoney = df.format(accountFundBean.getAccountFuntMoney());
                fundType = accountFundBean.getFundType();
                accountFrozenAmount = df.format(accountFundBean.getAccountFrozenAmount());
                accountFundFrozenAmount = df.format(accountFundBean.getAccountFundFrozenAmount()); 
                accountCode = accountFundBean.getAccountCode();
                Acid = accountFundBean.getId();
                //将主账户的数据库id、主账户的编号、主账户的资金类别 放入session
                setCurrentAcId(request.getSession(), Acid);
                setCurrentAccountCode(request.getSession(), accountCode);
                setCurrentFundType(request.getSession(), fundType);
            }
            DodopalResponse<Integer> bindResponse = ddpService.findMerUserCardBDCount(merOrUserName);
            if(bindResponse.getResponseEntity() != null){
                bindCardCount = bindResponse.getResponseEntity();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("availableMoney", availableMoney);
        model.addAttribute("frozenMoney", frozenMoney);
        model.addAttribute("accountMoney", accountMoney);
        model.addAttribute("accountFuntMoney", accountFuntMoney);
        model.addAttribute("accountFrozenAmount", accountFrozenAmount);
        model.addAttribute("accountFundFrozenAmount", accountFundFrozenAmount);
        model.addAttribute("bindCardCount", bindCardCount);
        model.addAttribute("fundType", fundType);
        return new ModelAndView("ddp/transfer");
    }
    
    //转账 or提取额度
    @RequestMapping("accountTransfer")
    public @ResponseBody DodopalResponse<Boolean> accountTransfer(HttpServletRequest request,@RequestBody PortalTransfer portalTransfer){
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String merType = getMerType(request.getSession());
        String sourceCustType = "";
        String sourceCusCode = "";
        String currentUserId = getCurrentUserId(request.getSession());
        //用来校验用户是否存在，是否被停用
        String userCode = getCurrentUserCode(request.getSession());
        String merCode = getCurrentMerchantCode(request.getSession()); 
        
        if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
            sourceCustType =  MerUserTypeEnum.PERSONAL.getCode();
            sourceCusCode = getCurrentUserCode(request.getSession());
        }else{
            sourceCustType = MerUserTypeEnum.MERCHANT.getCode();
            sourceCusCode = getCurrentMerchantCode(request.getSession()); 
        }
        portalTransfer.setCreateUser(currentUserId);
        portalTransfer.setSourceCustType(sourceCustType);
        portalTransfer.setSourceCusCode(sourceCusCode);
        portalTransfer.setUserCode(userCode);
        portalTransfer.setMerCode(merCode);
        portalTransfer.setBusinessType(RateCodeEnum.ACCT_TRANSFER.getCode());
        try {
            response = transferService.accountTransfer(portalTransfer);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
      
        return  response;
    }
    
    
    
    //查询 转账 的直营网点
    @RequestMapping("findDirectTransfer")
    public @ResponseBody DodopalResponse<List<DirectMerChantBean>> findDirectTransfer(HttpServletRequest request, @RequestBody String merName){
        String merType = getMerType(request.getSession());
        String merParentCode = "";
        if(MerTypeEnum.CHAIN.getCode().equals(merType)){
            merParentCode = getCurrentMerchantCode(request.getSession());
        }
        DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
        try {
            response = transferService.findMerchantByParentCode(merParentCode, merName);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return  response;
    }
    //查询 转账 的直营网点
    @RequestMapping("findDirectTransferType")
    public @ResponseBody DodopalResponse<List<DirectMerChantBean>> findDirectTransferType(HttpServletRequest request, @RequestBody String merName,String businessType){
    	String merType = getMerType(request.getSession());
    	String merParentCode = "";
    	if(MerTypeEnum.CHAIN.getCode().equals(merType)){
    		merParentCode = getCurrentMerchantCode(request.getSession());
    	}
    	DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
    	try {
    		if(StringUtils.isBlank(businessType) || "undefined".equals(businessType)){
    			businessType="13";
    		}
    		response = transferService.findMerchantByParentCode(merParentCode, merName,businessType);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		response.setCode(ResponseCode.UNKNOWN_ERROR);
    	}
    	return  response;
    }
    
    //查询 提取额度 直营网点
    @RequestMapping("findDirectExtraction")
    public @ResponseBody DodopalResponse<List<DirectMerChantBean>> findDirectExtraction(HttpServletRequest request, @RequestBody String merName,@RequestBody String businessType){
        String merType = getMerType(request.getSession());
        String merParentCode = "";
        if(MerTypeEnum.CHAIN.getCode().equals(merType)){
            merParentCode = getCurrentMerchantCode(request.getSession());
        }
        DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
        try {
            response = transferService.findMerchantByParentCode(merParentCode, merName,businessType);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return  response;
    }
    
    
    
  //查询 转账 的直营网点
    @RequestMapping("findDirectTransferFilter")
    public @ResponseBody DodopalResponse<List<DirectMerChantBean>> findDirectTransferFilter(HttpServletRequest request){
    	String merType = getMerType(request.getSession());
    	String merParentCode = "";
    	if(MerTypeEnum.CHAIN.getCode().equals(merType)){
    		merParentCode = getCurrentMerchantCode(request.getSession());
    	}
    	DodopalResponse<List<DirectMerChantBean>> response = new DodopalResponse<List<DirectMerChantBean>>();
    	String merName = request.getParameter("merName");
		String businessType = request.getParameter("businessType");
    	try {
    		if(StringUtils.isBlank(businessType) || "undefined".equals(businessType)){
    			businessType="13";
    		}
    		response = transferService.findDirectTransferFilter(merParentCode, merName,businessType);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		response.setCode(ResponseCode.UNKNOWN_ERROR);
    	}
    	return  response;
    }
    
    
    //提取加盟网点额度
    @RequestMapping("transFerLeague")
    public @ResponseBody DodopalResponse<Boolean> transFerLeague(HttpServletRequest request,@RequestBody PortalTransfer portalTransfer){
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String currentUserId = getCurrentUserId(request.getSession());
        //用来校验用户是否存在，是否被停用
        String merCode = getCurrentMerchantCode(request.getSession()); 
        String parentMerName = getMerName(request.getSession());
        List<Map<String,String>> list = portalTransfer.getDirectMer();
        try {
        	MerLimitExtractConfirmDTO merLimitExtractConfirm = new MerLimitExtractConfirmDTO();
            merLimitExtractConfirm.setParentMerCode(merCode);
            merLimitExtractConfirm.setExtractUser(currentUserId);
            merLimitExtractConfirm.setExtractAmt(portalTransfer.getMoney());
            merLimitExtractConfirm.setChildMerCode(list.get(0).get("merCode"));
            merLimitExtractConfirm.setState(MerLimitExtractConfirmEnum.TO_BE_CONFIRM.getCode());
            merLimitExtractConfirm.setParentMerName(parentMerName);
            merLimitExtractConfirm.setChildMerName(list.get(0).get("merName"));
            
            DodopalResponse<Integer> response2 = merLimitExtractConfirmService.findMerLimitExtractConfirmByCode(merLimitExtractConfirm);
            if(ResponseCode.SUCCESS.equals(response2.getCode()) && response2.getResponseEntity() > 0){
            	response.setCode(ResponseCode.SYSTEM_ERROR);
            	response.setMessage("已经提取过一次额度了");
            }else{
            	DodopalResponse<Boolean> dodopalResponse = merLimitExtractConfirmService.insertMerLimitExtractConfirm(merLimitExtractConfirm);
            	if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode()) && dodopalResponse.getResponseEntity()){
            		response.setCode(ResponseCode.SUCCESS);
            		response.setMessage("提取额度成功,请等待确认");
            	}else{
            		response.setCode(ResponseCode.SYSTEM_ERROR);
            		response.setMessage("额度提取失败");
            	}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
      
        return  response;
    }
    
}
