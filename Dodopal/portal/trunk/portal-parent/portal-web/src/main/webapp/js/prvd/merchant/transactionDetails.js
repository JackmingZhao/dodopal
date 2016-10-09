$(function() {
	findTransaction();
	$('.page-navi').paginator({prefix : 'transactionDetails',pageSizeOnChange : findTransaction});
	highlightTitle();
	
	$('#_excelDownLoad').click(function(){
		exportExcel('exportCardRechargeDetails','frm')
    });
});

function searchData(num){
	$('#_excelDownLoad').unbind('click');
	$("#frm")[0].reset();
	$("#num").val(num);
	findTransaction();
	if(parseInt(num) == 1){
		$('#_excelDownLoad').click(function(){
			exportExcel('exportCardRechargeDetails','frm')
	    });
	}else{
		$('#_excelDownLoad').click(function(){
			exportExcel('exportCardConsumDetails','frm')
	    });
	}
	
}

function findTransaction(page) {
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}else{
		pageNo, pageSize
	}
	var merCountQuery ='';
	var url = "";
	var num =$("#num").val();
	
	var orderDateStart = escapePeculiar($.trim($('#orderDateStart').val()));
	var orderDateEnd = escapePeculiar($.trim($('#orderDateEnd').val()));
	if(parseInt(num) == 1){
		url='queryCardRechargeDetails';
		merCountQuery = {
				merName : escapePeculiar($.trim($('#merName').val())),
				merCode : escapePeculiar($.trim($('#merCode').val())),
				proOrderNum : escapePeculiar($.trim($('#proOrderNum').val())),
				yktCode : escapePeculiar($.trim($('#yktCode').val())),
				orderDateStart : orderDateStart,
				orderDateEnd : orderDateEnd,
				page : page,
			}
		$("#xf").removeClass("cur");
		$("#cz").addClass('cur');
		$("#txnAmt").html('充值金额(元)');
		$("#blackAmt").html('充值后卡内余额(元)');
		$("#proOrderDate").html('充值时间');
		$(".w130").html('充值日期：');
		compareTime(merCountQuery, orderDateStart, orderDateEnd,0);
	}else{
		url='queryCardConsumDetails';
		merCountQuery = {
				merName : escapePeculiar($.trim($('#merName').val())),
				orderNo : escapePeculiar($.trim($('#proOrderNum').val())),
				merCode : escapePeculiar($.trim($('#merCode').val())),
				yktCode : escapePeculiar($.trim($('#yktCode').val())),
				startDate : orderDateStart,
				endDate : orderDateEnd,
				page : page,
			}
		$("#cz").removeClass("cur");
		$("#xf").addClass('cur');
		$("#txnAmt").html('消费金额(元)');
		$("#blackAmt").html('消费后卡内余额(元)');
		$("#proOrderDate").html('消费时间');
		$(".w130").html('消费日期：');
		
		compareTime(merCountQuery, orderDateStart, orderDateEnd,1);
	}
	ddpAjaxCall({
		url : url,
		data : merCountQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#productOrderTbl tbody').empty();
				if (data.responseEntity != null
						&& data.responseEntity.records.length > 0) {
							$(data.responseEntity.records).each(
									function(i, v) {
										html = '<tr>';
										html += '<td class="nobor">&nbsp;</td>';

										html += '<td>'
												+ v.orderNo + '</td>'
										html += '<td>' + v.txnAmt
												+ '</td>';
										html += '<td>' + v.orderCardno
												+ '</td>';
										html += '<td>' + v.blackAmt + '</td>';

										html += '<td>' + v.proOrderState + '</td>';

										html += '<td>' + v.proOrderDate + '</td>';
										html += '</tr>';
										$('#productOrderTbl').append(html);
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

function compareTime(query, beginDate, endDate,flag) {
	var d1 = new Date(beginDate.replace(/\-/g, "\/"));
	var d2 = new Date(endDate.replace(/\-/g, "\/"));
	if (beginDate != "" && endDate != "" && d1 >= d2) {
		var temp = endDate;
		endDate = beginDate;
		beginDate = temp;
		
		$('#orderDateStart').val(beginDate)
		$('#orderDateEnd').val(endDate)
		
		if(parseInt(flag) == 0){
			query.orderDateStart = beginDate;
			query.orderDateEnd = endDate;
		}else{
			query.startDate = beginDate;
			query.endDate = endDate;
		}
		
	}
}