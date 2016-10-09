package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerCountDTO;
import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.api.users.dto.query.MerCountQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.MerCountBean;
import com.dodopal.portal.business.bean.PosBean;
import com.dodopal.portal.business.bean.query.MerCountQuery;
import com.dodopal.portal.business.bean.query.PosQuery;
import com.dodopal.portal.business.service.SupplierService;
import com.dodopal.portal.delegate.SupplierDelegate;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
    private final static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    SupplierDelegate SupplierDelegate;

    //查询商户pos信息
    @Override
    public DodopalResponse<DodopalDataPage<PosBean>> countMerchantPosForSupplier(PosQuery posQuery) {
        DodopalResponse<DodopalDataPage<PosBean>> response = new DodopalResponse<DodopalDataPage<PosBean>>();
        try {
            PosQueryDTO posQueryDTO = new PosQueryDTO();
            PropertyUtils.copyProperties(posQueryDTO, posQuery);
            DodopalResponse<DodopalDataPage<PosDTO>> rtResponse = SupplierDelegate.countMerchantPosForSupplier(posQueryDTO);
            List<PosBean> PosBeanList = new ArrayList<PosBean>();

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                List<PosDTO> posDTOList = rtResponse.getResponseEntity().getRecords();
                for (PosDTO posDTO : posDTOList) {
                    PosBean posBean = new PosBean();
                    PropertyUtils.copyProperties(posBean, posDTO);
                    PosBeanList.add(posBean);
                }

                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
                DodopalDataPage<PosBean> pages = new DodopalDataPage<PosBean>(page, PosBeanList);
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug(" SupplierServiceImpl countMerchantPosForSupplier call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(" SupplierServiceImpl countMerchantPosForSupplier  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //查询城市商户信息和pos数量
    @Override
    public DodopalResponse<DodopalDataPage<MerCountBean>> countMerchantForSupplier(MerCountQuery merCountQuery) {
        DodopalResponse<DodopalDataPage<MerCountBean>> response = new DodopalResponse<DodopalDataPage<MerCountBean>>();
        try {
            MerCountQueryDTO merCountQueryDTO = new MerCountQueryDTO();
            PropertyUtils.copyProperties(merCountQueryDTO, merCountQuery);
            DodopalResponse<DodopalDataPage<MerCountDTO>> rtResponse = SupplierDelegate.countMerchantForSupplier(merCountQueryDTO);
            List<MerCountBean> merCountBeanList = new ArrayList<MerCountBean>();

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                List<MerCountDTO> merCountDTOList = rtResponse.getResponseEntity().getRecords();
                for (MerCountDTO merCountDTO : merCountDTOList) {
                    MerCountBean merCountBean = new MerCountBean();
                    PropertyUtils.copyProperties(merCountBean, merCountDTO);
                    merCountBeanList.add(merCountBean);
                }

                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
                DodopalDataPage<MerCountBean> pages = new DodopalDataPage<MerCountBean>(page, merCountBeanList);
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(rtResponse.getCode());
            }

        }
        catch (HessianRuntimeException e) {
            log.debug(" SupplierServiceImpl countMerchantForSupplier call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(" SupplierServiceImpl countMerchantForSupplier  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        // TODO Auto-generated method stub
        return response;
    }

    /**
     * POS操作 绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param userId 操作员ID
     * @param userName 操作员姓名
     * @return
     */
    @Override
    public DodopalResponse<String> posOper(PosOperTypeEnum posOper, String merCode, String[] pos, String userId, String userName) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            OperUserDTO operUser = new OperUserDTO();
            operUser.setOperName(userName);
            operUser.setId(userId);
            response = SupplierDelegate.posOper(posOper, merCode, pos, operUser);
        }
        catch (HessianRuntimeException e) {
            log.debug(" SupplierServiceImpl posOper call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(" SupplierServiceImpl posOper  throws e:" + e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    //导出
    @Override
    public DodopalResponse<String> excelExport(HttpServletRequest request, HttpServletResponse response, MerCountQuery merCountQuery) {
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            MerCountQueryDTO merCountQueryDTO = new MerCountQueryDTO();
            PropertyUtils.copyProperties(merCountQueryDTO, merCountQuery);
            int exportMaxNum = 5000;
            merCountQueryDTO.setPage(new PageParameter(1, exportMaxNum));
            DodopalResponse<DodopalDataPage<MerCountDTO>> rtResponse = SupplierDelegate.countMerchantForSupplier(merCountQueryDTO);

            List<MerCountBean> merCountBeanList = new ArrayList<MerCountBean>();

            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("merName", "商户名称");
            index.put("merUserName", "商户管理员");
            index.put("merUserMobile", "手机号码");
            index.put("posCount", "拥有pos数量");
            index.put("merAddress", "店面地址");
            index.put("activateView", "启用标识");
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                List<MerCountDTO> merCountDTOList = rtResponse.getResponseEntity().getRecords();
                for (MerCountDTO dto : merCountDTOList) {
                    MerCountBean bean = new MerCountBean();
                    PropertyUtils.copyProperties(bean, dto);
                    merCountBeanList.add(bean);
                }

            }
            String code = ExcelUtil.excelExport(request, response, merCountBeanList, index, "商户信息");
            excelExport.setCode(code);

        }
        catch (HessianRuntimeException e) {
            log.debug(" 导出 商户信息 SupplierServiceImpl excelExport countMerchantForSuppliercall HessianRuntimeException error", e);
            e.printStackTrace();
            excelExport.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }

        return excelExport;
    }

    @Override
    public DodopalResponse<String> exportPos(HttpServletRequest request, HttpServletResponse response, PosQuery queryDTO) {
        DodopalResponse<String> excelExport = new DodopalResponse<String>();
        try {
            PosQueryDTO posQueryDTO = new PosQueryDTO();
            PropertyUtils.copyProperties(posQueryDTO, queryDTO);
            int exportMaxNum = 5000;
            posQueryDTO.setPage(new PageParameter(1, exportMaxNum));
            DodopalResponse<DodopalDataPage<PosDTO>> rtResponse = SupplierDelegate.countMerchantPosForSupplier(posQueryDTO);

            List<PosBean> posBeanList = new ArrayList<PosBean>();

            Map<String, String> index = new LinkedHashMap<String, String>();
            index.put("code", "pos编号");
            index.put("statusView", "pos状态");
            index.put("bundlingDate", "pos绑定时间");

            if (ResponseCode.SUCCESS.equals(rtResponse.getCode()) && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                List<PosDTO> posDTOList = rtResponse.getResponseEntity().getRecords();
                for (PosDTO dto : posDTOList) {
                    PosBean bean = new PosBean();
                    PropertyUtils.copyProperties(bean, dto);
                    posBeanList.add(bean);
                }
            }
            String code = ExcelUtil.excelExport(request, response, posBeanList, index, "商户pos信息");
            excelExport.setCode(code);

        }
        catch (HessianRuntimeException e) {
            log.debug(" 导出 商户pos信息 SupplierServiceImpl excelExport countMerchantPosForSupplier HessianRuntimeException error", e);
            e.printStackTrace();
            excelExport.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            excelExport.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }

        return excelExport;
    }

}
