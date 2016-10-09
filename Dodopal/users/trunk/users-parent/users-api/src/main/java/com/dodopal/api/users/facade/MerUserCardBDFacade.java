package com.dodopal.api.users.facade;

import java.util.List;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.dto.query.MerUserCardBDDTOQuery;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午3:52:16
 */
public interface MerUserCardBDFacade {
    //查询列表
    DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO);
    
    /**
     * 用户绑卡
     * @param cardBDDTO 
     * @return DodopalResponse<MerUserCardBDDTO>
     */
    DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(MerUserCardBDDTO cardBDDTO);
    
    /**
     * 查询用户绑定卡数量
     * @param cardBDDTO 
     * @return DodopalResponse<Integer>
     */
    DodopalResponse<Integer> findMerUserCardBDCount(MerUserCardBDDTO cardBDDTO);
    
    //用户解绑
    /**
     * 卡片管理，用户解绑
     * @param userId 用户号（userId）
     * @param ids  选择的卡片记录 
     * @param resource 来源（记日志使用）
     * @return  DodopalResponse<String>
     */
    DodopalResponse<String> unBundlingCard(String userId,String operName,List<String> ids,String source);
    
    /** 
     * @Title: findMerUserCardBDListByPage 
     * @Description: 分页查询
     * @param cardBDFindDTO
     * @return    设定文件 
     * DodopalDataPage<MerUserCardBDDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> findMerUserCardBDListByPage(MerUserCardBDDTOQuery cardBDFindDTO);
    
    /**
     * 用户操作日志查询
     * @param query
     * @return DodopalDataPage<MerUserCardBDLogDTO>
     */
    DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> findUserCardBDLogByPage(UserCardLogQueryDTO query);
    
    /**
     * 编辑卡片
     * @param cardDTO
     * @return
     */
    public DodopalResponse<Boolean> editUserCard(MerUserCardBDDTO cardDTO);
    /**
     * 根据id查询用户卡片信息
     * @param id
     * @return
     */
    public DodopalResponse<MerUserCardBDDTO> findMerUserCardBDById(String id);
    
    /**
     * 查询个人卡片充值和消费信息（分页）
     * @author matianzuo(Mikaelayn)
     * @param query
     * @return
     */
    public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query);
    
    /**
     * @author Mika
     * 根据充值还是消费的类型和订单号来查询一单的充值和消费信息
     * @param type
     * @param orderNum
     * @return
     */
    public DodopalResponse<UserCardRecordDTO> findCardRecordInfoByTypeOrderNum(String type, String orderNum);

    List<MerUserCardBDDTO> getExportExcelMerUserCardList(MerUserCardBDDTOQuery cardBDFindDTO);

    List<MerUserCardBDLogDTO> getExportExcelUserCardLog(UserCardLogQueryDTO query);
}
