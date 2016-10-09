package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerBatchRechargeItemDTO;
import com.dodopal.api.users.dto.MerBatchRechargeOrderDTO;
import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.query.MerBatchRcgOrderQueryDTO;
import com.dodopal.api.users.facade.MerBatchRechargeOrderFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BatchRcgItemStatusEnum;
import com.dodopal.common.enums.BatchRcgOrderStatusEnum;
import com.dodopal.common.enums.EmpTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.model.MerBatchRechargeItem;
import com.dodopal.users.business.model.MerBatchRechargeOrder;
import com.dodopal.users.business.model.MerGroupUser;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerBatchRcgOrderQuery;
import com.dodopal.users.business.service.MerBatchRechargeOrderService;
import com.dodopal.users.business.service.MerGroupUserService;
import com.dodopal.users.business.service.MerchantUserService;

/**
 * @author lifeng@dodopal.com
 */
@Service("merBatchRechargeOrderFacade")
public class MerBatchRechargeOrderFacadeImpl implements MerBatchRechargeOrderFacade {
	private Logger logger = LoggerFactory.getLogger(MerBatchRechargeOrderFacade.class);
	private static final String MER_USER_FLAG = "merUserFlag";

	@Autowired
	private MerGroupUserService merGroupUserService;
	@Autowired
	private MerchantUserService merchantUserService;
	@Autowired
	private MerBatchRechargeOrderService merBatchRechargeOrderService;

	@Override
	public DodopalResponse<List<MerGroupUserDTO>> findMerGpUsersByUserName(String merCode, String merUserName) {
		DodopalResponse<List<MerGroupUserDTO>> response = new DodopalResponse<List<MerGroupUserDTO>>();
		try {
			Map<String, String> param = new HashMap<String, String>();
			String resCode = findMerGpUsersByUserNameCheck(param, merCode, merUserName);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				// 管理员可以查询所有部门人员，其他人只能查询自己管辖部门人员
				if (MerUserFlgEnum.ADMIN.getCode().equals(param.get(MER_USER_FLAG))) {
					merUserName = null;
				}
				List<MerGroupUser> resultList = merGroupUserService.findMerGpUsersByUserName(merCode, merUserName);
				if (CollectionUtils.isNotEmpty(resultList)) {
					List<MerGroupUserDTO> resResultList = new ArrayList<MerGroupUserDTO>(resultList.size());
					for (MerGroupUser user : resultList) {
						MerGroupUserDTO userDTO = new MerGroupUserDTO();
						BeanUtils.copyProperties(user, userDTO);
						resResultList.add(userDTO);
					}
					response.setResponseEntity(resResultList);
				}
				response.setCode(ResponseCode.SUCCESS);
			} else {
				response.setCode(resCode);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<DodopalDataPage<MerBatchRechargeOrderDTO>> findBatchRcgOrderByPage(MerBatchRcgOrderQueryDTO queryDTO) {
		DodopalResponse<DodopalDataPage<MerBatchRechargeOrderDTO>> response = new DodopalResponse<DodopalDataPage<MerBatchRechargeOrderDTO>>();
		try {
			String resCode = findBatchRcgOrderByPageCheck(queryDTO);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				MerBatchRcgOrderQuery query = new MerBatchRcgOrderQuery();
				BeanUtils.copyProperties(queryDTO, query);
				DodopalDataPage<MerBatchRechargeOrder> ddpResult = merBatchRechargeOrderService.findBatchRcgOrderByPage(query);
				if (ddpResult != null) {
					List<MerBatchRechargeOrder> resultList = ddpResult.getRecords();
					if (CollectionUtils.isNotEmpty(resultList)) {
						List<MerBatchRechargeOrderDTO> resResultList = new ArrayList<MerBatchRechargeOrderDTO>(resultList.size());
						for (MerBatchRechargeOrder order : resultList) {
							MerBatchRechargeOrderDTO orderDTO = new MerBatchRechargeOrderDTO();
							BeanUtils.copyProperties(order, orderDTO);
							resResultList.add(orderDTO);
						}
						PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
						DodopalDataPage<MerBatchRechargeOrderDTO> ddpDTOResult = new DodopalDataPage<MerBatchRechargeOrderDTO>(page, resResultList);
						response.setResponseEntity(ddpDTOResult);
					}
				}
				response.setCode(ResponseCode.SUCCESS);
			} else {
				response.setCode(resCode);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}

		return response;
	}

	@Override
	public DodopalResponse<String> saveBatchRcgOrder(MerBatchRechargeOrderDTO orderDTO) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		try {
			String resCode = saveBatchRcgOrderCheck(orderDTO);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				// 构建订单
				MerBatchRechargeOrder order = new MerBatchRechargeOrder();
				BeanUtils.copyProperties(orderDTO, order);
				List<MerBatchRechargeItemDTO> itemDTOList = orderDTO.getBatchItemList();
				List<MerBatchRechargeItem> itemList = new ArrayList<MerBatchRechargeItem>();
				for (MerBatchRechargeItemDTO itemDTO : itemDTOList) {
					MerBatchRechargeItem item = new MerBatchRechargeItem();
					BeanUtils.copyProperties(itemDTO, item);
					itemList.add(item);
				}
				order.setBatchItemList(itemList);

				merBatchRechargeOrderService.saveBatchRcgOrder(order);
				response.setCode(ResponseCode.SUCCESS);
			} else {
				response.setCode(resCode);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<MerBatchRechargeOrderDTO> findBatchRcgOrderInfoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DodopalResponse<MerBatchRechargeOrderDTO> addBatchRcgItem(MerBatchRechargeOrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DodopalResponse<MerBatchRechargeOrderDTO> delBatchRcgItemById(MerBatchRechargeOrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DodopalResponse<String> executeBatchRcgOrderByIds(List<String> orderIds, String updateUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DodopalResponse<String> delBatchRcgOrderById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------参数校验
	private String findMerGpUsersByUserNameCheck(Map<String, String> param, String merCode, String merUserName) {
		if (StringUtils.isBlank(merCode)) {
			return ResponseCode.USERS_FIND_MER_CODE_NULL;
		}

		if (StringUtils.isBlank(merUserName)) {
			return ResponseCode.USERS_FIND_USER_NAME_NULL;
		}

		MerchantUser merUser = merchantUserService.findMerchantUserByUserName(merUserName);
		if (merUser == null) {
			return ResponseCode.USERS_FIND_USER_ERR;
		}

		if (!merCode.equals(merUser.getMerCode())) {
			return ResponseCode.USERS_USER_MER_ERR;
		}

		param.put(MER_USER_FLAG, merUser.getMerUserFlag());

		return ResponseCode.SUCCESS;
	}

	private String findBatchRcgOrderByPageCheck(MerBatchRcgOrderQueryDTO queryDTO) {
		String merCode = queryDTO.getMerCode();
		if (StringUtils.isBlank(merCode)) {
			return ResponseCode.USERS_FIND_MER_CODE_NULL;
		}
		String userId = queryDTO.getUserId();
		if (StringUtils.isBlank(userId)) {
			return ResponseCode.USERS_FIND_USER_ID_NULL;
		}

		PageParameter page = queryDTO.getPage();
		if (page == null) {
			queryDTO.setPage(new PageParameter());
		}

		return ResponseCode.SUCCESS;
	}

	private String saveBatchRcgOrderCheck(MerBatchRechargeOrderDTO orderDTO) {
		String merCode = orderDTO.getMerCode();
		if (StringUtils.isBlank(merCode)) {
			return ResponseCode.USERS_FIND_MER_CODE_NULL;
		}

		String createUser = orderDTO.getCreateUser();
		if (StringUtils.isBlank(createUser)) {
			return ResponseCode.CREATE_USER_NULL;
		}

		List<MerBatchRechargeItemDTO> itemList = orderDTO.getBatchItemList();
		if (CollectionUtils.isEmpty(itemList)) {
			return ResponseCode.USERS_BATCH_RCG_ITEM_NULL;
		}

		for (MerBatchRechargeItemDTO item : itemList) {
			String groupUserId = item.getGroupUserId();
			if (StringUtils.isBlank(groupUserId)) {
				return ResponseCode.USERS_BATCH_RCG_GROUP_USER_ID_NULL;
			}

			MerGroupUser groupUser = merGroupUserService.findMerGpUserById(groupUserId);
			if (groupUser == null) {
				return ResponseCode.USERS_BATCH_RCG_GROUP_USER_NULL;
			}

			String empType = groupUser.getEmpType();
			if (!EmpTypeEnum.EMP.getCode().equals(empType)) {
				return ResponseCode.USERS_BATCH_RCG_GROUP_USER_EMP;
			}

			String cardCode = groupUser.getCardCode();
			if (StringUtils.isBlank(cardCode)) {
				return ResponseCode.USERS_BATCH_RCG_GROUP_USER_CARD_NULL;
			}
			// 设置卡号
			item.setCardCode(cardCode);

			long rcgAmt = groupUser.getRechargeAmount();
			if (rcgAmt <= 0) {
				return ResponseCode.USERS_BATCH_RCG_GROUP_USER_RCG_AMT_NULL;
			}
			// 设置充值金额
			item.setRechargeAmt(rcgAmt);

			// 充值项状态默认为未充值 TODO:枚举值待完善
			item.setStatus(BatchRcgItemStatusEnum.UN_RECHARGE.getCode());
			// 创建人
			item.setCreateUser(createUser);
		}
		// 批次单状态
		orderDTO.setStatus(BatchRcgOrderStatusEnum.CREATE.getCode());
		return ResponseCode.SUCCESS;
	}

}
