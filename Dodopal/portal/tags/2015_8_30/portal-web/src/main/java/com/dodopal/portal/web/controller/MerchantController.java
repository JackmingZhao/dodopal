package com.dodopal.portal.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.validator.DDPValidationHandler;
import com.dodopal.common.validator.DdpValidationBoxEnum;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.service.MerchantService;

@Controller
@RequestMapping("/merchant")
public class MerchantController extends CommonController{

    @Autowired
    MerchantService merchantService;

    /**************************************************** 子商户信息管理开始 *****************************************************/
    @RequestMapping("/merchantTypeDemg")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("merchant/merchantGroupDepMgr/merGroupDepartment");
    }
    
    /**************************************************** 子商户信息管理结束 *****************************************************/
    /**************************************************** 商户信息查看开始 *****************************************************/
     /**************************************************** 商户信息查看开始 *****************************************************/

    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: showMerchantInfo 
     * @Description: 跳转商户信息页面
     * @param request
     * @return    设定文件 
     * ModelAndView    返回类型 
     * @throws 
     */
    @RequestMapping("/showMerchantInfo")
    public ModelAndView showMerchantInfo(Model model,HttpServletRequest request) {
        DodopalResponse<MerchantBean> response = merchantService.findMerchantBeans(super.getCurrentMerchantCode(request.getSession()));
        model.addAttribute("merchant",response.getResponseEntity());
        return new ModelAndView("merchant/merchantInfo/merchantInfo");
    }
    @RequestMapping("/toUpdateMerchantInfo")
    public @ResponseBody DodopalResponse<MerchantBean> toUpdateMerchantInfo(HttpServletRequest request,@RequestBody String merCode) {
        DodopalResponse<MerchantBean> response = merchantService.findMerchantBeans(super.getCurrentMerchantCode(request.getSession()));
        return response;
    }
    
    @RequestMapping("/updateMerchantInfo")
    public @ResponseBody DodopalResponse<String> updateMerchantInfo(HttpServletRequest request,@RequestBody Map map) {
        MerchantBean bean = new MerchantBean();
        createUpdateMerchant(map, bean);
        //030641000000001
        //TODO 长度校验
//        if(!checkValue(bean)){
//            DodopalResponse<String> response  = new DodopalResponse<String>();
//            response.setCode(ResponseCode.CREATE_USER_NULL);
//            return response;
//        }
        bean.setUpdateUser(super.getLoginUser(request.getSession()).getId());
        DodopalResponse<String> response = merchantService.updateMerchantForPortal(bean);
        return response;
    }
    private boolean checkValue(MerchantBean bean){
       return DDPValidationHandler.validate(bean.getMerLinkUser(), true, DdpValidationBoxEnum.EN_CN)
            &&DDPValidationHandler.validate(bean.getMerEmail(),false,DdpValidationBoxEnum.EMAIL)
            &&DDPValidationHandler.validate(bean.getMerTelephone(), false, DdpValidationBoxEnum.PHONE)
            &&DDPValidationHandler.validate(bean.getMerFax(), false, DdpValidationBoxEnum.PHONE)
            ;
    }

    private void createUpdateMerchant(Map map, MerchantBean bean) {
        MerchantUserBean userBean = new MerchantUserBean();
        userBean.setMerUserIdentityNumber((String)map.get("merUserIdentityNumber"));
        userBean.setMerUserIdentityType((String)map.get("merUserIdentityType"));
        userBean.setMerUserAdds((String)map.get("merUserAdds"));
        userBean.setMerCode((String)map.get("merCode"));
        bean.setMerCode((String)map.get("merCode"));
        bean.setMerBankName((String)map.get("merBankName"));
        bean.setMerBankAccount((String)map.get("merBankAccount"));
        bean.setMerBankUserName((String)map.get("merBankUserName"));
        bean.setMerBusinessScopeId((String)map.get("merBusinessScopeId"));
        bean.setId((String)map.get("id"));
        bean.setMerTelephone((String)map.get("merTelephone"));
        bean.setMerLinkUser((String)map.get("merLinkUser"));
        bean.setMerFax((String)map.get("merFax"));
        bean.setMerEmail((String)map.get("merEmail"));
        bean.setMerZip((String)map.get("merZip"));
        bean.setMerchantUserBean(userBean);
    }
  
    /**************************************************** 商户信息查看结束 *****************************************************/

}
