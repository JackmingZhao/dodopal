package com.dodopal.oss.business.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.Pos;
import com.dodopal.oss.business.model.PosInfo;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.PosOperationDTO;
import com.dodopal.oss.business.model.dto.PosQuery;
import com.dodopal.oss.business.model.dto.PosUpdateBatch;

public interface PosService {

    /**
     * 保存或修改pos 信息
     * @param pos
     * @return
     */
    String saveOrUpdatePos(Pos pos);

    /**
     * 查找pos 
     * @param pos
     * @return
     */
    List<Pos> findPoss(Pos pos);
    
    /**
     * 根据posID 查找pos 
     * @param pos
     * @return
     */
    PosInfo findPosById(String posId);
    
    List<Pos> findPosByCode(String[] posCode);

    /**
     * 删除pos 
     * @param posId
     */
    DodopalResponse<String>  deletePos(String[] posId,User user);
    
    
    DodopalDataPage<PosInfo> findPosByPage(PosQuery posQuery);
    
    
    /**
     *  POS操作
     *     绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @return
     */
    DodopalResponse<String> posOper(PosOperationDTO operation, User user);
    
    
    void modifyCity(PosUpdateBatch pos);
    
    void modifyVersion(String codes, String version, String updateUser);
    
    void modifyLimitation(String codes, long maxTimes, String updateUser);
    
    DodopalResponse<String> importPos(CommonsMultipartFile file, String userId); 
    
    
    /**
     * 查询pos信息  导出
     * @param posQuery
     * @return
     */
   List<PosInfo> findPosByList(PosQuery posQuery); 
   
}
