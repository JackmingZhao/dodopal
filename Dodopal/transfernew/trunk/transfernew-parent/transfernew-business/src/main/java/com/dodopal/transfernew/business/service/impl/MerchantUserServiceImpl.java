package com.dodopal.transfernew.business.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.dao.AccountControlMapper;
import com.dodopal.transfernew.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfernew.business.dao.AccountCouponMapper;
import com.dodopal.transfernew.business.dao.AccountFundMapper;
import com.dodopal.transfernew.business.dao.AccountMapper;
import com.dodopal.transfernew.business.dao.MerUserCardBdMapper;
import com.dodopal.transfernew.business.dao.MerchantUserExtendMapper;
import com.dodopal.transfernew.business.dao.MerchantUserMapper;
import com.dodopal.transfernew.business.dao.TlcouponinfoMapper;
import com.dodopal.transfernew.business.model.transfer.Account;
import com.dodopal.transfernew.business.model.transfer.AccountControl;
import com.dodopal.transfernew.business.model.transfer.AccountControllerDefault;
import com.dodopal.transfernew.business.model.transfer.AccountCoupon;
import com.dodopal.transfernew.business.model.transfer.AccountFund;
import com.dodopal.transfernew.business.model.transfer.MerUserCardBd;
import com.dodopal.transfernew.business.model.transfer.MerchantUser;
import com.dodopal.transfernew.business.model.transfer.MerchantUserExtend;
import com.dodopal.transfernew.business.model.transfer.Tlcouponinfo;
import com.dodopal.transfernew.business.service.MerchantUserService;

/**
 * @author lifeng@dodopal.com
 */

@Service
public class MerchantUserServiceImpl implements MerchantUserService {

	@Autowired
	private MerchantUserMapper mapper;
	@Autowired
	private MerchantUserExtendMapper merUserExtendMapper;
	@Autowired
	private MerUserCardBdMapper merUserCardBdMapper;

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountFundMapper accountFundMapper;
	@Autowired
	private AccountControllerDefaultMapper accountControllerDefaultMapper;
	@Autowired
	private AccountControlMapper accountControlMapper;
	@Autowired
	private AccountCouponMapper accountCouponMapper;
	@Autowired
    private TlcouponinfoMapper tlcouponinfoMapper;

	@Override
	@Transactional(readOnly = true)
	public String generateMerUserCode(UserClassifyEnum userClassify) {
		StringBuffer sb = new StringBuffer();
		// 是否测试账户(1位):1-正式商户用户; 2--测试商户用户; 3--正式个人用户; 4--个人测试用户
		sb.append(userClassify.getCode());
		// 4位随机数
		int number = new Random().nextInt(9999) + 1;
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(4);
		formatter.setGroupingUsed(false);
		String randomNum = formatter.format(number);
		sb.append(randomNum);
		// 数据库12位sequence
		String seq = mapper.getMerUserCodeSeq();
		sb.append(seq);
		return sb.toString();
	}

	/**
	 * 检查用户名是否存在
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean checkExist(String merUserName) {
		return mapper.checkExist(merUserName);
	}

	@Override
	@Transactional
	public DodopalResponse<String> addMerchantUserInfo(MerchantUser merUser) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		// ------------------------------------------------------------通用校验
		Assert.notNull(merUser, "用户信息不能为空");

		String merUserName = merUser.getMerUserName();
		Assert.hasText(merUserName, "用户名不能为空");

		MerchantUserExtend merUserExtend = merUser.getMerUserExtend();
		Assert.notNull(merUserExtend, "用户扩展信息不能为空");

		// ------------------------------------------------------------公共信息
		// 创建人(默认为10000000000000000733超级管理员)
		String createUser = "10000000000000000733";

		// ------------------------------------------------------------用户信息
		// 生成用户编号
		String userCode = generateMerUserCode(UserClassifyEnum.PERSONAL);
		merUser.setUserCode(userCode);
		// 用户标志，默认为管理员
		merUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());
		// 用户类型，默认为个人
		merUser.setMerUserType(MerUserTypeEnum.PERSONAL.getCode());
		// 创建人
		merUser.setCreateUser(createUser);
		int result = mapper.addMerchantUser(merUser);
		if (result == 0) {
			throw new DDPException("", "用户信息添加失败");
		}

		// ------------------------------------------------------------用户扩展信息
		merUserExtend.setUserCode(userCode);
		result = merUserExtendMapper.addMerchantUserExtend(merUserExtend);
		if (result == 0) {
			throw new DDPException("", "用户扩展信息添加失败");
		}

		// ------------------------------------------------------------用户绑卡信息
		List<MerUserCardBd> merUserCardBdList = merUser.getMerUserCardBdList();
		if (CollectionUtils.isNotEmpty(merUserCardBdList)) {
			for (MerUserCardBd merUserCardBdTemp : merUserCardBdList) {
				merUserCardBdTemp.setMerUserName(merUserName);
			}
			result = merUserCardBdMapper.batchAddMerUserCardBd(merUserCardBdList);
			if (result != merUserCardBdList.size()) {
				throw new DDPException("", "用户绑卡信息添加失败");
			}
		}

		// ------------------------------------------------------------账户信息
		// 主账户
		String seqId = accountMapper.getSequenceNextId();
		String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;// 按规则生成账户编号
		Account account = new Account();
		account.setAccountCode(preId);// 账户编号
		account.setCustomerType("0");// 0：个人 1：商户
		account.setCustomerNo(userCode);
		account.setFundType("0"); // 0：资金 1：授信
		accountMapper.addAccount(account);

		// 资金账号
		AccountFund accountFund = new AccountFund();
		accountFund.setAccountCode(preId);// 主账户编号A2016030716454610001
		accountFund.setFundAccountCode("F" + "0" + preId);// 资金授信账户编号
		accountFund.setFundType(FundTypeEnum.FUND.getCode());// 资金类别 ，0：资金
		accountFund.setFrozenAmount(new BigDecimal(0));// 冻结金额
		accountFund.setSumTotalAmount(new BigDecimal(0));// 购买总额度
		accountFund.setTotalBalance(new BigDecimal(0));// 剩余额度
		accountFund.setAvailableBalance(new BigDecimal(0));// 可用余额
		accountFund.setState("0");// 状态
		accountFundMapper.addAccountFund(accountFund);

		// 查询默认的风控值
		AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault(MerTypeEnum.PERSONAL.getCode());
		// 资金账号风控
		AccountControl accountControl = new AccountControl();
		accountControl.setFundAccountCode("F" + "0" + preId);// 资金账户编号
		accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
		accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
		accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
		accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
		accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
		accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
		accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
		accountControlMapper.addAccountControl(accountControl);

		// 优惠券账户
		AccountCoupon accountCoupon = new AccountCoupon();
		String acpreId = "C" + preId;
		accountCoupon.setAccountCode(preId);// 主账户编号
		accountCoupon.setCouponAccountCode(acpreId);// 优惠券主账户编号
		accountCouponMapper.addAccountCoupon(accountCoupon);

		List<Tlcouponinfo> tlcouponinfoList = merUser.getTlcouponinfoList();
		if (CollectionUtils.isNotEmpty(tlcouponinfoList)) {
			for (Tlcouponinfo tlcouponinfoTemp : tlcouponinfoList) {
				tlcouponinfoTemp.setCouponAccountcode(acpreId);
				tlcouponinfoTemp.setUserCode(userCode);
			}
			tlcouponinfoMapper.batchAddTlcouponinfo(tlcouponinfoList);
		}

		return response;
	}
}
