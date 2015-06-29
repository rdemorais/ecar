<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.dao.ExercicioDao" %>

<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%
session.removeAttribute("objPesquisa");
%>


<%@page import="ecar.pojo.historico.HistoricoItemEstrtIndResulIettr"%>
<%@page import="java.io.Serializable"%>
<%@page import="ecar.dao.ItemEstrutFisicoDao"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>

<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">
function aoClicarConsultar(form, codIettir){
	form.codIettir.value = codIettir;
	document.form.action = "../quantPrevista/lista.jsp";
	document.form.submit();
} 

function aoClicarEditar(form, codIettir){
	form.codIettir.value = codIettir;
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
</script>

</head>

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	String codAba = Pagina.getParamStr(request, "codAba");
	
%>

<!-- TITULO -->
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post">

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
						
						
							<br><br>

							<%
								EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
								EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
								
								estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
								
								Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
							%>

							<input type="hidden" name="hidAcao" value="">
							<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
							<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
							
							<!--codigos-->
							<input type="hidden" name="codIettir" value="">
						
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
							<tr class="linha_titulo" align="right">
								<td>
								<%if(permissaoAlterar.booleanValue()){%>
									<input type="button" class="botao" value="Adicionar" onclick="javascript:aoClicarIncluir(form);">&nbsp;
									<input type="button" class="botao" value="Excluir" onclick="javascript:aoClicarExcluir(form);">&nbsp;
								<%}%>
								</td>
							</tr>
							</table>
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
							<tr class="linha_subtitulo">
								<td width="2%"><input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)"></td>
								<td width="4%"></td>
								<td>Nome</td>
								<td>Informações Complementares</td>
								<td>Unidade<br>Medida</td>
								<td>Formato</td>
								<td>Periodicidade de Acompanhamento</td>
								<td>Acumulável?</td>
								<td>Valor Final</td>
							</tr>
							<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
						<%
						
							List lista = null;
							if (itemEstrutura.getItemEstrtIndResulIettrs() != null){
										
								lista = itemEstruturaDao.ordenaSet(itemEstrutura.getItemEstrtIndResulIettrs(), "this.nomeIettir", "asc");
								
								//lista = itemEstrtIndResulDao.listaHistorico(itemEstrutura.getItemEstrtIndResulIettrs(), "this.nomeIettir", "asc");
								
								Iterator it = lista.iterator();
								
								ItemEstrtIndResulIettr itemEstrtIndResul = null;
								
								int cont = 0;
								while (it.hasNext()) {
									cont++;
								
										itemEstrtIndResul = (ItemEstrtIndResulIettr) it.next();
										
										
										if((itemEstrtIndResul.getIndAtivoIettr() == null) || ("S".equals(itemEstrtIndResul.getIndAtivoIettr()))) {
								%>		
											<tr class="linha_subtitulo2">
												<td width="2%">
													<input type="checkbox" class="form_check_radio" name="excluir" value="<%=itemEstrtIndResul.getCodIettir()%>">
												</td>
												<td width="4%">
													<a href="javascript:aoClicarEditar(document.form,<%=itemEstrtIndResul.getCodIettir()%>)">
													<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
												</td>
												<td>
													<a href="javascript:MostraLinha('<%=cont%>');" title='Mostrar/esconder Quantidades Previstas'>
													<%=itemEstrtIndResul.getNomeIettir()%></a>
												</td>
												<td><%=itemEstrtIndResul.getDescricaoIettir()%></td>
												<td><%=new ItemEstrtIndResulDao(null).getUnidadeUsada(itemEstrtIndResul)%></td>
												<td>
								<%
													if ("Q".equalsIgnoreCase(itemEstrtIndResul.getIndTipoQtde())) {
														out.println("Quantidade");
													}
													else if ("V".equalsIgnoreCase(itemEstrtIndResul.getIndTipoQtde())) {
														out.println("Valor");
													}
								%>				
												</td>
												<td>
								<% if( itemEstrtIndResul.getPeriodicidadePrdc() != null && itemEstrtIndResul.getPeriodicidadePrdc().getDescricaoPrdc() != null) {
									out.println( itemEstrtIndResul.getPeriodicidadePrdc().getDescricaoPrdc() );
								} else {
									out.println(""); 
								}
//													if ("S".equalsIgnoreCase(itemEstrtIndResul.getIndProjecaoIettr())){
//														out.println("Sim");
//													} else {
//														out.println("Não");
//													}
								%>
												</td>
												<td>
								<%
													if ("S".equalsIgnoreCase(itemEstrtIndResul.getIndAcumulavelIettr())){
														out.println("Sim");
													} else {
														out.println("Não");
													}
								%>
												</td>
												<td>
								<%=itemEstrtIndResul.getIndAcumulavelIettr()
													//if ("S".equalsIgnoreCase(itemEstrtIndResul.getIndRealPorLocal())){
													//	out.println("Sim");
													//} else {
													//	out.println("Não");
													//}
								%>
												</td>
											</tr>
											<tr>
												<td colspan="9">
													<table id="linha_<%=cont%>" style="display:none" border="0" cellspacing="0">
								<%
														if (itemEstrtIndResul.getItemEstrutFisicoIettfs() != null &&
																 	itemEstrtIndResul.getItemEstrutFisicoIettfs().size() > 0){
								%>
															<tr>
																<td class="titulo">Exercício</td>
																<td class="titulo">Quantidade Prevista</td>
															</tr>
								<%
															List listaExercicios = new ExercicioDao(request).getExerciciosValidos(itemEstrtIndResul.getItemEstruturaIett().getCodIett());
															
															ItemEstrutFisicoDao itemEstrutFisicoDao = new ItemEstrutFisicoDao(request);

															Iterator itExercicio = listaExercicios.iterator();
															while (itExercicio.hasNext()) {
																ExercicioExe exercicio = (ExercicioExe) itExercicio.next();
																Double qtdePrevista = itemEstrutFisicoDao.getQtdPrevistaExercicio(exercicio, itemEstrtIndResul, null);
								%>
																<tr>
																	<td class="form_label" align="center">
																		<%=exercicio.getDescricaoExe()%>
																	</td>
																	<td class="form_label" align="center">
																		<%
																			String sPrevisto;
																			if("Q".equals(itemEstrtIndResul.getIndTipoQtde())){
																				sPrevisto=Pagina.trocaNullNumeroDecimal(qtdePrevista);
																			}else{
																				sPrevisto=Pagina.trocaNullMoeda(qtdePrevista);												
																			}
																		%>
																		<%=sPrevisto%>
																	</td>
																</tr>
								<%
															}
															
															ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
								%>
															<tr>
																<td class="titulo" align="center">Total</td>
																<td class="titulo" align="center">
																	<%//Pagina.trocaNullNumeroSemDecimal(itemEstrtIndResulDao.getSomaQuantidadePrevista(itemEstrtIndResul))%>
																	<%=itemEstrtIndResulDao.getSomaQuantidadePrevista(itemEstrtIndResul)%>
																</td>
															</tr>
								<%
														} else {
								%>							
															<tr><td class="form_label">Não há quantidades previstas</td></tr>
								<%
														}
								%>
													</table>
												</td>
											</tr>
								<%
									}
									
									
								}
												
									
						}
						%>
							</table>
							<%@ include file="../../include/estadoMenu.jsp"%>
							
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>


</form>


<%
} catch(ECARException e){
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>