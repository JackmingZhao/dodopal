package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerBusRateBean;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.model.query.ChildMerchantQuery;

public interface ChildMerchantService {
    /**
     * 加载子商户信息列表
     * @param childMerchantQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerchantBean>> findChildMerchantBeanByPage(ChildMerchantQuery childMerchantQuery);
   /**
    * 保存子商户信息
    * @param merchantBean
    * @return
    */
    public DodopalResponse<String> saveChildMerchants(MerchantBean merchantBean);
   /**
    * 根据商户编号查看单条子商户信息
    * @param merCode
    * @return
    */
    public DodopalResponse<MerchantBean> findChildMerchantByCode(String merCode,String merPercode);

    /**
     * 修改子商户信息
     * @param merchantBean
     * @return
     */
    public DodopalResponse<String> upChildMerchantBean(MerchantBean merchantBean);
      /**
       * 停启用子商户信息
       * @param merCode
       * @param activate
       * @return
       */
    public DodopalResponse<Integer>  startAndDisableMerchant(String[] merCode,String activate,String merPercode, String updateUser);
 
    /**
     * 停启用子商户信息
     * @param merCode
     * @param activate
     * @return
     */
  public DodopalResponse<Integer>  delChildMerchant(String[] merCode,String merState,String merPercode, String updateUser);
   /**
    * 查看通卡公司信息
    * @param merCode
    * @return
    */
   public DodopalResponse<List<MerBusRateBean>> findProductList(String merCode);
   
   /**
    * 查看通卡公司信息 TODO 假数据
    * @param macDATA
    * @return
    */
   public DodopalResponse<List<MerBusRateBean>> loadYktInfo(String[] productCode,String merCode);
   
   /**
    * 检查手机号是否已注册
    * @param mobile
    * @return true:已注册
    */
   public DodopalResponse<Boolean> checkMobileExist(String mobile,String merCode);

   /**
    * 检查用户名是否已注册
    * @param userName
    * @return true:已注册
    */
   public DodopalResponse<Boolean> checkUserNameExist(String userName,String merCode);

   /**
    * 检查商户名称是否已注册
    * @param merName
    * @return
    */
   public DodopalResponse<Boolean> checkMerchantNameExist(String merName,String merCode);
}
