function validar(form){
	if (!validaString(form.nomeIettuc,'Nome',true)){
		return(false);
	}
	if (!validaString(form.descricaoIettuc,'Descri��o',true)){
		return(false);
	}
	if (!validaString(form.uploadTipoCategoriaUtc,'Tipo',true)){
		return(false);
	}	
	return true;
}