$(function() {
	findMerchantPos();
	$('.page-navi').paginator({prefix : 'merchantPos',pageSizeOnChange : findMerchantPos});
	highlightTitle();
});

function findMerchantPos(page) {
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var posQuery = {
		code : escapePeculiar($.trim($('#posCode').val())),
		merchantCode : $.trim($('#merCode').val()),
		merchantName : $.trim($('#merName').val()),
		page : page,
	}
	ddpAjaxCall({
		url : "posFind",
		data : posQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				afterFind("merPosCheck");
				//toggleActivateBtn('merPos');
				$('#merchantPosTb tbody').empty();
				if (data.responseEntity != null
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records)
							.each(
									function(i, v) {
										html = '<tr>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '<td class="a-center"><input type="checkbox" id="'+v.code+'"  name="merPos"  onclick="toggleActivateBtn(\'merPos\')"  /></td>'
										html += '<td>'+ (v.code == null ? '': v.code) + '</td>'
										html += '<td>' + v.statusView + '</td>';
										html += '<td>' ;
										html +=  v.bundlingDate == null ? '' : formatDate(v.bundlingDate,'yyyy-MM-dd HH:mm:ss');
										html +=  '</td>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '</tr>';
										$('#merchantPosTb').append(html);
									});

					$('.com-table01 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					pageSize = data.responseEntity.pageSize;
					pageNo = data.responseEntity.pageNo;
					totalPages = data.responseEntity.totalPages;
					rows = data.responseEntity.rows;
					$('.page-navi').paginator('setPage', pageNo, pageSize,
							totalPages, rows);
					$('.page-navi select').attr("style",
							"width:45px;padding:0px 0px");
				} else {
					$('.null-box').show();
					$('.page-navi').paginator('setPage');
				}
			} else {
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
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

//停用 启用
function toggleActivateBtn(cbName){
	var ids = [];
	$('input[name='+cbName+']').each(function(i,v){
		if($(v).is(':checked')){
			ids.push(v);
		}
	});
	
	if(ids.length > 0){
		disableOrEnableBtn(false,'#qiyong');
		disableOrEnableBtn(false,'#tingyong');
	}else{
		disableOrEnableBtn(true,'#qiyong');
		disableOrEnableBtn(true,'#tingyong');
	}
	
}


function startOrStopUser(flag){
	var ids = [];
	$('input[name=merPos]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	if(ids.length == 0){
		return;
	}
	
	if(flag == true){
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认启用？", confirmOnClick: startOrStopUserConfirm, param:true});
	}else{
		$.messagerBox({type:"confirm", title:"确认", msg: "您确认停用？", confirmOnClick: startOrStopUserConfirm, param:false});
	}
}


function startOrStopUserConfirm(flag) {
	var ids = [];
	$('input[name=merPos]').each(function(i,v){
		if($(v).is(':checked')){
			ids.push($(v).attr('id'));
		}
	});
	
	var activate = '';
	if(flag == true){
		activate = '0';
		ddpAjaxCall({
			url : "batchStartMerPos",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findMerchantPos($('#merPosPaginator').paginator('getPage'));
					//$('#qiyongBox').hide();
					toggleActivateBtn('merPos');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}else{
		activate = '1';
		ddpAjaxCall({
			url : "batchStopMerPos",
			data : ids,
			successFn : function(data) {
				if (data.code == "000000") {
					findMerchantPos($('#merPosPaginator').paginator('getPage'));
					//$('#tingyongBox').hide();
					toggleActivateBtn('merPos');
				}else{
					$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
				}
			}
		});
	}
	
}
