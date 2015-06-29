
<jsp:directive.page import="ecar.pojo.ObjetoEstrutura" />
<jsp:directive.page import="ecar.pojo.EstruturaAtributoEttat"/>
<jsp:directive.page import="ecar.pojo.ConfiguracaoCfg"/>
<jsp:directive.page import="ecar.dao.AcompRelatorioDao"/>
<jsp:directive.page import="ecar.dao.AcompReferenciaItemDao"/>
<jsp:directive.page import="ecar.pojo.AcompRealFisicoArf"/>
<jsp:directive.page import="java.lang.reflect.Method"/>
<jsp:directive.page import="ecar.pojo.ServicoSer"/>
<jsp:directive.page import="ecar.pojo.AcompReferenciaItemAri"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.StringTokenizer"/>
<jsp:directive.page import="ecar.pojo.ParametroPar"/>
<jsp:directive.page import="ecar.dao.ItemEstrtIndResulDao"/>
<jsp:directive.page import="ecar.dao.ServicoDao"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="comum.util.Data"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.util.Collections"/>
<jsp:directive.page import="java.util.Comparator"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="comum.util.Util"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.dao.SisAtributoDao"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaDao" />
<jsp:directive.page import="ecar.dao.EstruturaDao" />
<jsp:directive.page import="ecar.api.facade.*"/>
<jsp:directive.page import="java.io.*"/>
<%

boolean emEdicao = true;
String msg = "";

ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");

/* VALIDAÇÃO DE ACESSO AO REALIZADO FISICO, CASO NÃO TENHA ACESSO, ABRE PARA CONSULTA */
try{  
	//ariDao.verificaEditarAcompRealFisico(usuario, ari);


/* VALIDAÇAO DE PERMISSAO EM ITEM_ESTRUT_USUARIO 
if(!validaPermissao.permissaoInformarRealizadoFisico(ari.getItemEstruturaIett(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
	_disabled = "disabled";
	_readOnly = "readonly";
	emEdicao = false;			
}
*/
String primeiroItemClicado = Pagina.getParamStr(request, "primeiroItemClicado");
String primeiroItemAriClicado = Pagina.getParamStr(request, "primeiroItemAriClicado");
String codArisControle = Pagina.getParamStr(request, "codArisControle");
itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");


if(primeiroItemClicado.equals("")) {
	primeiroItemClicado = ari.getItemEstruturaIett().getCodIett().toString();
}

if(primeiroItemAriClicado.equals("")) {
	primeiroItemAriClicado = Pagina.getParamStr(request, "codAri");
}
%>

<script language="javascript">

<%@ include file="../../cadastroItens/indResultado/validacao.jsp"%>

function aoClicarGravarFilho(form, contItemFilho, contSelecionado){	
	<%if(emEdicao){%>		
		var obj = document.getElementById('qtdRealizadaArf'+contSelecionado+'-'+contItemFilho);				
		// retira pontos
		obj.value = obj.value.replace(/(\.)/g, "");		
		// troca virgula por ponto
		if (isNumeric(obj.value) ) {
			obj.value = obj.value.replace(/(\,)/g, ".");
		}		
		 
		form.hidNivel.value = "filho";
						
		if (!validarFilho(form, contItemFilho, contSelecionado)){
			return(false);
		}
		
		if(Trim(obj.value) == ''){
			form.acao.value = "limpar";
		}
		
		form.hidContFilhoSelecionado.value = contItemFilho;
		
		form.hidContSelecionado.value = contSelecionado;
				
		form.action = "ctrlRealizadoFisico.jsp?codArisControle=<%=codArisControle%>";
		form.submit();
		
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function aoClicarExcluirFilho(form, contItemFilho, contSelecionado){	
	<%if(emEdicao){%>
	    if(confirm("<%=_msg.getMensagem("acompanhamento.realizadoFisico.exclusao.confirma")%>")){		
	    	form.hidNivel.value = "filho";
	    
			form.acao.value = "excluir";		
		
			form.hidContFilhoSelecionado.value = contItemFilho;
		
			form.hidContSelecionado.value = contSelecionado;
				
			form.action = "ctrlRealizadoFisico.jsp?codArisControle=<%=codArisControle%>";
			form.submit();
		}
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}


/* Quando está em edição liberar alteração, senão exibe msg */
function aoClicarGravarPai(form, contSelecionado){
	<%if(emEdicao) {%>		
		var obj = document.getElementById('qtdRealizadaArf'+contSelecionado);
		// retira pontos
		obj.value = obj.value.replace(/(\.)/g, "");
		// troca virgula por ponto
		if (isNumeric(obj.value) ) {
			obj.value = obj.value.replace(/(\,)/g, ".");
		}

		form.hidNivel.value = "pai";

		if (!validarPai(form, contSelecionado)){
			return(false);			
		}
		if( Trim( eval("form.qtdRealizadaArf" + contSelecionado + ".value") ) == "" ){
			form.acao.value = "limpar";
		}
		form.codAri.value = "<%=primeiroItemAriClicado%>";
		form.hidContSelecionado.value = contSelecionado;		
		form.action = "ctrlRealizadoFisico.jsp?codArisControle=<%=codArisControle%>";
		form.submit();
		
	<%	} else{ %>
			alert("<%=msg%>");
	<%}%>
}

function aoClicarExcluirPai(form, contSelecionado){
	<%if(emEdicao) {%>
	    if(confirm("<%=_msg.getMensagem("acompanhamento.realizadoFisico.exclusao.confirma")%>")){		
			form.hidNivel.value = "pai";

			form.acao.value = "excluir";
		
			form.codAri.value = "<%=primeiroItemAriClicado%>";
			form.hidContSelecionado.value = contSelecionado;		
			form.action = "ctrlRealizadoFisico.jsp?codArisControle=<%=codArisControle%>";
			form.submit();
	    }
		
	<%	} else{ %>
			alert("<%=msg%>");
	<%}%>
}


function aoClicarConsultar(form, codigo){
	form.codAriSubNivel.value = codigo;
	form.action = "indicadoresResultado.jsp?subNiveis=true&codArisControle=<%=codArisControle%>";
	form.submit();
}

/* Quando está em edição liberar inclusão, senão exibe msg */
function aoClicarNovoIndicador(form, codigo, codAriFilho){
	<%if(emEdicao){%>
		if(confirm("<%=_msg.getMensagem("acompanhamento.realizadoFisico.novoIndicador.confirma")%>")){

			if (arguments.length == 3)
            	form.codAriFilho.value = codAriFilho;

			form.codNovoIndicador.value = codigo;
			form.action = "ctrlNovoIndicador.jsp";
			form.submit();
		}
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function aoClicarCancelar(form){
	form.codAri.value = "<%=primeiroItemAriClicado%>";
	form.action = "indicadoresResultado.jsp";
	form.submit();
}

function aoClicarIndicadorPorLocal(form, codARF, nomeIndicador, nomeSituacao, podeGravar, nivel) {
	var janela = abreJanela('popUpRealizadoFisicoPorLocal.jsp?codARF=' + codARF + '&codARI=<%=ari.getCodAri().toString()%>'  + '&podeGravar=' + podeGravar + '&nomeIndicador=' + escape(nomeIndicador)+'&nomeSituacao='+escape(nomeSituacao)+'&nivel='+escape(nivel), 600, 500, 'popUpRealizadoFisicoPorLocal');
	janela.focus();
}

function verificarSituacao(form, codArf, codSit){
	if(codSit != ""){
		var url = "realizadoFisicoVerificaSit.jsp?verificaCodArf=" + codArf + "&verificaCodSit=" + codSit;
		openAjax(url, "verificaSituacaoConclusao", null, null, true, "../../images");
	}
}
function aoClicarExibir(form){
	form.codAri.value = "<%=primeiroItemAriClicado%>";
	form.action = "indicadoresResultado.jsp";
	form.submit();
}

function aoClicarGravarServicoPai(form, contSelecionado){
	<%if(emEdicao){%>
					
		var qntReal = document.getElementById('qtdRealizadaArf'+contSelecionado);
		qntReal.value = qntReal.value.replace(".", ',');
		qntReal.disabled = false;
		var gravou = aoClicarGravarPai(form, contSelecionado); 
		if (gravou == false){
			qntReal.disabled = true;
		}
		
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function aoClicarGravarServicoFilho(form, contItemFilho, contSelecionado){
	<%if(emEdicao){%>
		var qntReal = document.getElementById('qtdRealizadaArf'+contSelecionado+'-'+contItemFilho);
		qntReal.value = qntReal.value.replace(".", ',');
		qntReal.disabled = false;
		var gravou = aoClicarGravarFilho(form, contItemFilho, contSelecionado); 
		if (gravou == false){
			qntReal.disabled = true;
		}
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function aoClicarLimparServicoPai(form, contSelecionado){
	<%if(emEdicao){%>
		form.acao.value = "limpar";
		form.hidNivel.value = "pai";
		form.codAri.value = "<%=primeiroItemAriClicado%>";
		form.hidContSelecionado.value = contSelecionado;		
		form.action = "ctrlRealizadoFisico.jsp?codArisControle=<%=codArisControle%>";
		form.submit();
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function aoClicarLimparServicoFilho(form, contItemFilho, contSelecionado){
 	<%if(emEdicao){%>
		form.acao.value = "limpar";
		form.hidNivel.value = "filho";				
		form.hidContFilhoSelecionado.value = contItemFilho;
		form.hidContSelecionado.value = contSelecionado;		
		form.action = "ctrlRealizadoFisico.jsp?codArisControle=<%=codArisControle%>";
		form.submit();
	<%}else{%>
		alert("<%=msg%>");
	<%}%>
}

function verificaEmApuracao(objeto){
	
	var numero = objeto.name.toString().substr(objeto.name.toString().length-1,objeto.name.toString().length);

	if(objeto.checked){
		document.getElementById("hdnEmApuracao").value = "S";
		document.getElementById("qtdRealizadaArf"+numero).value = "0";
		document.getElementById("qtdRealizadaArf"+numero).disabled = true;
		document.getElementById("dataReferencia"+numero).options[0].setAttribute("selected", "selected");
		document.getElementById("dataReferencia"+numero).disabled = true;
	}
	else{
		document.getElementById("hdnEmApuracao").value = "N";
		document.getElementById("qtdRealizadaArf"+numero).disabled = false;
		document.getElementById("dataReferencia"+numero).disabled = false;
	}
	
}



</script>
<br>
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
<!--  -->
<input type="hidden" name="hidContSelecionado" value="">
<input type="hidden" name="hidContFilhoSelecionado" value="" >
<!-- colocado pois está dando erro na hora de gravar filho. Elemento não encontrado -->
<input type="hidden" name="filhoSelecionado" value="" id="filhoSelecionado">
<input type="hidden" name="acao" id = "acao" value="">


<input type="hidden" name="hdnEmApuracao" id="hdnEmApuracao" value="N">

<%
	EstruturaDao estruturaDao = new EstruturaDao(request);

	boolean estavaDesabilitado = (!"".equals(_disabled)) ? true : false;	

	String LABEL_TOTAL = "Até o Ciclo";

	int cont = 0;

	//mantis 0011551 - 
	//Pegando todos os indicadores de acordo 
	EcarData periodoSelectionado = new EcarData(ari.getAcompReferenciaAref().getMesAref(),
												ari.getAcompReferenciaAref().getAnoAref());
	
	List lAcompRF = new ArrayList();
	ItemEstrutura itemSelectionado = new ItemEstrutura(ari.getItemEstruturaIett().getCodIett());
	
	ConfiguracaoCfg _configuracao = new ConfiguracaoDao(request).getConfiguracao();
	AcompanhamentoItemEstrutura _ariWrapper = new AcompanhamentoItemEstrutura(ari);


	lAcompRF = itemSelectionado.getRealizadosIndicadoresPorSituacao(tipoSelecao, _ariWrapper, _configuracao, periodoSelectionado, false);
	
	//lAcompRF = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, tipoSelecao, true);
	
	
	Iterator it = lAcompRF.iterator();
	String unidadeMedida = "";
	SisGrupoAtributoSga grupoUnidades = _configuracao.getSisGrupoAtributoSgaByUnidMedida();	
	IndicadorResultado indicador = null;
	ServicoDao servicoDao = new ServicoDao(request);
	
	boolean isConcluidoNoPeriodo = false;		
	
	/* se possuir lista mostrar os botões */
	if (it.hasNext()){
		String nome = "";
%>

		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>
				</td>
			</tr>
		</table>		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="10"><img src="../../images/pixel.gif"></td></tr>
			<%
			if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){
			%>
			<tr class="linha_subtitulo">
				<td width="10%" class="coluna_numerica">Indicador de Resultado</td>
				<td width="5%" class="coluna_numerica">Previsto<br><%=LABEL_TOTAL%></td>
				<td width="5%" class="coluna_numerica">Realizado<br><%=LABEL_TOTAL%></td>
				<td width="5%" class="coluna_numerica">Previsto<br>Exercício</td>
				<td width="5%" class="coluna_numerica">Realizado<br>Exercício</td>
				<td width="18%" align="center" colspan="2">Realizado</td>	
				<td width="12%" class="coluna_numerica">Data<br>Refer&ecirc;ncia</td>		
				<td width="5%" class="coluna_numerica">Em<br>Apura&ccedil;&atilde;o</td>			
				<td width="12%" class="coluna_numerica">Situação</td>
				<td width="5%" class="coluna_numerica">&nbsp;</td>
				<td width="5%" class="coluna_numerica">&nbsp;</td>
			</tr>
			<%
			}
			%>
			<tr><td class="espacador" colspan="11"><img src="../../images/pixel.gif"></td></tr>
<%
				String cor = new String(); 
				String grupoIndicador = "Indicador de Resultado";
				
				//Percorre a lista de AcompRealFisicoArf
				while (it.hasNext()){
					if (cont % 2 == 0){
						cor = "cor_nao";
					} else {
						cor = "cor_sim"; 
					}
					cont++;
					AcompRealFisicoArf acompRealFisico = (AcompRealFisicoArf) it.next();
					
					isConcluidoNoPeriodo = false;
					indicador = new IndicadorResultado(acompRealFisico.getItemEstrtIndResulIettr());
					//determina se o indicador está concluído se estiver concluído o input box do valor
					//e o combo box da situação serão desabilitados nos perídos posteriores ao ciclo da
					//conclusão
					AcompanhamentoItemEstrutura ariWrapper = new AcompanhamentoItemEstrutura(ari);
					
					if(indicador.isConcluido() && 
					   indicador.getDataConclusao().compareTo(ariWrapper.getDataReferencia()) == EcarData.ANTES ){
						
						isConcluidoNoPeriodo = true;
					}
					
					//Se a configuração do sistema está setada para trabalhar com um grupo de unidades,
					//a unidade de medida vem do atributo livre
					if(grupoUnidades != null && acompRealFisico.getItemEstrtIndResulIettr().getCodUnidMedidaIettr() != null){
						SisAtributoSatb sisAtributo = acompRealFisico.getItemEstrtIndResulIettr().getCodUnidMedidaIettr();									
						unidadeMedida = sisAtributo.getDescricaoSatb();
					}
					else{
						unidadeMedida = acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr();
					}					
					
					if(!estavaDesabilitado){
						//Verificar se é para habilitar ou não edição conforme regra do mantis 7795
						_disabled = (acompRealFisicoDao.habilitarEdicaoArf(acompRealFisico, ari.getAcompReferenciaAref(), radConcluido)) ? "" : "disabled";
					}

					String tipoQtde = "V";
					if("Q".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndTipoQtde())){
						tipoQtde = "Q";
					}
					if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
						if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
							grupoIndicador = acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="left"><%=grupoIndicador%></td>
								<td width="5%" class="coluna_numerica">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Previsto<br>Exercício</td>
								<td width="5%" class="coluna_numerica">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2">Realizado</td>	
								<td width="12%" class="coluna_numerica">Data<br>Refer&ecirc;ncia</td>	
								<td width="5%" class="coluna_numerica">Em<br>Apura&ccedil;&atilde;o</td>		
								<td width="12%" class="coluna_numerica">Situação</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
								
							</tr>
							<%
							
						}
						else if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)){
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="left"><%=grupoIndicador%></td>
								<td width="5%" class="coluna_numerica">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Previsto<br>Exercício</td>
								<td width="5%" class="coluna_numerica">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2">Realizado</td>
								<td width="12%" class="coluna_numerica">Data Refer&ecirc;ncia</td>	
								<td width="5%" class="coluna_numerica">Em<br>Apura&ccedil;&atilde;o</td>				
								<td width="12%" class="coluna_numerica">Situação</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
							</tr>
							<%
							grupoIndicador = "";
						}
					}					
					
					double realizado = 0;
					String linkPorLocal = "<img src=\"../../images/empty.gif\">";
					String desabilitarQtdeARF = "";
					
					
					if(acompRealFisico.getQtdRealizadaArf() != null) {
						realizado = acompRealFisico.getQtdRealizadaArf().doubleValue();
					}
					
					
					String previstoTotal = "0";
					String realizadoTotal = "0";
					String previstoExercicio = "0";
					String realizadoExercicio = "0";
					
					String labelRealizado = REALIZADO_NO_MES;

					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())) {
						String podeGravar = (!"".equals(_disabled) || isConcluidoNoPeriodo == true) ? "N" : "S";
						linkPorLocal = "<a href=\"#\" onclick=\"javascript:aoClicarIndicadorPorLocal(form, '" + acompRealFisico.getCodArf() + "','qtdRealizadaArf" + cont +"','situacao"+cont+"','" + podeGravar + "','pai')\">"
										+ "<img src=\"../../images/icon_exibir.png\" border=\"0\" title='Quantidade/Valor por Local'></a>";
						if("".equals(_disabled)) {
							desabilitarQtdeARF = "disabled";
						}
					}
					
					// Em todos os casos que a data de início ou ciclo não for informada, 
					// o sistema não permite a inclusão/alteração de valores realizados nos indicadores
					if(itemEstruturaDao.ObtemDataInicialItemEstrutura(acompRealFisico.getItemEstruturaIett()) == null) {
						desabilitarQtdeARF = "disabled";
					}
					
					/* Tratamento diferencia para exibição de indicadores Acumulavel e não-Acumulável */
					
					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){		
						if(tipoQtde.equals("Q")){
							previstoTotal = Util.formataNumeroDecimal(itemEstrutFisicoDao.getQuantidadePrevista(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoTotal = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizada(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							previstoExercicio = Util.formataNumeroDecimal(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), ari.getAcompReferenciaAref().getExercicioExe()));
							realizadoExercicio = Util.formataNumeroDecimal(acompRealFisicoDao.getQtdRealizadaExercicio(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
						}else{
							previstoTotal = Util.formataMoeda(itemEstrutFisicoDao.getQuantidadePrevista(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoTotal = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizada(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							previstoExercicio = Util.formataMoeda(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), ari.getAcompReferenciaAref().getExercicioExe()));
							realizadoExercicio = Util.formataMoeda(acompRealFisicoDao.getQtdRealizadaExercicio(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
						}
					}else{
						labelRealizado = REALIZADO_ATE_O_MES;
						if(tipoQtde.equals("Q")){
							previstoTotal = Util.formataNumeroDecimal(itemEstrutFisicoDao.getQuantidadePrevistaNaoAcumulavel(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoTotal = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P", "T"));
							previstoExercicio = Util.formataNumeroDecimal(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), ari.getAcompReferenciaAref().getExercicioExe()));
							realizadoExercicio = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P", "E"));
						}else{
							previstoTotal = Util.formataMoeda(itemEstrutFisicoDao.getQuantidadePrevistaNaoAcumulavel(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoTotal = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P", "T"));
							previstoExercicio = Util.formataMoeda(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), ari.getAcompReferenciaAref().getExercicioExe()));
							realizadoExercicio = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(ari, acompRealFisico.getItemEstrtIndResulIettr(), "P", "E"));
						}
					}
					
					
					if(isConcluidoNoPeriodo == true){
				 	    labelRealizado = labelRealizado + " " +  indicador.getDataConclusao().getDataFormatadaComBarra();
					}else{
						labelRealizado = labelRealizado + " " +  acompRealFisico.getMesArf().toString() + "/" + acompRealFisico.getAnoArf().toString();
					}
%>
					<tr class="<%=cor%>" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')" onClick="javascript: destacaLinha(this,'click','<%=cor%>')" class="linha_subtitulo2">
						<td width="10%">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir()%>
							<% if (!"".equals(unidadeMedida)){   %>
							- <%=unidadeMedida%>
							<% } %>
							<!-- CODARF adicionado devido ao Mantis 5518, antes era verificado pelo codIettir+codAri+codIett -->
							<input type="hidden" name="codArf<%=cont%>" value="<%=acompRealFisico.getCodArf()%>">
						</td>
						
						<td width="5%"><%=previstoTotal%></td>
						<td width="5%"><%=realizadoTotal%></td>
						<td width="5%"><%=previstoExercicio%></td>
						<td width="5%"><%=realizadoExercicio%></td>

<%
							List atributos = estruturaDao.getAtributosEstruturaDadosGerais(acompRealFisico.getItemEstruturaIett().getEstruturaEtt());
							String label = "Data Inicial";
							if(atributos != null) {
								Iterator itAtributos = atributos.iterator();
								while(itAtributos.hasNext()) {
									ObjetoEstrutura atributo = (ObjetoEstrutura) itAtributos.next();
									if (atributo.iGetTipo() == EstruturaAtributoEttat.class){	            	
							        	if(atributo.iGetNome().equals("dataInicioIett")) {
							        		label = atributo.iGetLabel();
							        	}
									}
								} 
							}
							

							String validacaoIndQtde = "";//" onblur=\"javascript:validarQtdeValor(this,'" + tipoQtde + "')\"";
							
							//Se o valor realizado foi setado para ser calculado através de um serviço no cadastro de metas/indicadores
							if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null){
								String url = acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer().getUrlSer();
								
								//Transforma em minúscula primeira letra do nome do serviço para o mesmo ser utilizado como chamada ao método
								//de forma dinâmica
								url = url.substring(0,1).toLowerCase() + url.substring(1);
																									
								Object[] parametros = servicoDao.getParametrosServico(
									acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer(),ari);									
								
								Object retornoServico = ServicoDao.class.getMethod(url, new Class[]{String.class, String.class}).invoke(servicoDao, parametros);
								String retornoServicoFormatado ="";

								if (retornoServico instanceof Double) {
									if(tipoQtde.equals("Q"))
										retornoServicoFormatado = Util.formataNumeroDecimal((Double)retornoServico);
									else
										retornoServicoFormatado = Util.formataMoeda((Double)retornoServico);
								}
								
								//se a gravação do serviço for manual
								if (acompRealFisico.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado().equals("M")){
									//Se o valor gravado no banco for não nulo e igual ao valor apurado pelo serviço
									if (retornoServico != null && acompRealFisico.getQtdRealizadaArf() != null &&
										acompRealFisico.getQtdRealizadaArf().equals(retornoServico)){
									%>
										<!-- Chama de forma dinâmica o método do serviço correspondente ao serviço marcado para o valor realizado no cadastro de metas/indicadores-->
										<td width="11%" align="right" nowrap="nowrap">									
										<%=labelRealizado%>
										<input type="text" name="qtdRealizadaArf<%=cont%>" id="qtdRealizadaArf<%=cont%>" 
										<%if(retornoServico != null) {%> value="<%=retornoServicoFormatado%>" <%} else { %> value="" <%}%>
										size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
										</td>									
										<td width="7%" align="left">
			                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
			                            </td>									
										
									<%
									}
									//Se o valor gravado no banco for não nulo e diferente valor apurado pelo serviço
									else if (retornoServico != null && 
										acompRealFisico.getQtdRealizadaArf() != null && 
										!Util.formataNumeroDecimal(acompRealFisico.getQtdRealizadaArf()).equals(retornoServicoFormatado))
									{
										String msgAtencao = "Valor Atual Apurado: "+ retornoServicoFormatado;
										// Em todos os casos que a data de início não for informada exibe esta mesagem
										if(acompRealFisico.getItemEstruturaIett().getDataInicioIett() == null) { 
											msgAtencao = label + " não informado";
										}
										if(tipoQtde.equals("Q"))
											retornoServicoFormatado = Util.formataNumeroDecimal((Double)acompRealFisico.getQtdRealizadaArf());
										else
											retornoServicoFormatado = Util.formataMoeda((Double)acompRealFisico.getQtdRealizadaArf());

									%>									
																			
										<!-- Chama de forma dinâmica o método do serviço correspondente ao serviço marcado para o valor realizado no cadastro de metas/indicadores-->
										<td width="11%" align="right" nowrap="nowrap">																			
										<%=labelRealizado%>										
										<img width="18px" height="14px" src="../../images/atencao.gif" title="<%=msgAtencao%>">
										<input type="text" name="qtdRealizadaArf<%=cont%>" id="qtdRealizadaArf<%=cont%>" 
										<%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=retornoServicoFormatado%>" <%} else { %> value="" <%}%>
										size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
										</td>	

										<td width="7%" align="left">
			                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
			                            </td>										
																		
									<%
									//Se o valor do banco for nulo
									}
									else{
									%>
										<td width="11%" align="right" nowrap="nowrap">									
										<%=labelRealizado%>
										<input type="text" name="qtdRealizadaArf<%=cont%>" id="qtdRealizadaArf<%=cont%>" 
										<%if(retornoServico != null) {%> value="<%=retornoServicoFormatado%>" <%} else { %> value="" <%}%>
										size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
														
										</td>
										<td width="7%" align="left">
			                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
			                            </td>								
									<%
									}
								}
								//se a gravação do serviço for automática
								else if (acompRealFisico.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado()!= null && acompRealFisico.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado().equals("A")){
									String msgAtencao = "O valor poderá alterar até a data limite";
									// Em todos os casos que a data de início não for informada exibe esta mesagem
									if(acompRealFisico.getItemEstruturaIett().getDataInicioIett() == null) { 
										msgAtencao = label + " não informado";
									}
									if(acompRealFisico.getQtdRealizadaArf() != null){
										if(tipoQtde.equals("Q"))
											retornoServicoFormatado = Util.formataNumeroDecimal((Double)acompRealFisico.getQtdRealizadaArf());
										else
											retornoServicoFormatado = Util.formataMoeda((Double)acompRealFisico.getQtdRealizadaArf());
									}
								%>	
									<!-- Chama de forma dinâmica o método do serviço correspondente ao serviço marcado para o valor realizado no cadastro de metas/indicadores-->
										<td width="11%" align="right" nowrap="nowrap">										
										<%=labelRealizado%>
										<%if (acompRealFisico.getQtdRealizadaArf() == null){ %>
											<img width="18px" height="14px" src="../../images/atencao.gif" title="<%=msgAtencao%>">
										<%} %>
										<input type="text" name="qtdRealizadaArf<%=cont%>" id="qtdRealizadaArf<%=cont%>" 
										<%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=retornoServicoFormatado%>" <%} else { %> value="<%=retornoServicoFormatado%>" <%}%>
										size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
										</td>																		
										<td width="7%" align="left">
			                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>			                                
			                            </td>	
			                    <%
								}														
							}
							else{
							%>
								<td width="11%" align="right" nowrap="nowrap">	
								<%=labelRealizado%>
								
								
								<%
								
								String sRealizado = "";
							
								
								if(isConcluidoNoPeriodo ==  true && indicador.getValorRealizadoConclusao() != null){
									sRealizado = indicador.getValorRealizadoConclusao().getValorFormatado();
								}else {
									if(acompRealFisico.getQtdRealizadaArf() != null) {
										if(tipoQtde.equals("Q")){
											sRealizado=Util.formataNumeroDecimal((Double)realizado);
										}else{
											sRealizado=Util.formataMoeda((Double)realizado);
										}
									}
								}
								
								%>
								
								
								<input type="text" name="qtdRealizadaArf<%=cont%>" id="qtdRealizadaArf<%=cont%>" 
									 value="<%= sRealizado %>"
									 
									  
								<%
								if(_disabled.equals("disabled") || desabilitarQtdeARF.equals("disabled") || validacaoIndQtde.equals("disabled") || isConcluidoNoPeriodo||(acompRealFisico.getIndEmApuracaoArf()!=null&&acompRealFisico.getIndEmApuracaoArf().equals("S")) ){%>
										
								    size="10" maxlength="20" disabled="true" />
								
								<%} else { %>
								
									size="10" maxlength="20" />
								
								<%} %>
								
								</td>
								<td>
		                            <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
		                            <%=linkPorLocal%>			                                
	                            </td>						
							<%
							}
							%>
							
							
							
	<%
	nome = "situacaoSit" + cont;
	String nomeDataReferencia = "dataReferencia" + cont;
	String nomeEmApuracao = "emApuracao" + cont;
	
	String _disabledQtdSituacao = _disabled;
	
	String _checaEmApuracao = "";
	
	if(acompRealFisico.getIndEmApuracaoArf()!=null&&acompRealFisico.getIndEmApuracaoArf().equals("S")){
		_checaEmApuracao = "checked=checked";
	}
	
	if ((acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() != null) || 
			isConcluidoNoPeriodo == true) {
		_disabledQtdSituacao = "disabled=\"true\"";
	}
	//else{
	//	_disabled = "";
	//}
	
	// Em todos os casos que a data de início ou ciclo não for informada, 
	// o sistema não permite a inclusão/alteração de valores realizados nos indicadores
	if(itemEstruturaDao.ObtemDataInicialItemEstrutura(acompRealFisico.getItemEstruturaIett()) == null || isConcluidoNoPeriodo) {				
		_disabledQtdSituacao = "disabled=\"true\"";
	}
	
	String scripts = _disabledQtdSituacao + " onchange=\"javascript:verificarSituacao(form, " + acompRealFisico.getCodArf().toString() + ", this.value)\"";

 	
                      	if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())) {
							
                      		String descricaoSituacao;
                      		
                      		if (acompRealFisico.getSituacaoSit() != null){
                      			if(isConcluidoNoPeriodo == true){
                      		    	descricaoSituacao = indicador.getValorRealizadoConclusao().getStringSituacao();
                      			}else{
                      				descricaoSituacao = acompRealFisico.getSituacaoSit().getDescricaoSit();
                      			}
                      		}
                      		else
                      		{
                      			descricaoSituacao = "";	
                      		}
							
							%>
                            <td width="12%">
                            </td>
                            <td width="5%">
                            </td>							
                            <td width="12%">
            				<input type="text" name="situacao<%=cont%>" id="situacao<%=cont%>" 
                                 value="<%=descricaoSituacao%>" size="10" maxlength="20" disabled>
                            </td>
							<%
								
						}
						else
						{
	
							if ( acompRealFisico.getSituacaoSit() != null ) {
								
								if(acompRealFisico.getIndEmApuracaoArf()!=null&&acompRealFisico.getIndEmApuracaoArf().equals("S")){
	%>
							 
							 
							 <td width="12%">
								<combo:ComboTag 
										nome="<%=nomeDataReferencia%>"
										objeto="ecar.pojo.AcompReferenciaAref" 
										label="mesAref,anoAref" 
										value="mesAnoReferencia" 
										order="mesAref,anoAref"
										filters="exercicioExe.codExe=12"
										disabled="true"
										
								/>
								 
                            </td>	
								<%}else{ %>
								
							 <td width="12%">
								<combo:ComboTag 
										nome="<%=nomeDataReferencia%>"
										objeto="ecar.pojo.AcompReferenciaAref" 
										label="mesAref,anoAref" 
										value="mesAnoReferencia" 
										order="mesAref,anoAref"
										filters="exercicioExe.codExe=12"
										selected="<%=acompRealFisico.getMesAnoReferencia()%>"
										disabled="<%= new Boolean(isConcluidoNoPeriodo).toString() %>"
										
								/>
								 
                            </td>									
								<%} %>
						
							<td width="5%">
								<input type="checkbox" name="<%=nomeEmApuracao%>" id="<%=nomeEmApuracao%>" onclick="verificaEmApuracao(this);" <%=_checaEmApuracao %>>
							</td>                            
                            <td width="12%">		
								<combo:ComboTag 
										nome="<%=nome%>"
										objeto="ecar.pojo.SituacaoSit" 
										label="descricaoSit" 
										value="codSit" 
										order="descricaoSit"
										filters="indMetaFisicaSit=S"
										selected="<%=acompRealFisico.getSituacaoSit().getCodSit().toString()%>"
										scripts="<%=scripts%>"
										disabled="<%= new Boolean(isConcluidoNoPeriodo).toString() %>"
								/>
							</td>

	<%
							} else {
								String situacaoConclusao = "";
								if(indicador != null && isConcluidoNoPeriodo){
									situacaoConclusao = indicador.getValorRealizadoConclusao().getStringSituacao();
								}
	%>
						<td width="12%">
								<combo:ComboTag 
										nome="<%=nomeDataReferencia%>"
										objeto="ecar.pojo.AcompReferenciaAref" 
										label="mesAref,anoAref" 
										value="mesAnoReferencia" 
										order="mesAref,anoAref"
										filters="exercicioExe.codExe=12"
										disabled="<%= new Boolean(isConcluidoNoPeriodo).toString() %>"
								/>						
                        </td>
						<td width="5%">
							<input type="checkbox" name="<%=nomeEmApuracao%>" id="<%=nomeEmApuracao%>" onclick="verificaEmApuracao(this);" <%=_checaEmApuracao %>>
						</td>	                        
						<td width="12%">	
								<combo:ComboTag 
										nome="<%=nome%>"
										objeto="ecar.pojo.SituacaoSit" 
										label="descricaoSit" 
										value="codSit" 
										order="descricaoSit"
										filters="indMetaFisicaSit=S"
										textoPadrao="<%=situacaoConclusao%>"
										scripts="<%=scripts%>"
										disabled="<%= new Boolean(isConcluidoNoPeriodo).toString() %>"
								/>
						</td>
					
	<%
							}
						}
	%>
						

			                <%
							if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() != null) { 
								// Em todos os casos que a data de início não for informada, 
								// o sistema não permite a inclusão/alteração de valores realizados nos indicadores
								if(itemEstruturaDao.ObtemDataInicialItemEstrutura(acompRealFisico.getItemEstruturaIett()) == null) {								
									_disabled = "disabled=\"true\" ";
								} 
							%>
								<td width="7%" align="left">
	                                <input name="btnLimparValorRealizado" type="button" value="Limpar" class="botao" onclick="aoClicarLimparServicoPai(form, <%= cont %>);"
	                                <%=_disabled%>>
				                </td>
			                <%
			                }else if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() == null){
			                %>
			                	<td width="7%" align="left">
	                                <input name="btnGravarValorRealizado" type="button" value="Gravar" class="botao" onclick="aoClicarGravarServicoPai(form, <%=cont%>);"
	                                <%=_disabledQtdSituacao%>>
	                            </td>
			                <%
			                }else{ 
			                	if(!"S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())){
			                 %>
			                	<td width="7%" align="left">
	                                <input name="btnGravarValorRealizado" type="button" value="Gravar" class="botao" onclick="aoClicarGravarPai(form, <%=cont%>);"
	                                <%=_disabledQtdSituacao%>>
	                            </td>
			                 <%
			                 }else{ %>
			                 
			                	<td width="7%" align="left"/>
			                 <%
			                		 
			                	 }
		                	
			                }
			                  %>
			                	<td width="7%" align="left">
	                                <input name="btnExcluirValorRealizado" type="button" value="Excluir" class="botao" onclick="aoClicarExcluirPai(form, <%=cont%>);"
	                                <%=_disabledQtdSituacao%>>	                        
	                            </td>			                  
						</tr>
	<%
				}
//			}//fim do else sobre situação
%>
			<tr><td class="espacador" colspan="11"><img src="../../images/pixel.gif"></td></tr>
		</table>
		<input type="hidden" name="hidCont" value="<%=cont%>">
		<input type="hidden" id="hidNivel" name="hidNivel" value="">
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>					
				</td>
			</tr>
		</table>		
<%
	} else {
		// se nao possui, verificar mensagem
%>
		<input type="hidden" id="hidNivel" name="hidNivel" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo2" colspan="9">
				<td>Não há Indicadores de Resultado para este ciclo de acompanhamento</td>
			</tr>
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
		</table>
<%
	}
	
		//*** Aqui começa a codificação para novos indicadores que ainda não foram incluídos 
		/************/ 
		//	Captura apenas as Metas Físicas configuradas para os Tipo de Acompanhamento em questão
		/***************/
		List novosIndicadores = null;// ariDao.getNovosIndicadores(ari);
		Iterator itIndicadores = null;// novosIndicadores.iterator();
		List listAtributosConfig = new ArrayList();
		Set sisAtributosSatbs = null;
	   	
    	if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null && ari != null){
    		//Obter os sisAtributos de grupos de metas físicas configurados 
			sisAtributosSatbs = ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getSisAtributoSatbs();
			 					//  acompRefItem.       getAcompReferenciaAref().getTipoAcompanhamentoTa().getSisAtributoSatbs();
			
			if(sisAtributosSatbs != null){
				Iterator itSatbs = sisAtributosSatbs.iterator();
				while(itSatbs.hasNext()) {
					SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) itSatbs.next();
					if(sisAtributoSatb.getSisGrupoAtributoSga().equals(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas())){
						listAtributosConfig.add(sisAtributoSatb);
					}
				}
			}		
		
			
			novosIndicadores = ariDao.getNovosIndicadores(ari);

			//Se não há "tipos de indicadores" configurados, limpa a lista de novos indicadores 
			if (sisAtributosSatbs.isEmpty()){
				novosIndicadores.clear();
			}			
			
			//remover os indicadores que não pertençam à coleção de atributos identificadas acima
			if(!listAtributosConfig.isEmpty()){
			
				itIndicadores = novosIndicadores.iterator();
	   			while(itIndicadores.hasNext()) {
	   				ItemEstrtIndResulIettr itemEstrtIndResulIettr = (ItemEstrtIndResulIettr) itIndicadores.next(); 
	    				
	   				if(itemEstrtIndResulIettr.getSisAtributoSatb() != null && !listAtributosConfig.contains(itemEstrtIndResulIettr.getSisAtributoSatb())) {
	   					itIndicadores.remove();
	   				} 
	   			}
			}
			
		}
	
	
	/* MOSTRAR LISTA DE NOVOS INDICADORES NÃO CADASTRADOS PARA O ARI */
	if (novosIndicadores!=null){
		itIndicadores = novosIndicadores.iterator();
	} else {
		novosIndicadores = ariDao.getNovosIndicadores(ari);
		itIndicadores = novosIndicadores.iterator();
	}
	
	if(itIndicadores.hasNext()){
%>
		<BR>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo"><td>Novo(s) Indicador(es) de Resultado (clique para adicionar)</td></tr>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
<%
			while(itIndicadores.hasNext()){
				ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) itIndicadores.next();
%>
				<tr class="linha_subtitulo2"><td>
					<a href="javaScript:aoClicarNovoIndicador(form, <%=indResul.getCodIettir()%>);">
					<%=indResul.getNomeIettir()%></a>
				</td></tr>
<%
			}
%>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		</table>
<!-- 		</form> -->
<%
	}
	
	
	
	
	/* MONTAR ÁRVORE COM SUBNÍVEIS DO ITEM, COM LINK PARA AQUELE QUE POSSUIREM ARI */
	List[] listas = acompReferenciaDao.getItensFilhosByAcompReferencia(ari.getAcompReferenciaAref(), ari.getItemEstruturaIett(),usuario);
	List arvore = listas[0];
	List selecionaveis = listas[1];


	
	Iterator itItem = arvore.iterator();
	
	int nivelPai = ari.getItemEstruturaIett().getNivelIett().intValue();
	
	if(itItem.hasNext()){
%>
		<BR>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<!-- tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr -->
			<!-- Título retirado devido ao BUG 599 -->
			<!-- tr class="linha_subtitulo"><td>Sub-Níveis</td></tr-->
			<!-- tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr -->
<%
	 	int contFilho = 0;
 		int contArfFilhos = 0;
			while(itItem.hasNext()){
				AcompReferenciaItemAri acompRefItemFilho = new AcompReferenciaItemAri();
				ItemEstruturaIett item = (ItemEstruturaIett) itItem.next();
				String linkVermelho = "";
				
				%>
				<tr valign="top">
				<td><%
				
				int nivel = item.getNivelIett().intValue() - nivelPai;
				
				for(int i = 1; i < nivel; i++) {
				%>
					<img src="../../images/pixel.gif" width="15" height="15">
				<%
				}
				
				boolean link = false;
				if(selecionaveis.contains(item)){
		        	link = true;
				}

				if (link){
					acompRefItemFilho = ariDao.getAcompReferenciaItemByItemEstruturaIett(ari.getAcompReferenciaAref(), item);
					%>
					<div class="cascata_nivel_<%=item.getNivelIett() %>">				
					
					<img src="../../images/icon_bullet_seta.png">&nbsp;<%
					
					/* testar Ari caso existam quantidades não informadas */
					List qtdNaoInformada = ariDao.getAcompRealFisicoArfsComQtdNaoInformada(acompRefItemFilho);
					
					/* caso tenha quantidades não informadas mostrar link em vermelho */
					if(qtdNaoInformada.size() > 0) {
						linkVermelho = "class='link_vermelho'";
					}
					
					%>
					<%=item.getEstruturaEtt().getNomeEtt() %> - <a <%=linkVermelho%> href="javascript:mostrarEsconder(<%=contFilho%>, <%=acompRefItemFilho.getCodAri() %>)"><%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, item.getEstruturaEtt())%></a>
					<%
					
					
// Montagem do esqueminha de mostrar os níveis abaixo e seus respectivos indicadores de resultado,
// permitindo a inserção/alteração dos mesmos, na tela corrente

	/* selecionar os indicadores dependendo da situacao selecionada*/
	//lAcompRF = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(acompRefItemFilho, radConcluido, true);

	ItemEstrutura itemFilho = new ItemEstrutura(acompRefItemFilho.getItemEstruturaIett());
	lAcompRF = itemFilho.getRealizadosIndicadoresPorSituacao(tipoSelecao, _ariWrapper, _configuracao, periodoSelectionado, false);
	
	

	it = lAcompRF.iterator();
	unidadeMedida = "";
	
	//AQUI COMEÇA A DIV DO INDICADOR FILHO - REPLICADO DO CÓDIGO DO INDICADOR PAI
		
%><div id="div<%=contFilho%>" style="display:none">
<!-- 	<form  name="form<%=contFilho %>" method="post" >	 -->
					<br>
					<br>
	<script>
		document.getElementById('contFilho').value = <%=contFilho%>;
	</script>
	<% 
	/* se possuir lista mostrar os botões */
	if (it.hasNext()){
		String nome = "";
%>

		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>				
				</td>
			</tr>
		</table>		
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="10"><img src="../../images/pixel.gif"></td></tr>
			<%
			if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){
			%>
			<tr class="linha_subtitulo">
				<td width="10%" class="coluna_numerica">Indicador de Resultado</td>
				<td width="5%" class="coluna_numerica">Previsto<br><%=LABEL_TOTAL%></td>
				<td width="5%" class="coluna_numerica">Realizado<br><%=LABEL_TOTAL%></td>
				<td width="5%" class="coluna_numerica">Previsto<br>Exercício</td>
				<td width="5%" class="coluna_numerica">Realizado<br>Exercício</td>
				<td width="18%" align="center" colspan="2">Realizado</td>				
				<td width="12%" class="coluna_numerica">Situação</td>
				<td width="5%" class="coluna_numerica">&nbsp;</td>
				<td width="5%" class="coluna_numerica">&nbsp;</td>
			</tr>
			<%
			}
			%>
			<tr><td class="espacador" colspan="10"><img src="../../images/pixel.gif"></td></tr>
<%				
				String realizadoTotalP;
				String realizadoExercicioP;
				String realizadoTotalT;
				String realizadoExercicioT;
				contArfFilhos = 0;
				String grupoIndicador = "Indicador de Resultado";
				String corArfFilhos = new String();
				

				
				while (it.hasNext()){
					if (contArfFilhos % 2 == 0){
						corArfFilhos = "cor_nao";
					} else {
						corArfFilhos = "cor_sim"; 
					}
					contArfFilhos++;
					AcompRealFisicoArf acompRealFisico = (AcompRealFisicoArf) it.next();
		
					//replicando código, infelizmente, para poder determinar se o 
					//realizado pode ser alterado ou não conforme mantis 0011551
				
					boolean isFilhoConcluidoNoPeriodo = false;
					IndicadorResultado indicadorFilho = new IndicadorResultado(acompRealFisico.getItemEstrtIndResulIettr());
					AcompanhamentoItemEstrutura ariWrapper = new AcompanhamentoItemEstrutura(ari);
					
					if(indicadorFilho.isConcluido() && 
					   indicadorFilho.getDataConclusao().compareTo(ariWrapper.getDataReferencia()) == EcarData.ANTES ){
						isFilhoConcluidoNoPeriodo = true;
					}
					
					
					//Se a configuração do sistema está setada para trabalhar com um grupo de unidades,
					//a unidade de medida vem do atributo livre
					if(grupoUnidades != null && acompRealFisico.getItemEstrtIndResulIettr().getCodUnidMedidaIettr() != null){
						SisAtributoSatb sisAtributo = acompRealFisico.getItemEstrtIndResulIettr().getCodUnidMedidaIettr();									
						unidadeMedida = sisAtributo.getDescricaoSatb();
					}
					else{
						unidadeMedida = acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr();
					}					

					List atributos = estruturaDao.getAtributosEstruturaDadosGerais(acompRealFisico.getItemEstruturaIett().getEstruturaEtt());
					String label = "Data Inicial";
					if(atributos != null) {
						Iterator itAtributos = atributos.iterator();
						while(itAtributos.hasNext()) {
							ObjetoEstrutura atributo = (ObjetoEstrutura) itAtributos.next();
							if (atributo.iGetTipo() == EstruturaAtributoEttat.class){	            	
					        	if(atributo.iGetNome().equals("dataInicioIett")) {
					        		label = atributo.iGetLabel();
					        	}
							}
						} 
					}
					
					if(!estavaDesabilitado){
						//Verificar se é para habilitar ou não edição conforme regra do mantis 7795
						_disabled = (acompRealFisicoDao.habilitarEdicaoArf(acompRealFisico, ari.getAcompReferenciaAref(), radConcluido)) ? "" : "disabled";
					}
					
					if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
						if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
							grupoIndicador = acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
							%>
							
							
							<tr class="linha_subtitulo">
								<td width="10%" align="left"><%= grupoIndicador %></td>
								<td width="5%" class="coluna_numerica">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Previsto<br>Exercício</td>
								<td width="5%" class="coluna_numerica">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2">Realizado</td>				
								<td width="12%" class="coluna_numerica">Situação</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
							</tr>
							<%
							
						}
						else if(acompRealFisico.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)){					
							%>
							<tr class="linha_subtitulo">
								<td width="10%" align="left">Indicador de Resultado</td>
								<td width="5%" class="coluna_numerica">Previsto<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Realizado<br><%=LABEL_TOTAL%></td>
								<td width="5%" class="coluna_numerica">Previsto<br>Exercício</td>
								<td width="5%" class="coluna_numerica">Realizado<br>Exercício</td>
								<td width="18%" align="center" colspan="2">Realizado</td>				
								<td width="12%" class="coluna_numerica">Situação</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
								<td width="5%" class="coluna_numerica">&nbsp;</td>
							</tr>
							<%
							grupoIndicador = "";
						}
					}					

					String tipoQtde = "V";
					if("Q".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndTipoQtde())){
						tipoQtde = "Q";
					}					
					
					double realizado = 0;
					String linkPorLocal = "<img src=\"../../images/empty.gif\">";
					String desabilitarQtdeARF = "";
					
					if(acompRealFisico.getQtdRealizadaArf() != null) {
						realizado = acompRealFisico.getQtdRealizadaArf().doubleValue();
					}
					
					String labelRealizado = REALIZADO_NO_MES;
					
					String previstoTotal = "0";
					String realizadoTotal = "0";
					String previstoExercicio = "0";
					String realizadoExercicio = "0";
					
					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())) {
						String podeGravar = (!"".equals(_disabled) || isFilhoConcluidoNoPeriodo == true) ? "N" : "S";
						linkPorLocal = ("<a href=\"#\" onclick=\"javascript:aoClicarIndicadorPorLocal(form, '" + acompRealFisico.getCodArf() + "','qtdRealizadaArf" + contArfFilhos + "-"+contFilho+"','situacao"+ contArfFilhos + "-" + contFilho + "','" + podeGravar + "','filho')\">" +
									   "<img src=\"../../images/icon_exibir.png\" border=\"0\" title='Quantidade/Valor por Local'></a>");
						if("".equals(_disabled)) {
							desabilitarQtdeARF = "disabled=\"disabled\" ";
						}
					}
					// Em todos os casos que a data de início ou ciclo não for informada, 
					// o sistema não permite a inclusão/alteração de valores realizados nos indicadores
					if(itemEstruturaDao.ObtemDataInicialItemEstrutura(acompRealFisico.getItemEstruturaIett()) == null) {
						desabilitarQtdeARF = "disabled=\"disabled\" ";
					}
					
					previstoTotal = indResultDao.getSomaQuantidadePrevista(acompRealFisico.getItemEstrtIndResulIettr());					
					/* Tratamento diferencia para exibição de indicadores Acumulavel e não-Acumulável */
					if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
						if(tipoQtde.equals("Q")){
							previstoExercicio = Util.formataNumeroDecimal(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompRefItemFilho.getAcompReferenciaAref().getExercicioExe()));
							realizadoTotalP = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizada(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoExercicioP = Util.formataNumeroDecimal(acompRealFisicoDao.getQtdRealizadaExercicio(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoTotalT = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizada(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T"));
							realizadoExercicioT = Util.formataNumeroDecimal(acompRealFisicoDao.getQtdRealizadaExercicio(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T"));
						}
						else{
							previstoExercicio = Util.formataMoeda(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompRefItemFilho.getAcompReferenciaAref().getExercicioExe()));
							realizadoTotalP = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizada(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoExercicioP = Util.formataMoeda(acompRealFisicoDao.getQtdRealizadaExercicio(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P"));
							realizadoTotalT = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizada(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T"));
							realizadoExercicioT = Util.formataMoeda(acompRealFisicoDao.getQtdRealizadaExercicio(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T"));
						}
					}else{
						labelRealizado = REALIZADO_ATE_O_MES;
						if(tipoQtde.equals("Q")){
							previstoExercicio = Util.formataNumeroDecimal(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompRefItemFilho.getAcompReferenciaAref().getExercicioExe()));
							realizadoTotalP = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P", "T"));
							realizadoExercicioP = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P", "E"));
							realizadoTotalT = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T", "T"));
							realizadoExercicioT = Util.formataNumeroDecimal(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T", "E"));
						}
						else
						{
							previstoExercicio = Util.formataMoeda(indResultDao.getQtdPrevistoExercicio(acompRealFisico.getItemEstrtIndResulIettr(), acompRefItemFilho.getAcompReferenciaAref().getExercicioExe()));
							realizadoTotalP = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P", "T"));
							realizadoExercicioP = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "P", "E"));
							realizadoTotalT = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T", "T"));
							realizadoExercicioT = Util.formataMoeda(acompRealFisicoDao.getQuantidadeRealizadaNaoAcumulados(acompRefItemFilho, acompRealFisico.getItemEstrtIndResulIettr(), "T", "E"));
						}
					}
					
					if(isFilhoConcluidoNoPeriodo == true && indicadorFilho.getDataConclusao() != null){
				 	    labelRealizado = labelRealizado + " " +  indicadorFilho.getDataConclusao().getDataFormatadaComBarra();
					}else{
						labelRealizado = labelRealizado + " " +  acompRealFisico.getMesArf().toString() + "/" + acompRealFisico.getAnoArf().toString();
					}
					
										
%>

			<!--  A tabela abaixo está duplica propositalmente, para que possamos ter o efeito de troca de
				Acumulado no periodo x acumulado total sem consulta ao servidor. -->
					<tr class="<%=corArfFilhos%>" onmouseover="javascript: destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','<%=corArfFilhos%>')" onClick="javascript: destacaLinha(this,'click','<%=corArfFilhos%>')" class="linha_subtitulo2" id="acumulaPeriodo<%=contFilho %>" name="acumulaPeriodo<%=contFilho %>" >
						<td width="10%">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getNomeIettir()%>
							<% if (!"".equals(unidadeMedida)){   %>
							- <%=unidadeMedida%>
							<% } %>
							<!-- CODARF adicionado devido ao Mantis 5518, antes era verificado pelo codIettir+codAri+codIett -->
							<input type="hidden" name="codArf<%=contArfFilhos%>-<%=contFilho%>" id="codArf<%=contArfFilhos%>-<%=contFilho%>" value="<%=acompRealFisico.getCodArf()%>">
						</td>
						
						<%

						String validacaoIndQtde = "";///" onblur=\"javascript:validarQtdeValor(this,'" + tipoQtde + "')\"";
												
						%>
						
						<td width="5%"><%=previstoTotal%></td>
						<td width="5%"><%=realizadoTotalP%></td>
						<td width="5%"><%=previstoExercicio%></td>
						<td width="5%"><%=realizadoExercicioP%></td>
						
						<%
						//Se o valor realizado foi setado para ser calculado através de um serviço no cadastro de metas/indicadores
						if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null){
							
							String url = acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer().getUrlSer();
								
							//Transforma em minúscula primeira letra do nome do serviço para o mesmo ser utilizado como chamada ao método
							//de forma dinâmica
							url = url.substring(0,1).toLowerCase() + url.substring(1);
																								
							Object[] parametros = servicoDao.getParametrosServico(
								acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer(),ari);									
							
							Object retornoServico = ServicoDao.class.getMethod(url, new Class[]{String.class, String.class}).invoke(servicoDao, parametros);
							String retornoServicoFormatado = "";
							if (retornoServico instanceof Double){
								retornoServicoFormatado = Util.formataNumeroDecimal((Double)retornoServico);
							}																																						
							
							//se a gravação do serviço for manual
							if (acompRealFisico.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado()!= null && acompRealFisico.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado().equals("M")){
								//Se o valor gravado no banco for não nulo e igual ao valor apurado pelo serviço
								if (retornoServico != null && 
									acompRealFisico.getQtdRealizadaArf() != null &&
									acompRealFisico.getQtdRealizadaArf().equals(retornoServico)){
								%>
									<!-- Chama de forma dinâmica o método do serviço correspondente ao serviço marcado para o valor realizado no cadastro de metas/indicadores-->
									<td width="11%" align="right" nowrap="nowrap">									
									<%=labelRealizado%>
								
									<input type="text" name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" 
									<%if(retornoServico != null) {%> value="<%=retornoServicoFormatado%>" <%} else { %> value="" <%}%>
									size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>

									</td>
									<td width="7%" align="left">
		                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
		                            </td>									
									
								<%
								}
								//Se o valor gravado no banco for não nulo e diferente valor apurado pelo serviço
								else if (retornoServico != null && 
									acompRealFisico.getQtdRealizadaArf() != null && 
									!Util.formataNumeroDecimal(acompRealFisico.getQtdRealizadaArf()).equals(retornoServicoFormatado))
								{
									String msgAtencao = "Valor Atual Apurado: " + retornoServicoFormatado;
									// Em todos os casos que a data de início não for informada exibe esta mesagem
									if(acompRealFisico.getItemEstruturaIett().getDataInicioIett() == null) { 
										msgAtencao = label + " não informado";
									}
								%>									
																		
									<!-- Chama de forma dinâmica o método do serviço correspondente ao serviço marcado para o valor realizado no cadastro de metas/indicadores-->
									<td width="11%" align="right" nowrap="nowrap">																			
									<%=labelRealizado%>										
									<img width="18px" height="14px" src="../../images/atencao.gif" title="<%=msgAtencao%>">
									<input type="text" name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" 
									<%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=acompRealFisico.getQtdRealizadaArf()%>" <%} else { %> value="" <%}%>
									size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>

									</td>									
									<td width="7%" align="left">
		                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
		                            </td>										
																	
								<%
								//Se o valor do banco for nulo
								}
								else{
								%>
									<td width="11%" align="right" nowrap="nowrap">									
									<%=labelRealizado%>
									<input type="text" name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" 
									<%if(retornoServico != null) {%> value="<%=retornoServicoFormatado%>" <%} else { %> value="" <%}%>
									size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
															
									</td>
									<td width="7%" align="left">
		                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
		                            </td>								
								<%
								}
							}
							//se a gravação do serviço for automática
							else if (acompRealFisico.getItemEstrtIndResulIettr().getIndTipoAtualizacaoRealizado().equals("A")){
								String msgAtencao = "O valor poderá alterar até a data limite";
								// Em todos os casos que a data de início não for informada exibe esta mesagem
								if(acompRealFisico.getItemEstruturaIett().getDataInicioIett() == null) { 
									msgAtencao = label + " não informado";
								}
							%>	
								<!-- Chama de forma dinâmica o método do serviço correspondente ao serviço marcado para o valor realizado no cadastro de metas/indicadores-->
									<td width="11%" align="right" nowrap="nowrap">										
									<%=labelRealizado%>
									<%if (acompRealFisico.getQtdRealizadaArf() == null){ %>
										<img width="18px" height="14px" src="../../images/atencao.gif" title="<%=msgAtencao%>">
									<%} %>
									<input type="text" name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" 
									<%if(acompRealFisico.getQtdRealizadaArf() != null) {%> value="<%=acompRealFisico.getQtdRealizadaArf()%>" <%} else { %> value="<%=retornoServicoFormatado%>" <%}%>
									size="10" maxlength="20" disabled <%=desabilitarQtdeARF%> <%=validacaoIndQtde%>>
									</td>
									<td width="7%" align="left">
		                                <%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>			                                
		                            </td>	
		                    <%
							}														
						}
						else{
							
							String valorRealizado = "";
							//pegando o valor de quando o indicador foi concluído e não o valor
							//do acompRealFisico corrente, pois é o indicador que é concluído, o
							//acompRealFisico é apenas o objeto que contém as informações de conclusão
							if(isFilhoConcluidoNoPeriodo == true){
								valorRealizado = indicadorFilho.getValorRealizadoConclusao().getValorFormatado();
								_disabled = "\"disabled\"";
							}else if(acompRealFisico.getQtdRealizadaArf() != null){
								valorRealizado = Util.formataNumeroDecimal(realizado);
							}
							
						%>
						<td width="11%" align="right" nowrap="nowrap">
							<%=labelRealizado%>
							<input type="text" name="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>" id="qtdRealizadaArf<%=contArfFilhos%>-<%=contFilho%>"
								value="<%=valorRealizado%>" <%=_disabled%> size="10" maxlength="20" <%=desabilitarQtdeARF%> <%=validacaoIndQtde%> />
						</td>
						
						<td width="7%" align="left">
							<%=acompRealFisico.getItemEstrtIndResulIettr().getUnidMedidaIettr()%>
							<%=linkPorLocal%>
						</td>		
						
						<%
						}
						%>
						
						<td width="12%"> 
<%
						String _disabledQtdSituacao = _disabled;
	
						if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() != null  || isFilhoConcluidoNoPeriodo ) {
							_disabledQtdSituacao = "disabled";
						}

						nome = "situacaoSit" + contArfFilhos + "-" + contFilho;
						String scripts = _disabledQtdSituacao + " onchange=\"javascript:verificarSituacao(form, " + acompRealFisico.getCodArf().toString() + ", this.value)\"";

						if("S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())) {
							
                      		String descricaoSituacao;
                      		
                      		if (acompRealFisico.getSituacaoSit() != null){
                      		    descricaoSituacao = acompRealFisico.getSituacaoSit().getDescricaoSit();	
                      		}
                      		else
                      		{
                      			descricaoSituacao = "";	
                      		}
							
							%>
            				<input type="text" name="situacao<%=contArfFilhos%>-<%=contFilho%>" id="situacao<%=contArfFilhos%>-<%=contFilho%>" 
                                 value="<%=descricaoSituacao%>" size="10" maxlength="20" disabled>
							<%
								
						}
						else
						{
							
						
							
						if ( acompRealFisico.getSituacaoSit() != null ) {
%>						

							<combo:ComboTag 
									nome="<%=nome%>"
									objeto="ecar.pojo.SituacaoSit" 
									label="descricaoSit" 
									value="codSit" 
									order="descricaoSit"
									filters="indMetaFisicaSit=S"
									selected="<%=acompRealFisico.getSituacaoSit().getCodSit().toString()%>"
									scripts="<%=scripts%>"
									disabled="<%= new Boolean(isFilhoConcluidoNoPeriodo).toString() %>"
							/>

<%
						} else {
							
							String situacaoConclusao = "";
							if(indicadorFilho != null && isFilhoConcluidoNoPeriodo == true){
								situacaoConclusao = indicadorFilho.getValorRealizadoConclusao().getStringSituacao();
							}

							
%>
							<combo:ComboTag 
									nome="<%=nome%>"
									objeto="ecar.pojo.SituacaoSit" 
									label="descricaoSit" 
									value="codSit" 
									order="descricaoSit"
									filters="indMetaFisicaSit=S"
									textoPadrao="<%=situacaoConclusao%>"
									scripts="<%=scripts%>"
									disabled="<%= new Boolean(isFilhoConcluidoNoPeriodo).toString() %>"
							/>
<%
						}
						}
%>
						</td>
						
						<%
						// Em todos os casos que a data de início não for informada, 
						// o sistema não permite a inclusão/alteração de valores realizados nos indicadores
						if(itemEstruturaDao.ObtemDataInicialItemEstrutura(acompRealFisico.getItemEstruturaIett()) == null || isFilhoConcluidoNoPeriodo) {						
							_disabledQtdSituacao = "disabled=\"disabled\" ";
							_disabled = "disabled=\"disabled\" ";
						} 
						
						 if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() != null) { 
						 %>
							<td width="7%" align="left">
                                <input name="btnLimparValorRealizado" type="button" value="Limpar" class="botao" onclick="aoClicarLimparServicoFilho(form, <%=contFilho%>, <%=contArfFilhos%>)" 
                               	<%=_disabled%>>
			                </td>

		                <%
		                }
		                else if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() == null){
		                 %>
		                	<td width="7%" align="left">
                                <input name="btnGravarFilho" type="button" value="Gravar" class="botao" 
                                onclick="aoClicarGravarServicoFilho(form, <%=contFilho%>, <%=contArfFilhos%>)"
                                <%=_disabledQtdSituacao%>>
                            </td>
		                <%
		                }
		                else { //if (acompRealFisico.getItemEstrtIndResulIettr().getRealizadoServicoSer() != null && acompRealFisico.getQtdRealizadaArf() == null){
		                	if(!"S".equals(acompRealFisico.getItemEstrtIndResulIettr().getIndRealPorLocal())){
		                 %>
		                	<td width="7%" align="left">
                                <input name="btnGravarFilho" type="button" value="Gravar" class="botao" onclick="aoClicarGravarFilho(form, <%=contFilho %> ,<%=contArfFilhos%>)"
                                <%=_disabledQtdSituacao%>>
                            </td>
                            <%
			                 }else{ %>
			                 
			                	<td width="7%" align="left"/>
			                 <%
			                		 
			                	 }
		                	
			                }
			                  %>
		                    <td width="7%" align="left">
                                <input name="btnExcluirFilho" type="button" value="Excluir" class="botao" onclick="aoClicarExcluirFilho(form, <%=contFilho %> ,<%=contArfFilhos%>)"
                                <%=_disabledQtdSituacao%> />
                            </td>			                  
						</tr>
				
				<!-- RETIRADA TABELA DUPLICADA  -->		
<%
				}
//			}//fim do else sobre situação
%>
			<tr><td class="espacador" colspan="10"><img src="../../images/pixel.gif"></td></tr>
		</table>
		
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">
					&nbsp;
				</td>
				<td>					
					<input name="btnCancelar" type="button" value="Cancelar" class="botao" onclick="aoClicarCancelar(form);">
				</td>
			</tr>
		</table>
		
		<input type="hidden" name="hidContFilhos<%=contFilho%>" id="hidContFilhos<%=contFilho%>" value="<%=contArfFilhos%>">	
<%
	} else {
		// se nao possui, verificar mensagem
%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo2" colspan="9">
				<td>Não há Indicadores de Resultado para este ciclo de acompanhamento</td>
			</tr>
			<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
		</table>
<%
	}
	
	
//***********
//  Retira as Metas Físicas não configuradas para o Tipo de Acompanhamento da lista de exibição	
// ************	
	novosIndicadores=null;
	if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){		
		novosIndicadores =  ariDao.getNovosIndicadores(acompRefItemFilho); 

		//Se não há "tipos de indicadores" configurados, limpa a lista de novos indicadores 
		if (sisAtributosSatbs.isEmpty()){
			novosIndicadores.clear();
		}
		
		//remover os indicadores que não pertençam à coleção de atributos identificadas acima
		if(!listAtributosConfig.isEmpty()){
		
			itIndicadores = novosIndicadores.iterator();
	  			while(itIndicadores.hasNext()) {
	  				ItemEstrtIndResulIettr itemEstrtIndResulIettr = (ItemEstrtIndResulIettr) itIndicadores.next(); 
	   				
	  				if(itemEstrtIndResulIettr.getSisAtributoSatb() != null && !listAtributosConfig.contains(itemEstrtIndResulIettr.getSisAtributoSatb())) {
	  					itIndicadores.remove();
	  				} 
	  			}
			}
		
	}	
	
	
	/* MOSTRAR LISTA DE NOVOS INDICADORES NÃO CADASTRADOS PARA O ARI */
	//novosIndicadores = ariDao.getNovosIndicadores(acompRefItemFilho);
	
	if(novosIndicadores!=null) {
		itIndicadores = novosIndicadores.iterator();
	} else{
		novosIndicadores =  ariDao.getNovosIndicadores(acompRefItemFilho);
		itIndicadores = novosIndicadores.iterator();
	}
	

	if(itIndicadores.hasNext()){
%>
		<BR>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
			<tr class="linha_subtitulo"><td>Novo(s) Indicador(es) de Resultado (clique para adicionar)</td></tr>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
<%
			while(itIndicadores.hasNext()){
				ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) itIndicadores.next();
%>
				<tr class="linha_subtitulo2"><td>
					<a href="javaScript:aoClicarNovoIndicador(form, <%=indResul.getCodIettir()%>, <%=acompRefItemFilho.getCodAri()%>);">
					<%=indResul.getNomeIettir()%></a>
				</td></tr>
<%
			}
%>
			<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		</table>
<%
	}
		}else{
				%>
					<img src="../../images/icon_bullet_seta.png">&nbsp;<%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, item.getEstruturaEtt())%>
				<%
				}
				
				%>
				</div>
				</td>
				</tr>
			<%
			contFilho++;
			}
%>
		</table>
<%
	}
%>

<script language="JavaScript">
	/* Para montar as validações em javascript: 										*/
	/*  	cont - é a variável que guardou a quantidade de registros (linhas) do form,	*/
	/*				sendo necessária para montar os nomes dos campos que se forma com 	*/
	/*				nomeCampo + cont (Ex: nome1, nome2)									*/
	function validarPai(form, contSelecionado){		

		if( eval("form.qtdRealizadaArf" + contSelecionado + ".disabled") == false ) {
			if( Trim( eval("form.qtdRealizadaArf" + contSelecionado + ".value") ) != "" ) {
				if( !validaDecimal( eval("form.qtdRealizadaArf" + contSelecionado + ".value") ) ) {
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.invalido")%>");
					eval("form.qtdRealizadaArf" + contSelecionado + ".focus()");
					return(false); 
				}

				if( eval("form.situacaoSit"+contSelecionado+".disabled") == false ) {
					if( eval("form.situacaoSit"+contSelecionado+"[form.situacaoSit"+contSelecionado+".selectedIndex].value") == ''){							
						alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.situacaoSit.obrigatorio")%>");
						eval("form.situacaoSit" + contSelecionado + ".focus()");
						return(false); 				
					}
				}
			}
			else{

				if( eval("form.situacaoSit"+contSelecionado+".disabled") == false ) {
					if( eval("form.situacaoSit"+contSelecionado+"[form.situacaoSit"+contSelecionado+".selectedIndex].value") != ''){							
						alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.obrigatorio")%>");
						eval("form.qtdRealizadaArf" + contSelecionado + ".focus()");
						return(false); 
					}
				}
			}
		}
		return(true);
	}
	
	function validarFilho(form, contItemFilho, contSelecionado) {
				
		var numero = contItemFilho; /*form.numFilho.value*/ 
		var jsCont = 1;
		var numReg = eval("form.hidContFilhos"+contItemFilho+".value");
			
		var qtdRealizadaArf = 0;
		var situacaoSit = '';
	
		qtdRealizadaArf = document.getElementById('qtdRealizadaArf'+contSelecionado+'-'+contItemFilho);
		situacaoSit = document.getElementById('situacaoSit'+contSelecionado+'-'+contItemFilho);
		if( qtdRealizadaArf == 'undefined' ) return false; /* se campo não encontrado, sai do tratamento */

		if(qtdRealizadaArf.disabled == false ) {
			if( Trim(qtdRealizadaArf.value) != "" ) {
				if(!validaDecimal(qtdRealizadaArf.value)) {
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.invalido")%>");					
					qtdRealizadaArf.focus();
					return(false); 
				}

				if( situacaoSit.disabled == false ) {
					if( situacaoSit.value == ''){							
						alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.situacaoSit.obrigatorio")%>");
						situacaoSit.focus();
						return(false); 				
					}
				}
			}
			else{

				if( situacaoSit.disabled == false ) {
					if( situacaoSit[situacaoSit.selectedIndex].value != ''){							
						alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.obrigatorio")%>");
						qtdRealizadaArf.focus();
						return(false); 
					}
				}
			}
		}
		else{
			return(false); 
		}					
		return(true);

	}
	
	function validarQtdeValor(obj, tipo){
		if(obj.value != ''){
			if(tipo == 'Q'){
				if(!isInteger(obj.value)){
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.valorInteiro")%>");
					obj.value='';
					obj.focus();
				}
			}
			
			if(tipo == 'V'){
				if(!isNumeric(obj.value) && !validaDecimal(obj.value)){
					alert("<%=_msg.getMensagem("acompanhamento.realizadoFisico.validacao.qtdRealizadaArf.valorNumeric")%>");
					obj.value='';
					obj.focus();
				}
			}
		}
	}
</script>

<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){

%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>