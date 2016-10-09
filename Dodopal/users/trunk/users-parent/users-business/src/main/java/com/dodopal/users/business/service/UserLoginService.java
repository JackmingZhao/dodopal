package com.dodopal.users.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.Merchant;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface UserLoginService {

    /**
     * 用户登录校验、获取权限
     * @param userName
     * @param password
     * @return
     */
    public DodopalResponse<Merchant> login(String userName, String password);

}
