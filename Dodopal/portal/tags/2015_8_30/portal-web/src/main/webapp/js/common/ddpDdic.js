var ddicData;
/**
 * 方法简略
 * 
 * createDdicSelectWithSelectId(selectId,categoryCode) 创建
 * 
 * setDdicCode(selectId,ddicCode)   设置数据字典
 * 
 * getDdicValue(v)		获取数据字典的Code
 * 
 * getDdicText(v)       获取数据字典的Text
 * 
 * resetDdicSelect(v)   重置数据字典
 */

/**
 * @param selectId 数据字典select Id
 * @param categoryCode 数据字典大类Code
 */
function createDdicSelectWithSelectId(selectId,categoryCode){
	$("#"+selectId).empty();
	if(ddicData!=undefined){
		var ddicList = ddicData[categoryCode];
		var html = "";
		html+= "<option selected value=''>--请选择--</option>";
		$(ddicList).each(function(i,v){
			html+="<option value='"+v.code+"'>";
			html+= v.name;
			html+="</option>";
		});
		$("#"+selectId).append(html);
		selectUiInit();
		return;
	}
		var map = {};
		ddpAjaxCall({
			url :$.base + "/components/getDdicList",
			data : map,
			successFn : function(data) {
				if(data.code == "000000") {
					ddicData=data.responseEntity;
					var ddicList = ddicData[categoryCode];
					var html = "";
					html+= "<option selected value=''>－－ 请选择 －－</option>";
					$(ddicList).each(function(i,v){
						html+="<option value='"+v.code+"'>";
						html+= v.name;
						html+="</option>";
					});
					$("#"+selectId).append(html);
				} 
				selectUiInit();
			}
		});
}

function getDdicValue(v){
	return $("#"+v).val();
}

function getDdicText(v){
		return $("#"+v+" option:selected").text();
}
function setDdicCode(selectId,ddicCode){
		$("#"+selectId+" option[value="+ddicCode+"]").attr("selected",true);
		selectUiInit();
}

function resetDdicSelect(v){
	$("#"+v+" option[value='']").attr("selected",true);
	selectUiInit();
}
