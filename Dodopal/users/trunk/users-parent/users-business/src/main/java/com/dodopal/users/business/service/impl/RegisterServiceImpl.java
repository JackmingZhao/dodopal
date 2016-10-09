package com.dodopal.users.business.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.RegisterService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private MerchantService merchantService;

    @Override
    @Transactional(readOnly = true)
    public boolean checkMobileExist(String mobile, String merCode) {
        MerchantUser merUser = new MerchantUser();
        merUser.setMerUserMobile(mobile);
        MerchantUser findUser = merchantUserService.findMerchantUserExact(merUser);
        if (findUser != null) {
            // 如果查出的用户对应的商户号与传入的商户号相同,则认为手机号不存在,可以注册
            if (StringUtils.isNotBlank(merCode) && merCode.equals(findUser.getMerCode())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUserNameExist(String userName, String merCode) {
        MerchantUser merUser = new MerchantUser();
        merUser.setMerUserName(userName);
        MerchantUser findUser = merchantUserService.findMerchantUserExact(merUser);
        if (findUser != null) {
            // 如果查出的用户对应的商户号与传入的商户号相同,则认为用户名不存在,可以注册
            if (StringUtils.isNotBlank(merCode) && merCode.equals(findUser.getMerCode())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkMerchantNameExist(String merName, String merCode) {
        Merchant merchant = new Merchant();
        merchant.setMerName(merName);
        Merchant findMer = merchantService.findMerchantExact(merchant);
        if (findMer != null) {
            // 如果查出的商户的商户号与传入的商户号相同,则认为商户不存在,可以注册
            if (StringUtils.isNotBlank(merCode) && merCode.equals(findMer.getMerCode())) {
                return false;
            }
            return true;
        }
        return false;
    }

}
