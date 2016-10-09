package com.dodopal.users.business.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerKeyTypeMapper;
import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.service.MerKeyTypeService;
@Service
public class MerKeyTypeServiceImpl implements MerKeyTypeService {

    @Autowired
    private MerKeyTypeMapper merKeyTypeMapper;
    
    /** 
     * @Title: findMerchantUser 
     * @Description: 签名秘钥查看列表
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型 
     * @throws 
     */
    @Transactional(readOnly = true)
    public List<MerKeyType> findMerKeyTypeList(MerKeyType merkey) {
        return merKeyTypeMapper.findMerKeyType(merkey);
    }

    /** 
     * @Title: findMerMD5PayPwd 
     * @Description: 签名秘钥查看
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型   ID,MER_CODE,MER_MD5_PAYPWD,ACTIVATE
     * @throws 
     */
    @Transactional(readOnly = true)
    public MerKeyType findMerMD5PayPwd(MerKeyType keyType) {
        if(StringUtils.isNotBlank(keyType.getMerCode())){//判断条件参数
                //检查是否有参数，为空放入查找
            return merKeyTypeMapper.findMerMD5PayPwd(keyType);
        }
        return null;
    }
    /** 
     * @Title: findMerMD5BackPayPWD 
     * @Description: 验签秘钥查看
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型       ID,MER_CODE,MER_MD5_BACKPAYPWD,ACTIVATE
     * @throws 
     */
    @Transactional(readOnly = true)
    public MerKeyType findMerMD5BackPayPWD(MerKeyType keyType) {
        if(StringUtils.isNotBlank(keyType.getMerCode())){//判断条件参数
              //检查是否有参数，为空放入查找
                return merKeyTypeMapper.findMerMD5BackPayPWD(keyType);
        }
        return null;
    }
    /** 
     * @Title: saveKeyType 
     * @Description: 保存
     * @param keyType    设定文件 
     * void    返回类型 
     * @throws 
     */
    @Transactional
    public void saveKeyType(MerKeyType keyType) {
        keyType.setCreateDate(new Date());
        merKeyTypeMapper.insertMerKeyType(keyType);
    }

    
    /** 
     * @Title: updateMerKeyType 
     * @Description: 更新
     * @param keyType    设定文件 
     * void    返回类型 
     * @throws 
     */
    @Transactional
    public int updateMerKeyType(MerKeyType keyType) {
        keyType.setUpdateDate(new Date());
        return merKeyTypeMapper.updateMerKeyType(keyType);
    }
    
    /** 
     * @Title: updateMerMD5PayPwd 
     * @Description: 签名秘钥更新   
     * @param keyType    设定文件   merCode/id   merMD5PayPwd   oldMerMD5PayPWD:原签名秘钥
     * void    返回类型 
     * @throws 
     */
    @Transactional
    public int updateMerMD5PayPwd(MerKeyType keyType,String oldMerMD5PayPWD) {
        keyType.setUpdateDate(new Date());
        MerKeyType oldMerKey = new MerKeyType();
        oldMerKey.setMerCode(keyType.getMerCode());
        oldMerKey.setId(keyType.getId());
        oldMerKey.setMerMD5PayPwd(oldMerMD5PayPWD);
        oldMerKey =  merKeyTypeMapper.findMerMD5PayPwd(oldMerKey);
        if(null!=oldMerKey){
            if(StringUtils.isNotBlank(keyType.getMerMD5PayPwd()))
                return merKeyTypeMapper.updateMerMD5PayPwd(keyType);
            else
                return 0;
        }
        return 0;
    }

    /** 
     * @Title: updateMerMD5BackPayPWD 
     * @Description: 验签秘钥更新 
     * @param keyType    设定文件 　merCode/id  merMD5BackPayPWD    oldMerMD5BackPayPWD:原验签
     * void    返回类型 
     * @throws 
     */
    @Transactional
    public int updateMerMD5BackPayPWD(MerKeyType keyType,String oldMerMD5BackPayPWD) {
        keyType.setUpdateDate(new Date());
        MerKeyType oldMerKey = new MerKeyType();
        oldMerKey.setMerCode(keyType.getMerCode());
        oldMerKey.setId(keyType.getId());
        oldMerKey.setMerMD5BackPayPWD(oldMerMD5BackPayPWD);
        oldMerKey = merKeyTypeMapper.findMerMD5BackPayPWD(oldMerKey);
        if(null!=oldMerKey){
            if(null!=keyType.getMerMD5BackPayPWD()&&!"".equals(keyType.getMerMD5BackPayPWD()))
                return merKeyTypeMapper.updateMerMD5BackPayPWD(keyType);
            else
                return 0;
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public MerKeyType findMerKeyTypeByMerCode(String merCode) {
        return merKeyTypeMapper.findMerKeyTypeByMerCode(merCode);
    }

    @Override
    @Transactional
    public int batchDelMerKeyTypeByMerCodes(List<String> merCodes) {
        return merKeyTypeMapper.batchDelMerKeyTypeByMerCodes(merCodes);
    }
}
