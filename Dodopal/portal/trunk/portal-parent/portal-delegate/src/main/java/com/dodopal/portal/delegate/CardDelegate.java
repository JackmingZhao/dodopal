package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface CardDelegate {
    
    //查询用户卡片
    /**
     * 查询用户卡片
     * @param cardBDFindDTO
     * @return
     */
    public  DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO) ;

    //查询卡片操作日志（分页）
    /**
     * 查询卡片操作日志（分页）
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> findUserCardBDLogByPage(UserCardLogQueryDTO query);
    
    
    /**
     * 编辑卡片信息
     * @param cardBean
     * @return
     */
    public DodopalResponse<Boolean> editUserCard(MerUserCardBDDTO cardDTO);
    
    /**
     * 解绑
     * @param userName 登录用户名
     * @param ids 卡片管理表数据库id
     * @param source 来源
     * @return
     */
    public DodopalResponse<String> unbindCardByUser(String userName,String operName,List<String> ids,String source);
    
    /**
     * 根据id查询用户卡片信息
     * @param id
     * @return
     */
    public DodopalResponse<MerUserCardBDDTO> findMerUserCardBDById(String id);
    
    /**
     * @author matianzuo(Mikaelyan)
     * 查询个人卡片充值和消费信息（分页）
     * @param queryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO queryDTO);
    
    /**
     * @author Mika
     * 根据充值还是消费的类型和订单号来查询一单的充值和消费信息
     * @param type
     * @param orderNum
     * @return
     */
    public DodopalResponse<UserCardRecordDTO> findCardRecordInfoByTypeOrderNum(String type, String orderNum);
}
