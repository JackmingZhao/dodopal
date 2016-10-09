package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayTraTransactionDTO;
import com.dodopal.api.payment.dto.query.PayTraTransactionQueryDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ServiceRateTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.TraTransactionBean;
import com.dodopal.oss.business.dao.PayTraTransactionMapper;
import com.dodopal.oss.business.model.PayTraTransaction;
import com.dodopal.oss.business.model.dto.MerCreditsQuery;
import com.dodopal.oss.business.model.dto.PayTraTransactionQuery;
import com.dodopal.oss.business.model.dto.TraTransactionQuery;
import com.dodopal.oss.business.service.TraTransactionService;
import com.dodopal.oss.delegate.MerchantDelegate;
import com.dodopal.oss.delegate.TraTransactionDelegate;

@Service
public class TraTransactionServiceImpl implements TraTransactionService {

    private final static Logger log = LoggerFactory.getLogger(TraTransactionServiceImpl.class);

    @Autowired
     private TraTransactionDelegate traTransactionDelegate;
    @Autowired
     private MerchantDelegate merchantDelegate;
    @Autowired
     private PayTraTransactionMapper payTraTransactionMapper;
    /**
     * 查询交易流水（分页）
     * @param traQuery
     * @return
     */
 public DodopalResponse<DodopalDataPage<TraTransactionBean>> findTraTransactionsByPage(TraTransactionQuery traQuery) {
        DodopalResponse<DodopalDataPage<TraTransactionBean>> response = new DodopalResponse<DodopalDataPage<TraTransactionBean>>();
        try {
            PayTraTransactionQueryDTO traDTO = new PayTraTransactionQueryDTO();
            queryToDto(traQuery, traDTO);
            //PropertyUtils.copyProperties(traDTO, traQuery);
            //鏌ヨ浜ゆ槗娴佹按璁板綍
            DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> PayTraTransactionDTOList =  traTransactionDelegate.findPayTraTransactionByPage(traDTO);

            if (ResponseCode.SUCCESS.equals(PayTraTransactionDTOList.getCode())) {

                List<TraTransactionBean> TraTransactionBeanList = new ArrayList<TraTransactionBean>();

                if (PayTraTransactionDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(PayTraTransactionDTOList.getResponseEntity().getRecords())) {
                    for (PayTraTransactionDTO traDto : PayTraTransactionDTOList.getResponseEntity().getRecords()) {
                        TraTransactionBean traBean = new TraTransactionBean();

                        PropertyUtils.copyProperties(traBean, traDto);
                        traBean.setAmountMoney((double)traDto.getAmountMoney()/100);;
                        traBean.setRealTranMoney((double)traDto.getRealTranMoney()/100);
                        TraTransactionBeanList.add(traBean);

                    }
                  
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(PayTraTransactionDTOList.getResponseEntity());
                DodopalDataPage<TraTransactionBean> pages = new DodopalDataPage<TraTransactionBean>(page, TraTransactionBeanList);
                response.setCode(PayTraTransactionDTOList.getCode());
                response.setResponseEntity(pages);
            }
        }
        catch (HessianRuntimeException e) {
            log.debug("TraTransactionServiceImpl call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("TraTransactionServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    
 private void queryToDto(TraTransactionQuery query,PayTraTransactionQueryDTO queryDTO) {

         queryDTO.setCreateDateStart(query.getCreateDateStart());
  
     
         queryDTO.setCreateDateEnd(query.getCreateDateEnd());
     
     if (StringUtils.isNotBlank(query.getRealMinTranMoney())) {
         if(StringUtils.isNotBlank(query.getRealMaxTranMoney())){
             if(Double.parseDouble(query.getRealMaxTranMoney()) < Double.parseDouble(query.getRealMinTranMoney())){
                 queryDTO.setRealMinTranMoney(new Double(Double.parseDouble(query.getRealMaxTranMoney())*100).longValue());
             }else{
                 queryDTO.setRealMinTranMoney(new Double(Double.parseDouble(query.getRealMinTranMoney())*100).longValue());
             }
         }else{
             queryDTO.setRealMinTranMoney(new Double(Double.parseDouble(query.getRealMinTranMoney())*100).longValue());
         }
     }
     
     if (StringUtils.isNotBlank(query.getRealMaxTranMoney())) {
         if(StringUtils.isNotBlank(query.getRealMinTranMoney())){
             if(Double.parseDouble(query.getRealMaxTranMoney()) < Double.parseDouble(query.getRealMinTranMoney())){
                 queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(query.getRealMinTranMoney())*100).longValue());
             }else{
                 queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(query.getRealMaxTranMoney())*100).longValue());
             }
         }else{
             queryDTO.setRealMaxTranMoney(new Double(Double.parseDouble(query.getRealMaxTranMoney())*100).longValue());
         }
     }
     
     if (StringUtils.isNotBlank(query.getTranCode())) {
         queryDTO.setTranCode(query.getTranCode());
     }
     
     if (StringUtils.isNotBlank(query.getOrderNumber())) {
         queryDTO.setOrderNumber(query.getOrderNumber());
     }
     
     if (StringUtils.isNotBlank(query.getTranOutStatus())) {
         queryDTO.setTranOutStatus(query.getTranOutStatus());
     }
     
     if (StringUtils.isNotBlank(query.getTranInStatus())) {
         queryDTO.setTranInStatus(query.getTranInStatus());
     }
     
     if (StringUtils.isNotBlank(query.getMerOrUserName())) {
         queryDTO.setMerOrUserName(query.getMerOrUserName());
     }
     
     if (StringUtils.isNotBlank(query.getBusinessType())) {
         queryDTO.setBusinessType(query.getBusinessType());
     }
     
   //分页参数
     if (query.getPage() != null) {
         queryDTO.setPage(query.getPage());;
     }
 }
 
 
    
    /**
     * 查询交易流水详情
     * @param traCode
     * @return
     */
     public DodopalResponse<TraTransactionBean> findTraTransaction(String id) {

       DodopalResponse<TraTransactionBean> rtResponse = new DodopalResponse<TraTransactionBean>();
        try {

            //鏍规嵁浜ゆ槗娴佹按鍙锋煡璇氦鏄撴祦姘磋鎯�
            DodopalResponse<PayTraTransactionDTO> getResponse = traTransactionDelegate.findTraTransaction(id);
            if (ResponseCode.SUCCESS.equals(getResponse.getCode())&&getResponse.getResponseEntity()!=null) {
                PayTraTransactionDTO traDTO = getResponse.getResponseEntity();
                TraTransactionBean traBean = new TraTransactionBean();
                //杞负鍓嶇瀵硅薄
                PropertyUtils.copyProperties(traBean, traDTO);
                traBean.setAmountMoney((double)traDTO.getAmountMoney()/100);;
                traBean.setRealTranMoney((double)traDTO.getRealTranMoney()/100);
                traBean.setPayProceFee((double)traDTO.getPayProceFee()/100);
                traBean.setPayServiceFee((double)traDTO.getPayServiceFee()/100);
                
                if(ServiceRateTypeEnum.SINGLE_AMOUNT.getCode().equals(traDTO.getPayServiceType()) ){
                    traBean.setPayServiceRate((double)traDTO.getPayServiceRate()/100);
                }else{
                    traBean.setPayServiceRate(traDTO.getPayServiceRate());
                }
                
                String turnIntoMerCode =traDTO.getTurnIntoMerCode();
                String turnOutMerCode =traDTO.getTurnOutMerCode();
                if(turnIntoMerCode!=null){
                    DodopalResponse<MerchantDTO>  rtMerchantDTO = merchantDelegate.findMerchants(turnIntoMerCode);
                    if(rtMerchantDTO.getCode().equals(ResponseCode.SUCCESS)&&rtMerchantDTO.getResponseEntity()!=null){
                        traBean.setTurnIntoMerCode(rtMerchantDTO.getResponseEntity().getMerName());
                    }
                }
                if(turnOutMerCode!=null){
                    DodopalResponse<MerchantDTO>  rtMerchantDTO = merchantDelegate.findMerchants(turnOutMerCode);
                    if(rtMerchantDTO.getCode().equals(ResponseCode.SUCCESS)&&rtMerchantDTO.getResponseEntity()!=null){
                        traBean.setTurnOutMerCode(rtMerchantDTO.getResponseEntity().getMerName());
                    }
                }
                
                rtResponse.setResponseEntity(traBean);
                rtResponse.setCode(ResponseCode.SUCCESS);
            } else {
                rtResponse.setCode(getResponse.getCode());
            }
        }
        catch (HessianRuntimeException e) {
            log.debug("TraTransactionServiceImpl call HessianRuntimeException error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("TraTransactionServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
             //throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }
        return rtResponse;
    }

    
    //导出交易流水
    public DodopalResponse<List<TraTransactionBean>> exportTransaction(TraTransactionQuery traQuery) {
        DodopalResponse<List<TraTransactionBean>> excelExport = new DodopalResponse<List<TraTransactionBean>>();
        try {
            PayTraTransactionQueryDTO traDTO = new PayTraTransactionQueryDTO();
            queryToDto(traQuery, traDTO);
            DodopalResponse<List<PayTraTransactionDTO>> PayTraTransactionDTOList =  traTransactionDelegate.findTrasactionExport(traDTO);
            List<TraTransactionBean> TraTransactionBeanList = new ArrayList<TraTransactionBean>();
            if (ResponseCode.SUCCESS.equals(PayTraTransactionDTOList.getCode())) {
                if (PayTraTransactionDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(PayTraTransactionDTOList.getResponseEntity())) {
                    for (PayTraTransactionDTO traDto : PayTraTransactionDTOList.getResponseEntity()) {
                        TraTransactionBean traBean = new TraTransactionBean();
                        PropertyUtils.copyProperties(traBean, traDto);
                        traBean.setAmountMoney((double)traDto.getAmountMoney()/100);
                        traBean.setRealTranMoney((double)traDto.getRealTranMoney()/100);
                        TraTransactionBeanList.add(traBean);
                    }
                }
            }
            excelExport.setCode(PayTraTransactionDTOList.getCode());
            
            excelExport.setResponseEntity(TraTransactionBeanList);
        } 
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return excelExport;
    }
    
    public DodopalResponse<DodopalDataPage<TraTransactionBean>> findMerCreditsByPage(MerCreditsQuery traQuery) {
        DodopalResponse<DodopalDataPage<TraTransactionBean>> response = new DodopalResponse<DodopalDataPage<TraTransactionBean>>();
        try {
            PayTraTransactionQueryDTO traDTO = new PayTraTransactionQueryDTO();
//            queryToDto(traQuery, traDTO);
            PropertyUtils.copyProperties(traDTO, traQuery);
            
            DodopalResponse<DodopalDataPage<PayTraTransactionDTO>> PayTraTransactionDTOList =  traTransactionDelegate.findMerCreditsByPage(traDTO);

            if (ResponseCode.SUCCESS.equals(PayTraTransactionDTOList.getCode())) {

                List<TraTransactionBean> TraTransactionBeanList = new ArrayList<TraTransactionBean>();

                if (PayTraTransactionDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(PayTraTransactionDTOList.getResponseEntity().getRecords())) {
                    for (PayTraTransactionDTO traDto : PayTraTransactionDTOList.getResponseEntity().getRecords()) {
                        TraTransactionBean traBean = new TraTransactionBean();

                        PropertyUtils.copyProperties(traBean, traDto);
                        traBean.setAmountMoney((double)traDto.getAmountMoney()/100);;
                        traBean.setRealTranMoney((double)traDto.getRealTranMoney()/100);
                        TraTransactionBeanList.add(traBean);

                    }
                  
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(PayTraTransactionDTOList.getResponseEntity());
                DodopalDataPage<TraTransactionBean> pages = new DodopalDataPage<TraTransactionBean>(page, TraTransactionBeanList);
                response.setResponseEntity(pages);
            }
            response.setCode(PayTraTransactionDTOList.getCode());
        }
        catch (HessianRuntimeException e) {
            log.debug("TraTransactionServiceImpl call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("TraTransactionServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }
    


    @Override
    @Transactional
    public List<PayTraTransaction> findPayTraTransactionByList(int threshold) {
        return payTraTransactionMapper.findPayTraTransactionByList(threshold);
    }


    @Override
    public DodopalResponse<List<TraTransactionBean>> findMerCreditsExport(MerCreditsQuery queryDTO) {
        DodopalResponse<List<TraTransactionBean>> response = new DodopalResponse<List<TraTransactionBean>>();
        try {
            PayTraTransactionQueryDTO traDTO = new PayTraTransactionQueryDTO();
//            queryToDto(traQuery, traDTO);
            PropertyUtils.copyProperties(traDTO, queryDTO);
            DodopalResponse<List<PayTraTransactionDTO>> PayTraTransactionDTOList =  traTransactionDelegate.findMerCreditsExport(traDTO);
            if (ResponseCode.SUCCESS.equals(PayTraTransactionDTOList.getCode())) { 
                List<TraTransactionBean> TraTransactionBeanList = new ArrayList<TraTransactionBean>();
                if (PayTraTransactionDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(PayTraTransactionDTOList.getResponseEntity())) {
                    for (PayTraTransactionDTO traDto : PayTraTransactionDTOList.getResponseEntity()) {
                        TraTransactionBean traBean = new TraTransactionBean();
                        PropertyUtils.copyProperties(traBean, traDto);
                        traBean.setAmountMoney((double)traDto.getAmountMoney()/100);;
                        traBean.setRealTranMoney((double)traDto.getRealTranMoney()/100);
                        TraTransactionBeanList.add(traBean);
                    }
                }
                response.setResponseEntity(TraTransactionBeanList);
            }
            response.setCode(PayTraTransactionDTOList.getCode());
        }catch (HessianRuntimeException e) {
            log.debug("TraTransactionServiceImpl call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("TraTransactionServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    @Override
    public DodopalResponse<DodopalDataPage<TraTransactionBean>> findMerCreditsNoHessionByPage(MerCreditsQuery query) {
        DodopalResponse<DodopalDataPage<TraTransactionBean>> response = new DodopalResponse<DodopalDataPage<TraTransactionBean>>();
        log.info("查询商户额度分页调用oss本地sql");
        try {
            PayTraTransactionQuery traquery = new PayTraTransactionQuery();
            PropertyUtils.copyProperties(traquery, query);
            List<PayTraTransaction> resultList =  payTraTransactionMapper.findMerCreditsByPage(traquery);
            log.info("查询到的数据条数"+resultList.size());
                List<TraTransactionBean> TraTransactionBeanList = new ArrayList<TraTransactionBean>();
                if (CollectionUtils.isNotEmpty(resultList)) {
                    for (PayTraTransaction bean : resultList) {
                        TraTransactionBean traBean = new TraTransactionBean();
                        PropertyUtils.copyProperties(traBean, bean);
                        traBean.setAmountMoney((double)bean.getAmountMoney()/100);;
                        traBean.setRealTranMoney((double)bean.getRealTranMoney()/100);
                        TraTransactionBeanList.add(traBean);
                    }
                  
                }
                DodopalDataPage<TraTransactionBean> pages = new DodopalDataPage<TraTransactionBean>(query.getPage(), TraTransactionBeanList);
                response.setResponseEntity(pages);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            log.debug("TraTransactionServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // throw new DDPException(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }


    @Override
    public DodopalResponse<List<TraTransactionBean>> findMerCreditsNoHessionExport(MerCreditsQuery queryDTO) {
        log.info("查询商户额度分页导出调用oss本地sql");
        DodopalResponse<List<TraTransactionBean>> response = new DodopalResponse<List<TraTransactionBean>>();
        try {
            PayTraTransactionQuery query = new PayTraTransactionQuery();
//            queryToDto(traQuery, traDTO);
            PropertyUtils.copyProperties(query, queryDTO);
            List<PayTraTransaction> resultList = payTraTransactionMapper.findMerCreditsExport(query);
            log.info("查询到的数据条数"+resultList.size());
            List<TraTransactionBean> TraTransactionBeanList = new ArrayList<TraTransactionBean>();
            if (CollectionUtils.isNotEmpty(resultList)) {
                for (PayTraTransaction bean : resultList) {
                    TraTransactionBean traBean = new TraTransactionBean();
                    PropertyUtils.copyProperties(traBean, bean);
                    traBean.setAmountMoney((double)bean.getAmountMoney()/100);;
                    traBean.setRealTranMoney((double)bean.getRealTranMoney()/100);
                    TraTransactionBeanList.add(traBean);
                }
            }
            response.setResponseEntity(TraTransactionBeanList);
            response.setCode(ResponseCode.SUCCESS);
        }catch (Exception e) {
            log.debug("TraTransactionServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }
}
