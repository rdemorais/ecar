<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<% 
session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
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
function aoClicarConsultar(form, cod){
	form.cod.value = cod;
	form.hidAcao.value = 'alterar';
	document.form.action = "frm_con.jsp";
	document.form.submit();
} 

function aoClicarVoltarRecurso(form){
	form.action = "../recurso/lista.jsp";
	form.submit();
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
try{
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaContaOrcamentoDao itemEstruturaContaOrcamentoDao = new ItemEstruturaContaOrcamentoDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")) , seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
	
	String codAba = Pagina.getParamStr(request, "codAba");
	long codFonr = Long.parseLong((Pagina.getParamStr(request, "codFonr").equals("")?"0":Pagina.getParamStr(request, "codFonr")));
	
	// Comentado devido ao BUG 4851, NÃO retirar a linha abaixo
	//boolean podeIncluir = itemEstruturaContaOrcamentoDao.verificaPossibilidadeInclusao(itemEstrutura);
	boolean podeIncluir = true;
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));

	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);

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

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<!--codigos-->
	<input type="hidden" name="cod" value="">


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
								<util:arvoreEstruturas 
									itemEstrutura="<%=itemEstrutura%>" 
									contextPath="<%=_pathEcar%>" 
									seguranca="<%=seguranca%>" 
									idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado")%>"
									ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
								<!-- ############### Árvore de Estruturas  ################### -->
								
								<!-- ############### Barra de Links  ################### -->
								<util:barraLinksItemEstrutura 
									estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
									selectedFuncao="<%=codAba%>" 
									codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" 
									contextPath="<%=request.getContextPath()%>" 
									idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
								<!-- ############### Barra de Links  ################### -->
								<br><br>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
									<tr class="linha_titulo" align="right">
										<td>
											<%if(permissaoAlterar.booleanValue() && podeIncluir){%>
											<!-- input type="button" class="botao" value="Adicionar <%=estruturaFuncao.getLabelEttf()%>" onclick="javascript:aoClicarIncluir(form);">&nbsp;
											<input type="button" class="botao" value="Excluir <%=estruturaFuncao.getLabelEttf()%>" onclick="javascript:aoClicarExcluir(form);">&nbsp; -->
											<input type="button" class="botao" value="Adicionar" onclick="javascript:aoClicarIncluir(form);">&nbsp;
											<input type="button" class="botao" value="Excluir" onclick="javascript:aoClicarExcluir(form);">&nbsp;
											<%}%>
											<input type="button" class="botao" value="Voltar para <%=estruturaFuncao.getLabelEttf()%>" onclick="javascript:aoClicarVoltarRecurso(form);">&nbsp;
										</td>
									</tr>
									</table>
									
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
									<tr class="linha_subtitulo">
										<td width="5%">
											<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
										</td>
										<td>	
											<%=ItemEstruturaContaOrcamentoDao.geraLabelCadastroEstruturaConta(request)%>
										</td>
										<td>Exerc&iacute;cios</td>
										<td>Categorias Econômicas</td>
										<td>Fontes</td>
										<td>Acumulado</td>				
									</tr>
									<tr><td class="espacador" colspan="6"><img src="../../images/pixel.gif"></td></tr>
									 
									<%if(podeIncluir){%>
									
												<%
												List lista = new ItemEstruturaContaOrcamentoDao(request).getAtivos(itemEstrutura, codFonr);
															
													if (lista != null){
														Iterator it = lista.iterator();
														
														EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
														
														while (it.hasNext()) {
															conta = (EfItemEstContaEfiec) it.next();
												%>		
																<tr class="linha_subtitulo2">
																	<td>
																		<input type="checkbox" class="form_check_radio" name="excluir" value="<%=conta.getCodEfiec()%>">
																	</td>
																	<td>
																		<a href="javascript:aoClicarConsultar(document.form,<%=conta.getCodEfiec()%>)">
																		<%=conta.getContaSistemaOrcEfiec()%></a>
																	</td> 
																	<td><%=conta.getExercicioExe().getDescricaoExe()%></td>
																	<td><%=conta.getFonteRecursoFonr().getNomeFonr()%></td>
																	<td><%=conta.getRecursoRec().getNomeRec()%></td>
																	<td>
																	<%if("S".equals(conta.getIndAcumuladoEfiec())) out.println("Sim");%>
																	<%if("N".equals(conta.getIndAcumuladoEfiec())) out.println("Não");%>
																	</td>
																</tr>
													
												<%
														}
													}
												%>
								<%} else {
											%>
											<tr><td colspan=5><%=_msg.getMensagem("itemEstrutura.contaOrcamento.inclusao.naoPermitida")%></td></tr>
											<%
								}%>
									</table>
							</td>
						</tr>
					</table>			
				</div>
				
			</td>
		</tr>
		
	</table>
	<%@ include file="../../include/estadoMenu.jsp"%>
</form>

</div>
<%
} catch(ECARException e){
//	Mensagem.alert(_jspx_page_context, e.getMessageKey());
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>