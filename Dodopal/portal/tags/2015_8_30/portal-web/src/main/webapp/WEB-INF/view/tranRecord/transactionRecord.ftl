<#assign sec=JspTaglibs["/WEB-INF/tld/portalTagLib.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<#include "../include.ftl"/>
<meta http-equiv="Content-Type" content="text/html; cha rset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>交易记录</title>
<link href="${base}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/common/ddpPaginator.js"></script>
<script src="${base}/js/common/select.js"></script>
<script src="${base}/js/common/area.js"></script>
<script type="text/javascript" src="../js/tranRecord/transactionRecord.js"></script>
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<#include "../header.ftl"/>
<div class="con-main"> <!-- InstanceBeginEditable name="main" -->
<div class="seach-top-box com-top-bor" id="tranRecordList">
		<div class="seach-top-inner">
			<form action="/" id="recordForm">
				<ul class="clearfix">
					<li class="w320">
						<span class="w100">起止日期：</span>
							<input name="createDateStart" type="text" class="com-input-txt w74"  placeholder="起始时间" id="createDateStart" onfocus="c.showMoreDay = false;c.show(this,'');"/>
							-
							<input name="createDateEnd" type="text" class="com-input-txt w74"  placeholder="结束时间" id="createDateEnd" onfocus="c.showMoreDay = false;c.show(this,'');"/>
					</li>
					<li class="w210"><span>支付方式：</span>
						<select name="tranName" id="tranName">
						</select>
					</li>
                    <li class="w210"><span>交易类型：</span>
						<select name="tranType" id="tranType">
						<option selected="selected" value="">--请选择--</option>
							<option value="0">待支付</option>
							<option value="1">已取消</option>
							<option value="2">支付中</option>
							<option value="3">已支付</option>
							<option value="4">待退款</option>
							<option value="5">已退款</option>
							<option value="6">待调账</option>
							<option value="7">已调账</option>
						</select>
					</li>
					<li class="btn-list">
						<input type="button" value="查询" class="orange-btn-h26" onclick="findTranRecord()"/>
						<input type="button" value="清空" class="orange-btn-text26" onclick="clearTranRecord()"/>
					</li>
				</ul>
				<ul class="clearfix">
					<li class="w320"><span class="w100">交易金额范围：</span>
						<input type="text" class="com-input-txt w74" id="realMinTranMoney" name="realMinTranMoney"/>
						-
						<input type="text" class="com-input-txt w74" id="realMaxTranMoney" name="realMaxTranMoney"/>
						元</li>
                     <li class="w210"><span>交易状态：</span>
						<select id="tranOutStatus" name="tranOutStatus">
						</select>
					</li>					
				</ul>
			</form>
		</div>
	 <div class="com-bor-box02">
        <ul class="navi-ul01 clearfix">
			<li class="fr">金额单位(元)</li>
		</ul>
        </div>
		<table cellpadding="0" cellspacing="0" class="com-table01 mb20" id="transactionRecordTb">
			<colgroup>
			    <col width="2%" />
			    <col width="15%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="10%" />
			    <col width="11%" />
			    <col width="20%" />
		        <col width="11%" />
		        <col width="2%" />
	        </colgroup>
			<thead>
	        	<tr>
			      <th></th>
			      <th>交易流水号</th>
			      <th>业务名称</th>
			      <th>交易金额</th>
			      <th>支付方式</th>
			      <th>交易类型</th>
			      <th>交易状态</th>
			      <th>交易日期</th>
			      <th class="a-center">操作</th>
			      <th></th>
		       </tr>
		    </thead>
		    <tbody></tbody>
		</table>
		<p class="page-navi">
		</p>
	</div>
	<!-- InstanceEndEditable --> </div>
<#include "../footer.ftl"/>
</body>
<!-- InstanceEnd --></html>
