package com.dodopal.card.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.card.business.dao.TestMapper;
import com.dodopal.card.business.model.Test;
import com.dodopal.card.business.service.TestService;


@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper mapper;

    @Transactional(readOnly = true)
    public List<Test> findTest(Test test) {
        return mapper.findTest(test);
    }

    @Transactional
    public void insertTest(Test test) {
        test.setCreateDate(new Date());
        test.setUpdateDate(new Date());
        mapper.insertTest(test);
    }

    @Transactional
    public void updateTest(Test test) {
        mapper.updateTest(test);
    }

    @Transactional
    public void deleteTest(String testId) {
        mapper.deleteTest(testId);
    }

    @Transactional(readOnly = true)
    public Test findTestById(String id) {
        return mapper.findTestById(id);
    }

}
