package com.dodopal.card.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysOrderTDTO;
import com.dodopal.api.card.dto.CrdSysSupplementTDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.api.card.facade.QueryCrdSysOrderFacade;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;
import com.dodopal.card.business.service.QueryCrdSysOrderService;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.PrdYktInfo;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.PrdYktInfoService;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;

@Service("queryCrdSysOrderFacade")
public class QueryCrdSysOrderFacadeImpl implements QueryCrdSysOrderFacade {

    @Autowired
    QueryCrdSysOrderService queryCardOrderService;
    @Autowired
    AreaService areaService;
    @Autowired
    PrdYktInfoService prdYktInfoService;

    /**
     * @Title: findCrdSysOrderByPage
     * @Description: 查询卡服务订单
     * @param crdSysOrderQueryDTO
     * @return 集合
     * @Name 乔
     */
    @Override
    public DodopalResponse<DodopalDataPage<CrdSysOrderTDTO>> findCrdSysOrderByPage(CrdSysOrderQueryDTO crdSysOrderQueryDTO) {
        DodopalResponse<DodopalDataPage<CrdSysOrderTDTO>> response = new DodopalResponse<DodopalDataPage<CrdSysOrderTDTO>>();
        response.setCode(ResponseCode.SUCCESS);
        try {
            //1.重新生成Query查询条件，model层的
            CrdSysOrderQuery crdSysOrderQuery = new CrdSysOrderQuery();
            //2.当DTO和QUERY一样，直接COPY。
            BeanUtils.copyProperties(crdSysOrderQueryDTO, crdSysOrderQuery);
            if (crdSysOrderQuery.getPage() == null) {
                crdSysOrderQuery.setPage(new PageParameter());
            }

            DodopalDataPage<CrdSysOrder> ddpResult = queryCardOrderService.findCrdSysOrderByPage(crdSysOrderQuery);
            if (ddpResult != null) {
                List<CrdSysOrder> resultList = ddpResult.getRecords();

                List<CrdSysOrderTDTO> resResultList = null;
                if (resultList != null && resultList.size() > 0) {
                    resResultList = new ArrayList<CrdSysOrderTDTO>(resultList.size());
                    for (CrdSysOrder crd : resultList) {
                        //城市名称
                        String cityCode = crd.getCityCode();
                        if (StringUtils.isNotBlank(cityCode)) {
                            Area area = areaService.findCityByCityCode(cityCode);
                            if (area != null) {
                                crd.setCityName(area.getCityName());
                            }
                        }

                        //一卡通名称
                        String yktCode = crd.getYktCode();
                        if (StringUtils.isNotBlank(yktCode)) {
                            PrdYktInfo prd = prdYktInfoService.findPrdYktInfoYktCode(yktCode);
                            if (prd != null) {
                                crd.setYktName(prd.getYktName());
                            }
                        }
                        CrdSysOrderTDTO crdDTO = new CrdSysOrderTDTO();
                        crdDTO.setId(crd.getId());
                        crdDTO.setCrdOrderNum(crd.getCrdOrderNum());
                        crdDTO.setProOrderNum(crd.getProOrderNum());
                        crdDTO.setCheckCardNo(crd.getCheckCardNo());
                        crdDTO.setProCode(crd.getProCode());
                        crdDTO.setProName(crd.getProName());
                        crdDTO.setYktCode(crd.getYktCode());
                        crdDTO.setCityCode(crd.getCityCode());
                        crdDTO.setMerCode(crd.getMerCode());
                        crdDTO.setCardFaceNo(crd.getCardFaceNo());
                        crdDTO.setPosCode(crd.getPosCode());
                        crdDTO.setUserCode(crd.getUserCode());
                        crdDTO.setBefbal(crd.getBefbal());
                        crdDTO.setTxnAmt(crd.getTxnAmt());
                        crdDTO.setBlackAmt(crd.getBlackAmt());
                        crdDTO.setCrdOrderStates(crd.getCrdOrderStates());
                        crdDTO.setCrdBeforeorderStates(crd.getCrdBeforeorderStates());
                        crdDTO.setYktName(crd.getYktName());
                        crdDTO.setCityName(crd.getCityName());
                        crdDTO.setCardType(crd.getCardType());
                        crdDTO.setTxnDate(crd.getTxnDate());
                        crdDTO.setTxnTime(crd.getTxnTime());
                        crdDTO.setMerName(crd.getMerName());
                        crdDTO.setMerUserNickName(crd.getMerUserNickName());
                        crdDTO.setChargeCardNo(crd.getChargeCardNo());
                        resResultList.add(crdDTO);
                    }
                }
                PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
                DodopalDataPage<CrdSysOrderTDTO> ddpDTOResult = new DodopalDataPage<CrdSysOrderTDTO>(page, resResultList);
                response.setResponseEntity(ddpDTOResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 查询卡服务订单详情
     */
    @Override
    public DodopalResponse<CrdSysOrderTDTO> findCrdSysOrderByCode(String crdOrderNum) {
        DodopalResponse<CrdSysOrderTDTO> response = new DodopalResponse<CrdSysOrderTDTO>();
        try {
            CrdSysOrder crdSysOrder = queryCardOrderService.findCrdSysOrderByCode(crdOrderNum);
            CrdSysSupplement crdSysSupplement = queryCardOrderService.findCrdSysSupplementByCode(crdOrderNum);
            CrdSysOrderTDTO crdSysOrderTDTO = new CrdSysOrderTDTO();

            //城市名称
            String cityCode = crdSysOrder.getCityCode();
            if (StringUtils.isNotBlank(cityCode)) {
                Area area = areaService.findCityByCityCode(cityCode);
                //edit by dky set对象错了
                if (area != null) {
                    crdSysOrderTDTO.setCityName(area.getCityName());
                }
            }

            //一卡通名称
            String yktCode = crdSysOrder.getYktCode();
            if (StringUtils.isNotBlank(yktCode)) {
                PrdYktInfo prd = prdYktInfoService.findPrdYktInfoYktCode(yktCode);
                if (prd != null) {
                    crdSysOrderTDTO.setYktName(prd.getYktName());
                }
            }

            crdSysOrderTDTO.setId(crdSysOrder.getId());
            crdSysOrderTDTO.setProOrderNum(crdSysOrder.getProOrderNum());
            crdSysOrderTDTO.setCrdOrderNum(crdSysOrder.getCrdOrderNum());
            crdSysOrderTDTO.setProCode(crdSysOrder.getProCode());
            crdSysOrderTDTO.setMerCode(crdSysOrder.getMerCode());
            crdSysOrderTDTO.setMerOrderCode(crdSysOrder.getMerOrderCode());
            crdSysOrderTDTO.setCrdOrderStates(crdSysOrder.getCrdOrderStates());
            crdSysOrderTDTO.setCrdBeforeorderStates(crdSysOrder.getCrdBeforeorderStates());
            crdSysOrderTDTO.setRespCode(crdSysOrder.getRespCode());
            crdSysOrderTDTO.setUserCode(crdSysOrder.getUserCode());
            crdSysOrderTDTO.setCityCode(crdSysOrder.getCityCode());
            crdSysOrderTDTO.setCardInnerNo(crdSysOrder.getCardInnerNo());
            crdSysOrderTDTO.setCardFaceNo(crdSysOrder.getCardFaceNo());
            crdSysOrderTDTO.setOrderCardNo(crdSysOrder.getOrderCardNo());
            crdSysOrderTDTO.setCardType(crdSysOrder.getCardType());
            crdSysOrderTDTO.setPosType(crdSysOrder.getPosType());
            if (crdSysOrder.getPosSeq() != null) {
                crdSysOrderTDTO.setPosSeq(crdSysOrder.getPosSeq());
            }
            crdSysOrderTDTO.setBefbal(crdSysOrder.getBefbal());
            crdSysOrderTDTO.setBlackAmt(crdSysOrder.getBlackAmt());
            crdSysOrderTDTO.setTxnAmt(crdSysOrder.getTxnAmt());
            crdSysOrderTDTO.setCardLimit(crdSysOrder.getCardLimit());
            crdSysOrderTDTO.setSource(crdSysOrder.getSource());
            crdSysOrderTDTO.setTxnType(crdSysOrder.getTxnType());
            crdSysOrderTDTO.setTxnSeq(crdSysOrder.getTxnSeq());
            crdSysOrderTDTO.setTxnDate(crdSysOrder.getTxnDate());
            crdSysOrderTDTO.setTxnTime(crdSysOrder.getTxnTime());
            crdSysOrderTDTO.setCheckCardNo(crdSysOrder.getCheckCardNo());
            crdSysOrderTDTO.setCheckSendCardDate(crdSysOrder.getCheckSendCardDate());
            crdSysOrderTDTO.setCheckResCardDate(crdSysOrder.getCheckResCardDate());
            crdSysOrderTDTO.setCheckCardPosCode(crdSysOrder.getCheckCardPosCode());
            crdSysOrderTDTO.setChargeCardNo(crdSysOrder.getChargeCardNo());
            crdSysOrderTDTO.setChargeCardPosCode(crdSysOrder.getChargeCardPosCode());
            crdSysOrderTDTO.setChargeSendCardDate(crdSysOrder.getChargeSendCardDate());
            crdSysOrderTDTO.setChargeResCardDate(crdSysOrder.getChargeResCardDate());
            crdSysOrderTDTO.setResultSendCardDate(crdSysOrder.getResultSendCardDate());
            crdSysOrderTDTO.setResultResCardDate(crdSysOrder.getResultResCardDate());
            crdSysOrderTDTO.setChargeType(crdSysOrder.getChargeType());
            if (crdSysOrder.getMetropassType() != null) {
                crdSysOrderTDTO.setMetropassType(crdSysOrder.getMetropassType());
            }
            crdSysOrderTDTO.setMetropassChargeStartDate(crdSysOrder.getMetropassChargeStartDate());
            crdSysOrderTDTO.setMetropassChargeEndDate(crdSysOrder.getMetropassChargeEndDate());
            if (crdSysOrder.getDounknowFlag() != null) {
                crdSysOrderTDTO.setDounknowFlag(crdSysOrder.getDounknowFlag());
            }
            crdSysOrderTDTO.setCardCounter(crdSysOrder.getCardCounter());
            crdSysOrderTDTO.setAfterCardNotes(crdSysOrder.getAfterCardNotes());
            crdSysOrderTDTO.setBeforeCardNotes(crdSysOrder.getBeforeCardNotes());
            crdSysOrderTDTO.setTradeStep(crdSysOrder.getTradeStep());
            crdSysOrderTDTO.setTradeStepVer(crdSysOrder.getTradeStepVer());
            crdSysOrderTDTO.setTradeEndFlag(crdSysOrder.getTradeEndFlag());

            //卡服务补充信息
            CrdSysSupplementTDTO crdSysSupplementTDTO = new CrdSysSupplementTDTO();
            crdSysSupplementTDTO.setId(crdSysSupplement.getId());
            crdSysSupplementTDTO.setCrdOrderNum(crdSysSupplement.getCrdOrderNum());
            crdSysSupplementTDTO.setTxnType(crdSysSupplement.getTxnType());
            crdSysSupplementTDTO.setCheckYktmw(crdSysSupplement.getCheckYktmw());
            crdSysSupplementTDTO.setCheckYktkey(crdSysSupplement.getCheckYktkey());
            crdSysSupplementTDTO.setChargeYktmw(crdSysSupplement.getChargeYktmw());
            crdSysSupplementTDTO.setChargeKey(crdSysSupplement.getChargeKey());
            crdSysSupplementTDTO.setResultYktmw(crdSysSupplement.getResultYktmw());
            crdSysSupplementTDTO.setTxnDate(crdSysSupplement.getTxnDate());
            crdSysSupplementTDTO.setTxnTime(crdSysSupplement.getTxnTime());
            if (crdSysSupplement.getLastReadKeyTime() != null) {
                crdSysSupplementTDTO.setLastReadKeyTime(crdSysSupplement.getLastReadKeyTime());
            }
            if (crdSysSupplement.getLastChargeKeyTime() != null) {
                crdSysSupplementTDTO.setLastChargeKeyTime(crdSysSupplement.getLastChargeKeyTime());
            }
            if (crdSysSupplement.getLastResultTime() != null) {
                crdSysSupplementTDTO.setLastResultTime(crdSysSupplement.getLastResultTime());
            }
            crdSysSupplementTDTO.setRequestReadKeyCount(crdSysSupplement.getRequestReadKeyCount());
            crdSysSupplementTDTO.setRequestChargeKeyCount(crdSysSupplement.getRequestChargeKeyCount());
            crdSysSupplementTDTO.setResultYktmw(crdSysSupplement.getResultYktmw());
            crdSysSupplementTDTO.setSendResultCount(crdSysSupplement.getSendResultCount());
            crdSysOrderTDTO.setCrdSysSupplementTDTO(crdSysSupplementTDTO);

            //创建人和创建时间
            crdSysOrderTDTO.setCreateUser(crdSysOrder.getCreateUser());
            crdSysOrderTDTO.setCreateDate(crdSysOrder.getCreateDate());
            crdSysOrderTDTO.setUpdateUser(crdSysOrder.getUpdateUser());
            crdSysOrderTDTO.setUpdateDate(crdSysOrder.getUpdateDate());
            response.setResponseEntity(crdSysOrderTDTO);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 导出卡服务订单
     */
    @Override
    public DodopalResponse<List<CrdSysOrderTDTO>> findCrdSysOrderAll(CrdSysOrderQueryDTO crdSysOrderQueryDTO) {
        DodopalResponse<List<CrdSysOrderTDTO>> response = new DodopalResponse<List<CrdSysOrderTDTO>>();
        try {
            //1.重新生成Query查询条件，model层的
            CrdSysOrderQuery crdSysOrderQuery = new CrdSysOrderQuery();
            //2.当DTO和QUERY一样，直接COPY。
            BeanUtils.copyProperties(crdSysOrderQueryDTO, crdSysOrderQuery);
            if (crdSysOrderQuery.getPage() == null) {
                crdSysOrderQuery.setPage(new PageParameter());
            }
            List<CrdSysOrder> resultList = queryCardOrderService.findCrdSysOrderAll(crdSysOrderQuery);
            if (resultList.size() != 0) {
                if (resultList.size() > ExcelUtil.EXPORT_MAX_COUNT) {
                    response.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
                } else {
                    List<CrdSysOrderTDTO> resResultList = null;
                    if (resultList != null && resultList.size() > 0) {
                        resResultList = new ArrayList<CrdSysOrderTDTO>(resultList.size());
                        for (CrdSysOrder crd : resultList) {
                            //城市名称
                            String cityCode = crd.getCityCode();
                            if (StringUtils.isNotBlank(cityCode)) {
                                Area area = areaService.findCityByCityCode(cityCode);
                                if (area != null) {
                                    crd.setCityName(area.getCityName());
                                }
                            }
                            //一卡通名称
                            String yktCode = crd.getYktCode();
                            if (StringUtils.isNotBlank(yktCode)) {
                                PrdYktInfo prd = prdYktInfoService.findPrdYktInfoYktCode(yktCode);
                                if (prd != null) {
                                    crd.setYktName(prd.getYktName());
                                }
                            }
                            CrdSysOrderTDTO crdDTO = new CrdSysOrderTDTO();
                            crdDTO.setId(crd.getId());
                            crdDTO.setCrdOrderNum(crd.getCrdOrderNum());
                            crdDTO.setProOrderNum(crd.getProOrderNum());
                            crdDTO.setCheckCardNo(crd.getCheckCardNo());
                            crdDTO.setProCode(crd.getProCode());
                            crdDTO.setProName(crd.getProName());
                            crdDTO.setYktCode(crd.getYktCode());
                            crdDTO.setCityCode(crd.getCityCode());
                            crdDTO.setMerCode(crd.getMerCode());
                            crdDTO.setCardFaceNo(crd.getCardFaceNo());
                            crdDTO.setPosCode(crd.getPosCode());
                            crdDTO.setUserCode(crd.getUserCode());
                            crdDTO.setBefbal(crd.getBefbal());
                            crdDTO.setTxnAmt(crd.getTxnAmt());
                            crdDTO.setBlackAmt(crd.getBlackAmt());
                            crdDTO.setCrdOrderStates(crd.getCrdOrderStates());
                            crdDTO.setCrdBeforeorderStates(crd.getCrdBeforeorderStates());
                            crdDTO.setYktName(crd.getYktName());
                            crdDTO.setCityName(crd.getCityName());
                            crdDTO.setCardType(crd.getCardType());
                            crdDTO.setTxnDate(crd.getTxnDate());
                            crdDTO.setTxnTime(crd.getTxnTime());
                            crdDTO.setMerUserNickName(crd.getMerUserNickName());
                            crdDTO.setChargeCardNo(crd.getChargeCardNo());
                            
                            crdDTO.setRespCode(crd.getRespCode());
                            crdDTO.setCardInnerNo(crd.getCardInnerNo());
                            crdDTO.setOrderCardNo(crd.getOrderCardNo());
                            crdDTO.setPosType(crd.getPosType());
                            crdDTO.setPosSeq(crd.getPosSeq());
                            crdDTO.setCardLimit(crd.getCardLimit());
                            crdDTO.setSource(crd.getSource());
                            crdDTO.setTxnType(crd.getTxnType());
                            crdDTO.setTxnSeq(crd.getTxnSeq());
                            crdDTO.setCheckCardPosCode(crd.getCheckCardPosCode());
                            crdDTO.setCheckSendCardDate(crd.getCheckSendCardDate());
                            crdDTO.setCheckResCardDate(crd.getCheckResCardDate());
                            
                            crdDTO.setChargeCardNo(crd.getChargeCardNo());
                            crdDTO.setChargeCardPosCode(crd.getChargeCardPosCode());
                            crdDTO.setChargeSendCardDate(crd.getChargeSendCardDate());
                            crdDTO.setChargeResCardDate(crd.getChargeResCardDate());
                            crdDTO.setResultSendCardDate(crd.getResultSendCardDate());
                            crdDTO.setResultResCardDate(crd.getResultResCardDate());
                            crdDTO.setChargeType(crd.getChargeType());
                            crdDTO.setMetropassType(crd.getMetropassType());
                            crdDTO.setMetropassChargeStartDate(crd.getMetropassChargeStartDate());
                            crdDTO.setMetropassChargeEndDate(crd.getMetropassChargeEndDate());
                            crdDTO.setDounknowFlag(crd.getDounknowFlag());
                            crdDTO.setCardCounter(crd.getCardCounter());
                            crdDTO.setAfterCardNotes(crd.getAfterCardNotes());
                            crdDTO.setTradeStep(crd.getTradeStep());
                            crdDTO.setTradeStepVer(crd.getTradeStepVer());
                            crdDTO.setTradeEndFlag(crd.getTradeEndFlag());
                            resResultList.add(crdDTO);
                        }
                    }
                    response.setResponseEntity(resResultList);
                    response.setCode(ResponseCode.SUCCESS);
                }
            } else {
                response.setCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            e.printStackTrace();
        }
        return response;
    }

}
