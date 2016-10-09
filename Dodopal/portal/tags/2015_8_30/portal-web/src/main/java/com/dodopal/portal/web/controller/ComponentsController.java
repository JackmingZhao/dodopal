package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DdicVo;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.DdicService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月7日 上午10:15:09
 */
@Controller
@RequestMapping("/components")
public class ComponentsController extends CommonController{
    @Autowired
    AreaService areaService;
    @Autowired
    DdicService ddicService;
    @RequestMapping("/getCityInfo")
    public  @ResponseBody DodopalResponse<Map<String,List<Area>>> getCityInfo(HttpServletRequest request,@RequestBody Map map) {
        List<Area> areaList= areaService.findAllCityInfo();
        DodopalResponse<Map<String,List<Area>>> response = new DodopalResponse<Map<String,List<Area>>>();
        Map <String,List<Area>> areamap = new HashMap<String,List<Area>>();
        List<Area> provinceList = new ArrayList<Area>();
        for(Area area:areaList){
            if(area.getParentCode().equals("0")){
                provinceList.add(area);
            }
        }
        areamap.put("0", provinceList);
        if(null!=provinceList){
            //拿到每个省份的数据
            for(Area province:provinceList){
                //找每个省份下的城市
                List<Area> cityList = new ArrayList<Area>();
                //把所有省市数据进行对比，抓取当前省份下的城市
                for(Area area:areaList){
                    if(area.getParentCode().equals(province.getCityCode())){
                        cityList.add(area);
                    }
                }
                //循环完毕，将当前省份code作为key保存，value为省份下城市的list
                areamap.put(province.getCityCode(), cityList);
            }
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(areamap);
        return response;
    }
    
    @RequestMapping("/getDdicList")
    public  @ResponseBody DodopalResponse<Map<String,List<DdicVo>>> getDdicList(Model model,HttpServletRequest request,Map map) {
        DodopalResponse<Map<String,List<DdicVo>>> response = new DodopalResponse<Map<String,List<DdicVo>>>();
        Map<String,List<DdicVo>> ddicMap = ddicService.getDdicMap();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(ddicMap);
        return response;
    }
   
}
