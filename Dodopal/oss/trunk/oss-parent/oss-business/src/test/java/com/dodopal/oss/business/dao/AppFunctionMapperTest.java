package com.dodopal.oss.business.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.oss.business.model.Operation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class AppFunctionMapperTest {

    @Autowired
    private AppFunctionMapper appFunctionMapper;

    //    @Test
    public void testInsertAppFunction() {
        try {
            Operation operation = new Operation();
            operation.setCode("test2");
            operation.setName("test2");
            int id = appFunctionMapper.insertFunction(operation);
            System.out.println("##########################################");
            System.out.println(id);
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void testLoadAllOperationCodes() {
        try {
            String ids = "2b3a11bb9e1640e5857f9090decfc08e,7e2b5d099e6342caadd33cb28b18e1ac,81fa4bdeae834a16a7ea3d021b972e9a,a50c27d2cea64013b00d57fb2b3e57cd,38805036bd32496888b77d2bca5ed4e4,92e55cc9a5c04a709edcb3e52d539777,4a9173931ace4eb7a9828c78108233e2,4e2419df091a418b8ac6ff827731007e,85c11673c85a477a955e67060e4134e5,2008364883aa462581767530d2f98257,00cac89103d7411c9b66664b428f82b9";
            List<String> operations = appFunctionMapper.loadAllOperationCodes(Arrays.asList(ids.split(",")));
            if (operations != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(operations, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (String operation : operations) {
                    System.out.println("##########################################");
                    System.out.println(operation);
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Test
    public void testFindAllmenus() {
        try {
            List<Operation> operations = appFunctionMapper.findAllMenus();
            if (operations != null) {
                System.out.println("##########################################");
                System.out.println(ReflectionToStringBuilder.toString(operations, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("##########################################");
                for (Operation operation : operations) {
                    System.out.println("##########################################");
                    System.out.println(ReflectionToStringBuilder.toString(operation, ToStringStyle.MULTI_LINE_STYLE));
                    System.out.println("##########################################");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void testFindOperationIdByCode() {
        try {
            String id = appFunctionMapper.findOperationIdByCode("merchant");
            System.out.println("##########################################");
            System.out.println(id);
            System.out.println("##########################################");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
