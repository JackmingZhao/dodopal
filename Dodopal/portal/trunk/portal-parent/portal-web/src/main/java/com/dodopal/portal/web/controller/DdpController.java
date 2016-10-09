package com.dodopal.portal.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.log.ActivemqLogPublisher;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.portal.business.bean.PayServiceRateBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.model.MerchantUser;
import com.dodopal.portal.business.service.DdpService;
import com.dodopal.portal.web.util.BaseRecharge;

/**
 * 账户充值
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月21日
 */
@Controller
@RequestMapping("/ddp")
public class DdpController extends CommonController {
    private static Logger log = Logger.getLogger(DdpController.class);
    @Autowired
    private DdpService ddpService;

    //进入账户充值页面
    @RequestMapping("recharge")
    public ModelAndView ddpRecharge(Model model, HttpServletRequest request) {
        //正式商户 or 测试商户
        String merClassify = getMerClassify(request.getSession());
        //商户类型
        String merType = getMerType(request.getSession());
        String merCode = "";
        //是否显示支付确认信息
        String payInfoFlag = "";
        
        //是否为外接商户
        String merExternal = "false";

        String merName = getCurrentUserName(request.getSession());

        try {
            DodopalResponse<MerchantUser> rtResponse = ddpService.findUserInfoByUserName(merName);
            payInfoFlag = rtResponse.getResponseEntity().getPayInfoFlg();
        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        if (payInfoFlag == null || payInfoFlag == "") {
            payInfoFlag = "0";
        }
        //判断是否是个人还是商户
        if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
            merCode = getCurrentUserCode(request.getSession());
        } else {
            merCode = getCurrentMerchantCode(request.getSession());
        }
        //判断是否为外接商户
        if(MerTypeEnum.EXTERNAL.getCode().equals(merType)){
            merExternal = "true";
        }else{
            merExternal = "false";
        }
        model.addAttribute("merType", merType);
        model.addAttribute("merExternal", merExternal);
        model.addAttribute("payInfoFlag", payInfoFlag);
        model.addAttribute("merClassify", merClassify);
        model.addAttribute("merCode", merCode);
        return new ModelAndView("ddp/recharge");
    }

    //修改支付确认信息
    @RequestMapping("modifyPayInfoFlg")
    public @ResponseBody DodopalResponse<Boolean> modifyPayInfoFlg(HttpServletRequest request, @RequestBody String flag) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        MerchantUser user = new MerchantUser();
        user.setId(getCurrentUserId(request.getSession()));
        user.setUserCode(getCurrentUserCode(request.getSession()));
        if (flag.equals("true")) {
            user.setPayInfoFlg("1");
        }

        try {
            response = ddpService.modifyPayInfoFlg(user);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }

        return response;
    }

    //获取支付服务费率和支付服务类型
    @RequestMapping("findPayServiceRate")
    public @ResponseBody DodopalResponse<PayServiceRateBean> findPayServiceRate(HttpServletRequest request, @RequestParam("payWayId") String payWayId, @RequestParam("rechargeMoney") String rechargeMoney) {
        DodopalResponse<PayServiceRateBean> response = new DodopalResponse<PayServiceRateBean>();
        try {
            String busType = RateCodeEnum.ACCT_RECHARGE.getCode();
            long money = Math.round(Double.valueOf(rechargeMoney) * 100);

            response = ddpService.findPayServiceRate(payWayId, busType, money);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }

        return response;
    }

    //校验用户商户的合法性
    @RequestMapping("checkUserAndMer")
    public @ResponseBody DodopalResponse<Boolean> checkUserAndMer(HttpServletRequest request) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String userCode = getCurrentUserCode(request.getSession());
        String merCode = getCurrentMerchantCode(request.getSession());
        String userId = getCurrentUserId(request.getSession());
        String merType = getMerType(request.getSession());
        try {
            response = ddpService.checkUserAndMer(userCode, merCode, userId, merType);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }

        return response;
    }

    //更多支付方式
    @RequestMapping("findPayWay")
    public @ResponseBody DodopalResponse<List<PayWayBean>> findPayWay(HttpServletRequest request) {
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        //商户类型
        String merType = getMerType(request.getSession());
        //是否是外接商户(true 是| false 否)
        Boolean ext = false;
        String merCode = "";
        //判断是否是外接商户
        if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
            ext = true;
            merCode = getCurrentMerchantCode(request.getSession());
        }
        try {
            response = ddpService.findPayWay(ext, merCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //用户常用支付方式
    @RequestMapping("findCommonPayWay")
    public @ResponseBody DodopalResponse<List<PayWayBean>> findCommonPayWay(HttpServletRequest request) {
        DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
        //商户类型
        String merType = getMerType(request.getSession());
        //是否是外接商户(true 是| false 否)
        Boolean ext = false;
        //用户号
        String userCode = getCurrentUserCode(request.getSession());
        //判断是否是外接商户
        if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
            ext = true;
        }
        try {
            response = ddpService.findCommonPayWay(ext, userCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //充值
    @RequestMapping("accountRecharge")
    public String accountRecharge(Model model,HttpServletRequest request,RedirectAttributes attr, @RequestParam("realMoney") String realMoney, @RequestParam("money") String money, @RequestParam("payId") String payId) {
        if (log.isInfoEnabled()) {
            log.info("accountRecharge 账户充值：接收的参数：实付金额 realMoney=" + realMoney + " 充值金额money:" + money + ",支付方式id payId=" + payId);
        }
        SysLog sysLog = new SysLog();
        ModelAndView mv = new ModelAndView();
        Map<String, String> rechargeMap = new LinkedHashMap<String, String>();
        String customerNo = ""; //客户号
        String customerType = ""; //客户类型
        String merchantType = "false";//商户类型（是否外接商户）
        //商户类型
        String merType = getMerType(request.getSession());

        if (MerTypeEnum.PERSONAL.getCode().equals(merType)) {
            customerNo = getCurrentUserCode(request.getSession());
            customerType = MerUserTypeEnum.PERSONAL.getCode();
        } else {
            customerNo = getCurrentMerchantCode(request.getSession());
            customerType = MerUserTypeEnum.MERCHANT.getCode();
            if (MerTypeEnum.EXTERNAL.getCode().equals(merType)) {
                merchantType = "true";
            }
        }
        //商品名称
        String commodityName = RateCodeEnum.ACCT_RECHARGE.getName() + realMoney;
        //业务类型
        String businessType = RateCodeEnum.ACCT_RECHARGE.getCode();
        //来源
        String source = SourceEnum.PORTAL.getCode();

        //校验参数
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        String userCode = getCurrentUserCode(request.getSession());
        String merCode = getCurrentMerchantCode(request.getSession());
        String userId = getCurrentUserId(request.getSession());
        try {
            response = ddpService.checkUserAndMer(userCode, merCode, userId, merType);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        
        if (!ResponseCode.SUCCESS.equals(response.getCode())) {
            response.setNewMessage(ResponseCode.PAY_ERROR);
            
            mv.setViewName("failure");
            model.addAttribute("errorMessage", response.getMessage());
            model.addAttribute("businessType", "账户充值失败");
            
           // attr.addAttribute("errorMessage", response.getMessage());
            //attr.addAttribute("businessType", "账户充值");
            return "failure";
        }
        //充值金额  单位（分）
        String tranMoney = Long.toString(Math.round(Double.valueOf(money) * 100));
        //实付金额  加上了服务费  单位（分）
        String realTranMoney = Long.toString(Math.round(Double.valueOf(realMoney) * 100));

        //加唯一标识
        String genId= UUID.randomUUID().toString();
         //java.util.Date date = new java.util.Date(System.currentTimeMillis());
         //java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
         //String UUID= formatter.format(date);
        
        rechargeMap.put("customerNo", customerNo);
        rechargeMap.put("customerType", customerType);
        rechargeMap.put("tranMoney", tranMoney);
        rechargeMap.put("realTranMoney", realTranMoney);
        rechargeMap.put("payWayId", payId);
        rechargeMap.put("merchantType", merchantType);
        rechargeMap.put("commodityName", commodityName);
        rechargeMap.put("businessType", businessType);
        rechargeMap.put("source", source);
        rechargeMap.put("operateId", userId);
        rechargeMap.put("genId", genId);
        
        attr.addAttribute("customerNo", customerNo);
        attr.addAttribute("customerType", customerType);
        attr.addAttribute("tranMoney", tranMoney);
        attr.addAttribute("realTranMoney", realTranMoney);
        attr.addAttribute("payWayId", payId);
        attr.addAttribute("merchantType", merchantType);
        attr.addAttribute("commodityName", commodityName);
        attr.addAttribute("businessType", businessType);
        attr.addAttribute("source", source);
        attr.addAttribute("operateId", userId);
        attr.addAttribute("genId", genId);
        
        System.out.println(JSONObject.toJSONString(rechargeMap));
        //数据签名 假设：签名KEY = ‘123’；编码=UTF-8
        String key = "123";
        String input_charset = "UTF-8";
        
        
        //String checkSign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), PaymentConstants.KEY, PaymentConstants.INPUT_CHARSET_UTF_8);
        
        String sign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(rechargeMap)), key, input_charset);
        attr.addAttribute("sign", sign);
        attr.addAttribute("input_charset", input_charset);

        String submitHtml = BaseRecharge.buildRequest(input_charset, sign, rechargeMap, BaseRecharge.getRechargeUrl());
        if (log.isInfoEnabled()) {
            log.info("账户充值：跳转支付网关参数==" + submitHtml);
        }
        
        sysLog.setServerName("portal");
        //sysLog.setOrderNum();
        //sysLog.setTranNum(tran.getTranCode());
        sysLog.setClassName(DdpController.class.toString());
        //sysLog.setMethodName("exceptionHandle");
        sysLog.setInParas("账户充值：跳转支付网关参数==" +submitHtml);
        ActivemqLogPublisher.publishLog2Queue(sysLog, DodopalAppVarPropsUtil.getStringProp(PortalConstants.SERVER_LOG_NAME), DodopalAppVarPropsUtil.getStringProp(PortalConstants.SERVER_LOG_URL));
        
        mv.setViewName("rechargesubmit");
        mv.addObject("submitHtml", submitHtml);
        return "redirect:"+BaseRecharge.getRechargeUrl();

    }

}
