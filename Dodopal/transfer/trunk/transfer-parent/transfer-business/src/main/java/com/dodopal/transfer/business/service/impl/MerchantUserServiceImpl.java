package com.dodopal.transfer.business.service.impl;

import java.text.NumberFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.transfer.business.dao.MerchantUserMapper;
import com.dodopal.transfer.business.service.MerchantUserService;
@Service
public class MerchantUserServiceImpl implements MerchantUserService {
    
    @Autowired
    private MerchantUserMapper mapper;
    @Override
    @Transactional(readOnly = true)
    public String generateMerUserCode(UserClassifyEnum userClassify) {
        StringBuffer sb = new StringBuffer();
        // 是否测试账户(1位):1-正式商户用户; 2--测试商户用户; 3--正式个人用户;  4--个人测试用户
        sb.append(userClassify.getCode());
        // 4位随机数
        int number = new Random().nextInt(9999) + 1;
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        String randomNum = formatter.format(number);
        sb.append(randomNum);
        // 数据库12位sequence
        String seq = mapper.getMerUserCodeSeq();
        sb.append(seq);
        return sb.toString();
    }

    /**
     * 检查用户名是否存在
     */
    @Override
    public boolean checkExist(String merUserName) {
        return mapper.checkExist(merUserName);
    }

}
