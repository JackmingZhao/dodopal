package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.MerBusRateBean;
import com.dodopal.oss.business.bean.MerRateSupplementBean;
import com.dodopal.oss.business.bean.MerchantBean;
import com.dodopal.oss.business.bean.ProductBean;
import com.dodopal.oss.business.model.TreeNode;
import com.dodopal.oss.business.model.dto.MerchantQuery;


public interface MerchantService {
    
    /**
     * 加载商户审核信息列表- 分页查找
     * @param type
     * @return
     */
   public DodopalResponse<DodopalDataPage<MerchantBean>> findMerchantBeanByPage(MerchantQuery merchantBean);
    /**
     * 商户信息保存
     * @param merchantUserBean
     * @return
     */
    public DodopalResponse<String> saveMerchantUserBusRate(MerchantBean merchantBean);
    /**
     * 商户信息修改和审核修改
     * @param merchantUserBean
     * @return
     */
    public DodopalResponse<String> updateMerchantUserBusRate(MerchantBean merchantBean);
    /**
     * 停启用商户
     * @param userId
     */
    public DodopalResponse<Integer>  startAndDisableMerchant(List<String> merCode,String activate, String updateUser);
     /**
      * 修改和审核时加载单个商户信息
      * @param merchantBean
      * @return
      */
    public DodopalResponse<MerchantBean> findMerchantBeans(String merCode);
    
     
    /** 
     * @Title: findUnverifyMgmtByMerCode 
     * @Description: 根据商户号查询
     * @param merUser
     * @return    设定文件 
     * DodopalResponse<MerchantBean>    返回类型 
     * @throws 
     */
    public DodopalResponse<MerchantBean> findInfoByMerCode(MerchantBean merUser,MerStateEnum state);

    
    
    /**
     * 查看自定义商户信息
     */
    
    public DodopalResponse<List<TreeNode>> findMerFuncitonInfoList(String merchantType, String param, String merCode);
    
    //TODO 产品库接口上来后改掉 2015-07-18 
    /**
     * 根据MER_PARENT_CODE查询通卡公司，并且勾选住
     */
    public DodopalResponse<List<MerBusRateBean>> findProductList(String merParentCode, String rateCode, String merType);
    //TODO 产品库接口上来后改掉 2015-07-18 
    /**
     * 根据MER_CODE查询通卡公司，并且勾选住
     */
    public DodopalResponse<List<MerBusRateBean>> findMerProductList(String merCode, String rateCode);
    
    /**
     * 加载已选择的一卡通信息
     */
    public DodopalResponse<List<MerBusRateBean>> loadYktInfo(String[] productCode,String merParentCode, String rateCode, String merType);
    
    
    public String findMerchantNameByMerchantCode(String code);
   
    /**
     * 查看产品库通卡公司名称
     */
    public DodopalResponse<List<ProductBean>> findProductNames(ProductBean productBean);
    /**
     * 根据通卡公司名称查询业务信息
     */
    public DodopalResponse<List<ProductBean>> findProductList(String[] productCode);
    /**
     * 删除审核不通过的商户信息
     * @param merCodes
     * @return
     */
    public DodopalResponse<Integer>  delNotverifMerchants(List<String> merCodes,String upUser);

    /**
     * 判断是否与上级费率类型不一致，或者少于下级通卡公司数量，或者与下级费率类型不一致
     * @param merchantDTO
     * @return
     */
    public DodopalResponse<Boolean> checkRelationMerchantProviderAndRateType(MerchantBean merchantBean);
    
    /**
     * 查看产品库通卡公司名称
     */
    public  DodopalResponse<List<MerBusRateBean>> loadYktInfoName(String merCode);

    /**
     * 根据商户编码获取上级业务类型
     * @param merCode
     * @return
     */
    public DodopalResponse<List<MerRateSupplementBean>> findMerRateSupplementByCode(String merCode, String merType);
}
