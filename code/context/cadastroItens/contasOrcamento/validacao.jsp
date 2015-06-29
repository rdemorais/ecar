function validar(form){	
	
	<%=ecar.dao.ItemEstruturaContaOrcamentoDao.geraValidacaoCadastroEstruturaConta(request)%>	
	
	if (!validaString(form.exercicioExe,'Exercício',true)){
		return(false);
	}
	if (!validaString(form.fonteRecursoFonr,'Fonte de Recurso',true)){
		return(false);
	}
	if (!validaString(form.recursoRec,'Recurso',true)){
		return(false);
	}
	if(form.indAcumuladoEfiec[0].checked == false && form.indAcumuladoEfiec[1].checked == false){
		alert("<%=_msg.getMensagem("itemEstrutura.contaOrcamento.validacao.indAcumuladoEfiec.obrigatorio")%>");
		return(false);
	}	
	
	return(true);
}