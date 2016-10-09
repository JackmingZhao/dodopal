package com.dodopal.card.business.facadeImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.facade.BJNfcRechargeFacade;
import com.dodopal.card.business.constant.CardConstants;
import com.dodopal.card.business.log.LogHelper;
import com.dodopal.card.business.service.BJNfcService;
import com.dodopal.card.business.util.DateUtil;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;

@Service("bjNfcRechargeFacade")
public class BJNfcRechargeFacadeImpl implements BJNfcRechargeFacade {

    @Autowired
    private LogHelper logHelper;
    @Autowired
    private BJNfcService bjNfcService;

    /*
     * 用户终端信息登记接口(外)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> userTeminalRegister(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.userTeminalRegister(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(retDto, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("用户终端登记失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]终端登记(外)", "", "", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_OTHER);
        }
        return response;
    }

    private void setErrorResponse(BJCrdSysOrderDTO crdDTO, DodopalResponse<BJCrdSysOrderDTO> response, String errorCode) {
        crdDTO.setRespcode(errorCode);
        response.setResponseEntity(crdDTO);
        response.setCode(errorCode);
    }

    /*
     * 充值验卡接口(外)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeValidateCard(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //返回dto
        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.chargeValidateCard(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(crdDTO, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("充值验卡失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]充值验卡(外)", retDto.getCrdordernum(), retDto.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 充值申请起始(外)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeStart(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //返回dto
        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.chargeStart(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(crdDTO, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("充值申请起始失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]充值申请起始(外)", retDto.getCrdordernum(), retDto.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 充值申请后续(外)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeFollow(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //返回dto
        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.chargeFollow(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(crdDTO, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("充值申请后续失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]充值申请后续(外)", retDto.getCrdordernum(), retDto.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 充值结果上传(外)
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> chargeResultUp(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //返回dto
        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.chargeResultUp(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(crdDTO, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("充值结果上传失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]结果上传(外)", retDto.getCrdordernum(), retDto.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 补充值申请起始
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> reChargeApplyStart(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //返回dto
        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.reChargeApplyStart(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(crdDTO, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("补充值申请起始失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]补充值起始(外)", retDto.getCrdordernum(), retDto.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

    /*
     * 补充值申请后续
     */
    @Override
    public DodopalResponse<BJCrdSysOrderDTO> reChargeApplyFollow(BJCrdSysOrderDTO crdDTO) {
        Long logTradeStart = Long.valueOf(DateUtil.getCurrSdTime());//日志开始时间
        StringBuffer logRespexplain = new StringBuffer();//日志响应吗描述
        String logRespcode = ResponseCode.SUCCESS;//日志响应吗
        String inPras = JSONObject.toJSONString(crdDTO);//入参

        DodopalResponse<BJCrdSysOrderDTO> response = new DodopalResponse<BJCrdSysOrderDTO>();
        response.setCode(ResponseCode.SUCCESS);

        //返回dto
        BJCrdSysOrderDTO retDto = new BJCrdSysOrderDTO();
        try {
            //调用服务
            retDto = bjNfcService.reChargeApplyFollow(crdDTO);
            if (!ResponseCode.SUCCESS.equals(retDto.getRespcode())) {
                setErrorResponse(crdDTO, response, retDto.getRespcode());
                logRespcode = retDto.getRespcode();
                logRespexplain.append("补充值申请后续失败:");
                logRespexplain.append(response.getMessage());
            } else {
                response.setResponseEntity(retDto);
            }
        }
        catch (DDPException e) {
            response.setCode(e.getCode());
            logRespcode = e.getCode();
            logRespexplain.append(e.getMessage());
            crdDTO.setRespcode(e.getCode());
            response.setResponseEntity(crdDTO);
            e.printStackTrace();
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            logRespcode = ResponseCode.SYSTEM_ERROR;
            logRespexplain.append(e.getCause());
            crdDTO.setRespcode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(crdDTO);
        }
        finally {
            logHelper.recordLogFun(inPras, logTradeStart, logRespcode, logRespexplain.toString(), "[BJ][NFC]补充值后续(外)", retDto.getCrdordernum(), retDto.getPrdordernum(), this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), JSONObject.toJSONString(response), CardConstants.CARD_LOG_TXNTYPE_CHARGE);
        }
        return response;
    }

}
