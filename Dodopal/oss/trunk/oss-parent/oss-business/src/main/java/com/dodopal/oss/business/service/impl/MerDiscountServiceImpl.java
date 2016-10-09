package com.dodopal.oss.business.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.dao.MerchantDiscountMapper;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;
import com.dodopal.oss.business.service.MerDiscountService;
import com.dodopal.oss.delegate.MerDiscountDelegate;

@Service
public class MerDiscountServiceImpl implements MerDiscountService{

	 private final static Logger log = LoggerFactory.getLogger(MerDiscountServiceImpl.class);
	    @Autowired
	    MerDiscountDelegate merDiscountDelegate;
	    
	    @Autowired
	    MerchantDiscountMapper merchantDiscountMapper;
	    
	    
	  //查询商户折扣（分页）
	    public DodopalResponse<DodopalDataPage<MerchantDiscount>> findMerDiscountByPage(MerchantDiscountQuery query) {
	        DodopalResponse<DodopalDataPage<MerchantDiscount>> response = new DodopalResponse<DodopalDataPage<MerchantDiscount>>();
	        List<MerchantDiscount> result = merchantDiscountMapper.findTranDiscountByPage(query);
	        DodopalDataPage<MerchantDiscount> pages = new DodopalDataPage<MerchantDiscount>(query.getPage(), result);
	        response.setResponseEntity(pages);
	        return response;
	    }
	    
	    //停用or启用
	    public DodopalResponse<Integer> startOrStopMerDiscount(String activate, List<String> ids) {
	        DodopalResponse<Integer> response = new DodopalResponse<Integer>();
	        try {
	            response = merDiscountDelegate.startOrStopMerDiscount(activate, ids);
	        }
	        catch (HessianRuntimeException e) {
	            log.debug("停用or启用  MerDiscountServiceImpl startOrStopMerDiscount call HessianRuntimeException error", e);
	            e.printStackTrace();
	            response.setCode(ResponseCode.HESSIAN_ERROR);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            log.error("停用or启用   MerDiscountServiceImpl startOrStopMerDiscount  throws e:" + e);
	            response.setCode(ResponseCode.SYSTEM_ERROR);
	        }
	        return response;
	    }


		//解除折扣
		public DodopalResponse<Integer> unbind(Map<String,Object> map) {
			DodopalResponse<Integer> response = new DodopalResponse<Integer>();
			int unbind = merchantDiscountMapper.unbind(map);
			if(unbind != 0){
				response.setCode(ResponseCode.SUCCESS);
			}
			return response;
		}

		//绑定折扣
		@Transactional
		public DodopalResponse<Integer> bind(String merId, List<String> ids) {
			DodopalResponse<Integer> response = new DodopalResponse<Integer>();
			String sqlOldBindDiscountLen = " select mt.discount_id as discountId from merchant_tran_discount mt where mt.mer_code in ( "
										 + " select m.mer_code as merCode from merchant m where m.id = '"+merId+"') ";
			List<String> bindedList = merchantDiscountMapper.findMerDiscountsByMerCode(sqlOldBindDiscountLen); 	// 从商户的ID得到merCode再得到以绑定的discountId
			if(bindedList.size() <= 0) { 	// 如果以前没有绑过折扣
				String ins = " insert into merchant_tran_discount (mer_code, discount_id) "
						   + " select (select mer_code from merchant where id = '" + merId + "') as mer_code, A.* "
						   + " from ( ";
				for(int i = 0; i < ids.size(); i++) {
					if(i == 0) {
						ins += " select '" + ids.get(0) + "' from  dual ";
					}else {
						ins += " union all select '"+ids.get(i)+"' from dual ";
					}
				}
				ins += " ) A ";
				merchantDiscountMapper.insertSql(ins);
				response.setCode(ResponseCode.SUCCESS);
				return response;
			}else { 	// 如果以前商户绑定过折扣, 则需要进行时间冲突检查
				String conflictResult = getConflictMsg(ids, bindedList); 	// 检查相同的商户新进的discountId的数组ids和以前绑定的bindedList的时间冲突
				if("0".equals(conflictResult)) { 	// 如果没有冲突
					String sql = " insert into merchant_tran_discount (mer_code, discount_id) "
							   + " select (select mer_code from merchant where id = '" + merId + "') as mer_code, A.* "
							   + " from ( ";
					for(int i = 0; i < ids.size(); i++) {
						if(i == 0) {
							sql += " select '" + ids.get(0) + "' from  dual ";
						}else {
							sql += " union all select '"+ids.get(i)+"' from dual ";
						}
					}
					sql += " ) A ";
					merchantDiscountMapper.insertSql(sql);
					response.setCode(ResponseCode.SUCCESS);
					return response;
				}else {
					response.setCode("*^_^*");
					response.setMessage(conflictResult);
					return response;
				}
			}
		}
		private String getConflictMsg(List<String> ids, List<String> bindedList) { 	// 如果冲突将会返回ids里的冲突的辣条的信息
			String sql = " select case when count(res.id1) > 0 then max(res.id1)||'splitMe'||max(res.id2) else '0' end from ( "
					+ " select tt.id1, tt.id2, tt.bothWeek, tt.bothBeginDate, tt.BothEndDate, "
					+ " (case when tt.diffDaysNum >= 7 then '1' " 	
					+ " when instr(tt.bothWeek, to_char(tt.bothBeginDate-1, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1' "
					+ " when instr(tt.bothWeek, to_char(tt.bothBeginDate,   'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1' "
					+ " when instr(tt.bothWeek, to_char(tt.bothBeginDate+1, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1' "
					+ " when instr(tt.bothWeek, to_char(tt.bothBeginDate+2, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1' "
					+ " when instr(tt.bothWeek, to_char(tt.bothBeginDate+3, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1' "
					+ " when instr(tt.bothWeek, to_char(tt.bothBeginDate+4, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1' "
					+ " else '0' end) as conflictFlag "
					+ " from (  "
					+ " select t1.id as id1, t2.id as id2, " 
					+ " (''  " 	// -- 提取重叠星期 
					+ " ||(select (case when instr(t1.week, '1') > 0 and instr(t2.week, '1') > 0 then '1' else '' end)  from dual) "
					+ " ||(select (case when instr(t1.week, '2') > 0 and instr(t2.week, '2') > 0 then '2' else '' end)  from dual) "
					+ " ||(select (case when instr(t1.week, '3') > 0 and instr(t2.week, '3') > 0 then '3' else '' end)  from dual) "
					+ " ||(select (case when instr(t1.week, '4') > 0 and instr(t2.week, '4') > 0 then '4' else '' end)  from dual) "
					+ " ||(select (case when instr(t1.week, '5') > 0 and instr(t2.week, '5') > 0 then '5' else '' end)  from dual) "
					+ " ||(select (case when instr(t1.week, '6') > 0 and instr(t2.week, '6') > 0 then '6' else '' end)  from dual) "
					+ " ||(select (case when instr(t1.week, '7') > 0 and instr(t2.week, '7') > 0 then '7' else '' end)  from dual) "
					+ " ) as bothWeek, "
					+ " (case when t1.begin_date > t2.begin_date then t1.begin_date else t2.begin_date end) as bothBeginDate, "
					+ " (case when t1.end_date   > t2.end_date   then t2.end_date else t1.end_date     end) as bothEndDate, "
					+ " (trunc( case when t1.end_date > t2.end_date then t2.end_date else t1.end_date end - "
					+ " case when t1.begin_date > t2.begin_date then t1.begin_date else t2.begin_date end )+1) as diffDaysNum "
					+ " from ( "
					+ " select e.id, e.begin_date, e.end_date, e.week, e.begin_time, e.end_time, e.discount_threshold, e.set_discount from tran_discount e where e.id in ( ";
					for(int i = 0; i < ids.size(); i++) {
						if(i == 0) {
							sql += " '" + ids.get(0) + "' ";
						}else {
							sql += ", '" + ids.get(i) + "' ";
						}
					}
					sql += " ) "
					+ " ) t1, ( "
					+ " select a.id, a.begin_date, a.end_date, a.week, a.begin_time, a.end_time, a.discount_threshold, a.set_discount from tran_discount a where a.id in ( ";
					for(int i = 0; i < ids.size(); i++) {
						if(i == 0) {
							sql += " '" + ids.get(0) + "' ";
						}else {
							sql += ", '" + ids.get(i) + "' ";
						}
					}
					sql += " ) "
					+ " or a.id in (";
					for(int i = 0; i < bindedList.size(); i++) {
						if(i == 0) {
							sql += " '" + bindedList.get(i) + "' ";
						}else {
							sql += " , '" + bindedList.get(i) + "' ";
						}
					}
					sql += ") "
					+ " ) t2 "
					+ " where 1 = 1 and t1.id <> t2.id "
					+ " and (  " 	// -- 是否有时间重叠
					//+ " (replace(t1.begin_time, ':', '.') between replace(t2.begin_time, ':', '.') and replace(t2.end_time, ':', '.')) "
					//+ " or (replace(t1.end_time,   ':', '.') between replace(t2.begin_time, ':', '.') and replace(t2.end_time, ':', '.'))    ) "
					+ "         t1.begin_time <= t2.end_time "
					+ "     and t1.end_time >= t2.begin_time ) "
					+ " and (  " 	// -- 是否有星期重叠
					+ "    (instr(t1.week, '1') > 0 and instr(t2.week, '1') > 0) "
					+ " or (instr(t1.week, '2') > 0 and instr(t2.week, '2') > 0) "
					+ " or (instr(t1.week, '3') > 0 and instr(t2.week, '3') > 0) "
					+ " or (instr(t1.week, '4') > 0 and instr(t2.week, '4') > 0) "
					+ " or (instr(t1.week, '5') > 0 and instr(t2.week, '5') > 0) "
					+ " or (instr(t1.week, '6') > 0 and instr(t2.week, '6') > 0) "
					+ " or (instr(t1.week, '7') > 0 and instr(t2.week, '7') > 0)    ) "
					+ " and (  " 	// -- 是否是日期重叠
//					+ " t1.begin_date between t2.begin_date and t2.end_date  "
//					+ " or t1.end_date   between t2.begin_date and t2.end_date      ) "            
					+ " t1.begin_date <= t2.end_date and t1.end_date >= t2.begin_date ) "
					+ " ) tt ) res where res.conflictFlag = '1' and rownum = 1 ";
			System.out.println("line 190: "+sql);
			String  result = merchantDiscountMapper.getConflictInfoList(sql);
			if("0".equals(result)) {
				return "0";
			}else {
				String[] d = result.split("splitMe");
				String sql1 = " select * from tran_discount where id = '" + d[0] + "' ";
				String sql2 = " select * from tran_discount where id = '" + d[1] + "' ";
				MerchantDiscount md1 = merchantDiscountMapper.getMerchantDiscountById(sql1);
				MerchantDiscount md2 = merchantDiscountMapper.getMerchantDiscountById(sql2);
				String msg = md1.getDiscountThreshold()/100.00+"折与"+md2.getDiscountThreshold()/100.00+"折有冲突!";
				return msg;
			}
		}

		//查询交易折扣
		public DodopalResponse<DodopalDataPage<MerchantDiscount>> findAllTranDiscountByPage(MerchantDiscountQuery query) { 	// 用商户id来过滤掉以经绑定的折扣
			DodopalResponse<DodopalDataPage<MerchantDiscount>> response = new DodopalResponse<DodopalDataPage<MerchantDiscount>>();
			
			List<MerchantDiscount> result = merchantDiscountMapper.findMerchantDiscountsByMerIdByPage(query);
			
	        DodopalDataPage<MerchantDiscount> pages = new DodopalDataPage<MerchantDiscount>(query.getPage(), result);
	        response.setResponseEntity(pages);
	        return response;
		}
}
