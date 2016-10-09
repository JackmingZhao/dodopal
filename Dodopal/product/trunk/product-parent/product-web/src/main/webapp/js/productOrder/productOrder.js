$(function() {
			$('.page-navi').paginator({
						prefix : 'productOrder',
						pageSizeOnChange : findProductOrderByPage
					});
			$('#productOrderView').hide();
			highlightTitle();
			$('.header-inr-nav ul li a').each(function(){
					if($.trim($(this).text())=="订单查询"){
						$(this).addClass('cur');
					}
				});
	initMainProduct();
		getOtherOCX("73E062B2-BF5F-4952-8BE2-29387DFEBC74",
				"1,0,0,2","DDPrint","打印机控件加载失败! -- 请检查浏览器的安全级别设置.","OCXPRRINTER","OCXPRRINTER");
		});
function initMainProduct(){
	findProductOrderStatus();
	findProductOrderByPage();
	initTranDialog();
}
function initTranDialog(){
	$("#txnAmtStart").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtEnd").bind('keyup', function() {
		clearNoNum($(this));
	});
}
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
	var txnAmtStart = $.trim($('#txnAmtStart').val());
	var txnAmtEnd = $.trim($('#txnAmtEnd').val());
	// 商户用于充值的POS机编号，仅适用于商户。
	var posCode = $.trim($('#posCode').val());
	// 外部订单号 文本框 精确 用户输入 仅在商户类型为外接商户的时候显示。
	var merOrderNum = $.trim($('#merOrderNum').val());
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
	var beginDate = prdOrderQuery.orderDateStart;
	var endDate = prdOrderQuery.orderDateEnd;
	var min = prdOrderQuery.txnAmtStart;
	var max = prdOrderQuery.txnAmtEnd;
	compareTime(prdOrderQuery,beginDate,endDate);
	compareMoney(prdOrderQuery,min,max);
	ddpAjaxCall({
		url : "findProductOrderByPage",
		data : prdOrderQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#productOrderTbl tbody').empty();
				//var i = 1;
				if (data.responseEntity.records
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records).each(function(i, v) {
						//i = i + 1;
						$('.null-box').hide();
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';

						html += '<td>'
						html += ++i+(data.responseEntity.pageNo-1)*data.responseEntity.pageSize;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.proOrderNum;
						html += '</td>';
						if($.merType=='18'){
							html += '<td style="word-break:break-all" >'
							html += v.merOrderNum == null
								? ''
								: v.merOrderNum;
							html += '</td>';
					    }
						
						html += '<td style="word-break:break-all" >'
						html += v.cityName;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.showBefbal;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.showTxnAmt;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.showReceivedPrice;
						html += '</td>';


						html += '<td style="word-break:break-all" >'
						html += v.showBlackAmt == null
								? ''
								: v.showBlackAmt;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.showMerGain;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.posCode;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.orderCardno;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.proOrderStateView == null
								? ''
								: v.proOrderStateView;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.proOrderDate == null ? '' : formatDate(
								v.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.posComments == null? '': htmlTagFormat(v.posComments);
						html += '</td>';
						
						html += '<td class="a-center">'
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findProductOrderByCode(\''
								+ v.proOrderNum + '\');"></a>';
						if(v.proOrderStateView=='充值成功'){
							html += '<a href="javascript:void(0);" class="text-icon print-icon01" title="打印小票" onclick="ConsumePrint(\''
								+ v.proOrderNum + '\');"></a>';
						}else{
							html += '<a href="javascript:void(0);" class="text-icon print-icon02" title="不可打印" ></a>';
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
					$('.page-navi').paginator('setPage', pageNo, pageSize, totalPages, rows);
					$('.page-navi select').attr("style","width:45px;padding:0px 0px");
				} else {
					$('.null-box').show();
					$('.page-navi').paginator('setPage');
				}
			}else {
				$.messagerBox({type: 'error', title:"信息提示", msg: ""+data.message+"",confirmOnClick:closeWebPage});
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
	$('#proOrderNumSpan').html(data.proOrderNum);
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
	$('#txnAmtSpan').html(data.showTxnAmt);
	// 成本价
	$('#merPurchaasePriceSpan').html(data.showMerPurchaasePrice);
	// 实付金额
	$('#receivedPriceSpan').html(data.showReceivedPrice);
	// 商户利润
	$('#merGainSpan').html(data.showMerGain);
	// 充值前金额
	$('#befbalSpan').html(data.showBefbal);
	// 充值后金额
	$('#blackAmtSpan').html(data.showBlackAmt);
	// 充值前账户可用余额 TODO 后期改
	// $('#merUserIdentityTypeSpan').html();
	// 充值后账户可用余额 TODO 后期改
	// $('#merUserIdentityNumberSpan').html();
	// POS编码
	$('#posCodeSpan').html(data.posCode);
	// POS备注 TODO 无此字段
	// $('#merBankNameSpan').html(findBankNumber(data.merBankName));
	// 订单状态
	$('#proOrderStateSpan').html(data.proOrderStateView);
	// 操作员
	$('#userNameSpan').html(data.userName);
	// 订单时间
	if (data.proOrderDate) {
		data.proOrderDate = formatDate(data.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#proOrderDateSpan').html(data.proOrderDate);
	}
	// 外部订单号
	$('#merOrderNumSpan').html(data.merOrderNum);
	// pos备注
	$('#posCommentsSpan').html(data.posComments);
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
	$.ajax({
				async : false,
				type : 'post',
				url : 'findProductOrderStatus',
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
								$('#proOrderState').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}

// 导出一卡通订单表
//function excelProductOrder() {
//	$.fileDownload('excelProductOrderSet', {
//		data: $('#queryProductOrderForm').serialize(),
//		failCallback: function() {
//			msgShow(systemWarnLabel, "文件导出出错", 'warning');
//		}
//	});
//}
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
function compareTime(query,beginDate,endDate){
	if(beginDate!=""&& endDate!="" &&typeof(beginDate)!="undefined" && typeof(endDate)!="undefined"){
	var d1 = new Date(beginDate.replace(/\-/g, "\/"));
	var d2 = new Date(endDate.replace(/\-/g, "\/"));
	if(d1 >=d2)
		{
			var temp = endDate;
			endDate = beginDate;
			beginDate = temp;
			$('#orderDateStart').val(beginDate)
			$('#orderDateEnd').val(endDate)
			query.orderDateStart = beginDate;
			query.orderDateEnd = endDate;
		}
	}
}
//转换卡金额
function compareMoney(query,min,max){
if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		$('#txnAmtStart').val(min);
		$('#txnAmtEnd').val(max);
		query.txnAmtStart = min*100;
		query.txnAmtEnd = max*100;
	}
	if(min!=""&& max != ""){
		query.txnAmtStart = min*100;
		query.txnAmtEnd = max*100;
	}
	if(min!=""){
		query.txnAmtStart = min*100;
	}
	if(max != ""){
		query.txnAmtEnd = max*100;
	}
}

//----------------------------一卡通充值打印小票功能-------------------------------
function toPrinter(){
	try{
		var str = createConsumePrintMapStr();
		ocxPrint(str);
	}catch(ex){
	   console.log(ex.description);
	}
}

function createConsumePrintMapStr(){
	var line = "@1234567890----------------------";
	var printWarnMsg = "@1234567890客服电话：400-817-1000@1234567890请妥善保管小票。";
	var head = "@1234567890@1234567890@1234567890@1234567890都都宝-交易凭证";
	var foot = "@1234567890@1234567890@1234567890@1234567890";
	var printTime = "@1234567890打印时间："+formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
	var body = "";
	var merInfo = "";
	$("#printSpan li").each(function(i, value){
		if($(this).text().length>90){
			var arr = $(this).text().split("：");	
			body+="@1234567890"+arr[0]+"：@1234567890"+arr[1];
		}else{
			body+="@1234567890"+$(this).text();
		}
		
	});
	return head+line+body+line+printTime+printWarnMsg+foot;
}


function ConsumePrint(proOrderNum){
	ddpAjaxCall({
		url : "findProductOrderByCode",
		data : proOrderNum,
		successFn : function(data) {
			if (data.code == "000000") {
				loadProductConsumeOrdertoPrinter(data.responseEntity);
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



function loadProductConsumeOrdertoPrinter(data){
	// 商户名称
	$('#merNameRes').html(data.merName);
	// 通卡名称
	$('#yktNameRes').html(data.yktName);
	// 订单编号
	$('#proOrderNumRes').html(data.proOrderNum);
	// 充值前金额
	$('#showBefbalRes').html(data.showBefbal);
	// 充值金额
	$('#showTxnAmtRes').html(data.showTxnAmt);
	// 实付金额
	$('#receivedPrice').html(data.showReceivedPrice);
	// 充值后金额
	$('#showBlackAmtRes').html(data.showBlackAmt);
	// POS编码
	$('#posCodeRes').html(data.posCode);
	// 卡号
	$('#orderCardnoRes').html(data.orderCardno);
	// 订单时间
	if (data.proOrderDate) {
		data.proOrderDate = formatDate(data.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#proOrderDateRes').html(data.proOrderDate);
	}
	// 订单状态
	$('#proOrderStateRes').html(data.proOrderStateView);
	toPrinter();
}



