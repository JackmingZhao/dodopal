package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantUserQuery;

public interface MerchantUserService {
    public List<MerchantUser> findMerchantUserAndMerName(MerchantUser user);
    
    public DodopalDataPage<MerchantUser>  findMerchantUserAndMerNameByPage(MerchantUserQuery user);
    
    public List<MerchantUser> findMerchantUser(MerchantUser user);

    public DodopalDataPage<MerchantUser> findMerchantUserByPage(MerchantUserQuery userQuery);

    public DodopalDataPage<MerchantUser> findMerOperatorByPage(MerchantUserQuery userQuery);

    public boolean insertMerchantUser(MerchantUser user,UserClassifyEnum userClassify);

    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updateMerchantUser 
     * @Description: oss内调用的更新方法
     * @param user
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int updateMerchantUser(MerchantUser user);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updateMerchantUserPortal 
     * @Description: 门户调用的更新方法
     * @param user
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int updateMerchantUserPortal(MerchantUser user);
    
    public int deleteMerchantUser(String userId);
    
    public MerchantUser findMerchantUserById(String id);
    
    public MerchantUser findMerchantUserAndMerNameById(String id);
    /**
     * 批量启用或停用用户
     */
    public int batchStopUser(String flag,List<String> ids,String updateUser);

    public int batchActivateUser(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes);

    public int updateMerchantUserWithMerchant(MerchantUser user);
    /** 
     * @Title: findMerchantUserNameByMobile 
     * @Description: 根据手机号查询用户账号
     * @param mobile
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    public String findMerchantUserNameByMobile(String mobile);
    /** 
     * @Title: checkMerUserByMobile 
     * @Description: 根据手机号关联merchant表查找审核通过的手机是否存在
     * @param mobile
     * @return    设定文件 
     * String   返回不为空证明手机号已被注册
     * @throws 
     */
    public String checkMerUserByMobile(String mobile);

    /** 
     * @Title: modifyPWD 
     */
    public boolean modifyPWD(MerchantUser user,String oldPwd);
    
    
    /** 
     * @Title: resetPWD 
     * @Description: 用户重置，必要字段  id 密码， 备选 账号
     * @param user
     * @return    设定文件 
     * boolean    返回类型 
     * @throws 
     */
    public boolean resetPWD(MerchantUser user);
    
    /** 
     * @Title: resetPWDByMobile 
     * @Description: 根据手机号，校验用户，重置用户密码
     * @param mobile  手机号
     * @param pwd  密码
     * @return    设定文件 
     * boolean    返回类型 
     * @throws 
     */
    public boolean resetPWDByMobile(String mobile,String pwd);
    /** 
     * @Title: modifyPayInfoFlg 
     * @Description: 支付确认信息标识修改
     * @param user
     * @return    设定文件 
     * boolean    返回类型 
     * @throws 
     */
    public boolean modifyPayInfoFlg(MerchantUser user);

    /**
     * 生成用户编号
     * 1位(是否为测试账户)+ 4位随机数 + 12位数据库sequence.
     * 99-个人;  1-正式商户用户; 2--测试商户用户; 3--正式个人用户;  4--个人测试用户
     * @param merType
     * @param classify
     * @return
     */
    public String generateMerUserCode(UserClassifyEnum userClassify);

    /**
     * 查找用户是否存在
     * @param merUser
     * @return
     */
    public boolean checkExist(MerchantUser merUser);

    /**
     * 精确查找用户信息
     * @param merUser
     * @return
     */
    public MerchantUser findMerchantUserExact(MerchantUser merUser);

    /**
     * 根据商户号批量删除用户信息
     * @param merCodes
     * @return
     */
    public int batchDelMerUserByMerCodes(List<String> merCodes);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: batchSaveMerDeptUser 
     * @Description: 根据集团用户名(登录用户)保存管辖部门
     * @param merUserName
     * @param merGrpDeptId
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public void batchSaveMerDeptUser(String merUserName,List<String> merGrpDeptId);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: batchDelMerDeptUser 
     * @Description: 根据用户名删除管辖部门
     * @param merUserName
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int delMerDeptUserByUserName(String merUserName);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: delMerDeptUserById 
     * @Description: 根据部门id删除用户关联的部门
     * @param ids
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int delMerDeptUserById(List<String> ids);
    
    public MerchantUser findMerchantUserByUserCode(String userCode); 

    public MerchantUser findMerchantUserByUserName(String userName);

    /**
     * 更新用户登录数据
     * @param merUser
     * @return
     */
    public int updateLoginDataByUserName(MerchantUser merUser, boolean isSuccess);

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
     * @param userId
     * @return
     */
    public int updateMerUserBusCity(String cityCode, String userId);

    /**
     * 修改用户手机号,如果是商户管理员,则修改商户手机号
     * @param merUser
     * @return
     */
    public int updateMerUserMobile(String merUserMobile, String updateUser);

    /**
     * 根据用户ID修改密码
     * @param merUserPWD
     * @param userId
     * @return
     */
    public int updateMerUserPWDById(String merUserPWD, String userId);

    /**
     * 校验用户密码是否正确
     * @param userId
     * @param merUserPWD
     * @return
     */
    public boolean checkMerUserPWD(String userId, String merUserPWD);

    /**
     * 个人用户门户注册
     * @param user
     * @return
     */
    public boolean registertUser(MerchantUser user);
    
    /**
     * 根据userId 查询用户姓名
     * @param userId 用户id
     * @return
     */
    public String findNickNameByUserId(String userId);

    /**
     * 获取个人当日充值成功次数 
     * @param userCode
     * @return
     */
    public int getCurDayPrdRcgCount(String userCode);

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
    public String findUnionLoginUserName(String userid, String usertype);

    /**
     * 根据老平台用户id和用户类型查出新平台对应id
     * @param oldUserId
     * @param oldUserType
     * @return
     */
    public String findUserIdByOldUserId(String oldUserId, String oldUserType);
}
