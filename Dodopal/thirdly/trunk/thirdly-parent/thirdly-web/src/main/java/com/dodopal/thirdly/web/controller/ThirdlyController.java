package com.dodopal.thirdly.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.thirdly.business.model.SignData;
import com.dodopal.thirdly.business.model.SignPayData;
import com.dodopal.thirdly.business.model.ThirdlyProduct;

@Controller
public class ThirdlyController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(ThirdlyController.class);
    /**
     * 请输入商户号
     * @param request
     * @return
     */
    @RequestMapping("/thirdlyCodeMgmt")
    public ModelAndView thirdlyCodeMgmt(HttpServletRequest request) {
        return new ModelAndView("thirdlyView/thirdlyCodeMgmt");
    }
    /**
     * 点击一卡通充值
     * @param request
     * @return
     */
    @RequestMapping("/thirdlyMgmt")
    public ModelAndView thirdlyMgmt(HttpServletRequest request) {
        return new ModelAndView("thirdlyView/thirdlyMgmt");
    }
   /**
    * 第一次外接商户验证验签密钥
    * @param request
    * @param signData
    * @return
    */
    @RequestMapping("/signDate")
    public @ResponseBody String signTest(HttpServletRequest request, @RequestBody SignData signData) {
         //把对象转换为签名map
         DodopalResponse<Map<String,String>> rep  = SignUtils.getSignMap(signData);     
         //数据签名 假设：签名KEY = ‘123’；编码=UTF-8
         String sign = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), "123", "UTF-8");
         return sign;
    }
    /**
     * 生单返回的方法
     * @param request
     * @param input_charset
     * @param sign_type
     * @param sign
     * @param rescode
     * @param prdordernum
     * @param orderdate
     * @param productcode
     * @param productname
     * @param tradecard
     * @param posid
     * @param befbal
     * @param tranamt
     * @return
     */
    @RequestMapping("/toPageCheckCallBackUrl")
    public ModelAndView toPayMent(HttpServletRequest request,
        @RequestParam(value="input_charset",required=false)String input_charset,
        @RequestParam(value="sign_type",required=false)String sign_type,
        @RequestParam(value="sign",required=false)String sign,
        @RequestParam(value="rescode",required=false)String rescode,
        @RequestParam(value="prdordernum",required=false)String prdordernum,
        @RequestParam(value="orderdate",required=false)String orderdate,
        @RequestParam(value="productcode",required=false)String productcode,
        @RequestParam(value="productname",required=false)String productname,
        @RequestParam(value="tradecard",required=false) String tradecard,
        @RequestParam(value="posid",required=false) String posid,
        @RequestParam(value="befbal",required=false) String befbal,
        @RequestParam(value="tranamt",required=false) String tranamt
        ) {
  
    Map<String, String> map = new HashMap<String, String>();
    ModelAndView mv = new ModelAndView();
    map.put("input_charset", input_charset);
    map.put("prdordernum", prdordernum);
    map.put("orderdate", orderdate);
    map.put("productcode", productcode);
    map.put("productname", productname);
    map.put("tradecard", tradecard);
    map.put("posid", posid);
    map.put("befbal", befbal);
    map.put("tranamt", tranamt);
    log.info("Page Check CallBack URL map="+map);
    log.info("Page Check CallBack URL Start================================================");
    try {
        if("000000".equals(rescode)){
            log.info("Page Check CallBack URL rescode:"+rescode);
            log.info("Page Check CallBack URL sign:"+sign);
            String mySign = SignUtils.sign(SignUtils.createLinkString(map), "123", input_charset);
            log.info("Page Check CallBack URL mySign:"+mySign);
            if(!mySign.equals(sign)){
                mv.setViewName("notFound");
                log.info("Page Check CallBack URL notFound:"+mv);
                return mv;
            }else{
                ThirdlyProduct thirdlyProduct = new ThirdlyProduct();
                thirdlyProduct.setPrdordernum(prdordernum);
                thirdlyProduct.setOrderdate(orderdate);
                thirdlyProduct.setProductcode(productcode);
                thirdlyProduct.setProductname(productname);
                thirdlyProduct.setTradecard(tradecard);
                thirdlyProduct.setPosid(posid);
                thirdlyProduct.setBefbal(befbal == null ? "" : String.format("%.2f",Double.parseDouble(befbal) / 100));
                thirdlyProduct.setTranamt(tranamt == null ? "" : String.format("%.2f",Double.parseDouble(tranamt) / 100));
                request.setAttribute("thirdlyProduct", thirdlyProduct);
                mv.setViewName("thirdlyView/thirdlyProduct");
                log.info("Page Check CallBack URL thirdlyProduct:"+mv);
                return mv;
                
            }
        }else{
            request.setAttribute("rescode", rescode);
            mv.setViewName("thirdlyView/thirdlyCode");
            log.info("thirdlyCode:"+mv);
            return mv;
        } 
    }
    catch (Exception e) {
        e.printStackTrace();
        mv.setViewName("error");
        return mv;
    }
}
    
    
    /**
     * 第二次外接商户验证验签密钥
     * @param request
     * @param signData
     * @return
     */
     @RequestMapping("/singThirdlyData")
     public @ResponseBody String singThirdlyData(HttpServletRequest request, @RequestBody SignPayData signPayData) {
          //把对象转换为签名map
          DodopalResponse<Map<String,String>> rep  = SignUtils.getSignMap(signPayData);     
          //数据签名 假设：签名KEY = ‘123’；编码=UTF-8
          String sign = SignUtils.sign(SignUtils.createLinkString(rep.getResponseEntity()), "123", "UTF-8");
          return sign;
     }
     
     /**
      * 充值结果完成回调函数了
      * @param request
      * @param input_charset
      * @param sign_type
      * @param sign
      * @param rescode
      * @param prdordernum
      * @param extordernum
      * @param returndate
      * @param productcode
      * @param productname
      * @param tradecard
      * @param posid
      * @param befbal
      * @param tranamt
      * @return
      */
     @RequestMapping("/toReturnPageCollBack")
     public ModelAndView toReturnResult(HttpServletRequest request,
         @RequestParam(value="input_charset",required=false)String input_charset,
         @RequestParam(value="sign_type",required=false)String sign_type,
         @RequestParam(value="sign",required=false)String sign,
         @RequestParam(value="rescode",required=false)String rescode,
         @RequestParam(value="prdordernum",required=false)String prdordernum,
         @RequestParam(value="extordernum",required=false)String extordernum,
         @RequestParam(value="returndate",required=false)String returndate,
         @RequestParam(value="productcode",required=false)String productcode,
         @RequestParam(value="productname",required=false) String productname,
         @RequestParam(value="tradecard",required=false) String tradecard,
         @RequestParam(value="posid",required=false) String posid,
         @RequestParam(value="befbal",required=false) String befbal,
         @RequestParam(value="tranamt",required=false) String tranamt
         ) {
     Map<String, String> map = new HashMap<String, String>();
     ModelAndView mv = new ModelAndView();
     map.put("input_charset", input_charset);
     map.put("prdordernum", prdordernum);
     map.put("extordernum", extordernum);
     map.put("returndate", returndate);
     map.put("productcode", productcode);
     map.put("productname", productname);
     map.put("tradecard", tradecard);
     map.put("posid", posid);
     map.put("befbal", befbal);
     map.put("tranamt", tranamt);
     log.info("Return Page Check CallBack map="+map);
     log.info("Return Page Check CallBack  Start================================================");
     try {
         if("000000".equals(rescode)){
             log.info("Return Page Check CallBack sign:"+sign);
             String mySign = SignUtils.sign(SignUtils.createLinkString(map), "123", input_charset);
             log.info("Return Page Check CallBack mySign:"+mySign);
             if(!mySign.equals(sign)){
                 mv.setViewName("notFound");
                 log.info("Return Page Check CallBack notFound:"+mv);
                 return mv;
             }else{
                 ThirdlyProduct thirdlyProduct = new ThirdlyProduct();
                 thirdlyProduct.setPrdordernum(prdordernum);
                 thirdlyProduct.setExtordernum(extordernum);
                 thirdlyProduct.setReturndate(returndate);
                 thirdlyProduct.setProductcode(productcode);
                 thirdlyProduct.setProductname(productname);
                 thirdlyProduct.setTradecard(tradecard);
                 thirdlyProduct.setPosid(posid);
                 thirdlyProduct.setBefbal(befbal == null ? "" : String.format("%.2f",Double.parseDouble(befbal) / 100));
                 thirdlyProduct.setTranamt(tranamt == null ? "" : String.format("%.2f",Double.parseDouble(tranamt) / 100));
                 request.setAttribute("thirdlyProduct", thirdlyProduct);
                 mv.setViewName("thirdlyView/thirdlyToReturnResult");
                 log.info("Return Page Check CallBack thirdlyToReturnResult:"+mv);
                 return mv;
             }
         }else{
             request.setAttribute("rescode", rescode);
             mv.setViewName("thirdlyView/thirdlyCode");
             log.info("thirdlyCode:"+mv);
             return mv;
         } 
     }
     catch (Exception e) {
         mv.setViewName("error");
         return mv;
     }
 }
     
     @RequestMapping("/toReturnServer")
     public ModelAndView toReturnServer(HttpServletRequest request,
         @RequestParam(value="input_charset",required=false)String input_charset,
         @RequestParam(value="sign_type",required=false)String sign_type,
         @RequestParam(value="sign",required=false)String sign,
         @RequestParam(value="rescode",required=false)String rescode,
         @RequestParam(value="prdordernum",required=false)String prdordernum,
         @RequestParam(value="extordernum",required=false)String extordernum,
         @RequestParam(value="returndate",required=false)String returndate,
         @RequestParam(value="productcode",required=false)String productcode,
         @RequestParam(value="productname",required=false) String productname,
         @RequestParam(value="tradecard",required=false) String tradecard,
         @RequestParam(value="posid",required=false) String posid,
         @RequestParam(value="befbal",required=false) String befbal,
         @RequestParam(value="tranamt",required=false) String tranamt
         ) {
     Map<String, String> map = new HashMap<String, String>();
     ModelAndView mv = new ModelAndView();
     map.put("input_charset", input_charset);
     map.put("prdordernum", prdordernum);
     map.put("extordernum", extordernum);
     map.put("returndate", returndate);
     map.put("productcode", productcode);
     map.put("productname", productname);
     map.put("tradecard", tradecard);
     map.put("posid", posid);
     map.put("befbal", befbal);
     map.put("tranamt", tranamt);
     log.info("Return Service Page Check CallBack map="+map);
     log.info("Return Service Page Check CallBack  Start================================================");
     try {
         if("000000".equals(rescode)){
             log.info("Return  Service Page Check CallBack sign:"+sign);
             String mySign = SignUtils.sign(SignUtils.createLinkString(map), "123", input_charset);
             log.info("Return  Service Page Check CallBack mySign:"+mySign);
             if(!mySign.equals(sign)){
                 mv.setViewName("notFound");
                 log.info("Return  Service Page Check CallBack notFound:"+mv);
                 return mv;
             }else{
                 ThirdlyProduct thirdlyProduct = new ThirdlyProduct();
                 thirdlyProduct.setPrdordernum(prdordernum);
                 thirdlyProduct.setExtordernum(extordernum);
                 thirdlyProduct.setReturndate(returndate);
                 thirdlyProduct.setProductcode(productcode);
                 thirdlyProduct.setProductname(productname);
                 thirdlyProduct.setTradecard(tradecard);
                 thirdlyProduct.setPosid(posid);
                 thirdlyProduct.setBefbal(befbal == null ? "" : String.format("%.2f",Double.parseDouble(befbal) / 100));
                 thirdlyProduct.setTranamt(tranamt == null ? "" : String.format("%.2f",Double.parseDouble(tranamt) / 100));
                 request.setAttribute("thirdlyProduct", thirdlyProduct);
                 mv.setViewName("thirdlyView/thirdlyToReturn");
                 log.info("Return  Service Page Check CallBack thirdlyToReturn:"+mv);
                 return mv;
             }
         }else{
             request.setAttribute("rescode", rescode);
             mv.setViewName("thirdlyView/thirdlyCode");
             log.info("thirdlyCode:"+mv);
             return mv;
         } 
     }
     catch (Exception e) {
         mv.setViewName("error");
         return mv;
     }
 }
}
