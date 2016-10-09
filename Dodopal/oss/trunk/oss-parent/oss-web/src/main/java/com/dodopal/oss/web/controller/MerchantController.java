package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.MerBusRateBean;
import com.dodopal.oss.business.bean.MerRateSupplementBean;
import com.dodopal.oss.business.bean.MerchantBean;
import com.dodopal.oss.business.bean.MerchantExcelBean;
import com.dodopal.oss.business.bean.MerchantNotUnauditedExpBean;
import com.dodopal.oss.business.bean.MerchantUnauditedExpBean;
import com.dodopal.oss.business.bean.PrdRateBean;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.Ddic;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;
import com.dodopal.oss.business.model.dto.MerchantQuery;
import com.dodopal.oss.business.service.DdicService;
import com.dodopal.oss.business.service.MerDiscountService;
import com.dodopal.oss.business.service.MerchantExpService;
import com.dodopal.oss.business.service.MerchantService;
import com.dodopal.oss.business.service.PrdRateService;
import com.dodopal.oss.web.constant.Constant;



@Controller
@RequestMapping("/merchant")
public class MerchantController extends CommonController {
    private Logger logger = LoggerFactory.getLogger(MerchantController.class);
    @Autowired
    MerchantService merchantService;
    
    @Autowired
    PrdRateService prdRateService;
    
    @Autowired
    MerchantExpService merchantExpService;
    
    @Autowired
    private ExpTempletService expTempletService;
    @Autowired
    private MerDiscountService merDiscountService;
    
    
    @Autowired
    private DdicService ddicService;
    /**************************************************** 审核通过商户信息开始 *****************************************************/
    /**
     * 已审核商户信息初始页面
     * @param request
     * @return
     */
    @RequestMapping("enterprise/verifiedMgmt")
    public ModelAndView verifiedMgmt(HttpServletRequest request) {
        return new ModelAndView("merchant/enterprise/verifiedMgmt");
    }
  /**
   * 查询商户信息列表
   * Time:2015-05-31 17:01
   * Title:商户列表查询-后台分页
   * Name:Joe
   * @param request
   * @param merchantQuery
   * @return
   */
  @RequestMapping("enterprise/findMerchantsPage")
  public @ResponseBody DodopalResponse<DodopalDataPage<MerchantBean>> findMerchantsPage(HttpServletRequest request, @RequestBody MerchantQuery merchantQuery){  
      DodopalResponse<DodopalDataPage<MerchantBean>> response = new DodopalResponse<DodopalDataPage<MerchantBean>>();
      try {
    	  response= merchantService.findMerchantBeanByPage(merchantQuery);
      }
      catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.UNKNOWN_ERROR);
      }
      return response;
  }
  /**
   * 开户
   * @param request
   * @param merchantBeans
   * @return
   */
  @RequestMapping("enterprise/saveMerchantUserBusRate")
  public @ResponseBody DodopalResponse<String> saveMerchantUserBusRate(HttpServletRequest request, @RequestBody MerchantBean merchantBeans) {
      DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(OSSConstants.SESSION_USER);
            merchantBeans.setCreateUser(user.getId());
            if (StringUtils.isNotBlank(merchantBeans.getMerCode())) {
                response = merchantService.updateMerchantUserBusRate(merchantBeans);
            } else {
                response = merchantService.saveMerchantUserBusRate(merchantBeans);
            }

        }
      catch (DDPException ddp) {
          ddp.printStackTrace();
          response.setCode(ResponseCode.OSS_USER_CONNECTION_ERROR);
      }
      catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.UNKNOWN_ERROR);
      }
      return response;
  }
  
  /**
   * 查看自定义商户功能信息
   * @param request
   * @param merType
   * @return
   */
   @RequestMapping("enterprise/findMerFuncitonInfoList")
  public @ResponseBody DodopalResponse<List<TreeNode>> findMerFuncitonInfoList(HttpServletRequest request, @RequestBody String merType, @RequestParam("param") String param, @RequestParam("merCode") String merCode) {
      DodopalResponse<List<TreeNode>> response = new DodopalResponse<List<TreeNode>>();
      try {
        response = merchantService.findMerFuncitonInfoList(merType, param, merCode);
      }
      catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.OSS_MER_FUNCTION_INFO);
      }
	return response;
  }
 
  /**
   * 停用启用用户
   * @param request
   * @param activate
   * @return
   */
  @RequestMapping("enterprise/startAndDisableMerchant")
  public @ResponseBody DodopalResponse<String> startAndDisableMerchant(HttpServletRequest request, @RequestBody String[] merCodes,@RequestParam("activate") String activate) {
      DodopalResponse<String> response = new DodopalResponse<String>();
      try {
          if(merCodes != null && merCodes.length > 0) {
              HttpSession session = request.getSession();
              User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
        	  List<String> merCode =new ArrayList<String>(Arrays.asList(merCodes));
        	  DodopalResponse<Integer> number=  merchantService.startAndDisableMerchant(merCode, activate,  user.getUserId());
        	  response.setCode(number.getCode());
        	  response.setMessage(number.getMessage());
          } else {
              response.setCode(ResponseCode.OSS_START_AND_DISABLE);
          }
      }
      catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.UNKNOWN_ERROR);
      }
      return response;
  }
 
  /** 
 * @author dingkuiyuan@dodopal.com
 * @Title: findVerified 
 * @Description: TODO
 * @param request
 * @param model
 * @param merCode
 * @return    设定文件 
 * DodopalResponse<MerchantBean>    返回类型 
 * @throws 
 */
@RequestMapping("enterprise/findVerified")
  public @ResponseBody DodopalResponse<MerchantBean> findVerified(HttpServletRequest request, Model model,@RequestBody String merCode) {
      DodopalResponse<MerchantBean> response = new DodopalResponse<MerchantBean>();
      try {
          MerchantBean merUser = new MerchantBean();
          merUser.setMerCode(merCode);
          response = merchantService.findInfoByMerCode(merUser,MerStateEnum.THROUGH);
          if(null!=response.getResponseEntity().getMerBusinessScopeId()){
              Ddic ddic = new Ddic();
              //数据字典，查询的为经营范围
              ddic.setCategoryCode(Constant.DDIC_CODE_BUSINESS_SCOPE);
              ddic.setCode(response.getResponseEntity().getMerBusinessScopeId());
              List<Ddic>ddicList = ddicService.findDdics(ddic);
              if(null!=ddicList&&ddicList.size()>0){
                  response.getResponseEntity().setMerBusinessScopeId(ddicList.get(0).getName());
              }
          }
          //model.addAttribute("merchant",result.getResponseEntity());
        } catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }

  /**
   * 选择一条商户信息进行查看
   * @param request
   * @param merCode
   * @return
   */
  @RequestMapping("enterprise/findMerchantByCode")
  public @ResponseBody DodopalResponse<MerchantBean> findMerchantByCode(HttpServletRequest request, @RequestBody String merCode) {
	  DodopalResponse<MerchantBean> response = new DodopalResponse<MerchantBean>();
	     try {
	       response = merchantService.findMerchantBeans(merCode);
	     }
	     catch (Exception e) {
	         e.printStackTrace();
	         response.setCode(ResponseCode.OSS_USER_CONNECTION_ERROR);
	     }
	     return response;
	 }

    /**
     * 判断是否与上级费率类型不一致，或者少于下级通卡公司数量，或者与下级费率类型不一致
     * @param request
     * @param merchantBean
     * @return
     */
    @RequestMapping("enterprise/checkRelationMerchantProviderAndRateType")
    public @ResponseBody DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(HttpServletRequest request, @RequestBody MerchantBean merchantBean) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            response = merchantService.checkRelationMerchantProviderAndRateType(merchantBean);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    
    //查询分页
    @RequestMapping("enterprise/findMerDiscountList")
    public @ResponseBody DodopalResponse<DodopalDataPage<MerchantDiscount>> findMerDiscountList(HttpServletRequest request, @RequestBody MerchantDiscountQuery query) {
        DodopalResponse<DodopalDataPage<MerchantDiscount>> response = new DodopalResponse<DodopalDataPage<MerchantDiscount>>();
        try {
            response = merDiscountService.findMerDiscountByPage(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }

    //启用/停用
    @RequestMapping("enterprise/batchStartMerOperator")
    public @ResponseBody DodopalResponse<Integer> startMerchnatUser(HttpServletRequest request, @RequestBody Map<String, Object> operation) {
    	DodopalResponse<Integer> response = new DodopalResponse<Integer>();
    	String id = (String) operation.get("ids");
    	List<String> ids = new ArrayList<String>();
    	ids.add(id);
    	if("0".equals(operation.get("activate"))){
    		response = merDiscountService.startOrStopMerDiscount(ActivateEnum.ENABLE.getCode(), ids);
    	}else{
    		response = merDiscountService.startOrStopMerDiscount(ActivateEnum.DISABLE.getCode(), ids);
    	}
    	return response;
    }
    
    
  //解除折扣
    @RequestMapping("enterprise/unbind")
    public @ResponseBody DodopalResponse<Integer> unbind(HttpServletRequest request, @RequestBody Map<String, Object> operation) {
    	Map<String,Object> map = new HashMap<String, Object>();
//    	List merCode = (List) operation.get("merCode");
    	map.put("merCode", operation.get("merCode"));
    	map.put("discountIds", operation.get("discountIds"));
    	DodopalResponse<Integer> response = merDiscountService.unbind(map);
    	return response;
    }
    
    //绑定折扣
    @RequestMapping("enterprise/bind")
    public @ResponseBody DodopalResponse<Integer> bind(HttpServletRequest request, @RequestBody Map<String, Object> pa) {
    	String merId = (String)pa.get("merId");
    	ArrayList ids = (ArrayList)pa.get("ids");
    	DodopalResponse<Integer> response = merDiscountService.bind(merId, ids);
    	return response;
    }
    
    //查询交易折扣
    @RequestMapping("enterprise/findAllTranDiscount")
    public @ResponseBody DodopalResponse<DodopalDataPage<MerchantDiscount>> findAllTranDiscount(HttpServletRequest request, @RequestBody MerchantDiscountQuery query) {
        DodopalResponse<DodopalDataPage<MerchantDiscount>> response = new DodopalResponse<DodopalDataPage<MerchantDiscount>>();
        try {
            response = merDiscountService.findAllTranDiscountByPage(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        response.setCode(ResponseCode.SUCCESS);
        return response;
    }
    /**************************************************** 审核通过商户信息结束 *****************************************************/

  /**************************************************** 未审核商户信息开始 *****************************************************/
  @RequestMapping("enterprise/unverifyMgmt")
  public ModelAndView unverifyMgmt(HttpServletRequest request) {
      return new ModelAndView("merchant/enterprise/unverifyMgmt");
  }
  
  
   /** 
     * @Title: findUnverifyMgmt 
     * @param request
     * @param merCode
     * @return    设定文件 
     * DodopalResponse<MerchantBean>    返回类型 
     * @throws 
     */
  @RequestMapping("enterprise/findUnverifyMgmt")
  public ModelAndView findUnverifyMgmt(HttpServletRequest request, Model model,@RequestParam("merCode") String merCode) {
      try {
        MerchantBean merUser = new MerchantBean();
        merUser.setMerCode(merCode);
        DodopalResponse<MerchantBean> result = merchantService.findInfoByMerCode(merUser,MerStateEnum.NO_AUDIT);
        model.addAttribute("merchant",result.getResponseEntity());
      } catch (Exception e) {
          e.printStackTrace();
      }
      return new ModelAndView("merchant/enterprise/unverifyMgmtView");
  }
  /**
   * 审核商户信息
   * @param request
   * @param merchantBeans
   * @return
   */
  @RequestMapping("enterprise/editUnverifyMerchants")
  public @ResponseBody DodopalResponse<String> editUnverifyMerchants(HttpServletRequest request, @RequestBody MerchantBean merchantBeans) {
      DodopalResponse<String> response = new DodopalResponse<String>();
      try {
    	  HttpSession session = request.getSession();
  		  User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
  		  merchantBeans.setMerStateUser(user.getId());
    	  response= merchantService.updateMerchantUserBusRate(merchantBeans);
      }
      catch (DDPException ddp) {
          response.setCode(ResponseCode.OSS_ROLE_VALIDATOR);
          response.setMessage(ddp.getMessage());
      }
      catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.OSS_USER_CONNECTION_ERROR);
      }
      return response;
  }
  
  /**************************************************** 未审核商户信息结束 *****************************************************/
   /**************************************************** 审核不通过商户信息开始 *****************************************************/
   @RequestMapping("enterprise/notverifiedMgmt")
   public ModelAndView notverifiedMgmt(HttpServletRequest request) {
       return new ModelAndView("merchant/enterprise/notverifiedMgmt");
   }
   
   //删除审核不通过商户信息
   @RequestMapping("enterprise/delNotverifMerchant")
   public @ResponseBody DodopalResponse<String> delNotverifMerchant(HttpServletRequest request,  @RequestBody String[] merCodes) {
       DodopalResponse<String> response = new DodopalResponse<String>();
       try {
           if(merCodes != null && merCodes.length > 0) {
               HttpSession session = request.getSession();
               List<String> merCode =new ArrayList<String>(Arrays.asList(merCodes));
               User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
               String upUser=user.getId();
               DodopalResponse<Integer> delNot=  merchantService.delNotverifMerchants(merCode,upUser);
               response.setCode(delNot.getCode());
           } else {
               response.setCode(ResponseCode.OSS_DELETE_NOT_MERCHANT);
           }
       }catch (Exception e) {
           e.printStackTrace();
           response.setCode(ResponseCode.OSS_USER_CONNECTION_ERROR);
       }
       return response;
   }
   /**************************************************** 审核不通过商户信息结束 *****************************************************/
   /**************************************************** 数据字典加载开始 *****************************************************/

   @RequestMapping("enterprise/findDdic")
   public @ResponseBody DodopalResponse<List<Ddic>> findDdic(HttpServletRequest request, @RequestBody Ddic ddic) {
       DodopalResponse<List<Ddic>> response = new DodopalResponse<List<Ddic>>();
       try {
           List<Ddic> result = ddicService.findDdics(ddic);
           if(result!=null){
               response.setCode(ResponseCode.SUCCESS);
           }
       }
       catch (Exception e) {
           e.printStackTrace();
           response.setCode(ResponseCode.UNKNOWN_ERROR);
       }
       return response;
   }
   /**************************************************** 数据字典加载结束 *****************************************************/
   /**************************************************** 通卡公司数据加载开始 *****************************************************/
    /**
     * 查看上级通卡公司信息
     * @param request
     * @return
     */
     @RequestMapping("enterprise/findYktInfo")
    public @ResponseBody DodopalResponse<List<MerBusRateBean>> findYktInfo(HttpServletRequest request,@RequestBody String merParentCode,  @RequestParam(value = "rateCode", required = false) String rateCode, @RequestParam(value = "merType", required = false) String merType) {
        DodopalResponse<List<MerBusRateBean>> response = new DodopalResponse<List<MerBusRateBean>>();
        
        try {
            if(StringUtils.isNotBlank(merParentCode)) 
                merParentCode = merParentCode.replace("=", "");
           response = merchantService.findProductList(merParentCode, rateCode, merType);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
     
     /**
      * 查看通卡公司信息
      * @param request
      * @return
      */
      @RequestMapping("enterprise/findMerProductList")
     public @ResponseBody DodopalResponse<List<MerBusRateBean>> findMerProductList(HttpServletRequest request,@RequestBody String merCode, @RequestParam(value = "rateCode", required = false) String rateCode) {
         DodopalResponse<List<MerBusRateBean>> response = new DodopalResponse<List<MerBusRateBean>>();
         try {
            response = merchantService.findMerProductList(merCode, rateCode);
         }
         catch (Exception e) {
             e.printStackTrace();
             response.setCode(ResponseCode.UNKNOWN_ERROR);
         }
         return response;
     }
     
     
    /**
     * 加载通卡公司费率表 --通卡公司勾选点击确定加载费率信息
     * @param request
     * @param productCodes
     * @return
     */
    @RequestMapping("enterprise/loadYktInfo")
    public @ResponseBody DodopalResponse<List<MerBusRateBean>> loadYktInfo(HttpServletRequest request, @RequestBody String[] productCodes,@RequestParam("merParentCode") String merParentCode, @RequestParam(value = "rateCode", required = false) String rateCode, @RequestParam(value = "merType", required = false) String merType) {
        DodopalResponse<List<MerBusRateBean>> response = new DodopalResponse<List<MerBusRateBean>>();
        try {
          response = merchantService.loadYktInfo(productCodes,merParentCode,rateCode,merType);
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
    }
     
    
   /**************************************************** 通卡公司数据加载结束 *****************************************************/
    /**
     * 初始化加载业务类型
     * @param request
     * @return
     */
    @RequestMapping("enterprise/findPrdRateBean")
    public @ResponseBody DodopalResponse<List<PrdRateBean>> findPrdRateBean(HttpServletRequest request, @RequestParam(value = "merType", required = false) String merType) {
        DodopalResponse<List<PrdRateBean>> response = new DodopalResponse<List<PrdRateBean>>();
        try {
          response = prdRateService.findPrdRate(merType);
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
    }
    
    
    /**
     * 根据商户编码获取上级商户业务类型
     * @param request
     * @param merCode
     * @return
     */
    @RequestMapping("enterprise/findMerRateSupplementByCode")
    public @ResponseBody DodopalResponse<List<MerRateSupplementBean>> findMerRateSupplementByCode(HttpServletRequest request ,@RequestBody String merParentCode, @RequestParam(value = "merType", required = false) String merType) {
        DodopalResponse<List<MerRateSupplementBean>> response = new DodopalResponse<List<MerRateSupplementBean>>();
        try {
        response = merchantService.findMerRateSupplementByCode(merParentCode, merType);
        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
     return response;
    }
    /***************************************************************已审核商户数据导出start****************************/
    @RequestMapping("enterprise/exportExcelVerified")
    public @ResponseBody DodopalResponse<String> exportExcelVerified(HttpServletRequest req, HttpServletResponse res) {
        DodopalResponse<String> resp = new DodopalResponse<String>();
        MerchantQuery merquery = new MerchantQuery();
        merquery.setMerName(req.getParameter("merNameQuery"));
        merquery.setMerCode(req.getParameter("merCodeQuery"));
        merquery.setMerParentName(req.getParameter("merParentNameQuery"));
        merquery.setMerType(req.getParameter("merTypeQuery"));
        merquery.setMerClassify(req.getParameter("merClassifyQuery"));
        merquery.setMerPro(req.getParameter("merProQuery"));
        merquery.setMerCity(req.getParameter("merCityQuery"));
        merquery.setActivate(req.getParameter("activateQuery"));
        merquery.setSource(req.getParameter("sourceQuery"));
        merquery.setBussQuery(req.getParameter("bussQuery"));
        merquery.setMerState(req.getParameter("merState"));
        int exportMaxNum = 10000;
        merquery.setPage(new PageParameter(1, exportMaxNum));
        int merChantCount = merchantExpService.exportExcelCountService(merquery);
        if(merChantCount>exportMaxNum){
              resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
              return resp;
        }
        List<MerchantExcelBean> list = merchantExpService.exportExcelVerifiedService(merquery);
        String sheetName = new String("已审核商户信息");
        Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
        String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
        resp.setCode(resultCode);        
        return resp;
    }
    /***************************************************************已审核商户数据导出end****************************/

    /***************************************************************未审核商户数据导出start****************************/
    @RequestMapping("enterprise/exportExcelUnverify")
    public @ResponseBody DodopalResponse<String> exportExcelUnverify(HttpServletRequest req, HttpServletResponse res) {
        DodopalResponse<String> resp = new DodopalResponse<String>();
        MerchantQuery merquery = new MerchantQuery();
        merquery.setMerName(req.getParameter("merNameQuery"));
        merquery.setMerParentName(req.getParameter("merParentNameQuery"));
        merquery.setMerLinkUser(req.getParameter("merLinkUser"));
        merquery.setMerLinkUserMobile(req.getParameter("merLinkUserMobile"));
        merquery.setMerPro(req.getParameter("merProQuery"));
        merquery.setMerCity(req.getParameter("merCityQuery"));
        merquery.setMerState(req.getParameter("merState"));
        int exportMaxNum = 10000;
        merquery.setPage(new PageParameter(1, exportMaxNum));
        int merChantCount = merchantExpService.exportExcelCountService(merquery);
        if(merChantCount>exportMaxNum){
              resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
              return resp;
        }
        List<MerchantUnauditedExpBean> list = merchantExpService.exportExcelUnauditedService(merquery);
        String sheetName = new String("未审核商户信息");
        Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
        String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
        resp.setCode(resultCode);        
        return resp;
    }
    /***************************************************************未审核商户数据导出end****************************/
    /***************************************************************审核不通过商户数据导出start****************************/
    @RequestMapping("enterprise/exportExcelNotverified")
    public @ResponseBody DodopalResponse<String> exportExcelNotverified(HttpServletRequest req, HttpServletResponse res) {
        DodopalResponse<String> resp = new DodopalResponse<String>();
        MerchantQuery merquery = new MerchantQuery();
        merquery.setMerName(req.getParameter("merNameQuery"));
        merquery.setMerParentName(req.getParameter("merParentNameQuery"));
        merquery.setMerLinkUserMobile(req.getParameter("merLinkUserMobile"));
        merquery.setMerPro(req.getParameter("merProQuery"));
        merquery.setMerCity(req.getParameter("merCityQuery"));
        merquery.setMerState(req.getParameter("merState"));
        int exportMaxNum = 10000;
        merquery.setPage(new PageParameter(1, exportMaxNum));
        int merChantCount = merchantExpService.exportExcelCountService(merquery);
        if(merChantCount>exportMaxNum){
              resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
              return resp;
        }
        List<MerchantNotUnauditedExpBean> list = merchantExpService.exportExcelNotUnauditedService(merquery);
        String sheetName = new String("审核不通过商户信息");
        Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
        String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
        resp.setCode(resultCode);        
        return resp;
    }
    /***************************************************************审核不通过商户数据导出end****************************/

}
