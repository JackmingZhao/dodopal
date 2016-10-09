package com.dodopal.users.business.facadeImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.service.SendService;

@Service("sendFacade")
public class SendFacadeImpl implements SendFacade {
    private final static Logger logger = LoggerFactory.getLogger(SendFacadeImpl.class);
    @Autowired
    private SendService sendService;

    @Override
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType) {
        return send(mobileNum, codeType, true);
    }

    @Override
    public DodopalResponse<Map<String, String>> sendNoCheck(String mobileNum) {
        return send(mobileNum, MoblieCodeTypeEnum.USER_RG, false);
    }

	private DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, boolean checkExist) {
		DodopalResponse<Map<String, String>> response = null;
		try {
			// 电话号验证
			if (!DDPStringUtil.isMobile(mobileNum)) {
				// 手机号格式不正确
				response = new DodopalResponse<Map<String, String>>();
				response.setCode(ResponseCode.USERS_MOB_TEL_ERR);
				return response;
			}
			response = sendService.send(mobileNum, codeType, checkExist);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response = new DodopalResponse<Map<String, String>>();
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

    /**
     * 手机验证码验证
     * @param moblieNum 手机号
     * @param dypwd 验证码
     * @param serialNumber 序号
     * @return
     */
    @Override
	public DodopalResponse<String> moblieCodeCheck(String moblieNum, String dypwd, String serialNumber) {
		DodopalResponse<String> response = null;
		try {
			// 电话号验证
			if (!DDPStringUtil.isMobile(moblieNum)) {
				// 手机号格式不正确
				response = new DodopalResponse<String>();
				response.setCode(ResponseCode.USERS_MOB_TEL_ERR);
				return response;
			}
			// 验证码
			if (DDPStringUtil.isNotPopulated(dypwd)) {
				response = new DodopalResponse<String>();
				response.setCode(ResponseCode.USERS_MOB_CODE_ERR);
				return response;
			}
			// 序号
			if (DDPStringUtil.isNotPopulated(serialNumber)) {
				response = new DodopalResponse<String>();
				response.setCode(ResponseCode.USERS_MOB_CODE_ERR);
				return response;
			}

			response = sendService.moblieCodeCheck(moblieNum, dypwd, serialNumber);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response = new DodopalResponse<String>();
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

}
