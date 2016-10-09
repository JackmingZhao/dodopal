package com.dodopal.users.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.users.business.model.Test;
import com.dodopal.users.business.service.TestService;

@Controller
public class TestController extends CommonController {

    @Autowired
    private TestService testService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test");
        return mav;
    }

    @RequestMapping("/addTest")
    public @ResponseBody String addTest(HttpServletRequest request, @RequestBody Test test) {
        // 
        testService.insertTest(test);
        return "success";
    }

    @RequestMapping("/deleteTest")
    public @ResponseBody String deleteTest(HttpServletRequest request, @RequestBody String id) {
        testService.deleteTest(id);
        return "success";
    }

    @RequestMapping("/updateTest")
    public @ResponseBody String updateTest(HttpServletRequest request, @RequestBody Test test) {
        testService.updateTest(test);
        return "success";
    }

    @RequestMapping("/findTest")
    public @ResponseBody List<Test> findTest(HttpServletRequest request, @RequestBody Test test) {
        return testService.findTest(test);
    }

}
