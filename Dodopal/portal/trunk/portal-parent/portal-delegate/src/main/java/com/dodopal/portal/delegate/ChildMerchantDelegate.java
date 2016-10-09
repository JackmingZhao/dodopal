package com.dodopal.portal.delegate;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ChildMerchantDelegate {
    //商户信息查询 (分页)
    public DodopalResponse<DodopalDataPage<MerchantDTO>> findChildMerchantListByPage(MerchantQueryDTO merQueryDTO);

    //保存商户信息
    public DodopalResponse<String> saveChildMerchant(MerchantDTO merchantDTO);

    //单个用户信息查询
    public DodopalResponse<MerchantDTO> findChildMerchants(String merCode, String merParentCode);

    //更改商户信息
    public DodopalResponse<String> updateChildMerchant(MerchantDTO merchantDTO);

    //停启用商户信息
    public DodopalResponse<Integer> startAndDisableChildMerchant(String[] merCode, String activate,String merParentCode, String updateUser);
    
    //删除子商户信息
    public DodopalResponse<Integer> deleteChildMerchant(String[] merCode, String merState,String merParentCode, String updateUser);
    /**
     * 检查手机号是否已注册
     * @param mobile
     * @return true:已注册
     */
    public DodopalResponse<Boolean> checkMobileExist(String mobile,String merCode);

    /**
     * 检查用户名是否已注册
     * @param userName
     * @return true:已注册
     */
    public DodopalResponse<Boolean> checkUserNameExist(String userName,String merCode);

    /**
     * 检查商户名称是否已注册
     * @param merName
     * @return
     */
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName,String merCode);
}
