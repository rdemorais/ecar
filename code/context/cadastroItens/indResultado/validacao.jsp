function validar(form){
	if(!validaString(form.nomeIettir,'Nome do Indicador',true)){
		return(false);
	}
	
	if(!validaString(form.conceitIettir,'Conceituacao',true)){
		return(false);
	}
	
	if(!validaString(form.interpIettir,'Interpretacao',true)){
		return(false);
	}
	
	if(!validaString(form.fonteIettr,'Fonte',true)){
		return(false);
	}
	
	if(!validaString(form.mCalcIettir,'Metodo de Calculo',true)){
		return(false);
	}
	
	if (!validaCamposVariaveis(form)){
		return (false);	
	}
	
<!--Comentado devido ao BUG 4615	-->
<!--	if(!validaString(form.descricaoIettir,'Informações Complementares',true)){-->
<!--		return(false);-->
<!--	}-->
	<!-- Modificado pelo Serpro. Verificação só será feita se o campo Unidade de Medida for um -->	
	<!-- edit. Caso seja combo, a validação é feita no form. -->	
	if (document.getElementById('unidMedidaIettr') != null) {
		if(!validaString(form.unidMedidaIettr,'Unidade de Medida',true)){
			return(false);
		}
	}

	if((form.indPrevPorLocal[0].checked == true || form.indRealPorLocal[0].checked == true)&&(form.abrangenciaLocal.selectedIndex < 1)){
        alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.prevRealLocal.obrigatorio")%>");
		return(false);
	}

	if(form.indTipoQtde[0].checked == false && form.indTipoQtde[1].checked == false){
		alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indTipoQtde.obrigatorio")%>");
		return(false);
	}

	if(form.indProjecaoIettr[0].checked == false && form.indProjecaoIettr[1].checked == false){
		alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indProjecaoIettr.obrigatorio")%>");
		return(false);
	}
	
	if(form.indAcumulavelIettr[0].checked == false && form.indAcumulavelIettr[1].checked == false){
		alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indAcumulavelIettr.obrigatorio")%>");
		return(false);
	} else if (form.indAcumulavelIettr[1].checked == true) {
		if(form.indValorFinalIettr[0].checked == false && form.indValorFinalIettr[1].checked == false){
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indValorFinalIettr.obrigatorio")%>");
			return(false);
		}
	}
	
	if(form.indPrevPorLocal[0].checked == false && form.indPrevPorLocal[1].checked == false){
		alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indPrevPorLocal.obrigatorio")%>");
		return(false);
	}
	
	if(form.indRealPorLocal[0].checked == false && form.indRealPorLocal[1].checked == false){
		alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indRealPorLocal.obrigatorio")%>");
		return(false);
	}
	
//	if(!validarQtdeValor(form, form.indiceMaisRecenteIettr)){
//		return(false);
//	}	
	
	if(!validaQuantidadePrevista(form)){
		return(false);
	}
	
	if (!validarCamposObrigatoriosSinalizacao()){
		return(false);
	}
	
	if (!validarEstadosIguais()){
		return(false);
	}
	
	if (!validarValoresSinalizacao()){
		return(false);
	}
	
	if (!validarTipoUtilizacaoServico(form)){
		return(false);
	}
	
	desabilitarCamposDisabledSinalizacao();
	
	return(true);
}

function validarQtdeValor(form, qtdValor){
	
	// retira pontos
	qtdValor = qtdValor.replace(/(\.)/g, "");
	
	var qtdValorAux = qtdValor;
		
	if (qtdValor.indexOf(',') != -1){
		var indiceVirgula = qtdValor.indexOf(',');
		qtdValorAux = qtdValor.substring(0, indiceVirgula);
	}
	
	if (qtdValorAux.length > 12){
		alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.qtdPrevistaIettf.MaiorQuePermitido")%>");
		return false;
	}
	
	
	// troca virgula por ponto
	qtdValor = qtdValor.replace(/(\,)/g, ".");
	// verifica se o valor que restou é numérico
	if(qtdValor != '' && !isNumeric(qtdValor)){
	
		if(form.indTipoQtde[0].checked == true){ //qtde
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indiceMaisRecenteIettr.valorNum")%>");
		} else {
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indiceMaisRecenteIettr.valorMoeda")%>");
		}					
		qtdValor.focus();
		return (false);
	} else {
		qtdValor = qtdValor.replace(/(\.)/g, ",");
	}
	return (true);
}

function validarCamposObrigatoriosSinalizacao(){
	qtdCores = document.getElementById('quantidadeCores').value;
	for (i = 1; i <= qtdCores; i++){
		if (document.getElementById('ativo[' + i + ']').checked){
			if (document.getElementById('cor_' + i).selectedIndex == 0) {
				alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.camposObrigatoriosSinalizacao.estado")%>");
				return false;
			} else if (document.getElementById('valor_' + i).value == '') {
				alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.camposObrigatoriosSinalizacao.valor")%>");
				return false;
			}
		
		}
	}
	return true;
}

function validarEstadosIguais(){
	qtdCores = document.getElementById('quantidadeCores').value;
	for (i = 1; i <= qtdCores; i++){
		for (j = 1; j <= qtdCores; j++){
			if (i != j && document.getElementById('cor_' + i).selectedIndex != 0){
				if (document.getElementById('cor_' + i).selectedIndex == document.getElementById('cor_' + j).selectedIndex) {			
					alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.estadosIguais")%>");
					return false;			
				}
			}
		}
	}
	return true;
}

function validarValoresSinalizacao(){
	
	qtdCores = document.getElementById('quantidadeCores').value;
	valor = 0;
	valorAnterior = 0;
	for (i = 1; i <= qtdCores; i++){
		valor = document.getElementById('valor_' + i).value.replace(/(\.)/g, "");
		if (document.getElementById('valor_' + i).disabled == false){
		 
			valor = valor.replace(/(\,)/g, ".");
			
			if (!isNumeric(valor)){
				alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.valor.valorInvalido")%>");
				return false;
			} else {
				if (parseFloat(valorAnterior) >= parseFloat(valor)){
					alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.sequenciaValores")%>");
					return false;	
				}
			}
		}
		valorAnterior = valor;	
	}
	return true;
}


function desabilitarCamposDisabledSinalizacao(){
	qtdCores = document.getElementById('quantidadeCores').value;
	for (i = 1; i <= qtdCores; i++){
		document.getElementById('ativo[' + i + ']').disabled = false;
		document.getElementById('valor_' + i).disabled = false;
	}
}

function validarTipoUtilizacaoServico(form){
	var servcoPrevisto = document.getElementById('previstoServicoSer') ;
	var servcoRealizado = document.getElementById('realizadoServicoSer') ;

	if (servcoPrevisto && servcoPrevisto.value != null && servcoPrevisto.value != ''){
		if((!document.getElementsByName('indTipoAtualizacaoPrevisto')[0].checked) &&
			 (!document.getElementsByName('indTipoAtualizacaoPrevisto')[1].checked)){			
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indTipoAtualizacaoPrevisto")%>");
			return false;
			
		} else if (servicoPermitirPorLocal(form)==false){ 
		//Não pode usar serviço e Por Local Simultaneamente
			return false;
		}
	}
	
	if (servcoRealizado && servcoRealizado.value != null && servcoRealizado.value != ''){				
		if((!document.getElementsByName('indTipoAtualizacaoRealizado')[0].checked) &&
			 (!document.getElementsByName('indTipoAtualizacaoRealizado')[1].checked)){			
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indTipoAtualizacaoRealizado")%>");
			return false;
		} else if (servicoPermitirPorLocal(form)==false){ 
		//Não pode usar serviço e Por Local Simultaneamente
 			return false;
		}
	}
	
	if((document.getElementsByName('indTipoAtualizacaoPrevisto')[0].checked) ||
			 (document.getElementsByName('indTipoAtualizacaoPrevisto')[1].checked)){
		if (servcoPrevisto == null || servcoPrevisto.value == ''){
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.servicoValorPrevisto")%>");
			return false;
		}			
	}
	
	if((document.getElementsByName('indTipoAtualizacaoRealizado')[0].checked) ||
			 (document.getElementsByName('indTipoAtualizacaoRealizado')[1].checked)){
		if (servcoRealizado == null || servcoRealizado.value == ''){
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.servicoValorRealizado")%>");
			return false;
		}			
	}
	
	return true;
}

function servicoPermitirPorLocal(form){
	if(form.indPrevPorLocal[0].checked){
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indPrevPorLocal.NaoPodeComServico")%>");
			return(false);
	} else if(form.indRealPorLocal[0].checked){
			alert("<%=_msg.getMensagem("itemEstrutura.indResultado.validacao.indRealPorLocal.NaoPodeComServico")%>");
			return(false);
	}
	return true;
}