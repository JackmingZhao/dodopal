package com.dodopal.account.web.controller;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.account.business.model.Test;
import com.dodopal.account.business.service.TestService;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

@Controller
public class TestController extends CommonController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test");
        return mav;
    }
    
    @RequestMapping("/sign")
    public ModelAndView sign(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signmd5");
        return mav;
    }


    @RequestMapping("/signTest")
    public @ResponseBody String signTest(HttpServletRequest request, @RequestBody Test test) {
    	 //把对象转换为签名map
    	 DodopalResponse<Map<String,String>> rep  = SignUtils.getSignMap(test);    	
    	 System.out.println(JSONObject.toJSONString(rep));    
    	 //数据签名 假设：签名KEY = ‘123’；编码=UTF-8
    	 String sign = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), "123", "UTF-8");
    	 
        return sign;
    }
    
    @RequestMapping("/changeCity")
    public ModelAndView changeCity(HttpServletRequest request) {
    	 DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
    	 ModelAndView mav = new ModelAndView();
    	 String city = request.getParameter("city");
    	 Map<String ,String> map = new HashMap<String,String>();
         if (StringUtils.isBlank(city)) {
        	 mav.setViewName("ocx");
         }
         String ykt = "";
         //1、根据城市code 取得对应通卡公司 TODO 
         
         if(city.equals("1791")){
        	 ykt = "330000";
         } else if(city.equals("100000")){
        	 ykt = "100000";
         }
         
         //2、根据通卡公司code和城市code 取得对应的版本号          
         String ver = ddicService.getDdicNameByCode(ykt, city).toString();
         
         //3、根据通卡公司code取得 对应的class id
         String clsId = ddicService.getDdicNameByCode(ykt, "CLSID").toString();
         
        
         mav.addObject("ykt", ykt);
         mav.addObject("ver", ver);
         mav.addObject("CLSID", clsId);
         
         mav.setViewName("ocx");
         return  mav;
    	 
         
    }
    
    @RequestMapping("/getOcxVer")
    public @ResponseBody Map<String ,String> getOcxVer(HttpServletRequest request,@RequestBody String city) {
    	 DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
    	 Map<String ,String> map = new HashMap<String,String>();
//    	 String city = request.getParameter("city");
         if (StringUtils.isBlank(city)) {
             return map;
         }
         
         //1、根据城市code 取得对应通卡公司
         
         //2、根据通卡公司code和城市code 取得对应的版本号          
         String ver = ddicService.getDdicNameByCode("330000", city).toString();
         
         //3、根据通卡公司code取得 对应的class id
         String clsId = ddicService.getDdicNameByCode("330000", "CLSID").toString();
         
         map.put("ykt", "330000");  
         
         map.put("ver", ver);
         
         map.put("CLSID", clsId);
         
         return  map;
    	 
        
    }

    @RequestMapping("/checkSignTest")
    public @ResponseBody String checkSignTest(HttpServletRequest request, @RequestBody Test test) {
       //获取旧的签名数据
       String signOld = request.getParameter("sign");
       //获取新的签名MAP
       DodopalResponse<Map<String,String>> rep  = SignUtils.getSignMap(test);
       //新的数据签名  假设：签名KEY = ‘123’；编码=UTF-8
       String signNew = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), "123", "UTF-8");
       //对比签名
       if (signNew.equals(signOld)) {
            return "验签成功";
        } else {
            return "验签失败";
        }        
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
