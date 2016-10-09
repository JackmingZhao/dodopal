package com.dodopal.oss.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.account.dto.AccountControllerDefaultDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.AccountControllerDefaultService;

/**
 * 12.4 资金账户风控默认值管理
 */
@Controller
@RequestMapping("/account")
public class RiskAccountDefaultController extends CommonController {

    private Logger logger = LoggerFactory.getLogger(RiskAccountDefaultController.class);

    @Autowired
    private AccountControllerDefaultService accountDefaultService;

    /**
     * 资金账户风控 初始页面
     * @param request
     * @return
     */
    @RequestMapping("/riskController/acctDefault")
    public ModelAndView adjustment(HttpServletRequest request) {
        return new ModelAndView("account/riskController/acctDefault");
    }

    /**
     * 资金账户风控默认值查询
     */
    @RequestMapping("/riskController/findAccountRiskControllerDefaultItemListByPage")
    public @ResponseBody DodopalResponse<List<AccountControllerDefaultDTO>> findAccountRiskControllerDefaultItemListByPage(HttpServletRequest request) {
        DodopalResponse<List<AccountControllerDefaultDTO>> response;
        try {
            return accountDefaultService.findAccountRiskControllerDefaultItemListByPage();
        }
        catch (Exception e) {
            response = new DodopalResponse<List<AccountControllerDefaultDTO>>();
            logger.error("资金账户风控默认值查询时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    /**
     * 资金账户风控默认值查询
     */
    @RequestMapping("/riskController/findAccountRiskControllerDefaultById")
    public @ResponseBody DodopalResponse<AccountControllerDefaultDTO> findAccountRiskControllerDefaultById(HttpServletRequest request, @RequestBody String id) {
        DodopalResponse<AccountControllerDefaultDTO> response;
        try {
            return accountDefaultService.findAccountRiskControllerDefaultById(id);
        }
        catch (Exception e) {
            response = new DodopalResponse<AccountControllerDefaultDTO>();
            logger.error("资金账户风控默认值查询时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    

    /**
     * 资金账户风控默认值修改
     */
    @RequestMapping("/riskController/updateAccountRiskControllerDefaultItem")
    public @ResponseBody DodopalResponse<String> updateAccountRiskControllerDefaultItem(HttpServletRequest request, @RequestBody AccountControllerDefaultDTO acctDefault) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            User user = generateLoginUser(request);
            response = accountDefaultService.updateAccountRiskControllerDefaultItem(acctDefault, user);
        }
        catch (Exception e) {
            logger.error("资金账户风控默认值修改时发生错误:", e);
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
