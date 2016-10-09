package com.dodopal.oss.business.service.impl;

import org.springframework.stereotype.Service;

import com.dodopal.oss.business.service.AppFunctionService;

@Service
public class AppFunctionServiceImpl implements AppFunctionService {

//    private Properties functionUrlMapping = null;
//    
//    @Autowired
//    private AppFunctionMapper appFunctionMapper;
//    
//    @Autowired
//    private PermissionService permissionService;
//    
//    @Override
//    @Transactional(readOnly=true)
//    public List<Operation> findMenus(User user) {
////        List<Operation> assignedMenus = new ArrayList<Operation>();
////        List<Operation> allMenus = appFunctionMapper.findMenus();
////        if (user != null) {
////            List<String> assignedOperationIds = user.getOperationIdList();
////            if (assignedOperationIds != null) {
////                for (Operation menu : allMenus) {
////                    if (permissionService.hasAssignedLeaf(menu, assignedOperationIds)) {
////                        assignedMenus.add(menu);
////                    }
////                }
////            }
////        } else {
////            assignedMenus.addAll(allMenus);
////        }
////        return assignedMenus;
//        
//        return permissionService.findMenus(user);
//    }
//
//    
//    @Override
//    @Transactional(readOnly=true)
//    public List<TreeNode> findContextMenuByMenuId(User user, String menuId) {
////        List<Operation> operations = permissionService.getAssignedOperationTree(user, menuId);
//         
//        // TODO 构建权限树结构
////        return createContextMenuTree(operations);
//        return null;
//    }
//    
//    
////    @Override
////    @Transactional(readOnly=true)
////    public List<ContextMenu> findContextMenuByMenuId(User user, String menuId) {
////        List<Operation> operations = permissionService.getAssignedOperationTree(user, menuId);
////         
////        // TODO 构建权限树结构
////        return createContextMenu(operations);
////    }
//    
//    /**
//     * 循环遍历operations 构建导航主菜单
//     * 
//     * @param operations
//     * @return
//     */
//    private List<TreeNode> createContextMenuTree(List<Operation> operations) {
//        List<TreeNode> trunkNodes = new ArrayList<TreeNode>();
//        for(Operation operation : operations) {
//            TreeNode node = new TreeNode();
//            node.setId(operation.getId());
//            node.setText(operation.getName());
//            node.setIconCls("icon-" + operation.getCode().replace(".", "-"));
//            Attribute attributes = new Attribute();
//            attributes.setCode(operation.getCode());
//            attributes.setUrl(getUrl(operation.getCode()));
//            node.setAttributes(attributes);
//            if (operation.getChildren() != null && !operation.getChildren().isEmpty()) {
//                node.setChildren(createContextMenuTree(operation.getChildren()).toArray(new TreeNode[0]));
//            }
//            trunkNodes.add(node);
//        }
//        return trunkNodes;
//    }
//    
//    /**
//     * 循环遍历operations 构建导航主菜单
//     * 
//     * @param operations
//     * @return
//     */
//    private List<ContextMenu> createContextMenu(List<Operation> operations) {
//        List<ContextMenu> contextMenus = new ArrayList<ContextMenu>();
//        for (Operation operation : operations) {
//            ContextMenu menu = new ContextMenu();
//            menu.setMenuId(operation.getId());
//            menu.setMenuName(operation.getName());
////            menu.setUrl(getUrl(operation.getCode()));
//            menu.setIcon("icon-" + operation.getCode());
//            menu.setMenuCode(operation.getCode());
//            if (operation.getChildren() != null && !operation.getChildren().isEmpty()) {
//                menu.setMenus(createContextMenu(operation.getChildren()).toArray(new ContextMenu[0]));
//            }
//            contextMenus.add(menu);
//        }
//        return contextMenus;
//    }



}
