package com.dodopal.users.business.facadeImpl;

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

import com.dodopal.api.users.dto.MerGroupDepartmentDTO;
import com.dodopal.api.users.dto.query.MerGroupDepartmentQueryDTO;
import com.dodopal.api.users.facade.MerGroupDepartmentFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.model.MerGroupDepartment;
import com.dodopal.users.business.model.MerGroupUserFind;
import com.dodopal.users.business.model.query.MerGroupDepartmentQuery;
import com.dodopal.users.business.service.MerGroupDepartmentService;
import com.dodopal.users.business.service.MerGroupUserService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 下午3:17:24
 */
@Service("merGroupDepartmentFacade")
public class MerGroupDepartmentFacadeImpl implements MerGroupDepartmentFacade {
    private final static Logger log = LoggerFactory.getLogger(MerGroupDepartmentFacadeImpl.class);
    @Autowired
    private MerGroupDepartmentService merGroupDepartmentService;
    @Autowired
    private MerGroupUserService groupUserService;
    @Override
    public DodopalResponse<List<MerGroupDepartmentDTO>> findMerGroupDepartmentDTOList(MerGroupDepartmentDTO departmentDTO, String fromFlag) {
        DodopalResponse<List<MerGroupDepartmentDTO>> dodopalResponse = new DodopalResponse<List<MerGroupDepartmentDTO>>();
        MerGroupDepartment department = new MerGroupDepartment();
        List<MerGroupDepartmentDTO> dtoList = new ArrayList<MerGroupDepartmentDTO>();
        List<MerGroupDepartment> deptList = null;
        try {
            PropertyUtils.copyProperties(department, departmentDTO);
            //验空
            if(StringUtils.isNotBlank(fromFlag)){
                //判断请求方向
                if(UsersConstants.FIND_OSS.equals(fromFlag)){
                    deptList = merGroupDepartmentService.findMerGroupDepartmentList(department);
                }else if(UsersConstants.FIND_WEB.equals(fromFlag)){
                //验证从门户发送查询请求中的商户号   门户只能查找该商户号下的数据
                    if(StringUtils.isNotBlank(departmentDTO.getMerCode())){
                        deptList = merGroupDepartmentService.findMerGroupDepartmentList(department);
                    }else{
                        dodopalResponse.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                    }
                }else{
                    dodopalResponse.setCode(ResponseCode.USERS_FIND_FLG_ERR);
                }
                //判断获取列表的信息是否为空
                if(null!=deptList){
                        for(MerGroupDepartment tempDept:deptList){
                            departmentDTO = new MerGroupDepartmentDTO();
                            PropertyUtils.copyProperties(departmentDTO,tempDept);
                            dtoList.add(departmentDTO);
                        }
                        dodopalResponse.setResponseEntity(dtoList);
                }
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }else{
                dodopalResponse.setCode(ResponseCode.USERS_MER_USER_INFO_NULL);
            }
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("MerGroupDepartmentFacadeImpl call error", e);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<MerGroupDepartmentDTO> saveMerGroupDepartmentDTO(MerGroupDepartmentDTO departmentDTO) {
        DodopalResponse<MerGroupDepartmentDTO> dodopalResponse = new DodopalResponse<MerGroupDepartmentDTO>();
        MerGroupDepartment department = new MerGroupDepartment();
        try {
            String checkResult = checkMerGroupDept(departmentDTO);
            if(StringUtils.isNotBlank(checkResult)){
                //验证部门名是否为空
                dodopalResponse.setCode(checkResult);
            } else {
                PropertyUtils.copyProperties(department, departmentDTO);
                List<MerGroupDepartment>deptList = merGroupDepartmentService.findMerGpDepByMerCodeAndDeptName(department);
                if(CollectionUtils.isEmpty(deptList)){
                    merGroupDepartmentService.saveMerGroupDepartment(department);
                    PropertyUtils.copyProperties(departmentDTO, department);
                    dodopalResponse.setResponseEntity(departmentDTO);
                    dodopalResponse.setCode(ResponseCode.SUCCESS);
                }else{
                    dodopalResponse.setResponseEntity(departmentDTO);
                    dodopalResponse.setMessage("重复的部门名称");
                    dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_NAME_REPEAT);
                }
           }
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("MerGroupDepartmentFacadeImpl call error", e);
        }
        return dodopalResponse;
    }
    public DodopalResponse<Boolean> checkMerGroupDepartmentDTO(String mercode,String deptName,String id){
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerGroupDepartment department = new MerGroupDepartment();
        department.setMerCode(mercode);
        department.setDepName(deptName);
        department.setId(id);
        if(StringUtils.isBlank(deptName)){
            //验证部门名是否为空
            dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_NAME_NULL);
        }else if(StringUtils.isBlank(mercode)){
            dodopalResponse.setCode(ResponseCode.USERS_MER_CODE_NULL);
        } else {
            List<MerGroupDepartment>deptList = merGroupDepartmentService.findMerGpDepByMerCodeAndDeptName(department);
            if(CollectionUtils.isEmpty(deptList)){
                dodopalResponse.setCode(ResponseCode.SUCCESS);
                dodopalResponse.setResponseEntity(false);
            }else{
                dodopalResponse.setResponseEntity(true);
                dodopalResponse.setMessage("重复的部门名称");
                dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_NAME_REPEAT);
            }
        }
        return dodopalResponse;
    }
    /** 
     * @author dingkuiyuan@dodopal.com
     * @Title: checkMerGroupDept 
     * @Description: 校验字段有效性
     * @param departmentDTO
     * @return    设定文件 
     * String    返回类型 
     * @throws 
     */
    private String checkMerGroupDept(MerGroupDepartmentDTO departmentDTO){
        if(StringUtils.isBlank(departmentDTO.getDepName())){
            //验证部门名是否为空
            return ResponseCode.MER_GROUP_DEP_NAME_NULL;
        }else if(StringUtils.isBlank(departmentDTO.getDepName())){
            return ResponseCode.USERS_MER_CODE_NULL;
        }
        return null;
    }
    
    @Override
    public DodopalResponse<Boolean> deleteMerGroupDepartmentDTO(List<String> id) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerGroupUserFind findBean = new MerGroupUserFind();
        List<String> resultList = new ArrayList<String>();
        for(String tempId:id){
            //判断该部门下是否有人员
           findBean.setDepId(tempId);
           int rows = groupUserService.getMerGpUserCount(findBean);
           if(rows>0){
               resultList.add(tempId);
           }
        }
        //TODO 将所有有人员关联的部门返回给接口调用
        if(null!=resultList&&resultList.size()>0){
            dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_HAVE_PERSON);
            dodopalResponse.setResponseEntity(false);
        }else{
            int rows = merGroupDepartmentService.deleteMerGroupDepartment(id);
            if(rows>0){
                dodopalResponse.setCode(ResponseCode.SUCCESS);
                dodopalResponse.setResponseEntity(true);
            }else{
                dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_HAVE_PERSON);
                dodopalResponse.setResponseEntity(false);
            }
        }
        
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<Boolean> updateMerGroupDepartmentDTO(MerGroupDepartmentDTO departmentDTO) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        MerGroupDepartment department = new MerGroupDepartment();
        try {
            PropertyUtils.copyProperties(department, departmentDTO);
            if(StringUtils.isBlank(department.getId())){
                dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_ID_NULL);
                dodopalResponse.setResponseEntity(false);
            }else{
                int rows = merGroupDepartmentService.updateMerGroupDepartment(department);
                if(rows>0){
                    dodopalResponse.setResponseEntity(true);
                    dodopalResponse.setCode(ResponseCode.SUCCESS);
                }else{
                    dodopalResponse.setResponseEntity(false);
                    dodopalResponse.setCode(ResponseCode.SUCCESS);
                }
            }
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("MerGroupDepartmentFacadeImpl call error", e);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<MerGroupDepartmentDTO> findMerGroupDepartmentDTOById(String id) {
        DodopalResponse<MerGroupDepartmentDTO> dodopalResponse = new DodopalResponse<MerGroupDepartmentDTO>();
        MerGroupDepartmentDTO departmentDTO = new MerGroupDepartmentDTO();
        if(StringUtils.isBlank(id)){
            dodopalResponse.setCode(ResponseCode.MER_GROUP_DEP_ID_NULL);
        }else{
            MerGroupDepartment department = merGroupDepartmentService.findMerGpDepById(id);
            try {
                PropertyUtils.copyProperties(departmentDTO, department);
                dodopalResponse.setResponseEntity(departmentDTO);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
                log.debug("MerGroupDepartmentFacadeImpl call error", e);
            }
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>> findMerGroupDepartmentDTOListByPage(MerGroupDepartmentQueryDTO departmentQQueryDTO, String fromFlag) {
        DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>> dodopalResponse = new DodopalResponse<DodopalDataPage<MerGroupDepartmentDTO>>();
        MerGroupDepartmentQuery department = new MerGroupDepartmentQuery();
        List<MerGroupDepartmentDTO> dtoList = new ArrayList<MerGroupDepartmentDTO>();
        DodopalDataPage<MerGroupDepartment> deptPageList = null;
        try {
            PropertyUtils.copyProperties(department, departmentQQueryDTO);
            //验空
            if(StringUtils.isNotBlank(fromFlag)){
                //判断请求方向
                if(UsersConstants.FIND_OSS.equals(fromFlag)){
                    deptPageList = merGroupDepartmentService.findMerGroupDepartmentListByPage(department);
                }else if(UsersConstants.FIND_WEB.equals(fromFlag)){
                //验证从门户发送查询请求中的商户号  门户只能查找该商户号下的数据
                    if(StringUtils.isNotBlank(departmentQQueryDTO.getMerCode())){
                        deptPageList = merGroupDepartmentService.findMerGroupDepartmentListByPage(department);
                    }else{
                        dodopalResponse.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
                    }
                }else{
                    dodopalResponse.setCode(ResponseCode.USERS_FIND_FLG_ERR);
                }
                //判断获取列表的信息是否为空
                if(null!=deptPageList){
                        for(MerGroupDepartment tempDept:deptPageList.getRecords()){
                            MerGroupDepartmentDTO departmentDTO = new MerGroupDepartmentDTO();
                            PropertyUtils.copyProperties(departmentDTO,tempDept);
                            dtoList.add(departmentDTO);
                        }
                 }
              
                //page页取值转换工具
                PageParameter page =  DodopalDataPageUtil.convertPageInfo(deptPageList);
                DodopalDataPage<MerGroupDepartmentDTO> pages = new DodopalDataPage<MerGroupDepartmentDTO>(page, dtoList);
                
                dodopalResponse.setResponseEntity(pages);
                dodopalResponse.setCode(ResponseCode.SUCCESS);
            }else{
                dodopalResponse.setCode(ResponseCode.USERS_MER_USER_INFO_NULL);
            }
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("MerGroupDepartmentFacadeImpl call error", e);
        }
        return dodopalResponse;
    }

    @Override
    public DodopalResponse<Boolean> startOrStopMerGroupDepartmentDTO(ActivateEnum act, List<String> idList) {
        DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();
        merGroupDepartmentService.startOrStopMerGroupDepartment(act.getCode(), idList);
        
        return null;
    }


}
