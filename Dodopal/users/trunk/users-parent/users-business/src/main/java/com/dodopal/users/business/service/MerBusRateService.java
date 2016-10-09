package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerBusRate;

/**
 * 类说明 ：
 * @author lifeng
 */

public interface MerBusRateService {
    public List<MerBusRate> findMerBusRateByMerCode(String merCode);

    public List<MerBusRate> batchFindMerBusRateByMerCodes(List<String> merCodes);

    public int addMerBusRateBatch(List<MerBusRate> list);

    public int updateMerBusRate(MerBusRate merBusRate);

    public int batchUpdateMerBusRate(List<MerBusRate> list, String merCode);

    public int deleteMerBusRateById(String id);

    public int deleteMerBusRateByMerCode(String merCode);

    public int batchDelMerBusRateByMerCodes(List<String> merCodes);

    public List<MerBusRate> findChildMerProRateCodeType(List<String> merCodes);

    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param merCode
     * @return
     */
    public List<String> findMerCitys(String merCode);

    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通消费)
     * @param merCode
     * @return
     */
    public List<String> findMerCitysPayment(String merCode);

    /**
     * 查询个人用户开通城市列表(充值)
     * @return
     */
    public List<String> findUserCitys();

    /**
     * 根据城市code查询通卡code
     * @param cityCode
     * @return
     */
    public String findYktCodeByCityCode(String cityCode);
}
