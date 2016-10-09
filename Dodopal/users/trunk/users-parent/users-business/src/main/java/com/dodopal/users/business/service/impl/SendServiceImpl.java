package com.dodopal.users.business.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.users.business.dao.MerchantUserMapper;
import com.dodopal.users.business.dao.MobileCodeCheckMapper;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.MobileCodeCheck;
import com.dodopal.users.business.service.SendService;
import com.dodopal.users.business.util.SendMessageUtil;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class SendServiceImpl implements SendService {
    private Logger logger = Logger.getLogger(SendServiceImpl.class);

    @Autowired
    private MobileCodeCheckMapper mobileCheManager;

    @Autowired
    private MerchantUserMapper merchantUserMapper;

    @Override
    @Transactional
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType) {
        return send(mobileNum, codeType, null);
    }

    @Override
    @Transactional
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, String dypwd) {
        return send(mobileNum, codeType, dypwd, true);
    }

    @Override
    @Transactional
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, boolean checkExist) {
        return send(mobileNum, codeType, null, checkExist);
    }

    @Override
    @Transactional
    public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType, String dypwd, boolean checkExist) {
        DodopalResponse<Map<String, String>> response = new DodopalResponse<Map<String, String>>();
        response.setCode(ResponseCode.SUCCESS);

        //电话号验证
        if (!DDPStringUtil.isMobile(mobileNum)) {
            //手机号格式不正确
            response.setCode(ResponseCode.USERS_MOB_TEL_ERR);
            return response;
        }

        MobileCodeCheck mbCheck = new MobileCodeCheck();

        //先查询该手机是否已存在
        MerchantUser user = new MerchantUser();
        user.setMerUserMobile(mobileNum);
        boolean isExist = merchantUserMapper.checkExist(user);
        if (checkExist && isExist && codeType == MoblieCodeTypeEnum.USER_RG) {
            response.setCode(ResponseCode.USERS_MOB_EXIST);
        } else if ((!isExist) && codeType == MoblieCodeTypeEnum.USER_PWD) {
            response.setCode(ResponseCode.USERS_MOB_NO_EXIST);
        } else {
            Map<String, String> result = null;
            try {
                // TODO：先生成密码再发送短信
                SendMessageUtil sentUtil = new SendMessageUtil();
                result = sentUtil.send(mobileNum, codeType.getCode(), dypwd);
            }
            catch (IOException e) {
                result = null;
                logger.error(e.getMessage(), e);
            }
            if (logger.isInfoEnabled()) {
                logger.info("进入sendService send方法 ：参数result=" + result);
            }
            if (!CollectionUtils.isEmpty(result) && result.get("result").toString().equals("success")) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 30);
                //发送成功往数据库写记录
                mbCheck.setMobilTel(mobileNum);
                mbCheck.setDypwd(result.get("dypwd").toString());
                mbCheck.setExpirationTime(c.getTime());
                mbCheck.setSerialNumber(result.get("pwdseq").toString());
                mobileCheManager.insertMobileCode(mbCheck);
                logger.info("进入insert方法，短信发送成功，新增");
                Map<String, String> pwdseq = new HashMap<String, String>();
                if (codeType == MoblieCodeTypeEnum.USER_PWD) {
                    pwdseq.put("dypwd", result.get("dypwd").toString());
                } else if (codeType == MoblieCodeTypeEnum.USER_RG) {
                    pwdseq.put("dypwd", result.get("dypwd").toString());
                } else if (codeType == MoblieCodeTypeEnum.MER_RG) {
                    pwdseq.put("dypwd", result.get("dypwd").toString());
                }
                pwdseq.put("pwdseq", result.get("pwdseq").toString());
                response.setResponseEntity(pwdseq);
            } else {
                logger.info("!!!短信发送失败" + result);
                response.setCode(ResponseCode.USERS_SEND_MOB_CODE_ERR);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalResponse<String> moblieCodeCheck(String mobileNum, String dypwd, String serialNumber) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);

        MobileCodeCheck moliCodeCheck = new MobileCodeCheck();
        moliCodeCheck.setMobilTel(mobileNum);
        moliCodeCheck.setDypwd(dypwd);
        moliCodeCheck.setSerialNumber(serialNumber);
        // moliCodeCheck.setExpirationTime(new Date());
        //取得数据
        List<MobileCodeCheck> mobileCodeCheckList = mobileCheManager.findMobileCodeCheckes(moliCodeCheck);
        if(CollectionUtils.isEmpty(mobileCodeCheckList)) {
            response.setCode(ResponseCode.PORTAL_MOBILE_DYPWD_ERROR);
        } else {
            MobileCodeCheck lastCode = mobileCodeCheckList.get(0);
            if(new Date().after(lastCode.getExpirationTime())) {
                response.setCode(ResponseCode.PORTAL_MOBILE_DYPWD_EXPIRED);
            }
        }

        return response;
    }

}
