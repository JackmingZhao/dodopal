var jqgrid_server_opts = {
	jsonReader: {
	      root: "records",
	      page: "pageNo",
	      total: "totalPages",
	      records: "rows",
	      repeatitems: false
	},
	rowNum : 20,
	rowList : [20,50,100],
	viewrecords : true,
	// onSortCol:jqGridClientSort,
	gridview : true,
	pagerpos :'left',
	onSelectRow : function(rowid, status) {
		if (status == true) {
			var tableId = $(this).attr('id');
			var headerCheckbox = 'cb_' + tableId;
			var selarrrow = $(this).getGridParam('selarrrow');
			var allData = $(this).jqGrid('getDataIDs');
			if (selarrrow != null && selarrrow.length == allData.length) {
				$('#' + headerCheckbox).attr('checked', 'checked');
			}
		}
	}
};

var jqgrid_local_opts = {
	datatype: "local",
	viewrecords : true
};


function getJqgridPage(grid, defaultPageNo) {
	var pageNo = $('#' + grid).getGridParam('page'); // current page
	if(pageNo < 1) {
		pageNo = 1;
	}
    var rows = $('#' + grid).getGridParam('rowNum'); // rows  
    if(rows < 20) {
    	rows = 20;
    }
    if(typeof(defaultPageNo) != 'undefined') {
    	pageNo = defaultPageNo;
    }
	var page = {
		pageNo : pageNo,
		pageSize : rows
	}
	return page;
}

//////////////////////////////JQGrid Common Formatter Start/////////////////////
function cellDateFormatterYYYYMMdd(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return formatDate(cellval, dateformatterYYYYMMdd);
	} else {
		return '';
	}
}

function cellDateFormatter(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return formatDate(cellval, dateformatterStr);
	} else {
		return '';
	}
}

function cellTimeFormatter(cellval, el, rowData) {
	if (typeof cellval == "string"){
		return cellval;
	}
	if(cellval != null && $.trim(cellval) != '') {
		return formatDate(cellval, timeFormatterStr);
	} else {
		return '';
	}
}

function jqDateFormate(date) {
	if(date != null && date != '') {
		return formatDate(date, dateformatterStr);
	} else {
		return '';
	}
}

/**
 * 显示到小数点后2位
 */
function roundDecimal(cellval, el, rowData) {
	if(isNaN(cellval)) {
		return cellval;
	} else {
		return parseFloat(cellval).toFixed(2);
	}
} 

/////////////用于用中文显示boolean///////////////////////////////////
function validFormatter(cellval,el,rowData) {
	if(cellval == true) {
		return '否';
	}else if(cellval == false) {
		return '是';
	}else {
		return cellval;
	}
}

function inActiveFormatter(cellval, el, rowData) {
	if(cellval == true) {
		return '已禁用';
	}else if(cellval ==false) {
		return '已激活';
	} else {
		return '';
	}
}

function YesNoFormatter(cellval,el,rowData){
	if (typeof cellval == "string"){
		return cellval;
	}
	if(cellval == true){
		return '是';
	}else{
		return '否';
	}
}

function invalidFormatter(cellval,el,rowData){
	if(cellval == false) {
		return '有效';
	}else if(cellval == true) {
		return '无效';
	}else {
		return cellval;
	}
}
////////////////////////////JQGrid Common Formatter End/////////////////////


//////////////////////////JQGrid Util Method Start/////////////////////
function loadJqGridData(userGrid, jsonData, rowNum) {
	userGrid.clearGridData();
	userGrid.resetSelection();
	if(jsonData != null) {
		for(var i=0;i<=jsonData.length;i++) {
			if(jsonData[i] != null) {
				userGrid.jqGrid('addRowData',i+1,jsonData[i]);
			}
		}
	}
	if(rowNum > 0) {
		userGrid.jqGrid("setGridParam", {  
	        rowNum: rowNum,  
	        page: 1
	    }).trigger('reloadGrid'); 
	} else {
		userGrid.trigger('reloadGrid');
	}
}

function loadJqGridPageData(userGrid, jsonData) {
	cacheDataPage(userGrid[0].id,jsonData);
	userGrid.clearGridData();
	userGrid.resetSelection();
	if(jsonData != null) {
		userGrid[0].addJSONData(jsonData);
	}
}

function getSelectedJqGridData(userGrid){
	return getJqGridData(userGrid,userGrid.getGridParam('selarrrow'));
}

function getJqGridData(userGrid, rowDataIds) {
	var gridDatas = new Array();
	if(rowDataIds != null && rowDataIds.length > 0) {
		for(var i=0; i<rowDataIds.length; i++) {
			var rowData = userGrid.getRowData(rowDataIds[i]);
			gridDatas.push(rowData);
		}
	}
	return gridDatas;
}

function deleteJqGridData(userGrid, rowDataIds) {
	if(rowDataIds != null && rowDataIds.length > 0) {
		for(var j=0; j<rowDataIds.length;j++) {
			userGrid.delRowData(rowDataIds[j]);
			userGrid.trigger('reloadGrid');
		}
	}
}

function appendJqGridRowData(userGrid, rowData) {
	var gridDatas = getJqGridData(userGrid, userGrid.getDataIDs());
	gridDatas.push(rowData);
	userGrid.clearGridData();
	if(gridDatas != null) {
		for(var i=0;i<=gridDatas.length;i++) {
			if(gridDatas[i] != null) {
				userGrid.jqGrid('addRowData',i+1,gridDatas[i]);
			}
		}
	}
	userGrid.trigger('reloadGrid');
}

function setJqGridRowData(userGrid,rowData){
	userGrid.jqGrid('setRowData',rowData.rowId,rowData);
}

function saveJqGridRowData(userGrid,rowData){
	if(!rowData.rowId){
		appendJqGridRowData(userGrid,rowData);
	}else{
		setJqGridRowData(userGrid,rowData);
	}
}

function showJqGridCol(userGrid, columnName, show) {
	if(show) {
		userGrid.jqGrid('showCol', columnName);
	} else {
		userGrid.jqGrid('hideCol', columnName);
	}
}


function sortObjects(array,sortBy,direction){
	if(!sortBy){
		return array;
	}
	var order = sortBy;
	if(direction != 'asc'){
		order = "-" + order;
	}
	return jLinq.from(array).sort(order).select();
}

function jqGridClientSort(ts,index,idxcol){
	var data = findDataPage(this.id);
	if(data){
		data.records = sortObjects(data.records,ts,idxcol);
	}
	$(this)[0].addJSONData(data);
	return 'stop';
}

var currentPages = {};
function cacheDataPage(name,datapage){
	currentPages[name] = datapage;
}

function findDataPage(name){
	return currentPages[name];
}

function selectAllLines(grid) {
	var ids = $("#" + grid).jqGrid('getDataIDs');
//	$('#cb_'+grid).attr("checked",true);
	$.each(ids, function(index, element) {
		$("#" + grid).jqGrid('setSelection', element);
	});
}

//从多选的表格获取一条数据，这里主要针对对已有数据的修改行为。有且只有当用户只选择一条数据的时候才返回数据。
function getSelectedRowDataFromMultiples(tbl) {
	$tbl = $('#'+tbl);
	var selarrrow = $tbl.getGridParam('selarrrow');// 多选
	if (selarrrow) {
		if (selarrrow.length == 1) {
			var rowData = $tbl.getRowData(selarrrow[0]);
			return rowData;
		} else if (selarrrow.length == 0){
			msgShow(systemWarnLabel, unSelectLabel, 'warning');
		} else {
			msgShow(systemWarnLabel, onlyAllowOneSelectLabel, 'warning');
		}
	} else {
		msgShow(systemWarnLabel, unSelectLabel, 'warning');
	}
	return null;
}
////////////////////////JQGrid Util Method End/////////////////////
