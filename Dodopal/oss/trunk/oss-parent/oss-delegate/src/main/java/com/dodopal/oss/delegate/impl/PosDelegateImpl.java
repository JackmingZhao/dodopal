package com.dodopal.oss.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.PosDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("posDelegate")
public class PosDelegateImpl extends BaseDelegate implements PosDelegate {

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
        return facade.posOper(posOper, merCode, pos, operUser,SourceEnum.OSS,null);
    }

}
