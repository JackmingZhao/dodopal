package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.AccountCoupon;

public interface AccountCouponMapper {

	/**
	 * 增加优惠券主账户信息
	 * @param accountCoupon
	 */
	public void addAccountCoupon(AccountCoupon accountCoupon);
}
