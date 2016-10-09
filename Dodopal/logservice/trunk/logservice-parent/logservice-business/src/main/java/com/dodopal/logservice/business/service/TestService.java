package com.dodopal.logservice.business.service;

import java.util.List;

import com.dodopal.logservice.business.model.Test;

public interface TestService {
	public List<Test> findTest(Test test);

	public void insertTest(Test test);

	public void updateTest(Test test);

	public void deleteTest(String testId);
	
	public Test findTestById(String id);
	
}
