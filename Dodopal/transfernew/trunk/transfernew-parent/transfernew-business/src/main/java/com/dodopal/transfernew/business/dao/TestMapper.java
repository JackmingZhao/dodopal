package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dodopal.transfernew.business.model.Test;

@Component("TestMapper")
public interface TestMapper {
	
	public List<Test> findTest(Test test);

	public void insertTest(Test test);

	public void updateTest(Test test);

	public void deleteTest(String testId);
	
	public Test findTestById(String id);
	
}
