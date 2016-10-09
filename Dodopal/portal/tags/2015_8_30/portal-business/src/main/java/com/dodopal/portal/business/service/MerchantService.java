package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantBean;


public interface MerchantService {
    
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
     * @author dingkuiyuan@dodopal.com
     * @Title: updateMerchantForPortal 
     * @Description: 门户商户信息编辑更新
     * @param merchantBean
     * @return    设定文件 
     * DodopalResponse<String>    返回类型 
     * @throws 
     */
    public DodopalResponse<String> updateMerchantForPortal(MerchantBean merchantBean);

    /**
     * 停启用商户
     * @param userId
     */
    public DodopalResponse<Integer> startAndDisableMerchant(List<String> merCode,String activate);
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

    

}
