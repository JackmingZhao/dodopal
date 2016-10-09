$(function() {
	$('#merchant').bind('click', function(){
		transfer("transferMerchant");
	});

	$('#group').bind('click', function(){
		transfer("transferGroup");
	});
	
	$('#groupproxy').bind('click', function(){
		transfer("transferGroupProxy");
	});
	
	$('#otherproxy').bind('click', function(){
		transfer("transferOtherProxy");
	});

	$('#gr').bind('click', function(){
		addGr();
	});

	$('#qupici').bind('click', function(){
		findTest();
	});
	
	findTest();
});

function transfer(url) {
	var citycode = $('#citycode').val();
	$.ajax({
		type : "POST",
		url : url,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(citycode),
		success : function(data, status) {
			findTestOne(data.responseEntity);
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}




function findTestOne(batchId) {
	var logTransfer = {
			batchId : batchId
		};
	$.ajax({
		type : "POST",
		url : "findLogTransfer",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(logTransfer),
		success : function(data, status) {
			console.log(data);
			buildPage(data.responseEntity);
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}
function findTest(batchId) {
	var logTransfer = {
			batchId : $("#batchId").val()
		};
	$.ajax({
		type : "POST",
		url : "findLogTransfer",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(logTransfer),
		success : function(data, status) {
			console.log(data);
			buildPage(data.responseEntity);
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}
function addGr() {
	var totalPages = $("#totalPages").val();
	$.ajax({
		type : "POST",
		url : "findGRSysuserstb",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(totalPages),
		success : function(data, status) {
			findTest();
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}
function buildPage(data) {
	$("#displayTbl tr:gt(0)").remove();
	var lines = "";
	$.each(data, function(index, element) {
		lines += "<tr><td>" + element.id + "</td>";
		lines += "<td>" + element.batchId + "</td>";
		lines += "<td>" + element.oldMerchantId + "</td>";
		lines += "<td>" + element.oldMerchantType + "</td>";
		lines += "<td>" + element.newMerchantCode + "</td>";
		lines += "<td>" + element.newMerchantType + "</td>";
		lines += "<td>" + element.state + "</td>";
	});
	lines += "</tr>";
	$('#displayTbl').append(lines);
		
}


