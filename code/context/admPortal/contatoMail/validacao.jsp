function validar(form){

	if (!validaString(form.contatoAreaCtta,'Área de Contato',true)){
		return(false);
	}
	if (!validaString(form.nomeCttm,'Descrição',true)){
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