package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.facade.ProductYktFacade;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantExtendDTO;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerchantDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;
@Service("merchantDelegate")
public class MerchantDelegateImpl extends BaseDelegate implements MerchantDelegate { 
	 /**
	  * 查询商户列表信息
	  */
	@Override
	public DodopalResponse<List<MerchantDTO>> findMerchantList(MerchantDTO merchantDTO) {
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<List<MerchantDTO>> response = facade.findMerchant(merchantDTO);
	    return response;
	}
	/**
	 * 保存商户信息
	 */
	@Override
	public DodopalResponse<String> saveMerchant(MerchantDTO merchantDTO) {
	    //MerchantFacade facade = RemotingCallUtil.getHessianService("http://192.168.1.107:8082/users-web/hessian/index.do?id=", MerchantFacade.class);
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<String> response = facade.merchantRegister(merchantDTO);
	    return response;
	}
	
	
	/**
	 * 停启用商户
	 */
	@Override
	public DodopalResponse<Integer> startAndDisableMerchant(List<String> merCode,String activate, String updateUser) {
	    //MerchantFacade facade = RemotingCallUtil.getHessianService("http://192.168.1.101:82/users-web/hessian/index.do?id=", MerchantFacade.class);
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<Integer> response = facade.updateMerchantActivate(activate, merCode, updateUser);
	    return response;
	}
	
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
	 * 查看单个用户信息
	 */
	@Override
	public  DodopalResponse<MerchantDTO> findMerchants(String merCode) {
		   	//MerchantFacade facade = RemotingCallUtil.getHessianService("http://192.168.1.107:8082/users-web/hessian/index.do?id=", MerchantFacade.class);
		 	MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		    DodopalResponse<MerchantDTO> response = facade.findMerchantByMerCode(merCode);
		    return response;
	}
	/***
	 * 更新商户信息
	 */
	@Override
	public DodopalResponse<String> updateMerchant(MerchantDTO merchantDTO) {
		 MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		    DodopalResponse<String> response = facade.merchantAudit(merchantDTO);
		    return response;
	}
	public DodopalResponse<String> updateMerchantForPortal(MerchantDTO merchantDTO) {
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<String> response = facade.updateMerchantForPortal(merchantDTO);
	    return response;
	}
	/*@Override
	public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncitonInfoList() {
			MerFunctionInfoFacade facade = getFacade(MerFunctionInfoFacade.class, DelegateConstant.FACADE_USERS_URL);
		    DodopalResponse<List<MerFunctionInfoDTO>> response = facade.findAllFuncInfoForOSS();
		    return response;
	}*/
	
	/**
	 * 查看通卡公司
	 */
	@Override
	public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode) {
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<List<MerBusRateDTO>> response = facade.findMerBusRateByMerCode(merCode);
	    return response;
	}
	
	@Override
	public DodopalResponse<List<ProductYKTDTO>> yktData(){
	    ProductYktFacade facade = getFacade(ProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
	    DodopalResponse<List<ProductYKTDTO>>  response = facade.getAllYktBusinessRateList();
	    return response;
	}
	@Override
	public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode) {
	    MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<List<MerRateSupplementDTO>> response = facade.findMerRateSupplementsByMerCode(merCode);
	    return response;
	}

	public DodopalResponse<List<Area>> findMerCitys(String merCode){
	   MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
	    DodopalResponse<List<Area>> response = facade.findMerCitys(merCode);
	    return response;
	}
    @Override
    public DodopalResponse<MerchantExtendDTO> findMerchantExtend(String merCode) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerchantExtendDTO> response = facade.findMerchantExtend(merCode);
        return response;
    }
}
