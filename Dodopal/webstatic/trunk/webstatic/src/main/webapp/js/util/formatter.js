function cellDateFormatter(cellval, el, rowData) {
	if (cellval != null && $.trim(cellval) != '') {
		return formatDate(cellval, dateformatterStr);
	} else {
		return '';
	}
}

function booleanFieldFormatter(cellval, el, rowData) {
	if (cellval == true || cellval == 'true') {
		return '是';
	} else {
		return '否';
	}
}

function activateFormatter(cellval, el, rowData) {
	if (cellval == 0 || cellval == '0') {
		return '启用';
	} else {
		return '停用';
	}
}

function bindFormatter(cellval, el, rowData) {
	if (cellval == 0 || cellval == '0') {
		return '绑定';
	} else {
		return '未绑定';
	}
}

function posStatusfomatter(cellval, el, rowData) {
	if (cellval == '0') {
		return '启用';
	} else if (cellval == '1') {
		return '停用';
	} else if (cellval == '2') {
		return '作废';
	} else if (cellval == '3') {
		return '消费封锁';
	} else if (cellval == '4') {
		return '充值封锁';
	} else {
		return cellval;
	}
}

function htmlFormatter(cellval, el, rowData) {
	if (cellval != null && $.trim(cellval) != '') {
		return cellval.replace(/>/g, "&gt;").replace(/</g, "&lt;");
	} else {
		return '';
	}
}

function ddpDateFormatter(value) {
	if (isNotBlank(value)) {
		return formatDate(value, dateformatterStr);
	} else {
		return '';
	}
}
function ddpDateFormatter(value) {
	if (isNotBlank(value)) {
		return formatDate(value, dateformatterStr);
	} else {
		return '';
	}
}
/**
 * 由分转元
 */
function ddpMoneyFenToYuan(cellval, el, rowData) {
	if (cellval != null && $.trim(cellval) != '') {
		return cellval/100;
	} else {
		return '';
	}
}
/**
 * 转换为日期格式
 * add by Ding
 * yyyy-dd-MM
 */
function ddpDateForMatterForLine(cellval, el, rowData){
	//return formatDate(getDateFromFormat(cellval,"yyyyddMM"),"yyyy-dd-MM");
	if (cellval != null && $.trim(cellval) != '') {
		return cellval.substring("0","4")+"-"+ cellval.substring("4","6")+"-"+cellval.substring("6",cellval.length)
	} else {
		return '';
	}
	
}
// ******************企业信息管理code转name开始************************************************
// 商户类型
function merTypeFormatter(cellval, el, rowData) {
	if (cellval == 10) {
		return '代理商户';
	} else if (cellval == 11) {
		return '代理商户自有网点';
	} else if (cellval == 12) {
		return '连锁商户';
	} else if (cellval == 13) {
		return '连锁直营网点';
	} else if (cellval == 14) {
		return '连锁加盟网点';
	} else if (cellval == 15) {
		return '都都宝自有网点';
	} else if (cellval == 16) {
		return '集团商户';
	} else if (cellval == 18) {
		return '外接商户';
	} else {
		return cellval;
	}
}

// 商户分类
function merClassifyFormatter(cellval, el, rowData) {
	if (cellval == 0) {
		return '正式商户';
	} else if (cellval == 1) {
		return '测试商户';
	} else {
		return cellval;
	}
}
// 商户属性
function merPropertyFormatter(cellval, el, rowData) {
	if (cellval == 0) {
		return '标准商户';
	} else if (cellval == 1) {
		return '自定义商户';
	} else {
		return cellval;
	}
}
// 来源
function sourceFormatter(cellval, el, rowData) {
	if (cellval == 0) {
		return '门户';
	} else if (cellval == 1) {
		return 'OSS';
	} else {
		return cellval;
	}
}

// ******************企业信息管理code转name结束************************************************
