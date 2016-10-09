function createAreaComponent(elementName, needStreet) {
	var area = '';
	area += '<td colspan="4" id="addflag"><select id="province_' + elementName + '" />&nbsp;';
	area += '<select id="city_' + elementName + '" name="city_' + elementName + '"" />';
	if(typeof(needStreet) != "undefined" && needStreet == constant.createAreaWithStreet) {
		area += '&nbsp;&nbsp;<input id="street_' + elementName + '" type="text" style="width:260px;line-height: 18px;" maxlength="200"/>';
	}
	area += '</td>';
	return area;
}

function createAreaComponent2(elementName, needStreet) {
	var area = '';
	area += '<select id="province_' + elementName + '" />&nbsp;';
	area += '<select id="city_' + elementName + '" name="city_' + elementName + '" />';
	if(typeof(needStreet) != "undefined" && needStreet == constant.createAreaWithStreet) {
		area += '&nbsp;&nbsp;<input id="street_' + elementName + '" type="text" style="width:350px;" maxlength="200"/>';
	}
	return area;
}


function initAreaComponent(elementName, comboboxWidth) {
	$('#province_' + elementName).combobox({
		valueField : 'cityCode',
		textField : 'cityName',
		data : parent.areaData,
		editable : false,
		width : comboboxWidth,
		onSelect : function(record){
			$('#city_' + elementName).combobox('clear').combobox('loadData', record.subAreas);
		}
	});
	$('#city_' + elementName).combobox({
		valueField : 'cityCode',
		textField : 'cityName',
		editable : false,
		width : comboboxWidth
	});
}

function getCityCodeFromCompoent(elementName) {
	if(!isBlank($('#city_' + elementName).combobox('getValue'))){
		return $('#city_' + elementName).combobox('getValue');
	}
	return "";
}

function getCityNameFromCompoent(elementName) {
	if(!isBlank($('#city_' + elementName).combobox('getText'))){
		return $('#city_' + elementName).combobox('getText');
	}
	return "";
}

function getProvinceCodeFromCompoent(elementName) {
	if(!isBlank($('#province_' + elementName).combobox('getValue'))){
		return $('#province_' + elementName).combobox('getValue');
	}
	return "";
}


function getProvinceNameFromCompoent(elementName) {
	if(!isBlank($('#province_' + elementName).combobox('getText'))){
		return $('#province_' + elementName).combobox('getText');
	}
	return "";
}

function getStreetFromCompoent(elementName) {
	return $('#street_' + elementName).val();
}

// 重新加载区域组件
function setAreaComponent(elementName, province, city, street) {
	if(isNotBlank(province)) {
		$('#province_' + elementName).combobox('select', province);
	}
	
	if(isNotBlank(city)) {
		$('#city_' + elementName).combobox('select', city);
	}
	
	if(isNotBlank(street)) {
		$('#street_' + elementName).val(street);
	}
}

function startAreaComponent(elementName) {
	$('#province_' + elementName).combobox('enable');
	$('#city_' + elementName).combobox('enable');
}

//禁用省份城市
function diableAreaComponent(elementName) {
		$('#province_' + elementName).combobox('disable');
		$('#city_' + elementName).combobox('disable');
}

// 清空/重置 区域组件
function clearAreaComponent(elementName) {
	$('#province_' + elementName).combobox('clear');
	$('#city_' + elementName).combobox('clear').combobox('loadData', '[{"id":"", "name":""}');
	if(document.getElementById('street_' + elementName)) {
		$('#street_' + elementName).val('');
	}
}