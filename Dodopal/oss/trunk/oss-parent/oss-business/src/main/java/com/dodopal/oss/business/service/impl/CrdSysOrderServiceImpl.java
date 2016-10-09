package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.CrdSysOrderTDTO;
import com.dodopal.api.card.dto.CrdSysSupplementTDTO;
import com.dodopal.api.card.dto.query.CrdSysOrderQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.CrdSysOrderBean;
import com.dodopal.oss.business.bean.CrdSysSupplementBean;
import com.dodopal.oss.business.bean.query.CrdSysOrderQuery;
import com.dodopal.oss.business.model.VUserInfo;
import com.dodopal.oss.business.service.CrdSysOrderService;
import com.dodopal.oss.business.service.VUserInfoService;
import com.dodopal.oss.delegate.CrdSysOrderDelegate;

@Service
public class CrdSysOrderServiceImpl implements CrdSysOrderService {
    private final static Logger log = LoggerFactory.getLogger(CrdSysOrderServiceImpl.class);
    @Autowired
    CrdSysOrderDelegate crdSysOrderDelegate;

    @Autowired
    VUserInfoService vuserinfo;

    @Override
    public DodopalResponse<DodopalDataPage<CrdSysOrderBean>> findCrdSysOrderByPage(CrdSysOrderQuery crdSysOrderQuery) {
        log.info("oss input crdSysOrder " + crdSysOrderQuery);
        DodopalResponse<DodopalDataPage<CrdSysOrderBean>> response = new DodopalResponse<DodopalDataPage<CrdSysOrderBean>>();
        CrdSysOrderQueryDTO crdDto = new CrdSysOrderQueryDTO();
        //卡服务订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdOrderNum())) {
            crdDto.setCrdOrderNum(crdSysOrderQuery.getCrdOrderNum());
        }
        //产品库主订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getProOrderNum())) {
            crdDto.setProOrderNum(crdSysOrderQuery.getProOrderNum());
        }
        //产品编号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getProCode())) {
            crdDto.setProCode(crdSysOrderQuery.getProCode());
        }
        //产品名称
        if(StringUtils.isNotBlank(crdSysOrderQuery.getProName())){
        	crdDto.setProName(crdSysOrderQuery.getProName());
        }
        //商户编号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getMerCode())) {
            crdDto.setMerCode(crdSysOrderQuery.getMerCode());
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

        DodopalResponse<DodopalDataPage<CrdSysOrderTDTO>> crdSysOrderDtoList = crdSysOrderDelegate.findCrdSysOrderByPage(crdDto);
        List<CrdSysOrderBean> crdSysOrderBeanList = new ArrayList<CrdSysOrderBean>();
        if (ResponseCode.SUCCESS.equals(crdSysOrderDtoList.getCode())) {
            if (crdSysOrderDtoList.getResponseEntity() != null && CollectionUtils.isNotEmpty(crdSysOrderDtoList.getResponseEntity().getRecords())) {
                for (CrdSysOrderTDTO crdDTO : crdSysOrderDtoList.getResponseEntity().getRecords()) {
                    CrdSysOrderBean crdBean = new CrdSysOrderBean();
                    crdBean.setId(crdDTO.getId());
                    crdBean.setCrdOrderNum(crdDTO.getCrdOrderNum());
                    crdBean.setProOrderNum(crdDTO.getProOrderNum());
                    crdBean.setProCode(crdDTO.getProCode());
                    crdBean.setYktCode(crdDTO.getYktCode());
                    crdBean.setMerName(crdDTO.getMerName());
                    crdBean.setCityCode(crdDTO.getCityCode());
                    crdBean.setProName(crdDTO.getProName());
                    crdBean.setMerCode(crdDTO.getMerCode());
                    crdBean.setCardFaceNo(crdDTO.getCardFaceNo());
                    crdBean.setChargeCardNo(crdDTO.getChargeCardNo());
                    crdBean.setCheckCardNo(crdDTO.getCheckCardNo());
                    crdBean.setPosCode(crdDTO.getPosCode());
                    crdBean.setUserCode(crdDTO.getUserCode());
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
                    crdBean.setCrdOrderStates(crdDTO.getCrdOrderStates());
                    crdBean.setCrdBeforeorderStates(crdDTO.getCrdBeforeorderStates());
                    crdBean.setYktName(crdDTO.getYktName());
                    crdBean.setCityName(crdDTO.getCityName());
                    crdBean.setCardType(crdDTO.getCardType());
                    crdBean.setTxnTime(crdDTO.getTxnDate() + crdDTO.getTxnTime());
                    crdBean.setTxnDateTime(DateUtils.stringtoDate(crdDTO.getTxnDate() + crdDTO.getTxnTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
                    crdBean.setMerUserNickName(crdDTO.getMerUserNickName());
                    crdBean.setChargeCardNo(crdDTO.getChargeCardNo());
                    crdSysOrderBeanList.add(crdBean);
                }

            }
            log.info(" return code:" + crdSysOrderDtoList.getCode());
            //后台传入总页数，总条数，当前页
            PageParameter page = DodopalDataPageUtil.convertPageInfo(crdSysOrderDtoList.getResponseEntity());
            DodopalDataPage<CrdSysOrderBean> pages = new DodopalDataPage<CrdSysOrderBean>(page, crdSysOrderBeanList);
            response.setCode(crdSysOrderDtoList.getCode());
            response.setResponseEntity(pages);
        } else {
            response.setCode(crdSysOrderDtoList.getCode());
        }
        return response;
    }

    /**
     * 查询卡服务订单详情
     */
    @Override
    public DodopalResponse<CrdSysOrderBean> findCrdSysOrderByCode(String crdOrderNum) {
        log.info("oss input crdSysOrder crdOrderNum" + crdOrderNum);
        DodopalResponse<CrdSysOrderTDTO> crdSysOrderDto = crdSysOrderDelegate.findCrdSysOrderByCode(crdOrderNum);
        DodopalResponse<CrdSysOrderBean> crdBean = new DodopalResponse<CrdSysOrderBean>();
        CrdSysOrderTDTO crdSysOrderTDTO = crdSysOrderDto.getResponseEntity();
        CrdSysOrderBean crdSysOrderBean = new CrdSysOrderBean();
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
            //            crdSysOrderBean.setTxnDate(crdSysOrderTDTO.getTxnDate());
            //            crdSysOrderBean.setTxnTime(crdSysOrderTDTO.getTxnTime());
            crdSysOrderBean.setYktName(crdSysOrderTDTO.getYktName());
            crdSysOrderBean.setTxnDateTime(DateUtils.stringtoDate(crdSysOrderTDTO.getTxnDate() + crdSysOrderTDTO.getTxnTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
            crdSysOrderBean.setCheckCardNo(crdSysOrderTDTO.getCheckCardNo());
            crdSysOrderBean.setCheckSendCardDate(crdSysOrderTDTO.getCheckSendCardDate());
            crdSysOrderBean.setCheckResCardDate(crdSysOrderTDTO.getCheckResCardDate());
            crdSysOrderBean.setCheckCardPosCode(crdSysOrderTDTO.getCheckCardPosCode());
            crdSysOrderBean.setChargeCardNo(crdSysOrderTDTO.getChargeCardNo());
            crdSysOrderBean.setChargeCardPosCode(crdSysOrderTDTO.getChargeCardPosCode());
            crdSysOrderBean.setChargeSendCardDate(crdSysOrderTDTO.getChargeSendCardDate());
            crdSysOrderBean.setChargeResCardDate(crdSysOrderTDTO.getChargeResCardDate());
            crdSysOrderBean.setResultSendCardDate(crdSysOrderTDTO.getResultSendCardDate());
            crdSysOrderBean.setResultResCardDate(crdSysOrderTDTO.getResultResCardDate());
            crdSysOrderBean.setChargeType(crdSysOrderTDTO.getChargeType());
            if (crdSysOrderTDTO.getMetropassType() != null) {
                crdSysOrderBean.setMetropassType(crdSysOrderTDTO.getMetropassType());
            }
            if (crdSysOrderTDTO.getPosSeq() != null) {
                crdSysOrderBean.setPosSeq(crdSysOrderTDTO.getPosSeq());
            }
            crdSysOrderBean.setMetropassChargeStartDate(crdSysOrderTDTO.getMetropassChargeStartDate());
            crdSysOrderBean.setMetropassChargeEndDate(crdSysOrderTDTO.getMetropassChargeEndDate());
            if (crdSysOrderTDTO.getDounknowFlag() != null) {
                crdSysOrderBean.setDounknowFlag(crdSysOrderTDTO.getDounknowFlag());
            }
            crdSysOrderBean.setCardCounter(crdSysOrderTDTO.getCardCounter());
            crdSysOrderBean.setAfterCardNotes(crdSysOrderTDTO.getAfterCardNotes());
            crdSysOrderBean.setBeforeCardNotes(crdSysOrderTDTO.getBeforeCardNotes());
            crdSysOrderBean.setTradeStep(crdSysOrderTDTO.getTradeStep());
            crdSysOrderBean.setTradeStepVer(crdSysOrderTDTO.getTradeStepVer());
            crdSysOrderBean.setTradeEndFlag(crdSysOrderTDTO.getTradeEndFlag());
            //卡服务补充信息
            CrdSysSupplementTDTO crdSysSupplementTDTO = crdSysOrderTDTO.getCrdSysSupplementTDTO();
            CrdSysSupplementBean crdSysSupplementBean = new CrdSysSupplementBean();
            crdSysSupplementBean.setId(crdSysSupplementTDTO.getId());
            crdSysSupplementBean.setCrdOrderNum(crdSysSupplementTDTO.getCrdOrderNum());
            crdSysSupplementBean.setTxnType(crdSysSupplementTDTO.getTxnType());
            crdSysSupplementBean.setCheckYktmw(crdSysSupplementTDTO.getCheckYktmw());
            crdSysSupplementBean.setCheckYktkey(crdSysSupplementTDTO.getCheckYktkey());
            crdSysSupplementBean.setChargeYktmw(crdSysSupplementTDTO.getChargeYktmw());
            crdSysSupplementBean.setChargeKey(crdSysSupplementTDTO.getChargeKey());
            crdSysSupplementBean.setResultYktmw(crdSysSupplementTDTO.getResultYktmw());
            crdSysSupplementBean.setTxnDate(crdSysSupplementTDTO.getTxnDate());
            crdSysSupplementBean.setTxnTime(crdSysSupplementTDTO.getTxnTime());

            crdSysSupplementBean.setLastReadKeyTime(crdSysSupplementTDTO.getLastReadKeyTime());
            crdSysSupplementBean.setLastChargeKeyTime(crdSysSupplementTDTO.getLastChargeKeyTime());
            crdSysSupplementBean.setLastResultTime(crdSysSupplementTDTO.getLastResultTime());
            crdSysSupplementBean.setRequestReadKeyCount(crdSysSupplementTDTO.getRequestReadKeyCount());
            crdSysSupplementBean.setRequestChargeKeyCount(crdSysSupplementTDTO.getRequestChargeKeyCount());
            crdSysSupplementBean.setResultYktmw(crdSysSupplementTDTO.getResultYktmw());

            crdSysSupplementBean.setSendResultCount(crdSysSupplementTDTO.getSendResultCount());
            //创建人和创建时间
            crdSysOrderBean.setCreateUser(crdSysOrderTDTO.getCreateUser());
            crdSysOrderBean.setCreateDate(crdSysOrderTDTO.getCreateDate());
            crdSysOrderBean.setUpdateUser(crdSysOrderTDTO.getUpdateUser());
            crdSysOrderBean.setUpdateDate(crdSysOrderTDTO.getUpdateDate());
            if (StringUtils.isNotBlank(crdSysOrderTDTO.getCreateUser())) {
                VUserInfo vuser = vuserinfo.findVUserInfoById(crdSysOrderTDTO.getCreateUser());
                crdSysOrderBean.setCreateUserName(vuser.getNickName());
            }
            if (StringUtils.isNotBlank(crdSysOrderTDTO.getUpdateUser())) {
                VUserInfo vuser = vuserinfo.findVUserInfoById(crdSysOrderTDTO.getUpdateUser());
                crdSysOrderBean.setUpdateUserName(vuser.getNickName());
            }

            crdSysOrderBean.setCrdSysSupplementBean(crdSysSupplementBean);
        }

        crdBean.setResponseEntity(crdSysOrderBean);
        crdBean.setCode(ResponseCode.SUCCESS);
        return crdBean;
    }

    /**
     * 列表画面的 导出按钮 处理
     * @param response HttpServletResponse
     * @return 导出结果
     */
    public DodopalResponse<List<CrdSysOrderBean>> excelExport(HttpServletResponse response, CrdSysOrderQuery crdSysOrderQuery) {
        DodopalResponse<List<CrdSysOrderBean>> rep = new DodopalResponse<List<CrdSysOrderBean>>();
        List<CrdSysOrderBean> lstData = new ArrayList<CrdSysOrderBean>();

        CrdSysOrderQueryDTO crdDto = new CrdSysOrderQueryDTO();
        //卡服务订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getCrdOrderNum())) {
            crdDto.setCrdOrderNum(crdSysOrderQuery.getCrdOrderNum());
        }
        //产品库主订单号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getProOrderNum())) {
            crdDto.setProOrderNum(crdSysOrderQuery.getProOrderNum());
        }
        //产品编号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getProCode())) {
            crdDto.setProCode(crdSysOrderQuery.getProCode());
        }
        if (StringUtils.isNotBlank(crdSysOrderQuery.getProName())) {
            //crdDto.setProName(crdSysOrderQuery.getProName());
        }
        //商户编号
        if (StringUtils.isNotBlank(crdSysOrderQuery.getMerCode())) {
            crdDto.setMerCode(crdSysOrderQuery.getMerCode());
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
            crdDto.setTxnDataTimStart(crdSysOrderQuery.getTxnDataTimStart());
        }
        //交易后时间
        if (StringUtils.isNotBlank(crdSysOrderQuery.getTxnDataTimEnd())) {
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

        DodopalResponse<List<CrdSysOrderTDTO>> crdSysOrderDtoList = crdSysOrderDelegate.findCrdSysOrderAll(crdDto);
        if (ResponseCode.SUCCESS.equals(crdSysOrderDtoList.getCode())) {
            List<CrdSysOrderTDTO> crdDtoList = crdSysOrderDtoList.getResponseEntity();
            if (crdDtoList != null) {
                for (CrdSysOrderTDTO crdDTO : crdDtoList) {
                    CrdSysOrderBean crdBean = new CrdSysOrderBean();
                    crdBean.setId(crdDTO.getId());
                    crdBean.setCrdOrderNum(crdDTO.getCrdOrderNum());
                    crdBean.setProOrderNum(crdDTO.getProOrderNum());
                    //crdBean.setProName(crdDTO.getProName());
                    crdBean.setProCode(crdDTO.getProCode());
                    crdBean.setYktCode(crdDTO.getYktCode());
                    crdBean.setCityCode(crdDTO.getCityCode());
                    crdBean.setMerCode(crdDTO.getMerCode());
                    crdBean.setCardFaceNo(crdDTO.getCardFaceNo());
                    crdBean.setChargeCardNo(crdDTO.getChargeCardNo());
                    crdBean.setCheckCardNo(crdDTO.getCheckCardNo());
                    crdBean.setPosCode(crdDTO.getPosCode());
                    crdBean.setUserCode(crdDTO.getUserCode());
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
                    crdBean.setCrdOrderStates(crdDTO.getCrdOrderStates());
                    crdBean.setCrdBeforeorderStates(crdDTO.getCrdBeforeorderStates());
                    crdBean.setYktName(crdDTO.getYktName());
                    crdBean.setCityName(crdDTO.getCityName());
                    crdBean.setCardType(crdDTO.getCardType());
                    crdBean.setTxnTime(crdDTO.getTxnDate() + crdDTO.getTxnTime());
                    crdBean.setTxnDateTime(DateUtils.stringtoDate(crdDTO.getTxnDate() + crdDTO.getTxnTime(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));

                    crdBean.setMerUserNickName(crdDTO.getMerUserNickName());
                    crdBean.setChargeCardNo(crdDTO.getChargeCardNo());

                    crdBean.setTradeEndFlag(crdDTO.getTradeEndFlag());
                    crdBean.setRespCode(crdDTO.getRespCode());
                    crdBean.setCardInnerNo(crdDTO.getCardInnerNo());
                    crdBean.setOrderCardNo(crdDTO.getOrderCardNo());
                    crdBean.setPosType(crdDTO.getPosType());
                    crdBean.setPosSeq(crdDTO.getPosSeq());
                    if (crdDTO.getCardLimit() != 0) {
                        crdBean.setCardLimitView(String.format("%.2f", Double.valueOf(crdDTO.getCardLimit() + "") / 100));
                    } else {
                        crdBean.setCardLimitView(String.format("%.2f", Double.valueOf(crdDTO.getCardLimit() + "")));
                    }
                    crdBean.setSource(crdDTO.getSource());
                    crdBean.setTxnType(crdDTO.getTxnType());
                    crdBean.setTxnSeq(crdDTO.getTxnSeq());
                    crdBean.setCheckCardNoView(crdDTO.getCheckCardNo());
                    crdBean.setCheckCardPosCode(crdDTO.getCheckCardPosCode());
                    crdBean.setCheckSendCardDate(crdDTO.getCheckSendCardDate());
                    crdBean.setCheckResCardDate(crdDTO.getCheckResCardDate());

                    crdBean.setChargeCardNo(crdDTO.getChargeCardNo());
                    crdBean.setChargeCardPosCode(crdDTO.getChargeCardPosCode());
                    crdBean.setChargeSendCardDate(crdDTO.getChargeSendCardDate());
                    crdBean.setChargeResCardDate(crdDTO.getChargeResCardDate());
                    crdBean.setResultSendCardDate(crdDTO.getResultSendCardDate());
                    crdBean.setResultResCardDate(crdDTO.getResultResCardDate());
                    crdBean.setChargeType(crdDTO.getChargeType());
                    crdBean.setMetropassType(crdDTO.getMetropassType());
                    crdBean.setMetropassChargeStartDate(crdDTO.getMetropassChargeStartDate());
                    crdBean.setMetropassChargeEndDate(crdDTO.getMetropassChargeEndDate());
                    crdBean.setDounknowFlag(crdDTO.getDounknowFlag());
                    crdBean.setCardCounter(crdDTO.getCardCounter());
                    crdBean.setAfterCardNotes(crdDTO.getAfterCardNotes());
                    crdBean.setTradeStep(crdDTO.getTradeStep());
                    crdBean.setTradeStepVer(crdDTO.getTradeStepVer());
                    lstData.add(crdBean);
                }
                rep.setCode(ResponseCode.SUCCESS);
                rep.setResponseEntity(lstData);
            } else {
                rep.setCode(ResponseCode.EXCEL_EXPORT_NULL_DATA);
            }
        } else {
            rep.setCode(crdSysOrderDtoList.getCode());
            return rep;
        }
        return rep;
    }

}
