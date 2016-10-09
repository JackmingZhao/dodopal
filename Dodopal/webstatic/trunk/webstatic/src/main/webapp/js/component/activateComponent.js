function createActivateComponentCombobox(elementName) {
	var query = '';
	query += '<select id="activate_' + elementName + '" />';
	
	$('#activate_' + elementName).combobox({
		valueField : 'id',
		textField :'text',
		data : [{
		    "id":0,
		    "text":"启用"
		},{
		    "id":1,
		    "text":"停用"
		}],
		editable : false,
		width : 50
	});
	alert(query);
	return query;
}

function getActivateSelectedValue(elementName) {
	return $('#activate_' + elementName).combobox('getValue');
}
