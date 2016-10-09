var ddpPermissions = [];
$(function(){
	//console.log('loading permissions ...');
	ddpAjaxCall({
		url : $.base + "/permissions/findAllPermissions",
		data : {},
		successFn : function(data) {
			//console.log('load permissions complete.');
			$(data).each(function(i,v){
				ddpPermissions.push(v);
			})
			//console.log(data);
		}
	});
	
})

function hasPermission(code){
	if(ddpPermissions.indexOf(code) == -1){
		return false;
	}else{
		return true;
	}
}