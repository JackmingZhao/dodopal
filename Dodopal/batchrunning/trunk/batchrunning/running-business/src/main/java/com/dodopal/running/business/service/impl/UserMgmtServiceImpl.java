package com.dodopal.running.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.mail.MailSender;
import com.dodopal.common.md5.MD5;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalMailMessage;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.running.business.constant.RunningConstants;
import com.dodopal.running.business.dao.RoleMapper;
import com.dodopal.running.business.dao.UserMapper;
import com.dodopal.running.business.model.Role;
import com.dodopal.running.business.model.TreeNode;
import com.dodopal.running.business.model.User;
import com.dodopal.running.business.model.UserQuery;
import com.dodopal.running.business.service.UserMgmtService;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Transactional
    @Override
    public String saveOrUpdateUser(User user) {
        validateUser(user);
        if (DDPStringUtil.isNotPopulated(user.getId())) {
            user.setCreateDate(new Date());
            user.setUpdateDate(new Date());
            userMapper.insertUser(user);
        } else {
            user.setUpdateDate(new Date());
            userMapper.updateUser(user);
        }
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findUsers(UserQuery user) {
        return userMapper.findUsers(user);
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        if (StringUtils.isNotEmpty(userId)) {
            User user = userMapper.findUserById(userId);
            if (user != null) {
                if (RunningConstants.ADMIN_USER.equalsIgnoreCase(user.getLoginName())) {
                    throw new DDPException("validate.error:\n", "admin 为超级管理员用户,不允许操作。");
                }
                userMapper.deleteUser(userId);
            }
        }
    }

    private void validateUser(User user) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(user.getName(), 64)) {
            msg.add("用户姓名不能为空或长度不能超过64个字符");
        }

        //        if (!DDPStringUtil.existingWithLength(user.getPassword(), 64)) {
        //            msg.add("角色密码不能为空或长度不能超过200个字符");
        //        }

        int count = userMapper.countUser(user.getLoginName());
        if ((DDPStringUtil.isNotPopulated(user.getId()) && count >= 1)) {
            msg.add("该用户名已经存在");
        }else if(DDPStringUtil.isPopulated(user.getId()) && count >= 1){
            User tempUser = userMapper.findUserById(user.getId());
            if(!tempUser.getLoginName().equalsIgnoreCase(user.getLoginName())){
                msg.add("该用户名已经存在");
            }
        }

        if (RunningConstants.ADMIN_USER.equalsIgnoreCase(user.getLoginName())) {
            msg.add("admin 为超级管理员用户,不允许操作");
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    @Override
    public List<TreeNode> loadRoles(User user) {
        List<TreeNode> trunkNodes = new ArrayList<TreeNode>();
        boolean isInit = (user == null || DDPStringUtil.isNotPopulated(user.getId())) ? true : false;
        List<String> assignedRoles = null;
        if (!isInit) {
            assignedRoles = user.getRoleIdList();
        }
        Role queryRole = new Role();
        List<Role> roles = roleMapper.findRoles(queryRole);
        if (roles != null) {
            for (Role role : roles) {
                TreeNode node = new TreeNode();
                node.setId(role.getId());
                node.setIconCls("icon-system-user"); // 以后如有需要可以添加
                String nodeText = role.getName();
//2015年6月29日12:54:02注释掉详情
//                if (DDPStringUtil.isPopulated(role.getDescription())) {
//                    nodeText += "  ---------  " + role.getDescription();
//                }
                node.setText(nodeText);
                if (isInit) {
                    node.setChecked(false);
                } else {
                    if (assignedRoles != null && !assignedRoles.isEmpty()) {
                        node.setChecked(assignedRoles.contains(role.getId()));
                    }
                }
                trunkNodes.add(node);
            }

        }
        return trunkNodes;
    }
    @Transactional
    @Override
    public void disableUser(String[] userIds) {
//        if (StringUtils.isNotEmpty(userId)) {
//            User user = userMapper.findUserById(userId);
//            user.setActivate(false);
//            if (user != null) {
//                if (OSSConstants.ADMIN_USER.equalsIgnoreCase(user.getLoginName())) {
//                    throw new DDPException("validate.error:\n", "admin 为超级管理员用户,不允许操作禁用。");
//                }
//                userMapper.updateUser(user);
//            }
//        }
    	 userMapper.disableUser(userIds);
    }

    @Transactional
    @Override
    public void startUser(String[] userIds) {
//        if (StringUtils.isNotEmpty(userId)) {
//            User user = userMapper.findUserById(userId);
//            user.setActivate(true);
//            if (user != null) {
//                if (OSSConstants.ADMIN_USER.equalsIgnoreCase(user.getLoginName())) {
//                    throw new DDPException("validate.error:\n", "admin 为超级管理员用户,不允许操作禁用。");
//                }
//                userMapper.updateUser(user);
//            }
//        }
        userMapper.startUser(userIds);
    }

    @Override
    public String upPwdUser(User user) {
            user.setUpdateDate(new Date());
            userMapper.upPwdUser(user);
        return CommonConstants.SUCCESS;
    }
    
    @Transactional(readOnly = true)
    public User findUser(String id){
        return userMapper.findUserById(id);
    }
    
    
    
    @Transactional(readOnly = true)
    @Override
    public DodopalDataPage<User> findUserByPage(UserQuery user) {
        List<User> result = userMapper.findUserByPage(user);
        DodopalDataPage<User> pages = new DodopalDataPage<User>(user.getPage(), result);
        return pages;
    }

    @Override
    @Transactional
    public String resetPWD(User user) {
        //获取数据库原有用户数据邮箱
        User ouser = userMapper.findUser(user.getLoginName());
        String repwd = getCharacterAndNumber(8).toLowerCase();
        if(null!=ouser){
            ouser.setPassword(MD5.MD5Encode(repwd));
            userMapper.upPwdUser(ouser);
        }else{
            return "false";
        }
        DodopalMailMessage emailMessage = new  DodopalMailMessage();
        emailMessage.setSubject("OSS系統管理平台用户密码重置");
        emailMessage.setContent("您好： 您的用户密码已经重置为:"+repwd+"请及时登录并修改新密码");
        emailMessage.setTo(new String[]{ouser.getEmail()});
        emailMessage.setFrom("yunwei@dodopal.com");
        MailSender.sendEmailMessage(emailMessage, "mail/Session");
        return CommonConstants.SUCCESS;
    }
    
    private String getCharacterAndNumber(int length){
        String val = "";                
        Random random = new Random();      
        for(int i = 0; i < length; i++){
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if("char".equalsIgnoreCase(charOrNum)){
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母  
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字          
            {              
                val += String.valueOf(random.nextInt(10));          
            }  
        }
        return val;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByLoginName(String loginName) {
        return  userMapper.findUser(loginName);
    }
}
