package com.dodopal.users.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantUserQuery;

public interface MerchantUserMapper {
    
    /** 
     * @Title: findMerchantUserAndMerName 
     * @Description: 查询用户列表及商户信息
     * @param user
     * @return    设定文件 
     * List<MerchantUser>    返回类型 
     * @throws 
     */
    public List<MerchantUser> findMerchantUserAndMerName(MerchantUser user);
    /** 
     * @Title: findMerchantUserAndMerNameByPage 
     * @Description:  查询用户列表及商户信息后台分页
     * @param user
     * @return    设定文件 
     * List<MerchantUser>    返回类型 
     */
    public List<MerchantUser> findMerchantUserAndMerNameByPage(MerchantUserQuery user);
    /** 
     * @Title: findMerchantUser 
     * @Description: 查找用户列表
     * @param user
     * @return    设定文件 
     * List<MerchantUser>    返回类型 
     * @throws 
     */
    public List<MerchantUser> findMerchantUser(MerchantUser user);

    public List<MerchantUser> findMerchantUserByPage(MerchantUserQuery user);

    /**
     * 根据商户号和查询条件查询操作员
     * @param user
     * @return
     */
    public List<MerchantUser> findMerOperatorByPage(MerchantUserQuery user);

    /** 
     * @Title: insertMerchantUser 
     * @Description: 保存用户
     * @param user    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void insertMerchantUser(MerchantUser user);

    public int deleteMerchantUser(String id);
    
    /** 
     * @Title: findMerchantUserById 
     * @Description: 单个用户信息查询
     * @param id
     * @return    设定文件 
     * MerchantUser    返回类型 
     * @throws 
     */
    public MerchantUser findMerchantUserById(String id);
    
    
    /** 
     * @Title: findMerchantUserAndMerNameById 
     * @Description: 单个用户信息查询附带所属商户信息
     * @param id
     * @return    设定文件 
     * MerchantUser    返回类型 
     * @throws 
     */
    public MerchantUser findMerchantUserAndMerNameById(String id);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findMerchantUserAndMerNameByUserName 
     * @Description:    
     * @param UserName
     * @return    设定文件 
     * MerchantUser    返回类型 
     * @throws 
     */
    public MerchantUser findMerchantUserByUserName(@Param("userName")String userName);

    /**
     * 根据登录用户名查询用户（有锁）
     * @param userName
     * @return
     */
    public MerchantUser findMerUserByUserNameForUpdate(@Param("userName")String userName);

    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findMerchantUserByUserCode 
     * @Description: 用户号查找
     * @param userCode
     * @return    设定文件 
     * MerchantUser    返回类型 
     * @throws 
     */
    public MerchantUser findMerchantUserByUserCode(@Param("userCode")String userCode);
    /** 
     * @Title: updateMerchantUser 
     * @Description: 更新用户
     * @param user
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int updateMerchantUser(MerchantUser user);
    
    /** 
     * @Title: startOrStopUser 
     * @Description: 批量启用或停止
     * @param idList
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int startOrStopUser(Map<String ,Object> map);

    /**
     * 批量停启用用户
     * @param activate
     * @param updateUser
     * @param userCodes
     * @return
     */
    public int batchActivateOperator(@Param("merCode") String merCode, @Param("activate") String activate, @Param("updateUser") String updateUser, @Param("userCodes") List<String> userCodes);

    /** 
     * 修改密码
     */
    public int modifyPWD(MerchantUser user);
    
    /** 
     * 根据用户名密码查找
     */
    public List<MerchantUser> findByUsernameAndPWd(MerchantUser user);
    
    /** 
     * 重置密码
     */
    public int resetPWD(MerchantUser user);
    
    /** 
     * 支付确认信息标识修改
     */
    public int modifyPayInfoFlg(MerchantUser user);

    /**
     * 用户编码使用的sequence
     * @return
     */
    public String getMerUserCodeSeq();

    /**
     * 查找用户是否存在(用户名或手机号)
     * @param merUser
     * @return
     */
    public boolean checkExist(MerchantUser merUser);

    /**
     * 精确查找用户信息
     * @param merUserName
     * @return
     */
    public MerchantUser findMerchantUserExact(MerchantUser merUser);
    
    /** 
     * @Title: findMerchantUserByMobile 
     * @Description: 根据手机号查到用户id
     * @param mobile
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String findMerchantUserByMobile(String mobile);
    
    /** 
     * @Title: findMerchantUserNameByMobile 
     * @Description:根据手机号查到用户账号
     * @param mobile
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String  findMerchantUserNameByMobile(String mobile);

    /**
     * 根据商户号批量删除商户用户
     * @param merCodes
     * @return
     */
    public int batchDelMerUserByMerCodes(@Param("merCodes") List<String> merCodes);
    
    
    /**
     * @author dingkuiyuan@dodopal.com
     * @Title: saveMerDeptUser 
     * @Description:保存商户管辖中间表
     * @param map
     * @return    设定文件 
     * int    返回类型 
     * @throws
     */
    public int saveMerDeptUser(@Param("merUserName")String merUserName,@Param("merGrpDepId")String merGrpDepId); 
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: deleteMerDeptUser 
     * @Description: 删除商户管辖中间表
     * @param names
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int deleteMerDeptUserByUserName(String merUserName);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: deleteMerDeptUserByIdAndName 
     * @Description: 
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int deleteMerDeptUserByDeptId(List<String> deptId);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findMerGroupDeptByUserName 
     * @Description: 根据用户账号查找所属部门
     * @param userName
     * @return    设定文件 
     * List<String>    返回类型 
     * @throws 
     */
    public List<String> findMerGroupDeptByUserName(String userName);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findMerGroupDeptNameByUserName 
     * @Description: 返回机构名
     * @param userName
     * @return    设定文件 
     * List<String>    返回类型 
     * @throws 
     */
    public List<String> findMerGroupDeptNameByUserName(String userName);

    /**
     * 更新用户登录数据
     * @param merUser
     * @return
     */
    public int updateLoginDataByUserName(MerchantUser merUser);

    /**
     * 更新审核不通过商户用户信息(OSS)
     * @param merUser
     * @return
     */
    public int updateRejectMerchantUserByUserName(MerchantUser merUser);

    /**
     * 更新审核不通过商户用户信息(门户)
     * @param merUser
     * @return
     */
    public int updateRejectMerchantUserByUserCode(MerchantUser merUser);

    /**
     * 修改用户默认业务城市
     * @param cityCode
     * @param id
     * @return
     */
    public int updateMerUserBusCity(@Param("cityCode")String cityCode,@Param("id")String id);

    /**
     * 修改用户手机号
     * @param merUserMobile
     * @param id
     * @return
     */
    public int updateMerUserMobileById(@Param("merUserMobile")String merUserMobile,@Param("id")String id);

    /**
     * 修改用户密码
     * @param merUserPWD
     * @param id
     * @return
     */
    public int updateMerUserPWDById(@Param("merUserPWD")String merUserPWD,@Param("id")String id);
    
    /**
     * 根据userId 查询用户姓名
     * @param userId  用户id
     * @return
     */
    public String findNickNameByUserId(@Param("userId")String userId);

    /**
     * 获取个人当日充值成功次数 
     * @return
     */
	public int getCurDayPrdRcgCount(@Param("userCode") String userCode);
	 /**
     * 导出用户信息列表
     * @return
     */
    public List<MerchantUser> getExportExcelMerUserList(MerchantUserQuery user);

    /**
     * 更新供应商管理员信息
     * @param merUser
     * @return
     */
    public int updateProviderAdminUser(MerchantUser merUser);

    /**
     * 根据老平台用户类型和用户id查出对应的用户名
     * @param userid
     * @param usertype
     * @return
     */
    public String findUnionLoginUserName(@Param("userid")String userid, @Param("usertype")String usertype);

    /**
     * 根据老平台用户id和用户类型查出新平台对应id
     * @param userid
     * @param usertype
     * @return
     */
    public String findUserIdByOldUserId(@Param("oldUserId")String oldUserId, @Param("oldUserType")String oldUserType);
}
