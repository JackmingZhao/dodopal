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
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.bean.TranscationListResultBean;
import com.dodopal.product.business.bean.TranscationRequestBean;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.PayTransactionService;
import com.dodopal.product.web.param.QueryTranscationRequest;
import com.dodopal.product.web.param.QueryTranscationResponse;

/**
 * 3.20 查询交易记录 （手机端和VC端接入）  
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 */
@Controller
@RequestMapping("/payment")
public class PayTransactionController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
    

    @Autowired
    PayTransactionService payTransactionService;
    @Autowired
    MerchantUserService merchantUserService;
    
    /**
     * 3.20 交易记录查询接口(移动端、VC端接口)
     */
    @RequestMapping("/queryTranscation")
    public @ResponseBody QueryTranscationResponse queryTranscation(HttpServletRequest request) {
        QueryTranscationResponse response = new QueryTranscationResponse();
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
            QueryTranscationRequest queryTranscationRequest = convertJsonToRequest(jsondata, QueryTranscationRequest.class);// 转换jsondata
            baseCheck(queryTranscationRequest);// 通用参数校验
            queryTranscationRequestCheck(queryTranscationRequest);// 业务入参校验
            signCheck(key, queryTranscationRequest);// 验签

            //查询条件
            TranscationRequestBean requestDto = new TranscationRequestBean();
            requestDto.setCustcode(queryTranscationRequest.getCustcode());
            requestDto.setCusttype(queryTranscationRequest.getCusttype());
            requestDto.setEndamt(queryTranscationRequest.getEndamt());
            requestDto.setEnddate(queryTranscationRequest.getEnddate());
            requestDto.setStartamt(queryTranscationRequest.getStartamt());
            requestDto.setStartdate(queryTranscationRequest.getStartdate());
            requestDto.setTranoutstatus(queryTranscationRequest.getTranoutstatus());
            requestDto.setTrantype(queryTranscationRequest.getTrantype());
            
            //区分 管理员 还是操作员
            String merUserFlag = "";
            if(MerUserTypeEnum.MERCHANT.getCode().equals(queryTranscationRequest.getCusttype())){
                DodopalResponse<MerchantUserBean> merchantBeans = merchantUserService.findUserInfoById(queryTranscationRequest.getUserid());
                merUserFlag = merchantBeans.getResponseEntity().getMerUserFlag();
            }
            
            //判断用户是否为操作员
            if(MerUserFlgEnum.COMMON.getCode().equals(merUserFlag)){
                requestDto.setUserid(queryTranscationRequest.getUserid());
            }
       
            // 查询
            DodopalResponse<List<TranscationListResultBean>> rTResponse = payTransactionService.queryPayTranscation(requestDto);

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())) {
                response.setRespcode(rTResponse.getCode());
                response.setTranscationListResultDTO(rTResponse.getResponseEntity());
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

    
    
    /**
     * 3.20 交易记录查询接口     业务入参检查
     *
     * @param request
     */
    private void queryTranscationRequestCheck(QueryTranscationRequest request) {
        // 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = request.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTTYPE_ERROR);
        }
        // 客户号    String[40]   不为空
        String custcode = request.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }
        // 用户id    String[20]   不为空
        String userid = request.getUserid();
        if (!DDPStringUtil.existingWithLength(userid, 20)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_USERID_NULL);
        }
    }


}
