function validar(form){

	if (!validaString(form.segmentoSgt,'Segmento',true)){
		return(false);
	}
	if (!validaString(form.tituloSgtc,'Titulo',true)){
		return(false);
	}
	if (!validaString(form.descricaoSgtc,'Descri��o',true)){
		return(false);
	}	
	return true;
}