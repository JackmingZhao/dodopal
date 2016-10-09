package com.dodopal.oss.business.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dodopal.api.card.dto.PosSignInOutDTO;
import com.dodopal.api.product.dto.YktPsamDTO;
import com.dodopal.api.product.dto.query.YktPsamQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.PrdYktInfo;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.PrdYktInfoService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.validator.DDPValidationHandler;
import com.dodopal.common.validator.DdpValidationBoxEnum;
import com.dodopal.oss.business.bean.YktPsamBatchInfo;
import com.dodopal.oss.business.bean.YktPsamBean;
import com.dodopal.oss.business.bean.query.YktPsamQuery;
import com.dodopal.oss.business.dao.YktPsamMapper;
import com.dodopal.oss.business.model.MerChant;
import com.dodopal.oss.business.model.Samsigninoff;
import com.dodopal.oss.business.model.YktPsam;
import com.dodopal.oss.business.service.PsamService;
import com.dodopal.oss.delegate.PsamDelegate;

@Service
public class PsamServiceImpl implements PsamService {

    @Autowired
    PsamDelegate psamDelegate;
    @Autowired
    AreaService areaService;
    @Autowired
    PrdYktInfoService yktInfoService;
    @Autowired
    YktPsamMapper yktPsamMapper;
    

    public DodopalResponse<DodopalDataPage<YktPsamBean>> findYktPsamByPage(YktPsamQuery yktPsamQuery) {
        DodopalResponse<DodopalDataPage<YktPsamBean>> response = new DodopalResponse<DodopalDataPage<YktPsamBean>>();
        try {
            YktPsamQueryDTO yktPsamQueryDTO = new YktPsamQueryDTO();
            PropertyUtils.copyProperties(yktPsamQueryDTO, yktPsamQuery);
            DodopalResponse<DodopalDataPage<YktPsamDTO>> rtResponse = psamDelegate.findYktPsamByPage(yktPsamQueryDTO);
            List<YktPsamBean> YktPsamBeanList = new ArrayList<YktPsamBean>();
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode())) {
                for (YktPsamDTO yktPsamDTO : rtResponse.getResponseEntity().getRecords()) {
                    YktPsamBean yktPsamBean = new YktPsamBean();
                    PropertyUtils.copyProperties(yktPsamBean, yktPsamDTO);
                    YktPsamBeanList.add(yktPsamBean);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
            DodopalDataPage<YktPsamBean> pages = new DodopalDataPage<YktPsamBean>(page, YktPsamBeanList);
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOWN_ERROR);
        }
        return response;
    }

    public DodopalResponse<String> batchDeleteYktPsamByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = psamDelegate.batchDeleteYktPsamByIds(ids);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    public DodopalResponse<String> batchActivateMerByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = psamDelegate.batchActivate(ids);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    public DodopalResponse<String> batchNeedDownLoadPsamByIds(List<String> ids) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            response = psamDelegate.batchNeedDownLoad(ids);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }

        return response;
    }

    public DodopalResponse<String> addYktPsam(YktPsamBean yktPsamBean) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            YktPsamDTO yktPsamDTO = new YktPsamDTO();
            PropertyUtils.copyProperties(yktPsamDTO, yktPsamBean);
            response = psamDelegate.addYktPsam(yktPsamDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    public DodopalResponse<String> updateYktPsam(YktPsamBean yktPsamBean) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        try {
            YktPsamDTO yktPsamDTO = new YktPsamDTO();
            PropertyUtils.copyProperties(yktPsamDTO, yktPsamBean);
            response = psamDelegate.updateYktPsam(yktPsamDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<YktPsamBean> findPsamById(String id) {
        DodopalResponse<YktPsamBean> response = new DodopalResponse<YktPsamBean>();
        //校验id不能为空
        if(StringUtils.isBlank(id)){
            response.setCode(ResponseCode.SYSTEM_ERROR);
            return response;
        }
        try {
            YktPsamBean  yktPsamBean = new YktPsamBean(); 
            DodopalResponse<YktPsamDTO> result = psamDelegate.findPsamById(id);
            if(ResponseCode.SUCCESS.equals(result.getCode())){
                PropertyUtils.copyProperties(yktPsamBean, result.getResponseEntity());
                response.setResponseEntity(yktPsamBean);
            }
            response.setCode(result.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @Override
    public DodopalResponse<PosSignInOutDTO> posSignInOutForV61(PosSignInOutDTO posSignInOutDTO) {
        DodopalResponse<PosSignInOutDTO> response = new DodopalResponse<PosSignInOutDTO>();
        try {
           
            response = psamDelegate.posSignInOutForV61(posSignInOutDTO);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    /**
     * PSAM号、通卡编码、城市号 必须，在导入过程中如果这三个有任何一个为空或无效，提示用户错误信息（第XXX行，XXX不能为空或无效），程序终止。
     *      PSAM号必须唯一约束，PASM号12为数字, 通卡商户号最长12为数字,POS号最长12位数字，通卡编码6位数字，城市编码4位数字
     *      
     * 通卡商户号为空时，都都宝平台商户号、POS号、POS类型 不进行判断，不取值 。
     * 
     * 通卡商户号不为空时， 都都宝平台商户号必须；如果都都宝平台商户号为空或无效，（第XXX行，XXX不能为空或无效），程序终止。
     * 
     * 通卡商户号不为空时，且都都宝平台商户号有效：
     *      POS号不为空，判断POS是否与该商户已绑定；如果没绑定，跳过当前行，执行下一行数据；
     *      如果绑定，POS类型必须且值必须为0（家用机）或1（手持用机）如果POS类型无效或空，（第XXX行，XXX不能为空或无效），程序终止。
     * 
     */
    @Override
    public DodopalResponse<String> importPsam(CommonsMultipartFile file, String userId) {
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

            // 解析excel文档生成List信息
            List<YktPsamBatchInfo> psamBatchList = parsePsam(file.getBytes());

            if (psamBatchList != null && !psamBatchList.isEmpty()) {
                
                // 验证文件中的数据是否正确，如果不正确直接抛出异常返回.
                validatePsamBatchInfo(psamBatchList);
                
                List<YktPsam> psamlist = new ArrayList<YktPsam>();
                List<Samsigninoff> samsigninofflist = new ArrayList<Samsigninoff>();
                for (YktPsamBatchInfo info : psamBatchList) {
                    YktPsam yktPsam = new YktPsam();
                    yktPsam.setSamNo(info.getSamNo());
                    yktPsam.setYktCode(info.getYktCode());
                    yktPsam.setYktName(info.getYktName());
                    yktPsam.setCityCode(info.getCityCode());
                    yktPsam.setCityName(info.getCityName());
                    yktPsam.setMerCode(info.getMerCode());
                    yktPsam.setMerName(info.getMerName());
                    psamlist.add(yktPsam);
                    
                    Samsigninoff samsigninoff = new Samsigninoff();
                    samsigninoff.setYktCode(info.getYktCode());
                    samsigninoff.setSamNo(info.getSamNo());
                    samsigninoff.setMchntid(info.getMchntid());
                    samsigninoff.setPosId(info.getPosId());
                    samsigninoff.setPosType(info.getPosType());
                    samsigninoff.setNeedDownLoad("2");
                    samsigninoff.setSettDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));//结算日期，当前系统时间
                    samsigninoff.setSigninFlag("0");//签退标志
                    samsigninoff.setSignoffFlag("1");//签退标志
                    samsigninoff.setBatchStep("4");//日切步骤控制
                    samsigninofflist.add(samsigninoff);
                }
                
                // 批量导入psam表
                yktPsamMapper.insertPsamBatch(psamlist);
                
                // 批量导入签到控制表记录表
                yktPsamMapper.insertSamSigninOffBatch(samsigninofflist);
                
                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.OSS_BATCH_UPLOAD_FILE_EMPTY);
            }
            return response;
        } else {
            response.setCode(ResponseCode.OSS_BATCH_UPLOAD_FILE_EMPTY);
            return response;
        }
    }
    
    
    /**
     * 
     * PSAM号、通卡编码、城市号 必须，在导入过程中如果这三个有任何一个为空或无效，提示用户错误信息（第XXX行，XXX不能为空或无效），程序终止。
     *      PSAM号必须唯一约束，PASM号12为数字, 通卡商户号最长12为数字,POS号最长12位数字，通卡编码6位数字，城市编码4位数字
     *      
     * 通卡商户号为空时，都都宝平台商户号、POS号、POS类型 不进行判断，不取值 。
     * 
     * 通卡商户号不为空时， 都都宝平台商户号必须；如果都都宝平台商户号为空或无效，（第XXX行，XXX不能为空或无效），程序终止。
     * 
     * 通卡商户号不为空时，且都都宝平台商户号有效：
     *      POS号不为空，判断POS是否与该商户已绑定；如果没绑定，提示错误信息（第XXX行，POS：XXXX 与商户未绑定）；
     *      如果绑定，POS类型必须且值必须为0（家用机）或1（手持用机）如果POS类型无效或空，（第XXX行，XXX不能为空或无效），程序终止。
     *      
     * @param psamBatchInfoList
     */
    private void validatePsamBatchInfo(List<YktPsamBatchInfo> psamBatchInfoList) {
        List<String> msg = new ArrayList<String>();
        for (YktPsamBatchInfo bean : psamBatchInfoList) {
            
            if (DDPStringUtil.isNotPopulated(bean.getSamNo())) {
                msg.add(" 第" + bean.getRowNum() + "行Psam号为空");
            }
            // 1.必填 2.全局唯一 3.数字 4. 1<=长度<=12位字符
            if (!DDPStringUtil.lengthRange(bean.getSamNo(), 1, 12) || !DDPValidationHandler.validate(bean.getSamNo(), true, DdpValidationBoxEnum.NO)) {
                msg.add(" 第" + bean.getRowNum() + "行Psam号不正确:长度为12位以内数字");
            }

            // 验证数据库是否已存在相同的psam号信息，如果存在，抛出提示信息，程序终止。
            YktPsam yktPsam = yktPsamMapper.getPsamBySamNo(bean.getSamNo());
            if (null != yktPsam) {
                msg.add(" 第" + bean.getRowNum() + "行Psam号数据库已存在");
            }
            
            // 通卡编码 必选
            if (DDPStringUtil.isNotPopulated(bean.getYktCode())) {
                msg.add(" 第" + bean.getRowNum() + "行通卡编码为空");
            } else {
                // 验通卡编码有效性，并取值通卡名称
                PrdYktInfo yktInfo = yktInfoService.findPrdYktInfoYktCode(bean.getYktCode());
                if (null == yktInfo) {
                    msg.add(" 第" + bean.getRowNum() + "行通卡编码不存在");
                } else {
                    bean.setYktName(yktInfo.getYktName());
                }
            }

            // 城市编码 必选
            if (DDPStringUtil.isNotPopulated(bean.getCityCode())) {
                msg.add(" 第" + bean.getRowNum() + "行城市编码为空");
            } else {
                // 验城市编码有效性，并取值城市名称
                Area area = areaService.findCityByCityCode(bean.getCityCode());
                if (null == area) {
                    msg.add(" 第" + bean.getRowNum() + "行城市编码不存在");
                } else {
                    bean.setCityName(area.getCityName());
                }
            }

            // 通卡商户号为空时，都都宝平台商户号、POS号、POS类型 不进行判断，不取值 。
            if (DDPStringUtil.isNotPopulated(bean.getMchntid())) {
                bean.setMerCode("");
                bean.setPosId("");
                bean.setPosType("");
            } else if (!DDPStringUtil.lengthRange(bean.getMchntid(), 1, 12)) {
                msg.add(" 第" + bean.getRowNum() + "行通卡商户号不正确:只能是长度为1-12位字符");
            } else {
                
                // 通卡商户号不为空时， 都都宝平台商户号必须；如果都都宝平台商户号为空或无效，（第XXX行，XXX不能为空或无效），程序终止。
                if (DDPStringUtil.isNotPopulated(bean.getMerCode())) {
                    msg.add(" 第" + bean.getRowNum() + "行DDP平台商户号为空");
                } else {
                    MerChant merChant = yktPsamMapper.getMerchantByMerCode(bean.getMerCode());
                    if (null == merChant) {
                        msg.add(" 第" + bean.getRowNum() + "行DDP平台商户号不存在");
                    } else {
                    	
                    	bean.setMerName(merChant.getMerName());
                        //通卡商户号不为空时，且都都宝平台商户号有效：
                        //     POS号不为空，判断POS是否与该商户已绑定；如果没绑定，提示错误信息（第XXX行，POS：XXXX 与商户未绑定）；
                        //     如果绑定，POS类型必须有值必须为0（家用机）或1（手持用机）如果POS类型无效或空，（第XXX行，XXX不能为空或无效），程序终止。
                        if (!DDPStringUtil.isNotPopulated(bean.getPosId())) {
                            
                            boolean bind = yktPsamMapper.checkPosMerBind(bean.getMerCode(), bean.getPosId());
                            if (!bind) {
                                msg.add(" 第" + bean.getRowNum() + "行Pos号与商户未绑定");
                            } else {
                                if (DDPStringUtil.isNotPopulated(bean.getPosType())) {
                                    msg.add(" 第" + bean.getRowNum() + "行Pos类型为空");
                                } else if (!"0".equals(bean.getPosType()) && !"1".equals(bean.getPosType())) {
                                    msg.add(" 第" + bean.getRowNum() + "行Pos类型无效");
                                }
                            }
                        }
                    }
                }
            }
            
            // 验PSAM号重复性
            List<String> duplicatedPsam = validateDuplicatedPsam(psamBatchInfoList, bean);
            if (CollectionUtils.isNotEmpty(duplicatedPsam)) {
                msg.addAll(duplicatedPsam);
            }
        }

        if (!msg.isEmpty()) {
        	//最多显示4条错误信息
        	if(msg.size()>4){
        		msg = msg.subList(0, 4);
        	}
            throw new DDPException("", "" + DDPStringUtil.concatenate(msg, ";<br/>"));
        }
    }
    
    private List<String> validateDuplicatedPsam(List<YktPsamBatchInfo> psamList, YktPsamBatchInfo pos) {
        List<String> msg = new ArrayList<String>();
        for (YktPsamBatchInfo bean : psamList) {
            if (pos.getRowNum() != bean.getRowNum() && pos.getRowNum() < bean.getRowNum() && pos.getSamNo().equals(bean.getSamNo())) {
                msg.add(" 第" + pos.getRowNum() + "行与第" + bean.getRowNum() + "行Psam号重复");
            }
        }
        return msg;
    }
    
    private List<YktPsamBatchInfo> parsePsam(byte[] config) {
        List<YktPsamBatchInfo> psamList = new ArrayList<YktPsamBatchInfo>();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(config);
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                String samNo = getValue(row.getCell(0),false);//psam号
                String yktCode = getValue(row.getCell(1),false);//通卡编码
                String cityCode = getValue(row.getCell(2),false);//城市编码
                String mchntid = getValue(row.getCell(3),false);//通卡商户号
                String merCode = getValue(row.getCell(4),false);//DDP平台商户号
                String posId = getValue(row.getCell(5),true);//POS号
                String posType = getValue(row.getCell(6),false);//POS型号
                if (DDPStringUtil.isAllBlank(new String[] {samNo, yktCode, cityCode, mchntid, merCode, posId, posType})) {
                    continue;
                }
                YktPsamBatchInfo psamBatchInfo = new YktPsamBatchInfo();
                psamBatchInfo.setSamNo(samNo);
                psamBatchInfo.setYktCode(yktCode);
                psamBatchInfo.setCityCode(cityCode);
                psamBatchInfo.setMchntid(mchntid);
                psamBatchInfo.setMerCode(merCode);
                psamBatchInfo.setPosId(posId);
                psamBatchInfo.setPosType(posType);
                psamBatchInfo.setRowNum(rowNum + 1);
                psamList.add(psamBatchInfo);
            }
            return psamList;
        }
        catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            e.printStackTrace();
            throw new DDPException("importPsam.error", "导入文件出错", e);
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
}
