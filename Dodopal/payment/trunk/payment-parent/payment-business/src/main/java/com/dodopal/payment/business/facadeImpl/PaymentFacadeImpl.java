package com.dodopal.payment.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PaymentDTO;
import com.dodopal.api.payment.dto.query.PaymentQueryDTO;
import com.dodopal.api.payment.facade.PaymentFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.payment.business.model.Payment;
import com.dodopal.payment.business.model.query.PaymentQuery;
import com.dodopal.payment.business.service.PaymentService;

@Service("paymentFacade")
public class PaymentFacadeImpl implements PaymentFacade {
    private final static Logger log = LoggerFactory.getLogger(PaymentFacadeImpl.class);

    @Autowired
    private PaymentService paymentService;

    @Override
    public DodopalResponse<DodopalDataPage<PaymentDTO>> findPayMentByPage(PaymentQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PaymentDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<PaymentDTO>>();
        PaymentQuery paymentQuery = new PaymentQuery();
        try {
            PropertyUtils.copyProperties(paymentQuery, queryDTO);
            DodopalDataPage<Payment> pagelist = paymentService.findPaymentByPage(paymentQuery);
            List<Payment> paymentList = pagelist.getRecords();
            List<PaymentDTO> payDTOList = new ArrayList<PaymentDTO>();
            if (CollectionUtils.isNotEmpty(paymentList)) {
                for (Payment paymentTemp : paymentList) {
                    PaymentDTO paymentDTO = new PaymentDTO();
                    PropertyUtils.copyProperties(paymentDTO, paymentTemp);
                    payDTOList.add(paymentDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<PaymentDTO> pages = new DodopalDataPage<PaymentDTO>(page, payDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (Exception e) {
            log.debug("PaymentFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<PaymentDTO> findPaymentInfoById(String id) {
        DodopalResponse<PaymentDTO> dodopalResponse = new DodopalResponse<PaymentDTO>();
        Payment payment = paymentService.findPaymentById(id);
        PaymentDTO paymentDTO = new PaymentDTO();
        try {
            if (null != payment) {
                PropertyUtils.copyProperties(paymentDTO, payment);
            }
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(paymentDTO);
        }
        catch (Exception e) {
            log.debug("PaymentFacadeImpl call error", e);
            e.printStackTrace();
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

}
