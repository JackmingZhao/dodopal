package com.dodopal.portal.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserUpBean;
import com.dodopal.portal.business.bean.MobileBean;
import com.dodopal.portal.business.service.AccountSecureService;

@Controller
@RequestMapping("/secure")
public class AccountSecureController extends CommonController{
    
    @Autowired
    private AccountSecureService  accountSecureService;
    
    /******************************************门户系统安全设置********************************/
    @RequestMapping("/showAccountSecureMgr")
     public ModelAndView verifiedMgmt(HttpServletRequest request) {
         return new ModelAndView("ddp/accountSecure");
     }
    
    /*******************1.手机设置*******************************/
    /**
      * 手机发送验证码
      * @param moblieNum 手机号
      * @param codeType 短信类型  
      *              1：个人都都宝注册；
      *              4：个人变更终端设备；
      *              5：个人找回密码；
      *              6：网点客户端注册
      * @return
      */
    @RequestMapping("/accountSend")
     public @ResponseBody DodopalResponse<Map<String, String>> accountSend(HttpServletRequest request,@RequestBody String moblieNum){
         return accountSecureService.send(moblieNum);
     }

     /**
      * 手机验证码验证
      * @param moblieNum    手机号
      * @param dypwd        验证码
      * @param serialNumber 序号
      * @return
      */
    @RequestMapping("/accountMoblieCodeCheck")
     public @ResponseBody DodopalResponse<String> accountMoblieCodeCheck(HttpServletRequest request,@RequestBody MobileBean mobileBean){
         return accountSecureService.moblieCodeCheck(mobileBean);
     }
    
    
    /**
     * 保存更新手机号
     * @param moblieNum    手机号
     * @param dypwd        验证码
     * @param serialNumber 序号
     * @return
     */
   @RequestMapping("/saveAccountMoblie")
    public @ResponseBody DodopalResponse<String> saveAccountMoblie(HttpServletRequest request,@RequestBody MobileBean mobileBean){
       DodopalResponse<String> response = new DodopalResponse<String>();
       MerchantUserBean merUserBean = new MerchantUserBean();
       merUserBean.setId(super.getCurrentUserId(request.getSession()));
       merUserBean.setMerUserMobile(mobileBean.getMoblieNum());
       response =accountSecureService.saveAccountMoblie(mobileBean,merUserBean);
       return  response;
    }
   
   /**
    * 效验原密码
    * @param request
    * @param merchantUserUpBean
    * @return
    */
   @RequestMapping("/findMerUserPWD")
   public @ResponseBody DodopalResponse<String> findMerUserPWD(HttpServletRequest request,@RequestBody MerchantUserUpBean merchantUserUpBean){
       DodopalResponse<String> response = new DodopalResponse<String>();
       merchantUserUpBean.setId(super.getCurrentUserId(request.getSession()));
       response= accountSecureService.validateMerUserPWD(merchantUserUpBean);
       return response;
   }
   
 /**
  * 保存新密码
  * @param request
  * @param mobileBean
  * @return
  */
   @RequestMapping("/updateLogPwd")
   public @ResponseBody DodopalResponse<String> updateLogPwd(HttpServletRequest request,@RequestBody MerchantUserUpBean merchantUserUpBean){
      DodopalResponse<String> response = new DodopalResponse<String>();
      merchantUserUpBean.setId(super.getCurrentUserId(request.getSession()));
      response =accountSecureService.updateMerUserPWDById(merchantUserUpBean);
      return  response;
   }
   
  /**
   * 查看签名密钥
   * @param request
   * @param merchantUserUpBean
   * @return
   */
   @RequestMapping("/findMerMD5Pay")
   public @ResponseBody DodopalResponse<MerchantUserUpBean> findMerMD5Pay(HttpServletRequest request){
       DodopalResponse<MerchantUserUpBean> response = new DodopalResponse<MerchantUserUpBean>();
       MerchantUserUpBean merchantUserUpBean = new MerchantUserUpBean();
       merchantUserUpBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
       response =accountSecureService.findMerMD5PayPWD(merchantUserUpBean);
       return response;
   }
   /**
    * 查看验签密钥
    * @param request
    * @param merchantUserUpBean
    * @return
    */
    @RequestMapping("/findMD5BackPayPWD")
    public @ResponseBody DodopalResponse<MerchantUserUpBean> findMD5BackPayPWD(HttpServletRequest request){
        DodopalResponse<MerchantUserUpBean> response = new DodopalResponse<MerchantUserUpBean>();
        MerchantUserUpBean merchantUserUpBean = new MerchantUserUpBean();
        merchantUserUpBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
        response =accountSecureService.findMerMD5BackPayPWD(merchantUserUpBean);
        return response;
    }
 /**
  * 更新签名密钥
  * @param request
  * @param merchantUserUpBean
  * @return
  */
 @RequestMapping("/upmerMD5PayPwd")
  public @ResponseBody DodopalResponse<String> upmerMD5PayPwd(HttpServletRequest request,@RequestBody MerchantUserUpBean merchantUserUpBean){
     DodopalResponse<String> response = new DodopalResponse<String>();
     merchantUserUpBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
     response =accountSecureService.updateMerMD5PayPWD(merchantUserUpBean);
     return response;
  }
 
/**
* 更新验签密钥
* @param request
* @param merchantUserUpBean
* @return
*/
@RequestMapping("/upmerMD5BackPayPWD")
 public @ResponseBody DodopalResponse<String> upmerMD5BackPayPWD(HttpServletRequest request,@RequestBody MerchantUserUpBean merchantUserUpBean){
    DodopalResponse<String> response = new DodopalResponse<String>();
    merchantUserUpBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
    response =accountSecureService.updateMerMD5BackPayPWD(merchantUserUpBean);
    return response;
 }
 /******************************************门户系统安全设置********************************/
/******************************************门户系统个性化设置********************************/
@RequestMapping("/showAccountPersonalMgr")
public ModelAndView verifiedPersonalMgmt(HttpServletRequest request) {
    return new ModelAndView("ddp/accountPersonal");
}
@RequestMapping("/findModifyPayInfoFlg")
public @ResponseBody DodopalResponse<MerchantUserBean> findModifyPayInfoFlg(HttpServletRequest request){
   DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
   String id=super.getCurrentUserId(request.getSession());
   response =accountSecureService.findModifyPayInfoFlg(id);
   return response;
}


@RequestMapping("/upModifyPayInfoFlg")
public @ResponseBody DodopalResponse<String> upModifyPayInfoFlg(HttpServletRequest request,@RequestBody String payInfoFlg){
   DodopalResponse<String> response = new DodopalResponse<String>();
   MerchantUserBean merchantUserBean =new MerchantUserBean();
   merchantUserBean.setPayInfoFlg(payInfoFlg);
   merchantUserBean.setId(super.getCurrentUserId(request.getSession()));
   response =accountSecureService.upModifyPayInfoFlg(merchantUserBean);
   return response;
}
/******************************************门户系统个性化设置********************************/
}
