package com.dodopal.transfer.business.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PosStatusEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SexEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.AccountControlMapper;
import com.dodopal.transfer.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfer.business.dao.AccountFundMapper;
import com.dodopal.transfer.business.dao.AccountMapper;
import com.dodopal.transfer.business.dao.BicostdisdetailMapper;
import com.dodopal.transfer.business.dao.BimchnitfeetbMapper;
import com.dodopal.transfer.business.dao.BimchntinfotbMapper;
import com.dodopal.transfer.business.dao.BiposidsaletbMapper;
import com.dodopal.transfer.business.dao.BiposinfotbMapper;
import com.dodopal.transfer.business.dao.BiposinuserstbMapper;
import com.dodopal.transfer.business.dao.BisalediscountMapper;
import com.dodopal.transfer.business.dao.LogTransferMapper;
import com.dodopal.transfer.business.dao.MchnitlimitinfotbMapper;
import com.dodopal.transfer.business.dao.MchntposinfoMapper;
import com.dodopal.transfer.business.dao.MerAutoAmtMapper;
import com.dodopal.transfer.business.dao.MerBusRateMapper;
import com.dodopal.transfer.business.dao.MerDdpInfoMapper;
import com.dodopal.transfer.business.dao.MerKeyTypeMapper;
import com.dodopal.transfer.business.dao.MerRateSupplementMapper;
import com.dodopal.transfer.business.dao.MerchantExtendMapper;
import com.dodopal.transfer.business.dao.MerchantMapper;
import com.dodopal.transfer.business.dao.MerchantTranDiscountMapper;
import com.dodopal.transfer.business.dao.MerchantUserExtendMapper;
import com.dodopal.transfer.business.dao.MerchantUserMapper;
import com.dodopal.transfer.business.dao.PosMapper;
import com.dodopal.transfer.business.dao.PosMerTbMapper;
import com.dodopal.transfer.business.dao.ProxyLimitInfoTbMapper;
import com.dodopal.transfer.business.dao.ProxyinfotbMapper;
import com.dodopal.transfer.business.dao.ProxypostbMapper;
import com.dodopal.transfer.business.dao.ProxyuserinfotbMapper;
import com.dodopal.transfer.business.dao.SysuserstbMapper;
import com.dodopal.transfer.business.dao.TranDiscountDateMapper;
import com.dodopal.transfer.business.dao.TranDiscountMapper;
import com.dodopal.transfer.business.dao.TranDiscountTimeMapper;
import com.dodopal.transfer.business.dao.TransferMerchantMapper;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.model.old.Bimchnitfeetb;
import com.dodopal.transfer.business.model.old.Bimchntinfotb;
import com.dodopal.transfer.business.model.old.Biposidsaletb;
import com.dodopal.transfer.business.model.old.Biposinfotb;
import com.dodopal.transfer.business.model.old.Biposinuserstb;
import com.dodopal.transfer.business.model.old.Bisalediscount;
import com.dodopal.transfer.business.model.old.Mchnitlimitinfotb;
import com.dodopal.transfer.business.model.old.Proxyinfotb;
import com.dodopal.transfer.business.model.old.Proxylimitinfotb;
import com.dodopal.transfer.business.model.old.Proxypostb;
import com.dodopal.transfer.business.model.old.Proxyuserinfotb;
import com.dodopal.transfer.business.model.old.Sysuserstb;
import com.dodopal.transfer.business.model.target.Account;
import com.dodopal.transfer.business.model.target.AccountControl;
import com.dodopal.transfer.business.model.target.AccountControllerDefault;
import com.dodopal.transfer.business.model.target.AccountFund;
import com.dodopal.transfer.business.model.target.MerAutoAmt;
import com.dodopal.transfer.business.model.target.MerBusRate;
import com.dodopal.transfer.business.model.target.MerDdpInfo;
import com.dodopal.transfer.business.model.target.MerKeyType;
import com.dodopal.transfer.business.model.target.MerRateSupplement;
import com.dodopal.transfer.business.model.target.Merchant;
import com.dodopal.transfer.business.model.target.MerchantExtend;
import com.dodopal.transfer.business.model.target.MerchantTranDiscount;
import com.dodopal.transfer.business.model.target.MerchantUser;
import com.dodopal.transfer.business.model.target.MerchantUserExtend;
import com.dodopal.transfer.business.model.target.Pos;
import com.dodopal.transfer.business.model.target.PosMerTb;
import com.dodopal.transfer.business.model.target.TranDiscount;
import com.dodopal.transfer.business.model.target.TransferMerchant;
import com.dodopal.transfer.business.service.MerchantService;
import com.dodopal.transfer.business.service.MerchantUserService;

@Service
public class MerchantServiceImpl implements MerchantService {
    private Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
    @Autowired
     private  MerchantMapper mapper;
    @Autowired
    BimchntinfotbMapper bimMapper;
    @Autowired
    MerchantUserMapper maUserpper;
    @Autowired
    MerAutoAmtMapper merAutoAmtMapper;
    @Autowired
    MerKeyTypeMapper merKeyTypeMapper;
    @Autowired
    MerchantExtendMapper merchantExtendMapper;
    @Autowired
    MerBusRateMapper merBusRateMapper;
    @Autowired
    MerchantTranDiscountMapper mtdMapper;
    @Autowired
    BiposidsaletbMapper biposidsaletbMapper;
    @Autowired
    BiposinuserstbMapper biposinuserstbMapper;
    @Autowired
    BicostdisdetailMapper bicostdisdetailMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    AccountFundMapper accountFundMapper;
    @Autowired
    MchnitlimitinfotbMapper mchnitlimitinfotbMapper;
    @Autowired
    BisalediscountMapper bisalediscountMapper;
    @Autowired
    BimchnitfeetbMapper bimchnitfeetbMapper;
    @Autowired
    TranDiscountDateMapper tranDiscountDateMapper;
    @Autowired
    TranDiscountTimeMapper tranDiscountTimeMapper;
    @Autowired
    MchntposinfoMapper mchntposinfoMapper;
    @Autowired
    MerDdpInfoMapper merDdpInfoMapper;
    @Autowired
    SysuserstbMapper sysuserstbMapper;
    @Autowired
    TransferMerchantMapper transferMerchantMapper;
    @Autowired
    PosMapper posMapper;
    @Autowired
    private MerchantUserService merchantUserService;
    @Autowired
    private ProxyuserinfotbMapper proxyuserinfotbMapper;
    @Autowired
    private MerchantUserExtendMapper merchantUserExtendMapper;
    @Autowired
    ProxypostbMapper proxypostbMapper;
    @Autowired
    PosMerTbMapper posMerTbMapper;
    @Autowired
    BiposinfotbMapper biposinfotbMapper;
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
    private ProxyinfotbMapper proxyinfotbMapper;
    @Autowired
    private LogTransferMapper logTransferMapper;
    
    private static final String YKT_CODE ="100000"; //北京一卡通ID
    
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
        String seq = mapper.getMerCodeSeq();
        sb.append(seq);
        return sb.toString();
    }

    @Override
    @Transactional
    public DodopalResponse<String> insertMerchant(Bimchntinfotb bim, String batchId) {
        logger.info("******************** start insart merchant ****************************");
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        String merCode = generateMerCode(MerClassifyEnum.OFFICIAL.getCode()); //商户编号
        response.setResponseEntity(merCode);
            //***********************************1.生成连锁商户信息************************//
            Merchant merchant = new Merchant();
            merchant.setMerName(bim.getMchnitname());//商户名称
            merchant.setMerLinkUser(bim.getLinkman());//联系人
            if (DDPStringUtil.isMobile(DDPStringUtil.trim(bim.getTel()))) {
                merchant.setMerLinkUserMobile(DDPStringUtil.trim(bim.getTel())); //商户联系人
            } else {
                merchant.setMerTelephone(DDPStringUtil.trim(bim.getTel()));//固定电话
            }
            merchant.setMerFax(bim.getFax());//传真号码
            merchant.setMerAdds(bim.getAddress());//联系地址
            merchant.setMerZip(bim.getZipcode());//邮编
            if(bim.getAreaid()!=null){
                merchant.setMerArea(String.valueOf(bim.getAreaid()));//商户所属地区 
            }else{
                merchant.setMerArea("");//商户所属地区
            }
           
            merchant.setMerBankAccount(bim.getBankacc());//开户行账号
            merchant.setMerBankName(bim.getBankaccname());//开户行名称
            merchant.setMerBusinessScopeId(bim.getTradeid());//行业代码
            if(bim.getParentmchnitid()!=null){
                merchant.setMerPro(bim.getProvinceid().toString());//商户省份
            }else{
                merchant.setMerPro("1000");//商户省份 北京
            }
            if(bim.getCityid()!=null){
            merchant.setMerCity(bim.getCityid().toString());//商户城市
            }else{
                merchant.setMerCity("1110");//商户城市  
            }
            merchant.setMerCode(merCode);//商户编号
            merchant.setMerType(MerTypeEnum.CHAIN.getCode());//商户类型
            merchant.setMerState(MerStateEnum.THROUGH.getCode());//审核状态
            merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
            merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
            merchant.setSource(SourceEnum.TRANSFER.getCode());//来源
            merchant.setActivate(ActivateEnum.ENABLE.getCode());//启用
            merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除
            mapper.addMerchant(merchant);
            //***********************************1.1.生成标志信息表************************//
            MerchantExtend merchantExtend = new MerchantExtend();
            merchantExtend.setMerCode(merCode);//新生成的商户CODE
            merchantExtend.setOldMerchantId(bim.getMchnitid());//老商户表的商户ID
            merchantExtend.setOldMerchantType("3");//标识为老商户表的数据，集团：1，网点：2，商户：3

            merchantExtendMapper.addMerchantExtend(merchantExtend);
            //***********************************2.生成连锁商户用户信息************************//
            Sysuserstb sysuserstb = sysuserstbMapper.findSysUserstbByMchnitid(bim.getMchnitid());
            MerchantUser merchantUser = new MerchantUser();
         
            /*boolean merUserCodeFlag=true;
            if(sysuserstb.getLoginid()!=null){
                if (sysuserstb.getLoginid().indexOf("@") >= 0 ) {
                    merUserCodeFlag = merchantUserService.checkExist("U" + merCode);
                } else{
                    merUserCodeFlag = merchantUserService.checkExist(sysuserstb.getLoginid());
                }
            }else{
                merUserCodeFlag = merchantUserService.checkExist("U" + merCode);
            }
            if (merUserCodeFlag) {
                logger.error("商户用户MerUserCode：" + "U" + merCode + "对应的用户存在。");
               throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            }*/
                if(sysuserstb!=null){
                    merchantUser.setMerCode(merCode);//所属商户编号
                    if (sysuserstb.getRelname() != null) {
                        merchantUser.setMerUserNickname(sysuserstb.getRelname());
                    } else {
                        merchantUser.setMerUserNickname(sysuserstb.getUsername());
                    }
                    if(sysuserstb.getLoginid()!=null){
                        if (sysuserstb.getLoginid().indexOf("@") >= 0 ) {
                            merchantUser.setMerUserName("U" + merCode);
                        } else{
                            merchantUser.setMerUserName(sysuserstb.getLoginid());//用户昵称、登录名，新平台merCode +"U"
                        }
                    }else {
                        merchantUser.setMerUserName("U" + merCode);
                    }

                    merchantUser.setMerUserPwd(sysuserstb.getPassword());//登录密码
                    merchantUser.setMerUserTelephone(sysuserstb.getTel());//固定电话
                    if (sysuserstb.getMobiltel() != null) {
                        merchantUser.setMerUserTelephone(sysuserstb.getMobiltel());//移动电话 
                    }
                    if (sysuserstb.getProvinceid() != null) {
                        merchantUser.setMerUserPro(sysuserstb.getProvinceid().toString());//省份代码  
                    }else{
                        merchantUser.setMerUserPro("1000");
                    }
                    if (sysuserstb.getCityid() != null) {
                        merchantUser.setMerUserCity(sysuserstb.getCityid().toString());//城市代码
                    }else{
                        merchantUser.setMerUserCity("1110");
                    }
              
                    
                    merchantUser.setMerUserAdds(sysuserstb.getAddress());//地址
                    if ("0".equals(sysuserstb.getSex())) {
                        merchantUser.setMerUserSex(SexEnum.FEMALE.getCode());//性别
                    } else if ("1".equals(sysuserstb.getSex())) {
                        merchantUser.setMerUserSex(SexEnum.MALE.getCode());//性别
                    } else if ("2".equals(sysuserstb.getSex())) {
                        merchantUser.setMerUserSex(SexEnum.MALE.getCode());//性别
                    }

                    merchantUser.setMerUserIdentityNum(sysuserstb.getIdentityid());//证件号码
                    if ("1000000001".equals(sysuserstb.getIdentitytype())) {//证件类型
                        merchantUser.setMerUserIdentityType("0");
                    } else if ("1000000002".equals(sysuserstb.getIdentitytype())) {
                        merchantUser.setMerUserIdentityType("2");
                    } else if ("1000000005".equals(sysuserstb.getIdentitytype())) {
                        merchantUser.setMerUserIdentityType("1");
                    } else {
                        merchantUser.setMerUserIdentityType(sysuserstb.getIdentitytype());//证件类型  
                    }
                    merchantUser.setMerUserEmall(sysuserstb.getEmail());//电子邮件
                    merchantUser.setCreateDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FULL_STR));//注册时间
                    merchantUser.setUpdateDate(DateUtils.stringtoDate(sysuserstb.getLastedittime(), DateUtils.DATE_FULL_STR));//最后修改时间
                    merchantUser.setMerUserType(sysuserstb.getUsertype().toString());//用户类型
                    if (sysuserstb.getLastmobiltel() != null) {//当前使用的移动电话
                        merchantUser.setMerUserMobile(sysuserstb.getLastmobiltel());
                    } else {
                        merchantUser.setMerUserMobile("0000000000");
                    }
                    if (sysuserstb.getYktcityid() != null) {
                        merchantUser.setCityCode(sysuserstb.getYktcityid().toString());//城市code
                    } else {
                        merchantUser.setCityCode("1110");//城市code
                    }
                    merchantUser.setBirthday(DateUtils.dateToString(DateUtils.stringtoDate(sysuserstb.getBirthday(), DateUtils.DATE_SMALL_STR), DateUtils.DATE_SMALL_STR));//生日
                    merchantUser.setMerUserNickname(sysuserstb.getRelname());//真实姓名
                    merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());//用户标志
                    merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
                    merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
                    merchantUser.setUserCode(merchantUserService.generateMerUserCode(UserClassifyEnum.MERCHANT));//用户编号
                    merchantUser.setSource(SourceEnum.TRANSFER.getCode());//用户注册来源
                    if (sysuserstb.getStatus() != null) {
                        merchantUser.setActivate(sysuserstb.getStatus());//是否启用
                    } else {
                        merchantUser.setActivate(ActivateEnum.ENABLE.getCode());//用户启用
                    }

                }else{
                    merchantUser.setMerCode(merCode);//所属商户编号
                    merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());//用户标志
                    merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
                    merchantUser.setMerUserName("U" + merCode);//商户用户登录账号
                    merchantUser.setMerUserPwd("123456");//商户用户登录密码，默认14e1b600b1fd579f47433b88e8d85291
                    merchantUser.setMerUserAdds(bim.getAddress());//用户地址
                    merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
                    merchantUser.setUserCode(merchantUserService.generateMerUserCode(UserClassifyEnum.MERCHANT));//用户编号
                    merchantUser.setSource(SourceEnum.TRANSFER.getCode());//用户注册来源
                    merchantUser.setMerUserPro(bim.getProvinceid().toString());//用户所属省份
                    merchantUser.setMerUserCity(bim.getCityid().toString());//用户所属城市
                    merchantUser.setCityCode("1110");//默认开通城市编号 北京
                    merchantUser.setActivate(ActivateEnum.ENABLE.getCode());//用户启用
                    if (bim.getTel() != null) {
                        if (DDPStringUtil.isMobile(DDPStringUtil.trim(bim.getTel()))) {
                            merchantUser.setMerUserMobile(DDPStringUtil.trim(bim.getTel()));//商户用户手机号码
                        } else {
                            merchantUser.setMerUserMobile("0000000");//商户随意填写
                            merchantUser.setMerUserTelephone(DDPStringUtil.trim(bim.getTel()));//商户用户固定电话
                        }
                    } else {
                        merchantUser.setMerUserMobile("0000000");//商户随意填写
                    } 
                }
                

                maUserpper.addMerchantUser(merchantUser);

                MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
                merchantUserExtend.setUserCode(merchantUser.getUserCode());//新生成的商户CODE
                merchantUserExtend.setOldUserId(sysuserstb.getUserid());//老商户表的商户ID
                merchantUserExtend.setOldUserType("3");//标识为老商户表的数据
                merchantUserExtendMapper.addMerchantUserExtend(merchantUserExtend);
            //***********************************3.生成商户补充信息表MER_DDP_INFO************************//
            Bimchnitfeetb bimchnitfeetbInfo = bimchnitfeetbMapper.findBimchnitfeetbByMchId(bim.getMchnitid());
            MerDdpInfo merDdpInfo = new MerDdpInfo();
            merDdpInfo.setMerCode(merCode);//商户编号
            if (bimchnitfeetbInfo != null) {
                if ("3".equals(bimchnitfeetbInfo.getFeetype())) {
                    merDdpInfo.setSettlementType("0");//结算类型
                    merDdpInfo.setSettlementThreshold(new BigDecimal(0));//结算参数
                } else {
                    merDdpInfo.setSettlementType(bimchnitfeetbInfo.getFeetype());//结算类型
                    merDdpInfo.setSettlementThreshold(bimchnitfeetbInfo.getSetpara());//结算参数
                }
            }
            merDdpInfoMapper.addMerDdpInfo(merDdpInfo);
            //***********************************4.生成商户密钥分配表数据MER_KEY_TYPE************************//
            //             MerKeyType merKeyType = new MerKeyType();
            //             merKeyType.setMerCode(merCode);//商户编号
            //             merKeyType.setMerKeyType(MerKeyTypeEnum.MD5.getCode());//商户密钥类型
            //             merKeyTypeMapper.addMerKeyType(merKeyType);
            //***********************************5.生成商户业务类型信息表mer_rate_supplement************************//
            MerRateSupplement merRateSupplement = new MerRateSupplement();
            merRateSupplement.setMerCode(merCode);
            merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
            merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
            MerRateSupplement merRateSupplement2 = new MerRateSupplement();
            merRateSupplement2.setMerCode(merCode);
            merRateSupplement2.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
            merRateSupplementMapper.addMerRateSupplement(merRateSupplement2);
            //***********************************6.生成商户业务费率MER_BUS_RATE************************//
            List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
            MerBusRate merBusRate = new MerBusRate();
            merBusRate.setMerCode(merCode);//商户编号
            merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务
            merBusRate.setProviderCode(YKT_CODE);//所在业务提供商编码
            merBusRate.setRate(new BigDecimal(0));//数值
            merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型
            merBusRateList.add(merBusRate);
            MerBusRate merBusRate2 = new MerBusRate();
            merBusRate2.setMerCode(merCode);//商户编号
            merBusRate2.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());//消费业务
            merBusRate2.setProviderCode(YKT_CODE);//所在业务提供商编码
            merBusRate2.setRate(new BigDecimal(0));//数值
            merBusRate2.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型
            merBusRateList.add(merBusRate2);
            merBusRateMapper.addMerBusRateBatch(merBusRateList);
            //***********************************7.生成连锁商户主账户信息************************//
            Account account = new Account();
            String seqId = accountMapper.getSequenceNextId();
            String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;//按规则生成账户编号
            account.setAccountCode(preId);//账户编号
            account.setFundType(FundTypeEnum.AUTHORIZED.getCode()); // 0：资金 1：授信
            account.setCustomerType("1");//0：个人 1：商户
            account.setCustomerNo(merCode);//在MERCHANT_USER表中自动生成的USER_CODE值
            accountMapper.addAccount(account);
            //***********************************8.生成连锁商户资金账户记录************************//
            TransferMerchant transferMerchant = transferMerchantMapper.findTransferMerchant(bim.getMchnitid());
            Mchnitlimitinfotb mlimtinfo = mchnitlimitinfotbMapper.findMchlimitByMchId(bim.getMchnitid());
            AccountFund accountFund = new AccountFund();
            accountFund.setAccountCode(preId);//主账户编号A2016030716454610001
            accountFund.setFundAccountCode("F" + "0" + preId);//资金账户编号
            accountFund.setFundType(FundTypeEnum.FUND.getCode());//资金类别 0：资金 
            accountFund.setFrozenAmount(new BigDecimal(0));//冻结金额
            accountFund.setState("0");//状态默认为正常
//            if (FundTypeEnum.FUND.getCode().equals(transferMerchant.getFundType())) {
//                if (mlimtinfo != null) {
//                    if (mlimtinfo.getAmtlimit() != null) {
//                        accountFund.setSumTotalAmount(mlimtinfo.getAmtlimit());//购买总额度
//                        accountFund.setAvailableBalance(mlimtinfo.getSurpluslimit());//可用余额
//                        accountFund.setTotalBalance(mlimtinfo.getSurpluslimit());//剩余额度
//                    } else {
//                        accountFund.setAvailableBalance(new BigDecimal(0));//可用余额
//                        accountFund.setSumTotalAmount(new BigDecimal(0));//购买总额度
//                        accountFund.setTotalBalance(new BigDecimal(0));//剩余额度
//                    }
//                } else {
//                    accountFund.setAvailableBalance(new BigDecimal(0));//可用余额
//                    accountFund.setSumTotalAmount(new BigDecimal(0));//购买总额度
//                    accountFund.setTotalBalance(new BigDecimal(0));//剩余额度
//                }
//            } else {
//                accountFund.setAvailableBalance(new BigDecimal(0));//可用余额
//                accountFund.setSumTotalAmount(new BigDecimal(0));//购买总额度
//                accountFund.setTotalBalance(new BigDecimal(0));//剩余额度
//            }
            // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
            accountFund.setAvailableBalance(new BigDecimal(0));//可用余额
            accountFund.setSumTotalAmount(new BigDecimal(0));//购买总额度
            accountFund.setTotalBalance(new BigDecimal(0));//剩余额度
            accountFundMapper.addAccountFund(accountFund);

            AccountFund accountFundSA = new AccountFund();
            accountFundSA.setAccountCode(preId);//主账户编号A2016030716454610001
            accountFundSA.setFundAccountCode("F" + "1" + preId);//授信账户编号
            accountFundSA.setFundType(FundTypeEnum.AUTHORIZED.getCode());//资金类别  1：授信
            accountFundSA.setFrozenAmount(new BigDecimal(0));//冻结金额
            accountFundSA.setState("0");//状态默认为正常
            //授信账号
//            if (FundTypeEnum.AUTHORIZED.getCode().equals(transferMerchant.getFundType())) {
//                if (mlimtinfo != null) {
//                    if (mlimtinfo.getAmtlimit() != null) {
//                        accountFundSA.setSumTotalAmount(mlimtinfo.getAmtlimit());//购买总额度
//                        accountFundSA.setAvailableBalance(mlimtinfo.getSurpluslimit());//可用余额
//                        accountFundSA.setTotalBalance(mlimtinfo.getSurpluslimit());//剩余额度
//                    } else {
//                        accountFundSA.setSumTotalAmount(new BigDecimal(0));//购买总额度
//                        accountFundSA.setAvailableBalance(new BigDecimal(0));//可用余额
//                        accountFundSA.setTotalBalance(new BigDecimal(0));//购买总额度 
//                    }
//
//                } else {
//                    accountFundSA.setSumTotalAmount(new BigDecimal(0));//购买总额度
//                    accountFundSA.setAvailableBalance(new BigDecimal(0));//可用余额
//                    accountFundSA.setTotalBalance(new BigDecimal(0));//购买总额度 
//                }
//            } else {
//                accountFundSA.setSumTotalAmount(new BigDecimal(0));//购买总额度
//                accountFundSA.setAvailableBalance(new BigDecimal(0));//可用余额
//                accountFundSA.setTotalBalance(new BigDecimal(0));//购买总额度 
//            }
            // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
            accountFundSA.setSumTotalAmount(new BigDecimal(0));//购买总额度
            accountFundSA.setAvailableBalance(new BigDecimal(0));//可用余额
            accountFundSA.setTotalBalance(new BigDecimal(0));//购买总额度 
            accountFundMapper.addAccountFund(accountFundSA);
            //***********************************8.1生成连锁商户资金风控记录************************//
            //查询默认的风控值
            AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault(MerTypeEnum.CHAIN.getCode());//商户类型
            AccountControl accountControl = new AccountControl();
            accountControl.setFundAccountCode("F" + "1" + preId);//授信账户编号
            accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
            accountControlMapper.addAccountControl(accountControl);
            AccountControl accountControlSA = new AccountControl();
            accountControlSA.setFundAccountCode("F" + "0" + preId);//资金账户编号
            accountControlSA.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControlSA.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControlSA.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControlSA.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControlSA.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControlSA.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControlSA.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
            accountControlMapper.addAccountControl(accountControlSA);
            //***********************************9.生成商户折扣记录*********************************//
            //***********************************9.1生成交易折扣日期表*********************************//
            //             List<Bisalediscount> bisalediList = bisalediscountMapper.findBisalediscountById(bim.getMchnitid());
            //             TranDiscountDate tDisDate = new TranDiscountDate();
            //             TranDiscountTime tDisTime = new TranDiscountTime();
            //             if(bisalediList.size()!=0){
            //                 for(Bisalediscount bisalediscount : bisalediList){
            //                     tDisDate.setMerCode(merCode);//商户号
            //                     Date dt1 = DateUtils.stringtoDate(bisalediscount.getStartdate(),DateUtils.DATE_FORMAT_YYMMDD_STR);
            //                     boolean flag = dt1.before(nowdate);//是否小于当前时间
            //                     if(flag){
            //                       tDisDate.setBeginDate(nowdate);//折扣开始日期
            //                       tDisDate.setEndDate(nowdate);//折扣结束日期   
            //                     }else{
            //                       tDisDate.setBeginDate(DateUtils.stringtoDate(bisalediscount.getStartdate(),DateUtils.DATE_FORMAT_YYMMDD_STR));//折扣开始日期
            //                       tDisDate.setEndDate(DateUtils.stringtoDate(bisalediscount.getEnddate(),DateUtils.DATE_FORMAT_YYMMDD_STR));//折扣结束日期  
            //                     }
            //                     tranDiscountDateMapper.addTranDiscountDate(tDisDate);
            //***********************************9.2生成交易折扣日期TRAN_DISCOUNT_TIME表*********************************//
            //                 tDisTime.setMerCode(merCode);//商户号
            //                 tDisTime.setDateId(tDisDate.getId());//交易折扣日期表ID
            //                 tDisTime.setDiscountThreshold(new BigDecimal(0));//折扣阀值
            //                 Date dt2 = DateUtils.stringtoDate(bisalediscount.getStartdate(),DateUtils.DATE_FORMAT_YYMMDD_STR);
            //                 boolean flagTime = dt2.before(nowdate);//是否小于当前时间
            //                 if(flagTime){
            //                     tDisTime.setBeginTime(DateUtils.getCurrDate(DateUtils.DATE_FORMAT_HHMM_STR));//折扣开始时间
            //                     tDisTime.setEndTime(DateUtils.getCurrDate(DateUtils.DATE_FORMAT_HHMM_STR));//折扣结束时间
            //                   }else{
            //                       tDisTime.setBeginTime(DateUtils.dateToString(DateUtils.stringtoDate(bisalediscount.getStarttime(), DateUtils.DATE_HHMM_STR),DateUtils.DATE_FORMAT_HHMM_STR));//折扣开始时间
            //                       tDisTime.setEndTime(DateUtils.dateToString(DateUtils.stringtoDate(bisalediscount.getEndtime(), DateUtils.DATE_HHMM_STR),DateUtils.DATE_FORMAT_HHMM_STR));//折扣结束时间
            //                   }
            //                     tranDiscountTimeMapper.addTranDiscountTime(tDisTime);
            //   }
            //   }
            // 查出老系统商户折扣信息
//            List<Bisalediscount> discountList = bisalediscountMapper.findBisalediscountById(bim.getMchnitid());
//            Map<String, String> discountIdMap = new HashMap<String, String>(); // 老折扣id与新折扣id对应关系
//            if(CollectionUtils.isNotEmpty(discountList)) {
//            	for(Bisalediscount discountTemp : discountList) {
//            		TranDiscount tranDiscount = new TranDiscount();
//            		// 结束日期
//            		Date endDate = DateUtils.stringtoDate(discountTemp.getEnddate(),DateUtils.DATE_FORMAT_YYMMDD_STR);
//            		tranDiscount.setEndDate(endDate);
//            		// 开始日期
//            		Date beginDate = DateUtils.stringtoDate(discountTemp.getStartdate(),DateUtils.DATE_FORMAT_YYMMDD_STR);
//            		tranDiscount.setBeginDate(beginDate);
//            		// 星期
//            		tranDiscount.setWeek(discountTemp.getWeek());
//            		// 开始时间
//            		String startTime = discountTemp.getStarttime();
//            		tranDiscount.setBeginTime(startTime.substring(0, 2) + startTime.substring(2, 4));
//            		// 结束时间
//            		String endTime = discountTemp.getEndtime();
//            		tranDiscount.setEndTime(endTime.substring(0, 2) + endTime.substring(2, 4));
//            		// 用户折扣
//					BigDecimal discountThreshold = discountTemp.getSale();
//					if (discountThreshold != null) {
//						tranDiscount.setDiscountThreshold(discountThreshold.toString());
//					}
//            		// 结算折扣
//					BigDecimal setDiscount = discountTemp.getSetsale();
//					if (setDiscount != null) {
//						tranDiscount.setSetDiscount(setDiscount.toString());
//					}
//
//					int count = tranDiscountMapper.addTranDiscount(tranDiscount);
//					if (count > 0) {
//						discountIdMap.put(Long.toString(discountTemp.getSaleid()), tranDiscount.getId());
//					}
//            	}
//            }
            
            
            
            //***********************************10.根据连锁商户生成连锁加盟网点*****************************//
            try {
                //第一步： 根据商户查询下面有多少POS
                List<Biposinfotb> listBip = biposinfotbMapper.findBiposinfotbAll(bim.getMchnitid());
                if (CollectionUtils.isNotEmpty(listBip)) {
                    for (Biposinfotb biposinfotb : listBip) {
                        insertMerchantChinaJoin(biposinfotb, batchId, bim, merCode);
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
           
            //***********************************11.更新老系统表迁移记录*****************************//
            // add by lifeng 2016.3.15
//            int result = bimMapper.updateTransFlag(bim.getMchnitid());
//            if (result <= 0) {
//                throw new RuntimeException();
//            }
            // add by lifeng 2016.4.8 插入中间表
			int result = bimMapper.addBimchntinfotbExtend(bim.getMchnitid(), "0");
			if (result <= 0) {
				throw new RuntimeException();
			}

        logger.info("******************** end insart merchant ****************************");
        return response;
    }

    //****************************************************************连锁加盟网点*************************//
    @Override
    @Transactional
    public DodopalResponse<String> insertMerchantChinaJoin(Biposinfotb biposinfotb, String batchId, Bimchntinfotb bim, String merParentCode) {
        logger.info("******************** start insart merchantChindJoin ****************************");
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        String merCode = generateMerCode(MerClassifyEnum.OFFICIAL.getCode()); //商户编号
        //新增日志
        LogTransfer logTransfer = new LogTransfer();
        //***************************************************第二步查询biposinuserstb对应的用户*************************//
        Biposinuserstb biposinuserstb = biposinuserstbMapper.findBiposinuserstbByPosId(String.valueOf(biposinfotb.getPosid()));
		if (biposinuserstb == null) {
			return response;
		}
        Sysuserstb sysuserstb = sysuserstbMapper.findSysuserstb(String.valueOf(biposinuserstb.getUserid()));
        try {
            //***********************************1.生成连锁商户信息************************//
            Merchant merchant = new Merchant();
            merchant.setMerCode(merCode);//商户编号
            if (merParentCode != null) {
                merchant.setMerParentCode(merParentCode);//上级商户CODE  
            }
            merchant.setMerName(sysuserstb.getUsername());//商户名称
            merchant.setMerLinkUser(sysuserstb.getRelname());//联系人
            merchant.setMerTelephone(sysuserstb.getTel());//固定电话
            merchant.setMerLinkUserMobile(sysuserstb.getMobiltel()); //商户联系人电话
            merchant.setMerFax("");//传真号码
            merchant.setMerAdds(sysuserstb.getAddress());//联系地址
            merchant.setMerZip(sysuserstb.getZipcode());//邮编
            merchant.setMerBankAccount("");//开户行账号
            merchant.setMerBankName("");//开户行名称
            merchant.setMerBusinessScopeId("");//经营范围:数据字典维护
            if (sysuserstb.getProvinceid() != null) {
                merchant.setMerPro(sysuserstb.getProvinceid().toString());//商户省份
            }else{
                merchant.setMerPro("1000");//商户省份 北京
            }
            if (sysuserstb.getCityid() != null) {
                merchant.setMerCity(sysuserstb.getCityid().toString());//商户城市
            }else{
                merchant.setMerCity("1110");//商户城市  
            }

            merchant.setMerType(MerTypeEnum.CHAIN_JOIN_MER.getCode());//商户类型
            merchant.setMerState(MerStateEnum.THROUGH.getCode());//审核状态
            merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
            merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
            merchant.setSource(SourceEnum.TRANSFER.getCode());//来源
            merchant.setActivate(ActivateEnum.ENABLE.getCode());//启用
            merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除
            merchant.setMerRegisterDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));//注册时间
            mapper.addMerchant(merchant);
            //***********************************1.1.生成用标志信息表************************//
            MerchantExtend merchantExtend = new MerchantExtend();
            merchantExtend.setMerCode(merCode);//新生成的上级商户CODE
            merchantExtend.setOldMerchantId(bim.getMchnitid());//老商户表的商户ID
            merchantExtend.setOldMerchantType("3");//标识为老商户表的数据
            merchantExtendMapper.addMerchantExtend(merchantExtend);
            
            //***********************************2.生成连锁商户用户信息************************//
            MerchantUser merchantUser = new MerchantUser();
            boolean merUserCodeFlag = true;
            /*if(sysuserstb.getLoginid()!=null){
                if (sysuserstb.getLoginid().indexOf("@") >= 0 ) {
                    merUserCodeFlag = merchantUserService.checkExist("U" + merCode);
                } else{
                    merUserCodeFlag= merchantUserService.checkExist(sysuserstb.getLoginid());//用户昵称、登录名，新平台merCode +"U"
                }
            }else{
                merUserCodeFlag = merchantUserService.checkExist("U" + merCode);
            }
            if (merUserCodeFlag) {
                logger.error("商户用户MerUserCode：" + "U" + merCode + "对应的用户存在。");
                throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            }*/
                merchantUser.setMerCode(merCode);//所属商户编号
                if (sysuserstb.getRelname() != null) {
                    merchantUser.setMerUserNickname(sysuserstb.getRelname());
                } else {
                    merchantUser.setMerUserNickname(sysuserstb.getUsername());
                }
                if(sysuserstb.getLoginid()!=null){
                    if (sysuserstb.getLoginid().indexOf("@") >= 0 ) {
                        merchantUser.setMerUserName("U" + merCode);
                    } else{
                        merchantUser.setMerUserName(sysuserstb.getLoginid());//用户昵称、登录名，新平台merCode +"U"
                    }
                }else {
                    merchantUser.setMerUserName("U" + merCode);
                }

                merchantUser.setMerUserPwd(sysuserstb.getPassword());//登录密码
                merchantUser.setMerUserTelephone(sysuserstb.getTel());//固定电话
                if (sysuserstb.getMobiltel() != null) {
                    merchantUser.setMerUserTelephone(sysuserstb.getMobiltel());//移动电话 
                }
                if (sysuserstb.getProvinceid() != null) {
                    merchantUser.setMerUserPro(sysuserstb.getProvinceid().toString());//省份代码  
                }else{
                    merchantUser.setMerUserPro("1000");//省份代码  
                }
                if (sysuserstb.getCityid() != null) {
                    merchantUser.setMerUserCity(sysuserstb.getCityid().toString());//城市代码
                }else{
                    merchantUser.setMerUserCity("1110");//城市代码
                }
                merchantUser.setMerUserAdds(sysuserstb.getAddress());//地址
                if ("0".equals(sysuserstb.getSex())) {
                    merchantUser.setMerUserSex(SexEnum.FEMALE.getCode());//性别
                } else if ("1".equals(sysuserstb.getSex())) {
                    merchantUser.setMerUserSex(SexEnum.MALE.getCode());//性别
                } else if ("2".equals(sysuserstb.getSex())) {
                    merchantUser.setMerUserSex(SexEnum.MALE.getCode());//性别
                }

                merchantUser.setMerUserIdentityNum(sysuserstb.getIdentityid());//证件号码
                if ("1000000001".equals(sysuserstb.getIdentitytype())) {//证件类型
                    merchantUser.setMerUserIdentityType("0");
                } else if ("1000000002".equals(sysuserstb.getIdentitytype())) {
                    merchantUser.setMerUserIdentityType("2");
                } else if ("1000000005".equals(sysuserstb.getIdentitytype())) {
                    merchantUser.setMerUserIdentityType("1");
                } else {
                    merchantUser.setMerUserIdentityType(sysuserstb.getIdentitytype());//证件类型  
                }
                merchantUser.setMerUserEmall(sysuserstb.getEmail());//电子邮件
                merchantUser.setCreateDate(DateUtils.stringtoDate(sysuserstb.getRegisttime(), DateUtils.DATE_FULL_STR));//注册时间
                merchantUser.setUpdateDate(DateUtils.stringtoDate(sysuserstb.getLastedittime(), DateUtils.DATE_FULL_STR));//最后修改时间
                merchantUser.setMerUserType(sysuserstb.getUsertype().toString());//用户类型
                if (sysuserstb.getLastmobiltel() != null) {//当前使用的移动电话
                    merchantUser.setMerUserMobile(sysuserstb.getLastmobiltel());
                } else {
                    merchantUser.setMerUserMobile("0000000000");
                }
                if (sysuserstb.getYktcityid() != null) {
                    merchantUser.setCityCode(sysuserstb.getYktcityid().toString());//城市code
                } else {
                    merchantUser.setCityCode("1110");//城市code
                }
                merchantUser.setBirthday(DateUtils.dateToString(DateUtils.stringtoDate(sysuserstb.getBirthday(), DateUtils.DATE_SMALL_STR), DateUtils.DATE_SMALL_STR));//生日
                merchantUser.setMerUserNickname(sysuserstb.getRelname());//真实姓名
                merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());//用户标志
                merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
                merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
                merchantUser.setUserCode(merchantUserService.generateMerUserCode(UserClassifyEnum.MERCHANT));//用户编号
                merchantUser.setSource(SourceEnum.TRANSFER.getCode());//用户注册来源
                if (sysuserstb.getStatus() != null) {
                    merchantUser.setActivate(sysuserstb.getStatus());//是否启用
                } else {
                    merchantUser.setActivate(ActivateEnum.ENABLE.getCode());//用户启用
                }
                maUserpper.addMerchantUser(merchantUser);

                MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
                merchantUserExtend.setUserCode(merchantUser.getUserCode());//新生成的商户CODE
                merchantUserExtend.setOldUserId(sysuserstb.getUserid());//老商户表的商户ID
                merchantUserExtend.setOldUserType("3");//标识为老商户表的数据
                merchantUserExtendMapper.addMerchantUserExtend(merchantUserExtend);
            //***********************************3.生成商户补充信息表MER_DDP_INFO************************//
            Bimchnitfeetb bimchnitfeetbInfo = bimchnitfeetbMapper.findBimchnitfeetbByMchId(bim.getMchnitid());
            MerDdpInfo merDdpInfo = new MerDdpInfo();
            merDdpInfo.setMerCode(merCode);//商户编号
            if (bimchnitfeetbInfo != null) {
                if ("3".equals(bimchnitfeetbInfo.getFeetype())) {
                    merDdpInfo.setSettlementType("0");//结算类型
                    merDdpInfo.setSettlementThreshold(new BigDecimal(0));//结算参数
                } else {
                    merDdpInfo.setSettlementType(bimchnitfeetbInfo.getFeetype());//结算类型
                    merDdpInfo.setSettlementThreshold(bimchnitfeetbInfo.getSetpara());//结算参数
                }
            }
            merDdpInfo.setIsAutoDistribute("0");//是否自动分配额度
            merDdpInfo.setLimitSource("0");//默认自己购买
            merDdpInfoMapper.addMerDdpInfo(merDdpInfo);
            //***********************************4.生成商户密钥分配表数据MER_KEY_TYPE************************//
            MerKeyType merKeyType = new MerKeyType();
            merKeyType.setMerCode(merCode);//商户编号
            merKeyType.setMerKeyType(MerKeyTypeEnum.MD5.getCode());//商户密钥类型
            merKeyType.setMerMd5Paypwd(bim.getPaypwd());//商户MD5签名秘钥
            merKeyType.setMerMd5Backpaypwd(bim.getBackpaypwd());//商户MD5验签秘钥
            merKeyTypeMapper.addMerKeyType(merKeyType);
            //***********************************5.生成商户业务类型信息表mer_rate_supplement************************//
            //***********************************6.生成商户业务费率MER_BUS_RATE***********************************//
            List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
            if (bim.getRateamt() != null) {
                MerBusRate merBusRate = new MerBusRate();
                merBusRate.setMerCode(merCode);//商户编号
                merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务
                merBusRate.setProviderCode(YKT_CODE);//所在业务提供商编码
                merBusRate.setRate(bim.getRateamt());//数值
                merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型
                merBusRateList.add(merBusRate);
                MerRateSupplement merRateSupplement = new MerRateSupplement();
                merRateSupplement.setMerCode(merCode);
                merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
                merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
            }
            if (bimchnitfeetbInfo != null) {
                if (bimchnitfeetbInfo.getTxncode() == 3005) {
                    MerBusRate merBusRate = new MerBusRate();
                    merBusRate.setMerCode(merCode);//商户编号
                    merBusRate.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());//消费业务
                    merBusRate.setProviderCode(YKT_CODE);//所在业务提供商编码
                    merBusRate.setRate(bimchnitfeetbInfo.getAmtfee());//数值
                    merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型
                    merBusRateList.add(merBusRate);
                    MerRateSupplement merRateSupplement2 = new MerRateSupplement();
                    merRateSupplement2.setMerCode(merCode);
                    merRateSupplement2.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
                    merRateSupplementMapper.addMerRateSupplement(merRateSupplement2);
                }
            }
            merBusRateMapper.addMerBusRateBatch(merBusRateList);
            //***********************************7.生成连锁商户主账户信息************************//
            String seqId = accountMapper.getSequenceNextId();
            String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;//按规则生成账户编号
            Account account = new Account();
            account.setAccountCode(preId);//账户编号
            account.setCustomerType("1");//0：个人 1：商户
            account.setCustomerNo(merCode);//在MERCHANT_USER表中自动生成的USER_CODE值
            account.setFundType(FundTypeEnum.AUTHORIZED.getCode()); // 0：资金 1：授信
            accountMapper.addAccount(account);
            //***********************************8.生成连锁商户资金账户记录************************//
            //资金账号
            AccountFund accountFund = new AccountFund();
            accountFund.setAccountCode(preId);//主账户编号A2016030716454610001
            accountFund.setFundAccountCode("F" + "0" + preId);//资金授信账户编号
            accountFund.setFundType(FundTypeEnum.FUND.getCode());//资金类别 0：资金 
            accountFund.setFrozenAmount(new BigDecimal(0));//冻结金额
            accountFund.setSumTotalAmount(new BigDecimal(0));//购买总额度
            accountFund.setTotalBalance(new BigDecimal(0));//剩余额度
            accountFund.setAvailableBalance(new BigDecimal(0));//可用余额
            accountFund.setState("0");//状态
            accountFundMapper.addAccountFund(accountFund);
            AccountFund accountFundSA = new AccountFund();
            accountFundSA.setAccountCode(preId);//主账户编号A2016030716454610001
            accountFundSA.setFundAccountCode("F" + "1" + preId);//授信账户编号
            accountFundSA.setFundType(FundTypeEnum.AUTHORIZED.getCode());//资金类别 1：授信
            accountFundSA.setFrozenAmount(new BigDecimal(0));//冻结金额
            accountFundSA.setSumTotalAmount(new BigDecimal(0));//购买总额度
            accountFundSA.setTotalBalance(new BigDecimal(0));//剩余额度
            accountFundSA.setAvailableBalance(new BigDecimal(0));//可用余额
            accountFundSA.setState("0");//状态
            accountFundMapper.addAccountFund(accountFundSA);
            //***********************************8.1添加自动转账功能mer_auto_amt************************//
            MerAutoAmt merAutoAmt = new MerAutoAmt();
            merAutoAmt.setMerCode(merCode);
            merAutoAmt.setAutoLimitThreshold(new BigDecimal("100000"));
            merAutoAmt.setLimitThreshold(new BigDecimal(0));
            merAutoAmtMapper.addMerAutoAmt(merAutoAmt);
            //***********************************9.生成风控记录*********************************//
            //查询默认的风控值
            AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault(MerTypeEnum.CHAIN_JOIN_MER.getCode());//商户类型
            AccountControl accountControl = new AccountControl();
            accountControl.setFundAccountCode("F" + "1" + preId);//授信账户编号
            accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
            accountControlMapper.addAccountControl(accountControl);
            AccountControl accountControlSA = new AccountControl();
            accountControlSA.setFundAccountCode("F" + "0" + preId);//资金账户编号
            accountControlSA.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
            accountControlSA.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
            accountControlSA.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
            accountControlSA.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
            accountControlSA.setDayTransferMax(accountControllerDefault.getDayTransferMax());
            accountControlSA.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
            accountControlSA.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
            accountControlMapper.addAccountControl(accountControlSA);
            //***********************************10.生成商户折扣记录*********************************//
            //***********************************10.1生成交易折扣日期表*********************************//
            /*Bisalediscount bisalediscount = new Bisalediscount();
            MerchantTranDiscount merTranDis = new MerchantTranDiscount();
            List<Biposidsaletb> listBipos = biposidsaletbMapper.findByPosId(String.valueOf(biposinuserstb.getPosid()));
            if (listBipos != null) {
                for (Biposidsaletb biposidsaletb : listBipos) {
                    TranDiscount tranDis = new TranDiscount();
                    bisalediscount = bisalediscountMapper.findBisalediscountBySaleId(String.valueOf(biposidsaletb.getSaleid()));
                    tranDis.setBeginDate(DateUtils.stringtoDate(bisalediscount.getStartdate(), DateUtils.DATE_FORMAT_YYMMDD_STR));
                    tranDis.setEndDate(DateUtils.stringtoDate(bisalediscount.getEnddate(), DateUtils.DATE_FORMAT_YYMMDD_STR));
                    tranDis.setBeginTime(bisalediscount.getStarttime());
                    tranDis.setEndTime(bisalediscount.getEndtime());
                    tranDis.setWeek(bisalediscount.getWeek());
                    tranDis.setDiscountThreshold(String.valueOf(bisalediscount.getSale()));
                    tranDis.setSetDiscount(String.valueOf(bisalediscount.getSetsale()));
                    TranDiscount transD= tranDiscountMapper.findTranDiscountCheck(tranDis);
                    if(transD!=null){
                        //如果存在就直接在pos关键折扣表加一条记录 TODO 添加纪录
                        merTranDis.setMerCode(merCode);
                        merTranDis.setDiscountId(transD.getId());
                        mtdMapper.addMerchantTranDiscount(merTranDis);
                    }else{
                        tranDiscountMapper.addTranDiscount(tranDis);
                        merTranDis.setMerCode(merCode);
                        merTranDis.setDiscountId(tranDis.getId());
                        mtdMapper.addMerchantTranDiscount(merTranDis);
                    }
                }
                
            }*/

			List<Biposidsaletb> listBipos = biposidsaletbMapper.findByPosId(String.valueOf(biposinuserstb.getPosid()));
			if (CollectionUtils.isNotEmpty(listBipos)) {
				for (Biposidsaletb biposidsaleTemp : listBipos) {
					String saleid = biposidsaleTemp.getSaleid().toString();
					// 根据老的折扣id查询新系统是否已经迁移
					TranDiscount findTranDiscount = tranDiscountMapper.findTranDiscountByOldSaleId(saleid);
					if (findTranDiscount != null) {
						MerchantTranDiscount merTranDis = new MerchantTranDiscount();
						merTranDis.setMerCode(merCode);
						merTranDis.setDiscountId(findTranDiscount.getId());
						mtdMapper.addMerchantTranDiscount(merTranDis);
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

						tranDiscountMapper.addTranDiscount(tranDiscount);

						MerchantTranDiscount merTranDis = new MerchantTranDiscount();
						merTranDis.setMerCode(merCode);
						merTranDis.setDiscountId(tranDiscount.getId());
						mtdMapper.addMerchantTranDiscount(merTranDis);
					}
				}
			}

            //***********************************11.生成商户POS管理表记录*****************************//
            //      
            Pos pos = new Pos();
            pos.setCode(String.valueOf(biposinfotb.getPosid()));
            pos.setMerCode(merCode);
            pos.setProvinceCode("1000");//省份code
            pos.setCityCode("1110");//城市code
            pos.setMaxTimes(new BigDecimal(999999));//限制笔数，老系统没有，到新系统默认为0
            pos.setBind("0");//默认启用
            pos.setProvinceName("北京市");
            pos.setCityName("北京");
            pos.setMerName(sysuserstb.getUsername());//商户名称
            pos.setBundlingDate(new Date());//迁移时绑定
            if (biposinfotb.getLasttime() != null) {
                pos.setUpdateDate(DateUtils.stringtoDate(biposinfotb.getLasttime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
            }

            if (biposinfotb.getBuycost() != null) {
                pos.setUnitCost(biposinfotb.getBuycost());//采购成本  
            }
            if (biposinfotb.getFactorycode() != null) {
                pos.setPosCompanyCode(biposinfotb.getFactorycode());//厂商编号
            }
            if (biposinfotb.getPatterncode() != null) {
                pos.setPosTypeCode(biposinfotb.getPatterncode());//型号编号
            }

            if (biposinfotb.getVersion() != null) {
                pos.setVersion(biposinfotb.getVersion().toString());//pos版本号
            }
            if ("0".equals(biposinfotb.getStatus())) {//0:启用,1:未启用, 2:停用,3:作废, 4：消费封锁, 5：充值封锁;  新系统   0：启用, 1：停用,2：作废,3：充值封锁,4：消费封锁
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
            posMapper.addPos(pos);
            //***********************************12.生成商户POS关系记录*****************************//
            PosMerTb posMerTb = new PosMerTb();
            posMerTb.setCode(String.valueOf(biposinfotb.getPosid()));//pos编号
            posMerTb.setMerCode(merCode);//商户编号
            posMerTb.setComments(PosStatusEnum.ACTIVATE.getCode());//备注
            posMerTbMapper.addPosMerTb(posMerTb);

            //***********************************13.更新老系统表迁移记录*****************************//
            // add by qjc 2016.3.18
            int result = sysuserstbMapper.updateUserTransFlag(sysuserstb.getUserid());
            if (result <= 0) {
                throw new RuntimeException("更新迁移标志失败");
            }
            //***********************************14.生成日志记录*****************************//
            logTransfer.setBatchId(batchId);//日志批次号
            logTransfer.setOldMerchantId(sysuserstb.getUserid());//老商户ID
            logTransfer.setOldMerchantType("3");//老商户类型
            logTransfer.setNewMerchantCode(merCode);//新商户号
            logTransfer.setNewMerchantType(MerTypeEnum.CHAIN_JOIN_MER.getCode());//新商户类型
            logTransfer.setState("0");//成功和失败的状态
            logTransfer.setMemo(ResponseCode.SUCCESS);//导入描述
        }
        catch (Exception e) {
            logTransfer.setBatchId(batchId);//日志批次号
            logTransfer.setOldMerchantId(sysuserstb.getUserid());//老商户ID
            logTransfer.setOldMerchantType("3");//老商户类型
            logTransfer.setNewMerchantCode(merCode);//新商户号
            logTransfer.setNewMerchantType(MerTypeEnum.CHAIN_JOIN_MER.getCode());//新商户类型
            logTransfer.setState("1");//成功和失败的状态
            logTransfer.setMemo(e.getMessage());//导入描述
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            logTransferMapper.insartLogTransfer(logTransfer);
        }

        logger.info("******************** end insart merchantChindJoin ******************************");
        return response;
    }


    
    
    /**
     * @author Mikaelyan
     * @param Proxyinfotb
     */
	@Override
    @Transactional
	public DodopalResponse<String> insertMerchantFromProxy(Proxyinfotb proxyinfotb,String batchId) {
		DodopalResponse<String> response = new DodopalResponse<String>();
        		
		String merCode = generateMerCode(MerClassifyEnum.OFFICIAL.getCode()); 	// 商户编号
		// 联系人手机
		String merLinkUserMobile = "000000";
		Long proxyid = proxyinfotb.getProxyid();
		
		/**************************** 步骤2：生成商户信息表MERCHANT ************************************/
		Merchant merchant = new Merchant();
 		merchant.setMerCode(merCode);
		merchant.setMerName(proxyinfotb.getProxyname());
		merchant.setMerType("15");
		merchant.setMerAdds(proxyinfotb.getProxyaddress());
		if(StringUtils.isNotBlank(proxyinfotb.getProxytel())) {
			//merchant.setMerLinkUserMobile(proxyinfotb.getProxytel().replaceAll("-", ""));
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
		merchant.setMerPro("1000");
		merchant.setMerCity("1110");

		mapper.insertFromProxyInfoTb(merchant); 	// MerchantMapper mapper;
        		
        		
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
		
		merDdpInfoMapper.addMerDdpInfo(merDdpInfo);

        		
		/**************************** 步骤4：生成商户信息扩展表MERCHANT_EXTEND数据 ************************************/
		MerchantExtend merchantExtend = new MerchantExtend();
		
		merchantExtend.setMerCode(merCode);
		merchantExtend.setOldMerchantId(proxyid+"");
		merchantExtend.setOldMerchantType("2");
		
		merchantExtendMapper.addMerchantExtend(merchantExtend);

        		
		/**************************** 步骤5：生成用户信息表MERCHANT_USER数据 ************************************/
		Proxyuserinfotb proxyuserinfotb = proxyuserinfotbMapper.findProxyuserinfotbByID(proxyid+"");
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
			merchantUser.setMerUserSex("0");
		}
		if(BigDecimal.valueOf(0).compareTo(proxyuserinfotb.getSex()) == 0) {
			merchantUser.setMerUserSex("1");
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

		// 用户标志：管理员
		//merchantUser.setMerUserFlg(proxyuserinfotb.getStatus()+"");
		merchantUser.setMerUserFlg("1000");

		merchantUser.setMerUserRemark(proxyuserinfotb.getRemarks());
		if(StringUtils.isNotBlank(proxyuserinfotb.getLastchoosecity())) {
			merchantUser.setCityCode(proxyuserinfotb.getLastchoosecity().substring(proxyuserinfotb.getLastchoosecity().length()-4, proxyuserinfotb.getLastchoosecity().length()));
		}
		merchantUser.setUserCode(userCode); 								// 用户编号
		merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode()); 	// 用户类型
		merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode()); 		// 审核状态
		// 用户来源
		merchantUser.setSource(SourceEnum.TRANSFER.getCode());
		// 省份城市：默认为北京
		merchantUser.setMerUserPro("1000");
		merchantUser.setMerUserCity("1110");

		maUserpper.insertMerchantUser(merchantUser);
        		
        			
		/**************************** 步骤6：生成用户信息扩展表MERCHANT_USER_EXTEND数据 ************************************/
		MerchantUserExtend merchantUserExtend = new MerchantUserExtend();

		merchantUserExtend.setOldUserId(Long.toString(proxyuserinfotb.getUserid()));
		merchantUserExtend.setOldUserType("2");
		merchantUserExtend.setUserCode(userCode);
		
		merchantUserExtendMapper.addMerchantUserExtend(merchantUserExtend);


		/**************************** 步骤7：生成pos信息、pos绑定中间表数据 ************************************/
		
		List<Proxypostb> proxypostbList = proxypostbMapper.findProxypostbListById(Long.toString(proxyid));
		
		if (proxypostbList.size() == 0) {
			logger.error("网点id：" + proxyid + "对应的网点绑定pos信息不存在。");
		}
		for(Proxypostb proxypostb : proxypostbList) {
			/**************************** pos_mer_tb ************************************/
			PosMerTb posMerTb = new PosMerTb();
			
			posMerTb.setCode(proxypostb.getPosid());
			posMerTb.setComments(proxypostb.getRemarks());
			posMerTb.setMerCode(merCode);
			
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
				pos.setBind("0");
				pos.setBundlingDate(new Date());
				pos.setProvinceCode("1000");
				pos.setProvinceName("北京市");
				pos.setCityCode("1110");
				pos.setCityName("北京");

				posMapper.addPos(pos);
			}

		}

        		
		/**************************** 步骤8：生成商户业务信息表MER_RATE_SUPPLEMENT数据    mer_rate_supplement ************************************/
		if (proxyinfotb.getRateamt() != null && StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
			MerRateSupplement merRateSupplement = new MerRateSupplement();
			
			merRateSupplement.setRateCode("01"); 					// 充值业务
			merRateSupplement.setMerCode(merchant.getMerCode());
			
			merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
		}

		if (proxyinfotb.getPayflag()!=null && proxyinfotb.getPayflag().intValue() == 1) {
			MerRateSupplement merRateSupplement = new MerRateSupplement();
			
			merRateSupplement.setRateCode("03"); 					// 消费业务
			merRateSupplement.setMerCode(merchant.getMerCode()); 	// 商户编号
			
			merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
		}
        		
		/****************************   步骤9：生成商户业务费率表MER_BUS_RATE数据  ************************************/
		if (proxyinfotb.getRateamt() != null && StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
			MerBusRate merBusRate = new MerBusRate();
			
			merBusRate.setMerCode(merchant.getMerCode()); 	// 商户编号
			merBusRate.setRateCode("01"); 					// 充值业务
			merBusRate.setProviderCode("100000"); 			// 供应商编号
			merBusRate.setRate(new BigDecimal(0)); 			// 数值
			merBusRate.setRateType("2"); 					// 费率类型:1.笔数;2.千分比
			
			merBusRateMapper.addMerBusRate(merBusRate);
		}

		if (proxyinfotb.getPayflag()!=null && proxyinfotb.getPayflag().intValue() == 1) {
			MerBusRate merBusRate = new MerBusRate();
			
			merBusRate.setMerCode(merchant.getMerCode()); 	// 商户编号
			merBusRate.setRateCode("03"); 					// 消费业务
			merBusRate.setProviderCode("100000"); 			// 供应商编号
			merBusRate.setRate(new BigDecimal(0)); 			// 数值
			merBusRate.setRateType("2"); 					// 费率类型:1.笔数;2.千分比
			
			merBusRateMapper.addMerBusRate(merBusRate);
		}
		
		// 只对审核通过的商户创建账户信息
		if("1".equals(merchant.getMerState())) {
			/****************************   步骤10：生成主账户表ACCOUNT记录  ********************/
			String seqId = accountMapper.getSequenceNextId();
			String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId; 	// 按规则生成账户编号
			Account account = new Account();
			
			account.setAccountCode(preId); 											// 账户编号
			account.setFundType("0"); 												// 0：资金 1：授信
			account.setCustomerType("1");											// 0：个人 1：商户
			account.setCustomerNo(merchant.getMerCode()); 							// 商户编号
			
			accountMapper.addAccount(account);
	                

			/****************************   步骤11：生成资金授信账户表ACCOUNT_FUND记录  ********************/
			Proxylimitinfotb proxylimitinfotb = proxyLimitInfoTbMapper.findProxyLimitInfoTbById(Long.toString(proxyid));
			BigDecimal amtLimit = new BigDecimal(0);
			BigDecimal surplusLimit = new BigDecimal(0);
			if (proxylimitinfotb == null) {
				logger.error("网点id：" + proxyid + "对应的网点充值额度信息不存在。");
			} else {
				// 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
//				amtLimit = proxylimitinfotb.getAmtlimit();
//				surplusLimit = proxylimitinfotb.getSurpluslimit();
			}
			AccountFund accountFund = new AccountFund();
			
			accountFund.setAccountCode(account.getAccountCode());					// 主账户编号
			accountFund.setFundAccountCode("F" + "0" + account.getAccountCode()); 	// 资金授信账户编号
			accountFund.setSumTotalAmount(amtLimit); 								// 购买总额度
			accountFund.setTotalBalance(surplusLimit); 								// 剩余额度
			accountFund.setAvailableBalance(surplusLimit);							// 可用金额
			accountFund.setFundType("0"); 											// 资金类别 0：资金 1：授信
			accountFund.setFrozenAmount(new BigDecimal(0)); 						// 冻结金额
			accountFund.setState("0");												// 状态
			accountFundMapper.addAccountFund(accountFund);
	        
			
			/****************************   步骤13、 生成资金账户风控表ACCOUNT_CONTROL数据 ********************/
			AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault(merchant.getMerType()); 	// 商户类型 	// 查询默认的风控值
			AccountControl accountControl = new AccountControl();
			
			accountControl.setFundAccountCode(accountFund.getFundAccountCode()); 	// 资金授信账户编号
			if (accountControllerDefault == null) {
				logger.error("商户类型：" + merchant.getMerType() + "对应的默认的风控值信息不存在。");
			} else {
				accountControl.setFundAccountCode(accountFund.getFundAccountCode()); 	// 资金授信账户编号
				accountControl.setDayConsumeSingleLimit(accountControllerDefault.getDayConsumeSingleLimit());
				accountControl.setDayConsumeSumLimit(accountControllerDefault.getDayConsumeSumLimit());
				accountControl.setDayRechargeSingleLimit(accountControllerDefault.getDayRechargeSingleLimit());
				accountControl.setDayRechargeSumLimit(accountControllerDefault.getDayRechargeSumLimit());
				accountControl.setDayTransferMax(accountControllerDefault.getDayTransferMax());
				accountControl.setDayTransferSingleLimit(accountControllerDefault.getDayTransferSingleLimit());
				accountControl.setDayTransferSumLimit(accountControllerDefault.getDayTransferSumLimit());
				
				accountControlMapper.addAccountControl(accountControl);
			}
		}

		// 更新迁移标识
		proxyinfotbMapper.updateTransFlag(Long.toString(proxyinfotb.getProxyid()));

		response.setCode(ResponseCode.SUCCESS);
		response.setResponseEntity(merchant.getMerCode());

		return response;
	}


   

}
