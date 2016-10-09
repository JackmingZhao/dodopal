package com.dodopal.oss.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.users.dto.MerAutoAmtDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerDdpInfoDTO;
import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.IsAutoDistributeEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.MerAutoAmtBean;
import com.dodopal.oss.business.bean.MerBusRateBean;
import com.dodopal.oss.business.bean.MerDdpInfoBean;
import com.dodopal.oss.business.bean.MerFunctionInfoBean;
import com.dodopal.oss.business.bean.MerKeyTypeBean;
import com.dodopal.oss.business.bean.MerRateSupplementBean;
import com.dodopal.oss.business.bean.MerchantBean;
import com.dodopal.oss.business.bean.MerchantUserBean;
import com.dodopal.oss.business.bean.ProductBean;
import com.dodopal.oss.business.dao.MerchantUserMapper;
import com.dodopal.oss.business.dao.ProductIcdcMapper;
import com.dodopal.oss.business.model.ProductIcdc;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.dto.MerchantQuery;
import com.dodopal.oss.business.service.MerchantService;
import com.dodopal.oss.business.service.PrdRateService;
import com.dodopal.oss.delegate.MerchantDelegate;

@Service
public class MerchantServiceImpl implements MerchantService {
    private final static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    MerchantDelegate merchantdelegate;

    @Autowired
    MerchantUserMapper merchantMapper;
    @Autowired
    ProductIcdcMapper productIcdcMapper;
    
    @Autowired
    PrdRateService prdRateService;
    
    /**
     * Title:审核通过商户信息中的开户保存商户信息
     * User:qiaojicheng 
     * Time:2015-05-13
     * UPTime: 2015-12-05 
     * UPContent : 修改商户补充信息表，修改商户用户信息表
     */   
    @Override
    public DodopalResponse<String> saveMerchantUserBusRate(MerchantBean merchantBean) {
        log.info("start--------------------saveMerchantUserBusRate----------");
        log.info("oss商户开户保存的参数----------------:"+merchantBean);
        DodopalResponse<String> mer = new DodopalResponse<String>();
        //商户信息
        MerchantDTO merchantDto = new MerchantDTO();
        //商户用户信息
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        //商户验签密钥信息
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        //商户补充信息表
        MerDdpInfoDTO merDdpInfoDTO = new MerDdpInfoDTO();
        //自动分配额度
        MerAutoAmtDTO merAutoAmtDTO = new MerAutoAmtDTO();
        //获取商户补充信息
        MerDdpInfoBean merDdpInfoBean = merchantBean.getMerDdpInfoBean();
        //商户签名密钥
        MerKeyTypeBean merKeyTypeBean = merchantBean.getMerKeyTypeBean();
        //自动分配额度
        MerAutoAmtBean merAutoAmtBean = merchantBean.getMerAutoAmtBean();
        
        if (merKeyTypeBean != null) {
            //merKeyTypeDTO.setMerCode(merchantBean.getMerCode());
            merKeyTypeDTO.setMerMD5PayPwd(merKeyTypeBean.getMerMD5PayPwd());
            merKeyTypeDTO.setMerMD5BackPayPWD(merKeyTypeBean.getMerMD5BackPayPWD());
            merchantDto.setMerKeyTypeDTO(merKeyTypeDTO);
        }
        //获取商户用户信息
        MerchantUserBean merchantUserBean = merchantBean.getMerchantUserBean();
        if (merchantUserBean != null) {
            merchantUserDTO.setMerUserName(merchantUserBean.getMerUserName());
            merchantUserDTO.setMerUserNickName(merchantUserBean.getMerUserNickName());
            merchantUserDTO.setMerUserSex(merchantUserBean.getMerUserSex());
            merchantUserDTO.setMerUserTelephone(merchantUserBean.getMerUserTelephone());
            merchantUserDTO.setMerUserIdentityType(merchantUserBean.getMerUserIdentityType());
            merchantUserDTO.setMerUserIdentityNumber(merchantUserBean.getMerUserIdentityNumber());
            merchantUserDTO.setMerUserEmail(merchantUserBean.getMerUserEmail());
            merchantUserDTO.setMerUserRemark(merchantUserBean.getMerUserRemark());
            merchantUserDTO.setMerUserMobile(merchantUserBean.getMerUserMobile());
            merchantUserDTO.setActivate(merchantBean.getActivate());// 界面选择启用或者停用的时候，两者在开户的都关联
            merchantUserDTO.setMerUserSource(SourceEnum.OSS.getCode());
            merchantUserDTO.setMerUserAdds(merchantUserBean.getMerUserAdds());
            merchantUserDTO.setCreateUser(merchantBean.getCreateUser());
            //----------新增字段 2015-12-05------
            merchantUserDTO.setEducation(merchantUserBean.getEducation());//学历
            merchantUserDTO.setIncome(merchantUserBean.getIncome());//收入
            merchantUserDTO.setBirthday(merchantUserBean.getBirthday());//'生日，格式：1990-09-10
            merchantUserDTO.setIsMarried(merchantUserBean.getIsMarried());//是否已婚，0是，1否
            merchantUserDTO.setTradeArea(merDdpInfoBean.getTradeArea());//商圈
            merchantUserDTO.setMerUserPro(merchantBean.getMerPro());//用户所在省份
            merchantUserDTO.setMerUserCity(merchantBean.getMerCity()); //用户所在城市
            merchantDto.setMerchantUserDTO(merchantUserDTO);
        }
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
        merchantDto.setCreateUser(merchantBean.getCreateUser());
        merchantDto.setMerParentCode(merchantBean.getMerParentCode());
        merchantDto.setMerEmail(merchantUserBean.getMerUserEmail());
        merchantDto.setMerTelephone(merchantUserBean.getMerUserTelephone());
        //账号资金类型
        merchantDto.setFundType(merchantBean.getFundType());
        //商户打款人
        merchantDto.setMerPayMoneyUser(merchantBean.getMerPayMoneyUser());
        
        //商户补充信息表 -------------  2015-12-05  修改人 ： JOE -----
        if(merDdpInfoBean!=null){
            merDdpInfoDTO.setMerDdpLinkIp(merDdpInfoBean.getMerDdpLinkIp());
            merDdpInfoDTO.setMerDdpLinkUser(merDdpInfoBean.getMerDdpLinkUser());
            merDdpInfoDTO.setMerDdpLinkUserMobile(merDdpInfoBean.getMerDdpLinkUserMobile());
            merDdpInfoDTO.setSettlementType(merDdpInfoBean.getSettlementType());
            merDdpInfoDTO.setSettlementThreshold(merDdpInfoBean.getSettlementThreshold());
            merDdpInfoDTO.setTradeArea(merDdpInfoBean.getTradeArea());
            if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merchantBean.getMerType()) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merchantBean.getMerType())){
                merDdpInfoDTO.setIsAutoDistribute(merDdpInfoBean.getIsAutoDistribute());
              //自动分配额度
                if(IsAutoDistributeEnum.IS_AUTO_DISTRIBUTE.getCode().equals(merDdpInfoBean.getIsAutoDistribute())){
                   // merAutoAmtDTO.setMerCode(merchantBean.getMerCode());
                    merAutoAmtDTO.setLimitThreshold(merAutoAmtBean.getLimitThreshold());
                    merAutoAmtDTO.setAutoLimitThreshold(merAutoAmtBean.getAutoLimitThreshold());
                    merchantDto.setMerAutoAmtDTO(merAutoAmtDTO);
                }
            }
//            if(MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merchantBean.getMerType())){
//                merDdpInfoDTO.setLimitSource(merDdpInfoBean.getLimitSource());
//            }
            
        }
        merchantDto.setMerDdpInfo(merDdpInfoDTO);
        
        
        
        //商户业务信息表
        List<MerRateSupplementBean> merRateSpmtList= merchantBean.getMerRateSpmtList();
        if(merRateSpmtList.size()!=0){
            List<MerRateSupplementDTO> merBusRateList = new ArrayList<MerRateSupplementDTO>();
            for(MerRateSupplementBean merRateSupplementBeanTemp :merRateSpmtList){
                MerRateSupplementDTO merRateSupplementDTOTemp = new MerRateSupplementDTO();
                merRateSupplementDTOTemp.setId(merRateSupplementBeanTemp.getId());
                merRateSupplementDTOTemp.setMerCode(merRateSupplementBeanTemp.getMerCode());
                merRateSupplementDTOTemp.setRateCode(merRateSupplementBeanTemp.getRateCode());
                merRateSupplementDTOTemp.setServiceNoticeUrl(merRateSupplementBeanTemp.getServiceNoticeUrl());
                merRateSupplementDTOTemp.setPageCallbackUrl(merRateSupplementBeanTemp.getPageCallbackUrl());
                merRateSupplementDTOTemp.setPageCheckCallbackUrl(merRateSupplementBeanTemp.getPageCheckCallbackUrl());
                merRateSupplementDTOTemp.setCreateUser(merchantBean.getCreateUser());
                //新增是否弹出提示信息 ---------- 2015-12-05 JOE--------
                merRateSupplementDTOTemp.setIsShowErrorMsg(merRateSupplementBeanTemp.getIsShowErrorMsg());
                merBusRateList.add(merRateSupplementDTOTemp);
            }
            merchantDto.setMerRateSpmtList(merBusRateList);
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
                    if ((int)(merBusRateBeanTemp.getRate() * 100) <=99999) {
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate() * 100);
                    } else {
                         mer.setCode(ResponseCode.OSS_MER_SINGLE_AMOUNT_ERROR);
                        return mer;
                    }
                }else if(RateTypeEnum.PERMILLAGE.getCode().equals(merBusRateBeanTemp.getRateType())){
                    if ((int)(merBusRateBeanTemp.getRate() * 100) <=99999) {
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate());
                    } else {
                        mer.setCode(ResponseCode.OSS_MER_PERMILLAGE_ERROR);
                        return mer;
                    }
                }
                merBusRateDTOTemp.setRateType(merBusRateBeanTemp.getRateType());
                merBusRateDTOTemp.setCreateUser(merchantBean.getCreateUser());
                merBusRateList.add(merBusRateDTOTemp);
            }
            merchantDto.setMerBusRateList(merBusRateList);
        }
        // 自定义功能商户
        MerFunctionInfoBean[] merFunctionInfoBeanList = merchantBean.getMerDefineFunBeanList();
        if (merFunctionInfoBeanList != null) {
            List<MerFunctionInfoDTO> merFunctionInfoList = new ArrayList<MerFunctionInfoDTO>();
            for (MerFunctionInfoBean merFunctionInfoBeanTemp : merFunctionInfoBeanList) {
                MerFunctionInfoDTO merFunctionInfoDTOTemp = new MerFunctionInfoDTO();
                merFunctionInfoDTOTemp.setMerFunCode(merFunctionInfoBeanTemp.getMerFunCode());
                merFunctionInfoDTOTemp.setMerFunName(merFunctionInfoBeanTemp.getMerFunName());
                merFunctionInfoBeanTemp.setCreateUser(merchantBean.getCreateUser());
                merFunctionInfoList.add(merFunctionInfoDTOTemp);
            }
            merchantDto.setMerDefineFunList(merFunctionInfoList);
        }
        
     
        log.info("save and update merchant_user_bus end：" + merchantDto);
        mer = merchantdelegate.saveMerchant(merchantDto);
        log.info("return code：" + mer.getCode());
        formatResponseMessage(mer);
        return mer;
    }

    /**
     * Title:商户停用启用 User:qiaojicheng Time:2015-05-13
     */
    @Override
    public DodopalResponse<Integer> startAndDisableMerchant(List<String> merCode, String activate, String updateUser) {
        DodopalResponse<Integer> number = merchantdelegate.startAndDisableMerchant(merCode, activate, updateUser);
        return number;
    }

    /**
     * @Title: findUnverifyMgmtByMerCode
     * @Description: 根据商户号查询
     * @param merUser
     * @return 设定文件 DodopalResponse<MerchantBean> 返回类型
     * @throws
     */
    public DodopalResponse<MerchantBean> findInfoByMerCode(MerchantBean merUser, MerStateEnum state) {
        //获取当前的商户
        DodopalResponse<MerchantDTO> merDto = merchantdelegate.findMerchantByCode(merUser.getMerCode());
        DodopalResponse<MerchantBean> merbean = new DodopalResponse<MerchantBean>();
        MerchantUserBean merchantUserBean = new MerchantUserBean();
        MerchantBean bean = new MerchantBean();
        try {
            PropertyUtils.copyProperties(bean, merDto.getResponseEntity());
            //商户的管理员转换
            if (null != merDto.getResponseEntity().getMerchantUserDTO()) {
                PropertyUtils.copyProperties(merchantUserBean, merDto.getResponseEntity().getMerchantUserDTO());
            }
            //保存管理员信息
            bean.setMerchantUserBean(merchantUserBean);
            List<MerBusRateDTO> busRateDTOList = merDto.getResponseEntity().getMerBusRateList();
            //一卡通 公司信息
            if (null != busRateDTOList && busRateDTOList.size() > 0) {
                List<MerBusRateBean> beanBusList = new ArrayList<MerBusRateBean>();
                for (MerBusRateDTO tempDTO : busRateDTOList) {
                    MerBusRateBean busBean = new MerBusRateBean();
                    PropertyUtils.copyProperties(busBean, tempDTO);
                    beanBusList.add(busBean);
                }
                bean.setMerBusRateBeanList(beanBusList.toArray(new MerBusRateBean[beanBusList.size()]));
            }
            //枚举为空的话
            if (state != null) {
                if (MerStateEnum.REJECT.getCode().equals(state.getCode())) {
                    //获取上级的商户信息
                    if (null != merDto.getResponseEntity().getMerParentCode()) {
                        //如果上级商户不为空的话
                        DodopalResponse<MerchantDTO> merDtoParent = merchantdelegate.findMerchantByCode(merDto.getResponseEntity().getMerParentCode());
                        bean.setMerBankName(merDtoParent.getResponseEntity().getMerName());
                        bean.setMerParentCode(merDtoParent.getResponseEntity().getMerType());
                        merbean.setCode(merDtoParent.getCode());
                    }
                } else if (MerStateEnum.NO_AUDIT.getCode().equals(state.getCode())) {
                    // TODO 未审核 城市的转换
                } else {
                    // TODO 已审核的商户详情查看
                }
                merbean.setCode(ResponseCode.SUCCESS);
            } else {
                merbean.setCode(ResponseCode.SYSTEM_ERROR);
            }
            merbean.setResponseEntity(bean);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            merbean.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }catch(HessianRuntimeException e){
            log.debug("MerchantServiceImpl call error", e);
            e.printStackTrace();
            merbean.setCode(ResponseCode.HESSIAN_ERROR);
        }
        return merbean;
    }


    /**
     * Title:审核和修改查看加载单个商户信息，用户信息，业务费率信息，商户功能信息，商户补充信息。 
     * Query:查询条件根据merCode
     * User:qiaojicheng 
     * Time:2015-05-13
     */
    public DodopalResponse<MerchantBean> findMerchantBeans(String merCode) {
        log.info("query this merCode:"+merCode);
        // 从用户系统调用加载单个商户信息，用户信息，业务费率信息，商户功能信息
        DodopalResponse<MerchantDTO> merchantDtos = merchantdelegate.findMerchants(merCode);
        log.info("users and merchant ：" + merchantDtos);
        // 前台加载单个商户信息，用户信息，业务费率信息，商户功能信息
        DodopalResponse<MerchantBean> merchantBeanRes = new DodopalResponse<MerchantBean>();
        // 商户信息
        MerchantBean merchantBeans = new MerchantBean();
        // 商户用户信息
        MerchantUserBean merchantUserBeans = new MerchantUserBean();
        //自定义商户信息
        List<MerFunctionInfoBean> merFunctionInfoBeanList = new ArrayList<MerFunctionInfoBean>();
        // 商户业务费率信息
        List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();
        //商户验签密钥信息
        MerKeyTypeBean merKeyTypeBeans = new MerKeyTypeBean();
        //商户业务信息
        List<MerRateSupplementBean> merRateSupListBean = new ArrayList<MerRateSupplementBean>();
        //商户补充信息
        MerDdpInfoBean merDdpInfoBean = new MerDdpInfoBean();
        //自动分配额度
        MerAutoAmtBean merAutoAmtBean = new MerAutoAmtBean();
        
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
                //-----------新增修改字段 2015-12-05 JOE
                // 复制商户用户信息
                if (merchantDtos.getResponseEntity().getMerDdpInfo()!= null) {
                    PropertyUtils.copyProperties(merDdpInfoBean, merchantDtos.getResponseEntity().getMerDdpInfo());
                }
                merchantBeans.setMerDdpInfoBean(merDdpInfoBean);
                
                //自动分配额度
                if(merchantDtos.getResponseEntity().getMerAutoAmtDTO()!= null){
                    PropertyUtils.copyProperties(merAutoAmtBean, merchantDtos.getResponseEntity().getMerAutoAmtDTO());
                }
                merchantBeans.setMerAutoAmtBean(merAutoAmtBean);
                //复制自定义商户信息 
                List<MerFunctionInfoDTO> merDefineFunList =merchantDtos.getResponseEntity().getMerFunInfoList();
                if(CollectionUtils.isNotEmpty(merDefineFunList)){
                  for(MerFunctionInfoDTO  merFunctionInfoDTO : merDefineFunList ){
                      MerFunctionInfoBean merFunctionInfoBean = new MerFunctionInfoBean();
                      merFunctionInfoBean.setActivate(merFunctionInfoDTO.getActivate());
                      merFunctionInfoBean.setMerFunCode(merFunctionInfoDTO.getMerFunCode());
                      merFunctionInfoBean.setParentCode(merFunctionInfoDTO.getMerFunCode());
                      merFunctionInfoBean.setDescription(merFunctionInfoDTO.getDescription());
                      merFunctionInfoBean.setType(merFunctionInfoDTO.getType());
                      merFunctionInfoBean.setVisibility(merFunctionInfoDTO.getVisibility());
                      merFunctionInfoBean.setLevels(merFunctionInfoDTO.getLevels());
                      merFunctionInfoBean.setPosition(merFunctionInfoDTO.getPosition());
                      merFunctionInfoBean.setChecked(true);
                      merFunctionInfoBeanList.add(merFunctionInfoBean);
                  }
                }
                merchantBeans.setMerFunctionInfoBeanList(merFunctionInfoBeanList);
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
                                if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(tempDTO.getRateType())){
                                        busBean.setRate(tempDTO.getRate()/100);
                                }else if(RateTypeEnum.PERMILLAGE.getCode().equals(tempDTO.getRateType())){
                                       busBean.setRate(tempDTO.getRate());  
                                }
                                busBean.setRateName(productBeanTemp.getRateName());
                                busBean.setChecked(true);
                            }else if(RateCodeEnum.IC_LOAD.getCode().equals(tempDTO.getRateCode()) && tempDTO.getProviderCode().equals(productBeanTemp.getProCode())){
                                PropertyUtils.copyProperties(busBean, tempDTO);
                                busBean.setProCode(tempDTO.getProviderCode());
                                busBean.setProName(productBeanTemp.getProName());
                                busBean.setRateType(tempDTO.getRateType());
                                if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(tempDTO.getRateType())){
                                        busBean.setRate(tempDTO.getRate()/100);
                                }else if(RateTypeEnum.PERMILLAGE.getCode().equals(tempDTO.getRateType())){
                                       busBean.setRate(tempDTO.getRate());  
                                }
                                busBean.setRateName(RateCodeEnum.IC_LOAD.getName());
                                busBean.setChecked(true);
                            }
                        }
                        merBusRateBeanList.add(busBean);
                    }
                } 
                merchantBeans.setMerBusRateBeanList(merBusRateBeanList.toArray(new MerBusRateBean[merBusRateBeanList.size()]));
                //获取商户验签密钥信息
                if (merchantDtos.getResponseEntity().getMerKeyTypeDTO() != null) {
                    PropertyUtils.copyProperties(merKeyTypeBeans, merchantDtos.getResponseEntity().getMerKeyTypeDTO());
                }
                merchantBeans.setMerKeyTypeBean(merKeyTypeBeans);
               
                //获取商户业务编码信息
                if(merchantDtos.getResponseEntity().getMerRateSpmtList()!=null){
                    for(MerRateSupplementDTO merRateSupplementDTO : merchantDtos.getResponseEntity().getMerRateSpmtList()){
                        MerRateSupplementBean merRateSupplementBean  = new MerRateSupplementBean();
                        merRateSupplementBean.setId(merRateSupplementDTO.getId());
                        merRateSupplementBean.setMerCode(merRateSupplementDTO.getMerCode());
                        merRateSupplementBean.setRateCode(merRateSupplementDTO.getRateCode());
                        merRateSupplementBean.setPageCallbackUrl(merRateSupplementDTO.getPageCallbackUrl());
                        merRateSupplementBean.setServiceNoticeUrl(merRateSupplementDTO.getServiceNoticeUrl());
                        merRateSupplementBean.setPageCheckCallbackUrl(merRateSupplementDTO.getPageCheckCallbackUrl());
                        merRateSupplementBean.setIsShowErrorMsg(merRateSupplementDTO.getIsShowErrorMsg());
                        merRateSupListBean.add(merRateSupplementBean);
                    }
                    
                }
                merchantBeans.setMerRateSpmtList(merRateSupListBean);
                merchantBeanRes.setCode(merchantDtos.getCode());
                merchantBeanRes.setResponseEntity(merchantBeans);
            } else {
                merchantBeanRes.setCode(merchantDtos.getCode());
            }

        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            merchantBeanRes.setCode(merchantDtos.getCode());
        }

        return merchantBeanRes;
    }

   /**
    * Title: 已审核商户信息时勾选的编辑信息，未审核商户信息的审核信息
    * Name: JOE
    * Time:2015-12-05
    * UPTime: 2015-12 -05 16:48
    * UPContont: 修改用户信息和商户补充信息
    */
    @Override
    public DodopalResponse<String> updateMerchantUserBusRate(MerchantBean merchantBean) {
        log.info("进入编辑或者审核的方法---------updateMerchantUserBusRate------------");
        log.info("修改参数:" + merchantBean);
        DodopalResponse<String> merssageCode = new DodopalResponse<String>();
        //商户信息
        MerchantDTO merchantDto = new MerchantDTO();
        //商户用户信息表
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        //商户验签密钥信息
        MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
        //自动分配额度
        MerAutoAmtDTO merAutoAmtDTO = new MerAutoAmtDTO();
        //商户补充信息
        MerDdpInfoDTO merDdpInfoDTO = new MerDdpInfoDTO();
        //商户用户信息
        MerchantUserBean merchantUserBean = merchantBean.getMerchantUserBean();
        //商户补充信息表
        MerDdpInfoBean merDdpInfoBean = merchantBean.getMerDdpInfoBean();
        //自动分配额度
        MerAutoAmtBean merAutoAmtBean = merchantBean.getMerAutoAmtBean();
        
        //-----------商户补充信息表   2015-12-05  JOE 新增字段-------------------
        if(merDdpInfoBean!=null){
            merchantUserDTO.setTradeArea(merDdpInfoBean.getTradeArea());//商圈  
            merDdpInfoDTO.setMerDdpLinkIp(merDdpInfoBean.getMerDdpLinkIp());
            merDdpInfoDTO.setMerDdpLinkUser(merDdpInfoBean.getMerDdpLinkUser());
            merDdpInfoDTO.setMerDdpLinkUserMobile(merDdpInfoBean.getMerDdpLinkUserMobile());
            merDdpInfoDTO.setSettlementType(merDdpInfoBean.getSettlementType());
            merDdpInfoDTO.setSettlementThreshold(merDdpInfoBean.getSettlementThreshold());
            merDdpInfoDTO.setTradeArea(merDdpInfoBean.getTradeArea());
            if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merchantBean.getMerType())  || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merchantBean.getMerType())){
                merDdpInfoDTO.setIsAutoDistribute(merDdpInfoBean.getIsAutoDistribute());
              //自动分配额度
                if(IsAutoDistributeEnum.IS_AUTO_DISTRIBUTE.getCode().equals(merDdpInfoBean.getIsAutoDistribute())){
                   // merAutoAmtDTO.setMerCode(merchantBean.getMerCode());
                    if(merAutoAmtBean!=null){
                        merAutoAmtDTO.setLimitThreshold(merAutoAmtBean.getLimitThreshold());
                        merAutoAmtDTO.setAutoLimitThreshold(merAutoAmtBean.getAutoLimitThreshold());
                        merchantDto.setMerAutoAmtDTO(merAutoAmtDTO);
                    }
                 
                }
            }
//            if(MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merchantBean.getMerType())){
//                merDdpInfoDTO.setLimitSource(merDdpInfoBean.getLimitSource());
//            }
            
        }
        merchantDto.setMerDdpInfo(merDdpInfoDTO);
        
        merchantUserDTO.setMerUserName(merchantUserBean.getMerUserName());
        merchantUserDTO.setMerUserNickName(merchantUserBean.getMerUserNickName());
        merchantUserDTO.setMerUserSex(merchantUserBean.getMerUserSex());
        merchantUserDTO.setMerUserTelephone(merchantUserBean.getMerUserTelephone());
        merchantUserDTO.setMerUserIdentityType(merchantUserBean.getMerUserIdentityType());
        merchantUserDTO.setMerUserIdentityNumber(merchantUserBean.getMerUserIdentityNumber());
        merchantUserDTO.setMerUserEmail(merchantUserBean.getMerUserEmail());
        merchantUserDTO.setMerUserRemark(merchantUserBean.getMerUserRemark());
        merchantUserDTO.setMerUserMobile(merchantUserBean.getMerUserMobile());
        merchantUserDTO.setMerUserAdds(merchantUserBean.getMerUserAdds());
        merchantUserDTO.setUserCode(merchantUserBean.getUserCode());
        merchantUserDTO.setUpdateUser(merchantBean.getCreateUser());
        merchantUserDTO.setMerUserSource(SourceEnum.OSS.getCode());
        merchantUserDTO.setUpdateUser(merchantBean.getCreateUser());
        //新增字段 -------------2015-12-05 JOE -----------------
        merchantUserDTO.setEducation(merchantUserBean.getEducation());//学历
        merchantUserDTO.setIncome(merchantUserBean.getIncome());//收入
        merchantUserDTO.setBirthday(merchantUserBean.getBirthday());//'生日，格式：1990-09-10
        merchantUserDTO.setIsMarried(merchantUserBean.getIsMarried());//是否已婚，0是，1否
        //新增字段 -------------2016-01-19 JOE -----------------
        merchantUserDTO.setMerUserPro(merchantUserBean.getMerUserPro());//省份
        merchantUserDTO.setMerUserCity(merchantUserBean.getMerUserCity());//城市
        
        merchantDto.setMerchantUserDTO(merchantUserDTO);
         // 商户信息
        merchantDto.setMerName(merchantBean.getMerName());
        merchantDto.setMerType(merchantBean.getMerType());
        merchantDto.setActivate(merchantBean.getActivate());
        merchantDto.setMerClassify(merchantBean.getMerClassify());
        merchantDto.setMerProperty(merchantBean.getMerProperty());
        merchantDto.setMerState(merchantBean.getMerState());
        merchantDto.setMerLinkUser(merchantBean.getMerLinkUser());
        merchantDto.setMerLinkUserMobile(merchantUserBean.getMerUserMobile());
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
        merchantDto.setMerCode(merchantBean.getMerCode());
        merchantDto.setUpdateUser(merchantBean.getCreateUser());
        merchantDto.setMerStateUser(merchantBean.getCreateUser());//审核人
        merchantDto.setMerParentCode(merchantBean.getMerParentCode());
        merchantDto.setMerTelephone(merchantUserBean.getMerUserTelephone());
        merchantDto.setMerEmail(merchantUserBean.getMerUserEmail());
        merchantDto.setMerRejectReason(merchantBean.getMerRejectReason());
        //资金类型
        merchantDto.setFundType(merchantBean.getFundType());
       //商户打款人
        merchantDto.setMerPayMoneyUser(merchantBean.getMerPayMoneyUser());
        //业务费率表
        MerBusRateBean[] merBusRateBeanList = merchantBean.getMerBusRateBeanList();
        List<MerBusRateDTO> merBusRateList = new ArrayList<MerBusRateDTO>();
        if (merBusRateBeanList != null) {
            for (MerBusRateBean merBusRateBeanTemp : merBusRateBeanList) {
                MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
                merBusRateDTOTemp.setMerCode(merBusRateBeanTemp.getMerCode());
                merBusRateDTOTemp.setRateCode(merBusRateBeanTemp.getRateCode());
                merBusRateDTOTemp.setActivate(merBusRateBeanTemp.getActivate());
                merBusRateDTOTemp.setProviderCode(merBusRateBeanTemp.getProCode());
                if (RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merBusRateBeanTemp.getRateType())) {
                    if ((int)(merBusRateBeanTemp.getRate() * 100) <=99999) {
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate() * 100);
                    } else {
                        merssageCode.setCode(ResponseCode.OSS_MER_SINGLE_AMOUNT_ERROR);
                        return merssageCode;
                    }
                } else if (RateTypeEnum.PERMILLAGE.getCode().equals(merBusRateBeanTemp.getRateType())) {
                    if ((int)(merBusRateBeanTemp.getRate() * 100) <=99999) {
                        merBusRateDTOTemp.setRate(merBusRateBeanTemp.getRate());
                    } else {
                        merssageCode.setCode(ResponseCode.OSS_MER_PERMILLAGE_ERROR);
                        return merssageCode;
                    }
                }
                merBusRateDTOTemp.setRateType(merBusRateBeanTemp.getRateType());
                merBusRateList.add(merBusRateDTOTemp);
            }
            merchantDto.setMerBusRateList(merBusRateList);
        }

        // 自定义功能商户
        MerFunctionInfoBean[] merFunctionInfoBeanList = merchantBean.getMerDefineFunBeanList();
        if (merFunctionInfoBeanList != null) {
            List<MerFunctionInfoDTO> merFunctionInfoList = new ArrayList<MerFunctionInfoDTO>();
            for (MerFunctionInfoBean merFunctionInfoBeanTemp : merFunctionInfoBeanList) {
                MerFunctionInfoDTO merFunctionInfoDTOTemp = new MerFunctionInfoDTO();
                merFunctionInfoDTOTemp.setMerFunCode(merFunctionInfoBeanTemp.getMerFunCode());
                merFunctionInfoDTOTemp.setMerFunName(merFunctionInfoBeanTemp.getMerFunName());
                merFunctionInfoList.add(merFunctionInfoDTOTemp);
            }
            merchantDto.setMerDefineFunList(merFunctionInfoList);
        }
        
        //商户签名密钥
        MerKeyTypeBean merKeyTypeBean = merchantBean.getMerKeyTypeBean();
        if (merKeyTypeBean != null) {
            merKeyTypeDTO.setMerCode(merchantBean.getMerCode());
            merKeyTypeDTO.setMerMD5PayPwd(merKeyTypeBean.getMerMD5PayPwd());
            merKeyTypeDTO.setMerMD5BackPayPWD(merKeyTypeBean.getMerMD5BackPayPWD());
            merchantDto.setMerKeyTypeDTO(merKeyTypeDTO);
        }

        
        //保存当前业务信息表
        List<MerRateSupplementBean> merRateSupplementBeanList = merchantBean.getMerRateSpmtList();
		if (CollectionUtils.isNotEmpty(merRateSupplementBeanList)) {
			List<MerRateSupplementDTO> merRateSupplementDTOList = new ArrayList<MerRateSupplementDTO>();
			for (MerRateSupplementBean merRateSupplementBeanTemp : merRateSupplementBeanList) {
				MerRateSupplementDTO merRateSupplementDTOTemp = new MerRateSupplementDTO();
				merRateSupplementDTOTemp.setId(merRateSupplementBeanTemp.getId());
				merRateSupplementDTOTemp.setMerCode(merRateSupplementBeanTemp.getMerCode());
				merRateSupplementDTOTemp.setPageCallbackUrl(merRateSupplementBeanTemp.getPageCallbackUrl());
				merRateSupplementDTOTemp.setRateCode(merRateSupplementBeanTemp.getRateCode());
				merRateSupplementDTOTemp.setServiceNoticeUrl(merRateSupplementBeanTemp.getServiceNoticeUrl());
				merRateSupplementDTOTemp.setPageCheckCallbackUrl(merRateSupplementBeanTemp.getPageCheckCallbackUrl());
				merRateSupplementDTOTemp.setUniqueIdentification(merRateSupplementBeanTemp.getUniqueIdentification());
				merRateSupplementDTOTemp.setCreateUser(merchantBean.getCreateUser());
				merRateSupplementDTOTemp.setCreateDate(merRateSupplementBeanTemp.getCreateDate());
				merRateSupplementDTOTemp.setUpdateUser(merchantBean.getCreateUser());
				merRateSupplementDTOTemp.setUpdateDate(merRateSupplementBeanTemp.getUpdateDate());
				merRateSupplementDTOTemp.setIsShowErrorMsg(merRateSupplementBeanTemp.getIsShowErrorMsg());
				merRateSupplementDTOList.add(merRateSupplementDTOTemp);
			}
			merchantDto.setMerRateSpmtList(merRateSupplementDTOList);
		}
        merssageCode = merchantdelegate.updateMerchant(merchantDto);
        log.info("update verified and unverify end:" + merssageCode);
        formatResponseMessage(merssageCode);
        return merssageCode;
    }

    /**
     * 查询自定义商户功能信息
     */
    @Override
    public DodopalResponse<List<TreeNode>> findMerFuncitonInfoList(String merchantType, String param, String merCode) {
        //全部功能
        DodopalResponse<List<MerFunctionInfoDTO>> merFunctionInfoList = merchantdelegate.findMerFuncitonInfoList(merchantType);
        DodopalResponse<List<TreeNode>> resResult = new DodopalResponse<List<TreeNode>>();
        List<MerFunctionInfoDTO> merTypeList = null;

        if (StringUtils.isBlank(merCode)) {
            DodopalResponse<List<MerFunctionInfoDTO>> merTypeListResponse = merchantdelegate.findMerTypeFunList(merchantType);
            if (ResponseCode.SUCCESS.equals(merTypeListResponse.getCode())) {
                merTypeList = merTypeListResponse.getResponseEntity();
            }
        } else {
            DodopalResponse<MerchantDTO> curMerResponse = merchantdelegate.findMerchantByCode(merCode);
            if (ResponseCode.SUCCESS.equals(curMerResponse.getCode())) {
                MerchantDTO curMer = curMerResponse.getResponseEntity();
                if (curMer != null) {
                    String merProperty = curMer.getMerProperty();
                    if (MerPropertyEnum.CUSTOM.getCode().equals(merProperty)) {
                        merTypeList = curMer.getMerDefineFunList();
                    } else {
                        DodopalResponse<List<MerFunctionInfoDTO>> merTypeListResponse = merchantdelegate.findMerTypeFunList(merchantType);
                        if (ResponseCode.SUCCESS.equals(merTypeListResponse.getCode())) {
                            merTypeList = merTypeListResponse.getResponseEntity();
                        }
                    }
                }
            }
        }

        //模板功能
        if (merFunctionInfoList.getResponseEntity() != null && ResponseCode.SUCCESS.equals(merFunctionInfoList.getCode())) {
            List<MerFunctionInfoBean> merchantBeanList = new ArrayList<MerFunctionInfoBean>();
            for (MerFunctionInfoDTO merFunctionInfoDTO : merFunctionInfoList.getResponseEntity()) {
                MerFunctionInfoBean merFunctionInfoBean = new MerFunctionInfoBean();
                merFunctionInfoBean.setMerFunName(merFunctionInfoDTO.getMerFunName());
                merFunctionInfoBean.setMerFunCode(merFunctionInfoDTO.getMerFunCode());
                merFunctionInfoBean.setParentCode(merFunctionInfoDTO.getParentCode());
                merFunctionInfoBean.setType(merFunctionInfoDTO.getType());
                merFunctionInfoBean.setActivate(merFunctionInfoDTO.getActivate());
                merFunctionInfoBean.setDescription(merFunctionInfoDTO.getDescription());
                if (CollectionUtils.isNotEmpty(merTypeList)) {
                    for (MerFunctionInfoDTO merTypeDto : merTypeList) {
                        if (merTypeDto.getMerFunCode().equals(merFunctionInfoBean.getMerFunCode())) {
                            merFunctionInfoBean.setChecked(true);
                            break;
                        }
                    }
                } else {
                    merFunctionInfoBean.setChecked(false);
                }
                merchantBeanList.add(merFunctionInfoBean);
            }
            
            if (CollectionUtils.isNotEmpty(merchantBeanList)) {
                if (StringUtils.isNotBlank(param)) {
                    String[] params = StringUtils.split(param, '|');
                    if (params.length > 0) {
                        for (MerFunctionInfoBean bean : merchantBeanList) {
                            boolean isFound = false;
                            for (String temp : params) {
                                if (bean.getMerFunCode().equals(temp)) {
                                    bean.setChecked(true);
                                    isFound = true;
                                    break;
                                }
                            }
                            if (!isFound) {
                                bean.setChecked(false);
                            }
                        }
                    }
                }
            }

            // 
            List<MerFunctionInfoBean> parents = findParents(merchantBeanList);
            for (MerFunctionInfoBean parent : parents) {
                buildTree(merchantBeanList, parent);
            }
            List<TreeNode> tree = createTreeNode(parents);

            resResult.setCode(merFunctionInfoList.getCode());
            resResult.setResponseEntity(tree);
        } else {
            resResult.setCode(merFunctionInfoList.getCode());
        }
        return resResult;
    }

    
    private List<MerFunctionInfoBean> findParents(List<MerFunctionInfoBean> merchantBeanList) {
        List<MerFunctionInfoBean> parents = new ArrayList<MerFunctionInfoBean>();
        for (MerFunctionInfoBean bean : merchantBeanList) {
            if (DDPStringUtil.isNotPopulated(bean.getParentCode())) {
                parents.add(bean);
            }
        }
        return parents;
    }
    
    private void buildTree(List<MerFunctionInfoBean> merchantBeanList, MerFunctionInfoBean parent) {
        List<MerFunctionInfoBean> childs = getChildsByParentId(merchantBeanList, parent);
        if (!childs.isEmpty()) {
            for (MerFunctionInfoBean child : childs) {
                buildTree(merchantBeanList, child);
            }
        }
    }
    
    private List<MerFunctionInfoBean> getChildsByParentId(List<MerFunctionInfoBean> merchantBeanList, MerFunctionInfoBean parent) {
        List<MerFunctionInfoBean> childs = new ArrayList<MerFunctionInfoBean>();
        if (parent != null) {
            for (MerFunctionInfoBean bean : merchantBeanList) {
                if (bean != null && DDPStringUtil.isPopulated(bean.getParentCode()) && bean.getParentCode().equals(parent.getMerFunCode())) {
                    bean.setParent(parent);
                    childs.add(bean);
                }
            }
            parent.setChildren(childs);
        }
        return childs;
    }

    private List<TreeNode> createTreeNode(List<MerFunctionInfoBean> merchantBeanList) {
        List<TreeNode> trunkNodes = new ArrayList<TreeNode>();
        for (MerFunctionInfoBean bean : merchantBeanList) {
            TreeNode node = new TreeNode();
            node.setId(bean.getMerFunCode());
            node.setText(bean.getMerFunName());
            node.setIconCls("icon-blank");
            if (bean.getChildren() != null && !bean.getChildren().isEmpty()) {
                node.setChildren(createTreeNode(bean.getChildren()).toArray(new TreeNode[0]));
                node.setLeaf(false);
            } else {
                node.setLeaf(true);
                node.setChecked(bean.isChecked());
            }
            trunkNodes.add(node);
        }
        return trunkNodes;
    }
    /**
     * TODO:产品库接口上来就改掉
     * TIME:2015-07-19
     * 加载已选择的一卡通信息
     */
    @Override
    public DodopalResponse<List<MerBusRateBean>> loadYktInfo(String[] productCode,String merParentCode, String rateCode, String merType) {
        DodopalResponse<List<MerBusRateDTO>> roductBeans = merchantdelegate.findMerBusRateByMerCode(merParentCode);
        DodopalResponse<List<MerBusRateBean>> resResult = new DodopalResponse<List<MerBusRateBean>>();
        List<MerBusRateDTO> merBusRateList  = roductBeans.getResponseEntity();
        
        List<MerBusRateBean> result = new ArrayList<MerBusRateBean>();
        RateCodeEnum rateCodeEnum = RateCodeEnum.getRateTypeByCode(rateCode);
		if (rateCodeEnum == null) {
			rateCodeEnum = RateCodeEnum.YKT_RECHARGE;
		}
        List<MerBusRateBean> mockData = getYktInfoByRateCode(rateCodeEnum);
        for (MerBusRateBean mock : mockData) {
            for (String code : productCode) {
                if(RateCodeEnum.YKT_RECHARGE == rateCodeEnum) {
                	if(merBusRateList!=null&&merBusRateList.size()!=0){
                        for(MerBusRateDTO merBusRateDTO: merBusRateList){
                            MerBusRateBean productBean = new MerBusRateBean();
                            if (mock.getProCode().equals(code)) {
                                if(merBusRateDTO.getProviderCode().equals(mock.getProCode()) && merBusRateDTO.getRateCode().equals(mock.getRateCode())){
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
                    }else{
                          if (mock.getProCode().equals(code)) {
                                mock.setChecked(true);
                                result.add(mock);
                        }
                    }
                } else if(RateCodeEnum.YKT_PAYMENT == rateCodeEnum) {
                	if (mock.getProCode().equals(code)) {
                			MerBusRateBean productBean = new MerBusRateBean();
                            productBean.setProCode(mock.getProCode());
                            productBean.setProName(mock.getProName());
                            productBean.setRateCode(mock.getRateCode());
                            productBean.setRateName(mock.getRateName());
                            productBean.setChecked(true);
                            result.add(productBean);
                    }
                }
               
              
            }
        }
        resResult.setCode(ResponseCode.SUCCESS);
        resResult.setResponseEntity(result);
        return resResult;
    }
    
    
    /**
     * 根据上级商户名称查看通卡公司信息
     */
    @Override
    public DodopalResponse<List<MerBusRateBean>> findProductList(String merParentCode, String rateCode, String merType) {
        DodopalResponse<List<MerBusRateDTO>> roductBeans = merchantdelegate.findMerBusRateByMerCode(merParentCode);
        List<MerBusRateDTO> merBusRateList  = roductBeans.getResponseEntity();
        RateCodeEnum rateCodeEnum = RateCodeEnum.getRateTypeByCode(rateCode);
		if (rateCodeEnum == null) {
			rateCodeEnum = RateCodeEnum.YKT_RECHARGE;
		}
        List<MerBusRateBean> mockData = getYktInfoByRateCode(rateCodeEnum);
        DodopalResponse<List<MerBusRateBean>> resResult = new DodopalResponse<List<MerBusRateBean>>();
        List<MerBusRateBean> proBeanList =new ArrayList<MerBusRateBean>();
       
        if(roductBeans.getResponseEntity()!=null){
            //判断有上级商户的费率信息
            for(MerBusRateDTO merBusRateTemp : merBusRateList){
                for(MerBusRateBean mock : mockData){
                    if(merBusRateTemp.getProviderCode().equals(mock.getProCode()) && merBusRateTemp.getRateCode().equals(mock.getRateCode())){
                    	if(RateCodeEnum.YKT_RECHARGE == rateCodeEnum 
                    			|| (RateCodeEnum.YKT_PAYMENT == rateCodeEnum && MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType))) {
                    		//商户CODE
                    		MerBusRateBean productBean = new MerBusRateBean();
                            productBean.setMerCode(merBusRateTemp.getMerCode());
                            productBean.setProCode(merBusRateTemp.getProviderCode());
                            productBean.setProName(mock.getProName());
                            productBean.setRateCode(merBusRateTemp.getRateCode());
                            productBean.setRateName(mock.getRateName());
                            productBean.setRateType(merBusRateTemp.getRateType());
                            if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(merBusRateTemp.getRateType())){
                                productBean.setRate(merBusRateTemp.getRate()/100); 
                            }else{
                                productBean.setRate(merBusRateTemp.getRate()); 
                            }
                            productBean.setChecked(true);
                            proBeanList.add(productBean);
                            break;
                    	}
                    }
                }
            }

            if(RateCodeEnum.YKT_PAYMENT == rateCodeEnum && !MerTypeEnum.AGENT.getCode().equals(merType) && !MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
            	for(MerBusRateBean mock : mockData){
            		MerBusRateBean productBean = new MerBusRateBean();
                    productBean.setProCode(mock.getProCode());
                    productBean.setProName(mock.getProName());
                    productBean.setRateCode(mock.getRateCode());
                    productBean.setRateName(mock.getRateName());
                    productBean.setChecked(false);
                    proBeanList.add(productBean);
            	}
            }
        }
       
        resResult.setCode(ResponseCode.SUCCESS);
        resResult.setResponseEntity(proBeanList);
        return resResult;
    }
    /***
     * 根据商户CODE查询全部的通卡公司名称并且勾选住
     * TODO 后期产品库上来后改掉
     */
    @Override
    public DodopalResponse<List<MerBusRateBean>> findMerProductList(String merCode, String rateCode) {
        DodopalResponse<List<MerBusRateDTO>> roductBeans = merchantdelegate.findMerBusRateByMerCode(merCode);
        List<MerBusRateDTO> merBusRateList  = roductBeans.getResponseEntity();
		RateCodeEnum rateCodeEnum = RateCodeEnum.getRateTypeByCode(rateCode);
		if (rateCodeEnum == null) {
			rateCodeEnum = RateCodeEnum.YKT_RECHARGE;
		}
		List<MerBusRateBean> mockData = getYktInfoByRateCode(rateCodeEnum);
        DodopalResponse<List<MerBusRateBean>> resResult = new DodopalResponse<List<MerBusRateBean>>();
        List<MerBusRateBean> proBeanList =new ArrayList<MerBusRateBean>();
        if(roductBeans.getResponseEntity()!=null){
            //通卡公司勾选费率
            for(MerBusRateBean mock : mockData){
                MerBusRateBean merBean = new MerBusRateBean();
                for(MerBusRateDTO merBusRateTemp : merBusRateList){
                    if(mock.getProCode().equals(merBusRateTemp.getProviderCode())){
                        merBean.setChecked(true);
                    }
                }
                merBean.setProCode(mock.getProCode());
                merBean.setProName(mock.getProName());
                merBean.setRateCode(mock.getRateCode());
                merBean.setRateName(mock.getRateName());
                merBean.setRate(mock.getRate());
                merBean.setRateType(mock.getRateType());
                proBeanList.add(merBean);
            }
        }else{
            for(MerBusRateBean mock : mockData){
                MerBusRateBean merBean = new MerBusRateBean();
                merBean.setProCode(mock.getProCode());
                merBean.setProName(mock.getProName());
                merBean.setRateCode(mock.getRateCode());
                merBean.setRateName(mock.getRateName());
                merBean.setRate(mock.getRate());
                merBean.setRateType(mock.getRateType());
                merBean.setChecked(false);
                proBeanList.add(merBean);
            }
        }
        resResult.setCode(ResponseCode.SUCCESS);
        resResult.setResponseEntity(proBeanList);
        return resResult;
    }


    /**
     * 加载通卡公司数据
     * @return
     */
    private List<MerBusRateBean> mockData() {
        List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();
        DodopalResponse<List<ProductYKTDTO>> responseYktDto =  merchantdelegate.yktData();
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
//            //业务类型为一卡通支付
//            if(proDuctYKTMock.getYktIsPay().equals("0")){
//                merBusRateBean.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
//                merBusRateBean.setRateName(RateCodeEnum.YKT_PAYMENT.getName());
//            }
           
        }
        return merBusRateBeanList;
    }

    /**
     * 根据业务类型加载通卡公司数据
     * @return
     */
	private List<MerBusRateBean> getYktInfoByRateCode(RateCodeEnum rateCode) {
		List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();
		DodopalResponse<List<ProductYKTDTO>> responseYktDto = merchantdelegate.yktData();
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


    /**
     * Time:2015-05-31 17:00 
     * Title:商户列表信息查询-后台分页,查詢上級商戶名稱
     * Name:Joe
     * Email:qiaojicheng@dodopal.com
     */
    @Override
    public DodopalResponse<DodopalDataPage<MerchantBean>> findMerchantBeanByPage(MerchantQuery merchantQuery) {
        log.info("初始化和点击查询商户信息列表"+merchantQuery);
        DodopalResponse<DodopalDataPage<MerchantBean>> response = new DodopalResponse<DodopalDataPage<MerchantBean>>();
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
        
        //分页参数
        if (merchantQuery.getPage() != null) {
            merchantDto.setPage(merchantQuery.getPage());
        }
        // 查询列表信息
        DodopalResponse<DodopalDataPage<MerchantDTO>> merchantDtoList = merchantdelegate.findMerchantListByPage(merchantDto);
        List<MerchantBean> merchantBeanList = new ArrayList<MerchantBean>();
        if (ResponseCode.SUCCESS.equals(merchantDtoList.getCode())) {
            if (merchantDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(merchantDtoList.getResponseEntity().getRecords())) {
                for (MerchantDTO merDTO : merchantDtoList.getResponseEntity().getRecords()) {
                    MerchantBean merBean = new MerchantBean();
                    merBean.setId(merDTO.getId());
                    merBean.setMerName(merDTO.getMerName());
                    merBean.setMerCode(merDTO.getMerCode());
                    merBean.setMerClassify(merDTO.getMerClassify());
                    merBean.setMerProperty(merDTO.getMerProperty());
                    merBean.setMerType(merDTO.getMerType());
                    merBean.setMerLinkUserMobile(merDTO.getMerLinkUserMobile());
                    merBean.setMerPro(merDTO.getMerPro());
                    merBean.setMerCity(merDTO.getMerCity());
                    merBean.setSource(merDTO.getSource());
                    merBean.setActivate(merDTO.getActivate());
                    merBean.setMerCityName(merDTO.getMerCityName());
                    merBean.setMerParentName(merDTO.getMerParentName());
                    merBean.setMerProName(merDTO.getMerProName());
                    merBean.setMerLinkUser(merDTO.getMerLinkUser());
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

    /**
     * 根据商户号查找商户名称
     */
    @Override
    public String findMerchantNameByMerchantCode(String code) {
        if (DDPStringUtil.isPopulated(code)) {
            DodopalResponse<MerchantDTO> result = merchantdelegate.findMerchantByCode(code);
            if (ResponseCode.SUCCESS.equals(result.getCode())) {
                MerchantDTO merchant = result.getResponseEntity();
                if (merchant != null) {
                    return merchant.getMerName();
                }
            }
        }
        return null;
    }
    /**
     *Time:2015-06-06 13:42
     *TODO:后期跟进产品库需求需改动
     *Title:查询通卡公司名称
     */
    @Override
    public DodopalResponse<List<ProductBean>> findProductNames(ProductBean productBean) {
           DodopalResponse<List<ProductBean>> response = new DodopalResponse<List<ProductBean>>();
           ProductIcdc productIcdc =new ProductIcdc();
           productIcdc.setProCode(productBean.getProCode());
           List<ProductBean> proList =new ArrayList<ProductBean>();
          List<ProductIcdc> proicdcList = productIcdcMapper.findProductIcdcNames(productIcdc);
           if(proicdcList!=null){
               for(ProductIcdc pros : proicdcList){
                   ProductBean productBeans = new ProductBean();
                   productBeans.setProCode(pros.getProCode());
                   productBeans.setProName(pros.getProName());
                   proList.add(productBeans);
               }
               
           }
           response.setResponseEntity(proList);
           return response;
    }

    /**
     *Time:2015-06-06 14:10
     *Titile:通過通卡公司的productCode讀取到通卡公司的業務信息
     *TODO:后期跟进产品库需求需改动
     * @param productCode
     * @return
     */
    @Override
    public DodopalResponse<List<ProductBean>> findProductList(String[] productCode) {
        DodopalResponse<List<ProductBean>> response = new DodopalResponse<List<ProductBean>>();
        List<ProductBean> proBeanList = new ArrayList<ProductBean>();
        List<ProductIcdc> proList = productIcdcMapper.findProductIcdcbByCode(productCode);
        if(proList!=null){
            for(ProductIcdc productIcdc :proList){
                ProductBean productBean = new ProductBean();
                productBean.setProCode(productIcdc.getProCode());
                productBean.setProName(productIcdc.getProName());
                productBean.setRateCode(productIcdc.getRateCode());
                productBean.setRateName(productIcdc.getProName());
                proBeanList.add(productBean);
            }
        }
        response.setResponseEntity(proBeanList);
        return response;
    }

    @Override
    public DodopalResponse<Integer> delNotverifMerchants(List<String> merCodes,String upUser) {
        log.info("delete notverified this merCodes"+merCodes);
        DodopalResponse<Integer> delNotNumber = merchantdelegate.delNotverified(merCodes,upUser);
        return delNotNumber;
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
    public DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(MerchantBean merchantBean) {
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerCode(merchantBean.getMerCode());

        MerBusRateBean[] busRateBeans = merchantBean.getMerBusRateBeanList();
        if (busRateBeans != null && busRateBeans.length > 0) {
            List<MerBusRateDTO> merBusRateList = new ArrayList<MerBusRateDTO>(busRateBeans.length);
            for (MerBusRateBean busRateBean : busRateBeans) {
                MerBusRateDTO busRateDTO = new MerBusRateDTO();
                busRateDTO.setMerCode(merchantBean.getMerCode());
                busRateDTO.setProviderCode(busRateBean.getProCode());
                busRateDTO.setRateCode(busRateBean.getRateCode());
                busRateDTO.setRateType(busRateBean.getRateType());
                busRateDTO.setRate(busRateBean.getRate());
                merBusRateList.add(busRateDTO);
            }
            merchantDTO.setMerBusRateList(merBusRateList);
        }
        return merchantdelegate.checkRelationMerchantProviderAndRateType(merchantDTO);
    }
    /******************************************链接产品库查询通卡公司名称开始*********************************/
    @Override
    public DodopalResponse<List<MerBusRateBean>> loadYktInfoName(String merCode) {
        //1.根据商户Code查询是否有费率存在
        DodopalResponse<List<MerBusRateDTO>> roductBeans = merchantdelegate.findMerBusRateByMerCode(merCode);
        //2.获取费率信息
        List<MerBusRateDTO> merBusRateList  = roductBeans.getResponseEntity();
        //3.查询通卡公司信息
        List<MerBusRateBean> mockData = mockData();
        DodopalResponse<List<MerBusRateBean>> resResult = new DodopalResponse<List<MerBusRateBean>>();
        List<MerBusRateBean> proBeanList =new ArrayList<MerBusRateBean>();
        if(roductBeans.getResponseEntity()!=null){
            //通卡公司勾选费率
            for(MerBusRateBean mock : mockData){
                MerBusRateBean merBean = new MerBusRateBean();
                for(MerBusRateDTO merBusRateTemp : merBusRateList){
                    if(mock.getProCode().equals(merBusRateTemp.getProviderCode())){
                        merBean.setChecked(true);
                    }
                }
                merBean.setProCode(mock.getProCode());
                merBean.setProName(mock.getProName());
                merBean.setRateCode(mock.getRateCode());
                merBean.setRateName(mock.getRateName());
                merBean.setRate(mock.getRate());
                merBean.setRateType(mock.getRateType());
                proBeanList.add(merBean);
            }
        }else{
            for(MerBusRateBean mock : mockData){
                MerBusRateBean merBean = new MerBusRateBean();
                merBean.setProCode(mock.getProCode());
                merBean.setProName(mock.getProName());
                merBean.setRateCode(mock.getRateCode());
                merBean.setRateName(mock.getRateName());
                merBean.setRate(mock.getRate());
                merBean.setRateType(mock.getRateType());
                merBean.setChecked(false);
                proBeanList.add(merBean);
            }
        }
        resResult.setCode(ResponseCode.SUCCESS);
        resResult.setResponseEntity(proBeanList);
        return resResult;
    }
    /******************************************链接产品库查询通卡公司名称结束*********************************/


    @Override
    public DodopalResponse<List<MerRateSupplementBean>> findMerRateSupplementByCode(String merCode, String merType) {
        DodopalResponse<List<MerRateSupplementDTO>> merRateSupDTOList = merchantdelegate.findMerRateSupplementsByMerCode(merCode);
        DodopalResponse<List<MerRateSupplementBean>> respone= new DodopalResponse<List<MerRateSupplementBean>>();
        List<MerRateSupplementBean> merRateSupBeanList = new ArrayList<MerRateSupplementBean>();
        // 代理商无消费业务，连锁直营与上级保持一致，其他类型商户不依赖于上级的消费业务
        for(MerRateSupplementDTO merSupTemp : merRateSupDTOList.getResponseEntity()){
        	if(RateCodeEnum.YKT_RECHARGE.getCode().equals(merSupTemp.getRateCode())) {
        		MerRateSupplementBean merSupBeanTemp = new MerRateSupplementBean();
                merSupBeanTemp.setId(merSupTemp.getId());
                merSupBeanTemp.setMerCode(merSupTemp.getMerCode());
                merSupBeanTemp.setRateCode(merSupTemp.getRateCode());
                merSupBeanTemp.setPageCallbackUrl(merSupTemp.getPageCallbackUrl());
                merSupBeanTemp.setServiceNoticeUrl(merSupTemp.getServiceNoticeUrl());
                merRateSupBeanList.add(merSupBeanTemp);
        	} else if(RateCodeEnum.YKT_PAYMENT.getCode().equals(merSupTemp.getRateCode())) {
        		if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
        			MerRateSupplementBean merSupBeanTemp = new MerRateSupplementBean();
                    merSupBeanTemp.setId(merSupTemp.getId());
                    merSupBeanTemp.setMerCode(merSupTemp.getMerCode());
                    merSupBeanTemp.setRateCode(merSupTemp.getRateCode());
                    merSupBeanTemp.setPageCallbackUrl(merSupTemp.getPageCallbackUrl());
                    merSupBeanTemp.setServiceNoticeUrl(merSupTemp.getServiceNoticeUrl());
                    merRateSupBeanList.add(merSupBeanTemp);
        		}
        	}
        }
        if(!MerTypeEnum.AGENT.getCode().equals(merType) && !MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
        	MerRateSupplementBean merSupBeanTemp = new MerRateSupplementBean();
            merSupBeanTemp.setRateCode(RateCodeEnum.YKT_PAYMENT.getCode());
            merRateSupBeanList.add(merSupBeanTemp);
        }
        respone.setCode(merRateSupDTOList.getCode());
        respone.setResponseEntity(merRateSupBeanList);
        return respone;
    }
}
