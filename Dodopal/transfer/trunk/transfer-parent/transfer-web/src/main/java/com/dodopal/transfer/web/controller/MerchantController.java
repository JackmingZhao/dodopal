package com.dodopal.transfer.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.service.BimchntinfotbService;
import com.dodopal.transfer.business.service.LogTransferService;
import com.dodopal.transfer.business.service.ProxyInfoTbService;
import com.dodopal.transfer.business.service.SysuserstbService;
import com.dodopal.transfer.business.service.TransferChilrenMerService;

@Controller
public class MerchantController extends CommonController{
@Autowired
BimchntinfotbService bimService;
@Autowired
SysuserstbService sysService;
@Autowired
LogTransferService logTransferService;
@Autowired
TransferChilrenMerService transferChilrenMerService;
@Autowired
private ProxyInfoTbService proxyInfoTbService;

@RequestMapping("/merchantTest")
public ModelAndView index(HttpServletRequest request) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("merchant");
    return mav;
}


@RequestMapping("/findAllBimchntinfotb")
public @ResponseBody DodopalResponse<String> findAllBimchntinfotb(HttpServletRequest request) {
    return bimService.findAllBimchntinfotb();
}

@RequestMapping("/findAllSysuserstb")
public @ResponseBody DodopalResponse<String> findAllSysuserstb(HttpServletRequest request, @RequestBody String mchnitid) {
    return sysService.findAllSysuserstb(mchnitid);
}



@RequestMapping("/findLogTransfer")
public @ResponseBody DodopalResponse<List<LogTransfer>> findLogTransfer(HttpServletRequest request, @RequestBody LogTransfer logTransfer) {
    System.out.println("测试一下");
    DodopalResponse<List<LogTransfer>> listRespons = new DodopalResponse<List<LogTransfer>>();
    listRespons = logTransferService.findLogTransfer(logTransfer);
    return listRespons;
}


@RequestMapping("/findGRSysuserstb")
public @ResponseBody  DodopalResponse<String>  findGRSysuserstb(HttpServletRequest request,@RequestBody String  totalPages) {
    
   DodopalResponse<String> response = new  DodopalResponse<String> ();
    try {
        response= sysService.findGRAllSysuserstbByPage(totalPages);
    }
    catch (Exception e) {
        e.printStackTrace();
        response.setCode(ResponseCode.UNKNOWN_ERROR);
    }
    return response;
}

@RequestMapping("/findLianSuoAndJM")
public @ResponseBody DodopalResponse<String> findLianSuoAndJM(HttpServletRequest request) {
    return transferChilrenMerService.transferChilrenMerService();
}

@RequestMapping("/findGRWD")
public @ResponseBody DodopalResponse<String> findGRWD(HttpServletRequest request) {
    return proxyInfoTbService.insertDataByProxyId();
}
}
