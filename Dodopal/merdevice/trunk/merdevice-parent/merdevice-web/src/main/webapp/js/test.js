$(function() {
	$('#add').bind('click', function(){
		insertTest();
	});
	
	$('#delete').bind('click', function(){
		deleteTest($('#testId').val());
	});
	
	$('#update').bind('click', function(){
		updateTest();
	});
	
	$('#find').bind('click', function(){
		findTest();
	});
	
	$('#reset').bind('click', function(){
		$("#name").val("");
		$("#appName").val("");
		$("#description").val("");
		$('#testId').val("");
	});
	
	findTest();
	
});

function updateTest() {
	if($('#testId').val() == '') {
		alert('请输入要修改的数据ID');
		return;
	} else if($('#name').val() =='' && $('#appName').val() =='' && $('#description').val() =='') {
		alert('请输入要修改的信息.');
		return;
	}

	
	var jsonData = {
			name : $("#name").val(),
			appName : $("#appName").val(),
			description : $("#description").val(),
			id: $('#testId').val()
		};
	$.ajax({
		type : "POST",
		url : "updateTest",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(jsonData),
		success : function(data, status) {
			alert(data);
		},
		failure : function(errMsg) {
			alert(errMsg);
			findTest();
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}

function insertTest() {
	var jsonData = {
			name : $("#name").val(),
			appName : $("#appName").val(),
			description : $("#description").val(),
		};
	$.ajax({
		type : "POST",
		url : "addTest",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(jsonData),
		success : function(data, status) {
			alert(data);
			findTest();
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}

function deleteTest(testId) {
	if(testId == '') {
		alert('请输入要删除的数据ID');
		return;
	}
	$.ajax({
		type : "POST",
		url : "deleteTest",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(testId),
		success : function(data, status) {
			alert(data);
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
	var jsonData = {
			name : $("#name").val(),
			appName : $("#appName").val(),
			description : $("#description").val(),
		};
	$.ajax({
		type : "POST",
		url : "findTest",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(jsonData),
		success : function(data, status) {
			buildPage(data);
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
		lines += "<td>" + element.name + "</td>";
		lines += "<td>" + element.appName + "</td>";
		lines += "<td>" + element.description + "</td>";
	});
	lines += "</tr>";
	$('#displayTbl').append(lines);
		
}


