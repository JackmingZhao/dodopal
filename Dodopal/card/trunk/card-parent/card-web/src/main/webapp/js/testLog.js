$(function() {
	$('#checkCard').bind('click', function() {
		checkCard();
	});
	$('#createCard').bind('click', function() {
		createCard();
	});
	$('#getLoadOrderFun').bind('click', function() {
		getLoadOrderFun();
	});
	$('#checkCardCosum').bind('click', function() {
		checkCardCosum();
	});
	$('#upload').bind('click', function() {
		upload();
	});
	$('#getLoadOrderCosum').bind('click', function() {
		getLoadOrderCosum();
	});
});

function getLoadOrderCosum() {
	var json = $("#josnText").val();
	ddpAjaxCall({
		url : "getLoadOrderCosum",
		data : json,
		successFn : function(data, status) {
			alert(data);
		}
	});
}

function upload() {
	var json = $("#josnText").val();
	ddpAjaxCall({
		url : "upload",
		data : json,
		successFn : function(data, status) {
			alert(data);
		}
	});
}

function checkCardCosum() {
	var json = $("#josnText").val();
	ddpAjaxCall({
		url : "checkCardCosumLocal",
		data : json,
		successFn : function(data, status) {
			alert(data);
		}
	});
}

function checkCard() {
	var json = $("#josnText").val();
	ddpAjaxCall({
		url : "checkCardLocal",
		data : json,
		successFn : function(data, status) {
			console.log(data);
		}
	});
}

function sleep(numberMillis) { 
	   var now = new Date();
	   var exitTime = now.getTime() + numberMillis;  
	   while (true) { 
	       now = new Date(); 
	       if (now.getTime() > exitTime)    return;
	    }
}

function createCard() {
	var json = $("#josnText").val();
	ddpAjaxCall({
		url : "createCardLocal",
		data : json,
		successFn : function(data, status) {
			alert(data);
		}
	});
}

function getLoadOrderFun() {
	var json = $("#josnText").val();
	ddpAjaxCall({
		url : "getLoadOrderFunLocal",
		data : json,
		successFn : function(data, status) {
			alert(data);
		}
	});
}

function findAccountInfo() {
	var acid = $("#josnText").val();
	ddpAjaxCall({
		url : "findAccountInfo",
		data : acid,
		successFn : function(data, status) {
			alert(data);
		}
	});
}

function findAccountInfoList() {
	var acid = $("#josnText").val();
	ddpAjaxCall({
		url : "findAccountInfoList",
		data : acid,
		successFn : function(data, status) {
			alert(data);
		}
	});
}
