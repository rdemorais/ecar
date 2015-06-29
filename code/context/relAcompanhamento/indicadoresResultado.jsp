
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.util.Dominios" %>
<%@ page import="java.util.Collections" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.AcompReferenciaAref" %> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.pojo.AcompRealFisicoArf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.bean.IettArfBean" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %> 
<%@ page import="ecar.dao.AcompRealFisicoDao" %> 
<%@ page import="ecar.dao.ItemEstruturaDao" %> 
<%@ page import="ecar.dao.ExercicioDao" %> 
<%@ page import="ecar.dao.SisAtributoDao" %> 
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %> 
<%@ page import="ecar.bean.NomeImgsNivelPlanejamentoBean" %>

<%@ page import="comum.util.Pagina" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %> 

<%@ page import="java.util.Date" %>
<%@ page import="comum.util.*" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %> 



<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/datas.js"></script>
<script language="javascript" src="../js/numero.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>

<script>
function reload(){
	document.forms[0].action = "indicadoresResultado.jsp";
	document.forms[0].submit();
}
function trocaAba(link, codAri){
	document.forms[0].codAri.value = codAri;
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}
function abrirPopUpNivelPlanejamento(){
	abreJanela("popUpNivelPlanejamento.jsp", 500, 300, "nivelPlanejamento");
}
function abrirPopUpInfoIndicador(codIndicador){
	abreJanela("popUpInfoIndicador.jsp?codIndicador=" + codIndicador, 650, 400, "InfoIndicador");
}
function abrirPopUpDetalhesIndicadorResultado(codAri, codExercicio){
	abreJanela("popUpDetalhesIndicadorResultado.jsp?codAri=" + codAri + "&codExe=" + codExercicio, 700, 300, "InfoIndicadorDetalhe");
}
function abrirPopUpGraficoRealizadoPorExercicio(codAri, codExe, codIettir){
	abreJanela("popUpGraficoRealizadoPorExercicio.jsp?codAri=" + codAri + "&codExe=" + codExe + "&codIettir=" + codIettir, 600, 500);
}
function abrirPopUpGraficoPrevisao(codIndicador, codAri, comQtde, soPrevisao){
	abreJanela("popUpGraficoPrevisaoIndicadorResultado.jsp?comQtde=" + comQtde + "&soPrevisao=" + soPrevisao + "&mesIni=" + document.form.primeiroMesExe.value + "&anoIni=" + document.form.primeiroAnoExe.value + "&codIndicador=" + codIndicador + "&codAri=" + codAri + "&qtdeAcompRealFisico=" + document.form.qtdeAcompRealFisico.value, 750, 550);
}

function abrirPopUpConsultaRealizadoPorLocal(form, codARF, nomeIndicador) {
	var janela = abreJanela('popUpConsultaRealizadoFisicoPorLocal.jsp?codARF=' + codARF + '&nomeIndicador=' + escape(nomeIndicador), 600, 500, 'popUpConsultaRealizadoFisicoPorLocal');
	janela.focus();
}

function trocaAbaImpressao(link){
	document.forms[0].action = link;
	document.forms[0].clicouAba.value = "S";
	document.forms[0].submit();
}

function mostrarEsconder(numero) {
    var i = 0;
    var divs = document.getElementsByTagName('DIV');

    var divdavez = null;
    for (i = 0; i < divs.length; i++) {
		divdavez = divs[i];

		if( divdavez.id.substring(0, 5) == 'block' ) {
			divdavez.style.display = 'none';
		}
	}

	divdavez = document.getElementById('block'+numero);
	divdavez.style.display = 'block';
}

</script>

</head>

<body> <%
request.setAttribute("exibirAguarde", "true"); %>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%> 

<div id="conteudo">
	<%
try{	
	String strCodAri = Pagina.getParamStr(request, "codAri");
	String radConcluido = Pagina.getParamStr(request, "radConcluido");
	
	String tipoSelecao = Dominios.NAO_CONCLUIDOS;
	
	if(!"".equals(radConcluido)){
		tipoSelecao = radConcluido;
	}
	
	if(!"".equals(Pagina.getParamStr(request, "referencia")) && !"S".equals(Pagina.getParamStr(request, "linkControle"))){
		// Veio nova referência, selecionada na combo, deve trocar o Ari.
		strCodAri = Pagina.getParamStr(request, "referencia");
	}
	
	String niveisPlanejamento = Pagina.getParamStr(request, "niveisPlanejamento");
				
	List listNiveis = new ArrayList();
	SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
	String[] niveis = niveisPlanejamento.split(":");
	for(int n = 0; n < niveis.length; n++){
		String codNivel = niveis[n];
		if(!"".equals(codNivel)){
			listNiveis.add(sisAtributoDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
		}
	}
	
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) new AcompReferenciaItemDao(request).buscar(
						AcompReferenciaItemAri.class, Long.valueOf(strCodAri));

	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaParecerIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
	{
		response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
	AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
	//ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	ItemEstrtIndResulDao indResultDao = new ItemEstrtIndResulDao(request);
	%>

	<util:barraLinkTipoAcompanhamentoTag
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		chamarPagina="posicaoGeral.jsp"
	/> 

	<%@ include file="botoesAvancaRetrocede.jsp" %>

	<util:arvoreEstruturas 
		itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
		exibirLinks="false" 
		proximoNivel="false" 
		contextPath="<%=_pathEcar%>"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		codTipoAcompanhamentoSelecionado="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
	/> 

	<br>
	
	<util:barraLinksRelatorioAcompanhamento
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		funcaoSelecionada="REL_FISICO_IND_RESULTADO"
		primeiroIettClicado="<%=request.getParameter("primeiroIettClicado")%>"
		primeiroAriClicado="<%=request.getParameter("primeiroAriClicado")%>"
		caminho="<%=_pathEcar + "/relAcompanhamento/"%>"
		codTipoAcompanhamento="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>"
		gruposUsuario="<%=seguranca.getGruposAcesso() %>" 
	/> 
	
	<br>
	
	<form  name="form" method="post" >	
		
	<table border="0" width="100%">
		<tr>
			<td valign="bottom" class="texto" align="left" colspan="2"> 
				<b>Data do relatório:</b> <%=Data.parseDate(Data.getDataAtual())%><br>
				<b>Mês de referência: </b>
				<combo:ComboReferenciaTag
					nome="referencia"
					acompReferenciaItem="<%=acompReferenciaItem%>"
					scripts="onchange='reload()'"
				/>			
			</td>
		</tr>
	</table>
		
		<!-- Campos para manter a seleção em Posição Geral -->
		<input type="hidden" name="codTipoAcompanhamento" value="<%=Pagina.getParamStr(request, "codTipoAcompanhamento")%>">
		<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
		<!-- input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>" -->
		<input type="hidden" name="mesReferencia" value="<%=acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString()%>">
		<input type="hidden" name="niveisPlanejamento" value="<%=Pagina.getParamStr(request, "niveisPlanejamento")%>">
		<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
		<input type="hidden" name="primeiroIettClicado" value="<%=Pagina.getParamStr(request, "primeiroIettClicado")%>">
		<input type="hidden" name="primeiroAriClicado" value="<%=Pagina.getParamStr(request, "primeiroAriClicado")%>">
		<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
		<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>" >

		<!-- Campo necessário para botões de Avança/Retrocede -->
		<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
		<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
		<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
				

		<!-- Campos para manter a seleção em Posição Geral -->

		<!-- 
		<table class="barrabotoes" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
				<b>Indicadores de Resultado</b>
				</td>
			</tr>
		</table>
		-->
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador" colspan="2"><img src="../images/pixel.gif"></td></tr>
		<tr  class="linha_subtitulo" valign="bottom">
			<td width="40%"><br>Selecione uma das opções abaixo: <br> 
				<input type="radio" class="form_check_radio" name="radConcluido" value="<%=Dominios.TODOS%>" onclick="reload();" <%=Pagina.isChecked(tipoSelecao, Dominios.TODOS)%>> Todos 
				<input type="radio" class="form_check_radio" name="radConcluido" value="<%=Dominios.NAO_CONCLUIDOS%>" onclick="reload();" <%=Pagina.isChecked(tipoSelecao, Dominios.NAO_CONCLUIDOS)%>> Não Concluídos 
				<input type="radio" class="form_check_radio" name="radConcluido" value="<%=Dominios.CONCLUIDOS%>" onclick="reload();" <%=Pagina.isChecked(tipoSelecao, Dominios.CONCLUIDOS)%>> Concluídos
			</td>
			<td><br>&nbsp;<br>
			Selecionar <%=configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%><a href="javascript:abrirPopUpNivelPlanejamento()" title="Selecionar <%=configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getDescricaoSga()%>"><img src="../images/relAcomp/icon_nivelplanejamento.png" border="0"></a>
			</td>
		</tr>
		<tr><td class="espacador" colspan="2"><img src="../images/pixel.gif"></td></tr>
		<%
		ItemEstruturaDao ieDao = new ItemEstruturaDao(request);
		int nivelBase = acompReferenciaItem.getItemEstruturaIett().getNivelIett().intValue();		
		List arvoreItens = new ArrayList();
		/*
		arvoreItens.add(acompReferenciaItem.getItemEstruturaIett());
		if(listNiveis.size() > 0){
			arvoreItens.addAll(ieDao.getDescendentesPorNivelPlanejamento(acompReferenciaItem.getItemEstruturaIett(), listNiveis));
		}
		else {
			arvoreItens.addAll(ieDao.getDescendentes(acompReferenciaItem.getItemEstruturaIett(), true));
		}
		*/

		arvoreItens = acompRealFisicoDao.getArfsByIettAndTipoAcomp(acompReferenciaItem.getItemEstruturaIett(), acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa(), configura, listNiveis, Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()), Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()));

		AcompReferenciaItemDao acompRefItemDao = new AcompReferenciaItemDao(request);						
		
/*------------------------------------------------------------------------------------------*/
/* O trecho abaixo serve para retirar da lista de itens todos os itens filhos que não possuem link */
/* Referente ao bug 3719 */
/* Vai daqui até... */
		boolean remover = true;
		if(arvoreItens != null && arvoreItens.size() > 1){ //Se arvoreItens.size()==1 é certo que não possui itens filhos para remover.
			while(remover){
				remover = false;
				int maiorNivelSemLink = 0;
				Iterator itTodosOsItens = arvoreItens.iterator();
				
				while(itTodosOsItens.hasNext()){
					//ItemEstruturaIett iett = (ItemEstruturaIett) itTodosOsItens.next();
					IettArfBean iaBean = (IettArfBean) itTodosOsItens.next();
					ItemEstruturaIett iett = iaBean.getItem();
					
					int nivel = iett.getNivelIett().intValue();
					if(nivel > maiorNivelSemLink){
						AcompReferenciaItemAri linkIett = acompRefItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaItem.getAcompReferenciaAref(), iett);
						
						List listArf = new ArrayList();
						if(linkIett != null) {
							listArf = acompRealFisicoDao.buscarPorIett(
											linkIett.getItemEstruturaIett().getCodIett(),
											Long.valueOf(linkIett.getAcompReferenciaAref().getMesAref()),
											Long.valueOf(linkIett.getAcompReferenciaAref().getAnoAref()));
						}
						if(linkIett == null ||(linkIett != null && listArf.size() < 1)){
							maiorNivelSemLink = nivel;
						}
					}
				}
	
				itTodosOsItens = arvoreItens.iterator();
				while(itTodosOsItens.hasNext()){
					//ItemEstruturaIett iett = (ItemEstruturaIett) itTodosOsItens.next();
					IettArfBean iaBean = (IettArfBean) itTodosOsItens.next();
					ItemEstruturaIett iett = iaBean.getItem();
					
					int nivel = iett.getNivelIett().intValue();
					if(nivel == maiorNivelSemLink){
						AcompReferenciaItemAri linkIett = acompRefItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaItem.getAcompReferenciaAref(), iett);

						List listArf = new ArrayList();
						if(linkIett != null) {
							listArf = acompRealFisicoDao.buscarPorIett(
											linkIett.getItemEstruturaIett().getCodIett(),
											Long.valueOf(linkIett.getAcompReferenciaAref().getMesAref()),
											Long.valueOf(linkIett.getAcompReferenciaAref().getAnoAref()));
						}

						if(linkIett == null || (linkIett != null && listArf.size() < 1)){
						
							boolean possuiFilhosComLink = false;
							Iterator itDescendentes = ieDao.getDescendentes(iett, true).iterator();
							while(itDescendentes.hasNext()){
								ItemEstruturaIett iettFilho = (ItemEstruturaIett) itDescendentes.next();
	
								AcompReferenciaItemAri linkIettFilho = acompRefItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaItem.getAcompReferenciaAref(), iettFilho);

								List listArfFilho = new ArrayList();
								if(linkIettFilho != null) {
									listArfFilho = acompRealFisicoDao.buscarPorIett(
													linkIettFilho.getItemEstruturaIett().getCodIett(),
													Long.valueOf(linkIettFilho.getAcompReferenciaAref().getMesAref()),
													Long.valueOf(linkIettFilho.getAcompReferenciaAref().getAnoAref()));
								}
								
								if(linkIettFilho != null && listArfFilho.size() > 0){
									possuiFilhosComLink = true;
									break;
								}
								
							}
	
							if(!possuiFilhosComLink){
								itTodosOsItens.remove();
								remover = true;
							}
						}
					}
				}
			}
		}
/*  ...aqui.*/
/*------------------------------------------------------------------------------------------*/
		boolean primeiroLink = true;
		Iterator it = arvoreItens.iterator();
		while(it.hasNext()){
			//ItemEstruturaIett item = (ItemEstruturaIett) it.next();
			IettArfBean iaBean = (IettArfBean) it.next();
			ItemEstruturaIett item = iaBean.getItem();

			int nivelItem = item.getNivelIett().intValue();
			
			String classVermelho = "";
			AcompReferenciaItemAri ariLink = acompRefItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaItem.getAcompReferenciaAref(), item);
			%>
			<tr class="linha_subtitulo2" valign="top">
				<td  colspan="2" width="100%">
				<table>
					<tr>
					<td>
						&nbsp;
						<%
						for(int i = nivelBase; i< nivelItem;i++){
							%><img src="../images/pixel.gif" width="22" height="9"><%
						}
						%>	
					</td>
					<td>			
						<img src="../images/icon_bullet_seta.png">
					</td>
					<td>
						<%
						List listArfAri = new ArrayList();
						if(ariLink != null) {
							listArfAri = acompRealFisicoDao.buscarPorIett(
											ariLink.getItemEstruturaIett().getCodIett(),
											Long.valueOf(ariLink.getAcompReferenciaAref().getMesAref()),
											Long.valueOf(ariLink.getAcompReferenciaAref().getAnoAref()));
						}

						// Verifica se o item possui um ari no periodo em questão.
						if(ariLink != null && listArfAri.size() > 0){
							/* testar Ari caso existam quantidades não informadas */
							List qtdNaoInformada = acompRefItemDao.getAcompRealFisicoArfsComQtdNaoInformada(ariLink);
							/* caso tenha quantidades não informadas mostrar link em vermelho */
							if(qtdNaoInformada.size() > 0)
								classVermelho = "class='link_vermelho'"; %>
							<a href="javascript:mostrarEsconder(<%=ariLink.getCodAri()%>);">
							<%
						}
						%>			
						<%=item.getEstruturaEtt().getNomeEtt()%>: <b><%=item.getNomeIett()%></b>
						<%
						if(ariLink != null){
							%>
							</a>
							<%
						}
						%>
					</td>
					<td nowrap> <%
					Iterator itNiveis = ieDao.getNomeImgsNivelPlanejamentoItemAgrupado(item).iterator();
					while( itNiveis.hasNext() ){
						NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean)itNiveis.next();
						out.print(imagemNivelPlanejamento.geraHtmlImg(request));
					} %>
					</td>					
					</tr>
				</table>
				</td>
			</tr>
		</table> <%
		
		// Por Rogério (16/04/2007). Mantis #7905
		// Corrigindo falha na visualização, quando não há link para subprodutos.
		
		String blockDivName = "";
		if( ariLink != null ) {
			blockDivName = "block" + ariLink.getCodAri();
		} else {
			blockDivName = "blockUndefined";
		} 
		
		String estiloDiv = "display: none;";
		if( primeiroLink ) {
			estiloDiv = "display: block;";
			primeiroLink = false;
		} %>
		
		<div id="<%=blockDivName%>" style="<%=estiloDiv%>" >		
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td colspan="2">
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
						
			<%
				if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() == null){
			%>
						<tr>
							<td class="titulo">Indicadores de Resultado</td>
							<td class="titulo" align="center" colspan="2">Realizado no Mês</td>
							<td class="titulo" align="center">Situação</td>
							<td class="titulo" align="center">P/R</td>
							<%
							//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosAnteriores(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
							//exerciciosAnteriores.add(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
							List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecao(item.getCodIett());
							Iterator itEx = exerciciosAnteriores.iterator();
							boolean primeiro = true;
							while(itEx.hasNext()){
								ExercicioExe exercicio = (ExercicioExe) itEx.next();
								
								if(primeiro){
									primeiro = false;
								%>
									<input type="hidden" name="primeiroMesExe" value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
									<input type="hidden" name="primeiroAnoExe" value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
								<%
								}
								%><td class="titulo_exercicio" align="center"><a href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=acompReferenciaItem.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;</td><%
							}
							%>
							<td class="titulo" align="center">Total</td>
							<td class="titulo" align="center">Realizado/<br>Previsto(%)</td>							
							<td class="titulo">
								<table width="100%">
								<tr><td colspan="3" class="titulo">Projeção na Data Final</td></tr>
								<tr><td class="titulo" colspan="2">Situação Prevista</td><td class="titulo" align="right">%</td>
								</table>
							</td>
						</tr>
						<%
				}
						int totalColunas = 9;
						
						List indResultados;
						if( !item.equals(acompReferenciaItem.getItemEstruturaIett()) && ariLink != null ) {
							acompReferenciaItem = ariLink;
						} 
						
						indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(acompReferenciaItem ,tipoSelecao, true);
												
						//List indResultados = acompRealFisicoDao.getIndResulByIett(item, tipoSelecao);
						
						%>
						<input type="hidden" name="qtdeAcompRealFisico" value="<%=indResultados.size()%>">
						<%

						List exerciciosAnteriores = new ExercicioDao(request).getExerciciosProjecao(item.getCodIett());
						if(indResultados != null && indResultados.size() > 0){
							Iterator itIndResult = indResultados.iterator();
							
							String grupoIndicador = "Indicador de Resultado";
							boolean primeiro = true;
							
							while(itIndResult.hasNext()){
								AcompRealFisicoArf arf = (AcompRealFisicoArf) itIndResult.next();
								
								if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
									if(arf.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
										grupoIndicador = arf.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
									%>
										<tr>
											<td colspan="<%=8 + exerciciosAnteriores.size()%>">&nbsp;</td>
										</tr>
										<tr>
											<td class="titulo"><%=grupoIndicador%></td>
											<td class="titulo" align="center" colspan="2">Realizado no Mês</td>
											<td class="titulo" align="center">Situação</td>
											<td class="titulo" align="center">P/R</td>
											<%
											//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosAnteriores(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
											//exerciciosAnteriores.add(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
											Iterator itEx = exerciciosAnteriores.iterator();
											while(itEx.hasNext()){
												ExercicioExe exercicio = (ExercicioExe) itEx.next();
												
												if(primeiro){
													primeiro = false;
												%>
													<input type="hidden" name="primeiroMesExe" value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
													<input type="hidden" name="primeiroAnoExe" value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
												<%
												}
												%><td class="titulo_exercicio" align="center"><a href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=acompReferenciaItem.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;</td><%
											}
											%>
											<td class="titulo" align="center">Total</td>
											<td class="titulo" align="center">Realizado/<br>Previsto(%)</td>							
											<td class="titulo">
												<table width="100%">
												<tr><td colspan="3" class="titulo">Projeção na Data Final</td></tr>
												<tr><td class="titulo" colspan="2">Situação Prevista</td><td class="titulo" align="right">%</td>
												</table>
											</td>
										</tr>
									<%
									}
									else if(arf.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)){
									%>
										<tr>
											<td class="titulo"><%=grupoIndicador%></td>
											<td class="titulo" align="center" colspan="2">Realizado no Mês</td>
											<td class="titulo" align="center">Situação</td>
											<td class="titulo" align="center">P/R</td>
											<%
											//List exerciciosAnteriores = new ExercicioDao(request).getExerciciosAnteriores(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
											//exerciciosAnteriores.add(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe());
											Iterator itEx = exerciciosAnteriores.iterator();
											while(itEx.hasNext()){
												ExercicioExe exercicio = (ExercicioExe) itEx.next();
												
												if(primeiro){
													primeiro = false;
												%>
													<input type="hidden" name="primeiroMesExe" value="<%=String.valueOf(Data.getMes(exercicio.getDataInicialExe()) + 1)%>">
													<input type="hidden" name="primeiroAnoExe" value="<%=String.valueOf(Data.getAno(exercicio.getDataInicialExe()))%>">
												<%
												}
												%><td class="titulo_exercicio" align="center"><a href="javascript:abrirPopUpDetalhesIndicadorResultado(<%=acompReferenciaItem.getCodAri()%>,<%=exercicio.getCodExe()%>)"><%=exercicio.getDescricaoExe()%></a>&nbsp;</td><%
											}
											%>
											<td class="titulo" align="center">Total</td>
											<td class="titulo" align="center">Realizado/<br>Previsto(%)</td>							
											<td class="titulo">
												<table width="100%">
												<tr><td colspan="3" class="titulo">Projeção na Data Final</td></tr>
												<tr><td class="titulo" colspan="2">Situação Prevista</td><td class="titulo" align="right">%</td>
												</table>
											</td>
										</tr>
									<%
										grupoIndicador = "";
									}
								}
								
								String tipoQtde = arf.getItemEstrtIndResulIettr().getIndTipoQtde();
						%>
								<tr>
									<td class="subtitulo" width="20%">
										<table>
											<tr>
												<td class="subtitulo" width="3%" ><a href="javascript:abrirPopUpGraficoRealizadoPorExercicio(<%=acompReferenciaItem.getCodAri()%>, <%=acompReferenciaItem.getAcompReferenciaAref().getExercicioExe().getCodExe()%>, <%=arf.getItemEstrtIndResulIettr().getCodIettir()%>)"><img src="../images/relAcomp/icon_grafico.png" border="0"></a></td>
												<td class="subtitulo"><a href="javascript:abrirPopUpInfoIndicador(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>)"><%=arf.getItemEstrtIndResulIettr().getNomeIettir()%></a></td>
											</tr>
										</table>
									</td>
									<td class="subtitulo" width="6%" colspan="2">
										<table>
											<tr>
											<%
											String porLocal = arf.getItemEstrtIndResulIettr().getIndRealPorLocal();
											
											if(arf.getQtdRealizadaArf() != null){
											%>
												<td class="subtitulo" align="center" width="3%">
													<%
													String qtdeRealizada = "";
													
													if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
														qtdeRealizada = Pagina.trocaNullNumeroSemDecimal(arf.getQtdRealizadaArf());
													}
													else { //Valor --> 2 casas decimais
														qtdeRealizada = Pagina.trocaNullMoeda(arf.getQtdRealizadaArf());
													}
													
													if("S".equals(porLocal)) {
													%>
														<a href="javascript:abrirPopUpConsultaRealizadoPorLocal(form, '<%=arf.getCodArf()%>','<%=arf.getItemEstrtIndResulIettr().getNomeIettir()%>')" title="Realizado por Local">
															<%=qtdeRealizada%>
														</a>
													<%
													} else {
													%>
														<%=qtdeRealizada%>
													<%
													}
													%>
													&nbsp;<%=new ItemEstrtIndResulDao(null).getUnidadeUsada(arf.getItemEstrtIndResulIettr())%>
												</td>
											<%
											}
											else{
											%>
												<td class="subtitulo" align="center" width="3%">
													0
													&nbsp;
													<%=new ItemEstrtIndResulDao(null).getUnidadeUsada(arf.getItemEstrtIndResulIettr())%>
												</td>
											<%
											}
											%>
											</tr>
										</table>
									</td>
									<td class="subtitulo" align="center" width="7%">&nbsp;<%if(arf.getSituacaoSit()!=null) out.print(arf.getSituacaoSit().getDescricaoSit());%></td>
									<td class="subtitulo" align="center" width="2%">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr >
											<td class="subtitulo"><b>P</b></td>
											</tr>
									 		<tr >
											<td class="subtitulo"><b>R</b></td>
											</tr>
										</table>
									</td>
									<%
									Iterator itValoresExAnt = exerciciosAnteriores.iterator();
									double totalRealizado = 0;
									double totalPrevisto = 0;
									List valoresR = new ArrayList();
									List valoresP = new ArrayList();

									ExercicioExe exeAref = acompReferenciaItem.getAcompReferenciaAref().getExercicioExe();
									while(itValoresExAnt.hasNext()){
										ExercicioExe exercicioAnt = (ExercicioExe) itValoresExAnt.next();
										
										double realizadoNoExercicio = 0;

										boolean exercicioValido = false;
										if(exercicioAnt.getDataFinalExe().before(exeAref.getDataFinalExe()) || exercicioAnt.equals(exeAref)){
											exercicioValido = true;
										}
											
										/*
										
										Trecho comentado por Aleixo (17/04/2007)
										
										if(exercicioAnt.equals(acompReferenciaItem.getAcompReferenciaAref().getExercicioExe())){
											// Se o exercício em questão é mesmo exercicio do periodo de referência e o indicador for acumulável
											// soma todas as quantidades até o mes/ano do periodo
											if("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
												AcompReferenciaAref aref = acompReferenciaItem.getAcompReferenciaAref();
												int mesRef = 0;
												int anoRef = 0;
												if(aref.getMesAref() != null){
													mesRef = Integer.valueOf(aref.getMesAref()).intValue();
												}
												if(aref.getAnoAref() != null){
													anoRef = Integer.valueOf(aref.getAnoAref()).intValue();
												}
												realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, arf.getItemEstrtIndResulIettr(), mesRef, anoRef);																					
											} else {
												//se não for acumulável o realizado no exercicio é o realizado no periodo
												realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicioAnt, arf.getItemEstrtIndResulIettr());
											}
										} else {
											if("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
												//realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, arf.getItemEstrtIndResulIettr());
												realizadoNoExercicio = acompRealFisicoDao.getRealizadoExercicioByIettrAndExe(exercicioAnt, exercicioAnt, arf.getItemEstrtIndResulIettr());
											} else {
												realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicioAnt, arf.getItemEstrtIndResulIettr());
											}
										} */
										
										
										if("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
											realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, arf.getItemEstrtIndResulIettr(), acompReferenciaItem.getAcompReferenciaAref());
										} else {
											realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicioAnt, arf.getItemEstrtIndResulIettr(), acompReferenciaItem.getAcompReferenciaAref());																						
										}
										
										
										double previstoNoExercicio = new ItemEstrtIndResulDao(request).getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(), exercicioAnt);
										if(arf.getItemEstrtIndResulIettr() != null){
											if("S".equals(arf.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
												totalRealizado += realizadoNoExercicio;
												totalPrevisto += previstoNoExercicio;
											}else{
												totalRealizado = realizadoNoExercicio;
												totalPrevisto = previstoNoExercicio;											
											}	
										}

										valoresR.add(Double.valueOf(realizadoNoExercicio));
										valoresP.add(Double.valueOf(previstoNoExercicio));
										
										totalColunas++;

										%>
										<td class="subtitulo" align="center">
											<table width="100%" cellpadding="0" cellspacing="0">
												<tr>
												<td class="subtitulo" align="right">
													<%
													if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
														out.print(Pagina.trocaNullNumeroSemDecimal(new Double(previstoNoExercicio)));
														
													}
													else { //Valor --> 2 casas decimais
														out.print(Pagina.trocaNullMoeda(new Double(previstoNoExercicio)));
													}
													%>
												</td>
												
												</tr>
												<tr>
												<td class="subtitulo" align="right">
													<%
													if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
														out.print(Pagina.trocaNullNumeroSemDecimal(new Double(realizadoNoExercicio)));
														
													}
													else { //Valor --> 2 casas decimais
														out.print(Pagina.trocaNullMoeda(new Double(realizadoNoExercicio)));
													}
													%>
												</td>
												</tr>
											</table>
										</td>										
										<%
									}
									
									Collections.reverse(valoresR);
									Collections.reverse(valoresP);
									%>
									<td class="subtitulo" align="center">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr >
												<td class="subtitulo" align="right"> 
													<%
													totalPrevisto = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresP).doubleValue();

													if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
														out.print(Pagina.trocaNullNumeroSemDecimal(new Double(totalPrevisto)));
														
													}
													else { //Valor --> 2 casas decimais
														out.print(Pagina.trocaNullMoeda(new Double(totalPrevisto)));
													}
													%>
												</td>
												
											</tr>
											<tr>
												<td class="subtitulo" align="right">
													<%
													totalRealizado = acompRealFisicoDao.getSomaValoresArfs(arf.getItemEstrtIndResulIettr(), valoresR).doubleValue();
													
													if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
														out.print(Pagina.trocaNullNumeroSemDecimal(new Double(totalRealizado)));
														
													}
													else { //Valor --> 2 casas decimais
														out.print(Pagina.trocaNullMoeda(new Double(totalRealizado)));
													}
													%>
												</td>
											
											</tr>
										</table>
									</td>
									<td class="subtitulo" align="center">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr >
											<td class="subtitulo">&nbsp;</td>
											</tr>
											<tr >
											<%
											double realizadoPrevisto = 0;
											if(totalPrevisto > 0)
												realizadoPrevisto = ((totalRealizado/totalPrevisto) * 100);
											%>
											<td class="subtitulo" align="center"><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double((realizadoPrevisto)))%></td>
											</tr>
										</table>
									</td>
									<td class="subtitulo">
										<%
										int qtdeRegistros = acompRealFisicoDao.getQtdRegistrosRealizadoPeriodo(arf.getItemEstrtIndResulIettr(), arf.getItemEstruturaIett(), Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()), Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()));

										if (qtdeRegistros > 1){
											if(arf.getItemEstrtIndResulIettr().getIndProjecaoIettr().equals("S") && totalRealizado > 0){
												%>
												<table width="100%" cellspacing="0" border="0">
												<tr>
													<td class="subtitulo"><a href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>, <%=acompReferenciaItem.getCodAri()%>, 'S', '')"><img src="../images/relAcomp/icon_grafico.png" border="0"></a></td>
													<td class="subtitulo">
													<%
													double resultado = acompRefItemDao.calculoProjecao(arf.getItemEstrtIndResulIettr(), acompReferenciaItem);
													ExercicioExe exercicioPrevisto = indResultDao.getMaiorExercicioIndicador(arf.getItemEstrtIndResulIettr());
													double qtdePrevista = indResultDao.getQtdPrevistoExercicio(arf.getItemEstrtIndResulIettr(), exercicioPrevisto);
													String strProjecao = "";
													String strCorProjecao = "color=#1C2265";
													if(resultado == qtdePrevista)
														strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
													if(resultado > qtdePrevista)
														strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");												
													if(resultado < qtdePrevista){
														strProjecao = _msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");																									
														strCorProjecao = "color=red"; 
													}
													Double porcentagem = new Double((resultado/qtdePrevista) * 100);
													%>
													<font <%=strCorProjecao%>><%=strProjecao%></font>
													</td>
													<td class="subtitulo"><%=Pagina.trocaNullNumeroDecimalSemMilhar(porcentagem)%></td>
												</tr>	
												</table>																		
												<%
											} else {
												if(totalRealizado == 0){
													%>Não é possível realizar projeção sem informação de quantidades realizadas.<%										
												} else {
													%>
													<!-- Indicador não permite projeção -->
													<table width="100%" cellspacing="0" border="0">
														<tr>
															<td class="subtitulo">
																<a href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>, <%=acompReferenciaItem.getCodAri()%>, 'N', '')"><img src="../images/relAcomp/icon_grafico.png" border="0"></a>
															</td>
															<td class="subtitulo">
															Indicador não permite projeção
															</td>
														</tr>
													</table>
													<%
												}
											}
										}
										else if(qtdeRegistros == 1) {
										%>
											<table width="100%" cellspacing="0" border="0">
											<tr>
												<td class="subtitulo"><a href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>, <%=acompReferenciaItem.getCodAri()%>, 'N', '')"><img src="../images/relAcomp/icon_grafico.png" border="0"></a></td>
												<td class="subtitulo">
											<%
												out.println(_msg.getMensagem("relAcompanhamento.graficoProjecao.quantidadeInsuficiente"));
											%>
												</td>
											</tr>
											</table>
										<%
										}
										else {
											
											//Passar no javascript "P" para comQtde para que no servlet 
											//só gere os dados previstos.
											%>
											<table width="100%" cellspacing="0" border="0">
											<tr>
												<td class="subtitulo"><a href="javascript:abrirPopUpGraficoPrevisao(<%=arf.getItemEstrtIndResulIettr().getCodIettir()%>, <%=acompReferenciaItem.getCodAri()%>, 'N', 'S')"><img src="../images/relAcomp/icon_grafico.png" border="0"></a></td>
												<td class="subtitulo">
											<%
												out.println(_msg.getMensagem("relAcompanhamento.graficoProjecao.semQuantidade"));
											%>
											</td>
										</tr>
										</table>
										<%
										}
										%>
									</td>
								</tr>
								<tr><td class="espacador" colspan="<%=8 + exerciciosAnteriores.size()%>"><img src="../images/pixel.gif"></td></tr>		
								<%
							}
						} else {
							%><tr>
								<td class="subtitulo" colspan="<%=8 + exerciciosAnteriores.size()%>"><b>Não existem indicadores cadastrados.</b></td>
							</tr><%
						}
						%>
						</table>
						</td>
					</tr>			
		</table>
		</div> <%
		
		}
		%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador" colspan="2"><img src="../images/pixel.gif"></td></tr>		
	</table>		

	</form>	
</div>
<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>

</html>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>

