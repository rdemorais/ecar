function validar(form){

	if (!validaString(form.destaqueAreaDtqa,'Área',true)){
		return(false);
	}
	if (!validaString(form.identificacaoDtqsa,'Identificação',true)){
		return(false);
	}
	if (!validaString(form.descricaoDtqsa,'Descrição',true)){
		return(false);
	}
	if (!validaString(form.qtdMaxItensDtqsa,'Quantidade Máxima de Itens',true)){
		return(false);
	} else {
		if(!validaNum(form.qtdMaxItensDtqsa, 'Quantidade Máxima de Itens', true)){
			return(false);
		}	
	}
	if (!validaString(form.destaqueTipoOrdemDtqto,'Tipo de Ordenação',true)){
		return(false);
	}	
	return true;
}