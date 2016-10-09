package com.dodopal.running.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.running.business.dao.PayTraTransactionMapper;
import com.dodopal.running.business.model.PayTraTransaction;
import com.dodopal.running.business.service.TraTransactionService;

@Service
public class TraTransactionServiceImpl implements TraTransactionService {

    private final static Logger log = LoggerFactory.getLogger(TraTransactionServiceImpl.class);

    @Autowired
     private PayTraTransactionMapper payTraTransactionMapper;

    


    @Override
    @Transactional
    public List<PayTraTransaction> findPayTraTransactionByList(int threshold) {
        return payTraTransactionMapper.findPayTraTransactionByList(threshold);
    }
}
