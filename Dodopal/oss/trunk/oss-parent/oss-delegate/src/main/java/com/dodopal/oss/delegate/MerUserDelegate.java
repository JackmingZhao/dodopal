package com.dodopal.oss.delegate;

import java.util.List;
import java.util.Map;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerUserCardBDDTOQuery;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;


/**
 * @author vn
 *
 */
public interface MerUserDelegate {
    
    DodopalResponse<DodopalDataPage<MerchantUserDTO>> findUserInfoListByPage(MerchantUserQueryDTO userDTO);

    /**
     * 商户/个人用户 用户信息查询
     * @param userDTO
     * @return
     */
    public DodopalResponse<List<MerchantUserDTO>> findMerUserList(MerchantUserDTO userDTO);
    
    /**
     * 查看个人用户信息
     * @param userID 用户ID
     * @return
     */
    public DodopalResponse<MerchantUserDTO> findMerUser(String userID);
    
    /**
     * 重置密码发短信
     * @param moblieNum 手机号
     * @return
     */
    public DodopalResponse<Map<String, String>> resetPwdSendMsg(String mobileNum);
    
    /**
     * 重置密码
     * @param 用户信息
     * @return
     */
    public DodopalResponse<Boolean> resetPwd(MerchantUserDTO userDTO);
    
    /**
     * 查询用户卡片信息
     * @param findDTO 查询条件
     * @return
     */
    public DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardList(MerUserCardBDFindDTO findDTO);
    
    /**
     * 解绑用户卡片
     * @param userCardIds 解绑信息ID
     * @param longName 登陆用户名
     * @return
     */
    public DodopalResponse<String> unbundUserCards(String operName,List<String> userCardIds,String longName);

    /** 
     * @Title: findMerUserCardBDListByPage 
     * @Description: 分页查询
     * @param cardBDFindDTO
     * @return    设定文件 
     * DodopalDataPage<MerUserCardBDDTO>    返回类型 
     * @throws 
     */
    public  DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> findMerUserCardBDListByPage(MerUserCardBDDTOQuery cardBDFindDTO);
    
    /** 
     * @Title: updateUser 
     * @Description: 更新用户
     * @param userDTO
     * DodopalResponse<Boolean>    返回类型 
     * @throws 
     */
    public DodopalResponse<Boolean> updateUser(MerchantUserDTO userDTO);
    
    /**
     * 编辑
     * @param 
     * @param 
     * @return
     */
    public DodopalResponse<String> EditMerchant(MerchantUserDTO userDTO);

    
    
    /**
     * 启用
     * @param 
     * @param 
     * @return
     */
    public DodopalResponse<String> ActivateMerchant(MerchantUserDTO userDTO);
    
    
    /** 
     * @Title: startOrStop 
     * @Description: 启用或停用
     * @param userDTO
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    public DodopalResponse<Map<String,String>> startOrStop(List <String>idList, ActivateEnum activate,String updateUser);
    
    public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerUsersByPage(MerchantUserQueryDTO dto);
    
    /**
     * 卡片操作日志查询（分页）
     * @param dto
     * @return DodopalDataPage<MerUserCardBDLogDTO>
     */
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> findUserCardLogByPage(UserCardLogQueryDTO dto);

    public List<MerchantUserDTO> getExportExcelMerUserList(MerchantUserQueryDTO dto);

    List<MerUserCardBDDTO> getExportExcelMerUserCardList(MerUserCardBDDTOQuery findDto);

    List<MerUserCardBDLogDTO> getExportExcelUserCardLog(UserCardLogQueryDTO dto);


}
