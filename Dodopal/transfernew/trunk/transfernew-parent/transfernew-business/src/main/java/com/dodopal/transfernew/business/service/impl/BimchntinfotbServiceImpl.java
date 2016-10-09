package com.dodopal.transfernew.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerKeyTypeEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerTypeOldEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SexEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfernew.business.dao.AccountControlMapper;
import com.dodopal.transfernew.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfernew.business.dao.BimchnitfeetbMapper;
import com.dodopal.transfernew.business.dao.BimchntinfotbMapper;
import com.dodopal.transfernew.business.dao.BiposidsaletbMapper;
import com.dodopal.transfernew.business.dao.BiposinfotbMapper;
import com.dodopal.transfernew.business.dao.BiposinuserstbMapper;
import com.dodopal.transfernew.business.dao.BisalediscountMapper;
import com.dodopal.transfernew.business.dao.LogTransferMapper;
import com.dodopal.transfernew.business.dao.MerRateSupplementMapper;
import com.dodopal.transfernew.business.dao.MerchantUserExtendMapper;
import com.dodopal.transfernew.business.dao.PosMapper;
import com.dodopal.transfernew.business.dao.PosMerTbMapper;
import com.dodopal.transfernew.business.dao.ProxyLimitInfoTbMapper;
import com.dodopal.transfernew.business.dao.ProxyinfotbMapper;
import com.dodopal.transfernew.business.dao.ProxypostbMapper;
import com.dodopal.transfernew.business.dao.SysuserstbMapper;
import com.dodopal.transfernew.business.dao.TranDiscountMapper;
import com.dodopal.transfernew.business.model.LogTransfer;
import com.dodopal.transfernew.business.model.old.Bimchnitfeetb;
import com.dodopal.transfernew.business.model.old.Bimchntinfotb;
import com.dodopal.transfernew.business.model.old.Biposidsaletb;
import com.dodopal.transfernew.business.model.old.Biposinfotb;
import com.dodopal.transfernew.business.model.old.Biposinuserstb;
import com.dodopal.transfernew.business.model.old.Bisalediscount;
import com.dodopal.transfernew.business.model.old.Sysuserstb;
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
import com.dodopal.transfernew.business.model.transfer.TranDiscount;
import com.dodopal.transfernew.business.service.BimchntinfotbService;
import com.dodopal.transfernew.business.service.MerchantService;
import com.dodopal.transfernew.business.service.MerchantUserService;

@Service
public class BimchntinfotbServiceImpl implements BimchntinfotbService {
	private Logger logger = LoggerFactory.getLogger(BimchntinfotbServiceImpl.class);

	@Autowired
	BimchntinfotbMapper bimMapper;
	@Autowired
	MerchantService merchantService;
	@Autowired
	LogTransferMapper logTransferMapper;
	@Autowired
	SysuserstbMapper sysuserstbMapper;
	@Autowired
	BimchnitfeetbMapper bimchnitfeetbMapper;
	@Autowired
	BiposinuserstbMapper biposinuserstbMapper;
	@Autowired
	BiposinfotbMapper biposinfotbMapper;
	@Autowired
	PosMapper posMapper;
	@Autowired
	MerchantUserService merchantUserService;
	@Autowired
	MerchantUserExtendMapper merchantUserExtendMapper;
	@Autowired
	ProxypostbMapper proxypostbMapper;
	@Autowired
	PosMerTbMapper posMerTbMapper;
	@Autowired
	MerRateSupplementMapper merRateSupplementMapper;
	@Autowired
	ProxyLimitInfoTbMapper proxyLimitInfoTbMapper;
	@Autowired
	AccountControllerDefaultMapper accountControllerDefaultMapper;
	@Autowired
	AccountControlMapper accountControlMapper;
	@Autowired
	TranDiscountMapper tranDiscountMapper;
	@Autowired
	ProxyinfotbMapper proxyinfotbMapper;
	@Autowired
	BiposidsaletbMapper biposidsaletbMapper;
	@Autowired
	BisalediscountMapper bisalediscountMapper;

	@Override
	public DodopalResponse<String> findBimchntinfotbs(String procode, String citycode) {
		DodopalResponse<String> req = new DodopalResponse<String>();
		List<Bimchntinfotb> listBim = bimMapper.findAllBimchntinfotb();
		// 定位批次号
		String batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
		if (listBim.size() != 0) {
			for (Bimchntinfotb bim : listBim) {
				// 新增日志
				LogTransfer logTransfer = new LogTransfer();
				logTransfer.setBatchId(batchId);// 日志批次号
				logTransfer.setOldMerchantId(bim.getMchnitid());// 老商户ID
				logTransfer.setOldMerchantType(MerTypeOldEnum.MERCHANT.getCode());// 老商户类型
				try {
					insertMerchantChain(batchId, bim, procode, citycode, logTransfer);
				} catch (Exception e) {
					logTransfer.setState("1");// 成功和失败的状态
					logTransfer.setMemo(e.getMessage());// 导入描述
					e.printStackTrace();
				} finally {
					try {
						logger.info("批次号batchId：" + batchId);
						logTransferMapper.insartLogTransfer(logTransfer);
					} catch (Exception e2) {
						logger.error("商户数据库日志写入失败，异常信息:" + e2.getMessage(), e2);
					}
				}

			}

		}
		req.setCode(ResponseCode.SUCCESS);
		req.setResponseEntity(batchId);
		return req;
	}

	@Transactional
	private void insertMerchantChain(String batchId, Bimchntinfotb bim, String procode, String citycode, LogTransfer logTransfer) {
		// ***********************************1.生成连锁商户信息************************//
		Merchant merchant = new Merchant();
		merchant.setMerName(bim.getMchnitname());// 商户名称
		merchant.setMerLinkUser(bim.getLinkman());// 联系人
		if (DDPStringUtil.isMobile(DDPStringUtil.trim(bim.getTel()))) {
			merchant.setMerLinkUserMobile(DDPStringUtil.trim(bim.getTel())); // 商户联系人
		} else {
			merchant.setMerTelephone(DDPStringUtil.trim(bim.getTel()));// 固定电话
		}
		merchant.setMerFax(bim.getFax());// 传真号码
		merchant.setMerAdds(bim.getAddress());// 联系地址
		merchant.setMerZip(bim.getZipcode());// 邮编
		if (bim.getAreaid() != null) {
			merchant.setMerArea(String.valueOf(bim.getAreaid()));// 商户所属地区
		}

		merchant.setMerBankAccount(bim.getBankacc());// 开户行账号
		merchant.setMerBankName(bim.getBankaccname());// 开户行名称
		merchant.setMerBusinessScopeId(bim.getTradeid());// 行业代码
		if (bim.getParentmchnitid() != null) {
			merchant.setMerPro(bim.getProvinceid().toString());// 商户省份
		} else {
			merchant.setMerPro(procode);// 商户省份 北京
		}
		if (bim.getCityid() != null) {
			merchant.setMerCity(bim.getCityid().toString());// 商户城市
		} else {
			merchant.setMerCity(citycode);// 商户城市
		}
		merchant.setMerType(MerTypeEnum.CHAIN.getCode());// 商户类型
		merchant.setMerState(MerStateEnum.THROUGH.getCode());// 审核状态
		merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());// 商户分类
		merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());// 商户属性
		merchant.setSource(SourceEnum.TRANSFER.getCode());// 来源
		merchant.setActivate(ActivateEnum.ENABLE.getCode());// 启用
		merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());// 删除标志:0：正常、1：删除

		// ***********************************2.生成商户扩展信息表************************//
		MerchantExtend merchantExtend = new MerchantExtend();
		merchantExtend.setOldMerchantId(bim.getMchnitid());// 老商户表的商户ID
		merchantExtend.setOldMerchantType("3");// 标识为老商户表的数据，集团：1，网点：2，商户：3
		merchant.setMerExtend(merchantExtend);

		// ***********************************3.生成连锁商户用户信息************************//
		Sysuserstb sysuserstb = sysuserstbMapper.findSysUserstbByMchnitid(bim.getMchnitid());
		MerchantUser merchantUser = new MerchantUser();
		if (sysuserstb != null) {
			if (sysuserstb.getRelname() != null) {
				merchantUser.setMerUserNickname(sysuserstb.getRelname());
			} else {
				merchantUser.setMerUserNickname(sysuserstb.getUsername());
			}
			if (sysuserstb.getLoginid() != null) {
				if (sysuserstb.getLoginid().indexOf("@") >= 0) {
					merchantUser.setMerUserName("U" + DateUtils.dateToStrLongs(new Date()));// 用户名U+yyyyMMddHHmmss
				} else {
					merchantUser.setMerUserName(sysuserstb.getLoginid());// 用户昵称、登录名，新平台merCode+"U"
				}
			} else {
				merchantUser.setMerUserName("U" + DateUtils.dateToStrLongs(new Date()));
			}

			merchantUser.setMerUserPwd(sysuserstb.getPassword());// 登录密码
			merchantUser.setMerUserTelephone(sysuserstb.getTel());// 固定电话
			if (sysuserstb.getMobiltel() != null) {
				merchantUser.setMerUserTelephone(sysuserstb.getMobiltel());// 移动电话
			}
			if (sysuserstb.getProvinceid() != null) {
				merchantUser.setMerUserPro(sysuserstb.getProvinceid().toString());// 省份代码
			} else {
				merchantUser.setMerUserPro(procode);
			}
			if (sysuserstb.getCityid() != null) {
				merchantUser.setMerUserCity(sysuserstb.getCityid().toString());// 城市代码
			} else {
				merchantUser.setMerUserCity(citycode);
			}

			merchantUser.setMerUserAdds(sysuserstb.getAddress());// 地址
			if ("0".equals(sysuserstb.getSex())) {
				merchantUser.setMerUserSex(SexEnum.FEMALE.getCode());// 性别
			} else if ("1".equals(sysuserstb.getSex())) {
				merchantUser.setMerUserSex(SexEnum.MALE.getCode());// 性别
			} else if ("2".equals(sysuserstb.getSex())) {
				merchantUser.setMerUserSex(SexEnum.MALE.getCode());// 性别
			}

			merchantUser.setMerUserIdentityNum(sysuserstb.getIdentityid());// 证件号码
			if ("1000000001".equals(sysuserstb.getIdentitytype())) {// 证件类型
				merchantUser.setMerUserIdentityType("0");
			} else if ("1000000002".equals(sysuserstb.getIdentitytype())) {
				merchantUser.setMerUserIdentityType("2");
			} else if ("1000000005".equals(sysuserstb.getIdentitytype())) {
				merchantUser.setMerUserIdentityType("1");
			} else {
				merchantUser.setMerUserIdentityType(sysuserstb.getIdentitytype());// 证件类型
			}
			merchantUser.setMerUserEmall(sysuserstb.getEmail());// 电子邮件
			merchantUser.setCreateDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FULL_STR));// 注册时间
			merchantUser.setUpdateDate(DateUtils.stringtoDate(sysuserstb.getLastedittime(), DateUtils.DATE_FULL_STR));// 最后修改时间
			merchantUser.setMerUserType(sysuserstb.getUsertype().toString());// 用户类型
			if (sysuserstb.getLastmobiltel() != null) {// 当前使用的移动电话
				merchantUser.setMerUserMobile(sysuserstb.getLastmobiltel());
			} else {
				merchantUser.setMerUserMobile("0000000000");
			}
			if (sysuserstb.getYktcityid() != null) {
				merchantUser.setCityCode(sysuserstb.getYktcityid().toString());// 城市code
			} else {
				merchantUser.setCityCode(citycode);// 城市code
			}
			merchantUser.setBirthday(DateUtils.dateToString(DateUtils.stringtoDate(sysuserstb.getBirthday(), DateUtils.DATE_SMALL_STR), DateUtils.DATE_SMALL_STR));// 生日
			merchantUser.setMerUserNickname(sysuserstb.getRelname());// 真实姓名
			merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());// 用户标志
			merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());// 用户类型
			merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());// 审核状态
			merchantUser.setSource(SourceEnum.TRANSFER.getCode());// 用户注册来源
			if (sysuserstb.getStatus() != null) {
				merchantUser.setActivate(sysuserstb.getStatus());// 是否启用
			} else {
				merchantUser.setActivate(ActivateEnum.ENABLE.getCode());// 用户启用
			}

		} else {
			merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());// 用户标志
			merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());// 用户类型
			merchantUser.setMerUserName("U" + DateUtils.dateToStrLongs(new Date()));// 商户用户登录账号
			merchantUser.setMerUserPwd("123456");// 商户用户登录密码，默认14e1b600b1fd579f47433b88e8d85291
			merchantUser.setMerUserAdds(bim.getAddress());// 用户地址
			merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());// 审核状态
			merchantUser.setSource(SourceEnum.TRANSFER.getCode());// 用户注册来源
			merchantUser.setMerUserPro(bim.getProvinceid().toString());// 用户所属省份
			merchantUser.setMerUserCity(bim.getCityid().toString());// 用户所属城市
			merchantUser.setCityCode(citycode);// 默认开通城市编号 北京
			merchantUser.setActivate(ActivateEnum.ENABLE.getCode());// 用户启用
			if (bim.getTel() != null) {
				if (DDPStringUtil.isMobile(DDPStringUtil.trim(bim.getTel()))) {
					merchantUser.setMerUserMobile(DDPStringUtil.trim(bim.getTel()));// 商户用户手机号码
				} else {
					merchantUser.setMerUserMobile("0000000");// 商户随意填写
					merchantUser.setMerUserTelephone(DDPStringUtil.trim(bim.getTel()));// 商户用户固定电话
				}
			} else {
				merchantUser.setMerUserMobile("0000000");// 商户随意填写
			}
		}
		merchant.setMerUser(merchantUser);

		// ***********************************4.生成用户信息标志信息表************************//
		MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
		merchantUserExtend.setUserCode(merchantUser.getUserCode());// 新生成的商户CODE
		merchantUserExtend.setOldUserId(sysuserstb.getUserid());// 老商户表的商户ID
		merchantUserExtend.setOldUserType(MerTypeOldEnum.MERCHANT.getCode());// 标识为老商户表的数据
		merchant.getMerUser().setMerUserExtend(merchantUserExtend);

		// ***********************************5.生成商户补充信息表MER_DDP_INFO************************//
		Bimchnitfeetb bimchnitfeetbInfo = bimchnitfeetbMapper.findBimchnitfeetbByMchId(bim.getMchnitid());
		MerDdpInfo merDdpInfo = new MerDdpInfo();
		if (bimchnitfeetbInfo != null) {
			if ("3".equals(bimchnitfeetbInfo.getFeetype())) {
				merDdpInfo.setSettlementType("0");// 结算类型
				merDdpInfo.setSettlementThreshold(new BigDecimal(0));// 结算参数
			} else {
				merDdpInfo.setSettlementType(bimchnitfeetbInfo.getFeetype());// 结算类型
				merDdpInfo.setSettlementThreshold(bimchnitfeetbInfo.getSetpara());// 结算参数
			}
		}
		merchant.setMerDdpInfo(merDdpInfo);

		// ***********************************6.生成商户业务类型信息表mer_rate_supplement************************//
		List<MerRateSupplement> merRateSupplementList = new ArrayList<MerRateSupplement>();
		MerRateSupplement merRateSupplement = new MerRateSupplement();
		merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
		merRateSupplementList.add(merRateSupplement);
		MerRateSupplement merRateSupplement2 = new MerRateSupplement();
		merRateSupplement2.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
		merRateSupplementList.add(merRateSupplement2);
		merchant.setMerRateSpmtList(merRateSupplementList);

		// ***********************************7.生成商户业务费率MER_BUS_RATE************************//
		List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
		MerBusRate merBusRate = new MerBusRate();
		merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());// 充值业务
		TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
		if (cityEnum != null) {
			merBusRate.setProviderCode(cityEnum.getYktCode());
		}

		merBusRate.setRate(new BigDecimal(0));// 数值
		merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());// 费率类型
		merBusRateList.add(merBusRate);
		MerBusRate merBusRate2 = new MerBusRate();
		merBusRate2.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());// 消费业务

		if (cityEnum != null) {
			merBusRate2.setProviderCode(cityEnum.getYktCode());
		}
		merBusRate2.setRate(new BigDecimal(0));// 数值
		merBusRate2.setRateType(RateTypeEnum.PERMILLAGE.getCode());// 费率类型
		merBusRateList.add(merBusRate2);
		merchant.setMerBusRateList(merBusRateList);

		// ***********************************8.生成连锁商户主账户信息************************//
		merchant.setFundType(FundTypeEnum.AUTHORIZED.getCode());

		DodopalResponse<String> addMerRes = merchantService.addMerchantInfo(merchant);
		// ***********************************9.生成子商户信息************************//
		// 第一步： 根据商户查询下面有多少POS
		List<Biposinfotb> listBip = biposinfotbMapper.findBiposinfotbAll(bim.getMchnitid());
		if (CollectionUtils.isNotEmpty(listBip)) {
			for (Biposinfotb biposinfotb : listBip) {
				insertMerchantChinaJoin(biposinfotb, batchId, bim, addMerRes.getCode(), procode, citycode);
			}
		}

		logTransfer.setNewMerchantCode(addMerRes.getResponseEntity());// 新商户号
		logTransfer.setNewMerchantType(MerTypeEnum.CHAIN.getCode());// 新商户类型
		logTransfer.setState("0");// 成功和失败的状态
	}

	/**
	 * 生成连锁直营商户
	 */
	@Transactional
	private DodopalResponse<String> insertMerchantChinaJoin(Biposinfotb biposinfotb, String batchId, Bimchntinfotb bim, String merParentCode, String procode, String citycode) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		response.setCode(ResponseCode.SUCCESS);
		// ***************************************************第二步查询biposinuserstb对应的用户*************************//
		Biposinuserstb biposinuserstb = biposinuserstbMapper.findBiposinuserstbByPosId(String.valueOf(biposinfotb.getPosid()));
		if (biposinuserstb == null) {
			return response;
		} 
		// 新增日志
		LogTransfer logTransfer = new LogTransfer();

		Sysuserstb sysuserstb = sysuserstbMapper.findSysuserstb(String.valueOf(biposinuserstb.getUserid()));
		try {
			// ***********************************1.生成连锁商户信息************************//
			Merchant merchant = new Merchant();
			if (merParentCode != null) {
				merchant.setMerParentCode(merParentCode);// 上级商户CODE
			}
			merchant.setMerName(sysuserstb.getUsername());// 商户名称
			merchant.setMerLinkUser(sysuserstb.getRelname());// 联系人
			merchant.setMerTelephone(sysuserstb.getTel());// 固定电话
			merchant.setMerLinkUserMobile(sysuserstb.getMobiltel()); // 商户联系人电话
			merchant.setMerFax("");// 传真号码
			merchant.setMerAdds(sysuserstb.getAddress());// 联系地址
			merchant.setMerZip(sysuserstb.getZipcode());// 邮编
			merchant.setMerBankAccount("");// 开户行账号
			merchant.setMerBankName("");// 开户行名称
			merchant.setMerBusinessScopeId("");// 经营范围:数据字典维护
			if (sysuserstb.getProvinceid() != null) {
				merchant.setMerPro(sysuserstb.getProvinceid().toString());// 商户省份
			} else {
				merchant.setMerPro(procode);// 商户省份 北京
			}
			if (sysuserstb.getCityid() != null) {
				merchant.setMerCity(sysuserstb.getCityid().toString());// 商户城市
			} else {
				merchant.setMerCity(citycode);// 商户城市
			}

			merchant.setMerType(MerTypeEnum.CHAIN_JOIN_MER.getCode());// 商户类型
			merchant.setMerState(MerStateEnum.THROUGH.getCode());// 审核状态
			merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());// 商户分类
			merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());// 商户属性
			merchant.setSource(SourceEnum.TRANSFER.getCode());// 来源
			merchant.setActivate(ActivateEnum.ENABLE.getCode());// 启用
			merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());// 删除标志:0：正常、1：删除
			merchant.setMerRegisterDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));// 注册时间
			// ***********************************1.1.生成用标志信息表************************//
			MerchantExtend merchantExtend = new MerchantExtend();
			//merchantExtend.setMerCode(merParentCode);// 新生成的上级商户CODE
			merchantExtend.setOldMerchantId(bim.getMchnitid());// 老商户表的商户ID
			merchantExtend.setOldMerchantType(MerTypeOldEnum.MERCHANT.getCode());// 标识为老商户表的数据
			merchant.setMerExtend(merchantExtend);
			// ***********************************2.生成连锁商户用户信息************************//
			MerchantUser merchantUser = new MerchantUser();
			if (sysuserstb.getRelname() != null) {
				merchantUser.setMerUserNickname(sysuserstb.getRelname());
			} else {
				merchantUser.setMerUserNickname(sysuserstb.getUsername());
			}
			if (sysuserstb.getLoginid() != null) {
				if (sysuserstb.getLoginid().indexOf("@") >= 0) {
					merchantUser.setMerUserName("U" + DateUtils.dateToStrLongs(new Date()));
				} else {
					merchantUser.setMerUserName(sysuserstb.getLoginid());// 用户昵称、登录名，新平台merCode
																			// +"U"
				}
			} else {
				merchantUser.setMerUserName("U" + DateUtils.dateToStrLongs(new Date()));
			}

			merchantUser.setMerUserPwd(sysuserstb.getPassword());// 登录密码
			merchantUser.setMerUserTelephone(sysuserstb.getTel());// 固定电话
			if (sysuserstb.getMobiltel() != null) {
				merchantUser.setMerUserTelephone(sysuserstb.getMobiltel());// 移动电话
			}
			if (sysuserstb.getProvinceid() != null) {
				merchantUser.setMerUserPro(sysuserstb.getProvinceid().toString());// 省份代码
			} else {
				merchantUser.setMerUserPro(procode);// 省份代码
			}
			if (sysuserstb.getCityid() != null) {
				merchantUser.setMerUserCity(sysuserstb.getCityid().toString());// 城市代码
			} else {
				merchantUser.setMerUserCity(citycode);// 城市代码
			}
			merchantUser.setMerUserAdds(sysuserstb.getAddress());// 地址
			if ("0".equals(sysuserstb.getSex())) {
				merchantUser.setMerUserSex(SexEnum.FEMALE.getCode());// 性别
			} else if ("1".equals(sysuserstb.getSex())) {
				merchantUser.setMerUserSex(SexEnum.MALE.getCode());// 性别
			} else if ("2".equals(sysuserstb.getSex())) {
				merchantUser.setMerUserSex(SexEnum.MALE.getCode());// 性别
			}

			merchantUser.setMerUserIdentityNum(sysuserstb.getIdentityid());// 证件号码
			if ("1000000001".equals(sysuserstb.getIdentitytype())) {// 证件类型
				merchantUser.setMerUserIdentityType("0");
			} else if ("1000000002".equals(sysuserstb.getIdentitytype())) {
				merchantUser.setMerUserIdentityType("2");
			} else if ("1000000005".equals(sysuserstb.getIdentitytype())) {
				merchantUser.setMerUserIdentityType("1");
			} else {
				merchantUser.setMerUserIdentityType(sysuserstb.getIdentitytype());// 证件类型
			}
			merchantUser.setMerUserEmall(sysuserstb.getEmail());// 电子邮件
			merchantUser.setCreateDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FULL_STR));// 注册时间
			merchantUser.setUpdateDate(DateUtils.stringtoDate(sysuserstb.getLastedittime(), DateUtils.DATE_FULL_STR));// 最后修改时间
			merchantUser.setMerUserType(sysuserstb.getUsertype().toString());// 用户类型
			if (sysuserstb.getLastmobiltel() != null) {// 当前使用的移动电话
				merchantUser.setMerUserMobile(sysuserstb.getLastmobiltel());
			} else {
				merchantUser.setMerUserMobile("0000000000");
			}
			if (sysuserstb.getYktcityid() != null) {
				merchantUser.setCityCode(sysuserstb.getYktcityid().toString());// 城市code
			} else {
				merchantUser.setCityCode(citycode);// 城市code
			}
			merchantUser.setBirthday(DateUtils.dateToString(DateUtils.stringtoDate(sysuserstb.getBirthday(), DateUtils.DATE_SMALL_STR), DateUtils.DATE_SMALL_STR));// 生日
			merchantUser.setMerUserNickname(sysuserstb.getRelname());// 真实姓名
			merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());// 用户标志
			merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());// 用户类型
			merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());// 审核状态
			merchantUser.setUserCode(merchantUserService.generateMerUserCode(UserClassifyEnum.MERCHANT));// 用户编号
			merchantUser.setSource(SourceEnum.TRANSFER.getCode());// 用户注册来源
			if (sysuserstb.getStatus() != null) {
				merchantUser.setActivate(sysuserstb.getStatus());// 是否启用
			} else {
				merchantUser.setActivate(ActivateEnum.ENABLE.getCode());// 用户启用
			}
			merchant.setMerUser(merchantUser);

			MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
			merchantUserExtend.setOldUserId(sysuserstb.getUserid());// 老商户表的商户ID
			merchantUserExtend.setOldUserType("3");// 标识为老商户表的数据
			merchant.getMerUser().setMerUserExtend(merchantUserExtend);
			// ***********************************3.生成商户补充信息表MER_DDP_INFO************************//
			Bimchnitfeetb bimchnitfeetbInfo = bimchnitfeetbMapper.findBimchnitfeetbByMchId(bim.getMchnitid());
			MerDdpInfo merDdpInfo = new MerDdpInfo();
			if (bimchnitfeetbInfo != null) {
				if ("3".equals(bimchnitfeetbInfo.getFeetype())) {
					merDdpInfo.setSettlementType("0");// 结算类型
					merDdpInfo.setSettlementThreshold(new BigDecimal(0));// 结算参数
				} else {
					merDdpInfo.setSettlementType(bimchnitfeetbInfo.getFeetype());// 结算类型
					merDdpInfo.setSettlementThreshold(bimchnitfeetbInfo.getSetpara());// 结算参数
				}
			}
			merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度
			merDdpInfo.setLimitSource("0");// 默认自己购买
			merchant.setMerDdpInfo(merDdpInfo);
			// ***********************************4.生成商户密钥分配表数据MER_KEY_TYPE************************//
			MerKeyType merKeyType = new MerKeyType();
			merKeyType.setMerKeyType(MerKeyTypeEnum.MD5.getCode());// 商户密钥类型
			merKeyType.setMerMd5Paypwd(bim.getPaypwd());// 商户MD5签名秘钥
			merKeyType.setMerMd5Backpaypwd(bim.getBackpaypwd());// 商户MD5验签秘钥
			merchant.setMerKeyType(merKeyType);
			// ***********************************6.生成商户业务费率MER_BUS_RATE***********************************//
			List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
			List<MerRateSupplement> merRateSupplementList = new ArrayList<MerRateSupplement>();
			if (bim.getRateamt() != null) {
				MerBusRate merBusRate = new MerBusRate();
				merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());// 充值业务

				TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
				if (cityEnum != null) {
					merBusRate.setProviderCode(cityEnum.getYktCode());
				}

				merBusRate.setRate(bim.getRateamt());// 数值
				merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());// 费率类型
				merBusRateList.add(merBusRate);

				MerRateSupplement merRateSupplement = new MerRateSupplement();
				merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
				merRateSupplementList.add(merRateSupplement);
			}
			if (bimchnitfeetbInfo != null) {
				if (bimchnitfeetbInfo.getTxncode() == 3005) {
					MerBusRate merBusRate = new MerBusRate();
					merBusRate.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());// 消费业务

					TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
					if (cityEnum != null) {
						merBusRate.setProviderCode(cityEnum.getYktCode());
					}

					merBusRate.setRate(bimchnitfeetbInfo.getAmtfee());// 数值
					merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());// 费率类型
					merBusRateList.add(merBusRate);
					MerRateSupplement merRateSupplement2 = new MerRateSupplement();
					merRateSupplement2.setMerCode(merParentCode);
					merRateSupplement2.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
					merRateSupplementList.add(merRateSupplement2);
				}
			}
			merchant.setMerRateSpmtList(merRateSupplementList);
			merchant.setMerBusRateList(merBusRateList);
			// ***********************************7.资金类型************************//
			merchant.setFundType(FundTypeEnum.AUTHORIZED.getCode());
			// ***********************************8.1添加自动转账功能mer_auto_amt************************//
			MerAutoAmt merAutoAmt = new MerAutoAmt();
			merAutoAmt.setMerCode(merParentCode);
			merAutoAmt.setAutoLimitThreshold(new BigDecimal("100000"));
			merAutoAmt.setLimitThreshold(new BigDecimal(0));
			merchant.setMerAutoAmt(merAutoAmt);

			List<Biposidsaletb> listBipos = biposidsaletbMapper.findByPosId(String.valueOf(biposinuserstb.getPosid()));
			List<MerchantTranDiscount> mtDiscountList = new ArrayList<MerchantTranDiscount>();
			List<TranDiscount> tranDiscountList = new ArrayList<TranDiscount>();
			if (CollectionUtils.isNotEmpty(listBipos)) {
				for (Biposidsaletb biposidsaleTemp : listBipos) {
					String saleid = biposidsaleTemp.getSaleid().toString();
					// 根据老的折扣id查询新系统是否已经迁移
					TranDiscount findTranDiscount = tranDiscountMapper.findTranDiscountByOldSaleId(saleid);
					if (findTranDiscount != null) {
						MerchantTranDiscount merTranDis = new MerchantTranDiscount();
						merTranDis.setDiscountId(findTranDiscount.getId());
						mtDiscountList.add(merTranDis);
						merchant.setMtDiscountList(mtDiscountList);
						;
						continue;
					}
					// 查询老平台折扣信息
					Bisalediscount bisalediscount = bisalediscountMapper.findBisalediscountBySaleId(saleid);
					if (bisalediscount != null) {
						TranDiscount tranDiscount = new TranDiscount();
						// 结束日期
						Date endDate = DateUtils.stringtoDate(bisalediscount.getEnddate(), DateUtils.DATE_FORMAT_YYMMDD_STR);
						tranDiscount.setEndDate(endDate);
						// 开始日期
						Date beginDate = DateUtils.stringtoDate(bisalediscount.getStartdate(), DateUtils.DATE_FORMAT_YYMMDD_STR);
						tranDiscount.setBeginDate(beginDate);
						// 星期
						tranDiscount.setWeek(bisalediscount.getWeek());
						// 开始时间
						String startTime = bisalediscount.getStarttime();
						tranDiscount.setBeginTime(startTime.substring(0, 2) + ":" + startTime.substring(2, 4));
						// 结束时间
						String endTime = bisalediscount.getEndtime();
						tranDiscount.setEndTime(endTime.substring(0, 2) + ":" + endTime.substring(2, 4));
						// 用户折扣
						BigDecimal discountThreshold = bisalediscount.getSale();
						if (discountThreshold != null) {
							tranDiscount.setDiscountThreshold(discountThreshold.toString());
						}
						// 结算折扣
						BigDecimal setDiscount = bisalediscount.getSetsale();
						if (setDiscount != null) {
							tranDiscount.setSetDiscount(setDiscount.toString());
						}
						// 老平台折扣id
						tranDiscount.setOldSaleId(saleid);
						tranDiscountList.add(tranDiscount);
						merchant.setTranDiscountList(tranDiscountList);
						MerchantTranDiscount merTranDis = new MerchantTranDiscount();
						merTranDis.setMerCode(merParentCode);
						merTranDis.setDiscountId(tranDiscount.getId());
						mtDiscountList.add(merTranDis);
						merchant.setMtDiscountList(mtDiscountList);
						;
					}
				}
			}

			// ***********************************11.生成商户POS管理表记录*****************************//
			List<Pos> posList = new ArrayList<Pos>();
			Pos pos = new Pos();
			pos.setCode(String.valueOf(biposinfotb.getPosid()));
			pos.setProvinceCode(procode);// 省份code
			pos.setCityCode(citycode);// 城市code
			pos.setMaxTimes(new BigDecimal(999999));// 限制笔数，老系统没有，到新系统默认为0
			pos.setBind("0");// 默认启用

			TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
			if (cityEnum != null) {
				pos.setCityName(cityEnum.getCityName());
			}

			pos.setMerName(sysuserstb.getUsername());// 商户名称
			pos.setBundlingDate(new Date());// 迁移时绑定
			if (biposinfotb.getLasttime() != null) {
				pos.setUpdateDate(DateUtils.stringtoDate(biposinfotb.getLasttime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
			}

			if (biposinfotb.getBuycost() != null) {
				pos.setUnitCost(biposinfotb.getBuycost());// 采购成本
			}
			if (biposinfotb.getFactorycode() != null) {
				pos.setPosCompanyCode(biposinfotb.getFactorycode());// 厂商编号
			}
			if (biposinfotb.getPatterncode() != null) {
				pos.setPosTypeCode(biposinfotb.getPatterncode());// 型号编号
			}

			if (biposinfotb.getVersion() != null) {
				pos.setVersion(biposinfotb.getVersion().toString());// pos版本号
			}

			// 老系统，0:启用,1:未启用,2:停用,3:作废,4：消费封锁,5：充值封锁; 
			// 新系统，0：启用,1：停用,2：作废,3：充值封锁,4：消费封锁
			if ("0".equals(biposinfotb.getStatus())) {
				pos.setStatus("0");
			} else if ("2".equals(biposinfotb.getStatus()) || "1".equals(biposinfotb.getStatus())) {
				pos.setStatus("1");
			} else if ("3".equals(biposinfotb.getStatus())) {
				pos.setStatus("2");
			} else if ("4".equals(biposinfotb.getStatus())) {
				pos.setStatus("4");
			} else if ("5".equals(biposinfotb.getStatus())) {
				pos.setStatus("3");
			}
			posList.add(pos);
			merchant.setPosList(posList);

			// ***********************************生成商户*****************************//
			response = merchantService.addMerchantInfo(merchant);

			// ***********************************14.生成日志记录*****************************//
			logTransfer.setBatchId(batchId);// 日志批次号
			logTransfer.setOldMerchantId(bim.getMchnitid());// 老商户ID
			logTransfer.setOldMerchantType(MerTypeOldEnum.MERCHANT.getCode());// 老商户类型
			logTransfer.setNewMerchantCode(response.getResponseEntity());// 新商户号
			logTransfer.setNewMerchantType(MerTypeEnum.CHAIN_JOIN_MER.getCode());// 新商户类型
			logTransfer.setState("0");// 成功和失败的状态
			logTransfer.setMemo(ResponseCode.SUCCESS);// 导入描述
			logTransferMapper.insartLogTransfer(logTransfer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return response;
	}

}
