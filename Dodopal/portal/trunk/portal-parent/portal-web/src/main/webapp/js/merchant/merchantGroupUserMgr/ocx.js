
var merInfo="";
function loadMerCity(){
	ddpAjaxCall({
		url : $.base + "/merchantGroupUser/getMerCity",
		data : '',
		successFn : function(data) {
			if(data.code=="000000"){
				var bean = data.responseEntity;
				getOCX(bean.ocxCLSID,bean.ocxVersionId,bean.ocxName);
				merInfo = bean;
				$("#choiceCity").show();
				var hotCity = "";
				$.each(bean.areaList,function (i, value){
					hotCity += "<li><a href='javascript:toChangeCity(\""+value.cityName+"\",\""+value.cityCode+"\");'>"+value.cityName+"</a></li>";
				});
				$("#hotCity").append(hotCity);
				$("#cityName").val(merInfo.cityName);
				$("#cityCode").val(merInfo.cityCode);
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}
		}
	});
}
function createBean(){
	var map = {
			"mercode":merInfo.merCode,
			"usercode":merInfo.userCode,
			"source":"0",
			"userid":merInfo.userId
	};
	return map;
}
function findCard(){
	var map = createBean();
	queryBean=JSON.stringify(map);
	console.log(queryBean);
	try{
		OCXFAPAY.Dodopal_CheckCardInRechargePhaseAysnc(queryBean,cardInfoBack);
	}catch(e){
		if(null==merInfo.ocxVersionId||""==merInfo.ocxVersionId){
			$.messagerBox({type: 'error', title:"信息提示", msg: "请联系管理员配置控件"});
		}
	}
}

function cardInfoBack(rebackParm){
	console.log(rebackParm);
	var ocxBean = eval("("+rebackParm+")");
	if(ocxBean.code=="000000"){
		$("#cardCode").val(ocxBean.tradecard);
		var carType = "";
		if("1"==ocxBean.cardtype){
			carType = "CPU";
		}else if ("2"==ocxBean.cardtype){
			carType = "M1";
		}else{
			carType = ocxBean.cardtype;
		}
		$("#cardType").val(carType);
	}else if(ocxBean=="181002"){
		$.messagerBox({type: 'error', title:"信息提示", msg:"请检查是否插入了pos"});
	} else{
		$.messagerBox({type: 'error', title:"信息提示", msg:ocxBean.message });
	}
	
}
function toChangeCity(name,code){
	$("#cityCode").val(code);
	$("#cityName").val(name);
	getOCX();
	window.location.href = $.base + "/merchantGroupUser/toChangeCity?cityCode="+code;
}
function getOCX(clsId,versionId,yktCode,msg){
	if(""==clsId||""==versionId||""==yktCode||null==clsId||null==versionId){
		return;
	}
	var defaultMsg = "控件加载失败! -- 请检查浏览器的安全级别设置.";
	if(msg!=undefined){
		defaultMsg = msg;
	}
	var oxcStr = '<OBJECT ID="OCXFAPAY"  name="OCXFAPAY" CLASSID="CLSID:'+clsId+'" ';
	oxcStr+=' HEIGHT=0 WIDTH=0 codebase="'+$.ocxUrl+'/'+yktCode+'.CAB#version='+versionId+'" viewastext>';
	oxcStr+='<SPAN STYLE="color:red">'+defaultMsg+'</SPAN></OBJECT>';
	$("body").prepend(oxcStr);
}