package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.bean.CardListResultDTO;
import com.dodopal.product.business.service.CardService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.web.param.BaseResponse;
import com.dodopal.product.web.param.CardBindRequest;
import com.dodopal.product.web.param.CardListRequest;
import com.dodopal.product.web.param.CardListResponse;
import com.dodopal.product.web.param.CardUnbindRequest;

/**
 * 卡片绑定、卡片解绑、卡片列表查询接口（手机端和VC端接入）
 * @author xiongzhijing@dodopal.com
 * @version 2015年10月20日
 *
 */
@Controller
@RequestMapping("/card")
public class CardController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CardController.class);
    @Autowired
    CardService cardService;
    
    @Autowired
    MerchantUserService merchantUserService;
    
    /**
     * 3.4 卡片绑定 （手机端、VC端）
     */
    @RequestMapping("/cardBind")
    public @ResponseBody BaseResponse cardBind(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if(StringUtils.isBlank(jsondata)) {
                if(logger.isInfoEnabled()) {
                    logger.info("CardController cardBind:用户绑卡:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if(logger.isInfoEnabled()) {
                logger.info("CardController cardBind:用户绑卡:接收到的jsondata参数:"+jsondata);
            }
            CardBindRequest cardBindRequest = convertJsonToRequest(jsondata, CardBindRequest.class);// 转换jsondata
            baseCheck(cardBindRequest);// 通用参数校验
            cardBindRequestCheck(cardBindRequest);// 业务入参校验
            signCheck(key, cardBindRequest);// 验签
            
            MerUserCardBDDTO cardBDDTO = new MerUserCardBDDTO();
            
            cardBDDTO.setCardCode(cardBindRequest.getTradecard());
            cardBDDTO.setCardName(cardBindRequest.getTradecardname());
            cardBDDTO.setMerUserName(cardBindRequest.getUsername());
            cardBDDTO.setSource(cardBindRequest.getSource());
            cardBDDTO.setRemark(cardBindRequest.getRemark());
            
            DodopalResponse<MerUserCardBDDTO> rTResponse = cardService.saveMerUserCardBD(cardBDDTO);
            response.setRespcode(rTResponse.getCode());
        } catch (DDPException e) {
            response.setRespcode(e.getCode());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if(logger.isInfoEnabled()) {
            logger.info("CardController cardBind:用户绑卡:返回respcode:" + response.getRespcode());
        }
        return response;
    }
    
  
    /**
     * 3.5 用户已绑卡片的列表查询  （手机端、VC端）
     */
    @RequestMapping("/findCardList")
    public @ResponseBody CardListResponse findCardList(HttpServletRequest request) {
        CardListResponse response = new CardListResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            if(StringUtils.isBlank(jsondata)) {
                if(logger.isInfoEnabled()) {
                    logger.info("CardController findCardList:用户绑定卡片 列表查询:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if(logger.isInfoEnabled()) {
                logger.info("CardController findCardList:用户绑定卡片 列表查询:接收到的jsondata参数:"+jsondata);
            }
            CardListRequest cardListRequest = convertJsonToRequest(jsondata, CardListRequest.class);// 转换jsondata
            baseCheck(cardListRequest);// 通用参数校验
            cardListRequestCheck(cardListRequest);// 业务入参校验
            signCheck(key, cardListRequest);// 验签
            
            MerUserCardBDFindDTO cardBDFindDTO = new MerUserCardBDFindDTO();
            cardBDFindDTO.setMerUserName(cardListRequest.getUsername());
            cardBDFindDTO.setBundLingType(BindEnum.ENABLE.getCode());
            
            DodopalResponse<List<CardListResultDTO>> rTResponse = cardService.findMerUserCardBD(cardBDFindDTO);
            
            if (ResponseCode.SUCCESS.equals(rTResponse.getCode())&&rTResponse.getResponseEntity()!=null) {
                response.setRespcode(rTResponse.getCode());
                response.setCardListResultDTOList(rTResponse.getResponseEntity());
            } else {
                response.setRespcode(rTResponse.getCode());
            }
            //TODO 签名
            //sign(key, response);
        } catch (DDPException e) {
            response.setRespcode(e.getCode());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if(logger.isInfoEnabled()) {
            logger.info("CardController findCardList:用户绑定卡片 列表查询:返回respcode:" + response.getRespcode());
        }
        return response;
    }
    
    
    

    /**
     * 3.6 卡片解绑（手机端、VC端）
     */
    @RequestMapping("/cardUnbind")
    public @ResponseBody BaseResponse cardUnbind(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        String key = "123";// TODO:签名验签秘钥如何获取
        try {
            String jsondata = request.getParameter("jsondata");// 获取jsondata
            JSONObject jo = JSONObject.parseObject(jsondata, JSONObject.class);
            
            if(StringUtils.isBlank(jsondata)) {
                if(logger.isInfoEnabled()) {
                    logger.info("CardController cardUnbind:用户卡片解绑:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if(logger.isInfoEnabled()) {
                logger.info("CardController cardUnbind:用户卡片解绑:接收到的jsondata参数:"+jsondata);
            }
            
            CardUnbindRequest cardUnbindRequest = convertJsonToRequest(jsondata, CardUnbindRequest.class);// 转换jsondata
            baseCheck(cardUnbindRequest);// 通用参数校验
            cardUnbindRequestCheck(cardUnbindRequest);// 业务入参校验
            
            // 获取新的签名MAP
            //DodopalResponse<Map<String, String>> rep = SignUtils.getSignMap(obj);
            
            Map<String,String> map = new HashMap<String, String>();
            map.put("reqdate",jo.getString("reqdate"));
            map.put("userid",jo.getString("userid"));
            map.put("source",jo.getString("source"));
            map.put("ids",jo.getString("ids"));
            map.put("input_charset", jo.getString("input_charset"));
            // 新的数据签名
            String signNew = SignUtils.sign(SignUtils.createLinkString(map), key, CommonConstants.CHARSET_UTF_8);
            String sign = jo.getString("sign");
            if(!signNew.equals(sign)){
                throw new DDPException(ResponseCode.PRODUCT_REQ_SIGN_ERROR); 
            }
            
            //signCheck(key, jo);// 验签
            
            String userId = cardUnbindRequest.getUserid();
            String operName = "";
            String source = cardUnbindRequest.getSource();
            List<String> ids = new ArrayList<String>(Arrays.asList(cardUnbindRequest.getIds()));
            
            DodopalResponse<String> rep =  merchantUserService.findNickNameById(userId);
            
            if(ResponseCode.SUCCESS.equals(rep.getCode())&&rep.getResponseEntity()!=null){
                operName = rep.getResponseEntity();
            }
            
            DodopalResponse<String> rTResponse = cardService.unBundlingCard(userId, operName, ids, source);
            response.setRespcode(rTResponse.getCode());
        } catch (DDPException e) {
            response.setRespcode(e.getCode());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
        }
        if(logger.isInfoEnabled()) {
            logger.info("CardController cardUnbind:用户卡片解绑:返回respcode:" + response.getRespcode());
        }
        return response;
    }  
    
    
    
    /**
     * 3.4 卡片绑定   业务入参检查
     * @param request
     */
    private void cardBindRequestCheck(CardBindRequest request) {
        // 用户名    String[20]   不能为空
        String username = request.getUsername();
        if (!DDPStringUtil.existingWithLength(username, 40)) {
            throw new DDPException(ResponseCode.USERS_FIND_USER_NAME_NULL);
        }
        // 来源   String[1]   不为空 手机端：设值为2， VC端：设值为3 不能为空
        String source = request.getSource();
        if (!DDPStringUtil.existingWithLength(source, 40)) {
            throw new DDPException(ResponseCode.USERS_SOURCE_NOT_EMPTY);
        }
        // 卡号   String[50]   读取的一卡通卡号 不能为空
        String tradecard = request.getTradecard();
        if (!DDPStringUtil.existingWithLength(tradecard, 50)) {
            throw new DDPException(ResponseCode.USERS_CARD_CODE_NOT_EMPTY);
        }
      
    }
    
    
    /**
     * 3.5 用户绑定卡片列表查询   业务入参检查
     * @param request
     */
    private void cardListRequestCheck(CardListRequest request) {
        // 用户名    String[20]   不能为空
        String username = request.getUsername();
        if (!DDPStringUtil.existingWithLength(username, 40)) {
            throw new DDPException(ResponseCode.USERS_FIND_USER_NAME_NULL);
        }
    }
    
    
    
    /**
     * 3.6 用户卡片解绑   业务入参检查
     * @param request
     */
    private void cardUnbindRequestCheck(CardUnbindRequest request) {
        // 操作人id    String[20]   不能为空
        String userid = request.getUserid();
        if (!DDPStringUtil.existingWithLength(userid, 40)) {
            throw new DDPException(ResponseCode.USERS_FIND_USER_ID_NULL);
        }
        // 来源   String[1]   不为空 手机端：设值为2， VC端：设值为3 不能为空
        String source = request.getSource();
        if (!DDPStringUtil.existingWithLength(source, 40)) {
            throw new DDPException(ResponseCode.USERS_SOURCE_NOT_EMPTY);
        }
        // 需要解绑的邦卡数据库id   String[50]   读取的一卡通卡号 不能为空
        String[] ids = request.getIds();
        if (ids.length<=0) {
            throw new DDPException(ResponseCode.MER_USER_CARD_BD_ID_NULL);
        }
      
    }
    
   
    
    
}
