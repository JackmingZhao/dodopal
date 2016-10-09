package com.dodopal.thirdly.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BusinessController {

	@RequestMapping("/businessRecharge")
    public ModelAndView thirdlyCodeMgmt(HttpServletRequest request) {
        return new ModelAndView("business/businessRecharge");
    }
	
}
