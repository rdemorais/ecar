function validar(form){
	if(!validaString(form.descricaoSgtif, "Descri��o", true)){
		return(false);
	}
	return(true);
}

