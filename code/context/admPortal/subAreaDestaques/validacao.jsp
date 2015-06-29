function validar(form){

	if (!validaString(form.destaqueAreaDtqa,'�rea',true)){
		return(false);
	}
	if (!validaString(form.identificacaoDtqsa,'Identifica��o',true)){
		return(false);
	}
	if (!validaString(form.descricaoDtqsa,'Descri��o',true)){
		return(false);
	}
	if (!validaString(form.qtdMaxItensDtqsa,'Quantidade M�xima de Itens',true)){
		return(false);
	} else {
		if(!validaNum(form.qtdMaxItensDtqsa, 'Quantidade M�xima de Itens', true)){
			return(false);
		}	
	}
	if (!validaString(form.destaqueTipoOrdemDtqto,'Tipo de Ordena��o',true)){
		return(false);
	}	
	return true;
}