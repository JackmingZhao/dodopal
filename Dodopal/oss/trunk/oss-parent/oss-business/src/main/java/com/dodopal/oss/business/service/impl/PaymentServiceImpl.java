package com.dodopal.oss.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.PaymentBean;
import com.dodopal.oss.business.model.dto.PayMentQuery;
import com.dodopal.oss.business.service.PaymentService;
import com.dodopal.oss.delegate.PaymentDelegate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final static Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    @Autowired
    PaymentDelegate paymentDelegate;
    

    @Override
    public DodopalResponse<DodopalDataPage<PaymentBean>> findPaymentListByPage(PayMentQuery payQuery) {
        DodopalResponse<DodopalDataPage<PaymentBean>> response = new DodopalResponse<DodopalDataPage<PaymentBean>>();
        PaymentQueryDTO queryDto = new PaymentQueryDTO();
       
        try {
            PropertyUtils.copyProperties(queryDto, payQuery);
            DodopalResponse<DodopalDataPage<PaymentDTO>>  dtoPageRes  = paymentDelegate.findPaymentListByPage(queryDto); 
            
            if (ResponseCode.SUCCESS.equals(dtoPageRes.getCode())) {
                List<PaymentBean> resResult = new ArrayList<PaymentBean>();
                PaymentBean paymentBean = null;
                for (PaymentDTO paymentDTO : dtoPageRes.getResponseEntity().getRecords()) {
                    paymentBean = new PaymentBean();
                    PropertyUtils.copyProperties(paymentBean, paymentDTO);
                    paymentBean.setPayMoney((double)paymentDTO.getPayMoney()/100);
                    paymentBean.setPayServiceFee((double)paymentDTO.getPayServiceFee()/100);
                    resResult.add(paymentBean);
                }
//              //后台分页的总页数与count                
                PageParameter page = DodopalDataPageUtil.convertPageInfo(dtoPageRes.getResponseEntity());
                DodopalDataPage<PaymentBean> pages = new DodopalDataPage<PaymentBean>(page, resResult);
                response.setCode(dtoPageRes.getCode());
                response.setResponseEntity(pages);
            }else{
                response.setCode(dtoPageRes.getCode());
            }
            
        }catch(HessianRuntimeException e){
            log.debug("PaymentServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.debug("PaymentServiceImpl call error", e);
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 查看单个流水信息
     */
    @Override
    public DodopalResponse<PaymentBean> findPayment(String id) {
        DodopalResponse<PaymentBean> response = new DodopalResponse<PaymentBean>();
        try {

            //取得个人用户信息
            DodopalResponse<PaymentDTO> payment = paymentDelegate.findPayment(id);
            if (ResponseCode.SUCCESS.equals(payment.getCode())) {
                PaymentBean paymentBean = new PaymentBean();
                //转为前端对象
                PropertyUtils.copyProperties(paymentBean, payment.getResponseEntity());
                paymentBean.setPayMoney((double)payment.getResponseEntity().getPayMoney()/100);
                paymentBean.setPayServiceFee((double)payment.getResponseEntity().getPayServiceFee()/100);
                response.setResponseEntity(paymentBean);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(payment.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("PaymentServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("PaymentServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response; 
    }
    
}
