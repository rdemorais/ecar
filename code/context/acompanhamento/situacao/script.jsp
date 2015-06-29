<script>
	function retornoUsu<%=acompanhamento.getCodArel()%>(codigo, descricao){
		eval('document.forms[1].nomeUsu<%=acompanhamento.getCodArel()%>').value = descricao;
		eval('document.forms[1].codUsu<%=acompanhamento.getCodArel()%>').value = codigo;
	}
	function selectAllParecer(form, nomeCheckBoxCtrl, nomeCheckBoxDep){
			
		var numChecks = verificaChecks(form, nomeCheckBoxDep);
		
		if(numChecks > 1) {
			for (i = 0; i < eval('document.forms[1].'+ nomeCheckBoxDep + '.length'); i++) {
				eval('document.forms[1].'+ nomeCheckBoxDep + '['+i+']').checked = eval('document.forms[1].' + nomeCheckBoxCtrl).checked;
			}
		}
		
		if(numChecks == 1) {
			eval('document.forms[1].' + nomeCheckBoxDep).checked = eval('document.forms[1].' + nomeCheckBoxCtrl).checked;	
		}
		
	}
		
	function retornaRadioSelecionado(form, nome){
		var el = document.getElementsByTagName("INPUT");
		var i = 0;
		var radioSelecionado;
		
		while(i < el.length) {		
			if(el.item(i) != null) {
				if (el.item(i).type == "radio" && el.item(i).name == nome) {
					if(el.item(i).checked) {
						radioSelecionado = el.item(i).id;
						break;
					}
				}
			}
			i++;
		}	
		return radioSelecionado;
	}
	
	function reload(){
		periodoSel = document.forms[0].periodoSel.value;
		if(periodoSel != ""){
			document.forms[0].action = "relatorios.jsp?periodoSel="+periodoSel+"codAri=<%=strCodAri%>&codAcomp=<%=strCodAcomp%>";
			document.forms[0].submit();
		}
	}
	
	function abrirPopUpTodasPosicoes(form, indice){
        codAri = document.getElementById('codAri'+indice);
        codTpfa = document.getElementById('codTpfa'+indice);
        abreJanela("<%=_pathEcar%>/acompanhamento/situacao/popupTodasPosicoes.jsp?codAri=" + codAri.value + "&codTpfa=" + codTpfa.value, 700, 500, "Todas");
    }
	
	function aoClicarBtn2Arel<%=acompanhamento.getCodArel()%>(form, indice){
		reload();
	}
<%
if(podeGravarRelatorio==AcompRelatorioDao.OPERACAO_PERMITIDA){
%>
	// Ao clicar em Gravar
	function aoClicarBtn1Arel<%=acompanhamento.getCodArel()%>(form, indice){

			setHiddenParecerVal('DescricaoArel' + indice);
			document.getElementById('richTextDescricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value.replace(/>/g, "&gt;").replace(/\n/g, "");
			document.getElementById('richTextDescricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value.replace(/&gt;/g, ">");
			document.getElementById('descricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value;
			document.forms[0].descricaoArel.value = document.getElementById('richTextDescricaoArel'+indice).value;
			
			if(valida(form, indice)){
			
				// Caso a opção "Exige Liberar Parecer" não esteja marcada na tela de configurações do "Tipo de Acompanhamento" o sistema grava o parecer
				// e libera a posição.  
				if(document.getElementById('indLiberarParecer<%=acompanhamento.getCodArel()%>').value == 'N'){
					document.getElementById('indLiberado'+indice).value = 'S';
					if(document.getElementById('envioEmailAtivo'+indice).value == 'S') {
						if( document.getElementById('obrigatorio'+indice).value == 'S' || 
							(document.getElementById('obrigatorio'+indice).value == 'N' && 
							confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
							document.getElementById('autorizarMail'+indice).value = 'S';
						} 
					}
				
				}else if (confirm("<%=_msg.getMensagem("acompanhamento.acompRelatorio.gravacao.liberarposicao")%>")){
					document.getElementById('indLiberado'+indice).value = 'S';
					if(document.getElementById('envioEmailAtivo'+indice).value == 'S') {
						if( document.getElementById('obrigatorio'+indice).value == 'S' || 
							(document.getElementById('obrigatorio'+indice).value == 'N' && 
							confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
							document.getElementById('autorizarMail'+indice).value = 'S';
						} 
					}
				} else {
					document.getElementById('indLiberado'+indice).value = 'N';
				}
						
				document.forms[0].codArel.value = ''+indice;
				document.forms[0].codTpfa.value = document.getElementById('codTpfa'+indice).value;
				document.forms[0].funcao.value = document.getElementById('funcao'+indice).value;
				document.forms[0].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
				document.forms[0].autorizarMail.value = document.getElementById('autorizarMail'+indice).value;
				document.forms[0].indLiberado.value = document.getElementById('indLiberado'+indice).value;
				
				var radioCor = 'cor'+indice;
				var radioSelecionado = retornaRadioSelecionado(form, radioCor);
				document.forms[0].cor.value = document.getElementById(radioSelecionado).value;
				
				document.forms[0].situacaoSit.value = document.getElementById('situacaoSit'+indice).value;
				document.forms[0].descricaoArel.value = document.getElementById('richTextDescricaoArel'+indice).value;
				document.forms[0].complementoArel.value = document.getElementById('complementoArel'+indice).value;
				document.forms[0].action = "ctrl.jsp";
				document.forms[0].submit();							
			}
			
	
	}
	
	function aoClicarExcluir(form, indice){
		if(validarExclusao(document.forms[1], "excluir"+indice)){
			if(!confirm("Confirma a exclusão?")){
				return(false);
			}
			
			var numChecks = verificaChecks(document.forms[1], "excluir"+indice);
		
			if(numChecks > 1) {
				for(i = 0; i < eval('document.forms[1].excluir'+ indice + '.length'); i++){
					if(eval('document.forms[1].excluir'+ indice + '['+i+'].checked')){
						document.forms[1].excluirAnexo.value += eval('document.forms[1].excluir'+ indice + '['+i+'].value') + "|";
					}
				}
			}
		
			if(numChecks == 1) {
				if(eval('document.forms[1].excluir'+ indice +'.checked')){
						document.forms[1].excluirAnexo.value += eval('document.forms[1].excluir'+ indice + '.value') + "|";
				}	
			}
					
			document.getElementById('hidAcao'+indice).value = "excluir";
			document.forms[1].hidAcao.value = document.getElementById('hidAcao'+indice).value;
			document.forms[1].codIettuc.value = document.getElementById('codIettuc'+indice).value;
			
			document.forms[1].codArel.value = ''+indice;
			document.forms[1].codTpfa.value = document.getElementById('codTpfa'+indice).value;
			document.forms[1].funcao.value = document.getElementById('funcao'+indice).value;
			document.forms[1].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
			
			document.forms[1].action =  "ctrlAnexo.jsp?codArisControle=" + "<%=Pagina.getParamStr(request, "codArisControle")%>";
			document.forms[1].submit();
		}
	
	}
	

<%
} else{
%>
	function aoClicarBtn1Arel<%=acompanhamento.getCodArel()%>(form, indice){
<%
		_disabled = "disabled";
		_readOnly = "readOnly";		
		String msg = "";
		if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.usuarioInvalido");
		if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.acompanhamentoLiberado");
		if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_POSICAO_LIBERADA)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.posicaoLiberada");
		if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.gravacao.dataUltrapassada");			
%>
		alert("<%=msg%>");
	}
	
//	function aoClicarExcluir(form){
<%	//	String msgAnexo = "";
	//	msgAnexo = _msg.getMensagem("acompanhamento.acompRelatorio.anexo.exclusaoNaoPermitida");
%>
	//	alert("</%=msgAnexo%/>");	
	//}
<%
}

String labelBotao = "";	 
int podeLiberarRelatorio = acompRelatorioDao.podeLiberarRelatorio(usuario, funcao, ari, acompanhamento);

if(podeLiberarRelatorio==AcompRelatorioDao.OPERACAO_PERMITIDA && podeGravarRelatorio != AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA) {
	labelBotao = "Liberar Situa&ccedil;&atilde;o";
%>	
		function aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, indice){
		
			setHiddenParecerVal('DescricaoArel' + indice);
			document.getElementById('richTextDescricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value.replace(/>/g, "&gt;").replace(/\n/g, "");
			document.getElementById('richTextDescricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value.replace(/&gt;/g, ">");
			document.getElementById('descricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value;
			document.forms[0].descricaoArel.value = document.getElementById('richTextDescricaoArel'+indice).value;

			if (!valida(form, indice)) {
				return false;
			}
									
			if (document.getElementById('envioEmailAtivo'+indice))
				envioEmailAtivo = document.getElementById('envioEmailAtivo'+indice);
			if (document.getElementById('obrigatorio'+indice))
				obrigatorio = document.getElementById('obrigatorio'+indice);
			if (document.getElementById('autorizarMail'+indice))
				autorizarMail = document.getElementById('autorizarMail'+indice);
			if (document.getElementById('indLiberado'+indice))
				indLiberado = document.getElementById('indLiberado'+indice);
		
			if(envioEmailAtivo.value == 'S') {
				if(obrigatorio.value == 'S' || (obrigatorio.value == 'N' && confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
					autorizarMail.value = 'S';
				} 
			}
			indLiberado.value = "S";

			document.forms[0].codArel.value = ''+ indice;
			document.forms[0].codTpfa.value = document.getElementById('codTpfa'+indice).value;
			document.forms[0].funcao.value = document.getElementById('funcao'+indice).value;
			document.forms[0].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
			document.forms[0].autorizarMail.value = document.getElementById('autorizarMail'+indice).value;
			document.forms[0].indLiberado.value = document.getElementById('indLiberado'+indice).value;
			
			var radioCor = 'cor'+indice;
			var radioSelecionado = retornaRadioSelecionado(form, radioCor);
			document.forms[0].cor.value = document.getElementById(radioSelecionado).value;
			
			document.forms[0].situacaoSit.value = document.getElementById('situacaoSit'+indice).value;
			document.forms[0].descricaoArel.value = document.getElementById('richTextDescricaoArel'+indice).value;
			document.forms[0].complementoArel.value = document.getElementById('complementoArel'+indice).value;


			document.forms[0].action = "ctrl.jsp";
			if(valida(document.forms[0], indice)){
				document.forms[0].submit();
			}	 
		}	
<%
} 

int podeRecuperarRelatorio = acompRelatorioDao.podeRecuperarRelatorio(usuario, funcao, ari, acompanhamento);

if(podeRecuperarRelatorio==AcompRelatorioDao.OPERACAO_PERMITIDA && podeGravarRelatorio != AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
	labelBotao = "Recuperar";
%>	
		function aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, indice){

			setHiddenParecerVal('DescricaoArel' + indice);
			document.getElementById('richTextDescricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value.replace(/>/g, "&gt;").replace(/\n/g, "");
			document.getElementById('richTextDescricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value.replace(/&gt;/g, ">");
			document.getElementById('descricaoArel'+indice).value = document.getElementById('richTextDescricaoArel'+indice).value;
			document.forms[0].descricaoArel.value = document.getElementById('richTextDescricaoArel'+indice).value;
			
			if (!valida(form, indice)){
				return false;
			}
			
			if(document.getElementById('envioEmailAtivo'+indice).value == 'S') {
				if( document.getElementById('obrigatorio'+indice).value == 'S' || 
					(document.getElementById('obrigatorio'+indice).value == 'N' && 
					confirm('<%=_msg.getMensagem("acompanhamento.dadosBasicos.alteracao.autorizaEnviaEmail")%>') == true )) {
					document.getElementById('autorizarMail'+indice).value = 'S';
				} 
			}
			
			document.forms[0].action = "ctrl.jsp";
			document.forms[0].indLiberado.value = "N";
			
			document.forms[0].codArel.value = ''+indice;
			document.forms[0].codTpfa.value = document.getElementById('codTpfa'+indice).value;
			document.forms[0].funcao.value = document.getElementById('funcao'+indice).value;
			document.forms[0].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
			document.forms[0].autorizarMail.value = document.getElementById('autorizarMail'+indice).value;
			
			var radioCor = 'cor'+indice;
			var radioSelecionado = retornaRadioSelecionado(form, radioCor);
			document.forms[0].cor.value = document.getElementById(radioSelecionado).value;
			
			document.forms[0].situacaoSit.value = document.getElementById('situacaoSit'+indice).value;
			document.forms[0].descricaoArel.value = document.getElementById('richTextDescricaoArel'+indice).value;
			document.forms[0].complementoArel.value = document.getElementById('complementoArel'+indice).value;
			
			document.forms[0].submit();
		}	
<%
}

if("".equals(labelBotao) || podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
	if("S".equals(acompanhamento.getIndLiberadoArel())) {
		labelBotao = "Recuperar";
		String msg = "";
		if(podeRecuperarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.recuperacao.usuarioInvalido");
		if(podeRecuperarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.recuperacao.acompanhamentoLiberado");
		if(podeRecuperarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_FUNCAO_SUPERIOR_LIBERADA)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.recuperacao.funcaoSuperiorLiberada");
%>	
		function aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, indice){
			alert("<%=msg%>");
		}	
<%
	}
	
	if(acompanhamento.getIndLiberadoArel() == null || "N".equals(acompanhamento.getIndLiberadoArel())){
		labelBotao = "Liberar Situa&ccedil;&atilde;o";	
		String msg = "";
		if(podeLiberarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_USUARIO_SEM_PERMISSAO)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.liberacao.usuarioInvalido");
		if(podeLiberarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_ACOMPANHAMENTO_REFERENCIA_LIBERADO)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.liberacao.acompanhamentoLiberado");
		if(podeLiberarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_FUNCAO_SUPERIOR_LIBERADA)
			msg = _msg.getMensagem("acompanhamento.acompRelatorio.liberacao.funcaoSuperiorLiberada");
%>	 
		function aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, indice){
			alert("<%=msg%>");
		}
<%
	}
	
	if(podeGravarRelatorio == AcompRelatorioDao.OPERACAO_NEGADA_DATA_ULTRAPASSADA){
	String msg=_msg.getMensagem("acompanhamento.acompRelatorio.gravacao.dataUltrapassada");
%>
			function aoClicarBtn3Arel<%=acompanhamento.getCodArel()%>(form, indice){
				alert("<%=msg%>");
			}	
<%
	}
} 
	
%>
	
	function valida(form, indice){
		if(!validaString(document.getElementById('situacaoSit'+indice),"<%=configuracao.getLabelSituacaoParecer() %> ",true)){
			return(false);
		}
		var cor = 'cor'+indice;
		if(!validaRadioSelecionado(form, cor)){
			alert("<%=_msg.getMensagem("acompanhamento.acompRelatorio.validacao.cor.obrigatorio") + configuracao.getLabelCorParecer()%>");
			return false;
		}
		
		if(!validaString(document.getElementById('descricaoArel'+indice),"<%=funcao.getLabelPosicaoTpfa()%>",true)){
			return false;
		}
		
		return true;
	} 	
	
	function mostrarEsconder(indice) {
		if (document.getElementById('ultimasPosicoes'+indice).style.display=='none') {
		     document.getElementById('ultimasPosicoes'+indice).style.display='';
		} else {
		     document.getElementById('ultimasPosicoes'+indice).style.display='none';
		}
	}   
	
	function mostrarEsconderAnexos(indice) {
		if (document.getElementById('anexos'+indice).style.display=='none') {
		     document.getElementById('anexos'+indice).style.display='';
		} else {
		    document.getElementById('anexos'+indice).style.display='none';
		}
	}
	
	function mostrarEscondePosicoes(indice) {
		if (document.getElementById('ultimasPosicoes'+indice).style.display=='none') {
		     document.getElementById('ultimasPosicoes'+indice).style.display='';
		} else {
		     document.getElementById('ultimasPosicoes'+indice).style.display='none';
		}
	}   
	
	
	function aoClicarIncluir(form, indice) {
		document.getElementById('codArel').value = ''+indice;
		document.getElementById('inclusaoAnexo'+indice).value = "S";
		document.getElementById('cod'+indice).value = "";
		document.getElementById('cod').value = document.getElementById('cod'+indice).value
		document.getElementById('inclusaoAnexo').value = document.getElementById('inclusaoAnexo'+indice).value;
		var anexosUploadTag = 'anexosUploadTag'+indice;
		if (document.getElementById(anexosUploadTag).style.display!='') {
		     document.getElementById(anexosUploadTag).style.display='';
		     document.getElementById('anexosListaTag'+indice).style.display ='none';
		     document.getElementById('quantidade_anexos'+indice).style.display='none';
		}
	}
	
	
	function aoClicarAlterar(form, indice){
	
		if(validarAnexo(form, indice)){
			if (document.getElementById('inclusaoAnexo'+indice).value == "S"){
				document.getElementById('hidAcao'+indice).value = "incluir";
			} else {
				document.getElementById('hidAcao'+indice).value = "alterar";
			}
			
			if(document.forms[1].hidAcao != null && document.getElementById('hidAcao'+indice) != null)
				document.forms[1].hidAcao.value = document.getElementById('hidAcao'+indice).value;
			
			if(document.forms[1].arquivoIettup != null && document.getElementById('arquivoIettup'+indice) != null)
				document.forms[1].arquivoIettup.value = document.getElementById('arquivoIettup'+indice).value; 
			
			
			if(document.forms[1].descricaoIettup != null && document.getElementById('descricaoIettup'+indice) != null)
				document.forms[1].descricaoIettup.value = document.getElementById('descricaoIettup'+indice).value;
			
			if(document.forms[1].uploadTipoArquivoUta != null && document.getElementById('uploadTipoArquivoUta'+indice) != null)	
				document.forms[1].uploadTipoArquivoUta.value = document.getElementById('uploadTipoArquivoUta'+indice).value;
			
			if(document.forms[1].codIettuc != null && document.getElementById('codIettuc'+indice) != null)	
				document.forms[1].codIettuc.value = document.getElementById('codIettuc'+indice).value;
			
			if(document.forms[1].codArel != null)
				document.forms[1].codArel.value = ''+indice;
			
			if(document.forms[1].codTpfa != null && document.getElementById('codTpfa'+indice) != null)
				document.forms[1].codTpfa.value = document.getElementById('codTpfa'+indice).value;
			
			if(document.forms[1].funcao != null && document.getElementById('funcao'+indice)!= null)
				document.forms[1].funcao.value = document.getElementById('funcao'+indice).value;
			
			if(document.forms[1].funcaoParecer != null && document.getElementById('funcaoParecer'+indice)!= null)
				document.forms[1].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
			
			document.forms[1].action =  "ctrlAnexo.jsp?codArisControle=" + "<%=Pagina.getParamStr(request, "codArisControle")%>";
			document.forms[1].submit();
		}
	}	
	
	
	function aoClicarEditar(form, indice, cod){
		document.forms[1].codPontoCritico.value = cod;
		document.forms[0].codPontoCritico.value = cod;
		document.forms[0].codArel.value = indice;
		document.forms[0].codTpfa.value = document.getElementById('codTpfa'+indice).value;
		document.forms[0].funcao.value = document.getElementById('funcao'+indice).value;
		document.forms[0].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
		document.forms[0].inclusaoPontoCritico.value = "N";
		document.forms[0].action = "relatorios.jsp";
		document.forms[0].submit();
	}
	
	
	function validarAnexo(form, indice){
		if (document.getElementById('inclusaoAnexo'+indice).value == "S" && !validaString(document.getElementById('arquivoIettup'+indice),'Arquivo',true)){
			return(false);
		}
	
		if (!validaString(document.getElementById('descricaoIettup'+indice),'Descrição',true)){
			return(false);
		}
		
		if (!validaString(document.getElementById('uploadTipoArquivoUta'+indice),'Tipo',true)){
			return(false);
		}
		return true;
	}
	
	function aoClicarVoltar(form, indice){
		var anexosUploadTag = 'anexosUploadTag'+indice;
		if (document.getElementById(anexosUploadTag).style.display!='none') {
		     document.getElementById(anexosUploadTag).style.display='none';
		     document.getElementById('anexosListaTag'+indice).style.display ='';
		     document.getElementById('quantidade_anexos'+indice).style.display='';
		     document.getElementById('cod').value = '';
		}
	}
	

	
	function aoClicarConsultar(form, indice, cod){
		document.forms[1].cod.value = cod;
		document.forms[0].cod.value = cod;
		document.forms[0].codArel.value = ''+indice;
		document.forms[0].codTpfa.value = document.getElementById('codTpfa'+indice).value;
		document.forms[0].funcao.value = document.getElementById('funcao'+indice).value;
		document.forms[0].funcaoParecer.value = document.getElementById('funcaoParecer'+indice).value;
		document.forms[0].inclusaoAnexo.value = "N";
		document.forms[0].action = "relatorios.jsp";
		document.forms[0].submit();
	}
	

	
	/*
	 * Seleciona os dados da janela de pesquisa
	 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
	 * Sempre deve receber um código e uma descricao
	 */
	function getDadosPesquisa(codigo, descricao){
		document.form.codUsu.value = codigo;
		document.form.nomeUsu.value = descricao;
	}

</script>