<!-- 需要在引用该模块出引入对应的公共资源 -->
	<!-- 商户快速查询模块 开始 -->
	<div id="posSearchDialog" title="pos号" draggable="false" resizable="true">
		 <div class="easyui-layout" fit="true">  
	        <!--商户查询条件 开始-->
			<div region="north" border="false" style="height:60px;overflow: hidden;">
				<table id="posQueryCondition" class="viewtable" style="padding:4px;">
					<tr>
						<td>
						    POS编码
							<input name="posQuery" id="posQuery" type="text" class="easyui-validatebox" style="width:200px; height:19px;"/>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="findpos();">查询</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-eraser" onclick="clearAllText('posQueryCondition');">重置</a>
								&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectPos();">确定</a>
						</td>
					</tr>
				</table>
			</div>
			<!-- 商户查询条件 结束 -->
	        <!--商户列表显示 开始 -->
			<div region="center" border="false" style="overflow: hidden;">
				<table id="posListTbl"></table>
			</div>
			<div id="posListTblPagination"></div>
			<!-- 商户列表显示 结束 -->
    	 </div>  
	</div>
	<!-- 商户快速查询模块 结束 -->
