<script language="javascript" type="text/javascript">
<!--

/************************************************/  
/********* Scripts para Pontos Criticos *********/  
/************************************************/

//function validar(form){

//	if(!validaData(form.dataIdentificacaoPtc,false,true,true)){
//		if(form.dataIdentificacaoPtc.value == ""){
//			alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataIdentificacaoPtc.obrigatorio")%>");
//		}else{
//			alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataIdentificacaoPtc.invalido")%>");
//		}
//		return(false);
//	}	
//	if (!validaString(form.descricaoPtc,'Descrição',true)){
//		return(false);
//	}
//	if(form.indAmbitoInternoGovPtc[0].checked == false && form.indAmbitoInternoGovPtc[1].checked == false){
//		alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.indAmbitoInternoGovPtc.obrigatorio")%>");
//		return(false);
//	}	
//	if(!validaData(form.dataLimitePtc,false,true,true)){
//		if(form.dataLimitePtc.value == ""){
//			alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataLimitePtc.obrigatorio")%>");
//		}else{
//			alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataLimitePtc.invalido")%>");
//		}
//		return(false);
//	}	
//	if(form.dataSolucaoPtc.value != ""){
//		if(!validaData(form.dataSolucaoPtc,false,true,true)){
//			alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataSolucaoPtc.obrigatorio")%>");		
//			return(false);
//		}
//	}
//	if(form.indAtivoPtc[0].checked == false && form.indAtivoPtc[1].checked == false){
//		alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.indAtivoPtc.obrigatorio")%>");
//		return(false);
//	}	
//	if(form.codUsu.value != "" && form.dataSolucaoPtc.value == ""){
//		alert("<%//=_msg.getMensagem("itemEstrutura.pontoCritico.validacao.dataSolucaoPtc.obrigatorioQuandoCodUsuSelecionado")%>");
//		return(false);
//	}	


//	return true;
//}

/************************************************/  
/********** Scripts para apontamentos ***********/  
/************************************************/

function aoClicarExcuirApontamentos(form){
	if(validarExclusao(form, "excluir")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		if('<%--=configMailCfgm.getAtivoCfgm()--%>' == 'S') {
			if(document.form.obrigatorio.value == 'S' || (document.form.obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.cadastroItens.apontamentos.exclusao.autorizaEnviaEmail")%>') == true )) {
				document.form.autorizarMail.value = 'S';
			} 
		}
		//form.hidAcao.value = "excluir";
		form.action = "ctrlApontamentos.jsp?hidAcao=excluir";
		form.submit();
	}
}



function validarApontamento(form){
	if (!validaString(form.textoApt,'Texto',true)){
		return(false);
	}

	return true;
}


function aoClicarConsultarApontamento(form, codApt, codPtc, codAri, hidAcaoApontamento){
	if (hidAcaoApontamento == "")
		hidAcaoApontamento = "alterar";

	window.open('<%=_pathEcar%>/acompanhamento/restricoes/formApontamentos.jsp?hidAcaoApontamento='+hidAcaoApontamento+'&codApt='+codApt+'&codPtc='+ codPtc + '&codAri='+codAri,'apontameto','width=600,height=400,toolbar= no,scrollbars=no');
}

function aoClicarConsultar(form, cod){
	form.cod.value = cod;
	document.form.action = "frm_con.jsp";
	document.form.submit();
} 

function popupApontamento( hidAcaoApontamento, codApontamento, codPtc) {
    if (codApontamento == undefined || codApontamento =="" ) 
    	hidAcaoApontamento = 'incluir';
    else {
    	return abreJanela('<%=_pathEcar%>/acompanhamento/restricoes/formApontamentos.jsp?codApontamento='+codApontamento+'&hidAcaoApontamento='+hidAcaoApontamento+'&codPtc'+codPtc, '500','470', 'Apontamentos') 
    }
    	
	 //abreJanela('<%=_pathEcar%>/acompanhamento/restricoes/formApontamentos.jsp?codApontamento='+codApontamento+'&hidAcaoApontamento='+hidAcaoApontamento+'&codPtc'+codPtc)
}

function aoClicarIncluirApontamento(form, codPtc, codAri, hidAcaoApontamento){
 
	if (codPtc == undefined || codPtc==""|| codPtc ==null){
		alert ('É preciso salvar <%=aba.getLabelAba() %> primeiro!' );
		reutun ;
	}
	
	if (codAri==""){
		alert ('Não foi possível identificar o acompanhamento.');
	}
	
	if (hidAcaoApontamento == "")
		hidAcaoApontamento = "incluir";

	window.open('<%=_pathEcar%>/acompanhamento/restricoes/formApontamentos.jsp?hidAcaoApontamento=incluir&codPtc='+ codPtc + '&codAri='+codAri,'apontameto','width=600,height=400,toolbar= no,scrollbars=no');
	//window.open('<%=_pathEcar%>/acompanhamento/restricoes/formApontamentos.jsp?hidAcaoApontamento='+hidAcaoApontamento+'&codPtc='+ codPtc + '&codAri='+codAri,'apontameto','toolbar= no,scrollbars=no');
	
}
	 


//-->
</script>
