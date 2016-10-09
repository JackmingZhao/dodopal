package com.dodopal.payment.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.payment.business.model.Test;
import com.dodopal.payment.business.service.TestService;

@Service("testServiceImpl")
public class TestServiceImpl implements TestService {

//    @Autowired
//    private TestMapper mapper;

    @Transactional(readOnly = true)
    public List<Test> findTest(Test test) {
//        return mapper.findTest(test);
    	return null;
    }

	@Override
	public void insertTest(Test test) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTest(Test test) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTest(String testId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Test findTestById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

//    @Transactional
//    public void insertTest(Test test) {
//        test.setCreateDate(new Date());
//        test.setUpdateDate(new Date());
//        mapper.insertTest(test);
//    }
//
//    @Transactional
//    public void updateTest(Test test) {
//        mapper.updateTest(test);
//    }
//
//    @Transactional
//    public void deleteTest(String testId) {
//        mapper.deleteTest(testId);
//    }
//
//    @Transactional(readOnly = true)
//    public Test findTestById(String id) {
//        return mapper.findTestById(id);
//    }

}
