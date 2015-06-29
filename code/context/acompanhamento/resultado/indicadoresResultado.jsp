<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>


<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.dao.AcompRealFisicoDao"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.pojo.AcompRealFisicoArf"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.pojo.ExercicioExe"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>

<%@ page import="ecar.permissao.ValidaPermissao"%>

<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.CorDao"%>
<%@ page import="ecar.dao.ExercicioDao"%>
<%@ page import="ecar.dao.ItemEstrtIndResulDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.dao.ItemEstrutFisicoDao"%>

<%@ page import="ecar.api.facade.*" %>

<%@ page import="ecar.util.Dominios"%>
<%@ page import="comum.util.Data"%>
<%@ page import="comum.util.Util"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Set"%>


<%@page import="java.util.GregorianCalendar"%>
<html lang="pt-br">
	<head>
		<%@ include file="../../include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet"
			href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css"
			type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/popUp.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/prototype.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/numero.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/datas.js"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>

		<%
		String codArisControleV = Pagina.getParamStr(request, "codArisControleVisualizacao");
		boolean telaVisualizacao = Pagina.getParamStr(request, "tela").equals("V");
 		%>

		<script type="text/javascript">

			function abrirPopUpNivelPlanejamento(){
				abreJanela("popUpNivelPlanejamento.jsp", 500, 300, "nivelPlanejamento");
				janela.focus();
			}
			function abrirPopUpInfoIndicador(codIndicador){
				abreJanela("popUpInfoIndicador.jsp?codIndicador=" + codIndicador, 650, 400, "InfoIndicador");
				janela.focus();
			}
			function abrirPopUpDetalhesIndicadorResultado(codAri, codExercicio){
				abreJanela("popUpDetalhesIndicadorResultado.jsp?codAri=" + codAri + "&codExe=" + codExercicio, 700, 300, "InfoIndicadorDetalhe");
				janela.focus();
			}
			function abrirPopUpGraficoRealizadoPorExercicio(codAri, codExe, codIettir){
				abreJanela("popUpGraficoRealizadoPorExercicio.jsp?codAri=" + codAri + "&codExe=" + codExe + "&codIettir=" + codIettir + "&mapOnly=false" , 800, 500);
				janela.focus();
			}
			function abrirPopUpGraficoPrevisao(codIndicador, codAri, comQtde, soPrevisao){
				abreJanela("popUpGraficoPrevisaoIndicadorResultado.jsp?comQtde=" + comQtde + "&soPrevisao=" + soPrevisao + "&mesIni=" + document.form.primeiroMesExe.value + "&anoIni=" + document.form.primeiroAnoExe.value + "&codIndicador=" + codIndicador + "&codAri=" + codAri + "&qtdeAcompRealFisico=" + document.form.qtdeAcompRealFisico.value, 750, 550);
				janela.focus();
			}
			
			function abrirPopUpConsultaRealizadoPorLocal(form, codARF, nomeIndicador) {
				var janela = abreJanela('popUpConsultaRealizadoFisicoPorLocal.jsp?codARF=' + codARF + '&nomeIndicador=' + escape(nomeIndicador), 600, 500, 'popUpConsultaRealizadoFisicoPorLocal');
				janela.focus();
			}


<% if (telaVisualizacao) { %>

function reload(){
	document.forms[0].action = "indicadoresResultado.jsp?codArisControleVisualizacao=<%=codArisControleV%>";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.form.codArisControleVisualizacao.value = "<%=codArisControleV%>";
	document.forms[0].action = link + "&codArisControleVisualizacao=<%=codArisControleV%>";
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}


function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function mostrarEsconder(numero) {
    var i = 0;
    var divs = document.getElementsByTagName('DIV');
    var divEstiloDaVez = document.getElementById('block'+numero).style.display;

    var divdavez = null;
    for (i = 0; i < divs.length; i++) {
		divdavez = divs[i];

		if( divdavez.id.substring(0, 5) == 'block' ) {
			divdavez.style.display = 'none';
		}
	}
	
	divdavez = document.getElementById('block'+numero);

	if (divEstiloDaVez == 'block' ){
		divdavez.style.display = 'none';
	} else {
		divdavez.style.display = 'block';
	}
}

<%} else { %>		
			
			function reload(){
				document.forms[0].action = "indicadoresResultado.jsp";
				document.forms[0].submit();
			}
	
			function trocarAba(nomeAba) {
				document.forms[0].action = nomeAba;
				document.forms[0].clicouAba.value = "S";
				document.forms[0].submit();
			}
			
			function aoClicarTrocar(radAcumulados, numFilho){
				if ('P' == radAcumulados)
				{
					document.getElementById('acumulaPeriodo'+numFilho).style.display='';
					document.getElementById('acumulaTotal'+numFilho).style.display='none';
				}
				if ('T' == radAcumulados)
				{
					document.getElementById('acumulaPeriodo'+numFilho).style.display='none';
					document.getElementById('acumulaTotal'+numFilho).style.display='';
				}
			}
			
			function mostrarEsconder(numero, codIett, compl) {
				var i=0;
			    var contador = document.getElementById('contFilho').value;
                var complemento = null;

                if (arguments.length == 2)
					complemento = ''
                else
                    complemento = compl;
				
			    var divdavez = null;
			    for (i = 0; i <= contador; i++) {
					if (i != numero) {
						divdavez = document.getElementById('div'+i+complemento);
						if( divdavez != null ) {
				    		divdavez.style.display = 'none';
				    	}
					}
			    }
			    
				if (document.getElementById('div'+numero+complemento).style.display=='none') {
				     document.getElementById('div'+numero+complemento).style.display='';
				     document.getElementById('filhoSelecionado').value = codIett;
				     document.getElementById('numFilho').value = numero;
				} else {
				     document.getElementById('div'+numero+complemento).style.display='none';
				}

			}

			
<%}%>
		</script>
	</head>

	<%@ include file="../../cabecalho.jsp"%>
	<%@ include file="../../divmenu.jsp"%>
	<body>
		<style type="text/css">
		.dateCel{width:100px;}
		.coluna_numerica{text-align:left;}
		</style>
		<div id="verificaSituacaoConclusao" style="position: absolute;"></div>
		<div id="conteudo">
			<%
try{ 
		final String REALIZADO_NO_MES = "no mês";
		final String REALIZADO_ATE_O_MES = "até o mês";
		
		IndicadorResultadoFachada indicadorResultadoFachada;
		
		ValidaPermissao validaPermissao = new ValidaPermissao();
		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(); 
		ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);
		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
		//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		CorDao corDao = new CorDao(request);
		ItemEstrtIndResulDao indResultDao = new ItemEstrtIndResulDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		
		//ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
		AcompReferenciaAref arefSelecionado = null;
		ItemEstruturaIett itemEstrutura = null;
		
		//pega os atributos do request
		String status = Pagina.getParamStr(request, "relatorio");
		String itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
		String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String mesReferencia = Pagina.getParamStr(request, "referencia_hidden");
		if(mesReferencia == null && mesReferencia.equals(""))
			mesReferencia = Pagina.getParamStr(request, "mesReferencia");
		String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
		//Caso de Visualização
		String primeiroIettClicado = Pagina.getParamStr(request, "primeiroIettClicado");		
		AcompReferenciaItemAri ari = null;
		List acompanhamentos = null;
		
		// Pra nao dar erro quando perder codTipoAcompanhamento
		// descobre pelo Ari
		String strAri = Pagina.getParamStr(request, "codAri");
		ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strAri));
		arefSelecionado = ari.getAcompReferenciaAref();
		
		if(codTipoAcompanhamento.equals("")) {
			codTipoAcompanhamento = arefSelecionado.getTipoAcompanhamentoTa().getCodTa().toString();
		} 
		
		acompanhamentos = acompReferenciaDao.getListAcompReferenciaByTipoAcompanhamento(new Long(codTipoAcompanhamento));
		
		Iterator acompanhamentosIt = acompanhamentos.iterator();
		while(acompanhamentosIt.hasNext()) {
				arefSelecionado = (AcompReferenciaAref)acompanhamentosIt.next();
			if((arefSelecionado.getCodAref().toString()).equals(mesReferencia)) {
				break;
			}
		}
		
	
		if(arefSelecionado != null && !itemDoNivelClicado.trim().equals("") && itemDoNivelClicado != null ){
			if (telaVisualizacao && primeiroIettClicado!=null) {
				itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(primeiroIettClicado));
			} else {
				itemEstrutura = (ItemEstruturaIett)itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(itemDoNivelClicado));
			}

			//ari = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionado,itemEstrutura);
			// se a pesquisa nao resultar em nada 
			if(ari == null) {
				ari = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strAri));
			}
		} else {
			// Pra nao dar erro quando perder codTipoAcompanhamento
			// descobre pelo Ari
			if(ari != null)
				itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, ari.getItemEstruturaIett().getCodIett());
		}

		List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();

		String periodo = Pagina.getParamStr(request, "periodo");
		
		String funcaoSelecionada = "REL_FISICO_IND_RESULTADO";
		String tipoSelecao = Pagina.getParamStr(request, "radConcluido");
			if(tipoSelecao.equals(""))
				tipoSelecao = Dominios.NAO_CONCLUIDOS;

	
		if (telaVisualizacao){ // se tela = "V" provém de visualização dado na URL
			AcompReferenciaItemAri acompReferenciaItem = ari;
			%>
			<%@ include file="../form_visualizar.jsp" %>	
			<%
					 
		} else { // provém de Registro, fazer o tratamento de registro

			arefSelecionado = null;
			arefSelecionado = ari.getAcompReferenciaAref();
			
		
			%>
			<%@ include  file="../form_registro.jsp" %>	
		<%} // fim do if caso esteja em Registro %>

			<br>

			<form name="form" id="form" method="post" action="">
				<input type="hidden" name="codTipoAcompanhamento"
					value="<%=codTipoAcompanhamento%>">
				<input type="hidden" name="niveisPlanejamento"
					value="<%=niveisPlanejamento%>">
				<input type="hidden" name="referencia_hidden"
					value="<%=mesReferencia%>">
				<input type="hidden" name="itemDoNivelClicado"
					value="<%=itemDoNivelClicado%>">
				<input type="hidden" name="codAri"
					value="<%=Pagina.getParamStr(request, "codAri")%>">
				<input type="hidden" name="primeiroIettClicado"
					value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
				<input type="hidden" name="primeiroAriClicado"
					value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
				<input type="hidden" name="periodo"
					value="<%=Pagina.getParamStr(request, "periodo")%>">
				<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
				<input type="hidden" name="hidFormaVisualizacaoEscolhida"
					value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
				<!-- Campo necessário para botões de Avança/Retrocede -->
				<input type="hidden" name="codArisControle"
					value="<%=Pagina.getParamStr(request, "codArisControle")%>">

				<input type="hidden" name="subNiveis"
					value="<%=Pagina.getParamStr(request, "subNiveis")%>">
				<input type="hidden" id="contFilho" name="contFilho" value="">
				<input type="hidden" id="numFilho" name="numFilho" value="">
				<input type="hidden" name="codNovoIndicador" value="">
				<input type="hidden" name="codAriFilho" value=''>
				

				<!-- Compos para compatibilidade entre registro e visualização -->
				<input type="hidden" name="mesReferencia"
					value="<%=ari.getAcompReferenciaAref().getCodAref().toString()%>">
				<input type="hidden" name="codigosItem"
					value="<%=Pagina.getParamStr(request, "codigosItem")%>">
				<input type="hidden" name="semInformacaoNivelPlanejamento"
					value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>">
				<input type="hidden" name="tela"
					value="<%=Pagina.getParamStr(request, "tela") %>">
				<input type="hidden" name="codTipoAcompanhamento"
					value="<%= Pagina.getParamStr(request, "codTipoAcompanhamento") %>">
					
				
				<input type="hidden" name="codRegd" value="">
				<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
				<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
			

				<!-- Campo necessário para botões de Avança/Retrocede -->
				<!-- input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>"-->
				<input type="hidden" name="codArisControleVisualizacao"
					value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">


				<!-- começa a exibir os indicadores de resultado -->

				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- começa a imprimir a parte de seleção das opções de indicadores (concluidos, nao concluidos e todos) -->
					<tr>
						<td class="espacador" colspan="2">
							<img src="../../images/pixel.gif">
						</td>
					</tr>
					<tr class="linha_subtitulo" valign="bottom">
						<td width="100%" colspan="2">
							Exibir:
							<input type="radio" class="form_check_radio" name="radConcluido"
								value="<%=Dominios.TODOS%>" onclick="reload();"
								<%=Pagina.isChecked(tipoSelecao, Dominios.TODOS)%>>
							Todos
							<input type="radio" class="form_check_radio" name="radConcluido"
								value="<%=Dominios.NAO_CONCLUIDOS%>" onclick="reload();"
								<%=Pagina.isChecked(tipoSelecao, Dominios.NAO_CONCLUIDOS)%>>
							Não Concluídos
							<input type="radio" class="form_check_radio" name="radConcluido"
								value="<%=Dominios.CONCLUIDOS%>" onclick="reload();"
								<%=Pagina.isChecked(tipoSelecao, Dominios.CONCLUIDOS)%>>
							Concluídos
						</td>

						<%-- td align="right">
							&nbsp;Selecionar: 
							< %=configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%>
							<a href="javascript:abrirPopUpNivelPlanejamento()" title="Selecionar < %=configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%>">
							<img src="../../images/relAcomp/icon_nivelplanejamento.png"	border="0">
							</a>
						</td--%>
					</tr>
					<tr>
						<td class="espacador" colspan="2">
							<img src="../../images/pixel.gif">
						</td>
					</tr>
					<br>
				    <br>

					<%
					String radConcluido = Pagina.getParamStr(request, "radConcluido");
				
					if(!radConcluido.equals("")) {
						tipoSelecao = radConcluido;
					}
					%>
					<!-- jsp que grava realizado físico -->
					<%-- Só exibe os campos para informar os valores se o usuário tiver permissão para editá-lo  
						e não vier da tela de visualização--%>
					<%if ( ( !telaVisualizacao) && validaPermissao.permissaoInformarRealizadoFisico(ari.getItemEstruturaIett(), seguranca.getUsuario(), seguranca.getGruposAcesso() ) ) { 
					
						AcompanhamentoItemEstrutura acomp = new  AcompanhamentoItemEstrutura(ari);
						//pega a data do acompanhamento
						EcarData data = new EcarData(acomp.getMesReferencia(), acomp.getAnoReferencia());
					    //pega a data inicial e final do item
						EcarData dataInicial = new EcarData(acomp.getItemEstrutura().getDataInicial());	
					    EcarData dataFinal = new EcarData(acomp.getItemEstrutura().getDataFinal());	
						//se o acompanhamento está fora do ciclo de meses do item não permitimos
						//entrar com valores realizados
						if(EcarData.pertenceAoIntervalo(data, dataInicial, dataFinal)){
					%>
					<tr>
						<td colspan="2"><%@include file="realizadoFisico.jsp"%>
					      <br>
					    </td>
					</tr>
					 <%  }
					 } %>
					<!-- termina de imprimir a parte de seleção das opções de indicadores (concluidos, nao concluidos e todos) -->
					<%
		boolean primeiroLink = true;
		String blockDivName = "";
		if( ari != null ) {
			blockDivName = "block" + ari.getCodAri();
		} else {
			blockDivName = "blockUndefined";
		} 
		
		String estiloDiv = "display: none;";
		if( primeiroLink ) {
			estiloDiv = "display: block;";
			primeiroLink = false;
		}
		%>
					<!-- começa a imprimir a linha da execução física -->
					<tr valign="top">
						<td colspan="2" width="100%">
							<div id="<%=blockDivName%>">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr valign="top">
										<td colspan="2">
											<table width="100%" cellpadding="0" cellspacing="0"
												border="0">
												<%	if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){ %>
												<tr>
													<td class="titulo">
														Indicadores de Resultado
													</td>
													<td class="titulo" align="center" colspan="2">
														Realizado no Mês
													</td>
													<td class="titulo" align="center">
														Situação
													</td>
													<td class="titulo" align="center">
														P/R
													</td>
													<%
			//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecao(itemEstrutura.getCodIett(), ari, tipoSelecao);
			//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecaoResultados(itemEstrutura.getCodIett());
			List exerciciosAnteriores = new ExercicioDao(request).getExerciciosValidos(itemEstrutura.getCodIett());
			Iterator itEx = exerciciosAnteriores.iterator();
			boolean primeiro = true;
			while(itEx.hasNext()) {
				ExercicioExe exercicio = (ExercicioExe) itEx.next();					
				if( (itemEstrutura.getDataInicioIett() != null && itemEstrutura.getDataTerminoIett() != null) || 
						acompRealFisicoDao.getQtdeRealizadoExercicioByIettAndExe(exercicio, itemEstrutura, ari.getAcompReferenciaAref(), "") != 0  ||
						new ItemEstrtIndResulDao(request).getQtdPrevistoExercicio(ari, tipoSelecao, itemEstrutura, exercicio, "") != 0 ) {
					if(primeiro){
						primeiro = false;
	%>
													<input type="hidden" name="primeiroMesExe"
														value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
													<input type="hidden" name="primeiroAnoExe"
														value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
													<% 			}	%>
													<td class="titulo_exercicio" align="center">
														<a
															href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=ari.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;
													</td>
													<%		}
				} %>
													<td class="titulo" align="center">
														Total
													</td>
													<td class="titulo" align="center">
														Realizado/
														<br>
														Previsto(%)
													</td>
													<td class="titulo">
														<table width="100%">
															<tr>
																<td colspan="3" class="titulo">
																	Projeção na Data Final 1
																</td>
															</tr>
															<tr>
																<td class="titulo" colspan="2">
																	Situação Prevista
																</td>
																<td class="titulo" align="right">
																	Projeção de Término
																</td>
														</table>
													</td>
												</tr>
												<%	}
	int totalColunas = 9;

	List indResultados = null;
	//indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, tipoSelecao, true);
	
	EcarData periodoSelecionado = new EcarData(ari.getAcompReferenciaAref().getMesAref(),
			ari.getAcompReferenciaAref().getAnoAref());
	
	//ConfiguracaoCfg conf = new ConfiguracaoDao(request).getConfiguracao();
	AcompanhamentoItemEstrutura _ari = new AcompanhamentoItemEstrutura(ari);
	
	ItemEstrutura itemSelecionado = new ItemEstrutura(ari.getItemEstruturaIett());
	indResultados = itemSelecionado.getRealizadosIndicadoresPorSituacao(tipoSelecao, _ari, configuracao, periodoSelecionado, true);
	
	
	
	%>
												<input type="hidden" name="qtdeAcompRealFisico"
													value="<%=indResultados.size()%>">
												<%
	//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecao(itemEstrutura.getCodIett(),ari, tipoSelecao);
	//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecaoResultados(itemEstrutura.getCodIett());	
	List exerciciosAnteriores = new ExercicioDao(request).getExerciciosValidos(itemEstrutura.getCodIett());
	
	if (indResultados != null && indResultados.size() > 0) {
		Iterator itIndResult = indResultados.iterator();

		String grupoIndicador = "Indicador de Resultado";
		boolean primeiro = true;

		while(itIndResult.hasNext()) {
			AcompRealFisicoArf arf = (AcompRealFisicoArf) itIndResult.next();
			if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null) {
				if( arf.getItemEstrtIndResulIettr().getSisAtributoSatb() != null
					&& !grupoIndicador.equals(arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())) {
					grupoIndicador = arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
	%>
												<tr>
													<td colspan="<%=8 + exerciciosAnteriores.size()%>">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td class="titulo"><%=grupoIndicador%></td>
													<td class="titulo" align="center" colspan="2">
														Realizado no Mês
													</td>
													<td class="titulo" align="center">
														Situação
													</td>
													<td class="titulo" align="center">
														P/R
													</td>
													<%
					Iterator itEx = exerciciosAnteriores.iterator();
					while (itEx.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itEx.next();
							if(primeiro) {
								primeiro = false;
	%>
													<input type="hidden" name="primeiroMesExe"
														value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
													<input type="hidden" name="primeiroAnoExe"
														value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
		<%					}	%>
													<td class="titulo_exercicio" align="center">
														<a
															href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=ari.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;
													</td>
	<%					
					}		%>
													<td class="titulo" align="center">
														Total
													</td>
													<td class="titulo" align="center">
														Realizado/
														<br>
														Previsto(%)
													</td>
													<td class="titulo">
														<table width="100%">
															<tr>
																<td colspan="3" class="titulo" align="center">
																	Sinalização do indicador
																</td>
															</tr>
															<tr>
																<td class="titulo" align="center">
																	Ciclo atual
																</td>
																<td class="titulo" align="center">
																	Linha de Base
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<%
				} else if (arf.getItemEstrtIndResulIettr().getSisAtributoSatb() == null	&& !grupoIndicador.equals("")) {
	%>
												<tr>
													<td class="titulo"><%=grupoIndicador%></td>
													<td class="titulo" align="center" colspan="2">
														Realizado no Mês
													</td>
													<td class="titulo" align="center">
														Situação
													</td>
													<td class="titulo" align="center">
														P/R
													</td>
													<%				Iterator itEx = exerciciosAnteriores.iterator();
					while (itEx.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itEx.next();
							if (primeiro) {
								primeiro = false;
	%>
													<input type="hidden" name="primeiroMesExe"
														value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
													<input type="hidden" name="primeiroAnoExe"
														value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
	<%						}	%>
													<td class="titulo_exercicio" align="center">
														<a
															href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=ari.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;
													</td>
	<%					
					}		%>
													<td class="titulo" align="center">
														Total
													</td>
													<td class="titulo" align="center">
														Realizado/
														<br>
														Previsto(%)
													</td>
													<td class="titulo">
														<table width="100%">
															<tr>
																<td colspan="3" class="titulo">
																	Projeção na Data Final 3
																</td>
															</tr>
															<tr>
																<td class="titulo" colspan="2">
																	Situação Prevista
																</td>
																<td class="titulo" align="right">
																	Projeção de Término
																</td>
														</table>
													</td>
												</tr>
												<%				grupoIndicador = "";
				}
			}
			String tipoQtde = arf.getItemEstrtIndResulIettr().getIndTipoQtde();
	%>
												<tr>
													<td class="subtitulo" width="20%">
														<table>
															<tr>
																<td class="subtitulo" width="3%">
																	<a
																		href="javascript:abrirPopUpGraficoRealizadoPorExercicio(<%=ari.getCodAri()%>, 
										<%=ari.getAcompReferenciaAref().getExercicioExe().getCodExe()%>, <%=arf.getItemEstrtIndResulIettr().getCodIettir()%>)">
																		<img src="../../images/relAcomp/icon_grafico.png"
																			border="0"> </a> 
																</td>
																<td class="subtitulo">
																	<a
																		href="javascript:abrirPopUpInfoIndicador(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>)"><%=arf.getItemEstrtIndResulIettr().getNomeIettir()%>&nbsp;-&nbsp;<%=new ItemEstrtIndResulDao(null).getUnidadeUsada(arf.getItemEstrtIndResulIettr())%></a>
																
																</td>
															</tr>
														</table>
													</td>
													<td class="subtitulo" width="6%" colspan="2">
														<table>
															<tr>
																<%
			String porLocal = arf.getItemEstrtIndResulIettr().getIndRealPorLocal();
			if (arf.getQtdRealizadaArf() != null) {
	%>
																<td class="subtitulo" align="center" width="3%">
																	<%
				String qtdeRealizada = "";
				if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
					qtdeRealizada = Pagina.trocaNullNumeroDecimalPT_BR(arf.getQtdRealizadaArf());
				} else { //Valor --> 2 casas decimais
					qtdeRealizada = Pagina.trocaNullMoeda(arf.getQtdRealizadaArf());
				}
	
				if ("S".equals(porLocal)) { %>
						<a
							href="javascript:abrirPopUpConsultaRealizadoPorLocal(form, '<%=arf.getCodArf()%>','<%=arf.getItemEstrtIndResulIettr().getNomeIettir()%>')"
							title="Realizado por Local"> <%=qtdeRealizada%></a>
							2
	<%			} else {	%>
						<%=qtdeRealizada%>
	<%			}			%>
						&nbsp;
					</td>
	<%		} else {	%>
			<td class="subtitulo" align="center" width="3%">
				0&nbsp;
			</td>
	<%		}	%>
															</tr>
														</table>
													</td>
													<td class="subtitulo" align="center" width="7%">
														&nbsp;
														<%		if (arf.getSituacaoSit() != null)
				out.print(arf.getSituacaoSit().getDescricaoSit());
	%>
													</td>
													<td class="subtitulo" align="center" width="2%">
														<table width="100%" cellpadding="0" cellspacing="0">
															<tr>
																<td class="subtitulo">
																	<b>P</b>
																</td>
															</tr>
															<tr>
																<td class="subtitulo">
																	<b>R</b>
																</td>
															</tr>
														</table>
													</td>
													<%
			Iterator itValoresExAnt = exerciciosAnteriores.iterator();
			double totalRealizado = 0;
			double totalPrevisto = 0;
			List valoresR = new ArrayList();
			List valoresP = new ArrayList();

			ExercicioExe exeAref = ari.getAcompReferenciaAref().getExercicioExe();
			while (itValoresExAnt.hasNext()) {
				ExercicioExe exercicioAnt = (ExercicioExe) itValoresExAnt.next();
					
				double previstoNoExercicio = 0;
				double realizadoNoExercicio = 0;
		
				boolean exercicioValido = false;
				List valoresRealExerc = null;
				List valoresPrevExerc = null;
				if (exercicioAnt.getDataFinalExe().before(exeAref.getDataFinalExe()) || exercicioAnt.equals(exeAref)) {
					exercicioValido = true;
				}
		
				if ("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())) {
					realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, arf.getItemEstrtIndResulIettr(),ari.getAcompReferenciaAref());
					//double previstoNoExercicio = new ItemEstrtIndResulDao(request).getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(), exercicioAnt);
					previstoNoExercicio = itemEstrutFisicoDao.getQtdPrevistaExercicio(exercicioAnt, arf.getItemEstrtIndResulIettr(),null);					
				} else {													
					valoresRealExerc = acompRealFisicoDao.ObtemListaRealizadoExercicioByIettrAndExe(exercicioAnt,arf.getItemEstrtIndResulIettr(),ari.getAcompReferenciaAref());
					realizadoNoExercicio = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresRealExerc);
					
					valoresPrevExerc = itemEstrutFisicoDao.ObtemListaPrevistoExercicioByIettrAndExe(exercicioAnt,arf.getItemEstrtIndResulIettr(),null);
					previstoNoExercicio = itemEstrutFisicoDao.getSomaValoresIettfs(arf.getItemEstrtIndResulIettr(), valoresPrevExerc);										
					
					
				}
				
				if ("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())) {
					//não existe a lista de realizados para o tipo "S". O valor realizado foi buscado com sum no banco
					valoresR.add(Double.valueOf(realizadoNoExercicio));
					valoresP.add(Double.valueOf(previstoNoExercicio));
				} else{
					if (valoresRealExerc.size() > 0){
						valoresR.add(Double.valueOf(realizadoNoExercicio));
					} else //precisa adicionar null para efetuar o cálculo do último informado de maneira correta.
						valoresR.add(null);
					if (valoresPrevExerc.size() > 0){
						valoresP.add(Double.valueOf(previstoNoExercicio));
					} else //precisa adicionar null para efetuar o cálculo do último informado de maneira correta.
						valoresP.add(null);
				}
				//valoresP.add(Double.valueOf(previstoNoExercicio));		
				totalColunas++;
	%>
													<td class="subtitulo" align="center">
														<table width="100%" cellpadding="0" cellspacing="0">
															<tr>
																<td class="subtitulo" align="right"> 
																	<%
				if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
					out.print(Pagina.trocaNullNumeroDecimal(new Double(previstoNoExercicio)));
				} else { //Valor --> 2 casas decimais
					out.print(Pagina.trocaNullMoeda(new Double(previstoNoExercicio)));
				}
	%>
																</td>
															</tr>
															<tr>
																<td class="subtitulo" align="right">
																	<%
				if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
					out.print(Pagina.trocaNullNumeroDecimal(new Double(realizadoNoExercicio)));
				} else { //Valor --> 2 casas decimais
					out.print(Pagina.trocaNullMoeda(new Double(realizadoNoExercicio)));
				}
	%>
																</td>
															</tr>
														</table>
													</td>
<%														
			}//termina o while de itIndResult

		Collections.reverse(valoresR);
		Collections.reverse(valoresP);
%>
													<td class="subtitulo" align="center">
														<table width="100%" cellpadding="0" cellspacing="0">
															<tr>
																<td class="subtitulo" align="right">
																	<%
		totalPrevisto = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresP).doubleValue();

		if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
			out.print(Pagina.trocaNullNumeroDecimal(new Double(totalPrevisto)));
		} else { //Valor --> 2 casas decimais
			out.print(Pagina.trocaNullMoeda(new Double(totalPrevisto)));
		}
	%>
																</td>
															</tr>
															<tr>
																<td class="subtitulo" align="right">
																	<%
		totalRealizado = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresR).doubleValue();

		if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade 
			out.print(Pagina.trocaNullNumeroDecimal(new Double(totalRealizado)));
		} else { //Valor --> 2 casas decimais
			out.print(Pagina.trocaNullMoeda(new Double(totalRealizado)));
		}
	%>
																</td>
															</tr>
														</table>
													</td>
													<td class="subtitulo" align="center">
														<table width="100%" cellpadding="0" cellspacing="0">
															<tr>
																<td class="subtitulo">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<%	double realizadoPrevisto = 0;
		if (totalPrevisto > 0)
			realizadoPrevisto = ((totalRealizado / totalPrevisto) * 100);
	%>
																<td class="subtitulo" align="center"><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double((realizadoPrevisto)))%></td>
															</tr>
														</table>
													</td>
													<td class="subtitulo">
														<%
		int qtdeRegistros = acompRealFisicoDao.getQtdRegistrosRealizadoPeriodo(arf.getItemEstrtIndResulIettr(), arf.getItemEstruturaIett(), Long
			.valueOf(ari.getAcompReferenciaAref().getMesAref()), Long.valueOf(ari.getAcompReferenciaAref().getAnoAref()));
		if (qtdeRegistros > 1) {
			if (arf.getItemEstrtIndResulIettr().getIndProjecaoIettr().equals("S") && totalRealizado > 0) {
	%>
														<table  border="0" width="100%" cellspacing="0">
															<tr>
																<td class="subtitulo">
																	<a
																		href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr()
																		.getCodIettir()%>, <%=ari.getCodAri()%>, 'S', '')">
																		<img src="../../images/relAcomp/icon_grafico.png"
																			border="0"> </a> 2
																</td>
																<td class="subtitulo">
																	<%
				double resultado = ariDao.calculoProjecao(arf.getItemEstrtIndResulIettr(),ari);
				// Mantis 0010128 - Qtd prevista não é mais informado por exercício													
				// objeto exercicioPrevisto não está sendo usado.
				//ExercicioExe exercicioPrevisto = indResultDao.getMaiorExercicioIndicador(arf.getItemEstrtIndResulIettr());
				//double qtdePrevista = indResultDao.getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(),exercicioPrevisto);
				String strProjecao = "";
				String strCorProjecao = "color=#1C2265";
				if (resultado == totalPrevisto)
					strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
				if (resultado > totalPrevisto)
					strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");
				if (resultado < totalPrevisto) {
					strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");
					strCorProjecao = "color=red";
				}
			    GregorianCalendar dataTermino = ariDao.getProjecaoDataTermino(arf.getItemEstrtIndResulIettr(),ari,totalPrevisto);
	%>
																	<font <%=strCorProjecao%>><%=strProjecao%></font>
																</td>
																<%
																if (totalPrevisto > 0){%>
																<td class="subtitulo"><%DateFormat month = new SimpleDateFormat("MMMMM");
																						String sMonth = month.format(dataTermino.getTime());
																						%><%=sMonth%>/<%=dataTermino.get(GregorianCalendar.YEAR)%></td>
																<%
																}%>
															</tr>
														</table>
														<%
			} else {
				if (totalRealizado == 0) {
	%>
														Não é possível realizar projeção sem informação de
														quantidades realizadas.<td class="subtitulo dateCel"> </td>
														<%			} else {
	%>
														<!-- Indicador não permite projeção -->
														<table width="100%" cellspacing="0" border="0">
															<tr>
																<td class="subtitulo">
																	<a
																		href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr()
																		.getCodIettir()%>, <%=ari.getCodAri()%>, 'N', '')">
																		<img src="../../images/relAcomp/icon_grafico.png"
																			border="0"> </a> 3
																</td>
																<td class="subtitulo">
																	Indicador não permite projeção
																</td>
																<td class="subtitulo dateCel"> </td>
															</tr>
														</table>
														<%
				}
			}
		} else if (qtdeRegistros == 1) {
			indicadorResultadoFachada = new IndicadorResultadoFachada();
			indicadorResultadoFachada.calculaSinalizacao(ari, arf);
	%>
														<table width="100%" cellspacing="0" border="0">
															<tr>
																<td align="center">
																	<img src="<%=_pathEcar%>/images/relAcomp/<%=indicadorResultadoFachada.getCorPeriodoCorrente() %>"
																			border="0">
																</td>
																<td align="center">
																	<img src="<%=_pathEcar%>/images/relAcomp/<%=indicadorResultadoFachada.getCorLinhaDeBase() %>"
																			border="0">
																</td>
															</tr>
														</table>
														<%	} else {
															indicadorResultadoFachada = new IndicadorResultadoFachada();
															indicadorResultadoFachada.calculaSinalizacao(ari, arf);
			//Passar no javascript "P" para comQtde para que no servlet só gere os dados previstos.
	%>
														<table width="100%" cellspacing="0" border="0">
															<tr>
																<td align="center">
																	<img src="<%=_pathEcar%>/images/relAcomp/<%=indicadorResultadoFachada.getCorPeriodoCorrente() %>"
																			border="0">
																</td>
																<td align="center">
																	<img src="<%=_pathEcar%>/images/relAcomp/<%=indicadorResultadoFachada.getCorLinhaDeBase() %>"
																			border="0">
																</td>
															</tr>
														</table>
														<%	}	%>
													</td>
												</tr>
												<tr>
													<td class="espacador"
														colspan="<%=8 + exerciciosAnteriores.size()%>">
														<img src="../../images/pixel.gif">
													</td>
												</tr>
												<%	}
	} else {	%>
												<tr>
													<td class="subtitulo"
														colspan="<%=8 + exerciciosAnteriores.size()%>">
														<b>Não existem indicadores cadastrados.</b>
													</td>
												</tr>
												<%
	}	%>
											</table>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
                   </div>
				</table>
<%
	 	//Visualição dos gráficos para as métricas do itens de estrutura filhos.
	 	
	List[] listas = acompReferenciaDao.getItensFilhosByAcompReferencia(ari.getAcompReferenciaAref(), ari.getItemEstruturaIett(),usuario);
	List arvore = listas[0];
	List selecionaveis = listas[1];
	
	Iterator itItem = arvore.iterator();
	
	int nivelPai = ari.getItemEstruturaIett().getNivelIett().intValue();
	
	if(itItem.hasNext()){	 // if - 1	
        int contFilho = 0;
 		int contArfFilhos = 0;
 		
		while(itItem.hasNext()){ // while - 1
			
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
				<%=item.getEstruturaEtt().getNomeEtt() %> - <a <%=linkVermelho%> href="javascript:mostrarEsconder(<%=contFilho%>, <%=acompRefItemFilho.getCodAri() %>,'grafico')"><%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, item.getEstruturaEtt())%></a>
			<%
			primeiroLink = true;
			blockDivName = "";
			if( acompRefItemFilho != null ) {
				blockDivName = "block" + acompRefItemFilho.getCodAri();
			} else {
				blockDivName = "blockUndefined";
			} 
			
			estiloDiv = "display: none;";
			if( primeiroLink ) {
				estiloDiv = "display: block;";
				primeiroLink = false;
			}
			%>         
			 <div id="div<%=contFilho%>grafico" style="display:none">
			 <br/><br/>
						<!-- começa a imprimir a linha da execução física -->
						<tr valign="top">
							<td colspan="2" width="100%">
								<div id="<%=blockDivName%>">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr valign="top">
											<td colspan="2">
												<table width="100%" cellpadding="0" cellspacing="0"
													border="0">
													<%	if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){ %>
													<tr>
														<td class="titulo">
															Indicadores de Resultado
														</td>
														<td class="titulo" align="center" colspan="2">
															Realizado no Mês
														</td>
														<td class="titulo" align="center">
															Situação
														</td>
														<td class="titulo" align="center">
															P/R
														</td>
														<%
				//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecao(itemEstrutura.getCodIett(), ari, tipoSelecao);
				//exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecaoResultados(item.getCodIett());
				exerciciosAnteriores = new ExercicioDao(request).getExerciciosValidos(item.getCodIett());
				Iterator itEx = exerciciosAnteriores.iterator();
				boolean primeiro = true;
				while(itEx.hasNext()) {
					ExercicioExe exercicio = (ExercicioExe) itEx.next();					
					if( (item.getDataInicioIett() != null && item.getDataTerminoIett() != null) || 
							acompRealFisicoDao.getQtdeRealizadoExercicioByIettAndExe(exercicio, item, acompRefItemFilho.getAcompReferenciaAref(), "") != 0  ||
							new ItemEstrtIndResulDao(request).getQtdPrevistoExercicio(acompRefItemFilho, tipoSelecao, item, exercicio, "") != 0 ) {
						if(primeiro){
							primeiro = false;
		%>
														<input type="hidden" name="primeiroMesExe"
															value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
														<input type="hidden" name="primeiroAnoExe"
															value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
														<% 			}	%>
														<td class="titulo_exercicio" align="center">
															<a
																href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=acompRefItemFilho.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;
														</td>
														<%		}
					} %>
														<td class="titulo" align="center">
															Total
														</td>
														<td class="titulo" align="center">
															Realizado/
															<br>
															Previsto(%)
														</td>
														<td class="titulo">
															<table width="100%">
																<tr>
																	<td colspan="3" class="titulo">
																		Projeção na Data Final 4
																	</td>
																</tr>
																<tr>
																	<td class="titulo" colspan="2">
																		Situação Prevista
																	</td>
																	<td class="titulo" align="right">
																		Projeção de Término
																	</td>
															</table>
														</td>
													</tr>
													<%	}
													totalColunas = 9;

													//indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(acompRefItemFilho, tipoSelecao, true);
													ItemEstrutura itemFilho = new ItemEstrutura(acompRefItemFilho.getItemEstruturaIett());
													indResultados = itemFilho.getRealizadosIndicadoresPorSituacao(tipoSelecao, _ari, configuracao, periodoSelecionado, true);
													
													
													%>
																								<input type="hidden" name="qtdeAcompRealFisico"
																									value="<%=indResultados.size()%>">
																								<%
													//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecao(itemEstrutura.getCodIett(),ari, tipoSelecao);
													//exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecaoResultados(item.getCodIett());
													exerciciosAnteriores = new ExercicioDao(request).getExerciciosValidos(item.getCodIett());
													
													if (indResultados != null && indResultados.size() > 0) {
														Iterator itIndResult = indResultados.iterator();

														String grupoIndicador = "Indicador de Resultado";
														boolean primeiro = true;

														while(itIndResult.hasNext()) {
															AcompRealFisicoArf arf = (AcompRealFisicoArf) itIndResult.next();
															if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null) {
																if( arf.getItemEstrtIndResulIettr().getSisAtributoSatb() != null
																	&& !grupoIndicador.equals(arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())) {
																	grupoIndicador = arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
													%>
																								<tr>
																									<td colspan="<%=8 + exerciciosAnteriores.size()%>">
																										&nbsp;
																									</td>
																								</tr>
																								<tr>
																									<td class="titulo"><%=grupoIndicador%></td>
																									<td class="titulo" align="center" colspan="2">
																										Realizado no Mês
																									</td>
																									<td class="titulo" align="center">
																										Situação
																									</td>
																									<td class="titulo" align="center">
																										P/R
																									</td>
																									<%
																	Iterator itEx = exerciciosAnteriores.iterator();
																	while (itEx.hasNext()) {
																		ExercicioExe exercicio = (ExercicioExe) itEx.next();
																			if(primeiro) {
																				primeiro = false;
													%>
																									<input type="hidden" name="primeiroMesExe"
																										value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
																									<input type="hidden" name="primeiroAnoExe"
																										value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
														<%					}	%>
																									<td class="titulo_exercicio" align="center">
																										<a
																											href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=acompRefItemFilho.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;
																									</td>
													<%					
																	}		%>
																									<td class="titulo" align="center">
																										Total
																									</td>
																									<td class="titulo" align="center">
																										Realizado/
																										<br>
																										Previsto(%)
																									</td>
																									<td class="titulo">
																										<table width="100%">
																											<tr>
																												<td colspan="3" class="titulo">
																													Projeção na Data Final 5
																												</td>
																											</tr>
																											<tr>
																												<td class="titulo" colspan="2">
																													Situação Prevista
																												</td>
																												<td class="titulo" align="right">
																													Projeção de Término
																												</td>
																										</table>
																									</td>
																								</tr>
																								<%
																} else if (arf.getItemEstrtIndResulIettr().getSisAtributoSatb() == null	&& !grupoIndicador.equals("")) {
													%>
																								<tr>
																									<td class="titulo"><%=grupoIndicador%></td>
																									<td class="titulo" align="center" colspan="2">
																										Realizado no Mês
																									</td>
																									<td class="titulo" align="center">
																										Situação
																									</td>
																									<td class="titulo" align="center">
																										P/R
																									</td>
																									<%				Iterator itEx = exerciciosAnteriores.iterator();
																	while (itEx.hasNext()) {
																		ExercicioExe exercicio = (ExercicioExe) itEx.next();
																			if (primeiro) {
																				primeiro = false;
													%>
																									<input type="hidden" name="primeiroMesExe"
																										value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
																									<input type="hidden" name="primeiroAnoExe"
																										value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
													<%						}	%>
																									<td class="titulo_exercicio" align="center">
																										<a
																											href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=acompRefItemFilho.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;
																									</td>
													<%					
																	}		%>
																									<td class="titulo" align="center">
																										Total
																									</td>
																									<td class="titulo" align="center">
																										Realizado/
																										<br>
																										Previsto(%)
																									</td>
																									<td class="titulo">
																										<table width="100%">
																											<tr>
																												<td colspan="3" class="titulo">
																													Projeção na Data Final 6
																												</td>
																											</tr>
																											<tr>
																												<td class="titulo" colspan="2">
																													Situação Prevista
																												</td>
																												<td class="titulo" align="right">
																													Projeção de Término
																												</td>
																										</table>
																									</td>
																								</tr>
																								<%				grupoIndicador = "";
																}
															}
															String tipoQtde = arf.getItemEstrtIndResulIettr().getIndTipoQtde();
													%>
																								<tr>
																									<td class="subtitulo" width="20%">
																										<table>
																											<tr>
																												<td class="subtitulo" width="3%">
																													<a
																														href="javascript:abrirPopUpGraficoRealizadoPorExercicio(<%=acompRefItemFilho.getCodAri()%>, 
																						<%=acompRefItemFilho.getAcompReferenciaAref().getExercicioExe().getCodExe()%>, <%=arf.getItemEstrtIndResulIettr().getCodIettir()%>)">
																														<img src="../../images/relAcomp/icon_grafico.png"
																															border="0"> </a> 6
																												</td>
																												<td class="subtitulo">
																													<a
																														href="javascript:abrirPopUpInfoIndicador(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>)"><%=arf.getItemEstrtIndResulIettr().getNomeIettir()%>&nbsp;-&nbsp;<%=new ItemEstrtIndResulDao(null).getUnidadeUsada(arf.getItemEstrtIndResulIettr())%></a>
																														3
																												</td>
																											</tr>
																										</table>
																									</td>
																									<td class="subtitulo" width="6%" colspan="2">
																										<table>
																											<tr>
																												<%
															String porLocal = arf.getItemEstrtIndResulIettr().getIndRealPorLocal();
															if (arf.getQtdRealizadaArf() != null) {
													%>
																												<td class="subtitulo" align="center" width="3%">
																													<%
																String qtdeRealizada = "";
																if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
																	qtdeRealizada = Pagina.trocaNullNumeroDecimal(arf.getQtdRealizadaArf());
																} else { //Valor --> 2 casas decimais
																	qtdeRealizada = Pagina.trocaNullMoeda(arf.getQtdRealizadaArf());
																}
													
																if ("S".equals(porLocal)) { %>
																		<a
																			href="javascript:abrirPopUpConsultaRealizadoPorLocal(form, '<%=arf.getCodArf()%>','<%=arf.getItemEstrtIndResulIettr().getNomeIettir()%>')"
																			title="Realizado por Local"> <%=qtdeRealizada%></a>
																			4
													<%			} else {	%>
																		<%=qtdeRealizada%>
													<%			}			%>
																		&nbsp;
																	</td>
													<%		} else {	%>
															<td class="subtitulo" align="center" width="3%">
																0&nbsp;
															</td>
													<%		}	%>
																											</tr>
																										</table>
																									</td>
																									<td class="subtitulo" align="center" width="7%">
																										&nbsp;
																										<%		if (arf.getSituacaoSit() != null)
																out.print(arf.getSituacaoSit().getDescricaoSit());
													%>
																									</td>
																									<td class="subtitulo" align="center" width="2%">
																										<table width="100%" cellpadding="0" cellspacing="0">
																											<tr>
																												<td class="subtitulo">
																													<b>P</b>
																												</td>
																											</tr>
																											<tr>
																												<td class="subtitulo">
																													<b>R</b>
																												</td>
																											</tr>
																										</table>
																									</td>
																									<%
															Iterator itValoresExAnt = exerciciosAnteriores.iterator();
															double totalRealizado = 0;
															double totalPrevisto = 0;
															List valoresR = new ArrayList();
															List valoresP = new ArrayList();

															ExercicioExe exeAref = acompRefItemFilho.getAcompReferenciaAref().getExercicioExe();
															while (itValoresExAnt.hasNext()) {
																ExercicioExe exercicioAnt = (ExercicioExe) itValoresExAnt.next();
																	
																double realizadoNoExercicio = 0;
														
																boolean exercicioValido = false;
																List valoresRealExerc = null;
																if (exercicioAnt.getDataFinalExe().before(exeAref.getDataFinalExe()) || exercicioAnt.equals(exeAref)) {
																	exercicioValido = true;
																}
														
																if ("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())) {
																	realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, arf.getItemEstrtIndResulIettr(),ari.getAcompReferenciaAref());
																} else {													
																	valoresRealExerc = acompRealFisicoDao.ObtemListaRealizadoExercicioByIettrAndExe(exercicioAnt,arf.getItemEstrtIndResulIettr(),ari.getAcompReferenciaAref());
																	realizadoNoExercicio = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresRealExerc);										
																}
														
																double previstoNoExercicio = new ItemEstrtIndResulDao(request).getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(), exercicioAnt);
																if ("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())) {
																	//não existe a lista de realizados para o tipo "S". O valor realizado foi buscado com sum no banco
																	valoresR.add(Double.valueOf(realizadoNoExercicio));						
																} else{
																	if (valoresRealExerc.size() > 0){
																		valoresR.add(Double.valueOf(realizadoNoExercicio));
																	} else //precisa adicionar null para efetuar o cálculo do último informado de maneira correta.
																		valoresR.add(null);
																}
																valoresP.add(Double.valueOf(previstoNoExercicio));
																totalColunas++;
													%>
																									<td class="subtitulo" align="center">
																										<table width="100%" cellpadding="0" cellspacing="0">
																											<tr>
																												<td class="subtitulo" align="right"> 
																													<%
																if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
																	out.print(Pagina.trocaNullNumeroDecimal(new Double(previstoNoExercicio)));
																} else { //Valor --> 2 casas decimais
																	out.print(Pagina.trocaNullMoeda(new Double(previstoNoExercicio)));
																}
													%>
																												</td>
																											</tr>
																											<tr>
																												<td class="subtitulo" align="right">
																													<%
     																		if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade 
																			out.print(Pagina.trocaNullNumeroDecimal(new Double(realizadoNoExercicio)));
																			} else { //Valor --> 2 casas decimais
	     																	out.print(Pagina.trocaNullMoeda(new Double(realizadoNoExercicio)));
																													}
													%>
																												</td>
																											</tr>
																										</table>
																									</td>
												<%														
															}//termina o while de itIndResult

														Collections.reverse(valoresR);
														Collections.reverse(valoresP);
												%>
																									<td class="subtitulo" align="center">
																										<table width="100%" cellpadding="0" cellspacing="0">
																											<tr>
																												<td class="subtitulo" align="right">
																													<%
														totalPrevisto = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresP).doubleValue();

														if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
															out.print(Pagina.trocaNullNumeroDecimal(new Double(totalPrevisto)));
														} else { //Valor --> 2 casas decimais
															out.print(Pagina.trocaNullMoeda(new Double(totalPrevisto)));
														}
													%>
																												</td>
																											</tr>
																											<tr>
																												<td class="subtitulo" align="right">
																													<%
														totalRealizado = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresR).doubleValue();

														if ("Q".equalsIgnoreCase(tipoQtde)) { //Quantidade
															out.print(Pagina.trocaNullNumeroDecimal(new Double(totalRealizado)));
														} else { //Valor --> 2 casas decimais
															out.print(Pagina.trocaNullMoeda(new Double(totalRealizado)));
														}
													%>
																												</td>
																											</tr>
																										</table>
																									</td>
																									<td class="subtitulo" align="center">
																										<table width="100%" cellpadding="0" cellspacing="0">
																											<tr>
																												<td class="subtitulo">
																													&nbsp;
																												</td>
																											</tr>
																											<tr>
																												<%	double realizadoPrevisto = 0;
														if (totalPrevisto > 0)
															realizadoPrevisto = ((totalRealizado / totalPrevisto) * 100);
													%>
																												<td class="subtitulo" align="center"><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double((realizadoPrevisto)))%></td>
																											</tr>
																										</table>
																									</td>
																									<td class="subtitulo">
																										<%
														int qtdeRegistros = acompRealFisicoDao.getQtdRegistrosRealizadoPeriodo(arf.getItemEstrtIndResulIettr(), arf.getItemEstruturaIett(), Long
															.valueOf(acompRefItemFilho.getAcompReferenciaAref().getMesAref()), Long.valueOf(acompRefItemFilho.getAcompReferenciaAref().getAnoAref()));
														if (qtdeRegistros > 1) {
															if (arf.getItemEstrtIndResulIettr().getIndProjecaoIettr().equals("S") && totalRealizado > 0) {
													%>
																										<table width="100%" cellspacing="0" border="0">
																											<tr>
																												<td class="subtitulo">
																													<a
																														href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr()
																														.getCodIettir()%>, <%=acompRefItemFilho.getCodAri()%>, 'S', '')">
																														<img src="../../images/relAcomp/icon_grafico.png"
																															border="0"> </a> 7
																												</td>
																												<td class="subtitulo">
																													<%
																double resultado = ariDao.calculoProjecao(arf.getItemEstrtIndResulIettr(),acompRefItemFilho);
																// Mantis 0010128 - Qtd prevista não é mais informado por exercício													
																// objeto exercicioPrevisto não está sendo usado.																													
																//ExercicioExe exercicioPrevisto = indResultDao.getMaiorExercicioIndicador(arf.getItemEstrtIndResulIettr());
																//double qtdePrevista = indResultDao.getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(),exercicioPrevisto);
																String strProjecao = "";
																String strCorProjecao = "color=#1C2265";
																if (resultado == totalPrevisto)
																	strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
																if (resultado > totalPrevisto)
																	strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");
																if (resultado < totalPrevisto) {
																	strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");
																	strCorProjecao = "color=red";
																}
																GregorianCalendar dataTermino = ariDao.getProjecaoDataTermino(arf.getItemEstrtIndResulIettr(),ari,totalPrevisto);
																%>
																																<font <%=strCorProjecao%>><%=strProjecao%></font>
																															</td>
																															<td class="subtitulo"><%	DateFormat month = new SimpleDateFormat("MMMMM");
																																						String sMonth = month.format(dataTermino.getTime());
																																						%><%=sMonth%>/<%=dataTermino.get(GregorianCalendar.YEAR)%></td>
																														</tr>
																										</table>
																										<%
															} else {
																if (totalRealizado == 0) {
													%>
																										Não é possível realizar projeção sem informação de
																										quantidades realizadas.<td class="subtitulo dateCel"> </td>
																										<%			} else {
													%>
																										<!-- Indicador não permite projeção -->
																										<table width="100%" cellspacing="0" border="0">
																											<tr>
																												<td class="subtitulo">
																													<a
																														href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr()
																														.getCodIettir()%>, <%=acompRefItemFilho.getCodAri()%>, 'N', '')">
																														<img src="../../images/relAcomp/icon_grafico.png"
																															border="0"> </a> 8
																												</td>
																												<td class="subtitulo">
																													Indicador não permite projeção
																												</td>
																												<td class="subtitulo dateCel"> </td>
																											</tr>
																										</table>
																										<%
																}
															}
														} else if (qtdeRegistros == 1) {
													%>
																										<table width="100%" cellspacing="0" border="0">
																											<tr>
																												<td class="subtitulo">
																													<a
																														href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr()
																														.getCodIettir()%>, <%=acompRefItemFilho.getCodAri()%>, 'N', '')">
																														<img src="../../images/relAcomp/icon_grafico.png"
																															border="0"> </a> 8
																												</td>
																												<td class="subtitulo">
																													<%		out.println(_msg.getMensagem("relAcompanhamento.graficoProjecao.quantidadeInsuficiente"));
													%>
																												</td>
																												<td class="subtitulo dateCel"> </td>
																											</tr>
																										</table>
																										<%	} else {
															//Passar no javascript "P" para comQtde para que no servlet só gere os dados previstos.
													%>
																										<table width="100%" cellspacing="0" border="0">
																											<tr>
																												<td class="subtitulo">
																													<a
																														href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr()
																														.getCodIettir()%>, <%=acompRefItemFilho.getCodAri()%>, 'N', '')">
																														<img src="../../images/relAcomp/icon_grafico.png"
																															border="0"> </a> 9
																												</td>
																												<td class="subtitulo">
																													<%		out.println(_msg.getMensagem("relAcompanhamento.graficoProjecao.semQuantidade"));
													%>
																												</td>
																												<td class="subtitulo dateCel"> </td>
																											</tr>
																										</table>
																										<%	}	%>
																									</td>
																								</tr>
																								<tr>
																									<td class="espacador"
																										colspan="<%=8 + exerciciosAnteriores.size()%>">
																										<img src="../../images/pixel.gif">
																									</td>
																								</tr>
																								<%	}
													} else {	%>
																								<tr>
																									<td class="subtitulo"
																										colspan="<%=8 + exerciciosAnteriores.size()%>">
																										<b>Não existem indicadores cadastrados.</b>
																									</td>
																								</tr>
																								<%
													}	
			}
			%>
																								</table>
																								</td>
																								</tr>
																								</table>
			</div>
			</td>
			</tr>
			</div>
			</div>
			</td>
			</tr>			
			
			<%
			++contFilho;
		} //final do while - 1
		
	} //final do if - 1		
													%>
			
<%	
   		//Final da visualização dos gráficos das métricas do itens filhos
%>
				
				<!-- termina de exibir os indicadores de resultado -->

			</form>
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
		</div>
	</body>
	<%@ include file="/../../include/estadoMenu.jsp"%>
	<%@ include file="../../include/mensagemAlert.jsp"%>
</html>