package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianConnectionException;
import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.OpenSignEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerBusRateBean;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.service.MerchantService;
import com.dodopal.portal.delegate.MerchantDelegate;

@Service
public class MerchantServiceImpl implements MerchantService {
    private final static Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    MerchantDelegate merchantdelegate;

    @Override
    public DodopalResponse<String> saveMerchantUserBusRate(MerchantBean merchantBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DodopalResponse<String> updateMerchantUserBusRate(MerchantBean merchantBean) {
        log.info("update verified and unverify start:" + merchantBean);
        MerchantDTO merchantDto = new MerchantDTO();
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();

        MerchantUserBean merchantUserBean = merchantBean.getMerchantUserBean();
        merchantUserDTO.setMerUserIdentityType(merchantUserBean.getMerUserIdentityType()!=null?merchantUserBean.getMerUserIdentityType():"");
        merchantUserDTO.setMerCode(merchantUserBean.getMerCode());
        merchantUserDTO.setMerUserIdentityNumber(merchantUserBean.getMerUserIdentityNumber());
        merchantDto.setMerchantUserDTO(merchantUserDTO);
        merchantDto.setMerCode(merchantBean.getMerCode());
        // 商户为空字段
        merchantDto.setMerLinkUser(merchantBean.getMerLinkUser());
        merchantDto.setMerAdds(merchantBean.getMerAdds());
        merchantDto.setMerBusinessScopeId(merchantBean.getMerBusinessScopeId());
        merchantDto.setMerFax(merchantBean.getMerFax());
        merchantDto.setMerBankName(merchantBean.getMerBankName());
        merchantDto.setMerBankAccount(merchantBean.getMerBankAccount());
        merchantDto.setMerBankUserName(merchantBean.getMerBankUserName());
        //TODO merchantDto.setUpdateUser(merchantBean.getCreateUser());

        DodopalResponse<String> code = merchantdelegate.updateMerchant(merchantDto);
        log.info("update verified and unverify end:" + code);
        return code;
    }

    @Override
    public DodopalResponse<Integer> startAndDisableMerchant(List<String> merCode, String activate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DodopalResponse<MerchantBean> findMerchantBeans(String merCode) {
        log.info("query this merCode:"+merCode);
        // 从用户系统调用加载单个商户信息，用户信息，业务费率信息，商户功能信息
        DodopalResponse<MerchantDTO> merchantDtos = merchantdelegate.findMerchants(merCode);
        // 前台加载单个商户信息，用户信息，业务费率信息，商户功能信息
        DodopalResponse<MerchantBean> merchantBean = new DodopalResponse<MerchantBean>();
        // 商户信息
        MerchantBean merchantBeans = new MerchantBean();
        // 商户用户信息
        MerchantUserBean merchantUserBeans = new MerchantUserBean();
        // 商户业务费率信息
        List<MerBusRateBean> merBusRateBeanList = new ArrayList<MerBusRateBean>();

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

                // 复制业务费率信息
                List<MerBusRateDTO> busRateDTOList = merchantDtos.getResponseEntity().getMerBusRateList();
                if (CollectionUtils.isNotEmpty(busRateDTOList)) {
                    for (MerBusRateDTO tempDTO : busRateDTOList) {
                        MerBusRateBean busBean = new MerBusRateBean();
                        PropertyUtils.copyProperties(busBean, tempDTO);
                        busBean.setProCode(tempDTO.getProviderCode());
                        busBean.setProName(tempDTO.getProviderCode()); 
                        busBean.setRateType(tempDTO.getRateType());
                        if(RateTypeEnum.SINGLE_AMOUNT.getCode().equals(tempDTO.getRateType())){
                            busBean.setRate(tempDTO.getRate()/100);   
                        }else{
                            busBean.setRate(tempDTO.getRate());  
                        }
                        List<MerBusRateBean> mockData = mockData();
                     // TODO 后台没有传name 过来，这里假写为code ，@乔
                        for (MerBusRateBean mock : mockData) {
                            if (mock.getProCode().equals(tempDTO.getProviderCode())) {
                                busBean.setProName(mock.getProName()); 
                                busBean.setRateName(mock.getRateName());
                            }
                        }
                        merBusRateBeanList.add(busBean);
                    }
                } 
                
                //获取商户验签密钥信息
                merchantBeans.setMerBusRateBeanList(merBusRateBeanList.toArray(new MerBusRateBean[merBusRateBeanList.size()]));
                merchantBean.setCode(merchantDtos.getCode());
                merchantBean.setResponseEntity(merchantBeans);
            } else {
                merchantBean.setCode(merchantDtos.getCode());
            }

        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.error("MerchantServiceImpl findMerchantBeans throws:" ,e);
            merchantBean.setCode(merchantDtos.getCode());
        }

        return merchantBean;
    }
    
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
        }
        return merBusRateBeanList;
    }
    
    @Override
    public DodopalResponse<MerchantBean> findInfoByMerCode(MerchantBean merUser, MerStateEnum state) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DodopalResponse<String> updateMerchantForPortal(MerchantBean merchantBean) {
        MerchantDTO merchantDTO = new MerchantDTO();
        DodopalResponse<String> code = new DodopalResponse<String>();
        MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
        MerchantUserBean merchantUserBean = merchantBean.getMerchantUserBean();
        try {
            PropertyUtils.copyProperties(merchantUserDTO, merchantUserBean);
            PropertyUtils.copyProperties(merchantDTO, merchantBean);
            merchantDTO.setMerchantUserDTO(merchantUserDTO);
            code = merchantdelegate.updateMerchantForPortal(merchantDTO);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("MerchantServiceImpl updateMerchantForPortal throws:" , e);
            code.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        } catch(HessianConnectionException e){
            log.error("MerchantServiceImpl updateMerchantForPortal throws:", e);
            code.setCode(ResponseCode.HESSIAN_ERROR);
            e.printStackTrace();
        }
        return code;
    }


}
