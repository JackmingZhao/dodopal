package com.dodopal.transfer.business.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SexEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.AccountControlMapper;
import com.dodopal.transfer.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfer.business.dao.AccountCouponMapper;
import com.dodopal.transfer.business.dao.AccountFundMapper;
import com.dodopal.transfer.business.dao.AccountMapper;
import com.dodopal.transfer.business.dao.BiposinfotbMapper;
import com.dodopal.transfer.business.dao.BiposinuserstbMapper;
import com.dodopal.transfer.business.dao.MerUserCardBdMapper;
import com.dodopal.transfer.business.dao.MerchantUserExtendMapper;
import com.dodopal.transfer.business.dao.MerchantUserMapper;
import com.dodopal.transfer.business.dao.ScoreuserCardMapper;
import com.dodopal.transfer.business.dao.SysuserstbMapper;
import com.dodopal.transfer.business.dao.TlcouponinfoMapper;
import com.dodopal.transfer.business.dao.TlcouponinfoTargetMapper;
import com.dodopal.transfer.business.dao.TlposPointsMapper;
import com.dodopal.transfer.business.dao.TlposPointsTnxMapper;
import com.dodopal.transfer.business.model.old.ScoreuserCard;
import com.dodopal.transfer.business.model.old.Sysuserstb;
import com.dodopal.transfer.business.model.old.Tlcouponinfo;
import com.dodopal.transfer.business.model.target.Account;
import com.dodopal.transfer.business.model.target.AccountControl;
import com.dodopal.transfer.business.model.target.AccountControllerDefault;
import com.dodopal.transfer.business.model.target.AccountCoupon;
import com.dodopal.transfer.business.model.target.AccountFund;
import com.dodopal.transfer.business.model.target.MerUserCardBd;
import com.dodopal.transfer.business.model.target.MerchantUser;
import com.dodopal.transfer.business.model.target.MerchantUserExtend;
import com.dodopal.transfer.business.model.target.TlcouponinfoTarget;
import com.dodopal.transfer.business.service.AccountService;
import com.dodopal.transfer.business.service.PersonalInfoService;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private Logger logger = LoggerFactory.getLogger(PersonalInfoServiceImpl.class);

    @Autowired
    private SysuserstbMapper sysuserstbMapper;

    @Autowired
    private TlcouponinfoMapper tlcouponinfoMapper;

    @Autowired
    private BiposinfotbMapper biposinfotbMapper;

    @Autowired
    private TlposPointsMapper tlposPointsMapper;

    @Autowired
    private TlposPointsTnxMapper tlposPointsTnxMapper;
    @Autowired
    private MerUserCardBdMapper merUserCardBdMapper;
    @Autowired
    private ScoreuserCardMapper scoreuserCardMapper;

    @Autowired
    private MerchantUserMapper merchantUserMapper;

    @Autowired
    private MerchantUserExtendMapper merchantUserExtendMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountFundMapper accountFundMapper;

    @Autowired
    private AccountControlMapper accountControlMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountCouponMapper accountCouponMapper;
    @Autowired
    private TlcouponinfoTargetMapper tlcouponinfoTargetMapper;
    @Autowired
    private AccountControllerDefaultMapper accountControllerDefaultMapper;
    @Autowired
    private BiposinuserstbMapper biposinuserstbMapper;

    @Transactional
    public DodopalResponse<String> insertSysUserstb(String userid, String batchId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        //***********************************    1、查询需要迁移的个人用户信息   **************************//
        //		String userType ="2";
        //		String cityId ="1110";
        //		String YktCityId ="1110";
        String merUserCode = generateMerUserCode(UserClassifyEnum.PERSONAL);//用户CODE
        response.setResponseEntity(merUserCode);
        //查询用户的基础信息
            Sysuserstb sysUserstb = sysuserstbMapper.findSysUserstb(userid);
            if (sysUserstb == null) {
                logger.error("用户信息不存在");
                throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            }
            //***********************************    1.1、查询优惠券使用信息   **************************//
            List<Tlcouponinfo> findTlcouponinfo = tlcouponinfoMapper.findTlcouponinfoById(userid);

            //***********************************    1.2、查询用户相关pos信息   **************************//
            //Biposinuserstb biposinuserstb = biposinuserstbMapper.findBiposinuserstb(userid);
            //		if (biposinuserstb == null) {
            //            logger.error("biposinuserstb不存在");
            //            throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            //        }else{
            //        	long posUserid = biposinuserstb.getPosid();
            //        	if (biposinuserstb.getPosid() == 0) {
            //        		logger.error("posUserid不存在");
            //        		throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            //        	}
            //        	Tlpospoint tlpospointinfo = tlposPointsMapper.findTlpospointById(biposinuserstb.getPosid());
            //        	if (tlpospointinfo == null) {
            //        		logger.error("用户相关pos不存在");
            //        		 throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            //        	}

            //***********************************    1.3、查询用户的积分信息   **************************//
            //        	long posId = tlpospointinfo.getPosid().longValue();
            //        	Tlpospoint tlpospoint = tlposPointsMapper.findTlpospointById(posId);
            //        	if (tlpospoint == null) {
            //        		logger.error("用户的积分信息不存在");
            //        		throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            //        	}

            //***********************************    1.4、查询用户的积分流水   **************************//
            //        	List<Tlpospointstnx> tlpospointstnxs = tlposPointsTnxMapper.findTlpospointstnxById(posId);
            //        	if (tlpospointstnxs.size() == 0) {
            //        		logger.error("用户的积分流水信息不存在");
            //        		throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            //        	}
            //        	
            //***********************************    1.5、查询用户的绑定卡片信息  **************************//

            //***********************************    2、生成用户信息表记录MERCHANT_USER  **************************//
            MerchantUser merchantUser = new MerchantUser();
            merchantUser.setUserCode(merUserCode);//正式个人用户
            if (sysUserstb.getRelname() == null) {
                merchantUser.setMerUserNickname(sysUserstb.getUsername());
            } else {
                merchantUser.setMerUserNickname(sysUserstb.getRelname());
            }
            merchantUser.setMerUserName(sysUserstb.getLoginid());//用户昵称、登录名
            if (sysUserstb.getLoginid() != null) {
                if (sysUserstb.getLoginid().indexOf("@") >= 0) {
                    merchantUser.setMerUserName("G" + sysUserstb.getMobiltel());
                } else {
                    merchantUser.setMerUserName(sysUserstb.getLoginid());//用户昵称、登录名，新平台merCode +"U"
                }
            } else {
                merchantUser.setMerUserName("G" + sysUserstb.getMobiltel());
            }
            merchantUser.setMerUserPwd(sysUserstb.getPassword());//登录密码
            merchantUser.setMerUserTelephone(sysUserstb.getTel());//固定电话
            merchantUser.setMerUserMobile(sysUserstb.getMobiltel());//移动电话
            merchantUser.setMerUserPro(sysUserstb.getProvinceid() == null ? "" : sysUserstb.getProvinceid().toString());//身份代码
            merchantUser.setMerUserCity(sysUserstb.getCityid() == null ? "" : sysUserstb.getCityid().toString()); //城市代码
            merchantUser.setMerUserAdds(sysUserstb.getAddress()); //地址
            if ("0".equals(sysUserstb.getSex())) {
                merchantUser.setMerUserSex(SexEnum.FEMALE.getCode());//性别
            } else if ("1".equals(sysUserstb.getSex())) {
                merchantUser.setMerUserSex(SexEnum.MALE.getCode());//性别
            } else if ("2".equals(sysUserstb.getSex())) {
                merchantUser.setMerUserSex(SexEnum.MALE.getCode());//性别
            }

            if (sysUserstb.getIdentityid() != null) {
                merchantUser.setMerUserIdentityNum(sysUserstb.getIdentityid()); //证件号码
            }

            if ("1000000001".equals(sysUserstb.getIdentitytype())) {//证件类型
                merchantUser.setMerUserIdentityType("0");
            } else if ("1000000002".equals(sysUserstb.getIdentitytype())) {
                merchantUser.setMerUserIdentityType("2");
            } else if ("1000000005".equals(sysUserstb.getIdentitytype())) {
                merchantUser.setMerUserIdentityType("1");
            } else {
                merchantUser.setMerUserIdentityType(sysUserstb.getIdentitytype());//证件类型  
            }
            merchantUser.setMerUserEmall(sysUserstb.getEmail()); //电子邮件
            //生日
            if (sysUserstb.getBirthday() != null) {
                merchantUser.setMerUserBirthday(DateUtils.stringtoDate(sysUserstb.getBirthday(), DateUtils.DATE_SMALL_STR));
            }
            merchantUser.setMerUserType("0"); //用户类型: 固定值为0 1.系统用户2:客户3:商户6:厦门APP用户
            merchantUser.setActivate(sysUserstb.getStatus()); //0：启用 1：禁用
            //当前使用的移动电话
            if (sysUserstb.getLastmobiltel() == null) {
                merchantUser.setMerUserMobile(sysUserstb.getMobiltel());
            } else {
                merchantUser.setMerUserMobile(sysUserstb.getLastmobiltel());
            }
            //所选业务所在城市
            merchantUser.setCityCode(sysUserstb.getYktcityid() == null ? "" : sysUserstb.getYktcityid().toString());

            if (sysUserstb.getRelname() == null) {
                merchantUser.setMerUserNickname(sysUserstb.getUsername()); //真是姓名
            } else {
                merchantUser.setMerUserNickname(sysUserstb.getRelname()); //真是姓名
            }
            merchantUser.setCreateDate(DateUtils.stringtoDate(sysUserstb.getRegisttime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));//注册时间
            merchantUser.setUpdateDate(DateUtils.stringtoDate(sysUserstb.getLastedittime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));//最后修改时间
            merchantUser.setMerUserFlg("1000");
            merchantUser.setMerUserState("1");
            merchantUser.setSource(SourceEnum.TRANSFER.getCode());//默认迁移用户
            merchantUserMapper.addMerchantUser(merchantUser);

            //***********************************   3：生成用户绑定卡片信息表数据MER_USER_CARD_BD  **************************//
            ScoreuserCard scoreuserCard = scoreuserCardMapper.findScoreuserCardById(userid);
            MerUserCardBd merUserCardBd = new MerUserCardBd();//scoreuserCard
            if (scoreuserCard != null) {
                merUserCardBd.setCardOuterCode(scoreuserCard.getCardno());//一卡通面号
                if (merchantUser.getMerUserName() != null) {
                    merUserCardBd.setMerUserName(merchantUser.getMerUserName());//默认用户名
                }
                merUserCardBd.setYktCode(scoreuserCard.getYktid());//一卡通id
                //绑定时间
                if (scoreuserCard.getBoundtime() != null) {
                    merUserCardBd.setCreateDate(DateUtils.stringtoDate(scoreuserCard.getBoundtime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                }
                //解绑时间
                if (scoreuserCard.getUnboundtime() != null) {
                    merUserCardBd.setUnbundlingDate(DateUtils.stringtoDate(scoreuserCard.getUnboundtime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                }
                //绑定状态 0绑定1解绑
                merUserCardBd.setBundLingType(scoreuserCard.getStatus());
                merUserCardBd.setCardCode(scoreuserCard.getCardnoinner());//卡内号 
                
                merUserCardBdMapper.addMerUserCardBd(merUserCardBd);
            }
            //***********************************   步骤4：生成用户信息扩展表MERCHANT_USER_EXTEND  **************************//
            MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
            merchantUserExtend.setOldUserId(sysUserstb.getUserid());
            merchantUserExtend.setUserCode(merUserCode);//用户编号
            //用户类型 个人用户：0  集团用户：1  网点用户：2
            merchantUserExtend.setOldUserType("0");
            //用户注册来源 具体参考：usersourcetb 表
            merchantUserExtend.setUserType1("0");
            //通过活动注册的用户的活动ID
            merchantUserExtend.setActivateId(sysUserstb.getActiveid());//通过活动注册的用户的活动ID
            merchantUserExtend.setWechatId(sysUserstb.getWechatid()); //微信号
            merchantUserExtend.setWechatIcon(sysUserstb.getWechaticon()); //微信头像
            merchantUserExtend.setOccupation(sysUserstb.getOccupation()); //职业
            if (sysUserstb.getUsertype1() != null) {
                merchantUserExtend.setUserType1(String.valueOf(sysUserstb.getUsertype1()));//扩展字段   
            }

            merchantUserExtendMapper.addMerchantUserExtend(merchantUserExtend);

            //***********************************   5：生成主账户表记录ACCOUNT  **************************//
            Account account = new Account();
            String seqId = accountMapper.getSequenceNextId();
            String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;//按规则生成账户编号
            account.setAccountCode(preId);//账户编号
            account.setFundType("0"); // 0：资金 1：授信
            account.setCustomerType("0");//0：个人 1：商户
            account.setCustomerNo(merUserCode);//商户编号
            accountMapper.addAccount(account);

            //***********************************   6：生成资金授信账户表ACCOUNT_FUND记录  **************************//
            AccountFund accountFund = new AccountFund();
            accountFund.setAccountCode(account.getAccountCode());//主账户编号
            accountFund.setFundAccountCode("F" + "0" + account.getAccountCode());//资金授信账户编号
            accountFund.setFundType("0");//资金类别 0：资金 1：授信
            accountFund.setSumTotalAmount(new BigDecimal(0));//累计总金额(单位：分。默认为0。该账户历次充值累计相加的总金额。)
            accountFund.setTotalBalance(new BigDecimal(0));//总余额(单位：分。默认为0。表示归属于这个资金账户的所有金额，包括冻结资金。)
            accountFund.setAvailableBalance(new BigDecimal(0));//可用金额(单位：分。默认为0。表示该账户中可以自由使用的金额)
            accountFund.setFrozenAmount(new BigDecimal(0));//冻结金额(单位：分。默认为0。表示这部分金额虽然归属于该账户，但是当前状态下不能自由使用比如在进行公交卡充值流程中，相应的充值费用被冻结。)
            accountFundMapper.addAccountFund(accountFund);

            //***********************************   7：生成资金账户风控表ACCOUNT_CONTROL数据  **************************//
            AccountControl accountControl = new AccountControl();
            accountControl.setFundAccountCode(accountFund.getFundAccountCode());//资金授信账户编号

            //查询默认的风控值
            AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault("99");//个人
            //1资金风控
            accountControl.setFundAccountCode(accountFund.getFundAccountCode());//资金授信账户编号
            accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());

            accountControlMapper.addAccountControl(accountControl);

            //***********************************   8：生成优惠券主账户数据ACCOUNT_ COUPON  **************************//
            AccountCoupon accountCoupon = new AccountCoupon();
            // String acseqId = accountService.getSequenceNextId();
            String acpreId = "C" + account.getAccountCode();

            accountCoupon.setAccountCode(account.getAccountCode());//主账户编号
            accountCoupon.setCouponAccountCode(acpreId);//优惠券主账户编号
            //查询优惠券使用信息统计
            Tlcouponinfo tlcouponinfoCount = tlcouponinfoMapper.findTlcouponinfoCountById(userid);
            accountCoupon.setCouponAccountCount(tlcouponinfoCount.getCouponAccountCount());//优惠券总数
            accountCoupon.setUseAccountCount(tlcouponinfoCount.getUseAccountCount()); //已使用优惠券
            accountCoupon.setSurplusAccountCount(tlcouponinfoCount.getSurplusAccountCount()); //剩余优惠券
            accountCouponMapper.addAccountCoupon(accountCoupon);

            //***********************************   9：生成优惠券子账户表数据TLCOUPONINFO  **************************//
            TlcouponinfoTarget tlcouponinfoTarget = new TlcouponinfoTarget();//findTlcouponinfo
            for (Tlcouponinfo tlcouponinfo : findTlcouponinfo) {

                tlcouponinfoTarget.setCouponAccountcode(acpreId);
                tlcouponinfoTarget.setUserCode(merUserCode);
                tlcouponinfoTarget.setActiveid(tlcouponinfo.getActiveid());
                tlcouponinfoTarget.setAmt(tlcouponinfo.getAmt());
                tlcouponinfoTarget.setBorndate(tlcouponinfo.getBorndate());
                tlcouponinfoTarget.setBorntime(tlcouponinfo.getBorntime());
                tlcouponinfoTarget.setCorderid(tlcouponinfo.getCorderid());
                tlcouponinfoTarget.setCorderstates(tlcouponinfo.getCorderstates());
                tlcouponinfoTarget.setCouponcode(tlcouponinfo.getCouponcode());
                tlcouponinfoTarget.setFinishdate(tlcouponinfo.getFinishdate());
                tlcouponinfoTarget.setFinishtime(tlcouponinfo.getFinishtime());
                tlcouponinfoTarget.setOverduestatus(tlcouponinfo.getOverduestatus());
                tlcouponinfoTarget.setOverduetime(tlcouponinfo.getOverduetime());
                //tlcouponinfoTarget.setMerCode(tlcouponinfo.getMchnitid());
                tlcouponinfoTarget.setRemarks(tlcouponinfo.getRemarks());
                tlcouponinfoTarget.setReservecha(tlcouponinfo.getReservecha());
                tlcouponinfoTarget.setReservedate(tlcouponinfo.getReservedate());
                tlcouponinfoTarget.setReservendate(tlcouponinfo.getReservendate());
                tlcouponinfoTarget.setReserventime(tlcouponinfo.getReserventime());
                tlcouponinfoTarget.setReservenum(tlcouponinfo.getReservenum());
                tlcouponinfoTarget.setReservevar(tlcouponinfo.getReservevar());
                tlcouponinfoTarget.setSorderid(tlcouponinfo.getSorderid());
                tlcouponinfoTarget.setSorderstates(tlcouponinfo.getSorderstates());
                tlcouponinfoTarget.setStatus(tlcouponinfo.getStatus());
                tlcouponinfoTarget.setUpdatedate(tlcouponinfo.getUpdatedate());
                tlcouponinfoTarget.setUpdatetime(tlcouponinfo.getUpdatetime());
                tlcouponinfoTarget.setUpdateuser(tlcouponinfo.getUpdateuser());
                tlcouponinfoTargetMapper.addTlcouponinfoTarget(tlcouponinfoTarget);
            }

            //***********************************   10：生成积分主账户表数据tlposPoints  **************************//
            //            Tlpospoints tlpospoints = new Tlpospoints();
            //            for(Tlpospointstnx tlpospointstnx :tlpospointstnxs ){
            //            	tlpospoints.setAccountCode(account.getAccountCode());
            //            	tlpospoints.setPonitsAccountCode("P"+account.getAccountCode());
            //            	tlpospoints.setTotalPoints(tlpospointstnx.getPointvalue());
            //            	tlpospoints.setLastChangePoints(tlpospointstnx.getPointvalue());
            //            	tlpospoints.setBeforeChangePoints(tlpospointstnx.getPointvalue());
            //            	tlposPointsMapper.addTlpospoints(tlpospoints);
            //            	//***********************************   11：生成积分流水表数据tlposPointsTnx  **************************//
            //            	TlpospointstnxTarget tlpospointstnxTarget = new TlpospointstnxTarget();
            //            	tlpospointstnxTarget.setPonitsAccountCode("P"+account.getAccountCode());
            //            	tlpospointstnxTarget.setApplytime(tlpospointstnx.getApplytime());
            //            	tlpospointstnxTarget.setOrderid(tlpospointstnx.getOrderid());
            //            	tlpospointstnxTarget.setPointspara(tlpospointstnx.getPointspara());
            //            	tlpospointstnxTarget.setPointtxn(tlpospointstnx.getPointtxn());
            //            	tlpospointstnxTarget.setPointvalue(tlpospointstnx.getPointvalue());
            //            	tlposPointsTnxMapper.addTlpospointstnxTarget(tlpospointstnxTarget);
            //            }

            // 更新迁移标志
            sysuserstbMapper.updateUserTransFlag(userid);

            //***********************************   12：生成积分规则数据biposPoints  **************************//
            //直接通过脚本直接导入数据

        return response;
    }

    //生成商户用户号
    @Override
    @Transactional(readOnly = true)
    public String generateMerUserCode(UserClassifyEnum userClassify) {
        StringBuffer sb = new StringBuffer();
        // 是否测试账户(1位):1-正式商户用户; 2--测试商户用户; 3--正式个人用户;  4--个人测试用户
        sb.append(userClassify.getCode());
        // 4位随机数
        int number = new Random().nextInt(9999) + 1;
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        String randomNum = formatter.format(number);
        sb.append(randomNum);
        // 数据库12位sequence
        String seq = merchantUserMapper.getMerUserCodeSeq();
        sb.append(seq);
        return sb.toString();
    }

}
