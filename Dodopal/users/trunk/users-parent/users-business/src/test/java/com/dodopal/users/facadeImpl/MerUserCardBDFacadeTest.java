package com.dodopal.users.facadeImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午4:45:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerUserCardBDFacadeTest {
    @Autowired
    private MerUserCardBDFacade facade;
    @Test
    public void testfind(){
        
        MerUserCardBDDTO dBDDTO = new MerUserCardBDDTO();
        dBDDTO.setCardCode("10060");
        dBDDTO.setBundLingType("0");
        dBDDTO.setCardName("www");
        dBDDTO.setMerUserName("lifeng17");
        dBDDTO.setSource("1");
        dBDDTO.setMerUserNameName("lifeng17");
        dBDDTO.setRemark("11");
        
        //dBDDTO.set
        DodopalResponse<MerUserCardBDDTO> response= facade.saveMerUserCardBD(dBDDTO);
        System.out.println(response.getCode());
    } 
}
