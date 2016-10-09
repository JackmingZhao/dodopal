package com.dodopal.oss.business.model.dto;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.QueryBase;
import com.dodopal.oss.business.model.Operation;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月26日 下午4:20:23
 */
public class RoleQuery extends QueryBase {
    private static final long serialVersionUID = -8351180898281778997L;

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
