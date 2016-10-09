package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerUserCardBDDelegate {
	 //查询列表
    DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO);
    
    /**
     * 用户绑卡
     * @param cardBDDTO 
     * @return DodopalResponse<MerUserCardBDDTO>
     */
    DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(MerUserCardBDDTO cardBDDTO);

	/**
	 * 查询个人卡片充值和消费信息
	 * 
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query);
}
