package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.payment.dto.PayServiceRateDTO;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.model.DodopalResponse;

public interface DdpDelegate {

    /**
     * 更多支付方式
     * @param ext 是否为外接商户（true 是|false 否）
     * @param merCode 商户编号  如果是外接商户，商户编号不能为空
     * @return   DodopalResponse<List<PayWayDTO>>
     */
    DodopalResponse<List<PayWayDTO>> findPayWay(boolean ext,String merCode);
    
    /**
     * 用户常用支付方式
     * @param ext 是否为外接商户（true 是|false 否）
     * @param userCode 用户号|商户编号  如果是外接商户，商户编号不能为空
     * @return DodopalResponse<List<PayWayDTO>>
     */
    DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,String userCode);
    /**
     * 门户系统 账户首页 可用余额 资金授信账户信息
     * @param aType 个人 or 企业
     * @param custNum 用户号 or 商户号
     * @return DodopalResponse<List<AccountFundDTO>>
     */
    DodopalResponse<AccountFundDTO> findAccountBalance(String aType,String custNum);
    
    /**
     * 查询用户绑定卡数量
     * @param merUserCardBDDTO 用户信息
     * @return DodopalResponse<Integer>
     */
    DodopalResponse<Integer> findMerUserCardBDCount(MerUserCardBDDTO merUserCardBDDTO);
    
    /**
     * 账户首页查询最新的十条交易记录
     * @param ext 个人OR企业
     * @param merOrUserCode 用户号or商户号
     * @param createUser 区分操作员和管理员
     * @return DodopalResponse<List<PayTraTransactionDTO>>
     */
    DodopalResponse<List<PayTraTransactionDTO>> findTraTransactionByCode(String ext, String merOrUserCode,String createUser);
    
    /** 
     * @Title: modifyPayInfoFlg 
     * @Description: 支付确认信息修改
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     */
    DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserDTO userDTO);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findUserInfoByUserName 
     * @Description: 查询商户用户信息
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<MerchantUserDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile);

    /**
     * 获取支付服务费和费率类型
     * @param payWayId (通用)支付方式id
     * @param busType 业务类型
     * @param amout 金额 单位（分）
     * @return  DodopalResponse<PayServiceRateDTO>
     */
    DodopalResponse<PayServiceRateDTO> findPayServiceRate(String payWayId,String busType,long amout);
}
