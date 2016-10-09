package com.dodopal.merdevice.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.merdevice.business.util.MessageParser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:merdevice-business-test-context.xml"})
public class MessageParserTest {
     Logger log = LoggerFactory.getLogger(MessageParserTest.class);
    @Test
    public  void testParsePreProcessChargeResp() {
        String resp1 = "05170000000000000000010002cee21948ae7fe3e2f6787d22e62547f5045920110220150907000355100164000000179103411101201000009     0000000000000022220000165631015050500532500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000013300000101445568    000000000000000000002015090700035500001000000000005758A18016208558434F53105758A1803c869833000000FF003300000100160EC0000120150720000000000003000044869833000000FFFF010033003300000100160EC020140508203405070001000000000000000000";
        MessageParser mp = new MessageParser();
        CrdSysOrderDTO newCard = new CrdSysOrderDTO();
        CrdSysOrderDTO mc = mp.parsePreProcessChargeResp(newCard, resp1);
    }
}
