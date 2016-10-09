<script type="text/javascript">
$(function() {
	initMainComponent();
	initDetailComponent();
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
	    colNames : [ 'id', '用户名', '姓名','卡号','卡片名称','卡类型','卡所属城市','来源','操作动作','操作时间', '操作人姓名'],
		colModel : [ 
		{
			name : 'id',
			index : 'id',
			hidden : true ,
			frozen:true
		},{
			name : 'merUserName',
			index : 'merUserName',
			width : 100,
			align : 'left',
			sortable : false,
			frozen:true
		}, {
			name : 'merUserNickName',
			index : 'merUserNickName',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: htmlFormatter
		},{
			name : 'code',
			index : 'code',
			width : 140,
			align : 'left',
			sortable : false
		},{
			name : 'cardName',
			index : 'cardName',
			width : 130,
			align : 'left',
			sortable : false,
			formatter: htmlFormatter
		}, {
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
		},{
			name : 'source',
			index : 'source',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
			
				if(cellval == '0') {return '门户'} 
				else if(cellval == '1') {return 'OSS'} 
				else if(cellval == '2') {return '手机端'} 
				else if(cellval == '3') {return '客户端'} 
				else if(cellval == '4') {return '商用机'} 
				else { return cellval};
			}
		},{
			name : 'operStatus',
			index : 'operStatus',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: function(cellval, el, rowData) {
			
				if(cellval == '0') {return '绑定'} 
				else if(cellval == '1') {return '解绑'	} 
				else { return cellval};
			}
		}, {
			name : 'createDate',
			index : 'createDate',
			width : 150,
			align : 'left',
			sortable : false,
			formatter: cellDateFormatter
		}, {
			name : 'operName',
			index : 'operName',
			width : 100,
			align : 'left',
			sortable : false,
			formatter: htmlFormatter
		}],
		multiselect: false,
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



function findData(defaultPageNo) {
	var findBean = {
	    merUserName : escapePeculiar($.trim($('#merUserName').val())),
	    code : escapePeculiar($.trim($('#code').val())),
	    operName : escapePeculiar($.trim($('#operName').val())),
	    source : $('#source').combobox('getValue'),
		page : getJqgridPage('dataTbl',defaultPageNo),
		cardType: $('#cardType').combobox('getValue')
	}
	ddpAjaxCall({
		url : "findUserCardsLog",
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
//数据导出
var expConfInfo = {
  "btnId"         : "btnSelExcCol",                   
  "typeSelStr"    : "MerUserCardBDLogBean",  
  "toUrl"         : "exportExcelUserCardLogControl"  ,
  "parDiaHeight"  : "170"
};
var filterConStr = [
      {'name':'merUserName',          'value': "escapePeculiar($.trim($('#merUserName').val()))"},    
      {'name':'code',      'value': "escapePeculiar($.trim($('#code').val()))"},
      {'name':'operName',    'value': "$('#operName').val()"},  
      {'name':'source',             'value': "$('#source').combobox('getValue')"},
      {'name':'cardType',             'value': "$('#cardType').combobox('getValue')"}
];

</script>