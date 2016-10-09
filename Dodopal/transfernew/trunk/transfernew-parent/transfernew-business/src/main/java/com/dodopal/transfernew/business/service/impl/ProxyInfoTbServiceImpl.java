package com.dodopal.transfernew.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerTypeOldEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SettlementTypeEnum;
import com.dodopal.common.enums.SexEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfernew.business.dao.BiposinfotbMapper;
import com.dodopal.transfernew.business.dao.BiposinuserstbMapper;
import com.dodopal.transfernew.business.dao.LogTransferMapper;
import com.dodopal.transfernew.business.dao.PosMerTbMapper;
import com.dodopal.transfernew.business.dao.ProxyinfotbMapper;
import com.dodopal.transfernew.business.dao.ProxypostbMapper;
import com.dodopal.transfernew.business.dao.ProxyuserinfotbMapper;
import com.dodopal.transfernew.business.dao.SysuserstbMapper;
import com.dodopal.transfernew.business.model.LogTransfer;
import com.dodopal.transfernew.business.model.old.Biposinfotb;
import com.dodopal.transfernew.business.model.old.Biposinuserstb;
import com.dodopal.transfernew.business.model.old.Proxyinfotb;
import com.dodopal.transfernew.business.model.old.Proxypostb;
import com.dodopal.transfernew.business.model.old.Proxyuserinfotb;
import com.dodopal.transfernew.business.model.old.Sysuserstb;
import com.dodopal.transfernew.business.model.transfer.MerBusRate;
import com.dodopal.transfernew.business.model.transfer.MerDdpInfo;
import com.dodopal.transfernew.business.model.transfer.MerRateSupplement;
import com.dodopal.transfernew.business.model.transfer.Merchant;
import com.dodopal.transfernew.business.model.transfer.MerchantExtend;
import com.dodopal.transfernew.business.model.transfer.MerchantUser;
import com.dodopal.transfernew.business.model.transfer.MerchantUserExtend;
import com.dodopal.transfernew.business.model.transfer.Pos;
import com.dodopal.transfernew.business.model.transfer.PosMerTb;
import com.dodopal.transfernew.business.service.MerchantService;
import com.dodopal.transfernew.business.service.MerchantUserService;
import com.dodopal.transfernew.business.service.ProxyInfoTbService;

@Service
public class ProxyInfoTbServiceImpl implements ProxyInfoTbService {

	private Logger logger = LoggerFactory.getLogger(ProxyInfoTbServiceImpl.class);
	
//	@Autowired
//	private ProxyInfoTbService ProxyInfoTbService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MerchantUserService merchantUserService;
	
	@Autowired
	private LogTransferMapper logTransferMapper;
	@Autowired
    private ProxyinfotbMapper proxyinfotbMapper;
	@Autowired
	private ProxyuserinfotbMapper proxyuserinfotbMapper;
	@Autowired
	private ProxypostbMapper proxypostbMapper;
	@Autowired
	private BiposinuserstbMapper biposinuserstbMapper;
	@Autowired
	private SysuserstbMapper sysuserstbMapper;
	@Autowired
	private PosMerTbMapper posMerTbMapper;
	@Autowired
	private BiposinfotbMapper biposinfotbMapper;
	
	
	public DodopalResponse<String> insertDataByProxyId(String cityCode) {
		DodopalResponse<String> res = new DodopalResponse<String>();
        TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(cityCode);
        String cityno = cityEnum.getProxyCityNo();
		try {
			List<Proxyinfotb> proxyinfotbList = proxyinfotbMapper.findProxyInfoTbByCitynoAndProxytypeid(cityno, 1); 	// // 在老系统网点信息表proxyInfoTb中按查询条件cityno=100000-1110&proxytypeid=1查询出需要迁移的个人网点
			String  batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
			for(Proxyinfotb proxyinfotb : proxyinfotbList) {
				// 日志
				LogTransfer logTransfer = new LogTransfer();
				logTransfer.setBatchId(batchId);// 日志批次号
				logTransfer.setOldMerchantId(String.valueOf(proxyinfotb.getProxyid()));// 老商户ID
				logTransfer.setOldMerchantType(MerTypeOldEnum.PROXY.getCode());
				try {
					DodopalResponse<String> stepsResponse = stepsInsertDate(proxyinfotb, batchId);
					
					logTransfer.setNewMerchantCode(stepsResponse.getResponseEntity());// 新商户号
					logTransfer.setNewMerchantType(MerTypeEnum.DDP_MER.getCode());// 新商户类型
					logTransfer.setState("0");// 成功和失败的状态
				}catch(Exception ex) {
					logger.error("" + proxyinfotb.getProxyid(), ex);
					ex.printStackTrace();
					logTransfer.setState("1");// 成功和失败的状态
					logTransfer.setMemo(ex.getMessage());// 导入描述
				}finally {
					try {
						int num = logTransferMapper.insartLogTransfer(logTransfer);
						if (num == 0) {
							logger.warn("个人网点数据库日志写入失败");
						}
					} catch (Exception e) {
						logger.error("个人网点数据库日志写入失败，异常信息:" + e.getMessage(), e);
					}
				}
			}
		}catch(Exception e) {
			
		}
		return res;
	}

	@Transactional
	public DodopalResponse<String> stepsInsertDate(Proxyinfotb proxyinfotb, String batchId) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		String merCode = merchantService.generateMerCode(MerClassifyEnum.OFFICIAL.getCode());
		// 联系人手机
		String merLinkUserMobile = "000000";
		Long proxyid = proxyinfotb.getProxyid();
		
		
		/**************************** 步骤2：生成商户信息表MERCHANT ************************************/
		Merchant merchant = new Merchant();
 		merchant.setMerCode(merCode);
		merchant.setMerName(proxyinfotb.getProxyname());
		merchant.setMerType(MerTypeEnum.CHAIN_STORE_MER.getCode());
		merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());// 商户分类
		merchant.setFundType(FundTypeEnum.AUTHORIZED.getCode());
		merchant.setMerAdds(proxyinfotb.getProxyaddress());
		if(StringUtils.isNotBlank(proxyinfotb.getProxytel())) {
			merchant.setMerTelephone(proxyinfotb.getProxytel());
		}
		merchant.setMerLinkUserMobile(merLinkUserMobile);
		merchant.setMerFax(proxyinfotb.getProxyfax());
		if(StringUtils.isNotBlank(proxyinfotb.getRegisttime())) {
			merchant.setMerRegisterDate(DateUtils.stringtoDate(proxyinfotb.getRegisttime(), "yyyy-MM-dd HH:mm:ss"));
		}
		//审核状态
		//老系统：0 提交注册未激活1 已激活启用2 停用3审批中4审批未通过5审批通过
		//新系统：0未审核1审核通过2审核未通过，0启用1停用
		//转换规则：
		//未激活0、审批中3(老系统)      ：未审核，启用
		//停用2(老系统)                ：审核通过，停用
		//已激活启用1、审批通过5(老系统)：审核通过，启用
		//审批未通过4（老系统）                       ：审核未通过，启用
		String oldStatus = proxyinfotb.getStatus().toString();
		if("0".equals(oldStatus) || "3".equals(oldStatus)) {
			merchant.setMerState("0");
			merchant.setActivate("0");
		} else if("1".equals(oldStatus) || "5".equals(oldStatus)) {
			merchant.setMerState("1");
			merchant.setActivate("0");
		} else if("2".equals(oldStatus)) {
			merchant.setMerState("1");
			merchant.setActivate("1");
		} else if("4".equals(oldStatus)) {
			merchant.setMerState("2");
			merchant.setActivate("0");
		}
		if(StringUtils.isNotBlank(proxyinfotb.getEdittime())) {
			merchant.setMerDeactivateDate(DateUtils.stringtoDate(proxyinfotb.getEdittime(), "yyyy-MM-dd HH:mm:ss"));
		}
		if(StringUtils.isNotBlank(proxyinfotb.getApprovaldate())) {
			merchant.setMerStateDate(DateUtils.stringtoDate(proxyinfotb.getApprovaldate(), "YYYY-MM-DD"));
		}
		merchant.setMerBankUserName(proxyinfotb.getBankaccountname());
		merchant.setMerBankAccount(proxyinfotb.getBankacc());
		merchant.setMerBankName(proxyinfotb.getBankaccname());
		merchant.setSource(SourceEnum.TRANSFER.getCode());
		//省份城市
		merchant.setMerPro(TransferProCtiyCodeEnum.QINGDAO.getProCode());
		merchant.setMerCity(TransferProCtiyCodeEnum.QINGDAO.getCityCode());
		        		
		/**************************** 步骤3：生成商户补充信息表MER_DDP_INFO数据 ************************************/
		MerDdpInfo merDdpInfo = new MerDdpInfo();
		
		merDdpInfo.setMerCode(merCode);
		if("1".equals(proxyinfotb.getFeetype()) || "2".equals(proxyinfotb.getFeetype())) { 	// 1:笔数 2:金额
			merDdpInfo.setSettlementType(proxyinfotb.getFeetype());
			merDdpInfo.setSettlementThreshold(proxyinfotb.getSetpara());
		} else if("3".equals(proxyinfotb.getFeetype())) { // 3:周期
			merDdpInfo.setSettlementType("0");
			merDdpInfo.setSettlementThreshold(new BigDecimal(0));//结算参数
		}
		merDdpInfo.setIsAutoDistribute(1+"");
		
		merchant.setMerDdpInfo(merDdpInfo);
		      		
		
		/**************************** 步骤4：生成商户信息扩展表MERCHANT_EXTEND数据 ************************************/
		MerchantExtend merchantExtend = new MerchantExtend();
//			
		merchantExtend.setMerCode(merCode);
		merchantExtend.setOldMerchantId(proxyid+"");
		merchantExtend.setOldMerchantType(MerTypeOldEnum.PROXY.getCode());

		merchant.setMerExtend(merchantExtend);
				
		        		
		/**************************** 步骤5：生成用户信息表MERCHANT_USER数据 ************************************/
		List<Proxyuserinfotb> proxyuserinfotbList = proxyuserinfotbMapper.findProxyuserinfotbByProxyid(proxyid+"");
        if (CollectionUtils.isEmpty(proxyuserinfotbList)) {
            logger.error("网点id：" + proxyid + "对应的网点用户信息不存在。");
//            continue;
            throw new RuntimeException("网点id：" + proxyid + "对应的网点用户信息不存在。");
        }
		List<MerchantUser> mu = new ArrayList<MerchantUser>(); 
		for(Proxyuserinfotb proxyuserinfotb : proxyuserinfotbList) {
			boolean merUserCodeFlag = merchantUserService.checkExist("U"+merCode);
			String userCode = merchantUserService.generateMerUserCode(UserClassifyEnum.MERCHANT);
			MerchantUser merchantUser = new MerchantUser();
			
			if(merUserCodeFlag){
				logger.error("商户用户MerUserCode：" + "U"+merCode + "对应的用户存在。");
				System.out.println("start");
				throw new DDPException(ResponseCode.UNKNOWN_ERROR);
			}
					
			if(StringUtils.isNotBlank(proxyuserinfotb.getLoginname())) {
				merchantUser.setMerUserName(proxyuserinfotb.getLoginname());
			}
			if(StringUtils.isNotBlank(proxyuserinfotb.getPwd())) {
				merchantUser.setMerUserPwd(proxyuserinfotb.getPwd());
			}
			if (proxyuserinfotb.getIdentitytypeid() != null) {
				if ("1000000001".equals(proxyuserinfotb.getIdentitytypeid().toString())) { 	// 证件类型
					merchantUser.setMerUserIdentityType("0");
				} else if ("1000000002".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
					merchantUser.setMerUserIdentityType("2");
				} else if ("1000000005".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
					merchantUser.setMerUserIdentityType("1");
				}
			}
			merchantUser.setMerUserIdentityNum(proxyuserinfotb.getIdentityid());
			merchantUser.setMerUserNickname(proxyuserinfotb.getUsername());
			if(BigDecimal.valueOf(1).compareTo(proxyuserinfotb.getSex()) == 0) { 	// 老系统：1:男 0:女 新系统：1：女0：男
				merchantUser.setMerUserSex(SexEnum.MALE.getCode());
			}
			if(BigDecimal.valueOf(0).compareTo(proxyuserinfotb.getSex()) == 0) {
				merchantUser.setMerUserSex(SexEnum.FEMALE.getCode());
			}
			merchantUser.setMerUserEmall(proxyuserinfotb.getEmail());
			if(StringUtils.isBlank(proxyuserinfotb.getMobiltel())) {
				merchantUser.setMerUserMobile(merLinkUserMobile);
			} else {
				merchantUser.setMerUserMobile(proxyuserinfotb.getMobiltel());
			}
			merchantUser.setMerUserTelephone(proxyuserinfotb.getTel());
			merchantUser.setMerUserPro(proxyuserinfotb.getProvinceid()+"");
			merchantUser.setMerUserCity(proxyuserinfotb.getCityid()+"");
			merchantUser.setMerUserAdds(proxyuserinfotb.getAddress());
			merchantUser.setMerCode(merCode);

			

			merchantUser.setMerUserRemark(proxyuserinfotb.getRemarks());
			if(StringUtils.isNotBlank(proxyuserinfotb.getLastchoosecity())) {
				merchantUser.setCityCode(proxyuserinfotb.getLastchoosecity().substring(proxyuserinfotb.getLastchoosecity().length()-4, proxyuserinfotb.getLastchoosecity().length()));
			}
			merchantUser.setUserCode(userCode); 								// 用户编号
			merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode()); 	// 用户类型
			merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode()); 		// 审核状态
			// 用户来源
			merchantUser.setSource(SourceEnum.TRANSFER.getCode());

			merchantUser.setMerUserPro(TransferProCtiyCodeEnum.QINGDAO.getProCode());
			merchantUser.setMerUserCity(TransferProCtiyCodeEnum.QINGDAO.getCityCode());
			merchantUser.setActivate(ActivateEnum.ENABLE.getCode());//0启用，1停用
			
			/**************************** 步骤6：生成用户信息扩展表MERCHANT_USER_EXTEND数据 ************************************/
			MerchantUserExtend merchantUserExtend = new MerchantUserExtend();

			merchantUserExtend.setOldUserId(Long.toString(proxyuserinfotb.getUserid()));
			merchantUserExtend.setOldUserType(MerTypeOldEnum.PROXY.getCode());
			merchantUserExtend.setUserCode(userCode);				
			merchantUser.setMerUserExtend(merchantUserExtend);
			System.out.println("proxyid: "+proxyid);
			System.out.println("proxyuserinfotb.getStatus(): "+proxyuserinfotb.getStatus());
			if(BigDecimal.valueOf(0).compareTo(proxyuserinfotb.getStatus()) == 0) { 	// 如果是管理员
				// 用户标志：管理员
				merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());
				merchant.setMerUser(merchantUser);
			}else {
				// 用户标志：普通操作员
				merchantUser.setMerUserFlg(MerUserFlgEnum.COMMON.getCode());
				mu.add(merchantUser);
			}
		}
		
		merchant.setMerUserList(mu);
		merchantService.addMerchantInfo(merchant);

/*********************************************************************************************************************************************************************/
		/**************************** 步骤7：生成pos信息、pos绑定中间表数据 ************************************/
		List<Proxypostb> proxypostbList = proxypostbMapper.findProxypostbListById(Long.toString(proxyid));
		
		if (proxypostbList.size() == 0) {
			logger.error("网点id：" + proxyid + "对应的网点绑定pos信息不存在。");
		}
		for(Proxypostb proxypostb : proxypostbList) {
			Merchant merchant1 = new Merchant();
			List<Pos> posList = new ArrayList<Pos>(); 
			List<MerRateSupplement> merRateSupplementList = new ArrayList<MerRateSupplement>();
			List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
			String merCode1 = merchantService.generateMerCode(MerClassifyEnum.OFFICIAL.getCode());
			merchant1.setMerCode(merCode1);
			
			System.out.println("*****************"+proxypostb.getPosid());
			Biposinuserstb biposinuserstb  = biposinuserstbMapper.findBiposinuserstbByPosId(proxypostb.getPosid());

			BigDecimal userid = biposinuserstb.getUserid();
			Sysuserstb sysuserstb = sysuserstbMapper.findSysuserstb(userid+"");
			
			merchant1.setMerType(MerTypeEnum.CHAIN_STORE_MER.getCode()); 	// 商户类型
			merchant1.setSource(SourceEnum.TRANSFER.getCode()); 	// 来源
			merchant1.setMerPro(TransferProCtiyCodeEnum.QINGDAO.getProCode());
			merchant1.setMerCity(TransferProCtiyCodeEnum.QINGDAO.getCityCode());
            merchant1.setActivate(ActivateEnum.ENABLE.getCode()); 	// 启用
            merchant1.setDelFlg(DelFlgEnum.NORMAL.getCode()); 	// 删除标志:0：正常、1：删除
			if(StringUtils.isNotBlank(sysuserstb.getMobiltel())) { 	// 商户联系人电话
				merchant1.setMerLinkUserMobile(sysuserstb.getMobiltel());
			}else {
				merchant1.setMerLinkUserMobile(merLinkUserMobile);
			}
			merchant1.setMerEmail(sysuserstb.getEmail());
			merchant1.setMerZip(sysuserstb.getZipcode()); 	// 邮编
			merchant1.setMerAdds(sysuserstb.getAddress()); 	// 联系地址
			merchant1.setMerTelephone(sysuserstb.getTel()); 	// 固定电话
			merchant1.setMerName(sysuserstb.getUsername()); 	// 商户名称
			merchant1.setMerLinkUser(sysuserstb.getRelname()); 	// 联系人
            merchant1.setMerParentCode(merCode); 	// 上级商户CODE  
            merchant1.setMerClassify(MerClassifyEnum.OFFICIAL.getCode()); 	// 商户分类
            merchant1.setMerFax(""); 	// 传真号码
            merchant1.setMerBankAccount(""); 	// 开户行账号
            merchant1.setMerBankName(""); 	// 开户行名称
            merchant1.setMerBusinessScopeId(""); 	// 经营范围:数据字典维护
            if (sysuserstb.getProvinceid() != null) {
                merchant1.setMerPro(sysuserstb.getProvinceid().toString()); 	// 商户省份
            }else{
                merchant1.setMerPro(TransferProCtiyCodeEnum.QINGDAO.getProCode()); 	// 商户省份 
            }
            if (sysuserstb.getCityid() != null) {
                merchant1.setMerCity(sysuserstb.getCityid().toString()); 	// 商户城市
            }else{
                merchant1.setMerCity(TransferProCtiyCodeEnum.QINGDAO.getCityCode()); 	// 商户城市  
            }
            merchant1.setMerRegisterDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR)); 	// 注册时间
            /*--------------*/
			merchant1.setMerState(MerStateEnum.THROUGH.getCode()); // 审核状态
			merchant1.setMerProperty(MerPropertyEnum.STANDARD.getCode()); // 商户属性
			merchant1.setFundType(FundTypeEnum.FUND.getCode()); 	// 资金帐户
			/**/
			MerchantExtend merchantExtend1 = new MerchantExtend();
			//merchantExtend.setMerCode(merParentCode); 	// 新生成的上级商户CODE
			merchantExtend1.setOldMerchantId(proxyid+""); 	// 老商户表的商户ID
			merchantExtend1.setOldMerchantType(MerTypeOldEnum.MERCHANT.getCode()); 	// 标识为老商户表的数据
			merchant1.setMerExtend(merchantExtend1);
			// ***********************************2.生成连锁商户用户信息************************//
			MerchantUser merchantUser1 = new MerchantUser();

			merchantUser1.setMerCode(merCode1);// 所属商户编号
			if (sysuserstb.getRelname() != null) {
				merchantUser1.setMerUserNickname(sysuserstb.getRelname());
			} else {
				merchantUser1.setMerUserNickname(sysuserstb.getUsername());
			}
			if (sysuserstb.getLoginid() != null) {
				if (sysuserstb.getLoginid().indexOf("@") >= 0) {
					merchantUser1.setMerUserName("U" + merCode1);
				} else {
					merchantUser1.setMerUserName(sysuserstb.getLoginid());// 用户昵称、登录名，新平台merCode +"U"
				}
			} else {
				merchantUser1.setMerUserName("U" + merCode1);
			}

			merchantUser1.setMerUserPwd(sysuserstb.getPassword());// 登录密码
			merchantUser1.setMerUserTelephone(sysuserstb.getTel());// 固定电话
			if (sysuserstb.getMobiltel() != null) {
				merchantUser1.setMerUserTelephone(sysuserstb.getMobiltel());// 移动电话
			}
			if (sysuserstb.getProvinceid() != null) {
				merchantUser1.setMerUserPro(sysuserstb.getProvinceid().toString());// 省份代码
			} else {
				merchantUser1.setMerUserPro(TransferProCtiyCodeEnum.QINGDAO.getProCode());// 省份代码
			}
			if (sysuserstb.getCityid() != null) {
				merchantUser1.setMerUserCity(sysuserstb.getCityid().toString());// 城市代码
			} else {
				merchantUser1.setMerUserCity(TransferProCtiyCodeEnum.QINGDAO.getCityCode());// 城市代码
			}
			merchantUser1.setMerUserAdds(sysuserstb.getAddress());// 地址
			if ("0".equals(sysuserstb.getSex())) {
				merchantUser1.setMerUserSex(SexEnum.FEMALE.getCode());// 性别
			} else if ("1".equals(sysuserstb.getSex())) {
				merchantUser1.setMerUserSex(SexEnum.MALE.getCode());// 性别
			} else if ("2".equals(sysuserstb.getSex())) {
				merchantUser1.setMerUserSex(SexEnum.MALE.getCode());// 性别
			}

			merchantUser1.setMerUserIdentityNum(sysuserstb.getIdentityid());// 证件号码
			if ("1000000001".equals(sysuserstb.getIdentitytype())) {// 证件类型
				merchantUser1.setMerUserIdentityType("0");
			} else if ("1000000002".equals(sysuserstb.getIdentitytype())) {
				merchantUser1.setMerUserIdentityType("2");
			} else if ("1000000005".equals(sysuserstb.getIdentitytype())) {
				merchantUser1.setMerUserIdentityType("1");
			} else {
				merchantUser1.setMerUserIdentityType(sysuserstb.getIdentitytype());// 证件类型
			}
			merchantUser1.setMerUserEmall(sysuserstb.getEmail());// 电子邮件
			merchantUser1.setCreateDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FULL_STR));// 注册时间
			merchantUser1.setUpdateDate(DateUtils.stringtoDate(sysuserstb.getLastedittime(), DateUtils.DATE_FULL_STR));// 最后修改时间
			merchantUser1.setMerUserType(sysuserstb.getUsertype().toString());// 用户类型
			if (sysuserstb.getLastmobiltel() != null) {// 当前使用的移动电话
				merchantUser1.setMerUserMobile(sysuserstb.getLastmobiltel());
			} else {
				merchantUser1.setMerUserMobile("0000000000");
			}
			if (sysuserstb.getYktcityid() != null) {
				merchantUser1.setCityCode(sysuserstb.getYktcityid().toString());// 城市code
			} else {
				merchantUser1.setCityCode(TransferProCtiyCodeEnum.QINGDAO.getCityCode());// 城市code
			}
			merchantUser1.setBirthday(DateUtils.dateToString(DateUtils.stringtoDate(sysuserstb.getBirthday(), DateUtils.DATE_SMALL_STR), DateUtils.DATE_SMALL_STR));// 生日
			merchantUser1.setMerUserNickname(sysuserstb.getRelname());// 真实姓名
			merchantUser1.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());// 用户标志
			merchantUser1.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());// 用户类型
			merchantUser1.setMerUserState(MerStateEnum.THROUGH.getCode());// 审核状态
			merchantUser1.setUserCode(merchantUserService.generateMerUserCode(UserClassifyEnum.MERCHANT));// 用户编号
			merchantUser1.setSource(SourceEnum.TRANSFER.getCode());// 用户注册来源
			if (sysuserstb.getStatus() != null) {
				merchantUser1.setActivate(sysuserstb.getStatus());// 是否启用
			} else {
				merchantUser1.setActivate(ActivateEnum.ENABLE.getCode());// 用户启用
			}
			
			
			MerchantUserExtend merchantUserExtend1 = new MerchantUserExtend();
			merchantUserExtend1.setUserCode(merchantUser1.getUserCode());// 新生成的商户CODE
			merchantUserExtend1.setOldUserId(sysuserstb.getUserid());// 老商户表的商户ID
			
            merchantUser1.setMerUserExtend(merchantUserExtend1);
            merchant1.setMerUser(merchantUser1);
            
            MerDdpInfo merDdpInfo1 = new MerDdpInfo();
            merDdpInfo1.setMerCode(merchant1.getMerCode());//商户编号
            merDdpInfo1.setSettlementType(SettlementTypeEnum.DAYS_NUMBER.getCode());//结算类型  0：天数 1：笔数 2：金额 
            merDdpInfo1.setSettlementThreshold(new BigDecimal(7));//结算参数
            merDdpInfo1.setIsAutoDistribute("2");// 是否自动分配额度      0 是  , 1否, 2 共享额度
            merchant1.setMerDdpInfo(merDdpInfo1);//生成商户补充信息表
            
            /**************************** pos_mer_tb ************************************/
			PosMerTb posMerTb = new PosMerTb();
					
			posMerTb.setCode(proxypostb.getPosid());
			posMerTb.setComments(proxypostb.getRemarks());
			posMerTb.setMerCode(merCode1);
					
			posMerTbMapper.addPosMerTb(posMerTb);
					
					
			/**************************** pos ************************************/
			Biposinfotb biposinfotb = biposinfotbMapper.findBiposinfotbByPosId(proxypostb.getPosid());

			if (biposinfotb != null) {
				Pos pos = new Pos();
				pos.setCode(proxypostb.getPosid());
				if ("0".equals(biposinfotb.getStatus())) {
					pos.setStatus("0");
				}
				if ("2".equals(biposinfotb.getStatus())) {
					pos.setStatus("1");
				}
				if ("3".equals(biposinfotb.getStatus())) {
					pos.setStatus("2");
				}
				if ("5".equals(biposinfotb.getStatus())) {
					pos.setStatus("3");
				}
				if ("4".equals(biposinfotb.getStatus())) {
					pos.setStatus("4");
				}
				pos.setUpdateDate(DateUtils.stringtoDate(biposinfotb.getLasttime(), "yyyyMMddHHmmss"));
				pos.setUnitCost(biposinfotb.getBuycost());
				pos.setPosCompanyCode(biposinfotb.getFactorycode());
				pos.setPosTypeCode(biposinfotb.getPatterncode());
				pos.setMerCode(merCode);
				pos.setMaxTimes(new BigDecimal(9999999999l));
				pos.setBind(BindEnum.ENABLE.getCode());
				pos.setBundlingDate(new Date());
				pos.setProvinceCode(TransferProCtiyCodeEnum.QINGDAO.getProCode());
				pos.setProvinceName(TransferProCtiyCodeEnum.QINGDAO.getProName());
				pos.setCityCode(TransferProCtiyCodeEnum.QINGDAO.getCityCode());
				pos.setCityName(TransferProCtiyCodeEnum.QINGDAO.getCityName());

				posList.add(pos);
			}
			/**************************** 步骤8：生成商户业务信息表MER_RATE_SUPPLEMENT数据    mer_rate_supplement ************************************/
			if (proxyinfotb.getRateamt() != null && StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
				MerRateSupplement merRateSupplement = new MerRateSupplement();
				
				merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode()); 					// 充值业务
				merRateSupplement.setMerCode(merchant1.getMerCode());
						
				merRateSupplementList.add(merRateSupplement);
			}

			if (proxyinfotb.getPayflag()!=null && proxyinfotb.getPayflag().intValue() == 1) {
				MerRateSupplement merRateSupplement = new MerRateSupplement();
				
				merRateSupplement.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode()); 					// 消费业务
				merRateSupplement.setMerCode(merchant1.getMerCode()); 	// 商户编号
						
				merRateSupplementList.add(merRateSupplement);
			}
			
			
			/****************************   步骤9：生成商户业务费率表MER_BUS_RATE数据  ************************************/
			if (proxyinfotb.getRateamt() != null && StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
				MerBusRate merBusRate = new MerBusRate();
				
				merBusRate.setMerCode(merchant.getMerCode()); 	// 商户编号
				merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode()); 					// 充值业务
				merBusRate.setProviderCode(TransferProCtiyCodeEnum.QINGDAO.getYktCode()); 			// 供应商编号
				merBusRate.setRate(new BigDecimal(0)); 			// 数值
				merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode()); 					// 费率类型:1.笔数;2.千分比
				
				merBusRateList.add(merBusRate);
			}

			if (proxyinfotb.getPayflag()!=null && proxyinfotb.getPayflag().intValue() == 1) {
				MerBusRate merBusRate = new MerBusRate();
				
				merBusRate.setMerCode(merchant.getMerCode()); 	// 商户编号
				merBusRate.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode()); 					// 消费业务
				merBusRate.setProviderCode(TransferProCtiyCodeEnum.QINGDAO.getYktCode()); 			// 供应商编号
				merBusRate.setRate(new BigDecimal(0)); 			// 数值
				merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode()); 					// 费率类型:1.笔数;2.千分比
				
				merBusRateList.add(merBusRate);
			}
			
			
            merchantService.addMerchantInfo(merchant1);
		}
		

		// 更新迁移标识
		proxyinfotbMapper.updateTransFlag(Long.toString(proxyinfotb.getProxyid()));

		response.setCode(ResponseCode.SUCCESS);
		response.setResponseEntity(merchant.getMerCode());
		return response;
	}
	

	
	
	
	
	
	

}
