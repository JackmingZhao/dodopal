package com.dodopal.portal.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserRegisterBean;
import com.dodopal.portal.business.bean.MobileBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.service.RegisterService;

@Controller
@RequestMapping("/register")
public class RegisterController extends CommonController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/registerPage")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("register/register");
    }

    /**
     * 个人用户注册
     * @param merUserBean
     * @return
     */
    @RequestMapping("/registerUser")
    public ModelAndView registerUser(HttpServletRequest request, @RequestParam String merUserName, @RequestParam String merUserPWD, @RequestParam String merUserMobile2, @RequestParam String mobileCheckCode) {
        ModelAndView mv = new ModelAndView();
        try {
            String serialNumber = (String) request.getSession().getAttribute(PortalConstants.SERIAL_NUMBER);
            MerchantUserBean merUserBean = new MerchantUserBean();
            merUserBean.setMerUserName(merUserName);
            merUserBean.setMerUserPWD(merUserPWD);
            merUserBean.setMerUserMobile(merUserMobile2);
            DodopalResponse<MerchantUserBean> response = registerService.registerUser(merUserBean, mobileCheckCode, serialNumber);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                mv.addObject(PortalConstants.LOGIN_NAME, response.getResponseEntity() != null ? response.getResponseEntity().getMerUserName() : "");
                mv.setViewName("register/registerUserSuccess");
            } else {
                mv.addObject(PortalConstants.DODOPAL_RESPONSE, generateErrorMessage(response.getCode(), response.getMessage()));
                mv.setViewName("register/registerUserFail");
            }
            return mv;
        }
        catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            mv.addObject(PortalConstants.DODOPAL_RESPONSE, generateErrorMessage(response.getCode(), response.getMessage()));
            mv.setViewName("register/registerUserFail");
        }
        return mv;
    }

    /**
     * 商户注册
     * @param merUserBean merUserBean.merUserName：用户名 merUserBean.merUserPWD：密码
     * merUserBean.merUserMobile：用户手机号
     * @return 商户号
     * 
     * TODO 临时方法 for debug purpose
     */
    @RequestMapping("/registerMerchant")
    public ModelAndView registerMerchant(HttpServletRequest request, 
        @RequestParam String merName, 
        @RequestParam String merLinkUser, 
        @RequestParam String merLinkUserMobile2, 
        @RequestParam String merLinkUserMobileCheckCode, 
        @RequestParam String province,
        @RequestParam String city,
        @RequestParam String address, 
        @RequestParam String merchantUserName, 
        @RequestParam String merchantUserPWD
        
        ) {
        String serialNumber = (String) request.getSession().getAttribute(PortalConstants.SERIAL_NUMBER);
        ModelAndView mv = new ModelAndView();
        try {
            MerchantUserRegisterBean merchantBean = new MerchantUserRegisterBean();
            merchantBean.setMerName(merName);
            merchantBean.setMerLinkUser(merLinkUser);
            merchantBean.setMerLinkUserMobile(merLinkUserMobile2);
            merchantBean.setProvinceCode(province);
            merchantBean.setCityCode(city);
            merchantBean.setAddress(address);
            merchantBean.setMerUserName(merchantUserName);
            merchantBean.setMerUserPWD(merchantUserPWD);
            merchantBean.setDypwd(merLinkUserMobileCheckCode);
            merchantBean.setSerialNumber(serialNumber);
            DodopalResponse<String> response = registerService.registerMerchant(merchantBean);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                mv.addObject(PortalConstants.LOGIN_NAME, response.getResponseEntity() != null ? response.getResponseEntity() : "");
                mv.setViewName("register/registerMerchantSuccess");
            } else {
                mv.addObject(PortalConstants.DODOPAL_RESPONSE, generateErrorMessage(response.getCode(), response.getMessage()));
                mv.setViewName("register/registerMerchantFail");
            }
            return mv;
        }
        catch (Exception e) {
            e.printStackTrace();
            DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            mv.addObject(PortalConstants.DODOPAL_RESPONSE, generateErrorMessage(response.getCode(), response.getMessage()));
            mv.setViewName("register/registerMerchantFail");
        }
        return mv;
    }

    /**
     * 检查手机号是否已注册
     * @param mobile
     * @return true:已注册
     */
    @RequestMapping("/checkMobileExist")
    public @ResponseBody DodopalResponse<Boolean> checkMobileExist(HttpServletRequest request, @RequestBody String mobile) {
        return registerService.checkMobileExist(mobile);
    }

    /**
     * 检查用户名是否已注册
     * @param userName
     * @return true:已注册
     */
    @RequestMapping("/checkUserNameExist")
    public @ResponseBody DodopalResponse<Boolean> checkUserNameExist(HttpServletRequest request, @RequestBody String userName) {
        return registerService.checkUserNameExist(userName);
    }

    /**
     * 检查商户名称是否已注册
     * @param merName
     * @return
     */
    @RequestMapping("/checkMerchantNameExist")
    public @ResponseBody DodopalResponse<Boolean> checkMerchantNameExist(HttpServletRequest request, @RequestBody String merName) {
        return registerService.checkMerchantNameExist(merName);
    }
    

    /**
     * 短信发送（不校验手机号）
     * @param moblieNum 手机号
     * @return 
     * key1:dypwd 验证码 
     * key2:pwdseq 序号
     */
    @RequestMapping("/sendNoCheck")
    public @ResponseBody DodopalResponse<Map<String, String>> sendNoCheck(HttpServletRequest request, @RequestBody String mobile) {
        // TODO 校验 是否是重复发送
        DodopalResponse<Map<String, String>> response = null;
        try {
            response = registerService.sendNoCheck(mobile);
            if(ResponseCode.SUCCESS.equals(response.getCode()) && response.getResponseEntity() != null) {
                request.getSession().setAttribute(PortalConstants.SERIAL_NUMBER, response.getResponseEntity().get("pwdseq"));
            }
        }
        catch (Exception e) {
            response = new DodopalResponse<Map<String, String>>();
            response.setCode(ResponseCode.SEND_MOBILE_CHECKCODE_ERROR);
        }
        return response;
    }

    
    @RequestMapping("/moblieCodeCheck")
    public @ResponseBody DodopalResponse<String> moblieCodeCheck(HttpServletRequest request, @RequestBody MobileBean mobile) {
        // TODO 校验 是否是重复发送
        DodopalResponse<String> response = null;
        try {
            String searialNum = (String) request.getSession().getAttribute(PortalConstants.SERIAL_NUMBER);
            if(DDPStringUtil.isNotPopulated(searialNum)) {
                response = new DodopalResponse<String>();
                response.setCode(ResponseCode.PORTAL_MOBILE_DYPWD_ERROR);
            } else {
                response = registerService.moblieCodeCheck(mobile.getMoblieNum(), mobile.getDypwd(), searialNum);
            }
        }
        catch (Exception e) {
            response = new DodopalResponse<String>();
            response.setCode(ResponseCode.SEND_MOBILE_CHECKCODE_ERROR);
        }
        return response;
    }
}
