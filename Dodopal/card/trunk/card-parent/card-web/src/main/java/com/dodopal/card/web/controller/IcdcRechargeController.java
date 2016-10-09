package com.dodopal.card.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.IcdcRechargeCardFacade;
import com.dodopal.common.model.DodopalResponse;

@Controller
@RequestMapping("/icdcRecharge")
public class IcdcRechargeController {
	@Autowired
	private IcdcRechargeCardFacade icdcRechargeCardFacade;
	
	@RequestMapping(value="/getLoadInitFun")
	public
	@ResponseBody 
	DodopalResponse<CrdSysOrderDTO> getLoadInitFun(HttpServletRequest request){
	    String jsondata = request.getParameter("jsondata");
	    CrdSysOrderDTO  crdDTO = JSONObject.parseObject(jsondata, CrdSysOrderDTO.class);
		return icdcRechargeCardFacade.getLoadInitFun(crdDTO);
	}
	
	
	
	@RequestMapping(value="/uploadResultChkUdpOrderFun")
	public
	@ResponseBody
	DodopalResponse<CrdSysOrderDTO> uploadResultChkUdpOrderFun(HttpServletRequest request){
	    String jsondata = request.getParameter("jsondata");
        CrdSysOrderDTO  crdDTO = JSONObject.parseObject(jsondata, CrdSysOrderDTO.class);
		return icdcRechargeCardFacade.uploadResultChkUdpOrderFun(crdDTO);
	}
	
	@RequestMapping(value="/uploadResultFun")
	public
	@ResponseBody
	DodopalResponse<CrdSysOrderDTO> uploadResultFun(HttpServletRequest request){
	    String jsondata = request.getParameter("jsondata");
	    System.out.println("--------------------------"+jsondata);
        CrdSysOrderDTO  crdDTO = JSONObject.parseObject(jsondata, CrdSysOrderDTO.class);
		return icdcRechargeCardFacade.uploadResultFun(crdDTO);
	}
	
	@RequestMapping(value="/getLoadInitFun2")
    public
    @ResponseBody 
    DodopalResponse<CrdSysOrderDTO> getLoadInitFun(@RequestBody CrdSysOrderDTO crdDTO){
        return icdcRechargeCardFacade.getLoadInitFun(crdDTO);
    }
	
	@RequestMapping(value="/uploadResultChkUdpOrderFun2")
    public
    @ResponseBody
    DodopalResponse<CrdSysOrderDTO> uploadResultChkUdpOrderFun(@RequestBody CrdSysOrderDTO crdDTO){
        return icdcRechargeCardFacade.uploadResultChkUdpOrderFun(crdDTO);
    }
	
	@RequestMapping(value="/uploadResultFun2")
    public
    @ResponseBody
    DodopalResponse<CrdSysOrderDTO> uploadResultFun(@RequestBody CrdSysOrderDTO crdDTO){
        return icdcRechargeCardFacade.uploadResultFun(crdDTO);
    }
}
