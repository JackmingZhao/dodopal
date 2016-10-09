<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge"/>
	<#include "include.ftl"/>
<script type='text/javascript' src='http://localhost:8080/webstatic/js/common/jquery-easyui-1.3.1/jquery-1.8.0.min.js'></script>	
<script type="text/javascript">

$(function() {
	$('#sign').bind('click', function(){
		signTest();
	});
	
	
	
	$('#checkSign').bind('click', function(){
		checkSignTest();
	});

	
	$('#reset').bind('click', function(){		
		$('#findTB').find('input,select').each(function(){
		      $(this).val('');
		});
	});	
});
var flg= 0;
$().ready( function() {
document.getElementById('ocxIframeId').src="#";	
});

function testBtn() {
	
	$("#findTB").find("input[type=checkbox]").each(function(){	
	var $thisChild = $(this);
	    $thisChild.attr("checked",true);
	});
}

function checkSignTest() {
	
   var sign = $('#signVle').val();
	
	var jsonData = {
			name : $("#name").val(),
			appName : $("#appName").val(),
			description : $("#description").val(),
			id: $('#testId').val()
		};
	$.ajax({
		type : "POST",
		url : "checkSignTest?sign="+sign,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(jsonData),
		success : function(data, status) {
			$('#signRET').val(data);
		},
		failure : function(errMsg) {
			alert(errMsg);
			
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}

function signTest() {
	var jsonData = {
			name : $("#name").val(),
			appName : $("#appName").val(),
			description : $("#description").val(),
			id : $("#testId").val()
		};
	$.ajax({
		type : "POST",
		url : "signTest",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(jsonData),
		success : function(data, status) {
			$('#signVle').val(data);
			
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}

//通卡公司code
var ykt = "";
//版本号
var ver = "";
//CLASSID
var CLSID = "";

//过去根据城市获取：通卡公司code、版本号、ClassID
function getVser() {
    //TODO 城市code 
	var  city = "1791";
	
	$.ajax({
		type : "POST",
		url : "getOcxVer",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify(city),
		success : function(data, status) {
			alert("一卡通code = " + data.ykt + "+版本号==" + data.ver + " CLASSID==" + data.CLSID);
			ykt = data.ykt;
			ver = data.ver;
			CLSID = data.CLSID;
		},
		failure : function(errMsg) {
			alert(errMsg);
		},
        complete: function(XMLHttpRequest, textStatus) {
	    }
	});
}
var sss ="11111";
//加载OCX
function loadOCX() {
	
		
var oxcStr = '<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:'+CLSID+'" ';
		oxcStr+=' HEIGHT=0 WIDTH=0 codebase="${ocxUrl}/'+ykt+'.CAB#version=' + ver +'}" viewastext>';
		oxcStr+='<SPAN STYLE="color:red">控件加载失败! -- 请检查浏览器的安全级别设置.</SPAN></OBJECT>';
		alert(oxcStr);
		$("body").append(oxcStr);
		
	
}

//测试切换OCX
function changeOCX() {

//window.location.reload();

	document.getElementById('ocxIframeId').src="changeCity?city=100000";	
	
}

//测试是否安装成功
function testOCX() {

 try
	    {
			  var inparam = "{\"txnamt\":\"100\",\"prdordernum\":\"O2015092217011911303\"}";
	          OCXFAPAY.Dodopal_DebitForPurchaseAsync(inparam, jsDebitCallbackFunc);
				
	    }
	    catch(ex)
	    {
		      alert("Bolas_sarc failure\r\nDetail="+ex.description+"Dodopal_DebitForPurchaseAsync");
	    }
		
	
}

 function jsDebitCallbackFunc(debitResult)
  {
		try
	    {
			  
			  alert(debitResult);
				
	    }
	    catch(ex)
	    {
		      alert("Bolas_sarc failure\r\nDetail="+ex.description+"jsDebitCallbackFunc");
	    }
  }



</script>
<title>Test</title>

</head>
<body >
    <div id ="findTB">
    <hr/>
    </br>
	<table>
		<tr>
			<th>名称：</th>
			<td><input name="name" id="name" type="text" /></td>
			<th>描述:</th>
			<td><input name="description" id="description" type="text"/></td>
		</tr>
		<tr>
			<th>项目名:</th>
			<td><input name="appName" id="appName" ></input></td>
			<th>ID:</th>
			<td><input name="testId" id="testId" type="text"/></td>
		</tr>
		<button type="button" id="testBtn">测试</button>&nbsp;&nbsp;
		<tr>
			<th>项目名:</th>
			<td><input name="ck" id="01" type= "checkbox"></input></td>
			<th>ID:</th>
			<td><input name="ck" id="02" type= "checkbox"/></td>
		</tr>
	</table>
	</br>
	<button type="button" id="sign">MD5签名</button>&nbsp;&nbsp;
	签名后数据：<input name="signVle" value = "" id = "signVle" type="text"/>
	</br>
	</br>
	<button type="button" id="checkSign" >验签开始</button>&nbsp;&nbsp;
	验签结果：<input name="signRET" value = "" id = "signRET" type="text"/>
	</br>
	</br>
	<button type="button" id="reset" >重置</button>
	</br>
	</br>
	<input value = "${ocxUrl}" type="text"/>
	<button type="button"  onclick ="getVser()" >获取版本号</button>
	
	<button type="button"  onclick ="loadOCX()" >重新加载OCX</button>
	
	<button type="button"  onclick ="testOCX()" >测试OCX</button>
	<button type="button"  onclick ="changeOCX()" >切换OCX</button>
	</br>
	<hr/>
	
	
	</div>
	
	<iframe src="changeCity?city=1791" id="ocxIframeId"> </iframe>
	
</body>
</html>