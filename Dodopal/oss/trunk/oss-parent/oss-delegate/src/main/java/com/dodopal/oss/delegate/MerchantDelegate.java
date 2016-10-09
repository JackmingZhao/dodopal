package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerchantDelegate {
    //商户信息查询 
    public DodopalResponse<List<MerchantDTO>> findMerchantList(MerchantDTO merchantDTO);
    //保存商户信息
    public DodopalResponse<String> saveMerchant(MerchantDTO merchantDTO);
    //停启用商户信息
	public DodopalResponse<Integer> startAndDisableMerchant(List<String> merCode,
			String activate, String updateUser);
	//根据mercode查找商户详情
    public DodopalResponse<MerchantDTO> findMerchantByCode(String merCode);
	//单个用户信息查询
    public DodopalResponse<MerchantDTO> findMerchants(String merCode);
    //更改商户信息
    public DodopalResponse<String> updateMerchant(MerchantDTO merchantDTO);
    //查询自定义商户功能
    public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncitonInfoList(String merType);
    //按照商户类型查询商户功能模板
    public DodopalResponse<List<MerFunctionInfoDTO>> findMerTypeFunList(String merType);
    //商户信息查询 (分页)
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findMerchantListByPage(MerchantQueryDTO merQueryDTO);
    //停启用商户信息
    public DodopalResponse<Integer> delNotverified(List<String> merCodes,String upUser);
    
    //加载通卡公司费率
    public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode);

    /**
     * 判断是否与上级费率类型不一致，或者少于下级通卡公司数量，或者与下级费率类型不一致
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(MerchantDTO merchantDTO); 
    //加载通卡公司数据
    public DodopalResponse<List<ProductYKTDTO>>  yktData();
    //初始加载上级商户业务类型
    public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode);
    
    
    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通充值)
     * @param merCode
     * @return
     */
    public DodopalResponse<List<Area>> findMerCitys(String merCode); 

    /**
     * 查询商户开通的通卡对应的城市列表(必须开通一卡通消费)
     * @param merCode
     * @return
     */
    public DodopalResponse<List<Area>> findMerCitysPayment(String merCode);

}
