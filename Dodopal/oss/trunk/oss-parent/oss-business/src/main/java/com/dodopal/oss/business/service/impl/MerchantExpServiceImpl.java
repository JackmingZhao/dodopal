package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.common.enums.SettlementFlagEnum;
import com.dodopal.common.enums.SettlementTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.MerchantExcelBean;
import com.dodopal.oss.business.bean.MerchantNotUnauditedExpBean;
import com.dodopal.oss.business.bean.MerchantUnauditedExpBean;
import com.dodopal.oss.business.model.dto.MerchantQuery;
import com.dodopal.oss.business.service.MerchantExpService;
import com.dodopal.oss.delegate.MerchantExpDelegate;
@Service
public class MerchantExpServiceImpl implements MerchantExpService{
    private final static Logger log = LoggerFactory.getLogger(MerchantExpServiceImpl.class);
    
    @Autowired
    MerchantExpDelegate merExpDel;
    
    

    @Override
    public int exportExcelCountService(MerchantQuery merchantQuery) {
        int expCount = 0;
        // 界面模糊查询传入过来的值
        MerchantQueryDTO merchantDto = new MerchantQueryDTO();
        //商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerName())) {
            merchantDto.setMerName(merchantQuery.getMerName());
        }
        //商户编码
        if (StringUtils.isNotBlank(merchantQuery.getMerCode())) {
            merchantDto.setMerCode(merchantQuery.getMerCode());
        }
        //上级商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerParentName())) {
            merchantDto.setMerParentName(merchantQuery.getMerParentName());
        }
        //商户类型
        if (StringUtils.isNotBlank(merchantQuery.getMerType())) {
            merchantDto.setMerType(merchantQuery.getMerType());
        }
        //商户联系人
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUser())) {
            merchantDto.setMerLinkUser(merchantQuery.getMerLinkUser());
        }
        //商户联系方式-手机号码
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUserMobile())) {
            merchantDto.setMerLinkUserMobile(merchantQuery.getMerLinkUserMobile());
        }
        //商户分类
        if (StringUtils.isNotBlank(merchantQuery.getMerClassify())) {
            merchantDto.setMerClassify(merchantQuery.getMerClassify());
        }
        //商户属性
        if (StringUtils.isNotBlank(merchantQuery.getMerProperty())) {
            merchantDto.setMerProperty(merchantQuery.getMerProperty());
        }
        //省份
        if (StringUtils.isNotBlank(merchantQuery.getMerPro())) {
            merchantDto.setMerPro(merchantQuery.getMerPro());
        }
        //城市
        if (StringUtils.isNotBlank(merchantQuery.getMerCity())) {
            merchantDto.setMerCity(merchantQuery.getMerCity());
        }
        //启用标识
        if (StringUtils.isNotBlank(merchantQuery.getActivate())) {
            merchantDto.setActivate(merchantQuery.getActivate());
        }
        //来源
        if (StringUtils.isNotBlank(merchantQuery.getSource())) {
            merchantDto.setSource(merchantQuery.getSource());
        }
        //审核状态
        if (StringUtils.isNotBlank(merchantQuery.getMerState())) {
            merchantDto.setMerState(merchantQuery.getMerState());
        }
        //业务类型
        if (StringUtils.isNotBlank(merchantQuery.getBussQuery())) {
            merchantDto.setBussQuery(merchantQuery.getBussQuery());
        }
        try {
            DodopalResponse<Integer> exCount=  merExpDel.getExpMerchantCount(merchantDto);
            expCount =exCount.getResponseEntity();
        }
        catch (Exception e) {
            log.error("导出查询总条数出错", e);
        }
        return expCount;
    }

    @Override
    public List<MerchantExcelBean> exportExcelVerifiedService(MerchantQuery merchantQuery) {
        log.info("-----------------exportExcelVerifiedService start ------------------");
        List<MerchantExcelBean> merList =new ArrayList<MerchantExcelBean>();
       
        // 界面模糊查询传入过来的值
        MerchantQueryDTO merchantDto = new MerchantQueryDTO();
        //商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerName())) {
            merchantDto.setMerName(merchantQuery.getMerName());
        }
        //商户编码
        if (StringUtils.isNotBlank(merchantQuery.getMerCode())) {
            merchantDto.setMerCode(merchantQuery.getMerCode());
        }
        //上级商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerParentName())) {
            merchantDto.setMerParentName(merchantQuery.getMerParentName());
        }
        //商户类型
        if (StringUtils.isNotBlank(merchantQuery.getMerType())) {
            merchantDto.setMerType(merchantQuery.getMerType());
        }
        //商户联系人
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUser())) {
            merchantDto.setMerLinkUser(merchantQuery.getMerLinkUser());
        }
        //商户联系方式-手机号码
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUserMobile())) {
            merchantDto.setMerLinkUserMobile(merchantQuery.getMerLinkUserMobile());
        }
        //商户分类
        if (StringUtils.isNotBlank(merchantQuery.getMerClassify())) {
            merchantDto.setMerClassify(merchantQuery.getMerClassify());
        }
        //商户属性
        if (StringUtils.isNotBlank(merchantQuery.getMerProperty())) {
            merchantDto.setMerProperty(merchantQuery.getMerProperty());
        }
        //省份
        if (StringUtils.isNotBlank(merchantQuery.getMerPro())) {
            merchantDto.setMerPro(merchantQuery.getMerPro());
        }
        //城市
        if (StringUtils.isNotBlank(merchantQuery.getMerCity())) {
            merchantDto.setMerCity(merchantQuery.getMerCity());
        }
        //启用标识
        if (StringUtils.isNotBlank(merchantQuery.getActivate())) {
            merchantDto.setActivate(merchantQuery.getActivate());
        }
        //来源
        if (StringUtils.isNotBlank(merchantQuery.getSource())) {
            merchantDto.setSource(merchantQuery.getSource());
        }
        //审核状态
        if (StringUtils.isNotBlank(merchantQuery.getMerState())) {
            merchantDto.setMerState(merchantQuery.getMerState());
        }
        //业务类型
        if (StringUtils.isNotBlank(merchantQuery.getBussQuery())) {
            merchantDto.setBussQuery(merchantQuery.getBussQuery());
        }
        
        try {
            DodopalResponse<List<MerchantDTO>> merDtoRes = merExpDel.getExportMerchantList(merchantDto);
            List<MerchantDTO> merDtoList = merDtoRes.getResponseEntity();
            for(MerchantDTO merDto : merDtoList){
                MerchantExcelBean mebean = new MerchantExcelBean();
                mebean.setMerCode(merDto.getMerCode());
                mebean.setMerName(merDto.getMerName());
                mebean.setMerType(merDto.getMerType());
                mebean.setMerLinkUser(merDto.getMerLinkUser());
                mebean.setMerZip(merDto.getMerZip());
                mebean.setActivate(merDto.getActivate());
                mebean.setMerLinkUserMobile(merDto.getMerLinkUserMobile());
                mebean.setMerTelephone(merDto.getMerTelephone());
                mebean.setMerEmail(merDto.getMerEmail());
                mebean.setMerParentCode(merDto.getMerParentCode());
                mebean.setMerParentType(merDto.getMerParentType());
                mebean.setMerParentName(merDto.getMerParentName());
                mebean.setMerState(merDto.getMerState());
                mebean.setMerActivateDate(merDto.getMerActivateDate());
                mebean.setMerDeactivateDate(merDto.getMerDeactivateDate());
                mebean.setMerFax(merDto.getMerFax());
                mebean.setMerRegisterDate(merDto.getMerRegisterDate());
                mebean.setMerBankName(merDto.getMerBankName());
                mebean.setMerBankAccount(merDto.getMerBankAccount());
                mebean.setMerBankUserName(merDto.getMerBankUserName());
                mebean.setMerBusinessScopeId(merDto.getMerBusinessScopeId());
                mebean.setMerBankUserName(merDto.getMerBankUserName());
                mebean.setMerAreaName(merDto.getMerAreaName());
                mebean.setMerProName(merDto.getMerProName());
                mebean.setMerCityName(merDto.getMerCityName());
                mebean.setMerClassify(merDto.getMerClassify());
                mebean.setMerProperty(merDto.getMerProperty());
                mebean.setMerStateUser(merDto.getMerStateUser());
                mebean.setMerStateDate(merDto.getMerStateDate());
                mebean.setMerRejectReason(merDto.getMerRejectReason());
               // mebean.setFundType(merDto.getFundType());
                mebean.setMerAdds(merDto.getMerAdds());
                mebean.setSource(merDto.getSource());
                if(merDto.getMerDdpInfo()!=null){
                    mebean.setMerDdpLinkIp(merDto.getMerDdpInfo().getMerDdpLinkIp());
                    mebean.setMerDdpLinkUser(merDto.getMerDdpInfo().getMerDdpLinkUser());
                    mebean.setMerDdpLinkUserMobile(merDto.getMerDdpInfo().getMerDdpLinkUserMobile());
                    mebean.setSettlementType(merDto.getMerDdpInfo().getSettlementType());
                    if(SettlementTypeEnum.MONEY.getCode().equals(merDto.getMerDdpInfo().getSettlementType())){
                        mebean.setSettlementThreshold(merDto.getMerDdpInfo().getSettlementThreshold()/100);
                    }else{
                        mebean.setSettlementThreshold(merDto.getMerDdpInfo().getSettlementThreshold());
                    }
                    mebean.setIsAutoDistribute(merDto.getMerDdpInfo().getIsAutoDistribute());
                    mebean.setLimitSource(merDto.getMerDdpInfo().getLimitSource());
                    mebean.setTradeArea(merDto.getMerDdpInfo().getTradeArea());
                }
                merList.add(mebean);
            }
        }
        catch (Exception e) {
            log.error("导出查询出错", e);
        }
        return merList;
    }
    @Override
    public List<MerchantUnauditedExpBean> exportExcelUnauditedService(MerchantQuery merchantQuery) {
        log.info("-----------------exportExcelVerifiedService start ------------------");
        List<MerchantUnauditedExpBean> merList =new ArrayList<MerchantUnauditedExpBean>();
       
        // 界面模糊查询传入过来的值
        MerchantQueryDTO merchantDto = new MerchantQueryDTO();
        //商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerName())) {
            merchantDto.setMerName(merchantQuery.getMerName());
        }
        //商户编码
        if (StringUtils.isNotBlank(merchantQuery.getMerCode())) {
            merchantDto.setMerCode(merchantQuery.getMerCode());
        }
        //上级商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerParentName())) {
            merchantDto.setMerParentName(merchantQuery.getMerParentName());
        }
        //商户类型
        if (StringUtils.isNotBlank(merchantQuery.getMerType())) {
            merchantDto.setMerType(merchantQuery.getMerType());
        }
        //商户联系人
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUser())) {
            merchantDto.setMerLinkUser(merchantQuery.getMerLinkUser());
        }
        //商户联系方式-手机号码
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUserMobile())) {
            merchantDto.setMerLinkUserMobile(merchantQuery.getMerLinkUserMobile());
        }
        //商户分类
        if (StringUtils.isNotBlank(merchantQuery.getMerClassify())) {
            merchantDto.setMerClassify(merchantQuery.getMerClassify());
        }
        //商户属性
        if (StringUtils.isNotBlank(merchantQuery.getMerProperty())) {
            merchantDto.setMerProperty(merchantQuery.getMerProperty());
        }
        //省份
        if (StringUtils.isNotBlank(merchantQuery.getMerPro())) {
            merchantDto.setMerPro(merchantQuery.getMerPro());
        }
        //城市
        if (StringUtils.isNotBlank(merchantQuery.getMerCity())) {
            merchantDto.setMerCity(merchantQuery.getMerCity());
        }
        //启用标识
        if (StringUtils.isNotBlank(merchantQuery.getActivate())) {
            merchantDto.setActivate(merchantQuery.getActivate());
        }
        //来源
        if (StringUtils.isNotBlank(merchantQuery.getSource())) {
            merchantDto.setSource(merchantQuery.getSource());
        }
        //审核状态
        if (StringUtils.isNotBlank(merchantQuery.getMerState())) {
            merchantDto.setMerState(merchantQuery.getMerState());
        }
        //业务类型
        if (StringUtils.isNotBlank(merchantQuery.getBussQuery())) {
            merchantDto.setBussQuery(merchantQuery.getBussQuery());
        }
        
        try {
            DodopalResponse<List<MerchantDTO>> merDtoRes = merExpDel.getExportMerchantList(merchantDto);
            List<MerchantDTO> merDtoList = merDtoRes.getResponseEntity();
            for(MerchantDTO merDto : merDtoList){
                MerchantUnauditedExpBean mebean = new MerchantUnauditedExpBean();
                mebean.setMerCode(merDto.getMerCode());
                mebean.setMerName(merDto.getMerName());
                mebean.setMerType(merDto.getMerType());
                mebean.setMerLinkUser(merDto.getMerLinkUser());
                mebean.setMerZip(merDto.getMerZip());
                mebean.setActivate(merDto.getActivate());
                mebean.setMerLinkUserMobile(merDto.getMerLinkUserMobile());
                mebean.setMerTelephone(merDto.getMerTelephone());
                mebean.setMerEmail(merDto.getMerEmail());
                mebean.setMerParentCode(merDto.getMerParentCode());
                mebean.setMerParentType(merDto.getMerParentType());
                mebean.setMerParentName(merDto.getMerParentName());
                mebean.setMerState(merDto.getMerState());
                mebean.setMerActivateDate(merDto.getMerActivateDate());
                mebean.setMerDeactivateDate(merDto.getMerDeactivateDate());
                mebean.setMerFax(merDto.getMerFax());
                mebean.setMerRegisterDate(merDto.getMerRegisterDate());
                mebean.setMerBankName(merDto.getMerBankName());
                mebean.setMerBankAccount(merDto.getMerBankAccount());
                mebean.setMerBankUserName(merDto.getMerBankUserName());
                mebean.setMerBusinessScopeId(merDto.getMerBusinessScopeId());
                mebean.setMerBankUserName(merDto.getMerBankUserName());
                mebean.setMerAreaName(merDto.getMerAreaName());
                mebean.setMerProName(merDto.getMerProName());
                mebean.setMerCityName(merDto.getMerCityName());
                mebean.setMerClassify(merDto.getMerClassify());
                mebean.setMerProperty(merDto.getMerProperty());
                mebean.setMerStateUser(merDto.getMerStateUser());
                mebean.setMerStateDate(merDto.getMerStateDate());
                mebean.setMerRejectReason(merDto.getMerRejectReason());
               // mebean.setFundType(merDto.getFundType());
                mebean.setMerAdds(merDto.getMerAdds());
                mebean.setSource(merDto.getSource());
                if(merDto.getMerDdpInfo()!=null){
                    mebean.setMerDdpLinkIp(merDto.getMerDdpInfo().getMerDdpLinkIp());
                    mebean.setMerDdpLinkUser(merDto.getMerDdpInfo().getMerDdpLinkUser());
                    mebean.setMerDdpLinkUserMobile(merDto.getMerDdpInfo().getMerDdpLinkUserMobile());
                    if(SettlementTypeEnum.MONEY.getCode().equals( merDto.getMerDdpInfo().getSettlementType())){
                        mebean.setSettlementThreshold(merDto.getMerDdpInfo().getSettlementThreshold()/100);
                    }else{
                        mebean.setSettlementThreshold(merDto.getMerDdpInfo().getSettlementThreshold());
                    }
                    mebean.setSettlementType(merDto.getMerDdpInfo().getSettlementType());
                    mebean.setIsAutoDistribute(merDto.getMerDdpInfo().getIsAutoDistribute());
                    mebean.setLimitSource(merDto.getMerDdpInfo().getLimitSource());
                    mebean.setTradeArea(merDto.getMerDdpInfo().getTradeArea());
                }
                merList.add(mebean);
            }
        }
        catch (Exception e) {
            log.error("导出查询出错", e);
        }
        return merList;
    }
    @Override
    public List<MerchantNotUnauditedExpBean> exportExcelNotUnauditedService(MerchantQuery merchantQuery) {
        log.info("-----------------exportExcelVerifiedService start ------------------");
        List<MerchantNotUnauditedExpBean> merList =new ArrayList<MerchantNotUnauditedExpBean>();
       
        // 界面模糊查询传入过来的值
        MerchantQueryDTO merchantDto = new MerchantQueryDTO();
        //商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerName())) {
            merchantDto.setMerName(merchantQuery.getMerName());
        }
        //商户编码
        if (StringUtils.isNotBlank(merchantQuery.getMerCode())) {
            merchantDto.setMerCode(merchantQuery.getMerCode());
        }
        //上级商户名称
        if (StringUtils.isNotBlank(merchantQuery.getMerParentName())) {
            merchantDto.setMerParentName(merchantQuery.getMerParentName());
        }
        //商户类型
        if (StringUtils.isNotBlank(merchantQuery.getMerType())) {
            merchantDto.setMerType(merchantQuery.getMerType());
        }
        //商户联系人
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUser())) {
            merchantDto.setMerLinkUser(merchantQuery.getMerLinkUser());
        }
        //商户联系方式-手机号码
        if (StringUtils.isNotBlank(merchantQuery.getMerLinkUserMobile())) {
            merchantDto.setMerLinkUserMobile(merchantQuery.getMerLinkUserMobile());
        }
        //商户分类
        if (StringUtils.isNotBlank(merchantQuery.getMerClassify())) {
            merchantDto.setMerClassify(merchantQuery.getMerClassify());
        }
        //商户属性
        if (StringUtils.isNotBlank(merchantQuery.getMerProperty())) {
            merchantDto.setMerProperty(merchantQuery.getMerProperty());
        }
        //省份
        if (StringUtils.isNotBlank(merchantQuery.getMerPro())) {
            merchantDto.setMerPro(merchantQuery.getMerPro());
        }
        //城市
        if (StringUtils.isNotBlank(merchantQuery.getMerCity())) {
            merchantDto.setMerCity(merchantQuery.getMerCity());
        }
        //启用标识
        if (StringUtils.isNotBlank(merchantQuery.getActivate())) {
            merchantDto.setActivate(merchantQuery.getActivate());
        }
        //来源
        if (StringUtils.isNotBlank(merchantQuery.getSource())) {
            merchantDto.setSource(merchantQuery.getSource());
        }
        //审核状态
        if (StringUtils.isNotBlank(merchantQuery.getMerState())) {
            merchantDto.setMerState(merchantQuery.getMerState());
        }
        //业务类型
        if (StringUtils.isNotBlank(merchantQuery.getBussQuery())) {
            merchantDto.setBussQuery(merchantQuery.getBussQuery());
        }
        
        try {
            DodopalResponse<List<MerchantDTO>> merDtoRes = merExpDel.getExportMerchantList(merchantDto);
            List<MerchantDTO> merDtoList = merDtoRes.getResponseEntity();
            for(MerchantDTO merDto : merDtoList){
                MerchantNotUnauditedExpBean mebean = new MerchantNotUnauditedExpBean();
                mebean.setMerCode(merDto.getMerCode());
                mebean.setMerName(merDto.getMerName());
                mebean.setMerType(merDto.getMerType());
                mebean.setMerLinkUser(merDto.getMerLinkUser());
                mebean.setMerZip(merDto.getMerZip());
                mebean.setActivate(merDto.getActivate());
                mebean.setMerLinkUserMobile(merDto.getMerLinkUserMobile());
                mebean.setMerTelephone(merDto.getMerTelephone());
                mebean.setMerEmail(merDto.getMerEmail());
                mebean.setMerParentCode(merDto.getMerParentCode());
                mebean.setMerParentType(merDto.getMerParentType());
                mebean.setMerParentName(merDto.getMerParentName());
                mebean.setMerState(merDto.getMerState());
                mebean.setMerActivateDate(merDto.getMerActivateDate());
                mebean.setMerDeactivateDate(merDto.getMerDeactivateDate());
                mebean.setMerFax(merDto.getMerFax());
                mebean.setMerRegisterDate(merDto.getMerRegisterDate());
                mebean.setMerBankName(merDto.getMerBankName());
                mebean.setMerBankAccount(merDto.getMerBankAccount());
                mebean.setMerBankUserName(merDto.getMerBankUserName());
                mebean.setMerBusinessScopeId(merDto.getMerBusinessScopeId());
                mebean.setMerBankUserName(merDto.getMerBankUserName());
                mebean.setMerAreaName(merDto.getMerAreaName());
                mebean.setMerProName(merDto.getMerProName());
                mebean.setMerCityName(merDto.getMerCityName());
                mebean.setMerClassify(merDto.getMerClassify());
                mebean.setMerProperty(merDto.getMerProperty());
                mebean.setMerStateUser(merDto.getMerStateUser());
                mebean.setMerStateDate(merDto.getMerStateDate());
                mebean.setMerRejectReason(merDto.getMerRejectReason());
                mebean.setMerAdds(merDto.getMerAdds());
                mebean.setSource(merDto.getSource());
                if(merDto.getMerDdpInfo()!=null){
                    mebean.setMerDdpLinkIp(merDto.getMerDdpInfo().getMerDdpLinkIp());
                    mebean.setMerDdpLinkUser(merDto.getMerDdpInfo().getMerDdpLinkUser());
                    mebean.setMerDdpLinkUserMobile(merDto.getMerDdpInfo().getMerDdpLinkUserMobile());
                    mebean.setSettlementType(merDto.getMerDdpInfo().getSettlementType());
                    if(SettlementTypeEnum.MONEY.getCode().equals(merDto.getMerDdpInfo().getSettlementType())){
                        mebean.setSettlementThreshold(merDto.getMerDdpInfo().getSettlementThreshold()/100);
                    }else{
                        mebean.setSettlementThreshold(merDto.getMerDdpInfo().getSettlementThreshold());
                    }
                    mebean.setIsAutoDistribute(merDto.getMerDdpInfo().getIsAutoDistribute());
                    mebean.setLimitSource(merDto.getMerDdpInfo().getLimitSource());
                    mebean.setTradeArea(merDto.getMerDdpInfo().getTradeArea());
                }
                merList.add(mebean);
            }
        }
        catch (Exception e) {
            log.error("导出查询出错", e);
        }
        return merList;
    }


}
