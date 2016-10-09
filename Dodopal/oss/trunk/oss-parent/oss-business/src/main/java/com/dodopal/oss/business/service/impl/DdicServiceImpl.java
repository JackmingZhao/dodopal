package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.oss.business.dao.DdicCategoryMapper;
import com.dodopal.oss.business.dao.DdicMapper;
import com.dodopal.oss.business.dao.RoleMapper;
import com.dodopal.oss.business.model.Ddic;
import com.dodopal.oss.business.model.DdicCategory;
import com.dodopal.oss.business.model.dto.DdicQuery;
import com.dodopal.oss.business.service.DdicService;

@Service
public class DdicServiceImpl implements DdicService {

    @Autowired
    private DdicMapper ddicMapper;

    @Autowired
    private DdicCategoryMapper ddicCategoryMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Transactional
    @Override
    public String saveOrUpdateDdic(Ddic ddic) {
        if (StringUtils.isNotBlank(ddic.getId())) {
            validateDdic(ddic, true);
        } else {
            validateDdic(ddic, false);
        }

        populateDdicCategory(ddic);
        if (DDPStringUtil.isNotPopulated(ddic.getId())) {
            ddic.setCreateDate(new Date());
            ddic.setUpdateDate(new Date());
            ddicMapper.insertDdic(ddic);
        } else {
            ddic.setUpdateDate(new Date());
            ddicMapper.updateDdic(ddic);
        }
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Ddic> findDdics(Ddic ddic) {
        return ddicMapper.findDdics(ddic);
    }

    @Override
    @Transactional(readOnly = true)
    public DodopalDataPage<Ddic> findDdicsByPage(DdicQuery ddicQuery) {
        List<Ddic> result = ddicMapper.findDdicsByPage(ddicQuery);
        DodopalDataPage<Ddic> pages = new DodopalDataPage<Ddic>(ddicQuery.getPage(), result);
        return pages;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Ddic> findDdicByCategoryCode(String categoryCode) {
        return ddicMapper.findDdicByCategoryCode(categoryCode);
    }

    @Transactional
    @Override
    public void deleteDdic(String ddicId) {
        if (StringUtils.isNotEmpty(ddicId)) {
            ddicMapper.deleteDdic(ddicId);
        }
    }

    @Transactional
    @Override
    public void batchDelDdic(String updateUser, List<String> ids) {
        ddicMapper.batchDelDdic(updateUser, ids);
    }

    private void validateDdic(Ddic ddic, boolean isUpdate) {
        List<String> msg = new ArrayList<String>();
        if (!DDPStringUtil.existingWithLength(ddic.getName(), 64)) {
            msg.add("字典名称不能为空或长度不能超过64个字符");
        }

        if (!DDPStringUtil.existingWithLength(ddic.getCode(), 64)) {
            msg.add("字典编码不能为空或长度不能超过64个字符");
        }

        if (!DDPStringUtil.lessThan(ddic.getDescription(), 255)) {
            msg.add("数据字典描述信息长度不能超过255个字符");
        }

        Ddic result = ddicMapper.findDdicByCategoryAndCode(ddic.getCode(), ddic.getCategoryCode());
        if (result != null) {
            if (isUpdate) {
                if (!ddic.getId().equals(result.getId())) {
                    msg.add("同一分类编码下,该字典编码已存在");
                }
            } else {
                if (result.getDelFlg().equals(DelFlgEnum.NORMAL.getCode())) {
                    msg.add("同一分类编码下,该字典编码已存在");
                } else {
                    ddic.setId(result.getId());
                }
            }
        }

        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    private void populateDdicCategory(Ddic ddic) {
        if (ddic != null && DDPStringUtil.isPopulated(ddic.getCategoryCode())) {
            DdicCategory category = ddicCategoryMapper.findDdicCategoryByCode(ddic.getCategoryCode());
            ddic.setCategoryName(category.getCategoryName());
        }
    }

    @Override
    @Transactional
    public int batchActivateDdic(String updateUser, List<String> ids) {
        return ddicMapper.batchActivateDdic(updateUser, ActivateEnum.ENABLE.getCode(), ids);
    }

    @Override
    @Transactional
    public int batchInactivateDdic(String updateUser, List<String> ids) {
        return ddicMapper.batchActivateDdic(updateUser, ActivateEnum.DISABLE.getCode(), ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Ddic findDdicById(String id) {
        return ddicMapper.findDdicById(id);
    }

}
