package com.dodopal.api.users.facade;


import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ChildMerchantFacade {
    /**
     * 查詢子商戶信息列表
     * @param merQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findChildMerchantByPage(MerchantQueryDTO merQueryDTO);
    /**
     * 創建子商戶
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<String> saveChildMerchant(MerchantDTO merchantDTO);
    /**
     * 根据商户号查询子商户信息
     * @param merCode
     * @return
     */
    public DodopalResponse<MerchantDTO> findChildMerchantByMerCode(String merCode, String merParentCode);
   
    /**
     * 保存子商戶信息
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<String> upChildMerchant(MerchantDTO merchantDTO);

    /**
     * 批量停启用商户
     * @param activate
     * @param merCodes
     * @return
     */
    public DodopalResponse<Integer> upChildMerchantActivate(String[] merCodes,String activate, String merParentCode, String updateUser);

    /**
     * 批量删除子商户
     * @param merCodes
     * @param merParentCode
     * @return
     */
    public DodopalResponse<Integer> batchDelChildMerchant(String[] merCodes, String merParentCode);
}
