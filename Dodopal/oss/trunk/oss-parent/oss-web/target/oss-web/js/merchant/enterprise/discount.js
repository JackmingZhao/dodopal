/* -- 1. 主框架构建------------------------------------------------------- */
$(function() {
	merPageInfo.init();
	initMainComponent();
	parent.closeGlobalWaitingDialog();
});
var merPageInfo = {
	init: function() {
		this.ids = {};
	},
	setIdsByArr: function(arr) {
		$.each(arr, function(index, value) {
			merPageInfo.ids[value] = 1;
		});
	},
	setIdsByPageSel: function(arrS, arrD) {
		$.each(arrS, function(index, value) {
			if(merPageInfo.ids[value] > 0) {
				delete merPageInfo.ids[value];
			}
		});
		$.each(arrD, function(index, value) {
			merPageInfo.ids[value] = 1;
		});
	},
	getIds: function() {
		return this.ids;
	},
	getIdsArr: function() {
		var arr = [];
		$.each(merPageInfo.ids, function(key, value) {
			arr[arr.length] = key;
		});
		return arr;
	}
}
function initMainComponent() {
	$('#discountDialog').dialog({ 	// 折扣编辑
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
	$('#discountViewDia').dialog({ 	// 折扣查看
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
	$('#selMerDia').dialog({ 	// 商户列表
		collapsible : false,
		modal : true,
		closed : true,
		closable : true,
		width : 700,
		height : 320
	});
}


/* -- 2. 折扣列表 ------------------------------------------------------- */
$(function() {
	autoResize({
		dataGrid : {
			selector : '#discountListTbl', 	// 折扣列表
			offsetHeight : 60,
			offsetWidth : 0
		},
		callback : initDiscountListTbl,
		toolbar : [true, 'top'],
		dialogs : ['discountDialog', 'discountViewDia']
	});
});
function initDiscountListTbl(size) {
	$('#discountListTbl').jqGrid($.extend({
		datatype : function(pdata) {
			findDiscountDetailList();
		},
		colNames : ['ID', '用户折扣', '结算折扣', '绑定商户(个)', '开始日期', 		'结束日期', '星期', '开始时间', '结束时间'],
		colModel : [
	            { name : 'id', hidden : true }, 
	            { name : 'discountThreshold',  index : 'discountThreshold', width : 100, align : 'center', sortable : false, formatter: ddpMoneyFenToYuan}, 
	            { name : 'setDiscount',  index : 'setDiscount', width : 100, align : 'center', sortable : false, formatter: ddpMoneyFenToYuan}, 
	            { name : 'merCodeArrLen',  index : 'merCodeArrLen', width : 100, align : 'center', sortable : false }, 
	            { name : 'beginDate', index : 'beginDate', width : 100, align : 'center', sortable : false, formatter: function(v,r,i){var d=new Date(v);return ""+d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()} }, 
	            
	            { name : 'endDate', index : 'endDate', width : 100, align : 'center', sortable : false, formatter: function(v,r,i){var d=new Date(v);return ""+d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()} }, 
	            { name : 'week', index : 'week', width : 100, align : 'center', sortable : false }, 
	            { name : 'beginTime', index : 'beginTime', width : 100, align : 'center', sortable : false }, 
	            { name : 'endTime', index : 'endTime', width : 100, align : 'center', sortable : false }
        ],
		height : size.height - 60,
		width : size.width,
		multiselect : true,/**/
		pager : '#discountTblPagination',
		toolbar : [true, "top"]
	}, jqgrid_server_opts));
	$("#t_discountListTbl").append($('#discountTblToolbar'));
}
function findDiscountDetailList(defaultPageNo) {
	ddpAjaxCall({
		url : "findDiscountsPage",
		data : {
			discountThreshold : escapePeculiar($.trim($('#discountThresholdQuerry').val())),
			page : getJqgridPage('discountListTbl', defaultPageNo)
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#discountListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}
$(function() {
	$('#discountQueryBtn').click(function() {
		findDiscountDetailList();
	});
	$('#resetDisQueryBtn').click(function(){
		$("#discountThresholdQuerry").attr("value", "");
	});
	$('#addDiscount').click(function() {
		$('#titleCon').html('添加折扣')
		clearEditDiscountForm();
		$('#discountDialog').show().dialog('open');
		$('#discountThreshold').attr('readonly', false);
		$('#discountThreshold').attr('disabled', false);
		initMerListTbl();
	});
	$('#edtDiscount').click(function() {
		$('#titleCon').html('编辑折扣')
		var sa = $("#discountListTbl").jqGrid('getGridParam', 'selarrrow');// 多选
		if(sa != null && sa.length > 1) { msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning'); return; }
		var sr = $("#discountListTbl").jqGrid('getGridParam', 'selrow');
		if(sr) {
			var rowData = $('#discountListTbl').getRowData(sr);
			initMerListTbl();
			clearEditDiscountForm();
			(function() {
				ddpAjaxCall({url: "viewDiscountById", data: rowData.id, successFn: function(data) {
					var d = data.responseEntity;
					$('#discountDialog').show().dialog('open');
					$('#discountId').val(d.id);
					$('#discountThreshold').val(ddpMoneyFenToYuan(d.discountThreshold));
					$('#discountThreshold').attr('readonly', 'readonly');
					$('#discountThreshold').attr('disabled', 'disabled');
					$('#setDiscount').val(ddpMoneyFenToYuan(d.setDiscount));
					$('#beginDate').val((function(v) {
						var d=new Date(v),n=d.getMonth()+1,m=n<10?"0"+n:""+n,s=d.getDate(),t=s<10?"0"+s:""+s;
						return ""+d.getFullYear()+"-"+m+"-"+t;})(d.beginDate));
					$('#endDate').val((function(v) {
						var d=new Date(v),mm=(d.getMonth()+1)<10?"0"+(d.getMonth()+1):(d.getMonth()+1)+"",dd=d.getDate()<10?"0"+d.getDate():""+d.getDate();
						return ""+d.getFullYear()+"-"+mm+"-"+dd;})(d.endDate));
					$.each(d.week.split(''),function(i, v) {
						$('input[name="week"]').eq(v-1).attr('checked', "checked");
					});
					if(d.endTime == '23:59' && d.beginTime == '00:00') {
						$('#wholeTimeChoose').attr("checked", "checked");
						$('#partTimeChoose').attr("checked", false);
						timeChoiceStyle();
					}else {
						$('#wholeTimeChoose').attr("checked", false);
						$('#partTimeChoose').attr("checked", "checked");
						$('#mBeginTime').timespinner({ showSeconds: false }); 
						$('#mEndTime').timespinner({ showSeconds: false }); 
						$('#mBeginTime').timespinner('setValue', d.beginTime);
						$('#mEndTime').timespinner('setValue', d.endTime);
						timeChoiceStyle();
					}
					(function() {
						$('#setDiscount').focus(function() {
//							
							var tit = $('#titleCon').html();
							if(tit == '编辑折扣') {
								$('#setDiscount').val(ddpMoneyFenToYuan(d.setDiscount));
							}
						});
					})();
				}});
				ddpAjaxCall({url: "viewMerArrByDiscountId", data: rowData.id, successFn: function(data) {
					var ids = [];
					if(data.responseEntity.length > 0) {
						$.each(data.responseEntity, function(dx, val) {
							ids[ids.length] = val['merCode'];
						});
					}
					tblMerInfoInPage(ids, 'resultSelMer'); 	// show mer info
					var selId = $("#merListTbl").jqGrid('getGridParam', 'selarrrow');
					merPageInfo.setIdsByArr(ids);
					findMerListPage(1);
				}});
				
			})();
		}else {
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		}
	});
	$('#viwDiscount').click(function() {
		var sa = $("#discountListTbl").jqGrid('getGridParam', 'selarrrow');// 多选
		if(sa != null && sa.length > 1) {
			msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning'); return;
		}
		var sr = $("#discountListTbl").jqGrid('getGridParam', 'selrow');
		if(sr) {
			$('#discountViewDia').show().dialog('open');
			var rowData = $('#discountListTbl').getRowData(sr);
			(function(){
				$('#discountThresholdView').val();
				$('#setDiscountView').val();
				$('#beginDateView').val();
				$('#endDateView').val();
				$('#beginTimeView').val();
				$('#endTimeView').val();
				for(var i=1;i<8;i++) {$('input[name="weekView"]').eq(i-1).attr('checked', false);$('input[name="weekView"]').eq(i-1).parent('label').css({color:'black'})};
				tblMerInfoInPage([], 'resultSelMerView');
				merPageInfo.ids={};
			})();
			ddpAjaxCall({url: "viewDiscountById", data: rowData.id, successFn: function(data) {
				var d = data.responseEntity;
				$('#discountThresholdView').text(ddpMoneyFenToYuan(d.discountThreshold));
				$('#setDiscountView').text(ddpMoneyFenToYuan(d.setDiscount));
				$('#beginDateView').text((function(v) {
					var d=new Date(v),n=d.getMonth()+1,m=n<10?"0"+n:""+n,s=d.getDate(),t=s<10?"0"+s:""+s;
					return ""+d.getFullYear()+"-"+m+"-"+t;})(d.beginDate));
				$('#endDateView').text((function(v) {
					var d=new Date(v),mm=(d.getMonth()+1)<10?"0"+(d.getMonth()+1):(d.getMonth()+1)+"",dd=d.getDate()<10?"0"+d.getDate():""+d.getDate();
					return ""+d.getFullYear()+"-"+mm+"-"+dd;})(d.endDate));
				$.each(d.week.split(''),function(i, v) {
					$('input[name="weekView"]').eq(v-1).attr('checked', 'checked');
					$('input[name="weekView"]').eq(v-1).parent('label').css({color:'red'});
				});
				$('#beginTimeView').text(d.beginTime);
				$('#endTimeView').text(d.endTime);
			}});
			ddpAjaxCall({url: "viewMerArrByDiscountId", data: rowData.id, successFn: function(data) {
				var ids = [];
				if(data.responseEntity.length > 0) {
					$.each(data.responseEntity, function(dx, val) {
						ids[ids.length] = val['merCode'];
					});
				}
				tblMerInfoInPage(ids, "resultSelMerView"); 	// show mer info
				if(data.responseEntity.length == 0) {
					$('#resultSelMerView').html('');
				}
			}});
		}else {
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		}
	});
	$('#delDiscount').click(function() {
		var sr = $("#discountListTbl").jqGrid('getGridParam', 'selarrrow');
		if(sr) {
			var rowData = [], rowsId = [], isBod = 0;
			for(var i = 0; i < sr.length; i++) {
				var data = $('#discountListTbl').getRowData(sr[i]);
				rowData[rowData.length] = data;
				rowsId[rowsId.length] = data.id;
			}
			for(var i = 0; i < rowData.length; i++) {
				isBod = isBod/1 + (rowData[i].merCodeArrLen)/1;
			}
			if(isBod*1 != 0) {
				msgShow(systemWarnLabel, '中间尚有折扣绑定商户, 不能删除! ', 'warning');
			}else {
				$.messager.confirm(systemConfirmLabel, " "+rowData.length+" 条记录将会删除? ", function(r) {
					if(r) {
						ddpAjaxCall({url: "deleteMerDiscountByIds", data: rowsId, successFn: function(data) {
							if(data.code == "000000") {
								findDiscountDetailList();
							}else {
								msgShow(systemWarnLabel, data.message, 'warning');
							}
						}});
					}
				});
			}
		}else {
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		}
	});
	$('#cancViewBtn').click(function() {
		merPageInfo.init();
		merPageInfo.setIdsByArr([]);
		$('#discountViewDia').show().dialog('close');
	});
});

/* -- 3. 折扣编辑 ------------------------------------------------------- */
$(function() {
	!function() {
		timeChoiceStyle();
	}();
	$('.TimeChoose').change(function() {
		timeChoiceStyle();
	});
	$('#saveBtn').click(function() {
		var validateDis = function() {
//			var r = /^\+?[1-9][0-9]*$/;
			var r= /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/;
			var data = getDiscountData();
			if(data.discountThreshold == '' || data.discountThreshold > 10 || !r.test(data.discountThreshold)) { msgShow(systemWarnLabel, '请重新输入: 用户折扣', 'warning'); return false; }
			if(data.setDiscount == '' || data.setDiscount > 10 || !r.test(data.setDiscount)) { msgShow(systemWarnLabel, '请重新输入: 结算折扣', 'warning'); return false; }
			if(data.beginDate == '') { msgShow(systemWarnLabel, '请重新输入: 开始日期', 'warning'); return false; }
			if(data.endDate == '') { msgShow(systemWarnLabel, '请重新输入: 结束日期', 'warning'); return false; }
			if(data.beginTime == '') { msgShow(systemWarnLabel, '请重新输入: 开始时间', 'warning'); return false; }
			if(data.endTime == '') { msgShow(systemWarnLabel, '请重新输入: 结束时间', 'warning'); return false; }
			if(data.week.length == 0) { msgShow(systemWarnLabel, '请重新选择: 星期', 'warning'); return false; }
			return true;
		}
		if(validateDis()) {
			$.messager.confirm(systemConfirmLabel, (function(){var l=getDiscountData().merCodeArr.length;if(l==0)return "未将折扣绑定商户, 是否继续?"; else return "确定要保存折扣商户信息吗?<br />已绑定商户 "+l+" 个!";})(), function(r) {
				if(r) {
					ddpAjaxCall({
						url : "saveDiscountAndMer", data : (function(){var d=getDiscountData();d.setDiscount=Math.round(d.setDiscount*100);d.discountThreshold=Math.round(d.discountThreshold*100); return d;})(),
						successFn : function(data) {
							if(data.code == "000000") {
								clearEditDiscountForm();
								$('#discountDialog').hide().dialog('close');
								findDiscountDetailList();
								findMerListPage(1);
								merPageInfo.init();
							}else {
								var msg = data.message.split('__');
								msgShow(systemWarnLabel, "至少与一个商户的折扣有冲突:<br />商户名:"+msg[0]+"<br />折扣值:"+ddpMoneyFenToYuan(msg[1]), 'warning');
							}
						}
					});
				}
			});
		}
	});
	$('#cancBtn').click(function() {
		
		clearEditDiscountForm();
		$('#discountDialog').show().dialog('close');
		findMerListPage(1);
		merPageInfo.ids={};
	});
	$('#boundMer').click(function() {
		var pageIdArr = $('#merListTbl').jqGrid('getDataIDs');
		$('#selMerDia').show().dialog('open');
	});
	$('#beginDate').focus(function() {
		WdatePicker({skin:'whyGreen',minDate:'1900-01-01',maxDate:$('#endDate').val()});
	});
	$('#endDate').focus(function() {
		WdatePicker({skin:'whyGreen',minDate:$('#beginDate').val()});
	});
});


/* -- 4. 商户列表 ------------------------------------------------------- */
function initMerListTbl() {
	autoResize({
		dataGrid : {
			selector : '#merListTbl'//, 	// 折扣列表
//			offsetHeight : 20,
//			offsetWidth : 0
		},
		callback : initDiscountListTbl,
		toolbar : [true, 'top']//,
		//dialogs : ['discountDialog', 'discountViewDia']
	});
	$('#merListTbl').jqGrid($.extend({
		datatype : function(pdata) {
			findMerListPage();
		},
		colNames : ['商户名称', '商户号', '商户类型'],
		colModel : [
			           { name : 'merName', index : 'merName', align : 'center', sortable : false, width : 210, }, 
			           { name : 'merCode', index : 'merCode', align : 'center', sortable : false, width : 180, key: true }, 
			           { name : 'merType', index : 'merType', align : 'center', sortable : false, width : 200, }
		],
		multiselect : true,

		height: 480,
		pager : '#merTblPagination',
		toolbar : [true, "top"],
		gridComplete: function() {
			var ids = merPageInfo.getIds();
			var pageIdArr = $('#merListTbl').jqGrid('getDataIDs');
			$.each(pageIdArr, function(index, value) {
				if(ids[value] > 0) {
					$('#merListTbl').jqGrid('setSelection', value);
				}
			});
			var selI = merPageInfo.getIds();
		},
		onPaging: function(pgButton) {
			var pageIdArr = $('#merListTbl').jqGrid('getDataIDs');
			var selId = $("#merListTbl").jqGrid('getGridParam', 'selarrrow');
			merPageInfo.setIdsByPageSel(pageIdArr, selId);
		}
	}, jqgrid_server_opts));
	$("#t_merListTbl").append($('#selMerDiaToolbar'));
}
function findMerListPage(defaultPageNo) {
	ddpAjaxCall({
		url : "findMerchantsByDiscountIdByPage",
		data : {
			merName : escapePeculiar($.trim($('#merName').val())),
			page : getJqgridPage('merListTbl', defaultPageNo)
		},
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#merListTbl'), data.responseEntity);
			} else {
				msgShow(systemWarnLabel, data.message, 'warning');
			}
		}
	});
}
$(function() {
	$('#searSelMerBtn').click(function() {
		findMerListPage();
	});
	$('#saveSelMerBtn').click(function() { 	// m set tbl checkbox
		var pageIdArr = $('#merListTbl').jqGrid('getDataIDs');
		var selId = $("#merListTbl").jqGrid('getGridParam', 'selarrrow');
		merPageInfo.setIdsByPageSel(pageIdArr, selId);
		
		tblMerInfoInPage(merPageInfo.getIdsArr(), 'resultSelMer');
		$('#selMerDia').show().dialog('close');
	});
	$('#cancSelMerBtn').click(function() {
		$('#selMerDia').show().dialog('close');
		findMerListPage(1);
		merPageInfo.setIdsByArr([]);
	});
});


/* -- 5. 公有方法 ------------------------------------------------------- */
function clearEditDiscountForm() {
	clearAllText('discountDialog');
	$('#discountId').val('');
	$('#discountThreshold').val('');$('#discountThreshold').removeClass('validatebox-invalid');
	$('#setDiscount').val('');$('#setDiscount').removeClass('validatebox-invalid');
	$('#beginDate').val('');
	$('#endDate').val('');
	$('input[name="week"]').attr("checked", false);
	$('#wholeTimeChoose').attr("checked", "checked");
	$('#partTimeChoose').attr("checked", false);
	timeChoiceStyle();
	tblMerInfoInPage([], 'resultSelMer');
	tblMerInfoInPage([], 'resultSelMerView');
	
	$('#mBeginTime').timespinner({ showSeconds: false }); 
	$('#mEndTime').timespinner({ showSeconds: false }); 
	$('#mBeginTime').timespinner('setValue', '08:00');
	$('#mEndTime').timespinner('setValue', '18:00');
}
function timeChoiceStyle() {
	if($('input[name="TimeChoose"]:checked').val() == '0') {
		$('#mBeginTime').attr("disabled", "disabled");
		$('#mEndTime').attr("disabled", "disabled");
		$('#lBeginTime').css("color", "grey");
		$('#lEndTime').css("color", "grey");
	}else {
		$('#mBeginTime').removeAttr("disabled", "disabled");
		$('#mEndTime').removeAttr("disabled", "disabled");
		$('#lBeginTime').css("color", "");
		$('#lEndTime').css("color", "");
		$('#mBeginTime').timespinner({ showSeconds: false }); 
		$('#mEndTime').timespinner({ showSeconds: false }); 
		if($('#mBeginTime').timespinner('getValue') == '') {
			$('#mBeginTime').timespinner('setValue', '08:00');
		}
		if($('#mEndTime').timespinner('getValue') == '') {
			$('#mEndTime').timespinner('setValue', '18:00');
		}
	}
}
function tblMerInfoInPage(a, tbl) {
	if(a.length == 0) {
		$('#'+ tbl).html('');
		return;
	}else {
		ddpAjaxCall({url: "findMerInfoByIdArr", data: a, successFn: function(data) {
			var str = '<table style="width: 99%"><tr style="border:solid 1px #cccccc;">';
			var merA = data.responseEntity;
			for(var i = 0; i < merA.length; i++) {
				if((i)%5 == 0 && i != 0) str += '</tr><tr style="border:solid 1px #cccccc;">';
				str += '<td title="商户名:'+merA[i].merName+'&#10;商户号:'+merA[i].merCode+'&#10;商户类型:'+merA[i].merType+'" style="border-bottom:solid 1px #cccccc; border-left:solid 2px #ccc22c;">'
			    	+  '<label style="width:150px; margin-left: 10px;display:inline-block;" ><a>'+merA[i].merName+'</a></label>'
			    	+  '</td>';
			}
			str += '</tr></table>';
			$('#'+tbl).html(str);
		}});
	}
}
function getDiscountData() {
	var DiscountData = {};
	DiscountData.id = $('#discountId').val();
	DiscountData.discountThreshold = $('#discountThreshold').val();
	DiscountData.week = $("input[name='week']:checked").map(function() { var str = ""; str += $(this).val(); return str; }).get().join('');
	DiscountData.discountThreshold = $('#discountThreshold').val();
	DiscountData.setDiscount = $('#setDiscount').val();
	DiscountData.beginDate = $('#beginDate').val();
	DiscountData.endDate = $('#endDate').val();
	if($('input[name="TimeChoose"]:checked').val() == '0') {
		DiscountData.beginTime = "00:00";
		DiscountData.endTime = "23:59";
	}
	else {
		DiscountData.beginTime = $('#mBeginTime').val();
		DiscountData.endTime = $('#mEndTime').val();
	}
	DiscountData.merCodeArr = merPageInfo.getIdsArr();
	return DiscountData;
}




