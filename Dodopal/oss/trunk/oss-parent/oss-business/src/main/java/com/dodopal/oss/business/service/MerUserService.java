package com.dodopal.oss.business.service;

import java.util.List;
import java.util.Map;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.MerUserCardBDBean;
import com.dodopal.oss.business.bean.MerUserCardBDLogBean;
import com.dodopal.oss.business.bean.MerchantUserBean;
import com.dodopal.oss.business.bean.MerchantUserExpBean;
import com.dodopal.oss.business.bean.query.MerUserCardBDFindBean;
import com.dodopal.oss.business.bean.query.UserCardLogQuery;
import com.dodopal.oss.business.model.dto.MerchantUserQuery;

public interface MerUserService {
    
	/******************************************用户信息******************************************开始*/
    /**
     * 查询用户信息
     * @param merUserBean
     * @return 
     */
    public  DodopalResponse<List<MerchantUserBean>> findMerUsers(MerchantUserBean merUserBean);
    
    
    
    public  DodopalResponse<DodopalDataPage<MerchantUserBean>> findMerUsersByPage(MerchantUserQuery userQuery);

    /**
     * 查看用户信息
     * @param userId 用户ID
     * @return 
     */
    public  DodopalResponse<MerchantUserBean> findMerUser(String userId);
    
    
    /** 
     * @Title: startOrStopUser 
     * @Description: 启用或停用
     * @param idList
     * @param activate
     * @return    设定文件 
     * DodopalResponse<Map<String,String>>    返回类型 
     */
    public  DodopalResponse<Map<String,String>> startOrStopUser(List<String> idList,String activate,String updateUser);

    /**
     * 重置密码
     * @param userId 用户ID
     * @return 
     */
    public  DodopalResponse<String> resetPwd(String userId);
    
    /******************************************用户信息******************************************结束*/
    
    /******************************************用户卡片信息******************************************开始*/
    /**
     * 查询用户信息
     * @param merUserBean
     * @return 
     */
    public  DodopalResponse<List<MerUserCardBDBean>> findMerUserCards(MerUserCardBDFindBean bean);
    
    /** 
     * @Title: findMerUserCardBDListByPage 
     * @Description: 分页查询
     * @param findBean
     * @return    设定文件 
     * DodopalDataPage<MerUserCardBDDTO>    返回类型 
     * @throws 
     */
    public  DodopalResponse<DodopalDataPage<MerUserCardBDBean>> findMerUserCardBDListByPage(MerUserCardBDFindBean findBean);
    
    /**
     * 解绑卡片
     * @param merUserBean
     * @return 
     */
    public  DodopalResponse<String> unbundUserCards(String operName,List<String> userCardIds,String longName);
    
    
    /** 
     * @Title: updateMerUser 
     * @Description: 更新用户信息
     * @param merUserBean
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    public  DodopalResponse<Boolean> updateMerUser(MerchantUserBean merUserBean);

    /**
     * 卡片操作日志查询
     * @param dto
     * @return DodopalDataPage<MerUserCardBDLogBean>
     */
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> findUserCardLogByPage(UserCardLogQuery dto);
    
    /******************************************用户卡片信息******************************************结束*/
   
    /******************************************修改企业用户密码******************************************结束*/
   
    public  DodopalResponse<String> activateMechant(MerchantUserBean merUserBean);
    
    public  DodopalResponse<String> editMerchant(MerchantUserBean merUserBean);
    
    List<MerchantUserBean> getExportExcelMerUserList(MerchantUserQuery merUser);
    List<MerUserCardBDBean> getExportExcelMerUserCardList(MerUserCardBDFindBean findBean);
    List<MerUserCardBDLogBean> getExportExcelUserCardLog(UserCardLogQuery dto);
    
    /******************************************商户信息导出查询******************************************结束*/
    public  DodopalResponse<DodopalDataPage<MerchantUserExpBean>> findMerUsersExpByPage(MerchantUserQuery userQuery);

}
