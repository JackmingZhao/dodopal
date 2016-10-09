package com.dodopal.portal.web.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.portal.business.bean.AccountFundBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.DdpService;
import com.dodopal.portal.business.service.MerchantUserService;
import com.dodopal.portal.business.service.PermissionService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月1日 下午4:52:34
 */

@Controller
public class HomeController extends CommonController {

    private final static Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    MerchantUserService merUserService;
    @Autowired
    DdpService ddpService;
    @Autowired
    PermissionService permissionService;

    @Autowired
    AccountSetService accountSetService;

    @RequestMapping("/index")
    public ModelAndView index(Model model, HttpServletRequest request) {
        //商户类型
        String merType = getMerType(request.getSession());
        ModelAndView mav = new ModelAndView();
        if (MerTypeEnum.PROVIDER.getCode().equals(merType)) {
            mav.setViewName("prvd/merchant");
        } else {
            String aType = ""; //个人or企业
            String merOrUserName = "";
            String custNum = ""; //用户号or商户号
            String availableMoney = "0.00";//可用余额
            String frozenMoney = "0.00"; //冻结金额
            String accountMoney = "0.00"; //资金账户可用余额
            String accountFuntMoney = "0.00"; //授信账户可用余额    
            String accountFrozenAmount = "0.00"; //资金账户 冻结金额
            String accountFundFrozenAmount = "0.00"; //授信账户 冻结金额

            HttpSession session = request.getSession();
            int bindCardCount = 0;
            String fundType = "";//主账户的资金类别
            String accountCode = "";//主账户编号
            String Acid = "";//主账户的数据库id

            //金额格式化 保留两位小数
            DecimalFormat df = new DecimalFormat("0.00");

            if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
                aType = MerUserTypeEnum.PERSONAL.getCode();
                custNum = getCurrentUserCode(request.getSession());
            } else {
                aType = MerUserTypeEnum.MERCHANT.getCode();
                custNum = getCurrentMerchantCode(request.getSession());
            }
            merOrUserName = getCurrentUserName(session);
            try {
                DodopalResponse<AccountFundBean> response = ddpService.findAccountBalance(aType, custNum);
                if (response.getResponseEntity() != null) {
                    AccountFundBean accountFundBean = response.getResponseEntity();
                    availableMoney = df.format(accountFundBean.getAvailableBalance());
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
                if (bindResponse.getResponseEntity() != null) {
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

            mav.setViewName("index");
        }
        return mav;
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        log.info("进入默认首页");
        return "forward:/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        PortalUserDTO user = (PortalUserDTO) request.getSession().getAttribute(PortalConstants.SESSION_USER);
        if (user == null) {
            return "login";
        }
        return "forward:/index";
    }

    @RequestMapping("/loginAction")
    public String loginAction(HttpServletRequest request, @RequestParam String loginName, @RequestParam String loginPasswd) {
        //        User user = new User();
        //        user.setLoginName(loginName);
        //        request.getSession().setAttribute(OSSConstants.SESSION_USER, user);
        return "forward:/index";
    }

    //前十条交易记录
    @RequestMapping("/findTraBeanList")
    public @ResponseBody DodopalResponse<List<TraTransactionBean>> findTraBeanList(HttpServletRequest request) {
        DodopalResponse<List<TraTransactionBean>> rtResponse = new DodopalResponse<List<TraTransactionBean>>();
        String merType = getMerType(request.getSession());
        //个人or企业
        String aType = "";
        //用户号or商户号
        String custNum = "";
        //区分 管理员 还是操作员
        String merUserFlag = "";
        //用户id
        String createUser = "";
        //前十条交易记录
        if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
            aType = MerUserTypeEnum.PERSONAL.getCode();
            custNum = getCurrentUserCode(request.getSession());
        } else {
            aType = MerUserTypeEnum.MERCHANT.getCode();
            custNum = getCurrentMerchantCode(request.getSession());
            DodopalResponse<MerchantUserBean> merchantBeans = accountSetService.findUserInfoById(getCurrentUserId(request.getSession()));
            merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
        }

        //判断用户是否为操作员
        if (MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)) {
            createUser = getCurrentUserId(request.getSession());
        }

        try {
            rtResponse = ddpService.findTraTransactionByCode(aType, custNum, createUser);
        }
        catch (Exception e) {
            rtResponse.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }

        return rtResponse;
    }

    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: sendAuthCode
     * @Description: 发送验证码
     * @param request
     * @param mobile
     * @return 设定文件 DodopalResponse<Map<String,String>> 返回类型
     * @throws
     */
    @RequestMapping("/sendAuthCode")
    public @ResponseBody DodopalResponse<Map<String, String>> sendAuthCode(HttpServletRequest request, @RequestBody String yzm) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        request.getSession().removeAttribute(PortalConstants.AUTHCODESTATUS);
        Date lastTime = (Date) request.getSession().getAttribute(PortalConstants.LASTSENDTIME);
        String sessionCaptcha = (String) request.getSession().getAttribute(PortalConstants.SESSION_KAPTCHATYPE_PERSON_RESETPWD);
        if (StringUtils.isNotBlank(sessionCaptcha) && sessionCaptcha.equals(yzm)) {
        } else {
            response.setCode(ResponseCode.USERS_MOB_CODE_ERR);
            // TODO 临时输入message , 后期会被数据库维护的message 替代
            return response;
        }
        if (null == lastTime) {
            request.getSession().setAttribute(PortalConstants.LASTSENDTIME, new Date());
        } else {
            Date nowTime = new Date();
            long d = 1000 * 24 * 60 * 60;
            long h = 1000 * 60 * 60;
            long m = 1000 * 60;
            if (((nowTime.getTime() - lastTime.getTime()) % d % h / m) < 2) {
                return null;
            }
        }
        //登录账号
        //DodopalResponse<String> loginResponse = merUserService.findMerUserNameByMobile(mobile);
        //检测手机号是否注册
        // if(ResponseCode.SUCCESS.equals(loginResponse.getCode())&&null!=loginResponse.getResponseEntity()){
        try {
            if (null != request.getSession().getAttribute(PortalConstants.PWDMOBILE)) {
                DodopalResponse<Map<String, String>> result = merUserService.sendAuthCode((String) request.getSession().getAttribute(PortalConstants.PWDMOBILE));
                request.getSession().setAttribute(PortalConstants.PWDSEQ, result.getResponseEntity().get(PortalConstants.PWDSEQ));
                Map<String, String> map = new HashMap<String, String>();
                map.put(PortalConstants.PWDSEQ, result.getResponseEntity().get(PortalConstants.PWDSEQ));
                response.setResponseEntity(map);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.USERS_MOB_NOT_EXIST);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        //        }else{
        //            response.setCode(loginResponse.getCode());
        //        }
        return response;
    }

    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: checkAuthCode
     * @Description: 检查验证码
     * @param request
     * @param authCode
     * @return 设定文件 DodopalResponse<String> 返回类型
     * @throws
     */
    @RequestMapping("/checkAuthCode")
    public @ResponseBody DodopalResponse<String> checkAuthCode(HttpServletRequest request, @RequestBody Map map) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            String authCode = (String) map.get("authCode");
            String seq = (String) map.get(PortalConstants.PWDSEQ);
            String mobile = (String) request.getSession().getAttribute(PortalConstants.PWDMOBILE);
            //String code = (String)request.getSession().getAttribute(PortalConstants.PWDRESET);
            response = merUserService.checkAuthCode(mobile, authCode, seq);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                //验证码通过，session更新验证码状态
                request.getSession().setAttribute(PortalConstants.AUTHCODESTATUS, "0");
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: checkMobileOrUserName
     * @Description: 检查用户名手机号是否被注册
     * @param request
     * @param mobileOrUserName
     * @return 设定文件 DodopalResponse<MerchantUserBean> 返回类型
     * @throws
     */
    @RequestMapping("/checkMobileOrUserName")
    public @ResponseBody DodopalResponse<MerchantUserBean> checkMobileOrUserName(HttpServletRequest request, @RequestBody String mobileOrUserName) {
        DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
        request.getSession().removeAttribute(PortalConstants.PWDMOBILE);
        try {
            response = merUserService.findUserInfoByMobileOrUserName(mobileOrUserName);
            MerchantUserBean bean = response.getResponseEntity();
            MerchantUserBean reBean = new MerchantUserBean();
            if (null != bean && StringUtils.isNotBlank(bean.getMerUserMobile())) {
                request.getSession().setAttribute(PortalConstants.RESETNAME, bean.getMerUserName());
                request.getSession().setAttribute(PortalConstants.PWDMOBILE, bean.getMerUserMobile());

                reBean.setMerUserMobile(bean.getMerUserMobile().substring(0, 3) + "****" + bean.getMerUserMobile().substring(7, bean.getMerUserMobile().length()));
                reBean.setMerUserName(bean.getMerUserName().substring(0, 2) + "****" + bean.getMerUserName().substring(bean.getMerUserName().length() - 2, bean.getMerUserName().length()));
            } else {
                response.setCode(ResponseCode.USERS_MOB_NOT_EXIST);
            }
            response.setResponseEntity(reBean);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    @RequestMapping("/modifyNewPWD")
    public @ResponseBody DodopalResponse<Boolean> modifyNewPWD(HttpServletRequest request, @RequestBody String pwd) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String status = (String) request.getSession().getAttribute(PortalConstants.AUTHCODESTATUS);
        if (StringUtils.isNotBlank(status) && "0".equals(status)) {
            String mobile = (String) request.getSession().getAttribute(PortalConstants.PWDMOBILE);
            response = merUserService.modifyPWD(mobile, pwd);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {
                return response;
            }
            return response;
        } else {
            return response;
        }
    }

    @RequestMapping("/toResetPWD")
    public ModelAndView toResetPWD(HttpServletRequest request) {
        return new ModelAndView("resetPWDPage");
    }

    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: resetSuccess
     * @Description: 密码重置后的展示页面
     * @param request
     * @param status
     * @return 设定文件 ModelAndView 返回类型
     * @throws
     */
    @RequestMapping("/resetSuccess")
    public ModelAndView resetSuccess(HttpServletRequest request, @RequestParam String status) {
        ModelAndView movi = new ModelAndView("resetPWDSucPage");
        String codecheck = (String) request.getSession().getAttribute(PortalConstants.AUTHCODESTATUS);
        if (StringUtils.isBlank(codecheck) && !"0".equals(codecheck)) {
            return new ModelAndView("resetPWDPage");
        }
        if (null != status && "true".equals(status)) {
            System.out.println((String) request.getSession().getAttribute(PortalConstants.RESETNAME));
            movi.addObject("mobile", (String) request.getSession().getAttribute(PortalConstants.PWDMOBILE));
            //TODO 常量或者前端code对应显示
            movi.addObject("info", PortalConstants.RESET_PWD_SUCCESS);
        } else {
            movi.addObject("info", PortalConstants.RESET_PWD_FAIL);
        }

        return movi;
    }

    @RequestMapping("/checkCaptcha")
    public @ResponseBody DodopalResponse<Boolean> checkCaptcha(HttpServletRequest request, @RequestParam String captcha, @RequestParam String captchaType) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String sessionCaptcha = null;
        if (PortalConstants.SESSION_KAPTCHATYPE_MER_REGISTER.equals(captchaType)) {
            sessionCaptcha = (String) request.getSession().getAttribute(PortalConstants.SESSION_KAPTCHATYPE_MER_REGISTER);
        } else if (PortalConstants.SESSION_KAPTCHATYPE_PERSON_REGISTER.equals(captchaType)) {
            sessionCaptcha = (String) request.getSession().getAttribute(PortalConstants.SESSION_KAPTCHATYPE_PERSON_REGISTER);
        } else if (PortalConstants.SESSION_KAPTCHATYPE_PERSON_RESETPWD.equals(captchaType)) {
            sessionCaptcha = (String) request.getSession().getAttribute(PortalConstants.SESSION_KAPTCHATYPE_PERSON_RESETPWD);
        }
        if (StringUtils.isNotBlank(sessionCaptcha) && sessionCaptcha.equals(captcha)) {
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(true);
        } else {
            response.setCode(ResponseCode.PORTAL_CAPTCHA_ERROR);
            //            response.setMessage("输入的验证码不正确");
        }
        return response;
    }

    /**
     * 服务协议
     * @param request
     * @return
     */
    @RequestMapping("/serviceAgreement")
    public ModelAndView serviceAgreement(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("serviceAgreement");
        return mav;
    }

    @RequestMapping("/notFound")
    public ModelAndView notFound(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notFound");
        return mav;
    }

    @RequestMapping("/errorPage")
    public ModelAndView errorPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    /**
     * 联合登陆
     * @param request
     * @param attr
     * @return
     */
    @RequestMapping("/unionLogin")
	public String unionLogin(HttpServletRequest request, RedirectAttributes attr) {
		String respCode = unionLoginCheck(request);
		if(log.isInfoEnabled()) {
			log.info("联合登录参数校验结果：" + respCode);
		}
		String backurl = request.getParameter("backurl");
		if (!ResponseCode.SUCCESS.equals(respCode)) {
			if (!ResponseCode.PORTAL_UNION_LOGIN_BACKURL_NULL.equals(respCode)) {
				attr.addAttribute("type", "4"); // 参数错误
				return "redirect:" + backurl;
			} else {
				return "forward:/login"; // 返回地址未传，则返回新系统登录页面
			}
		}

		//String username = request.getParameter("username");
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		DodopalResponse<PortalUserDTO> resp = permissionService.unionLogin(loginid, password, usertype);
		if (ResponseCode.SUCCESS.equals(resp.getCode())) { // 验证通过，登录新系统主页
			// 验证成功将登录名、密码放入session中
            request.getSession().setAttribute(PortalConstants.REG_USER_NAME, resp.getResponseEntity().getMerUserName());
            request.getSession().setAttribute(PortalConstants.REG_USER_PWD, password);
			return "unionLogin";
		} else if (ResponseCode.USERS_USER_NAME_OR_PWD_ERR.equals(resp.getCode())) {
			attr.addAttribute("type", "1"); // 用户名或密码错误
		} else if (ResponseCode.USERS_USER_AUTHORITY_NULL.equals(resp.getCode())) {
			attr.addAttribute("type", "2"); // 您没有权限，请联系管理员
		} else {
			attr.addAttribute("type", "3"); // 系统异常
		}
		return "redirect:" + backurl;
	}

    private String unionLoginCheck(HttpServletRequest request) {
    	String signType = request.getParameter("sign_type");
    	String sign = request.getParameter("sign");
    	String inputCharset = request.getParameter("input_charset");
    	String reqdate = request.getParameter("reqdate");
    	String backurl = request.getParameter("backurl");

    	//String username = request.getParameter("username");
    	String loginid = request.getParameter("loginid");
    	String password = request.getParameter("password");
    	String usertype = request.getParameter("usertype");

    	// TODO:签名秘钥
    	String key = "123";

    	if (StringUtils.isBlank(inputCharset)) {
			return ResponseCode.PRODUCT_REQ_PARAM_INPUT_CHARSET_NULL;
		}

		if (StringUtils.isBlank(signType)) {
			return ResponseCode.PRODUCT_REQ_PARAM_SIGN_TYPE_ERROR;
		}
		
		if (StringUtils.isBlank(sign)) {
			return ResponseCode.PRODUCT_REQ_PARAM_SIGN_NULL;
		}

		if (StringUtils.isBlank(reqdate)) {
			return ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR;
		}
		if (reqdate.length() != 14) {
			return ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR;
		}
		Date date = DateUtils.stringtoDate(reqdate, DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
		if (date == null) {
			return ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR;
		}

		if (StringUtils.isBlank(backurl)) {
			return ResponseCode.PORTAL_UNION_LOGIN_BACKURL_NULL;
		}

		if (StringUtils.isBlank(loginid)) {
			return ResponseCode.USERS_FIND_USER_NAME_NULL;
		}

		if (StringUtils.isBlank(password)) {
			return ResponseCode.USERS_PWD_NULL;
		}

		if (StringUtils.isBlank(usertype)) {
			return ResponseCode.USERS_FIND_USER_NAME_NULL;
		}

		Map<String, String>  signMap =  new  HashMap<String, String>();
		signMap.put("input_charset", inputCharset);
		signMap.put("reqdate", reqdate);
		signMap.put("backurl", backurl);
		signMap.put("loginid", loginid);
		signMap.put("password", password);
		signMap.put("usertype", usertype);

		String signNew = SignUtils.sign(SignUtils.createLinkString(signMap), key, CommonConstants.CHARSET_UTF_8);
		if(!signNew.equals(sign) ) {
			return ResponseCode.PRODUCT_REQ_SIGN_ERROR;
		}

    	return ResponseCode.SUCCESS;
    }
}
