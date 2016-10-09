package com.dodopal.payment.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.payment.dto.PayAwayDTO;
import com.dodopal.api.payment.dto.PayConfigDTO;
import com.dodopal.api.payment.dto.query.PayAwayQueryDTO;
import com.dodopal.api.payment.facade.PayWayExternalFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.payment.business.model.PayConfig;
import com.dodopal.payment.business.model.PayWayExternal;
import com.dodopal.payment.business.model.query.PayWayQuery;
import com.dodopal.payment.business.service.PayCommonService;
import com.dodopal.payment.business.service.PayWayExternalService;

/**
 * 外接支付方式 PayWayExternalFacadeImpl
 * @author dodopal
 */
@Service("payWayExternalFacade")
public class PayWayExternalFacadeImpl implements PayWayExternalFacade {

    private final static Logger log = LoggerFactory.getLogger(PayWayExternalFacadeImpl.class);

    @Autowired
    private PayWayExternalService payWayExternalService;

    @Autowired
    private PayCommonService payCommonService;

    /**
     * 查询外接支付方式 分页
     */
    @Override
    public DodopalResponse<DodopalDataPage<PayAwayDTO>> findPayWayExternalByPage(PayAwayQueryDTO queryDto) {

        DodopalResponse<DodopalDataPage<PayAwayDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<PayAwayDTO>>();
        PayWayQuery payWayQuery = new PayWayQuery();

        try {
            //将外接支付方式查询条件封装的在传输时的实体转成 Query实体
            PropertyUtils.copyProperties(payWayQuery, queryDto);
            //查询外接支付方式
            DodopalDataPage<PayWayExternal> pagelist = payWayExternalService.findPayWayExternalByPage(payWayQuery);
            //获取外接支付方式实体List
            List<PayWayExternal> PayWayExternalList = pagelist.getRecords();

            List<PayAwayDTO> payDTOList = new ArrayList<PayAwayDTO>();
            //将查询到的外接支付方式实体转成外接支付方式在传输时的实体Dto
            if (CollectionUtils.isNotEmpty(PayWayExternalList)) {
                for (PayWayExternal PayWayExternalTemp : PayWayExternalList) {
                    PayAwayDTO PayAwayDTO = new PayAwayDTO();
                    PropertyUtils.copyProperties(PayAwayDTO, PayWayExternalTemp);
                    payDTOList.add(PayAwayDTO);
                }
            }
            //获取分页信息
            PageParameter page = DodopalDataPageUtil.convertPageInfo(pagelist);
            DodopalDataPage<PayAwayDTO> pages = new DodopalDataPage<PayAwayDTO>(page, payDTOList);
            dodopalResponse.setCode(ResponseCode.SUCCESS);
            dodopalResponse.setResponseEntity(pages);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.debug("PayWayExternalFacadeImpl call error", e);
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return dodopalResponse;
    }

    /**
     * 批量删除
     */
    public DodopalResponse<Integer> batchDelPayWayExternalByIds(List<String> ids) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        int num = 0;
        try {
            if (ids != null && ids.size() > 0) {

                try {
                    List<String> idList = new ArrayList<String>();
                    for (String payWayId : ids) {
                        List<String> id = payCommonService.findPaywayCommonByPayWayId(payWayId);
                        idList.addAll(id);
                    }
                    if (idList != null && idList.size() > 0) {
                        payCommonService.deletePayCommonByIds(idList);
                    }
                }
                catch (Exception e) {
                    log.debug("删除外接支付方式  同时删除对应的用户常用支付方式  PayWayExternalFacadeImpl batchDelPayWayExternalByIds call error", e);
                    e.printStackTrace();
                }

                num = payWayExternalService.batchDelPayExternal(ids);
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }

    /**
     * 保存
     */
    @Override
    public DodopalResponse<Integer> savePayWayExternal(PayAwayDTO payAwayDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        //校验参数
        validatePayAwayDTO(payAwayDTO);
        int num = 0;
        try {
            PayWayExternal payExternal = new PayWayExternal();
            PropertyUtils.copyProperties(payExternal, payAwayDTO);
            num = payWayExternalService.savePayWayExternal(payExternal);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(num);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * 修改
     */
    public DodopalResponse<Integer> updatePayAway(PayAwayDTO payDTO) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        PayWayExternal payExternal = new PayWayExternal();
        //校验参数
        validatePayAwayDTO(payDTO);
        int num = 0;
        if (payDTO != null) {
            try {

                PropertyUtils.copyProperties(payExternal, payDTO);
                num = payWayExternalService.updatePayWay(payExternal);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(num);

            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
            }
        }

        return response;
    }

    /**
     * 校验 添加和编辑外接支付方式 参数合不合法
     * @param payDTO 外接支付方式对应的实体DTO
     */
    public void validatePayAwayDTO(PayAwayDTO payDTO) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(payDTO.getMerCode(), 40)) {
            msg.add("商户名称不能为空或长度不能超过40个字符");
        }
        if (!DDPStringUtil.isNumberic(String.valueOf(payDTO.getSort())) || (String.valueOf(payDTO.getSort())).length() > 5) {
            msg.add("排序号必须是数字且长度不能大于5位数");
        }

        if (!DDPStringUtil.lessThan(payDTO.getComments(), 200)) {
            msg.add("备注信息长度不能超过200个字符");
        }

        if (StringUtils.isEmpty(payDTO.getId())) { //新建.  商户编号和支付方式不能与现有的重复。
            int count = payWayExternalService.countBymerCodeAndPayConfigId(payDTO.getMerCode(), payDTO.getPayConfigId());
            if (count > 0) {
                msg.add("商户名称和支付方式不能与现有的重复.");
            }
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, "!\n"));
        }
    }

    /**
     * 批量启用停用
     */
    public DodopalResponse<Integer> updatePayWayExternalActivate(List<String> ids, String activate, String updateUser) {
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        response.setCode(ResponseCode.SUCCESS);
        int num = 0;
        try {
            if (activate == null) {
                response.setCode(ResponseCode.ACTIVATE_ERROR);
                return response;
            }
            if (CollectionUtils.isEmpty(ids)) {
                response.setResponseEntity(num);
                return response;
            }
            num = payWayExternalService.batchActivateOperator(activate, updateUser, ids);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        response.setResponseEntity(num);
        return response;
    }

    public PayAwayDTO findPayExternalById(String id) {
        PayAwayDTO payAwayDTO = new PayAwayDTO();
        PayWayExternal payWayExternal = new PayWayExternal();
        try {
            if (id != null && id != "") {
                payWayExternal = payWayExternalService.findPayExternalById(id);
            }
            PropertyUtils.copyProperties(payAwayDTO, payWayExternal);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return payAwayDTO;
    }

    /**
     * 根据支付类型查找支付方式
     */
    public List<PayConfigDTO> findPayWayNameByPayType(String payType) {
        List<PayConfigDTO> payConfigDTOList = new ArrayList<PayConfigDTO>();
        List<PayConfig> payConfigList = payWayExternalService.findPayWayNameByPayType(payType);
        if (payConfigList != null && payConfigList.size() > 0) {

            for (PayConfig pay : payConfigList) {
                PayConfigDTO payConfigDTO = new PayConfigDTO();
                try {
                    PropertyUtils.copyProperties(payConfigDTO, pay);
                    payConfigDTOList.add(payConfigDTO);
                }
                catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
        }

        return payConfigDTOList;
    }

    //根据商户编码和支付方式配置信息表id查询表中是否已经存在该支付方式
    public int countBymerCodeAndPayConfigId(String merCode, String payConfigId) {
        return payWayExternalService.countBymerCodeAndPayConfigId(merCode, payConfigId);
    }

    //根据外接支付方式ID 查询外接支付方式详细信息
    public DodopalResponse<PayAwayDTO> findPayExternalByPayId(String id) {
        DodopalResponse<PayAwayDTO> response = new DodopalResponse<PayAwayDTO>();

        PayAwayDTO payAwayDTO = new PayAwayDTO();
        PayWayExternal payWayExternal = new PayWayExternal();
        try {
            if (StringUtils.isBlank(id)) {
                response.setCode(ResponseCode.PAY_CONFIG_ID_NULL);
                return response;
            }
            payWayExternal = payWayExternalService.findPayExternalById(id);

            if (payWayExternal != null) {
                PropertyUtils.copyProperties(payAwayDTO, payWayExternal);
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(payAwayDTO);
            }else{
                response.setCode(ResponseCode.PAY_WAY_NOT_FIND);
                return response;
            }
          

        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
        }
        return response;
    }

}
