function validar(form){

	setHiddenVal('rte');
    form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
    form.richText.value = form.richText.value.replace(/&gt;/g, ">");
    form.conteudoPpp.value = form.richText.value;

	if (!validaString(form.popupComportamentoPppc,'Comportamento',true)){
		return(false);
	}
	if (!validaString(form.nomePpp,'Nome',true)){
		return(false);
	}
	if (!validaString(form.janelaAlturaPpp,'Altura da Janela',true)){
		return(false);
	} else {
		if(!validaNum(form.janelaAlturaPpp, 'Altura da Janela', true)){
			return(false);
		}
	}
	if (!validaString(form.janelaLarguraPpp,'Largura da Janela',true)){
		return(false);
	} else {
		if(!validaNum(form.janelaLarguraPpp, 'Largura da Janela', true)){
			return(false);
		}	
	}
	if(!validaData(form.dataIniExibicaoPpp,false,true,true)){
		if(form.dataIniExibicaoPpp.value == ""){
			alert("<%=_msg.getMensagem("popup.validacao.dataIniExibicaoPpp.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("popup.validacao.dataIniExibicaoPpp.invalido")%>");
		}
		return(false);
	}
	if(!validaData(form.dataFimExibicaoPpp,false,true,true)){
		if(form.dataFimExibicaoPpp.value == ""){
			alert("<%=_msg.getMensagem("popup.validacao.dataFimExibicaoPpp.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("popup.validacao.dataFimExibicaoPpp.invalido")%>");
		}
		return(false);
	}	
	
	var dtIni;
	var dtFim;
	
	dtIni = dataInversa(form.dataIniExibicaoPpp.value);
	dtFim = dataInversa(form.dataFimExibicaoPpp.value);
	
	if (dtIni > dtFim){
		alert("<%=_msg.getMensagem("popup.validacao.dataFinalMenorQueDataInicial")%>");
		form.dataFimExibicaoPpp.focus();
		return(false)
	}	
	
	return true;
}