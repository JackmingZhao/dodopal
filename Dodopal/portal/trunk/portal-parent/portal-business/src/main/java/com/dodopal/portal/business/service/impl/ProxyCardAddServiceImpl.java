package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProxyCardAddDTO;
import com.dodopal.api.product.dto.ProxyCountAllByIDDTO;
import com.dodopal.api.product.dto.ProxyOffLineOrderTbDTO;
import com.dodopal.api.product.dto.query.ProxyCardAddQueryDTO;
import com.dodopal.api.product.dto.query.ProxyOffLineOrderTbQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.portal.business.bean.ProxyCardAddBean;
import com.dodopal.portal.business.bean.ProxyCountAllByIDBean;
import com.dodopal.portal.business.bean.ProxyOffLineOrderTbBean;
import com.dodopal.portal.business.bean.query.ProxyCardAddQuery;
import com.dodopal.portal.business.bean.query.ProxyOffLineOrderTbQuery;
import com.dodopal.portal.business.service.ProxyCardAddService;
import com.dodopal.portal.delegate.ProxyCardAddDelegate;

@Service
public class ProxyCardAddServiceImpl implements ProxyCardAddService {
    private final static Logger log = LoggerFactory.getLogger(ProxyCardAddServiceImpl.class);

    @Autowired
    ProxyCardAddDelegate proxyCardAddDelegate;

    //查询城市一卡通充值记录
    @Override
    public DodopalResponse<DodopalDataPage<ProxyCardAddBean>> cardTradeList(ProxyCardAddQuery proxyCardAddQuery) {
        DodopalResponse<DodopalDataPage<ProxyCardAddBean>> response = new DodopalResponse<DodopalDataPage<ProxyCardAddBean>>();
        try {
            ProxyCardAddQueryDTO proxyCardAddQueryDTO = new ProxyCardAddQueryDTO();
            PropertyUtils.copyProperties(proxyCardAddQueryDTO, proxyCardAddQuery);
            DodopalResponse<DodopalDataPage<ProxyCardAddDTO>> rtResponse = proxyCardAddDelegate.cardTradeList(proxyCardAddQueryDTO);
            List<ProxyCardAddBean> proxyCardAddBeanList = new ArrayList<ProxyCardAddBean>();

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode())) {
                List<ProxyCardAddDTO> proxyCardAddDTOList = rtResponse.getResponseEntity().getRecords();
                if (CollectionUtils.isNotEmpty(proxyCardAddDTOList)) {
                    for (ProxyCardAddDTO proxyCardAddDTO : proxyCardAddDTOList) {
                        ProxyCardAddBean proxyCardAddBean = new ProxyCardAddBean();
                        PropertyUtils.copyProperties(proxyCardAddBean, proxyCardAddDTO);
                        proxyCardAddBeanList.add(proxyCardAddBean);
                    }
                }
                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
                DodopalDataPage<ProxyCardAddBean> pages = new DodopalDataPage<ProxyCardAddBean>(page, proxyCardAddBeanList);
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("查询城市一卡通充值记录 ProxyCardAddServiceImpl cardTradeList call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询城市一卡通充值记录 ProxyCardAddServiceImpl cardTradeList  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //查询城市一卡通充值记录统计
    @Override
    public DodopalResponse<ProxyCountAllByIDBean> findCardTradeListCount(ProxyCardAddQuery proxyCardAddQuery) {

        DodopalResponse<ProxyCountAllByIDBean> response = new DodopalResponse<ProxyCountAllByIDBean>();
        try {
            ProxyCardAddQueryDTO proxyCardAddQueryDTO = new ProxyCardAddQueryDTO();
            PropertyUtils.copyProperties(proxyCardAddQueryDTO, proxyCardAddQuery);
            DodopalResponse<ProxyCountAllByIDDTO> rtResponse = proxyCardAddDelegate.findCardTradeListCount(proxyCardAddQueryDTO);
            ProxyCountAllByIDBean proxyCountAllByIDBean = new ProxyCountAllByIDBean();
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode())) {
                if (null != rtResponse.getResponseEntity()) {
                    PropertyUtils.copyProperties(proxyCountAllByIDBean, rtResponse.getResponseEntity());
                }
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(proxyCountAllByIDBean);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("查询城市一卡通充值记录统计  ProxyCardAddServiceImpl findCardTradeListCount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询城市一卡通充值记录统计  ProxyCardAddServiceImpl findCardTradeListCount  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //查询城市一卡通消费记录
    @Override
    public DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>> offLineTradeList(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {
        DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>> response = new DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbBean>>();
        try {
            ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO = new ProxyOffLineOrderTbQueryDTO();
            PropertyUtils.copyProperties(proxyOffLineOrderTbQueryDTO, proxyOffLineOrderTbQuery);
            DodopalResponse<DodopalDataPage<ProxyOffLineOrderTbDTO>> rtResponse = proxyCardAddDelegate.offLineTradeList(proxyOffLineOrderTbQueryDTO);

            List<ProxyOffLineOrderTbBean> proxyOffLineOrderTbBeanList = new ArrayList<ProxyOffLineOrderTbBean>();

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode())) {
                List<ProxyOffLineOrderTbDTO> proxyOffLineOrderTbDTOList = rtResponse.getResponseEntity().getRecords();
                if (CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                    for (ProxyOffLineOrderTbDTO proxyOffLineOrderTbDTO : proxyOffLineOrderTbDTOList) {
                        ProxyOffLineOrderTbBean proxyOffLineOrderTbBean = new ProxyOffLineOrderTbBean();
                        proxyOffLineOrderTbBean.setAmt(proxyOffLineOrderTbDTO.getAmt());
                        proxyOffLineOrderTbBean.setCardkfhao(proxyOffLineOrderTbDTO.getCardkfhao());
                        proxyOffLineOrderTbBean.setCheckcardno(proxyOffLineOrderTbDTO.getCheckcardno());
                        proxyOffLineOrderTbBean.setCheckcardposid(proxyOffLineOrderTbDTO.getCheckcardposid());
                        proxyOffLineOrderTbBean.setCityid(proxyOffLineOrderTbDTO.getCityid());
                        proxyOffLineOrderTbBean.setFacevalue(proxyOffLineOrderTbDTO.getFacevalue());
                        proxyOffLineOrderTbBean.setCreateDate(proxyOffLineOrderTbDTO.getCreateDate());
                        proxyOffLineOrderTbBean.setCreateUser(proxyOffLineOrderTbDTO.getCreateUser());
                        proxyOffLineOrderTbBean.setId(proxyOffLineOrderTbDTO.getId());
                        proxyOffLineOrderTbBean.setMchnitorderid(proxyOffLineOrderTbDTO.getMchnitorderid());
                        proxyOffLineOrderTbBean.setOrderid(proxyOffLineOrderTbDTO.getOrderid());
                        proxyOffLineOrderTbBean.setOrderserror(proxyOffLineOrderTbDTO.getOrderserror());
                        proxyOffLineOrderTbBean.setOrderstates(proxyOffLineOrderTbDTO.getOrderstates());
                        proxyOffLineOrderTbBean.setOrdertime(proxyOffLineOrderTbDTO.getOrdertime());
                        proxyOffLineOrderTbBean.setPosremark(proxyOffLineOrderTbDTO.getPosremark());
                        proxyOffLineOrderTbBean.setProxyname(proxyOffLineOrderTbDTO.getProxyname());
                        proxyOffLineOrderTbBean.setSale(proxyOffLineOrderTbDTO.getSale());
                        proxyOffLineOrderTbBean.setUniqueIdentification(proxyOffLineOrderTbDTO.getUniqueIdentification());
                        proxyOffLineOrderTbBean.setUpdateDate(proxyOffLineOrderTbDTO.getUpdateDate());
                        proxyOffLineOrderTbBean.setUpdateUser(proxyOffLineOrderTbDTO.getUpdateUser());
                        proxyOffLineOrderTbBean.setYktid(proxyOffLineOrderTbDTO.getYktid());
                        if("总计".equals(proxyOffLineOrderTbDTO.getMchnitorderid())){
                            proxyOffLineOrderTbBean.setBlackamt("");
                            proxyOffLineOrderTbBean.setProamt("");
                        }else{
                            proxyOffLineOrderTbBean.setBlackamt(String.valueOf(proxyOffLineOrderTbDTO.getBlackamt()));
                            proxyOffLineOrderTbBean.setProamt(String.valueOf(proxyOffLineOrderTbDTO.getProamt()));
                        }
                       // PropertyUtils.copyProperties(proxyOffLineOrderTbBean, proxyOffLineOrderTbDTO);
                        proxyOffLineOrderTbBeanList.add(proxyOffLineOrderTbBean);
                    }
                }
                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
                DodopalDataPage<ProxyOffLineOrderTbBean> pages = new DodopalDataPage<ProxyOffLineOrderTbBean>(page, proxyOffLineOrderTbBeanList);
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("查询城市一卡通消费记录  ProxyCardAddServiceImpl offLineTradeList call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询城市一卡通消费记录  ProxyCardAddServiceImpl offLineTradeList  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<ProxyCountAllByIDBean> findoffLineTradeListCount(ProxyOffLineOrderTbQuery proxyOffLineOrderTbQuery) {

        DodopalResponse<ProxyCountAllByIDBean> response = new DodopalResponse<ProxyCountAllByIDBean>();
        try {
            ProxyOffLineOrderTbQueryDTO proxyOffLineOrderTbQueryDTO = new ProxyOffLineOrderTbQueryDTO();
            PropertyUtils.copyProperties(proxyOffLineOrderTbQueryDTO, proxyOffLineOrderTbQuery);
            DodopalResponse<ProxyCountAllByIDDTO> rtResponse = proxyCardAddDelegate.findoffLineTradeListCount(proxyOffLineOrderTbQueryDTO);
            ProxyCountAllByIDBean proxyCountAllByIDBean = new ProxyCountAllByIDBean();
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode())) {
                if (null != rtResponse.getResponseEntity()) {
                    PropertyUtils.copyProperties(proxyCountAllByIDBean, rtResponse.getResponseEntity());
                }
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(proxyCountAllByIDBean);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug("查询城市一卡通充值记录统计  ProxyCardAddServiceImpl findCardTradeListCount call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("查询城市一卡通充值记录统计  ProxyCardAddServiceImpl findCardTradeListCount  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}
