package com.dodopal.users.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.users.business.model.MerRateSupplement;
import com.dodopal.users.business.service.MerRateSupplementService;

/**
 * @author lifeng@dodopal.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:users-business-test-context.xml" })
public class MerRateSupplementServiceTest {
	@Autowired
	MerRateSupplementService merRateSupplementService;

	@Test
	public void batchAddMerRateSpmts() {
		List<MerRateSupplement> list = new ArrayList<MerRateSupplement>();

		MerRateSupplement spmt1 = new MerRateSupplement();
		spmt1.setMerCode("111");
		spmt1.setRateCode("01");
		list.add(spmt1);
		
		MerRateSupplement spmt2 = new MerRateSupplement();
		spmt2.setMerCode("111");
		spmt2.setRateCode("02");
		list.add(spmt2);

		int num = merRateSupplementService.batchAddMerRateSpmts(list, null);
		System.out.println(num);
	}

	@Test
	public void findMerRateSpmtsByMerCodeTest() {
		String merCode = "111";
		List<MerRateSupplement> resultList = merRateSupplementService.findMerRateSpmtsByMerCode(merCode);
		if (resultList != null && resultList.size() > 0) {
			System.out.println("查询记录数：" + resultList.size());
			for (MerRateSupplement spmtTemp : resultList) {
				System.out.println(ReflectionToStringBuilder.toString(spmtTemp, ToStringStyle.MULTI_LINE_STYLE));
			}
		} else {
			System.out.println("未查到记录");
		}
	}

	@Test
	public void deleteMerRateSpmtsByMerCodeTest() {
		String merCode = "111";
		int num = merRateSupplementService.deleteMerRateSpmtsByMerCode(merCode);
		System.out.println(num);
	}
}
