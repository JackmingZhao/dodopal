package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.users.business.model.MerAutoAmt;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantExtend;
import com.dodopal.users.business.model.query.MerchantQuery;

public interface MerchantMapper {

    public List<Merchant> findMerchant(Merchant merchant);

    public Merchant findMerchantById(String id);

    public Merchant findMerchantByMerCode(String merCode);

    public Merchant findMerchantExact(Merchant merchant);

    public List<Merchant> batchFindMerchantByMerCode(@Param("merCodes") List<String> merCodes);

    public List<Merchant> findMerchantRelationByMerCode(String merCode);

    public List<Merchant> findChildMerchantByMerCodes(@Param("merCodes") List<String> merCodes);

    /**
     * 根据上级商户号和相应的商户类型查找子商户
     * @param merParentCode
     * @param merTypes
     * @return
     */
    public List<Merchant> findChildMerchantByParentCodeType(@Param("merParentCode")String merParentCode, @Param("merTypes")List<String> merTypes);

    public int addMerchant(Merchant merchant);

    public int updateMerchant(Merchant merchant);

    /**
     * 商户审核不通过
     * @param merchant
     * @return
     */
    public int rejectMerchantReg(Merchant merchant);

    public int batchUpdateMerchant(@Param("merchant") Merchant merchant, @Param("merCodes") List<String> merCodes);

    public String getMerCodeSeq();

    public boolean checkExist(Merchant merchant);

    /**
     * 分页查询商户信息
     * @param merchantQuery
     * @return
     */
    public List<Merchant> findMerchantByPage(MerchantQuery merchantQuery);

    /**
     * 获取记录总数
     * @param merchantQuery
     * @return
     */
    public int findMerchantByPageCount(MerchantQuery merchantQuery);

    /**
     * 获取导出记录
     * @param merchantQuery
     * @return
     */
    public List<Merchant> findMerchantByPageList(MerchantQuery merchantQuery);

    /**
     * 分页查询商户信息
     * @param merchantQuery
     * @return
     */
    public List<Merchant> findMerchantBusByPage(MerchantQuery merchantQuery);

    /**
     * 获取记录总数
     * @param merchantQuery
     * @return
     */
    public int findMerchantBusByPageCount(MerchantQuery merchantQuery);

    /**
     * 获取导出记录
     * @param merchantQuery
     * @return
     */
    public List<Merchant> findMerchantBusByPageList(MerchantQuery merchantQuery);

    public int batchDelMerchantByMerCodes(@Param("merCodes") List<String> merCodes);
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: updateMerchantMobileLinkUser 
     * @Description: 根据mercode更新商户的联系人及手机号码
     * @param merchant
     * @return    设定文件 
     * int    返回类型 
     * @throws 
     */
    public int  updateMerchantMobileLinkUser(Merchant merchant);
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
    public int updateThroughMerchant(Merchant merchant);

    /**
     * 更新商户手机号
     * @param merCode
     * @param merLinkUserMobile
     * @param updateUser
     * @return
     */
    public int updateMerLinkUserMobile(@Param("merCode")String merCode, @Param("merLinkUserMobile")String merLinkUserMobile, @Param("updateUser")String updateUser);

    /**
     * 获取外接商户账户支付方式ID
     * @param merCode
     * @return
     */
    public String findPayWayExtId(@Param("merCode")String merCode);
    /**
     * 获取自动分配额度信息
     * @param merCode
     * @return
     */
    public MerAutoAmt findMerAutoAmtInfo(@Param("merCode")String merCode);
    
    /**
     * 获取商户管理员ID
     * @param merCode
     * @return
     */
    public String findManagerIdByMerCode(@Param("merCode")String merCode);

    /**
     * 根据商户号获取一卡通编码
     * @param merCode
     * @return
     */
    public String getYktCodeByMerCode(String merCode);

    /**
     * 更新供应商商户信息
     * @param merchant
     * @return
     */
    public int updateMerchantProvider(Merchant merchant);

    /**
     * 根据商户号查找商户扩展表信息
     * @param merCode
     * @return
     */
    public MerchantExtend findMerchantExtend(@Param("merCode")String merCode);
}
