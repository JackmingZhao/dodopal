<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>供应商商户管理-pos信息管理</title>
<!-- InstanceEndEditable -->
<#include "../include.ftl"/>
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="${scriptUrl}/common/file/jquery.fileDownload.js"></script>
<script type="text/javascript" src="../js/prvd/rechargeForSupplier.js"></script>
<link href="${styleUrl}/product/css/calendar.css" rel="stylesheet" type="text/css" />
<link href="${styleUrl}/portal/css/m-base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var c = new Calendar("c");
document.write(c);
var baseUrl = "${base}";
</script>
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main" id="rechargeForSupplierForm"> <!-- InstanceBeginEditable name="main" -->
 <#include "../prvd/childrenCardConsumForSupplier.ftl"/> 
	<div class="seach-top-box">
		<div class="seach-top-inner">
			<form action="exportRechargeForSupplier" id="recordForm">
				<ul class="clearfix">
					<li>
						<label><span class="w70">POS号：</span>
							<input type="text" class="com-input-txt w88" id="proCode" name="proCode"/>
						</label>
					</li>
					<li>
						<label><span class="w90">当前绑定商户：</span>
							<input type="text" class="com-input-txt w88" id="merName" name="merName"/>
						</label>
					</li>
					<li>
						<span class="w70">启用标识：</span>
							<select name="bind" id="bind">
								<option value="0" selected>启用</option>
								<option value="1">停用</option>
								<option value="">全部</option>
							</select>
						
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findrechargeForSupplier()"/>
						<input type="button" value="清空" class="orange-btn-text26"  onclick="clearRecharge('recordForm')"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="com-bor-box ">
		<ul class="navi-ul clearfix">
			<li><a href="javascript:void(0);" onclick="startOrStopUser(true);" id="qiyong"  disabled="disabled" class="gray-btn-h22 w66">启用</a></li>
			<li><a href="javascript:void(0);" onclick="startOrStopUser(false);" id="tingyong" disabled="disabled" class="gray-btn-h22 w66">停用</a></li>
		</ul>
		<table width="100%" border="0" id="supplierTb" cellspacing="0" cellpadding="0" class="com-table01">
			<colgroup>
			<col width="2%" />
			<col width="3%" />
			<col width="15%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="6%" />
			<col width="2%" />
			</colgroup>
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th class="a-center"><input type="checkbox" onclick="toggle(this,'rechargeForSuppliers');toggleActivateBtn('rechargeForSuppliers');" id="allChild"/></th>
				<th>POS号</th>
				<th>启用标识</th>
				<th>充值交易笔数</th>
				<th>充值交易金额（元）</th>
				<th>当前绑定商户</th>
				<th class="a-center">操作</th>
				<th>&nbsp;</th>
			</tr>
			</thead>
				<tbody></tbody>
		</table>
		<div class="null-box" style="display:none;"></div>
		<p class="page-navi" id="rechargeForSupplier"><span class="fl">交易记录下载：
		<a href="#" class="easyui-linkbutton" id="exportSupplier" onclick="exportExcel()">导出Excel</a></span></p>
	</div>
 </div>
 <#include "rechargeForDetails.ftl"/>
<script type="text/javascript">
$(document).ready(function(e) {
$('[js="tijiao"]').click(function(){
		$('[js="tijiaoBox"]').show();
	});

	$('[js="zhixing"]').click(function(){
		$('[js="zhixingBox"]').show();
	});
	$('[js="del"]').click(function(){
		$('[js="delBox"]').show();
	});
	$('[js="moban"]').click(function(){
		$('[js="mobanBox"]').show();
	});
	if($('.com-table01 :checked').length>0){
		$('[js="tijiao"]').attr('class','orange-btn-h22 w66');
		$('[js="zhixing"]').attr('class','orange-btn-h22');
		$('[js="del"]').attr('class','orange-btn-h22');
	};
	$('.com-table01 [type="checkbox"]').click(function(){
		if($('.com-table01 :checked').length>0){
			$('[js="tijiao"]').attr('class','orange-btn-h22');
			$('[js="zhixing"]').attr('class','orange-btn-h22');
			$('[js="del"]').attr('class','orange-btn-h22');
		}else{
			$('[js="tijiao"]').attr('class','gray-btn-h22');
			$('[js="zhixing"]').attr('class','gray-btn-h22');
			$('[js="del"]').attr('class','gray-btn-h22');
		};
	});

});
	$('.header-inr-nav ul li a').each(function(){
		if($.trim($(this).text())=="POS信息管理"){
			$(this).addClass('cur');
		}
	});
	$('.tit-h3 a').each(function(){
		if($.trim($(this).text())=="一卡通充值"){
			$(this).addClass('cur');
		}
	});
</script>
<div class="pop-win" js="tijiaoBox" style="display: none;">
	<div class="bg-win"></div>
	<div class="pop-bor"></div>
	<div class="pop-box">
		<h3>信息提示</h3>
		<div class="innerBox">
			<div class="tips">
            <div class="alert-icons alert-icons1"></div>
            <span>您确认停用？</span></div>
			<ul class="ul-btn">
				<li><a href="javascript:;" class="pop-borange" onclick="popclo(this)">确认</a></li>
				<li><a href="javascript:;" js="clopop" class="pop-bgrange">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- InstanceEndEditable --> 
<div class="foot">
    <div class="footer-copy">
        <div class="w1030 footer-p">
            <p><a href="http://www.dodopal.com/">关于都都宝</a>&nbsp;|&nbsp;官方微博 <a href="#">http://weibo.com/dodopal</a></p>
            <p>都都宝版权所有&nbsp;COPYRIGHT (C) 2014 ICDC(Beijing) ALL RIGHTS RESERVED 京ICP证100323号</p>
            <p class="orange">使用完毕请安全退出，请勿泄露自己的登录名和密码</p>
        </div>
    </div>
</div>
</body>
<!-- InstanceEnd --></html>
