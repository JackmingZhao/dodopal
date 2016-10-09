package com.dodopal.oss.web.controller;

import java.util.Arrays;
import java.util.Date;
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
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.MerUserCardBDBean;
import com.dodopal.oss.business.bean.MerUserCardBDLogBean;
import com.dodopal.oss.business.bean.MerchantUserBean;
import com.dodopal.oss.business.bean.MerchantUserExpBean;
import com.dodopal.oss.business.bean.query.MerUserCardBDFindBean;
import com.dodopal.oss.business.bean.query.UserCardLogQuery;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.MerchantUserQuery;
import com.dodopal.oss.business.service.MerUserService;



@Controller
@RequestMapping("/merchant")
public class MerUserController {
    private Logger logger = LoggerFactory.getLogger(MerUserController.class);
    @Autowired
    MerUserService merUserService;
    @Autowired
    private ExpTempletService expTempletService;
    
    /****************************************************查询个人户信息开始 *****************************************************/
    /**
     * 个人用户信息初始页面
     * @param request
     * @return
     */
    @RequestMapping("personal/userList")
    public ModelAndView userList(HttpServletRequest request) {
        return new ModelAndView("merchant/personal/userList");
    }
    
  /**
  * 查询个人用户列表信息
  * @param request
  * @param findMerUsers
  * @return
  */
  @RequestMapping("personal/findMerUsers")
 public @ResponseBody DodopalResponse<List<MerchantUserBean>> findMerUsers(HttpServletRequest request,@RequestBody MerchantUserBean merUserBean) {
     DodopalResponse<List<MerchantUserBean>> response = new DodopalResponse<List<MerchantUserBean>>();
     try {
    	 response = merUserService.findMerUsers(merUserBean);
     }
     catch (Exception e) {
    	 e.printStackTrace();
         response.setCode(ResponseCode.UNKNOWN_ERROR);
     }
     return response;
 }
  /**
   * 查询个人用户列表信息后台分页
   * @param request
   * @param findMerUsers
   * @return
   */
   @RequestMapping("personal/findMerUsersByPage")
  public @ResponseBody DodopalResponse<DodopalDataPage<MerchantUserBean>> findMerUsersByPage(HttpServletRequest request,@RequestBody MerchantUserQuery merUser) {
       DodopalResponse<DodopalDataPage<MerchantUserBean>> response = new DodopalResponse<DodopalDataPage<MerchantUserBean>>();
      try {
          response = merUserService.findMerUsersByPage(merUser);
      }catch (Exception e) {
          e.printStackTrace();
          response.setCode(ResponseCode.UNKNOWN_ERROR);
      }
      return response;
  }
  
    /**************************************************** 审核通过商户信息结束 *****************************************************/

  /**
   * 查看用户详情
   * @param request
   * @param userId 用户ID
   * @return
   */
   @RequestMapping("personal/findUser")
  public @ResponseBody DodopalResponse<MerchantUserBean> findUser(HttpServletRequest request,@RequestBody String userId) {
      DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
      try {
     	 response = merUserService.findMerUser(userId);
      }
      catch (Exception e) {
     	 e.printStackTrace();
          response.setCode(ResponseCode.UNKNOWN_ERROR);
      }
      return response;
  }
   
   /**
    * 查看用户详情
    * @param model
    * @param request
    * @param userId 用户ID
    * @return
    */
   @RequestMapping("personal/findMerUser")
   public ModelAndView findMerUser(Model model, HttpServletRequest request,@RequestParam String userId) {
	   DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
	   try {
	     	 response = merUserService.findMerUser(userId);	     	
	     	 model.addAttribute("user",response.getResponseEntity());
	     	 
	      }
	      catch (Exception e) {
	     	 e.printStackTrace();
	          response.setCode(ResponseCode.UNKNOWN_ERROR);
	      }
	   
       return new ModelAndView("merchant/personal/userView");
   }
   
   
   
   /** 
 * @Title: viewMerUser 
 * @Description: 企业用户信息详情查看
 * @param request
 * @param userId
 * @return    设定文件 
 * DodopalResponse<MerchantUserBean>    返回类型 
 * @throws 
 */
   @RequestMapping("personal/viewMerUser")
   public @ResponseBody DodopalResponse<MerchantUserBean> viewMerUser(HttpServletRequest request,@RequestBody String userId) {
       DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
       try {
             response = merUserService.findMerUser(userId); 
             //   model.addAttribute("user",response.getResponseEntity());
          } catch (Exception e) {
             e.printStackTrace();
              response.setCode(ResponseCode.UNKNOWN_ERROR);
          }
       
       return response;
   }
   
   /**
    * 编辑用户详情获取用户信息
    * @param model
    * @param request
    * @param userId 用户ID
    * @return
    */
   @RequestMapping("personal/editMerUser")
   public @ResponseBody DodopalResponse<MerchantUserBean> editMerUser(Model model, HttpServletRequest request,@RequestParam String userId) {
       DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
       try {
             response = merUserService.findMerUser(userId);         
             model.addAttribute("user",response.getResponseEntity());
          }
          catch (Exception e) {
             e.printStackTrace();
              response.setCode(ResponseCode.UNKNOWN_ERROR);
          }
       
       return response;
   }
   /** 
 * @Title: startUser 
 * @Description: 启用用户
 * @param model
 * @param request
 * @param userId
 * DodopalResponse<Map<String,String>>    返回类型 
 * @throws 
 */
@RequestMapping("personal/startOrStopUser")
   public @ResponseBody DodopalResponse<Map<String,String>> startOrStopUser(Model model, HttpServletRequest request,@RequestBody String[] ids,@RequestParam("activate") String activate) {
       DodopalResponse<Map<String,String>> response = new DodopalResponse<Map<String,String>>();
       List <String> idList = Arrays.asList(ids);
       try {
           User loginUser = (User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
             response = merUserService.startOrStopUser(idList, activate,loginUser.getId());         
          }catch (Exception e) {
             e.printStackTrace();
              response.setCode(ResponseCode.UNKNOWN_ERROR);
          }
       
       return response;
   }
   
   /**
    * 更新用户详情
    * @param model
    * @param request
    * @param userId 用户ID
    * @return
    */
   @RequestMapping("personal/updateMerUser")
   public @ResponseBody DodopalResponse<Boolean> updateMerUser(HttpServletRequest request,@RequestBody MerchantUserBean merUserBean) {
       DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
       try {
           User loginUser = (User)request.getSession().getAttribute(OSSConstants.SESSION_USER);
           merUserBean.setUpdateUser(loginUser.getId());
             if(DDPStringUtil.isMobile(merUserBean.getMerUserMobile())){
                 response = merUserService.updateMerUser(merUserBean);         
             }else{
                 response.setCode(ResponseCode.USERS_MOB_TEL_ERR);
             }
          }
          catch (Exception e) {
             e.printStackTrace();
              response.setCode(ResponseCode.UNKNOWN_ERROR);
          }
       
       return response;
   }
   
   /**
    * 用户重置密码   
    * @param request
    * @param userId 用户ID
    * @return
    */
   @RequestMapping("personal/resetPwd")
   public @ResponseBody DodopalResponse<String> resetPwd(HttpServletRequest request,@RequestBody String userId) {
	   DodopalResponse<String> response = new DodopalResponse<String>();
	   try {
	     	 response = merUserService.resetPwd(userId);
	      }
	      catch (Exception e) {
	     	 e.printStackTrace();
	          response.setCode(ResponseCode.UNKNOWN_ERROR);
	      }
	   
       return response;
   }
   
   /**************************************************** 用户卡片管理       开始*****************************************************/
   /**
    * 个人用户卡片信息初始页面
    * @param request
    * @return
    */
   @RequestMapping("personal/userCardList")
   public ModelAndView userCardList(HttpServletRequest request) {
       return new ModelAndView("merchant/personal/userCardList");
   }
   
   /**
    * 查询个人用户卡片列表信息
    * @param request
    * @param findMerUsers
    * @return
    */
    @RequestMapping("personal/findUserCards")
   public @ResponseBody DodopalResponse<List<MerUserCardBDBean>> findUserCards(HttpServletRequest request,@RequestBody MerUserCardBDFindBean bean) {
       DodopalResponse<List<MerUserCardBDBean>> response = new DodopalResponse<List<MerUserCardBDBean>>();
       try {
      	 response = merUserService.findMerUserCards(bean);
       }
       catch (Exception e) {
      	 e.printStackTrace();
           response.setCode(ResponseCode.UNKNOWN_ERROR);
       }
       return response;
   }
    
    /**
     * 查询个人用户卡片列表信息(后台分页)
     * @param request
     * @param findUserCardsByPage
     * @return
     */
     @RequestMapping("personal/findUserCardsByPage")
    public @ResponseBody DodopalResponse<DodopalDataPage<MerUserCardBDBean>> findUserCardsByPage(HttpServletRequest request,@RequestBody MerUserCardBDFindBean bean) {
    	 DodopalResponse<DodopalDataPage<MerUserCardBDBean>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDBean>>();
        try {
       	 response = merUserService.findMerUserCardBDListByPage(bean);
        }
        catch (Exception e) {
       	 e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
    
    /**
     * 解绑卡片
     * @param request
     * @param findMerUsers
     * @return
     */
     @RequestMapping("personal/unbundUserCards")
    public @ResponseBody DodopalResponse<String> unbundUserCards(HttpServletRequest request,@RequestBody String[] userCardIds) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
        	HttpSession session = request.getSession();
    		User user =(User)session.getAttribute(OSSConstants.SESSION_USER);
        	List<String> ls = Arrays.asList(userCardIds);
       	    response = merUserService.unbundUserCards(user.getName(),ls,user.getId());
        }
        catch (Exception e) {
       	 e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }
     
     /**
      * 卡片操作日志初始页面
      * @param request
      * @return
      */
     @RequestMapping("personal/userCardLog")
     public ModelAndView userCardLog(HttpServletRequest request) {
         return new ModelAndView("merchant/personal/userCardLog");
     }
     
     @RequestMapping("personal/findUserCardsLog")
     public @ResponseBody DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> findUserCardsLog(HttpServletRequest request,@RequestBody UserCardLogQuery query) {
     	 DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>>();
         try {
        	 response = merUserService.findUserCardLogByPage(query);
         }
         catch (Exception e) {
        	 e.printStackTrace();
             response.setCode(ResponseCode.UNKNOWN_ERROR);
         }
         return response;
     }
   /**************************************************** 用户卡片管理       结束*****************************************************/

     
     /****************************************************查询个人户信息开始 *****************************************************/
     /**
      * 企业用户信息初始页面
      * @param request
      * @return
      */
     @RequestMapping("personal/merchantList")
     public ModelAndView merchantList(HttpServletRequest request) {
         return new ModelAndView("merchant/personal/merchantList");
     }
     
     /**
      * 查询企业用户列表信息
      * @param request
      * @param findMerUsers
      * @return
      */
      @RequestMapping("personal/findMerchantUsers")
     public @ResponseBody DodopalResponse<List<MerchantUserBean>> findMerchantUsers(HttpServletRequest request,@RequestBody MerchantUserBean merUserBean) {
         DodopalResponse<List<MerchantUserBean>> response = new DodopalResponse<List<MerchantUserBean>>();
         try {
        	 response = merUserService.findMerUsers(merUserBean);
         }
         catch (Exception e) {
        	 e.printStackTrace();
             response.setCode(ResponseCode.UNKNOWN_ERROR);
         }
         return response;
     }
      //用户信息列表导出
      @RequestMapping("personal/exportExcelMerUserListControl")
      public @ResponseBody DodopalResponse<String> exportExcelMerUserList(HttpServletRequest req,  HttpServletResponse res) {
          DodopalResponse<String> resp = new DodopalResponse<String>();
          MerchantUserQuery merUser = new MerchantUserQuery();
          merUser.setMerUserName(req.getParameter("merUserName"));
          merUser.setMerUserNickName(req.getParameter("merUserNickName"));
          merUser.setMerUserType("0");
          if (StringUtils.isNotBlank(req.getParameter("createDateStart"))) {
              Date createDateStart = DateUtils.stringtoDate(req.getParameter("createDateStart"), DateUtils.DATE_SMALL_STR);
              merUser.setCreateDateStart(createDateStart);
          }
          if (StringUtils.isNotBlank(req.getParameter("createDateEnd"))) {
              Date createDateEnd = DateUtils.stringtoDate(req.getParameter("createDateEnd"), DateUtils.DATE_SMALL_STR);
              merUser.setCreateDateEnd(createDateEnd);
          }
          merUser.setMerUserMobile(req.getParameter("merUserMobile"));
          merUser.setMerUserSex(req.getParameter("merUserSex"));
          int exportMaxNum = ExcelUtil.EXPORT_MAX_COUNT;
          merUser.setPage(new PageParameter(1, exportMaxNum));
          List<MerchantUserBean> list = merUserService.getExportExcelMerUserList(merUser);
          int resultSize = list.size();
          if(resultSize > exportMaxNum) {
              logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
              resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
              return resp;
          }
          String sheetName = new String("用户信息");
          Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
          String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
          resp.setCode(resultCode);
          return resp;
      }
      
    //用户卡片信息列表导出
      @RequestMapping("personal/exportExcelMerUserCardListControl")
      public @ResponseBody DodopalResponse<String> exportExcelMerUserCardList(HttpServletRequest req,  HttpServletResponse res) {
          DodopalResponse<String> resp = new DodopalResponse<String>();
          MerUserCardBDFindBean merUserCardBDFindBean = new MerUserCardBDFindBean();
          merUserCardBDFindBean.setMerUserName(req.getParameter("merUserName"));
          merUserCardBDFindBean.setMerUserNameName(req.getParameter("merUserNameName"));
          merUserCardBDFindBean.setCardCode(req.getParameter("cardCode"));
          merUserCardBDFindBean.setCardType(req.getParameter("cardType"));
          if (StringUtils.isNotBlank(req.getParameter("bundLingDateStart"))) {
              Date bundLingDateStart = DateUtils.stringtoDate(req.getParameter("bundLingDateStart"), DateUtils.DATE_SMALL_STR);
              merUserCardBDFindBean.setBundLingDateStart(bundLingDateStart);
          }
          if (StringUtils.isNotBlank(req.getParameter("bundLingDateEnd"))) {
              Date bundLingDateEnd = DateUtils.stringtoDate(req.getParameter("bundLingDateEnd"), DateUtils.DATE_SMALL_STR);
              merUserCardBDFindBean.setBundLingDateEnd(bundLingDateEnd);
          }
          if (StringUtils.isNotBlank(req.getParameter("unBundLingDateStart"))) {
              Date unBundLingDateStart = DateUtils.stringtoDate(req.getParameter("unBundLingDateStart"), DateUtils.DATE_SMALL_STR);
              merUserCardBDFindBean.setUnBundLingDateStart(unBundLingDateStart);
          }
          if (StringUtils.isNotBlank(req.getParameter("unBundLingDateEnd"))) {
              Date unBundLingDateEnd = DateUtils.stringtoDate(req.getParameter("unBundLingDateEnd"), DateUtils.DATE_SMALL_STR);
              merUserCardBDFindBean.setUnBundLingDateEnd(unBundLingDateEnd);
          }
          int exportMaxNum = ExcelUtil.EXPORT_MAX_COUNT;
          merUserCardBDFindBean.setPage(new PageParameter(1, exportMaxNum));
          List<MerUserCardBDBean> list = merUserService.getExportExcelMerUserCardList(merUserCardBDFindBean);
          int resultSize = list.size();
          if(resultSize > exportMaxNum) {
              logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
              resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
              return resp;
          }
          String sheetName = new String("用户卡片管理");
          Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
          String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
          resp.setCode(resultCode);
          return resp;
      }
      //操作日志列表导出
      @RequestMapping("personal/exportExcelUserCardLogControl")
      public @ResponseBody DodopalResponse<String> exportExcelUserCardLog(HttpServletRequest req,  HttpServletResponse res) {
          DodopalResponse<String> resp = new DodopalResponse<String>();
          UserCardLogQuery query = new UserCardLogQuery();
          query.setMerUserName(req.getParameter("merUserName"));
          //query.setUserCode(req.getParameter("userCode"));
          query.setCode(req.getParameter("code"));
          query.setOperName(req.getParameter("operName"));
          query.setSource(req.getParameter("source"));
          query.setCardType(req.getParameter("cardType"));
          int exportMaxNum = ExcelUtil.EXPORT_MAX_COUNT;
          query.setPage(new PageParameter(1, exportMaxNum));
          List<MerUserCardBDLogBean> list = merUserService.getExportExcelUserCardLog(query);
          int resultSize = list.size();
          if(resultSize > exportMaxNum) {
              logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
              resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
              return resp;
          }
          String sheetName = new String("卡片操作日志管理");
          Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
          String resultCode = ExcelUtil.excelExport(req,res, list, col, sheetName);
          resp.setCode(resultCode);
          return resp;
      }
      /***************************************************************商户用户信息数据导出start****************************/
      @RequestMapping("personal/exportExcelMerchantUser")
      public @ResponseBody DodopalResponse<String> exportExcelMerchantUser(HttpServletRequest req, HttpServletResponse res) {
          DodopalResponse<String> resp = new DodopalResponse<String>();
          DodopalResponse<DodopalDataPage<MerchantUserExpBean>> response = new DodopalResponse<DodopalDataPage<MerchantUserExpBean>>();
          try {
              MerchantUserQuery merquery = new MerchantUserQuery();
              merquery.setMerUserType(req.getParameter("merUserType"));
              merquery.setMerUserName(req.getParameter("merUserName"));
              merquery.setMerUserMobile(req.getParameter("merUserMobile"));
              merquery.setMerUserNickName(req.getParameter("merUserNickName"));
              merquery.setMerUserSex(req.getParameter("merUserSex"));
              merquery.setMerUserFlag(req.getParameter("merUserFlag"));
              merquery.setMerchantName(req.getParameter("merchantName"));
              merquery.setMerchantState(req.getParameter("merchantState"));
              merquery.setMerchantType(req.getParameter("merchantType"));
              merquery.setActivate(req.getParameter("activate"));
              if(StringUtils.isNotBlank(req.getParameter("createDateStart"))){
                  merquery.setCreateDateStart(DateUtils.stringtoDate(req.getParameter("createDateStart"), DateUtils.DATE_SMALL_STR));  
              }
              if(StringUtils.isNotBlank(req.getParameter("createDateEnd"))){
                  merquery.setCreateDateEnd(DateUtils.stringtoDate(req.getParameter("createDateEnd"), DateUtils.DATE_SMALL_STR));  
              }
              merquery.setMerUserSource(req.getParameter("merUserSource"));
              
              int exportMaxNum = 10000;
              merquery.setPage(new PageParameter(1,1));
              Integer merChantCount = merUserService.findMerUsersByPage(merquery).getResponseEntity().getRows();
              if(merChantCount>exportMaxNum){
                    logger.info("merChantCount :"+merChantCount+"or"+" exportMaxNum:"+exportMaxNum);
                    resp.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                    return resp;
              }
              merquery.setPage(new PageParameter(1,merChantCount));
              response =  merUserService.findMerUsersExpByPage(merquery);
              String sheetName = new String("商户用户信息");
              Map<String, String> col = expTempletService.getCloName(req.getParameter("col").split("@"), req.getParameter("typeSelStr"));
              resp = ExcelUtil.ddpExcelExport(req, res, response, col, sheetName);
        }
        catch (Exception e) {
            logger.info("error:"+e);
        }
        return resp;
      }
      /***************************************************************商户用户信息数据导出end****************************/
}
