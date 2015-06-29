
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EfIettFonteTotEfieftPK" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EfIettFonteTotEfieft" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiepPK" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>

<%@ page import="comum.util.Util" %>
<%@ page import="java.math.BigDecimal" %>
  
<%@ page import="java.text.SimpleDateFormat" %> 
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.HashSet" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head> 

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">
function aoClicarConsultar(form, codigo1){
	form.codFonr.value = codigo1;
	document.form.action = "frm_con.jsp";
	document.form.submit();	
} 

function aoClicarEditar(form, codigo1){
	form.codFonr.value = codigo1;
	form.hidAcao.value = 'alterar'; 
	document.form.action = "frm_con.jsp";
	document.form.submit();
}

function MostraLinha(parmCodigo) {
  if (document.getElementById('linha' + '_' + parmCodigo).style.display=='none') {
     document.getElementById('linha' + '_' + parmCodigo).style.display='';
  } else {
     document.getElementById('linha' + '_' + parmCodigo).style.display='none';
  }
}

function incluirConta(form, codFonr){
	
	var campoCodFonr = document.getElementById("codFonr");
	campoCodFonr.value = codFonr;	
	form.action = "../contasOrcamento/lista.jsp?codIett=" + form.codIett.value;
	form.submit();
}

function incluirValor(form){
	if(form.possuiTodosRecCadastrados.value == "S"){
		alert("<%=_msg.getMensagem("itemEstrutura.recurso.validacao.todosRecursos")%>");
	}
	else {
		form.incluirNovoValor.value = "S";
		aoClicarIncluir(form);
	}
}
</script>
 
</head>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<body>
<div id="conteudo"> 

<% 
try{
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
	
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
	ItemEstruturaFonteRecursoDao ieFonteRecDao = new ItemEstruturaFonteRecursoDao(request);
	ItemEstruturaPrevisaoDao itemEstPrevisaoDao = new ItemEstruturaPrevisaoDao(request);

	// Comentado devido ao BUG 4851, NÃO retirar a linha abaixo
	//boolean podeIncluir = ieFonteRecDao.verificaPossibilidadeInclusao(itemEstrutura);
	boolean podeIncluir = true;
	
	String recursoDesc1 = (Pagina.trocaNull(configuracao.getRecursoDescValor1Cfg()));
	String recursoDesc2 = (Pagina.trocaNull(configuracao.getRecursoDescValor2Cfg()));
	String recursoDesc3 = (Pagina.trocaNull(configuracao.getRecursoDescValor3Cfg()));
	
	String financeiroDesc1 = (Pagina.trocaNull(configuracao.getFinanceiroDescValor1Cfg()));
	String financeiroDesc2 = (Pagina.trocaNull(configuracao.getFinanceiroDescValor2Cfg()));
	String financeiroDesc3 = (Pagina.trocaNull(configuracao.getFinanceiroDescValor3Cfg()));
	String financeiroDesc4 = (Pagina.trocaNull(configuracao.getFinanceiroDescValor4Cfg()));
	String financeiroDesc5 = (Pagina.trocaNull(configuracao.getFinanceiroDescValor5Cfg()));
	String financeiroDesc6 = (Pagina.trocaNull(configuracao.getFinanceiroDescValor6Cfg()));
	
	String codAba = Pagina.getParamStr(request, "codAba");
%>
 
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>
 
 <form name="form" method="post">

<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="hidAcao" value="">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="codFonr" id="codFonr" value="">
<input type=hidden name="possuiTodosRecCadastrados" value="N">
<input type=hidden name="incluirNovoValor" value="N">

<%
boolean ehTelaListagem = false;
 
EstruturaEtt estruturaEttSelecionada = null;
EstruturaDao estruturaDaoArvoreItens = new EstruturaDao(request);


//ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){
	%>
	<script language="javascript" src="../../js/menu_retratil_cadastro.js"></script>
	<script language="javascript" src="../../js/menu_cadastro.js"></script>	

	<%
	}else{
	%>
	<script language="javascript" src="../../js/menu_retratil.js"></script>
	<%
	}
	%>
<%@ include file="../arvoreItens.jsp"%>	
<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<%
			//Se usar a árvore o id do div precisa ser "conteudoCadastroEstrutura" para que seja utilizado o estilo da árvore
			if(configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")){ %>
			<div id="conteudoCadastroEstrutura">
			<%
			}else{
			%>
			<div>
			<%
			}
			%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<%
					//Utilizado apenas quando a árvore está configurada para aparecer
					if (configuracaoCfg.getIndExibirArvoreNavegacaoCfg() != null && configuracaoCfg.getIndExibirArvoreNavegacaoCfg().equals("S")) {
					%>
					<!-- Barra amarela -->
		    			<td class="menuHideShowCadastro">
		    			<!-- Botão na barra -->
					<div id="btmenuCadastro"></div>
					</td>
					<script language="javascript">			
						//Inicia com o menu cadastro aberto
						botaoCadastro("aberto");
						mudaEstadoCadastro("aberto");			
					</script>
					<%} %>
						<td width="100%" valign="top">
				<!-- ############### Árvore de Estruturas  ################### -->
<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
<!-- ############### Árvore de Estruturas  ################### -->
 
<!-- ############### Barra de Links  ################### -->
<util:barraLinksItemEstrutura estrutura="<%=itemEstrutura.getEstruturaEtt()%>" selectedFuncao="<%=codAba%>" codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" contextPath="<%=request.getContextPath()%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
<!-- ############### Barra de Links  ################### -->

<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);

%>

<br><br>



 
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td>
		<%if(podeIncluir && permissaoAlterar.booleanValue()){ %>
			<!-- input type="button" value="Adicionar <%=estruturaFuncao.getLabelEttf()%>" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
			<input type="button" value="Excluir <%=estruturaFuncao.getLabelEttf()%>" class="botao" onclick="javascript:aoClicarExcluir(document.form)"-->
			<input type="button" value="Adicionar Valores" name="btnAdd" class="botao" onclick="javascript:incluirValor(document.form)"> 
			<input type="button" value="Excluir Valores" name="btnDel" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
		<%} else {%>
			&nbsp;
		<%}%>
		</td>
	</tr>
</table>
	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan=8><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="4%">
			<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
		</td>
		<td width="4%"></td>
		<td width="4%"><%//=estruturaFuncaoDao.getLabelFuncaoContasOrcamento(estruturaFuncao.getEstruturaEtt()).toString()%></td>
		<td><%=estruturaFuncao.getLabelEttf()%></td>
		<!-- td><%=estruturaFuncaoDao.getLabelFuncaoRecurso(estruturaFuncao.getEstruturaEtt()).toString()%></td -->
		<%if(!"".equals(recursoDesc1.trim())) {%>
			<td align="right" width="200px"><%=recursoDesc1%></td>
		<%} %>
		<%if(!"".equals(recursoDesc2.trim())) {%>		
			<td align="right" width="200px"><%=recursoDesc2%></td> 
		<%} %>
		<%if(!"".equals(recursoDesc3.trim())) {%>		
			<td align="right" width="200px"><%=recursoDesc3%></td> 
		<%} %>
		<!-- <td align="right" width="200px">Varia&ccedil;&atilde;o (%)</td> -->
	</tr>
	<tr><td class="espacador" colspan=8><img src="../../images/pixel.gif"></td></tr>
<%
	int linha = 0;
	if(podeIncluir){
		EfIettFonteTotEfieftPK pk = new EfIettFonteTotEfieftPK();
		pk.setCodIett(itemEstrutura.getCodIett());
		
		List fontesAtivas = ieFonteRecDao.getAtivos(itemEstrutura);

		if(fontesAtivas != null ){ 
			
			String[] proximoRecurso = ieFonteRecDao.verificaExistenciaRecursoIett(itemEstrutura);
			
			if(proximoRecurso == null){
			%>
			<script language="Javascript">
				document.form.possuiTodosRecCadastrados.value = "S";
			</script>
			<%
			}
			else {//Ainda existem fontes que não foram cadastradas... verificar qual é a próxima fonte...
			%>
				<input type="hidden" name="codProximaFonte" value="<%=proximoRecurso[0]%>">
				<input type="hidden" name="nomeProximaFonte" value="<%=proximoRecurso[1]%>">
			<%
			}
			
			Iterator itFonteRecurso = fontesAtivas.iterator();
			while(itFonteRecurso.hasNext()){
				EfIettFonteTotEfieft ieFonteRecurso = (EfIettFonteTotEfieft) itFonteRecurso.next();
				List listaRecurso = new ArrayList();
				linha++;
				%>
				<tr class="linha_subtitulo2"> 
					<td width="4%">
						<input type="checkbox" class="form_check_radio" name="excluir" value="<%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>">
					</td>
					<td width="4%">
						<a href="javascript:aoClicarEditar(document.form,<%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>)">
						<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
					</td>
					<%
						//Obtendo o nome do sistema através da Categoria Econômica.
						String hintSistema = "Sistema Orçamentário-Financeiro";
						String sistemaSigla = "";
						if(ieFonteRecurso != null && ieFonteRecurso.getFonteRecursoFonr() != null && ieFonteRecurso.getFonteRecursoFonr().getConfigSisExecFinanCsef() != null){
							hintSistema = ieFonteRecurso.getFonteRecursoFonr().getConfigSisExecFinanCsef().getNomeCsef();
							sistemaSigla = ieFonteRecurso.getFonteRecursoFonr().getConfigSisExecFinanCsef().getSiglaCsef();							
						}
					%>
					
					<td width="4%" align="center">
						<a href="#" onclick="javascript:incluirConta(form, <%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>)">
						<img border="0" src="../../images/icon_cofre.png" alt="<%=hintSistema%>" title="<%=hintSistema%>"></a>&nbsp;
					</td>
					
					<td>

						<a href="javascript:MostraLinha('<%=linha%>');">
						<%=ieFonteRecurso.getFonteRecursoFonr().getNomeFonr()%></a>
					</td>
					<%
					double somaRecursosAprovados = new ItemEstruturaFonteRecursoDao(request).getSomaRecursosFonteRecurso(ieFonteRecurso, "Aprovado");
					double somaRecursosRevisados = new ItemEstruturaFonteRecursoDao(request).getSomaRecursosFonteRecurso(ieFonteRecurso, "Revisado");
					String variacao = "";

					if(somaRecursosAprovados == 0 && somaRecursosRevisados == 0)
						variacao = "0,00%";
					else if(somaRecursosAprovados == 0)
						variacao = "+999,99%";
					else if (somaRecursosRevisados == 0)
						variacao = "-100,00%";
					else
						variacao = Pagina.trocaNullNumeroDecimal(Double.valueOf((somaRecursosRevisados/somaRecursosAprovados) * 100)) + "%";
					%>
					
					<%if(!"".equals(recursoDesc1.trim())){ %>
						<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(somaRecursosAprovados))%></td>	
					<%} %>

					<%if(!"".equals(recursoDesc2.trim())){ %>
						<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(somaRecursosRevisados))%></td>
					<%} %>					
					
					<%if(!"".equals(recursoDesc3.trim())){ %>
						<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(somaRecursosRevisados + somaRecursosAprovados))%></td>
					<%} %>
				</tr>
				<tr>
					<td colspan="8">
						<%
						ItemEstruturaPrevisaoDao iePrevisaoDao = new ItemEstruturaPrevisaoDao(request);
						ItemEstruturaContaOrcamentoDao itemContaDao = new ItemEstruturaContaOrcamentoDao(request);

						iePrevisaoDao = new ItemEstruturaPrevisaoDao(request);
							
						//RecursoRec
						//List listaRec = iePrevisaoDao.getRecursosDistintosByFonteRecurso(ieFonteRecurso.getFonteRecursoFonr().getCodFonr(), ieFonteRecurso.getItemEstruturaIett().getCodIett());
						List listaRec = iePrevisaoDao.getRecursosDistintosByFonteRecurso(ieFonteRecurso.getFonteRecursoFonr().getCodFonr(), ieFonteRecurso.getItemEstruturaIett().getCodIett());
						
						if(listaRec != null && !listaRec.isEmpty()){
							%>
							<table id="linha_<%=linha%>" style="display:none" border="1" cellspacing="0">
							<tr class="titulo"><td align="center"  colspan="15"><%=ieFonteRecurso.getFonteRecursoFonr().getNomeFonr()%></td></tr>
							<tr class="titulo">
								<td align="center"><%//=estruturaFuncaoDao.getLabelFuncaoRecurso(estruturaFuncao.getEstruturaEtt()).toString()%></td>
								<td align="center">Exercício</td>
								<%if(!"".equals(recursoDesc1.trim())) {%>
									<td align="center"><%=recursoDesc1%></td>
								<%} %>
								<%if(!"".equals(recursoDesc2.trim())) {%>								
									<td align="center"><%=recursoDesc2%></td>
								<%} %>
								<%if(!"".equals(recursoDesc3.trim())) {%>								
									<td align="center"><%=recursoDesc3%></td>
								<%} %>
								<%if(!"".equals(financeiroDesc1.trim())) {%>								
									<td align="center"><%=financeiroDesc1%></td>
								<%} %>
								<%if(!"".equals(financeiroDesc2.trim())) {%>								
									<td align="center"><%=financeiroDesc2%></td>
								<%} %>
								<%if(!"".equals(financeiroDesc3.trim())) {%>								
									<td align="center"><%=financeiroDesc3%></td>
								<%} %>								
								<%if(!"".equals(financeiroDesc4.trim())) {%>								
									<td align="center"><%=financeiroDesc4%></td>
								<%} %>								
								<%if(!"".equals(financeiroDesc5.trim())) {%>								
									<td align="center"><%=financeiroDesc5%></td>
								<%} %>								
								<%if(!"".equals(financeiroDesc6.trim())) {%>								
									<td align="center"><%=financeiroDesc6%></td>
								<%} %>								
								<td align="center" width="10px">&nbsp;</td>
								<td align="center"><%=estruturaFuncaoDao.getLabelFuncaoContasOrcamento(estruturaFuncao.getEstruturaEtt()).toString()%></td>
								<td align="center">Acumulado</td>
								<td align="center">Ativo</td>
							</tr>
							<%

							double totalGeralAprovado = 0;
							double totalGeralRevisado = 0;
							double[] totalGeralValoresFinanceiros = {0,0,0,0,0,0};
							String corImportados = "#336633";
							
							Iterator itRec = listaRec.iterator();
							
							while(itRec.hasNext()){
								
								RecursoRec fonte = (RecursoRec) itRec.next();
								ExercicioDao exercicioDao = new ExercicioDao(request);
								ItemEstruturaRealizadoDao itemEstDAO = new ItemEstruturaRealizadoDao(request);
								
								Collection listaExercicios = exercicioDao.getExerciciosValidos(itemEstrutura.getCodIett());
								
								Iterator itExerc = listaExercicios.iterator();
								int qtdeExe = listaExercicios.size();
								String nomeFonte = fonte.getNomeRec();
								double totalExeAprovado = 0;
								double totalExeRevisado = 0;
								double totalAumentoReducao = 0;
								
								double[] totalValoresFinanceiros = {0,0,0,0,0,0};
								
								while (itExerc.hasNext()) {
																	
									ExercicioExe exercicio = (ExercicioExe) itExerc.next();
									EfItemEstPrevisaoEfiep ieRecurso = iePrevisaoDao.buscar(itemEstrutura.getCodIett(), ieFonteRecurso.getFonteRecursoFonr().getCodFonr(), fonte.getCodRec(), exercicio.getCodExe());
									
									EfItemEstContaEfiec contaOrc = itemContaDao.getItemEstruturaConta(itemEstrutura, exercicio, ieFonteRecurso.getFonteRecursoFonr(), fonte);

									Double[][] valoresFinanceiros = itemEstDAO.getSomaDetalhadaItemEstruturaRealizado(contaOrc, exercicio).getDetalhesContas();
									
									BigDecimal valorAprovado = new BigDecimal(0);
									if(ieRecurso.getItemEstruturaIett() != null)
										valorAprovado = itemEstPrevisaoDao.previsaoSomaValores(ieRecurso.getItemEstruturaIett().getCodIett(), ieRecurso.getFonteRecursoFonr().getCodFonr(), ieRecurso.getExercicioExe().getCodExe(), ieRecurso.getRecursoRec().getCodRec(), Dominios.EFIEP_VALOR_APROVADO);
									BigDecimal valorRevisado = new BigDecimal(0);
									if(ieRecurso.getItemEstruturaIett() != null)
										valorRevisado = itemEstPrevisaoDao.previsaoSomaValores(ieRecurso.getItemEstruturaIett().getCodIett(), ieRecurso.getFonteRecursoFonr().getCodFonr(), ieRecurso.getExercicioExe().getCodExe(), ieRecurso.getRecursoRec().getCodRec(), Dominios.EFIEP_VALOR_REVISADO);
									
									%>
										<tr>
									<%
											if(!"".equals(nomeFonte)){
									%>
												<td rowspan="<%=qtdeExe%>" style="text-align: right;">
													<table width="100%">						
														<tr>									
															<td align="center"><%=nomeFonte.trim()%></td>
														</tr>
													</table>
												</td>
									<%
											}
									%>
												<td align="center" style="padding: 0px">
													<table>											
														<tr> 													
															<td style="vertical-align: middle;text-align: center;"><%=exercicio.getDescricaoExe().trim()%></td> 
												        </tr>
												    </table>
												</td>
											
											<%if(!"".equals(recursoDesc1.trim())) { %>
												<td class="form_label" align="right">
													<table>											
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valorAprovado==null?new BigDecimal(0):valorAprovado)%></td>
														</tr>
													</table>
												</td>
													
											<%} %>
											<%if(!"".equals(recursoDesc2.trim())) { %>
												<td class="form_label" align="right">
													<table>
															<tr>																					
																<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valorRevisado==null?new BigDecimal(0):valorRevisado)%></td>
															</tr>
													</table>
												</td>
											<%} %>
									<%--<%
											double aumentoReducao = 0;
											if(ieRecurso.getValorRevisadoEfiep() != null && ieRecurso.getValorRevisadoEfiep().doubleValue() > 0){
												aumentoReducao = ieRecurso.getValorRevisadoEfiep().subtract(ieRecurso.getValorAprovadoEfiep()).doubleValue();
											}
									%>--%>
									<%
											double aumentoReducao = 0;
											if(valorAprovado != null){
												if(valorRevisado != null && valorRevisado.doubleValue() > 0){
													aumentoReducao = valorRevisado.add(valorAprovado).doubleValue();
												} else {
													aumentoReducao = valorAprovado.doubleValue();
												}
											}
									%>
											<%if(!"".equals(recursoDesc3.trim())) { %>
												<td class="form_label" align="right">
													<table>
															<tr>
																<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(aumentoReducao))%></td>
															</tr>
													</table>
												</td>
											<%} %>
											<%if(!"".equals(financeiroDesc1.trim())) {%>							
												<td class="form_label" align="right">
													<table>
														<tr>
															<td class="form_label" align="right"  style="color: <%= corImportados%>"><%=Pagina.trocaNullMoeda(valoresFinanceiros[0][0])%></td>
														</tr>
														<%if(valoresFinanceiros[1][0].doubleValue() > 0.0) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valoresFinanceiros[1][0])%></td>
														</tr>
														<%} %>
														<%
															double v1 = valoresFinanceiros[0][0].doubleValue();
															double v2 = valoresFinanceiros[1][0].doubleValue();
															double vTot = (valoresFinanceiros[0][0].doubleValue() + valoresFinanceiros[1][0].doubleValue());
														%>
														<%if((vTot > 0.0) && (vTot != v1)) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(new Double(valoresFinanceiros[0][0].doubleValue() + valoresFinanceiros[1][0].doubleValue()))%></td>
														</tr>
														<%} %>												
													</table>
												</td>
											<%} %>
											<%if(!"".equals(financeiroDesc2.trim())) {%>								
												<td class="form_label" align="right">
													<table>
														<tr>
															<td class="form_label" align="right"  style="color: <%= corImportados%>"><%=Pagina.trocaNullMoeda(valoresFinanceiros[0][1])%></td>
														</tr>
														<%if(valoresFinanceiros[1][1].doubleValue() > 0.0) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valoresFinanceiros[1][1])%></td>
														</tr>
														<%}
															double v1 = valoresFinanceiros[0][1].doubleValue();
															double v2 = valoresFinanceiros[1][1].doubleValue();
															double vTot = (valoresFinanceiros[0][1].doubleValue() + valoresFinanceiros[1][1].doubleValue());
														%>
														<%if((vTot > 0.0) && (vTot != v1)) {%>
														<tr>
															<td align="right" class="form_label"><%=Pagina.trocaNullMoeda(new Double(valoresFinanceiros[0][1].doubleValue() + valoresFinanceiros[1][1].doubleValue()))%></td>
														</tr>
														<%} %>													
													</table>
												</td>
											<%} %>
											<%if(!"".equals(financeiroDesc3.trim())) {%>								
												<td class="form_label" align="right">
													<table>
														<tr>
															<td class="form_label" align="right"  style="color: <%= corImportados%>"><%=Pagina.trocaNullMoeda(valoresFinanceiros[0][2])%></td>
														</tr>
														<%if(valoresFinanceiros[1][2].doubleValue() > 0.0) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valoresFinanceiros[1][2])%></td>
														</tr>
														<%}
															double v1 = valoresFinanceiros[0][2].doubleValue();
															double v2 = valoresFinanceiros[1][2].doubleValue();
															double vTot = (valoresFinanceiros[0][2].doubleValue() + valoresFinanceiros[1][2].doubleValue());
														%>
														<%if((vTot > 0.0) && (vTot != v1)) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(new Double(valoresFinanceiros[0][2].doubleValue() + valoresFinanceiros[1][2].doubleValue()))%></td>
														</tr>
														<%} %>													
													</table>
												</td>
											<%} %>								
											<%if(!"".equals(financeiroDesc4.trim())) {%>								
												<td class="form_label" align="right">
													<table>
														<tr>
															<td class="form_label" align="right"  style="color: <%= corImportados%>"><%=Pagina.trocaNullMoeda(valoresFinanceiros[0][3])%></td>
														</tr>
														<%if(valoresFinanceiros[1][3].doubleValue() > 0.0) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valoresFinanceiros[1][3])%></td>
														</tr>
														<%}
															double v1 = valoresFinanceiros[0][3].doubleValue();
															double v2 = valoresFinanceiros[1][3].doubleValue();
															double vTot = (valoresFinanceiros[0][3].doubleValue() + valoresFinanceiros[1][3].doubleValue());
														%>
														<%if((vTot > 0.0) && (vTot != v1)) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(new Double(valoresFinanceiros[0][3].doubleValue() + valoresFinanceiros[1][3].doubleValue()))%></td>
														</tr>
														<%} %>
													</table>
												</td>
											<%} %>								
											<%if(!"".equals(financeiroDesc5.trim())) {%>								
												<td class="form_label" align="right">
													<table>
														<tr>
															<td class="form_label" align="right" style="color: <%= corImportados%>"><%=Pagina.trocaNullMoeda(valoresFinanceiros[0][4])%></td>
														</tr>
														<%if(valoresFinanceiros[1][4].doubleValue() > 0.0) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valoresFinanceiros[1][4])%></td>
														</tr>
														<%}
															double v1 = valoresFinanceiros[0][4].doubleValue();
															double v2 = valoresFinanceiros[1][4].doubleValue();
															double vTot = (valoresFinanceiros[0][4].doubleValue() + valoresFinanceiros[1][4].doubleValue());
														%>
														<%if((vTot > 0.0) && (vTot != v1)) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(new Double(valoresFinanceiros[0][4].doubleValue() + valoresFinanceiros[1][4].doubleValue()))%></td>
														</tr>
														<%} %>
													</table>
												</td>
											<%} %>								
											<%if(!"".equals(financeiroDesc6.trim())) {%>								
												<td class="form_label" align="right">
													<table>
														<tr>
															<td class="form_label" align="right" style="color: <%= corImportados%>"><%=Pagina.trocaNullMoeda(valoresFinanceiros[0][5])%></td>
														</tr>
														<%if(valoresFinanceiros[1][5].doubleValue() > 0.0) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(valoresFinanceiros[1][5])%></td>
														</tr>
														<%} %>
														<%
															double v1 = valoresFinanceiros[0][5].doubleValue();
															double v2 = valoresFinanceiros[1][5].doubleValue();
															double vTot = (valoresFinanceiros[0][5].doubleValue() + valoresFinanceiros[1][5].doubleValue());
														%>
														<%if((vTot > 0.0) && (vTot != v1)) {%>
														<tr>
															<td class="form_label" align="right"><%=Pagina.trocaNullMoeda(new Double(valoresFinanceiros[0][5].doubleValue() + valoresFinanceiros[1][5].doubleValue()))%></td>
														</tr>
														<%} %>
													</table>
												</td>
											<%} %>
																			
											<td class="form_label" align="center" width="10px">&nbsp;</td>
											<td class="form_label" align="center">
												<table>
													<tr>
														<td><%=(contaOrc != null && contaOrc.getContaSistemaOrcEfiec() != null ? contaOrc.getContaSistemaOrcEfiec() : "&nbsp;")%></td>
													</tr>
												</table>
											</td>
											<td class="form_label" align="center">
												<table>
													<tr>
														<td><%=("S".equals(contaOrc.getIndAcumuladoEfiec()) ? "Sim" : "Não")%></td>
													</tr>
												</table>
											</td>
												<td class="form_label" align="center">
													<table>
														<tr>																									
															<td><%=("S".equals(ieRecurso.getIndAtivoEfiep()) ? "Sim" : "Não")%></td>
														</tr>
													</table>
												</td>
										</tr>
									<%
									nomeFonte = "";
									if(valorAprovado != null)
										totalExeAprovado += valorAprovado.doubleValue();
									if(valorRevisado != null)
										totalExeRevisado += valorRevisado.doubleValue();
									totalAumentoReducao += aumentoReducao;
									
									//total
									for(int i=0; i<	totalValoresFinanceiros.length; i++) {
										double valor1 = totalValoresFinanceiros[i];
										double valor2 = (valoresFinanceiros[0][i].doubleValue() + valoresFinanceiros[1][i].doubleValue());
										totalValoresFinanceiros[i] = (valor1 + valor2);
									}

									//Acumula no Total Geral
									for(int i=0; i<	totalGeralValoresFinanceiros.length; i++) {
										double valor1 = totalGeralValoresFinanceiros[i];
										double valor2 = (valoresFinanceiros[0][i].doubleValue() + valoresFinanceiros[1][i].doubleValue());
										totalGeralValoresFinanceiros[i] = (valor1 + valor2);
									}
																		
								}
								%>
								<tr class="titulo">
									<td align="center">&nbsp;</td>
									<td align="center">Total</td>
									
									<%if(!"".equals(recursoDesc1.trim())) {%>
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalExeAprovado))%></td>
									<%} %>
									<%if(!"".equals(recursoDesc2.trim())) {%>									
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalExeRevisado))%></td>
									<%} %>
									<%if(!"".equals(financeiroDesc1.trim())) {%>								
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalValoresFinanceiros[0]))%></td>
									<%} %>
									<%if(!"".equals(financeiroDesc2.trim())) {%>								
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalValoresFinanceiros[1]))%></td>
									<%} %>
									<%if(!"".equals(financeiroDesc3.trim())) {%>								
										<td align=right><%=Pagina.trocaNullMoeda(Double.valueOf(totalValoresFinanceiros[2]))%></td>
									<%} %>								
									<%if(!"".equals(financeiroDesc4.trim())) {%>								
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalValoresFinanceiros[3]))%></td>
									<%} %>								
									<%if(!"".equals(financeiroDesc5.trim())) {%>								
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalValoresFinanceiros[4]))%></td>
									<%} %>								
									<%if(!"".equals(financeiroDesc6.trim())) {%>								
										<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalValoresFinanceiros[5]))%></td>
									<%} %>

									<td align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
									<td align="center">&nbsp;</td>
								</tr>
								<%
								totalGeralAprovado += totalExeAprovado;
								totalGeralRevisado += totalExeRevisado;
							}
							
							if(listaRec != null && listaRec.size() > 1){
							%>
							<tr class="titulo">
								<td align="center">Total&nbsp;Geral</td>
								<td align="center">&nbsp;</td>
								<%if(!"".equals(recursoDesc1.trim())) {%>
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralAprovado))%></td>
								<%}%>
								<%if(!"".equals(recursoDesc2.trim())) {%>
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralRevisado))%></td>
								<%}%>
								<%if(!"".equals(financeiroDesc1.trim())) {%>								
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralValoresFinanceiros[0]))%></td>
								<%} %>
								<%if(!"".equals(financeiroDesc2.trim())) {%>								
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralValoresFinanceiros[1]))%></td>
								<%} %>
								<%if(!"".equals(financeiroDesc3.trim())) {%>								
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralValoresFinanceiros[2]))%></td>
								<%} %>								
								<%if(!"".equals(financeiroDesc4.trim())) {%>								
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralValoresFinanceiros[3]))%></td>
								<%} %>								
								<%if(!"".equals(financeiroDesc5.trim())) {%>								
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralValoresFinanceiros[4]))%></td>
								<%} %>								
								<%if(!"".equals(financeiroDesc6.trim())) {%>								
									<td align="right"><%=Pagina.trocaNullMoeda(Double.valueOf(totalGeralValoresFinanceiros[5]))%></td>
								<%} %>
								<td align="center">&nbsp;</td>								
								<td align="center">&nbsp;</td>								
								<td align="center">&nbsp;</td>
								<td align="center">&nbsp;</td>								
							</tr>
							<tr>
								<td align="left" colspan="14">
									<table>
										<tr>
											<td>
												<table>
													<tr>
														<td style="background-color: <%= corImportados%>;" width="10px" height="10px">
														</td>
													</tr>
												</table>											
											</td>
											<td colspan="13">
												Valores Realizados <%//= " obtidosdo Sistema " + sistemaSigla%>
											</td>								
										</tr>
									</table>
								</td>
							</tr>
							<%
							}
							%>
							</table>
						<%
						} else {
						%>
							<table id="linha_<%=linha%>" style="display:none" border="1" cellspacing="0">
							<tr><td class="titulo" align="center">Nenhum valor cadastrado</td></tr>
							</table>
						<%
						}
						%>
						
						
						
					</td>
				</tr>
				<%
			}  
		}			
	}else{
		%>
		<tr><td colspan=6><br><%=_msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.naoPermitida")%><br></td></tr>
		<%
	}
	%>
						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
<%
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	<!-- ############### Listagem  ################### -->

<table>
</div>
<br>
</form>

</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="/include/mensagemAlert.jsp" %>
</html>

