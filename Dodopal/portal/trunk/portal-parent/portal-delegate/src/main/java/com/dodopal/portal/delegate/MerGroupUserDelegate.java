package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.MerGroupUserFindDTO;
import com.dodopal.api.users.dto.query.MerGroupUserQueryDTO;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerGroupUserDelegate {

	/**
	 * 查询集团用户
	 * @param findDTO 查询条件
	 * @param findFlg 查询来源 0:门户，1：OSS
	 * @return
	 */
	DodopalResponse<List<MerGroupUserDTO>> findMerGpUsers(MerGroupUserFindDTO findDTO, String findFlg);

	 /**
     * 查询集团用户(分页)
     * @param findDTO 查询条件
     * @param source 来源 0:门户，1：OSS
     * @return
     */
	DodopalResponse<DodopalDataPage<MerGroupUserDTO>> findMerGpUsersByPage(MerGroupUserQueryDTO findDTO,SourceEnum source);
	
	/**
	 * 查询集团用户详细信息
	 * @param gpUserId 集团用户ID
	 * @return
	 */
	DodopalResponse<MerGroupUserDTO> findMerGpUserById(String gpUserId);

	/**
	 * 添加集团用户(支持批量添加：用于导入)
	 * @param gpUserDTOs 集团用户信息
	 * @return
	 */
	DodopalResponse<String> saveMerGpUsers(List<MerGroupUserDTO> gpUserDtos);

	/**
	 * 添加集团用户(单条数据添加)
	 * @param gpUserDto 集团用户信息
	 * @return
	 */
	DodopalResponse<String> saveMerGpUser(MerGroupUserDTO gpUserDto);

	/**
	 * 修改集团用户
	 * @param gpUserDTO 集团用户信息
	 * @return
	 */
	DodopalResponse<Integer> updateMerGpUser(MerGroupUserDTO gpUserDto);

	/**
	 * 删除集团用户
	 * @param gpUserId 集团用户ID
	 * @return
	 */
	DodopalResponse<Integer> deleteMerGpUser(String gpUserId);
	
    /**
     * 检测公交卡号是否重复
     * 
     * @param merCode 商户code
     * @param cardCode 卡号
     * @param gpUserId 集团用户ID：新增时为null，
     * @return
     */
    DodopalResponse<String> checkCardCode(String merCode,String cardCode,String gpUserId);

}
