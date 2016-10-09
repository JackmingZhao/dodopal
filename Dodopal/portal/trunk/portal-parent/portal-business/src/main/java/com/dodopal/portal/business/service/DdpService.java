package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountFundBean;
import com.dodopal.portal.business.bean.PayServiceRateBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.TraTransactionBean;
import com.dodopal.portal.business.model.MerchantUser;

public interface DdpService {
    /**
     * 更多支付方式
     * @param ext
     * @param merCode
     * @return
     */
    DodopalResponse<List<PayWayBean>> findPayWay(boolean ext,String merCode);
    
    /**
     * 常用支付方式
     * @param ext
     * @param userCode
     * @return
     */
    DodopalResponse<List<PayWayBean>> findCommonPayWay(boolean ext,String userCode);
    
    
    /**
     * 门户首页 账户可用余额 资金授信账户信息
     * @param aType 个人or企业
     * @param custNum 用户号or商户号
     * @return
     */
    DodopalResponse<AccountFundBean> findAccountBalance(String aType,String custNum);
    
    /**
     * 查询用户绑定卡数量
     * @param merOrUserName 用户登录名
     * @return DodopalResponse<Integer>
     */
    DodopalResponse<Integer> findMerUserCardBDCount(String merOrUserName);
    
    /**
     * 账户首页查询最新的十条交易记录
     * @param ext 个人OR企业
     * @param merOrUserCode 用户号or商户号
     * @param createUser 区分操作员和管理员
     * @return  DodopalResponse<List<TraTransactionBean>>
     */
    DodopalResponse<List<TraTransactionBean>> findTraTransactionByCode(String ext, String merOrUserCode,String createUser);
    
    /** 
     * @Title: modifyPayInfoFlg  add by xiongzhijing
     * @Description: 支付确认信息修改
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     */
    DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUser user);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findUserInfoByUserName 
     * @Description: 查询商户用户信息
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<MerchantUserDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<MerchantUser> findUserInfoByUserName(String userNameOrMobile);
    
    /**
     * 充值前 校验用户 商户合法性  add by xiong 
     * @param userCode 用户编号
     * @param merCode 商户号
     * @return
     */
    DodopalResponse<Boolean> checkUserAndMer(String userCode, String merCode,String userId,String merType);
    
    /**
     * 获取支付服务费和费率类型
     * @param payWayId (通用)支付方式id
     * @param busType 业务类型
     * @param amout 金额 单位（元）
     * @return  DodopalResponse<PayServiceRateDTO>
     */
    DodopalResponse<PayServiceRateBean> findPayServiceRate(String payWayId,String busType,long amout);
}
