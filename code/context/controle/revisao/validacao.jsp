function validar(form){


	if (trim(form.descricaoPrev.value) == ""){
		alert("<%=_msg.getMensagem("periodoRevisao.validacao.descricaoPrev.obrigatorio")%>");
		form.descricaoPrev.focus();
		return(false);
	}
	
	if(!validaData(form.dtInicioPrev,false,true,true)){
		if(form.dtInicioPrev.value == ""){
			alert("<%=_msg.getMensagem("periodoRevisao.validacao.dtInicioPrev.obrigatorio")%>");
		}else{
	 		alert("<%=_msg.getMensagem("periodoRevisao.validacao.dtInicioPrev.invalido")%>");
		} 
		return(false);
	}
	
	if(!validaData(form.dtFimPrev,false,true,true)){
		if(form.dtFimPrev.value == ""){
			alert("<%=_msg.getMensagem("periodoRevisao.validacao.dtFimPrev.obrigatorio")%>");
		}else{
	 		alert("<%=_msg.getMensagem("periodoRevisao.validacao.dtFimPrev.invalido")%>");
		} 
		return(false);
	}
	return true;
}