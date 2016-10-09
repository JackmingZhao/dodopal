package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MobileCodeCheck;
import com.dodopal.users.business.model.Test;

public interface MobileCodeCheckMapper {
	
	public List<MobileCodeCheck> findMobileCodeCheckes(MobileCodeCheck moliCodeCheck);

	public void insertMobileCode(MobileCodeCheck moliCodeCheck);
	
	public Integer findMobileCodeCount(MobileCodeCheck moliCodeCheck);

	//public void updateTest(MobileCodeCheck moliCodeCheck);
	
	//public Test findTestById(String mobilTel,);
	
}
