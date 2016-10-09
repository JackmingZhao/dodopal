package com.dodopal.oss.delegate;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.model.DodopalResponse;

public interface PosDelegate {

    /**
     * POS操作 绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @return
     */
    DodopalResponse<String> posOper(PosOperTypeEnum posOper, String merCode, String[] pos, OperUserDTO operUser);

}
