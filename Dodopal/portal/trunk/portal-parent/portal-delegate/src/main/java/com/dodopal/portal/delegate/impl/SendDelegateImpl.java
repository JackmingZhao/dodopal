package com.dodopal.portal.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.SendDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service("sendDelegate")
public class SendDelegateImpl extends BaseDelegate implements SendDelegate {

    @Override
    public DodopalResponse<Map<String, String>> send(String moblieNum, MoblieCodeTypeEnum codeType) {
        SendFacade sendFacade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
        return sendFacade.send(moblieNum, codeType);
    }

    @Override
    public DodopalResponse<String> moblieCodeCheck(String moblieNum, String dypwd, String serialNumber) {
        SendFacade sendFacade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
        return sendFacade.moblieCodeCheck(moblieNum, dypwd, serialNumber);
    }

    @Override
    public DodopalResponse<Map<String, String>> sendNoCheck(String moblieNum) {
        SendFacade sendFacade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
        return sendFacade.sendNoCheck(moblieNum);
    }

}
