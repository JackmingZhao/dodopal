$(function() {
	$('#lsmancht').bind('click', function(){
		addlsmancht();
	});
	$('#lsjmMancht').bind('click', function(){
		addlsjmMancht();
	});
	$('#groupInfo').bind('click', function(){
		addGroupInfo();
	});
	
	$('#gr').bind('click', function(){
		addGr();
	});
	
	$('#lszyAndJm').bind('click', function(){
		addLszyAndJm();
	});
	
	$('#ddpzy').bind('click', function(){
		addDdpzy();
	});
	
	
	findTest();
});

function addlsmancht() {
	$.ajax({
		type : "POST",
		url : "findAllBimchntinfotb",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		//data : JSON.stringify(testId),
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

function addDdpzy() {
	$.ajax({
		type : "POST",
		url : "findGRWD",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
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
function addLszyAndJm() {
	$.ajax({
		type : "POST",
		url : "findLianSuoAndJM",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
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

function addlsjmMancht() {
	$.ajax({
		type : "POST",
		url : "findAllSysuserstb",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		//data : JSON.stringify(testId),
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

function addGroupInfo() {
	$.ajax({
		type : "POST",
		url : "findAllGroupinfo",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		//data : JSON.stringify(testId),
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

function findTest() {
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


