$(function(){
	findPosList();
	$('.page-navi').paginator({prefix:'pos',pageSizeOnChange:findPosList});
	highlightTitle();
	
	
	var oxcStr = '<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:76056ABB-01BD-4C17-829E-5123C56C3F9E" ';
	oxcStr+=' HEIGHT=0 WIDTH=0 codebase="http://application.dodopal.com:9997/ocxweb/ocxinfo/comn.CAB#version=1,0,1,2}" viewastext>';
	oxcStr+='<SPAN STYLE="color:red">控件加载失败! -- 请检查浏览器的安全级别设置.</SPAN></OBJECT>';
	$("body").append(oxcStr);
});


function findPosList(page){
	
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	
	var posQuery ={ 
			code : escapePeculiar($.trim($('#posCode').val())),
			page:page
	}
	
	ddpAjaxCall({
		url : "findPosList",
		data : posQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				afterFind();
				var html = '';
				$('#displayTbl tbody').empty();
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td class="a-center">';
						html += '<input id="'+v.id+'" name="pos" value="'+v.code+'" type="checkbox" onclick="toggleDelBtn(\'pos\',\'deleteBtn\')"/>'
						html += '</td>';
						
						html += '<td>'
						html += ++i+(data.responseEntity.pageNo-1)*data.responseEntity.pageSize;
						html += '</td>';
						
						html += '<td>'
						html += v.code;
						html += '</td>';
						
						html += '<td>'
						html += v.bundlingDate == null ? '' : formatDate(v.bundlingDate,'yyyy-MM-dd HH:mm:ss');
						html += '</td>';
						
						html += '<td class="a-left" style="word-break:break-all">'
						html += v.comments == null ? '' :htmlTagFormat(v.comments);
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#displayTbl').append(html);
					});
				}
				else{
					$('.null-box').show();
				}
				
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
				$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');				
			}else{
				//TODO handle error
				$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}

function afterFind(){
	$("#allPos").attr('checked',false);
	disableOrEnableBtn(true,'#deleteBtn');
}
function checkRemark(){
	var rmlength =$.lengthRange($.trim($('#remarks').val()),0,200);
	var remck = $.validationHandler(rmlength, 'remarksWarn', "长度范围为200字符内");
	return remck;
}
function clearPosQuery(){
	$('#posCode').val('');
}

function bindingPos(){
	$('#bangdingBox').hide();
	var pos = new Array;
	//加入权限校验
	if(!hasPermission('merchant.pos.bind')){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "你没有权限进行该操作"});
		return;
	}
	var posid = $('#posCodeBind').val();
	if(posid==""){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请读取POS编号"});
		return;
	}
	if(!checkRemark()){
		return;
	}
	pos.push($.trim($('#posCodeBind').val()));
	var posBean={
		remarks:$.trim($('#remarks').val()),
		pos:pos
	}
	ddpAjaxCall({
		url : "bindingPos",
		data : posBean,
		successFn : function(data) {
			if(data.code=="000000"){
				closeBinding();
				findPosList($('.page-navi').paginator('getPage'));
				$('#deleteBtn').attr('class','gray-btn-h22');
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function unBinding(){
	var pos = new Array;
	$('input[name="pos"]:checked').each(function(){
	     pos.push($(this).val());
    });
	if(pos.length<1){
		//样式CSS TODO
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请至少选择一条记录进行操作"});
		return;
	}
	if(!hasPermission('merchant.pos.unbind')){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "你没有权限进行该操作"});
		return;
	}
	var posBean={
			pos:pos
		}
	ddpAjaxCall({
		url : "unBindingPos",
		data : posBean,
		successFn : function(data) {
			if(data.code=="000000"){
				closeBindding();
				findPosList($('.page-navi').paginator('getPage'));
				toggleDelBtn('pos','deleteBtn');
				//$('[js="qiyong"]').attr('class','orange-btn-h22');
				$('#deleteBtn').attr('class','gray-btn-h22');
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
    return false;
}

function toUnBinding(){
	var pos = new Array;
	$('input[name="pos"]:checked').each(function(){
	     pos.push($(this).val());
    });
	if(pos.length<1){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请至少选择一条记录进行操作"});
		return;
	}
	$.messagerBox({type:"confirm", title:"信息提示", msg: "您确认解绑POS吗？", confirmOnClick: unBinding, param:""});
}
function closeBindding(){
	$("#unBindingBoxDiv").hide();
	
}
function toBinding(){
	$("#posCodeBindTemp").val('');
	$("#posCodeBind").val('');
	$("#remarksWarn").hide();
	$('#bangdingBox').show();
}
function closeBinding(){
	$('#bangdingBox').hide();
	$('#bindingInfo')[0].reset();
}


function getPOS(){
	$("#posCodeBindTemp").val('');
	$("#posCodeBind").val('');
	var posID = getPosid();	
	if(posID==null||posID==""){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请确认设备"});
		                    //TODO  测试
		return;
	}
	$("#posCodeBindTemp").val(posID);
	$("#posCodeBind").val(posID);
}

/** =====================测试==================================*/
/**
 * 获取pos号
 * @return 返回null错误
 */
function getPosid() {
	var xunkaData = sendCmd("COMFF00000014");//TODO  获取pos命令 
	var xunkaDataArr = xunkaData.split("COM");
	var backPosId = xunkaDataArr[1];
	//判断取POS号指令是否执行成功
	if(!backPosId || backPosId.substr(backPosId.length-4,4)!="9000" ){
  		return null;
  	} else {
		return backPosId.substr(backPosId.length-16,12);//获取POS
	}
}


// ocx执行指定
var sendCmd = function(command) {
	var execCmdResult = OCXFAPAY.SendCmd(command);
	if (execCmdResult != "0") {
	}
	return OCXFAPAY.pOutVal;
}

function checkAll(va,id){
	 toggle(va,'pos');
	 if(va.checked&&$('input[name=pos]').length>0){
		 disableOrEnableBtn(false,'#deleteBtn');
	 }else{
		 disableOrEnableBtn(true,'#deleteBtn');
	 }
	 
}



