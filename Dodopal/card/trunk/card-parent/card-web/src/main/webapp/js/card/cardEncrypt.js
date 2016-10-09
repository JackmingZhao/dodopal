function encryptMode(){
	var encryptName =  $.trim($('#encryptName').val());
	ddpAjaxCall({
		url : "encryptMode",
		data : encryptName,
		successFn : function(data) {
			if (data.code == "000000") {
				$('#decryptText').val(data.responseEntity)
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}


function decryptMode(){
	var decryptText = $('#decryptText').val();
	ddpAjaxCall({
		url : "decryptMode",
		data : decryptText ,
		successFn : function(data) {
			if (data.code == "000000") {
				$('#decryptName').val(data.responseEntity)
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}