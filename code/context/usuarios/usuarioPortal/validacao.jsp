function validar(form){

		/*
		 if(!validaString(form.orgaoOrg, "Órgão", true)){
		  return false;
		 }
		 */  
		if(Trim(form.dataNascimentoUsu.value) != ""){
		 if(!validaData(form.dataNascimentoUsu,false,true,true)){
		  if(form.dataNascimentoUsu.value == ""){
		   alert("<%=_msg.getMensagem("usuario.validacao.dataNascimento.obrigatorio")%>");
		  }else{
		   alert("<%=_msg.getMensagem("usuario.validacao.dataNascimento.invalido")%>");
		  }
		  return(false);
		 }
		}
		/* if(!validaString(form.email2Usu, "E Mail 2", true)){
		  return false;
		 }*/  
		if ( ! validaCamposVariaveis(form) ) 
			return false;	
		return true;
}

