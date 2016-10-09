package com.dodopal.transfernew.business.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.dao.GroupinfotbMapper;
import com.dodopal.transfernew.business.dao.GroupinproxytbMapper;
import com.dodopal.transfernew.business.dao.MerchantMapper;
import com.dodopal.transfernew.business.dao.MerchantUserMapper;
import com.dodopal.transfernew.business.dao.ProxypostbMapper;
import com.dodopal.transfernew.business.dao.ProxyuserinfotbMapper;
import com.dodopal.transfernew.business.dao.TransferProxyMapper;
import com.dodopal.transfernew.business.model.TransferProxy;
import com.dodopal.transfernew.business.model.old.Groupinfotb;
import com.dodopal.transfernew.business.model.old.Groupinproxytb;
import com.dodopal.transfernew.business.model.old.Groupuserinfotb;
import com.dodopal.transfernew.business.model.old.Proxyuserinfotb;
import com.dodopal.transfernew.business.model.transfer.MerBusRate;
import com.dodopal.transfernew.business.model.transfer.MerDdpInfo;
import com.dodopal.transfernew.business.model.transfer.MerRateSupplement;
import com.dodopal.transfernew.business.model.transfer.Merchant;
import com.dodopal.transfernew.business.model.transfer.MerchantExtend;
import com.dodopal.transfernew.business.model.transfer.MerchantUser;
import com.dodopal.transfernew.business.model.transfer.MerchantUserExtend;
import com.dodopal.transfernew.business.service.GroupinfoService;
import com.dodopal.transfernew.business.service.MerchantService;

@Service
public class GroupinfoServiceImpl implements GroupinfoService {
    private Logger logger = LoggerFactory.getLogger(GroupinfoServiceImpl.class);

    @Autowired
    GroupinfotbMapper groupinfotbMapper;

    @Autowired
    MerchantUserMapper merchantUserMapper;

    @Autowired
    ProxypostbMapper proxypostbMapper;

    @Autowired
    GroupinproxytbMapper groupinproxytbMapper;

    @Autowired
    ProxyuserinfotbMapper proxyuserinfotbMapper;

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    MerchantService merchantService;

    @Autowired
    TransferProxyMapper transferProxyMapper;

    /**
     * 集团迁移相关信息数据
     */
    @Override
    @Transactional
    public DodopalResponse<String> groupInfoTranfer(String groupid, String citycode) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        //***********************************    1.1、 查询迁移集团的基本信息数据 ********************//
        Groupinfotb groupinfotb = groupinfotbMapper.findGroupinfoByCityNO(groupid);

        //***********************************    1.2、 查询迁移集团的用户信息数据 ********************//
        List<Groupuserinfotb> groupuserinfotbList = groupinfotbMapper.findGroupuserinfoByList(groupid);
        if (CollectionUtils.isEmpty(groupuserinfotbList)) {
            logger.error("集团id：" + groupid + "对应的集团用户信息不存在。");
            throw new DDPException(ResponseCode.UNKNOWN_ERROR);
        }

        //***********************************   2、生成商户信息表merchant     ********************//
        Merchant merchant = new Merchant();

        MerBusRate merBusRate = new MerBusRate();

        List<MerRateSupplement> merRateSupplementList = new ArrayList<MerRateSupplement>();
        List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();

        merchant.setMerCode(generateMerCode(MerClassifyEnum.OFFICIAL.getCode())); //正式商户 生成商户编号
        merchant.setMerName(groupinfotb.getGroupname());//集团名称

        merchant.setMerCity(citycode);//城市编号
        TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
        if (cityEnum != null) {
            merchant.setMerPro(cityEnum.getProCode());//省份编号
        }

        merchant.setMerType(MerTypeEnum.CHAIN.getCode());//商户类型 连锁商户
        merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
        merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
        merchant.setSource(SourceEnum.TRANSFER.getCode());//来源
        merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除

        if (groupinfotb.getStatus() != null) { //审核状态  和启用标识
            String merState = groupinfotb.getStatus().toString();//状态
            if ("0".equals(merState) || "1".equals(merState)) {
                merchant.setMerState(MerStateEnum.NO_AUDIT.getCode()); //未审核
                merchant.setActivate(ActivateEnum.ENABLE.getCode());//启用
            } else if ("2".equals(merState) || "4".equals(merState)) {
                merchant.setMerState(MerStateEnum.THROUGH.getCode()); //已审核
                merchant.setActivate(ActivateEnum.DISABLE.getCode());//停用
            } else if ("3".equals(merState)) {
                merchant.setMerState(MerStateEnum.THROUGH.getCode()); //审核
                merchant.setActivate(ActivateEnum.ENABLE.getCode());//启用
            }
        }

        if (groupinfotb.getAddress() != null) {
            merchant.setMerAdds(groupinfotb.getAddress());//地址
        }
        if (groupinfotb.getZipcode() != null) {
            merchant.setMerZip(groupinfotb.getZipcode());//邮政编码
        }
        if (groupinfotb.getLxperson() != null) {
            merchant.setMerLinkUser(groupinfotb.getLxperson());//联系人
        }
        if (groupinfotb.getLxmobile() != null) {
            merchant.setMerLinkUserMobile(groupinfotb.getLxmobile());//手机号
        }
        if (groupinfotb.getLxtel() != null) {
            merchant.setMerTelephone(groupinfotb.getLxtel());//电话号码
        }

        try {
            if (groupinfotb.getRegisttime() != null) {
                merchant.setMerRegisterDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupinfotb.getRegisttime()));//注册时间  
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }//注册时间
        try {
            if (groupinfotb.getStarttime() != null) {
                merchant.setMerActivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupinfotb.getStarttime()));
            }
        }
        catch (ParseException e) {

            e.printStackTrace();
        }//激活时间

        try { //停/启用时间
            if (groupinfotb.getEdittime() != null) {
                merchant.setMerDeactivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupinfotb.getEdittime()));
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        if (groupinfotb.getEdituser() != null) {
            merchant.setUpdateUser(groupinfotb.getEdituser());//操作人
        }
        if (groupinfotb.getRateamt() != null) {
            merBusRate.setRate(groupinfotb.getRateamt());//充值费率
        }
        //***********************************   3、生成商户补充信息表MER_DDP_INFO数据     ********************//
        MerDdpInfo merDdpInfo = new MerDdpInfo();
        merDdpInfo.setMerCode(merchant.getMerCode());//商户编号
        merDdpInfo.setSettlementType("0");//结算类型  0：天数 1：笔数 2：金额 
        merDdpInfo.setSettlementThreshold(new BigDecimal(7));//结算参数
        merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否
        merchant.setMerDdpInfo(merDdpInfo);
        //***********************************   4、 生成商户信息扩展表MERCHANT_EXTEND数据     ********************//
        MerchantExtend merchantExtend = new MerchantExtend();
        merchantExtend.setMerCode(merchant.getMerCode());//商户编号
        merchantExtend.setOldMerchantId(groupid);//老系统网点id
        merchantExtend.setOldMerchantType("1");//老系统商户类型  集团1，网点2，商户3
        merchant.setMerExtend(merchantExtend);
        //***********************************   5、 生成用户信息表MERCHANT_USER数据     ********************//
        MerchantUser merchantUser = null;
        List<MerchantUser> merUserList = new ArrayList<MerchantUser>(); //操作员

        for (Groupuserinfotb groupuserinfotb : groupuserinfotbList) {

            //***********************************   5.1、 生成集团 【管理员】 的用户信息和用户信息扩展表     ********************//
            // 集团管理员
            if ("0".equals(groupuserinfotb.getUserqx().toString())) {
                merchantUser = new MerchantUser(); //管理员
                merchantUser.setMerCode(merchant.getMerCode());//集团编号
                merchantUser.setUserCode(generateMerUserCode(UserClassifyEnum.MERCHANT));//正式商户   生成商户用户编号
                merchantUser.setMerUserName(groupuserinfotb.getLoginname());//帐户名
                merchantUser.setMerUserMobile(groupuserinfotb.getLxmobile());//手机号
                merchantUser.setMerUserNickname(groupuserinfotb.getLxperson());//联系人
                merchantUser.setMerUserPwd(groupuserinfotb.getPassword());//登录密码
                merchantUser.setMerUserEmall(groupuserinfotb.getEmail());//邮箱
                merchantUser.setMerUserRemark(groupuserinfotb.getRemarks());//备注
                // 证件号码
                String merUserIdentityNum = groupinfotb.getIdentityid();
                if (StringUtils.isNotBlank(merUserIdentityNum)) {
                    merchantUser.setMerUserIdentityType("0");
                    merchantUser.setMerUserIdentityNum(merUserIdentityNum);
                }
                try {
                    if (groupuserinfotb.getCreatetime() != null) {
                        merchantUser.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupuserinfotb.getCreatetime()));//开通时间
                    }
                }
                catch (ParseException e) {

                    e.printStackTrace();
                }
                merchantUser.setMerUserFlg(MerUserFlgEnum.ADMIN.getCode());//用户标志//老系统：0为管理员，1普通用户；新系统：1000为管理员，1100普通用户
                merchantUser.setMerUserState("1");
                merchantUser.setActivate("0");
                merchantUser.setMerUserType("1");
                merchantUser.setSource(SourceEnum.TRANSFER.getCode());

                MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
                merchantUserExtend.setOldUserId(Long.toString(groupuserinfotb.getLoginuserid()));//老系统用户id
                merchantUserExtend.setOldUserType("1");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
                merchantUserExtend.setUserCode(merchantUser.getUserCode());//对应 新系统用户编号
                merchantUser.setMerUserExtend(merchantUserExtend);//商户用户扩展表信息
                merchant.setMerUser(merchantUser); //商户用户表    管理员

            } else { //集团操作员  
                //***********************************    5.2、 生成集团 【操作员】 的用户信息和用户信息扩展表     ********************//
                MerchantUser merchantUser1 = new MerchantUser();
                merchantUser1.setMerCode(merchant.getMerCode());//集团编号
                merchantUser1.setUserCode(generateMerUserCode(UserClassifyEnum.MERCHANT));//正式商户   生成商户用户编号
                merchantUser1.setMerUserName(groupuserinfotb.getLoginname());//帐户名
                merchantUser1.setMerUserMobile(groupuserinfotb.getLxmobile());//手机号
                merchantUser1.setMerUserNickname(groupuserinfotb.getLxperson());//联系人
                merchantUser1.setMerUserPwd(groupuserinfotb.getPassword());//登录密码
                merchantUser1.setMerUserEmall(groupuserinfotb.getEmail());//邮箱
                merchantUser1.setMerUserRemark(groupuserinfotb.getRemarks());//备注
                // 证件号码
                String merUserIdentityNum = groupinfotb.getIdentityid();
                if (StringUtils.isNotBlank(merUserIdentityNum)) {
                    merchantUser1.setMerUserIdentityType("0");
                    merchantUser1.setMerUserIdentityNum(merUserIdentityNum);
                }
                try {
                    if (groupuserinfotb.getCreatetime() != null) {
                        merchantUser1.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupuserinfotb.getCreatetime()));//开通时间
                    }
                }
                catch (ParseException e) {

                    e.printStackTrace();
                }
                merchantUser1.setMerUserFlg(MerUserFlgEnum.COMMON.getCode());//用户标志//老系统：0为管理员，1普通用户；新系统：1000为管理员，1100普通用户
                merchantUser1.setMerUserState("1");
                merchantUser1.setActivate("0");
                merchantUser1.setMerUserType("1");
                merchantUser1.setSource(SourceEnum.TRANSFER.getCode());

                MerchantUserExtend merchantUserExtend1 = new MerchantUserExtend();
                merchantUserExtend1.setOldUserId(Long.toString(groupuserinfotb.getLoginuserid()));//老系统用户id
                merchantUserExtend1.setOldUserType("1");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
                merchantUserExtend1.setUserCode(merchantUser1.getUserCode());//对应 新系统用户编号
                merchantUser1.setMerUserExtend(merchantUserExtend1);//商户用户扩展表信息
                merUserList.add(merchantUser1);
            }
        }

        //***********************************    5.3、将集团下网点的管理员  生成集团 【操作员】 的用户信息和用户信息扩展表     ********************//   ********************//

        List<Groupinproxytb> groupinproxytbList = groupinproxytbMapper.findGroupinproxytb(groupid);
        if (CollectionUtils.isNotEmpty(groupinproxytbList)) {
            for (Groupinproxytb groupinproxytb : groupinproxytbList) {

                TransferProxy transferProxy = transferProxyMapper.findTransferProxyByProxyId(groupinproxytb.getProxyid());
                if (transferProxy == null) {
                    logger.info("网点id：" + groupinproxytb.getProxyid() + "在transferProxy表没有记录。");
                    continue;
                }

                //如果集团下的网点，是按网点下pos迁移的，则把网点的管理员迁移成商户的操作员。
                if ("0".equals(transferProxy.getTransferType())) {

                    Proxyuserinfotb proxyuserinfotb = proxyuserinfotbMapper.findProxyuserinfotbByID(groupinproxytb.getProxyid());
                    if (proxyuserinfotb != null) {
                        MerchantUser merchantUser2 = new MerchantUser();
                        merchantUser2.setUserCode(generateMerCode("0"));//正式商户   生成商户用户编号
                        if (proxyuserinfotb.getLoginname() != null) {
                            merchantUser2.setMerUserName(proxyuserinfotb.getLoginname());//登录名
                        }

                        if (proxyuserinfotb.getPwd() != null) {
                            merchantUser2.setMerUserPwd(proxyuserinfotb.getPwd());//密码
                        }
                        if (proxyuserinfotb.getIdentitytypeid() != null) {
                            if ("1000000001".equals(proxyuserinfotb.getIdentitytypeid().toString())) {//证件类型
                                merchantUser2.setMerUserIdentityType("0");
                            } else if ("1000000002".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
                                merchantUser2.setMerUserIdentityType("2");
                            } else if ("1000000005".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
                                merchantUser2.setMerUserIdentityType("1");
                            }
                        }
                        if (proxyuserinfotb.getIdentityid() != null) {
                            merchantUser2.setMerUserIdentityNum(proxyuserinfotb.getIdentityid());//证件号码
                        }
                        if (proxyuserinfotb.getUsername() != null) {
                            merchantUser2.setMerUserNickname(proxyuserinfotb.getUsername());//用户真实姓名
                        }
                        if (proxyuserinfotb.getSex() != null) {
                            merchantUser2.setMerUserSex(("0".equals(proxyuserinfotb.getSex().toString())) ? "1" : "0");//老系统：1:男 0:女;新系统：1：女0：男
                        }
                        if (proxyuserinfotb.getEmail() != null) {
                            merchantUser2.setMerUserEmall(proxyuserinfotb.getEmail());//邮箱
                        }
                        if (proxyuserinfotb.getMobiltel() != null) {
                            merchantUser2.setMerUserMobile(proxyuserinfotb.getMobiltel());//移动电话
                        }
                        if (proxyuserinfotb.getTel() != null) {
                            merchantUser2.setMerUserTelephone(proxyuserinfotb.getTel());//固定电话
                        }

                        if (proxyuserinfotb.getProvinceid() != null) {
                            merchantUser2.setMerUserPro(proxyuserinfotb.getProvinceid().toString());//省份
                        }
                        if (proxyuserinfotb.getCityid() != null) {
                            merchantUser2.setMerUserCity(proxyuserinfotb.getCityid().toString());//城市
                        }

                        if (proxyuserinfotb.getAddress() != null) {
                            merchantUser2.setMerUserAdds(proxyuserinfotb.getAddress());//地址
                        }

                        merchantUser2.setMerCode(merchant.getMerCode());//商户编号
                        merchantUser2.setMerUserFlg(MerUserFlgEnum.COMMON.getCode());
                        merchantUser2.setActivate("0");//0启用，1停用
                        merchantUser2.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
                        merchantUser2.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
                        merchantUser2.setPayinfoFlg("0");//是否弹框提示
                        merchantUser2.setMerUserRemark(proxyuserinfotb.getRemarks());//备注
                        merchantUser2.setSource(SourceEnum.TRANSFER.getCode());

                        MerchantUserExtend merchantUserExtend2 = new MerchantUserExtend();
                        merchantUserExtend2.setOldUserId(Long.toString(proxyuserinfotb.getUserid()));//老系统用户id
                        merchantUserExtend2.setOldUserType("1");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
                        merchantUserExtend2.setUserCode(merchantUser2.getUserCode());//对应 新系统用户编号
                        merchantUser2.setMerUserExtend(merchantUserExtend2);//商户用户扩展表信息
                        
                        
                        if(merchantUser.getMerUserName().equals(merchantUser2.getMerUserName())){
                        }else{
                            merUserList.add(merchantUser2);
                        }
                        
                    }

                }
            }
        }

        merchant.setMerUserList(merUserList); //商户用户表  操作员

        //***********************************   6、 生成商户业务信息表MER_RATE_SUPPLEMENT数据   ********************//
        if (StringUtils.isNotBlank(groupinfotb.getRateamt().toString())) {
            MerRateSupplement merRateSupplement = new MerRateSupplement();
            merRateSupplement.setRateCode("01");//充值业务
            merRateSupplement.setMerCode(merchant.getMerCode());
            merRateSupplementList.add(merRateSupplement);
        }
        merchant.setMerRateSpmtList(merRateSupplementList);
        //***********************************   9、 生成商户业务费率表MER_BUS_RATE数据  ********************//
        if (StringUtils.isNotBlank(groupinfotb.getRateamt().toString())) {
            merBusRate.setMerCode(merchant.getMerCode());//商户编号
            merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务

            if (cityEnum != null) { //通卡
                merBusRate.setProviderCode(cityEnum.getYktCode());
            }
            merBusRate.setRate(new BigDecimal(0));//数值
            merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型:1.笔数;2.千分比
            merBusRateList.add(merBusRate);
        }
        merchant.setMerBusRateList(merBusRateList);

        //  if ("1".equals(merchant.getMerState())) {//已审核
        merchant.setFundType("0"); //资金类别 0：资金 1：授信
        //}
        merchantService.addMerchantInfo(merchant);
        // 更新迁移标志
        groupinfotbMapper.updateTransFlag(groupid);
        response.setResponseEntity(merchant.getMerCode());
        return response;
    }

    //生成商户号
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
