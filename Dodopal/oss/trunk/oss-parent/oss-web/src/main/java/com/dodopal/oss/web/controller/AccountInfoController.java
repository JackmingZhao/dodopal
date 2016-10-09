package com.dodopal.oss.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.ExpTempletService;
import com.dodopal.common.util.DDPLog;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.oss.business.bean.AccountInfoBean;
import com.dodopal.oss.business.bean.AccountMainInfoBean;
import com.dodopal.oss.business.bean.FundChangeBean;
import com.dodopal.oss.business.bean.query.AccountInfoListQuery;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.FundChangeQuery;
import com.dodopal.oss.business.service.AccountInfoService;

@Controller
@RequestMapping("/account")
public class AccountInfoController extends CommonController {

    private DDPLog<AccountInfoController> ddpLog = new DDPLog<>(AccountInfoController.class);

    @Autowired
    AccountInfoService accountInfoService;
    
    @Autowired
    private ExpTempletService expTempletService;

    /**
     * 初始化加载资金授信账户信息界面-个人
     * @param request
     * @return
     */
    @RequestMapping("/accountInfo/personInfo")
    public ModelAndView personInfo(HttpServletRequest request) {
        return new ModelAndView("account/accountInfo/accountPersonInfo");
    }

    /**
     * 初始化加载资金授信账户信息界面-商户
     * @param request
     * @return
     */
    @RequestMapping("/accountInfo/merInfo")
    public ModelAndView merInfo(HttpServletRequest request) {
        return new ModelAndView("account/accountInfo/accountMerInfo");
    }

    /**
     * 查询账户信息列表
     * @param request
     * @param crdSysOrderBean
     * @return
     */
    @RequestMapping("/accountInfo/findAccountInfoByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<AccountMainInfoBean>> findAccountInfoByPage(HttpServletRequest request, @RequestBody AccountInfoListQuery accountInfoListQuery) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        DodopalResponse<DodopalDataPage<AccountMainInfoBean>> response = new DodopalResponse<DodopalDataPage<AccountMainInfoBean>>();
        try {
            response = accountInfoService.findAccountInfoListByPage(accountInfoListQuery);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        //记录日志
        SysLog syslog = new SysLog();
        syslog.setInParas(JSONObject.toJSONString(accountInfoListQuery));
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("查询账户信息列表");
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);
        return response;
    }

    /**
     * 查询账户详细信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/accountInfo/findAccountInfoByCode")
    public @ResponseBody
    DodopalResponse<AccountInfoBean> findAccountInfoByCode(HttpServletRequest request, @RequestBody String id) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        DodopalResponse<AccountInfoBean> response = new DodopalResponse<AccountInfoBean>();
        try {
            response = accountInfoService.findAccountInfoByCode(id);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        SysLog syslog = new SysLog();
        syslog.setInParas(id);
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("查询账户详细信息");
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);
        return response;
    }

    /**
     * 禁用、启用账户
     * @param request
     * @param operation
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/accountInfo/operateAccountById")
    public @ResponseBody
    DodopalResponse<String> operateFundAccountsById(HttpServletRequest request, @RequestBody Map<String, Object> operation) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        String oper = operation.get("oper") + "";
        List<String> fundAccountIds = (List<String>) operation.get("fundAccountIds");
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = generateLoginUser(request);
            String userId = user.getId();
            response = accountInfoService.operateFundAccountsById(oper, fundAccountIds, userId);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        SysLog syslog = new SysLog();
        syslog.setInParas(JSONObject.toJSONString(operation));
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("禁用、启用账户");
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);

        return response;
    }

    /**
     * 账户资金变动记录
     * @param fundChangeQueryDTO
     * @return
     */
    @RequestMapping("/accountInfo/findFundsChangeRecordsByPage")
    public @ResponseBody
    DodopalResponse<DodopalDataPage<FundChangeBean>> findFundsChangeRecordsByPage(HttpServletRequest request, @RequestBody FundChangeQuery fundChangeQuery) {
        //日志方法开始时间
        Long tradeStart = Long.valueOf(DateUtils.getCurrSdTime());
        //日志响应码描述
        StringBuffer respexplain = new StringBuffer();
        //日志响应码
        String logRespcode = ResponseCode.SUCCESS;

        DodopalResponse<DodopalDataPage<FundChangeBean>> response = new DodopalResponse<DodopalDataPage<FundChangeBean>>();
        try {
            response = accountInfoService.findFundsChangeRecordsByPage(fundChangeQuery);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        SysLog syslog = new SysLog();
        syslog.setInParas(JSONObject.toJSONString(fundChangeQuery));
        syslog.setTradeStart(tradeStart);
        syslog.setRespCode(logRespcode);
        syslog.setRespExplain(respexplain.toString());
        syslog.setDescription("账户资金变动记录");
        syslog.setClassName(this.getClass().getName());
        syslog.setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        syslog.setOutParas(JSONObject.toJSONString(response));
        ddpLog.info(syslog);

        return response;
    }

    //变更类型
    @RequestMapping("/accountInfo/findchangeType")
    public @ResponseBody
    List<NamedEntity> findchangeType(HttpServletRequest request) {
        List<NamedEntity> list = new ArrayList<NamedEntity>();
        for (AccTranTypeEnum status : AccTranTypeEnum.values()) {
            list.add(new NamedEntity(status.getCode(), status.getName()));
        }
        return list;
    }
    
    /**
     * 导出记录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/accountInfo/exportAccountInfo")
    public @ResponseBody
    DodopalResponse<String> exportAccountInfo(HttpServletRequest request, HttpServletResponse response) {
        DodopalResponse<String> rep = new DodopalResponse<String>();
        AccountInfoListQuery accountInfoListQuery = new AccountInfoListQuery();
        String title = "";
        String custNum = request.getParameter("custNum");
        if (StringUtils.isNotBlank(custNum)) {
            accountInfoListQuery.setCustNum(custNum);
        }
        String custName = request.getParameter("custName");
        if (StringUtils.isNotBlank(custName)) {
            accountInfoListQuery.setCustName(custName);
        }
        String aType = request.getParameter("aType");
        if (StringUtils.isNotBlank(aType)) {
            accountInfoListQuery.setaType(aType);
            if ("1".equals(aType)) {
                String fundType = request.getParameter("fundType");
                accountInfoListQuery.setFundType(fundType);
                title = "商户账户记录";
            } else if ("0".equals(aType)) {
                accountInfoListQuery.setFundType("0");
                title = "个人账户记录";
            }
        }
        String status = request.getParameter("status");
        if (StringUtils.isNotBlank(status)) {
            accountInfoListQuery.setStatus(status);
        }
        try {
            Map<String, String> col = expTempletService.getCloName(request.getParameter("col").split("@"), request.getParameter("typeSelStr"));
            DodopalResponse<List<AccountMainInfoBean>> expResponse = accountInfoService.expAccountInfo(accountInfoListQuery);
            if (ResponseCode.SUCCESS.equals(expResponse.getCode())) {
                String resultCode = ExcelUtil.excelExport(request, response, expResponse.getResponseEntity(), col, title);
                rep.setCode(resultCode);
            } else {
                rep.setCode(expResponse.getCode());
            }
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return rep;
    }

}
