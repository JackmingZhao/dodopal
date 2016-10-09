$(function(){
	initQueryDate();
	findCcOrderPos();
	$('.page-navi').paginator({prefix:'order',pageSizeOnChange:findCcOrderPos});
	highlightTitle();
	$('.data-tit a').each(function(){
		if($.trim($(this).text())=="充值订单查询"){
			$(this).addClass('currents').siblings().removeClass();
		}
	});
	initEvent();
});

function findCcOrderPos(page){
	if(!page){
		page = {pageNo:1,pageSize:10};
	}
	/** 时间 */
	if(compareDates($("#startdate").val(),"yyyy-MM-dd",$("#enddate").val(),"yyyy-MM-dd")==1){
		var temp = $("#startdate").val();
		$("#startdate").val($("#enddate").val());
		$("#enddate").val(temp);
	}
	/** 状态 */
	var status=$('#status').val();
	
	var queryCcOrderFormBean ={ 
			mchnitid:$("#merCode").val(), /** 商户号*/
			bankorderid:escapePeculiar($.trim($("#bankorderid").val())), /** 商户订单号*/
			startdate:$("#startdate").val(), /** 查询起始时间*/
			enddate:$("#enddate").val(),/** 查询结束时间*/
			posid:escapePeculiar($.trim($("#posid").val())),/** Pos编号*/
			cardno:escapePeculiar($.trim($("#cardno").val())),/** 卡号 */
			username:escapePeculiar($.trim($("#username").val())),/** 用户名称 */
			status:status,/** 订单状态 */
			page:page
	}
	
	ddpAjaxCall({
		url : $.base+"/ccOrder/findCcOrderPos",
		data : queryCcOrderFormBean,
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
						html += v.bankorderid == null ? '' :htmlTagFormat(v.bankorderid);//订单号
						html += '</td>';
						
						html += '<td>'
						html += v.mchnitname == null ? '' :htmlTagFormat(v.mchnitname);//商户名称
						html += '</td>';
				
						html += '<td>'
						html += v.userid == null ? '' :htmlTagFormat(v.userid);//用户名称
						html += '</td>';
						
						html += '<td>'
						html += v.cardno == null ? '' :htmlTagFormat(v.cardno);//卡号
						html += '</td>';
							
						html += '<td>'
						html += v.posid == null ? '' :htmlTagFormat(v.posid);//POS号
						html += '</td>';
						
						html += '<td>'
						html += v.amt == null ? '' :htmlTagFormat(v.amt);//交易金额
						html += '</td>';
						
						html += '<td>'
						html += v.paidamt == null ? '' :htmlTagFormat(v.paidamt);//实收金额(元)
						html += '</td>';
						
						html += '<td>'
						html += v.flamt == null ? '' :htmlTagFormat(v.flamt);//实收金额(元)
						html += '</td>';
						
						html += '<td>'
						html +=	v.senddate == null ? '' :htmlTagFormat(v.senddate+"&nbsp;"+v.sendtime);//订单时间
						html += '</td>';
						
						html += '<td>'
						html +=	v.status == null ? '' :htmlTagFormat(v.status);//订单状态
						html += '</td>';
						
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
//		formatDate(new Date(),"y-M-d");
		if(time=="1W"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*7),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"y-M-d"));
		}else if(time=="1M"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*30),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"y-M-d"));
		}else if(time=="3M"){
			$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*30*3),"yyyy-MM-dd"));
			$("#enddate").val(formatDate(new Date(),"y-M-d"));
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
