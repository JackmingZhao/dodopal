package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.NamedEntity;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.dao.PosMapper;
import com.dodopal.oss.business.dao.PosTypeMapper;
import com.dodopal.oss.business.model.Pos;
import com.dodopal.oss.business.model.PosType;
import com.dodopal.oss.business.model.dto.PosTypeQuery;
import com.dodopal.oss.business.service.PosTypeService;

@Service
public class PosTypeServiceImpl implements PosTypeService {

    @Autowired
    private PosTypeMapper typeMapper;
    
    @Autowired
    private PosMapper posMapper;

    @Transactional
    @Override
    public String saveOrUpdatePosType(PosType type) {
        validatePosType(type);
        if (DDPStringUtil.isNotPopulated(type.getId())) {
            typeMapper.insertPosType(type);
        } else {
            typeMapper.updatePosType(type);
        }
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public DodopalDataPage<PosType> findPosTypeByPage(PosTypeQuery type) {
        List<PosType> result = typeMapper.findPosTypesByPage(type);
        DodopalDataPage<PosType> pages = new DodopalDataPage<PosType>(type.getPage(), result);
        return pages;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<PosType> findPosTypes(PosType type) {
        return typeMapper.findPosTypes(type);
    }

    @Transactional
    @Override
    public void deletePosType(String[] codes) {
        if (codes != null && codes.length > 0) {
            List<Pos> pos = posMapper.findPosByPosTypeCode(codes);
            Set<String> posTypeCode = new HashSet<String>();
            if (CollectionUtils.isNotEmpty(pos)) {
                for (Pos p : pos) {
                    posTypeCode.add(p.getPosTypeCode());
                }
                String inValidCodes = "";
                for (String code : posTypeCode) {
                    if ("".equals(inValidCodes)) {
                        inValidCodes = code;
                    } else {
                        inValidCodes = inValidCodes + "," + code;
                    }
                }
                throw new DDPException("pos.type.delete:\n", "POS型号【" + inValidCodes + "】已关联POS机具,不能删除");
            } else {
                typeMapper.deletePosType(codes);
            }
        } else {
            throw new DDPException("pos.type.delete.empty:\n", "POS 型号ID不能为空");
        }
    }

    private void validatePosType(PosType type) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(type.getCode(), 64)) {
            msg.add("型号编号不能为空或长度不能超过64个字符");
        }

        if (!DDPStringUtil.existingWithLength(type.getName(), 64)) {
            msg.add("型号名称不能为空或长度不能超过64个字符");
        }


        if (!DDPStringUtil.lessThan(type.getComments(), 255)) {
            msg.add("备注信息长度不能超过255个字符");
        }

        
        if(StringUtils.isEmpty(type.getId())) { //新建型号-1.  型号编号不能与现有的POS型号编号重复。2.  型号名称不能与现有的POS型号名称重复。
            int count = typeMapper.countPosType(type.getCode());
            if(count > 0) {
                msg.add("型号编号不能与现有的POS型号编号重复");
            }
            count = typeMapper.countPosTypeByName(type.getName());
            if(count > 0) {
                msg.add("型号名称不能与现有的POS型号名称重复");
            }
        } else { // 修改型号 -1.    修改后的POS型号编号不能与其它POS型号编号重复。 2.   修改后的POS型号名称不能与其它POS型号名称重复。
            PosType oldType = typeMapper.findPosTypeById(type.getId());
            if (oldType != null) {
                if (!oldType.getName().equals(type.getName())) {
                    int count = typeMapper.countPosTypeByName(type.getName());
                    if(count > 0) {
                        msg.add("修改后的POS型号名称不能与其它POS型号名称重复");
                    }
                }

                if (!oldType.getCode().equals(type.getCode())) {
                    int count = typeMapper.countPosType(type.getCode());
                    if(count > 0) {
                        msg.add("修改后的POS型号编号不能与其它POS型号编号重复");
                    }
                }
            }
        }

        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    @Override
    @Transactional
    public int batchActivatePosType(String updateUser, List<String> ids) {
        return typeMapper.batchActivateType(updateUser, ActivateEnum.ENABLE.getCode(), ids);
    }

    @Override
    @Transactional
    public int batchInactivatePosType(String updateUser, List<String> ids) {
        return typeMapper.batchActivateType(updateUser, ActivateEnum.DISABLE.getCode(), ids);
    }

    @Transactional(readOnly=true)
    @Override
    public PosType findPosTypeById(String typeId) {
        if (StringUtils.isNotEmpty(typeId)) {
            return typeMapper.findPosTypeById(typeId);
        } else {
            throw new DDPException("pos.type.findPosTypeById.Idempty:\n", "POS 型号ID为空");
        }
    }

    @Transactional(readOnly=true)
    @Override
    public List<NamedEntity> loadPosType() {
        List<NamedEntity> result = new ArrayList<NamedEntity>();
        List<PosType> types = typeMapper.loadPosType();
        if (CollectionUtils.isNotEmpty(types)) {
            for (PosType type : types) {
                result.add(new NamedEntity(type.getCode(), type.getName()));
            }
        }
        return result;
    }

}
