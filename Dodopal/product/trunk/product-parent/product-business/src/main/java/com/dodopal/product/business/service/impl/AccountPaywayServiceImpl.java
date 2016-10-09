package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.PayWayResultBean;
import com.dodopal.product.business.service.AccountPaywayService;
import com.dodopal.product.delegate.AccountPaywayDelegate;

@Service
public class AccountPaywayServiceImpl implements AccountPaywayService{

	private final static Logger log = LoggerFactory.getLogger(AccountPaywayServiceImpl.class);
	
	@Autowired
	private AccountPaywayDelegate accountPaywayDelegate;
	
	@Override
	public DodopalResponse<List<PayWayResultBean>> findCommonPayWay(boolean ext, String userCode) {
        log.info("findCommonPayWay this userCode:"+userCode+",ext:"+ext);
        DodopalResponse<List<PayWayResultBean>> response = new DodopalResponse<List<PayWayResultBean>>();
        try {
            DodopalResponse<List<PayWayDTO>> payWayDTOList = accountPaywayDelegate.findCommonPayWay(ext, userCode);
            List<PayWayResultBean> payWayBeanList = new ArrayList<PayWayResultBean>();
            if (payWayDTOList.getResponseEntity() != null && payWayDTOList.getResponseEntity().size() > 0) {
                for (PayWayDTO payWayDTO : payWayDTOList.getResponseEntity()) {
                	PayWayResultBean payWayBean = new PayWayResultBean();
                	payWayBean.setPaywayid(payWayDTO.getId());
                	payWayBean.setPaylogoname(payWayDTO.getPayLogo());
                	payWayBean.setPaywayname(payWayDTO.getPayName());
                	payWayBean.setPaytype(payWayDTO.getPayType());
                   // PropertyUtils.copyProperties(payWayBean, payWayDTO);
                    payWayBeanList.add(payWayBean);
                }
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payWayBeanList);
            }else{
            	response.setCode(ResponseCode.PAY_CONFIG_NULL);
            }
        }catch(HessianRuntimeException e){
            log.error("AccountPaywayServiceImpl findCommonPayWay:支付方式查询,Hessian链接异常", e);
            e.printStackTrace();
            response.setCode(ResponseCode.PRODUCT_CALL_PAYMENT_ERROR);
        }catch (Exception e) {
            log.error("AccountPaywayServiceImpl findCommonPayWay:支付方式查询,系统错误 " +e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

}
