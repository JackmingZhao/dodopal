package com.dodopal.portal.delegate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:portal-delegate-test-context.xml"})
public class ChildMerchantDelegateTest {
    @Autowired
    ChildMerchantDelegate childMerchantDelegate;
    
    //测试初始化分页加载子商户信息
    @Test
    public void findChildMerchantPageDelegate(){
        //商户信息
        MerchantQueryDTO merQueryDTO = new MerchantQueryDTO();
        merQueryDTO.setMerParentCode("013541000000190");
        DodopalResponse<DodopalDataPage<MerchantDTO>> merchantDtoList = childMerchantDelegate.findChildMerchantListByPage(merQueryDTO);
        System.out.println(ReflectionToStringBuilder.toString(merchantDtoList, ToStringStyle.MULTI_LINE_STYLE));
    }
    //测试保存子商户和编辑后保存子商户信息
    @Test
    public void saveAndUpChildMerchantDelegate(){
        //商户信息
        MerchantDTO merchantDto = new MerchantDTO();
        //商户用户信息
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        merchantUserDTO.setMerUserName("qjc_007");//用户名
        merchantUserDTO.setMerUserPWD("qjc007");//登录密码
        merchantUserDTO.setMerUserTelephone("021-65488767");//固定电话
        merchantUserDTO.setMerUserIdentityType("0");//证件类型
        merchantUserDTO.setMerUserIdentityNumber("410967199109090778");//证件号码
        merchantUserDTO.setMerUserEmail("qjc@dodopal.com");//邮箱
        merchantUserDTO.setMerUserRemark("連說商戶");//备注
        merchantUserDTO.setMerUserMobile("18721270336");//手机号
        merchantUserDTO.setActivate("0");// 界面选择启用或者停用的时候，两者在开户的都关联
        merchantUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());// TODO 用户来源
        merchantUserDTO.setMerUserAdds("上海市徐汇区");//详细地址
        merchantUserDTO.setCreateUser("admin");//创建人
        merchantDto.setMerchantUserDTO(merchantUserDTO);
        // 商户为空字段
        merchantDto.setMerName("徐汇虹梅店");//商户名称
        merchantDto.setMerType("14");//商户类型
        merchantDto.setActivate("0");//停用，启用
        merchantDto.setMerState("1");//审核状态
        merchantDto.setMerLinkUser("qq");//联系人
        merchantDto.setMerLinkUserMobile("18721720882");//联系人手机号码
        merchantDto.setMerAdds("上海市徐汇区虹梅路9998弄");//详细地址
        merchantDto.setMerPro("3300");//省份
        merchantDto.setMerCity("1791");//市区
        merchantDto.setMerBusinessScopeId("2");//经营范围
        merchantDto.setMerFax("0");//传真
        merchantDto.setMerZip("452370");//商户邮编
        merchantDto.setMerBankName("01");//开户行
        merchantDto.setMerBankAccount("6225882107789008");//开户行账号
        merchantDto.setMerBankUserName("saj_001");//开户名称
        merchantDto.setCreateUser("admin");//
        merchantDto.setMerParentCode("15");//上级商户类型
        List<MerBusRateDTO> merBusRateList = new ArrayList<MerBusRateDTO>();
        MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
        merBusRateDTOTemp.setRateCode("01");
        merBusRateDTOTemp.setProviderCode("3300");
        merBusRateDTOTemp.setRate(1);
        merBusRateDTOTemp.setRateType("1");
        merBusRateDTOTemp.setCreateUser("admin");
        merBusRateList.add(merBusRateDTOTemp);
        merchantDto.setMerBusRateList(merBusRateList);
        DodopalResponse<String> childMerchant = childMerchantDelegate.saveChildMerchant(merchantDto);
        System.out.println(ReflectionToStringBuilder.toString(childMerchant, ToStringStyle.MULTI_LINE_STYLE));
        }
    
    //测试子商户停启用
    @Test
    public void startAndDisableChildMerchantTest(){
        String[] merCode={"060271000000127",""};
        String activate="0";
        String merParentCode="060271000000127";
        DodopalResponse<Integer> number = childMerchantDelegate.startAndDisableChildMerchant(merCode, activate,merParentCode, "111");
        System.out.println(ReflectionToStringBuilder.toString(number, ToStringStyle.MULTI_LINE_STYLE));

    }
}
