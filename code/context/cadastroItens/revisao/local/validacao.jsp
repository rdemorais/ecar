function validar(form){
	if (form.codLit.value == ""){
		alert("<%=_msg.getMensagem("itemEstrutura.localItem.validacao.identificacaoLit.obrigatorio")%>");
		return(false);
	}
	
	return(true);
}