package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerBusRateBean;
import com.dodopal.portal.business.bean.MerRateSupplementBean;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.PrdRateBean;
import com.dodopal.portal.business.model.query.ChildMerchantQuery;
import com.dodopal.portal.business.service.ChildMerchantService;
import com.dodopal.portal.business.service.PrdRateService;
import com.dodopal.portal.business.service.RegisterService;
@Controller
@RequestMapping("/childMerchant")
public class ChildMerchantController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(ChildMerchantController.class);
    @Autowired
    ChildMerchantService childMerchantService;
    @Autowired
    private RegisterService registerService;
    
    @Autowired
    private PrdRateService prdRateService;
    
    /**************************************************** 子商户信息管理开始 *****************************************************/
    @RequestMapping("/childMerchantTypeMgr")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("merchant/childmerchantMgr/childMerchant");
    }
    /**
     * 查询子商户信息列表
     * @param request
     * @param queryChildBean
     * @return
     */
    @RequestMapping(value = "/childFindMerchantType", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody DodopalResponse<DodopalDataPage<MerchantBean>> childFindMerchantType(HttpServletRequest request,@RequestBody ChildMerchantQuery queryChildBean) {
        log.info("ChildMerchantController ChildMerchantQuery:"+queryChildBean);
        DodopalResponse<DodopalDataPage<MerchantBean>> response = new DodopalResponse<DodopalDataPage<MerchantBean>>();
        queryChildBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
        try {
            response = childMerchantService.findChildMerchantBeanByPage(queryChildBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PORTAL_CHILD_MERCHANT_FIND_ERR);
        }
        return response;
    }
    /**
     * 新增和编辑子商户信息
     * @param request
     * @param merchantBean
     * @return
     */
    @RequestMapping("/saveAndUpdataChildMerchant")
    public @ResponseBody DodopalResponse<String> saveAndUpdataChildMerchnat(HttpServletRequest request, @RequestBody MerchantBean merchantBean) {
        log.info("sava and update this ChildMerchant :"+merchantBean);
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            merchantBean.setMerParentCode(super.getCurrentMerchantCode(request.getSession()));
            merchantBean.setCreateUser(super.getCurrentUserId(request.getSession()));
            if (StringUtils.isBlank(merchantBean.getMerCode())) {
                response = childMerchantService.saveChildMerchants(merchantBean);
            } else {
                response = childMerchantService.upChildMerchantBean(merchantBean);
            }

        }
        catch (DDPException ddp) {
            response.setCode(ResponseCode.PORTAL_SAVE_CHILD_MERCHANT_ERR);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
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
    @RequestMapping("/childfindMerchantByCode")
    public @ResponseBody DodopalResponse<MerchantBean> childfindMerchantByCode(Model model, HttpServletRequest request, @RequestBody String merCode) {
        DodopalResponse<MerchantBean> response = new DodopalResponse<MerchantBean>();
        String merPreCode=super.getCurrentMerchantCode(request.getSession());
        try {
          response = childMerchantService.findChildMerchantByCode(merCode,merPreCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.PORTAL_CHILD_MERCHANT_BY_CODE_ERR);
        }
        return response;
    }
    /**
     * 停用和启用子商户信息
     * @param request
     * @param merCodes
     * @param activate
     * @return
     */
    @RequestMapping("/startAndDisableChildMerchant")
    public @ResponseBody DodopalResponse<String> startAndDisableChildMerchant(HttpServletRequest request, @RequestBody String[] merCodes) {
       String activate= request.getParameter("activate");
        log.info("startAndDisableChildMerchant merCodes:"+merCodes+"\r\n activate:"+activate);
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if(merCodes != null && merCodes.length > 0) {
                String merPreCode = super.getCurrentMerchantCode(request.getSession());
                String updateUser = super.getCurrentUserId(request.getSession());
                DodopalResponse<Integer> number=  childMerchantService.startAndDisableMerchant(merCodes,activate,merPreCode,updateUser);
                response.setCode(number.getCode());
            } else {
                response.setCode(ResponseCode.PORTAL_START_DIA_CHILD_MERCHANT_ERR);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    /**************************************************** 子商户信息管理结束 *****************************************************/
    /**************************************************** 通卡公司数据加载开始 *****************************************************/
   /**
    * 加载通卡公司
    * @param request
    * @param merCode
    * @return
    */
     @RequestMapping("/findProductList")
    public @ResponseBody DodopalResponse<List<MerBusRateBean>> findProductList(HttpServletRequest request, @RequestBody String merCode, @RequestParam(value = "merType", required = false) String merType) {
        DodopalResponse<List<MerBusRateBean>> response = new DodopalResponse<List<MerBusRateBean>>();
        try {
          merCode = super.getCurrentMerchantCode(request.getSession());
          response = childMerchantService.findProductList(merCode, merType);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
    }
     /**
      * 加载通卡公司费率表 
      * @param request
      * @param productCodes
      * @return
      */
     @RequestMapping("loadNotYktInfo")
     public @ResponseBody DodopalResponse<List<MerBusRateBean>> loadYktInfo(HttpServletRequest request, @RequestBody String[] productCodes) {
         DodopalResponse<List<MerBusRateBean>> response = new DodopalResponse<List<MerBusRateBean>>();
         try {
            String merCode = super.getCurrentMerchantCode(request.getSession());
           response = childMerchantService.loadYktInfo(productCodes,merCode);
         }catch (Exception e) {
             e.printStackTrace();
             response.setCode(ResponseCode.UNKNOWN_ERROR);
         }
      return response;
     }
       
     
     
     
    /**************************************************** 通卡公司数据加载结束 *****************************************************/
    /**************************************************** 商户信息表效验开始 *****************************************************/
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
    /**************************************************** 商户信息表效验结束 *****************************************************/
      /**************************************************** 审核不通过商户信息表效验开始 *****************************************************/
      /**
       * 检查手机号是否已注册
       * @param mobile
       * @return true:已注册
       */
      @RequestMapping("/notcheckMobileExist")
      public @ResponseBody DodopalResponse<Boolean> notcheckMobileExist(HttpServletRequest request, @RequestBody String mobile,@RequestParam("merCode") String merCode) {
          return childMerchantService.checkMobileExist(mobile, merCode);
      }

      /**
       * 检查用户名是否已注册
       * @param userName
       * @return true:已注册
       */
      @RequestMapping("/notcheckUserNameExist")
      public @ResponseBody DodopalResponse<Boolean> notcheckUserNameExist(HttpServletRequest request, @RequestBody String userName, @RequestParam("merCode") String merCode) {
          return childMerchantService.checkUserNameExist(userName, merCode);
      }

      /**
       * 检查商户名称是否已注册
       * @param merName
       * @return
       */
      @RequestMapping("/notcheckMerchantNameExist")
      public @ResponseBody DodopalResponse<Boolean> notcheckMerchantNameExist(HttpServletRequest request, @RequestBody String merName,@RequestParam("merCode") String merCode) {
          return childMerchantService.checkMerchantNameExist(merName, merCode);
      }
    /**************************************************** 审核不通过商户信息表效验结束 *****************************************************/
      /****************************************************业务类型加载开始 *****************************************************/
      /**
       * 初始化加载业务类型
       * @param request
       * @return
       */
      @RequestMapping("/findPrdRateBean")
      public @ResponseBody DodopalResponse<List<PrdRateBean>> findPrdRateBean(HttpServletRequest request) {
          DodopalResponse<List<PrdRateBean>> response = new DodopalResponse<List<PrdRateBean>>();
          try {
            response = prdRateService.findPrdRate();
          }catch (Exception e) {
              e.printStackTrace();
              response.setCode(ResponseCode.UNKNOWN_ERROR);
          }
       return response;
      }
      
      /**************************************************** 业务类型加载结束 *****************************************************/
      /****************************************************直营网点商户加载业务类型开始 *****************************************************/
      /**
       * 根据商户编码获取上级商户业务类型
       * @param request
       * @param merCode
       * @return
       */
      @RequestMapping("/findMerRateSupplementByCode")
      public @ResponseBody DodopalResponse<List<MerRateSupplementBean>> findMerRateSupplementByCode(HttpServletRequest request, @RequestParam(value = "merType", required = false) String merType) {
          DodopalResponse<List<MerRateSupplementBean>> response = new DodopalResponse<List<MerRateSupplementBean>>();
          try {
            String merCode = super.getCurrentMerchantCode(request.getSession());
            response = childMerchantService.findMerRateSupplementByCode(merCode, merType);
          }catch (Exception e) {
              e.printStackTrace();
              response.setCode(ResponseCode.UNKNOWN_ERROR);
          }
       return response;
      }
      /****************************************************直营网点商户加载业务类型结束 *****************************************************/
      
}
