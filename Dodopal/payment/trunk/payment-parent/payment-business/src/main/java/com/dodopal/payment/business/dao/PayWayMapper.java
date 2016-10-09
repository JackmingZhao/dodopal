package com.dodopal.payment.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.payment.business.model.PayWay;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月13日 下午3:05:55
 */
public interface PayWayMapper {
    /**
     * 查询外接商户的外接支付方式
     * @param merCode
     * @return List<PayWay>
     */
    public List<PayWay> findPayWayExternal(@Param("merCode")String merCode);
    
    /**
     * 查询非外接商户的通用支付方式
     * @param payType
     * @return List<PayWay>
     */
    public List<PayWay> findPayWayGeneral(@Param("payType")String payType);
    
    /**
     * 常用支付方式（外接）
     * @param merCode
     * @return List<PayWay>
     */
    public List<PayWay> findCommonExternal(@Param("merCode")String merCode);
    /**
     * 常用支付方式（非外接）
     * @return List<PayWay>
     */
    public List<PayWay> findCommonGeneral(@Param("userCode")String userCode);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayWayGeneralByPayType 
     * @Description: 根据支付类型查通用
     * @param merCode
     * @return    设定文件 
     * List<PayWay>    返回类型 
     * @throws 
     */
    public List<PayWay>  findPayWayGeneralByPayType(@Param("payType")String payType);
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayWayExternalByPayType 
     * @Description: 根据支付类型商户号，查外接
     * @param merCode
     * @return    设定文件 
     * List<PayWay>    返回类型 
     * @throws 
     */
    public List<PayWay>  findPayWayExternalByPayType(@Param("merCode")String merCode,@Param("payType")String payType);
    
    public String  getPayTranCodeSeq();
}
