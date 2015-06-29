function validar(form){

	if (!validaString(form.segmentoArea,'Área',true)){
		return(false);
	}
	if (!validaString(form.tituloSgt,'Titulo',true)){
		return(false);
	}
	if (!validaString(form.descricaoSgt,'Descrição',true)){
		return(false);
	}	
	if (!validaString(form.segmentoLeiauteSgtl,'Leiaute do Segmento',true)){
		return(false);
	}	
	return true;
}