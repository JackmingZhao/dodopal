package com.dodopal.running.business.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.BaseEntity;

public class Role extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -6416354101467561307L;

    private String name;

    private String description;

    private List<Operation> operations;

    private String operationIds;

    public List<String> getOperationIdList() {
        if (StringUtils.isNotEmpty(operationIds)) {
            String[] ids = operationIds.split(",");
            return Arrays.asList(ids);
        }
        return null;
    }

    public String getRoleId() {
        return getId();
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

    public List<Operation> getOperations() {
        if (operations == null) {
            operations = new ArrayList<Operation>();
        }
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public String getOperationIds() {
        return operationIds;
    }

    public void setOperationIds(String operationIds) {
        this.operationIds = operationIds;
    }

}
