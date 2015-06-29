<script type="text/javascript">
<!--
function validar(form){

	msgObg ="Obrigatório o preenchimento do campo "; 
	msgInv="Valor inválido para o campo ";

	if (!validaString(form.eventoAge, "Evento", true)){
		return false;
	}
	
	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.descricaoAge.value = form.richText.value;
	
	if (!validaData(form.dataAge,false,true,true)){
		if(form.dataAge.value == ""){
			alert(msgObg + form.dataAge.title);
		}else{
			alert(msgInv+ form.dataAge.title);
		}
		return false;
	}
	
	if (!validaData(form.dataLimiteAge,false,true,true)){
		if(form.dataLimiteAge.value == ""){
			alert (msgObg + form.dataLimiteAge.title);
		}else{
			alert (msgInv + form.dataLimiteAge.title);
		}
		return false;
	}
	
	var dtIni;
	var dtLim;
		
	dtIni = dataInversa(form.dataAge.value);
	dtLim = dataInversa(form.dataLimiteAge.value);
	
	if (dtIni > dtLim){
		alert(form.dataAge.title +" menor do que "+ form.dataLimiteAge.title);
		form.dataLimiteAge.focus();
		return(false)
	}
	
	if(!validaString(form.horaEventoAge,'Hora do Evento',true)){
		return(false);
	}

	if(!validaNum(form.horaEventoAge, "Hora do Evento", false)){
		alert (msgInv + form.horaEventoAge.title);
		form.horaEventoAge.focus();
		return(false);
	} 

	if(form.horaEventoAge.value < 0 || form.horaEventoAge.value > 23){
		alert (msgInv + form.horaEventoAge.title);	
		form.horaEventoAge.focus();
		return(false);
	}
	 

	if(!validaString(form.minutoEventoAge,'Minutos da Hora do Evento',true)){
		return(false);
	}

	if(!validaNum(form.minutoEventoAge, "Minutos da Hora do Evento", false)){
		alert (msgInv + form.minutoEventoAge.title);
		form.minutoEventoAge.focus();
		return(false);
	}

	if(form.minutoEventoAge.value < 0 || form.minutoEventoAge.value > 59){
		alert (msgInv + form.minutoEventoAge.title);
		form.minutoEventoAge.focus();
		return(false);
	} 
	
	if (!validaString(form.localAge, "Local", true)){
		return false;
	}

	if (!validaString(form.descricaoAge, "Descrição", true)){
		return false;
	}

	if (!validaTipo()){
		return false;
	}

	
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

	return true;
}



//-->
</script>

