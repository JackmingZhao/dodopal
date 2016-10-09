package com.dodopal.running.business.model;

import java.util.ArrayList;
import java.util.List;

import com.dodopal.common.model.BaseEntity;

public class Operation extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -9144251766608391059L;

    private String name;

    private String description;

    private String type;

    private String code;

    private int levels;

    private int visibility;

    /**
     * 在菜单中的位置，从小到大
     */
    private int position;

    private String parentId;

    private String appName;

    private List<Operation> children = new ArrayList<Operation>();

    private Operation parent;
    
    public Operation getClone() {
        Operation clone = new Operation();
        clone.setId(super.getId());
        clone.setAppName(this.appName);
        clone.setName(this.name);
        clone.setDescription(this.description);
        clone.setType(this.type);
        clone.setCode(this.code);
        clone.setLevels(this.levels);
        clone.setVisibility(this.visibility);
        clone.setPosition(this.getPosition());
        clone.setParentId(this.getParentId());
        return clone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String url) {
        this.code = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public List<Operation> getChildren() {
        return children;
    }

    public void setChildren(List<Operation> children) {
        this.children = children;
    }

    public Operation getParent() {
        return parent;
    }

    public void setParent(Operation parent) {
        this.parent = parent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }


}