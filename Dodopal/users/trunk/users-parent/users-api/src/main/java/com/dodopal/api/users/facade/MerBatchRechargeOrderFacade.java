package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MerBatchRechargeOrderDTO;
import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.query.MerBatchRcgOrderQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author lifeng@dodopal.com
 */

public interface MerBatchRechargeOrderFacade {
	/**
	 * 查询集团人员信息(在职、部门启用) 根据：商户号、当前登录用户名
	 * 
	 * @param merCode
	 * @param merUserName
	 * @return
	 */
	public DodopalResponse<List<MerGroupUserDTO>> findMerGpUsersByUserName(String merCode, String merUserName);

	/**
	 * 查询批次单
	 * 
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<MerBatchRechargeOrderDTO>> findBatchRcgOrderByPage(MerBatchRcgOrderQueryDTO queryDTO);

	/**
	 * 新增批次单
	 * 
	 * @param orderDTO
	 *            必传参数: 
	 *            MerBatchRechargeOrderDTO.merCode
	 *            MerBatchRechargeOrderDTO.createUser
	 *            MerBatchRechargeItemDTO.groupUserId
	 * @return
	 */
	public DodopalResponse<String> saveBatchRcgOrder(MerBatchRechargeOrderDTO orderDTO);

	/**
	 * 查看批次单
	 * 
	 * @param id
	 * @return
	 */
	public DodopalResponse<MerBatchRechargeOrderDTO> findBatchRcgOrderInfoById(String id);

	/**
	 * 编辑批次单--添加充值项
	 * 
	 * @param orderDTO
	 * @return
	 */
	public DodopalResponse<MerBatchRechargeOrderDTO> addBatchRcgItem(MerBatchRechargeOrderDTO orderDTO);

	/**
	 * 编辑批次单--删除充值项
	 * 
	 * @param orderDTO
	 * @return
	 */
	public DodopalResponse<MerBatchRechargeOrderDTO> delBatchRcgItemById(MerBatchRechargeOrderDTO orderDTO);

	/**
	 * 执行批次单
	 * 
	 * @param orderIds
	 * @param updateUser
	 * @return
	 */
	public DodopalResponse<String> executeBatchRcgOrderByIds(List<String> orderIds, String updateUser);

	/**
	 * 删除批次单
	 * 
	 * @param id
	 * @return
	 */
	public DodopalResponse<String> delBatchRcgOrderById(String id);
}
