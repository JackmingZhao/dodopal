package com.dodopal.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountChildMerchantBean;
import com.dodopal.portal.business.bean.query.AccountChildMerchantQuery;
import com.dodopal.portal.business.service.ChildMerchantActAmtService;
/**
 * 账户余额
 * @author hxc
 *
 */
@Controller
@RequestMapping("/merchantChildAct")
public class ChildMerchantActAmtController extends CommonController{

	@Autowired
	private ChildMerchantActAmtService merchantService;
	
	@RequestMapping("/amt")
	public ModelAndView show(Model model,HttpServletRequest request){
		return new ModelAndView("merchant/childMerchantAccount/chlidAccountBalance");
	}
	
	//查询（分页）
	@RequestMapping("/find")
	public @ResponseBody DodopalResponse<DodopalDataPage<AccountChildMerchantBean>> findAccountBalance(HttpServletRequest request,@RequestBody AccountChildMerchantQuery query){
		String merParentCode = "";
		DodopalResponse<DodopalDataPage<AccountChildMerchantBean>> response = new DodopalResponse<DodopalDataPage<AccountChildMerchantBean>>();
		//上级商户编号
		merParentCode = getCurrentMerchantCode(request.getSession());
		query.setMerParentCode(merParentCode);
		response = merchantService.findAccountChildMerByPage(query);
		return response;
	} 
	
	//导出
    @RequestMapping("/balanceFormExport")
    public @ResponseBody DodopalResponse<String>  exportBalanceRecordOrder(HttpServletRequest request,HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        AccountChildMerchantQueryDTO queryDTO = new AccountChildMerchantQueryDTO();
        //上级商户编号
        String merParentCode = getCurrentMerchantCode(request.getSession());;
        //商户名称
        String merName = request.getParameter("merName");
     	//账户类别
        String fundType = request.getParameter("fundType");
      		
        try {
        	//上级商户编号
        	if(merParentCode != null && merParentCode !=  ""){
    			queryDTO.setMerParentCode(merParentCode);
    		}
        	if(merName != null && merName !=  ""){
    			queryDTO.setMerName(merName);
    		}
        	if(fundType != null && fundType !=  ""){
    			queryDTO.setFundType(fundType);
    		}
        	rep = merchantService.excelExport(queryDTO, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
}
