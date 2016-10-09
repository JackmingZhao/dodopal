package com.dodopal.transfer.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.transfer.business.service.PersonalInfoService;
/**
 * 个人用户信息迁移
 * @author lenovo
 *
 */
@RequestMapping("/personalInfoTest")
public class PersonalInfoController {
	@Autowired
	PersonalInfoService personalInfoService;
	


	@RequestMapping("/personalInfo")
	public ModelAndView index(HttpServletRequest request) {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("personalInfo");
	    return mav;
	}



//	@RequestMapping("/findPersonalInfo")
//	public @ResponseBody DodopalResponse<String> findLogTransfer(HttpServletRequest request, @RequestParam String userid){
//	    DodopalResponse<String> response = personalInfoService.insertSysUserstb(userid);
//	    return response;
//	}
}
