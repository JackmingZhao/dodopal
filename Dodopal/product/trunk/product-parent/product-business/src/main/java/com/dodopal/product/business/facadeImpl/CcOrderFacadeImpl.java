package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.CcOrderFormDTO;
import com.dodopal.api.product.dto.CzOrderByPosCountDTO;
import com.dodopal.api.product.dto.CzOrderByPosDTO;
import com.dodopal.api.product.dto.query.QueryCcOrderFormDTO;
import com.dodopal.api.product.dto.query.QueryCzOrderByPosCountDTO;
import com.dodopal.api.product.facade.CcOrderFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.CcOrderForm;
import com.dodopal.product.business.model.CzOrderByPosCount;
import com.dodopal.product.business.model.CzOrderByPosCountAll;
import com.dodopal.product.business.model.query.QueryCcOrderForm;
import com.dodopal.product.business.model.query.QueryCzOrderByPosCount;
import com.dodopal.product.business.service.CcOrderService;
@Service("ccOrderFacade")
public class CcOrderFacadeImpl implements CcOrderFacade {
    private final static Logger logger = LoggerFactory.getLogger(CcOrderFacadeImpl.class);
    @Autowired
    CcOrderService ccOrderService;
    
    
    @Override
    public DodopalResponse<DodopalDataPage<CcOrderFormDTO>> findCcOrderFormByPage(QueryCcOrderFormDTO queryCcOrderFormDTO) {
        DodopalResponse<DodopalDataPage<CcOrderFormDTO>> response = new DodopalResponse<DodopalDataPage<CcOrderFormDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            QueryCcOrderForm queryDTO = new QueryCcOrderForm();
            BeanUtils.copyProperties(queryCcOrderFormDTO, queryDTO);
            //分页总条数如果为空新塞值进去
            if (queryDTO.getPage() == null) {
                queryDTO.setPage(new PageParameter());
            }
            DodopalDataPage<CcOrderForm> ddpResult = ccOrderService.findCcOrderFormByPage(queryDTO);
            if (ddpResult != null) {
                List<CcOrderForm> resultList = ddpResult.getRecords();
                List<CcOrderFormDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<CcOrderFormDTO>(resultList.size());
                    for (CcOrderForm cor : resultList) {
                        CcOrderFormDTO cofDto = new CcOrderFormDTO();
                        cofDto.setAmt(cor.getAmt());
                        cofDto.setMchnitname(cor.getMchnitname());
                        cofDto.setUserid(cor.getUserid());
                        cofDto.setBankorderid(cor.getBankorderid());
                        cofDto.setFlamt(cor.getFlamt());
                        cofDto.setPaidamt(cor.getPaidamt());
                        cofDto.setPosid(cor.getPosid());
                        cofDto.setCardno(cor.getCardno());
                        cofDto.setSenddate(cor.getSenddate());
                        cofDto.setSendtime(cor.getSendtime());
                        cofDto.setStatus(cor.getStatus());
                        resResultList.add(cofDto);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                DodopalDataPage<CcOrderFormDTO> ddpDTOResult = new DodopalDataPage<CcOrderFormDTO>(page, resResultList);
                response.setResponseEntity(ddpDTOResult);
            } else {
                response.setCode(ResponseCode.V71_POS_ERROR);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        
        return response;
    }


    @Override
    public DodopalResponse<DodopalDataPage<CzOrderByPosDTO>> findCzOrderByPosCountByPage(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDTO) {
        DodopalResponse<DodopalDataPage<CzOrderByPosDTO>> response = new DodopalResponse<DodopalDataPage<CzOrderByPosDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            QueryCzOrderByPosCount queryCzOrderByPosCount = new QueryCzOrderByPosCount();
            BeanUtils.copyProperties(queryCzOrderByPosCountDTO, queryCzOrderByPosCount);
            //分页总条数如果为空新塞值进去
            if (queryCzOrderByPosCountDTO.getPage() == null) {
                queryCzOrderByPosCountDTO.setPage(new PageParameter());
            }
            DodopalDataPage<CzOrderByPosCount> ddpResult = ccOrderService.findCzOrderByPosCountByPage(queryCzOrderByPosCount);
            if (ddpResult != null) {
              
                List<CzOrderByPosCount> resultList = ddpResult.getRecords();
                List<CzOrderByPosDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<CzOrderByPosDTO>(resultList.size());
                    for (CzOrderByPosCount czByPosCount : resultList) {
                        CzOrderByPosDTO czDto = new CzOrderByPosDTO();
                        czDto.setPosid(czByPosCount.getCheckcardposid());
                        czDto.setUsername(czByPosCount.getUsername());
                        czDto.setTradeCount(czByPosCount.getJiaoyichenggongbishu()+czByPosCount.getJiaoyishibaibishu()+czByPosCount.getJiaoyikeyibishu());
                        czDto.setTradeMoney(czByPosCount.getJiaoyichenggongjine()+czByPosCount.getJiaoyishibaijine()+czByPosCount.getJiaoyikeyijine());
                        czDto.setTradeSucceedCount(czByPosCount.getJiaoyichenggongbishu());
                        czDto.setTradeSucceedMoney(czByPosCount.getJiaoyichenggongjine());
                        czDto.setTradeErrorCount(czByPosCount.getJiaoyishibaibishu());
                        czDto.setTradeErrorMoney(czByPosCount.getJiaoyishibaijine());
                        czDto.setDoubtfulTradeCount(czByPosCount.getJiaoyikeyibishu());
                        czDto.setDoubtfulTradeMoney(czByPosCount.getJiaoyikeyijine());
                        resResultList.add(czDto);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                DodopalDataPage<CzOrderByPosDTO> ddpDTOResult = new DodopalDataPage<CzOrderByPosDTO>(page, resResultList);
                response.setResponseEntity(ddpDTOResult);
            } else {
                response.setCode(ResponseCode.V71_POS_ERROR);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    @Override
    public DodopalResponse<CzOrderByPosCountDTO> findCzOrderByPosCountAll(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDTO) {
        DodopalResponse<CzOrderByPosCountDTO> response = new DodopalResponse<CzOrderByPosCountDTO>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            DodopalResponse<CzOrderByPosCountAll> czOrderByPosCount = new DodopalResponse<CzOrderByPosCountAll>();
            QueryCzOrderByPosCount queryCzOrderByPosCount = new QueryCzOrderByPosCount();
            BeanUtils.copyProperties(queryCzOrderByPosCountDTO, queryCzOrderByPosCount);
            czOrderByPosCount = ccOrderService.findCzOrderByPosCountAll(queryCzOrderByPosCount);
            if (czOrderByPosCount != null) {
                CzOrderByPosCountDTO czDto = new CzOrderByPosCountDTO();
                CzOrderByPosCountAll czOrderByPosCountAll = czOrderByPosCount.getResponseEntity();
                if(czOrderByPosCountAll!=null ){
                        czDto.setTradeCountSum(czOrderByPosCountAll.getJiaoyichenggongbishu()+czOrderByPosCountAll.getJiaoyishibaibishu()+czOrderByPosCountAll.getJiaoyikeyibishu());
                        czDto.setTradeMoneySum(czOrderByPosCountAll.getJiaoyichenggongjine()+czOrderByPosCountAll.getJiaoyishibaijine()+czOrderByPosCountAll.getJiaoyikeyijine());
                        czDto.setTradeSucceedCountSum(czOrderByPosCountAll.getJiaoyichenggongbishu());
                        czDto.setTradeSucceedMoneySum(czOrderByPosCountAll.getJiaoyichenggongjine());
                        czDto.setTradeErrorCountSum(czOrderByPosCountAll.getJiaoyishibaibishu());
                        czDto.setTradeErrorMoneySum(czOrderByPosCountAll.getJiaoyishibaijine());
                        czDto.setDoubtfulTradeCountSum(czOrderByPosCountAll.getJiaoyikeyibishu());
                        czDto.setDoubtfulTradeMoneySum(czOrderByPosCountAll.getJiaoyikeyijine());
                }
                response.setResponseEntity(czDto);
            } else {
                response.setCode(ResponseCode.V71_POS_ERROR);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    @Override
    public DodopalResponse<List<CzOrderByPosDTO>> findCzOrderByPosCountExcel(QueryCzOrderByPosCountDTO queryCzOrderByPosCountDTO) {
        DodopalResponse<List<CzOrderByPosDTO>> response = new DodopalResponse<List<CzOrderByPosDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            QueryCzOrderByPosCount queryCzOrderByPosCount = new QueryCzOrderByPosCount();
            BeanUtils.copyProperties(queryCzOrderByPosCountDTO, queryCzOrderByPosCount);
            //分页总条数如果为空新塞值进去
            if (queryCzOrderByPosCountDTO.getPage() == null) {
                queryCzOrderByPosCountDTO.setPage(new PageParameter());
            }
            DodopalResponse<List<CzOrderByPosCount>> ddpResult = ccOrderService.findCzOrderByPosCountExcel(queryCzOrderByPosCount);
            if (ddpResult != null) {
                List<CzOrderByPosCount> resultList = ddpResult.getResponseEntity();
                List<CzOrderByPosDTO> resResultList = new ArrayList<CzOrderByPosDTO>();
                if (resultList != null && resultList.size() > 0) {
                    for (CzOrderByPosCount czByPosCount : resultList) {
                        CzOrderByPosDTO czDto = new CzOrderByPosDTO();
                        czDto.setPosid(czByPosCount.getCheckcardposid());
                        czDto.setUsername(czByPosCount.getUsername());
                        czDto.setTradeCount(czByPosCount.getJiaoyichenggongbishu()+czByPosCount.getJiaoyishibaibishu()+czByPosCount.getJiaoyikeyibishu());
                        czDto.setTradeMoney(czByPosCount.getJiaoyichenggongjine()+czByPosCount.getJiaoyishibaijine()+czByPosCount.getJiaoyikeyijine());
                        czDto.setTradeSucceedCount(czByPosCount.getJiaoyichenggongbishu());
                        czDto.setTradeSucceedMoney(czByPosCount.getJiaoyichenggongjine());
                        czDto.setTradeErrorCount(czByPosCount.getJiaoyishibaibishu());
                        czDto.setTradeErrorMoney(czByPosCount.getJiaoyishibaijine());
                        czDto.setDoubtfulTradeCount(czByPosCount.getJiaoyikeyibishu());
                        czDto.setDoubtfulTradeMoney(czByPosCount.getJiaoyikeyijine());
                        resResultList.add(czDto);
                    }
                }
                response.setResponseEntity(resResultList);
            } else {
                response.setCode(ResponseCode.V71_POS_ERROR);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }


    @Override
    public DodopalResponse<List<CcOrderFormDTO>> findCcOrderFormExcel(QueryCcOrderFormDTO queryCcOrderFormDTO) {
        DodopalResponse<List<CcOrderFormDTO>> response = new DodopalResponse<List<CcOrderFormDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            QueryCcOrderForm queryCcOrderForm = new QueryCcOrderForm();
            BeanUtils.copyProperties(queryCcOrderFormDTO, queryCcOrderForm);
            //分页总条数如果为空新塞值进去
            if (queryCcOrderForm.getPage() == null) {
                queryCcOrderForm.setPage(new PageParameter());
            }
            DodopalResponse<List<CcOrderForm>> ddpResult = ccOrderService.findCcOrderFormExcel(queryCcOrderForm);
            if (ddpResult != null) {
                List<CcOrderForm> resultList = ddpResult.getResponseEntity();
                List<CcOrderFormDTO> resResultList = new ArrayList<CcOrderFormDTO>();
                if (resultList != null && resultList.size() > 0) {
                    for (CcOrderForm cor : resultList) {
                        CcOrderFormDTO cofDto = new CcOrderFormDTO();
                        cofDto.setAmt(cor.getAmt());
                        cofDto.setMchnitname(cor.getMchnitname());
                        cofDto.setUserid(cor.getUserid());
                        cofDto.setBankorderid(cor.getBankorderid());
                        cofDto.setFlamt(cor.getFlamt());
                        cofDto.setPaidamt(cor.getPaidamt());
                        cofDto.setPosid(cor.getPosid());
                        cofDto.setCardno(cor.getCardno());
                        cofDto.setSenddate(cor.getSenddate());
                        cofDto.setSendtime(cor.getSendtime());
                        cofDto.setStatus(cor.getStatus());
                        resResultList.add(cofDto);
                    }
                }
                response.setResponseEntity(resResultList);
            } else {
                response.setCode(ResponseCode.V71_POS_ERROR);
            }
        }
        catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        
        return response;
    }

}
