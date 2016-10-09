package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.users.business.dao.MerBusRateMapper;
import com.dodopal.users.business.model.MerBusRate;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerchantService;

/**
 * 类说明 ：
 * @author lifeng
 */
@Service
public class MerBusRateServiceImpl implements MerBusRateService {
    @Autowired
    private MerBusRateMapper merBusRateMapper;
    @Autowired
    private MerchantService merchantService;

    @Override
    @Transactional(readOnly = true)
    public List<MerBusRate> findMerBusRateByMerCode(String merCode) {
        return merBusRateMapper.findMerBusRateByMerCode(merCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerBusRate> batchFindMerBusRateByMerCodes(List<String> merCodes) {
        return merBusRateMapper.batchFindMerBusRateByMerCodes(merCodes);
    }

    @Override
    @Transactional
    public int addMerBusRateBatch(List<MerBusRate> list) {
        return merBusRateMapper.addMerBusRateBatch(list);
    }

    @Override
    @Transactional
    public int updateMerBusRate(MerBusRate merBusRate) {
        return merBusRateMapper.updateMerBusRate(merBusRate);
    }

    @Override
    @Transactional
    public int batchUpdateMerBusRate(List<MerBusRate> list, String merCode) {
        int num = merBusRateMapper.deleteMerBusRateByMerCode(merCode);
        if (!CollectionUtils.isEmpty(list)) {
            num = merBusRateMapper.addMerBusRateBatch(list);
        }
        return num;
    }

    @Override
    @Transactional
    public int deleteMerBusRateById(String id) {
        return merBusRateMapper.deleteMerBusRateById(id);
    }

    @Override
    @Transactional
    public int deleteMerBusRateByMerCode(String merCode) {
        return merBusRateMapper.deleteMerBusRateByMerCode(merCode);
    }

    @Override
    @Transactional
    public int batchDelMerBusRateByMerCodes(List<String> merCodes) {
        return merBusRateMapper.batchDelMerBusRateByMerCodes(merCodes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerBusRate> findChildMerProRateCodeType(List<String> merCodes) {
        return merBusRateMapper.findChildMerProRateCodeType(merCodes);
    }

	@Override
	@Transactional(readOnly = true)
	public List<String> findMerCitys(String merCode) {
		Merchant mer = merchantService.findMerchantInfoByMerCode(merCode);
		if (mer != null) {
			String merType = mer.getMerType();
			if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
				merCode = mer.getMerParentCode();
			}
		}
		return merBusRateMapper.findMerCitys(merCode, RateCodeEnum.YKT_RECHARGE.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findMerCitysPayment(String merCode) {
		Merchant mer = merchantService.findMerchantInfoByMerCode(merCode);
		if (mer != null) {
			String merType = mer.getMerType();
			if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
				merCode = mer.getMerParentCode();
			}
		}
		return merBusRateMapper.findMerCitysPayment(merCode, RateCodeEnum.YKT_PAYMENT.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findUserCitys() {
		return merBusRateMapper.findUserCitys();
	}

	@Override
	@Transactional(readOnly = true)
	public String findYktCodeByCityCode(String cityCode) {
		return merBusRateMapper.findYktCodeByCityCode(cityCode);
	}

}
