package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.AccountCoupon;

public interface AccountCouponMapper {

	/**
	 * 增加优惠券主账户信息
	 * @param accountCoupon
	 */
	public void addAccountCoupon(AccountCoupon accountCoupon);
}
