
function validar(form){

	if(form.situacaoIettrev.value == 'X'){
		alert("Obrigatório o preenchimento do campo Nome!");
		return(false);
	}
	
	if(form.situacaoIettrev.value == 'S'){
		return(true);
	}
	

	if(!validaString(form.justificativaIettrev,'Justificativa',true)){
		return(false);
	}
	
	return(true);
}

