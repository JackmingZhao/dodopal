$(function() {
	initQueryDate();
	findCzOrderByPosByPage();
	$('.page-navi').paginator({
		prefix : 'order',
		pageSizeOnChange : findCzOrderByPosByPage
	});
	highlightTitle();
	$('.data-tit a').each(function() {
		if ($.trim($(this).text()) == "充值订单统计") {
			$(this).addClass('currents').siblings().removeClass();
		}
	});
});

function findCzOrderByPosByPage(page) {
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	/** 时间 */
	if (compareDates($("#startdate").val(), "yyyy-MM-dd", $("#enddate").val(),
			"yyyy-MM-dd") == 1) {
		var temp = $("#startdate").val();
		$("#startdate").val($("#enddate").val());
		$("#enddate").val(temp);
	}

	var queryCzOrderByPosCountBean = {
		mchnitid : $("#merCode").val(),
		/** 商户号 */
		startdate : $("#startdate").val(),
		/** 查询起始时间 */
		enddate : $("#enddate").val(),
		/** 查询结束时间 */
		page : page
	}

	ddpAjaxCall({
		url : $.base + "/ccOrder/findCzOrderByPosByPage",
		data : queryCzOrderByPosCountBean,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#displayTbl tbody').empty();
				if (data.responseEntity.records
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records).each(
							function(i, v) {
								$('.null-box').hide();
								html = '<tr>';
								html += '<td class="nobor">&nbsp;</td>';

								html += '<td>'
								html += v.posid == null ? ''
										: htmlTagFormat(v.posid);// POS号
								html += '</td>';

								html += '<td>'
								html += v.username == null ? ''
										: htmlTagFormat(v.username);// 用户名称
								html += '</td>';

								html += '<td>'
								html += v.tradeCount == null ? ''
										: v.tradeCount;// 交易总笔数
								html += '</td>';

								html += '<td>'
								html += v.tradeMoney == null ? ''
										: v.tradeMoney;// 交易总金额
								html += '</td>';

								html += '<td>'
								html += v.tradeSucceedCount == null ? ''
										: v.tradeSucceedCount;// 交易成功笔数
								html += '</td>';

								html += '<td>'
								html += v.tradeSucceedMoney == null ? ''
										: v.tradeSucceedMoney;// 交易成功金额
								html += '</td>';

								html += '<td>'
								html += v.tradeErrorCount == null ? ''
										: v.tradeErrorCount;// 交易失败笔数
								html += '</td>';

								html += '<td>'
								html += v.tradeErrorMoney == null ? ''
										: v.tradeErrorMoney;// 交易失败金额
								html += '</td>';

								html += '<td>'
								html += v.doubtfulTradeCount == null ? ''
										: v.doubtfulTradeCount;// 可疑交易笔数
								html += '</td>';

								html += '<td>'
								html += v.doubtfulTradeMoney == null ? ''
										: v.doubtfulTradeMoney;// 可疑交易金额
								html += '</td>';

								html += '<td class="nobor">&nbsp;</td>';
								html += '</tr>';
								$('#displayTbl').append(html);
							});
				} else {
					$('.null-box').show();
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage', pageNo, pageSize,
						totalPages, rows);
				$('.com-table01 tbody tr:even').find('td').css(
						"background-color", '#f6fafe');
				$('.com-table02 tbody tr:even').find('td').css(
						"background-color", '#f6fafe');
			} else {
				$.messagerBox({
					type : 'error',
					title : "信息提示",
					msg : data.message
				});
			}

		}
	});
	ddpAjaxCall({
		url : $.base + "/ccOrder/findCzOrderCount",
		data : queryCzOrderByPosCountBean,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#displayTblCount tbody').empty();
				var v =data.responseEntity;
				if (v!=null) {
								$('.null-box').hide();
								html = '<tr>';
								html += '<td class="nobor">&nbsp;</td>';
								html += '<td>'
								html += '交易合计';
								html += '</td>';
								
								html += '<td>'
								html += v.tradeCountSum == null ? ''
										: v.tradeCountSum;// 交易总笔数
								html += '</td>';

								html += '<td>'
								html += v.tradeMoneySum == null ? ''
										: v.tradeMoneySum;//  交易总金额
								html += '</td>';

								html += '<td class="nobor">&nbsp;</td>';
								html += '</tr>';
								$('#displayTblCount').append(html);
								html = '<tr>';
								html += '<td class="nobor">&nbsp;</td>';
								html += '<td>'
								html += '交易成功合计';// 交易总笔数
								html += '</td>';
								
								html += '<td>'
								html += v.tradeSucceedCountSum == null ? ''
										: v.tradeSucceedCountSum;// 交易成功笔数 
								html += '</td>';

								html += '<td>'
								html += v.tradeSucceedMoneySum == null ? ''
										: v.tradeSucceedMoneySum;//  交易成功金额
								html += '</td>';

								html += '<td class="nobor">&nbsp;</td>';
								html += '</tr>';
								$('#displayTblCount').append(html);
								html = '<tr>';
								html += '<td class="nobor">&nbsp;</td>';
								html += '<td>'
								html += '交易失败合计';// 
								html += '</td>';
								
								html += '<td>'
								html += v.tradeErrorCountSum == null ? ''
										: v.tradeErrorCountSum;// 交易失败笔数
								html += '</td>';

								html += '<td>'
								html += v.tradeErrorMoneySum == null ? ''
										: v.tradeErrorMoneySum;//  交易失败金额 
								html += '</td>';

								html += '<td class="nobor">&nbsp;</td>';
								html += '</tr>';
								$('#displayTblCount').append(html);
								html = '<tr>';
								html += '<td class="nobor">&nbsp;</td>';
								html += '<td>'
								html += '可疑交易合计';
								html += '</td>';
								
								html += '<td>'
								html += v.doubtfulTradeCountSum == null ? ''
										: v.doubtfulTradeCountSum;// 可疑交易笔数
								html += '</td>';

								html += '<td>'
								html += v.doubtfulTradeMoneySum == null ? ''
										: v.doubtfulTradeMoneySum;//  可疑交易金额 
								html += '</td>';

								html += '<td class="nobor">&nbsp;</td>';
								html += '</tr>';
								$('#displayTblCount').append(html);
				} else {
					$('.null-box').show();
				}
			} else {
				$.messagerBox({
					type : 'error',
					title : "信息提示",
					msg : data.message
				});
			}

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
