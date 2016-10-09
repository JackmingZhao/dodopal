$(function() {
	findLoadOrderStatus();
	findCitys();
	findLoadOrderByPage();
	$('.page-navi').paginator({
		prefix : 'loadOrder',
		pageSizeOnChange : findLoadOrderByPage
	});
	initTranDialog();
});

function initTranDialog(){
	$("#txnAmtStart").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtEnd").bind('keyup', function() {
		clearNoNum($(this));
	});
}

// 查询公交卡信息
function findLoadOrderByPage(page) {
	// 订单编号
	var loadOrderNum = escapePeculiar($('#loadOrderNum').val());
	// 订单状态
	var loadOrderState = $('#loadOrderState').val();
	// 订单创建时间
	var startDate = $('#startDate').val();
	// 结束时间
	var endDate = $('#endDate').val();
	// 卡号
	var cardNo = escapePeculiar($('#cardNo').val());
	// 业务城市
	var cityCode = $('#cityCode').val();
	// 起始金额
	var txnAmtStart = $.trim($('#txnAmtStart').val());
	// 结束金额
	var txnAmtEnd = $.trim($('#txnAmtEnd').val());
	
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var loadOrderQuery = {
		loadOrderNum : loadOrderNum,
		loadOrderState : loadOrderState,
		startDate : startDate,
		endDate : endDate,
		cardNo : cardNo,
		cityCode : cityCode,
		txnAmtStart : txnAmtStart,
		txnAmtEnd : txnAmtEnd,
		page : page
	}
	
	compareTime(loadOrderQuery,startDate,endDate);
	compareMoney(loadOrderQuery,txnAmtStart,txnAmtEnd);
	ddpAjaxCall({
		url : "findLoadOrderByPage",
		data : loadOrderQuery,
		successFn : function(data) {
			if ('000000' == data.code) {
				var html = '';
				$('#loadOrderTbl tbody').empty();
				var i = 1;
				if (data.responseEntity.records && data.responseEntity.records.length > 0) {
					$(data.responseEntity.records).each(function(i, v) {
						i = i + 1;
						$('.null-box').hide();
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '<td style="word-break:break-all" >'
						html += v.orderNum;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.cityName;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.loadAmtView;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.customerPayAmtView;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.cardFaceNum;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.status;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.orderTime == null ? '' : formatDate(
								v.orderTime, 'yyyy-MM-dd HH:mm:ss');
						html += '</td>';

						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#loadOrderTbl').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color", '#f6fafe');
					pageSize = data.responseEntity.pageSize;
					pageNo = data.responseEntity.pageNo;
					totalPages = data.responseEntity.totalPages;
					rows = data.responseEntity.rows;
					$('.page-navi').paginator('setPage', pageNo, pageSize,totalPages, rows);
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

// 初始化加载订单状态
function findLoadOrderStatus() {
	ddpAjaxCall({
		url : "findProOrderStatus",
		successFn : function(data) {
			$.each(data, function(key, value) {
				$('#loadOrderState').append($("<option/>", {
					value : value.code,
					text : value.name
				}));
				selectUiInit();
			});
		}
	});
}

function findCitys() {
	ddpAjaxCall({
		url : "findCitys",
		successFn : function(data) {
			$.each(data, function(key, value) {
				$('#cityCode').append($("<option/>", {
					value : value.cityCode,
					text : value.cityName
				}));
			});
			selectUiInit();
		}
	});
}

/*校验金额*/
function clearNoNum(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数
	var text = obj.val();
}
function compareTime(query, beginDate, endDate) {
	if (beginDate != "" && endDate != "" && typeof (beginDate) != "undefined" && typeof (endDate) != "undefined") {
		var d1 = new Date(beginDate.replace(/\-/g, "\/"));
		var d2 = new Date(endDate.replace(/\-/g, "\/"));
		if (d1 >= d2) {
			var temp = endDate;
			endDate = beginDate;
			beginDate = temp;
			$('#startDate').val(beginDate)
			$('#endDate').val(endDate)
			query.startDate = beginDate;
			query.endDate = endDate;
		}
	}
}

function compareMoney(query, min, max) {
	if (min != "" && max != "" && parseFloat(min) > parseFloat(max)) {
		var temp = max;
		max = min;
		min = temp;
		$('#txnAmtStart').val(min);
		$('#txnAmtEnd').val(max);
		query.txnAmtStart = min;
		query.txnAmtEnd = max;
	}
	if (min != "" && max != "") {
		query.txnAmtStart = min;
		query.txnAmtEnd = max;
	}
	if (min != "") {
		query.txnAmtStart = min;
	}
	if (max != "") {
		query.txnAmtEnd = max;
	}
}

//导出一卡通订单表
function excelLoadOrder() {
	$.fileDownload('excelLoadOrder', {
		data: $('#queryLoadOrderForm').serialize(),
		failCallback: function(data) {
			var obj = JSON.parse(data);
			$.messagerBox({type: 'warn', title:"信息提示", msg: obj.message});
		}
	});
}