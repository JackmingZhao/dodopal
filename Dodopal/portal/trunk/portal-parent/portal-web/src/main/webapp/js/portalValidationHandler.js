/*
 * portal项目的前端js 校验消息处理
 * 
 * 使用该js的前提是 ：
 * 
 * 1. 项目中必须引入ddpValidationBox.js及其依赖。
 * 2. 项目中必须引入对应的css文件，否则消息显示可能不正确
 * 
 */

jQuery.extend({
	validationHandler : function(validationFn, validateElement, msg, noIcon) {
			if (validationFn) {
//				if(noIcon){
				// 按产品最新要求,所有项目都不需要显示右边的icon-right.
					$('#' + validateElement).html("");
//				}else{
//					$('#' + validateElement).removeClass().addClass('icon-right').html("").show();
//				}
			} else {
				$('#' + validateElement).removeClass().addClass('tip-red-error').html(msg).show();
			}
			return validationFn;
	},
	/**
	 * submitState (必传)提交状态true代表表单提交，false代表焦点触发
	 * required:(必传)判断当前文本是否为必填项，true为必填，false否
	 * warnCheckFn:(必传)正则校验方法如$.cn
	 * valiDatInpI:(必传)校验节点的id
	 * warnMessageElement:(必传)错误提示的div Id
	 * msg:(必传)错误提示信息
	 * mixlength:(非必传)最小长度
	 * maxlength:(非必传)最大长度
	 */
	warnHandler : function(submitState,required,warnCheckFn,valiDatInpId,warnMessageElement, msg,mixlength,maxlength) {
		var myPlaceMsg = $('#' + valiDatInpId).attr("myPlaceholder");
		$("#"+warnMessageElement).show();
		if (!submitState) {
			$('#' + warnMessageElement).removeClass().addClass('tip-error').html(myPlaceMsg);
			$("#"+valiDatInpId).off('blur');
			$("#"+valiDatInpId).blur(function (){
				//闪开，
				if($.trim($("#"+valiDatInpId).val())==""){
					$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
				}else{
					if(mixlength==undefined){
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()))){
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
						}
					}else{
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength)){
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
						}
					}
				}
			});
			return submitState;
		}else{
			if(required){//必输项
				if($.trim($("#"+valiDatInpId).val())==""){
					$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html("请输入此项");
					return false;
				}else{
					if(mixlength==undefined){
						//没有长度限制
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()))){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()));
					}else{
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength)){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength);
					}
				}
			}else{
				//非必
				if($.trim($("#"+valiDatInpId).val())==""){
					$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
					return true;
				}else{
					if(mixlength==undefined){
						//没有长度限制
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()))){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()));
					}else{
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength)){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength);
					}
				}
			}
		} 
		return submitState;
	},
	/**
	 * submitState (必传)提交状态true代表表单提交，false代表焦点触发
	 * required:(必传)判断当前文本是否为必填项，true为必填，false否
	 * warnCheckFn:(必传)正则校验方法如$.cn
	 * valiDatInpI:(必传)校验节点的id
	 * warnMessageElement:(必传)错误提示的div Id
	 * msg:(必传)错误提示信息
	 * mixlength:(非必传)最小长度
	 * maxlength:(非必传)最大长度
	 * ajaxMethod:二部校验方法，一般在前端校验成功后调用
	 */
	warnDataFnHandler : function(data) {
		  var submitState = data.submitState;
		  var required = data.required;
		  var warnCheckFn = data.warnCheckFn;
		  var valiDatInpId = data.valiDatInpId;
		  var warnMessageElement = data.warnMessageElement;
		  var msg = data.msg;
		  var mixlength = data.mixlength;
		  var maxlength = data.maxlength;
		  var ajaxMethod = data.ajaxMethod;
		var myPlaceMsg = $('#' + valiDatInpId).attr("myPlaceholder");
		$("#"+warnMessageElement).show();
		if (!submitState) {
			$('#' + warnMessageElement).removeClass().addClass('tip-error').html(myPlaceMsg);
			$("#"+valiDatInpId).off('blur');
			$("#"+valiDatInpId).blur(function (){
				//闪开，
				if($.trim($("#"+valiDatInpId).val())==""){
					$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
				}else{
					if(mixlength==undefined){
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()))){
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
							if(ajaxMethod!=undefined){
								return ajaxMethod();
							}
						}
					}else{
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength)){
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
							if(ajaxMethod!=undefined){
								return ajaxMethod();
							}
						}
					}
				}
			});
			return submitState;
		}else{
			if(required){//必输项
				if($.trim($("#"+valiDatInpId).val())==""){
					$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html("请输入此项");
					return false;
				}else{
					if(mixlength==undefined){
						//没有长度限制
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()))){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
							if(ajaxMethod!=undefined){
								return ajaxMethod();
							}
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()));
					}else{
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength)){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
							if(ajaxMethod!=undefined){
								return 	ajaxMethod();
							}
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength);
					}
				}
			}else{
				//非必
				if($.trim($("#"+valiDatInpId).val())==""){
					$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
					return true;
				}else{
					if(mixlength==undefined){
						//没有长度限制
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()))){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
							if(ajaxMethod!=undefined){
								return 	ajaxMethod();
							}
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()));
					}else{
						if(!warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength)){
							//输入并且输错
							$('#' + warnMessageElement).removeClass().addClass('tip-red-error').html(msg).show();
						}else{
							$('#' + warnMessageElement).removeClass().addClass('tip-error').html("");
							if(ajaxMethod!=undefined){
								return 	ajaxMethod();
							}
						}
						return warnCheckFn($.trim($("#"+valiDatInpId).val()),mixlength,maxlength);
					}
				}
			}
		} 
		return submitState;
	}
})