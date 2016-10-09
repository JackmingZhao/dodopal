package com.dodopal.portal.business.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerGroupUserBean;
import com.dodopal.portal.business.bean.MerGroupUserQueryBean;

public interface MerGroupUserService {

	DodopalResponse<List<MerGroupUserBean>> findMerGroupUserDTOList(MerGroupUserQueryBean bean);

	DodopalResponse<DodopalDataPage<MerGroupUserBean>> findMerGpUsersByPage(MerGroupUserQueryBean findDTO,SourceEnum source);
	
	DodopalResponse<String> saveMerGroupUserDTOList(MerGroupUserBean merDep);
	
	DodopalResponse<String> importMerGpUsers(CommonsMultipartFile file,String merCode);

	DodopalResponse<MerGroupUserBean> findMerGroupUserById(String Id);

	DodopalResponse<String> deleteMerGroupUser(String id);

	DodopalResponse<String> updateMerGroupUser(MerGroupUserBean bean);
	
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
