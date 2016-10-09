package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.model.dto.PayAwayQuery;

public interface PayAwayService {

    /**
     * 查询外接支付方式（分页）
     * @param payQuery 外接支付方式查询条件封装的实体 payQuery
     * @return DodopalResponse<DodopalDataPage<PayAwayBean>>
     */
    public  DodopalResponse<DodopalDataPage<PayAwayBean>> findPayAwayByPage (PayAwayQuery  payQuery);

    /**
     * 新增or修改外接支付方式
     * @param payBean 外接支付方式对应的实体bean
     * @return String
     */
    public  String saveOrUpdatePayAway(PayAwayBean payBean);
    
    /**
     * 修改外接支付方式
     * @param payBean 外接支付方式对应的实体bean
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> updatePayAway(PayAwayBean payBean);
    
    /**
     * 批量删除外接支付方式
     * @param ids 外接支付方式的id  list
     * @param payAwayType 外接支付方式标识
     * @return DodopalResponse<Integer> 
     */
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType);
  
    /**
     * 批量停用启用外接支付方式
     * @param ids 外接支付方式的id  list
     * @param activate 启用 停用标识
     * @param payAwayType 外接支付方式标识
     * @param updateUser 修改人
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> enableOrDisablePayAway(List<String> ids, String activate, String payAwayType , String updateUser);
    /**
     * 根据外接支付方式的id查询外接支付方式信息
     * @param id 外接支付方式id
     * @param payAwayType 外接支付方式标识
     * @return PayAwayBean 外接支付方式对应的实体bean
     */
    public PayAwayBean findPayExternalById(String id,String payAwayType);

    /**
     * 根据支付类型查询相关的支付方式
     * @param payType 支付类型
     * @return List<PayConfigBean> 处于该支付类型相关的启用状态的支付方式
     */
    public List<PayConfigBean> findPayWayNameByPayType(String payType);
}
