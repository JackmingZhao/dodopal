$(function() {
	findTranOutStatus();
	findTranTypeStatus();
	if($('#tranTypeRecharge').val()!=''){
		$('#tranType').val("1")
		$('#tranTypeRecharge').val('')
	}
	
	findTranRecord();
	$('#transactionRecord').paginator({
						prefix : 'transactionRectransactionRecordTbordTb',
						pageSizeOnChange : findTranRecord
					});
	highlightTitle();
	$('[js="jiaoyi"]').click(function(){
		$('[js="jiaoyiBox"]').show();
	});
	
	
	$('#transactionRecordView').hide();
	initTranDialog();
});
function initTranDialog(){
	$("#realMinTranMoney").bind('keyup', function() {
		clearNoNum($(this));
	});
	$("#realMaxTranMoney").bind('keyup', function() {
		clearNoNum($(this));
	});
}

function findTranRecord(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
				createDateStart:$('#createDateStart').val(),
				createDateEnd:$('#createDateEnd').val(),
				payWay:escapePeculiar($.trim($('#payWay').val())),
				tranType:$('#tranType').val(),
				realMinTranMoney:escapePeculiar($.trim($('#realMinTranMoney').val())),
				realMaxTranMoney:escapePeculiar($.trim($('#realMaxTranMoney').val())),
				tranOutStatus:$('#tranOutStatus').val(),
				orderNumber :escapePeculiar($.trim($('#orderNumber').val())),
				page:page,
	}
	var beginDate = query.createDateStart;
	var endDate = query.createDateEnd;
	var min = query.realMinTranMoney;
	var max = query.realMaxTranMoney;
	compareTime(query,beginDate,endDate);
	compareMoney(query,min,max);
	ddpAjaxCall({
		url : "findPayTraTransactionByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#transactionRecordTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+v.tranCode+'</td>';
						html += '<td>'+v.businessType+'</td>'
						html += '<td>'+fmoney(v.realTranMoney,2)+'</td>';
						html += '<td>'+(v.payWayName == null ? '' : v.payWayName)+'</td>';
						html += '<td>'+(v.tranType == null ? '' : v.tranType) +'</td>';
						html += '<td>'+v.tranOutStatus+'</td>';
						html += '<td>'+(v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd HH:mm:ss'))+'</td>';
						
						html += '<td class="a-center">'
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findTranscationRecordView(\''
							+ v.tranCode + '\');"></a>';
						html += '</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#transactionRecordTb').append(html);
					});
				}
				 else {
						$('.null-box').show();
					}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				//$('#transactionRecord select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
	
	
}

//查看交易记录详情
function findTranscationRecordView(tranCode) {
	ddpAjaxCall({
				url : "tranView",
				data : tranCode,
				successFn : function(data) {
					if (data.code == "000000") {
						loadTranRecord(data.responseEntity);
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
function cleartranRecordView() {
	$('#tranRecordViewTable tr td').html('');
	$('#detailTranOutStatus').html('');
	$('#transactionRecordView ul li').html('');
}

//加载交易记录信息
function loadTranRecord(data) {
	cleartranRecordView();
	// 交易状态
	$('#detailTranOutStatus').html(data.tranOutStatus);
	// 订单时间
	if (data.orderDate) {
		data.orderDate = formatDate(data.orderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#orderDate').html("订单时间："+data.orderDate);
	}else{
		$('#orderDate').html("订单时间：");
	}
	// 交易时间
	if (data.createDate) {
		data.createDate = formatDate(data.createDate, 'yyyy-MM-dd HH:mm:ss');
		$('#createDate').html("交易时间："+data.createDate);
	}else{
		$('#createDate').html("交易时间："+data.createDate);
	}
	// 备注
	if (data.comments) {
		$('#comments').html("备注："+data.comments);
	}else{
		$('#comments').html("备注：");
	}
	// 交易流水号
	$('#tranCodeView').html(data.tranCode);
	// 订单编号
	$('#orderNumberView').html(data.orderNumber);
	// 交易类型
	$('#detailTrantypeView').html(data.tranType);
	// 业务名称
	$('#businessTypeView').html(data.businessType);
	
	// 应付金额（元）
	$('#amountMoneyView').html(data.amountMoney);
	// 实付金额（元）
	$('#realTranMoneyView').html(data.realTranMoney);
	
	$('#tranRecordMain').hide();
	$('#transactionRecordView').show();
	
}

//查看界面返回
function clearView() {
	$('#tranRecordMain').show();
	$('#transactionRecordView').hide();
}

function compareTime(query,beginDate,endDate){
	 var d1 = new Date(beginDate.replace(/\-/g, "\/")); 
	 var d2 = new Date(endDate.replace(/\-/g, "\/")); 

	  if(beginDate!=""&&endDate!=""&&d1 >=d2) 
	 { 
	  var temp = endDate;
	  endDate = beginDate;
	  beginDate = temp;
	  $('#createDateStart').val(beginDate)
	  $('#createDateEnd').val(endDate)
	  query.createDateStart = beginDate;
	  query.createDateEnd = endDate;
	 }
}

function compareMoney(query,min,max){
	if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		console.log("=========min="+min+"max="+max);
		$('#realMinTranMoney').val(min);
		$('#realMaxTranMoney').val(max);
		query.realMinTranMoney = min;
		query.realMaxTranMoney = max;
	}
}

function clearTranRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}


//导出
//function exportTraified(){
//   // $("#recordForm").submit();
//    $.fileDownload('tranRecordExport', {
//		data: $('#recordForm').serialize(),
//		failCallback: function() {
//			msgShow(systemWarnLabel, "文件导出出错", 'warning');
//		}
//	})
//}


//格式化金额 s-金额 n-保留小数个数
function fmoney(s, n)  
{  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
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
//	if (text.indexOf(".") < 0) {
//		var textChar = text.charAt(text.length - 1);
//		alert(text.length)
//		if (text.length >= 5  && textChar !=".") {
//			obj.val(parseFloat(text.substring(0,4)));  
//		}
//	} else {
//		var text01 = text.substring(0,text.indexOf("."));
//		var text02 = text.substring(text.indexOf("."),text.length);
//		if (text01.length > 4) {
//			text01 = text01.substring(0,text01.length-1);
//		}
//		var text = text01+text02;
//		if (text.length > 7) {
//			text = "";
//		}
//		obj.val(text);
//	}
}


//初始化加载交易状态
function findTranOutStatus() {
	$.ajax({
				async : false,
				type : 'post',
				url : 'findTranOutStatus',
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
								$('#tranOutStatus').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}
//初始化加载交易类型
function findTranTypeStatus() {
	$.ajax({
				async : false,
				type : 'post',
				url : 'findTranTypeStatus',
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
								$('#tranType').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}