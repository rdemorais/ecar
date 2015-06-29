function validar(form){

	if (!validaString(form.identificacaoDtqa,'Identifica��o',true)){
		return(false);
	}
	if (!validaString(form.descricaoDtqa,'Descri��o',true)){
		return(false);
	}
	if (!validaString(form.nomeDtqa,'Nome',true)){
		return(false);
	}
	if (!validaString(form.qtdColunasDtqa,'Quantidade de Colunas',true)){
		return(false);
	} else {
		if(!validaNum(form.qtdColunasDtqa, 'Quantidade de Colunas', true)){
			return(false);
		}	
	}
	return true;
}