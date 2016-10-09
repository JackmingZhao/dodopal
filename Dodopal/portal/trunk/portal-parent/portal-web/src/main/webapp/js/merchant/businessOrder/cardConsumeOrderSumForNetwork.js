$(function(){
	initQueryDate();
	findRechargeOrderCount();
	highlightTitle();
	$('.page-navi').paginator({prefix : 'order',pageSizeOnChange : findConsumeOrderCountList});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="业务订单汇总"){
			$(this).addClass('cur');
		}
	});
});

function findRechargeOrderCount(page){
	if(!page){
		page = {pageNo:1,pageSize:10};
	}

	if(compareDates($("#startdate").val(),"yyyy-MM-dd",$("#enddate").val(),"yyyy-MM-dd")==1){
		var temp = $("#startdate").val();
		$("#startdate").val($("#enddate").val());
		$("#enddate").val(temp);
	}
	var orderQuery ={ 
			orderDateStart:$("#startdate").val(), /** 交易日期开始*/
			orderDateEnd:$("#enddate").val(),/** 交易日期结束*/
			merCode:"",/** 商户号 */
			proOrderState:$("#proOrderState").val(),/**充值订单状态（0：成功；1：失败；2：可疑）*/
			page:page
	}
	
	
	ddpAjaxCall({
		url : $.base+"/businessOrder/findCardConsumeOrderSum",
		data : orderQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#displayTbl tbody').empty();
				if(data.responseEntity){
					$('.null-box').hide();
					html = '<tr>';
					
					html +='<td class="nobor">&nbsp;</td>';
					
					html += '<td>'
					html +=  1;
					html += '</td>';
					
					html += '<td>'
					html += data.responseEntity.sum == null ? '' :htmlTagFormat(data.responseEntity.sum);//充值笔数
					html += '</td>';
				    
					html += '<td>'
					html += data.responseEntity.sumAmt == null ? '' :htmlTagFormat(data.responseEntity.sumAmt);//充值金额（元)
					html += '</td>';
					
					html += '<td><a href="#" onclick="viewSumList();" class="text-icon"></td>'
					
					html +='<td class="nobor">&nbsp;</td>';
					html += '</tr>';
					$('#displayTbl').append(html);
				}
				$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
			}else{
				$.messagerBox({type: 'error', title:"信息提示", msg: data.message});
			}	
			
		}
	});
}



function myClearQueryForm(){
	clearQueryForm('queryForm');
	initQueryDate();
}

function initQueryDate(){
	$("#startdate").val(formatDate(new Date(new Date()-24*60*60*1000*7),"yyyy-MM-dd"));
	$("#enddate").val(formatDate(new Date(),"yyyy-MM-dd"));
}

function viewSumList(){
	$("#viewCountDiv").show();
	$("#countDiv").hide();
	findConsumeOrderCountList();
}
function hideViewSumList(){
	$("#viewCountDiv").hide();
	$("#countDiv").show();
}

function findConsumeOrderCountList(page) {
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={ 
			orderDateStart:$("#startdate").val(), /** 交易日期开始*/
			orderDateEnd:$("#enddate").val(),/** 交易日期结束*/
			merCode:"",/** 商户号 */
			proOrderState:$("#proOrderState").val(),/**充值订单状态（0：成功；1：失败；2：可疑）*/
			page:page
	}
	ddpAjaxCall({
		url : $.base + "/businessOrder/findCardConsumeOrderPage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#tblList tbody').empty();
				if (data.responseEntity.records
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records).each(
							function(i, v) {
								$('.null-box').hide();
								html = '<tr>';
								html += '<td class="nobor">&nbsp;</td>';

								html += '<td>'
								html += ++i+(data.responseEntity.pageNo-1)*data.responseEntity.pageSize;//序号
								html += '</td>';

								html += '<td>'
								html += v.proOrderNum == null ? '' : htmlTagFormat(v.proOrderNum);// 订单编号
								html += '</td>';

								html += '<td>'
								html += v.cityName == null ? '': v.cityName;// 城市
								html += '</td>';
								
								html += '<td>'
								html += v.cardNum == null ? '': v.cardNum;// 卡号
								html += '</td>';
								
								html += '<td>'
								html += v.posCode == null ? '': v.posCode;// POS号
								html += '</td>';
					            
					        	html += '<td>'
								html += v.originalPrice == null ? '': v.originalPrice;//应付金额（元）
								html += '</td>';
					            
								html += '<td>'
								html += v.receivedPrice == null ? '': v.receivedPrice;// 实付金额
								html += '</td>';

								html += '<td>'
								html += v.befbal == null ? '': v.befbal;// 消费前金额
								html += '</td>';
								
								html += '<td>'
								html += v.blackAmt == null ? '' : v.blackAmt;// 消费后金额
								html += '</td>';
								
								html += '<td>'
								html += v.proOrderStateStr == null ? '' : v.proOrderStateStr;//订单状态
								html += '</td>';

								html += '<td>'
								html += v.proOrderCreateDate == null ? '' : v.proOrderCreateDate;// 订单创建时间
								html += '</td>';

								html += '<td class="nobor">&nbsp;</td>';
								html += '</tr>';
								$('#tblList').append(html);
							});
				}	
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage', pageNo, pageSize,totalPages, rows);
				$('.com-table01 tbody tr:even').find('td').css("background-color", '#f6fafe');
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
