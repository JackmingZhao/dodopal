package com.dodopal.api.users.facade;

import java.util.List;
import java.util.Map;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface MerchantUserFacade {
    
    DodopalResponse<DodopalDataPage<MerchantUserDTO>> findUserInfoListByPage(MerchantUserQueryDTO userDTO);
    
    /**
     * 单个用户信息查询
     * 条件查询 模糊：userName nickName
     *       精准：mobile activate
     */
    DodopalResponse<List<MerchantUserDTO>> findUserInfoList(MerchantUserDTO userDTO);

	/**
	 * 查询商户用户列表
	 * 
	 * @param merCode
	 * @return
	 */
	DodopalResponse<List<MerchantUserDTO>> findMerchantUserList(MerchantUserDTO merUserDTO);

    /**
     * 单个用户信息查询
     */
    DodopalResponse<MerchantUserDTO> findUserInfoById(String id);
    
    /**
     * 用户批量启用或禁用
     */
    DodopalResponse<Map<String,String>> toStopOrStartUser(ActivateEnum startOrStop,List <String> idList,String updateUser);
    
    /**
     * 更新操作
     * activate  mobile  identityType  identityNumber
     * sex  email remark nickName
     */
    DodopalResponse<Boolean> updateUser(MerchantUserDTO userDTO);
    
    /**
     * 修改
     * @param userDTO
     * @return DodopalResponse<Boolean>
     */
    DodopalResponse<Boolean> updateMerchantUser(MerchantUserDTO userDTO);

    /** 
     * @Title: modifyPWD 
     * @Description: 修改密码
     * @param userDTO 要求传入数据，必须
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型  ture 为成功
     * @throws 
     */
    DodopalResponse<Boolean> modifyPWD(MerchantUserDTO userDTO,String oldPassword);
    
    
    /** 
     * @Title: resetPWD 
     * @Description: 重置密码
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型   ture 为成功
     * @throws 
     */
    DodopalResponse<Boolean> resetPWD(MerchantUserDTO userDTO);
    
    /** 
     * @Title: resetPWDByMobile 
     * @Description: 手机号更新用户密码
     * @param mobile
     * @param pwd
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    DodopalResponse<Boolean> resetPWDByMobile(String mobile,String pwd);
    /** 
     * @Title: resetPWDSendMsg 
     * @Description: 重置密码并发送短信
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型   ture 为成功
     * @throws 
     */
    DodopalResponse<Boolean> resetPWDSendMsg(MerchantUserDTO userDTO);

    /** 
     * @Title: modifyPayInfoFlg 
     * @Description: 支付确认信息修改
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<Boolean>    返回类型 
     */
    DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserDTO userDTO);

    /** 
     * @Title: findMerUserNameByMobile 
     * @Description: 根据手机号查找用户账号名
     * @param mobile
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    DodopalResponse<String>findMerUserNameByMobile(String mobile);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findUserInfoByUserName 
     * @Description: 查询商户用户信息
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<MerchantUserDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile);

    /**
     * 修改用户默认业务城市
     * @param cityCode 城市编码
     * @param updateUser 当前登录用户ID
     * @return
     */
    DodopalResponse<Boolean> updateMerUserBusCity(String cityCode, String updateUser);

    /**
     * 更新用户手机号
     * @param merLinkUserMobile 新手机号
     * @param userId 当前登录用户ID
     * @return
     */
    DodopalResponse<Boolean> updateMerUserMobile(String merLinkUserMobile, String userId);

    /**
     * 更改用户密码
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param userId 当前登录用户ID
     * @return
     */
    DodopalResponse<Boolean> updateMerUserPWDById(String oldPwd, String newPwd, String userId);

    /**
     * 校验用户密码是否正确
     * @param merUserPWD 原密码
     * @param userId 当前登录用户ID
     * @return
     */
    DodopalResponse<Boolean> validateMerUserPWD(String merUserPWD, String userId);
    
    /**
     * @param 根据userCode查询数据
     * @return
     */
    DodopalResponse<MerchantUserDTO> findUserInfoByUserCode(String userCode);
    
    /**
     * 根据id查询用户姓名
     * @param userId 用户id
     * @return
     */
    DodopalResponse<String> findNickNameByUserId(String userId);

    List<MerchantUserDTO> getExportExcelMerUserList(MerchantUserQueryDTO userDTO);

    /**
     * 根据老系统userid和usertype更新密码
     * @param oldUserId
     * @param oldUserType
     * @return
     */
    DodopalResponse<Boolean> updateUserPwdByOldUserId(String oldUserId, String oldUserType, String password);
}
