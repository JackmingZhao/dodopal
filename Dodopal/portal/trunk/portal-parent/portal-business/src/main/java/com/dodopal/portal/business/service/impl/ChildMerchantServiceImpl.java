package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.users.dto.MerAutoAmtDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerDdpInfoDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.IsAutoDistributeEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.portal.business.bean.MerAutoAmtBean;
import com.dodopal.portal.business.bean.MerBusRateBean;
import com.dodopal.portal.business.bean.MerDdpInfoBean;
import com.dodopal.portal.business.bean.MerRateSupplementBean;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.model.query.ChildMerchantQuery;
import com.dodopal.portal.business.service.ChildMerchantService;
import com.dodopal.portal.delegate.ChildMerchantDelegate;
import com.dodopal.portal.delegate.MerchantDelegate;
@Service
public class ChildMerchantServiceImpl implements ChildMerchantService {
    private final static Logger log = LoggerFactory.getLogger(ChildMerchantServiceImpl.class);
    @Autowired
    ChildMerchantDelegate  childMerchantDelegate;
    @Autowired
    MerchantDelegate  merchantDelegate;
    /**
     * 查询子商户信息列表
     * 
     */
    @Override
    public DodopalResponse<DodopalDataPage<MerchantBean>> findChildMerchantBeanByPage(ChildMerchantQuery childMerchantQuery) {
        log.info("input ChildMerchant :"+childMerchantQuery);
        DodopalResponse<DodopalDataPage<MerchantBean>> response = new DodopalResponse<DodopalDataPage<MerchantBean>>();
        MerchantQueryDTO merchantDto = new MerchantQueryDTO();
        //商户编号
        if (StringUtils.isNotBlank(childMerchantQuery.getMerCode())) {
            merchantDto.setMerParentCode(childMerchantQuery.getMerCode());
        }
        //商户名称
        if (StringUtils.isNotBlank(childMerchantQuery.getMerName())) {
            merchantDto.setMerName(childMerchantQuery.getMerName());
        }
        //商户联系人
        if (StringUtils.isNotBlank(childMerchantQuery.getMerLinkUser())) {
            merchantDto.setMerLinkUser(childMerchantQuery.getMerLinkUser());
        }
        //市
        if (StringUtils.isNotBlank(childMerchantQuery.getMerCity())) {
            merchantDto.setMerCity(childMerchantQuery.getMerCity());
        }
        //省
        if (StringUtils.isNotBlank(childMerchantQuery.getMerPro())) {
            merchantDto.setMerPro(childMerchantQuery.getMerPro());
        }
        //启用标识
        if (StringUtils.isNotBlank(childMerchantQuery.getActivate())) {
            merchantDto.setActivate(childMerchantQuery.getActivate());
        }
        //分页参数
        if (childMerchantQuery.getPage() != null) {
            merchantDto.setPage(childMerchantQuery.getPage());;
        }
        // 查询列表信息
        DodopalResponse<DodopalDataPage<MerchantDTO>> merchantDtoList = childMerchantDelegate.findChildMerchantListByPage(merchantDto);
        List<MerchantBean> merchantBeanList = new ArrayList<MerchantBean>();
        if (ResponseCode.SUCCESS.equals(merchantDtoList.getCode())) {
            if (merchantDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(merchantDtoList.getResponseEntity().getRecords())) {
                for (MerchantDTO merDTO : merchantDtoList.getResponseEntity().getRecords()) {
                    MerchantBean merBean = new MerchantBean();
                    merBean.setId(merDTO.getId());
                    merBean.setMerName(merDTO.getMerName());
                    merBean.setMerCode(merDTO.getMerCode());
                    merBean.setMerType(merDTO.getMerType());
                    merBean.setMerPro(merDTO.getMerPro());
                    merBean.setMerCity(merDTO.getMerCity());
                    merBean.setMerCityName(merDTO.getMerCityName());
                    merBean.setMerParentName(merDTO.getMerParentName());
                    merBean.setMerState(merDTO.getMerState());
                    merBean.setActivate(merDTO.getActivate());
                    merBean.setMerProName(merDTO.getMerProName());
                    merBean.setMerLinkUser(merDTO.getMerLinkUser());
                    merBean.setMerLinkUserMobile(merDTO.getMerLinkUserMobile());
                    merBean.setMerRegisterDate(merDTO.getMerRegisterDate());//注册时间/开户时间
                    merBean.setCreateDate(merDTO.getCreateDate());
                    merchantBeanList.add(merBean);
                }
                
            }
            log.info(" return code:"+merchantDtoList.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(merchantDtoList.getResponseEntity());
            DodopalDataPage<MerchantBean> pages = new DodopalDataPage<MerchantBean>(page, merchantBeanList);
            response.setCode(merchantDtoList.getCode());
            response.setResponseEntity(pages);
        } else {
            response.setCode(merchantDtoList.getCode());
        }

        return response;
    }

    @Override
    public DodopalResponse<String> saveChildMerchants(MerchantBean merchantBean) {
        log.info("save and update childMerchantBean start：" + merchantBean);
        DodopalResponse<String> childMerchantRes = new  DodopalResponse<String>();
        //商户信息
        MerchantDTO merchantDto = new MerchantDTO();
        //商户用户信息
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        //商户补充信息表
        MerDdpInfoDTO merDdpInfoDTO = new MerDdpInfoDTO();
        //获取商户补充信息
        MerDdpInfoBean merDdpInfoBean = merchantBean.getMerDdpInfoBean();
        //连锁直营网点 额度 和 阀值
        MerAutoAmtDTO merAutoAmtDTO = null;
        
        //商户补充信息表 -------------  
        if(merDdpInfoBean!=null){
            //merDdpInfoDTO.setMerCode(merchantBean.getMerCode());
            merDdpInfoDTO.setIsAutoDistribute(merDdpInfoBean.getIsAutoDistribute());
            merDdpInfoDTO.setLimitSource(merDdpInfoBean.getLimitSource());
            //如果连锁商户直营网点是 自动转账  则保存额度和阀值
            if(IsAutoDistributeEnum.IS_AUTO_DISTRIBUTE.getCode().equals(merDdpInfoBean.getIsAutoDistribute())){
                //获取 连锁直营网点 额度 和 阀值
                MerAutoAmtBean merAutoAmtBean = merchantBean.getMerAutoAmtBean();
                
                //连锁直营网点额度阀值表
                if(merAutoAmtBean!=null){
                    merAutoAmtDTO = new MerAutoAmtDTO();
                    //merAutoAmtDTO.setMerCode(merchantBean.getMerCode());
                    merAutoAmtDTO.setAutoLimitThreshold(Double.toString(Double.valueOf(merAutoAmtBean.getAutoLimitThreshold()) * 100));
                    merAutoAmtDTO.setLimitThreshold(Double.toString(Double.valueOf(merAutoAmtBean.getLimitThreshold()) * 100));
                }
                merchantDto.setMerAutoAmtDTO(merAutoAmtDTO);
                
            }else{
                merchantDto.setMerAutoAmtDTO(merAutoAmtDTO); 
            }
        }
        merchantDto.setMerDdpInfo(merDdpInfoDTO);
        
        //获取商户用户信息
        MerchantUserBean merchantUserBean = merchantBean.getMerchantUserBean();
        if(merchantUserBean!=null){
        merchantUserDTO.setMerUserName(merchantUserBean.getMerUserName());//用户名
        merchantUserDTO.setMerUserPWD(merchantUserBean.getMerUserPWD());//登录密码
        merchantUserDTO.setMerUserTelephone(merchantUserBean.getMerUserTelephone());//固定电话
        merchantUserDTO.setMerUserIdentityType(merchantUserBean.getMerUserIdentityType());//证件类型
        merchantUserDTO.setMerUserIdentityNumber(merchantUserBean.getMerUserIdentityNumber());//证件号码
        merchantUserDTO.setMerUserEmail(merchantUserBean.getMerUserEmail());//邮箱
        merchantUserDTO.setMerUserRemark(merchantUserBean.getMerUserRemark());//备注
        merchantUserDTO.setMerUserMobile(merchantUserBean.getMerUserMobile());//手机号
        merchantUserDTO.setActivate(merchantBean.getActivate());// 界面选择启用或者停用的时候，两者在开户的都关联
        merchantUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());// TODO 用户来源
        merchantUserDTO.setMerUserAdds(merchantUserBean.getMerUserAdds());//详细地址
        merchantUserDTO.setCreateUser(merchantBean.getCreateUser());//创建人
        merchantUserDTO.setBirthday(merchantUserBean.getBirthday());//生日
        merchantUserDTO.setIncome(merchantUserBean.getIncome());//收入
        merchantUserDTO.setIsMarried(merchantUserBean.getIsMarried());//婚姻状况
        merchantUserDTO.setEducation(merchantUserBean.getEducation());//学历
        merchantUserDTO.setMerUserPro(merchantBean.getMerPro());//省份
        merchantUserDTO.setMerUserCity(merchantBean.getMerCity());//城市
        merchantDto.setMerchantUserDTO(merchantUserDTO);
        }
        // 商户为空字段
        merchantDto.setMerName(merchantBean.getMerName());
        merchantDto.setMerType(merchantBean.getMerType());
        merchantDto.setActivate(merchantBean.getActivate());
        merchantDto.setMerState(merchantBean.getMerState());
        merchantDto.setMerLinkUser(merchantBean.getMerLinkUser());
        merchantDto.setMerLinkUserMobile(merchantBean.getMerLinkUserMobile());
        merchantDto.setMerAdds(merchantBean.getMerAdds());
        merchantDto.setMerPro(merchantBean.getMerPro());
        merchantDto.setMerCity(merchantBean.getMerCity());
        merchantDto.setMerBusinessScopeId(merchantBean.getMerBusinessScopeId());
        merchantDto.setMerFax(merchantBean.getMerFax());
        merchantDto.setMerZip(merchantBean.getMerZip());
        merchantDto.setMerEmail(merchantBean.getMerEmail());
        merchantDto.setMerTelephone(merchantBean.getMerTelephone());
        merchantDto.setMerBankName(merchantBean.getMerBankName());
        merchantDto.setMerBankAccount(merchantBean.getMerBankAccount());
        merchantDto.setMerBankUserName(merchantBean.getMerBankUserName());
        merchantDto.setCreateUser(merchantBean.getCreateUser());
        merchantDto.setMerParentCode(merchantBean.getMerParentCode());
        //业务类型
        List<MerRateSupplementDTO> merRateSupDTOList = new ArrayList<MerRateSupplementDTO>();
        List<MerRateSupplementBean> merRateSupBeanList=merchantBean.getMerRateSpmtList();
        if(merRateSupBeanList!=null){
            for(MerRateSupplementBean merRateSupBeanTemp : merRateSupBeanList){
                MerRateSupplementDTO merRateSupDtoTemp = new MerRateSupplementDTO();
                merRateSupDtoTemp.setId(merRateSupBeanTemp.getId());
                merRateSupDtoTemp.setMerCode(merRateSupBeanTemp.getMerCode());
                merRateSupDtoTemp.setRateCode(merRateSupBeanTemp.getRateCode());
                merRateSupDTOList.add(merRateSupDtoTemp);
            }
            merchantDto.setMerRateSpmtList(merRateSupDTOList);
        }
        // 业务费率表
        MerBusRateBean[] merBusRateBeanList = merchantBean.getMerBusRateBeanList();
        if (merBusRateBeanList != null) {
            List<MerBusRateDTO> merBusRateList = new ArrayList<MerBusRateDTO>();
            for (MerBusRateBean merBusRateBeanTemp : merBusRateBeanList) {
                MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
                merBusRateDTOTemp.setRateCode(merBusRateBeanTemp.getRateCode());
                merBusRateDTOTemp.setProviderCode(merBusRateBeanTemp.getProCode());
                if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merBusRateBeanTemp.getRateType())){
                    if((int)(merBusRateBeanTemp.getRate()*100) <= 99999){
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate()*100); 
                    }else {
                        childMerchantRes.setCode(ResponseCode.OSS_MER_SINGLE_AMOUNT_ERROR);
                        return childMerchantRes;
                   }
                }else if(RateTypeEnum.PERMILLAGE.getCode().equals(merBusRateBeanTemp.getRateType())){
                    if ((int)(merBusRateBeanTemp.getRate()*100) <= 99999) {
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate()); 
                    } else {
                        childMerchantRes.setCode(ResponseCode.OSS_MER_PERMILLAGE_ERROR);
                        return childMerchantRes;
                    }
                }
                merBusRateDTOTemp.setRateType(merBusRateBeanTemp.getRateType());
                merBusRateDTOTemp.setCreateUser(merchantBean.getCreateUser());
                merBusRateList.add(merBusRateDTOTemp);
            }
            merchantDto.setMerBusRateList(merBusRateList);
        }
       
        log.info("save and update merchant_user_bus end：" + merchantDto);
        childMerchantRes = childMerchantDelegate.saveChildMerchant(merchantDto);
        log.info("return code：" + childMerchantRes.getCode());
        formatResponseMessage(childMerchantRes);
        return childMerchantRes;
    }

    @Override
    public DodopalResponse<MerchantBean> findChildMerchantByCode(String merCode,String merPercode) {
        log.info("query this merCode:"+merCode);
        // 从用户系统调用加载单个商户信息，用户信息，业务费率信息，商户功能信息
        DodopalResponse<MerchantDTO> merchantDtos = childMerchantDelegate.findChildMerchants(merCode,merPercode);
        log.info("users and merchant ：" + merchantDtos);
        // 前台加载单个商户信息，用户信息，业务费率信息，商户功能信息
        DodopalResponse<MerchantBean> merchantBean = new DodopalResponse<MerchantBean>();
        // 商户信息
        MerchantBean merchantBeans = new MerchantBean();
        // 商户用户信息
        MerchantUserBean merchantUserBeans = new MerchantUserBean();
        // 商户业务费率信息
        List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();
        //自动转账额度阀值
        MerAutoAmtBean merAutoAmtBean =null;
        //补充信息表
        MerDdpInfoBean merDdpInfoBean = null;

        DecimalFormat df = new DecimalFormat("0.00");
        
        // 判断后台系统调用数据是否成功
        try {
            if (merchantDtos.getCode().equals(ResponseCode.SUCCESS)) {
                // 复制商户信息
                PropertyUtils.copyProperties(merchantBeans, merchantDtos.getResponseEntity());

                // 复制用户信息
                if (merchantDtos.getResponseEntity().getMerchantUserDTO() != null) {
                    PropertyUtils.copyProperties(merchantUserBeans, merchantDtos.getResponseEntity().getMerchantUserDTO());
                }
                merchantBeans.setMerchantUserBean(merchantUserBeans);
                
                //复制自动转账额度和阀值
                if (merchantDtos.getResponseEntity().getMerAutoAmtDTO()!= null) {
                    merAutoAmtBean = new MerAutoAmtBean();
                    PropertyUtils.copyProperties(merAutoAmtBean, merchantDtos.getResponseEntity().getMerAutoAmtDTO());
                    
                    merAutoAmtBean.setAutoLimitThreshold(df.format(Double.valueOf(merAutoAmtBean.getAutoLimitThreshold())/100)); 
                    merAutoAmtBean.setLimitThreshold(df.format(Double.valueOf(merAutoAmtBean.getLimitThreshold())/100)); 
                    
                }
                merchantBeans.setMerAutoAmtBean(merAutoAmtBean);
                
                //复制补充信息表
                if (merchantDtos.getResponseEntity().getMerDdpInfo()!= null) {
                    merDdpInfoBean = new MerDdpInfoBean();
                    PropertyUtils.copyProperties(merDdpInfoBean, merchantDtos.getResponseEntity().getMerDdpInfo());
                }
                merchantBeans.setMerDdpInfoBean(merDdpInfoBean);
                
                
                //复制业务类型
                List<MerRateSupplementBean> merSupBeanList =new ArrayList<MerRateSupplementBean>();
                if(merchantDtos.getResponseEntity().getMerRateSpmtList()!=null){
                    for(MerRateSupplementDTO merSupDtoTemp :merchantDtos.getResponseEntity().getMerRateSpmtList()){
                        MerRateSupplementBean merSupBeanTemp  = new MerRateSupplementBean();
                        merSupBeanTemp.setId(merSupDtoTemp.getId());
                        merSupBeanTemp.setRateCode(merSupDtoTemp.getRateCode());
                        merSupBeanTemp.setMerCode(merSupDtoTemp.getMerCode());
                        merSupBeanList.add(merSupBeanTemp);
                    }
                }
                merchantBeans.setMerRateSpmtList(merSupBeanList);
                // 复制业务费率信息
                List<MerBusRateDTO> busRateDTOList = merchantDtos.getResponseEntity().getMerBusRateList();
                List<MerBusRateBean> mockData = getYktInfoByRateCode(null);
                if (CollectionUtils.isNotEmpty(busRateDTOList)) {
                    for (MerBusRateDTO tempDTO : busRateDTOList) {
                        MerBusRateBean busBean = new MerBusRateBean();
                        for(MerBusRateBean productBeanTemp :mockData){
                            if(tempDTO.getProviderCode().equals(productBeanTemp.getProCode()) && tempDTO.getRateCode().equals(productBeanTemp.getRateCode())){
                                PropertyUtils.copyProperties(busBean, tempDTO);
                                busBean.setProCode(tempDTO.getProviderCode());
                                busBean.setProName(productBeanTemp.getProName()); 
                                busBean.setRateType(tempDTO.getRateType());
                                busBean.setRateName(productBeanTemp.getRateName());
                                if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(tempDTO.getRateType())){
                                    busBean.setRate(tempDTO.getRate()/100);   
                                }else{
                                    busBean.setRate(tempDTO.getRate());  
                                }
                                busBean.setChecked(true);
                            }
                        }
                        merBusRateBeanList.add(busBean);
                    }
                } 
                merchantBeans.setMerBusRateBeanList(merBusRateBeanList.toArray(new MerBusRateBean[merBusRateBeanList.size()]));
                merchantBean.setCode(merchantDtos.getCode());
                merchantBean.setResponseEntity(merchantBeans);
            } else {
                merchantBean.setCode(merchantDtos.getCode());
            }

        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            merchantBean.setCode(merchantDtos.getCode());
        }

        return merchantBean;
    }
    /**
     * 更新和编辑子商户信息
     */
    @Override
    public DodopalResponse<String> upChildMerchantBean(MerchantBean merchantBean) {
        log.info("update verified and unverify start:" + merchantBean);
        DodopalResponse<String> childMerchantRes =new DodopalResponse<String>();
        MerchantDTO merchantDto = new MerchantDTO();
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();

        MerchantUserBean merchantUserBean = merchantBean.getMerchantUserBean();
        merchantUserDTO.setMerUserName(merchantUserBean.getMerUserName());
        merchantUserDTO.setMerUserNickName(merchantUserBean.getMerUserNickName());
        merchantUserDTO.setMerUserSex(merchantUserBean.getMerUserSex());
        if(StringUtils.isNotBlank(merchantUserBean.getMerUserPWD())){
            merchantUserDTO.setMerUserPWD(merchantUserBean.getMerUserPWD()); 
        }
        merchantUserDTO.setMerUserTelephone(merchantUserBean.getMerUserTelephone());
        merchantUserDTO.setMerUserIdentityType(merchantUserBean.getMerUserIdentityType());
        merchantUserDTO.setMerUserIdentityNumber(merchantUserBean.getMerUserIdentityNumber());
        merchantUserDTO.setMerUserEmail(merchantUserBean.getMerUserEmail());
        merchantUserDTO.setMerUserRemark(merchantUserBean.getMerUserRemark());
        merchantUserDTO.setMerUserMobile(merchantUserBean.getMerUserMobile());
        merchantUserDTO.setMerUserAdds(merchantUserBean.getMerUserAdds());
        merchantUserDTO.setUserCode(merchantUserBean.getUserCode());
        merchantUserDTO.setUpdateUser(merchantBean.getCreateUser());
        merchantUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());
        merchantUserDTO.setUpdateUser(merchantBean.getCreateUser());
        merchantUserDTO.setBirthday(merchantUserBean.getBirthday());//生日
        merchantUserDTO.setIncome(merchantUserBean.getIncome());//收入
        merchantUserDTO.setIsMarried(merchantUserBean.getIsMarried());//婚姻状况
        merchantUserDTO.setEducation(merchantUserBean.getEducation());//学历
        merchantDto.setMerchantUserDTO(merchantUserDTO);

        // 商户为空字段
        merchantDto.setMerName(merchantBean.getMerName());
        merchantDto.setMerType(merchantBean.getMerType());
        merchantDto.setActivate(merchantBean.getActivate());
        merchantDto.setMerClassify(merchantBean.getMerClassify());
        merchantDto.setMerProperty(merchantBean.getMerProperty());
        merchantDto.setMerState(merchantBean.getMerState());
        merchantDto.setMerLinkUser(merchantBean.getMerLinkUser());
        merchantDto.setMerLinkUserMobile(merchantBean.getMerLinkUserMobile());
        merchantDto.setMerAdds(merchantBean.getMerAdds());
        merchantDto.setMerPro(merchantBean.getMerPro());
        merchantDto.setMerCity(merchantBean.getMerCity());
        merchantDto.setMerBusinessScopeId(merchantBean.getMerBusinessScopeId());
        merchantDto.setMerFax(merchantBean.getMerFax());
        merchantDto.setMerZip(merchantBean.getMerZip());
        merchantDto.setMerBankName(merchantBean.getMerBankName());
        merchantDto.setMerBankAccount(merchantBean.getMerBankAccount());
        merchantDto.setMerBankUserName(merchantBean.getMerBankUserName());
        merchantDto.setMerState(merchantBean.getMerState());
        merchantDto.setMerStateUser(merchantBean.getMerStateUser());
        merchantDto.setUpdateUser(merchantBean.getCreateUser());
        merchantDto.setMerCode(merchantBean.getMerCode());
        merchantDto.setMerParentCode(merchantBean.getMerParentCode());
        merchantDto.setMerStateUser(merchantBean.getCreateUser());
        merchantDto.setMerEmail(merchantUserBean.getMerUserEmail());
        merchantDto.setMerTelephone(merchantUserBean.getMerUserTelephone());

        
        //商户补充信息表
        MerDdpInfoDTO merDdpInfoDTO = null;
        //获取商户补充信息
        MerDdpInfoBean merDdpInfoBean = merchantBean.getMerDdpInfoBean();
        //连锁直营网点 额度 和 阀值
        MerAutoAmtDTO merAutoAmtDTO = null;
        
        //商户补充信息表 -------------  
        if(merDdpInfoBean!=null){
            merDdpInfoDTO = new MerDdpInfoDTO();
            merDdpInfoDTO.setMerCode(merchantBean.getMerCode());
            merDdpInfoDTO.setIsAutoDistribute(merDdpInfoBean.getIsAutoDistribute());
            merDdpInfoDTO.setLimitSource(merDdpInfoBean.getLimitSource());
            //如果连锁商户直营网点是 自动转账  则保存额度和阀值
            if(IsAutoDistributeEnum.IS_AUTO_DISTRIBUTE.getCode().equals(merDdpInfoBean.getIsAutoDistribute())){
                //获取 连锁直营网点 额度 和 阀值
                MerAutoAmtBean merAutoAmtBean = merchantBean.getMerAutoAmtBean();
                
                //连锁直营网点额度阀值表
                if(merAutoAmtBean!=null){
                    merAutoAmtDTO = new MerAutoAmtDTO();
                    merAutoAmtDTO.setMerCode(merchantBean.getMerCode());
                    
                    merAutoAmtDTO.setAutoLimitThreshold(Double.toString(Double.valueOf(merAutoAmtBean.getAutoLimitThreshold()) * 100));
                    merAutoAmtDTO.setLimitThreshold(Double.toString(Double.valueOf(merAutoAmtBean.getLimitThreshold()) * 100));
                }
                merchantDto.setMerAutoAmtDTO(merAutoAmtDTO);
                
            }else{
                merchantDto.setMerAutoAmtDTO(merAutoAmtDTO);
            }
        }
        merchantDto.setMerDdpInfo(merDdpInfoDTO);
        
        //业务类型
        List<MerRateSupplementDTO> merRateSupDTOList = new ArrayList<MerRateSupplementDTO>();
        List<MerRateSupplementBean> merRateSupBeanList=merchantBean.getMerRateSpmtList();
        if(merRateSupBeanList!=null && merRateSupBeanList.size()!=0){
            for(MerRateSupplementBean merRateSupBeanTemp : merRateSupBeanList){
                MerRateSupplementDTO merRateSupDtoTemp = new MerRateSupplementDTO();
                merRateSupDtoTemp.setId(merRateSupBeanTemp.getId());
                merRateSupDtoTemp.setMerCode(merRateSupBeanTemp.getMerCode());
                merRateSupDtoTemp.setRateCode(merRateSupBeanTemp.getRateCode());
                merRateSupDTOList.add(merRateSupDtoTemp);
            }
            merchantDto.setMerRateSpmtList(merRateSupDTOList);
        }
        //业务费率表
        MerBusRateBean[] merBusRateBeanList = merchantBean.getMerBusRateBeanList();
        List<MerBusRateDTO> merBusRateList = new ArrayList<MerBusRateDTO>();
        if (merBusRateBeanList !=null && merBusRateBeanList.length!=0) {
            for (MerBusRateBean merBusRateBeanTemp : merBusRateBeanList) {
                MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
                merBusRateDTOTemp.setMerCode(merBusRateBeanTemp.getMerCode());
                merBusRateDTOTemp.setRateCode(merBusRateBeanTemp.getRateCode());
                merBusRateDTOTemp.setActivate(merBusRateBeanTemp.getActivate());
                merBusRateDTOTemp.setProviderCode(merBusRateBeanTemp.getProCode());
                merBusRateDTOTemp.setRateType(merBusRateBeanTemp.getRateType());
                if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merBusRateBeanTemp.getRateType())){
                    if((int)(merBusRateBeanTemp.getRate()*100) <= 99999){
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate()*100); 
                    }else {
                        childMerchantRes.setCode(ResponseCode.OSS_MER_SINGLE_AMOUNT_ERROR);
                        return childMerchantRes;
                   }
                }else if(RateTypeEnum.PERMILLAGE.getCode().equals(merBusRateBeanTemp.getRateType())){
                    if((int)(merBusRateBeanTemp.getRate()*100) <= 99999){
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate()); 
                    }else {
                        childMerchantRes.setCode(ResponseCode.OSS_MER_SINGLE_AMOUNT_ERROR);
                        return childMerchantRes;
                   }
                }
                merBusRateList.add(merBusRateDTOTemp);
            }
            merchantDto.setMerBusRateList(merBusRateList);
        }
        childMerchantRes = childMerchantDelegate.updateChildMerchant(merchantDto);
        log.info("update verified and unverify end:" + childMerchantRes);
        formatResponseMessage(childMerchantRes);
        return childMerchantRes;
    }

    @Override
    public DodopalResponse<Integer> startAndDisableMerchant(String[] merCode, String activate,String merPercode, String updateUser) {
        log.info("portal startAndDisableMerchant merCode:"+merCode+",activate:"+activate);
        DodopalResponse<Integer> number = childMerchantDelegate.startAndDisableChildMerchant(merCode, activate,merPercode, updateUser);
        return number;
    }

    @Override
    public DodopalResponse<Integer> delChildMerchant(String[] merCode, String merState, String merPercode, String updateUser) {
        log.info("portal startAndDisableMerchant merCode:"+merCode+",merState:"+merState);
        DodopalResponse<Integer> number = childMerchantDelegate.deleteChildMerchant(merCode, merState, merPercode, updateUser);
        return number;
    }
 
    /**
     * 查看通卡公司信息
     */
    @Override
    public DodopalResponse<List<MerBusRateBean>> findProductList(String merCode, String merType) {
        DodopalResponse<List<MerBusRateDTO>> roductBeans = merchantDelegate.findMerBusRateByMerCode(merCode);
        List<MerBusRateDTO> merBusRateList  = roductBeans.getResponseEntity();
        RateCodeEnum rateCodeEnum = RateCodeEnum.YKT_RECHARGE;
        if(StringUtils.isNotBlank(merType) && MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
        	rateCodeEnum = null;
        }
        List<MerBusRateBean> mockData = getYktInfoByRateCode(rateCodeEnum);
        DodopalResponse<List<MerBusRateBean>> resResult = new DodopalResponse<List<MerBusRateBean>>();
        List<MerBusRateBean> proBeanList =new ArrayList<MerBusRateBean>();
        if(CollectionUtils.isNotEmpty(merBusRateList)){
            for(MerBusRateDTO merBusRateTemp : merBusRateList){
                for(MerBusRateBean mock : mockData){
                    if(merBusRateTemp.getProviderCode().equals(mock.getProCode()) && merBusRateTemp.getRateCode().equals(mock.getRateCode())){
                    	MerBusRateBean productBean = new MerBusRateBean();
                        productBean.setProCode(merBusRateTemp.getProviderCode());
                        productBean.setProName(mock.getProName());
                        productBean.setRateCode(merBusRateTemp.getRateCode());
                        productBean.setRateName(mock.getRateName());
                        if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merBusRateTemp.getRateType())){
                            productBean.setRate(merBusRateTemp.getRate()/100); 
                        }else{
                            productBean.setRate(merBusRateTemp.getRate()); 
                        }
                        productBean.setRateType(merBusRateTemp.getRateType());
                        productBean.setChecked(true);
                        proBeanList.add(productBean);
                    }
                }
            }
        }
        resResult.setCode(ResponseCode.SUCCESS);
        resResult.setResponseEntity(proBeanList);
        return resResult;
    }
    /**
     * 加载已选择的一卡通信息
     */
    @Override
    public DodopalResponse<List<MerBusRateBean>> loadYktInfo(String[] productCode,String merCode) {
        DodopalResponse<List<MerBusRateDTO>> roductBeans = merchantDelegate.findMerBusRateByMerCode(merCode);
        DodopalResponse<List<MerBusRateBean>> resResult = new DodopalResponse<List<MerBusRateBean>>();
        List<MerBusRateDTO> merBusRateList  = roductBeans.getResponseEntity();
        
        List<MerBusRateBean> result = new ArrayList<MerBusRateBean>();
        List<MerBusRateBean> mockData = mockData();
        for (MerBusRateBean mock : mockData) {
            for (String code : productCode) {
                for(MerBusRateDTO merBusRateDTO: merBusRateList){
                    MerBusRateBean productBean = new MerBusRateBean();
                    if (mock.getProCode().equals(code)) {
                        if(merBusRateDTO.getProviderCode().equals(mock.getProCode())){
                            //商户CODE
                            productBean.setMerCode(merBusRateDTO.getMerCode());
                            productBean.setProCode(merBusRateDTO.getProviderCode());
                            productBean.setProName(mock.getProName());
                            productBean.setRateCode(merBusRateDTO.getRateCode());
                            productBean.setRateName(mock.getRateName());
                            productBean.setRateType(merBusRateDTO.getRateType());
                            productBean.setChecked(true);
                            result.add(productBean);
                        }
                    }
                }
              
            }
        }
        resResult.setCode(ResponseCode.SUCCESS);
        resResult.setResponseEntity(result);
        return resResult;
    }
    /**
     * 加载通卡公司数据
     * @return
     */
    private List<MerBusRateBean> mockData() {
        List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();
        DodopalResponse<List<ProductYKTDTO>> responseYktDto =  merchantDelegate.yktData();
        List<ProductYKTDTO> productYKTDTOList  = responseYktDto.getResponseEntity();
        for(ProductYKTDTO proDuctYKTMock : productYKTDTOList){
            //业务类型为一卡通充值
            if(OpenSignEnum.OPENED.getCode().equals(proDuctYKTMock.getYktIsRecharge())){
                MerBusRateBean merBusRateBean = new MerBusRateBean();
                //通卡公司Code
                merBusRateBean.setProCode(proDuctYKTMock.getYktCode());
                //通卡公司名称
                merBusRateBean.setProName(proDuctYKTMock.getYktName());
                merBusRateBean.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
                merBusRateBean.setRateName(RateCodeEnum.YKT_RECHARGE.getName());
                merBusRateBean.setRate(0);
                merBusRateBeanList.add(merBusRateBean);
            }
           
        }
        return merBusRateBeanList;
    }
    
    /**
     * 根据业务类型加载通卡公司数据
     * @return
     */
	private List<MerBusRateBean> getYktInfoByRateCode(RateCodeEnum rateCode) {
		List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();
		DodopalResponse<List<ProductYKTDTO>> responseYktDto = merchantDelegate.yktData();
		List<ProductYKTDTO> productYKTDTOList = responseYktDto.getResponseEntity();
		if (CollectionUtils.isNotEmpty(productYKTDTOList)) {
			for (ProductYKTDTO proDuctYKTMock : productYKTDTOList) {
				// 业务类型为一卡通充值
				if (RateCodeEnum.YKT_RECHARGE == rateCode) {
					if (OpenSignEnum.OPENED.getCode().equals(proDuctYKTMock.getYktIsRecharge())) {
						MerBusRateBean merBusRateBean = new MerBusRateBean();
						// 通卡公司Code
						merBusRateBean.setProCode(proDuctYKTMock.getYktCode());
						// 通卡公司名称
						merBusRateBean.setProName(proDuctYKTMock.getYktName());
						merBusRateBean.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
						merBusRateBean.setRateName(RateCodeEnum.YKT_RECHARGE.getName());
						merBusRateBean.setRate(0);
						merBusRateBeanList.add(merBusRateBean);
					}
				} else if (RateCodeEnum.YKT_PAYMENT == rateCode) {
					if (OpenSignEnum.OPENED.getCode().equals(proDuctYKTMock.getYktIsPay())) {
						MerBusRateBean merBusRateBean = new MerBusRateBean();
						// 通卡公司Code
						merBusRateBean.setProCode(proDuctYKTMock.getYktCode());
						// 通卡公司名称
						merBusRateBean.setProName(proDuctYKTMock.getYktName());
						merBusRateBean.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
						merBusRateBean.setRateName(RateCodeEnum.YKT_PAYMENT.getName());
						merBusRateBean.setRate(0);
						merBusRateBeanList.add(merBusRateBean);
					}
				} else {
					if (OpenSignEnum.OPENED.getCode().equals(proDuctYKTMock.getYktIsRecharge())) {
						MerBusRateBean merBusRateBean = new MerBusRateBean();
						// 通卡公司Code
						merBusRateBean.setProCode(proDuctYKTMock.getYktCode());
						// 通卡公司名称
						merBusRateBean.setProName(proDuctYKTMock.getYktName());
						merBusRateBean.setRateCode(RateCodeEnum.YKT_RECHARGE.getCode());
						merBusRateBean.setRateName(RateCodeEnum.YKT_RECHARGE.getName());
						merBusRateBean.setRate(0);
						merBusRateBeanList.add(merBusRateBean);
					}
					if (OpenSignEnum.OPENED.getCode().equals(proDuctYKTMock.getYktIsPay())) {
						MerBusRateBean merBusRateBean = new MerBusRateBean();
						// 通卡公司Code
						merBusRateBean.setProCode(proDuctYKTMock.getYktCode());
						// 通卡公司名称
						merBusRateBean.setProName(proDuctYKTMock.getYktName());
						merBusRateBean.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
						merBusRateBean.setRateName(RateCodeEnum.YKT_PAYMENT.getName());
						merBusRateBean.setRate(0);
						merBusRateBeanList.add(merBusRateBean);
					}
				}
			}
		}
		return merBusRateBeanList;
	}


    @Override
    public DodopalResponse<Boolean> checkMobileExist(String mobile, String merCode) {
        return childMerchantDelegate.checkMobileExist(mobile, merCode);
    }

    @Override
    public DodopalResponse<Boolean> checkUserNameExist(String userName, String merCode) {
        return childMerchantDelegate.checkUserNameExist(userName, merCode);
    }

    @Override
    public DodopalResponse<Boolean> checkMerchantNameExist(String merName, String merCode) {
        return childMerchantDelegate.checkMerchantNameExist(merName, merCode);
    }

    /**
     * 解析费率超过父级或父级无此费率的返回消息 TODO:待优化
     * @param response
     */
    private void formatResponseMessage(DodopalResponse<String> response) {
        if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(response.getCode()) || ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(response.getCode())) {
            String msgAll = response.getMessage();
            if (StringUtils.isNotBlank(msgAll)) {
                StringBuilder sbMsg = new StringBuilder();
                if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(response.getCode())) {
                    // sbMsg.append("以下业务超过父级商户费率：");
                } else if (ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(response.getCode())) {
                    sbMsg.append("父级商户无此业务或费率类型不符：");
                }
                List<MerBusRateBean> mockData = mockData();
                String[] proArray = StringUtils.split(msgAll, ";");
                // 消息格式：通卡公司code,业务类型code,费率类型code
                if (proArray.length > 0) {
                    //for (String pro : proArray) {
                        String[] rateArray = StringUtils.split(proArray[0], ",");
                        if (rateArray.length > 2) {
                            for (MerBusRateBean temp : mockData) {
                                if (rateArray[0].equals(temp.getProCode()) && rateArray[1].equals(temp.getRateCode())) {
                                    if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(response.getCode())) {
                                        sbMsg.append("[" + temp.getProName() + "] 费率值不能高于上级商户");
                                    } else if (ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(response.getCode())) {
                                        sbMsg.append("[" + temp.getProName() + "||" + temp.getRateName() + "||" + RateTypeEnum.getRateTypeByCode(rateArray[2]).getName() + "]");
                                    }
                                    break;
                                }
                            }
                        }
                    //}
                }
                response.setMessage(sbMsg.toString());
            } 
        } else if(ResponseCode.USERS_BELOW_CHILD_RATE.equals(response.getCode())){
            String msgAll = response.getMessage();
            if (StringUtils.isNotBlank(msgAll)) {
                StringBuilder sbMsg = new StringBuilder();
                //sbMsg.append("不能小于下级商户费率：");
                List<MerBusRateBean> mockData = mockData();
                String[] proArray = StringUtils.split(msgAll, ";");
                // 消息格式：通卡公司code,业务类型code,费率类型code,费率数值,下级商户名称
                if (proArray.length > 0) {
                   // for (String pro : proArray) { // 原本提示多条，现改为提示一条，如需多条，可再次放开
                        String[] rateArray = StringUtils.split(proArray[0], ",");
                        if (rateArray.length > 4) {
                            for (MerBusRateBean temp : mockData) {
                                if (rateArray[0].equals(temp.getProCode()) && rateArray[1].equals(temp.getRateCode())) {
                                    sbMsg.append("[" + temp.getProName() + "] 费率值不能低于:" + rateArray[3]);
                                    break;
                                }
                            }
                        }
                    //}
                }
                response.setMessage(sbMsg.toString());
            }
        }
    }

    @Override
    public DodopalResponse<List<MerRateSupplementBean>> findMerRateSupplementByCode(String merCode, String merType) {
        DodopalResponse<List<MerRateSupplementDTO>> merRateSupDTOList = merchantDelegate.findMerRateSupplementsByMerCode(merCode);
        DodopalResponse<List<MerRateSupplementBean>> respone= new DodopalResponse<List<MerRateSupplementBean>>();
        if(ResponseCode.SUCCESS.equals(merRateSupDTOList.getCode()) && merRateSupDTOList.getResponseEntity() != null) {
        	List<MerRateSupplementBean> merRateSupBeanList = new ArrayList<MerRateSupplementBean>();
            for(MerRateSupplementDTO merSupTemp : merRateSupDTOList.getResponseEntity()){
            	String rateCode = merSupTemp.getRateCode();
				if ((StringUtils.isBlank(merType) || !MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) && RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCode)) {
					continue;
				}
                MerRateSupplementBean merSupBeanTemp = new MerRateSupplementBean();
                merSupBeanTemp.setId(merSupTemp.getId());
                merSupBeanTemp.setMerCode(merSupTemp.getMerCode());
                merSupBeanTemp.setRateCode(merSupTemp.getRateCode());
                merSupBeanTemp.setPageCallbackUrl(merSupTemp.getPageCallbackUrl());
                merSupBeanTemp.setServiceNoticeUrl(merSupTemp.getServiceNoticeUrl());
                merRateSupBeanList.add(merSupBeanTemp);
            }
            respone.setResponseEntity(merRateSupBeanList);
        }
        respone.setCode(merRateSupDTOList.getCode());
        return respone;
    }
    
}
