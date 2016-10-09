
function ddpAjaxSingleParamCall(options) {
	var defaults = {
			url : null,
			type : "POST",
			contentType : "application/text; charset=utf-8",
			dataType : "json",
			data : null,
			successFn : null,
			failureFn : null,
			completeFn : null,
			async:true
		};
		options = $.extend({}, defaults, options);
		
		$.ajax({
			async : options.async,
			type : options.type,
			url : options.url,
			contentType : options.contentType,
			dataType : options.dataType,
			data : options.data,
			success : function(data, status) {
				if(typeof(data) != 'undefined' && data.code == '999989') {
					window.location.href=$.base;
				} else {
					if ($.isFunction(options.successFn)) {
		        		options.successFn(data);
		        	}
				}
			},
			failure : function(errMsg) {
				if ($.isFunction(options.failureFn)) {
	        		options.failureFn();
	        	}
			},
			error : function(a) {
				alert('error');
			},
	        complete: function(XMLHttpRequest, textStatus) {
	        	if ($.isFunction(options.completeFn)) {
	        		options.completeFn();
	        	}
		    }
 		});
}

function ddpAjaxCall(options) {
	var defaults = {
		url : null,
		type : "POST",
		async : true,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : null,
		successFn : null,
		failureFn : null,
		completeFn : null
	};
	options = $.extend({}, defaults, options);
	
	if(options.data == null) {
		$.ajax({
			async : options.async,
			type : options.type,
			url : options.url,
			contentType : options.contentType,
			dataType : options.dataType,
			success : function(data, status) {
				if(typeof(data) != 'undefined' && data.code == '999989') {
					window.location.href=$.base;
				} else {
					if ($.isFunction(options.successFn)) {
		        		options.successFn(data);
		        	}
				}
			},
			failure : function(errMsg) {
				if ($.isFunction(options.failureFn)) {
	        		options.failureFn();
	        	}
			},
	        complete: function(XMLHttpRequest, textStatus) {
	        	if ($.isFunction(options.completeFn)) {
	        		options.completeFn();
	        	}
		    }
		});
	} else {
		$.ajax({
			async : options.async,
			type : options.type,
			url : options.url,
			contentType : options.contentType,
			dataType : options.dataType,
			data : JSON.stringify(options.data),
			success : function(data, status) {
				if(typeof(data) != 'undefined' && data.code == '999989') {
					window.location.href=$.base;
				} else {
					if ($.isFunction(options.successFn)) {
		        		options.successFn(data);
		        	}
				}
			},
			failure : function(errMsg) {
				if ($.isFunction(options.failureFn)) {
	        		options.failureFn();
	        	}
			},
	        complete: function(XMLHttpRequest, textStatus) {
	        	if ($.isFunction(options.completeFn)) {
	        		options.completeFn();
	        	}
		    }
		});
	}
}

function reLoadComboboxData(combobox, data) {
	if(combobox) {
		combobox.combobox('clear');
		combobox.combobox('loadData', data);
	}
}

function clearAllCombobox(id) {
	var combobox = $('#' + id).find('.easyui-combobox');
	$.each(combobox, function(index, element) {
		$(element).combobox('select', '');
	});
}

// 匹配用户输入元素:<input>, <textarea>, <select>和<button>( jQuery的扩展)
function clearAllText(id) {
	destroyValidate(id);
	var texts = $('#' + id +' :input');	
	$.each(texts, function(index, element) {	
		if($(element).attr('type') == 'text' || $(element).attr('type') == 'hidden') {
			$(element).val('');
		} 
	});
	var selectEl = $('#' + id).find("select");
	$.each(selectEl, function(index, element) {			
		if($(element).attr("id").indexOf("city_")==0){
			$(element).combobox('clear').combobox('loadData', '[{"id":"", "name":""}');;
		}else{	
			$(element).combobox('clear');
			var selectVal = $(element).find("option:selected").val();
			var selectText = $(element).find("option:selected").text();		
			if(typeof(selectVal) == "undefined"){			
				selectVal = "";
				selectText = "--请选择--";
			}
			$(element).next("span").find("input.combo-value").val(selectVal);
			$(element).next("span").find("input.combo-text").val(selectText);
		}
	});
}

function loadJsonData2Page(targetElement, jsonData) {
	if(jsonData && jsonData != null) {
		$.each(jsonData, function(na, ival) {
			var $input = $('#' + targetElement).find("input[name='" + na + "']");
			if($input) {
				if($input.attr('type') == 'text' || $input.attr('type') == 'hidden') {
					$input.val(ival);
				} else if($input.attr('type') == 'checkbox') {
					if(ival == '是' || ival == 'true') {
						$input.attr('checked', "checked");
					} else {
						$input.removeAttr("checked");
					}
				}
			} 
			// TODO find a good way for textarea
			var $textarea = $('#' + targetElement).find("textarea[name='" + na + "']");
			if($textarea) {
				$textarea.val(ival);
			}
		});
	}
}

function loadJsonData2ViewPage(targetElement, data, callbackFn) {
	if(data.code == "000000") {
		var jsonData = data.responseEntity;
		if(jsonData && jsonData != null) {
			$.each(jsonData, function(na, ival) {
				var $view = $('#' + targetElement).find("[id='view_" + na + "']");
				if($view) {
					if(na == 'createDate' || na == 'updateDate') {
						ival = ddpDateFormatter(ival);
					}
					if (na == 'activate') {
						if (ival == '0') {
							ival = '启用'
						} else if (ival == '1') {
							ival = '停用'
						}
					}
					$view.html(ival);
				} 
			});
			if ($.isFunction(callbackFn)) {
				callbackFn(jsonData);
			}
		}
	} else {
		if(isNotBlank(data.message)) {
			msgShow(systemWarnLabel, data.message, 'warning');
		} else {
			msgShow(systemWarnLabel, "错误码：" + data.code, 'warning');
		}
	}
}

//添加参数，对该参数对应的属性进行特殊字符格式化
function loadJsonData2ViewPageWithHtmlFormat(targetElement, data, formatKeys, callbackFn) {
	if(data.code == "000000") {
		var jsonData = data.responseEntity;
		if(jsonData && jsonData != null) {
			$.each(jsonData, function(na, ival) {
				var $view = $('#' + targetElement).find("[id='view_" + na + "']");
				if($view) {
					if(na == 'createDate' || na == 'updateDate') {
						ival = ddpDateFormatter(ival);
					}
					if (na == 'activate') {
						if (ival == '0') {
							ival = '启用'
						} else if (ival == '1') {
							ival = '停用'
						}
					}
					if(isInArray(na,formatKeys)){
						ival = htmlTagFormat(ival);
					}
					$view.html(ival);
				} 
			});
			if ($.isFunction(callbackFn)) {
				callbackFn(jsonData);
			}
		}
	} else {
		if(isNotBlank(data.message)) {
			msgShow(systemWarnLabel, data.message, 'warning');
		} else {
			msgShow(systemWarnLabel, "错误码：" + data.code, 'warning');
		}
	}
}

function loadDdics(ddic, afterFoundFn) {
	ddpAjaxCall({
		url : $.base + "/basic/ddicMgmt/findDdicByCategoryCode",
		data : ddic.categoryCode,
		successFn : afterFoundFn
	});
}


function loadPosCompany(queryData, afterFoundFn) {
	ddpAjaxCall({
		url : $.base + "/basic/pos/loadPosCompany",
		data : queryData,
		successFn : afterFoundFn
	});
}

function loadPosType(queryData, afterFoundFn) {
	ddpAjaxCall({
		url : $.base + "/basic/pos/loadPosType",
		data : queryData,
		successFn : afterFoundFn
	});
}

function isNotSelected(value) {
	if(isBlank(value) || value == 'sel') {
		return true;
	} else {
		return false;
	}
}

function getTrimValue(value) {
	if (isNotBlank(value)) {
		return $.trim(value);
	} else {
		return value;
	}
}

function isBlank(value) {
	if(value == null || value == 'null' || $.trim(value) == ''||typeof(value) == "undefined" ) {
		return true;
	} else {
		return false;
	}
}

function isNotBlank(value) {
	return !isBlank(value);
}

function addTipsOption(data) {
	if(isNotBlank(data)) {
		data.unshift({code:'',name:'--请选择--'});
	}
}

function isValidate(id) {
	var isValid = true;
	if(isNotBlank(id)) {
		var validationField = $('#' + id).find('.easyui-validatebox');
		$.each(validationField, function(index, element) {
			if (!$(element).validatebox('isValid')) {
				isValid = false;
				return false;
			}
		});
	}
	return isValid;
}


function destroyValidate(id) {
	if(isNotBlank(id)) {
		var validationField = $('#' + id).find('.easyui-validatebox');
		$.each(validationField, function(index, element) {
			$(element).removeClass('validatebox-invalid');
		});
	}
}


function loadCombobox(data, comboboxId) {
	if (data.code == "000000") {
		if(isNotBlank(data.responseEntity)) {
			data.responseEntity.unshift({id:'',name:'--请选择--'});
		}
		$('#' + comboboxId).combobox('clear').combobox('loadData', data.responseEntity).combobox('select','');
	} else {
		msgShow(systemWarnLabel, data.message, 'warning');
	}
}

function handleResponse(data, callbackFn) {
	if(data.code == "000000") {
		if ($.isFunction(callbackFn)) {
			callbackFn();
		}
	} else {
		if(isNotBlank(data.message)) {
			msgShow(systemWarnLabel, data.message, 'warning');
		} else {
			msgShow(systemWarnLabel, "错误码：" + data.code, 'warning');
		}
	}
}

function htmlTagFormat(value){
	if(typeof value === 'string' && isNotBlank(value)){
		return value.replace(/>/g, "&gt;").replace(/</g,"&lt;");
	}
	return "";
}

/**
 * 解决ie8及以下maxlength失效
 * 
 * s:this
 * length:所限制的长度
 */
function checkMaxlength(s,length){
	if (s.value.length>=length){
		if(event.keyCode!=8
				&&event.keyCode!=46
				&&event.keyCode!=40
				&&event.keyCode!=39
				&&event.keyCode!=38
				&&event.keyCode!=37)
		{
			event.returnValue=false
		}
	}
}

function generateResponseMsg(data) {
	if(typeof(data) != 'undefined') {
		if(isNotBlank(data.message)) {
			return data.message;
		} else {
			return "错误码：" + data.code;
		}
	}

}

function isInArray(search,array){
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}

function isStrNum(s) {
	if (s != null) {
		var r, re;
		re = /\d*/i; //\d表示数字,*表示匹配多个数字
		r = s.match(re);
		return (r == s) ? true : false;
	}
	return false;
}


/** 查询 % 和 _ 添加转义字符 */
function escapePeculiar(obj) {	
	if (isBlank(obj)){	
		return obj;
	}	
	return obj.replace(/\//g,"//").replace(/%/g,"/%").replace(/_/g,"/_");
}

function closeWebPage(){
	 if (navigator.userAgent.indexOf("MSIE") > 0) {
	  if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
	   window.opener = null;
	   window.close();
	  } else {
	   window.open('', '_top');
	   window.top.close();
	  }
	 }
	 else if (navigator.userAgent.indexOf("Firefox") > 0) {
	  window.location.href = 'about:blank ';
	 } else {
	  window.opener = null;
	  window.open('', '_self', '');
	  window.close();
	 }
}

/** EXCEL导出方法 
 * fnName:后台导出方法
 * formId:查询条件formId
 * */
function exportExcel(fnName, formId) {
	$.fileDownload(fnName, {
		data: $('#'+formId).serialize(),
		failCallback: function(data) {
			// IE8下会将返回内容添加<pre></pre>标签，这里先判断截取一下
			var start = data.indexOf(">");
          	if (start != -1) {
				var end = data.indexOf("<", start + 1);
				if (end != -1) {
					data = data.substring(start + 1, end);
				}
			}
			var response = JSON.parse(data);
			if(typeof msgShow != "undefined") {
				msgShow(systemWarnLabel, response.message, 'warning');
			} else if(  typeof $.messagerBox != "undefined") {
				$.messagerBox({type:'warn', title:"警告", msg: response.message});
			}
		}
	});
}

/*校验金额*/
function notNumber(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,7)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 8) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 10) {
			text = "";
		}
		obj.val(text);
	}
}

//自动分配额度阀值判断  整数6位，小数2位
function clearAutoLimitThresholdNoNum(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 7  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,6)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 6) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 9) {
			text = "";
		}
		obj.val(text);
	}
}
// 额度阀值后小数点后几位  整数6位，小数2位
function clearlimitThresholdNoNum(obj) { 
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 7  && textChar !=".") {
			obj.val(parseFloat(text.substring(0,6)));  
		}
	} else {
		var text01 = text.substring(0,text.indexOf("."));
		var text02 = text.substring(text.indexOf("."),text.length);
		if (text01.length > 6) {
			text01 = text01.substring(0,text01.length-1);
		}
		var text = text01+text02;
		if (text.length > 9) {
			text = "";
		}
		obj.val(text);
	}
}