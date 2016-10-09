package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.CardDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * 卡片管理
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月16日
 *
 */
@Service("CardDelegate")
public class CardDelegateImpl extends BaseDelegate implements CardDelegate {
    
    //查询用户卡片
    public  DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<List<MerUserCardBDDTO>> response = facade.findMerUserCardBD(cardBDFindDTO);
        return response;
    }
    
    //查询卡片操作日志（分页）
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> findUserCardBDLogByPage(UserCardLogQueryDTO query){
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> response = facade.findUserCardBDLogByPage(query);
        return response;
    }

    //编辑
    public DodopalResponse<Boolean> editUserCard(MerUserCardBDDTO cardDTO) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<Boolean> response = facade.editUserCard(cardDTO);
        return response;
    }

    //解绑
    public DodopalResponse<String> unbindCardByUser(String userName,String operName, List<String> ids, String source) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<String> response = facade.unBundlingCard(userName,operName, ids, source);
        return response;
    }

    @Override
    public DodopalResponse<MerUserCardBDDTO> findMerUserCardBDById(String id) {
        MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<MerUserCardBDDTO> response = facade.findMerUserCardBDById(id);
        return response;
    }

	// 查询个人卡片充值和消费信息（分页）by Mika
	public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query) {
		MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<DodopalDataPage<UserCardRecordDTO>> response = facade.findUserCardRecordByPage(query);
		return response;
	}

	// 根据充值还是消费的类型 和 单号 查询一单交易详情 by Mika
	public DodopalResponse<UserCardRecordDTO> findCardRecordInfoByTypeOrderNum(String type, String orderNum) {
		MerUserCardBDFacade facade = getFacade(MerUserCardBDFacade.class, DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<UserCardRecordDTO> response = facade.findCardRecordInfoByTypeOrderNum(type, orderNum);
		return response;
	}



}
