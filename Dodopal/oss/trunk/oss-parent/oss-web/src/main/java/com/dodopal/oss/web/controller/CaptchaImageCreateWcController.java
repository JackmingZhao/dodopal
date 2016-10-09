package com.dodopal.oss.web.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dodopal.oss.business.constant.OSSConstants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("/captchImage")
public class CaptchaImageCreateWcController {

    private Logger logger = Logger.getLogger(CaptchaImageCreateWcController.class);

    @Autowired
    private Producer captchaProducer;

    
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        String kaptchaType = request.getParameter("kaptchaType");
        try {
            if (logger.isInfoEnabled()) {
                logger.info("进入CaptchaImageCreateWcController create方法");
            }
            response.setDateHeader("Expires", 0);
            // Set standard HTTP/1.1 no-cache headers.  
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.  
            response.setHeader("Pragma", "no-cache");
            // return a jpeg  
            response.setContentType("image/jpeg");
            // create the text for the image  
            String capText = captchaProducer.createText();
            // store the text in the session manager
            if (OSSConstants.SESSION_KAPTCHATYPE_LOGIN.equals(kaptchaType)) {
                request.getSession().setAttribute(OSSConstants.SESSION_KAPTCHATYPE_LOGIN, capText);
            } 
            
            // create the image with the text  
            BufferedImage bi = captchaProducer.createImage(capText);
            out = response.getOutputStream();
            // write the data out  
            ImageIO.write(bi, "png", out);
            out.flush();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.error("Errors occurs :" + e.getMessage(), e);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("退出CaptchaImageCreateWcController create方法.");
        }
    }
}
