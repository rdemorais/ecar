function validar(form){

	if (!validaString(form.contatoAreaCtta,'�rea de Contato',true)){
		return(false);
	}
	if (!validaString(form.nomeCttm,'Descri��o',true)){
		return(false);
	}
	if (!validaString(form.emailCttm,'E-mail',true)){
		return(false);
	}
	if (!validaString(form.contatoMailCategoriaCttm,'Categoria',true)){
		return(false);
	}	
	return true;
}