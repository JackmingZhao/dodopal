$(function() {
	findChangeRecord();
	findChangeType();
	$('#accountChange').paginator({
						prefix : 'accountChangeTB',
						pageSizeOnChange : findChangeRecord
					});
			highlightTitle();
			initTranDialog();
			$('.header-inr-nav ul li a').each(function(){
				if($.trim($(this).text())=="资金变更记录"){
					$(this).addClass('cur');
				}
			});
});

function initTranDialog(){
	$("#changeAmountMin").bind('keyup', function() {
		clearNoNum($(this));
	});
//	$("#changeAmountMin").bind('keydown', function() {
//		checkDecimal($(this),1,9,0,2);
//	});
//	$("#changeAmountMin").bind('blur', function() {
//		clearNoNumOnBlur($(this));
//	});
	$("#changeAmountMax").bind('keyup', function() {
		clearNoNum($(this));
	});
//	$("#changeAmountMax").bind('keydown', function() {
//		checkDecimal($(this),1,9,0,2);
//	});
//	$("#changeAmountMax").bind('blur', function() {
//		clearNoNumOnBlur($(this));
//	});
}

function findChangeRecord(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
				startDate:$('#startDate').val(),
				endDate:$('#endDate').val(),
				changeType:$('#changeType').val(),
				changeAmountMin:escapePeculiar($.trim($('#changeAmountMin').val())),
				changeAmountMax:escapePeculiar($.trim($('#changeAmountMax').val())),
				page:page,
	}
	var beginDate = query.startDate;
	var endDate = query.endDate;
	var min = query.changeAmountMin;
	var max = query.changeAmountMax;
	compareTime(query,beginDate,endDate);
	compareMoney(query,min,max);
	ddpAjaxCall({
		url : "find",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#accountChangeTB tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+(v.tranCode == null ? '' : v.tranCode)+'</td>';
						html += '<td>'+(v.fundType == null ? '' : v.fundType)+'</td>';
						html += '<td>'+(v.changeType == null ? '' : v.changeType) +'</td>';
						html += '<td>'+fmoney(v.changeAmount,2)+'</td>';
						html += '<td>'+fmoney(v.beforeChangeAvailableAmount,2)+'</td>';
						html += '<td>'+fmoney(v.beforeChangeFrozenAmount,2)+'</td>';
						html += '<td>'+fmoney(v.afterChangeAvailableAmount,2)+'</td>';
						html += '<td>'+fmoney(v.afterChangeFrozenAmount,2)+'</td>';
						html += '<td>'+(v.createDate == null ? '' : formatDate(v.createDate,'yyyy-MM-dd HH:mm:ss'))+'</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#accountChangeTB').append(html);
					});
				}
				else{
					$('.null-box').show();
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('#accountChange').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#accountChange select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
}

function compareTime(query,beginDate,endDate){
	 var d1 = new Date(beginDate.replace(/\-/g, "\/")); 
	 var d2 = new Date(endDate.replace(/\-/g, "\/")); 

	  if(beginDate!=""&&endDate!=""&&d1 >=d2) 
	 { 
	  var temp = endDate;
	  endDate = beginDate;
	  beginDate = temp;
	  $('#startDate').val(beginDate)
	  $('#endDate').val(endDate)
	  query.startDate = beginDate;
	  query.endDate = endDate;
	 }
}

function compareMoney(query,min,max){
	if(min != "" && max != "" && parseFloat(min)>parseFloat(max)){
		var temp = max;
		max = min;
		min = temp;
		$('#changeAmountMin').val(min);
		$('#changeAmountMax').val(max);
		query.changeAmountMin = min;
		query.changeAmountMax = max;
	}
}

function clearAccountRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}

//导出
//function exportAmtfiled(){
//   // $("#accountForm").submit();
//	$.fileDownload('ddpAmtExport', {
//		data: $('#accountForm').serialize(),
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
//function clearNoNumOnBlur(obj) { 
//	var rate = obj.val();
//	rate = rate.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
//	rate = rate.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
//	rate = rate.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
//	rate = rate.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
//	obj.val(rate.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));//只能输入两个小数  
//	var text = obj.val();
//	if (text.indexOf(".") < 0) {
//		var textChar = text.charAt(text.length - 1);
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
//		} else {
//			var textChar = text.charAt(text.length - 1);
//			if (textChar ==".") {
//				text = text.substring(0,text.length-1);  
//			}
//		}
//		obj.val(text);
//	}
//}  
//
//function checkDecimal(obj, startWhole, endWhole, startDec, endDec) {
//	var rate = obj.val();
//	var re;
//	var posNeg;
//		re = new RegExp("^[+]?\\d{" + startWhole + "," + endWhole + "}(\\.\\d{"
//				+ startDec + "," + endDec + "})?$");
//		posNeg = /^[+]?]*$/;
//	
//	if (!re.test(rate) && !posNeg.test(rate)) {
//		rate = "";
//		obj.focus();
//		return false;
//	}
//}

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

//初始化加载变动类型
function findChangeType() {
	$.ajax({
				async : false,
				type : 'post',
				url : 'findChangeType',
				dataType : "json",
				success : function(data) {
					$.each(data, function(key, value) {
								$('#changeType').append($("<option/>", {
											value : value.code,
											text : value.name
										}));
							});

				}
			});
}