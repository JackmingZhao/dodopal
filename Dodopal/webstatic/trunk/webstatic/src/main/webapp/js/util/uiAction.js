/**
 * 提供UI上操作行为，比如：取消、确定、block操作等等
 */

//弹出取消对话框，需要传人对话框组件的JQuery对象
function cancelAction(dialogCompont) {
	$.messager.confirm("确定",'确定放弃当前所做的操作?',function(r){  
	    if (r){
	    	$('#' + dialogCompont).hide().dialog('close');  
	    }  
	});
}

function closeAction(dialogCompont) {
	$('#' + dialogCompont).hide().dialog('close');
}

function closeAction(dialogCompont, blockElement) {
	if(blockElement) {
		$('#' + blockElement).unblock(); 
	}
	$('#' + dialogCompont).hide().dialog('close');
}

function blockElement(blockElement) {
	if(blockElement) {
		$('#' + blockElement).block({ overlayCSS: { backgroundColor: '#FFFFFF' } , message: null });
	}
}

// 显示等待效果
function startLoading() {
	$.blockUI({
		message : '<img src="../images/busy.gif" width="100%" height="100%"/>',
		css: { 
            border: 'none'
        }
	}); 
}

function endLoading() {
	$.unblockUI();
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	parent.msgShow(title, msgString, msgType);
}

function reLoadComboboxData(combobox, data) {
	if(combobox) {
		combobox.combobox('clear');
		combobox.combobox('loadData', data);
	}
}

function showDialog(dialogElement) {
	$('#'+dialogElement).show().dialog('open');
}

function hideDialog(dialogElement) {
	$('#'+dialogElement).hide().dialog('close');
}

function clearEasyUiCombobox(array) {
	$.each(array, function(index, element) {
		$('#' + element).combobox('clear');
	});
}

/**
 * hidden normally html elements
 */
function hiddenHtmlElement(elements) {
	$(elements).each(function(index, element){
		$('#' + element).hide();
	});
}

/**
 * enable normally html elements 
 */
function showHtmlElement(elements) {
	$(elements).each(function(index, element){
		$('#' + element).show();
	});
}

/**
 * disable normally html elements 
 */
function disableHtmlElement(elements) {
	$(elements).each(function(index, element){
		$('#' + element).attr('disabled', true);
	});
}

/**
 * enable normally html elements 
 */
function enableHtmlElement(elements) {
	$(elements).each(function(index, element){
		$('#' + element).attr('disabled', false);
	});
}

/**
 * disable easyui combobox componenet
 */
function disableEasyUICombobox(elements) {
	$(elements).each(function(index, element){
		$('#' + element).combobox('disable');
	});
}

/**
 * enable easyui combobox componenet
 */
function enableEasyUICombobox(elements) {
	$(elements).each(function(index, element){
		$('#' + element).combobox('enable');
	});
}

//set button visible(true, false), button is JQuery Object
function showButton(button, visible) {
	if(visible) {
		button.show();
	} else {
		button.hide();
	}
}

function resetEasyUIComboboxWidth(combobox, width) {
	combobox.combobox({
		width: width
	});
}

function setCheckbox(data) {
	$.each(data, function(index, element) {
		$('#' + element.id).attr('checked', 'checked');
	});
}

/**
 * 子菜单之间的跳转
 */
function openMenu(options) {
	var defaults = {
		menuCode : null,
		useGet : null,
		parameters : null,
		callback : null,
		operationType : null,
		message :null
	};
	options = $.extend({}, defaults, options);
	parent.showGlobalWaitingDialog(options.message);
	// open menu 	
	var tab = parent.getTabInfos(options.menuCode);
	parent.addTab(tab.title, tab.url, tab.icon, options.menuCode);
	
	if ($.isFunction(options.callback)) {
		var timer = null;
		var time = 0;
		timer = setInterval(function() {
			var menuStatus = parent.menuStatusMap.get(options.menuCode);
			if(menuStatus == constant.ready) {
				clearInterval(timer);
				options.callback(options);
				setTimeout(function(){
					parent.closeGlobalWaitingDialog();
				}, 100);
			} else {
				if(time > 60000) {
					// still not ready, popup warning message and notify user 
					msgShow(systemInfoLabel, '数据加载错误，请刷新后重试', 'warning');
					clearInterval(timer);
					closeGlobalWaitingDialog();
				} else {
					time = time + 500;
				}
			}
		}, 500);
	}
};

function createNonEditableCombobox(selector,data,addBlankItem,addAllItem,onSelect,widthSize){
	if(addBlankItem == true){
		data.unshift({id:' ',name:'无'});
	}
	
	if(addAllItem == true){
		data.unshift({id:'ALL',name:'所有'});
	}
	
	if(widthSize==null || widthSize=='')
		widthSize = globalComboboxDefaultWidth;
	
	var option = {
		valueField : "id",
		textField : 'name',
		editable : false,
		width: widthSize,
		data : data
	};
	
	if(onSelect){
		option.onSelect = onSelect;
	}
	
	$(selector).combobox(option);
}

function getComboboxValues(formValues){
	var clone = jQuery.extend({}, formValues);
	for (var key in clone) {
		clone[key] = $('#' + key).combobox('getValue');
	}
	return clone;
}

function setCombobxValues(formValues){
	for (var key in formValues) {
		$('#' + key).combobox('select',formValues[key]);
	}
}
