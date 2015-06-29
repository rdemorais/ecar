function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	} 
		
}

function limparListBox(listObj){
		
	var listLength = listObj.length;	
	if(listLength == undefined) {
		listObj.selected = false;		
	}
	for(var i = 0; i < listLength; i++) {
		listObj.options[i].selected = false;
	}
		
}