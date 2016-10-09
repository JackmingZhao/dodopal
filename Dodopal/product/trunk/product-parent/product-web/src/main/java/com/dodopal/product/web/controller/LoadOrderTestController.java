package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.product.business.bean.CardRechargeBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.business.service.PrdProductYktService;

/**
 * 圈存订单测试用controller
 * 
 * @author dodopal
 *
 */
@Controller
@RequestMapping("/testLoadOrder")
public class LoadOrderTestController {

    @Autowired
    AreaService areaService;
    @Autowired
    CityFindService cityFindService;
    @Autowired
    PrdProductYktService prdProductYktService;
    
    /**
     * 外接商户圈存订单__下单接口测试用
     * 
     * @param request
     * @return
     */
    @RequestMapping("/testbookExtLoadOrder")
    public ModelAndView toLoadOrder(HttpServletRequest request) {
        return new ModelAndView("applicationCenter/testLoadOrder/testExtLoadOrder");
    }

    /**
     * 切换业务城市
     * 
     * @param request
     * @param cityCode
     * @return
     */
    @RequestMapping("/toChangeCity")
    public ModelAndView toChangeCity(HttpServletRequest request,@RequestParam String cityCode) {

        request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_CITY, cityCode);  
        //一卡通圈存
        return new ModelAndView("applicationCenter/testLoadOrder/testExtLoadOrder");
    }
    
    /**
     * 获取城市信息及城市充值产品信息
     * 
     * @param request
     * @param cityCode
     * @return
     */
    @RequestMapping("/getCityProductInfo")
    public @ResponseBody DodopalResponse<CardRechargeBean> getCardRechargeInfo(HttpServletRequest request,@RequestBody String cityCode) {
          DodopalResponse<CardRechargeBean> dodopalResponse = new DodopalResponse<CardRechargeBean>();
        try{
            CardRechargeBean cardRechargeBean =  new CardRechargeBean();
            
            cardRechargeBean.setAreaList(cityFindService.getAllBusinessCity());
            
            //查询城市
            cityCode = (String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_CITY);
            if (StringUtils.isBlank(cityCode)) {
                cityCode = "1791";//默认南昌
            }
            Area chooseCity = areaService.findCityByCityCode(cityCode);
            cardRechargeBean.setCityName(chooseCity.getCityName());
            cardRechargeBean.setCityCode(chooseCity.getCityCode());
            
            List<Integer> prdProductYktList = new ArrayList<Integer>();

            //查询城市产品
            List<PrdProductYkt>  proRespone = prdProductYktService.findAvailableIcdcProductsInCity(cardRechargeBean.getCityCode());
            if(CollectionUtils.isNotEmpty(proRespone)){
                for(PrdProductYkt bean:proRespone){
                    prdProductYktList.add(bean.getProPrice()/100);
                }
            }
            
            Collections.sort(prdProductYktList);
            cardRechargeBean.setPrdProductYktList(proRespone);
            cardRechargeBean.setProPriceList(prdProductYktList);
            dodopalResponse.setResponseEntity(cardRechargeBean);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
        }catch(DDPException e){
            if(!ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
                 dodopalResponse.setNewMessage(ResponseCode.PRODUCT_ERROR);
            }
        }catch(Exception e){
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }
    
    
    
    
    
}
