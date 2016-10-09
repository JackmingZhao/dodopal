$(function() {
	highlightTitle();
	$('.page-navi').paginator({
		prefix : 'cardRecord',
		pageSizeOnChange : findCardRecord
	});
	$('#cardRecordView').hide();
	findCardRecord();
});

function findCardRecord(page) {
	if (!page) {
		page = {
				pageNo 		: 1,
				pageSize 	: 10
		};
	}
	var query = {
			startDate	: $('#startDate').val(),
			endDate		: $('#endDate').val(),
			page		: page
	};
	var startDate = query.startDate;
	var endDate = query.endDate;
	compareTime(query, startDate, endDate);
	ddpAjaxCall({
		url : "findCardRecordByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#cardRecordTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+(v.orderDate == null ? '' : formatDate(v.orderDate,'yyyy-MM-dd HH:mm:ss'))+'</td>';
						html += '<td>'+fmoney(v.txnAmt/100.0, 2)+'</td>'
						html += '<td>'+(v.merName == null ? '' : v.merName)+'</td>';
						html += '<td>'+fmoney(v.befBal/100.0, 2)+'</td>';
						html += '<td>'+fmoney(v.blackAmt/100.0, 2) +'</td>';
						html += '<td>'+(v.cardFaceNo == null ? '' : v.cardFaceNo)+'</td>';
						html += '<td class="a-center">'
						html += '<a href="javascript:void(0);" class="text-icon" title="详情" onclick="findCardRecordView(\''
							  + v.type+v.orderNum + '\');"></a>';
						html += '</td>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#cardRecordTb').append(html);
					});
				}
				else {
					$('.null-box').show();
					$('.page-navi').paginator('setPage');
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('.page-navi select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}

function findCardRecordView(typeOrderNum) {
	ddpAjaxCall({
		url : "cardRecordView",
		data : typeOrderNum,
		successFn : function(data) {
			if (data.code == "000000") {
				loadCardRecord(data.responseEntity);
			}else {
				$.messagerBox({
					type : 'warn',
					title : "信息提示",
					msg : data.message
				});
			}
		}
	});
}

function loadCardRecord(data) {
	clearCardRecordView();
	$('#cardOrderStatus').html("订单状态: "+ data.statusStr);
	if(data.type == "CZ") {
		$('#type').html("交易类型: 充值");
	}else if(data.type == "XF") {
		$('#type').html("交易类型: 消费");
	}
	if (data.orderDate) {
		data.orderDate = formatDate(data.orderDate, 'yyyy-MM-dd HH:mm:ss');
		$('#orderDate').html("订单时间："+data.orderDate);
	}else{
		$('#orderDate').html("订单时间：");
	}
	$('#orderNum').html(data.orderNum); 							// 订单编号
	$('#txnAmt').html(fmoney(data.txnAmt/100.0, 2)); 				// 交易金额(元)
	$('#merName').html(data.merName == null ? '' : data.merName);	// 商户名称
	$('#befBal').html(fmoney(data.befBal/100.0, 2));				// 交易前卡余额（元）
	$('#blackAmt').html(fmoney(data.blackAmt/100.0, 2)); 			// 交易后卡余额（元）
	$('#cardFaceNo').html(data.cardFaceNo);							// 卡号
	
	$('#cardRecordMain').hide();
	$('#cardRecordView').show();
}

function clearCardRecordView() {
	$('#cardRecordViewTable tr td').html('');
	$('#cardRecordView ul li').html('');
}

function clearView() {
	$('#cardRecordMain').show();
	$('#cardRecordView').hide();
}

function compareTime(query, startDate, endDate) {
	 var d1 = new Date(startDate.replace(/\-/g, "\/")); 
	 var d2 = new Date(endDate.replace(/\-/g, "\/")); 

	  if(startDate!=""&&endDate!=""&&d1 >=d2) 
	 { 
	  var temp = endDate;
	  endDate = startDate;
	  startDate = temp;
	  $('#startDate').val(startDate)
	  $('#endDate').val(endDate)
	  query.startDate = startDate;
	  query.endDate = endDate;
	 }
}

function clearCardRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}

function fmoney(s, n) {
	n = (n > 0 && n <= 20) ? n : 2;
	if(s == '') {
		return '';
	}
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(),
	r = s.split(".")[1];
	t = "";
	for(i = 0; i < l.length; i ++ ) {  
	   t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	}  
	return t.split("").reverse().join("") + "." + r;  
}  


