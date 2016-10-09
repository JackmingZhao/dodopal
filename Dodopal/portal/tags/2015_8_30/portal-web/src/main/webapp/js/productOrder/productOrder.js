$(function() {
			findProductOrderStatus();
			findProductOrderByPage();
			$('.page-navi').paginator({
						prefix : 'productOrder',
						pageSizeOnChange : findProductOrderByPage
					});
			highlightTitle();
		});

// 查询公交卡信息
function findProductOrderByPage(page) {
	// 订单编号
	var proOrderNum = $('#proOrderNum').val();
	// 订单状态
	var proOrderState = $('#proOrderState').val();
	// 订单创建时间
	var orderDateStart = $('#orderDateStart').val();
	var orderDateEnd = $('#orderDateEnd').val();
	// 卡号
	var orderCardno = $('#orderCardno').val();
	// 业务城市
	var cityName = $('#cityName').val();
	// 充值金额
	var txnAmtStart = $('#txnAmtStart').val();
	var txnAmtEnd = $('#txnAmtEnd').val();
	// 商户用于充值的POS机编号，仅适用于商户。
	var posCode = $('#posCode').val();
	// 外部订单号 文本框 精确 用户输入 仅在商户类型为外接商户的时候显示。
	var merOrderNum = $('#merOrderNum').val();

	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var prdOrderQuery = {
		proOrderNum : proOrderNum,
		proOrderState : proOrderState,
		orderDateStart : orderDateStart,
		orderDateEnd : orderDateEnd,
		orderCardno : orderCardno,
		cityName : cityName,
		txnAmtStart : txnAmtStart,
		txnAmtEnd : txnAmtEnd,
		posCode : posCode,
		merOrderNum : merOrderNum,
		page : page
	}
	ddpAjaxCall({
		url : "findProductOrderByPage",
		data : prdOrderQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#productOrderTbl tbody').empty();
				var i = 1;
				if (data.responseEntity.records
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records).each(function(i, v) {
						i = i + 1;
						$('.null-box').hide();
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';

						html += '<td style="word-break:break-all" >'
						html += v.proOrderNum;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.cityName;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.txnAmt;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.receivedPrice;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.befbal;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.blackAmt;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.merGain;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.posCode;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.orderCardno;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.proOrderState;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.proOrderDate == null ? '' : formatDate(
								v.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
						html += '</td>';

						html += '<td class="a-center">'
						if (hasPermission('app.order.recharge.export')) {
							html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findProductOrderByCode(\''
									+ v.proOrderNum + '\');"></a>';
						}
						html += '</td>';

						html += '<td class="nobor">&nbsp;</td>';
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
// 查看一卡通充值订单（标准）详情
function findProductOrderByCode(proOrderNum) {
	ddpAjaxCall({
				url : "findProductOrderByCode",
				data : proOrderNum,
				successFn : function(data) {
					console.log(data);
					if (data.code == "000000") {
						loadProductOrder(data.responseEntity);
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
// 清空子商户详情界面界面信息
function clearProductOrderView() {
	$('#productOrderViewTable tr td span').html('');
}
// 加载子商户信息
function loadProductOrder(data) {
	clearProductOrderView();
	// 订单编号
	$('#proOrderNumSpan').val(data.proOrderNum);
	// 商户名称
	$('#merNameSpan').html(data.merName);
	// 产品编号
	$('#proCodeSpan').html(data.proCode);
	// 产品名称
	$('#proNameSpan').html(data.proName);
	// 城市
	$('#cityNameSpan').html(data.cityName);
	// 卡号
	$('#orderCardnoSpan').html(data.orderCardno);
	// 充值金额
	$('#txnAmtSpan').html(data.txnAmt);
	// 成本价
	$('#merPurchaasePriceSpan').html(data.merPurchaasePrice);
	// 实付金额
	$('#receivedPriceSpan').html(data.receivedPrice);
	// 商户利润
	$('#merGainSpan').html(data.merGain);
	// 充值前金额
	$('#befbalSpan').html(data.befbal);
	// 充值后金额
	$('#blackAmtSpan').html(data.blackAmt);
	// 充值前账户可用余额 TODO 后期改
	// $('#merUserIdentityTypeSpan').html();
	// 充值后账户可用余额 TODO 后期改
	// $('#merUserIdentityNumberSpan').html();
	// POS编码
	$('#posCodeSpan').html(data.posCode);
	// POS备注 TODO 无此字段
	// $('#merBankNameSpan').html(findBankNumber(data.merBankName));
	// 订单状态
	$('#proOrderStateSpan').html(data.proOrderState);
	// 操作员
	$('#userNameSpan').html(data.userName);
	// 订单时间
	if (data.proOrderDate) {
		data.proOrderDate = formatDate(data.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#proOrderDateSpan').html(data.proOrderDate);
	}
	// 外部订单号
	$('#merOrderNumSpan').html(data.merOrderNum);
	// 主界面关闭，详情界面打开
	$('#productOrderMain').hide();
	$('#productOrderView').show();
}

// 查看界面返回
function clearView(domainName) {
	$('#' + domainName + 'Main').show();
	$('#' + domainName + 'View').hide();
}

// 初始化加载订单状态
function findProductOrderStatus() {
	ddpAjaxCall({
				url : "findProductOrderStatus",
				successFn : function(data) {
					$.each(data, function(key, value) {
								$('#proOrderState').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}