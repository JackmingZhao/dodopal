package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.Operation;

public interface AppFunctionMapper {

    public int insertFunction(Operation operation);

    public String findOperationIdByCode(String code);

    public List<Operation> findAllMenus();
    
    public List<Operation> findAllOperations();
    
    public List<String> loadAllOperationCodes(List<String> operationIds);
    
}
