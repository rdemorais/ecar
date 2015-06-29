<script language="javascript" type="text/javascript">

function validar(form){

	if(form.tipoAcompanhamento.value == ''){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.tipoAcompanhamento.obrigatorio")%>");
		return(false);
	}
	
	if (!validaString(form.nomeAref, "Referência", true)){
		return(false);
	}
	
	if (!validaString(form.anoAref, "Ano", true)){
		return(false);
	}
	
	if(!validaNum(form.anoAref, "Ano", false)){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.anoAref.invalido")%>");
		form.anoAref.focus();
		return(false);
	} 
	
	if(form.anoAref.value < 1){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.anoAref.invalido")%>");
		form.anoAref.focus();
		return(false);
	}
	
	if(form.anoAref.value.length < 4){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.anoAref.invalido")%>");
		form.anoAref.focus();
		return(false);
	}
	
	if (form.mesAref.value == ""){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.mesAref.obrigatorio")%>");
		form.mesAref.focus();
		return(false);
	}

	if (form.diaAref.value == ""){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.diaAref.obrigatorio")%>");
		form.diaAref.focus();
		return(false);
	}

	if (!validaNum(form.diaAref, "Dia", false)){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.diaAref.invalido")%>");
		form.diaAref.focus();
		return(false);
	}

	if (form.diaAref.value < 1){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.diaAref.invalido")%>");
		form.diaAref.focus();
		return(false);
	}

	var diaMesAnoAref = form.diaAref.value + "/" + form.mesAref.value + "/" + form.anoAref.value;	
	if(!validaDataStr(diaMesAnoAref, true)) {
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.dataAref.invalido")%>");
		return(false);
	}	
	
	if (!validaData(form.dataInicioAref,false,true,true)){
		if(form.dataInicioAref.value == ""){
			alert("<%=_msg.getMensagem("periodoReferencia.validacao.dataInicioAref.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("periodoReferencia.validacao.dataInicioAref.invalido")%>");
		}
		return(false);
	}
	
	if (!validaData(form.dataLimiteAcompFisicoAref,false,true,true)){
		if (form.dataLimiteAcompFisicoAref.value == ""){
			alert("<%=_msg.getMensagem("periodoReferencia.validacao.dataLimiteAcompFisicoAref.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("periodoReferencia.validacao.dataLimiteAcompFisicoAref.invalido")%>");
		}
		return(false);
	}
	
	var dtIni;
	var dtLim;
	
	dtIni = dataInversa(form.dataInicioAref.value);
	dtLim = dataInversa(form.dataLimiteAcompFisicoAref.value);
	
	if (dtIni > dtLim){
		alert("<%=_msg.getMensagem("periodoReferencia.validacao.dataLimiteAcompFisicoAref.dataFinalMenorQueDataInicial")%>");
		form.dataLimiteAcompFisicoAref.focus();
		return(false)
	}
	
	if (!validaPrazoFinalPara(form)){
		return(false);
	}
	
	/* garante que os campos estarao sempre desabilitados mesmo na alteracao	*/
	document.form.orgaoOrg.disabled = false;
	document.form.anoAref.disabled = false;
	document.form.mesAref.disabled = false;
	document.form.diaAref.disabled = false;
	
	
	return(true);
}

function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();
}

function aoSelecionarClasseDeAcesso(form){
	form.action = "frm_inc.jsp";
	form.submit();
}


function habilitarComboOrgao (campo, valor) {

	if(valor == 'true') {
		campo.disabled = false;
	} else {
		campo.value = ""; 
		campo.disabled = true;
	}	
}
</script>

</script>