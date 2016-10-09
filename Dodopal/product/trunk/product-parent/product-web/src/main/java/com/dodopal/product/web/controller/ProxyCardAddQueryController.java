package com.dodopal.product.web.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.bean.ProxyCardAddRequestBean;
import com.dodopal.product.business.bean.ProxyCardAddResultBean;
import com.dodopal.product.business.service.ProxyCardAddQueryService;
import com.dodopal.product.web.param.ProxyCardAddRequest;
import com.dodopal.product.web.param.ProxyCardAddResponse;
import com.dodopal.product.web.param.QueryTranscationRequest;

/**
 * 4.2.1 查询城市一卡通充值记录 （新系统门户和VC端接入）  
 * @author xiongzhijing@dodopal.com
 * @version 2016年3月30日
 */
@Controller
@RequestMapping("/payment")
public class ProxyCardAddQueryController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    ProxyCardAddQueryService proxyCardAddQueryService;
    
    /**
     * 4.2.1 交易记录查询接口(移动端、VC端接口)
     */
    @RequestMapping("/cardTradeList")
    public @ResponseBody ProxyCardAddResponse cardTradeList(HttpServletRequest request) {
        ProxyCardAddResponse response = new ProxyCardAddResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("queryTranscation:交易记录查询接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("queryTranscation:交易记录查询接口:接收到的jsondata参数:" + jsondata);
            }
            ProxyCardAddRequest proxyCardAddRequest = convertJsonToRequest(jsondata, ProxyCardAddRequest.class);// 转换jsondata
            baseCheck(proxyCardAddRequest);// 通用参数校验
           // queryTranscationRequestCheck(proxyCardAddRequest);// 业务入参校验
            signCheck(key, proxyCardAddRequest);// 验签

            //查询条件
            ProxyCardAddRequestBean requestDto = new ProxyCardAddRequestBean();
            requestDto.setCardno(proxyCardAddRequest.getCardno());
            requestDto.setCityno(proxyCardAddRequest.getCityno());
            requestDto.setEnddate(proxyCardAddRequest.getEnddate());
            requestDto.setStartdate(proxyCardAddRequest.getStartdate());
            requestDto.setPosid(proxyCardAddRequest.getPosid());
            requestDto.setProxyid(proxyCardAddRequest.getProxyid());
            requestDto.setProxyorderno(proxyCardAddRequest.getProxyorderno());
            requestDto.setStatus(proxyCardAddRequest.getStatus());
            
            // 查询
            DodopalResponse<List<ProxyCardAddResultBean>> rTResponse = proxyCardAddQueryService.queryCardTradeList(requestDto);

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())) {
                response.setRespcode(rTResponse.getCode());
                response.setProxyCardAddResultBeanList(rTResponse.getResponseEntity());
            } else {
                response.setRespcode(rTResponse.getCode());
            }  
            //TODO 签名
            // sign(key, response);
        } catch (DDPException e) {
            response.setRespcode(e.getCode());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isInfoEnabled()) {
            logger.info("queryTranscation:交易记录查询接口:返回respcode:" + response.getRespcode());
        }
        return response;
    }

  
}
