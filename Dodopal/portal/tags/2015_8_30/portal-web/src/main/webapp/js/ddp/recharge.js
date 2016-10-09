$(function() {
	highlightTitle();
	findPayWay();
	findPayCommon();
	initMoney();
});

function initMoney() {
	$("#money").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#money").focus(function(){
		$('#msg').hide();
	})
	$("#money").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#money").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
}

function findPayWay() {
	ddpAjaxCall({
		url : "findPayWay",
		data : '',
		successFn : function(data) {
			if (data.code == "000000" && data.responseEntity != null) {
				loadPayWaySource(data.responseEntity);
			}else{
			}
		}
	})
}

function findPayCommon() {
	ddpAjaxCall({
		url : "findCommonPayWay",
		data : '',
		successFn : function(data) {
			if (data.code == "000000" && data.responseEntity != null) {
				loadPayWayCommonSource(data.responseEntity);
			}else{
			}
		}
	})
}

function loadPayWayCommonSource(PayWayBeanList) {
	if (PayWayBeanList != null && PayWayBeanList != '') {

		var merClassify = $("#merClassify").val();
		// 用户常用支付方式
		var html = '<p class="recharge_mode"><span>常用充值方式</span>（最多设置5种）：</p> <ul class="pay-way-list clearfix">';
		$(PayWayBeanList).each(function(i, v) {
			                //merClassify 0为正式商户 1为测试商户
							if(merClassify=='0'){
								// 3. 银行支付  2.在线支付  1.一卡通支付
							if (v.payType == '2' || v.payType == '3') {
								if(i=='0'){
									html += '<li><label><input type="radio" checked="checked" name="payId" value="'+ v.id+ '"/><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'	+ v.payName+ '"/></label></li>'
								}else{
									html += '<li><label><input type="radio" name="payId" value="'+ v.id+ '"/><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'	+ v.payName+ '"/></label></li>'
								}
								
							 }
							}else if(merClassify=='1'){
								if (v.payType == '2' || v.payType == '3' || v.payType=='1') {
									if(i=='0'){
										html += '<li><label><input type="radio" checked="checked" name="payId" value="'+ v.id+ '"/><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'+ v.payName+ '"/></label></li>'
									}else{
										html += '<li><label><input type="radio" name="payId" value="'+ v.id+ '"/><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '"  title="'+ v.payName+ '"/></label></li>'
									}
									
								}
							}
						})
		html += '</ul>';
		$('#payCommon').append(html);
	}
}

function loadPayWaySource(PayWayBeanList) {
	if (PayWayBeanList != null && PayWayBeanList != '') {
		// 银行支付
		var html3 = '<ul class="pay-way-list clearfix" style="display:none;" js="pay-list">';
		// 在线支付
		var html2 = '<ul class="pay-way-list clearfix" >';
		// 一卡通支付
		var html1 = '<ul class="pay-way-list clearfix" style="display:none;">';
		$(PayWayBeanList).each(function(i, v) {
							if (v.payType == '3') {
								html3 += '<li><label><input type="radio" name="payId" value="'+ v.id+ '"/><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></label></li>'}
							if (v.payType == '2') {
								html2 += '<li><label><input type="radio" name="payId" value="'+ v.id+ '" /><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></label></li>'}
							if (v.payType == '1') {
								html1 += '<li><label><input type="radio" name="payId" value="'+ v.id+ '" /><img src="../images/'+ v.payLogo+ '" alt="'+ v.payName+ '" title="'+ v.payName+ '"/></label></li>'}
						})
		html3 += '<li class="more-link only"><a onclick="moreShow();"><i class="i-more"></i>更多</a></li></ul>';
		html2 += '</ul>';
		html1 += '</ul>';
		$('#payMore').append(html2 + html3 + html1);
	}

}

/* 以下为校验费率js */
function clearNoNumOnBlur(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 7)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 7) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 10) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar == ".") {
				text = text.substring(0, text.length - 1);
			}
		}
		obj.val(text);
	}
}

function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
			+ startDec + "," + endDec + "})?$");
	posNeg = /^[+]?]*$/;

	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}

function clearNoNum(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 7 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 7)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 7) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 10) {
			text = "";
		}
		obj.val(text);
	}
}
