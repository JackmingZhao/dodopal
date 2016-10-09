$(function () {
	initMainComponent();
	expExcBySelColTools.initSelectExportDia(expConfInfo);
})

var sysQueYear;

function initMainComponent() {
    autoResize({
        dataGrid: {
            selector: '#statisticsDataTbl',
            offsetHeight: 90,
            offsetWidth: 0
        },
        callback: initStatisticsDataTbl,/* A */
        toolbar: [true, 'top']
    });
	$('#activeCityQuery').combobox({
		valueField : 'cityCode',
		textField : 'cityName',
		editable : false
	});
	var storeSysTime = function(){ddpAjaxCall({
		url : "getSysTime",
		successFn : function(data) {
			if (data.code == "000000") {
				var sysD = data.responseEntity.substr(0, 4);
				$('#queryDate').val(sysD);	// yyyyMMdd -> 
				setSysQueYear(sysD);
			} else { msgShow(systemWarnLabel, data.message, 'warning'); }
		}
	})};
	storeSysTime();
	ddpAjaxCall({
		url : "getCityInfo",
		successFn : function(data) {
			if (data.code == "000000") {
				data.responseEntity.unshift({"cityCode": "-1", "cityName": "--请选择--"});
				addTipsOption(data.responseEntity);
				reLoadComboboxData($('#activeCityQuery'), data.responseEntity);
				$('#activeCityQuery').combobox('select', '-1');
			}else { msgShow(systemWarnLabel, data.message, 'warning'); }
		}
	});
}

function initStatisticsDataTbl(size) {/* A */
    var option = {
        datatype: function (pdata) {
        	findStatisticsDatas();/* B */
        },
        colNames: ['城市', '时间', '充值笔数', '充值金额(元)', '活跃POS机', '活跃卡数', '平均：充值卡片数/每台 ','平均：充值金额(元)/每台'],
        colModel: [
                   {name: 'cityName', 			index: 'cityName', 			width: 150, 	align: 'center', 	sortable: false},
                   {name: 'yearMonthStr', 		index: 'yearMonthStr', 		width: 150, 	align: 'center', 	sortable: false, formatter: timeFormatter },
                   {name: 'orderNum', 			index: 'orderNum', 			width: 150, 	align: 'center', 	sortable: false},
                   {name: 'orderAmount', 		index: 'orderAmount', 		width: 150, 	align: 'right', 	sortable: false, formatter: cellDateFormatterYUAN },
                   {name: 'activePosNum', 		index: 'activePosNum', 		width: 150, 	align: 'center', 	sortable: false},
                   {name: 'activeCardNum', 		index: 'activeCardNum', 	width: 150, 	align: 'center', 	sortable: false},
                   {name: 'avgCardNumPerPos', 	index: 'avgCardNumPerPos', 	width: 150, 	align: 'center', 	sortable: false},
                   {name: 'amountPerPos', 		index: 'amountPerPos', 		width: 150, 	align: 'right', 		sortable: false, formatter: cellDateFormatterYUAN }
        ],
        height: size.height - 60,
        width: size.width,
        multiselect: false,
//        forceFit:true,
        autowidth : true,
        shrinkToFit : false,
        pager: '#statisticsDataTblPagination',
        toolbar: [true, "top"]
    };
    option = $.extend(option, jqgrid_server_opts);
    $('#statisticsDataTbl').jqGrid(option);
    $("#t_statisticsDataTbl").append($('#statisticsDataToolbar'));
}

function findStatisticsDatas(defaultPageNo) {/* B */
    ddpAjaxCall({
        url: "findCityCusRepStas",
        data: {
    		queryDate: $('#queryDate').val(),
    		cityCode: $('#activeCityQuery').combobox('getValue'),
            page: getJqgridPage('statisticsDataTbl', defaultPageNo)
        },
        successFn: function (data) {
            if (data.code == "000000") {
                loadJqGridPageData($('#statisticsDataTbl'), data.responseEntity);
            } else {
                msgShow(systemWarnLabel, data.message, 'warning');
            }
        }
    });
}

function cellDateFormatterYUAN(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return (cellval/100).toFixed(2);
	} else {
		return '';
	}
}

function timeFormatter(cellval, el, rowData) {
	if(cellval != null && $.trim(cellval) != '') {
		return (cellval+'').substr(0, 4)+'年'+(cellval+'').substr(4, 6)+'月';
	} else {
		return '';
	}
}

var sysQueYear='-1';
function setSysQueYear(str) { sysQueYear=str;return str; }

$(function() {	// event register
	$('#queryDate').on('click', function() {
		WdatePicker({dateFmt:'yyyy'})
	});
	$('#queryBtn').on('click', function() {
		findStatisticsDatas();
	});
	$('#cancelBtn').on('click', function() {
		$('#activeCityQuery').combobox('setValue', '-1');
		$('#queryDate').val(function(){if(sysQueYear == '-1') {storeSysTime();return sysQueYear;}else {return sysQueYear;}}());
	});
})

var expConfInfo = {
	"btnId"			: "btnSelExcCol", 
	"typeSelStr"	: "operCityCustomer",
	"toUrl"			: "exportExcelInfo"
};
var filterConStr = [
	{'name':'queryDate', 	'value': "$('#queryDate').val()"			},	// eval
	{'name':'cityCode',		'value': "$('#activeCityQuery').combobox('getValue')"			}
];

