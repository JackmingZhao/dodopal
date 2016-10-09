package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.facade.ProductYktFacade;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.facade.MerFunctionInfoFacade;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.MerchantDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("merchantDelegate")
public class MerchantDelegateImpl extends BaseDelegate implements MerchantDelegate {
	  /**
     * Tiem:2015-05-31 17:05
     * TODO:后期干掉
     * Title:商户列表信息查询-前台分页
     * Name:Joe
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
    public DodopalResponse<Integer> startAndDisableMerchant(List<String> merCode, String activate, String updateUser) {
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
    public DodopalResponse<MerchantDTO> findMerchants(String merCode) {
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
        // System.out.println(response);
        return response;
    }

    /**
     * 查询自定义商户信息
     */
    @Override
    public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncitonInfoList(String merType) {
        MerFunctionInfoFacade facade = getFacade(MerFunctionInfoFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerFunctionInfoDTO>> response = facade.findAllFuncInfoForOSS(merType);
        return response;
    }

    /**
     * Tiem:2015-05-31 17:05
     * Title:商户列表信息查询-后台分页
     * Name:Joe
     */
    @Override
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findMerchantListByPage(MerchantQueryDTO merQueryDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findMerchantByPage(merQueryDTO);
    }
    /**
     * 审核不通过商户信息删除列表
     */
    @Override
    public DodopalResponse<Integer> delNotverified(List<String> merCodes,String upUser) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.batchDelRejectMerchantByMerCodes(merCodes, upUser);
    }

    /**
     * 按照商户类型查询自定义商户功能商户问题
     */
    @Override
    public DodopalResponse<List<MerFunctionInfoDTO>> findMerTypeFunList(String merType) {
        MerFunctionInfoFacade facade = getFacade(MerFunctionInfoFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerFunctionInfoDTO>> response = facade.findFuncInfoByMerType(MerTypeEnum.getMerTypeByCode(merType));
        return response;
    }
    /**
     * 根据上级商户CODE查询通卡公司费率
     */
    @Override
    public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerBusRateDTO>> response = facade.findMerBusRateByMerCode(merCode);
        return response;
    }

    @Override
    public DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(MerchantDTO merchantDTO) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.checkRelationMerchantProviderAndRateType(merchantDTO);
        return response;
    }

    /***
     * Title:加载通卡公司数据
     * Time:2015-08-06
     * User:qjc
     */
    @Override
    public DodopalResponse<List<ProductYKTDTO>> yktData() {
        ProductYktFacade facade = getFacade(ProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        DodopalResponse<List<ProductYKTDTO>>  response = facade.getAllYktBusinessRateList();
        return response;
    }
    /**
     * Title:连锁商直营网点业务类型新增和编辑
     * Time:2015-09-10
     * User:Joe
     */
    @Override
    public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode) {
        MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerRateSupplementDTO>> response = facade.findMerRateSupplementsByMerCode(merCode);
        return response;
    }

	@Override
	public DodopalResponse<List<Area>> findMerCitys(String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<Area>> response = facade.findMerCitys(merCode);
		return response;
	}

	@Override
	public DodopalResponse<List<Area>> findMerCitysPayment(String merCode) {
		MerchantFacade facade = getFacade(MerchantFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<Area>> response = facade.findMerCitysPayment(merCode);
		return response;
	}
}
