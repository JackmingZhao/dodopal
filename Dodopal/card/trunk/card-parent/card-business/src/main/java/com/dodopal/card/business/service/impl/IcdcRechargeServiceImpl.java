package com.dodopal.card.business.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.card.business.dao.IcdcRechargeMapper;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.model.CrdSysSupplement;
import com.dodopal.card.business.service.CityFrontSocketService;
import com.dodopal.card.business.service.IcdcRechargeService;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.enums.CardTradeEndFlagEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
/**
 * 
 * @author yuanyue
 *
 */
@Service
public class IcdcRechargeServiceImpl implements IcdcRechargeService {
    @Autowired
    private LogHelper logHelper;

    @Autowired
    private IcdcRechargeMapper icdcRechargeMapper;

    @Autowired
    private CityFrontSocketService cityFrontSocketService;

    private static final String UPLOAD_RESULT_SUCCESS = "0";//成功

    private static final String UPLOAD_RESULT_FAILED = "1";//失败

    private static final String UPLOAD_RESULT_UNKONW = "2";//未知

    @Transactional(readOnly = true)
    @Override
    public Integer queryCrdSysOrderCountByPrdOrderId(String prdOrderNum) {
        return icdcRechargeMapper.queryCrdSysOrderCountByPrdOrderId(prdOrderNum);
    }

    @Transactional(readOnly = true)
    @Override
    public CrdSysOrder queryCardSysOrderByPrdId(String prdOrderNum) {
        return icdcRechargeMapper.queryCardSysOrderByPrdOrderId(prdOrderNum);
    }

    @Transactional(readOnly = true)
    @Override
    public CrdSysOrder queryCardSysOrderByCrdId(String crdOrderNum) {
        return icdcRechargeMapper.queryCardSysOrderByCrdOrderId(crdOrderNum);
    }

    @Transactional(readOnly = true)
    @Override
    public CrdSysSupplement queryCrdSysSupplementByCrdId(String crdSysOrderNum) {
        return icdcRechargeMapper.queryCrdSysSupplementByCrdOrderId(crdSysOrderNum);
    }

    @Transactional
    @Override
    public int updateCardSysOrderByCrdOrderId(Map<String, Object> value) {
        return icdcRechargeMapper.updateCardSysOrderByCrdOrderId(value);
    }

    @Transactional
    @Override
    public int updateCardSysSupplementByCrdOrderId(Map<String, Object> value) {
        return icdcRechargeMapper.updateCardSysSupplementByCrdOrderId(value);
    }

    @Transactional
    public int updateCardSysOrderBackAmtByCrdOrderId(String crdOrderId) {
        return icdcRechargeMapper.updateCardSysOrderBackAmtByCrdOrderId(crdOrderId);
    }

    @Transactional
    @Override
    public int updateResetCardSysOrderBackAmtByCrdOrderId(String crdOrderId) {
        return icdcRechargeMapper.updateResetCardSysOrderBackAmtByCrdOrderId(crdOrderId);
    }

    @Transactional
    @Override
    public int updateClearCardSysOrderBackAmtByCrdOrderId(String crdOrderId) {
        return icdcRechargeMapper.updateClearCardSysOrderBackAmtByCrdOrderId(crdOrderId);
    }

    @Transactional(readOnly = true)
    @Override
    public String queryCardSysOrderStatusByPrdId(String prdOrderNum) {
        return icdcRechargeMapper.queryCardSysOrderStatusByPrdId(prdOrderNum);
    }

    @Transactional
    @Override
    public DodopalResponse<CrdSysOrderDTO> getLoadInitFun(CrdSysOrderDTO crdDTO) {
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//结束标志
        response.setResponseEntity(crdDTO);
        // 1.检查卡服务订单是否存在
        int count = -1;
        count = queryCrdSysOrderCountByPrdOrderId(crdDTO.getPrdordernum());
        if (count != 1) {
            throw new DDPException(ResponseCode.SYSTEM_ERROR, "检查卡服务订单是否存在失败");
        }
        // 2.查询卡服务订单
        CrdSysOrder crdSysOrder = queryCardSysOrderByPrdId(crdDTO.getPrdordernum());
        // 3.校验前端读卡卡号和充值金额
        if (!crdSysOrder.getOrderCardNo().equals(crdDTO.getTradecard())) {
            response.setCode(ResponseCode.CARD_TRADECARD_ERROR);
            return response;
        }
        if (!(crdSysOrder.getTxnAmt() == Long.valueOf(crdDTO.getTxnamt()))) {
            response.setCode(ResponseCode.CARD_TXNAMT_ERROR);
            return response;
        }
        // 4.校验订单状态
        String orderStatus = crdSysOrder.getCrdOrderStates();
        String crdOrderNum = crdSysOrder.getCrdOrderNum();
        crdDTO.setCrdordernum(crdOrderNum);
        if (CardOrderStatesEnum.CARD_ORDER_CREATE_SUCCESS.getCode().equals(orderStatus)) {//状态正确
            // 5.卡服务订单号获得卡服务订单信息 apdu指令域(checkyktkey)
            CrdSysSupplement crdSysSupplement = null;
            String checkyktkey = null;
            crdSysSupplement = queryCrdSysSupplementByCrdId(crdSysOrder.getCrdOrderNum());
            if (crdSysSupplement == null) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "卡服务订单号获得卡服务订单信息 apdu指令域失败");
            }
            checkyktkey = crdSysSupplement.getCheckYktkey();
            // 6.检查申请读卡密钥是否成功
            if (StringUtils.isBlank(checkyktkey)) {// 如果失败
                // 7.更新卡服务订单状态
                Map<String, Object> value = new HashMap<String, Object>(1);
                value.put("RESP_CODE", ResponseCode.CARD_APPLAY_KEY_FAILED);
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_ERROR.getCode());//04
                value.put("MER_ORDER_CODE", crdDTO.getMerordercode());
                value.put("CRD_ORDER_NUM", crdOrderNum);
                value.put("USER_CODE", crdDTO.getUsercode());
                value.put("UPDATE_USER", crdDTO.getUserid());
                response.setCode(ResponseCode.CARD_APPLAY_KEY_FAILED);
                crdDTO.setRespcode(ResponseCode.CARD_APPLAY_KEY_FAILED);
                count = updateCardSysOrderByCrdOrderId(value);
                if (count != 1) {
                    throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单状态失败");
                }
                return response;
            } else {// 如果成功
                // 5.往DTO填充数据 卡号(cardinnerno)、卡面号(cardfaceno)、交易流水号(txnseq)、交易日期(txndate)、交易时间(交易时间)、设备号编号(posid)
                crdDTO.setCardinnerno(crdSysOrder.getCardInnerNo());
                crdDTO.setCardfaceno(crdSysOrder.getCardFaceNo());
                crdDTO.setTxnseq(crdSysOrder.getTxnSeq() + "");
                crdDTO.setTxndate(crdSysOrder.getTxnDate());
                crdDTO.setTxntime(crdSysOrder.getTxnTime());
                crdDTO.setPosid(crdSysOrder.getPosCode());
                //                crdDTO.setTradeendflag(crdSysOrder.getTradeEndFlag()+"");
                crdDTO.setTradeendflag(CardTradeEndFlagEnum.TRADE_END_FLAG_TRADE_END.getCode());//结束标志
                crdDTO.setCardlimit(crdSysOrder.getCardLimit() + "");
                crdDTO.setUsercode(crdSysOrder.getUserCode());
                crdDTO.setChargetype(crdSysOrder.getChargeType() + "");
                // 6.填充 APDU指令数组
                String[] apdu = JSONObject.parseObject(checkyktkey, String[].class);
                crdDTO.setApdu(apdu);
                // 7.更新卡服务订单状态
                Map<String, Object> value = new HashMap<String, Object>(1);
                value.put("RESP_CODE", ResponseCode.SUCCESS);
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode());//03
                value.put("MER_ORDER_CODE", crdDTO.getMerordercode());
                value.put("USER_CODE", crdDTO.getUsercode());
                value.put("CRD_ORDER_NUM", crdOrderNum);
                value.put("UPDATE_USER", crdDTO.getUserid());
                count = updateCardSysOrderByCrdOrderId(value);
                if (count != 1) {
                    throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单状态失败");
                }
                response.setCode(ResponseCode.SUCCESS);
                crdDTO.setRespcode(ResponseCode.SUCCESS);
                return response;
            }
        } else {//状态不正确
            response.setCode(ResponseCode.CARD_ORDER_STATES_ERROR);
            crdDTO.setRespcode(ResponseCode.CARD_ORDER_STATES_ERROR);
            return response;
        }
    }

    @Transactional
    @Override
    public DodopalResponse<CrdSysOrderDTO> uploadResultChkUdpOrderFun(CrdSysOrderDTO crdDTO) {
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(crdDTO);
        String txnstat = crdDTO.getTxnstat();
        String apdu = crdDTO.getSpecdata().getApdudata();
        if (StringUtils.isBlank(apdu)) {
            response.setCode(ResponseCode.CARD_TAC_NULL);
            return response;
        }
        String resultYktMw = JSONObject.toJSONString(crdDTO.getSpecdata());
        if (resultYktMw.length() > 4000) {
            resultYktMw = resultYktMw.substring(0, 4000);
        }
        if (StringUtils.isBlank(resultYktMw)) {
            response.setCode(ResponseCode.CARD_SPECDATA_NULL);
            return response;
        }
        int count = 0;
        // 1.判断订单状态
        if (!apdu.equals("FFFFFFFF") && txnstat.equals(UPLOAD_RESULT_SUCCESS)) {// 当txnstat=0(成功)并且apdudata非全FFFFFFFF
            // 2.更新卡服务订单状态
            Map<String, Object> value = new HashMap<String, Object>(1);
            value.put("CARD_TAC", apdu);
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_SUCCESS.getCode());
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            if (StringUtils.isNotBlank(crdDTO.getBlackamt())) {
                value.put("BLACK_AMT", Integer.valueOf(crdDTO.getBlackamt()));
            }
            count = updateCardSysOrderByCrdOrderId(value);
            if (count != 1) {//更新失败
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单交易状态失败");
            }
            //            // 3.更新卡服务订单交易后卡余额(单位:分)
            //            count = updateCardSysOrderBackAmtByCrdOrderId(crdDTO.getCrdordernum());
            //            if (count != 1) {
            //                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单交易后卡余额失败");
            //            }
            value = new HashMap<String, Object>(1);
            // 4.更新卡服务订单子表特殊域信息和TAC信息
            value.put("RESULT_YKTMW", resultYktMw);
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            count = updateCardSysSupplementByCrdOrderId(value);
            if (count != 1) {//更新失败
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单子表信息失败");
            }
        } else if (apdu.equals("FFFFFFFF") && txnstat.equals(UPLOAD_RESULT_FAILED)) {// 当txnstat=1(失败)
            // 2.更新卡服务订单状态
            Map<String, Object> value = new HashMap<String, Object>(1);
            value.put("CARD_TAC", apdu);
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            if (StringUtils.isNotBlank(crdDTO.getBlackamt())) {
                value.put("BLACK_AMT", Integer.valueOf(crdDTO.getBlackamt()));
            }
            count = updateCardSysOrderByCrdOrderId(value);
            if (count != 1) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单表失败");
            }
            //            // 3.更新卡服务订单交易后卡余额(单位:分)
            //            count = updateResetCardSysOrderBackAmtByCrdOrderId(crdDTO.getCrdordernum());
            //            if (count != 1) {
            //                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单交易后卡余额失败");
            //            }
            value = new HashMap<String, Object>(1);
            // 4.更新卡服务订单子表特殊域信息
            value.put("RESULT_YKTMW", resultYktMw);
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            count = updateCardSysSupplementByCrdOrderId(value);
            if (count != 1) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单子表信息失败");
            }
        } else if (apdu.equals("FFFFFFFF") && txnstat.equals(UPLOAD_RESULT_UNKONW)) {// 当txnstat=2(未知)
            // 2.更新卡服务订单状态
            Map<String, Object> value = new HashMap<String, Object>(1);
            value.put("CARD_TAC", apdu);
            value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_UNKNOW.getCode());
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            count = updateCardSysOrderByCrdOrderId(value);
            if (count != 1) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单子表失败");
            }
            //            // 3.更新卡服务订单交易后卡余额(单位:分)
            //            count = updateClearCardSysOrderBackAmtByCrdOrderId(crdDTO.getCrdordernum());
            //            if (count != 1) {
            //                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单表失败");
            //            }
            value = new HashMap<String, Object>(1);
            // 4.更新卡服务订单子表特殊域信息
            value.put("RESULT_YKTMW", resultYktMw);
            value.put("CRD_ORDER_NUM", crdDTO.getCrdordernum());
            value.put("UPDATE_USER", crdDTO.getUserid());
            count = updateCardSysSupplementByCrdOrderId(value);
            if (count != 1) {
                throw new DDPException(ResponseCode.SYSTEM_ERROR, "更新卡服务订单子表信息失败");
            }
        } else {
            response.setCode(ResponseCode.CARD_APDU_ERROR);
            return response;
        }
        return response;
    }
}
