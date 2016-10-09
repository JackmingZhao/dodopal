package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerBusRate;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerBusRateMapper {
    public MerBusRate findMerBusRateById(String id);

    public List<MerBusRate> findMerBusRateByMerCode(String merCode);

    public List<MerBusRate> batchFindMerBusRateByMerCodes(@Param("merCodes") List<String> merCodes);

    public int addMerBusRateBatch(List<MerBusRate> list);

    public int updateMerBusRate(MerBusRate merBusRate);

    public int deleteMerBusRateById(String id);

    public int deleteMerBusRateByMerCode(String merCode);

    public int batchDelMerBusRateByMerCodes(@Param("merCodes") List<String> merCodes);

    public List<MerBusRate> findChildMerProRateCodeType(@Param("merCodes") List<String> merCodes);

    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param merCode
     * @param rateCode
     * @return
     */
    public List<String> findMerCitys(@Param("merCode") String merCode, @Param("rateCode") String rateCode);
    
    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通消费)
     * @param merCode
     * @param rateCode
     * @return
     */
    public List<String> findMerCitysPayment(@Param("merCode") String merCode, @Param("rateCode") String rateCode);

    /**
     * 查询个人用户开通城市(充值)
     * @return
     */
    public List<String> findUserCitys();

    /**
     * 根据城市code查询通卡code
     * @param cityCode
     * @return
     */
    public String findYktCodeByCityCode(@Param("cityCode") String cityCode);
}
