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
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.dao.BiposinfotbMapper;
import com.dodopal.transfernew.business.dao.BiposinuserstbMapper;
import com.dodopal.transfernew.business.dao.MerchantExtendMapper;
import com.dodopal.transfernew.business.dao.MerchantMapper;
import com.dodopal.transfernew.business.dao.MerchantUserMapper;
import com.dodopal.transfernew.business.dao.ProxyLimitInfoTbMapper;
import com.dodopal.transfernew.business.dao.ProxyautoaddeduoperinfotbMapper;
import com.dodopal.transfernew.business.dao.ProxyinfotbMapper;
import com.dodopal.transfernew.business.dao.ProxypostbMapper;
import com.dodopal.transfernew.business.dao.ProxyuserinfotbMapper;
import com.dodopal.transfernew.business.dao.SysuserstbMapper;
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
import com.dodopal.transfernew.business.service.ChilrenMerchantService;
import com.dodopal.transfernew.business.service.MerchantService;

/**
 * 普通的直营网点和加盟网点
 * @author lenovo
 */

@Service
public class ChilrenMerchantServiceImpl implements ChilrenMerchantService {
    private Logger logger = LoggerFactory.getLogger(ChilrenMerchantServiceImpl.class);

    @Autowired
    ProxyinfotbMapper proxyinfotbMapper;

    @Autowired
    ProxyuserinfotbMapper proxyuserinfotbMapper;

    @Autowired
    ProxyLimitInfoTbMapper proxyLimitInfoTbMapper;

    @Autowired
    ProxypostbMapper proxypostbMapper;

    @Autowired
    BiposinfotbMapper biposinfotbMapper;

    @Autowired
    MerchantUserMapper merchantUserMapper;

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    MerchantExtendMapper merchantExtendMapper;

    @Autowired
    BiposinuserstbMapper biposinuserstbMapper;

    @Autowired
    SysuserstbMapper sysuserstbMapper;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProxyautoaddeduoperinfotbMapper proxyautoaddeduoperinfotbMapper;

    //网点迁移 

    @Override
    @Transactional
    public DodopalResponse<String> childrenMerchantTransfer(String proxyid, String groupid, String mertype, String citycode, String batchId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);

        //***********************************    1、查询需要迁移的网点信息   **************************//

        //***********************************    1.1、查询需要迁移的网点信息   ********************//
        Proxyinfotb proxyinfotb = proxyinfotbMapper.findProxyinfotbById(proxyid);

        if (proxyinfotb == null) {
            logger.error("网点id：" + proxyid + "对应的网点信息不存在。");
            throw new DDPException(ResponseCode.UNKNOWN_ERROR);
        }

        //***********************************    1.2、查询需要迁移的网点的用户信息   ********************//
        List<Proxyuserinfotb> proxyuserinfotbList = proxyuserinfotbMapper.findProxyuserinfotbByProxyid(proxyid);
        if (CollectionUtils.isEmpty(proxyuserinfotbList)) {
            logger.error("网点id：" + proxyid + "对应的网点用户信息不存在。");
            throw new DDPException(ResponseCode.UNKNOWN_ERROR);
        }

        //***********************************    2、生成商户信息表merchant   ********************//
        Merchant merchant = new Merchant();
        merchant.setMerCode(generateMerCode(MerClassifyEnum.OFFICIAL.getCode())); //正式商户 生成商户编号
        merchant.setMerName(proxyinfotb.getProxyname());//商户名称
        merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除
        merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
        merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
        merchant.setSource(SourceEnum.TRANSFER.getCode());
        TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
        if (cityEnum != null) {
            merchant.setMerPro(cityEnum.getProCode()); // 省份
        }
        merchant.setMerCity(citycode); // 城市
        merchant.setMerType(mertype); //商户类型

        //        merType = proxyinfotb.getProxytypeid().toString();//网点类型
        //
        //        if ("1".equals(merType) || "2".equals(merType) || "4".equals(merType)) { //1 个人,2 企业,4 加盟
        //            merchant.setMerType("14");//连锁加盟网点
        //        } else { //直营店  ，连锁直营店
        //            merchant.setMerType("13"); //连锁直营网点
        //        }
        //青岛东海星辰商贸有限公司 下三个网点：东海星辰科大店："13358",东海星辰海大店"13368",东海星辰科大支路店  "13369" 设置成连锁加盟
        //        if ("13358".equals(proxyid) || "13368".equals(proxyid) || "13369".equals(proxyid)) {
        //            merchant.setMerType("14"); //连锁加盟网点
        //        }

        //青岛菜鸟驿站 groupid ="1000001281"
        //青岛职业技术学院阿里巴巴菜鸟驿站:"5918",中国石油大学阿里巴巴菜鸟驿站:"5919",青岛理工大学琴岛学院阿里巴巴菜鸟驿站:"5920"
        //青岛大学东校区阿里巴巴菜鸟驿站:"5921",青岛大学西校区阿里巴巴菜鸟驿站:"5922",滨海学院阿里巴巴菜鸟驿站:"5923",青岛大学中心校区阿里巴巴菜鸟驿站:"5924"
        //        if ("5918".equals(proxyid) || "5919".equals(proxyid) || "5920".equals(proxyid) || "5921".equals(proxyid) || "5922".equals(proxyid) || "5923".equals(proxyid) || "5924".equals(proxyid)) {
        //            merchant.setMerType("13"); //连锁直营网点
        //        }

        if (proxyinfotb.getProxyaddress() != null) {
            merchant.setMerAdds(proxyinfotb.getProxyaddress()); //代理网点地址
        }

        if (proxyinfotb.getProxyfax() != null) {
            merchant.setMerFax(proxyinfotb.getProxyfax());//传真
        }
        try {
            if (proxyinfotb.getRegisttime() != null) {
                merchant.setMerRegisterDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(proxyinfotb.getRegisttime()));//注册时间 
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        } //注册时间
        if (proxyinfotb.getStatus() != null) {
            String merState = proxyinfotb.getStatus().toString();//审核状态
            if ("0".equals(merState) || "3".equals(merState)) {
                merchant.setMerState("0"); //未审核
            } else if ("4".equals(merState)) {
                merchant.setMerState("2"); //审核不通过
            } else {
                merchant.setMerState("1"); //审核通过
            }

            if ("2".equals(merState)) { //启用停用状态
                merchant.setActivate("1");
            } else {
                merchant.setActivate("0");
            }
        }

        try {
            if (proxyinfotb.getEdittime() != null) {
                merchant.setMerDeactivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(proxyinfotb.getEdittime()));
            }
            //停启用时间
            merchant.setUpdateUser(proxyinfotb.getEdituser());//操作人
            if (proxyinfotb.getApprovaldate() != null) {
                merchant.setMerStateDate(new SimpleDateFormat("yyyy-MM-dd").parse(proxyinfotb.getApprovaldate()));//审批日期
            }
            if (proxyinfotb.getBankaccountname() != null) {
                merchant.setMerBankUserName(proxyinfotb.getBankaccountname());//开户行账户名称
            }
            if (proxyinfotb.getBankacc() != null) {
                merchant.setMerBankAccount(proxyinfotb.getBankacc());//开户行账号
            }
            if (proxyinfotb.getBankaccname() != null) {
                merchant.setMerBankName(proxyinfotb.getBankaccname());//银行名称
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        // 查找上级商户号
        MerchantExtend merParent = merchantExtendMapper.findMerchantExtend(groupid, "1");
        if (merParent != null) {
            merchant.setMerParentCode(merParent.getMerCode());
        }
        //***********************************   3、生成商户补充信息表MER_DDP_INFO数据     4.生成直营网点 补充信息表mer_auto_amt数据   是否自动分配额度********************//
        MerDdpInfo merDdpInfo = new MerDdpInfo();
       // MerAutoAmt merAutoAmt = new MerAutoAmt();
        merDdpInfo.setMerCode(merchant.getMerCode());//商户编号
        merDdpInfo.setSettlementType("0");//结算类型  0：天数 1：笔数 2：金额 
        merDdpInfo.setSettlementThreshold(new BigDecimal(7));//结算参数
        merDdpInfo.setIsAutoDistribute("2");// 是否自动分配额度      0 是  , 1否, 2 共享额度
       // merchant.setMerAutoAmt(merAutoAmt);
        merchant.setMerDdpInfo(merDdpInfo);//生成商户补充信息表
        //***********************************   5、 生成商户信息扩展表MERCHANT_EXTEND数据     ********************//
        MerchantExtend merchantExtend = new MerchantExtend();
        merchantExtend.setMerCode(merchant.getMerCode());//商户编号
        merchantExtend.setOldMerchantId(proxyid);//老系统网点id
        merchantExtend.setOldMerchantType("2");//老系统商户类型 集团1，网点2，商户3
        merchant.setMerExtend(merchantExtend);//生成商户扩展表记录
        //***********************************   6、 生成用户信息表MERCHANT_USER数据     ********************//
        // 用户信息(管理员)
        MerchantUser merchantUser = new MerchantUser();
        // 用户信息(操作员)
        List<MerchantUser> merUserList = new ArrayList<MerchantUser>();

        for (Proxyuserinfotb proxyuserinfotb : proxyuserinfotbList) {

            //***********************************   6.1、 生成网点 的用户信息和用户信息扩展表数据 【管理员】    ********************//
            if ("0".equals(proxyuserinfotb.getStatus().toString())) { //管理员
                merchantUser.setUserCode(generateMerCode("0"));//正式商户   生成商户用户编号
                if (proxyuserinfotb.getLoginname() != null) {
                    merchantUser.setMerUserName(proxyuserinfotb.getLoginname());//登录名
                }
                if (proxyuserinfotb.getPwd() != null) {
                    merchantUser.setMerUserPwd(proxyuserinfotb.getPwd());//密码
                }
                if (proxyuserinfotb.getIdentitytypeid() != null) {
                    if ("1000000001".equals(proxyuserinfotb.getIdentitytypeid().toString())) {//证件类型
                        merchantUser.setMerUserIdentityType("0");
                    } else if ("1000000002".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
                        merchantUser.setMerUserIdentityType("2");
                    } else if ("1000000005".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
                        merchantUser.setMerUserIdentityType("1");
                    }
                }
                if (proxyuserinfotb.getIdentityid() != null) {
                    merchantUser.setMerUserIdentityNum(proxyuserinfotb.getIdentityid());//证件号码
                }
                if (proxyuserinfotb.getUsername() != null) {
                    merchantUser.setMerUserNickname(proxyuserinfotb.getUsername());//用户真实姓名
                }
                if (proxyuserinfotb.getSex() != null) {
                    merchantUser.setMerUserSex(("0".equals(proxyuserinfotb.getSex().toString())) ? "1" : "0");//老系统：1:男 0:女;新系统：1：女0：男
                }
                if (proxyuserinfotb.getEmail() != null) {
                    merchantUser.setMerUserEmall(proxyuserinfotb.getEmail());//邮箱
                }
                if (proxyuserinfotb.getMobiltel() != null) {
                    merchantUser.setMerUserMobile(proxyuserinfotb.getMobiltel());//移动电话
                }
                if (proxyuserinfotb.getTel() != null) {
                    merchantUser.setMerUserTelephone(proxyuserinfotb.getTel());//固定电话
                }

                if (proxyuserinfotb.getProvinceid() != null) {
                    merchantUser.setMerUserPro(proxyuserinfotb.getProvinceid().toString());//省份
                }
                if (proxyuserinfotb.getCityid() != null) {
                    merchantUser.setMerUserCity(proxyuserinfotb.getCityid().toString());//城市
                }

                if (proxyuserinfotb.getAddress() != null) {
                    merchantUser.setMerUserAdds(proxyuserinfotb.getAddress());//地址
                }

                merchantUser.setMerCode(merchant.getMerCode());//商户编号
                merchantUser.setMerUserFlg("1000");
                merchantUser.setActivate(ActivateEnum.ENABLE.getCode());//0启用，1停用
                merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
                merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
                merchantUser.setPayinfoFlg("0");//是否弹框提示
                merchantUser.setMerUserRemark(proxyuserinfotb.getRemarks());//备注
                merchantUser.setSource(SourceEnum.TRANSFER.getCode());
                merchantUser.setCityCode(citycode);

                MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
                merchantUserExtend.setOldUserId(Long.toString(proxyuserinfotb.getUserid()));//老系统用户id
                merchantUserExtend.setOldUserType("2");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
                merchantUserExtend.setUserCode(merchantUser.getUserCode());//对应 新系统用户编号
                merchantUser.setMerUserExtend(merchantUserExtend);

            } else { //操作员
                //***********************************   6.2、 生成网点 的用户信息和用户信息扩展表数据 【操作员】    ********************//
                MerchantUser merchantUser1 = new MerchantUser();

                merchantUser1.setUserCode(generateMerCode("0"));//正式商户   生成商户用户编号
                if (proxyuserinfotb.getLoginname() != null) {
                    merchantUser1.setMerUserName(proxyuserinfotb.getLoginname());//登录名
                }
                if (proxyuserinfotb.getPwd() != null) {
                    merchantUser1.setMerUserPwd(proxyuserinfotb.getPwd());//密码
                }
                if (proxyuserinfotb.getIdentitytypeid() != null) {
                    if ("1000000001".equals(proxyuserinfotb.getIdentitytypeid().toString())) {//证件类型
                        merchantUser1.setMerUserIdentityType("0");
                    } else if ("1000000002".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
                        merchantUser1.setMerUserIdentityType("2");
                    } else if ("1000000005".equals(proxyuserinfotb.getIdentitytypeid().toString())) {
                        merchantUser1.setMerUserIdentityType("1");
                    }
                }
                if (proxyuserinfotb.getIdentityid() != null) {
                    merchantUser1.setMerUserIdentityNum(proxyuserinfotb.getIdentityid());//证件号码
                }
                if (proxyuserinfotb.getUsername() != null) {
                    merchantUser1.setMerUserNickname(proxyuserinfotb.getUsername());//用户真实姓名
                }
                if (proxyuserinfotb.getSex() != null) {
                    merchantUser1.setMerUserSex(("0".equals(proxyuserinfotb.getSex().toString())) ? "1" : "0");//老系统：1:男 0:女;新系统：1：女0：男
                }
                if (proxyuserinfotb.getEmail() != null) {
                    merchantUser1.setMerUserEmall(proxyuserinfotb.getEmail());//邮箱
                }
                if (proxyuserinfotb.getMobiltel() != null) {
                    merchantUser1.setMerUserMobile(proxyuserinfotb.getMobiltel());//移动电话
                }
                if (proxyuserinfotb.getTel() != null) {
                    merchantUser1.setMerUserTelephone(proxyuserinfotb.getTel());//固定电话
                }

                if (proxyuserinfotb.getProvinceid() != null) {
                    merchantUser1.setMerUserPro(proxyuserinfotb.getProvinceid().toString());//省份
                }
                if (proxyuserinfotb.getCityid() != null) {
                    merchantUser1.setMerUserCity(proxyuserinfotb.getCityid().toString());//城市
                }

                if (proxyuserinfotb.getAddress() != null) {
                    merchantUser1.setMerUserAdds(proxyuserinfotb.getAddress());//地址
                }

                merchantUser1.setMerCode(merchant.getMerCode());//商户编号
                merchantUser1.setMerUserFlg("1100");
                merchantUser1.setActivate(ActivateEnum.ENABLE.getCode());//0启用，1停用
                merchantUser1.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
                merchantUser1.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
                merchantUser1.setPayinfoFlg("0");//是否弹框提示
                merchantUser1.setMerUserRemark(proxyuserinfotb.getRemarks());//备注
                merchantUser1.setSource(SourceEnum.TRANSFER.getCode());
                merchantUser1.setCityCode(citycode);

                MerchantUserExtend merchantUserExtend1 = new MerchantUserExtend();
                merchantUserExtend1.setOldUserId(Long.toString(proxyuserinfotb.getUserid()));//老系统用户id
                merchantUserExtend1.setOldUserType("2");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
                merchantUserExtend1.setUserCode(merchantUser1.getUserCode());//对应 新系统用户编号
                merchantUser1.setMerUserExtend(merchantUserExtend1);
                merUserList.add(merchantUser1);

            }
        }
        merchant.setMerUser(merchantUser);//生成商户用户表记录  (管理员)
        merchant.setMerUserList(merUserList);//生成商户用户表记录  (操作员)
        //***********************************   8、 生成pos信息     ********************//

        //if ("16156".equals(proxyid)) { //特殊处理  青岛港国际股份有限公司 groupid='1000004504'  下面网点 散货区 proxyid='16156',下挂三个pos
        List<Pos> posList = new ArrayList<Pos>();
        List<Proxypostb> proxypostblist = proxypostbMapper.findProxypostbListById(proxyid);
        for (Proxypostb proxypostb : proxypostblist) {
            Biposinfotb biposinfotb = biposinfotbMapper.findBiposinfotbByPosId(proxypostb.getPosid());
            Pos pos = new Pos();
            pos.setCode(proxypostb.getPosid());//pos编号
            pos.setMerCode(merchant.getMerCode());//绑定的商户编号
            pos.setMerName(merchant.getMerName());//绑定的商户名称

            if (cityEnum != null) { //通卡
                pos.setCityCode(cityEnum.getCityCode());//城市编号
                pos.setCityName(cityEnum.getCityName());//城市名称
                pos.setProvinceCode(cityEnum.getProCode());//省份编号
                pos.setProvinceName(cityEnum.getProName());//省份名称
            }

            pos.setBind(BindEnum.ENABLE.getCode());//绑定
            try {
                if (biposinfotb.getLasttime() != null) {
                    pos.setUpdateDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(biposinfotb.getLasttime()));
                }

            }
            catch (ParseException e) {
                e.printStackTrace();
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
                if (biposinfotb.getVersion().intValue() == 1 || "F".equals(biposinfotb.getVersion().toString())) {
                    pos.setVersion("V60");
                } else if (biposinfotb.getVersion().intValue() == 2) {
                    pos.setVersion("E充卡");
                } else if (biposinfotb.getVersion().intValue() == 4) {
                    pos.setVersion("V61");
                } else {
                    pos.setVersion(biposinfotb.getVersion().toString());//pos版本号
                }
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
            posList.add(pos);
        }
        merchant.setPosList(posList);

        //}

        //***********************************   9、 生成商户业务信息表MER_RATE_SUPPLEMENT数据   ********************//
        List<MerRateSupplement> merRateSupplementList = new ArrayList<MerRateSupplement>();
        if (proxyinfotb.getRateamt() != null) {
            if (StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
                MerRateSupplement merRateSupplement = new MerRateSupplement();
                merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务
                merRateSupplement.setMerCode(merchant.getMerCode());
                merRateSupplementList.add(merRateSupplement);
            }
        }

        if (proxyinfotb.getPayflag() != null) {
            if (proxyinfotb.getPayflag().intValue() == 1) {
                MerRateSupplement merRateSupplement = new MerRateSupplement();
                merRateSupplement.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());//消费业务
                merRateSupplement.setMerCode(merchant.getMerCode());//商户编号
                merRateSupplementList.add(merRateSupplement);
            }
        }
        merchant.setMerRateSpmtList(merRateSupplementList);

        //***********************************   10、 生成商户业务费率表MER_BUS_RATE数据  ********************//

        List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();

        if (proxyinfotb.getRateamt() != null) {
            if (StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
                MerBusRate merBusRate = new MerBusRate();
                merBusRate.setMerCode(merchant.getMerCode());//商户编号
                merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务

                if (cityEnum != null) { //通卡
                    merBusRate.setProviderCode(cityEnum.getYktCode());
                }
                merBusRate.setRate(new BigDecimal(0));//数值
                merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型:1.笔数;2.千分比
                merBusRateList.add(merBusRate);
            }
        }

        if (proxyinfotb.getPayflag() != null) {
            if (proxyinfotb.getPayflag().intValue() == 1) {
                MerBusRate merBusRate = new MerBusRate();
                merBusRate.setMerCode(merchant.getMerCode());//商户编号
                merBusRate.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());//消费业务
                if (cityEnum != null) { //通卡
                    merBusRate.setProviderCode(cityEnum.getYktCode());
                }

                merBusRate.setRate(new BigDecimal(0));//数值
                merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型:1.笔数;2.千分比
                merBusRateList.add(merBusRate);
            }
        }
        merchant.setMerBusRateList(merBusRateList);

        //***********************************   11、 生成主账户表ACCOUNT记录  ********************//
//        if (merchant.getMerState() == "0" || merchant.getMerState() == "2") { //当商户  0未审核  和 2审核不通过 时 不需要产生账户记录 
//        } else {
//            
//        }
        
        merchant.setFundType("0");//资金类别 0：资金 1：授信
        merchantService.addMerchantInfo(merchant);//生成商户相关信息
        //proxyinfotbMapper.updateTransFlag(proxyid);
        //***********************************14.更新老系统表迁移记录*****************************//
        int result = proxyinfotbMapper.updateTransFlag(proxyid);
        if (result <= 0) {
            throw new RuntimeException();
        }
        response.setResponseEntity(merchant.getMerCode());
        response.setMessage(merchant.getMerType());

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

    /**
     * 将pos迁移成 商户（连锁直营或者连锁加盟商户）
     */
    @Override
    @Transactional
    public DodopalResponse<String> childrenMerchantTransferByPos(String proxyid, String groupid, String posid, String mertype, String citycode, String batchId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);

        //***********************************    1、查询需要迁移的网点信息   **************************//

        //***********************************    1.1、查询需要迁移的网点信息   ********************//
        Proxyinfotb proxyinfotb = proxyinfotbMapper.findProxyinfotbById(proxyid);

        if (proxyinfotb == null) {
            logger.error("网点id：" + proxyid + "对应的网点信息不存在。");
            throw new DDPException(ResponseCode.UNKNOWN_ERROR);
        }

        //***********************************    1.3、根据pos查询pos信息   ********************//
        Proxypostb proxypostb = proxypostbMapper.findProxypostbByPosid(posid);

        if (proxypostb == null) {
            logger.error("网点id：" + proxyid + "对应的网点绑定pos信息不存在。");
        }

        //***********************************    2、生成商户信息表merchant   ********************//
        Merchant merchant = new Merchant();
        merchant.setMerCode(generateMerCode("0")); //正式商户 生成商户编号

        if (StringUtils.isNotBlank(proxypostb.getRemarks())) {//商户名称
            merchant.setMerName(proxypostb.getRemarks()); //pos备注作为商户名称
        } else {
            merchant.setMerName(proxypostb.getPosid());//pos号作为商户名称
        }
        merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除
        merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
        merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
        merchant.setSource(SourceEnum.TRANSFER.getCode());
        TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
        if (cityEnum != null) { //通卡
            merchant.setMerPro(cityEnum.getProCode());// 省份
        }
        merchant.setMerCity(citycode); // 城市
        merchant.setMerType(mertype);

        //        merType = proxyinfotb.getProxytypeid().toString();//网点类型
        //
        //
        //        if ("1".equals(merType) || "2".equals(merType) || "4".equals(merType)) { //1 个人,2 企业,4 加盟
        //            merchant.setMerType("14");//连锁加盟网点
        //        } else { //直营店  ，连锁直营店
        //            merchant.setMerType("13"); //连锁直营网点
        //        }

        if (proxyinfotb.getProxyaddress() != null) {
            merchant.setMerAdds(proxyinfotb.getProxyaddress()); //代理网点地址
        }

        if (proxyinfotb.getProxyfax() != null) {
            merchant.setMerFax(proxyinfotb.getProxyfax());//传真
        }
        try {
            if (proxyinfotb.getRegisttime() != null) {
                merchant.setMerRegisterDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(proxyinfotb.getRegisttime()));//注册时间 
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        } //注册时间
        if (proxyinfotb.getStatus() != null) {
            String merState = proxyinfotb.getStatus().toString();//审核状态
            if ("0".equals(merState) || "3".equals(merState)) {
                merchant.setMerState("0"); //未审核
            } else if ("4".equals(merState)) {
                merchant.setMerState("2"); //审核不通过
            } else {
                merchant.setMerState("1"); //审核通过
            }

            if ("2".equals(merState)) { //启用停用状态
                merchant.setActivate("1");
            } else {
                merchant.setActivate("0");
            }
        }

        try {
            if (proxyinfotb.getEdittime() != null) {
                merchant.setMerDeactivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(proxyinfotb.getEdittime()));
            }
            //停启用时间
            merchant.setUpdateUser(proxyinfotb.getEdituser());//操作人
            if (proxyinfotb.getApprovaldate() != null) {
                merchant.setMerStateDate(new SimpleDateFormat("yyyy-MM-dd").parse(proxyinfotb.getApprovaldate()));//审批日期
            }
            if (proxyinfotb.getBankaccountname() != null) {
                merchant.setMerBankUserName(proxyinfotb.getBankaccountname());//开户行账户名称
            }
            if (proxyinfotb.getBankacc() != null) {
                merchant.setMerBankAccount(proxyinfotb.getBankacc());//开户行账号
            }
            if (proxyinfotb.getBankaccname() != null) {
                merchant.setMerBankName(proxyinfotb.getBankaccname());//银行名称
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        // 查找上级商户号
        MerchantExtend merParent = merchantExtendMapper.findMerchantExtend(groupid, "1");
        if (merParent != null) {
            merchant.setMerParentCode(merParent.getMerCode());
        }
        //***********************************   3、生成商户补充信息表MER_DDP_INFO数据     4.生成直营网点 补充信息表mer_auto_amt数据   是否自动分配额度********************//
        MerDdpInfo merDdpInfo = new MerDdpInfo();
        //MerAutoAmt merAutoAmt = new MerAutoAmt();
        merDdpInfo.setMerCode(merchant.getMerCode());//商户编号
        merDdpInfo.setSettlementType("0");//结算类型  0：天数 1：笔数 2：金额 
        merDdpInfo.setSettlementThreshold(new BigDecimal(7));//结算参数
        merDdpInfo.setIsAutoDistribute("2");// 是否自动分配额度      0 是  ， 1否,2 共享额度

        /* if ("14".equals(merchant.getMerType())) {//连锁加盟网点 记录额度来源，默认都是自己购买
             merDdpInfo.setLimitSource("0"); //0：自己购买，1：上级分配；默认自己购买
         } else if ("13".equals(merchant.getMerType())) { //连锁直营网点记录 是否自动分配额度，和记录额度阀值和自动分配的额度值
             Proxyautoaddeduoperinfotb proxyautoaddeduoperinfotb = proxyautoaddeduoperinfotbMapper.findProxyautoaddeduoperinfotb(proxyid);
             if (proxyautoaddeduoperinfotb != null) {
                 if (proxyautoaddeduoperinfotb.getAutoAddTriggerLimit() != null && proxyautoaddeduoperinfotb.getAutoAddTriggerLimit().intValue() > 0) {
                     merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度     0 是  ， 1否
                     merAutoAmt.setMerCode(merchant.getMerCode());//商户编号
                     merAutoAmt.setAutoLimitThreshold(proxyautoaddeduoperinfotb.getAutoAddArriveLimit());//添加额度值
                     merAutoAmt.setLimitThreshold(proxyautoaddeduoperinfotb.getAutoAddTriggerLimit());//额度阀值
                 } else {
                     merDdpInfo.setIsAutoDistribute("2");// 是否自动分配额度      0 是  ， 1否,2 共享额度
                 }
             } else {
                 if (proxylimitinfotb != null) {
                    if (proxylimitinfotb.getAutoAddTriggerLimit() != null && proxylimitinfotb.getAutoAddTriggerLimit().intValue() > 0 &&proxylimitinfotb.getAutoAddArriveLimit() != null && proxylimitinfotb.getAutoAddArriveLimit().intValue() > 0) {
                         merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度     0 是  ， 1否
                         merAutoAmt.setMerCode(merchant.getMerCode());//商户编号
                         merAutoAmt.setAutoLimitThreshold(proxylimitinfotb.getAutoAddArriveLimit());//添加额度值
                         merAutoAmt.setLimitThreshold(proxylimitinfotb.getAutoAddTriggerLimit());//额度阀值
                       
                     } else {
                         merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否 
                     }
                 } else {
                     merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否 
                 }

             }

         }*/

        // merchant.setMerAutoAmt(merAutoAmt);
        merchant.setMerDdpInfo(merDdpInfo);//生成商户补充信息表
        //***********************************   5、 生成商户信息扩展表MERCHANT_EXTEND数据     ********************//
        MerchantExtend merchantExtend = new MerchantExtend();
        merchantExtend.setMerCode(merchant.getMerCode());//商户编号
        merchantExtend.setOldMerchantId(proxyid);//老系统网点id
        merchantExtend.setOldMerchantType("2");//老系统商户类型 集团1，网点2，商户3
        merchant.setMerExtend(merchantExtend);//生成商户扩展表记录
        //***********************************   6、 生成用户信息表MERCHANT_USER数据     ********************//
        MerchantUser merchantUser = null;

        Biposinuserstb biposinuserstb = biposinuserstbMapper.findBiposinuserstbByPosId(posid); //
        if (biposinuserstb != null && StringUtils.isNotBlank(biposinuserstb.getUserid().toString())) {
            Sysuserstb sysuserstb = sysuserstbMapper.findSysUserstb(biposinuserstb.getUserid().toString());
            merchantUser = new MerchantUser();
            merchantUser.setUserCode(generateMerUserCode(UserClassifyEnum.MERCHANT));//正式商户   生成商户用户编号
            merchantUser.setMerUserName(sysuserstb.getLoginid());//登录名
            merchantUser.setMerUserPwd(sysuserstb.getPassword());//密码
            if (sysuserstb.getIdentitytype() != null) {
                if ("1000000001".equals(sysuserstb.getIdentitytype().toString())) {//证件类型
                    merchantUser.setMerUserIdentityType("0");
                } else if ("1000000002".equals(sysuserstb.getIdentitytype().toString())) {
                    merchantUser.setMerUserIdentityType("2");
                } else if ("1000000005".equals(sysuserstb.getIdentitytype().toString())) {
                    merchantUser.setMerUserIdentityType("1");
                }
            }
            if (sysuserstb.getIdentityid() != null) {
                merchantUser.setMerUserIdentityNum(sysuserstb.getIdentityid());//证件号码
            }
            if (sysuserstb.getUsername() != null) {
                merchantUser.setMerUserNickname(sysuserstb.getUsername());//用户真实姓名
            }
            if (sysuserstb.getSex() != null) {
                merchantUser.setMerUserSex(("0".equals(sysuserstb.getSex().toString())) ? "1" : "0");//老系统：1:男 0:女;新系统：1：女0：男
            }
            if (sysuserstb.getEmail() != null) {
                merchantUser.setMerUserEmall(sysuserstb.getEmail());//邮箱
            }
            if (sysuserstb.getMobiltel() != null) {
                merchantUser.setMerUserMobile(sysuserstb.getMobiltel());//移动电话
            }else{
                merchantUser.setMerUserMobile("000000");
            }
            if (sysuserstb.getTel() != null) {
                merchantUser.setMerUserTelephone(sysuserstb.getTel());//固定电话
            }

            if (sysuserstb.getProvinceid() != null) {
                merchantUser.setMerUserPro(sysuserstb.getProvinceid().toString());//省份
            }
            if (sysuserstb.getCityid() != null) {
                merchantUser.setMerUserCity(sysuserstb.getCityid().toString());//城市
            }

            if (sysuserstb.getAddress() != null) {
                merchantUser.setMerUserAdds(sysuserstb.getAddress());//地址
            }

            merchantUser.setMerCode(merchant.getMerCode());//商户编号
            merchantUser.setMerUserFlg("1000");//管理员

            merchantUser.setActivate("0");//0启用，1停用
            merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
            merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
            merchantUser.setPayinfoFlg("0");//是否弹框提示
            merchantUser.setSource(SourceEnum.TRANSFER.getCode());

            //***********************************   6.1、 生成用户信息扩展表MERCHANT_USER_EXTEND数据     ********************//
            MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
            merchantUserExtend.setOldUserId(sysuserstb.getUserid());//老系统用户id
            merchantUserExtend.setOldUserType("2");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
            merchantUserExtend.setUserCode(merchantUser.getUserCode());//对应 新系统用户编号
            merchantUser.setMerUserExtend(merchantUserExtend); //商户用户扩展表信息

        }

        merchant.setMerUser(merchantUser);//生成商户用户表记录

        //***********************************   7、 生成pos信息     ********************//

        List<Pos> posList = new ArrayList<Pos>();

        if (proxypostb != null) {
            Biposinfotb biposinfotb = biposinfotbMapper.findBiposinfotbByPosId(posid);
            Pos pos = new Pos();
            pos.setCode(proxypostb.getPosid());//pos编号
            pos.setMerCode(merchant.getMerCode());//绑定的商户编号
            pos.setMerName(merchant.getMerName());//绑定的商户名称

            if (cityEnum != null) {
                pos.setCityCode(cityEnum.getCityCode());//城市编号
                pos.setCityName(cityEnum.getCityName());//城市名称
                pos.setProvinceCode(cityEnum.getProCode());//省份编号
                pos.setProvinceName(cityEnum.getProName());//省份名称
            }

            pos.setBind(BindEnum.ENABLE.getCode());//绑定
            try {
                if (biposinfotb.getLasttime() != null) {
                    pos.setUpdateDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(biposinfotb.getLasttime()));
                }

            }
            catch (ParseException e) {
                e.printStackTrace();
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
                if (biposinfotb.getVersion().intValue() == 1 || "F".equals(biposinfotb.getVersion().toString())) {
                    pos.setVersion("V60");
                } else if (biposinfotb.getVersion().intValue() == 2) {
                    pos.setVersion("E充卡");
                } else if (biposinfotb.getVersion().intValue() == 4) {
                    pos.setVersion("V61");
                } else {
                    pos.setVersion(biposinfotb.getVersion().toString());//pos版本号
                }
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
            posList.add(pos);
        }

        merchant.setPosList(posList);
        //***********************************   8、 生成商户业务信息表MER_RATE_SUPPLEMENT数据   ********************//
        List<MerRateSupplement> merRateSupplementList = new ArrayList<MerRateSupplement>();
        if (proxyinfotb.getRateamt() != null) {
            if (StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
                MerRateSupplement merRateSupplement = new MerRateSupplement();
                merRateSupplement.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务
                merRateSupplement.setMerCode(merchant.getMerCode());
                merRateSupplementList.add(merRateSupplement);
            }
        }

        if (proxyinfotb.getPayflag() != null) {
            if (proxyinfotb.getPayflag().intValue() == 1) {
                MerRateSupplement merRateSupplement = new MerRateSupplement();
                merRateSupplement.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());//消费业务
                merRateSupplement.setMerCode(merchant.getMerCode());//商户编号
                merRateSupplementList.add(merRateSupplement);
            }
        }
        merchant.setMerRateSpmtList(merRateSupplementList);
        //***********************************   9、 生成商户业务费率表MER_BUS_RATE数据  ********************//
        List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
        if (proxyinfotb.getRateamt() != null) {
            if (StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
                MerBusRate merBusRate = new MerBusRate();
                merBusRate.setMerCode(merchant.getMerCode());//商户编号
                merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务
                if (cityEnum != null) { //通卡
                    merBusRate.setProviderCode(cityEnum.getYktCode());
                }

                merBusRate.setRate(new BigDecimal(0));//数值
                merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型:1.笔数;2.千分比
                merBusRateList.add(merBusRate);
            }
        }

        if (proxyinfotb.getPayflag() != null) {
            if (proxyinfotb.getPayflag().intValue() == 1) {
                MerBusRate merBusRate = new MerBusRate();
                merBusRate.setMerCode(merchant.getMerCode());//商户编号
                merBusRate.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());//消费业务
                //TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
                if (cityEnum != null) { //通卡
                    merBusRate.setProviderCode(cityEnum.getYktCode());
                }

                merBusRate.setRate(new BigDecimal(0));//数值
                merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型:1.笔数;2.千分比
                merBusRateList.add(merBusRate);
            }
        }
        merchant.setMerBusRateList(merBusRateList);

        //***********************************   10、 生成主账户表ACCOUNT记录  ********************//
//        if (merchant.getMerState() == "0" || merchant.getMerState() == "2") { //当商户  0未审核  和 2审核不通过 时 不需要产生账户记录 
//        } else {
//           
//        }
        merchant.setFundType("0");//资金类别 0：资金 1：授信
        merchantService.addMerchantInfo(merchant);//生成商户相关信息
        //proxyinfotbMapper.updateTransFlag(proxyid);
        //***********************************14.更新老系统表迁移记录*****************************//
        int result = proxyinfotbMapper.updateTransFlag(proxyid);
        if (result <= 0) {
            throw new RuntimeException();
        }
        response.setResponseEntity(merchant.getMerCode());
        response.setMessage(merchant.getMerType());

        return response;
    }
}
