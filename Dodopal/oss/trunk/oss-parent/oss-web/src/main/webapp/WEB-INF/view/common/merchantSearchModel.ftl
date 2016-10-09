<!-- 需要在引用该模块出引入对应的公共资源 -->
	<!-- 商户快速查询模块 开始 -->
	<div id="merchantSearchDialog" title="上级商户名称" draggable="false" resizable="true">
		 <div class="easyui-layout" fit="true">  
	        <!--商户查询条件 开始-->
			<div region="north" border="false" style="height:60px;overflow: hidden;">
				<table id="merchantQueryCondition" class="viewtable" style="padding:4px;">
					<tr>
						<td>
							<select id="queryType" class="easyui-combobox" editable="false" style="width: 100px;">
									<option value="name" selected>商户名称</option>
									<option value="code">商户编码</option>
							</select>
							<input name="merchantQuery" id="merchantQuery" type="text" class="easyui-validatebox" style="width:200px; height:19px;"/>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findMerchantName();">查询</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('merchantQueryCondition');">重置</a>
								&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectMerchat();">确定</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- 商户查询条件 结束 -->
	        <!--商户列表显示 开始 -->
			<div region="center" border="false" style="overflow: hidden;">
				<table id="merchantListTbl"></table>
			</div>
			<div id="merchantListTblPagination"></div>
			<!-- 商户列表显示 结束 -->
    	 </div>  
	</div>
	<!-- 商户快速查询模块 结束 -->
