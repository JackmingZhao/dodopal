$(function(){
	findPosList();
	$('.page-navi').paginator({prefix:'pos',pageSizeOnChange:findPosList});
	highlightTitle();
});


function findPosList(page){
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	var posQuery ={ 
			code : escapePeculiar($.trim($('#posCode').val())),
			merchantName:escapePeculiar($.trim($("#merName").val())),
			page:page
	}
	
	ddpAjaxCall({
		url : "findChildrenPosList",
		data : posQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				afterFind("allPos");
				$('#displayTbl tbody').empty();
				if(data.responseEntity.records && data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td class="a-center">';
						html += '<input id="'+v.id+'" name="pos" value="'+v.code+'" type="checkbox" onclick="toggleActivateBtn(\'pos\')"/>'
						html += '</td>';
						
						html += '<td>'
						html += ++i+(data.responseEntity.pageNo-1)*data.responseEntity.pageSize;
						html += '</td>';
						
						html += '<td>'
						html += v.merchantName==null?"":v.merchantName;
						html += '</td>';
						
						html += '<td>';
						html += v.code;
						html += '</td>';

						html += '<td>';
							if(v.status=='0'){
								html += '启用';
							}else if(v.status=='1'){
								html += '停用';
							}else if(v.status=='2'){
								html += '作废';
							}else if(v.status=='3'){
								html += '封锁';
							}else if(v.status=='4'){
								html += '充值封锁';
							}
						html += '</td>';
						
						html += '<td class="a-left">';
						html += v.bundlingDate == null ? '' : formatDate(v.bundlingDate,'yyyy-MM-dd HH:mm:ss');
						html += '</td>';
						
						html += '<td style="word-break:break-all">';
						html += v.comments==null?"":htmlTagFormat(v.comments);
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
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}



/**
 * 查询置灰
 * @param id
 */
function afterFind(id){
	$("#"+id).attr('checked',false);
	disableOrEnableBtn(true,'#qiyong');
	disableOrEnableBtn(true,'#tingyong');
}
/**
 * @param va this
 * @param id check Name
 */
function checkAll(va,id){
	 toggle(va,id);
	 var qiyong = "#qiyong";
	 var tingyong = "#tingyong";
	 var qiyonghref = $(qiyong).attr("href");
	 var tingyonghref = $(tingyong).attr("href");
	 if(qiyonghref!="")
		$(qiyong).attr("myhref",qiyonghref);
	 if(tingyonghref!="")
		$(tingyong).attr("myhref",tingyonghref);
	 if(va.checked&&$('input[name='+id+']').length>0){
		 disableOrEnableBtn(false,'#qiyong');
		 disableOrEnableBtn(false,'#tingyong');
	 }else{
		 disableOrEnableBtn(true,'#qiyong');
		 disableOrEnableBtn(true,'#tingyong');
	 }
}


function startPos(){
	if(!hasPermission('merchant.child.pos.activate')){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "你没有权限进行该操作"});

		return;
	}
	var pos = new Array;
	$('input[name="pos"]:checked').each(function(){
	     pos.push($(this).val());
    });
	if(pos.length<1){
		//样式CSS TODO
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请至少选择一条记录进行操作"});
		return;
	}
	var posBean={
			pos:pos
		}
	ddpAjaxCall({
		url : "startPos",
		data : posBean,
		successFn : function(data) {
			if(data.code=="000000"){
				toStartHide();
				findPosList($('.page-navi').paginator('getPage'));
				toggleActivateBtn('pos');
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function stopPos(){
	if(!hasPermission('merchant.child.pos.inactivate')){
		$.messagerBox({type: 'warn', title:"信息提示", msg: "你没有权限进行该操作"});
		return;
	}
	var pos = new Array;
	$('input[name="pos"]:checked').each(function(){
	     pos.push($(this).val());
    });
	if(pos.length<1){
		//样式CSS TODO
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请至少选择一条记录进行操作"});
		return;
	}
	var posBean={
		pos:pos
	}
	ddpAjaxCall({
		url : "stopPos",
		data : posBean,
		successFn : function(data) {
			if(data.code=="000000"){
				toStopHide();
				findPosList($('.page-navi').paginator('getPage'));
				toggleActivateBtn('pos');
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
    return false;
}

function toStartShow(){
	var pos = new Array;
	$('input[name="pos"]:checked').each(function(){
	     pos.push($(this).val());
    });
	console.log(pos);
	if(pos.length<1){
		//样式CSS TODO
		//alert("请至少选择一条记录进行操作");
		return;
	}
	$.messagerBox({type:"confirm", title:"信息提示", msg: "您确认启用？", confirmOnClick: startPos, param:""});

	//$('#qiyongBox').show();
}
function toStartHide(){
	$('[js="qiyongBox"]').hide();
}
function toStopShow(){
	var pos = new Array;
	$('input[name="pos"]:checked').each(function(){
	     pos.push($(this).val());
    });
	if(pos.length<1){
		//样式CSS TODO
		$.messagerBox({type: 'warn', title:"信息提示", msg: "请至少选择一条记录进行操作"});
		return;
	}
	$.messagerBox({type:"confirm", title:"信息提示", msg: "您确认停用？", confirmOnClick: stopPos, param:""});

	//$('[js="tingyongBox"]').show();
}
function toStopHide(){
	$('[js="tingyongBox"]').hide();
}
