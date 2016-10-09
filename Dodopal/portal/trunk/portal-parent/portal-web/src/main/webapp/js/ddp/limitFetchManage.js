$(function() {
			findLimitExtractConfirmPage();
			$('.page-navi').paginator({
						prefix : 'limitFetchManage',
						pageSizeOnChange : findLimitExtractConfirmPage
					});
			highlightTitle();
			$('.header-inr-nav ul li a').each(function() {
						if ($.trim($(this).text()) == "额度提取管理") {
							$(this).addClass('cur');
						}
					});
		});
// 查询额度提取记录
function findLimitExtractConfirmPage(page) {
	var extractStartDate = $('#extractStartDate').val();
	var extractEndDate = $('#extractEndDate').val();
	var childMerName = $('#childMerName').val();
	var state = $('#statreQuery').val();
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var merLimitBeanQuery = {
		extractStartDate : extractStartDate,
		extractEndDate : extractEndDate,
		childMerName : childMerName,
		state: state,
		page : page
	}
	compareTime(merLimitBeanQuery, extractStartDate, extractEndDate);
	ddpAjaxCall({
		url : "findMerLimitByPage",
		data : merLimitBeanQuery,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#merLimitExtTable tbody').empty();
				var i = 1;
				if (data.responseEntity.records
						&& data.responseEntity.records.length > 0) {
					$(data.responseEntity.records).each(function(i, v) {
						i = i + 1;
						$('.null-box').hide();
						html = '<tr>';
						html += '<td class="nobor">&nbsp;</td>';

						html += '<td>';
						html += i++ + '';
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.extractAmt;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.merName;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.stateView;
						html += '</td>';

						html += '<td style="word-break:break-all" >'
						html += v.extractDate;
						html += '</td>';

						html += '<td class="a-center">'
						//if (hasPermission('ddp.limit.cancel')) {
							// 状态;0：待确认 1：已确认 2：已取消 3：已拒绝 ;1、2、3为最终状态，不可再修改
							if(merLimitType =='12'){
								if (v.state == '0') {
									html += '<a href="javascript:void(0);"  onclick="cancelPanl(\''
											+ v.id + '\');" style="color:#37b4e9">取消</a>';
								} else {
									html += '';
								}
							}
							
						//}
						//if (hasPermission('ddp.limit.confirm')) {
							if(merLimitType =='14' || merLimitType =='13'){
							if (v.state == '0') {
								html += '<a href="javascript:void(0);"  onclick="confirmPanl(\''
										+ v.id+'\''+',\''+v.extractAmt+'\''+',\''+v.parentMerCode+'\');" style="color:#37b4e9">确认</a>|';
								html += '<a href="javascript:void(0);"  onclick="rejectPanl(\''
										+ v.id + '\');"  style="color:#37b4e9">拒绝</a>';
							} else {
								html += '';
							}
							}
						//}
						html += '</td>';

						html += '<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#merLimitExtTable').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css(
							"background-color", '#f6fafe');
				} else {
					$('.null-box').show();
				}
				var pageSize = data.responseEntity.pageSize;
				var pageNo = data.responseEntity.pageNo;
				var totalPages = data.responseEntity.totalPages;
				var rows = data.responseEntity.rows;
				$('.page-navi').paginator('setPage', pageNo, pageSize,
						totalPages, rows);
				$('.page-navi select').attr("style",
						"width:45px;padding:0px 0px");
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
// 提取额度时间转换
function compareTime(query, beginDate, endDate) {
	var d1 = new Date(beginDate.replace(/\-/g, "\/"));
	var d2 = new Date(endDate.replace(/\-/g, "\/"));
	if (beginDate != "" && endDate != "" && d1 >= d2) {
		var temp = endDate;
		endDate = beginDate;
		beginDate = temp;
		$('#extractStartDate').val(beginDate)
		$('#extractEndDate').val(endDate)
//		query.orderDateStart = beginDate;
//		query.orderDateEnd = endDate;
	}
}
//取消弹出框
function cancelPanl(id){
	var merLimitPanlBean ={
	id : id
	}
	$.messagerBox({type:"confirm", title:"确认", msg: "您确认取消提取额度吗？", confirmOnClick: cancelMerLimit, param:merLimitPanlBean});
}
// 连锁商户取消提取额度记录
function cancelMerLimit(merLimitPanlBean) {
	var merLimitBean ={
	id : merLimitPanlBean.id,
	state : '2'
	}
	ddpAjaxCall({
				url : "upMerLimitByCode",
				data : merLimitBean,
				successFn : function(data) {
				if(data.code =="000000"){
					findLimitExtractConfirmPage($('.page-navi').paginator('getPage'));
				}else{
				$.messagerBox({
							type : 'warn',
							title : "信息提示",
							msg : data.message
						});
				}
				}
			});
}
//确认弹出框
function confirmPanl(id,extractAmt,parentMerCode){
	var merLimitPanlBean ={
	id : id,
	extractAmt : extractAmt,
	parentMerCode : parentMerCode
	}
$.messagerBox({type:"confirm", title:"确认", msg: "您确认提取额度吗？", confirmOnClick: confirmMerLimit, param:merLimitPanlBean});
}
// 连锁商户确认提取额度记录
function confirmMerLimit(merLimitPanlBean) {
	var merLimitBean ={
	id : merLimitPanlBean.id,
	state : '1',
	extractAmt : merLimitPanlBean.extractAmt,
	parentMerCode : merLimitPanlBean.parentMerCode
	}
	ddpAjaxCall({
				url : "upMerLimitByCode",
				data : merLimitBean,
				successFn : function(data) {
				if(data.code =="000000"){
				findLimitExtractConfirmPage($('.page-navi').paginator('getPage'));
				}else{
				$.messagerBox({
							type : 'warn',
							title : "信息提示",
							msg : data.message
						});
				}
				}
			});
}

//确认弹出框
function rejectPanl(id){
var merLimitPanlBean ={
	id : id
	}
$.messagerBox({type:"confirm", title:"确认", msg: "您拒绝提取额度吗？", confirmOnClick: rejectMerLimit, param:merLimitPanlBean});
}
// 连锁商户拒绝提取额度记录
function rejectMerLimit(merLimitPanlBean) {
	var merLimitBean = {
	id : merLimitPanlBean.id,
	state : '3'
	}
	ddpAjaxCall({
				url : "upMerLimitByCode",
				data : merLimitBean,
				successFn : function(data) {
				if(data.code == "000000"){
					findLimitExtractConfirmPage($('.page-navi').paginator('getPage'));
				}else{
				$.messagerBox({
							type : 'warn',
							title : "信息提示",
							msg : data.message
						});
				}
				}
			});
}