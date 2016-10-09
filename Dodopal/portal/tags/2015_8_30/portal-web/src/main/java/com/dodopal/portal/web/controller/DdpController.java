package com.dodopal.portal.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.service.DdpService;
import com.dodopal.portal.web.util.BaseRecharge;
/**
 * 账户充值
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月21日
 *
 */
@Controller
@RequestMapping("/ddp")
public class DdpController extends CommonController {
    private static Logger log = Logger.getLogger(DdpController.class);
    @Autowired
    private DdpService ddpService;

    //进入账户充值页面
    @RequestMapping("recharge")
    public ModelAndView ddpRecharge(Model model,HttpServletRequest request) {
        String merClassify = getMerClassify(request.getSession());
        model.addAttribute("merClassify", merClassify);
        return new ModelAndView("ddp/recharge");
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
    public ModelAndView accountRecharge(HttpServletRequest request, @RequestParam("money") String money, @RequestParam("payId") String payId) {
        if (log.isInfoEnabled()) {
            log.info("accountRecharge 账户充值：接收的参数：金额 money="+money+",支付方式id payId="+payId);
        }
        Map<String, String> rechargeMap = new LinkedHashMap<String, String>();
        String customerNo = "";   //客户号
        String customerType = "";  //客户类型
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
        String commodityName = RateCodeEnum.ACCT_RECHARGE.getName() + money;
        //业务类型
        String  businessType = RateCodeEnum.ACCT_RECHARGE.getCode();
        //来源
        String  source = SourceEnum.PORTAL.getCode();

        rechargeMap.put("customerNo", customerNo);
        rechargeMap.put("customerType", customerType);
        rechargeMap.put("tranMoney", money);
        rechargeMap.put("payWayId", payId);
        rechargeMap.put("merchantType", merchantType);
        rechargeMap.put("commodityName", commodityName);
        rechargeMap.put("businessType", businessType);
        rechargeMap.put("source", source);

        String submitHtml = BaseRecharge.buildRequest(rechargeMap, BaseRecharge.getRechargeUrl());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("rechargesubmit");
        mv.addObject("submitHtml", submitHtml);
        return mv;

    }

}
