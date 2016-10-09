package com.dodopal.payment.business.service;

import java.util.List;

import com.dodopal.common.enums.BankGatewayTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayTraTransaction;
import com.dodopal.payment.business.model.query.PayConfigQuery;
import com.dodopal.payment.business.model.query.PayTraTransactionQuery;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月28日 下午7:26:40
 */
public interface PayConfigService {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: savePayConfig 
     * @Description: 保存支付配置信息
     * @param payConfig    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void savePayConfig(PayConfig payConfig);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayConfigByPage 
     * @Description: 按分页查找支付配置信息
     * @param query
     * @return    设定文件 
     * DodopalDataPage<PayConfig>    返回类型 
     * @throws 
     */
    public DodopalDataPage<PayConfig> findPayConfigByPage(PayConfigQuery query);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayConfigById 
     * @Description: 查询支付配置详情
     * @param id
     * @return    设定文件 
     * PayConfig    返回类型 
     * @throws 
     */
    public PayConfig findPayConfigById(String id);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayConfigByAnotherAccountCode 
     * @Description: 根据第三方账号
     * @param anotherCode
     * @param payType
     * @return    设定文件 
     * Integer    返回类型 
     * @throws 
     */
    public Integer findPayConfigByAnotherAccountCode(String anotherCode,String payType);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: batchActivatePayConfig 
     * @Description: 批量停启用
     * @param flag
     * @param ids
     * @param updateUser
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int batchActivatePayConfig(String flag,List<String> ids,String updateUser); 
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updatePayConfig 
     * @Description: 更新配置信息，后手续费，支付方式名称，后手续费生效时间
     * @param payConfig
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int updatePayConfig(PayConfig payConfig);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updatePayConfigBankGateway 
     * @Description: 切换网关
     * @return    设定文件
     * int    返回类型 
     * @throws 
     */
    public void updatePayConfigBankGateway(List<String> ids,String updateUser,String payConfigId,BankGatewayTypeEnum toBankGateWayType);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayConfigByPayWayName 
     * @Description:根据支付方式名称进行查找
     * @param payWayName
     * @param id
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int findPayConfigByPayWayName(String payWayName,String id);

    /**
     * @description 查询支付配置参数(根据tranCode)
     * @param tranCode (交易id)
     * @return PayConfig
     */
    public PayConfig queryPayInfo(String tranCode);

    /**
     * @description 根据支付方式id查询支付配置信息
     * @param payWayId (支付方式id)
     * @return PayConfig
     */
    public PayConfig queryPayInfoByPayWayId(String payWayId);
    
    
    /**
     * 根据支付类型 查询支付方式
     * @param payType 支付类型
     * @return
     */
    public  List<PayConfig> findPayConfigPayType(String payType);
}
