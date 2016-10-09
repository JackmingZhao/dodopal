package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerKeyType;

public interface MerKeyTypeService {
    /** 
     * @Title: findMerchantUser 
     * @Description: 签名秘钥查看列表
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型 
     * @throws 
     */
    public List<MerKeyType> findMerKeyTypeList(MerKeyType merkey);
    

    /** 
     * @Title: findMerMD5PayPwd 
     * @Description: 签名秘钥查看
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型   ID,MER_CODE,MER_MD5_PAYPWD,ACTIVATE
     * @throws 
     */
    public MerKeyType findMerMD5PayPwd(MerKeyType keyType);
    
    
    /** 
     * @Title: findMerMD5BackPayPWD 
     * @Description: 验签秘钥查看
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型       ID,MER_CODE,MER_MD5_BACKPAYPWD,ACTIVATE
     * @throws 
     */
    public MerKeyType findMerMD5BackPayPWD(MerKeyType keyType);

    /** 
     * @Title: insertMerKeyType 
     * @Description: 保存
     * @param keyType    设定文件 
     * void    返回类型 
     * @throws 
     */
    public void saveKeyType(MerKeyType keyType);
    
    
    /** 
     * @Title: updateMerKeyType 
     * @Description: 更新
     * @param keyType    设定文件 
     * void    返回类型 
     * @throws 
     */
    public int updateMerKeyType(MerKeyType keyType);
    
    
    /** 
     * @Title: updateMerMD5PayPwd 
     * @Description: 签名秘钥更新   
     * @param keyType    设定文件   merCode/id   merMD5PayPwd   oldMerMD5PayPWD:原签名秘钥
     * void    返回类型 
     * @throws 
     */
    public int updateMerMD5PayPwd(MerKeyType keyType,String oldMerMD5PayPWD);
    
    
    /** 
     * @Title: updateMerMD5BackPayPWD 
     * @Description: 验签秘钥更新 
     * @param keyType    设定文件 　merCode/id  merMD5BackPayPWD    oldMerMD5BackPayPWD:原验签
     * void    返回类型 
     * @throws 
     */
    public int updateMerMD5BackPayPWD(MerKeyType keyType,String oldMerMD5BackPayPWD);

    /**
     * 根据商户号查询秘钥信息
     * @param merCode
     * @return
     */
    public MerKeyType findMerKeyTypeByMerCode(String merCode);

    /**
     * 根据商户号批量删除商户秘钥信息
     * @param merCodes
     * @return
     */
    public int batchDelMerKeyTypeByMerCodes(List<String> merCodes);
}
