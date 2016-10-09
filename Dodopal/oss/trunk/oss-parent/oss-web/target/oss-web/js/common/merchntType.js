function merPropertyClick(prop) {
	if (prop == 'customer') {
		findMerTypeFunInfo();
		$('#viewMerProperty').show();
		
		//showDialog('customerDialog');
		setTimeout(function() {showDialog('customerDialog')},100);
	}else if(prop=='editzdy'){
		findMerTypeFunInfo();
		$('#viewMerProperty').show();
		hideDialog('customerDialog');
	} else {
		hideDialog('customerDialog');
		$('#viewMerProperty').hide();
	}
}


//根据商户类型查询自定义商户功能
function findMerTypeFunInfo(){
	var param = $('#dataflag').attr('value');
	var merType = $('#merType').combobox('getValue');
	var merCode = $('#merCode').val();
	ddpAjaxCall({
		url : "findMerFuncitonInfoList?param="+param+"&merCode="+merCode,
		data : merType,
		successFn : createCustomerProperty
	});
}


function createCustomerProperty(data) {
	if(data.code == "000000") {
		$('#customerProperty').html('');
		var custProperty = data.responseEntity;
		if(custProperty == null) {
			return;
		}
		$('#customerProperty').tree({
			data : custProperty,
			lines : true,
			checkbox : true
		});
		$('#customerProperty').find(".tree-icon").removeClass();
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}


//-------------------------------------------商户编辑的时候重新加载子商户-----------------------------

function editmerPropertyClick(prop) {
	if (prop == 'customer') {
		findMerTypeFunInfo();
		$('#viewMerProperty').show();
		showDialog('customerDialog');
	} else {
		findMerTypeFunInfo();
		hideDialog('customerDialog');
		$('#viewMerProperty').hide();
	}
}


