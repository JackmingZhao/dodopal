package com.dodopal.card.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.card.business.service.CardEncryptService;
import com.dodopal.common.model.DodopalResponse;

@Controller
@RequestMapping("/cardEncrypt")
public class CardEncryptController{
    @Autowired
    private CardEncryptService cardEncryptService;
    
    
    @RequestMapping("/cardEncryptMgmt")
    public ModelAndView cardEncryptMgmt(HttpServletRequest request) {
        return new ModelAndView("card/cardEncryptMgmt");
    }
    
    
    @RequestMapping("/decryptMode")
    public @ResponseBody DodopalResponse<String> decryptMode(HttpServletRequest request, @RequestBody String decryptText) throws UnsupportedEncodingException {
        System.out.println("=========>>>>" + decryptText);
        byte[] srcbyte =decryptText.getBytes("ISO-8859-1");
        byte[] decryptname= cardEncryptService.decryptMode(srcbyte);
        DodopalResponse<String> response = new DodopalResponse<String>();
        String decryptnamet = new String(decryptname,"ISO-8859-1");
        response.setResponseEntity(decryptnamet);
        response.setCode("000000");
        return response;
    }
    
    @RequestMapping("/encryptMode")
    public @ResponseBody DodopalResponse<String> encryptMode(HttpServletRequest request, @RequestBody String encryptName) throws UnsupportedEncodingException {
        byte[] srtbyte = encryptName.getBytes("ISO-8859-1");
        //查看转换前字节
        byte[] decryptText = cardEncryptService.encryptMode(srtbyte);
        DodopalResponse<String> response = new DodopalResponse<String>();
        //从字节转换字符串
        String decryptTextt = new String(decryptText,"ISO-8859-1");
        //从字符串转换字节
        byte[] decryptTextts = decryptTextt.getBytes("ISO-8859-1");
        
        response.setResponseEntity(decryptTextt);
        response.setCode("000000");
        return response;
    } 
}
