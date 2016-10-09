package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.dodopal.api.payment.dto.query.PayAwayQueryDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayConfigBean;
import com.dodopal.oss.business.model.dto.PayAwayQuery;
import com.dodopal.oss.business.service.PayAwayService;
import com.dodopal.oss.delegate.MerchantDelegate;
import com.dodopal.oss.delegate.PayAwayDelegate;
import com.dodopal.oss.delegate.PayConfigDelegate;
/**
 * 外接支付方式
 * @author xiongzhijing
 *@version 创建时间：2015年7月30日 
 */
@Service
public class PayAwayServiceImpl implements PayAwayService {

    private final static Logger log = LoggerFactory.getLogger(PayAwayServiceImpl.class);

    @Autowired
    private PayAwayDelegate payAwayDelegate;
    
    @Autowired
    PayConfigDelegate payConfigDelegate;
    
    @Autowired
    MerchantDelegate merchantdelegate;
     
    // 查询外接支付方式（分页）
    public DodopalResponse<DodopalDataPage<PayAwayBean>> findPayAwayByPage(PayAwayQuery payQuery) {
        // TODO Auto-generated method stub
        DodopalResponse<DodopalDataPage<PayAwayBean>> rtResponse = new DodopalResponse<DodopalDataPage<PayAwayBean>>();

        try {
            PayAwayQueryDTO payAwayQueryDTO = new PayAwayQueryDTO();
          
            //将查询条件封装的Query转成传输的DTO
            PropertyUtils.copyProperties(payAwayQueryDTO, payQuery);

            //查询外接支付方式的列表
            DodopalResponse<DodopalDataPage<PayAwayDTO>> payAwayDTOList = payAwayDelegate.findPayAwayListByPage(payAwayQueryDTO);

            if (ResponseCode.SUCCESS.equals(payAwayDTOList.getCode())) {

                List<PayAwayBean> PayAwayBeanList = new ArrayList<PayAwayBean>();
                 
                if (payAwayDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(payAwayDTOList.getResponseEntity().getRecords())) {
                    for (PayAwayDTO payDto : payAwayDTOList.getResponseEntity().getRecords()) {
                        PayAwayBean payBean = new PayAwayBean();
                        PropertyUtils.copyProperties(payBean, payDto);
                        PayAwayBeanList.add(payBean);
                    }
                    //获取分页信息
                    PageParameter page = DodopalDataPageUtil.convertPageInfo(payAwayDTOList.getResponseEntity());
                    // 返回具有分页的外接支付方式
                    DodopalDataPage<PayAwayBean> pages = new DodopalDataPage<PayAwayBean>(page, PayAwayBeanList);
                    rtResponse.setCode(payAwayDTOList.getCode());
                    rtResponse.setResponseEntity(pages);
                } else {
                    rtResponse.setCode(payAwayDTOList.getCode());
                }
            }
        }
        catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl findPayAwayByPage  call  HessianRuntimeException error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl findPayAwayByPage call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return rtResponse;
    }

    /**
     * 新增或修改外接支付方式
     * @param PayAwayBean 外接支付方式对应的实体bean
     * @return String 执行结果
     */
    public String saveOrUpdatePayAway(PayAwayBean payBean) {
        //校验参数
        validatePayAwayBean(payBean);
        try {
            PayAwayDTO payDTO = new PayAwayDTO();
            PropertyUtils.copyProperties(payDTO, payBean);
            if(payDTO.getId() != null && payDTO.getId() !=""){
                payAwayDelegate.updatePayAway(payDTO);
            }else{
                payDTO.setUpdateUser(null);
                payAwayDelegate.savePayAway(payDTO);
            }
           
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl saveOrUpdatePayAway call HessianRuntimeException error", e);
            e.printStackTrace();
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl saveOrUpdatePayAway call  error", e);
            e.printStackTrace();
        }
        return CommonConstants.SUCCESS;
    }
    
    /**
     *校验 添加和编辑外接支付方式 参数合不合法
     * @param payBean 外接支付方式对应的实体bean
     */
    public void validatePayAwayBean(PayAwayBean payBean){
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(payBean.getMerCode(), 40)) {
            msg.add("商户名称不能为空或长度不能超过40个字符");
        }
        if (!DDPStringUtil.isNumberic(String.valueOf(payBean.getSort())) || (String.valueOf(payBean.getSort())).length()>5) {
            msg.add("排序号必须是数字且长度不能大于5位数");
        }
        
        if (!DDPStringUtil.lessThan(payBean.getComments(), 200)) {
            msg.add("备注信息长度不能超过200个字符");
        }
        
        if(StringUtils.isEmpty(payBean.getId())) { //新建.  商户编号和支付方式不能与现有的重复。
            int count = payAwayDelegate.countPayExternal(payBean.getMerCode(), payBean.getPayConfigId());
            if(count > 0) {
                msg.add("商户名称和支付方式不能与现有的重复.");
            }
        } 
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, "!\n"));
        }
    }
    

    //修改
    public DodopalResponse<Integer> updatePayAway(PayAwayBean payBean) {
        DodopalResponse<Integer> upRseponse = new DodopalResponse<Integer>();
        try {
            PayAwayDTO payDTO = new PayAwayDTO();
            PropertyUtils.copyProperties(payDTO, payBean);
            upRseponse = payAwayDelegate.updatePayAway(payDTO);
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl updatePayAway call HessianRuntimeException error", e);
            e.printStackTrace();
            upRseponse.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl updatePayAway call error", e);
            e.printStackTrace();
            upRseponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return upRseponse;
    }

    //删除
    public DodopalResponse<Integer> deletePayAway(List<String> ids, String payAwayType) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = payAwayDelegate.deletePayAway(ids, payAwayType);
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl deletePayAway call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl deletePayAway call  error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //启用停用
    public DodopalResponse<Integer> enableOrDisablePayAway(List<String> ids, String activate, String payAwayType, String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = payAwayDelegate.enableOrDisablePayAway(ids, activate, payAwayType, updateUser);
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl enableOrDisablePayAway call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl enableOrDisablePayAway call  error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //根据id查找
    public PayAwayBean findPayExternalById(String id, String payAwayType) {
        PayAwayBean payAwayBean = new PayAwayBean();
        try {
            //根据外接支付方式id 查询对应的外接支付方式对应的传输Dto
            PayAwayDTO payDTO = payAwayDelegate.findPayExternalById(id, payAwayType);
            if (payDTO != null) {
                DodopalResponse<PayConfigDTO> payConfigDTO = payConfigDelegate.findPayConfigById(payDTO.getPayConfigId());
                DodopalResponse<MerchantDTO> merchantDTO =  merchantdelegate.findMerchantByCode(payDTO.getMerCode());
                payDTO.setPayType(payConfigDTO.getResponseEntity().getPayType());
                payDTO.setPayWayName(payConfigDTO.getResponseEntity().getPayWayName());
                payDTO.setMerName(merchantDTO.getResponseEntity().getMerName());
                PropertyUtils.copyProperties(payAwayBean, payDTO);
            }
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl findPayExternalById call HessianRuntimeException error", e);
            e.printStackTrace();
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl findPayExternalById call  error", e);
            e.printStackTrace();
        }

        return payAwayBean;
    }

    //根据支付类型查询支付方式
    public List<PayConfigBean> findPayWayNameByPayType(String payType) {
        List<PayConfigBean> payConfigBeanList = new ArrayList<PayConfigBean>();
        try {
            // 根据支付类型返回支付方式对应的传输实体Dto list
            List<PayConfigDTO> payConfigDTOList = payAwayDelegate.findPayWayNameByPayType(payType);
           
            // 将支付方式对应的传输实体Dto 的 List 转换为 外接支付方式实体bean 的 List
            if (payConfigDTOList != null && payConfigDTOList.size() > 0) {
                for (PayConfigDTO payDTO : payConfigDTOList) {
                    PayConfigBean payConfigBean = new PayConfigBean();
                    //将支付方式对应的传输实体Dto 转换为 外接支付方式实体bean
                    PropertyUtils.copyProperties(payConfigBean, payDTO);
                    payConfigBeanList.add(payConfigBean);
                }
            }
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayServiceImpl findPayWayNameByPayType call HessianRuntimeException error", e);
            e.printStackTrace();
        }
        catch (Exception e) {
            log.debug("PayAwayServiceImpl findPayWayNameByPayType call  error", e);
            e.printStackTrace();
        }
        return payConfigBeanList;
    }

}
