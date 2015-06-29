function validar(form){

	if (!validaString(form.codSegmento,'Segmento',true)){
		return(false);
	}
	if (!validaString(form.descricaoSatb,'Titulo',true)){
		return(false);
	}

	return true;
}