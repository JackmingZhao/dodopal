package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.CreateTranDTO;
import com.dodopal.api.payment.dto.TranscationListResultDTO;
import com.dodopal.api.payment.dto.query.TranscationRequestDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.product.business.bean.CreatePayTransactionBean;
import com.dodopal.product.business.bean.TranscationListResultBean;
import com.dodopal.product.business.bean.TranscationRequestBean;
import com.dodopal.product.business.service.PayTransactionService;
import com.dodopal.product.delegate.PayTransactionDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * 3.20 查询交易记录 （手机端和VC端接入）
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 */
@Service
public class PayTransactionServiceImpl implements PayTransactionService {
    private final static Logger log = LoggerFactory.getLogger(PayTransactionServiceImpl.class);

    @Autowired
    PayTransactionDelegate payTransactionDelegate;

    //查询交易记录 
    public DodopalResponse<List<TranscationListResultBean>> queryPayTranscation(TranscationRequestBean requestDto) {
        DodopalResponse<List<TranscationListResultBean>> response = new DodopalResponse<List<TranscationListResultBean>>();
        try {
            List<TranscationListResultBean> transcationListResultBeanList = new ArrayList<TranscationListResultBean>();
            TranscationRequestDTO transcationRequestDTO = new TranscationRequestDTO();
            PropertyUtils.copyProperties(transcationRequestDTO, requestDto);
            DodopalResponse<List<TranscationListResultDTO>> rtResponse = payTransactionDelegate.queryPayTranscation(transcationRequestDTO);

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && rtResponse.getResponseEntity() != null) {
                for (TranscationListResultDTO transcationListResultDTO : rtResponse.getResponseEntity()) {
                    TranscationListResultBean transcationListResultBean = new TranscationListResultBean();
                    PropertyUtils.copyProperties(transcationListResultBean, transcationListResultDTO);
                    transcationListResultBeanList.add(transcationListResultBean);

                }
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(transcationListResultBeanList);

        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
            log.error("3.20 查询交易记录 （手机端和VC端接入）   PayTransactionServiceImpl queryPayTranscation call   HessianRuntimeException e:", e);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("3.20 查询交易记录 （手机端和VC端接入）   PayTransactionServiceImpl queryPayTranscation call Exception e:", e);
        }
        return response;
    }

    //创建交易流水 （自助终端）
    @Override
    public DodopalResponse<String> createTranscation(CreatePayTransactionBean requestDto) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            CreateTranDTO createTranDTO = new CreateTranDTO();
            PropertyUtils.copyProperties(createTranDTO, requestDto);
            createTranDTO.setNotifyUrl(DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PRODUCT_URL)+"/callBackOrder?cityCode="+requestDto.getCitycode());//TODO
            DodopalResponse<String> rtResponse = payTransactionDelegate.createTranscation(createTranDTO);
            log.info("调用支付系统返回:" + JSON.toJSONString(rtResponse));
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && rtResponse.getResponseEntity() != null) {
                response.setResponseEntity(rtResponse.getResponseEntity());
            }
            response.setCode(rtResponse.getCode());

        }
        catch (HessianRuntimeException e) {
            response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
            log.error("创建交易流水 （自助终端 ）  PayTransactionServiceImpl createTranscation call   HessianRuntimeException e:", e);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.error("创建交易流水 （自助终端 ）   PayTransactionServiceImpl createTranscation call Exception e:", e);
        }
        return response;
    }

}
