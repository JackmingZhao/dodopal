package com.dodopal.card.business.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.card.business.model.BJAccountIntegralOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:card-business-test-context.xml"})
public class BJConsumeTest {
    @Autowired
    private BJAccountIntegralOrderMapper mapper;
    
    @Test
    public void findOrderTest() {
        BJAccountIntegralOrder order = new BJAccountIntegralOrder();
        order.setProOrderNum("proOrderNum2");
        order.setBusinessType("bussiness");
        List<BJAccountIntegralOrder> list = mapper.findBJAccountIntegralOrder(order);
        System.out.println(list.size());
    }
    
    @Test
    public void updateTest(){
        BJAccountIntegralOrder order = new BJAccountIntegralOrder();
        order.setAccInfo("accinfo1231231");
        order.setAccNum("accnu123121");
        order.setProOrderNum("proOrderNum2");
        mapper.updateBJAccountIntegralOrderByProOrderNum(order);
    }
    @Test
    public void saveOrderTest() {
        try {
            BJAccountIntegralOrder order = new BJAccountIntegralOrder();
            order.setAccInfo("accinfo");
            order.setAccNum("accnu");
            order.setAccountNo("accountNo");
            order.setAccSeq("accSeq");
            order.setBatchId("batchid");
            order.setBusinessType("bussiness2");
            order.setCardNo("cardNo");
            order.setComSeq("comseq");
            order.setCrdAccIntOrderNum("crdIntorderNum");
            order.setCrdBeforeorderStates("beforeo");
            order.setCrdOrderStates("crdStates");
            order.setCreateUser("cuser"); 
            order.setCityCode("cicode");
            order.setDateTime("dateTime");
            order.setIcseq("icseq");
            order.setMerType("mc");
            order.setMerCode("merCode");
            order.setOperId("operid");
            order.setPosCode("posId");
            order.setPosType("pT");
            order.setPreautheAmt("preautheA");
            order.setPriviMsg("priviMsg");
            order.setProCode("proCode");
            order.setProOrderNum("proOrderNum2");
            order.setReserved("reserved");
            order.setRespCode("respCode");
            order.setSettDate("settDate");
            order.setSpecialConsome("specialConsome");
            order.setSpecialConsomeBack("specialConsomeBack");
            order.setSpecialConsome("specialConsome");
            order.setSpecialConsomeBack("specialConsomeBack");
            order.setSpecialSendBack("specialSendBack");
            order.setSpecialRevoke("specialRevoke");
            order.setSpecialRevokeBack("specialRevokeBack");
            order.setSpecialSend("specialSend");
            order.setTranDateTime("tranDateTime");
            order.setTxnAmt("txnAmt");
            
            mapper.saveBJAccountIntegralOrder(order);
            System.out.println(order.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
