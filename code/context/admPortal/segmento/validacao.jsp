function validar(form){

	if (!validaString(form.segmentoArea,'�rea',true)){
		return(false);
	}
	if (!validaString(form.tituloSgt,'Titulo',true)){
		return(false);
	}
	if (!validaString(form.descricaoSgt,'Descri��o',true)){
		return(false);
	}	
	if (!validaString(form.segmentoLeiauteSgtl,'Leiaute do Segmento',true)){
		return(false);
	}	
	return true;
}