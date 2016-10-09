package com.dodopal.transfer.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.service.LogTransferService;

@Controller
public class LogTransferController extends CommonController{

//@RequestMapping("/logTest")
//public ModelAndView index(HttpServletRequest request) {
//    ModelAndView mav = new ModelAndView();
//    mav.setViewName("logTest");
//    return mav;
//}


//public @ResponseBody DodopalResponse<List<LogTransfer>> findLogTransfer(HttpServletRequest request, @RequestBody LogTransfer logTransfer) {
//    return logTransferService.findLogTransfer(logTransfer);
//}
}
