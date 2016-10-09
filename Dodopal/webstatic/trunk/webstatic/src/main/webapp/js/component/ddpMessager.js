/**
 * ddp messager 消息提示框, 共分为3种, 确认,警告,错误
 * 
 * 使用前提:
 * 基于jquery-1.9.1, jquery.ui.widget
 * 
 * 使用方法分别为： 
 * 确认消息框：
 * var callbackFn = function(value){
 *		alert("hi I am callback param:[" + value + "]");
 *	}
 * $.messagerBox({type:"confirm", title:"确认", msg: "确认信息内容", confirmOnClick: callbackFn, param:"callbackparam",cancelOnClick:取消按钮回调});
 *
 * 
 * 警告消息框
 * $.messagerBox({type:'warn', title:"警告", msg: "警告信息内容",confirmOnClick: callbackFn});
 * 
 * 错误消息框
 * $.messagerBox({type: 'error', title:"错误", msg: "错误信息内容",confirmOnClick: callbackFn});
 * 
 * 
 */

$.widget('ui.messager', {
	options : {
		type : 'warn',
		confirmOnClick: function(){},
		param : ''
	},
	_create : function() {
		var _this = this;
		var errorBox = '<div class="pop-win" id="errorBox" style="display: none;">';
		errorBox += '<div class="bg-win"></div>';
		errorBox += '<div class="pop-bor"></div>';
		errorBox += '<div class="pop-box">';
		errorBox += '<h3 id="errorTitle">信息提示</h3>';
		errorBox += '<div class="innerBox">';
		errorBox += '<div class="tips">';
		errorBox += '<div class="alert-icons alert-icons3"></div>';
		errorBox += '<span id="errorMsg">错误操作！</span></div>';
		errorBox += '<ul class="ul-btn ul-btn1">';
		errorBox += '<li><a href="javascript:;" class="pop-borange" id="errorBtn">确认</a></li>';
		errorBox += '</ul></div></div></div>';
		$('body').append(errorBox);
		var confirmBox = '<div class="pop-win" id="confirmBox" style="display: none;">';
		confirmBox += '<div class="bg-win"></div>';
		confirmBox += '<div class="pop-bor"></div>';
		confirmBox += '<div class="pop-box">';
		confirmBox +=  '<h3 id="confirmTitle">信息提示</h3>';
		confirmBox += '<div class="innerBox">';
		confirmBox += '<div class="tips">';
		confirmBox += '<div class="alert-icons alert-icons1"></div>';
		confirmBox += '<span id="confirmMsg">确认?</span></div>';
		confirmBox += '<ul class="ul-btn">';
		confirmBox += '<li><a href="javascript:;" class="pop-borange" id="confirmBtn">确认</a></li>';
		confirmBox += '<li><a href="javascript:;" js="clopop" id="cancelBtn" class="pop-bgrange" onclick="$.messagerHide(this)">取消</a></li>';
		confirmBox += '</ul></div></div></div>';
		$('body').append(confirmBox);
		var warnBox = '<div class="pop-win" id="warnBox" style="display: none;">';
		warnBox += '<div class="bg-win"></div>';
		warnBox += '<div class="pop-bor"></div>';
		warnBox += '<div class="pop-box">';
		warnBox +=  '<h3 id="warnTitle">信息提示</h3>';
		warnBox += '<div class="innerBox">';
		warnBox += '<div class="tips">';
		warnBox += '<div class="alert-icons alert-icons2"></div>';
		warnBox += '<span id="warnMsg">将删除整条信息！</span></div>';
		warnBox += '<ul class="ul-btn ul-btn1">';
		warnBox += '<li><a href="javascript:;" class="pop-borange" id="warnBtn" onclick="$.messagerHide(this)">确认</a></li>';
		warnBox += '</ul></div></div></div>';
		$('body').append(warnBox);
		$('#confirmBtn').click(function(){
			$.messagerHide(this);
			if ($.isFunction(_this.options.confirmOnClick)) {
				_this.options.confirmOnClick(_this.options.param);
        	}
		});
		$('#errorBtn').click(function(){
			$.messagerHide(this);
			if ($.isFunction(_this.options.confirmOnClick)) {
				_this.options.confirmOnClick(_this.options.param);
        	}
		});
		$('#warnBtn').click(function(){
			$.messagerHide(this);
			if ($.isFunction(_this.options.confirmOnClick)) {
				_this.options.confirmOnClick(_this.options.param);
        	}
		});
		$('#cancelBtn').click(function(){
			$.messagerHide(this);
			if ($.isFunction(_this.options.cancelOnClick)) {
				_this.options.cancelOnClick();
        	}
		});
	},
	warn : function() {
		$('#warnBox').show();
	},
	error : function() {
		$('#errorBox').show();
	},
	confirm : function() {
		$('#confirmBox').show();
	},
	mofidyTitle : function(type, title) {
		var boxId = type+'Title';
		$('#' + boxId).html(title);
	},
	mofidyMsg : function(type, msg) {
		var boxId = type+'Msg';
		$('#' + boxId).html(msg);
	}
});

jQuery.extend({
	messagerBox : function(options) {
		var defaults = {
			type : "warn",
			title: "",
			msg : "",
			confirmOnClick : function(){},
			param : '',
			cancelOnClick:function(){
				$(this).closest('.pop-win').hide();
			}
		};
		options = $.extend({}, defaults, options);
		$('body').messager(options);
		if(options.title != '') {
			$('body').messager("mofidyTitle", options.type, options.title);
		}
		if(options.msg != '') {
			$('body').messager("mofidyMsg",options.type, options.msg);
		}
		$('body').messager(options.type);
	},
	messagerHide: function(obj) {
		$(obj).closest('.pop-win').hide();
	}
});
