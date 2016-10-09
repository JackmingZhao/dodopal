package com.dodopal.card.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.delegate.CardDelegate;
import com.dodopal.card.web.controller.CommonController;
import com.dodopal.common.model.DodopalResponse;

@Controller
public class TestLogController extends CommonController {

    @Autowired
    private CardDelegate cardDelegate;
    
    @RequestMapping("/testLog")
    public @ResponseBody
    ModelAndView testLog(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("testLog");
        return mav;
    }
    
    @RequestMapping("/checkCardTest")
    public @ResponseBody
    String checkCardTest(HttpServletRequest request) {
        String json = request.getParameter("jsondata");
//        String retJson = DodopalAppVarPropsUtil.getStringProp("card.log.testJson");
//        CrdSysOrderDTO orderDTO = JSONObject.parseObject(retJson, CrdSysOrderDTO.class);
//        BizdomainHead bizdomainHead = new BizdomainHead();
//        Bizdomain bizdomain = new Bizdomain();
//        List<LoadorderList> loadorderlist = new ArrayList<LoadorderList>();
//        for (int i = 0; i < 3; i++) {
//            LoadorderList load = new LoadorderList();
//            loadorderlist.add(load);
//        }
//        bizdomain.setLoadorderlist(loadorderlist);
//        bizdomainHead.setBizdomain(bizdomain);
//        bizdomainHead.setCarddomain(orderDTO);
//        String jsonStr = JSONObject.toJSONString(bizdomainHead, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        return json;
    }

    @RequestMapping("/checkCard")
    public @ResponseBody
    DodopalResponse<BizdomainHead> checkCard(HttpServletRequest request) {
        String json = request.getParameter("jsondata");
        System.out.println("json=====================================" + json);
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.checkCard(crdDTO);
        BizdomainHead bizdomainHead = new BizdomainHead();
        Bizdomain bizdomain = new Bizdomain();
        List<LoadorderList> loadorderlist = new ArrayList<LoadorderList>();
        for (int i = 0; i < 3; i++) {
            LoadorderList load = new LoadorderList();
            loadorderlist.add(load);
        }
        bizdomain.setLoadorderlist(loadorderlist);
        bizdomainHead.setBizdomain(bizdomain);
        bizdomainHead.setCarddomain(response.getResponseEntity());
//        bizdomainHead.setCarddomain(crdDTO);
//        crdDTO.setRespcode("999999");
//        String jsonStr = JSONObject.toJSONString(bizdomainHead, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        DodopalResponse<BizdomainHead> retResponse = new DodopalResponse<BizdomainHead>();
        retResponse.setResponseEntity(bizdomainHead);
        retResponse.setCode(bizdomainHead.getCarddomain().getRespcode());
        
        System.out.println("******************************");
        System.out.println(JSONObject.toJSONString(response));
        System.out.println("******************************");
        
        return retResponse;
        
//        DodopalResponse<BizdomainHead> response = new DodopalResponse<BizdomainHead>();
//        response.setCode("111111");
//        response.setResponseEntity(null);
//        return response;
    }

    @RequestMapping("/checkCardLocal")
    public @ResponseBody
    String checkCardLocal(HttpServletRequest request, @RequestBody String json) {
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
//        for(int i = 0; i<50 ;i++){
            DodopalResponse<CrdSysOrderDTO> response = cardDelegate.checkCard(crdDTO);
//        }
//        ThreadTest t1 = new ThreadTest(1);
//        t1.start();
//        ThreadTest t2 = new ThreadTest(2);
//        t2.start();
//        ThreadTest t3 = new ThreadTest(3);
//        t3.start();
//        ThreadTest t4 = new ThreadTest(4);
//        t4.start();
//        ThreadTest t5 = new ThreadTest(5);
//        t5.start();
//        ThreadTest t6 = new ThreadTest(6);
//        t6.start();
//        ThreadTest t7 = new ThreadTest(7);
//        t7.start();
        return response.getMessage();
    }

    @RequestMapping("/createCard")
    public @ResponseBody
    DodopalResponse<BizdomainHead> createCard(HttpServletRequest request) {
        String json = request.getParameter("jsondata");
        System.out.println("json=====================================" + json);
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.createCard(crdDTO);
        
        BizdomainHead bizdomainHead = new BizdomainHead();
        Bizdomain bizdomain = new Bizdomain();
        List<LoadorderList> loadorderlist = new ArrayList<LoadorderList>();
        for (int i = 0; i < 3; i++) {
            LoadorderList load = new LoadorderList();
            loadorderlist.add(load);
        }
        bizdomain.setLoadorderlist(loadorderlist);
        bizdomainHead.setBizdomain(bizdomain);
        bizdomainHead.setCarddomain(response.getResponseEntity());
        
        DodopalResponse<BizdomainHead> retResponse = new DodopalResponse<BizdomainHead>();
        retResponse.setResponseEntity(bizdomainHead);
        retResponse.setCode(bizdomainHead.getCarddomain().getRespcode());
        
        System.out.println("******************************");
        System.out.println(JSONObject.toJSONString(response));
        System.out.println("******************************");
        
        return retResponse;
    }

    @RequestMapping("/createCardLocal")
    public @ResponseBody
    String createCardLocal(HttpServletRequest request, @RequestBody String json) {
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);

        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.createCard(crdDTO);
        String code = response.getCode();
        String msg = response.getMessage();
        return code + "--" + msg;
    }

    @RequestMapping("/getLoadOrder")
    public @ResponseBody
    DodopalResponse<CrdSysOrderDTO> getLoadOrderFun(HttpServletRequest request) {
        String json = request.getParameter("jsondata");
        System.out.println("json=====================================" + json);
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.getLoadOrderFun(crdDTO);
        
        System.out.println("******************************");
        System.out.println(JSONObject.toJSONString(response));
        System.out.println("******************************");
        
        return response;
    }

    @RequestMapping("/getLoadOrderFunLocal")
    public @ResponseBody
    String getLoadOrderFunLocal(HttpServletRequest request, @RequestBody String json) {
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);

        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.getLoadOrderFun(crdDTO);
        String code = response.getCode();
        String msg = response.getMessage();
        return code + "--" + msg;
    }
    
/*    @RequestMapping("/findAccountInfo")
    public @ResponseBody
    String findAccountInfo(HttpServletRequest request, @RequestBody String acid) {
        DodopalResponse<AccountInfoDTO> response = cardDelegate.findAccountInfo(acid);
        String code = response.getCode();
        String msg = response.getMessage();
        return code + "--" + msg;
    }
    
    @RequestMapping("/findAccountInfoList")
    public @ResponseBody
    String findAccountInfoList(HttpServletRequest request, @RequestBody String acid) {
        AccountInfoListQueryDTO accountInfoListQueryDTO = new AccountInfoListQueryDTO();
        accountInfoListQueryDTO.setaType("0");
//        accountInfoListQueryDTO.setCustName("1");
        accountInfoListQueryDTO.setCustNum("000");
//        accountInfoListQueryDTO.setStatus("1");
        PageParameter page = new PageParameter();
        accountInfoListQueryDTO.setPage(page);
        DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> response = cardDelegate.findAccountInfoList(accountInfoListQueryDTO);
        String code = response.getCode();
        String msg = response.getMessage();
        return code + "--" + msg;
    }*/
    
    @RequestMapping("/checkCardCosumLocal")
    public @ResponseBody
    String checkCardCosumLocal(HttpServletRequest request, @RequestBody String json) {
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.checkCardCosum(crdDTO);
        return response.getMessage();
    }
    
    @RequestMapping("/upload")
    public @ResponseBody
    String upload(HttpServletRequest request, @RequestBody String json) {
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.upload(crdDTO);
        return response.getMessage();
    }
    
    @RequestMapping("/getLoadOrderCosum")
    public @ResponseBody
    String getLoadOrderCosum(HttpServletRequest request, @RequestBody String json) {
        CrdSysOrderDTO crdDTO = JSONObject.parseObject(json, CrdSysOrderDTO.class);
        DodopalResponse<CrdSysOrderDTO> response = cardDelegate.getLoadOrderCosum(crdDTO);
        return response.getCode();
    }
}
