function validar(form){

	if (!validaString(form.nomeCtta,'Nome',true)){
		return(false);
	}
	if (!validaString(form.assuntoRetornoCtta,'Assunto de Retorno',true)){
		return(false);
	}
	return true;
}