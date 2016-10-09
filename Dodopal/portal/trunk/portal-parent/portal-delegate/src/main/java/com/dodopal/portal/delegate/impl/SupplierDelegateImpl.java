package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerCountDTO;
import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.dto.query.MerCountQueryDTO;
import com.dodopal.api.users.facade.ManagementForSupplierFacade;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.SupplierDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("supplierDelegate")
public class SupplierDelegateImpl extends BaseDelegate implements SupplierDelegate {

    //查询商户pos信息
    @Override
    public DodopalResponse<DodopalDataPage<PosDTO>> countMerchantPosForSupplier(PosQueryDTO findDto) {
        ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.countMerchantPosForSupplier(findDto);
    }

    //查询城市商户信息
    @Override
    public DodopalResponse<DodopalDataPage<MerCountDTO>> countMerchantForSupplier(MerCountQueryDTO merCountQueryDTO) {
        ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.countMerchantForSupplier(merCountQueryDTO);
    }
    
    /**
     * POS操作 绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @return
     */
    @Override
    public DodopalResponse<String> posOper(PosOperTypeEnum posOper, String merCode, String[] pos, OperUserDTO operUser) {
        PosFacade facade = getFacade(PosFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.posOper(posOper, merCode, pos, operUser,SourceEnum.PORTAL,null);
    }

    
    
    

}
