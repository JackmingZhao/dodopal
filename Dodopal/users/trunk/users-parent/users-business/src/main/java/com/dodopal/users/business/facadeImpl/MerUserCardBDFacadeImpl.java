package com.dodopal.users.business.facadeImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerUserCardBDDTO;
import com.dodopal.api.users.dto.MerUserCardBDFindDTO;
import com.dodopal.api.users.dto.MerUserCardBDLogDTO;
import com.dodopal.api.users.dto.UserCardRecordDTO;
import com.dodopal.api.users.dto.query.MerUserCardBDDTOQuery;
import com.dodopal.api.users.facade.MerUserCardBDFacade;
import com.dodopal.api.users.facade.UserCardLogQueryDTO;
import com.dodopal.api.users.facade.UserCardRecordQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.CardBindEnum;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.model.MerUserCardBD;
import com.dodopal.users.business.model.MerUserCardBDLog;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.UserCardRecord;
import com.dodopal.users.business.model.query.MerUserCardBDQuery;
import com.dodopal.users.business.model.query.UserCardLogQuery;
import com.dodopal.users.business.model.query.UserCardRecordQuery;
import com.dodopal.users.business.service.MerUserCardBDService;
import com.dodopal.users.business.service.MerchantUserService;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午4:25:56
 */
@Service("merUserCardBDFacade")
public class MerUserCardBDFacadeImpl implements MerUserCardBDFacade {
    private final static Logger log = LoggerFactory.getLogger(MerUserCardBDFacadeImpl.class);
    @Autowired
    private MerUserCardBDService bdService;
    @Autowired
    private MerchantUserService merchantUserService;

    @Override
    public DodopalResponse<List<MerUserCardBDDTO>> findMerUserCardBD(MerUserCardBDFindDTO cardBDFindDTO) {
        MerUserCardBD cardBdModel = new MerUserCardBD();
        List<MerUserCardBDDTO> cardtoList = new ArrayList<MerUserCardBDDTO>();
        DodopalResponse<List<MerUserCardBDDTO>> response = new DodopalResponse<List<MerUserCardBDDTO>>();
        try {
            PropertyUtils.copyProperties(cardBdModel, cardBDFindDTO);
            List<MerUserCardBD> cardBDlist = bdService.findMerUserCardBDList(cardBdModel);
            if (cardBDlist.size() > 0) {
                for (MerUserCardBD cardBDTmp : cardBDlist) {
                    MerUserCardBDDTO cardBdDTO = new MerUserCardBDDTO();
                    PropertyUtils.copyProperties(cardBdDTO, cardBDTmp);
                    cardtoList.add(cardBdDTO);
                }
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(cardtoList);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            log.debug("MerUserCardBDFacadeImpl call error", e);
            throw new RuntimeException(e);
        }

        return response;
    }

    @Override
    public DodopalResponse<Integer> findMerUserCardBDCount(MerUserCardBDDTO cardBDDTO) {
        log.info("查询用户绑定卡数MerUserCardBDFacadeImpl findMerUserCardBDCount  cardBDDTO" + cardBDDTO.toString());
        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
        MerUserCardBD cardBdModel = new MerUserCardBD();
        String merUserName = cardBDDTO.getMerUserName();
        //用户号 不能为空
        if (null == merUserName) {
            response.setCode(ResponseCode.USERS_USER_CODE_NULL);
            return response;
        }
        try {
            PropertyUtils.copyProperties(cardBdModel, cardBDDTO);
            int count = bdService.findMerUserCardBDCount(cardBdModel);
            response.setResponseEntity(count);
            response.setCode(ResponseCode.SUCCESS);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
            log.debug("MerUserCardBDFacadeImpl call error", e);
        }

        return response;
    }

    //绑定
    @Override
    public DodopalResponse<MerUserCardBDDTO> saveMerUserCardBD(MerUserCardBDDTO cardBDDTO) {
        log.info("用户卡片绑定 MerUserCardBDFacadeImpl saveMerUserCardBD  cardBDDTO" + cardBDDTO.toString());
        DodopalResponse<MerUserCardBDDTO> response = new DodopalResponse<MerUserCardBDDTO>();
        String merUserName = cardBDDTO.getMerUserName();
        //用户登录账户 不能为空
        if (null == merUserName) {
            response.setCode(ResponseCode.USERS_USER_CODE_NULL);
            return response;
        }
        // 卡号不能为空
        String cardCode = cardBDDTO.getCardCode();
        if (null == cardCode) {
            response.setCode(ResponseCode.USERS_CARD_CODE_NOT_EMPTY);
            return response;
        }

        try {
            MerUserCardBD cardBdModel = new MerUserCardBD();
            PropertyUtils.copyProperties(cardBdModel, cardBDDTO);
            //用户绑定卡片数查询 根据  登录用户名
            int count = bdService.findMerUserCardBDCount(cardBdModel);
            //用户绑卡数 限制（5），需要记录到数据字典
            if (count >= 5) {
                response.setCode(ResponseCode.USERS_CARD_CODE_BIND_OVER);
                return response;
            } else {

                //卡片是否绑定
                List<MerUserCardBD> merUserCardBDList = new ArrayList<MerUserCardBD>();
                try {
                    //查询表中该卡片的绑定记录 List
                    merUserCardBDList = bdService.findMerUserCardByCardCode(cardCode);

                }
                catch (Exception e) {
                    e.printStackTrace();
                    response.setCode(ResponseCode.SYSTEM_ERROR);
                    log.debug("用户卡片绑定 MerUserCardBDFacadeImpl saveMerUserCardBD throws:" + e);
                    // TODO: handle exception
                }
                   //表中之前有绑定该卡的记录
                if (merUserCardBDList.size()>0) {
                    //如果卡片之前和多个用户有过绑定，现在处于解绑状态，和新用户绑定，表里产生新的用户绑卡记录
                    int m=0;
                    for (MerUserCardBD merUserCardBD : merUserCardBDList) {
                        //卡已绑定
                        if (merUserCardBD.getBundLingType().equals(BindEnum.ENABLE.getCode())) {
                            response.setCode(ResponseCode.USERS_CARD_CODE_BIND_ENABLE);
                            return response;
                         //该用户之前已有绑定该卡的记录，直接修改绑定状态
                        }else if (merUserCardBD.getMerUserName().equals(cardBDDTO.getMerUserName())) {
                            int num = bdService.updateBindType(cardBdModel);
                            m+=1;
                            if(num>0){
                                try {
                                    MerchantUser user = merchantUserService.findMerchantUserByUserName(merUserCardBD.getMerUserName());
                                    //添加卡片操作日志
                                    MerUserCardBDLog userCardLog = new MerUserCardBDLog();
                                    userCardLog.setCode(cardBDDTO.getCardCode());
                                    userCardLog.setUserCode(user.getUserCode());
                                    userCardLog.setCardName(cardBDDTO.getCardName());
                                    userCardLog.setCreateUser(user.getId());
                                    userCardLog.setMerUserNickName(user.getMerUserNickName());
                                    userCardLog.setSource(cardBDDTO.getSource());
                                    userCardLog.setOperStatus(CardBindEnum.ENABLE.getCode());
                                    userCardLog.setUpdateUser(user.getId());
                                    userCardLog.setOperName(user.getMerUserNickName());
                                    //保存卡的所属城市及卡的类型
                                    userCardLog.setCardCityName(cardBDDTO.getCardCityName());
                                    userCardLog.setCardType(cardBDDTO.getCardType());
                                    //记录操作日志
                                    bdService.addCardOperLog(userCardLog);

                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                    log.debug("用户卡片绑定  记录操作日志 MerUserCardBDFacadeImpl saveMerUserCardBD throws:" + e);
                                    // TODO: handle exception
                                }
                                response.setCode(ResponseCode.SUCCESS);
                                return response;
                            }else{
                                response.setCode(ResponseCode.USERS_CARD_CODE_BIND_FAILURE);
                            }
                        }

                    }
                    if(m==0){

                            //数据库添加记录，绑定卡片
                            MerUserCardBD userCard = new MerUserCardBD();

                            PropertyUtils.copyProperties(userCard, cardBDDTO);
                            //绑定卡片
                            bdService.saveMerUserCardBD(userCard);
                            try {
                                MerchantUser user = merchantUserService.findMerchantUserByUserName(cardBDDTO.getMerUserName());
                                //添加卡片操作日志
                                MerUserCardBDLog userCardLog = new MerUserCardBDLog();
                                userCardLog.setCode(cardBDDTO.getCardCode());
                                userCardLog.setUserCode(user.getUserCode());
                                userCardLog.setCardName(cardBDDTO.getCardName());
                                userCardLog.setCreateUser(user.getId());
                                userCardLog.setMerUserNickName(user.getMerUserNickName());
                                userCardLog.setSource(cardBDDTO.getSource());
                                userCardLog.setOperStatus(CardBindEnum.ENABLE.getCode());
                                userCardLog.setUpdateUser(user.getId());
                                userCardLog.setOperName(user.getMerUserNickName());
                                //保存卡的所属城市及卡的类型
                                userCardLog.setCardCityName(cardBDDTO.getCardCityName());
                                userCardLog.setCardType(cardBDDTO.getCardType());
                                //记录操作日志
                                bdService.addCardOperLog(userCardLog);

                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                log.debug("用户卡片绑定  记录操作日志 MerUserCardBDFacadeImpl saveMerUserCardBD throws:" + e);
                                // TODO: handle exception
                            }

                            response.setCode(ResponseCode.SUCCESS);
                            
                        }
                    
                } else {
                    //当前卡片还没有和用户绑定过，直接添加一条卡片记录
                    //数据库添加记录，绑定卡片
                    MerUserCardBD userCard = new MerUserCardBD();

                    PropertyUtils.copyProperties(userCard, cardBDDTO);
                    //绑定卡片
                    bdService.saveMerUserCardBD(userCard);
                    try {
                        MerchantUser user = merchantUserService.findMerchantUserByUserName(cardBDDTO.getMerUserName());
                        //添加卡片操作日志
                        MerUserCardBDLog userCardLog = new MerUserCardBDLog();
                        userCardLog.setCode(cardBDDTO.getCardCode());
                        userCardLog.setUserCode(user.getUserCode());
                        userCardLog.setCardName(cardBDDTO.getCardName());
                        userCardLog.setCreateUser(user.getId());
                        userCardLog.setMerUserNickName(user.getMerUserNickName());
                        userCardLog.setSource(cardBDDTO.getSource());
                        userCardLog.setOperStatus(CardBindEnum.ENABLE.getCode());
                        userCardLog.setUpdateUser(user.getId());
                        userCardLog.setOperName(user.getMerUserNickName());
                        //保存卡的所属城市及卡的类型
                        userCardLog.setCardCityName(cardBDDTO.getCardCityName());
                        userCardLog.setCardType(cardBDDTO.getCardType());
                        //记录操作日志
                        bdService.addCardOperLog(userCardLog);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        log.debug("用户卡片绑定  记录操作日志 MerUserCardBDFacadeImpl saveMerUserCardBD throws:" + e);
                        // TODO: handle exception
                    }

                    response.setCode(ResponseCode.SUCCESS);

                }

            }
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
            log.debug("用户卡片绑定   校验卡片是否绑定  MerUserCardBDFacadeImpl findMerUserCardByCardCode throws:" + e);
            // TODO: handle exception
        }

        return response;
    }
    
    
    

    //可以批量解绑，门户单个解绑，oss可以批量解绑
    @Override
    public DodopalResponse<String> unBundlingCard(String userId,String operName, List<String> ids, String source) {
        DodopalResponse<String> response = new DodopalResponse<String>();
        //验证参数
        if (ids.isEmpty()) {
            response.setCode(ResponseCode.MER_USER_CARD_BD_ID_NULL);
        } else if (StringUtils.isBlank(userId)) {
            response.setCode(ResponseCode.USERS_FIND_USER_ID_NULL);
        } else if (StringUtils.isBlank(source)) {
            response.setCode(ResponseCode.USERS_SOURCE_NOT_EMPTY);
        }else {
            try {
                int rows = bdService.unBundlingCard(userId, ids);
                //解绑一条记录，记录一条操作日志
                try {
                        if (rows > 0) {
                            for (String id : ids) {
                                MerUserCardBD userCard = new MerUserCardBD();
                                MerUserCardBDLog userCardLog = new MerUserCardBDLog();
                                //根据用户绑卡的数据库id,查询卡片相关信息，用来记录操作日志
                                userCard = bdService.findMerUserCardById(id);

                                userCardLog.setCode(userCard.getCardCode());
                                userCardLog.setUserCode(userCard.getUserCode());
                                userCardLog.setCardName(userCard.getCardName());
                                userCardLog.setMerUserNickName(userCard.getMerUserNameName());
                                userCardLog.setSource(source);
                                userCardLog.setOperStatus(userCard.getBundLingType());
                                userCardLog.setCreateUser(userId);
                                userCardLog.setUpdateUser(userId);
                                userCardLog.setOperName(operName);
                                //保存卡的所属城市及卡的类型
                                userCardLog.setCardCityName(userCard.getCardCityName());
                                userCardLog.setCardType(userCard.getCardType());
                                //记录操作日志
                                bdService.addCardOperLog(userCardLog);

                            }
                        }

                }
                catch (Exception e) {
                    e.printStackTrace();
                    log.debug("用户卡片解绑 MerUserCardBDFacadeImpl 记录操作日志 throws:" + e);
                    // TODO: handle exception
                }

                if(rows == 0) {
                	response.setCode(ResponseCode.USERS_CARD_UNBIND_FAIL);
                } else {
                	response.setCode(ResponseCode.SUCCESS);
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                response.setCode(ResponseCode.SYSTEM_ERROR);
                log.debug("用户卡片解绑 MerUserCardBDFacadeImpl unBundlingCard throws:" + e);
            }

        }
        return response;
    }

    @Override
    public DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> findMerUserCardBDListByPage(MerUserCardBDDTOQuery cardBDFindDTO) {
        DodopalResponse<DodopalDataPage<MerUserCardBDDTO>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDDTO>>();
        MerUserCardBDQuery cardBdModel = new MerUserCardBDQuery();
        List<MerUserCardBDDTO> userDtoList = new ArrayList<MerUserCardBDDTO>();
        try {
            PropertyUtils.copyProperties(cardBdModel, cardBDFindDTO);
            DodopalDataPage<MerUserCardBD> datapage = bdService.findMerUserCardBDListByPage(cardBdModel);
            List<MerUserCardBD> bdCardList = datapage.getRecords();
            if (bdCardList.size() > 0) {
                for (MerUserCardBD tempbd : bdCardList) {
                    MerUserCardBDDTO userCardBDDTO = new MerUserCardBDDTO();
                    PropertyUtils.copyProperties(userCardBDDTO, tempbd);
                    userDtoList.add(userCardBDDTO);
                }
            }
            PageParameter page = DodopalDataPageUtil.convertPageInfo(datapage);
            DodopalDataPage<MerUserCardBDDTO> pages = new DodopalDataPage<MerUserCardBDDTO>(page, userDtoList);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
            log.debug("MerUserCardBDFacadeImpl call error", e);
        }
        return response;
    }

    //卡片操作日志查询
    public DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> findUserCardBDLogByPage(UserCardLogQueryDTO query) {
        DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>> response = new DodopalResponse<DodopalDataPage<MerUserCardBDLogDTO>>();
        UserCardLogQuery userCardLogQuery = new UserCardLogQuery();
        List<MerUserCardBDLogDTO> list = new ArrayList<MerUserCardBDLogDTO>();
        try {
            PropertyUtils.copyProperties(userCardLogQuery, query);
            DodopalDataPage<MerUserCardBDLog> dodopalResponse = bdService.findUserCardBDLogByPage(userCardLogQuery);
            if (dodopalResponse != null) {
                if (dodopalResponse.getRecords().size() > 0) {
                    List<MerUserCardBDLog> bdCardList = dodopalResponse.getRecords();
                    for (MerUserCardBDLog bdLog : bdCardList) {
                        MerUserCardBDLogDTO logDTO = new MerUserCardBDLogDTO();
                        PropertyUtils.copyProperties(logDTO, bdLog);
                        list.add(logDTO);
                    }
                }
            }

            PageParameter page = DodopalDataPageUtil.convertPageInfo(dodopalResponse);
            DodopalDataPage<MerUserCardBDLogDTO> pages = new DodopalDataPage<MerUserCardBDLogDTO>(page, list);
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(pages);
        }
        catch (Exception e) {
            response.setCode(ResponseCode.SYSTEM_ERROR);
            e.printStackTrace();
            log.debug("MerUserCardBDFacadeImpl call error", e);
        }
        return response;
    }
    
    @Override
    public List<MerUserCardBDLogDTO> getExportExcelUserCardLog(UserCardLogQueryDTO query) {
       List<MerUserCardBDLogDTO> response = new ArrayList<MerUserCardBDLogDTO>();
       UserCardLogQuery userCardLogQuery = new UserCardLogQuery();
        try {
            PropertyUtils.copyProperties(userCardLogQuery, query);
            List<MerUserCardBDLog> dodopalResponse = bdService.getExportExcelUserCardLog(userCardLogQuery);
            if (dodopalResponse.size() > 0) {
                for (MerUserCardBDLog bdLog : dodopalResponse) {
                    MerUserCardBDLogDTO logDTO = new MerUserCardBDLogDTO();
                    PropertyUtils.copyProperties(logDTO, bdLog);
                    response.add(logDTO);
                }
            }
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.debug("MerUserCardBDFacadeImpl call error", e);
        }
        return response;
    }
    

    @Override
    public DodopalResponse<Boolean> editUserCard(MerUserCardBDDTO cardDTO) {
        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            int num = 0;
            MerUserCardBD card = new MerUserCardBD();
            PropertyUtils.copyProperties(card, cardDTO);
            num = bdService.editUserCard(card);
            if (num > 0) {
                response.setCode(ResponseCode.SUCCESS);
                response.setResponseEntity(true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            response.setResponseEntity(false);
            // TODO: handle exception
        }

        return response;
    }

    public DodopalResponse<MerUserCardBDDTO> findMerUserCardBDById(String id) {
        DodopalResponse<MerUserCardBDDTO> response = new DodopalResponse<MerUserCardBDDTO>();
        MerUserCardBDDTO merUserCardBDDTO = new MerUserCardBDDTO();
        try {
            MerUserCardBD merUserCardBD = bdService.findMerUserCardById(id);
            if (merUserCardBD != null) {
                PropertyUtils.copyProperties(merUserCardBDDTO, merUserCardBD);
            }
            response.setCode(ResponseCode.SUCCESS);
            response.setResponseEntity(merUserCardBDDTO);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
            // TODO: handle exception
        }

        return response;
    }
    
    @Override
    public List<MerUserCardBDDTO> getExportExcelMerUserCardList(MerUserCardBDDTOQuery cardBDFindDTO) {
       List<MerUserCardBDDTO> response = new ArrayList<MerUserCardBDDTO>();
        MerUserCardBDQuery cardBdModel = new MerUserCardBDQuery();
        try {
            PropertyUtils.copyProperties(cardBdModel, cardBDFindDTO);
            List<MerUserCardBD> datapage = bdService.getExportExcelMerUserCardList(cardBdModel);
            if (datapage.size() > 0) {
                for (MerUserCardBD tempbd : datapage) {
                    MerUserCardBDDTO userCardBDDTO = new MerUserCardBDDTO();
                    PropertyUtils.copyProperties(userCardBDDTO, tempbd);
                    response.add(userCardBDDTO);
                }
            }
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.debug("MerUserCardBDFacadeImpl call error", e);
        }
        return response;
    }

    // 查询个人卡片充值和消费信息（分页） by Mika
	public DodopalResponse<DodopalDataPage<UserCardRecordDTO>> findUserCardRecordByPage(UserCardRecordQueryDTO query) {
		
		DodopalResponse<DodopalDataPage<UserCardRecordDTO>> response = new DodopalResponse<DodopalDataPage<UserCardRecordDTO>>();
		UserCardRecordQuery userCardRecordModel = new UserCardRecordQuery();
		List<UserCardRecordDTO> userCardRecordDTOList = new ArrayList<UserCardRecordDTO>();
		try {
			PropertyUtils.copyProperties(userCardRecordModel, query);
			DodopalDataPage<UserCardRecord> datapage = bdService.findUserCardRecordByPage(userCardRecordModel);
			List<UserCardRecord> userCardRecordList = datapage.getRecords();
			if(userCardRecordList.size() > 0) {
				for(UserCardRecord temp : userCardRecordList) {
					UserCardRecordDTO userCardRecordDTO = new UserCardRecordDTO();
					PropertyUtils.copyProperties(userCardRecordDTO, temp);
					userCardRecordDTOList.add(userCardRecordDTO);
				}
			}
			PageParameter page = DodopalDataPageUtil.convertPageInfo(datapage);
			DodopalDataPage<UserCardRecordDTO> pages = new DodopalDataPage<UserCardRecordDTO>(page, userCardRecordDTOList);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(pages);
		}catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
			e.printStackTrace();
			log.debug("MerUserCardBDFacadeImpl call error", e);
		}
		return response;
	}

	// 根据充值还是消费的类型 和 单号 查询一单交易详情  by Mika
	public DodopalResponse<UserCardRecordDTO> findCardRecordInfoByTypeOrderNum(String type, String orderNum) {
		DodopalResponse<UserCardRecordDTO> response = new DodopalResponse<UserCardRecordDTO>();
		UserCardRecordDTO userCardRecordDTO = new UserCardRecordDTO();
		try {
			UserCardRecord userCardRecord = new UserCardRecord();
			if("CZ".equalsIgnoreCase(type)) {
				userCardRecord = bdService.findCardCZRecordInfoByOrderNum(orderNum);
			} else if("XF".equalsIgnoreCase(orderNum)) {
				userCardRecord = bdService.findCardXFRecordInfoByOrderNum(orderNum);
			}
			PropertyUtils.copyProperties(userCardRecordDTO, userCardRecord);
			response.setCode(ResponseCode.SUCCESS);
			response.setResponseEntity(userCardRecordDTO);
		}catch(Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

}
