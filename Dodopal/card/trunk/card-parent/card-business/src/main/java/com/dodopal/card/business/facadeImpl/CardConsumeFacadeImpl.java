package com.dodopal.card.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysConsOrderDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.api.card.facade.CardConsumeFacade;
import com.dodopal.card.business.model.CrdSysConsOrder;
import com.dodopal.card.business.model.dto.CrdSysOrderQuery;
import com.dodopal.card.business.service.CardConsumeService;
import com.dodopal.card.business.util.BJStringUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;

@Service("cardConsumeFacadeImpl")
public class CardConsumeFacadeImpl implements CardConsumeFacade {

    @Autowired
    private CardConsumeService cardConsumeService;
    private final static Logger log = LoggerFactory.getLogger(CardConsumeFacadeImpl.class);

    @Override
    public DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>> findCrdSysConsOrderByPage(CrdSysOrderQueryDTO crdSysOrderQuery) {
        log.info("CardConsumeFacadeImpl findCrdSysConsOrderByPage crdSysOrderQuery is start:" + crdSysOrderQuery);
        DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>> response = new DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>>();
        CrdSysOrderQuery orderQuery = new CrdSysOrderQuery();
        try {
            PropertyUtils.copyProperties(orderQuery, crdSysOrderQuery);
            DodopalDataPage<CrdSysConsOrder> orderByPage = cardConsumeService.findCrdSysConsOrderByPage(orderQuery);
            ArrayList<CrdSysConsOrderDTO> list = new ArrayList<CrdSysConsOrderDTO>();
            if (orderByPage != null) {
                List<CrdSysConsOrder> records = orderByPage.getRecords();
                if (records != null && records.size() > 0) {
                    for (CrdSysConsOrder consOrder : records) {
                        CrdSysConsOrderDTO orderDTO = new CrdSysConsOrderDTO();
                        orderDTO.setId(consOrder.getId());
                        orderDTO.setCrdOrderNum(consOrder.getCrdOrderNum());
                        orderDTO.setProOrderNum(consOrder.getProOrderNum());
                        orderDTO.setCheckCardNo(consOrder.getCheckCardNo());
                        orderDTO.setProCode(consOrder.getProCode());
                        orderDTO.setYktCode(consOrder.getYktCode());
                        orderDTO.setCityCode(consOrder.getCityCode());
                        orderDTO.setMerCode(consOrder.getMerCode());
                        orderDTO.setCardFaceNo(consOrder.getCardFaceNo());
                        orderDTO.setPosCode(consOrder.getPosCode());
                        orderDTO.setUserCode(consOrder.getUserCode());
                        orderDTO.setBefbal(consOrder.getBefbal() == null ? 0 : consOrder.getBefbal());
                        orderDTO.setTxnAmt(consOrder.getTxnAmt() == null ? 0 : consOrder.getTxnAmt());
                        orderDTO.setBlackAmt(consOrder.getBlackAmt() == null ? 0 : consOrder.getBlackAmt());
                        orderDTO.setCrdOrderStates(consOrder.getCrdOrderStates());
                        orderDTO.setCrdBeforeorderStates(consOrder.getCrdBeforeorderStates());
                        orderDTO.setCardType(consOrder.getCardType() == null ? 0 : consOrder.getCardType());
                        orderDTO.setTxnDate(consOrder.getTxnDate());
                        orderDTO.setTxnTime(consOrder.getTxnTime());
                        orderDTO.setBefbal(consOrder.getBefbal() == null ? 0 : consOrder.getBefbal());
                        orderDTO.setTxnAmt(consOrder.getTxnAmt() == null ? 0 : consOrder.getTxnAmt());
                        list.add(orderDTO);
                    }
                }

                PageParameter page = DodopalDataPageUtil.convertPageInfo(orderByPage);
                DodopalDataPage<CrdSysConsOrderDTO> ddpDTOResult = new DodopalDataPage<CrdSysConsOrderDTO>(page, list);
                response.setResponseEntity(ddpDTOResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        log.info("CardConsumeFacadeImpl findCrdSysConsOrderByPage crdSysOrderQuery is end:" + response.getCode());
        return response;
    }

    @Override
    public DodopalResponse<List<CrdSysConsOrderDTO>> findCrdSysConsOrder(CrdSysOrderQueryDTO crdSysOrderQuery) {
        log.info("CardConsumeFacadeImpl findCrdSysConsOrderByPage findCrdSysConsOrder is start:" + crdSysOrderQuery);
        DodopalResponse<List<CrdSysConsOrderDTO>> response = new DodopalResponse<List<CrdSysConsOrderDTO>>();
        CrdSysOrderQuery orderQuery = new CrdSysOrderQuery();
        try {
            PropertyUtils.copyProperties(orderQuery, crdSysOrderQuery);
            List<CrdSysConsOrder> list = cardConsumeService.findCrdSysConsOrder(orderQuery);
            ArrayList<CrdSysConsOrderDTO> arrayList = new ArrayList<CrdSysConsOrderDTO>();
            if (list.size() > 0) {
                for (CrdSysConsOrder consOrder : list) {
                    CrdSysConsOrderDTO orderDTO = new CrdSysConsOrderDTO();
                    orderDTO.setId(consOrder.getId());
                    orderDTO.setCrdOrderNum(consOrder.getCrdOrderNum());
                    orderDTO.setProOrderNum(consOrder.getProOrderNum());
                    orderDTO.setCheckCardNo(consOrder.getCheckCardNo());
                    orderDTO.setProCode(consOrder.getProCode());
                    orderDTO.setYktCode(consOrder.getYktCode());
                    orderDTO.setCityCode(consOrder.getCityCode());
                    orderDTO.setMerCode(consOrder.getMerCode());
                    orderDTO.setCardFaceNo(consOrder.getCardFaceNo());
                    orderDTO.setPosCode(consOrder.getPosCode());
                    orderDTO.setUserCode(consOrder.getUserCode());
                    orderDTO.setBefbal(consOrder.getBefbal() == null ? 0 : consOrder.getBefbal());
                    orderDTO.setTxnAmt(consOrder.getTxnAmt() == null ? 0 : consOrder.getTxnAmt());
                    orderDTO.setBlackAmt(consOrder.getBlackAmt() == null ? 0 : consOrder.getBlackAmt());
                    orderDTO.setCrdOrderStates(consOrder.getCrdOrderStates());
                    orderDTO.setCrdBeforeorderStates(consOrder.getCrdBeforeorderStates());
                    orderDTO.setCardType(consOrder.getCardType() == null ? 0 : consOrder.getCardType());
                    orderDTO.setTxnDate(consOrder.getTxnDate());
                    orderDTO.setTxnTime(consOrder.getTxnTime());
                    orderDTO.setBefbal(consOrder.getBefbal() == null ? 0 : consOrder.getBefbal());
                    orderDTO.setTxnAmt(consOrder.getTxnAmt() == null ? 0 : consOrder.getTxnAmt());
                    orderDTO.setMerOrderCode(consOrder.getMerOrderCode());
                    orderDTO.setRespCode(consOrder.getRespCode());
                    orderDTO.setCardInnerNo(consOrder.getCardInnerNo());
                    orderDTO.setOrderCardNo(consOrder.getOrderCardNo());
                    orderDTO.setPosType(consOrder.getPosType());
                    orderDTO.setCardLimit(consOrder.getCardLimit() == null ? 0 : consOrder.getCardLimit());
                    orderDTO.setSource(consOrder.getSource());
                    orderDTO.setTxnType(consOrder.getTxnType());
                    orderDTO.setTxnSeq(consOrder.getTxnSeq());
                    orderDTO.setCheckCardNo(consOrder.getCheckCardNo());
                    orderDTO.setCheckSendCardDate(consOrder.getCheckSendCardDate());
                    orderDTO.setCheckResCardDate(consOrder.getCheckResCardDate());
                    orderDTO.setCheckCardPosCode(consOrder.getCheckCardPosCode());
                    orderDTO.setResultSendCardDate(consOrder.getResultSendCardDate());
                    orderDTO.setResultResCardDate(consOrder.getResultResCardDate());
                    orderDTO.setChargeType(consOrder.getChargeType() == null ? 0 : consOrder.getChargeType());
                    orderDTO.setMetropassType(consOrder.getMetropassType());
                    orderDTO.setMetropassChargeStartDate(consOrder.getMetropassChargeStartDate());
                    orderDTO.setMetropassChargeEndDate(consOrder.getMetropassChargeEndDate());
                    orderDTO.setDounknowFlag(consOrder.getDounknowFlag());
                    orderDTO.setCardCounter(consOrder.getCardCounter());
                    orderDTO.setAfterCardNotes(consOrder.getAfterCardNotes());
                    orderDTO.setBeforeCardNotes(consOrder.getBeforeCardNotes());
                    orderDTO.setTradeStep(consOrder.getTradeStep());
                    orderDTO.setTradeStepVer(BJStringUtil.removeVerZero(consOrder.getTradeStepVer()));
                    orderDTO.setTradeEndFlag(consOrder.getTradeEndFlag());
                    //创建人和创建时间
                    orderDTO.setCreateUser(consOrder.getCreateUser());
                    orderDTO.setCreateDate(consOrder.getCreateDate());
                    orderDTO.setUpdateUser(consOrder.getUpdateUser());
                    orderDTO.setUpdateDate(consOrder.getUpdateDate());

                    orderDTO.setConsumeCardNo(consOrder.getConsumeCardNo());
                    orderDTO.setConsumeCardPosCode(consOrder.getConsumeCardPosCode());
                    orderDTO.setConsumeResCardDate(consOrder.getConsumeResCardDate());
                    orderDTO.setConsumeSendCardDate(consOrder.getConsumeSendCardDate());
                    arrayList.add(orderDTO);
                }
            }
            response.setResponseEntity(arrayList);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        log.info("CardConsumeFacadeImpl findCrdSysConsOrderByPage findCrdSysConsOrder is end:" + response.getCode());
        return response;
    }

}
