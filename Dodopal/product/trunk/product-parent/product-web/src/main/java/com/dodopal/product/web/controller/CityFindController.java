package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.web.mobileBean.AreaModel;
import com.dodopal.product.web.param.CityFindRequest;
import com.dodopal.product.web.param.CityFindResponse;

/**
 * @author taolj
 * @version 1.0
 * @since 创建时间：2015年9月24日
 */
@Controller
@RequestMapping("/cityFind")
public class CityFindController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CityFindController.class);
    @Autowired
    private CityFindService cityFindService;

    /**
     * 城市列表查询接口(手机端)
     */
    @RequestMapping("/findYktCitys")
    public @ResponseBody CityFindResponse findYktCitys(HttpServletRequest request) {
        CityFindResponse response = new CityFindResponse();
        // TODO:签名验签秘钥如何获取
        String key = "123";
        try {
            // 获取jsondata
            String jsondata = request.getParameter("jsondata");
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("findYktCitys:城市列表查询接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("findYktCitys:城市列表查询接口:接收到的jsondata参数:" + jsondata);
            }
            // 转换jsondata
            CityFindRequest sendRequest = convertJsonToRequest(jsondata, CityFindRequest.class);
            // 通用参数校验
            baseCheck(sendRequest);
            // 验签
            signCheck(key, sendRequest);
            // 业务参数校验
            sendCheck(sendRequest);
            // 业务逻辑
            DodopalResponse<List<Area>> cityFindResponse = cityFindService.findYktCitys(MerUserTypeEnum.getMerUserUserTypeByCode(sendRequest.getCusttype()), sendRequest.getCustcode());
            if (ResponseCode.SUCCESS.equals(cityFindResponse.getCode())) {
                List<Area> areaList = cityFindResponse.getResponseEntity();
                if (CollectionUtils.isNotEmpty(areaList)) {
                    List<AreaModel> resultList = new ArrayList<AreaModel>();
                    for (Area area : areaList) {
                        AreaModel areaModel = new AreaModel();
                        areaModel.setCitycode(area.getCityCode());
                        areaModel.setCityname(area.getCityName());
                        areaModel.setYktcode(area.getBzCityCode());
                        resultList.add(areaModel);
                    }
                    response.setAreaList(resultList);
                }
                response.setRespcode(cityFindResponse.getCode());
            } else {
                response.setRespcode(cityFindResponse.getCode());
            }
            // 签名
            if (ResponseCode.SUCCESS.equals(response.getRespcode())) {
                sign(key, response);
            }
        }
        catch (DDPException e) {
            response.setRespcode(e.getCode());
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isInfoEnabled()) {
            logger.info("findYktCitys:城市列表查询接口:返回respcode:" + response.getRespcode());
        }
        return response;
    }

    private void sendCheck(CityFindRequest sendRequest) {
        // 客户类型
        String custType = sendRequest.getCusttype();
        if (!MerUserTypeEnum.contains(custType)) {
            throw new DDPException(ResponseCode.ACC_CUSTOMER_TYPE_ERROR);
        }
    }
}
