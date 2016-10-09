$(function() {
	findChildMerRechargeOrderSum();
	$('#childMerRechargeOrderSumPaginator').paginator({prefix : 'childMerRechargeOrderSum',pageSizeOnChange : findChildMerRechargeOrderSum });
	$('#childMerOrderSumDetailPaginator').paginator({prefix : 'childMerOrderSumDetail',pageSizeOnChange : findChildOrderSumDetail
	});
	$('#childMerOrderSumDetailMain').hide();
	$('#childMerRechargeOrderSumMain').show();
	//highlightTitle();
});

function querySumDetail(obj){
	
	//var ss=obj;
//	var id = $(obj).parent().parent().find("td").eq(2).text();
//	$('#merCode').val(merCode);
	$('#merNameDetail').val($(obj).parent().parent().find("td").eq(2).text());
	$('#tradeDateStartDetail').val($('#startDate').val());
	$('#tradeDateEndDetail').val($('#endDate').val());
	$('#orderStateDetail').val($('#proOrderState').val());
	$('#merNameSpan').html($(obj).parent().parent().find("td").eq(2).text());
//	$('#posSpan').html($(obj).parent().parent().find("td").eq(5).text());
	findChildOrderSumDetail();
	$('#childMerRechargeOrderSumMain').hide();
	$('#childMerOrderSumDetailMain').show();

	
}

function findChildMerRechargeOrderSum(page) {
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query = {
		proOrderState : $('#proOrderState').val(),
		merName : escapePeculiar($.trim($('#merName').val())),
		startDate: escapePeculiar($.trim($('#startDate').val())),
		endDate : escapePeculiar($.trim($('#endDate').val())),
		page : page
	}
	var startDate = query.startDate;
	var endDate = query.endDate;
	compareTime(query, startDate, endDate);
	ddpAjaxCall({
		url : "findRechargeForChildOrderSum",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#childMerRechargeOrderSumTb tbody').empty();
				if (data.responseEntity != null
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records)
							.each(
									function(i, v) {
										$('.null-box').hide();
										html = '<tr>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '<td class="a-center">'
												+ getSequence(
														data.responseEntity, i)
												+ '</td>';

//										html += '<td>'+ v.tradeDate + '</td>';
										html += '<td>' + v.merName + '</td>';
//										html += '<td>' + (v.cityName == null ? '':v.cityName) + '</td>';
//										html += '<td>' + v.posCode + '</td>';
										html += '<td>' + v.rechargeCount
												+ '</td>';
										html += '<td>'
												+ fmoney(v.rechargeAmt, 2)
												+ '</td>';
										
//										html += '<td style="word-break:break-all" >'
//										html += v.posComments == null
//														? ''
//														: htmlTagFormat(v.posComments);
//										html += '</td>';
										html += '<td class="a-center">'
									if(hasPermission('merchant.child.ordersum.recharge.view')){
										html += '<a href="#" onClick="querySumDetail(this);" class="text-icon mg0" title="充值详情"></a>';
									}
										html += '</td>';

										html += '<td class="nobor">&nbsp;</td>';
										html += '</tr>';
										$('#childMerRechargeOrderSumTb').append(html);
									});

					$('.com-table01 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					pageSize = data.responseEntity.pageSize;
					pageNo = data.responseEntity.pageNo;
					totalPages = data.responseEntity.totalPages;
					rows = data.responseEntity.rows;
					$('#childMerRechargeOrderSumPaginator').paginator('setPage', pageNo, pageSize,
							totalPages, rows);
					$('#childMerRechargeOrderSumPaginator select').attr("style","width:45px;padding:0px 0px");
				} else {
					$('.null-box').show();
					$('#childMerRechargeOrderSumPaginator').paginator('setPage');
					$('#childMerRechargeOrderSumPaginator select').attr("style","width:45px;padding:0px 0px");
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

function compareTime(query, startDate, endDate) {
	var d1 = new Date(startDate.replace(/\-/g, "\/"));
	var d2 = new Date(endDate.replace(/\-/g, "\/"));

	if (startDate != "" && endDate != "" && d1 >= d2) {
		var temp = endDate;
		endDate = startDate;
		startDate = temp;
		$('#startDate').val(startDate)
		$('#endDate').val(endDate)
		query.startDate = startDate;
		query.endDate = endDate;
	}
}

// 格式化金额 s-金额 n-保留小数个数
function fmoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}




function findChildOrderSumDetail(page) {
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query = {
		proOrderState : $('#orderStateDetail').val(),
		orderDateStart : $('#tradeDateStartDetail').val(),
		orderDateEnd : $('#tradeDateEndDetail').val(),
		merName : $('#merNameDetail').val(),
		merCode: $('#merCodeDetail').val(),
		page : page
	}

	ddpAjaxCall({
		url : "findChildOrderSumDetail",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#childOrderSumDetailTb tbody').empty();
				if (data.responseEntity != null
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records)
							.each(
									function(i, v) {
										$('.null-box').hide();
										html = '<tr>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '<td class="a-center">'
												+ getSequence(
														data.responseEntity, i)
												+ '</td>';

										html += '<td>' + v.proOrderNum + '</td>';
										html += '<td>' + (v.merName == null ? '':v.merName) + '</td>';
										html += '<td>' + (v.cityName == null ? '':v.cityName) + '</td>';
										html += '<td>' + v.showTxnAmt + '</td>';
										html += '<td>' + v.showReceivedPrice + '</td>';
										html += '<td>' + v.showMerGain + '</td>';
										html += '<td>' + v.orderCardno + '</td>';
										html += '<td>' + v.posCode + '</td>';
										html += '<td>' + v.proOrderStateView+ '</td>';
										html += '<td>' + (v.proOrderDate == null ? '': formatDate(v.proOrderDate,'yyyy-MM-dd HH:mm:ss'))+ '</td>';
										html += '<td style="word-break:break-all" >'
										html += v.posComments == null
															? ''
															: htmlTagFormat(v.posComments);
										html += '</td>';
										html += '<td class="nobor">&nbsp;</td>';
										html += '</tr>';
										$('#childOrderSumDetailTb')
												.append(html);
									});

					$('.com-table01 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					pageSize = data.responseEntity.pageSize;
					pageNo = data.responseEntity.pageNo;
					totalPages = data.responseEntity.totalPages;
					rows = data.responseEntity.rows;
					$('#childMerOrderSumDetailPaginator').paginator(
							'setPage', pageNo, pageSize, totalPages, rows);
					$('#childMerOrderSumDetailPaginator select').attr(
							"style", "width:45px;padding:0px 0px");
				} else {
					$('.null-box').show();
					$('#childMerOrderSumDetailPaginator')
							.paginator('setPage');
					$('.page-navi select').attr("style","width:45px;padding:0px 0px");
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



function returnSum(){
	$('#childMerOrderSumDetailMain').hide();
	$('#childMerRechargeOrderSumMain').show();
};
