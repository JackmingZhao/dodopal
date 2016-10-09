package com.dodopal.transfernew.business.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeOldEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.dao.AccountControlMapper;
import com.dodopal.transfernew.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfernew.business.dao.AccountFundMapper;
import com.dodopal.transfernew.business.dao.AccountMapper;
import com.dodopal.transfernew.business.dao.BimchntinfotbMapper;
import com.dodopal.transfernew.business.dao.GroupinfotbMapper;
import com.dodopal.transfernew.business.dao.MerAutoAmtMapper;
import com.dodopal.transfernew.business.dao.MerBusRateMapper;
import com.dodopal.transfernew.business.dao.MerDdpInfoMapper;
import com.dodopal.transfernew.business.dao.MerKeyTypeMapper;
import com.dodopal.transfernew.business.dao.MerRateSupplementMapper;
import com.dodopal.transfernew.business.dao.MerchantExtendMapper;
import com.dodopal.transfernew.business.dao.MerchantMapper;
import com.dodopal.transfernew.business.dao.MerchantTranDiscountMapper;
import com.dodopal.transfernew.business.dao.MerchantUserExtendMapper;
import com.dodopal.transfernew.business.dao.MerchantUserMapper;
import com.dodopal.transfernew.business.dao.PosMapper;
import com.dodopal.transfernew.business.dao.PosMerTbMapper;
import com.dodopal.transfernew.business.dao.ProxyinfotbMapper;
import com.dodopal.transfernew.business.dao.TranDiscountMapper;
import com.dodopal.transfernew.business.model.transfer.Account;
import com.dodopal.transfernew.business.model.transfer.AccountControl;
import com.dodopal.transfernew.business.model.transfer.AccountControllerDefault;
import com.dodopal.transfernew.business.model.transfer.AccountFund;
import com.dodopal.transfernew.business.model.transfer.MerAutoAmt;
import com.dodopal.transfernew.business.model.transfer.MerBusRate;
import com.dodopal.transfernew.business.model.transfer.MerDdpInfo;
import com.dodopal.transfernew.business.model.transfer.MerKeyType;
import com.dodopal.transfernew.business.model.transfer.MerRateSupplement;
import com.dodopal.transfernew.business.model.transfer.Merchant;
import com.dodopal.transfernew.business.model.transfer.MerchantExtend;
import com.dodopal.transfernew.business.model.transfer.MerchantTranDiscount;
import com.dodopal.transfernew.business.model.transfer.MerchantUser;
import com.dodopal.transfernew.business.model.transfer.MerchantUserExtend;
import com.dodopal.transfernew.business.model.transfer.Pos;
import com.dodopal.transfernew.business.model.transfer.PosMerTb;
import com.dodopal.transfernew.business.model.transfer.TranDiscount;
import com.dodopal.transfernew.business.service.MerchantService;
import com.dodopal.transfernew.business.service.MerchantUserService;

@Service
public class MerchantServiceImpl implements MerchantService {
	private Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
	@Autowired
	private MerchantMapper merchantMapper;
	@Autowired
	private MerchantExtendMapper merExtendMapper;
	@Autowired
	private MerchantUserMapper merUserMapper;
	@Autowired
	private MerchantUserService merUserService;
	@Autowired
	private MerchantUserExtendMapper merUserExtendMapper;
	@Autowired
	private MerDdpInfoMapper merDdpInfoMapper;
	@Autowired
	private MerRateSupplementMapper merRateSupplementMapper;
	@Autowired
	private MerBusRateMapper merBusRateMapper;
	@Autowired
	private MerAutoAmtMapper merAutoAmtMapper;
	@Autowired
	private MerKeyTypeMapper merKeyTypeMapper;
	@Autowired
	private PosMapper posMapper;
	@Autowired
	private PosMerTbMapper posMerTbMapper;

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountFundMapper accountFundMapper;
	@Autowired
	private AccountControllerDefaultMapper accountControllerDefaultMapper;
	@Autowired
	private AccountControlMapper accountControlMapper;

	@Autowired
	private TranDiscountMapper tranDiscountMapper;
	@Autowired
	private MerchantTranDiscountMapper mtdMapper;

	@Autowired
	private GroupinfotbMapper groupinfotbMapper;
	@Autowired
	private ProxyinfotbMapper proxyinfotbMapper;
	@Autowired
	private BimchntinfotbMapper bimMapper;

	@Override
	@Transactional(readOnly = true)
	public String generateMerCode(String merClassify) {
		StringBuffer sb = new StringBuffer();
		// 商户分类(1位):0.正式商户,1.测试商户
		sb.append(merClassify);
		// 4位随机数
		int number = new Random().nextInt(9999) + 1;
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(4);
		formatter.setGroupingUsed(false);
		String randomNum = formatter.format(number);
		sb.append(randomNum);
		// 数据库10位sequence
		String seq = merchantMapper.getMerCodeSeq();
		sb.append(seq);
		return sb.toString();
	}

	@Override
	@Transactional
	public DodopalResponse<String> addMerchantInfo(Merchant merchant) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		// ------------------------------------------------------------通用校验
		Assert.notNull(merchant, "商户信息不能为空");
		// 商户分类
		String merClassify = merchant.getMerClassify();
		Assert.hasText(merClassify, "商户分类不能为空");
		// 商户类型
		String merType = merchant.getMerType();
		Assert.hasText(merType, "商户类型不能为空");
		// 审核状态
		String merState = merchant.getMerState();
		Assert.hasText(merState, "审核状态不能为空");

		MerchantExtend merExtend = merchant.getMerExtend();
		Assert.notNull(merExtend, "商户扩展信息不能为空");

		String oldMerType = merExtend.getOldMerchantType();
		Assert.hasText(oldMerType, "老系统商户类型不能为空");

		String oldMerId = merExtend.getOldMerchantId();
		Assert.hasText(oldMerId, "老系统商户ID不能为空");

		MerchantUser merUser = merchant.getMerUser();
		Assert.notNull(merUser, "用户信息不能为空");

		MerchantUserExtend merUserExtend = merUser.getMerUserExtend();
		Assert.notNull(merUserExtend, "用户扩展信息不能为空");

		String fundType = merchant.getFundType();
		Assert.hasText(fundType, "账户类型不能为空");
		FundTypeEnum fundTypeEnum = FundTypeEnum.getFundTypeByCode(fundType);
		if (fundTypeEnum == null) {
			throw new DDPException("", "账户类型非法");
		}

		logger.info("商户信息创建开始，商户名：" + merchant.getMerName() + "，商户类型：" + merType + "，账户类型：" + fundType + "，老系统类型：" + oldMerType + "，老系统ID：" + oldMerId);

		// ------------------------------------------------------------公共信息
		// 创建人(默认为10000000000000000733超级管理员)
		String createUser = "10000000000000000733";

		// ------------------------------------------------------------商户信息
		// 生成商户号
		String merCode = generateMerCode(merClassify);
		merchant.setMerCode(merCode);
		// 删除标志(默认为0正常)
		merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());
		// 商户属性(默认为0标准商户)
		merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());
		// 启用标志(默认为0启用)
		merchant.setActivate(ActivateEnum.ENABLE.getCode());
		// 创建人
		merchant.setCreateUser(createUser);

		int result = merchantMapper.addMerchant(merchant);
		if (result == 0) {
			throw new DDPException("", "商户信息添加失败");
		}

		// ------------------------------------------------------------商户扩展信息
		merExtend.setMerCode(merCode);
		result = merExtendMapper.addMerchantExtend(merExtend);
		if (result == 0) {
			throw new DDPException("", "商户扩展信息添加失败");
		}

		// ------------------------------------------------------------用户信息(管理员)
		// 生成用户编号
		UserClassifyEnum userClassify = null;
		if (MerClassifyEnum.OFFICIAL.getCode().equals(merClassify)) {
			userClassify = UserClassifyEnum.MERCHANT;
		} else {
			userClassify = UserClassifyEnum.MERCHANT_TEST;
		}
		String userCode = merUserService.generateMerUserCode(userClassify);
		if (StringUtils.isBlank(userCode)) {
			throw new DDPException("", "生成用户编号失败");
		}
		merUser.setMerCode(merCode);
		merUser.setUserCode(userCode);
		// 用户标志，默认为管理员
		merUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());
		// 用户类型，默认为企业
		merUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());
		// 创建人
		merUser.setCreateUser(createUser);

		result = merUserMapper.addMerchantUser(merUser);
		if (result == 0) {
			throw new DDPException("", "用户信息添加失败");
		}

		// ------------------------------------------------------------用户扩展信息(管理员)
		merUserExtend.setUserCode(userCode);

		result = merUserExtendMapper.addMerchantUserExtend(merUserExtend);
		if (result == 0) {
			throw new DDPException("", "用户扩展信息添加失败");
		}

		// ------------------------------------------------------------用户信息(操作员)
		List<MerchantUser> merUserList = merchant.getMerUserList();
		if (CollectionUtils.isNotEmpty(merUserList)) {
			for (MerchantUser userTemp : merUserList) {
				String userCodeTemp = merUserService.generateMerUserCode(userClassify);
				userTemp.setMerCode(merCode);
				userTemp.setUserCode(userCodeTemp);
				// 用户标志，默认为管理员
				userTemp.setMerUserFlg(MerUserFlgEnum.COMMON.getCode());
				// 用户类型，默认为企业
				userTemp.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());
				// 创建人
				userTemp.setCreateUser(createUser);

				result = merUserMapper.addMerchantUser(userTemp);
				if (result == 0) {
					throw new DDPException("", "用户信息(操作员)添加失败");
				}

				MerchantUserExtend merUserExtendTemp = userTemp.getMerUserExtend();
				if (merUserExtendTemp != null) {
					merUserExtendTemp.setUserCode(userCodeTemp);

					result = merUserExtendMapper.addMerchantUserExtend(merUserExtendTemp);
					if (result == 0) {
						throw new DDPException("", "用户扩展信息(操作员)添加失败");
					}
				}
			}
		}

		// ------------------------------------------------------------商户补充信息
		MerDdpInfo merDdpInfo = merchant.getMerDdpInfo();
		if (merDdpInfo != null) {
			merDdpInfo.setMerCode(merCode);
			merDdpInfo.setCreateUser(createUser);
			result = merDdpInfoMapper.addMerDdpInfo(merDdpInfo);
			if (result == 0) {
				throw new DDPException("", "商户补充信息添加失败");
			}
		}

		// ------------------------------------------------------------商户业务信息
		List<MerRateSupplement> merRateSpmtList = merchant.getMerRateSpmtList();
		if (CollectionUtils.isNotEmpty(merRateSpmtList)) {
			for (MerRateSupplement temp : merRateSpmtList) {
				temp.setMerCode(merCode);
				temp.setCreateUser(createUser);
			}
			result = merRateSupplementMapper.batchAddMerRateSpmts(merRateSpmtList);
			if (merRateSpmtList.size() != result) {
				throw new DDPException("", "商户业务信息添加失败");
			}
		}

		// ------------------------------------------------------------商户业务费率
		List<MerBusRate> merBusRateList = merchant.getMerBusRateList();
		if (!CollectionUtils.isEmpty(merBusRateList)) {
			for (MerBusRate temp : merBusRateList) {
				temp.setMerCode(merCode);
				temp.setCreateUser(createUser);
			}
			result = merBusRateMapper.addMerBusRateBatch(merBusRateList);
			if (merBusRateList.size() != result) {
				throw new DDPException("", "商户业务信息添加失败");
			}
		}

		// ------------------------------------------------------------商户秘钥
		MerKeyType merKeyType = merchant.getMerKeyType();
		if (merKeyType != null) {
			merKeyType.setMerCode(merCode);
			result = merKeyTypeMapper.addMerKeyType(merKeyType);
			if (result == 0) {
				throw new DDPException("", "商户秘钥信息添加失败");
			}
		}

		// ------------------------------------------------------------自动分配额度
		MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
		if (merAutoAmt != null) {
			merAutoAmt.setMerCode(merCode);
			merAutoAmtMapper.addMerAutoAmt(merAutoAmt);
		}

		// ------------------------------------------------------------POS信息
		List<Pos> posList = merchant.getPosList();
		if (CollectionUtils.isNotEmpty(posList)) {
			List<PosMerTb> posMerTbList = new ArrayList<PosMerTb>(posList.size());
			for (Pos posTemp : posList) {
				posTemp.setMerCode(merCode);

				PosMerTb posMerTbTemp = new PosMerTb();
				posMerTbTemp.setCode(posTemp.getCode());
				posMerTbTemp.setMerCode(merCode);
				posMerTbList.add(posMerTbTemp);
			}
			posMapper.batchAddPos(posList);
			posMerTbMapper.batchAddPosMerTb(posMerTbList);
		}

		// 审核通过的商户生成账户信息
		if(MerStateEnum.THROUGH.getCode().equals(merState)) {
			// ------------------------------------------------------------账户信息
			// 主账户
			String seqId = accountMapper.getSequenceNextId();
			String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;// 按规则生成账户编号
			Account account = new Account();
			account.setAccountCode(preId);// 账户编号
			account.setCustomerType("1");// 0：个人 1：商户
			account.setCustomerNo(merCode);
			account.setFundType(fundType); // 0：资金 1：授信
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

			if (FundTypeEnum.AUTHORIZED.getCode().equals(fundType)) {
				// 授信账号
				AccountFund accountFundSA = new AccountFund();
				accountFundSA.setAccountCode(preId);// 主账户编号A2016030716454610001
				accountFundSA.setFundAccountCode("F" + "1" + preId);// 授信账户编号
				accountFundSA.setFundType(FundTypeEnum.AUTHORIZED.getCode());// 资金类别，1：授信
				accountFundSA.setFrozenAmount(new BigDecimal(0));// 冻结金额
				accountFundSA.setSumTotalAmount(new BigDecimal(0));// 购买总额度
				accountFundSA.setTotalBalance(new BigDecimal(0));// 剩余额度
				accountFundSA.setAvailableBalance(new BigDecimal(0));// 可用余额
				accountFundSA.setState("0");// 状态
				accountFundMapper.addAccountFund(accountFundSA);
			}

			// 查询默认的风控值
			AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault(merType);
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

			if (FundTypeEnum.AUTHORIZED.getCode().equals(fundType)) {
				// 授信账号风控
				AccountControl accountControlSA = new AccountControl();
				accountControlSA.setFundAccountCode("F" + "1" + preId);// 授信账户编号
				accountControlSA.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
				accountControlSA.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
				accountControlSA.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
				accountControlSA.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
				accountControlSA.setDayTransferMax(accountControllerDefault.getDayTransferMax());
				accountControlSA.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
				accountControlSA.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
				accountControlMapper.addAccountControl(accountControlSA);
			}
		}

		// ------------------------------------------------------------折扣信息
		// 折扣信息
		List<TranDiscount> tranDiscountList = merchant.getTranDiscountList();
		if (CollectionUtils.isNotEmpty(tranDiscountList)) {
			tranDiscountMapper.batchAddTranDiscount(tranDiscountList);
		}

		// 商户折扣信息
		List<MerchantTranDiscount> mtDiscountList = merchant.getMtDiscountList();
		if (CollectionUtils.isNotEmpty(mtDiscountList)) {
			for (MerchantTranDiscount mtdTemp : mtDiscountList) {
				mtdTemp.setMerCode(merCode);
			}
			mtdMapper.batchAddMerchantTranDiscount(mtDiscountList);
		}

		// ------------------------------------------------------------更新老系统迁移标识
		if (MerTypeOldEnum.GROUP.getCode().equals(oldMerType)) {
			// 集团
			result = groupinfotbMapper.updateTransFlag(oldMerId);
		} else if (MerTypeOldEnum.PROXY.getCode().equals(oldMerType)) {
			// 网点
			result = proxyinfotbMapper.updateTransFlag(oldMerId);
		} else if (MerTypeOldEnum.MERCHANT.getCode().equals(oldMerType)) {
			// 商户
			result = bimMapper.addBimchntinfotbExtend(oldMerId, "0");
		}
		if(result == 0) {
			logger.info("老系统迁移标识更新失败，老系统类型：" + oldMerType + "，老系统ID：" + oldMerId);
			throw new DDPException("", "老系统迁移标识更新失败");
		}

		logger.info("商户信息创建结束，商户名：" + merchant.getMerName() + "，商户号：" + merCode + "，商户类型：" + merType + "，账户类型：" + fundType);
		response.setResponseEntity(merCode);
		response.setCode(ResponseCode.SUCCESS);
		return response;
	}

}
