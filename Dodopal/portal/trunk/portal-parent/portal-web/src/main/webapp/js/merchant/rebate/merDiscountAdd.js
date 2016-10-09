$(function() {
	findDirectMer('');
	initDiscount();
});


function findDirectMer(merName) {
	ddpAjaxCall({
		url : "findDirectMer",
		data : merName,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
				$('#dirMerShow tbody').empty();
				if (data.responseEntity != null
						&& data.responseEntity.length > 0) {
					$(data.responseEntity).each(
							function(i, v) {
								html = '<tr>';
								html += '<td class="taLeft">';
								html += '<label><input type="checkbox" id="'
										+ v.merCode + '" name="' + v.merName
										+ '" value="' + v.merType + '" />'
										+ v.merName + '</label>';
								html += '</td>';
								html += '<td>' + v.merMoney + '</td>';
								html += '</tr>';
								$('#dirMerShow').append(html);
							})

				}
				
			} else {
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
			}
		}
	})
}

function initDiscount() {
	$("#discountAdd").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#discountAdd").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 1);
	});
	$("#discountAdd").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
	
	$("#sortAdd").bind('keyup', function() {
		clearNoNum1($(this));
	});

}


// 模糊查询 直营网点
function queryDiscountPage() {
	var merName = escapePeculiar($.trim($('#childMerQuery').val()));
	findDirectMer(merName);
}

// 新增折扣 选择直营网点
function transferPopclo(obj) {
	var inputName = '';
	childMerAr = new Array();
	var tbodyObj = $("#transferPage");
	$("table :checkbox").each(function(key, value) {
		if ($(value).prop('checked')) {

			var merCode = $(value).prop('id');
			var merType = $(value).val();
			var merName = $(value).prop('name');
			inputName += merName + ',';
			var transferMap = {
				merType : merType,
				merCode : merCode,
				merName : merName
			};
			childMerAr.push(transferMap);
		}
	})
	if (inputName.length > 0) {
		inputName = inputName.substring(0, inputName.length - 1);
		$(obj).closest('.pop-win').hide();
		$('#collections').val(inputName);
	} else {
		 $.messagerBox({type: 'warn', title:"信息提示", msg: "请选择直营网点"});
		return;
	}
	
}

function saveDiscount() {
    var merType = $('#merType').val();
	var directMer = childMerAr;
	var sort = $('#sortAdd').val();
	var discount = $('#discountAdd').val();
	if(discount=='0' || discount=='0.0'|| discount==null || discount==''){
		$.messagerBox({
			type : 'warn',
			title : "信息提示",
			msg : '折扣不能为空'
		});
		return;
	}
	if(sort==null || sort==''){
		$.messagerBox({
			type : 'warn',
			title : "信息提示",
			msg : '排序号不能为空'
		});
		return;
	}
	
	if(merType=='12'){
	if(directMer && directMer.length > 0){
	}else{
		$.messagerBox({
			type : 'warn',
			title : "信息提示",
			msg : '请选择直营网点'
		});
		return;
	}}else{}

	var merDiscountAdd = {
		directMer : directMer,
		sort : sort,
		discount : discount,
		activateFlag : $('input[name=a1]:checked').val()
	}

	ddpAjaxCall({
		url : "saveMerDiscount",
		data : merDiscountAdd,
		successFn : function(data) {
			var html = '';
			if (data.code == "000000") {
				var windowReload = function() {
					window.location.href=baseUrl+"/merchant/merDiscount";
				}
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : "添加成功",
					confirmOnClick : windowReload
				});

			} else {
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
			}
		}
	})

}


function btnReturn(){
	window.location.href=baseUrl+"/merchant/merDiscount";
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
		if (text.length >= 1 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 1)));
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
		if (text01.length > 1) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 3) {
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
		if (text.length >= 1 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 1)));
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
		if (text01.length > 1) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length >3) {
			text = text.substring(0, text.length - 1);
		}
		obj.val(text);
	}
	
}

function clearNoNum1(obj) { 
	var rate = obj.val();
	rate = rate.replace(/\D/g ,"");  //清除“数字”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	var text = rate;
	obj.val(rate);
	if (text.indexOf(".") < 0) {
		if (text.length >5) {
			obj.val(text.substring(0,5));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		obj.val(text01);
	}
}
