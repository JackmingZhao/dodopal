package com.dodopal.api.payment.facade;

import java.util.List;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayAwayQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * 外接支付方式
 * @author xiongzhijing@dodopal.com
 * @version 创建时间：2015年8月6日
 */
public interface PayWayExternalFacade {
    /**
     * 查询外接支付方式（分页）
     * @param queryDto 查询条件封装的传输时的实体Dto 
     * @return DodopalResponse<DodopalDataPage<PayAwayDTO>>
     */
    public DodopalResponse<DodopalDataPage<PayAwayDTO>> findPayWayExternalByPage(PayAwayQueryDTO queryDto);
    
  
    /**
     * 保存外接支付方式
     * @param payAwayDTO 外接支付方式对应的传输时的实体Dto
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> savePayWayExternal(PayAwayDTO payAwayDTO);
    
 
    /**
     * 批量删除外接支付方式
     * @param ids 外接支付方式的ID 
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> batchDelPayWayExternalByIds(List<String> ids);
    
    /**
     * 修改外接支付方式
     * @param payDTO 外接支付方式 对应在传输数据时的实体DTO
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> updatePayAway (PayAwayDTO payDTO);


    /**
     * 批量修改启用停用标示
     * @param ids 外接支付方式的ID
     * @param activate 启用停用标识
     * @param updateUser 修改人
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> updatePayWayExternalActivate(List<String> ids, String activate,String updateUser);


    /**
     * 根据外接支付方式的id查询外接支付方式的详细详细
     * @param id 外接支付方式的id 
     * @return PayAwayDTO  外接支付方式 对应在传输数据时的实体DTO
     */
    public PayAwayDTO findPayExternalById(String id);
    
    
    
    /**
     * 根据外接支付方式的id查询外接支付方式的详细详细
     * @param id 外接支付方式的id 
     * @return PayAwayDTO  外接支付方式 对应在传输数据时的实体DTO
     */
    public DodopalResponse<PayAwayDTO> findPayExternalByPayId(String id);

    /**
     * 通过支付类型查询相关的支付方式
     * @param payType 支付类型
     * @return List<PayConfigDTO> 处于启用状态的支付方式list
     */
    public List<PayConfigDTO> findPayWayNameByPayType(String payType);
    
    /**
     * 根据商户编码和支付方式配置表id 查询外接支付表中是否已存在该支付方式
     * @param merCode 商户编号
     * @param payConfigId 支付方式配置表id
     * @return int 
     */
    public int countBymerCodeAndPayConfigId(String merCode,String payConfigId);

}
