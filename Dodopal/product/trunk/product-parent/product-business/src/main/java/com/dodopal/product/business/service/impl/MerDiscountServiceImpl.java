package com.dodopal.product.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerDiscountDTO;
import com.dodopal.api.users.dto.MerDiscountReferDTO;
import com.dodopal.api.users.dto.query.MerDiscountQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.MerDiscountBean;
import com.dodopal.product.business.bean.MerDiscountReferBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.dao.MerDiscountMapper;
import com.dodopal.product.business.service.MerDiscountService;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.delegate.MerDiscountDelegate;

@Service
public class MerDiscountServiceImpl implements MerDiscountService{
	
    private final static Logger log = LoggerFactory.getLogger(MerDiscountServiceImpl.class);

	@Autowired
	MerDiscountDelegate merDiscountDelegate;
	@Autowired
	MerchantService merchantService;
	@Autowired
	MerDiscountMapper discountMapper;
	
	@Override
	public DodopalResponse<List<MerDiscountBean>> findMerDiscountByList(
			MerDiscountQueryDTO merDiscountQueryDTO) {
		DodopalResponse<List<MerDiscountBean>>  dodopalResponse = new DodopalResponse<List<MerDiscountBean>>();
		try{
			DodopalResponse<List<MerDiscountDTO>>  dtoResponse = merDiscountDelegate.findMerDiscountByList(merDiscountQueryDTO);
			List<MerDiscountBean> beanList = new ArrayList<MerDiscountBean>();
			if(ResponseCode.SUCCESS.equals(dtoResponse.getCode())){
				if(CollectionUtils.isNotEmpty(dtoResponse.getResponseEntity())){
					for(MerDiscountDTO tempDTO : dtoResponse.getResponseEntity()){
						MerDiscountBean bean = new MerDiscountBean();
						PropertyUtils.copyProperties(bean, tempDTO);
						beanList.add(bean);
					}
				}
			}
			
			dodopalResponse.setCode(dtoResponse.getCode());
			dodopalResponse.setResponseEntity(beanList);
		}catch(Exception e){
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<List<MerDiscountReferBean>> findMerDiscountRefer(MerDiscountQueryDTO merDiscountQueryDTO) {
		DodopalResponse<List<MerDiscountReferBean>>  dodopalResponse = new DodopalResponse<List<MerDiscountReferBean>>();
		try{
			DodopalResponse<List<MerDiscountReferDTO>>  dtoResponse = merDiscountDelegate.findMerDiscountRefer(merDiscountQueryDTO);
			List<MerDiscountReferBean> beanList = new ArrayList<MerDiscountReferBean>();
			if(ResponseCode.SUCCESS.equals(dtoResponse.getCode())){
				if(CollectionUtils.isNotEmpty(dtoResponse.getResponseEntity())){
					for(MerDiscountReferDTO tempDTO : dtoResponse.getResponseEntity()){
						MerDiscountReferBean bean = new MerDiscountReferBean();
						PropertyUtils.copyProperties(bean, tempDTO);
						beanList.add(bean);
					}
				}
			}
			dodopalResponse.setCode(dtoResponse.getCode());
			dodopalResponse.setResponseEntity(beanList);
		}catch(Exception e){
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<List<MerDiscountBean>> findMerDiscountByList(String mercode,String merType) {
		DodopalResponse<List<MerDiscountBean>> dodopalResponse = new DodopalResponse<List<MerDiscountBean>>();
		List<MerDiscountBean> beanList = new ArrayList<MerDiscountBean>();
		MerDiscountQueryDTO queryDTO = new MerDiscountQueryDTO();
		queryDTO.setMerCode(mercode);
		queryDTO.setActivate(ActivateEnum.ENABLE.getCode());
		if(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)){
			DodopalResponse<List<MerDiscountReferDTO>>  dtoResponse = merDiscountDelegate.findMerDiscountRefer(queryDTO);
			if(ResponseCode.SUCCESS.equals(dtoResponse.getCode())){
				if(CollectionUtils.isNotEmpty(dtoResponse.getResponseEntity())){
					for(MerDiscountReferDTO tempDTO : dtoResponse.getResponseEntity()){
						MerDiscountBean bean = new MerDiscountBean();
						bean.setDiscount(tempDTO.getDiscount());
						beanList.add(bean);
					}
				}
			}
			dodopalResponse.setCode(dtoResponse.getCode());
		}else{
			DodopalResponse<List<MerDiscountDTO>>  dtoResponse = merDiscountDelegate.findMerDiscountByList(queryDTO);
			if(ResponseCode.SUCCESS.equals(dtoResponse.getCode())){
				if(CollectionUtils.isNotEmpty(dtoResponse.getResponseEntity())){
					for(MerDiscountDTO tempDTO : dtoResponse.getResponseEntity()){
						MerDiscountBean bean = new MerDiscountBean();
						bean.setDiscount(tempDTO.getDiscount());
						beanList.add(bean);
					}
				}
			}
			dodopalResponse.setCode(dtoResponse.getCode());
		}
		dodopalResponse.setResponseEntity(beanList);
		return dodopalResponse;
	}
	
    public List<MerDiscountBean> findMerDiscountToday(String mercode) {
        List<Map<String,String>> result = discountMapper.findMerDiscountToday(mercode);
        List<MerDiscountBean> listBean = new ArrayList<MerDiscountBean>();
        if(CollectionUtils.isNotEmpty(result)){
            for(Map<String,String> str :result){
                MerDiscountBean discountBean = new MerDiscountBean();
                discountBean.setMerCode(mercode);
                discountBean.setDiscount(String.valueOf(str.get("DISCOUNT")));
                discountBean.setSetDiscount(String.valueOf(str.get("SETDISCOUNT")));
                listBean.add(discountBean);
            }
        }
        return listBean;
    }
    public List<MerDiscountBean> findMerDiscountList(String mercode,String merType,String cityCode){
        if(log.isInfoEnabled()){
            log.info("获取圈存订单,merCode[{}],merType[{}],cityCode[{}]",mercode,merType,cityCode);
        }
        if(ProductConstants.BJ_CITY_CODE.equals(cityCode)){
            log.info("查询北京的折扣信息");
           return findMerDiscountToday(mercode);
        }else{
            log.info("查询其他城市的折扣信息");
            DodopalResponse<List<MerDiscountBean>> result = findMerDiscountByList(mercode, merType);
            if(result.isSuccessCode()){
                return result.getResponseEntity();
            }
            log.error("查询其他城市的折扣信息出错抛出ddp异常，错误码[{}]错误信息[{}]",result.getCode(),result.getMessage());
            throw new DDPException(result.getCode());
        }
    }

}
