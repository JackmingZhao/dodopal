package com.dodopal.users.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.hessian.RemotingCallUtil;

/**
 * 类说明 ：
 * @author lifeng
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends CommonController {
    @RequestMapping(value = "/findMerchnat", method = { RequestMethod.GET,RequestMethod.POST })
    public @ResponseBody DodopalResponse<List<MerchantDTO>> findMerchnat(HttpServletRequest request) {
        String params = request.getParameter("params");
        MerchantDTO merchantDTO = JSONObject.parseObject(params, MerchantDTO.class);
        String facadeUrl = DodopalAppVarPropsUtil.getStringProp("hessian.users.facade.url");
        MerchantFacade merchantFacade = RemotingCallUtil.getHessianService(facadeUrl, MerchantFacade.class);
        DodopalResponse<List<MerchantDTO>> response = merchantFacade.findMerchant(merchantDTO);
        return response;
    }
}
