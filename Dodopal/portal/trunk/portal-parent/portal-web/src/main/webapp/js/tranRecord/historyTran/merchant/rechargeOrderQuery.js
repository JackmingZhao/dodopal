$(function(){
	initQueryDate();
	findChargeOrderList();
	$('.page-navi').paginator({prefix:'order',pageSizeOnChange:findChargeOrderList});
	highlightTitle();
	initEvent();
});

function findChargeOrderList(page){
	if(!page){
		page = {pageNo:1,pageSize:10};
	}

	if(compareDates($("#startdate").val(),"yyyy-MM-dd",$("#enddate").val(),"yyyy-MM-dd")==1){
		var temp = $("#startdate").val();
		$("#startdate").val($("#enddate").val());
		$("#enddate").val(temp);
	}
	
	var orderQuery ={ 
//			code : escapePeculiar($.trim($('#posCode').val())),
			mchnitid:"", /** 商户号*/
			transactionid:$("#transactionidQuery").val(), /** 商户订单号*/
			startdate:$("#startdate").val(), /** 查询起始时间*/
			enddate:$("#enddate").val(),/** 查询结束时间*/
			posid:$("#posidQuery").val(),/** Pos编号*/
			cardno:$("#cardnoQuery").val(),/** 卡号 */
			username:$("#username").val(),/** 用户名称 */
			status:$("#statusQuery").val(),/** 订单状态 */
			page:page
	}
	
	ddpAjaxCall({
		url : $.base+"/merJointQuery/findChargeOrderList",
		data : orderQuery,
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
						
						html += '<td>'
						html += v.transaction_id == null ? '' :htmlTagFormat(v.transaction_id);
						html += '</td>';
						
						html += '<td>'
						html += v.mchnitname == null ? '' :htmlTagFormat(v.mchnitname);
						html += '</td>';
				
						html += '<td>'
						html += v.username == null ? '' :htmlTagFormat(v.username);
						html += '</td>';
						
						html += '<td>'
						html += v.cardno == null ? '' :htmlTagFormat(v.cardno);
						html += '</td>';
							
						html += '<td>'
						html += v.posid == null ? '' :htmlTagFormat(v.posid);
						html += '</td>';
						
						html += '<td>'
						html += v.amt == null ? '' :htmlTagFormat(v.amt);
						html += '</td>';
						
						html += '<td>'
						html += v.paidamt == null ? '' :htmlTagFormat(v.paidamt);
						html += '</td>';
						
						html += '<td>'
						html += v.rebatesamt == null ? '' :htmlTagFormat(v.rebatesamt);
						html += '</td>';
						
						html += '<td>'
						html += v.sendtime == null ? '' :htmlTagFormat(v.sendtime);
						html += '</td>';
							
						html += '<td>'
						html +=	v.statusStr == null ? '' :htmlTagFormat(v.statusStr);
						html += '</td>';
//						html += '<td>'
//						html += v.bundlingDate == null ? '' : formatDate(v.bundlingDate,'yyyy-MM-dd HH:mm:ss');
//						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#displayTbl').append(html);
					});
				}else{
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

function initEvent(){
	$("input[name=time]").click(function(){
		var time = $("input[name='time']:checked").val();
//		formatDate(new Date(),"yyyy-MM-dd");
		if(time=="1W"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*7),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
		}else if(time=="1M"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*30),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
		}else if(time=="3M"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*30*3),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
		}
	});
}
function myClearQueryForm(){
	clearQueryForm('queryForm');
	initQueryDate();
}

function initQueryDate(){
	$("#startdate").val(formatDate(new Date(),"yyyy-MM-dd"));
	$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
}
