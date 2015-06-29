function validar(form){

	if(!validaString(form.codBnf, "Nome", true)){
		return(false);
	}
	if(!validaString(form.qtdePrevistaIEttB, "Quantidade Prevista", true)){
		return(false);
	} else {
			if(!isNumeric(replaceAll(form.qtdePrevistaIEttB.value, ".", ""))){
				alert("<%=_msg.getMensagem("itemEstrutura.beneficiario.validacao.qtdePrevistaIEttB.invalido")%>");
				form.qtdePrevistaIEttB.focus();
				return(false); 
			}	
	}

	return true;
}