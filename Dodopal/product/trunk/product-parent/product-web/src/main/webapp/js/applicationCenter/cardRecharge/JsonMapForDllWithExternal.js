
function createFindBean(){
	var crMerCode = "";
	if(null!=merBean.merCode){
		crMerCode = merBean.merCode;
	}
	var map = {
			"mercode":crMerCode,
			"usercode":operatorId,
			"source":"0",
			"userid":merBean.userId
	};
	return map;
}
function createOrderBean(){
	//圈存订单号
	var cOrderNum = "";
	if($("#orderA").hasClass('a-click')){
		cOrderNum=$("#orderA").attr("ordCode");
		
	}
	if(payWayId==null){
		payWayId="";
	}
	if(cOrderNum==null){
		cOrderNum="";
	}
	//圈存订单，支付id置空
	if(cOrderNum!=""){
		payWayId="";
		payType="";
	}
	
	var crMerCode = "";
	if(null!=merBean.merCode){
		crMerCode = merBean.merCode;
	}
	var map = {
			"productcode":productcode,//产品编号
			"mercode":crMerCode,
			"chargetype":"0",//充值类型 0:钱包;1:月票
			"paytype":payType,//支付类型
			"loadordernum":cOrderNum+"",//圈存订单号
			"payway":payWayId//支付方式 "0","通用支付方式" "GW_ALL", "通用支付方式"
			}
	return map;
}
function createRechargeBean(bean,merordercode){
//	var cOrderNum = $("input[name='orderList']:checked").val();
	var isloadflag = "";
	if(productcode==""){
		//产品code 为空 为圈存
		isloadflag = isflagtrue;
	}else{
		isloadflag = isflagfalse;
	}
	var crMerCode = "";
	if(null!=merBean.merCode){
		crMerCode = merBean.merCode;
	}
	var map = {
		"mercode":crMerCode,
		"merordercode":merordercode,
		"prdordernum":bean.prdordernum
		}
	return map;
}