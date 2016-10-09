package com.dodopal.product.delegate.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.MerchantDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;
@Service("merchantDelegate")
public class MerchantDelegateImpl extends BaseDelegate implements MerchantDelegate { 
 
	/**
	 * 查询商户详情
	 */
	@Override
	public DodopalResponse<MerchantDTO> findMerchantByCode(String merCode) {
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<MerchantDTO> response = facade.findMerchantByMerCode(merCode);
	    return response;
	}
	  /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param merCode
     * @return
     */
	public DodopalResponse<List<Area>> findMerCitys(String merCode){
		   MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		    DodopalResponse<List<Area>> response = facade.findMerCitys(merCode);
		    return response;
	}
	@Override
	public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(
			String merCode) {
		 	MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		 	DodopalResponse<List<MerBusRateDTO>> response = facade.findMerBusRateByMerCode(merCode);
		    return response;
	}
	@Override
	public DodopalResponse<List<Area>> findMerCitysPayment(String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<List<Area>> response = facade.findMerCitysPayment(merCode);	 
	    return response;
	}
	@Override
	public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(
			String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<List<MerRateSupplementDTO>> response = facade.findMerRateSupplementsByMerCode(merCode);	 
		return response;
	}
	@Override
	public DodopalResponse<Map<String, String>> validateMerchantForIcdcRecharge(
			String merchantNum, String userId, String posId,
			String providerCode, String source) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<Map<String, String>> response = facade.validateMerchantForIcdcRecharge(
				 merchantNum, userId,  posId,
				 providerCode,  source);	 
		return response;
	}
    @Override
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcLoad(MerUserTypeEnum userType, String merCode, String providerCode) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> response = facade.validateMerchantForIcdcLoad(userType, merCode, providerCode);   
        return response;
    }
    
    /**************************************************** 供应商信息管理开始 *****************************************************/

    @Override
    public DodopalResponse<String> addProviderRegister(MerchantDTO merchantDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.providerRegister(merchantDTO);
        return response;
    }

    @Override
    public DodopalResponse<String> upProviderRegister(MerchantDTO merchantDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.providerUpdate(merchantDTO);
        return response;
    }
    /**************************************************** 供应商信息管理结束 *****************************************************/
    
    @Override
    public DodopalResponse<Map<String, String>> getMerchantByPosCode(String posId) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Map<String, String>> response = facade.getMerchantByPosCode(posId);     
        return response;
    }

}
