package com.transfer.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.transfer.business.dao.ProxyLimitInfoTbMapper;
import com.dodopal.transfer.business.dao.ProxyautoaddeduoperinfotbMapper;
import com.dodopal.transfer.business.model.old.Bimchntinfotb;
import com.dodopal.transfer.business.model.old.Proxyautoaddeduoperinfotb;
import com.dodopal.transfer.business.model.old.Proxylimitinfotb;
import com.dodopal.transfer.business.model.old.Sysuserstb;
import com.dodopal.transfer.business.model.target.MerAutoAmt;
import com.dodopal.transfer.business.model.target.MerDdpInfo;
import com.dodopal.transfer.business.service.BimchntinfotbService;
import com.dodopal.transfer.business.service.MerchantService;
import com.dodopal.transfer.business.service.PersonalInfoService;
import com.dodopal.transfer.business.service.SysuserstbService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:transfer-business-test-context.xml"})
public class transferTest {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private BimchntinfotbService bimchntinfotbService;
    @Autowired
    private PersonalInfoService personalInfoService;
    @Autowired
    ProxyautoaddeduoperinfotbMapper proxyautoaddeduoperinfotbMapper;
    @Autowired
    ProxyLimitInfoTbMapper proxyLimitInfoTbMapper;
    //@Test
    public void testTransfer() {
        Bimchntinfotb bimchntinfotb = bimchntinfotbService.findBimchntinfotb("411101201000103");
        //定位批次号
        String batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        DodopalResponse<String> req = merchantService.insertMerchant(bimchntinfotb, batchId);
        System.out.println(req.getCode());
    }

    @Test
    public void testGR() {
        //定位批次号
        String batchId = DateUtils.getCurrDate(DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
        DodopalResponse<String> req = personalInfoService.insertSysUserstb("1000001", batchId);
        System.out.println(req.getCode());
    }

    //@Test
    public void testJM() {
        MerDdpInfo merDdpInfo = new MerDdpInfo();
        Proxyautoaddeduoperinfotb proxyautoaddeduoperinfotb = proxyautoaddeduoperinfotbMapper.findProxyautoaddeduoperinfotb("14202");
        if (proxyautoaddeduoperinfotb != null) {
            if (proxyautoaddeduoperinfotb.getAutoAddTriggerLimit() != null && proxyautoaddeduoperinfotb.getAutoAddTriggerLimit().intValue() > 0) {
                merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度     0 是  ， 1否
            } else {
                merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否
            }
        } else {
            Proxylimitinfotb proxylimitinfotb = proxyLimitInfoTbMapper.findProxyLimitInfoTbById("14202");
            if (proxylimitinfotb != null) {
               if (proxylimitinfotb.getAutoAddTriggerLimit() != null && proxylimitinfotb.getAutoAddTriggerLimit().intValue() > 0 &&proxylimitinfotb.getAutoAddArriveLimit() != null && proxylimitinfotb.getAutoAddArriveLimit().intValue() > 0) {
                    merDdpInfo.setIsAutoDistribute("0");// 是否自动分配额度     0 是  ， 1否
                } else {
                    merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否 
                }
            } else {
                merDdpInfo.setIsAutoDistribute("1");// 是否自动分配额度      0 是  ， 1否 
            }

        }
    }
}
