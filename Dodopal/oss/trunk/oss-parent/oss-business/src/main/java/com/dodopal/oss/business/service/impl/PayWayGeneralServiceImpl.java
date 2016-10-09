package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.PayWayGeneralDTO;
import com.dodopal.api.payment.dto.query.PayWayGeneralQueryDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.bean.PayWayGeneralBean;
import com.dodopal.oss.business.model.dto.PayWayGeneralQuery;
import com.dodopal.oss.business.service.PayWayGeneralService;
import com.dodopal.oss.delegate.PayConfigDelegate;
import com.dodopal.oss.delegate.PayWayGeneralDelegate;

@Service
public class PayWayGeneralServiceImpl implements PayWayGeneralService {

    private final static Logger log = LoggerFactory.getLogger(PayWayGeneralServiceImpl.class);
    
    @Autowired
    private PayWayGeneralDelegate payWayGeneralDelegate;
    @Autowired
    private PayConfigDelegate payConfigDelegate;
    
    /**
     * 查询（分页）
     */
    public DodopalResponse<DodopalDataPage<PayAwayBean>> findPayAwayList(PayWayGeneralQuery payQuery) {
        DodopalResponse<DodopalDataPage<PayAwayBean>> rtResponse = new DodopalResponse<DodopalDataPage<PayAwayBean>>();
        
        try {
            PayWayGeneralQueryDTO payAwayQueryDTO = new PayWayGeneralQueryDTO();
            
            PropertyUtils.copyProperties(payAwayQueryDTO, payQuery);
            
            //查询
            DodopalResponse<DodopalDataPage<PayWayGeneralDTO>> payAwayDTOList =  payWayGeneralDelegate.findPayAwayListByPage(payAwayQueryDTO);
            
            if (ResponseCode.SUCCESS.equals(payAwayDTOList.getCode())) {

                List<PayAwayBean> PayAwayBeanList = new ArrayList<PayAwayBean>();

                if (payAwayDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(payAwayDTOList.getResponseEntity().getRecords())) {
                    for (PayWayGeneralDTO payDto : payAwayDTOList.getResponseEntity().getRecords()) {
                        PayAwayBean payBean = new PayAwayBean();
                        PropertyUtils.copyProperties(payBean, payDto);
                        PayAwayBeanList.add(payBean);
                    }
                    PageParameter page = DodopalDataPageUtil.convertPageInfo(payAwayDTOList.getResponseEntity());
                    DodopalDataPage<PayAwayBean> pages = new DodopalDataPage<PayAwayBean>(page, PayAwayBeanList);
                    rtResponse.setCode(payAwayDTOList.getCode());
                    rtResponse.setResponseEntity(pages);
                } else {
                    rtResponse.setCode(payAwayDTOList.getCode());
                }
            }
        }
        catch (HessianRuntimeException e) {
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return rtResponse;
    }

    /**
     * 新增
     */
    public String savePayAway(PayWayGeneralBean payBean, String payAwayType) {
        validatePayAwayBean(payBean);
        try {
            PayWayGeneralDTO payDTO = new PayWayGeneralDTO();
            PropertyUtils.copyProperties(payDTO, payBean);
            if(payDTO.getId() != null && payDTO.getId() !=""){
                payDTO.setCreateUser(null);
                payWayGeneralDelegate.updatePayAway(payDTO, payAwayType);
            }else{
                payDTO.setUpdateUser(null);
                payWayGeneralDelegate.savePayAway(payDTO, payAwayType);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.SYSTEM_ERROR;
        }
        
        return ResponseCode.SUCCESS;
    }
    
    /**
     * 验证支付信息
     */
    public void validatePayAwayBean(PayWayGeneralBean payBean){
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.isNumberic(String.valueOf(payBean.getSort())) || (String.valueOf(payBean.getSort())).length()>5) {
            msg.add("排序号必须是数字且长度不能大于5位数");
        }
        
        if (!DDPStringUtil.lessThan(payBean.getComments(), 200)) {
            msg.add("备注信息长度不能超过200个字符");
        }
        if(StringUtils.isEmpty(payBean.getId())) { //新建.  支付方式不能与现有的重复。
            int count = payWayGeneralDelegate.countPayGeneral(payBean.getPayConfigId());
            if(count > 0) {
                msg.add("支付方式不能与现有的重复.");
            }
        } 
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, "!\n"));
        }
    }

    /**
     * 修改
     */
    public DodopalResponse<Boolean> updatePayAway(PayWayGeneralBean payBean, String payAwayType) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        PayWayGeneralDTO dto = new PayWayGeneralDTO();
        try {
            PropertyUtils.copyProperties(dto, payBean);
            response = payWayGeneralDelegate.updatePayAway(dto, payAwayType);
        }catch(HessianRuntimeException e){
            log.debug("PayWayGeneralServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("PayWayGeneralServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 修改
     */
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType) {
        return payWayGeneralDelegate.deletePayAway(ids, payAwayType);
    }

    public DodopalResponse<Integer> startOrStop(List<String> ids, String activate,String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = payWayGeneralDelegate.startOrStop(ids, ActivateEnum.getActivateByCode(activate), updateUser);
        }catch(HessianRuntimeException e){
            log.debug("PayWayGeneralServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }catch (Exception e) {
            log.debug("PayWayGeneralServiceImpl call error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 根据id显示信息
     */
    public DodopalResponse<PayWayGeneralBean> findPayAwayById(String id, String payAwayType) {

        DodopalResponse<PayWayGeneralBean> response = new DodopalResponse<PayWayGeneralBean>();
        PayWayGeneralBean payWayGeneralBean = new PayWayGeneralBean();
        PayWayGeneralDTO payWayGeneralDTO = new PayWayGeneralDTO();
        try {
            DodopalResponse<PayWayGeneralDTO> pwgDTO = payWayGeneralDelegate.findPayGeneralById(id, payAwayType);
            PropertyUtils.copyProperties(payWayGeneralDTO, pwgDTO.getResponseEntity());
            if (pwgDTO != null) {
                DodopalResponse<PayConfigDTO> payConfigDTO = payConfigDelegate.findPayConfigById(pwgDTO.getResponseEntity().getPayConfigId());
                payWayGeneralDTO.setPayType(payConfigDTO.getResponseEntity().getPayType());
                payWayGeneralDTO.setPayWayName(payConfigDTO.getResponseEntity().getPayWayName());
                PropertyUtils.copyProperties(payWayGeneralBean,payWayGeneralDTO);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payWayGeneralBean);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

   /**
    * 根据支付类型code查询支付名称
    */
    public List<PayConfigBean> findPayWayNameByPayType(String payType) {
        List<PayConfigBean> payConfigBeanList = new ArrayList<PayConfigBean>();
        try {
            List<PayConfigDTO> payConfigDTOList = payWayGeneralDelegate.findPayWayNameByPayType(payType);
            if (payConfigDTOList != null && payConfigDTOList.size() > 0) {
                for (PayConfigDTO payDTO : payConfigDTOList) {
                    PayConfigBean payConfigBean = new PayConfigBean();
                    PropertyUtils.copyProperties(payConfigBean, payDTO);
                    payConfigBeanList.add(payConfigBean);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return payConfigBeanList;
    }

}
