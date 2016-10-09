package com.dodopal.oss.business.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dodopal.api.users.dto.OperUserDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.PosOperTypeEnum;
import com.dodopal.common.enums.PosStatusEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.Utils;
import com.dodopal.common.validator.DDPValidationHandler;
import com.dodopal.common.validator.DdpValidationBoxEnum;
import com.dodopal.oss.business.bean.PosBean;
import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.constant.PosConstants;
import com.dodopal.oss.business.dao.PosCompanyMapper;
import com.dodopal.oss.business.dao.PosMapper;
import com.dodopal.oss.business.dao.PosTypeMapper;
import com.dodopal.oss.business.model.Pos;
import com.dodopal.oss.business.model.PosInfo;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.model.dto.PosOperationDTO;
import com.dodopal.oss.business.model.dto.PosQuery;
import com.dodopal.oss.business.model.dto.PosUpdateBatch;
import com.dodopal.oss.business.service.MerchantService;
import com.dodopal.oss.business.service.PosService;
import com.dodopal.oss.delegate.MerchantDelegate;
import com.dodopal.oss.delegate.PosDelegate;

@Service
public class PosServiceImpl implements PosService {

    private static final String POS_VALID = "posValid";

    private static final String POS_INVALID = "posInValid";

    @Autowired
    private PosMapper posMapper;

    @Autowired
    private PosCompanyMapper companyMapper;

    @Autowired
    private PosTypeMapper posTypeMapper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PosDelegate posDelegate;
    
    @Autowired
    private MerchantDelegate merchantDelegate;

    @Transactional
    @Override
    public String saveOrUpdatePos(Pos pos) {

        if (DDPStringUtil.isNotPopulated(pos.getId())) {
            pos.setBind("1");
            List<Pos> posList = generatePosList(pos);
            if (posList.size() == 1) {
                validatePos(posList.get(0));
                posMapper.insertPos(posList.get(0));
            } else {
                posList = validatePosInsertBatch(posList);
                if (posList != null && posList.size() > 0) {
                    List<String> keys = posMapper.selectMultipleKeys(posList.size());
                    for (int i = 0; i < posList.size(); i++) {
                        posList.get(i).setId(keys.get(i));
                    }
                    posMapper.insertPosBatch(posList);
                }
            }
        } else {
        	PosInfo oldPos = posMapper.findPosById(pos.getId());
        	//获取原pos数据，拿到城市code，不为空情况下判断，更新后的数据，城市是否改动
        	if (OSSConstants.POS_BIND.equals(oldPos.getBind())) {
	        	if(StringUtils.isNotBlank(oldPos.getCityCode())){
	        		if(!oldPos.getCityCode().equals(pos.getCityCode())){
	        			throw new DDPException("POS【" + pos.getCode() + "】已绑定商户无法修改城市");
	        		}
	        	}
			}
            validatePos(pos);
            pos.setUpdateDate(new Date());
            posMapper.updatePos(pos);
        }
        return CommonConstants.SUCCESS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pos> findPoss(Pos pos) {
        return posMapper.findPoss(pos);
    }

    @Transactional
    @Override
    public   DodopalResponse<String> deletePos(String[] posId,User user) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (posId != null && posId.length > 0) {
            List<Pos> tobeDeletedPos = posMapper.findPosByCode(posId);
            if (CollectionUtils.isNotEmpty(tobeDeletedPos)) {
                for (Pos pos : tobeDeletedPos) {
                    if (OSSConstants.POS_BIND.equals(pos.getBind())) {
                        throw new DDPException("pos.delete.bind:", "POS【" + pos.getCode() + "】已绑定到商户" + (pos.getMerchantName() != null ? pos.getMerchantName() : "") + ",不能删除");
                    }
                }
            }
            OperUserDTO operUser = new OperUserDTO();
            operUser.setOperName(user.getName());
            operUser.setId(user.getId());
            response  =  posDelegate.posOper(PosOperTypeEnum.OPER_DELETE, null, posId, operUser);
        } else {
            throw new DDPException("pos.pos.delete.empty:", "POS ID不能为空");
        }
        return response;
    }

    private List<String> validatePosInput(Pos pos) {
        List<String> msg = new ArrayList<String>();

     // 1.必填 2.全局唯一 3.数字、英文、下划线  4. 1<=长度<=32位字符
        if (!DDPStringUtil.lengthRange(pos.getCode(), 1, 32) || !DDPValidationHandler.validate(pos.getCode(), true, DdpValidationBoxEnum.EN_NO_US)) {
            msg.add("Pos编码不正确:只能是长度为1-32位字符的数字、英文、下划线");
        }

        // POS厂商 必选
        if (DDPStringUtil.isNotPopulated(pos.getPosCompanyCode())) {
            msg.add("Pos厂商为空");
        }

        // POS型号名称 必选
        if (DDPStringUtil.isNotPopulated(pos.getPosTypeCode())) {
            msg.add("Pos型号为空");
        }
        // 版本 1.必填 2.任意字段 3.长度<=64位字符
        if (DDPStringUtil.isNotPopulated(pos.getVersion())) {
            msg.add("行Pos版本为空");
        } else if (!DDPStringUtil.lengthRange(pos.getVersion(), 0, 64)) {
            msg.add("Pos版本不正确:最大长度只能为64位字符");
        }
        // POS批次号 1.必填 2.英文、数字 3.长度<=20字符
        if (DDPStringUtil.isNotPopulated(pos.getSerialNo())) {
            msg.add("Pos批次号为空");
        } else if (!DDPStringUtil.lengthRange(pos.getSerialNo(), 0, 20) || !DDPValidationHandler.validate(pos.getSerialNo(), true, DdpValidationBoxEnum.EN_NO)) {
            msg.add("Pos批次号不正确:只能是最大长度为20位的数字、英文");
        }
        // 采购成本 1.非必填  2.正数 3.保留到小数点后两位 4.长度<=10字符
        if (pos.getUnitCost() != null) {
            BigDecimal cost = pos.getUnitCost();
            if (cost.scale() > 2 || cost.compareTo(BigDecimal.ZERO) == -1 || cost.compareTo((new BigDecimal("9999999999"))) > 0) {
                msg.add("Pos采购成本不正确:只能是保留2位小数的正数且最大不能超过9999999999");
            }
        }

        // 限制笔数 1.非必填 2.非负数 3.长度<=10字符
        if (pos.getMaxTimes() < 0 || pos.getMaxTimes() > (new Long("9999999999")).longValue()) {
            msg.add("Pos限制笔数不正确:只能是非负数且最大不能超过9999999999");
        }
        
        // POS状态
        if (DDPStringUtil.isNotPopulatedCombobx(pos.getStatus())) {
            msg.add("POS状态不能为空");
        }
        return msg;
    }

    private void validatePos(Pos pos) {
        List<String> msg = validatePosInput(pos);
        if (StringUtils.isEmpty(pos.getId())) { 
            //新建单一POS信息.  POS编号不能与现有的POS编号重复。
            List<Pos> poss = posMapper.findPosByCode((new String[] {pos.getCode()}));
            if (CollectionUtils.isNotEmpty(poss)) {
                msg.add(";POS编号已存在");
            }
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }
    
    private List<Pos> validatePosInsertBatch(List<Pos> posList) {
        List<Pos> newPosList =  new ArrayList<Pos>();
        newPosList.addAll(posList);
        for (Pos pos : posList) {
            List<String> msg = validatePosInput(pos);
            if (StringUtils.isEmpty(pos.getId())) { 
                //号段创建POS信息.  新建号段中对已存在的POS编号，不作处理，过滤。登录未存在的POS编号。
                List<Pos> poss = posMapper.findPosByCode((new String[] {pos.getCode()}));
                if (CollectionUtils.isNotEmpty(poss)) {
                    newPosList.remove(pos);
                }
            }
            if (!msg.isEmpty()) {
                throw new DDPException("validate.error:", DDPStringUtil.concatenate(msg, ";<br/>"));
            }
        }
        
        return newPosList;
    }

    @Transactional(readOnly = true)
    @Override
    public PosInfo findPosById(String posId) {
        if (StringUtils.isNotEmpty(posId)) {
            return posMapper.findPosById(posId);
        } else {
            throw new DDPException("pos.pos.findPosById.Idempty:", "POS 厂商ID为空");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pos> findPosByCode(String[] posCode) {
        if (posCode != null && posCode.length > 0) {
            return posMapper.findPosByCode(posCode);
        } else {
            throw new DDPException("pos.pos.findPosByCode", "POS 厂商ID为空");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DodopalDataPage<PosInfo> findPosByPage(PosQuery posQuery) {
        List<PosInfo> poses = posMapper.findPosByPage(posQuery);
        DodopalDataPage<PosInfo> pages = new DodopalDataPage<PosInfo>(posQuery.getPage(), poses);
        return pages;
    }
    
    /**
     * POS操作 绑定/解绑/启用/禁用
     * @param posOper 操作类型
     * @param merCode 商户号
     * @param pos pos号集合
     * @param operUser 操作员信息
     * @return
     */
    @Override
    public DodopalResponse<String> posOper(PosOperationDTO operation, User user) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (user == null) {
            response.setCode(ResponseCode.LOGIN_USER_NULL);
        } else if (operation == null) {
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        } else if (DDPStringUtil.isNotPopulated(operation.getOperation())) {
            response.setCode(ResponseCode.OSS_POS_OPERATE_EMPTY);
        } else if (PosOperTypeEnum.OPER_BUNDLING.name().equals(operation.getOperation()) && (DDPStringUtil.isNotPopulated(operation.getMerCode()) || "sel".equalsIgnoreCase(operation.getMerCode()))) {
            response.setCode(ResponseCode.OSS_MER_NULL);
        } else if (operation.getPos() == null || operation.getPos().length <= 0) {
            response.setCode(ResponseCode.OSS_POS_OPERATE_EMPTY);
        } else {
            if (PosOperTypeEnum.OPER_BUNDLING.name().equals(operation.getOperation())) {
                //检验 绑定
            	String valResult = validatePosForBinding(operation);
            	
//                if(StringUtils.isNotBlank(valResult)){
//                	response.setCode(valResult);
//                	return response;
//                }
            }
            OperUserDTO operUser = new OperUserDTO();
            operUser.setOperName(user.getName());
            operUser.setId(user.getId());
            response = posDelegate.posOper(PosOperTypeEnum.valueOf(operation.getOperation()), operation.getMerCode(), operation.getPos(), operUser);
        }
        return response;
    }

    private String validatePosForBinding(PosOperationDTO operation) {
    	String[] posCodes = operation.getPos();
        List<Pos> posList = findPosByCode(posCodes);
        List<Area> allArea = new ArrayList<Area>();
        DodopalResponse<List<Area>> reAreaListRe = merchantDelegate.findMerCitys(operation.getMerCode());
        DodopalResponse<List<Area>> coAreaListRe = merchantDelegate.findMerCitysPayment(operation.getMerCode());
        List<Area> reAreaList = reAreaListRe.getResponseEntity();
        List<Area> coAreaList = coAreaListRe.getResponseEntity();
        if(CollectionUtils.isNotEmpty(reAreaList)){
        	allArea.addAll(reAreaList);
        }
        if(CollectionUtils.isNotEmpty(coAreaList)){
        	allArea.addAll(coAreaList);
        }
        //遍历商户的开通业务城市的合计
        if (CollectionUtils.isNotEmpty(posList)) {
            for (Pos pos : posList) {
            	 boolean flag = false;
                if (PosOperTypeEnum.OPER_BUNDLING.getCode().equals(pos.getBind())) {
                    throw new DDPException("POS【" + pos.getCode() + "】已绑定其他商户,不能再绑定");
                }
                if(StringUtils.isBlank(pos.getCityCode())){
                	throw new DDPException("POS【" + pos.getCode() + "】尚未设置所属城市");
                	//return ResponseCode.USERS_POS_NO_BIND_CITY;
                }
                for(Area tempArea:allArea){
                	if(tempArea.getCityCode().equals(pos.getCityCode())){
                		flag = true;
                		break;
                	}
                }
                if(!flag){
                	throw new DDPException("POS【" + pos.getCode() + "】所属城市与当前商户开通的业务城市不符");
                }
            }
        }
		return null;
    }

    
    @Override
    @Transactional
    public void modifyCity(PosUpdateBatch pos) {
        List<String> list = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> msgArr = new ArrayList<String>();
        String[] codeArray = pos.getCode().split(",");
        List<Pos> oldPoss = posMapper.findPosByCode(codeArray);
        list = Arrays.asList(codeArray);
        for(Pos posTemp:oldPoss){
        	//获取原pos数据，拿到城市code，不为空情况下判断，更新后的数据，城市是否改动
        	if (OSSConstants.POS_BIND.equals(posTemp.getBind())) {
            	if(StringUtils.isNotBlank(posTemp.getCityCode())){
            		msgArr.add("POS【" + posTemp.getCode() + "】已绑定商户无法修改城市");
            	}
    		}
        }
        
        if(CollectionUtils.isNotEmpty(msgArr)){
        	throw new DDPException("validate.error:", DDPStringUtil.concatenate(msgArr, ";<br/>"));
        }
        map.put("list", list);
        map.put("cityCode", pos.getCityCode());
        map.put("cityName", pos.getCityName());
        map.put("provinceCode", pos.getProvinceCode());
        map.put("provinceName", pos.getProvinceName());
        map.put("updateUser",  pos.getUpdateUser());
        posMapper.modifyCity(map);
    }

    @Override
    @Transactional
    public void modifyVersion(String codes, String version, String updateUser) {
        List<String> list = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        String[] codeArray = codes.split(",");
        for (String code : codeArray) {
            list.add(code);
        }
        map.put("list", list);

        map.put("version", version);
        map.put("updateUser",  updateUser);
        posMapper.modifyVersion(map);
    }

    @Override
    @Transactional
    public void modifyLimitation(String codes, long maxTimes, String updateUser) {
        List<String> list = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        String[] codeArray = codes.split(",");
        for (String code : codeArray) {
            list.add(code);
        }
        map.put("list", list);

        map.put("maxTimes", maxTimes);
        map.put("updateUser",  updateUser);
        posMapper.modifyLimitation(map);
    }

    private List<Pos> generatePosList(Pos pos) {
        List<String> msg = new ArrayList<String>();
        // POS编码、POS号段必须要选择一种输入； 要是输入POS编码，表明录入的是一条POS机具信息；      若选择的是POS号段，则保存后生成是多条POS机具信息，如：号段输入的是000000000010-000000000100，表示要生成号段10-100，90个POS机具。
        if (DDPStringUtil.isPopulated(pos.getCode())) {
            if (!DDPStringUtil.lessThan(pos.getCode(), 32)) {
                msg.add("POS编号长度不能大于32位");
            }
        } else if (DDPStringUtil.isPopulated(pos.getCodeStart()) && DDPStringUtil.isPopulated(pos.getCodeEnd())) {
            if (pos.getCodeStart().length() != 12 || pos.getCodeEnd().length() != 12 || !DDPStringUtil.isNumberic(pos.getCodeStart()) || !DDPStringUtil.isNumberic(pos.getCodeEnd())) {
                msg.add("POS号段必须为12位的数字");
            } else {
                BigDecimal codeStart = new BigDecimal(pos.getCodeStart());
                BigDecimal codeEnd = new BigDecimal(pos.getCodeEnd());
                if (codeStart.compareTo(codeEnd) == 1) {
                    msg.add("POS号段开始值不能大于结束值");
                }
            }
        } else {
            msg.add("POS编号和POS号段不能同时为空");
        }
        if (!msg.isEmpty()) {
            throw new DDPException("validate.error:", DDPStringUtil.concatenate(msg, ";<br/>"));
        }
        List<Pos> posList = new ArrayList<Pos>();
        if (DDPStringUtil.isPopulated(pos.getCode())) {
            posList.add(pos);
        } else {
            BigDecimal codeStart = new BigDecimal(pos.getCodeStart());
            BigDecimal codeEnd = new BigDecimal(pos.getCodeEnd());
            for (; codeStart.compareTo(codeEnd) <= 0; codeStart = codeStart.add(BigDecimal.ONE)) {
                Pos newPos = (Pos) Utils.deepClone(pos);
                String newPosCode = codeStart.toString();
                String prefix = "";
                for (int i = 0; i < 12 - newPosCode.length(); i++) {
                    prefix += "0";
                }
                newPos.setCode(prefix + newPosCode);
                posList.add(newPos);
            }
        }
        return posList;
    }

    /**
     * 1. Excel中所有的数据要么全部导入，要么一条都不要导入。注意这里所谓的“全部导入”，并不是说EXCEL中所有的行都会新增（insert）
     * 到数据库中，在批量导入时，应该以POS编码为主键，如果数据库中不存在，则新增一条POS机具；如果数据库中存在，则跳过该记录。  * 2.
     * 字段按照添加POS信息时字段，格式也同样；  * 3.
     * EXCEL中不能出现POS编码重复情况，如在EXCEL中遇POS编码重复（比如第11行和第21行的卡号重复
     * ），则不要写库，数据一条都不录入；错误提示语：第11行和第21行POS编码重复。  * 4.
     * POS厂商与POS型号要在系统中已存在并且为启用状态；  * 5. 所属城市，需更改为：省份+城市两列完成；  * 6.
     * 采购成本无需加“元”字，默认单位：元。
     */
    @Override
    public DodopalResponse<String> importPos(CommonsMultipartFile file, String userId) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if (file != null && !file.isEmpty()) {
            // 文件上传大小
            long fileSize = 10 * 1024 * 1024;
            if (file.getSize() > fileSize) {
                response.setCode(ResponseCode.OSS_BATCH_UPLOAD_OVER_MAXSIZE);
                return response;
            }
            String OriginalFilename = file.getOriginalFilename();
            String fileSuffix = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1).toLowerCase();
            if (!Arrays.asList(new String[] {"xls", "xlsx"}).contains(fileSuffix)) {
                response.setCode(ResponseCode.OSS_BATCH_UPLOAD_INCORRECT_FILE);
                return response;
            }

            // import the POS information
            List<PosBean> posList = parsePos(file.getBytes());

            if (posList != null && !posList.isEmpty()) {
                // 验证文件中的数据是否正确，如果不正确直接抛出异常返回.
                validatePosBean(posList);
                // 如果表格中的数据正确，那么从表格数据中获取与数据中不重复的记录，然后导入到系统中。重复记录跳过不予导入。
                // 是否重复的判断条件： 如果编号一样，则视为重复
                Map<String, List<Pos>> posMap = splitPos(posList);

                if (CollectionUtils.isNotEmpty(posMap.get(POS_VALID))) {
                    List<Pos> validPos = posMap.get(POS_VALID);
                    List<String> keys = posMapper.selectMultipleKeys(validPos.size());
                    for (int i = 0; i < validPos.size(); i++) {
                        Pos pos = validPos.get(i);
                        pos.setId(keys.get(i));
                        pos.setCreateUser(userId);
                    }
                    posMapper.insertPosBatch(validPos);
                }
                response.setCode(ResponseCode.SUCCESS);
                if (CollectionUtils.isNotEmpty(posMap.get(POS_INVALID))) {
                    List<Pos> invalidPos = posMap.get(POS_INVALID);
                    response.setCode(ResponseCode.OSS_BATCH_UPLOAD_FILE_PART_SUCCESS);
                    response.setMessage("<br/>" + (invalidPos.size() + "条数据记录未能成功导入"));
                }
            } else {
                response.setCode(ResponseCode.OSS_BATCH_UPLOAD_FILE_EMPTY);
            }
            return response;
        } else {
            response.setCode(ResponseCode.OSS_BATCH_UPLOAD_FILE_EMPTY);
            return response;
        }
    }

    private Map<String, List<Pos>> splitPos(List<PosBean> beanList) {
        Map<String, List<Pos>> posMap = new HashMap<String, List<Pos>>();
        List<Pos> validPosList = new ArrayList<Pos>();
        List<Pos> invalidPosList = new ArrayList<Pos>();
        for (PosBean bean : beanList) {
            List<Pos> poss = posMapper.findPosByCode((new String[] {bean.getCode()}));
            if (CollectionUtils.isEmpty(poss)) {
                if(bean.getUnitCost() != null) {
                    bean.setUnitCost(bean.getUnitCost().multiply(new BigDecimal(100)));
                }
                validPosList.add(bean);
            } else {
                invalidPosList.add(bean);
            }
        }
        posMap.put(POS_VALID, validPosList);
        posMap.put(POS_INVALID, invalidPosList);
        return posMap;
    }

    /**
     * 验证Excel 文件中数据是否正确，如果因为模板数据中的数据不正确，那么直接返回，不导入任何数据
     * @param posList
     */
    private void validatePosBean(List<PosBean> posList) {
        List<String> msg = new ArrayList<String>();
        for (PosBean bean : posList) {
            if (DDPStringUtil.isNotPopulated(bean.getCode())) {
                msg.add("第" + bean.getRowNum() + "行Pos编码为空");
            }
            // 1.必填 2.全局唯一 3.数字、英文、下划线  4. 1<=长度<=32位字符
            if (!DDPStringUtil.lengthRange(bean.getCode(), 1, 32) || !DDPValidationHandler.validate(bean.getCode(), true, DdpValidationBoxEnum.EN_NO_US)) {
                msg.add("第" + bean.getRowNum() + "行Pos编码不正确:只能是长度为1-32位字符的数字、英文、下划线");
            }

            // POS厂商 必选
            if (DDPStringUtil.isNotPopulated(bean.getPosCompanyCode())) {
                msg.add("第" + bean.getRowNum() + "行Pos厂商为空");
            } else {
                int count = companyMapper.countPosCompany(bean.getPosCompanyCode());
                if (count != 1) {
                    msg.add("第" + bean.getRowNum() + "行Pos厂商不存在");
                }
            }

            // POS型号名称 必选
            if (DDPStringUtil.isNotPopulated(bean.getPosTypeCode())) {
                msg.add("第" + bean.getRowNum() + "行Pos型号为空");
            } else {
                int count = posTypeMapper.countPosType(bean.getPosTypeCode());
                if (count != 1) {
                    msg.add("第" + bean.getRowNum() + "行Pos型号不存在");
                }
            }
            // 版本 1.必填 2.任意字段 3.长度<=64位字符
            if (DDPStringUtil.isNotPopulated(bean.getVersion())) {
                msg.add("第" + bean.getRowNum() + "行Pos版本为空");
            } else if (!DDPStringUtil.lengthRange(bean.getVersion(), 0, 64)) {
                msg.add("第" + bean.getRowNum() + "行Pos版本不正确:最大长度只能为64位字符");
            }
            // POS批次号 1.必填 2.英文、数字 3.长度<=20字符
            if (DDPStringUtil.isNotPopulated(bean.getSerialNo())) {
                msg.add("第" + bean.getVersion() + "行Pos批次号为空");
            } else if (!DDPStringUtil.lengthRange(bean.getSerialNo(), 0, 20) || !DDPValidationHandler.validate(bean.getSerialNo(), true, DdpValidationBoxEnum.EN_NO)) {
                msg.add("第" + bean.getRowNum() + "行Pos批次号不正确:只能是最大长度为20位的数字、英文");
            }
            
            if (DDPStringUtil.isPopulated(bean.getUnitCostStr())) {
                try {
                    bean.setUnitCost(new BigDecimal(bean.getUnitCostStr()));
                } catch(Exception e) {
                    e.printStackTrace();
                    msg.add("第" + bean.getRowNum()  + "行, 采购成本格式不正确");
                }
            } else {
                bean.setUnitCost(null);
            }

            if (DDPStringUtil.isPopulated(bean.getMaxTimesStr())) {
                try {
                    bean.setMaxTimes((new BigDecimal(bean.getMaxTimesStr())).longValue());
                } catch(Exception e) {
                    e.printStackTrace();
                    msg.add("第" + bean.getRowNum() + "行, 限制笔数格式不正确");
                }
            } else {
                bean.setMaxTimes(0);
            }
            
            // 采购成本 1.非必填  2.正数 3.保留到小数点后两位 4.长度<=10字符
            if (bean.getUnitCost() != null) {
                BigDecimal cost = bean.getUnitCost();
                if (cost.scale() > 2 || cost.compareTo(BigDecimal.ZERO) == -1 || cost.compareTo((new BigDecimal("99999999"))) > 0) {
                    msg.add("第" + bean.getRowNum() + "行Pos采购成本不正确:只能是保留2位小数的正数且最大不能超过99999999.99");
                }
            }

            // 限制笔数 1.非必填 2.非负数 3.长度<=10字符
            if (bean.getMaxTimes() < 0 || bean.getMaxTimes() > (new Long("9999999999")).longValue()) {
                msg.add("第" + bean.getRowNum() + "行Pos限制笔数不正确:只能是非负数且最大不能超过9999999999");
            }
            // 备注：长度 <=200字符
            if (!DDPStringUtil.lessThan(bean.getComments(),100)) {
                msg.add("第" + bean.getRowNum() + "行Pos备注：长度能大于100");
            }

            List<String> duplicatedPos = validateDuplicatedPos(posList, bean);
            if (CollectionUtils.isNotEmpty(duplicatedPos)) {
                msg.addAll(duplicatedPos);
            }
        }

        if (!msg.isEmpty()) {
            throw new DDPException("", "" + DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }

    private List<String> validateDuplicatedPos(List<PosBean> posList, PosBean pos) {
        List<String> msg = new ArrayList<String>();
        for (PosBean bean : posList) {
            if (pos.getRowNum() != bean.getRowNum() && pos.getRowNum() < bean.getRowNum() && pos.getCode().equals(bean.getCode())) {
                msg.add("第" + pos.getRowNum() + "行与第" + bean.getRowNum() + "行POS编码重复");
            }
        }
        return msg;
    }

    // POS编码    POS厂商   POS型号   POS批次号  版本  采购成本    限制笔数
    private List<PosBean> parsePos(byte[] config) {
        List<PosBean> posList = new ArrayList<PosBean>();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(config);
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                String code = getValue(row.getCell(PosConstants.COL_CODE_INDEX),false);
                String company = getValue(row.getCell(PosConstants.COL_CODE_COMPANY_INDEX),false);
                String type = getValue(row.getCell(PosConstants.COL_CODE_TYPE_INDEX),false);
                String serialNo = getValue(row.getCell(PosConstants.COL_CODE_SERIAL_NO_INDEX),false);
                String version = getValue(row.getCell(PosConstants.COL_CODE_VERSION_INDEX),false);
                String cost = getValue(row.getCell(PosConstants.COL_CODE_COST_INDEX),true);
                String maxtimes = getValue(row.getCell(PosConstants.COL_CODE_MAXTIMES_INDEX),false);
                String comments = getValue(row.getCell(PosConstants.COL_CODE_COMMENTS_INDEX),false);
                if (DDPStringUtil.isAllBlank(new String[] {code, company, type, serialNo})) {
                    continue;
                }
                PosBean pos = new PosBean();
                pos.setCode(code);
                pos.setPosCompanyCode(company);
                pos.setPosTypeCode(type);
                pos.setSerialNo(serialNo);
                pos.setVersion(version);
                pos.setUnitCostStr(cost);
                pos.setMaxTimesStr(maxtimes);
                pos.setComments(comments);
                pos.setBind("1");
                pos.setStatus(PosStatusEnum.ACTIVATE.getCode());
                pos.setRowNum(rowNum + 1);
                posList.add(pos);
            }
            return posList;
        }
        catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            e.printStackTrace();
            throw new DDPException("importPos.error", "导入文件出错", e);
        }
    }
    
    /**
     * 得到excel 表格中的单元格值
     */

    public static String getValue(Cell cell,boolean flg) {
    	
    	
        if (cell == null) {
            return "";
        }
        if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
        	if(flg){
        		return String.valueOf(cell.getNumericCellValue());
        	}
             DecimalFormat df = new DecimalFormat("0");
            return df.format((cell.getNumericCellValue()));  
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosInfo> findPosByList(PosQuery posQuery) {
        List<PosInfo> poses = posMapper.findPosByList(posQuery);
        return poses;
    }
}
