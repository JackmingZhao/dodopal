package com.dodopal.thirdly.business.dao;

import java.util.List;

import com.dodopal.thirdly.business.model.Test;


public interface TestMapper {
	
	public List<Test> findTest(Test test);

	public void insertTest(Test test);

	public void updateTest(Test test);

	public void deleteTest(String testId);
	
	public Test findTestById(String id);
	
}
