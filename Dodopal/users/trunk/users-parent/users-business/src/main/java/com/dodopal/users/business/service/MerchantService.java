package com.dodopal.users.business.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.DirectMerChant;
import com.dodopal.users.business.model.MerAutoAmt;
import com.dodopal.users.business.model.MerBusRate;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantExtend;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantQuery;

/**
 * 类说明 :
 * @author lifeng
 */

public interface MerchantService {
    /**
     * 根据条件查询商户列表
     * @param merchant
     * @return
     */
    public List<Merchant> findMerchant(Merchant merchant);

    /**
     * 根据ID查询商户信息
     * @param id
     * @return
     */
    public Merchant findMerchantById(String id);

    /**
     * 根据商户号查询商户信息（包括对应的管理员用户信息、商户补充信息、业务费率信息、自定义功能信息）
     * @param merCode
     * @return
     */
    public Merchant findMerchantByMerCode(String merCode);

    public Merchant findMerchantExact(Merchant merchant);

    /**
     * 只查商户表
     * @param merCode
     * @return
     */
    public Merchant findMerchantInfoByMerCode(String merCode);

    /**
     * 根据商户号查询商户的层级关系
     * @param merCode
     * @return
     */
    public List<Merchant> findMerchantRelationByMerCode(String merCode);

    /**
     * 商户开户
     * @param merchant
     * @return
     */
    public DodopalResponse<String> register(Merchant merchant, AtomicReference<String> randomPWD);

    /**
     * 商户更新
     * @param merchant
     * @return
     */
    public String updateMerchant(Merchant merchant);

    /**
     * 根据ID集合批量更新商户属性
     * @param merchant
     * @param ids
     * @return 更新记录数
     */
    public int batchUpdateMerchant(Merchant merchant, List<String> merCodes);

    /**
     * 批量停启用商户
     * @param activate
     * @param merCodes
     * @return
     */
    public int batchUpdateMerchantActivate(String activate, List<String> merCodes, List<String> msg, String updateUser);

    /**
     * 根据商户类型、商户分类生成商户号 
     * 生成规则：1位(是否为测试账户)+ 4位随机数 + 10位数据库sequence
     * @param merType
     * @param merClassify
     * @return
     */
    public String generateMerCode(String merClassify);

    /**
     * 根据商户号查询商户下边的操作员信息 
     * @param merCode
     * @return
     */
    public List<MerchantUser> findMerOperators(MerchantUser merUser);

    /**
     * 根据商户号、用户号查看操作员信息
     * @return
     */
    public MerchantUser findMerOperatorByUserCode(String merCode, String userCode);

    /**
     * 配置操作员角色
     * @return
     */
    public int configMerOperatorRole(MerchantUser merUser);

    /**
     * 检查商户是否存在
     * @param merchant
     * @return
     */
    public boolean checkExist(Merchant merchant);

    /**
     * 根据条件查询商户列表(分页)
     * @param merchant
     * @return
     */
    public DodopalDataPage<Merchant> findMerchantByPage(MerchantQuery merchantQuery);

    /**
     * EXCEL导出使用
     * @param merchantQuery
     * @return
     */
    public int findMerchantByPageCount(MerchantQuery merchantQuery);

    /**
     * EXCEL导出使用
     * @param merchantQuery
     * @return
     */
    public List<Merchant> findMerchantByPageList(MerchantQuery merchantQuery);

    /**
     * 批量删除审核不通过商户信息
     * @param merCodes
     * @return
     */
    public int batchDelMerchantByMerCodes(List<String> merCodes);

    /**
     * 门户更新商户信息
     * @param merchant
     * @return
     */
    public int updateMerchantForPortal(Merchant merchant);

    /**
     * 门户更新审核通过商户信息
     * @param merchant
     * @return
     */
    public String updateThroughMerchant(Merchant merchant);

    /**
     * 商户审核不通过
     * @param merchant
     * @return
     */
    public String rejectMerchantReg(Merchant merchant);

    /**
     * 根据商户号查询费率信息
     * @param merCode
     * @return
     */
    public List<MerBusRate> findMerBusRateByMerCode(String merCode);

    /**
     * 门户更新审核不通过商户信息
     * @param merchant
     * @return
     */
    public String updateRejectMerchant(Merchant merchant);
    
    /**
     * 查询直营网点
     * @param merParentCode 上级商户编码
     * @param merName 商户名称
     * @return DodopalResponse<List<DirectMerChantDTO>>
     */
    public List<DirectMerChant> findMerchantByParentCode(String merParentCode, String merName) ;
    public List<DirectMerChant> findMerchantByParentCodeType(String merParentCode, String merName,String businessType) ;
    public List<DirectMerChant> findDirectTransferFilter(String merParentCode, String merName,String businessType) ;

    /**
     * 获取外接商户账户支付方式ID
     * @param merCode
     * @return
     */
    public String findPayWayExtId(String merCode);
    /**
     * 根据商户号查询自动分配额度信息
     * @param merCode
     * @return
     */
    public MerAutoAmt findMerAutoAmtInfo(String merCode) ;
    /**
     * 根据merCode 查询管理员ID
     * @param merCode
     * @return
     */
    public String findManagerIdByMerCode(String merCode);

    /**
     * 根据商户号（供应商）获取一卡通编码
     * @param merCode
     * @return
     */
    public String getYktCodeByMerCode(String merCode);

    /**
     * 更新供应商商户信息，管理员信息
     * @param merchant
     * @return
     */
    public int updateMerchantProvider(Merchant merchant);
    
    
    public  MerchantExtend findMerchantExtend(String merCode);
}
