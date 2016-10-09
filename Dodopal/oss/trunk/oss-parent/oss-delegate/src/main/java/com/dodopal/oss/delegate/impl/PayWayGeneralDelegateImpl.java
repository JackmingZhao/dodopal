package com.dodopal.oss.delegate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PayWayGeneralDTO;
import com.dodopal.api.payment.dto.query.PayWayGeneralQueryDTO;
import com.dodopal.api.payment.facade.PayWayExternalFacade;
import com.dodopal.api.payment.facade.PayWayGeneralFacade;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.PayAwayEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PayWayGeneralDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("payWayGeneralDelegate")
public class PayWayGeneralDelegateImpl extends BaseDelegate implements PayWayGeneralDelegate{

    /**
     * 查询
     */
    public DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> findPayAwayListByPage(PayWayGeneralQueryDTO queryDTO) {
        DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> response = new DodopalResponse<DodopalDataPage<PayWayGeneralDTO>>();
        if("0".equals(queryDTO.getPayAwayType())){
            PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.findPayWayGeneralByPage(queryDTO);
        }
        return response;
        
        
        
    }
    
    
    /**
     * 新增
     */
    @Override
    public DodopalResponse<Integer> savePayAway(PayWayGeneralDTO payDTO, String payAwayType) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        if("0".equals(payAwayType)){
            PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.savePayWayGeneral(payDTO);
        }
        return response;
    }

    /**
     * 修改
     */
    public DodopalResponse<Boolean> updatePayAway(PayWayGeneralDTO payWayGeneralDTO, String payAwayType) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        if("0".equals(payAwayType)){
            PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.updatePayWayGeneral(payWayGeneralDTO);
        }
        return response;
    }

    /**
     * 删除
     */
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        if("0".equals(payAwayType)){
            PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.deletePayWayGeneral(ids);
        }
        return response;
    }

    /**
     * 停用启用
     */
    @Override
    public DodopalResponse<Integer> startOrStop(List<String> ids, ActivateEnum activate,String updateUser) {
        PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        DodopalResponse<Integer> response = facade.batchStartOrStop(activate, ids, updateUser);
        return response;
    }

    /**
     * 根据id查询
     */
    public DodopalResponse<PayWayGeneralDTO> findPayGeneralById(String id, String payAwayType) {
        DodopalResponse<PayWayGeneralDTO>  response = new DodopalResponse<PayWayGeneralDTO>();
        if("0".equals(payAwayType)){
            PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
            response = facade.findGeneralById(id);
        }
        return response;
    }


    /**
     * 根据支付类型查找支付方式
     */
    public List<PayConfigDTO> findPayWayNameByPayType(String payType) {List<PayConfigDTO> payConfigDTOList = new ArrayList<PayConfigDTO>();
    PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
    payConfigDTOList = facade.findPayWayNameByPayType(payType);
    return payConfigDTOList;}


    @Override
    public int countPayGeneral(String payConfigId) {
        PayWayGeneralFacade facade = getFacade(PayWayGeneralFacade.class, DelegateConstant.FACADE_PAYMENT_URL);
        return facade.countByPayConfigId(payConfigId);
    }


}
