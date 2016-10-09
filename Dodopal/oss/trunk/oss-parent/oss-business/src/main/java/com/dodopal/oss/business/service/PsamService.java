package com.dodopal.oss.business.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dodopal.api.card.dto.PosSignInOutDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.YktPsamBean;
import com.dodopal.oss.business.bean.query.YktPsamQuery;

public interface PsamService {
    /**
     * psam卡查询 分页
     * @param yktPsamQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<YktPsamBean>> findYktPsamByPage(YktPsamQuery yktPsamQuery);
    
    
    /**
     * 批量删除
     * @param samNo
     * @return
     */
    public DodopalResponse<String> batchDeleteYktPsamByIds(List<String> ids);
    
    /**
     * 批量启用商户
     * @param 
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<String> batchActivateMerByIds(List<String> ids);
    
    
    /**
     * 批量修改查询下载参数
     * @param 
     * @return DodopalResponse<Integer>
     */
    public DodopalResponse<String> batchNeedDownLoadPsamByIds(List<String> ids);
    

    /**
     * 新增psam卡
     * @param yktPsamDTO
     * @return
     */
    public DodopalResponse<String> addYktPsam(YktPsamBean yktPsamBean);
    
    
    /**
     * 修改psam卡
     * @param yktPsamDTO
     * @return
     */
    public DodopalResponse<String> updateYktPsam(YktPsamBean yktPsamBean);


    /**
     * 根据id查询psam卡信息
     * @param id
     * @return
     */
    public DodopalResponse<YktPsamBean> findPsamById(String id);


    /**
     * V61签到签退
     * @param posSignInOutBean
     * @return
     */
    public DodopalResponse<PosSignInOutDTO> posSignInOutForV61(PosSignInOutDTO posSignInOutDTO);
    
    /**
     * 批量导入
     * @param file
     * @param userId
     * @return
     */
    public DodopalResponse<String> importPsam(CommonsMultipartFile file, String userId); 

}
