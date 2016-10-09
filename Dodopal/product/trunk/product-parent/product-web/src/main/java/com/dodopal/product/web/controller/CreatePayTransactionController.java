package com.dodopal.product.web.controller;

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
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.bean.CreatePayTransactionBean;
import com.dodopal.product.business.service.PayTransactionService;
import com.dodopal.product.web.param.CreatePayTransactionRequest;
import com.dodopal.product.web.param.CreatePayTransactionResponse;

/**
 * 生成交易流水 （自助终端）
 * @author xiongzhijing@dodopal.com
 * @version 2016年4月12日
 */
@Controller
@RequestMapping("payment")
public class CreatePayTransactionController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CreatePayTransactionController.class);

    @Autowired
    PayTransactionService payTransactionService;

    /**
     * 生成交易流水(自助终端)
     */
    @RequestMapping("/createTranscation")
    public @ResponseBody
    CreatePayTransactionResponse createTranscation(HttpServletRequest request) {
        CreatePayTransactionResponse response = new CreatePayTransactionResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("createTranscation:创建交易流水:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("createTranscation:创建交易流水:接收到的jsondata参数:" + jsondata);
            }
            CreatePayTransactionRequest createPayTransactionRequest = convertJsonToRequest(jsondata, CreatePayTransactionRequest.class);// 转换jsondata
            baseCheck(createPayTransactionRequest);// 通用参数校验
            queryTranscationRequestCheck(createPayTransactionRequest);// 业务入参校验
            signCheck(key, createPayTransactionRequest);// 验签

            //查询条件
            CreatePayTransactionBean requestBean = new CreatePayTransactionBean();
            requestBean.setAmont(Long.valueOf(createPayTransactionRequest.getAmont()));
            requestBean.setCardnum(createPayTransactionRequest.getCardnum());
            requestBean.setCustcode(createPayTransactionRequest.getCustcode());
            requestBean.setCusttype(createPayTransactionRequest.getCusttype());
            requestBean.setPayid(createPayTransactionRequest.getPayid());
            requestBean.setPayprocerate(Double.valueOf(createPayTransactionRequest.getPayprocerate()));
            requestBean.setPayservicerate(Double.valueOf(createPayTransactionRequest.getPayservicerate()));
            requestBean.setProcode(createPayTransactionRequest.getProcode());
            requestBean.setReqdate(createPayTransactionRequest.getReqdate());
            requestBean.setSource(createPayTransactionRequest.getSource());
            requestBean.setBusinessType(RateCodeEnum.IC_LOAD.getCode());
            requestBean.setGoodsName(RateCodeEnum.IC_LOAD.getCode() + createPayTransactionRequest.getAmont());
            // 查询
            DodopalResponse<String> rTResponse = payTransactionService.createTranscation(requestBean);

            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())) {
                response.setRespcode(rTResponse.getCode());
                response.setPaymenttranNo(rTResponse.getResponseEntity());
            } else {
                response.setRespcode(rTResponse.getCode());
            }
            //TODO 签名
            sign(key, response);
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
            logger.info("createTranscation:创建交易流水:返回respcode:" + response.getRespcode());
        }
        return response;
    }

    /**
     * 生成交易流水（自助终端）
     * @param request
     */
    private void queryTranscationRequestCheck(CreatePayTransactionRequest request) {
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

    }

}
