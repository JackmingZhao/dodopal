package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerKeyType;

public interface MerKeyTypeMapper {
    
    /** 
     * @Title: findMerchantUser 
     * @Description: 签名秘钥查看
     * @param keyType
     * @return    设定文件 
     * List<MerKeyType>    返回类型 
     * @throws 
     */
    public List <MerKeyType> findMerKeyType(MerKeyType keyType);
    
    
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
     * @Description: 保存操作
     * @param keyType    设定文件 
     * void    返回类型 
     * @throws 
     */
    public int insertMerKeyType(MerKeyType keyType);
    
    
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
     * @param keyType    设定文件   merCode/id   merMD5PayPwd
     * void    返回类型 
     * @throws 
     */
    public int updateMerMD5PayPwd(MerKeyType keyType);
    
    
    /** 
     * @Title: updateMerMD5BackPayPWD 
     * @Description: 验签秘钥更新 
     * @param keyType    设定文件 　merCode/id  merMD5BackPayPWD
     * void    返回类型 
     * @throws 
     */
    public int updateMerMD5BackPayPWD(MerKeyType keyType);

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
    public int batchDelMerKeyTypeByMerCodes(@Param("merCodes") List<String> merCodes);

}
