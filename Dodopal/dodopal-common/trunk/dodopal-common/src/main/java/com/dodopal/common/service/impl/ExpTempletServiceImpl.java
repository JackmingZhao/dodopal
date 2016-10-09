package com.dodopal.common.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.dao.ExpTempletMapper;
import com.dodopal.common.model.ExpTemplet;
import com.dodopal.common.service.ExpTempletService;

@Service
public class ExpTempletServiceImpl implements ExpTempletService {

	@Autowired
	private ExpTempletMapper expTempletMapper;

	public List<ExpTemplet> findExpSelItem(String templetCode) {

		return expTempletMapper.findExpTempletsByTempletCode(templetCode);
	}

	public String[] findExpSelNameArrByIndexes(String[] indexArr, String typeSelStr) {

		List<ExpTemplet> exptemp = expTempletMapper.findExpTempNameArrByIndex(indexArr, typeSelStr);

		String[] names = new String[indexArr.length];
		for (int i = 0; i < indexArr.length; i++) {
			for (int j = 0; j < exptemp.size(); j++) {
				if (indexArr[i].equals(exptemp.get(j).getPropertyCode())) {
					names[i] = exptemp.get(j).getPropertyName();
				}
			}
		}
		return names;
	}

	public Map<String, String> getCloName(String[] index, String typeSelStr) {
		List<ExpTemplet> exptemp = null;
		if(StringUtils.isNotBlank(typeSelStr) && typeSelStr.indexOf(",") > 0){
			exptemp = expTempletMapper.findExpTempNameArrByIndexs(index, typeSelStr.split(","));
		}else{
			exptemp = expTempletMapper.findExpTempNameArrByIndex(index, typeSelStr);
		}
		Map<String, String> indexAndName = new LinkedHashMap<String, String>();
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < exptemp.size(); j++) {
				if (index[i].equals(exptemp.get(j).getPropertyCode())) {
					indexAndName.put(exptemp.get(j).getPropertyCode(), exptemp.get(j).getPropertyName());
				}
			}
		}
		return indexAndName;
	}

	@Override
	public List<ExpTemplet> findExpTempletsByTempletCodes(String[] temCodes) {
		return expTempletMapper.findExpTempletsByTempletCodes(temCodes);
	}

	@Override
	public String[] findExpTempNameArrByIndexs(String[] temCodes, String typeSelStr) {
		List<ExpTemplet> exptemp = expTempletMapper.findExpTempNameArrByIndexs(temCodes, typeSelStr.split(","));

		String[] names = new String[temCodes.length];
		for (int i = 0; i < temCodes.length; i++) {
			for (int j = 0; j < exptemp.size(); j++) {
				if (temCodes[i].equals(exptemp.get(j).getPropertyCode())) {
					names[i] = exptemp.get(j).getPropertyName();
				}
			}
		}
		return names;
	}

}
