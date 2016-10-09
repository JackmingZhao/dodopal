package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayConfigQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.bean.query.PayConfigQuery;
import com.dodopal.oss.business.service.PayConfigService;
import com.dodopal.oss.delegate.PayConfigDelegate;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月30日 下午6:53:41
 * 支付配置信息
 */
@Service
public class PayConfigServiceImpl implements PayConfigService{
    private final static Logger log = LoggerFactory.getLogger(PayConfigServiceImpl.class);

    @Autowired
    PayConfigDelegate payConfigDelegate;
    @Override//
    public DodopalResponse<DodopalDataPage<PayConfigBean>> findPayConfigByPage(PayConfigQuery payConfigQuery) {
        DodopalResponse<DodopalDataPage<PayConfigBean>> rtResponse = new DodopalResponse<DodopalDataPage<PayConfigBean>>();
        try {
            PayConfigQueryDTO findDto = new PayConfigQueryDTO();
            PropertyUtils.copyProperties(findDto, payConfigQuery);
            DodopalResponse<DodopalDataPage<PayConfigDTO>> getResponse = payConfigDelegate.findPayConfigByPage(findDto);
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())) {
                List<PayConfigBean> resResult = new ArrayList<PayConfigBean>();
                PayConfigBean retBean = null;
                for (PayConfigDTO retDTO : getResponse.getResponseEntity().getRecords()) {
                    retBean = new PayConfigBean();
                    PropertyUtils.copyProperties(retBean, retDTO);
                    resResult.add(retBean);
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(getResponse.getResponseEntity());
                DodopalDataPage<PayConfigBean> pages = new DodopalDataPage<PayConfigBean>(page, resResult);
                rtResponse.setCode(getResponse.getCode());
                rtResponse.setResponseEntity(pages);
            } else {
                rtResponse.setCode(getResponse.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("MerUserServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return rtResponse;
    }
    @Override
    public DodopalResponse<PayConfigBean> findPayConfigById(String id) {
        DodopalResponse<PayConfigBean> response = new DodopalResponse<PayConfigBean>();
        DodopalResponse<PayConfigDTO> payConfigResult = payConfigDelegate.findPayConfigById(id);
        try{
            if(ResponseCode.SUCCESS.equals(payConfigResult.getCode())){
                PayConfigBean payConfigBean = new PayConfigBean();
                if(null!=payConfigResult.getResponseEntity()){
                    PropertyUtils.copyProperties(payConfigBean,payConfigResult.getResponseEntity());
                }  
                response.setResponseEntity(payConfigBean);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(payConfigResult.getCode());
            }
        }catch(HessianRuntimeException e){
            log.debug("PayConfigServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("PayConfigServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
    
    public DodopalResponse<Boolean> batchActivatePayConfig(String flag, List<String> ids, String updateUser){
        return payConfigDelegate.batchActivatePayConfig(flag, ids, updateUser);
    }
    @Override
    public DodopalResponse<Boolean> updatePayConfig(PayConfigBean payConfigBean) {
        PayConfigDTO payConfigDTO = new PayConfigDTO();
        try {
            PropertyUtils.copyProperties(payConfigDTO, payConfigBean);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return payConfigDelegate.updatePayConfig(payConfigDTO);
    }
    
    public DodopalResponse<Boolean> updatePayConfigBankGateway(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum gateType){
        return payConfigDelegate.updatePayConfigBankGateway(ids, updateUser, payConfigId,gateType);
    }
}
