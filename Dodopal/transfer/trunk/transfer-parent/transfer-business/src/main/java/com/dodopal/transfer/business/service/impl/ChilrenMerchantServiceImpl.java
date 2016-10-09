package com.dodopal.transfer.business.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.dao.AccountControlMapper;
import com.dodopal.transfer.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfer.business.dao.AccountFundMapper;
import com.dodopal.transfer.business.dao.AccountMapper;
import com.dodopal.transfer.business.dao.BiposinfotbMapper;
import com.dodopal.transfer.business.dao.ChilrenMerchantMapper;
import com.dodopal.transfer.business.dao.MerAutoAmtMapper;
import com.dodopal.transfer.business.dao.MerBusRateMapper;
import com.dodopal.transfer.business.dao.MerDdpInfoMapper;
import com.dodopal.transfer.business.dao.MerRateSupplementMapper;
import com.dodopal.transfer.business.dao.MerchantExtendMapper;
import com.dodopal.transfer.business.dao.MerchantMapper;
import com.dodopal.transfer.business.dao.MerchantUserExtendMapper;
import com.dodopal.transfer.business.dao.MerchantUserMapper;
import com.dodopal.transfer.business.dao.PayTranMapper;
import com.dodopal.transfer.business.dao.PosMapper;
import com.dodopal.transfer.business.dao.PosMerTbMapper;
import com.dodopal.transfer.business.dao.ProxyLimitInfoTbMapper;
import com.dodopal.transfer.business.dao.ProxyautoaddeduoperinfotbMapper;
import com.dodopal.transfer.business.dao.ProxyinfotbMapper;
import com.dodopal.transfer.business.dao.ProxypostbMapper;
import com.dodopal.transfer.business.dao.ProxyuserinfotbMapper;
import com.dodopal.transfer.business.dao.SysuserstbMapper;
import com.dodopal.transfer.business.model.old.Biposinfotb;
import com.dodopal.transfer.business.model.old.Proxyautoaddeduoperinfotb;
import com.dodopal.transfer.business.model.old.Proxyinfotb;
import com.dodopal.transfer.business.model.old.Proxylimitinfotb;
import com.dodopal.transfer.business.model.old.Proxypostb;
import com.dodopal.transfer.business.model.old.Proxyuserinfotb;
import com.dodopal.transfer.business.model.target.Account;
import com.dodopal.transfer.business.model.target.AccountControl;
import com.dodopal.transfer.business.model.target.AccountControllerDefault;
import com.dodopal.transfer.business.model.target.AccountFund;
import com.dodopal.transfer.business.model.target.MerAutoAmt;
import com.dodopal.transfer.business.model.target.MerBusRate;
import com.dodopal.transfer.business.model.target.MerDdpInfo;
import com.dodopal.transfer.business.model.target.MerRateSupplement;
import com.dodopal.transfer.business.model.target.Merchant;
import com.dodopal.transfer.business.model.target.MerchantExtend;
import com.dodopal.transfer.business.model.target.MerchantUser;
import com.dodopal.transfer.business.model.target.MerchantUserExtend;
import com.dodopal.transfer.business.model.target.Pos;
import com.dodopal.transfer.business.model.target.PosMerTb;
import com.dodopal.transfer.business.service.ChilrenMerchantService;

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
    PayTranMapper payTranMapper;

    @Autowired
    ChilrenMerchantMapper chilrenMerchantMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    BiposinfotbMapper biposinfotbMapper;

    @Autowired
    MerchantUserMapper merchantUserMapper;

    @Autowired
    MerchantMapper merchantMapper;

    @Autowired
    MerDdpInfoMapper merDdpInfoMapper;

    @Autowired
    MerchantExtendMapper merchantExtendMapper;

    @Autowired
    MerchantUserExtendMapper merchantUserExtendMapper;

    @Autowired
    PosMerTbMapper posMerTbMapper;

    @Autowired
    PosMapper posMapper;

    @Autowired
    MerRateSupplementMapper merRateSupplementMapper;

    @Autowired
    MerBusRateMapper merBusRateMapper;

    @Autowired
    AccountFundMapper accountFundMapper;

    @Autowired
    AccountControlMapper accountControlMapper;
    @Autowired
    AccountControllerDefaultMapper accountControllerDefaultMapper;
    @Autowired
    SysuserstbMapper sysuserstbMapper;
    @Autowired
    MerAutoAmtMapper merAutoAmtMapper;

    @Autowired
    ProxyautoaddeduoperinfotbMapper proxyautoaddeduoperinfotbMapper;
    private static final String YKT_CODE ="100000"; //北京一卡通ID
    @Override
    @Transactional
    public DodopalResponse<String> childrenMerchantTransfer(String proxyid, String groupid, String batchId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        response.setCode(ResponseCode.SUCCESS);
        
        Merchant merchant = new Merchant();
        String merType=null;
        //***********************************    1、查询需要迁移的网点信息   **************************//

        //***********************************    1.1、查询需要迁移的网点信息   ********************//
            Proxyinfotb proxyinfotb = proxyinfotbMapper.findProxyinfotbById(proxyid);

            if (proxyinfotb == null) {
                logger.error("网点id：" + proxyid + "对应的网点信息不存在。");
                throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            }

            //***********************************    1.2、查询需要迁移的网点的用户信息   ********************//
            Proxyuserinfotb proxyuserinfotb = proxyuserinfotbMapper.findProxyuserinfotbByID(proxyid);
            if (proxyuserinfotb == null) {
                logger.error("网点id：" + proxyid + "对应的网点用户信息不存在。");
                throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            }

            //***********************************    1.3、查询需要迁移的网点充值额度信息   ********************//
            Proxylimitinfotb proxylimitinfotb = proxyLimitInfoTbMapper.findProxyLimitInfoTbById(proxyid);
            if (proxylimitinfotb == null) {
                logger.error("网点id：" + proxyid + "对应的网点充值额度信息不存在。");
                //throw new DDPException(ResponseCode.UNKNOWN_ERROR);
            }

            //***********************************    1.3、查询需要迁移的网点绑定pos信息   ********************//
            Proxypostb proxypostb = proxypostbMapper.findProxypostbById(proxyid);
            if (proxypostb == null) {
                logger.error("网点id：" + proxyid + "对应的网点绑定pos信息不存在。");
            }

            //***********************************    2、生成商户信息表merchant   ********************//

            merchant.setMerCode(generateMerCode("0")); //正式商户 生成商户编号
            merchant.setMerName(proxyinfotb.getProxyname());//商户名称
            merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除
            merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
            merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
            merchant.setSource(SourceEnum.TRANSFER.getCode());
            merchant.setMerPro("1000"); // 省份
            merchant.setMerCity("1110"); // 城市
            merType= proxyinfotb.getProxytypeid().toString();//网点类型
            if ("5".equals(merType)|| "3".equals(merType)) {
                merchant.setMerType("13");//连锁直营网点
            } else if ("4".equals(merType)) {
                merchant.setMerType("14"); //连锁加盟网点
            }
            if (proxyinfotb.getProxyaddress() != null) {
                merchant.setMerAdds(proxyinfotb.getProxyaddress()); //代理网点地址
            }
            if (proxyinfotb.getProxytel() != null) { //联系电话
                merchant.setMerLinkUserMobile(proxyinfotb.getProxytel());
            } else if(proxyuserinfotb.getMobiltel() != null) {
            	merchant.setMerLinkUserMobile(proxyuserinfotb.getMobiltel());
            }
            if(proxyuserinfotb.getUsername() != null) { // 联系人
            	merchant.setMerLinkUser(proxyuserinfotb.getUsername());
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

            merchantMapper.addMerchant(merchant);

            //***********************************   3、生成商户补充信息表MER_DDP_INFO数据     4.生成直营网点 补充信息表mer_auto_amt数据   是否自动分配额度********************//
            MerDdpInfo merDdpInfo = new MerDdpInfo();
            merDdpInfo.setMerCode(merchant.getMerCode());//商户编号
            merDdpInfo.setSettlementType("0");//结算类型  0：天数 1：笔数 2：金额 
            merDdpInfo.setSettlementThreshold(new BigDecimal(7));//结算参数
            if ("14".equals(merchant.getMerType())) {//连锁加盟网点 记录额度来源，默认都是自己购买
                merDdpInfo.setLimitSource("0"); //0：自己购买，1：上级分配；默认自己购买
            } else if ("13".equals(merchant.getMerType())) { //连锁直营网点记录 是否自动分配额度，和记录额度阀值和自动分配的额度值
                Proxyautoaddeduoperinfotb proxyautoaddeduoperinfotb = proxyautoaddeduoperinfotbMapper.findProxyautoaddeduoperinfotb(proxyid);
                if (proxyautoaddeduoperinfotb != null) {
                    if (proxyautoaddeduoperinfotb.getAutoAddTriggerLimit() != null && proxyautoaddeduoperinfotb.getAutoAddTriggerLimit().intValue() > 0) {
                        merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度     0 是  ， 1否
                        MerAutoAmt merAutoAmt = new MerAutoAmt();
                        merAutoAmt.setMerCode(merchant.getMerCode());//商户编号
                        merAutoAmt.setAutoLimitThreshold(proxyautoaddeduoperinfotb.getAutoAddArriveLimit());//添加额度值
                        merAutoAmt.setLimitThreshold(proxyautoaddeduoperinfotb.getAutoAddTriggerLimit());//额度阀值
                        merAutoAmtMapper.addMerAutoAmt(merAutoAmt);
                    } else {
                        merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否
                    }
                } else {
                    if (proxylimitinfotb != null) {
                       if (proxylimitinfotb.getAutoAddTriggerLimit() != null && proxylimitinfotb.getAutoAddTriggerLimit().intValue() > 0 &&proxylimitinfotb.getAutoAddArriveLimit() != null && proxylimitinfotb.getAutoAddArriveLimit().intValue() > 0) {
                            merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度     0 是  ， 1否
                            MerAutoAmt merAutoAmt = new MerAutoAmt();
                            merAutoAmt.setMerCode(merchant.getMerCode());//商户编号
                            merAutoAmt.setAutoLimitThreshold(proxylimitinfotb.getAutoAddArriveLimit());//添加额度值
                            merAutoAmt.setLimitThreshold(proxylimitinfotb.getAutoAddTriggerLimit());//额度阀值
                            merAutoAmtMapper.addMerAutoAmt(merAutoAmt);
                        } else {
                            merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否 
                        }
                    } else {
                        merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否 
                    }

                }

            }

            merDdpInfoMapper.addMerDdpInfo(merDdpInfo);

            //***********************************   5、 生成商户信息扩展表MERCHANT_EXTEND数据     ********************//
            MerchantExtend merchantExtend = new MerchantExtend();
            merchantExtend.setMerCode(merchant.getMerCode());//商户编号
            merchantExtend.setOldMerchantId(proxyid);//老系统网点id
            merchantExtend.setOldMerchantType("2");//老系统商户类型 集团1，网点2，商户3
            merchantExtendMapper.addMerchantExtend(merchantExtend);

            //***********************************   6、 生成用户信息表MERCHANT_USER数据     ********************//
            MerchantUser merchantUser = new MerchantUser();
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
                //merchantUser.setMerUserIdentityType(proxyuserinfotb.getIdentitytypeid().toString());//证件类型
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
            if (proxyuserinfotb.getStatus() != null) {////老系统：0为管理员，1为附加的成员；新系统：1000为管理员，1100非管理员
                if (proxyuserinfotb.getStatus().intValue() == 0) {
                    merchantUser.setMerUserFlg("1000");
                } else {
                    merchantUser.setMerUserFlg("1100");
                }
                // merchantUser.setMerUserFlg(("0".equals(proxyuserinfotb.getStatus().toString())) ? "1000" : "1100");//老系统：0为管理员，1为附加的成员；新系统：1000为管理员，1100非管理员
            }
            merchantUser.setActivate("0");//0启用，1停用
            merchantUser.setMerUserType(MerUserTypeEnum.MERCHANT.getCode());//用户类型
            merchantUser.setMerUserState(MerStateEnum.THROUGH.getCode());//审核状态
            merchantUser.setPayinfoFlg("0");//是否弹框提示
            merchantUser.setMerUserRemark(proxyuserinfotb.getRemarks());//备注
            merchantUser.setSource(SourceEnum.TRANSFER.getCode());
            if (proxyuserinfotb.getLastchoosecity() != null) {
                merchantUser.setCityCode("1110");//proxyuserinfotb.getLastchoosecity().substring(proxyuserinfotb.getLastchoosecity().length() - 4, proxyuserinfotb.getLastchoosecity().length()));//上一次选择的城市
            }

            merchantUserMapper.addMerchantUser(merchantUser);

            //***********************************   7、 生成用户信息扩展表MERCHANT_USER_EXTEND数据     ********************//
            MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
            merchantUserExtend.setOldUserId(Long.toString(proxyuserinfotb.getUserid()));//老系统用户id
            merchantUserExtend.setOldUserType("2");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
            merchantUserExtend.setUserCode(merchantUser.getUserCode());//对应 新系统用户编号
            merchantUserExtendMapper.addMerchantUserExtend(merchantUserExtend);

            //***********************************   8、 生成pos信息、pos绑定中间表数据     ********************//
            if (proxypostb != null) {
                PosMerTb posMerTb = new PosMerTb();
                posMerTb.setCode(proxypostb.getPosid());//pos编号
                posMerTb.setMerCode(merchant.getMerCode());//商户编号
                posMerTb.setComments(proxypostb.getRemarks());//备注
                posMerTbMapper.addPosMerTb(posMerTb);

                Biposinfotb biposinfotb = biposinfotbMapper.findBiposinfotbByPosId(proxypostb.getPosid());
                Pos pos = new Pos();
                pos.setCode(posMerTb.getCode());//pos编号
                pos.setMerCode(merchant.getMerCode());//绑定的商户编号
                pos.setMerName(merchant.getMerName());//绑定的商户名称
                pos.setCityCode("1110");//城市编号
                pos.setCityName("北京");//城市名称
                pos.setProvinceCode("1000");//省份编号
                pos.setProvinceName("北京市");//省份名称
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
                posMapper.addPos(pos);
            }
            //***********************************   9、 生成商户业务信息表MER_RATE_SUPPLEMENT数据   ********************//
            if (proxyinfotb.getRateamt() != null) {
                if (StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
                    MerRateSupplement merRateSupplement = new MerRateSupplement();
                    merRateSupplement.setRateCode("01");//充值业务
                    merRateSupplement.setMerCode(merchant.getMerCode());
                    merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
                }
            }

            if (proxyinfotb.getPayflag() != null) {
                if (proxyinfotb.getPayflag().intValue() == 1) {
                    MerRateSupplement merRateSupplement = new MerRateSupplement();
                    merRateSupplement.setRateCode("03");//消费业务
                    merRateSupplement.setMerCode(merchant.getMerCode());//商户编号
                    merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
                }
            }

            //***********************************   10、 生成商户业务费率表MER_BUS_RATE数据  ********************//

            if (proxyinfotb.getRateamt() != null) {
                if (StringUtils.isNotBlank(proxyinfotb.getRateamt().toString())) {
                    MerBusRate merBusRate = new MerBusRate();
                    merBusRate.setMerCode(merchant.getMerCode());//商户编号
                    merBusRate.setRateCode("01");//充值业务
                    merBusRate.setProviderCode(YKT_CODE);//北京通卡code
                    merBusRate.setRate(new BigDecimal(0));//数值
                    merBusRate.setRateType("2");//费率类型:1.笔数;2.千分比
                    merBusRateMapper.addMerBusRate(merBusRate);
                }
            }

            if (proxyinfotb.getPayflag() != null) {
                if (proxyinfotb.getPayflag().intValue() == 1) {
                    MerBusRate merBusRate = new MerBusRate();
                    merBusRate.setMerCode(merchant.getMerCode());//商户编号
                    merBusRate.setRateCode("03");//消费业务
                    merBusRate.setProviderCode(YKT_CODE);//北京通卡code
                    merBusRate.setRate(new BigDecimal(0));//数值
                    merBusRate.setRateType("2");//费率类型:1.笔数;2.千分比
                    merBusRateMapper.addMerBusRate(merBusRate);
                }
            }

            //***********************************   11、 生成主账户表ACCOUNT记录  ********************//
            if (merchant.getMerState() == "0" || merchant.getMerState() == "2") { //当商户  0未审核  和 2审核不通过 时 不需要产生账户记录 
            } else {
                Account account = new Account();
                String seqId = accountMapper.getSequenceNextId();
                String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;//按规则生成账户编号
                account.setAccountCode(preId);//账户编号
                account.setFundType("0"); // 0：资金 1：授信
                account.setCustomerType("1");//0：个人 1：商户
                account.setCustomerNo(merchant.getMerCode());//商户编号
                accountMapper.addAccount(account);

                //***********************************   12、 生成资金授信账户表ACCOUNT_FUND记录  ********************//
                AccountFund accountFund = new AccountFund();
                accountFund.setAccountCode(account.getAccountCode());//主账户编号
                accountFund.setFundAccountCode("F" + "0" + account.getAccountCode());//资金授信账户编号
//                accountFund.setSumTotalAmount(proxylimitinfotb.getAmtlimit());//购买总额度
                accountFund.setSumTotalAmount(new BigDecimal(0));//购买总额度  // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
//                accountFund.setTotalBalance(proxylimitinfotb.getSurpluslimit());//剩余额度
                accountFund.setTotalBalance(new BigDecimal(0));//剩余额度  // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
//                accountFund.setAvailableBalance(proxylimitinfotb.getSurpluslimit());//可用余额
                accountFund.setAvailableBalance(new BigDecimal(0));//可用余额  // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
                accountFund.setFundType("0");//资金类别 0：资金 1：授信
                accountFund.setState("0");//状态 0 启用
                accountFund.setFrozenAmount(new BigDecimal(0));//冻结金额
                accountFundMapper.addAccountFund(accountFund);
                //***********************************   13、 生成资金账户风控表ACCOUNT_CONTROL数据 ********************//
                AccountControl accountControl = new AccountControl();
                accountControl.setFundAccountCode(accountFund.getFundAccountCode());//资金授信账户编号
                //查询默认的风控值
                AccountControllerDefault accountControllerDefault = accountControllerDefaultMapper.queryControlDefault(merchant.getMerType());//商户类型
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
            }
            proxyinfotbMapper.updateTransFlag(proxyid);
          //***********************************14.更新老系统表迁移记录*****************************//
            // add by qjc 2016.3.18
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
}
