package com.dodopal.api.payment.facade;

import java.util.List;

import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.PayTransferDTO;
import com.dodopal.api.payment.dto.TranscationListResultDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.dto.query.TranscationRequestDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月22日 下午5:39:57
 */
public interface PayTraTransactionFacade {
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayTraTransactionByPage 
     * @Description: 查询交易流水（分页）
     * @param queryDTO
     * @return    设定文件 
     * DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>    返回类型 
     * @throws 
     */
    DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findPayTraTransactionByPage(PayTraTransactionQueryDTO queryDTO);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月20日 下午7:52:23 
      * @version 1.0 
      * @parameter  
      * @描述 查找交易流水表的数据
      * @return  
      */
    DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionList(PayTraTransactionQueryDTO queryDTO);

    /**
     * 查询所有交易记录
     * @return
     */
    DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionAll(PayTraTransactionQueryDTO queryDTO);
    
    /**
     * 查询所有交易记录 (oss系统)导出
     * @return
     */
    DodopalResponse<List<PayTraTransactionDTO>>  findTrasactionExport(PayTraTransactionQueryDTO queryDTO);
    
    
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: findPayTraTransactionInfoById 
     * @Description: 根据id查询交易流水
     * @param id
     * @return    设定文件 
     * DodopalResponse<PayTraTransactionDTO>    返回类型 
     * @throws 
     */
    DodopalResponse<PayTraTransactionDTO> findPayTraTransactionInfoById(String id);
   
    /**
     * 账户首页查询最新的十条交易
     * @param ext 个人OR企业
     * @param merOrUserCode 用户号or商户号
     * @param createUser 区分操作员和管理员
     * @return DodopalResponse<List<PayTraTransactionDTO>>
     */
    DodopalResponse<List<PayTraTransactionDTO>> findPayTraTransactionByCode(String ext, String merOrUserCode,String createUser);
    
    /**
     * 根据tranCode查询交易流水信息
     * @param tranCode
     * @return DodopalResponse<PayTraTransactionDTO>
     */
    DodopalResponse<PayTraTransactionDTO> findTranInfoByTranCode(String tranCode);
    
    /**
     * 查询子商户交易记录
     * @param queryDTO
     * @return DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>
     */
    DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findTraRecordByPage(PayTraTransactionQueryDTO queryDTO);

    /**
     * 查询商户额度提取分页（财务报表导出）
     * @param queryDTO
     * @return DodopalResponse<DodopalDataPage<PayTraTransactionDTO>>
     */
    DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> findMerCreditsByPage(PayTraTransactionQueryDTO queryDTO);

    /**
     * 查询商户额度提取分页(oss系统)导出
     * @return
     */
    DodopalResponse<List<PayTraTransactionDTO>>  findMerCreditsExport(PayTraTransactionQueryDTO queryDTO);
    
    /**
     * 查询子商户交易记录
     * @param merParentCode
     * @return DodopalResponse<List<PayTraTransactionDTO>>
     */
    DodopalResponse<List<PayTraTransactionDTO>> findTraRecordByMerParentCode(String merParentCode);
    /**
     * 门户系统    转账  生成交易流水
     * @param payTransferDTO
     * @return
     */
    DodopalResponse<Boolean> transfercreateTran(List<PayTransferDTO> payTransferDTOList);
    
    /**
     * 查询交易记录 （手机端和VC端）
     * @param requestDto
     * @return
     */
    DodopalResponse<List<TranscationListResultDTO>> queryPayTranscation(TranscationRequestDTO requestDto);

}
