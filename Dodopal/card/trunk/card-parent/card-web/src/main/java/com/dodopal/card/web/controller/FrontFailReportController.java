package com.dodopal.card.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.FrontFailReportCardFacade;
import com.dodopal.common.model.DodopalResponse;

@Controller
@RequestMapping("/frontFailReport")
public class FrontFailReportController {
	@Autowired
	private FrontFailReportCardFacade frontFailReportCardFacade;
	
	@RequestMapping("/frontFailReportFun")
	public
	@ResponseBody 
	DodopalResponse<CrdSysOrderDTO> frontFailReportFun(@RequestBody CrdSysOrderDTO crdDto){
		return frontFailReportCardFacade.frontFailReportFun(crdDto);
	}
}
