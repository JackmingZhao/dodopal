$(function() {
	initQueryDate();
	findProxyCardAdd();
	$('#proxyCardAddPage').paginator({
						prefix : 'proxyCardAddTb',
						pageSizeOnChange : findProxyCardAdd
					});
	highlightTitle();
	getOtherOCX("73E062B2-BF5F-4952-8BE2-29387DFEBC74",
			"1,0,0,2","DDPrint","打印机控件加载失败! -- 请检查浏览器的安全级别设置.","OCXPRRINTER","OCXPRRINTER");
	
	
});


function findProxyCardAdd(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	
	var timeRadio = '';
	$('#timeRadio input:radio').each(function(key,value){
		 if($(value).prop('checked')){
			 var radiovalue = $(value).val();
			 if(radiovalue==7){
				 timeRadio = 7;
			 }else if(radiovalue==30){
				 timeRadio = 30;
			 }else if(radiovalue==90){
				 timeRadio = 90;
			 }
		 }
		
	})
	
	
	var query ={
			    startdate : $('#createDateStart').val(),
			    enddate : $('#createDateEnd').val(),
			    proxyid : escapePeculiar($.trim($('#proxyid').val())),
			    posid : escapePeculiar($.trim($('#posId').val())),
				proxyorderno : escapePeculiar($.trim($('#orderNo').val())),
				cardno : escapePeculiar($.trim($('#cardNo').val())),
				status : escapePeculiar($.trim($('#orderState').val())),
				timeRadio : timeRadio,
				page:page
	}
	var beginDate = query.startdate;
	var endDate = query.enddate;
	compareTime(query,beginDate,endDate);
	ddpAjaxCall({
		url : "findCardTradeListByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#proxyCardAddTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+v.proxyorderno+'</td>';
						html += '<td>'+(v.proxyname == null ? '' : v.proxyname)+'</td>'
						html += '<td>'+(v.cardno == null ? '' : v.cardno)+'</td>';
						html += '<td>'+(v.posid == null ? '' : v.posid)+'</td>';
						html += '<td>'+(v.txndate == null ? '' : v.txndate)+'</td>';
						html += '<td>'+(v.txntime == null ? '' : v.txntime)+'</td>';
						
						//html += '<td>'+(v.mobiltel == null ? '' : v.mobiltel)+'</td>';
						//html += '<td>'+(v.activerebate == null ? '' : v.activerebate)+'</td>';
						html += '<td>'+(v.befsurpluslimit == null ? '' : v.befsurpluslimit)+'</td>';
						html += '<td>'+(v.txnamt == null ? '' : v.txnamt)+'</td>';
						html += '<td>'+(v.sumamt == null ? '' : v.sumamt)+'</td>';
						html += '<td>'+(v.paidamt == null ? '' : v.paidamt)+'</td>';
						html += '<td>'+(v.rebatesamt == null ? '' : v.rebatesamt)+'</td>';
						html += '<td>'+(v.aftsurpluslimit == null ? '' : v.aftsurpluslimit)+'</td>';
						html += '<td>'+(v.status == null ? '' : v.status)+'</td>';
						html += '<td>'+(v.remarks == null ? '' : v.remarks)+'</td>';
						html += '<td class="a-center">'
						html += '<a href="javascript:void(0);" class="text-icon print-icon01" title="打印小票" onclick="RechargePrint(\''
							+ v.proxyname + '\',\''+v.yktname+'\',\''+v.proxyorderno+'\',\''+v.posid+'\',\''+v.cardno+'\',\''+v.beforeamt+'\',\''+v.txnamt+'\',\''+v.afteramt+'\',\''+v.txntime+'\',\''+v.status+'\');"></a>';
						html += '</td>';
						html += '</tr>';
						$('#proxyCardAddTb').append(html);
					});
				}
				 else {
						$('.null-box').show();
					}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('#proxyCardAddPage').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#proxyCardAddPage select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
	
	
	
	ddpAjaxCall({
		url : "findCardTradeListCount",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#proxyCardAddTbCount tbody').empty();
				if( data.responseEntity!=null ){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>交易合计</td>';
						html += '<td>'+data.responseEntity.total+'</td>';
						html += '<td>'+data.responseEntity.amount+'</td>'
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#proxyCardAddTbCount').append(html);
						
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>实收合计</td>';
						html += '<td>'+data.responseEntity.totalpen+'</td>';
						html += '<td>'+data.responseEntity.incomeamount+'</td>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#proxyCardAddTbCount').append(html);
						
						
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>活动返利合计</td>';
						html += '<td>'+data.responseEntity.rebatenumber+'</td>';
						html += '<td>'+data.responseEntity.rebateamount+'</td>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#proxyCardAddTbCount').append(html);

						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>充值返利合计</td>';
						html += '<td>'+data.responseEntity.allrebatesamt+'</td>';
						html += '<td>'+data.responseEntity.totalamount+'</td>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#proxyCardAddTbCount').append(html);
						
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>优惠券使用合计</td>';
						html += '<td>'+data.responseEntity.couponpennumber+'</td>';
						html += '<td>'+data.responseEntity.couponuse+'</td>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#proxyCardAddTbCount').append(html);
				}
				 else {
						$('.null-box').show();
					}
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
	  $('#createDateStart').val(beginDate)
	  $('#createDateEnd').val(endDate)
	  query.startdate = beginDate;
	  query.enddate = endDate;
	 } 

}

function clearTranRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
	initQueryDate();
}


function initQueryDate(){
	$("#createDateStart").val(formatDate(new Date(),"yyyy-MM-dd"));
	$("#createDateEnd").val(formatDate(new Date(),"yyyy-MM-dd"));
}

function clearDate(){
	 $('#createDateStart').val('');
	 $('#createDateEnd').val('');
}



function createRechargePrintMapStr(){
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



function RechargePrint(proxyname,yktname,proxyorderno,proxyid,cardno,beforeamt,txnamt,afteramt,txntime,status){
	
	// 商户名称
	$('#merName').html(proxyname == null ? '' : proxyname);
	
	//城卡公司名称
	$('#printCompany').html(yktname==null ? "" : yktname);
	
	// 订单编号
	$('#printOrderNumRes').html(proxyorderno == null ? '' : proxyorderno);

	// 充 值 设 备号
	$('#printTool').html(proxyid==null?"" : proxyid);
	
	// 卡号
	$('#printCardCode').html(cardno == null ? '' : cardno);
	
	// 充值前卡余额（卡内余额）
	$('#printSMoneySpan').html((beforeamt=='undefined' || beforeamt==null) ? '': beforeamt);

	//充   值  余  额
	$('#printCardMoneySpan').html(txnamt==null?"": txnamt);
	
	// 充值后卡余额（卡内余额）
	$('#printRMoneySpan').html((afteramt=='undefined' || afteramt==null) ? '': afteramt);

	// 订单时间
	$('#printRechargeTime').html(txntime == null ? '' : txntime);
	// 订单状态
	$('#printRechargestate').html(status == null ? '' : status);
	toPrinter();
	
}

function toPrinter(){
	try{
		var str = createRechargePrintMapStr();
		ocxPrint(str);
		//location.reload(true); 
	}catch(ex){
	   console.log(ex.description);
	}
}

function ocxPrint(inparam){
	try
	{
	  var errCode = OCXPRRINTER.Printer(inparam);
	 //console.log("打印结果："+errCode);
	}
	catch(ex)
	{	  //console.log(ex.description);
		$.messagerBox({type: 'error', title:"信息提示", msg: "打印出错，请检查打印机或控件安装情况"});

	}
}

