//function validar(form){

//	if(!validaData(form.dataIdentificacaoPtc,false,true,true)){
//		if(form.dataIdentificacaoPtc.value == ""){
//			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataIdentificacaoPtc.obrigatorio")%>");
//		}else{
//			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataIdentificacaoPtc.invalido")%>");
//		}
//		return(false);
//	}	
//	if (!validaString(form.descricaoPtc,'Descrição',true)){
//		return(false);
//	}
//	if(form.indAmbitoInternoGovPtc[0].checked == false && form.indAmbitoInternoGovPtc[1].checked == false){
//		alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.indAmbitoInternoGovPtc.obrigatorio")%>");
//		return(false);
//	}	
//	if(!validaData(form.dataLimitePtc,false,true,true)){
//		if(form.dataLimitePtc.value == ""){
//			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataLimitePtc.obrigatorio")%>");
//		}else{
//			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataLimitePtc.invalido")%>");
//		}
//		return(false);
//	}	
//	if(form.dataSolucaoPtc.value != ""){
//		if(!validaData(form.dataSolucaoPtc,false,true,true)){
//			alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataSolucaoPtc.obrigatorio")%>");		
//			return(false);
//		}
//	}
//	if(form.indAtivoPtc[0].checked == false && form.indAtivoPtc[1].checked == false){
//		alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.indAtivoPtc.obrigatorio")%>");
//		return(false);
//	}
//	if(form.codUsu.value != "" && form.dataSolucaoPtc.value == ""){
//		alert("<%=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataSolucaoPtc.obrigatorioQuandoCodUsuSelecionado")%>");
//		return(false);
//	}	
//
//	return true;
//}