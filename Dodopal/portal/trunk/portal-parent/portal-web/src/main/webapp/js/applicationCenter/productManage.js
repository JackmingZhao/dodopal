$(function(){
	findMerCitys();
	fidnProduct();
	$('.page-navi').paginator({
		prefix : 'productManage',
		pageSizeOnChange : fidnProduct
	});
});

function fidnProduct(page){
	if (!page) {
		page = {
			pageNo : 1,
			pageSize : 10
		};
	}
	var query ={
				cityId:$('#cityName').val(),
				page:page,
	}
	ddpAjaxCall({
		url : "findProductMage",
		data : query,
		successFn : function(data) {
			if (data.code == "000000") {
				var html = '';
				$('#productManageTb tbody').empty();
				if( data.responseEntity!=null &&  data.responseEntity.records.length > 0){
					$(data.responseEntity.records).each(function(i,v){
						i = i + 1;
						$('.null-box').hide();
						html = '<tr>';
						html +='<td class="nobor">&nbsp;</td>';
						html +='<td class="nobor"></td>';
						html += '<td>'+v.cityName+'</td>';
						html += '<td>'+v.proCode+'</td>'
						html += '<td>'+v.proName+'</td>';
						html += '<td>'+(Number(v.proPrice)/100).toFixed(2)+'</td>';
						html += '<td>'+(Number(v.proPayPrice)/100).toFixed(2) +'</td>';
						
						html +='<td class="nobor">&nbsp;</td>';
						html += '</tr>';
						$('#productManageTb').append(html);
					});
					$('.com-table01 tbody tr:even').find('td').css("background-color",'#f6fafe');
					$('.com-table02 tbody tr:even').find('td').css("background-color",'#f6fafe');	
					var pageSize = data.responseEntity.pageSize;
					var pageNo = data.responseEntity.pageNo;
					var totalPages = data.responseEntity.totalPages;
					var rows = data.responseEntity.rows;
					$('.page-navi').paginator('setPage',pageNo,pageSize,totalPages,rows);
					$('.page-navi select').attr("style","width:45px;padding:0px 0px");
				}
				 else {
						$('.null-box').show();
						$('.page-navi').paginator('setPage');
					}
			}else{
				$.messagerBox({type: 'warn', title:"信息提示", msg: data.message});
			}	
		}
	});
	
	
}

function fmoney(s, n)  
{  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("") + "." + r;  
}  

//初始化业务城市
function findMerCitys() {
	ddpAjaxCall({
				url : "findMerCitys",
				async : false,
				data : '',
				successFn : function(data) {
					$.each(data.responseEntity, function(key, value) {
								$('#cityName').append($("<option/>", {
											value : value.cityCode,
											text : value.cityName
										}));
							});
					var street = '上海';
					$('#cityName option:contains(' + street + ')').each(function(){
						if ($(this).text() == street) {
							$(this).attr('selected', true);
						}
					});
					selectUiInit();

				}
			});
}

function clearProduct(selector){
	$('#' + selector)[0].reset();
	selectUiInit();
}