package com.dodopal.card.business.facadeImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.card.facade.FrontFailReportCardFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.log.SysLogHelper;
import com.dodopal.card.business.model.CrdSysOrder;
import com.dodopal.card.business.service.CardTopupService;
import com.dodopal.card.business.service.FrontFailPeportService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.CardOrderStatesEnum;
import com.dodopal.common.model.DodopalResponse;

@Service("frontFailReportCardFacade")
public class FrontFailReportCardFacadeImpl implements FrontFailReportCardFacade {
    @Autowired
    private FrontFailPeportService frontFailReportService;
    @Autowired
    private CardTopupService cardTopupService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private SysLogHelper sysLogHelper;

    /**
     * 卡服务订单校验方法
     * @Description: 1.通过产品库订单号查询卡服务订单记录，获取订单交易状态,当订单状态为:
     * 申请读卡密钥成功(1000000003)时返回，响应码:000000,否则返回响应码:非000000
     * 2.更新上传的前端交易应答码frontrespcode,卡服务订单状态: 卡片充值失败(1000000009),前卡服务订单状态:
     * 申请读卡密钥成功(1000000003)
     */
    @Override
    public DodopalResponse<CrdSysOrderDTO> frontFailReportFun(CrdSysOrderDTO crdDTO) {
        StringBuffer respexplain = new StringBuffer();
        String logRespcode = ResponseCode.SUCCESS;
        Long tradestart = Long.valueOf(DateUtil.getCurrSdTime());
        String inPras = JSONObject.toJSONString(crdDTO);
        String statckTrace = "";
        DodopalResponse<CrdSysOrderDTO> response = new DodopalResponse<CrdSysOrderDTO>();
        response.setResponseEntity(crdDTO);
        try {
            // 1.检查入参
            if (StringUtils.isBlank(crdDTO.getPrdordernum())) {
                response.setCode(ResponseCode.CARD_PRDORDERNUM_NULL);
                return response;
            }
            if (StringUtils.isBlank(crdDTO.getRespcode())) {
                response.setCode(ResponseCode.CARD_RESPCODE_NULL);
                return response;
            }
            if (StringUtils.isBlank(crdDTO.getUserid())) {
                response.setCode(ResponseCode.CARD_USERID_NULL);
                return response;
            }
            // 2.检查卡服务订单是否存在
            Integer countInteger = frontFailReportService.queryCrdOrderCountByPrdOrderId(crdDTO.getPrdordernum());
            if (countInteger < 1) {
                response.setCode(ResponseCode.CARD_PRDORDERNUM_NOT_EXIST);
                return response;
            }
            // 3.查询卡服务系统订单中所需信息：订单交易状态
            CrdSysOrder order = cardTopupService.findCrdSysOrderByPrdnum(crdDTO.getPrdordernum());
            String compareCode = compareOrder(order, crdDTO);
            if (!ResponseCode.SUCCESS.equals(compareCode)) {
                //3.判断传入的参数卡号前端读出的(tradecard)、一卡通编号 (yktcode)、城市代码 (citycode)、设备编号 (posid)、交易前卡余额 (befbal)、 交易金额 (txnamt)是否一致，否则进行异常处理
                response.setCode(compareCode);
                return response;
            } else {
                if (CardOrderStatesEnum.CARD_ORDER_APPLY_READCARD_KEY_SUCCESS.getCode().equals(order.getCrdOrderStates())) {// 申请读卡密钥成功
                    response.setCode(ResponseCode.SUCCESS);// 成功
                } else {
                    response.setCode(ResponseCode.CARD_ORDER_STATES_ERROR);// 卡服务订单状态不正确
                    return response;
                }
                // 4.更新上传的交易应答码，卡服务订单状态
                Map<String, Object> value = new HashMap<String, Object>(1);
                value.put("RESP_CODE", crdDTO.getRespcode());
                value.put("CRD_ORDER_STATES", CardOrderStatesEnum.CARD_ORDER_RECHARGE_ERROR.getCode());
                value.put("PRO_ORDER_NUM", crdDTO.getPrdordernum());
                value.put("UPDATE_USER", crdDTO.getUserid());
                int count = frontFailReportService.updateCardSysOrderByPrdOrderNum(value);
                if (count != 1) {
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            respexplain.append(e.getMessage());
            response.setCode(ResponseCode.SYSTEM_ERROR);
            statckTrace = e.getMessage();
        }
        finally {
            // 5.记录日志
            logRespcode = response.getCode();
            respexplain.append(response.getMessage());
            logHelper.recordLogFun(inPras, tradestart, logRespcode, respexplain.toString(), "[充值]失败校验(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
            sysLogHelper.recordLogFun(inPras, tradestart, logRespcode, respexplain.toString(), "[充值]失败校验(外部调用)", crdDTO.getCrdordernum(), crdDTO.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), crdDTO.getSource(), statckTrace);
        }
        return response;
    }

    private String compareOrder(CrdSysOrder order, CrdSysOrderDTO crdDTO) {
        //判断传入的参数卡号前端读出的(tradecard)
        if (!crdDTO.getTradecard().equals(order.getCheckCardNo())) {
            return ResponseCode.CARD_TRADECARD_ERROR;
        } else if (!crdDTO.getYktcode().equals(order.getYktCode())) {//一卡通编号 (yktcode)
            return ResponseCode.CARD_YKTCODE_ERROR;
        } else if (!crdDTO.getCitycode().equals(order.getCityCode())) {//城市代码 (citycode)
            return ResponseCode.CARD_CITYCODE_ERROR;
        } else if (!crdDTO.getPosid().equals(order.getPosCode())) {//设备编号 (posid)
            return ResponseCode.CARD_POSID_ERROR;
        } else if (Long.valueOf(crdDTO.getBefbal()) != order.getBefbal()) {//交易前卡余额 (befbal)
            return ResponseCode.CARD_BEFBAL_ERROR;
        } else if (Long.valueOf(crdDTO.getTxnamt()) != order.getTxnAmt()) {//交易金额 (txnamt)
            return ResponseCode.CARD_TXNAMT_ERROR;
        }
        return ResponseCode.SUCCESS;
    }

}
