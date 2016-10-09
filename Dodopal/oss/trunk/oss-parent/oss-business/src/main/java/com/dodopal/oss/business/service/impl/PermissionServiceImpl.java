package com.dodopal.oss.business.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.exception.DDPException;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.dao.AppFunctionMapper;
import com.dodopal.oss.business.dao.RoleMapper;
import com.dodopal.oss.business.dao.UserMapper;
import com.dodopal.oss.business.model.Attribute;
import com.dodopal.oss.business.model.Operation;
import com.dodopal.oss.business.model.Role;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    private Properties functionUrlMapping = null;

    @Autowired
    private AppFunctionMapper appFunctionMapper;

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private UserMapper userMapper;

    /**
     * 查找第一级目录
     */
    @Transactional(readOnly = true)
    @Override
    public List<Operation> findMenus(User user) {
        List<Operation> menus = appFunctionMapper.findAllMenus();
        List<Operation> parents = findParents(menus);
        for (Operation parent : parents) {
            buildTree(menus, parent);
        }
        List<Operation> assignedMenus = new ArrayList<Operation>();
        if (user != null) {
            List<String> assignedOperationIds = user.getOperationIdList();
            for (Operation menu : parents) {
                if (isAssignedOperation(menu, assignedOperationIds)) {
                    assignedMenus.add(menu);
                }
            }
        } else {
            assignedMenus.addAll(parents); // temp code , will be removed later.
        }
        return assignedMenus;
    }

    /**
     * 查找accordion 二级目录及树结构
     */
    @Transactional(readOnly = true)
    @Override
    public List<TreeNode> findAccordionMenus(User user, String menuId) {
        List<Operation> menus = appFunctionMapper.findAllOperations();
        Operation parent = getOperationById(menus, menuId);
        buildTree(menus, parent);

        List<Operation> operationTree = getAssignedMenus(menus, parent, user);
        return createAccordionTree(operationTree);
    }

    /**
     * 加载角色权限树
     */
    @Override
    public List<TreeNode> loadPermissionTree(Role role) {
        List<Operation> menus = appFunctionMapper.findAllOperations();
        List<Operation> parents = findParents(menus);
        for (Operation parent : parents) {
            buildTree(menus, parent);
        }
        boolean isInit = (role == null || DDPStringUtil.isNotPopulated(role.getId())) ? true : false;
        List<String> assignedOperations = null;
        if (!isInit) {
            assignedOperations = role.getOperationIdList();
        }
        return createTree(parents, isInit, assignedOperations);
    }
    
    
    @Override
    public Collection<String> findAllPermissons(User user) {
        List<String> operationIds = new ArrayList<String>();
        if(user != null) {
            operationIds = user.getOperationIdList();
        }
        if(CollectionUtils.isNotEmpty(operationIds)) {
            return appFunctionMapper.loadAllOperationCodes(operationIds);
        } else {
            return new ArrayList<String>();
        }
    }


    @Override
    public User findUser(String loginName) {
        if (DDPStringUtil.isNotPopulated(loginName)) {
            throw new DDPException("validator.error", "用户名不能为空.");
        }
        User user =  userMapper.findUser(loginName);
        if(user != null && DDPStringUtil.isPopulated(user.getRoleIds())) {
            user.setRoles(roleMapper.loadRoles(user.getRoleIds().split(",")));
        }
        return user;
    }
    /**
     * 更新登录用户的Ip和登录时间
     */
    public void updateUserLoginIPAndDate(String loginName,String ip){
    	userMapper.updateUserLoginIPAndDate(loginName,ip);
    }

    /****************************************************************************************/

    /**
     * @param operation
     * @param assignedOperationIds
     * @return
     */
    private boolean isAssignedOperation(Operation operation, List<String> assignedOperationIds) {
        boolean isAssigned = false;
        if (assignedOperationIds.contains(operation.getId())) {
            return true;
        } else {
            List<Operation> childrens = operation.getChildren();
            if (childrens != null) {
                for (Operation child : childrens) {
                    isAssigned = isAssignedOperation(child, assignedOperationIds);
                    if(isAssigned) {
                        return isAssigned;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param opeations
     * @return
     */
    private List<Operation> findParents(List<Operation> opeations) {
        List<Operation> parents = new ArrayList<Operation>();
        for (Operation operation : opeations) {
            if (DDPStringUtil.isNotPopulated(operation.getParentId())) {
                parents.add(operation);
            }
        }
        return parents;
    }

    /**
     * 循环遍历operations 构建导航主菜单
     * @param operations
     * @return
     */
    private List<TreeNode> createAccordionTree(List<Operation> operations) {
        List<TreeNode> trunkNodes = new ArrayList<TreeNode>();
        for (Operation operation : operations) {
            if(!OSSConstants.MENU_TYPE_BUTTON.equals(operation.getType())) {
                TreeNode node = new TreeNode();
                node.setId(operation.getId());
                node.setText(operation.getName());
                node.setIconCls("icon-" + operation.getCode().replace(".", "-"));
                Attribute attributes = new Attribute();
                attributes.setCode(operation.getCode());
                attributes.setUrl(getUrl(operation.getCode()));
                node.setAttributes(attributes);
                if (operation.getChildren() != null && !operation.getChildren().isEmpty()) {
                    node.setChildren(createAccordionTree(operation.getChildren()).toArray(new TreeNode[0]));
                    node.setLeaf(false);
                } else {
                    node.setLeaf(true);
                }
                trunkNodes.add(node);
            }
        }
        return trunkNodes;
    }

    /**
     * @param operations
     * @param parent
     * @param user
     * @return
     */
    private List<Operation> getAssignedMenus(List<Operation> operations, Operation parent, User user) {
        List<Operation> menuTree = new ArrayList<Operation>();
        for (Operation operation : operations) {
            if (operation.getId() == parent.getId()) {
                menuTree = operation.getChildren();
                break;
            }
        }
        Operation finalParent = parent.getClone();
        List<Operation> treeOperations = new ArrayList<Operation>();
        if(user != null) {
            if(DDPStringUtil.isPopulated(user.getRoleIds())) {
                user.setRoles(roleMapper.loadRoles(user.getRoleIds().split(",")));
            }
            findOutAllAssignedOperations(menuTree, user.getOperationIdList(), treeOperations);
        } else {
            return menuTree;
        }
        buildTree(treeOperations, finalParent);
        
        return finalParent.getChildren();
    }
    
    private void findOutAllAssignedOperations(List<Operation> menuTree, List<String> assignedOperationIds, List<Operation> treeOperations) {
        if (menuTree != null) {
            for (Operation menu : menuTree) {
                if (isLeafAssigned(assignedOperationIds, menu)) {
                    treeOperations.add(menu.getClone());
                }
                findOutAllAssignedOperations(menu.getChildren(), assignedOperationIds, treeOperations);
            }
        }
    }
    
    private boolean isLeafAssigned(List<String> assignedOperationsIds, Operation menu) {
        if(menu != null && (menu.getChildren() == null || menu.getChildren().isEmpty()) && assignedOperationsIds.contains(menu.getId())) {
            return true;
        } else {
            if(menu != null && menu.getChildren() != null) {
                for(Operation child : menu.getChildren()) {
                    boolean isLeafAssigned = isLeafAssigned(assignedOperationsIds, child);
                    if(isLeafAssigned) {
                        return isLeafAssigned;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 将所有权限根据父子关系构建成树结构
     * @param functionList 权限集
     * @return 权限树的第一级节点
     */
    private void buildTree(List<Operation> operations, Operation parent) {
        List<Operation> childs = getChildsByParentId(operations, parent);
        if (!childs.isEmpty()) {
            for (Operation child : childs) {
                buildTree(operations, child);
            }
        }
    }

    /**
     * 循环遍历operations 构建树结构
     * @param operations
     * @return
     */
    private List<TreeNode> createTree(List<Operation> operations, boolean isInit, List<String> assignedOperations) {
        List<TreeNode> trunkNodes = new ArrayList<TreeNode>();
        for (Operation operation : operations) {
            TreeNode node = new TreeNode();
            node.setId(operation.getId());
            node.setText(operation.getName());
            node.setIconCls("icon-" + operation.getCode().replace(".", "-"));
            Attribute attributes = new Attribute();
            attributes.setCode(operation.getCode());
            attributes.setRequiredCheckedNodeId(getOperationIdByCode(operation.getCode(), operations));
            node.setAttributes(attributes);
            if (isInit) {
                //初始化check选中
                node.setChecked(false);
            } else {
                if (assignedOperations != null && !assignedOperations.isEmpty()) {
                    node.setChecked(assignedOperations.contains(operation.getId()));
                }
            }
            if (operation.getChildren() != null && !operation.getChildren().isEmpty()) {
                node.setChildren(createTree(operation.getChildren(), isInit, assignedOperations).toArray(new TreeNode[0]));
                node.setLeaf(false);
            } else {
                node.setLeaf(true);
            }
            trunkNodes.add(node);
        }
        return trunkNodes;
    }

    private String getOperationIdByCode(String code, List<Operation> operations) {
        if(DDPStringUtil.isPopulated(code) && code.contains(".")) {
            code = code.substring(0, code.lastIndexOf(".")) + OSSConstants.QUERY_BUTTON;
            for (Operation operation : operations) {
                if (operation.getCode().equals(code)) {
                    return operation.getId();
                }
            } 
        }
        
        return "";
    }
    
    /**
     * @param operations
     * @param parent
     * @return
     */
    private List<Operation> getChildsByParentId(List<Operation> operations, Operation parent) {
        List<Operation> childs = new ArrayList<Operation>();
        if (parent != null) {
            for (Operation operation : operations) {
                if (operation != null && DDPStringUtil.isPopulated(operation.getParentId()) && operation.getParentId().equals(parent.getId())) {
                    operation.setParent(parent);
                    childs.add(operation);
                }
            }
            parent.setChildren(childs);
        }
        return childs;
    }

    /**
     * @param operations
     * @param id
     * @return
     */
    private Operation getOperationById(List<Operation> operations, String id) {
        for (Operation operation : operations) {
            if (operation.getId().equals(id)) {
                return operation;
            }
        }
        return null;
    }

    /**
     * @param functionCode
     * @return
     */
    private String getUrl(String functionCode) {
        if (functionUrlMapping == null) {
            functionUrlMapping = new Properties();
            try {
                functionUrlMapping.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("functionMapping.properties")));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return functionUrlMapping.getProperty(functionCode);
    }


    /****************************************************************************************/

}
