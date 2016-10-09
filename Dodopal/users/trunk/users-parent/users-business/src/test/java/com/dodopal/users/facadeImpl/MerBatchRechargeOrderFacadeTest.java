package com.dodopal.users.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerBatchRechargeItemDTO;
import com.dodopal.api.users.dto.MerBatchRechargeOrderDTO;
import com.dodopal.api.users.dto.MerGroupUserDTO;
import com.dodopal.api.users.dto.query.MerBatchRcgOrderQueryDTO;
import com.dodopal.api.users.facade.MerBatchRechargeOrderFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.hessian.RemotingCallUtil;

/** 
 * @author lifeng@dodopal.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerBatchRechargeOrderFacadeTest {
	private MerBatchRechargeOrderFacade merBatchRechargeOrderFacade;
    @Before
    public void setUp() {
        String hessianUrl = "http://localhost:8082/users-web/hessian/index.do?id=";
        merBatchRechargeOrderFacade = RemotingCallUtil.getHessianService(hessianUrl, MerBatchRechargeOrderFacade.class);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFindMerGpUsersByUserName() {
    	try {
    		String merCode = "020731000000555";
    		String merUserName = "jtsha";

    		DodopalResponse<List<MerGroupUserDTO>> response = merBatchRechargeOrderFacade.findMerGpUsersByUserName(merCode, merUserName);
    		if(ResponseCode.SUCCESS.equals(response.getCode())) {
    			System.out.println("查询成功");
    		} else {
    			System.out.println("查询异常:" + response.getCode());
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testSaveBatchRcgOrder() {
    	try {
    		String merCode = "020731000000555";
    		String createUser = "10000000000000000633"; //jtsha
    		
    		List<MerBatchRechargeItemDTO> batchItemList = new ArrayList<MerBatchRechargeItemDTO>();

    		MerBatchRechargeItemDTO item1 = new MerBatchRechargeItemDTO();
    		item1.setGroupUserId("10000000000000000622");
    		batchItemList.add(item1);

    		MerBatchRechargeItemDTO item2 = new MerBatchRechargeItemDTO();
    		item2.setGroupUserId("10000000000000000623");
    		batchItemList.add(item2);

    		MerBatchRechargeItemDTO item3 = new MerBatchRechargeItemDTO();
    		item3.setGroupUserId("10000000000000000624");
    		batchItemList.add(item3);
    		
    		MerBatchRechargeItemDTO item4 = new MerBatchRechargeItemDTO();
    		item4.setGroupUserId("10000000000000000625");
    		batchItemList.add(item4);

    		MerBatchRechargeOrderDTO orderDTO = new MerBatchRechargeOrderDTO();
    		orderDTO.setMerCode(merCode);
    		orderDTO.setCreateUser(createUser);
    		orderDTO.setBatchItemList(batchItemList);

    		DodopalResponse<String> response = merBatchRechargeOrderFacade.saveBatchRcgOrder(orderDTO);
    		if(ResponseCode.SUCCESS.equals(response.getCode())) {
    			System.out.println("保存成功");
    		} else {
    			System.out.println("保存异常:" + response.getCode() + ":" + response.getMessage());
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testFindBatchRcgOrderByPage() {
    	try {
    		PageParameter page = new PageParameter();
            page.setPageNo(1);
            page.setPageSize(20);
            
            String merCode = "020731000000555";
            String userId = "10000000000000000632";
            
            MerBatchRcgOrderQueryDTO queryDTO = new MerBatchRcgOrderQueryDTO();
            queryDTO.setPage(page);
            queryDTO.setMerCode(merCode);
            queryDTO.setUserId(userId);
            
            DodopalResponse<DodopalDataPage<MerBatchRechargeOrderDTO>> response = merBatchRechargeOrderFacade.findBatchRcgOrderByPage(queryDTO);
            if(ResponseCode.SUCCESS.equals(response.getCode())) {
    			System.out.println("查询成功");
    		} else {
    			System.out.println("查询异常:" + response.getCode() + ":" + response.getMessage());
    		}
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
