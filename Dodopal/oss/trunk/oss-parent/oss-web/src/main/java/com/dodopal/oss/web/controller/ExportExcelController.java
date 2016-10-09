package com.dodopal.oss.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExpTemplet;
import com.dodopal.common.service.ExpTempletService;

/**
 * @author Mikaelyan
 * 导出Excel
 */
@Controller
public class ExportExcelController {
	
	private Logger logger = LoggerFactory.getLogger(RiskAccountController.class);
	
	@Autowired
    private ExpTempletService expTempletService;
	
	/**
     * 资金账户风控 用户选择导出的 列
     */
    @RequestMapping("/expExcel/findExpCol")
    public @ResponseBody DodopalResponse<List<ExpTemplet>> findExpSelItem(HttpServletRequest req, @RequestBody String templetCode) {
    	DodopalResponse<List<ExpTemplet>> response = new DodopalResponse<List<ExpTemplet>>();
    	try {
    		List<ExpTemplet> expTempletList = expTempletService.findExpSelItem(templetCode);
    		response.setResponseEntity(expTempletList);
    		response.setCode(ResponseCode.SUCCESS);
    	}catch(Exception e) {
    		response = new DodopalResponse<List<ExpTemplet>>();
    		logger.error("获取导出选项时时发生错误:", e);
    		response.setCode(ResponseCode.UNKNOWN_ERROR);
    		response.setMessage(e.getMessage());
    	}
    	return response;
    }
    
    @RequestMapping("/expExcel/getExpCol")
    public @ResponseBody DodopalResponse<List<ExpTemplet>> getExpCol(HttpServletRequest req, @RequestBody String templetCode) {
    	DodopalResponse<List<ExpTemplet>> response = new DodopalResponse<List<ExpTemplet>>();
    	try {
    		List<ExpTemplet> expTempletList = expTempletService.findExpTempletsByTempletCodes(templetCode.split(","));
    		response.setResponseEntity(expTempletList);
    		response.setCode(ResponseCode.SUCCESS);
    	}catch(Exception e) {
    		response = new DodopalResponse<List<ExpTemplet>>();
    		logger.error("获取导出选项时时发生错误:", e);
    		response.setCode(ResponseCode.UNKNOWN_ERROR);
    		response.setMessage(e.getMessage());
    	}
    	return response;
    }
}


