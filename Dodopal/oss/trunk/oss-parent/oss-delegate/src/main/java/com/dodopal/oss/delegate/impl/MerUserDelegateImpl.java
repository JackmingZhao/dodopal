package com.dodopal.oss.delegate.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerUserCardBDDTOQuery;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.MerUserDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("merUserDelegate")
public class MerUserDelegateImpl extends BaseDelegate implements MerUserDelegate {
	
	@Override
	public DodopalResponse<List<MerchantUserDTO>> findMerUserList(
			MerchantUserDTO userDTO) {
		MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<MerchantUserDTO>> response = facade.findUserInfoList(userDTO);
		return response;
	}
	
	@Override
	public DodopalResponse<MerchantUserDTO> findMerUser(String userID){
		MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<MerchantUserDTO> response = facade.findUserInfoById(userID);
		return response;
	}

	@Override
	public DodopalResponse<Map<String, String>> resetPwdSendMsg(String mobileNum){		
		SendFacade facade =getFacade(SendFacade.class,DelegateConstant.FACADE_USERS_URL);
		return facade.send(mobileNum, MoblieCodeTypeEnum.USER_PWD);
	}

	@Override
	public DodopalResponse<Boolean> resetPwd(MerchantUserDTO userDTO){		
		MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
		return facade.resetPWDSendMsg(userDTO);
	}
	
	@Override    
    public DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardList(MerUserCardBDFindDTO findDTO){
    	MerUserCardBDFacade facade =getFacade(MerUserCardBDFacade.class,DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<List<MerUserCardBDDTO>> response = facade.findMerUserCardBD(findDTO);
		return response;
    }
	
	@Override    
    public DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> findMerUserCardBDListByPage(MerUserCardBDDTOQuery cardBDFindDTO){
    	MerUserCardBDFacade facade =getFacade(MerUserCardBDFacade.class,DelegateConstant.FACADE_USERS_URL);
    	DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> response = facade.findMerUserCardBDListByPage(cardBDFindDTO);
		return response;
    }
    
    @Override
    public DodopalResponse<String> unbundUserCards(String operName,List<String> userCardIds,String longName){
    	MerUserCardBDFacade facade =getFacade(MerUserCardBDFacade.class,DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<String> response = facade.unBundlingCard(longName, operName,userCardIds,SourceEnum.OSS.getCode());
		return response;
    }
    
	@Override
	public DodopalResponse<String> ActivateMerchant(MerchantUserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DodopalResponse<String> EditMerchant(MerchantUserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public DodopalResponse<Boolean> updateUser(MerchantUserDTO userDTO) {
        MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.updateUser(userDTO);
    }

    @Override
    public DodopalResponse<Map<String,String>> startOrStop(List<String> idList, ActivateEnum activate,String updateUser) {
        MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.toStopOrStartUser(activate, idList, updateUser);
    }
	
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerUsersByPage(MerchantUserQueryDTO dto){
        MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.findUserInfoListByPage(dto);
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findUserInfoListByPage(MerchantUserQueryDTO userDTO) {
        MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
        DodopalResponse<DodopalDataPage<MerchantUserDTO>> response = facade.findUserInfoListByPage(userDTO);
        return response;
    }

	//卡片操作日志查询
	public DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> findUserCardLogByPage(UserCardLogQueryDTO dto) {
		MerUserCardBDFacade facade =getFacade(MerUserCardBDFacade.class,DelegateConstant.FACADE_USERS_URL);
		DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> response = facade.findUserCardBDLogByPage(dto);
		return response;
	}
	 @Override
    public List<MerchantUserDTO> getExportExcelMerUserList(MerchantUserQueryDTO dto) {
        MerchantUserFacade facade =getFacade(MerchantUserFacade.class,DelegateConstant.FACADE_USERS_URL);
        List<MerchantUserDTO> response = facade.getExportExcelMerUserList(dto);
        return response;
    }
	 
	 @Override
    public List<MerUserCardBDDTO> getExportExcelMerUserCardList(MerUserCardBDDTOQuery cardBDFindDTO) {
        MerUserCardBDFacade facade =getFacade(MerUserCardBDFacade.class,DelegateConstant.FACADE_USERS_URL);
        List<MerUserCardBDDTO> response = facade.getExportExcelMerUserCardList(cardBDFindDTO);
        return response;
    }
	 
	 @Override
    public List<MerUserCardBDLogDTO> getExportExcelUserCardLog(UserCardLogQueryDTO dto) {
        MerUserCardBDFacade facade =getFacade(MerUserCardBDFacade.class,DelegateConstant.FACADE_USERS_URL);
        List<MerUserCardBDLogDTO> response = facade.getExportExcelUserCardLog(dto);
        return response;
    }
}
