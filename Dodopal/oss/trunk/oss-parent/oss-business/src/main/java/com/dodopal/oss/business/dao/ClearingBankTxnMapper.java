package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.ClearingBankTxn;
import com.dodopal.oss.business.model.dto.BankClearingResultQuery;

public interface ClearingBankTxnMapper {

    public List<ClearingBankTxn> getBankTxnClearingResult(BankClearingResultQuery bankQuery);
       
}
    
