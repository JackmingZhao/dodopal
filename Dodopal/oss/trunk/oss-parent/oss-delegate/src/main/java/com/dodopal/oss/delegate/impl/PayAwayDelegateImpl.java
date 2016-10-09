package com.dodopal.oss.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayAwayQueryDTO;
import com.dodopal.api.payment.facade.PayWayExternalFacade;
import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PayAwayDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("payAwayDelegate")
public class PayAwayDelegateImpl extends BaseDelegate implements PayAwayDelegate {

    //查询
    public DodopalResponse<DodopalDataPage<PayAwayDTO>> findPayAwayListByPage(PayAwayQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PayAwayDTO>> response = new DodopalResponse<DodopalDataPage<PayAwayDTO>>();
        if(PayAwayEnum.PAY_EXTERNAL.getCode().equals(queryDTO.getPayAwayType())){
            PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.findPayWayExternalByPage(queryDTO);
        }
        return response;
    }
    
    
    //新增
    @Override
    public DodopalResponse<Integer> savePayAway(PayAwayDTO payDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        if(PayAwayEnum.PAY_EXTERNAL.getCode().equals(payDTO.getPayAwayType())){
            PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.savePayWayExternal(payDTO);
        }
        return response;
    }

    //修改
    @Override
    public DodopalResponse<Integer> updatePayAway(PayAwayDTO payDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
       if(PayAwayEnum.PAY_EXTERNAL.getCode().equals(payDTO.getPayAwayType())){
           PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
           response = facade.updatePayAway(payDTO);
       }
        return response;
    }

    //删除
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        if(PayAwayEnum.PAY_EXTERNAL.getCode().equals(payAwayType)){
            PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.batchDelPayWayExternalByIds(ids);
        }
        return response;
    }

    //停用启用
    public DodopalResponse<Integer> enableOrDisablePayAway(List<String> ids, String activate, String payAwayType,String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        if(PayAwayEnum.PAY_EXTERNAL.getCode().equals(payAwayType)){
            PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.updatePayWayExternalActivate(ids, activate, updateUser);
        }
        return response;
    }


    public PayAwayDTO findPayExternalById(String id,String payAwayType ) {
        PayAwayDTO payAwayDTO = new PayAwayDTO();
        if(PayAwayEnum.PAY_EXTERNAL.getCode().equals(payAwayType)){
            PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            payAwayDTO = facade.findPayExternalById(id);
        }
        return payAwayDTO;
    }


    public List<PayConfigDTO> findPayWayNameByPayType(String payType) {
        List<PayConfigDTO> payConfigDTOList = new ArrayList<PayConfigDTO>();
        PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        payConfigDTOList = facade.findPayWayNameByPayType(payType);
        return payConfigDTOList;
    }


    public int countPayExternal(String merCode, String payConfigId) {
        PayWayExternalFacade facade = getFacade(PayWayExternalFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.countBymerCodeAndPayConfigId(merCode, payConfigId);
    }

}
