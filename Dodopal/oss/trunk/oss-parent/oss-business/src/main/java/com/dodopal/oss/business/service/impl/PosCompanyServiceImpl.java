package com.dodopal.oss.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.dodopal.common.validator.DDPValidationHandler;
import com.dodopal.common.validator.DdpValidationBoxEnum;
import com.dodopal.oss.business.dao.PosCompanyMapper;
import com.dodopal.oss.business.dao.PosMapper;
import com.dodopal.oss.business.model.PosCompany;
import com.dodopal.oss.business.model.dto.PosCompanyQuery;
import com.dodopal.oss.business.service.PosCompanyService;

@Service
public class PosCompanyServiceImpl implements PosCompanyService {

    @Autowired
    private PosCompanyMapper companyMapper;

    @Autowired
    private PosMapper posMapper;
    
    @Transactional
    @Override
    public String saveOrUpdatePosCompany(PosCompany company) {
        validatePosCompany(company);
        if (DDPStringUtil.isNotPopulated(company.getId())) {
            company.setUpdateUser(null);
            companyMapper.insertPosCompany(company);
        } else {
            companyMapper.updatePosCompany(company);
        }
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public DodopalDataPage<PosCompany> findPosCompanysByPage(PosCompanyQuery company) {
        List<PosCompany> companies = companyMapper.findPosCompanysByPage(company);
        DodopalDataPage<PosCompany> pages = new DodopalDataPage<PosCompany>(company.getPage(), companies);
        return pages;
    }

    @Transactional
    @Override
    public void deletePosCompany(String[] companys) {
        // 在执行删除操作之前，需要检查是否POS机具关联该POS厂商，如果有，则提示用户不能删除（所有选中的都不能删除）。
        String companyIds[] = validateDeletePosCompany(companys);

        if (companyIds != null && companyIds.length > 0) {
            companyMapper.deletePosCompany(companyIds);
        } else {
            throw new DDPException("pos.company.delete.empty:\n", "POS 厂商ID不能为空");
        }
    }

    private String[] validateDeletePosCompany(String[] companys){
        List<String> msg = new ArrayList<String>();
        String companyIds[] = new String[companys.length];
        String companyCodes[] = new String[companys.length];
        StringBuffer companyNameBF = new StringBuffer();
        HashMap<String, String> companyMap = new HashMap<String, String>();
        for (int i = 0; i < companys.length; i++) {
            companyIds[i] = companys[i].split(",")[0];
            companyCodes[i] = companys[i].split(",")[1];
            if (!companyMap.containsKey(companyCodes[i])) {
                companyMap.put(companyCodes[i], companys[i].split(",")[2]);
            }
        }
        for (String companyCode:companyCodes) {
            int count = posMapper.countPosByCompanyCode(companyCode);
            if (count > 0) {
                companyNameBF.append(", ");
                companyNameBF.append(companyMap.get(companyCode));
            }
        }
        if (companyNameBF.length()>0) {
            String companyName = companyNameBF.toString().substring(1);
            msg.add(companyName+" 下已有POS关联,无法删除POS厂商");
            companyIds = null;
        }
        
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
        return companyIds;
    }
    
    private void validatePosCompany(PosCompany company) {
        List<String> msg = new ArrayList<String>();
        // 1.必填 2.全局唯一 3.数字、英文、下划线  4. 1<=长度<=32位字符
        if(!DDPValidationHandler.validate(company.getCode(), true, DdpValidationBoxEnum.EN_NO_US)) {
            msg.add("厂商编码必须输入且全局唯一,只能是1到32位字符： 数字、英文、下划线");
        }
        if(!DDPValidationHandler.validate(company.getName(), true, DdpValidationBoxEnum.CN_EN_NO)) {
            msg.add("厂商名称必须输入且全局唯一,最大20位字符: 中文、数字、英文");
        }

        if (!DDPStringUtil.lessThan(company.getCharger(), 20)) {
            msg.add("厂商负责人长度不能超过20个字符");
        }

        if (!DDPStringUtil.lessThan(company.getPhone(), 20)) {
            msg.add("联系电话长度不能超过20个字符");
        }
        if (!DDPStringUtil.lessThan(company.getMail(), 200)) {
            msg.add("邮箱长度不能超过200个字符");
        }

        if (!DDPStringUtil.lessThan(company.getAddress(), 255)) {
            msg.add("地址长度不能超过255个字符");
        }

        if (!DDPStringUtil.lessThan(company.getZipCode(), 20)) {
            msg.add("邮编长度不能超过64个字符");
        }

        if (!DDPStringUtil.lessThan(company.getComments(), 255)) {
            msg.add("备注信息长度不能超过255个字符");
        }

        
        if(StringUtils.isEmpty(company.getId())) { //新建厂商-1.  厂商编号不能与现有的POS厂商编号重复。2.  厂商名称不能与现有的POS厂商名称重复。
            PosCompany query = new PosCompany();
            query.setCode(company.getCode());
            List<PosCompany> companys = companyMapper.findPosCompanys(query);
            if(CollectionUtils.isNotEmpty(companys)) {
                msg.add("厂商编号不能与现有的POS厂商编号重复");
            }
            query = new PosCompany();
            query.setName(company.getName());
            companys = companyMapper.findPosCompanys(query);
            if(CollectionUtils.isNotEmpty(companys)) {
                msg.add("厂商名称不能与现有的POS厂商名称重复");
            }
        } else { // 修改厂商 -1.    修改后的POS厂商编号不能与其它POS厂商编号重复。 2.   修改后的POS厂商名称不能与其它POS厂商名称重复。
            PosCompany oldCompany = companyMapper.findPosCompanyById(company.getId());
            if (oldCompany != null) {
                if (!oldCompany.getName().equals(company.getName())) {
                    PosCompany query = new PosCompany();
                    query.setName(company.getName());
                    List<PosCompany> companys = companyMapper.findPosCompanys(query);
                    if (CollectionUtils.isNotEmpty(companys)) {
                        msg.add("修改后的POS厂商名称不能与其它POS厂商名称重复");
                    }
                }

                if (!oldCompany.getCode().equals(company.getCode())) {
                    PosCompany query = new PosCompany();
                    query.setCode(company.getCode());
                    List<PosCompany> companys = companyMapper.findPosCompanys(query);
                    if (CollectionUtils.isNotEmpty(companys)) {
                        msg.add("修改后的POS厂商编号不能与其它POS厂商编号重复");
                    }
                }
            }
        }
        int count = companyMapper.countPosCompany(company.getCode());
        if ((StringUtils.isEmpty(company.getId()) && count >= 1) || (StringUtils.isNotEmpty(company.getId()) && count > 1)) {
            
        }

        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:\n", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    @Override
    @Transactional
    public int batchActivatePosCompany(String updateUser, List<String> ids) {
        return companyMapper.batchActivateCompany(updateUser, ActivateEnum.ENABLE.getCode(), ids);
    }

    @Override
    @Transactional
    public int batchInactivatePosCompany(String updateUser, List<String> ids) {
        return companyMapper.batchActivateCompany(updateUser, ActivateEnum.DISABLE.getCode(), ids);
    }

    @Transactional(readOnly=true)
    @Override
    public PosCompany findPosCompanyById(String companyId) {
        if (StringUtils.isNotEmpty(companyId)) {
            return companyMapper.findPosCompanyById(companyId);
        } else {
            throw new DDPException("pos.company.findPosCompanyById.Idempty:\n", "POS 厂商ID为空");
        }
    }

    @Transactional(readOnly=true)
    @Override
    public List<NamedEntity> loadPosCompany() {
        List<NamedEntity> result = new ArrayList<NamedEntity>();
        List<PosCompany> companies = companyMapper.loadPosCompany();
        if (CollectionUtils.isNotEmpty(companies)) {
            for (PosCompany company : companies) {
                result.add(new NamedEntity(company.getCode(), company.getName()));
            }
        }
        return result;
    }

}
