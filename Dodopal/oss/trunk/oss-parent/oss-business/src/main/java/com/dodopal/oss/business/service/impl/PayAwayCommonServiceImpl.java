package com.dodopal.oss.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayWayCommonDTO;
import com.dodopal.api.payment.dto.query.PayCommonQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.oss.business.bean.PayAwayBean;
import com.dodopal.oss.business.bean.PayAwayCommonBean;
import com.dodopal.oss.business.model.dto.PayAwayCommonQuery;
import com.dodopal.oss.business.service.PayAwayCommonService;
import com.dodopal.oss.delegate.PayAwayCommonDelegate;
/**
 * 用户常用支付方式 PayAwayCommonServiceImpl
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月11日 
 */
@Service
public class PayAwayCommonServiceImpl implements PayAwayCommonService {

    private final static Logger log = LoggerFactory.getLogger(PayAwayCommonServiceImpl.class);

    @Autowired
    private PayAwayCommonDelegate payAwayCommonDelegate;

    //查询用户常用支付方式（分页）
    public DodopalResponse<DodopalDataPage<PayAwayCommonBean>> findPayAwayCommonList(PayAwayCommonQuery payQuery) {

        DodopalResponse<DodopalDataPage<PayAwayCommonBean>> rtResponse = new DodopalResponse<DodopalDataPage<PayAwayCommonBean>>();
        PayCommonQueryDTO payQueryDTO = new PayCommonQueryDTO();
        try {
            PropertyUtils.copyProperties(payQueryDTO, payQuery);
            DodopalResponse<DodopalDataPage<PayWayCommonDTO>> payWayCommonDTOList = payAwayCommonDelegate.findPayAwayCommonListByPage(payQueryDTO);
            if (ResponseCode.SUCCESS.equals(payWayCommonDTOList.getCode())) {

                List<PayAwayCommonBean> PayAwayCommonBeanList = new ArrayList<PayAwayCommonBean>();

                if (payWayCommonDTOList.getResponseEntity() != null && CollectionUtils.isNotEmpty(payWayCommonDTOList.getResponseEntity().getRecords())) {
                    for (PayWayCommonDTO payDto : payWayCommonDTOList.getResponseEntity().getRecords()) {
                        PayAwayCommonBean payBean = new PayAwayCommonBean();
                        PropertyUtils.copyProperties(payBean, payDto);
                        PayAwayCommonBeanList.add(payBean);
                    }
                    //获取分页信息
                    PageParameter page = DodopalDataPageUtil.convertPageInfo(payWayCommonDTOList.getResponseEntity());
                    // 返回具有分页的用户常用支付方式
                    DodopalDataPage<PayAwayCommonBean> pages = new DodopalDataPage<PayAwayCommonBean>(page, PayAwayCommonBeanList);
                    rtResponse.setCode(payWayCommonDTOList.getCode());
                    rtResponse.setResponseEntity(pages);
                } else {
                    rtResponse.setCode(payWayCommonDTOList.getCode());
                }
            }

        }catch (HessianRuntimeException e) {
            log.debug("PayAwayCommonServiceImpl findPayAwayCommonList call HessianRuntimeException error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PayAwayCommonServiceImpl call error", e);
            e.printStackTrace();
            rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return rtResponse;
    }

    //删除用户常用支付方式
    public DodopalResponse<Integer> deletePayAwayCommon(List<String> ids) {
        
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        try {
            response = payAwayCommonDelegate.deletePayAwayCommon(ids);
        }catch (HessianRuntimeException e) {
            log.debug("PayAwayCommonServiceImpl deletePayAwayCommon call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("PayAwayCommonServiceImpl deletePayAwayCommon call  error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

}
