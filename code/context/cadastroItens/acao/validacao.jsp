
function validar(form){

	if(!validaData(form.dataIetta,false,true,true)){
		if(form.dataIetta.value == ""){
			alert("<%=_msg.getMensagem("itemEstrutura.acao.validacao.dataIetta.obrigatorio")%>");
		}else{
	 		alert("<%=_msg.getMensagem("itemEstrutura.acao.validacao.dataIetta.invalido")%>");
		} 
		return(false);
	}
	
	if (!validaString(form.descricaoIetta,'Descrição',true)){
		return(false);
	}

	return true;
}