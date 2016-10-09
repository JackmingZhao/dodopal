package com.dodopal.users.business.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.dao.MerchantMapper;
import com.dodopal.users.business.dao.MerchantUserMapper;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantUserQuery;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.delegate.AccountManagementDelegate;
@Service
public class MerchantUserServiceImpl implements MerchantUserService{
	private final static Logger logger = LoggerFactory.getLogger(MerchantUserServiceImpl.class);
    @Autowired
    private MerchantUserMapper mapper; 
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    AccountManagementDelegate accountManagementDelegate;
    
    @Transactional(readOnly = true)
    public List<MerchantUser> findMerchantUser(MerchantUser user) {
        return mapper.findMerchantUser(user);
    }

    @Transactional
    public boolean insertMerchantUser(MerchantUser user,UserClassifyEnum userClassify) {
        String reg = UsersConstants.REG_USER_NAME;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(user.getMerUserName());
        if(matcher.matches()){
            if(!mapper.checkExist(user)){
                user.setCreateDate(new Date());
                user.setUserCode(generateMerUserCode(userClassify));
                mapper.insertMerchantUser(user);
                //如果mercode不为空则查找商户类型是否是集团用户
                if(StringUtils.isNotBlank(user.getMerCode())){
                    Merchant merchant = merchantMapper.findMerchantByMerCode(user.getMerCode());
                    if(merchant!=null && MerTypeEnum.GROUP.getCode().equals(merchant.getMerType())){
                        //保存集团用户管辖的部门
                        if(null!=user.getMerGroupDeptList()){
                            for(String deptId:user.getMerGroupDeptList()){
                                mapper.saveMerDeptUser(user.getMerUserName(), deptId);
                            }
                        }
                    }
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updateMerchantUserWithMerchant 
     * @Description: 更新商户信息时更新商户表的联系人及手机号
     * @param user
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    @Transactional
    public int updateMerchantUserWithMerchant(MerchantUser user) {
        MerchantUser userQuery = new MerchantUser();
        userQuery.setId(user.getId());
        userQuery.setMerUserName(user.getMerUserName());
        MerchantUser merUser = mapper.findMerchantUserExact(userQuery);
        if(null==merUser){
            return 0;
        }
        //如果当前更新的商户用户为商户的管理员，则同时更新商户的联系人及手机号
        if(StringUtils.isNotBlank(merUser.getMerCode())&&StringUtils.isNotBlank(merUser.getMerUserFlag())){
            if(MerUserFlgEnum.ADMIN.getCode().equals(merUser.getMerUserFlag())){
                Merchant merchant = merchantMapper.findMerchantByMerCode(merUser.getMerCode());
                merchant.setMerLinkUser(user.getMerUserNickName());    
                merchant.setMerLinkUserMobile(user.getMerUserMobile());
                merchant.setMerEmail(user.getMerUserEmail());
                merchant.setUpdateUser(user.getUpdateUser());
                merchantMapper.updateMerchantMobileLinkUser(merchant);
            }
        }
        user.setUpdateDate(new Date());
        if(checkUpdateUserInfo(user)){
            return mapper.updateMerchantUser(user);
        }
        return 0;
    }

    @Transactional
    public int updateMerchantUser(MerchantUser user) {
        user.setUpdateDate(new Date());
        if(StringUtils.isBlank(user.getId()) && StringUtils.isBlank(user.getMerUserName()) && StringUtils.isBlank(user.getUserCode())){
        	return 0;
        }
        return mapper.updateMerchantUser(user);
    }
    @Transactional
    public int updateMerchantUserPortal(MerchantUser user) {
        user.setUpdateDate(new Date());
        if(checkUpdateUserInfo(user)){
            if(StringUtils.isNotBlank(user.getMerCode())){
                Merchant merchant = merchantMapper.findMerchantByMerCode(user.getMerCode());
                if(MerTypeEnum.GROUP.getCode().equals(merchant.getMerType())){
                    // 先删除用户原管辖的部门关系
                    if(StringUtils.isNotBlank(user.getMerUserName())){
                        mapper.deleteMerDeptUserByUserName(user.getMerUserName());
                        if(CollectionUtils.isNotEmpty(user.getMerGroupDeptList())){
                            for(String deptId:user.getMerGroupDeptList()){
                                //保存变更后的用户部门关系
                                mapper.saveMerDeptUser(user.getMerUserName(), deptId);
                            }
                        }
                    }
                }
            }
            return mapper.updateMerchantUser(user);
        }
        return 0;
    }
    
    private boolean checkUpdateUserInfo(MerchantUser user) {
        return StringUtils.isNotBlank(user.getUserCode())||StringUtils.isNotBlank(user.getUserCode())||StringUtils.isNotBlank(user.getMerUserName());
    }

    @Transactional
    public int deleteMerchantUser(String userId) {
        return mapper.deleteMerchantUser(userId);
    }

    @Transactional(readOnly = true)
    public MerchantUser findMerchantUserById(String id) {
        return mapper.findMerchantUserById(id);
    }

    @Transactional
    public int batchStopUser(String flag,List<String> ids,String updateUser) {
        int updateRow = 0;
        if(!ids.isEmpty()){
            Map<String, Object> map  =  new HashMap<String, Object>();
            map.put("activate", flag);
            map.put("list", ids);
            map.put("updateUser", updateUser);
            updateRow = mapper.startOrStopUser(map);
        }
        return updateRow;
    }

    @Override
    @Transactional
    public int batchActivateUser(String merCode, String updateUser, ActivateEnum activate, List<String> userCodes) {
        return mapper.batchActivateOperator(merCode, activate.getCode(), updateUser, userCodes);
    }

    @Transactional
    public boolean modifyPWD(MerchantUser user,String oldPwd) {
        MerchantUser oldUser = new MerchantUser();
        oldUser.setMerUserName(user.getMerUserName());
        if(StringUtils.isNotBlank(oldPwd)){
            oldUser.setMerUserPWD(oldPwd);
            if(StringUtils.isNotBlank(user.getMerUserName())||StringUtils.isNotBlank(user.getMerUserPWD())){
                List<MerchantUser> oldUserList = mapper.findByUsernameAndPWd(oldUser);
                if(!oldUserList.isEmpty()){
                    user.setUpdateDate(new Date());
                    int row =  mapper.modifyPWD(user);
                    if(row==1){
                        return true;
                    }
                 }
            }
        }
        return false;
    }

    @Transactional
    public boolean resetPWD(MerchantUser user) {
        user.setUpdateDate(new Date());
        int rows = mapper.resetPWD(user);
        if(rows==1){
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean resetPWDByMobile(String mobile,String pwd){
        String id = mapper.findMerchantUserByMobile(mobile);
        if(null==id){
            return false;
        }
        MerchantUser user = new MerchantUser();
        user.setId(id);
        user.setMerUserPWD(pwd);
        user.setUpdateDate(new Date());
        int rows = mapper.resetPWD(user);
        if(rows==1){
            return true;
        }
        return false;
    }

    @Transactional
    public boolean modifyPayInfoFlg(MerchantUser user) {
        user.setUpdateDate(new Date());
        int rows = mapper.modifyPayInfoFlg(user);
        if(rows ==1)
            return true;
        else
            return false;
    }
    @Transactional(readOnly = true)
    public MerchantUser findMerchantUserAndMerNameById(String id){
        return mapper.findMerchantUserAndMerNameById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public String generateMerUserCode(UserClassifyEnum userClassify) {
        StringBuffer sb = new StringBuffer();
        // 是否测试账户(1位):1-正式商户用户; 2--测试商户用户; 3--正式个人用户;  4--个人测试用户
        sb.append(userClassify.getCode());
        // 4位随机数
        int number = new Random().nextInt(9999) + 1;
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        String randomNum = formatter.format(number);
        sb.append(randomNum);
        // 数据库12位sequence
        String seq = mapper.getMerUserCodeSeq();
        sb.append(seq);
        return sb.toString();
    }

    @Transactional(readOnly = true)
    public List<MerchantUser> findMerchantUserAndMerName(MerchantUser user) {
        return mapper.findMerchantUserAndMerName(user);
    }
    
    @Transactional(readOnly = true)
    public MerchantUser findMerchantUserByUserName(String userName) {
        MerchantUser user = mapper.findMerchantUserByUserName(userName);
        if(null!=user){
            List<String>merGroupDeptList = mapper.findMerGroupDeptByUserName(userName);
            List<String>merGroupDeptNameList = mapper.findMerGroupDeptNameByUserName(user.getMerUserName());
            user.setMerGroupDeptNameList(merGroupDeptNameList);
            user.setMerGroupDeptList(merGroupDeptList);
        }
        return user;
    }
    @Transactional(readOnly = true)
    public MerchantUser findMerchantUserByUserCode(String userCode) {
        MerchantUser user = mapper.findMerchantUserByUserCode(userCode);
        if(null!=user&&StringUtils.isNotBlank(user.getMerUserName())){
            List<String>merGroupDeptList = mapper.findMerGroupDeptByUserName(user.getMerUserName());
            List<String>merGroupDeptNameList = mapper.findMerGroupDeptNameByUserName(user.getMerUserName());
            user.setMerGroupDeptNameList(merGroupDeptNameList);
            user.setMerGroupDeptList(merGroupDeptList);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExist(MerchantUser merUser) {
        return mapper.checkExist(merUser);
    }

    @Override
    @Transactional(readOnly = true)
    public MerchantUser findMerchantUserExact(MerchantUser merUser) {
        return mapper.findMerchantUserExact(merUser);
    }
    
    @Transactional(readOnly = true)
    public DodopalDataPage<MerchantUser> findMerchantUserAndMerNameByPage(MerchantUserQuery userQuery) {
        List<MerchantUser> result = mapper.findMerchantUserAndMerNameByPage(userQuery);
        DodopalDataPage<MerchantUser> pages = new DodopalDataPage<MerchantUser>(userQuery.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<MerchantUser> findMerchantUserByPage(MerchantUserQuery userQuery) {
        List<MerchantUser> result = mapper.findMerchantUserAndMerNameByPage(userQuery);
        DodopalDataPage<MerchantUser> pages = new DodopalDataPage<MerchantUser>(userQuery.getPage(), result);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public String checkMerUserByMobile(String mobile) {
        return mapper.findMerchantUserByMobile(mobile);
    }

    @Override
    @Transactional(readOnly = true)
    public String findMerchantUserNameByMobile(String mobile) {
        return  mapper.findMerchantUserNameByMobile(mobile);
    }

    @Override
    @Transactional
    public int batchDelMerUserByMerCodes(List<String> merCodes) {
        return mapper.batchDelMerUserByMerCodes(merCodes);
    }

    @Override
    @Transactional
    public void batchSaveMerDeptUser(String merUserName, List<String> merGrpDeptId) {
        for(String grpDeptId:merGrpDeptId){
            mapper.saveMerDeptUser(merUserName, grpDeptId);
        } 
    }

    @Override
    @Transactional
    public int delMerDeptUserByUserName(String merUserName) {
        return mapper.deleteMerDeptUserByUserName(merUserName);
    }

    @Override
    @Transactional
    public int delMerDeptUserById(List<String> ids) {
        return mapper.deleteMerDeptUserByDeptId(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<MerchantUser> findMerOperatorByPage(MerchantUserQuery userQuery) {
        List<MerchantUser> result = mapper.findMerOperatorByPage(userQuery);
        DodopalDataPage<MerchantUser> pages = new DodopalDataPage<MerchantUser>(userQuery.getPage(), result);
        return pages;
    }

    @Override
    @Transactional
	public int updateLoginDataByUserName(MerchantUser merUser, boolean isSuccess) {
		MerchantUser findUser = mapper.findMerUserByUserNameForUpdate(merUser.getMerUserName());
		if (findUser == null) {
			return 0;
		}
		if(isSuccess) {
			// 更新最后一次登录时间
			merUser.setMerUserLastLoginDate(new Date());
			// 重置登录失败次数为0
			merUser.setMerUserLoginFaiCount(0);
		}else {
			// 登录失败次数+1
			int merUserLoginFaiCount = findUser.getMerUserLoginFaiCount();
			merUser.setMerUserLoginFaiCount(merUserLoginFaiCount + 1);
		}
		return mapper.updateLoginDataByUserName(merUser);
	}

    @Override
    @Transactional
    public int updateRejectMerchantUserByUserName(MerchantUser merUser) {
        return mapper.updateRejectMerchantUserByUserName(merUser);
    }

    @Override
    @Transactional
    public int updateRejectMerchantUserByUserCode(MerchantUser merUser) {
        return mapper.updateRejectMerchantUserByUserCode(merUser);
    }

	@Override
	@Transactional
	public int updateMerUserBusCity(String cityCode, String userId) {
		return mapper.updateMerUserBusCity(cityCode, userId);
	}

	@Override
	@Transactional
	public int updateMerUserMobile(String merUserMobile, String updateUser) {
		int result = mapper.updateMerUserMobileById(merUserMobile, updateUser);
		MerchantUser merUser = mapper.findMerchantUserById(updateUser);
		String merUserFlag = merUser.getMerUserFlag();
		// 如果是商户管理员,更新商户手机号
		if (MerUserFlgEnum.ADMIN.getCode().equals(merUserFlag)) {
			String merCode = merUser.getMerCode();
			if (StringUtils.isNotBlank(merCode)) {
				merchantMapper.updateMerLinkUserMobile(merCode, merUserMobile, updateUser);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public int updateMerUserPWDById(String merUserPWD, String userId) {
		return mapper.updateMerUserPWDById(merUserPWD, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean checkMerUserPWD(String userId, String merUserPWD) {
		MerchantUser merUser = mapper.findMerchantUserById(userId);
		if (merUser != null) {
			if(merUser.getMerUserPWD().equals(merUserPWD)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean registertUser(MerchantUser user) {
		// 生成用户编号
		String userCode = generateMerUserCode(UserClassifyEnum.PERSONAL);
		// 保存用户信息
		user.setUserCode(userCode);
		// 审核状态默认为审核通过
		user.setMerUserState(MerStateEnum.THROUGH.getCode());
		mapper.insertMerchantUser(user);

		// 调用账户接口创建账户
		DodopalResponse<String> response = accountManagementDelegate.createAccount(user.getMerUserType(), userCode, FundTypeEnum.FUND.getCode(), MerTypeEnum.PERSONAL.getCode(), user.getId());
		if (!ResponseCode.SUCCESS.equals(response.getCode())) {
			if (logger.isInfoEnabled()) {
				logger.info("registertUser---->调用账户系统创建账户接口失败,错误码:" + response.getCode());
			}
			throw new DDPException(ResponseCode.USERS_CREATE_ACCOUNT_FAIL);
		}
		return true;
	}

	@Transactional(readOnly = true)
    public String findNickNameByUserId(String userId) {
        return mapper.findNickNameByUserId(userId);
    }

	@Override
	@Transactional(readOnly = true)
	public int getCurDayPrdRcgCount(String userCode) {
		return mapper.getCurDayPrdRcgCount(userCode);
	}
	@Transactional(readOnly = true)
	public List<MerchantUser>  getExportExcelMerUserList(MerchantUserQuery userQuery){
	    List<MerchantUser> resp = new ArrayList<MerchantUser>();
        if(userQuery == null || userQuery.getPage() == null) {
            resp = new ArrayList<MerchantUser>();
        }else {
            resp = mapper.getExportExcelMerUserList(userQuery);;
        }
        return resp;
    }

	@Override
	@Transactional
	public int updateProviderAdminUser(MerchantUser merUser) {
		return mapper.updateProviderAdminUser(merUser);
	}

	@Override
	@Transactional(readOnly = true)
	public String findUnionLoginUserName(String userid, String usertype) {
		return mapper.findUnionLoginUserName(userid, usertype);
	}

	@Override
	@Transactional(readOnly = true)
	public String findUserIdByOldUserId(String oldUserId, String oldUserType) {
		return mapper.findUserIdByOldUserId(oldUserId, oldUserType);
	}
}
