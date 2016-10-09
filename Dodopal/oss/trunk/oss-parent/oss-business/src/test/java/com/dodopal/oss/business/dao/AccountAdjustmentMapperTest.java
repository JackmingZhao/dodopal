package com.dodopal.oss.business.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.enums.AccTranTypeEnum;
import com.dodopal.common.enums.AccountAdjustmentStateEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.oss.business.model.AccountAdjustment;
import com.dodopal.oss.business.model.dto.AccountAdjustmentQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:oss-business-test-context.xml"})
public class AccountAdjustmentMapperTest {

    @Autowired
    private AccountAdjustmentMapper mapper;

    
    private String generateAdjustmentCode() {
        String orderNum = "Q";
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
        String timeStr = simpleDateFormat.format(now);
        orderNum += timeStr;
        return orderNum + mapper.selectAdjustmentCodeSeq();
    }
    
//    @Test
    public void testSelectAdjustmentCodeSeq() {
        String codeSeq = mapper.selectAdjustmentCodeSeq();
        System.out.println("codeSeq -->" + codeSeq);
    }

    /**
     * 10.1 调账申请
     */
//    @Test
    public void testApplyAccountAdjustment() {
        AccountAdjustment adjustment = new AccountAdjustment();
        adjustment.setAccountAdjustCode(generateAdjustmentCode());
        adjustment.setAccountAdjustType(AccTranTypeEnum.ACC_PT_AD.getCode());
        adjustment.setCustomerType(MerUserTypeEnum.PERSONAL.getCode());
        adjustment.setCustomerNo("12321312");
        adjustment.setCustomerName("junit test");
        /**
         * // 账户类型 单选框 1. 对于个人账户，只显示一个“资金账户”的单选框。 2.
         * 对于企业客户，如果这个企业没有授信账户，则只显示一个“资金账户”单选框。 3.
         * 对于企业客户，如果这个企业有授信账户，则同时显示“资金账户”和“授信账户”两个单选框给用户选择。 长度=1, 必须提供
         * 账户类型分为：资金账户和授信账户。 1. 对于个人而言，只有资金账户。 2.
         * 对于企业，至少有一个资金账户，可能有一个授信账户。这个可以通过主账户上的“资金类别”字段区分。
         */
        adjustment.setFundType(FundTypeEnum.AUTHORIZED.getCode());
        //  账户号 只读  根据用户在UI上选择的客户以及账户类型，自动显示对应的账户号。 长度<40   
        adjustment.setFundAccountCode("12312");
        adjustment.setAccountAdjustAmount(123123);
        adjustment.setAccountAdjustReason("junit");
        adjustment.setAccountAdjustState(AccountAdjustmentStateEnum.UN_APPROVE.getCode());
        adjustment.setAccountAdjustApplyUser("123");
        mapper.applyAccountAdjustment(adjustment);
    }

    /**
     * 10.3 调账查询 (分页)
     */
    @Test
    public void testFindAccountAdjustmentByPage() {
        AccountAdjustmentQuery query = new AccountAdjustmentQuery();
        List<AccountAdjustment> result = mapper.findAccountAdjustmentByPage(query);
        if (result != null && CollectionUtils.isNotEmpty(result)) {
            for (AccountAdjustment p : result) {
                System.out.println("################");
                System.out.println(ReflectionToStringBuilder.toString(p, ToStringStyle.MULTI_LINE_STYLE));
                System.out.println("################");
            }
        }
    }

    /**
     * 10.3 调账查询 选择一条查询结果之后，点击“查看”按钮，系统以只读的方式显示申请单所有信息。
     */
//    @Test
    public void testFindAccountAdjustment() {
        String adjustmentId = "";
        AccountAdjustment p = mapper.findAccountAdjustment(adjustmentId);
        if (p != null) {
            System.out.println(ReflectionToStringBuilder.toString(p, ToStringStyle.MULTI_LINE_STYLE));
        }
    }

    /**
     * 10.4 调账修改
     */
//    @Test
    public void testUpdatetAccountAdjustment() {
        AccountAdjustment adjustment = new AccountAdjustment();
        adjustment.setId("");
        adjustment.setAccountAdjustAmount(123);
        adjustment.setAccountAdjustReason("Junit test");
        mapper.updatetAccountAdjustment(adjustment);
    }

    /**
     * 10.5 调账删除
     */
//    @Test
    public void testDeleteAccountAdjustment() {
        String[] adjustmentId = new String[]{"", ""};
        mapper.deleteAccountAdjustment(adjustmentId);
    }
    
    @Test
    public void testUpdateAuditAccountAdjustment() {
        AccountAdjustment adjustment = new AccountAdjustment();
        adjustment.setId("10000000000000000002");
        adjustment.setAccountAdjustState(AccountAdjustmentStateEnum.DIS_APPROVE.getCode());
        adjustment.setAccountAdjustAuditUser("123123");
        adjustment.setAccountAdjustAuditExplain("Junit test");
        adjustment.setUpdateUser("123123");
        mapper.updateAuditAccountAdjustment(adjustment);
    }

}
