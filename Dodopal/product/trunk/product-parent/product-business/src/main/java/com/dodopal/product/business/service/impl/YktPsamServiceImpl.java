package com.dodopal.product.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.dao.YktPsamMapper;
import com.dodopal.product.business.model.YktPsam;
import com.dodopal.product.business.model.query.YktPsamQuery;
import com.dodopal.product.business.service.YktPsamService;

@Service
public class YktPsamServiceImpl implements YktPsamService {

    @Autowired
    YktPsamMapper yktPsamMapper;

    //查询（分页）
    @Transactional(readOnly = true)
    public DodopalDataPage<YktPsam> findYktPsamByPage(YktPsamQuery yktPsamQuery) {
        List<YktPsam> result = yktPsamMapper.findYktPsamByPage(yktPsamQuery);
        DodopalDataPage<YktPsam> pages = new DodopalDataPage<YktPsam>(yktPsamQuery.getPage(), result);
        return pages;
    }

    @Transactional
    public DodopalResponse<String> batchDeleteYktPsamByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            if (CollectionUtils.isNotEmpty(ids)) {
                for (String string : ids) {
                    YktPsam yktPsam = yktPsamMapper.findPsamById(string);
                    yktPsamMapper.deleteSamSigninOfftb(yktPsam);
                }
            }

            yktPsamMapper.batchDeleteYktPsamByIds(ids);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    @Transactional
    public DodopalResponse<String> batchActivateMerByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            yktPsamMapper.batchActivateMerByIds(ids);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    @Transactional
    public DodopalResponse<String> batchNeedDownLoadPsamByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            yktPsamMapper.batchNeedDownLoadPsamByIds(ids);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    @Transactional
    public DodopalResponse<String> addYktPsam(YktPsam yktPsam) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            YktPsam yktPsamA = yktPsamMapper.findYktPsamByPsam(yktPsam.getSamNo());
            if (yktPsamA != null) {
                response.setCode(ResponseCode.PRODUCT_PSAM_ERROR);
                return response;
            }
            yktPsamMapper.addYktPsam(yktPsam);//新增ykt_psam表记录
            yktPsamMapper.addSamSigninOfftb(yktPsam); //增加签到控制表记录
            /// yktPsamMapper.updatePosByCode(yktPsam);//根据pos编号更新pos信息（添加绑定商户和修改绑定状态）
            ///yktPsamMapper.addPosMertb(yktPsam); //增加pos商户绑定中间表记录
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;

    }

    @Transactional
    public DodopalResponse<String> updateYktPsam(YktPsam yktPsam) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            yktPsamMapper.updateYktPsam(yktPsam);
            yktPsamMapper.updateSamSigninOfftb(yktPsam);
            //            YktPsam yktPsamA = yktPsamMapper.findPsamById(yktPsam.getId());
            //            
            //            if(yktPsamA.getSamNo().equals(yktPsam.getSamNo())){   //判断修改后的psam卡号表里是否已经存在
            //                //编辑psam表
            //                yktPsamMapper.updateYktPsam(yktPsam);
            //            }else{
            //                YktPsam yktPsamB = yktPsamMapper.findYktPsamByPsam(yktPsam.getSamNo());
            //                if (yktPsamB != null) {
            //                    response.setCode(ResponseCode.PRODUCT_PSAM_ERROR);
            //                    return response;
            //                }else{
            //                    //编辑psam表
            //                    yktPsamMapper.updateYktPsam(yktPsam); 
            //                }
            //            }

            //标记签到控制表
            // yktPsamMapper.updateSamSigninOfftb(yktPsam);
            //yktPsamMapper.updatePos(yktPsam);
            //删除pos商户中间表，重建pos商户的绑定关系。
            // yktPsamMapper.deletePosMerTb(yktPsam);
            // yktPsamMapper.addPosMertb(yktPsam);

            //            if (yktPsamA.getPosId().equals(yktPsam.getPosId())) {
            //                //修改pos记录
            //                yktPsamMapper.updatePosByCode(yktPsam);//根据pos编号更新pos信息
            //            } else {
            //                //修改pos记录
            //                yktPsamMapper.updatePosByCode(yktPsam);//根据pos编号更新pos信息
            //
            //                yktPsam.setBind(BindEnum.DISABLE.getCode());
            //                yktPsam.setMerCode("");
            //                yktPsam.setMerName("");
            //                yktPsam.setCityCode("");
            //                yktPsamMapper.updatePosByid(yktPsam);//根据pos表id重置pos记录
            //            }

            response.setCode(ResponseCode.SUCCESS);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;

    }

    @Transactional(readOnly = true)
    public DodopalResponse<YktPsam> findPsamById(String id) {
        DodopalResponse<YktPsam> response = new DodopalResponse<YktPsam>();
        YktPsam yktPsam = yktPsamMapper.findPsamById(id);
        response.setCode(ResponseCode.SUCCESS);
        response.setResponseEntity(yktPsam);
        return response;
    }

}
