<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.pojo.ConfigMailCfgm"%>
<%@ page import="ecar.dao.ConfigMailCfgmDAO"%>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.pojo.AcompRefLimitesArl"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>

<%@ page import="comum.util.Data"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="java.util.Collections"%>
<%@ page import="comum.util.Util"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.bean.OrdenacaoDataTpfa"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>

<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoRegAcompanhamento.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript">

function aoClicarConsultar(form, codAcomp){
	form.codAcomp.value = codAcomp;
	form.hidAcao.value = 'alterar';
	document.form.action = "listaItens.jsp";
	document.form.submit();
}

function aoClicarLiberar(form, codAcomp, nomeAcomp, avisarEmail){
	if(confirm("Deseja Liberar o Acompanhamento " + nomeAcomp + "?")){
		form.liberarAcomp.value = codAcomp;
		if(avisarEmail == 'S'){
			if(confirm("<%=_msg.getMensagem("periodoReferencia.elaboracao.liberar.autorizarEmail.todos")%>")){
				form.enviarEmailParaTodos.value = "S";
			}
			else {
				form.enviarEmailParaTodos.value = "N";
			}
		}
	
		form.action = "lista.jsp";
		form.submit();
	}
}

function aoClicarExibir(form){
	form.action = "lista.jsp";
	form.submit();
}


</script>

	</head>

	<%
	request.setAttribute("exibirAguarde", "true");
	%>
	<%@ include file="/cabecalho.jsp"%>
	<%@ include file="/divmenu.jsp"%>
	<%
				String codTipoAcompanhamento = Pagina.getParamStr(request,
				"codTipoAcompanhamento");
				String codTipoRefAcompanhamento = Pagina.getParamStr(request,"codTipoRefAcompanhamento");
				

		// controle para mostrar como default o primeiro tipo de acompanhamento das abas selecionado
		if ("".equals(codTipoAcompanhamento)) {
			TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
			List listTa = taDao
			.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();

			if (!listTa.isEmpty()) {
				codTipoAcompanhamento = ((TipoAcompanhamentoTa) listTa
				.get(0)).getCodTa().toString();
			}
		}
		//Filtro de pesquisa
		String radAberto = Pagina.getParamStr(request, "radAberto");
		
		// Se for a primeira vez que abre a pagina
		if (radAberto.equals("") && codTipoRefAcompanhamento.equals("")) {
			radAberto = "A";	
			codTipoRefAcompanhamento = "A";
		// Se for mudar o tipo de acompanhamento	
		} else if (radAberto.equals("") && !codTipoRefAcompanhamento.equals("")){
			radAberto = codTipoRefAcompanhamento;	
		}	
	%>
	<body>
		<div id="conteudo">
			&nbsp;

			<%@ include file="/titulo_tela.jsp"%>

			<form name="form" method="post" action="">

				<div style="text-align: right"> 
					Exibir:&nbsp;
					<input type="radio" class="form_check_radio" name="radAberto" id="radAbertoA" value="A" <%=Pagina.isChecked(radAberto, "A")%> onclick="aoClicarExibir(form)">
						<label for="radAbertoA"> Abertos</label>
					<input type="radio" class="form_check_radio" name="radAberto" id="radAbertoE" value="E"<%=Pagina.isChecked(radAberto, "E")%> onclick="aoClicarExibir(form)">
						<label for="radAbertoE">Encerrados</label>
					<input type="radio" class="form_check_radio" name="radAberto" id="radAbertoT" value="T" <%=Pagina.isChecked(radAberto, "T")%> onclick="aoClicarExibir(form)">
						<label for="radAbertoT">Todos</label>
				</div>
				<%
				if ("".equals(codTipoAcompanhamento)) {
				%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="linha_subtitulo" align="left">
						<td>
							<%=_msg.getMensagem("tipoAcompanhamento.ativo.comAref.nenhumRegistro")%>
						</td>
					</tr>
				</table>
				
				
				

				<%
				
				} else {
				%>
				<util:barraLinkTipoAcompanhamentoTag
					codTipoAcompanhamentoSelecionado="<%=codTipoAcompanhamento%>"
					chamarPagina="lista.jsp"
					codTipoRefAcompanhamentoSelecionado= "<%=radAberto%>"/>

				<div id="subconteudo">
					<input type="hidden" name="codTipoAcompanhamento"
						value="<%=codTipoAcompanhamento%>">
					<input type="hidden" name="codTipoRefAcompanhamento" value="<%=radAberto%>">	
					<input type="hidden" name="hidAcao" value="">
					<input type="hidden" name="codAcomp" value="">
					
					<input type="hidden" name="nuPagina" value="1">

					<%
					
								try {
								//ValidaPermissao validaPermissao = new ValidaPermissao();
								AcompReferenciaDao acompDao = new AcompReferenciaDao(null);
								AcompReferenciaItemDao acompRefItemDao = new AcompReferenciaItemDao(
								request);
							
								TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(
								request);

								ConfigMailCfgm cfgMailLiberar = (ConfigMailCfgm) new ConfigMailCfgmDAO(
								request).buscar(ConfigMailCfgm.class,
								Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO); //Liberar Acompanhamento é cod=20
								String perguntarMail = "N";
								String enviarMailTodos = "N";
								if ("S".equals(cfgMailLiberar.getAtivoCfgm())
								&& !"S".equals(cfgMailLiberar
								.getIndEnvioObrigatorio())) {
							perguntarMail = "S";
								} else if ("S".equals(cfgMailLiberar.getAtivoCfgm())
								&& "S".equals(cfgMailLiberar
								.getIndEnvioObrigatorio())) {
							enviarMailTodos = "S";
								}

								List lAcomp = null;//
								
								if (radAberto.equalsIgnoreCase("A")) {
									lAcomp = acompDao.getListAcompReferenciaByTipoAcompanhamentoAbertos(Long.valueOf(codTipoAcompanhamento));
								} else if (radAberto.equalsIgnoreCase("E")) {
									lAcomp = acompDao.getListAcompReferenciaByTipoAcompanhamentoConcluidos(Long.valueOf(codTipoAcompanhamento));
								} else if (radAberto.equalsIgnoreCase("T")) {
									lAcomp = acompDao.getListAcompReferenciaByTipoAcompanhamento(Long.valueOf(codTipoAcompanhamento));
								}

								// agrupar os acompanhamentos pela sequencia de apresentação do tipo de acompanhamento
								// e, se existir, pelo órgão também.
								if (lAcomp != null) {
							Collections.sort(lAcomp, new Comparator() {
								public int compare(Object o1, Object o2) {
									AcompReferenciaAref aRef1 = (AcompReferenciaAref) o1;
									AcompReferenciaAref aRef2 = (AcompReferenciaAref) o2;

									String ordem1 = Util.completarZerosEsquerda(
									aRef1.getTipoAcompanhamentoTa()
									.getSeqApresentacaoTa(), 3)
									+ Util.completarZerosEsquerda((aRef1
									.getOrgaoOrg() != null) ? aRef1
									.getOrgaoOrg().getCodOrg()
									: null, 8);

									String ordem2 = Util.completarZerosEsquerda(
									aRef2.getTipoAcompanhamentoTa()
									.getSeqApresentacaoTa(), 3)
									+ Util.completarZerosEsquerda((aRef2
									.getOrgaoOrg() != null) ? aRef2
									.getOrgaoOrg().getCodOrg()
									: null, 8);

									return ordem1.compareToIgnoreCase(ordem2);
								}
							});
								}

								String codPrimeiroAcomp = "";

								if (lAcomp != null && lAcomp.size() > 0) {
							codPrimeiroAcomp = ((AcompReferenciaAref) lAcomp.get(0))
									.getCodAref().toString();
								}
								
								
					%>
					<input type="hidden" name="primeiroAcomp"
						value="<%=codPrimeiroAcomp%>">
					<input type="hidden" name="liberarAcomp" value="">
					<input type="hidden" name="enviarEmailParaTodos"
						value="<%=enviarMailTodos%>">
					
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<%
									int cont = 0;
									String cor = new String();

									List listOrdenacaoDataTpfa = tipoFuncAcompDao
									.getTpfaOfArlAndTipoAcompanhamentoOrderByDatas(Long
									.valueOf(codTipoAcompanhamento), lAcomp);

									Iterator itOrdenacaoDataTpfa;

									Iterator itAcomp = lAcomp.iterator();

									String orgaoAtual = "";

									boolean exibirCabecalho = true;

									int numCols = 4;

									//FIXME: Tratar esse if fixo!!!
									boolean exibirLiberarPeriodo = (_pathEcar
									.contains("ecarcel")) ? true : false;
									if (exibirLiberarPeriodo) {
								exibirLiberarPeriodo = false;
								if (seguranca.getGruposAcesso() != null
										&& !seguranca.getGruposAcesso().isEmpty()) {
									Iterator itGa = seguranca.getGruposAcesso()
									.iterator();
									while (itGa.hasNext()) {
										SisAtributoSatb grupoAcesso = (SisAtributoSatb) itGa
										.next();
										if (grupoAcesso.getCodSatb().longValue() == 8) { //Grupo de Acesso = GPA Acompanhamentos
									exibirLiberarPeriodo = true;
									break;
										}
									}
								}
									}
									if (exibirLiberarPeriodo) {
								numCols = 5;
									}
									boolean enviarEmailATodos = ("S".equals(Pagina.getParamStr(
									request, "enviarEmailParaTodos"))) ? true : false;

									while (itAcomp.hasNext()) {
								if (cont % 2 == 0) {
									cor = "cor_nao";
								} else {
									cor = "cor_sim";
								}
								AcompReferenciaAref acompAref = (AcompReferenciaAref) itAcomp
										.next();

								if (!"".equals(Pagina.getParamStr(request,
										"liberarAcomp"))
										&& Pagina.getParamStr(request, "liberarAcomp")
										.equals(
										acompAref.getCodAref()
												.toString())) {
									if (acompRefItemDao.liberarAcompanhamentos(
									acompAref.getAcompReferenciaItemAris(),
									seguranca.getUsuario(), enviarEmailATodos,
									cfgMailLiberar)) {
										Mensagem.alert(_jspx_page_context,
										"O Acompanhamento "
										+ acompAref.getNomeAref()
										+ " foi liberado com sucesso.");
									}
								}

								boolean exibirBotaoLiberar = false;

								if (exibirLiberarPeriodo) { //Só faz o teste se for usuário GPS e contexto ecarcel.
									exibirBotaoLiberar = acompRefItemDao
									.verificarAcompanhamentoEmEdicao(acompAref);
								}
								
						%>
						<%
						if (exibirCabecalho) {
						%>
						<tr class="linha_subtitulo2">
							<td colspan="<%=listOrdenacaoDataTpfa.size()
									+ numCols%>">
								&nbsp;
							</td>
						</tr>

						<tr>
							<td class="espacador"
								colspan="<%=listOrdenacaoDataTpfa.size()
									+ numCols%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<tr class="linha_titulo" align="left">
							<td colspan="<%=listOrdenacaoDataTpfa.size() + 4%>">
								<%=acompAref.getTipoAcompanhamentoTa()
											.getDescricaoTa()%>
							</td>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=listOrdenacaoDataTpfa.size()
									+ numCols%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<tr class="linha_subtitulo">
							<td>
								Referência
							</td>
							<% if (exibirLiberarPeriodo) { 	%>
							<td> &nbsp; </td>
							<% } %>
							<td>
								M&ecirc;s/Ano
							</td>
							<%
										itOrdenacaoDataTpfa = listOrdenacaoDataTpfa
										.iterator();
										while (itOrdenacaoDataTpfa.hasNext()) {
											OrdenacaoDataTpfa ordenacao = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa
											.next();
							%>
							<td align="center"><%=ordenacao.getLabel()%> </td>
							<%
							}
							%>
						</tr>
						<tr>
							<td class="espacador"
								colspan="<%=listOrdenacaoDataTpfa.size()
									+ numCols%>">
								<img src="<%=_pathEcar%>/images/pixel.gif" alt="">
							</td>
						</tr>
						<%
								exibirCabecalho = false;
								}
						%>
						<%
								String orgaoItem = "";
								if (acompAref.getOrgaoOrg() != null) {
									orgaoItem = Pagina.trocaNull(acompAref
									.getOrgaoOrg().getDescricaoOrg());
								}

								if (!orgaoAtual.equals(orgaoItem)
										&& !"".equals(orgaoItem)) {
									orgaoAtual = orgaoItem;
						%>
						<tr class="linha_titulo">
							<td colspan="<%=listOrdenacaoDataTpfa.size()
									+ numCols%>">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=orgaoAtual%>
							</td>
						</tr>
						<%
						}
						%>
						<tr class="<%=cor%>"
							onmouseover="javascript:destacaLinha(this,'over','')"
							onmouseout="javascript: destacaLinha(this,'out','<%=cor%>')"
							onClick="javascript: destacaLinha(this,'click','<%=cor%>')">
							<td>
								<a
									href="javascript:aoClicarConsultar(document.form,<%=acompAref.getCodAref()%>)"><%=acompAref.getNomeAref()%></a>
							</td>
							<%
										if (exibirLiberarPeriodo) {
										if (exibirBotaoLiberar) {
							%>
							<td>
								<input type="button" value="Liberar" name="btLiberar"
									onclick="javascript:aoClicarLiberar(form,<%=acompAref.getCodAref()%>, '<%=acompAref.getNomeAref()%>', '<%=perguntarMail%>');">
							</td>
							<%
							} else {
							%>
							<td>
								&nbsp;
							</td>
							<%
									}
									}
							%>
							<td><%=acompAref.getMesAref()%>/<%=acompAref.getAnoAref()%></td>
							<%
									itOrdenacaoDataTpfa = listOrdenacaoDataTpfa.iterator();

									List listAcompLimite = acompDao
											.getAcompRefLimitesOrderByFuncaoAcomp(acompAref);

									while (itOrdenacaoDataTpfa.hasNext()) {
										OrdenacaoDataTpfa ordenacao = (OrdenacaoDataTpfa) itOrdenacaoDataTpfa
										.next();

										String strValueData = "";
										Iterator itAcompLimite = listAcompLimite.iterator();

										while (itAcompLimite.hasNext()) {
											AcompRefLimitesArl acompLimite = (AcompRefLimitesArl) itAcompLimite
											.next();

											if (ordenacao.getTpfa() != null
											&& acompLimite.getTipoFuncAcompTpfa()
											.equals(ordenacao.getTpfa())) {
										strValueData = Data.parseDate(acompLimite
												.getDataLimiteArl());
										break;
											} else if (ordenacao.getTpfaFixo() != null) {
										if (ordenacao.getTpfaFixo().equals(
												OrdenacaoDataTpfa.FUNCAO_INICIO)) {
											strValueData = Data.parseDate(acompAref
											.getDataInicioAref());
											break;
										} else if (ordenacao.getTpfaFixo().equals(
												OrdenacaoDataTpfa.FUNCAO_LIMITE)) {
											strValueData = Data
											.parseDate(acompAref
													.getDataLimiteAcompFisicoAref());
											break;
										}
											}
										}
										if ("".equals(strValueData)) {
											strValueData = "&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;";
										}
							%>
							<td align="center"><%=strValueData%></td>
							<%
									}
									cont++;
							%>
						</tr>
						<%
						} /* while (itAcomp.hasNext()) */
						%>

					</table>
			</form>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<%
					} catch (ECARException e) {
					org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
					Mensagem.alert(_jspx_page_context, e.getMessageKey());
				} catch (Exception e) {
		%>
		<%@ include file="/excecao.jsp"%>
		<%
			}
			}
		%>
		</div>

	</body>
	<%@ include file="/include/ocultarAguarde.jsp"%>
	<%
	/* Controla o estado do Menu (aberto ou fechado) */
	%>
	 
	<%@ include file="/include/estadoMenu.jsp"%>	 
	<%@ include file="/include/mensagemAlert.jsp"%>
	
</html>

