$(function() {
	    findProductConsumeOrder();
		$('.page-navi').paginator({prefix : 'productConsumeOrder',pageSizeOnChange : findProductConsumeOrder});
		$('#productConsumeOrderView').hide();
		highlightTitle();
		initTranDialog();
		findProductOrderStatus();
		getOtherOCX("73E062B2-BF5F-4952-8BE2-29387DFEBC74",
				"1,0,0,2","DDPrint","打印机控件加载失败! -- 请检查浏览器的安全级别设置.","OCXPRRINTER","OCXPRRINTER");
});


function initTranDialog(){
	$("#txnAmtStart").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtStart").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#txnAmtStart").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
	
	$("#txnAmtEnd").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#txnAmtEnd").bind('keydown', function() {
		checkDecimal($(this), 1, 9, 0, 2);
	});
	$("#txnAmtEnd").bind('blur', function() {
		clearNoNumOnBlur($(this));
	});
}
// 查询公交卡信息
function findProductConsumeOrder(page) {
	// 订单编号
	var orderNum = escapePeculiar($.trim($('#orderNum').val()));
	// 订单状态
	var states = $('#states').val();
	// 订单创建时间
	var orderDateStart = $('#orderDateStart').val();
	var orderDateEnd = $('#orderDateEnd').val();
	// 卡号
	var cardNum = escapePeculiar($.trim($('#cardNum').val()));
	// 业务城市
	var cityName = escapePeculiar($.trim($('#cityName').val()));
	// 充值金额
	var txnAmtStart = $('#txnAmtStart').val();
	var txnAmtEnd = $('#txnAmtEnd').val();
	// 商户用于充值的POS机编号，仅适用于商户。
	var posCode = escapePeculiar($.trim($('#posCode').val()));
	
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var prdOrderQuery = {
			
		  orderNum : orderNum,
		  orderDateStart : orderDateStart,
		  orderDateEnd : orderDateEnd,
		  cardNum : cardNum,
		  cityName : cityName,
		  txnAmtStart : txnAmtStart,
		  txnAmtEnd : txnAmtEnd,
		  states:states,
		  posCode : posCode,
		  page : page
	}
	var beginDate = prdOrderQuery.orderDateStart;
	var endDate = prdOrderQuery.orderDateEnd;
	var min = prdOrderQuery.txnAmtStart;
	var max = prdOrderQuery.txnAmtEnd;
	compareTime(prdOrderQuery,beginDate,endDate);
	compareMoney(prdOrderQuery,min,max);
	ddpAjaxCall({
		url : "findProductConsumeOrder",
		data : prdOrderQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#productConsumeOrderTbl tbody').empty();
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
						html += v.orderNum;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += htmlTagFormat(v.cityName == null ? '' : v.cityName);
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += htmlTagFormat(v.cardNum == null ? '' :v.cardNum);
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.posCode == null ? '' :v.posCode;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.originalPrice == null?'' :v.originalPrice;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.receivedPrice == null
								? '': v.receivedPrice;
						html += '</td>';

						
						html += '<td style="word-break:break-all" >'
						html += v.befbal == null?'' :v.befbal;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.blackAmt == null?'' : v.blackAmt;
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.states == null
								? ''
								: htmlTagFormat(v.states);
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.proOrderDate == null ? '' : formatDate(
									v.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
						html += '</td>';
						
						html += '<td style="word-break:break-all" >'
						html += v.comments == null
									? ''
									: htmlTagFormat(v.comments);
						html += '</td>';
						
						html += '<td class="a-center">'
							
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findproductConsumeOrderByCode(\''
								+ v.orderNum + '\');"></a>';
						
						if(v.states=='支付成功'){
							html += '<a href="javascript:void(0);" class="text-icon print-icon01" title="打印小票" onclick="ConsumePrint(\''
								+ v.orderNum + '\');"></a>';
						}else{
							html += '<a href="javascript:void(0);" class="text-icon print-icon02" title="不可打印" ></a>';
						}
						html += '</td>';

						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#productConsumeOrderTbl').append(html);
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
			} else {
				$.messagerBox({type: 'error', title:"信息提示", msg: ""+data.message+"",confirmOnClick:closeWebPage});
			}

		}
	});
}


//初始化加载订单状态
function findProductOrderStatus() {
	$.ajax({
				async : false,
				type : 'post',
				url : 'findProductConsumeOrderStatus',
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
								$('#states').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}



//查看一卡通消费  订单（标准）详情
function findproductConsumeOrderByCode(proOrderNum) {
	ddpAjaxCall({
				url : "findProductConsumeOrderByCode",
				data : proOrderNum,
				successFn : function(data) {
					if (data.code == "000000") {
						loadProductConsumeOrder(data.responseEntity);
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


//清空子商户详情界面界面信息
function clearProductConsumeOrderView() {
	$('#productConsumeOrderView tr td span').html('');
}


//加载子商户信息
function loadProductConsumeOrder(data) {
	clearProductConsumeOrderView();
	// 订单编号
	$('#orderNumSpan').html(data.orderNum);
	// 商户名称
	$('#merNameSpan').html(data.merName);
	// 城市
	$('#cityNameSpan').html(data.cityName);
	// 通卡公司名称
	$('#yktNameSpan').html(data.yktName);
	
	// POS号
	$('#posCodeSpan').html(data.posCode);
	// 卡号
	$('#cardNumSpan').html(data.cardNum);
	
	// 应收金额
	$('#originalPriceSpan').html(data.originalPrice==null?"" :data.originalPrice);
	// 实收金额
	$('#receivedPriceSpan').html(data.receivedPrice ==null ? "" :data.receivedPrice);

	// 消费前金额
	$('#befbalSpan').html(data.befbal==null?"":data.befbal);
	// 消费后金额
	$('#blackAmtSpan').html(data.blackAmt==null?"":data.blackAmt);
	
	// 商户服务费率
	$('#serviceRateSpan').html(data.serviceRate);
	
	// 商户服务费率类型
	$('#serviceRateTypeSpan').html(data.serviceRateType);
	
	//商户折扣
	$('#merDiscountSpan').html(data.merRate==null ? "" :data.merRate +"折");
	// 订单状态
	$('#stateSpan').html(data.states);
	
	// 操作员
	$('#userNameSpan').html(data.userName);
	// 订单时间
	if (data.proOrderDate) {
		data.proOrderDate = formatDate(data.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#proOrderDateSpan').html(data.proOrderDate);
	}
	// pos备注
	$('#posCommentsSpan').html(data.posComments);
	
	// 主界面关闭，详情界面打开
	$('#productConsumeOrderMain').hide();
	$('#productConsumeOrderView').show();
	
}



////导出一卡通消费 收单记录
//function excelproductConsumeOrder() {
//	$.fileDownload('excelproductConsumeOrder', {
//		data: $('#queryproductConsumeOrderForm').serialize(),
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
	var d1 = new Date(beginDate.replace(/\-/g, "\/"));
	var d2 = new Date(endDate.replace(/\-/g, "\/"));

	if(beginDate!=""&&endDate!=""&&d1 >=d2)
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

function compareMoney(query,min,max){
	if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		$('#txnAmtStart').val(min);
		$('#txnAmtEnd').val(max);
		query.txnAmtStart = min;
		query.txnAmtEnd = max;
	}
}




/*校验金额*/
//function clearNoNum(obj) {
//	var rate = obj.val();
//	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
//	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.
//	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
//	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
//	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数
//	var text = obj.val();
//}


/* 以下为校验费率js */
function clearNoNumOnBlur(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		} else {
			var textChar = text.charAt(text.length - 1);
			if (textChar == ".") {
				text = text.substring(0, text.length - 1);
			}
		}
		obj.val(text);
	}
}

function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
	var rate = obj.val();
	var re;
	var posNeg;
	re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
			+ startDec + "," + endDec + "})?$");
	posNeg = /^[+]?]*$/;

	if (!re.test(rate) && !posNeg.test(rate)) {
		rate = "";
		obj.focus();
		return false;
	}
}

function clearNoNum(obj) {
	var rate = obj.val();
	rate = rate.replace(/[^\d.]/g, ""); // 清除“数字”和“.”以外的字符
	rate = rate.replace(/^\./g, ""); // 验证第一个字符是数字而不是.
	rate = rate.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
	rate = rate.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));// 只能输入两个小数
	var text = obj.val();
	if (text.indexOf(".") < 0) {
		if(text.length >= 2 ){
		    var textchar0 = text.charAt(0);
		    var textchar1 = text.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	obj.val(parseFloat("0"));
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	obj.val(parseFloat(textchar1));
		    }
		}
		var textChar = text.charAt(text.length - 1);
		if (text.length >= 8 && textChar != ".") {
			obj.val(parseFloat(text.substring(0, 8)));
		}
	} else {
		var text01 = text.substring(0, text.indexOf("."));
		var text02 = text.substring(text.indexOf("."), text.length);
		if(text01.length >= 2 ){
		    var textchar0 = text01.charAt(0);
		    var textchar1 = text01.charAt(1);
		    if(textchar0=='0'&& textchar1=='0'){
		    	text01='0';
		    }
		    if(textchar0=='0'&& textchar1!='0'){
		    	text01=textchar1;
		    }
		}
		if (text01.length > 8) {
			text01 = text01.substring(0, text01.length - 1);
		}
		var text = text01 + text02;
		if (text.length > 11) {
			text = "";
		}
		obj.val(text);
	}
	
}


//查看界面返回
function clearView(domainName) {
	$('#' + domainName + 'Main').show();
	$('#' + domainName + 'View').hide();
}


function toPrinter(){
	try{
		var str = createConsumePrintMapStr();
		ocxPrint(str);
		//location.reload(true); 
	}catch(ex){
	   console.log(ex.description);
	}
}

function createConsumePrintMapStr(){
	var line = "@1234567890----------------------";
	var printWarnMsg = "@1234567890客服电话：400-817-1000@1234567890请妥善保管小票，请及时充值以确@1234567890保您的消费需求";
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
		url : "findProductConsumeOrderByCode",
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
	$('#merName').html(data.merName);
	// 订单编号
	$('#printOrderNumRes').html(data.orderNum);
	
	//商户折扣
	$('#printPayDiscountSpan').html(data.merRate==null ? "" :data.merRate +"折");
	
	// 应收金额
	$('#printSMoneySpan').html(data.originalPrice==null?"" :data.originalPrice);
	// 实收金额
	$('#printRMoneySpan').html(data.receivedPrice ==null ? "" :data.receivedPrice);	
	
	// POS号
	$('#printPosCode').html(data.posCode);
	// 卡号
	$('#printCardCode').html(data.cardNum);
	
	// 消费前金额（原有金额）
	$('#printBeforCardMoneySpan').html(data.befbal==null?"":data.befbal);
	// 消费后金额（卡内余额）
	$('#printCardMoneySpan').html(data.blackAmt==null?"":data.blackAmt);
	
	
	// 订单时间
	if (data.proOrderDate) {
		data.proOrderDate = formatDate(data.proOrderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#printConsumeTime').html(data.proOrderDate);
	}
	// 订单状态
	$('#printConsumestate').html(data.states);
	
	
	toPrinter();
	
}

