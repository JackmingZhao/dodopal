package com.dodopal.product.business.facadeImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.YktPsamDTO;
import com.dodopal.api.product.dto.query.YktPsamQueryDTO;
import com.dodopal.api.product.facade.YktPsamFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.PosStatusEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.product.business.model.YktPsam;
import com.dodopal.product.business.model.query.YktPsamQuery;
import com.dodopal.product.business.service.YktPsamService;

@Service("yktPsamFacade")
public class YktPsamFacadeImpl implements YktPsamFacade {

    @Autowired
    private YktPsamService yktPsamService;

    /**
     * 查询 PSAM卡 分页
     */
    public DodopalResponse<DodopalDataPage<YktPsamDTO>> findYktPsamByPage(YktPsamQueryDTO yktPsamQueryDTO) {
        DodopalResponse<DodopalDataPage<YktPsamDTO>> response = new DodopalResponse<DodopalDataPage<YktPsamDTO>>();
        try {
            YktPsamQuery yktPsamQuery = new YktPsamQuery();
            PropertyUtils.copyProperties(yktPsamQuery, yktPsamQueryDTO);

            List<YktPsamDTO> yktPsamDTOList = new ArrayList<YktPsamDTO>();
            DodopalDataPage<YktPsam> rtResponse = yktPsamService.findYktPsamByPage(yktPsamQuery);
            if (rtResponse != null && rtResponse.getRecords() != null && rtResponse.getRecords().size() > 0) {
                for (YktPsam yktPsam : rtResponse.getRecords()) {
                    YktPsamDTO yktPsamDTO = new YktPsamDTO();
                    PropertyUtils.copyProperties(yktPsamDTO, yktPsam);
                    yktPsamDTOList.add(yktPsamDTO);
                }
            }

            //获取分页信息
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse);
            DodopalDataPage<YktPsamDTO> pages = new DodopalDataPage<YktPsamDTO>(page, yktPsamDTOList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //批量删除
    @Override
    public DodopalResponse<String> batchDeleteYktPsamByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response =  yktPsamService.batchDeleteYktPsamByIds(ids);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //批量启用商户
    @Override
    public DodopalResponse<String> batchActivateMerByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = yktPsamService.batchActivateMerByIds(ids);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //批量修改重新下载参数
    @Override
    public DodopalResponse<String> batchNeedDownLoadPsamByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = yktPsamService.batchNeedDownLoadPsamByIds(ids);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //新增
    @Override
    public DodopalResponse<String> addYktPsam(YktPsamDTO yktPsamDTO) {
        
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            YktPsam yktPsam=new YktPsam();
            PropertyUtils.copyProperties(yktPsam, yktPsamDTO);
            //yktPsam.setPosStatus(PosStatusEnum.ACTIVATE.getCode());//启用状态
            //yktPsam.setBind(BindEnum.ENABLE.getCode());//绑定状态
            response = yktPsamService.addYktPsam(yktPsam);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //修改
    @Override
    public DodopalResponse<String> updateYktPsam(YktPsamDTO yktPsamDTO) {
        
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            YktPsam yktPsam=new YktPsam();
            PropertyUtils.copyProperties(yktPsam, yktPsamDTO);
           // yktPsam.setPosStatus(PosStatusEnum.ACTIVATE.getCode());//启用状态
           // yktPsam.setBind(BindEnum.ENABLE.getCode());//绑定状态
            response = yktPsamService.updateYktPsam(yktPsam);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<YktPsamDTO> findPsamById(String id) {
        DodopalResponse<YktPsamDTO> response = new DodopalResponse<YktPsamDTO>();
        try {
            YktPsamDTO yktPsamDTO = new YktPsamDTO();
            DodopalResponse<YktPsam> rtResponse = yktPsamService.findPsamById(id);
            if(ResponseCode.SUCCESS.equals(rtResponse.getCode())){
                PropertyUtils.copyProperties(yktPsamDTO, rtResponse.getResponseEntity());
                response.setResponseEntity(yktPsamDTO);
            }
            response.setCode(rtResponse.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}
