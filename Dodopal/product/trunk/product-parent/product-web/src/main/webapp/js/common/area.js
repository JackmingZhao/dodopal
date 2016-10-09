var area;
var provinceList;
var id;
/**
 * @param v 创建省份的select下拉框   v代表区域id
 * 使用方法
 * 		比如，要在td中创建省市下拉框 <td id="area">
 * 		在js初始化页面时加入createAreaSelect("area");
 * 
 * 如果要单独给select定义样式或灵活使用建议使用第二个构造方法
 * createAreaSelectWithSelectId(provinceId,cityId)
 * provinceId 为省份的select 的id
 * city       为城市的select 的id
 * 
 * resetArea(provinceId,cityId) 重置省市组件
 * 
 * setProvinceCity(provinceId,cityId,proVal,cityVal) 设置对应的省市值，参数1，2
 * 若是使用createAreaSelect(v)构造的省市，都传v （v为span或td等 的id）
 * 
 * getProvinceValue(v)  获取省份的code
 * 
 * getCityValue(v)      获取城市的code
 * 
 * getProvinceText(v)   获取省份的text
 * 
 * getCityText(v)       获取城市的text
 * 
 */
function createAreaSelect(v){
	if($("#province_"+id).length>0){
		$("#province_"+id).empty();	
	}
	id=v;
	if(area!=undefined){
		provinceList = area["0"];
		var html = "<select id='province_"+id+"' onchange='chooseCity(this.value)' city='city_"+id+"'>";
		html+= "<option selected value=''>省份</option>";
		$(provinceList).each(function(i,v){
			html+="<option value='"+v.cityCode+"'>";
			html+= v.cityName;
			html+="</option>";
		});
		html+="</select>";
		html+= "<select id='city_"+id+"'><option value=''>城市</option></select>";
		$("#"+id).append(html);
		selectUiInit();
		return ;
	}
	
	var map = {};
	ddpAjaxCall({
		url :$.base + "/components/getCityInfo",
		data : map,
		successFn : function(data) {
			if(data.code == "000000") {
				area=data.responseEntity;
				provinceList = area["0"];
				var html = "<select id='province_"+id+"' onchange='chooseCity(this.value)'>";
				html+= "<option selected value=''>省份</option>";
				$(provinceList).each(function(i,v){
					html+="<option value='"+v.cityCode+"'>";
					html+= v.cityName;
					html+="</option>";
				});
				html+="</select>";
				html+= "<select id='city_"+id+"'><option value=''>城市</option></select>";
				$("#"+id).append(html);
			}
			selectUiInit();
		}
	});
}

/**
 * @param th 二级联动
 */
function chooseCity(th){
	$("#province_"+id+" option[value='']").remove();
	$("#city_"+id).empty();
	var city = area[th];
	var html="";
	$(city).each(function(i,v){
		html+="<option value='"+v.cityCode+"'>";
		html+= v.cityName;
		html+="</option>";
	});
	$("#city_"+id).append(html);
}

function isEmptyObject(obj){
    for(var n in obj){return false} 
    return true; 
} 

/**
 * @param v select id
 * 传入省份select id 和城市的id 构造下拉
 * method回调方法  传入字符  "方法名()"
 */
function createAreaSelectWithSelectId(provinceId,cityId,method){
	$("#"+provinceId).empty();
	$("#"+cityId).empty();
	$("#"+provinceId).attr("city",cityId);
	
	if(method!=undefined){
		$("#"+provinceId).attr("method",method);
	}
	//$("#test").removeAttr("area");
	if(area!=undefined&&!isEmptyObject(area)){
		provinceList = area["0"];
		var html = "";
		html+= "<option selected value=''>省份</option>";
		$(provinceList).each(function(i,v){
			html+="<option value='"+v.cityCode+"'>";
			html+= v.cityName;
			html+="</option>";
		});
		$("#"+provinceId).append(html);
		var cityHtml= "<option value=''>城市</option>";
		$("#"+cityId).append(cityHtml);
		$("#"+provinceId).change(function (){
			if($(this).val()==""){
				return;
			}
			$("#"+provinceId+" option[value='']").remove();
			$("#"+cityId).empty();
			var city = area[$(this).val()];
			var cityHtml="";
			$(city).each(function(i,v){
				cityHtml+="<option value='"+v.cityCode+"'>";
				cityHtml+= v.cityName;
				cityHtml+="</option>";
			});
			$("#"+cityId).append(cityHtml);
		});
		selectUiInit();
		return;
	}
		var map = {};
		ddpAjaxCall({
			url :$.base + "/components/getCityInfo",
			data : map,
			successFn : function(data) {
				if(data.code == "000000") {
					area=data.responseEntity;
					provinceList = area["0"];
					var html = "";
					html+= "<option selected value=''>省份</option>";
					$(provinceList).each(function(i,v){
						html+="<option value='"+v.cityCode+"'>";
						html+= v.cityName;
						html+="</option>";
					});
					$("#"+provinceId).append(html);
					var cityHtml= "<option value=''>城市</option>";
					$("#"+cityId).append(cityHtml);
					$("#"+provinceId).change(function (){
						if($(this).val()==""){
							return;
						}
						$("#"+provinceId+" option[value='']").remove();
						$("#"+cityId).empty();
						var city = area[$(this).val()];
						var cityHtml="";
						$(city).each(function(i,v){
							cityHtml+="<option value='"+v.cityCode+"'>";
							cityHtml+= v.cityName;
							cityHtml+="</option>";
						});
						$("#"+cityId).append(cityHtml);
					});
					selectUiInit();
				} 
			}
		});
}

function changeCity(provinceId,cityId){
		var pvalue =$("#"+provinceId).val();
		if(pvalue==""){
			return;
		}
		if(document.getElementById(provinceId).getAttribute("method")!=null){
			 eval(document.getElementById(provinceId).getAttribute("method"));
		}
		$("#"+provinceId+" option[value='']").remove();
		$("#"+cityId).empty();
		var city = area[pvalue];
		var cityHtml="";
		$(city).each(function(i,v){
			cityHtml+="<option value='"+v.cityCode+"'>";
			cityHtml+= v.cityName;
			cityHtml+="</option>";
		});
		$("#"+cityId).append(cityHtml);
		selectUiInit();
}

/**
 * @param v 省框的id
 * @returns 获取省份的code
 */
function getProvinceValue(v){
	if($("#province_"+v).val()==null||$("#province_"+v).val()==undefined){
		return $("#"+v).val();
	}
	return $("#province_"+v).val();
}
/**
 * @param v 市框的id
 * @returns 获取城市的code
 */
function getCityValue(v){
	if($("#city_"+v).val()==null||$("#city_"+v).val()==undefined){
		return $("#"+v).val();
	}
	return $("#city_"+v).val();
}
/**
 * @param v
 * @returns 返回省份的text
 */
function getProvinceText(v){
	if($("#province_"+v+" option:selected").text()==""||$("#province_"+v+" option:selected").text()==null||$("#province_"+v+" option:selected").text()==undefined){
		return $("#"+v+" option:selected").text();
	}
	return $("#province_"+v+" option:selected").text();
}
/**
 * @param v
 * @returns  返回城市的text
 */
function getCityText(v){
	if($("#city_"+v+" option:selected").text()==""||$("#city_"+v+" option:selected").text()==null||$("#city_"+v+" option:selected").text()==undefined){
		return $("#"+v+" option:selected").text();
	}
	return $("#city_"+v+" option:selected").text();
}


/**
 * @param provinceId 省份id td id
 * @param cityId 省份id 或者 td id
 * @param proVal code
 * @param cityVal	code
 */
function setProvinceCity(provinceId,cityId,proVal,cityVal){
	if($("#province_"+provinceId).val()==null||$("#province_"+provinceId).val()==undefined){
		$("#"+provinceId+" option[value='']").remove();
		$("#"+cityId).empty();
		var city = area[proVal];
		var cityHtml="";
		$(city).each(function(i,v){
			if(v.cityCode==cityVal)
				cityHtml+="<option value='"+v.cityCode+"' selected>";
			else
				cityHtml+="<option value='"+v.cityCode+"'>";
			cityHtml+= v.cityName;
			cityHtml+="</option>";
		});
		$("#"+cityId).append(cityHtml);
		$("#"+provinceId+" option[value="+proVal+"]").attr("selected",true);
	}else{
		$("#province_"+provinceId+" option[value='']").remove();
		$("#city_"+cityId).empty();
		var city = area[proVal];
		var html="";
		$(city).each(function(i,v){
			if(v.cityCode==cityVal)
				cityHtml+="<option value='"+v.cityCode+"' selected>";
			else
				cityHtml+="<option value='"+v.cityCode+"'>";
			html+="<option value='"+v.cityCode+"'>";
			html+= v.cityName;
			html+="</option>";
		});
		$("#city_"+cityId).append(html);
		$("#province_"+provinceId+" option[value="+proVal+"]").attr("selected",true); 
	}
	selectUiInit();
}

/**
 * 重置区域组件
 * @param provinceId
 * @param cityId
 */
function resetArea(provinceId,cityId){
	if($("#province_"+provinceId).val()==null||$("#province_"+provinceId).val()==undefined){
		$("#"+provinceId+" option[value='']").remove();
		$("#"+cityId).empty();
		var proHtml = "<option value='' >省份</option>";
		var cityHtml = "<option value='' >城市</option>";
		$("#"+provinceId).append(proHtml);
		$("#"+cityId).append(cityHtml);
		$("#"+provinceId+" option[value='']").attr("selected",true);
	}else{
		$("#province_"+provinceId+" option[value='']").remove();
		$("#city_"+cityId).empty();
		var proHtml = "<option value='' >省份</option>";
		var cityHtml = "<option value='' >城市</option>";
		$("#province_"+provinceId).append(proHtml);
		$("#city_"+cityId).append(cityHtml);
		$("#province_"+provinceId+" option[value='']").attr("selected",true);
	}
	selectUiInit();
}
