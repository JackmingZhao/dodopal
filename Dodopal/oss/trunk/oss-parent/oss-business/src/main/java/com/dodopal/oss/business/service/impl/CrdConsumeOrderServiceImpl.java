package com.dodopal.oss.business.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysConsOrderDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.CrdSysConsOrderBean;
import com.dodopal.oss.business.bean.query.CrdSysOrderQuery;
import com.dodopal.oss.business.service.CrdConsumeOrderService;
import com.dodopal.oss.business.service.VUserInfoService;
import com.dodopal.oss.delegate.CrdConsumeOrderDelegate;

@Service
public class CrdConsumeOrderServiceImpl implements CrdConsumeOrderService {

    private final static Logger log = LoggerFactory.getLogger(CrdConsumeOrderServiceImpl.class);

    @Autowired
    private CrdConsumeOrderDelegate orderDelegate;
    @Autowired
    VUserInfoService vuserinfo;

    /**
     * 查询卡服务消费订单（分页）
     * @param crdSysOrderQuery
     */
    public DodopalResponse<DodopalDataPage<CrdSysConsOrderBean>> findCrdSysConsOrderByPage(CrdSysOrderQuery crdSysOrderQuery) {
        DodopalResponse<DodopalDataPage<CrdSysConsOrderBean>> response = new DodopalResponse<DodopalDataPage<CrdSysConsOrderBean>>();
        CrdSysOrderQueryDTO crdDto = new CrdSysOrderQueryDTO();
        try {
            //PropertyUtils.copyProperties(crdDto, crdSysOrderQuery);
            //卡服务订单号
            if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdOrderNum())) {
                crdDto.setCrdOrderNum(crdSysOrderQuery.getCrdOrderNum());
            }
            //产品库主订单号
            if (StringUtils.isNotBlank(crdSysOrderQuery.getProOrderNum())) {
                crdDto.setProOrderNum(crdSysOrderQuery.getProOrderNum());
            }
            //商户订单号
            if (StringUtils.isNotBlank(crdSysOrderQuery.getMerOrderCode())) {
                crdDto.setMerOrderCode(crdSysOrderQuery.getMerOrderCode());
            }
            //卡服务订单交易状态
            if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdOrderStates())) {
                crdDto.setCrdOrderStates(crdSysOrderQuery.getCrdOrderStates());
            }
            //卡服务订单前交易状态
            if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdBeforeorderStates())) {
                crdDto.setCrdBeforeorderStates(crdSysOrderQuery.getCrdBeforeorderStates());
            }
            //POS号
            if (StringUtils.isNotBlank(crdSysOrderQuery.getPosCode())) {
                crdDto.setPosCode(crdSysOrderQuery.getPosCode());
            }
            //卡号(验卡卡号)
            if (StringUtils.isNotBlank(crdSysOrderQuery.getCheckCardNo())) {
                crdDto.setCheckCardNo(crdSysOrderQuery.getCheckCardNo());
            }
            //卡类型
            if (StringUtils.isNotBlank(crdSysOrderQuery.getCardType())) {
                crdDto.setCardType(crdSysOrderQuery.getCardType());
            }
            //交易前时间
            if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnDataTimStart())) {
                Date txnDateStart = DateUtils.stringtoDate(crdSysOrderQuery.getTxnDataTimStart(), DateUtils.DATE_SMALL_STR);
                String txnDateStartStr = DateUtils.dateToString(txnDateStart, DateUtils.DATE_FORMAT_YYMMDD_STR);
                crdDto.setTxnDataTimStart(txnDateStartStr);
            }
            //交易后时间
            if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnDataTimEnd())) {
                Date txnDateEnd = DateUtils.stringtoDate(crdSysOrderQuery.getTxnDataTimEnd(), DateUtils.DATE_SMALL_STR);
                String txnDateEndStr = DateUtils.dateToString(txnDateEnd, DateUtils.DATE_FORMAT_YYMMDD_STR);
                crdDto.setTxnDataTimEnd(txnDateEndStr);
            }
            //交易前金额
            if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnAmtStart())) {
                crdDto.setTxnAmtStart(crdSysOrderQuery.getTxnAmtStart());
            }
            //交易后金额
            if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnAmtEnd())) {
                crdDto.setTxnAmtEnd(crdSysOrderQuery.getTxnAmtEnd());
            }
            //分页参数
            if (crdSysOrderQuery.getPage() != null) {
                crdDto.setPage(crdSysOrderQuery.getPage());
            }
            DodopalResponse<DodopalDataPage<CrdSysConsOrderDTO>> consOrderByPage = orderDelegate.findCrdSysConsOrderByPage(crdDto);
            ArrayList<CrdSysConsOrderBean> list = new ArrayList<CrdSysConsOrderBean>();
            if (consOrderByPage.getResponseEntity() != null && CollectionUtils.isNotEmpty(consOrderByPage.getResponseEntity().getRecords())) {
                for (CrdSysConsOrderDTO consOrderDTO : consOrderByPage.getResponseEntity().getRecords()) {
                    CrdSysConsOrderBean order = new CrdSysConsOrderBean();
                    PropertyUtils.copyProperties(order, consOrderDTO);
                    order.setTxnTime(consOrderDTO.getTxnDate() + consOrderDTO.getTxnTime());
                    order.setTxnDateTime(DateUtils.stringtoDate(consOrderDTO.getTxnDate() + consOrderDTO.getTxnTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                    if (consOrderDTO.getBefbal() != 0) {
                        order.setBefbalView(String.format("%.2f", Double.valueOf(consOrderDTO.getBefbal() + "") / 100));
                    } else {
                        order.setBefbalView(String.format("%.2f", Double.valueOf(consOrderDTO.getBefbal() + "")));
                    }
                    if (consOrderDTO.getTxnAmt() != 0) {
                        order.setTxnAmtView(String.format("%.2f", Double.valueOf(consOrderDTO.getTxnAmt() + "") / 100));
                    } else {
                        order.setTxnAmtView(String.format("%.2f", Double.valueOf(consOrderDTO.getTxnAmt() + "")));
                    }
                    if (consOrderDTO.getBlackAmt() != 0) {
                        order.setBlackAmtView(String.format("%.2f", Double.valueOf(consOrderDTO.getBlackAmt() + "") / 100));
                    } else {
                        order.setBlackAmtView(String.format("%.2f", Double.valueOf(consOrderDTO.getBlackAmt() + "")));
                    }
                    list.add(order);
                }
            }
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(consOrderByPage.getResponseEntity());
            DodopalDataPage<CrdSysConsOrderBean> pages = new DodopalDataPage<CrdSysConsOrderBean>(page, list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
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
    public DodopalResponse<CrdSysConsOrderBean> findCrdConsumeOrderByCode(String crdOrderNum) {
        log.info("oss input crdSysOrder crdOrderNum" + crdOrderNum);
        CrdSysOrderQueryDTO crdSysOrderQuery = new CrdSysOrderQueryDTO();
        crdSysOrderQuery.setCrdOrderNum(crdOrderNum);
        DodopalResponse<List<CrdSysConsOrderDTO>> crdSysOrderDto = orderDelegate.findCrdSysConsOrder(crdSysOrderQuery);
        DodopalResponse<CrdSysConsOrderBean> crdBean = new DodopalResponse<CrdSysConsOrderBean>();
        List<CrdSysConsOrderDTO> responseEntity = crdSysOrderDto.getResponseEntity();
        for (CrdSysConsOrderDTO crdSysOrderTDTO : responseEntity) {
            CrdSysConsOrderBean crdSysOrderBean = new CrdSysConsOrderBean();
            if (crdSysOrderTDTO != null) {
                crdSysOrderBean.setId(crdSysOrderTDTO.getId());
                crdSysOrderBean.setProOrderNum(crdSysOrderTDTO.getProOrderNum());
                crdSysOrderBean.setCrdOrderNum(crdSysOrderTDTO.getCrdOrderNum());
                crdSysOrderBean.setProCode(crdSysOrderTDTO.getProCode());
                crdSysOrderBean.setMerCode(crdSysOrderTDTO.getMerCode());
                crdSysOrderBean.setMerOrderCode(crdSysOrderTDTO.getMerOrderCode());
                crdSysOrderBean.setCrdOrderStates(crdSysOrderTDTO.getCrdOrderStates());
                crdSysOrderBean.setCrdBeforeorderStates(crdSysOrderTDTO.getCrdBeforeorderStates());
                crdSysOrderBean.setRespCode(crdSysOrderTDTO.getRespCode());
                crdSysOrderBean.setUserCode(crdSysOrderTDTO.getUserCode());
                crdSysOrderBean.setCityCode(crdSysOrderTDTO.getCityCode());
                crdSysOrderBean.setCardInnerNo(crdSysOrderTDTO.getCardInnerNo());
                crdSysOrderBean.setCardFaceNo(crdSysOrderTDTO.getCardFaceNo());
                crdSysOrderBean.setOrderCardNo(crdSysOrderTDTO.getOrderCardNo());
                crdSysOrderBean.setCardType(crdSysOrderTDTO.getCardType());
                crdSysOrderBean.setPosType(crdSysOrderTDTO.getPosType());
                crdSysOrderBean.setBefbal(crdSysOrderTDTO.getBefbal());
                crdSysOrderBean.setBlackAmt(crdSysOrderTDTO.getBlackAmt());
                crdSysOrderBean.setTxnAmt(crdSysOrderTDTO.getTxnAmt());
                crdSysOrderBean.setCardLimit(crdSysOrderTDTO.getCardLimit());
                crdSysOrderBean.setSource(crdSysOrderTDTO.getSource());
                crdSysOrderBean.setTxnType(crdSysOrderTDTO.getTxnType());
                crdSysOrderBean.setTxnSeq(crdSysOrderTDTO.getTxnSeq());
                crdSysOrderBean.setTxnDateTime(DateUtils.stringtoDate(crdSysOrderTDTO.getTxnDate() + crdSysOrderTDTO.getTxnTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                crdSysOrderBean.setCheckCardNo(crdSysOrderTDTO.getCheckCardNo());
                crdSysOrderBean.setCheckSendCardDate(crdSysOrderTDTO.getCheckSendCardDate());
                crdSysOrderBean.setCheckResCardDate(crdSysOrderTDTO.getCheckResCardDate());
                crdSysOrderBean.setCheckCardPosCode(crdSysOrderTDTO.getCheckCardPosCode());
                crdSysOrderBean.setResultSendCardDate(crdSysOrderTDTO.getResultSendCardDate());
                crdSysOrderBean.setResultResCardDate(crdSysOrderTDTO.getResultResCardDate());
                crdSysOrderBean.setChargeType(crdSysOrderTDTO.getChargeType());
                crdSysOrderBean.setMetropassType(crdSysOrderTDTO.getMetropassType());
                crdSysOrderBean.setPosSeq(crdSysOrderTDTO.getPosSeq());
                crdSysOrderBean.setMetropassChargeStartDate(crdSysOrderTDTO.getMetropassChargeStartDate());
                crdSysOrderBean.setMetropassChargeEndDate(crdSysOrderTDTO.getMetropassChargeEndDate());
                crdSysOrderBean.setDounknowFlag(crdSysOrderTDTO.getDounknowFlag());
                crdSysOrderBean.setCardCounter(crdSysOrderTDTO.getCardCounter());
                crdSysOrderBean.setAfterCardNotes(crdSysOrderTDTO.getAfterCardNotes());
                crdSysOrderBean.setBeforeCardNotes(crdSysOrderTDTO.getBeforeCardNotes());
                crdSysOrderBean.setTradeStep(crdSysOrderTDTO.getTradeStep());
                crdSysOrderBean.setTradeStepVer(crdSysOrderTDTO.getTradeStepVer());
                crdSysOrderBean.setTradeEndFlag(crdSysOrderTDTO.getTradeEndFlag());
                //创建人和创建时间
                crdSysOrderBean.setCreateUser(crdSysOrderTDTO.getCreateUser());
                crdSysOrderBean.setCreateDate(crdSysOrderTDTO.getCreateDate());
                crdSysOrderBean.setUpdateUser(crdSysOrderTDTO.getUpdateUser());
                crdSysOrderBean.setUpdateDate(crdSysOrderTDTO.getUpdateDate());

                crdSysOrderBean.setConsumeCardNo(crdSysOrderTDTO.getConsumeCardNo());
                crdSysOrderBean.setConsumeCardPosCode(crdSysOrderTDTO.getConsumeCardPosCode());
                crdSysOrderBean.setConsumeResCardDate(crdSysOrderTDTO.getConsumeResCardDate());
                crdSysOrderBean.setConsumeSendCardDate(crdSysOrderTDTO.getConsumeSendCardDate());

                if (crdSysOrderTDTO.getCardLimit() != 0) {
                    crdSysOrderBean.setCardLimitView(String.format("%.2f", Double.valueOf(crdSysOrderTDTO.getCardLimit() + "") / 100));
                } else {
                    crdSysOrderBean.setCardLimitView(String.format("%.2f", Double.valueOf(crdSysOrderTDTO.getCardLimit() + "")));
                }
                
                crdSysOrderBean.setCheckCardNoView(crdSysOrderTDTO.getCheckCardNo());
                crdBean.setResponseEntity(crdSysOrderBean);
            }
        }

        crdBean.setCode(ResponseCode.SUCCESS);
        return crdBean;
    }

    /**
     * 卡服务消费订单导出
     * @param crdSysOrderQuery
     */
    public DodopalResponse<List<CrdSysConsOrderBean>> excelExport(HttpServletResponse response, CrdSysOrderQuery crdSysOrderQuery) {
        DodopalResponse<List<CrdSysConsOrderBean>> rep = new DodopalResponse<List<CrdSysConsOrderBean>>();
        List<CrdSysConsOrderBean> lstData = new ArrayList<CrdSysConsOrderBean>();

        CrdSysOrderQueryDTO crdDto = new CrdSysOrderQueryDTO();
        //卡服务订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdOrderNum())) {
            crdDto.setCrdOrderNum(crdSysOrderQuery.getCrdOrderNum());
        }
        //产品库主订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getProOrderNum())) {
            crdDto.setProOrderNum(crdSysOrderQuery.getProOrderNum());
        }
        //商户订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getMerOrderCode())) {
            crdDto.setMerOrderCode(crdSysOrderQuery.getMerOrderCode());
        }
        //卡服务订单交易状态
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdOrderStates())) {
            crdDto.setCrdOrderStates(crdSysOrderQuery.getCrdOrderStates());
        }
        //卡服务订单前交易状态
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdBeforeorderStates())) {
            crdDto.setCrdBeforeorderStates(crdSysOrderQuery.getCrdBeforeorderStates());
        }
        //POS号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getPosCode())) {
            crdDto.setPosCode(crdSysOrderQuery.getPosCode());
        }
        //卡号(验卡卡号)
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCheckCardNo())) {
            crdDto.setCheckCardNo(crdSysOrderQuery.getCheckCardNo());
        }
        //卡类型
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCardType())) {
            crdDto.setCardType(crdSysOrderQuery.getCardType());
        }
        //交易前时间
        if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnDataTimStart())) {
            //            Date txnDateStart = DateUtils.stringtoDate(crdSysOrderQuery.getTxnDataTimStart(), DateUtils.DATE_SMALL_STR);
            //            String txnDateStartStr= DateUtils.dateToString(txnDateStart, DateUtils.DATE_FORMAT_YYMMDD_STR);
            crdDto.setTxnDataTimStart(crdSysOrderQuery.getTxnDataTimStart());
        }
        //交易后时间
        if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnDataTimEnd())) {
            //            Date txnDateEnd = DateUtils.stringtoDate(crdSysOrderQuery.getTxnDataTimEnd(), DateUtils.DATE_SMALL_STR);
            //            String txnDateEndStr= DateUtils.dateToString(txnDateEnd, DateUtils.DATE_FORMAT_YYMMDD_STR);
            crdDto.setTxnDataTimEnd(crdSysOrderQuery.getTxnDataTimEnd());
        }
        //交易前金额
        if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnAmtStart())) {
            crdDto.setTxnAmtStart(crdSysOrderQuery.getTxnAmtStart());
        }
        //交易后金额
        if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnAmtEnd())) {
            crdDto.setTxnAmtEnd(crdSysOrderQuery.getTxnAmtEnd());
        }
        try {
            DodopalResponse<List<CrdSysConsOrderDTO>> crdSysOrderDtoList = orderDelegate.findCrdSysConsOrder(crdDto);
            List<CrdSysConsOrderDTO> crdDtoList = crdSysOrderDtoList.getResponseEntity();
            if (crdDtoList != null) {
                for (CrdSysConsOrderDTO crdDTO : crdDtoList) {
                    CrdSysConsOrderBean crdBean = new CrdSysConsOrderBean();
                    PropertyUtils.copyProperties(crdBean, crdDTO);
                    if (crdDTO.getBefbal() != 0) {
                        crdBean.setBefbalView(String.format("%.2f", Double.valueOf(crdDTO.getBefbal() + "") / 100));
                    } else {
                        crdBean.setBefbalView(String.format("%.2f", Double.valueOf(crdDTO.getBefbal() + "")));
                    }
                    if (crdDTO.getTxnAmt() != 0) {
                        crdBean.setTxnAmtView(String.format("%.2f", Double.valueOf(crdDTO.getTxnAmt() + "") / 100));
                    } else {
                        crdBean.setTxnAmtView(String.format("%.2f", Double.valueOf(crdDTO.getTxnAmt() + "")));
                    }
                    if (crdDTO.getBlackAmt() != 0) {
                        crdBean.setBlackAmtView(String.format("%.2f", Double.valueOf(crdDTO.getBlackAmt() + "") / 100));
                    } else {
                        crdBean.setBlackAmtView(String.format("%.2f", Double.valueOf(crdDTO.getBlackAmt() + "")));
                    }
                    if (crdDTO.getCardLimit() != 0) {
                        crdBean.setCardLimitView(String.format("%.2f", Double.valueOf(crdDTO.getCardLimit() + "") / 100));
                    } else {
                        crdBean.setCardLimitView(String.format("%.2f", Double.valueOf(crdDTO.getCardLimit() + "")));
                    }
                    
                    crdBean.setCheckCardNoView(crdDTO.getCheckCardNo());
                    crdBean.setTxnTime(crdDTO.getTxnDate() + crdDTO.getTxnTime());
                    crdBean.setTxnDateTime(DateUtils.stringtoDate(crdDTO.getTxnDate() + crdDTO.getTxnTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                    lstData.add(crdBean);
                }
                rep.setCode(ResponseCode.SUCCESS);
                rep.setResponseEntity(lstData);
                return rep;
            } else {
                rep.setCode(ResponseCode.UNKNOWN_ERROR);
                return rep;
            }
        }
        catch (Exception e) {
            rep.setCode(ResponseCode.UNKNOWN_ERROR);
        }

        return rep;
    }

    public Date format(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date response = new Date();
        try {
            response = fmt.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}
