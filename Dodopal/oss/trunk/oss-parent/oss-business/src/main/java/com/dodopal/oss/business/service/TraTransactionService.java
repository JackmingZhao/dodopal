package com.dodopal.oss.business.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.payment.facade.PayTraTransactionFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.TraTransactionBean;
import com.dodopal.oss.business.model.PayTraTransaction;
import com.dodopal.oss.business.model.dto.MerCreditsQuery;
import com.dodopal.oss.business.model.dto.TraTransactionQuery;
import com.dodopal.oss.delegate.constant.DelegateConstant;

public interface TraTransactionService {

    /**
     * 查询交易流水
     * @param traQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<TraTransactionBean>> findTraTransactionsByPage(TraTransactionQuery traQuery);

    /**
     * 交易流水详情
     * @param traCode 交易流水号
     * @return
     */
    public DodopalResponse<TraTransactionBean> findTraTransaction(String id);
    
    /**
     * 导出交易流水
     * @param traQuery
     * @return
     */
    public DodopalResponse<List<TraTransactionBean>> exportTransaction(TraTransactionQuery traQuery);
    
    /**
     * 账户充值  异常交易流水补单程序  查询交易流水 
     * @param threshold 时间阀值
     * @return
     */
    public List<PayTraTransaction> findPayTraTransactionByList(@Param("threshold")int threshold);
    

    /**
     * 查询商户额度提取分页（财务报表导出）
     * @param traQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<TraTransactionBean>> findMerCreditsByPage(MerCreditsQuery query);
    
    public DodopalResponse<List<TraTransactionBean>> findMerCreditsExport(MerCreditsQuery queryDTO);
    
    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月21日 下午5:52:12 
      * @version 1.0 
      * @parameter  
      * @描述 oss本地查询 查询商户额度提取分页
      * @return  
      */
    public DodopalResponse<DodopalDataPage<TraTransactionBean>> findMerCreditsNoHessionByPage(MerCreditsQuery query);

    /** 
      * @author  Dingkuiyuan@dodopal.com 
      * @date 创建时间：2016年4月21日 下午5:52:24 
      * @version 1.0 
      * @parameter  
      * @描述 oss本地查询 查询商户额度导出
      * @return  
      */
    public DodopalResponse<List<TraTransactionBean>> findMerCreditsNoHessionExport(MerCreditsQuery queryDTO);

}
