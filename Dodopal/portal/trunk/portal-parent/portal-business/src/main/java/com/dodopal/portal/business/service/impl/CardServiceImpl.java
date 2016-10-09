package com.dodopal.portal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.common.util.ExcelUtil;
import com.dodopal.portal.business.bean.MerUserCardBDBean;
import com.dodopal.portal.business.bean.MerUserCardBDLogBean;
import com.dodopal.portal.business.bean.UserCardRecord;
import com.dodopal.portal.business.bean.query.MerUserCardBDFind;
import com.dodopal.portal.business.bean.query.UserCardLogQuery;
import com.dodopal.portal.business.model.query.UserCardRecordQuery;
import com.dodopal.portal.business.service.CardService;
import com.dodopal.portal.delegate.CardDelegate;

/**
 * 卡片管理
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月16日
 */
@Service
public class CardServiceImpl implements CardService {
    private static Logger log = Logger.getLogger(CardServiceImpl.class);

    @Autowired
    CardDelegate cardDelegate;

    //用户卡片查询
    public DodopalResponse<List<MerUserCardBDBean>> findMerUserCardBD(MerUserCardBDFind cardBDFind) {
        log.info("用户卡片查询 CardServiceImpl findMerUserCardBD cardBDFind：" + cardBDFind);
        DodopalResponse<List<MerUserCardBDBean>> response = new DodopalResponse<List<MerUserCardBDBean>>();
        try {
            MerUserCardBDFindDTO cardBDFindDTO = new MerUserCardBDFindDTO();
            PropertyUtils.copyProperties(cardBDFindDTO, cardBDFind);
            DodopalResponse<List<MerUserCardBDDTO>> rtResponse = cardDelegate.findMerUserCardBD(cardBDFindDTO);
            List<MerUserCardBDBean> CardBDList = new ArrayList<MerUserCardBDBean>();

            if (rtResponse.getCode().equals(ResponseCode.SUCCESS) && rtResponse.getResponseEntity() != null) {
                for (MerUserCardBDDTO CardBDDTO : rtResponse.getResponseEntity()) {
                    MerUserCardBDBean CardBDBean = new MerUserCardBDBean();
                    PropertyUtils.copyProperties(CardBDBean, CardBDDTO);
                    CardBDList.add(CardBDBean);
                }
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(CardBDList);
        }
        catch (HessianRuntimeException e) {
            log.debug("用户卡片查询 CardServiceImpl findMerUserCardBD  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("用户卡片查询 CardServiceImpl findMerUserCardBD call e:" + e);
            // TODO: handle exception
        }
        return response;
    }

    //卡片操作日志查询
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> findUserCardBDLogByPage(UserCardLogQuery query) {
        DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDLogBean>>();
        try {
            UserCardLogQueryDTO queryDTO = new UserCardLogQueryDTO();
            PropertyUtils.copyProperties(queryDTO, query);
            DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> rtResponse = cardDelegate.findUserCardBDLogByPage(queryDTO);
            if (ResponseCode.SUCCESS.equals(rtResponse.getCode())) {

                List<MerUserCardBDLogBean> CardBDLogBeanList = new ArrayList<MerUserCardBDLogBean>();

                if (rtResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
                    for (MerUserCardBDLogDTO CardBDLogDTO : rtResponse.getResponseEntity().getRecords()) {
                        MerUserCardBDLogBean CardBDLogBean = new MerUserCardBDLogBean();
                        PropertyUtils.copyProperties(CardBDLogBean, CardBDLogDTO);
                        CardBDLogBeanList.add(CardBDLogBean);

                    }
                }
                //后台传入总页数，总条数，当前页
                PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
                DodopalDataPage<MerUserCardBDLogBean> pages = new DodopalDataPage<MerUserCardBDLogBean>(page, CardBDLogBeanList);
                response.setCode(rtResponse.getCode());
                response.setResponseEntity(pages);
            } else {
                response.setCode(rtResponse.getCode());
            }
        
        }       
        catch (HessianRuntimeException e) {
            log.debug("用户卡片操作日志查询 CardServiceImpl findUserCardBDLogByPage  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("用户卡片操作日志查询 CardServiceImpl findUserCardBDLogByPage  call  Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    //编辑卡片
    public DodopalResponse<Boolean> editUserCard(MerUserCardBDBean cardBean) {
        log.info("编辑卡片信息 CardServiceImpl editUserCard cardBean:"+cardBean.toString());
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            MerUserCardBDDTO cardDTO = new MerUserCardBDDTO();
            PropertyUtils.copyProperties(cardDTO, cardBean);
            response = cardDelegate.editUserCard(cardDTO);
        }        
        catch (HessianRuntimeException e) {
            log.debug("编辑卡片信息 CardServiceImpl editUserCard  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("编辑卡片信息 CardServiceImpl editUserCard  call Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
    
        return response;
    }

    //解绑
    public DodopalResponse<String> unbindCardByUser(String userId,String operName, List<String> ids, String source) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        if(StringUtils.isEmpty(userId)){
            response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
            return response;
        }
        if(StringUtils.isEmpty(source)){
            response.setCode(ResponseCode.USERS_SOURCE_NOT_EMPTY);
            return response;
        }
        
        try {
            response = cardDelegate.unbindCardByUser(userId,operName, ids, source);
        }
        catch (HessianRuntimeException e) {
            log.debug("用户解绑卡片 CardServiceImpl unbindCardByUser  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("编用户解绑卡片 CardServiceImpl unbindCardByUser  call Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }
        return response;
    }

    //根据id 查询用户卡片信息
    public DodopalResponse<MerUserCardBDBean> findMerUserCardBDById(String id) {
        DodopalResponse<MerUserCardBDBean> response = new DodopalResponse<MerUserCardBDBean>();
        try {
            MerUserCardBDBean merUserCardBDBean = new MerUserCardBDBean();
            DodopalResponse<MerUserCardBDDTO> rtResponse =cardDelegate.findMerUserCardBDById(id);
            if(rtResponse.getCode().equals(ResponseCode.SUCCESS)&&rtResponse.getResponseEntity()!=null){
                MerUserCardBDDTO merUserCardBDDTO = rtResponse.getResponseEntity();
                PropertyUtils.copyProperties(merUserCardBDBean,merUserCardBDDTO );
            }
            response.setCode(rtResponse.getCode());
            response.setResponseEntity(merUserCardBDBean);
        }
        catch (HessianRuntimeException e) {
            log.debug("根据id 查询用户卡片信息 CardServiceImpl findMerUserCardBDById  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
        }
        catch (Exception e) {
            log.debug("根据id 查询用户卡片信息 CardServiceImpl findMerUserCardBDById  call Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
       
        return response;
    }

	// 查询个人卡片充值和消费信息
	public DodopalResponse<DodopalDataPage<UserCardRecord>> findUserCardRecordByPage(UserCardRecordQuery query) {
		DodopalResponse<DodopalDataPage<UserCardRecord>> response  = new DodopalResponse<DodopalDataPage<UserCardRecord>>();
		try {
			UserCardRecordQueryDTO queryDTO = new UserCardRecordQueryDTO();
			PropertyUtils.copyProperties(queryDTO, query);
			DodopalResponse<DodopalDataPage<UserCardRecordDTO>> rtResponse = cardDelegate.findUserCardRecordByPage(queryDTO);
			if(ResponseCode.SUCCESS.equals(rtResponse.getCode())) {
				List<UserCardRecord> userCardRecordList = new ArrayList<>();
				if(rtResponse.getResponseEntity() != null && CollectionUtils.isNotEmpty(rtResponse.getResponseEntity().getRecords())) {
					for(UserCardRecordDTO userCardRecordDTO : rtResponse.getResponseEntity().getRecords()) {
						UserCardRecord userCardRecord = new UserCardRecord();
						PropertyUtils.copyProperties(userCardRecord, userCardRecordDTO);
						userCardRecordList.add(userCardRecord);
					}
				}
				PageParameter page = DodopalDataPageUtil.convertPageInfo(rtResponse.getResponseEntity());
				DodopalDataPage<UserCardRecord> pages = new DodopalDataPage<UserCardRecord>(page, userCardRecordList);
				response.setCode(rtResponse.getCode());
				response.setResponseEntity(pages);
			}else {
				response.setCode(rtResponse.getCode());
			}
		}catch(HessianRuntimeException e) {
			log.debug("用户查询卡片交易记录 CardServiceImpl findMerUserCardRecordByPage  call HessianRuntimeException error", e);
			e.printStackTrace();
			response.setCode(ResponseCode.HESSIAN_ERROR);
		}catch (Exception e) {
			log.debug("用户查询卡片交易记录 CardServiceImpl findMerUserCardRecordByPage  call  Exception error", e);
			e.printStackTrace();
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	// 查询个人绑定卡交易记录 一单的详情(充值或消费)
	public DodopalResponse<UserCardRecord> findCardRecordInfoByTypeOrderNum(String type, String orderNum) {
		DodopalResponse<UserCardRecord> response = new DodopalResponse<UserCardRecord>();
		try {
			UserCardRecord userCardRecord = new UserCardRecord();
			DodopalResponse<UserCardRecordDTO> rtResponse =cardDelegate.findCardRecordInfoByTypeOrderNum(type, orderNum);
			if(rtResponse.getCode().equals(ResponseCode.SUCCESS) && rtResponse.getResponseEntity() != null) {
				UserCardRecordDTO userCardRecordDTO = rtResponse.getResponseEntity();
				PropertyUtils.copyProperties(userCardRecord, userCardRecordDTO);
			}
			response.setCode(rtResponse.getCode());
			response.setResponseEntity(userCardRecord);
		}catch(HessianRuntimeException e) {
			log.debug("根据type orderNum 查询用户卡片详细信息 CardServiceImpl findCardRecordInfoByTypeOrderNum  call HessianRuntimeException error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.HESSIAN_ERROR);
		}catch (Exception e) {
			log.debug("根据id 查询用户卡片信息 CardServiceImpl findMerUserCardBDById  call Exception error", e);
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	// 门户个人 绑定卡交易记录 导出
	public DodopalResponse<String> excelExport(HttpServletRequest request, HttpServletResponse response, UserCardRecordQueryDTO queryDTO) {
		DodopalResponse<DodopalDataPage<UserCardRecordDTO>> dto = cardDelegate.findUserCardRecordByPage(queryDTO);
		List<UserCardRecordDTO> dtoData = dto.getResponseEntity().getRecords();
		List<UserCardRecord> dataList = new ArrayList<UserCardRecord>();
		DodopalResponse<DodopalDataPage<UserCardRecord>> ddRes = new DodopalResponse<DodopalDataPage<UserCardRecord>>();
		DodopalDataPage<UserCardRecord> ddpDataPage;
		String sheetName = new String("绑定卡交易记录");
		DodopalResponse<String> excelExport = new DodopalResponse<String>();
		try {
			ExcelModel excelModel = new ExcelModel("userCardRecordList");
			excelModel.setDataStartIndex(3); // 数据开始行, 可以不设置,默认为3(第四行)
			if (CollectionUtils.isNotEmpty(dtoData)) {
				for(UserCardRecordDTO _dto : dtoData) {
					UserCardRecord _data = new UserCardRecord();
					PropertyUtils.copyProperties(_data, _dto);
					dataList.add(_data);
				}
				ddpDataPage = new DodopalDataPage<UserCardRecord>(new PageParameter(1, 5000), dataList);
				ddRes.setResponseEntity(ddpDataPage);
				Map<String, String> index = new LinkedHashMap<String, String>();
				index.put("orderNum", 		"订单号");
				index.put("typeStr", 		"交易类型");
				index.put("statusStr", 		"交易状态");
				index.put("orderDateStr", 	"交易时间");
				index.put("txnAmtStr", 		"交易金额（元）");
				index.put("merName", 		"商户名称");
				index.put("befBalStr", 		"交易前卡余额（元）");
				index.put("blackAmtStr", 	"交易后卡余额（元）");
				index.put("cardFaceNo", 	"卡号");
				
				return ExcelUtil.ddpExcelExport(request, response, ddRes, index, sheetName);
			}else {
				return ExcelUtil.excelExport(excelModel, response);
			}
		} catch(Exception e) {
			excelExport.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
		}
		return excelExport;
	}

}