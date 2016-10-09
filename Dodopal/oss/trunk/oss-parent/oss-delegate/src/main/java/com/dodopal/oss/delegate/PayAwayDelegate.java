package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayAwayQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface PayAwayDelegate {
   
    /**
     * 查询外接支付方式（分页）
     * @param queryDTO 查询条件封装的传输时的实体Dto 
     * @return DodopalResponse<DodopalDataPage<PayAwayDTO>>
     */
    public DodopalResponse<DodopalDataPage<PayAwayDTO>>findPayAwayListByPage(PayAwayQueryDTO queryDTO);

    /**
     * 新增外接支付方式
     * @param payDTO 外接支付方式对应的传输时的实体Dto
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> savePayAway (PayAwayDTO payDTO);
    
    /**
     * 修改外接支付方式
     * @param payDTO 外接支付方式 对应在传输数据时的实体DTO
     * @return DodopalResponse<Integer> 
     */
    public DodopalResponse<Integer> updatePayAway (PayAwayDTO payDTO);
    
    /**
     * 批量修改启用停用状态
     * @param List<String> ids  外接支付方式的id
     * @param activate    启用停用标识
     * @param payAwayType 外接支付方式 标识
     * @param updateUser 修改人
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> enableOrDisablePayAway(List<String> ids, String activate,String payAwayType,String updateUser);


    /**
     * 批量删除外接支付方式
     * @param List<String> ids  外接支付方式的id 
     * @param payAwayType 外接支付方式 标识
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType);
    
    /**
     * 通过外接支付方式id查询外接支付的详细详细
     * @param id 外接支付方式的id 
     * @param payAwayType 外接支付方式 标识
     * @return PayAwayDTO 外接支付方式对应在传输时的实体Dto
     */
    public PayAwayDTO findPayExternalById(String id, String payAwayType);
    
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
    public int countPayExternal(String merCode,String payConfigId);
}
