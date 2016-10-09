package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerUserCardBDBean;
import com.dodopal.product.business.bean.MerUserCardBDFindBean;

public interface MerUserCardBDService {
	 //查询列表
    DodopalResponse<List<MerUserCardBDBean>> findMerUserCardBD(MerUserCardBDFindBean bdFindBean);

    /**
     * 用户绑卡
     * @param cardBDDTO 
     * @return DodopalResponse<MerUserCardBDDTO>
     */
    DodopalResponse<MerUserCardBDBean> saveMerUserCardBD(MerUserCardBDBean cardBDBean);

	/**
	 * 查询个人卡片充值和消费信息(手机端)
	 * 
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query);
}
