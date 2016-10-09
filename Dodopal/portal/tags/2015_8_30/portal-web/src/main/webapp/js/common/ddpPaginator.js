$.widget('ui.paginator', {
	options : {
		rowList : [10,20,50,100],
		prefix : 'pager',
		pageSizeOnChange: function(){}
	},
	_create : function() {
		var prefix = this.options.prefix;
		
		var html = '&nbsp;总记录数  <span id="'+prefix+'_totalRows"> </span>(&nbsp;共 <span id="'+prefix+'_totalPage" ></span> 页&nbsp;) '; 
		html += '每页显示：';
		html += '<select id="'+prefix+'_pageSize">';
		
		$(this.options.rowList).each(function(i,v){
			html += '<option value="'+v+'">'+v+'</option>';
		})
		
		html += '</select>';
		html += '条<a href="javascript:void(0);" id="'+prefix+'_firstPage">&lt;&lt; 首页</a>';
		html += '<a href="javascript:void(0);" id="' +prefix+'_prevPage">&lt;上一页</a>';
		html += '第<input type="text" id="'+prefix+'_currentPageDisplay" >页';
		html += '<a href="javascript:void(0);" id="'+prefix+'_nextPage">下一页&gt;</a>'
		html += '<a href="javascript:void(0);" id="'+prefix+'_lastPage">尾页&gt;</a>'
		
		html += '<input type="hidden" id="' +prefix+'_currentPage" value="1">';
		$(this.element).append(html);
		
		var _this = this;
		$('#'+prefix + '_pageSize').change(function(){
			_this.options.pageSizeOnChange({pageNo:1,pageSize:$('#'+prefix + '_pageSize').val()});
		});
		
	},
	getPage : function() {
		var prefix = this.options.prefix;
		return {pageNo:$('#' + prefix +"_currentPage").val(),pageSize:$('#' + prefix +"_pageSize").val()};
	},
	setPage : function(pageNo,pageSize,totalPage,rows) {
		if(pageNo==undefined||pageSize==undefined||totalPage==undefined||rows==undefined){
			pageNo=0;
			pageSize= this.options.rowList[0];
			totalPage=0;
			rows=0;
		}
		var prefix = this.options.prefix;
		var _this = this;
		
		//console.log('setPage :: pageNo :' + pageNo);
		//console.log('setPage :: pageSize :' +pageSize);
		//console.log('setPage :: totalPage:' + totalPage);
		//console.log('setPage :: rows:' + rows);
		
		$('#' + prefix +"_pageSize").val(pageSize);
		$('#' + prefix +"_currentPage").val(pageNo);
		$('#' + prefix +"_currentPageDisplay").val(pageNo);
		
		$('#' + prefix +"_totalRows").text(rows + '');
		$('#' + prefix +"_totalPage").text(totalPage + '');
		
		$('#'+prefix + '_currentPageDisplay').off('keypress');
		$('#'+prefix + '_currentPageDisplay').on( "keypress", function(evt){
			if(evt.keyCode==13){
				var currentPage = $('#' + prefix +"_currentPage").val();
				var pageN = $('#'+prefix + '_currentPageDisplay').val();
				if(!pageN){
					return;
				}
				if(isNaN(pageN)){
					$.messagerBox({type: 'warn', title:"信息提示", msg: "请输入正确页码。"});
					$('#'+prefix + '_currentPageDisplay').val(currentPage);
					return;
				}else{
					pageN = parseInt(pageN);
				
					if(pageN > totalPage){
						pageN = totalPage;
					}
					
					if(pageN < 1){
						pageN = 1;
					}
					console.log(pageN);
					_this.options.pageSizeOnChange({pageNo:pageN,pageSize:pageSize});
				}
			}
		});
		
		var prePageNo = pageNo - 1;
		if(prePageNo <= 0){
			$('#'+prefix + '_prevPage').off("click");
			$('#'+prefix + '_prevPage').attr('disabled',true);
			
			$('#'+prefix + '_firstPage').off("click");
			$('#'+prefix + '_firstPage').attr('disabled',true);
		}else{
			$('#'+prefix + '_prevPage').attr('disabled',false);
			$('#'+prefix + '_prevPage').off("click");
			$('#'+prefix + '_prevPage').click(function(){
				_this.options.pageSizeOnChange({pageNo:prePageNo,pageSize:pageSize});
			});
			
			$('#'+prefix + '_firstPage').attr('disabled',false);
			$('#'+prefix + '_firstPage').off("click");
			$('#'+prefix + '_firstPage').click(function(){
				_this.options.pageSizeOnChange({pageNo:1,pageSize:pageSize});
			})
		}
		
		var nextPageNo = pageNo + 1;
		if(nextPageNo > totalPage){
			$('#'+prefix + '_nextPage').off("click");
			$('#'+prefix + '_nextPage').attr('disabled',true);
			
			$('#'+prefix + '_lastPage').off("click");
			$('#'+prefix + '_lastPage').attr('disabled',true);
			
		}else{
			$('#'+prefix + '_nextPage').attr('disabled',false);
			$('#'+prefix + '_nextPage').off("click");
			$('#'+prefix + '_nextPage').click(function(){
				_this.options.pageSizeOnChange({pageNo:nextPageNo,pageSize:pageSize});
			});
			
			$('#'+prefix + '_lastPage').off("click");
			$('#'+prefix + '_lastPage').attr('disabled',false);
			$('#'+prefix + '_lastPage').click(function(){
				_this.options.pageSizeOnChange({pageNo:totalPage,pageSize:pageSize});
			})
		}
	}
});
