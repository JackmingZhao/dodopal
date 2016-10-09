package com.dodopal.product.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
public class HomeController extends CommonController {
	 @RequestMapping("/notFound")
	 public ModelAndView notFound(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("notFound");
        return mav;
	 }
	 @RequestMapping("/errorPage")
	 public ModelAndView errorPage(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
	 }
}
