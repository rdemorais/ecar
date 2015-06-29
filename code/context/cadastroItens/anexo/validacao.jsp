function validar(form){

	if (form.validarArquivo.value == 'S' && !validaString(form.arquivoIettup,'Arquivo',true)){
		return(false);
	}	
	
	// validação adicionada devido ao BUG 1601
	if (!validaString(form.descricaoIettup,'Descrição',true)){
		return(false);
	}
	
	if (!validaString(form.uploadTipoArquivoUta,'Tipo',true)){
		return(false);
	}
	return true;
}