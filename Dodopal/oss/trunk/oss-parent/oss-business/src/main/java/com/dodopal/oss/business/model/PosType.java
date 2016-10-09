package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class PosType extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;

    /**
     * 型号编码 根据一定的规则进行设置（具体规则详细设计阶段定义）。全局唯一。必须提供。
     */
    private String code;

    /**
     * 型号名称 用户输入，全局唯一。必须提供。
     */
    private String name;

    /**
     * 备注
     */
    private String comments;

    /**
     * 是否启用 如果停用，那么在新录入POS机具的时候，不再出现在型号下拉列表中。
     */
    private String activate;

    public String getTypeId() {
        return getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

}
