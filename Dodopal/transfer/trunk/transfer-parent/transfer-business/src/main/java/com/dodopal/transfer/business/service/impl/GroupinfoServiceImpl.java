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
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.dao.AccountControlMapper;
import com.dodopal.transfer.business.dao.AccountControllerDefaultMapper;
import com.dodopal.transfer.business.dao.AccountFundMapper;
import com.dodopal.transfer.business.dao.AccountMapper;
import com.dodopal.transfer.business.dao.BiposinfotbMapper;
import com.dodopal.transfer.business.dao.ChilrenMerchantMapper;
import com.dodopal.transfer.business.dao.GroupinfotbMapper;
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
import com.dodopal.transfer.business.dao.ProxyinfotbMapper;
import com.dodopal.transfer.business.dao.ProxypostbMapper;
import com.dodopal.transfer.business.dao.ProxyuserinfotbMapper;
import com.dodopal.transfer.business.model.old.Groupinfotb;
import com.dodopal.transfer.business.model.old.Groupuserinfotb;
import com.dodopal.transfer.business.model.target.Account;
import com.dodopal.transfer.business.model.target.AccountControl;
import com.dodopal.transfer.business.model.target.AccountControllerDefault;
import com.dodopal.transfer.business.model.target.AccountFund;
import com.dodopal.transfer.business.model.target.MerBusRate;
import com.dodopal.transfer.business.model.target.MerDdpInfo;
import com.dodopal.transfer.business.model.target.MerRateSupplement;
import com.dodopal.transfer.business.model.target.Merchant;
import com.dodopal.transfer.business.model.target.MerchantExtend;
import com.dodopal.transfer.business.model.target.MerchantUser;
import com.dodopal.transfer.business.model.target.MerchantUserExtend;
import com.dodopal.transfer.business.service.GroupinfoService;
@Service
public class GroupinfoServiceImpl implements GroupinfoService{
	private Logger logger = LoggerFactory.getLogger(GroupinfoServiceImpl.class);
	 @Autowired
	 GroupinfotbMapper groupinfotbMapper;
	 
	 @Autowired
	 ChilrenMerchantMapper chilrenMerchantMapper;
	 
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
	  /**
	   * 北京集团迁移相关信息数据
	   */
 	@Override
    @Transactional
    public DodopalResponse<String> groupInfoTranfer(String groupid) {
 		 DodopalResponse<String> response = new DodopalResponse<String>();
         response.setCode(ResponseCode.SUCCESS);
        //***********************************    1.1、 查询迁移集团的基本信息数据 ********************//
         Groupinfotb groupinfotb = groupinfotbMapper.findGroupinfoByCityNO(groupid);

         //***********************************    1.2、 查询迁移集团的用户信息数据 ********************//
         Groupuserinfotb groupuserinfotb = groupinfotbMapper.findGroupuserinfoByGroupId(groupid);
         if (groupuserinfotb == null) {
             logger.error("网点id：" + groupid + "对应的网点用户信息不存在。");
             throw new DDPException(ResponseCode.UNKNOWN_ERROR);
         }

         //***********************************    1.3、查询集团的关联网点信息  ********************//
//         Groupinproxytb groupinproxytb = groupinfotbMapper.findGroupinproxyByGroupId(groupid);
//         if (groupinproxytb == null) {
//             logger.error("网点id：" + groupid + "对应的关联网点信息不存在。");
//             throw new DDPException(ResponseCode.UNKNOWN_ERROR);
//         }

         //***********************************    1.4、 查询迁移集团绑定的pos信息   ********************//
         /*List<Groupposinfotb> groupposinfotbList = groupinfotbMapper.findGroupposinfoByGroupId(groupid);
         if (groupposinfotbList == null) {
             logger.error("网点id：" + groupid + "对应的网点绑定pos信息不存在。");
             throw new DDPException(ResponseCode.UNKNOWN_ERROR);
         }*/
         //***********************************   2、生成商户信息表merchant     ********************//
           Merchant merchant = new Merchant();
	       AccountFund  accountFund= new AccountFund();
	       MerchantUser  merchantUser = new MerchantUser();
	       MerBusRate merBusRate = new MerBusRate();
	      
	       merchant.setMerCode(generateMerCode("0")); //正式商户 生成商户编号
	       merchant.setMerName(groupinfotb.getGroupname());//集团名称
	       merchant.setDelFlg(DelFlgEnum.NORMAL.getCode());//删除标志:0：正常、1：删除
	       merchant.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());//商户分类
	       merchant.setMerProperty(MerPropertyEnum.STANDARD.getCode());//商户属性
	       merchant.setMerType("12");//连锁商户
	       if(groupinfotb.getAddress() != null){
	    	   merchant.setMerAdds(groupinfotb.getAddress());//地址
	       }
	       if(groupinfotb.getZipcode() !=null){
	    	   merchant.setMerZip(groupinfotb.getZipcode());//邮政编码
	       }
	       if(groupinfotb.getLxperson() !=null){
	    	   merchant.setMerLinkUser(groupinfotb.getLxperson());//联系人
	       }
	       if(groupinfotb.getLxmobile() !=null){
	    	   merchant.setMerLinkUserMobile(groupinfotb.getLxmobile());//手机号
	       }
	      if(groupinfotb.getLxtel() !=null){
	    	  merchant.setMerTelephone(groupinfotb.getLxtel());//电话号码
	      }
	       if(groupinfotb.getStatus() !=null){
	    	   String merState = groupinfotb.getStatus().toString();//状态
	    	   if("0".equals(merState)||"1".equals(merState)){
		           merchant.setMerState("0"); //未审核
		           merchant.setActivate("0");//启用
		       } else if ("2".equals(merState)||"4".equals(merState)) {
		           merchant.setMerState("1"); //已审核
		           merchant.setActivate("1");//停用
		       }else if("3".equals(merState)){
		      	 merchant.setMerState("1"); //审核
		      	 merchant.setActivate("0");//启用
		       }
	       }
	       try {
	    	   if(groupinfotb.getRegisttime() !=null){
	    		   merchant.setMerRegisterDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupinfotb.getRegisttime()));//注册时间  
	    	   }
	       }
	       catch (ParseException e) {
	           e.printStackTrace();
	       }//注册时间
	       try {
	    	   if(groupinfotb.getStarttime() !=null){
	    		   merchant.setMerActivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupinfotb.getStarttime()));
	    	   }
	       }
	       catch (ParseException e) {
	
	           e.printStackTrace();
	       }//激活时间
	       
	      
	      // merchantUser.setMerUserIdentityNum(groupinfotb.getIdentityid());//注册人身份证
	       
	       merchant.setMerCity("1110");
	       merchant.setMerPro("1000");
	       merchant.setSource(SourceEnum.TRANSFER.getCode());
	       try{
	    	   if(groupinfotb.getEdittime() !=null){
	    		   merchant.setMerDeactivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupinfotb.getEdittime()));//停/启用时间
	    	   }
	    	   
	       }catch(ParseException e){
	    	   e.printStackTrace();
	       }
	       if(groupinfotb.getEdituser() !=null){
	    	   merchant.setUpdateUser(groupinfotb.getEdituser());//操作人
	       }
    	   if(groupinfotb.getRateamt() !=null){
    		   merBusRate.setRate(groupinfotb.getRateamt());//充值费率
    	   }
	       merchantMapper.addMerchant(merchant);
         
         //***********************************   3、生成商户补充信息表MER_DDP_INFO数据     ********************//
         MerDdpInfo merDdpInfo = new MerDdpInfo();
         
         merDdpInfo.setMerCode(merchant.getMerCode());//商户编号
         merDdpInfo.setSettlementType("0");//结算类型  0：天数 1：笔数 2：金额 
         merDdpInfo.setSettlementThreshold(new BigDecimal(7));//结算参数
         merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否
         merDdpInfoMapper.addMerDdpInfo(merDdpInfo);

         //***********************************   4、 生成商户信息扩展表MERCHANT_EXTEND数据     ********************//
         MerchantExtend merchantExtend = new MerchantExtend();
         merchantExtend.setMerCode(merchant.getMerCode());//商户编号
         merchantExtend.setOldMerchantId(groupid);//老系统网点id
         merchantExtend.setOldMerchantType("1");//老系统商户类型  集团1，网点2，商户3
         merchantExtendMapper.addMerchantExtend(merchantExtend);

         //***********************************   5、 生成用户信息表MERCHANT_USER数据     ********************//
        // MerchantUser merchantUser = new MerchantUser();
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
         try{
        	 if(groupuserinfotb.getCreatetime() !=null){
        		 merchantUser.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(groupuserinfotb.getCreatetime()));//开通时间
        	 }
	 	 }
	    catch (ParseException e) {
	
	        e.printStackTrace();
	    }
         merchantUser.setMerUserFlg(("0".equals(groupuserinfotb.getUserqx().toString())) ? "1000" : "1100");//老系统：0为管理员，1普通用户；新系统：1000为管理员，1100普通用户
         merchantUser.setMerUserState("1");
         merchantUser.setActivate("0");
         merchantUser.setMerUserType("1");
         merchantUser.setSource(SourceEnum.TRANSFER.getCode());
         merchantUserMapper.addMerchantUser(merchantUser);
         
         //***********************************   6、 生成用户信息扩展表MERCHANT_USER_EXTEND数据     ********************//
         MerchantUserExtend merchantUserExtend = new MerchantUserExtend();
         merchantUserExtend.setOldUserId(Long.toString(groupuserinfotb.getLoginuserid()));//老系统用户id
         merchantUserExtend.setOldUserType("1");//老系统 用户类型   老系统用户类型   个人用户0，   集团用户1，  网点用户2
         merchantUserExtend.setUserCode(merchantUser.getUserCode());//对应 新系统用户编号
         merchantUserExtendMapper.addMerchantUserExtend(merchantUserExtend);
         
         //***********************************   7、 生成pos厂商POS_COMPANY、pos型号POS_TYPE和pos绑定中间表POS_MER_TB、pos信息表数据记录     ********************//
         //2016.3.22 by lifeng 集团不再迁移POS信息，可能会与网点的POS信息重复
         /*PosMerTb posMerTb = new PosMerTb();
         for (Groupposinfotb groupposinfotb : groupposinfotbList) {
        	 posMerTb.setCode(groupposinfotb.getPosid());//pos编号
             posMerTb.setMerCode(merchant.getMerCode());//商户编号
             posMerTb.setComments(groupposinfotb.getRemarks());//备注
             
             Biposinfotb biposinfotb = biposinfotbMapper.findBiposinfotbByPosId(groupposinfotb.getPosid());
             Pos pos = new Pos();
             pos.setCode(posMerTb.getCode());//pos编号
             try {
                 pos.setUpdateDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(biposinfotb.getLasttime()));
             }
             catch (ParseException e) {
                 e.printStackTrace();
             }
             pos.setUnitCost(biposinfotb.getBuycost());//采购成本
             pos.setPosCompanyCode(biposinfotb.getFactorycode());//厂商编号
             pos.setPosTypeCode(biposinfotb.getPatterncode());//型号编号
             if (biposinfotb.getVersion() != null) {
                 pos.setVersion(biposinfotb.getVersion().toString());//pos版本号
             }
             if("0".equals(biposinfotb.getStatus())){//0:启用,1:未启用, 2:停用,3:作废, 4：消费封锁, 5：充值封锁;  新系统   0：启用, 1：停用,2：作废,3：充值封锁,4：消费封锁
                 pos.setStatus("0");
             }else if("2".equals(biposinfotb.getStatus()) || "1".equals(biposinfotb.getStatus())){
                 pos.setStatus("1");
             }else if("3".equals(biposinfotb.getStatus())){
                 pos.setStatus("2");
             }else if("4".equals(biposinfotb.getStatus())){
                 pos.setStatus("4");
             }else if("5".equals(biposinfotb.getStatus())){
                 pos.setStatus("3");
             }
             posMapper.addPos(pos);
         }*/
        
         //***********************************   8、 生成商户业务信息表MER_RATE_SUPPLEMENT数据   ********************//
         if (StringUtils.isNotBlank(groupinfotb.getRateamt().toString())) {
             MerRateSupplement merRateSupplement = new MerRateSupplement();
             merRateSupplement.setRateCode("01");//充值业务
             merRateSupplement.setMerCode(merchant.getMerCode());
             merRateSupplementMapper.addMerRateSupplement(merRateSupplement);
         }

         //***********************************   9、 生成商户业务费率表MER_BUS_RATE数据  ********************//
         if (StringUtils.isNotBlank(groupinfotb.getRateamt().toString())) {
            // MerBusRate merBusRate = new MerBusRate();
             merBusRate.setMerCode(merchant.getMerCode());//商户编号
             merBusRate.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());//充值业务
             merBusRate.setProviderCode(TransferProCtiyCodeEnum.PROVIDER_CODE_BJ.getCode());//城市编号,北京通卡
             merBusRate.setRate(new BigDecimal(0));//数值
             merBusRate.setRateType(RateTypeEnum.PERMILLAGE.getCode());//费率类型:1.笔数;2.千分比
             merBusRateMapper.addMerBusRate(merBusRate);
         }
         if("1".equals(merchant.getMerState())){//已审核
        	 //***********************************   10、 生成主账户表ACCOUNT记录  ********************//
             Account account = new Account();
             String seqId = accountMapper.getSequenceNextId();
             String preId = "A" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + seqId;//按规则生成账户编号
             account.setAccountCode(preId);//账户编号
             account.setFundType("0"); // 0：资金 1：授信
             account.setCustomerType("1");//0：个人 1：商户
             account.setCustomerNo(merchant.getMerCode());//商户编号
             accountMapper.addAccount(account);

             //***********************************   11、 生成资金授信账户表ACCOUNT_FUND记录  ********************//
           //  AccountFund accountFund = new AccountFund();
             accountFund.setAccountCode(account.getAccountCode());//主账户编号
             accountFund.setFundAccountCode("F" + "0" + account.getAccountCode());//资金授信账户编号
//             accountFund.setSumTotalAmount(groupinfotb.getBal());//累计总金额
             accountFund.setSumTotalAmount(new BigDecimal(0));//累计总金额  // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
//             accountFund.setTotalBalance(groupinfotb.getBal());//总余额
             accountFund.setTotalBalance(new BigDecimal(0));//总余额  // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
             accountFund.setFundType("0");//资金类别 0：资金 1：授信
             accountFund.setFrozenAmount(new BigDecimal(0));//冻结金额
//             accountFund.setAvailableBalance(groupinfotb.getBal());//剩余金额
             accountFund.setAvailableBalance(new BigDecimal(0));//剩余金额  // 先迁基础数据，额度置为0 -- modify by lifeng 2016.4.15
             accountFund.setState("0");
             accountFundMapper.addAccountFund(accountFund);

             //***********************************   12、 生成资金账户风控表ACCOUNT_CONTROL数据 ********************//
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
