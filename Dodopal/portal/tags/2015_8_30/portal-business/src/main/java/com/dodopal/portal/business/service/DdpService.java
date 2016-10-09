package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.AccountFundBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.TraTransactionBean;

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
     * 账户首页查询最新的十条交易记录
     * @param ext 个人OR企业
     * @param merOrUserCode 用户号or商户号
     * @return  DodopalResponse<List<TraTransactionBean>>
     */
    DodopalResponse<List<TraTransactionBean>> findTraTransactionByCode(String ext, String merOrUserCode);
}
