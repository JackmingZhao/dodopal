package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingBank;
import com.dodopal.oss.business.model.dto.BankClearingResultQuery;

public interface ClearingBankMapper {
    
    
    public List<ClearingBank> findBankClearingResultByPage(BankClearingResultQuery bankQuery);
    
    public List<ClearingBank> getBankClearingResultForExport(BankClearingResultQuery bankQuery);
    
}
    
