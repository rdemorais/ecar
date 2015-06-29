<script type="text/javascript">
<!--
function validar(form){

	if (!validaString(form.eventoAge, "Evento", true)){
		return false;
	}


	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.descricaoAge.value = form.richText.value;
	
	if (!validaData(form.dataAge,false,true,true)){
		if(form.dataAge.value == ""){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataAge.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataAge.inicio.invalido")%>");
		}
		return false;
	}
	
	if (!validaData(form.dataLimiteAge,false,true,true)){
		if(form.dataLimiteAge.value == ""){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataLimiteAge.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataLimiteAge.invalido")%>");
		}
		return false;
	}
	
	var dtIni;
	var dtLim;
		
	dtIni = dataInversa(form.dataAge.value);
	dtLim = dataInversa(form.dataLimiteAge.value);
		
	if (dtIni > dtLim){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataLimiteAge.dataLimiteMenorQueData")%>");
		form.dataLimiteAge.focus();
		return(false)
	}
	
	
	
	if (!validaString(form.localAge, "Local", true)){
		return false;
	}
	
	if (!validaString(form.descricaoAge, "Descrição", true)){
		return false;
	}
	
	if(!validaString(form.horaEventoAge,'Hora do Evento',true)){
		return(false);
	}
	
	if(!validaNum(form.horaEventoAge, "Hora do Evento", false)){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.horaEventoAge.invalido")%>");
		form.horaEventoAge.focus();
		return(false);
	} 
	
	if(form.horaEventoAge.value < 0 || form.horaEventoAge.value > 23){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.horaEventoAge.invalido")%>");
		form.horaEventoAge.focus();
		return(false);
	} 

	if(!validaString(form.minutoEventoAge,'Minutos da Hora do Evento',true)){
		return(false);
	}
	
	if(!validaNum(form.minutoEventoAge, "Minutos da Hora do Evento", false)){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.minutoEventoAge.invalido")%>");
		form.minutoEventoAge.focus();
		return(false);
	} 
	
	if(form.minutoEventoAge.value < 0 || form.minutoEventoAge.value > 59){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.minutoEventoAge.invalido")%>");
		form.minutoEventoAge.focus();
		return(false);
	} 
	
	<%if (!operacao.equals("alterar")) {%>
	if (form.opcaoPrdc[0].checked == false &&
				form.opcaoPrdc[1].checked == false &&
				form.opcaoPrdc[2].checked == false &&
				form.opcaoPrdc[3].checked == false &&
				form.opcaoPrdc[4].checked == false &&
				form.opcaoPrdc[5].checked == false){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.opcaoPrdc.obrigatorio")%>");
		return false;
	}
	
	if (form.opcaoPrdc[1].checked == true){
		if(!validarCheckSelecionado(form, "diasSemanaPrdc")){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.diasSemanaPrdc.obrigatorio")%>");
			return false;
		}
	}
	
	
	if (form.opcaoPrdc[5].checked == true){
		if(!validaString(form.diasPrdc,'Dias da Periodicidade',true)){
			return(false);
		}
		
		if(!validaNum(form.diasPrdc, "Dias da Periodicidade", false)){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.diasPrdc.invalido")%>");
			form.diasPrdc.focus();
			return(false);
		} 
	}
	<% } %>
	
	if (document.getElementById('relS').checked){
		if (!validaData(form.dataRealizado,false,true,true)){
			if(form.dataRealizado.value == ""){
				alert("É preciso informar a data em que o evento foi realizado.");
			}else{
				alert("A data em que o evento foi realizado está inválida");
			}
			return false;
		}
	}
	
	if (!validaTipo())
		return false;
		
	if (form.ativo.checked)	form.indAtivo.value = "S";
		else form.indAtivo.value = "N";
		
		return true;
}

//-->
</script>

