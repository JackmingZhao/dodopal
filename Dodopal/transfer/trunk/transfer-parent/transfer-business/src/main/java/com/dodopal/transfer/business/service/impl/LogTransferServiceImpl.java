package com.dodopal.transfer.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.dao.LogTransferMapper;
import com.dodopal.transfer.business.model.LogTransfer;
import com.dodopal.transfer.business.service.LogTransferService;
@Service
public class LogTransferServiceImpl implements LogTransferService {
    
    @Autowired
    LogTransferMapper logTransferMapper;
    @Override
    public DodopalResponse<List<LogTransfer>> findLogTransfer(LogTransfer logTransfer) {
        DodopalResponse<List<LogTransfer>> response =new DodopalResponse<List<LogTransfer>>();
        List<LogTransfer> logTransferList = new ArrayList<LogTransfer>();
         logTransferList = logTransferMapper.findAllLogTransfer(logTransfer);
         if(logTransfer!=null){
             response.setCode(ResponseCode.SUCCESS);
             response.setResponseEntity(logTransferList);
         }
        return response;
    }

}
