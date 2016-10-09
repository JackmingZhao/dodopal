var i=0;
/**
 * 更改手机获取验证码的样式, 注意，由于使用了共有变量i, 如果在同一个页面中同时使用2次的话，会有问题，TODO 以后修改
 */
function requestAuthCodeBtn(mobileInput, authCodeSendBtn){
		if (i == 0) {
			i=60;
			document.getElementById(mobileInput).removeAttribute("disabled");
			document.getElementById(authCodeSendBtn).removeAttribute("disabled");
			document.getElementById(authCodeSendBtn).value = "获取验证码";
			clearInterval(intervalid);
		}else{
			document.getElementById(authCodeSendBtn).value = "重新发送("+i+")";
			i--;
		}
}

function clearQueryForm(selector){
	$('#' + selector)[0].reset();
	//$('#'+selector+' select option:first-child').attr("selected", "selected");
	$('#'+selector+' select').each(function() {
	    this.selectedIndex = 0;
	});
	
	selectUiInit();
}

function clearReadOnlyForm(selector){
	$('#' + selector).find('span').text('');
}

function resetDetailForm(selector){
	$('#' + selector)[0].reset();
	$('#' + selector).find('.tip-error').empty();
	$('#' + selector).find('.tip-red-error').empty();
	//$('#'+selector+' select option:first-child').attr("selected", "selected");
	$('#'+selector+' select').each(function() {
	    this.selectedIndex = 0;
	});
	selectUiInit();
}

function toggle(source,cbName){
	$('input[name='+cbName+']').each(function(i,v){
		$(v).prop('checked',source.checked);
	});
}

function toggleDelBtn(cbName,delBtnId){
	var ids = [];
	$('input[name='+cbName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}else{
			$('input[type=checkBox]:eq(0)').prop('checked',false);
		}
	});
	if(ids.length==$('input[name='+cbName+']').length){
		$('input[type=checkBox]:eq(0)').prop('checked',true);
	}
	var deleteBtnId = '#' + delBtnId;
	var myhref = $(deleteBtnId).attr("href");
	if(myhref!="")
		$(deleteBtnId).attr("myhref",myhref);
	if(ids.length > 0){
		$(deleteBtnId).attr("href",$(deleteBtnId).attr("myhref"));
		$(deleteBtnId).attr('disabled',false);
		$(deleteBtnId).removeClass('gray-btn-h22');
		$(deleteBtnId).addClass('orange-btn-h22');
	}else{
		$(deleteBtnId).removeAttr('href');  
		$(deleteBtnId).attr('disabled',true);
		$(deleteBtnId).removeClass('orange-btn-h22');
		$(deleteBtnId).addClass('gray-btn-h22');
	}
	
}

function toggleActivateBtn(cbName){
	var ids = [];
	$('input[name='+cbName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}else{
			$('input[type=checkBox]:eq(0)').prop('checked',false);
		}
	});
	if(ids.length==$('input[name='+cbName+']').length){
		$('input[type=checkBox]:eq(0)').prop('checked',true);
	}
	var qiyong = "#qiyong";
	var tingyong = "#tingyong";
	var qiyonghref = $(qiyong).attr("href");
	var tingyonghref = $(tingyong).attr("href");
	if(qiyonghref!="")
		$(qiyong).attr("myhref",qiyonghref);
	if(tingyonghref!="")
		$(tingyong).attr("myhref",tingyonghref);
	if(ids.length > 0){
		disableOrEnableBtn(false,'#qiyong');
		disableOrEnableBtn(false,'#tingyong');
	}else{
		disableOrEnableBtn(true,'#qiyong');
		disableOrEnableBtn(true,'#tingyong');
	}
	
}

function cancelEdit(domainName){
	var callbackFn = function(domainName){
		$('#detailForm')[0].reset();
		$('#'+domainName+'Main').show();
		$('#'+domainName+'View').hide();
		$('#'+domainName+'Detail').hide();
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "确认要取消当前修改？", confirmOnClick: callbackFn, param:domainName});
}

function cancelView(domainName){
	$('#detailForm')[0].reset();
	$('#'+domainName+'Main').show();
	$('#'+domainName+'DetailView').hide();
	$('#'+domainName+'Detail').hide();
}

function loadPojo(data,readOnly){
	if(readOnly == false){
		for(var propt in data){
			if(data[propt]){
				$('input[id='+propt+']').val(data[propt]);
			}
		}
		
		if(data.createUser)
			$('#createUser').text(data.createUser);
		
		if(data.updateUser)
			$('#updateUser').text(data.updateUser);
		
		if(data.createDate)
			$('#createDate').text(data.createDate);
		
		if(data.updateDate)
			$('#updateDate').text(data.updateDate);
		
	}else{
		for(var propt in data){
			if(data[propt]){
				$('span[id='+propt+'Span]').text(data[propt]);
			}
		}
	}
}

function highlightTitle(){
	var title = $('title').text();
	if(!title){
		title = document.title;
	}
	
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())==title){
			$(this).addClass('cur');
		}
	});
	$('.tit-h3 a').each(function(){
		if($.trim($(this).text())==title){
			$(this).addClass('cur');
		}
	});
}


function disableOrEnableBtn(disableFlag,buttonId){
	var myhref = $(buttonId).attr("href");
	if(myhref!=""){
		$(buttonId).attr("myhref",myhref);	
	}
	$(buttonId).attr('disabled',false);
	if(disableFlag == true){
		//$(buttonId).removeAttr('href');  
		$(buttonId).removeAttr('href'); 
		$(buttonId).removeClass('orange-btn-h22');
		$(buttonId).addClass('gray-btn-h22');
	}else{
		//$(buttonId).attr("href",href);
		$(buttonId).attr("href",$(buttonId).attr("myhref"));
		$(buttonId).removeClass('gray-btn-h22');
		$(buttonId).addClass('orange-btn-h22');
	}
}

function validateRemark(){
	var formValid = true;
	if($('#remark').val() && !$.lengthRange($('#remark').val(),0,200)){
		$.validationHandler(false, 'remarkVal', '备注长度不能超过200个字符。');
		formValid = false;
	}else{
		$.validationHandler(true, 'remarkVal',null,true);
	}
	return formValid;
}

function setTitleForAction(selector,title){
	$(selector).html(title);
}

function getTdCss(i){
	var tdCss = 'oddRow';
	var n = i + 1;
	if(n > 0 && n%2 ==0 ){
		tdCss = 'evenRow';
	}
	return tdCss;
}

function getSequence(responseEntity,i){
	var startIndex = 0;
	if(responseEntity.pageNo > 1){
		startIndex = (responseEntity.pageNo - 1)* responseEntity.pageSize;
	}
	
	startIndex = startIndex + i + 1;
	return startIndex;
}
