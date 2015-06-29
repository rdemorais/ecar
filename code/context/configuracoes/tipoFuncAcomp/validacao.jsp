<script language="javascript">


function valida(form){
	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.documentacaoTpfa.value = form.richText.value;

	
	
	if(!validaString(form.descricaoTpfa,'Nome',true)){
		return(false);
	}
	
	if(!validaString(form.labelTpfa,'Label da Função',true)){
		return(false);
	}
	
	// seta os valores para leitura de item de estrutura
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indLerItemEstrutura.checked == false) {
		form.indLerItemEstrutura.value = 'N';
	} else {
	    form.indLerItemEstrutura.value = 'S';
	}
	
	// seta os valores para alteração de item de estrutura
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indAlterarItemEstrutura.checked == false) {
		form.indAlterarItemEstrutura.value = 'N';
	} else {
		form.indAlterarItemEstrutura.value = 'S';
	}
	
	// seta os valores para exclusão de item de estrutura
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indExcluirItemEstrutura.checked == false) {
		form.indExcluirItemEstrutura.value = 'N';
	} else {
		form.indExcluirItemEstrutura.value = 'S';
	}
	
	// seta os valores para visualizar parecer
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indVisualizarParecer.checked == false) {
		form.indVisualizarParecer.value = 'N';
	} else {
		form.indVisualizarParecer.value = 'S';
	}
	
	// seta os valores para bloquear planejamento
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indBloquearPlanejamento.checked == false) {
		form.indBloquearPlanejamento.value = 'N';
	} else {
		form.indBloquearPlanejamento.value = 'S';
	}
	
	// seta os valores para desbloquear planejamento
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indDesbloquearPlanejamento.checked == false) {
		form.indDesbloquearPlanejamento.value = 'N';
	} else {
		form.indDesbloquearPlanejamento.value = 'S';
	}
	
	// seta os valores para ativar monitoramento
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indAtivarMonitoramento.checked == false) {
		form.indAtivarMonitoramento.value = 'N';
	} else {
		form.indAtivarMonitoramento.value = 'S';
	}
	
	// seta os valores para desativar monitoramento
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indDesativarMonitoramento.checked == false) {
		form.indDesativarMonitoramento.value = 'N';
	} else {
		form.indDesativarMonitoramento.value = 'S';
	}
	
	// seta os valores para informar andamento
	// N - checkbox não selecionado
	// S - checkbox selecionado
	if(form.indInformaAndamentoTpfa.checked == false) {
		form.indInformaAndamentoTpfa.value = 'N';
	} else {
		form.indInformaAndamentoTpfa.value = 'S';
	}
	
	if(form.indEmitePosicaoTpfa.checked == false) {
		form.indEmitePosicaoTpfa.value = 'N';
	} else {
		form.indEmitePosicaoTpfa.value = 'S';
		//Obrigatório se Emite Posição for checked
		if(!validaString(form.labelPosicaoTpfa,'Label da Posição, se o campo Emite Posição for selecionado',true)){
			return(false);
		}

		/* Comentado Ref. Bug 5255
		if(form.indEmitePosicaoTpfa[0].checked == true){
			if (form.indInitMonitTpfa.checked == false && form.indNaoMonitTpfa.checked == false){
				alert("< %=_msg.getMensagem("tipoFuncAcomp.validacao.indEmitePosicaoMonitorado.obrigatorio")% >");
				return(false);
			}
		}*/
	}

	
	if(Trim(form.tamanhoSinalTpfa.value) != ""){
		if(!validaNum(form.tamanhoSinalTpfa, 'Tamanho da Imagem de Sinal', false)){
			alert("<%=_msg.getMensagem("tipoFuncAcomp.validacao.tamanhoSinalTpfa.invalido")%>");
			form.tamanhoSinalTpfa.focus();
			return(false);
		}
	}else{
		alert("<%=_msg.getMensagem("tipoFuncAcomp.validacao.tamanhoSinalTpfa.obrigatorio")%>");
		form.tamanhoSinalTpfa.focus();
		return(false);
	}


	return(true);
}
</script>