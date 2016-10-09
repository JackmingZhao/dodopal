package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.MerchantUser;

public interface MerchantUserMapper {

public List<MerchantUser> findMerchantUsers(MerchantUser merchantUser);
}
