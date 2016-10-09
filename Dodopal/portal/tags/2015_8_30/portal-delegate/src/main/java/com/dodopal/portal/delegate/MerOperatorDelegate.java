package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 类说明 ：用户管理（管理操作员）
 * @author lifeng
 */

public interface MerOperatorDelegate {
    /**
     * 查询用户（分页）
     * @param userQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerOperatorByPage(MerchantUserQueryDTO userQueryDTO);

    /**
     * 根据用户编号查询用户信息
     * @param merCode
     * @param userCode
     * @return
     */
    public DodopalResponse<MerchantUserDTO> findMerOperatorByUserCode(String merCode, String userCode);

    /**
     * 创建用户
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Integer> addMerOperator(MerchantUserDTO merchantUserDTO);

    /**
     * 修改用户
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Integer> updateMerOperator(MerchantUserDTO merchantUserDTO);

    /**
     * 批量停启用用户
     * @param merCode
     * @param updateUser
     * @param activate
     * @param userCodes
     * @return
     */
    public DodopalResponse<Integer> batchActivateMerOperator(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes);

    /**
     * 分配角色
     * @param merchantUserDTO
     * @return
     */
    public DodopalResponse<Integer> configMerOperatorRole(MerchantUserDTO merchantUserDTO);

    /**
     * 重置密码
     * @param merCode
     * @param id
     * @param password
     * @param updateUser
     * @return
     */
    public DodopalResponse<Boolean> resetOperatorPwd(String merCode, String id, String password, String updateUser);
}
