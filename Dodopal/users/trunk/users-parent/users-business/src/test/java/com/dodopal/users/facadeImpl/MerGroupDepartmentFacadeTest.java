package com.dodopal.users.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerGroupDepartmentDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.dto.query.MerGroupDepartmentQueryDTO;
import com.dodopal.api.users.facade.MerGroupDepartmentFacade;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.api.users.facade.PosFacade;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.constant.UsersConstants;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 下午4:09:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:users-business-test-context.xml"})
public class MerGroupDepartmentFacadeTest {
    @Autowired
    private MerGroupDepartmentFacade facade;
    @Autowired
    private MerchantUserFacade userFacade;
    @Autowired
    private PosFacade posFacade;
    @Test
    public void userFindTest(){
        MerchantUserDTO dto = new MerchantUserDTO();
        dto.setMerUserType("1");
        DodopalResponse<List<MerchantUserDTO>> dore =  userFacade.findUserInfoList(dto);
        System.out.println(dore);
        List <MerchantUserDTO>list = dore.getResponseEntity();
        if(list!=null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

             for (MerchantUserDTO dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }

    @Test
    public void findTest(){
        MerGroupDepartmentDTO  departmentDTO = new MerGroupDepartmentDTO();
        departmentDTO.setMerCode("M0000000009");
        MerGroupDepartmentQueryDTO queryBean = new MerGroupDepartmentQueryDTO();
        //TODO 这个应该从SESSION里拿
        queryBean.setMerCode("018491000000022");
        queryBean.setPage(new PageParameter(0, 25));
        
       // DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>> dore = 
              // facade.findMerGroupDepartmentDTOListByPage(queryBean, UsersConstants.FIND_WEB);
        DodopalResponse<List<MerGroupDepartmentDTO>>dore =  facade.findMerGroupDepartmentDTOList(departmentDTO, UsersConstants.FIND_WEB);
        List<MerGroupDepartmentDTO> list = dore.getResponseEntity();
       // List<MerGroupDepartmentDTO> list = dore.getResponseEntity().getRecords();
        
        if(list!=null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

             for (MerGroupDepartmentDTO dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    @Test
    public void checkTest(){
        MerGroupDepartmentDTO  departmentDTO = new MerGroupDepartmentDTO();
        departmentDTO.setMerCode("018491000000022");
        departmentDTO.setDepName("技术12部12");
       // System.out.println(facade.checkMerGroupDepartmentDTO(departmentDTO).getMessage());
    }
    @Test
    public void findPosTest(){
        PosQueryDTO queryBean = new PosQueryDTO();
        //TODO 这个应该从SESSION里拿
        queryBean.setPage(new PageParameter(0, 25));
        queryBean.setMerchantCode("019791000000102");
       // DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>> dore = 
              // facade.findMerGroupDepartmentDTOListByPage(queryBean, UsersConstants.FIND_WEB);
        DodopalResponse<DodopalDataPage<PosDTO>>dore =  posFacade.findChildrenMerPosListPage(queryBean);
        List<PosDTO> list = dore.getResponseEntity().getRecords();
       // List<MerGroupDepartmentDTO> list = dore.getResponseEntity().getRecords();
        
        if(list!=null){
            System.out.println("##########################################");
            System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));
            System.out.println("##########################################");

             for (PosDTO dp : list) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(dp, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
            }
        }
    }
    @Test
    public void testDelDept(){
        //10000000000000000021
        List<String> list = new ArrayList<String>();
        list.add("10000000000000000024");
        DodopalResponse<Boolean>dore = facade.deleteMerGroupDepartmentDTO(list);
        System.out.println(dore.getCode());
        System.out.println(dore.getMessage());
        
    }
}
