$(function(){
	initQueryDate();
	findOffLineTradeList();
	$('#offLineTradeListPage').paginator({
						prefix : 'offLineTradeListTb',
						pageSizeOnChange : findOffLineTradeList
					});
	highlightTitle();
	getOtherOCX("73E062B2-BF5F-4952-8BE2-29387DFEBC74",
			"1,0,0,2","DDPrint","打印机控件加载失败! -- 请检查浏览器的安全级别设置.","OCXPRRINTER","OCXPRRINTER");
});


function findOffLineTradeList(page){
	
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
			    posid : escapePeculiar($.trim($('#posId').val())),
			    proxyid : escapePeculiar($.trim($('#proxyid').val())),
			    startdate : $('#createDateStart').val(),
			    enddate : $('#createDateEnd').val(),
				status:$('#status').val(),
				timeRadio : timeRadio,
				page: page,
	}
	var beginDate = query.startdate;
	var endDate = query.enddate;
	compareTime(query,beginDate,endDate);
	ddpAjaxCall({
		url : "findOffLineTradeListByPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#offLineTradeListTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>'+getSequence(data.responseEntity,i)+'</td>';
						html += '<td>'+v.mchnitorderid+'</td>';
						html += '<td>'+(v.checkcardno==null ? '' : v.checkcardno)+'</td>'
						html += '<td>'+(v.checkcardposid==null ? '' : v.checkcardposid)+'</td>';
						html += '<td>'+(v.facevalue==null ? '' : v.facevalue)+'</td>';
						html += '<td>'+(v.sale==null ? '' : v.sale)+'</td>';
						html += '<td>'+(v.amt==null ? '' : v.amt)+'</td>';
						html += '<td>'+(v.proamt==0 ? '' : v.proamt)+'</td>';
						
						html += '<td>'+(v.blackamt==0 ? '' : v.blackamt)+'</td>';
						html += '<td>'+(v.posremark==null ? '' : v.posremark)+'</td>';
						
						html += '<td>'+(v.ordertime==null ? '' : v.ordertime)+'</td>';
						html += '<td>'+(v.orderstates==null ? '' : v.orderstates)+'</td>';
						html += '<td class="a-center">'
							if(v.mchnitorderid=='总计'){}else{
							html += '<a href="javascript:void(0);" class="text-icon print-icon01" title="打印小票" onclick="ConsumePrint(\''
								+ v.proxyname + '\',\''+v.mchnitorderid+'\',\''+v.orderserror+'\',\''+v.checkcardno+'\',\''+v.facevalue+'\',\''+v.sale+'\',\''+v.amt+'\',\''+v.proamt+'\',\''+v.blackamt+'\',\''+v.checkcardposid+'\',\''+v.ordertime+'\',\''+v.orderstates+'\');"></a>';
							html += '</td>';
							}
							//proxyname,mchnitorderid,orderserror,checkcardno,facevalue,sale,amt,proamt,blackamt,checkcardposid,checkcardno,ordertime,orderstates
						html += '</tr>';
						$('#offLineTradeListTb').append(html);
					});
					
				}else {
						$('.null-box').show();
					}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('#offLineTradeListPage').paginator('setPage',pageNo,pageSize,totalPages,rows);
				$('#offLineTradeListPage select').attr("style","width:45px;padding:0px 0px");
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
	
	
	
	
	ddpAjaxCall({
		url : "findoffLineTradeListCount",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#offLineTradeListTbCount tbody').empty();
				if( data.responseEntity!=null ){
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>消费合计</td>';
						html += '<td>'+data.responseEntity.countfacevalue+'</td>';
						html += '<td>'+data.responseEntity.facevalue+'</td>'
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#offLineTradeListTbCount').append(html);
						
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html += '<td>实收合计</td>';
						html += '<td>'+data.responseEntity.countamt+'</td>';
						html += '<td>'+data.responseEntity.amt+'</td>';
						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#offLineTradeListTbCount').append(html);
				}
				 else {
						$('.null-box').show();
					}
			}
		}
	});
}


function clearDate(){
	 $('#createDateStart').val('');
	 $('#createDateEnd').val('');
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

function initQueryDate(){
	$("#createDateStart").val(formatDate(new Date(),"yyyy-MM-dd"));
	$("#createDateEnd").val(formatDate(new Date(),"yyyy-MM-dd"));
}

function clearTranRecord(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
	initQueryDate();
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



function ConsumePrint(proxyname,mchnitorderid,orderserror,checkcardno,facevalue,sale,amt,proamt,blackamt,checkcardposid,ordertime,orderstates){
	
//	<div style="display:none;">
//	<ul class="jige-ul mb20" id="printSpan">
//		<li style="display:none">商户名称：<span id="merName"></span></li>
//		<li><em>订单号：</em><span id="printOrderNumRes"></span></li>
//		<li><em>终端流水号：</em><span id="printTranCode"></span></li>
//		<li><em>卡号：<span id="printCardCode"></span></li>
//		<li><em>消费金额：</em><span class="orange" id="printConsumeMoneySpan"></span>元</li>
//		<li><em>折扣：</em><span class="orange" id="printDiscountSpan"></span>元</li>
//		<li><em>实付金额：</em><span class="orange" id="printMoneySpan"></span>元</li>
//		<li><em>原有金额：</em><span class="orange" id="printSMoneySpan"></span>元</li>
//		<li><em>卡余额：</em><span class="orange" id="printCardMoneySpan"></span>元</li>
//		<li><em>POS号：</em><span id="printPosCode"></span></li>
//		<li><em>卡号：</em><span id="printCardNo"></span></li>
//		<li><em>交易时间：</em><span id="printConsumeTime"></span></li>
//		<li><em>状态：</em><span id="printConsumestate"></span></li>
//	</ul>
//</div>
	
	
	// 商户名称
	$('#merName').html(proxyname == null || proxyname=='null' ? '' : proxyname);
	// 订单编号
	$('#printOrderNumRes').html(mchnitorderid == null || mchnitorderid=='null' ? '' : mchnitorderid );
	//终端流水号
	$('#printTranCode').html(orderserror==null || orderserror=='null' ? "" : orderserror);
	
	//消费金额
	$('#printConsumeMoneySpan').html(facevalue==null?"": facevalue);
	// 折扣
	$('#printDiscountSpan').html(sale==null?"" : sale);
	// 实付金额
	$('#printMoneySpan').html((amt=='undefined' || amt==null) ? '': amt);
	// 原有金额
	$('#printSMoneySpan').html((proamt=='undefined' || proamt==null) ? '': proamt);
	// 卡余额
	$('#printCardMoneySpan').html((blackamt=='undefined' || blackamt==null) ? '': blackamt);
	// pos号
	$('#printPosCode').html(checkcardposid == null ? '' : checkcardposid);
	// 卡号
	$('#printCardCode').html(checkcardno == null ? '' : checkcardno);
	// 订单时间
	$('#printConsumeTime').html(ordertime == null ? '' : ordertime);
	// 订单状态
	$('#printConsumestate').html(orderstates == null ? '' : orderstates);
	toPrinter();
	
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


