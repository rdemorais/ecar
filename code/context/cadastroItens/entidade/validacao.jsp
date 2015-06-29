function validar(form){
	if (form.codEnt.value == ""){
		alert("<%=_msg.getMensagem("itemEstrutura.entidade.validacao.entidadeEnt.obrigatorio")%>");
		return(false);
	}
	if (form.tipoParticipacaoTpp.value == ""){
		alert("<%=_msg.getMensagem("itemEstrutura.entidade.validacao.tipoParticipacaoTpp.obrigatorio")%>");
		form.tipoParticipacaoTpp.focus();
		return(false);
	}
	
	if(!validaData(form.dataInicioIette,false,true,true) && form.dataInicioIette.value != ""){
		alert("<%=_msg.getMensagem("itemEstrutura.entidade.validacao.dataInicioIette.invalido")%>");
		return(false);
	}

	if(!validaData(form.dataFimIette,false,true,true) && form.dataFimIette.value != ""){
		alert("<%=_msg.getMensagem("itemEstrutura.entidade.validacao.dataFimIette.invalido")%>");
		return(false);
	}
	
	var dtIni;
	var dtFim;

	if(form.dataInicioIette.value != "" && form.dataFimIette.value != ""){
		dtIni = dataInversa(form.dataInicioIette.value);
		dtFim = dataInversa(form.dataFimIette.value);
		
		if (dtIni > dtFim){
			alert("<%=_msg.getMensagem("itemEstrutura.entidade.validacao.dataFimIette.dataFimMenorQueDataInico")%>");
			form.dataFimIette.focus();
			return(false)
		}
	}
	
	return(true);
}