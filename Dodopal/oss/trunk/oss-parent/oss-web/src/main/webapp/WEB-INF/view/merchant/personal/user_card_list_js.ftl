<script type="text/javascript">
$(function() {
	initMainComponent();
	initDetailComponent();
	//findData();
	parent.closeGlobalWaitingDialog();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
});

function initMainComponent() {
	autoResize({
		dataGrid : {
			selector : '#dataTbl',
			offsetHeight : 90,
			offsetWidth : 0
		},
		callback : initDataTbl,
		toolbar : [ true, 'top' ],
		dialogs : [ 'dataDialog' ]
	});
}

function initDetailComponent() {
	initDataDialog();
}

function initDataTbl(size) {
  var option = {
		datatype : function (pdata) {
			findData();
	    },
	    colNames : [ 'userCardId','bundLingTypeView', '用户名','姓名', '卡号','卡类型','卡所属城市','绑定状态','绑定时间', '解绑人','解绑时间', '备注'],
		colModel : [ 
		{
			name : 'userCardId',
			index : 'userCardId',
			hidden : true,
			frozen:true
		},{
			name : 'bundLingTypeView',
			index : 'bundLingTypeView',
			width : 100,
			align : 'left',
			hidden : true,
			frozen:true
		},{
			name : 'merUserName',
			index : 'merUserName',
			width : 100,
			align : 'left',
			sortable : false,
			frozen:true
		},{
			name : 'merUserNameName',
			index : 'merUserNameName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'cardCode',
			index : 'cardCode',
			width : 130,
			align : 'left',
			sortable : false
		},
		 {
			name : 'cardType',
			index : 'cardCode',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
				if(cellval == '1') {return 'CPU'} 
				else if(cellval == '2') {return 'M1'} 
				else { return ''};
			}
		},
		 {
			name : 'cardCityName',
			index : 'cardCode',
			width : 100,
			align : 'left',
			sortable : false
		},  {
			name : 'bundLingTypeView',
			index : 'bundLingType',
			width : 100,
			align : 'left',
			sortable : false,
		},{
			name : 'createDateView',
			index : 'createDateView',
			width : 150,
			align : 'left',
			sortable : false,
			formatter:'date',formatoptions:{srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}
		},{
			name : 'unBundLingUserName',
			index : 'unBundLingUserName',
			width : 100,
			align : 'left',
			sortable : false
		}, {
			name : 'unBundLingDateView',
			index : 'unBundLingDateView',
			width : 150,
			align : 'left',
			sortable : false,
			formatter:'date',formatoptions:{srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}
		}, {
			name : 'remark',
			index : 'remark',
			width : 100,
			align : 'left',
			formatter: htmlFormatter,
			sortable : false
		}],
		//caption : "用户卡片管理",
		//sortname : 'name',
		multiselect: true,
		height : size.height - 80,
		width : size.width,
		autowidth : true,
        shrinkToFit : false,
		pager : '#dataTblPagination',			
		toolbar : [ true, "top" ]
	};
	option = $.extend(option, jqgrid_server_opts);
	$('#dataTbl').jqGrid(option);
	$("#dataTbl").jqGrid('setFrozenColumns');
	$("#t_dataTbl").append($('#dataTblToolbar'));
}
function initDataDialog() {
	$('#dataDialog').dialog({
		collapsible : true,
		modal : true,
		closed : true,
		closable : false
	});
}

function showDataDialog() {
	$('#dataDialog').show().dialog('open');
}

function hideDataDialog() {
	clearForm();
	$('#dataDialog').hide().dialog('close');
}

function afterAddUser(data) {
	if(data.code == "000000") {
		hideDataDialog();
		findData();
	} else {
		msgShow(systemWarnLabel, data.code+":" + data.message, 'warning');
	}
}

function afterFindData(data) {
	if(data.code == "000000") {
		findData();
	} else {
		msgShow(systemWarnLabel, data.code+":" + data.message, 'warning');
	}
}

function findData(defaultPageNo) {
	var findBean = {
	    merUserName : escapePeculiar($.trim($('#merUserName').val())),
	    merUserNameName : escapePeculiar($.trim($('#merUserNameName').val())),
	    bundLingDateStart : $('#bundLingDateStart').val(),
	    bundLingDateEnd : $('#bundLingDateEnd').val(),
	    unBundLingDateStart : $('#unBundLingDateStart').val(),
	    unBundLingDateEnd : $('#unBundLingDateEnd').val(),
		cardCode : escapePeculiar($.trim($('#cardCode').val())),
		cardType: $('#cardType').combobox('getValue'),
		page : getJqgridPage('dataTbl',defaultPageNo)	
	}
	ddpAjaxCall({
		url : "findUserCardsByPage",
		data : findBean,
		successFn : function(data) {
			if (data.code == "000000") {
				loadJqGridPageData($('#dataTbl'), data.responseEntity);
			}else{
			  msgShow(systemWarnLabel,  data.code+":" + data.message, 'warning'); 
			}		
		}
	});
}

function resetDataQuery() {	
	$('#findTB').find('input,select').each(function(){
      $(this).val('');
    });
}

function unbundlingCard() {
	var selarrrow = $("#dataTbl").getGridParam('selarrrow');// 多选
	if (selarrrow !=null && selarrrow.length > 0) {
		$.messager.confirm(systemConfirmLabel, "确定要解绑用户卡片吗？", function(r) {
			if (r) {
				var userCardIds = new Array();
				for(var i=0; i<selarrrow.length; i++) {
					var rowData = $("#dataTbl").getRowData(selarrrow[i]);					
					if(rowData.bundLingTypeView==1){
					continue;
					}
					userCardIds.push(rowData.userCardId);
				}
				if(userCardIds.length==0){
				 return ;
				}
				ddpAjaxCall({
					url : "unbundUserCards",
					data : userCardIds,
					successFn : afterFindData
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}
function startUser() {
	var selrow = $("#userTbl").getGridParam('selrow');
	if (selrow) {
		var user = $('#userTbl').getRowData(selrow);
		$.messager.confirm(systemConfirmLabel, "确定要启用用户信息吗？", function(r) {
			if (r) {
				ddpAjaxCall({
					url : "startUser",
					data : user.userId,
					successFn : afterFindData
				});
				
			}
		});
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
}

function retFindJosn(){
    var str = "";     
    $('#findTB').find('input,select').each(function(){
      $this = $(this);      
      var name = $this.attr('name');
      str = str + name + ' : "' + $this.val() + '",'     
    });
    str = str.substr(0,str.length-1);
    var retJson = '[{ ' + str + ' }]';
    return retJson;
}
//数据导出
var expConfInfo = {
	"btnId"			: "btnSelExcCol", 					
	"typeSelStr"	: "MerUserCardBDBean", 	
	"toUrl"			: "exportExcelMerUserCardListControl" ,
	"parDiaHeight"  : "170"
};
var filterConStr = [
		{'name':'merUserName', 			'value': "escapePeculiar($.trim($('#merUserName').val()))"},	
		{'name':'merUserNameName',		'value': "escapePeculiar($.trim($('#merUserNameName').val()))"},
		{'name':'bundLingDateStart', 	'value': "$('#bundLingDateStart').val()"},	
		{'name':'bundLingDateEnd',		'value': "$('#bundLingDateEnd').val()"},
		{'name':'unBundLingDateStart', 	'value': "$('#unBundLingDateStart').val()"},	
		{'name':'unBundLingDateEnd',	'value': "$('#unBundLingDateEnd').val()"},
		{'name':'cardCode', 			'value': "escapePeculiar($.trim($('#cardCode').val()))"},	
		{'name':'cardType',				'value': "$('#cardType').combobox('getValue')"}
		
];

</script>