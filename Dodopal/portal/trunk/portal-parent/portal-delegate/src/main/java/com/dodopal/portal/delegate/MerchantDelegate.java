package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantExtendDTO;
import com.dodopal.common.model.Area;
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
    //public DodopalResponse<List<MerFunctionInfoDTO>> findMerFuncitonInfoList();
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updateMerchantForPortal 
     * @Description: 门户编辑更新商户信息
     * @param merchantDTO
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    public DodopalResponse<String> updateMerchantForPortal(MerchantDTO merchantDTO); 
    //开户时查看通卡公司费率
    public DodopalResponse<List<MerBusRateDTO>> findMerBusRateByMerCode(String merCode);
    
    //加载通卡公司数据
    public DodopalResponse<List<ProductYKTDTO>>  yktData();
    
    //初始加载上级商户业务类型
    public DodopalResponse<List<MerRateSupplementDTO>> findMerRateSupplementsByMerCode(String merCode);
    /** 
	  * @author  Dingkuiyuan@dodopal.com 
	  * @date 创建时间：2015年10月31日 下午4:59:29 
	  * @version 1.0 
	  * @parameter  
	  * @since  查询商户消费所开通的城市
	  * @return  
	  */
    public DodopalResponse<List<Area>> findMerCitys(String merCode);
    
    
    /**
     * 根据新系统的商户号，商户扩展表的记录
     * @param merCode
     * @return
     */
    public DodopalResponse<MerchantExtendDTO> findMerchantExtend(String merCode);
}
