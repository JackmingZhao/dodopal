package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerchantDelegate;
import com.dodopal.portal.delegate.PosDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月24日 上午9:41:33
 */
@Service("posDelegate")
public class PosDelegateImpl extends BaseDelegate implements PosDelegate {

    @Override
    public DodopalResponse<String> posOper(PosOperTypeEnum posOper, String merCode, String[] pos, OperUserDTO operUser, String comments) {
        PosFacade facade = getFacade(PosFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.posOper(posOper, merCode, pos, operUser,SourceEnum.PORTAL, comments);
    }

    @Override
    public DodopalResponse<DodopalDataPage<PosDTO>> findPosListPage(PosQueryDTO findDto) {
        PosFacade facade = getFacade(PosFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findPosListPage(findDto);
    }

    @Override
    public DodopalResponse<DodopalDataPage<PosDTO>> findChildrenMerPosListPage(PosQueryDTO findDto) {
        PosFacade facade = getFacade(PosFacade.class, DelegateConstant.FACADE_USERS_URL);
        return facade.findChildrenMerPosListPage(findDto);
    } 
}
