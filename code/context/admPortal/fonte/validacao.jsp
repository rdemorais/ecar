function validar(form){
	if(!validaString(form.descricaoSgtif, "Descrição", true)){
		return(false);
	}
	return(true);
}

