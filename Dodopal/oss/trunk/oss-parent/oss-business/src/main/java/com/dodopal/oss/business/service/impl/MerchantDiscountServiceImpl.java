package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.oss.business.dao.MerchantDiscountMapper;
import com.dodopal.oss.business.model.DiscountMerchantInfo;
import com.dodopal.oss.business.model.MerchantDiscount;
import com.dodopal.oss.business.model.dto.MerchantDiscountQuery;
import com.dodopal.oss.business.model.dto.MerchantQuery;
import com.dodopal.oss.business.service.MerchantDiscountService;

@Service
public class MerchantDiscountServiceImpl implements MerchantDiscountService {
	
	@Autowired
	MerchantDiscountMapper merDiscountMapper;

	@Override
	public DodopalDataPage<MerchantDiscount> findMerchantDiscountsByUserDiscountByPage(MerchantDiscountQuery mdq) {
		List<MerchantDiscount> result = merDiscountMapper.findMerchantDiscountsByUserDiscountByPage(mdq);
		DodopalDataPage<MerchantDiscount> datapage = new DodopalDataPage<MerchantDiscount>(mdq.getPage(), result);
		return datapage;
	}

	@Override
	public DodopalDataPage<DiscountMerchantInfo> findMerchantsByDiscountIdByPage(MerchantQuery merchantQuery) {
		List<DiscountMerchantInfo> result = merDiscountMapper.findMerchantsByDiscountIdByPage(merchantQuery);
		DodopalDataPage<DiscountMerchantInfo> pages = new DodopalDataPage<DiscountMerchantInfo>(merchantQuery.getPage(), result);
		return pages;
	}

	@Override
	@Transactional
	public String saveOrUpdateMerDiscount(MerchantDiscount merDiscount) {
		String resultStr;
		if(DDPStringUtil.isNotPopulated(merDiscount.getId())) { 	// 新增
			String merDiscountId = merDiscountMapper.getMerDiscountId(); 	// 从虚拟表里得到自增主键
			merDiscount.setId(merDiscountId); 	// 设置主键
			if(merDiscount.getMerCodeArr().length <= 0) { 	// 当新增折扣不绑定商户时
				merDiscountMapper.insertMerDiscount(merDiscount); 	// 对于不绑定商户的折扣, 新增不检查时间冲突
				resultStr = "000000";
			}else { 	// 当新增折扣绑定商户时
				String conflictMerCode = getConflictMerCode(merDiscount); 	// 得到冲突折扣商户时间信息
				if("0".equals(conflictMerCode)) { 	// 如果没有时间段冲突
					merDiscountMapper.insertMerDiscount(merDiscount); 	// 增折扣
					merDiscountMapper.insertMerTranDiscount(merDiscount); 	// 增关联表
					resultStr = "000000";
				}else {
					resultStr = conflictMerCode;
				}
			}
		}else { 	// 更新
			if(merDiscount.getMerCodeArr().length <= 0) { 	// 更新不绑商户
				merDiscountMapper.updateMerDiscount(merDiscount); 	// 更新折扣
				merDiscountMapper.delMerTranDiscountByDiscountId(merDiscount.getId()); 	// 删关联表
				resultStr = "000000";
			}else {
				String discountId = merDiscount.getId(); 	// 得到折扣主键
				List<String> oldMerCodeList = merDiscountMapper.getMerCodeListByDiscountId(discountId); 	// 得到以前绑定的商户code的List
				if(oldMerCodeList.size() <= 0) { 	// 如果以前未绑定
					String conflictMerCode = getConflictMerCode(merDiscount); 	// 得到冲突折扣商户时间信息
					if("0".equals(conflictMerCode)) {
						merDiscountMapper.updateMerDiscount(merDiscount); 	// 更新折扣
						merDiscountMapper.insertMerTranDiscount(merDiscount);	// 插入关联表
						resultStr = "000000";
					}else {
						resultStr = conflictMerCode;
					}
				}else {
					String conflictMerCode = getConflictMerCode(merDiscount); 	// 得到冲突折扣商户时间信息
					if("0".equals(conflictMerCode)) { 	// vk js i ukpw
						merDiscountMapper.updateMerDiscount(merDiscount);
						merDiscountMapper.delMerTranDiscountByDiscountId(merDiscount.getId());
						merDiscountMapper.insertMerTranDiscount(merDiscount);
						resultStr = "000000"; 	// wq dd wqb 套了四层逻辑 @Mika
					}else {
						resultStr = conflictMerCode;
					}
				}
			}
		}
		return resultStr;
	}

	@Override
	public MerchantDiscount findDiscountById(String discountId) {
		return merDiscountMapper.findDiscountById(discountId);
	}

	@Override
	public List<DiscountMerchantInfo> findMerArrByDiscountId(String discountId) {
		return merDiscountMapper.findMerArrByDiscountId(discountId);
	}

	@Override
	public List<DiscountMerchantInfo> findMerInfoByIdArr(String[] merCodeArr) {
		return merDiscountMapper.findMerInfoByIdArr(merCodeArr);
	}

	@Override
	public String deleteMerDiscountByIds(String[] ids) {
		String sql = " delete from tran_discount where id in (";
		for(int i = 0; i < ids.length; i++) {
			if(i == 0) {
				sql += "'" + ids[i] + "'";
			}else{
				sql += ", '" + ids[i] + "'";
			}
		}
		sql += " )"; 
		merDiscountMapper.deleteMerDiscountByIds(sql);
		return CommonConstants.SUCCESS;
	}

	private String getConflictMerCode(MerchantDiscount merDiscount) {

		String[] merCodeArr = merDiscount.getMerCodeArr();
		
		String sql = " "
				+ " select case when count(res.id2) > 0 then max(res.id2) else '0' end "
				+ " from (  "
				+ "     select tt.id1, tt.id2, tt.bothWeek, tt.bothBeginDate, tt.BothEndDate, "  
				+ "            (case when tt.diffDaysNum >= 7 then '1' "  
				+ "                  when instr(tt.bothWeek, to_char(tt.bothBeginDate-1, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1'  "
				+ "                  when instr(tt.bothWeek, to_char(tt.bothBeginDate,   'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1'  "
				+ "                  when instr(tt.bothWeek, to_char(tt.bothBeginDate+1, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1'  "
				+ "                  when instr(tt.bothWeek, to_char(tt.bothBeginDate+2, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1'  "
				+ "                  when instr(tt.bothWeek, to_char(tt.bothBeginDate+3, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1'  "
				+ "                  when instr(tt.bothWeek, to_char(tt.bothBeginDate+4, 'd')||'') > 0 and tt.bothBeginDate <= tt.bothEndDate then '1'  else '0' end) as conflictFlag  "
				+ "     from (   "
				+ "         select t1.id as id1, t2.id as id2,  "
				+ "                (''   ||(select (case when instr(t1.week, '1') > 0 and instr(t2.week, '1') > 0 then '1' else '' end)  from dual)  "
				+ "                      ||(select (case when instr(t1.week, '2') > 0 and instr(t2.week, '2') > 0 then '2' else '' end)  from dual)  "
				+ "                      ||(select (case when instr(t1.week, '3') > 0 and instr(t2.week, '3') > 0 then '3' else '' end)  from dual)  "
				+ "                      ||(select (case when instr(t1.week, '4') > 0 and instr(t2.week, '4') > 0 then '4' else '' end)  from dual)  "
				+ "                      ||(select (case when instr(t1.week, '5') > 0 and instr(t2.week, '5') > 0 then '5' else '' end)  from dual)  "
				+ "                      ||(select (case when instr(t1.week, '6') > 0 and instr(t2.week, '6') > 0 then '6' else '' end)  from dual)  "
				+ "                      ||(select (case when instr(t1.week, '7') > 0 and instr(t2.week, '7') > 0 then '7' else '' end)  from dual)  ) as bothWeek,  "
				+ "                (case when t1.begin_date > t2.begin_date then t1.begin_date else t2.begin_date end) as bothBeginDate,  "
				+ "                (case when t1.end_date   > t2.end_date   then t2.end_date else t1.end_date     end) as bothEndDate,  "
				+ "                (trunc( case when t1.end_date > t2.end_date then t2.end_date else t1.end_date end -  "
				+ "                        case when t1.begin_date > t2.begin_date then t1.begin_date else t2.begin_date end )+1) as diffDaysNum  "
				+ "         from (  select "
				+ "                         '"+merDiscount.getId()+"' as id, to_date('"+DateUtils.dateToStr(merDiscount.getBeginDate())+"', 'yyyy-mm-dd') as begin_date, "
				+ "                         to_date('"+DateUtils.dateToStr(merDiscount.getEndDate())+"', 'yyyy-mm-dd') as end_date, '"+merDiscount.getWeek()+"' as week, "
				+ "							'"+merDiscount.getBeginTime()+"' as begin_time, '"+merDiscount.getEndTime()+"' as end_time,  "+merDiscount.getDiscountThreshold()+" as discount_threshold, "
				+ "                         "+merDiscount.getSetDiscount()+" as set_discount "
				+ "                 from dual "
				+ "				 ) t1, "
				+ "              (  select a.id, a.begin_date, a.end_date, a.week, a.begin_time, a.end_time, a.discount_threshold, a.set_discount "
				+ "                 from tran_discount a "
				+ "                 where a.id in (  select discount_id as id from merchant_tran_discount where mer_code in (";
		for(int i = 0; i < merCodeArr.length; i++) {
			if(i == 0) {
				sql += "'"+merCodeArr[0]+"'";
			}else {
				sql += ", '"+merCodeArr[i]+"'";
			}
		}
		sql	   += "								  )  )  "
				+ "                 union all select  "
				+ "                         '"+merDiscount.getId()+"' as id, to_date("+DateUtils.dateToStr(merDiscount.getBeginDate())+", 'yyyy-mm-dd') as begin_date, "
				+ "                         to_date("+DateUtils.dateToStr(merDiscount.getEndDate())+", 'yyyy-mm-dd') as end_date, '"+merDiscount.getWeek()+"' as week, "
				+ "							'"+merDiscount.getBeginTime()+"' as begin_time, '"+merDiscount.getEndTime()+"' as end_time,  "+merDiscount.getDiscountThreshold()+" as discount_threshold, "
				+ "                         "+merDiscount.getSetDiscount()+" as set_discount "
				+ "                 from dual "
				+ "              ) t2  "
				+ "         where 1 = 1 and t1.id <> t2.id  "
				+ "               and (           t1.begin_time <= t2.end_time      "
				+ "                           and t1.end_time >= t2.begin_time )  "
				+ "               and (      (instr(t1.week, '1') > 0 and instr(t2.week, '1') > 0)  "
				+ "                       or (instr(t1.week, '2') > 0 and instr(t2.week, '2') > 0)  "
				+ "                       or (instr(t1.week, '3') > 0 and instr(t2.week, '3') > 0)  "
				+ "                       or (instr(t1.week, '4') > 0 and instr(t2.week, '4') > 0)  "
				+ "                       or (instr(t1.week, '5') > 0 and instr(t2.week, '5') > 0)  "
				+ "                       or (instr(t1.week, '6') > 0 and instr(t2.week, '6') > 0)  "
				+ "                       or (instr(t1.week, '7') > 0 and instr(t2.week, '7') > 0)    )  "
				+ "               and (   t1.begin_date <= t2.end_date "
				+ "                   and t1.end_date >= t2.begin_date )  "
				+ "     ) tt "
				+ " ) res where res.conflictFlag = '1' and rownum = 1  ";

		String res = merDiscountMapper.getConflictInfoList(sql);
		if("0".equals(res)) {
			return "0";
		}else {
			String discountId = res;
			String mInfoSql = " select m.mer_name||'__'||td.discount_threshold as mdInfo "
							+ " from tran_discount td, merchant m "
							+ " where m.mer_code in ( "
							+ " select max(mt.mer_code) as mer_code  "
							+ " from merchant_tran_discount mt "
							+ " where 1 = 1  "
							+ " and mt.discount_id = '" + discountId + "' "
							+ " and mt.mer_code in (";
							for(int i = 0; i < merCodeArr.length; i++) {
								if(i == 0) {
									mInfoSql += "'" + merCodeArr[i] + "'";
								}else{
									mInfoSql += ", '" + merCodeArr[i] + "'";
								}
							}
			mInfoSql += " )) and td.id = '" + discountId + "' ";
			String mdInfo = merDiscountMapper.getConflictInfoList(mInfoSql);
			return mdInfo;
		}
	}
	
}
