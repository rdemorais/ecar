<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ItemEstrutUsuarioIettus" %>
<%@ page import="ecar.dao.ItemEstrutUsuarioDao" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<% 
	session.removeAttribute("objPesquisa");
%>


<%@page import="ecar.dao.EstruturaDao"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js" type="text/javascript"></script>

<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js" type="text/javascript"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil_cadastro.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/menu_cadastro.js"></script>

<script language="javascript" type="text/javascript">


function aoClicarAlterar(form, codIettus){
	form.codIettus.value = codIettus;
	form.hidAcao.value = "alterar";
	document.form.action = "frm_alt.jsp";
	document.form.submit();
} 
</script>

</head>

<body> 
 
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<%
try{
	ValidaPermissao validaPermissao = new ValidaPermissao();
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	String codAba = Pagina.getParamStr(request, "codAba");
	
	
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>

<form name="form" method="post" action="">

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
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr><td class="espacador"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
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
										<tr>
										<td class="espacador" colspan="14"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
										<tr class="linha_subtitulo">
											<td colspan="3">&nbsp;</td>
											<td colspan="3" align="center">Item na Estrutura</td>
											<td align="center" rowspan="2">Ler<br>Parecer</td>
											<td align="center" rowspan="2">Ativ.<br>Monit.</td>
											<td align="center" rowspan="2">Desativ.<br>Monit.</td>
											<td align="center" rowspan="2">Bloq.<br>Planej.</td>
											<td align="center" rowspan="2">Desb.<br>Planej.</td>
											<td align="center" rowspan="2">Manter<br>Prox.<br>Nivel</td>
											<td align="center" rowspan="2">Função</td>
											<td align="center" rowspan="2">Item</td>
										</tr>
										<tr class="linha_subtitulo">
											<td width="2%"><input type="checkBox" class="form_check_radio" name="todos" onclick="javascript:selectAll(document.form)"></td>
											<td>&nbsp;</td>
											<td>Nome</td>
											<td align="center">Ler</td>
											<td align="center">Alterar</td>
											<td align="center">Excluir</td>
										</tr>
										<tr><td class="espacador" colspan="14"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td></tr>
									<%
										if (itemEstrutura.getItemEstrutUsuarioIettusesByCodIett() != null){
											ItemEstrutUsuarioDao itemEstrutUsuarioDao = new ItemEstrutUsuarioDao(request);
											ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
											
											List lista = new ArrayList();
											lista.addAll(itemEstrutura.getItemEstrutUsuarioIettusesByCodIett());
											
											Iterator it = itemEstrutUsuarioDao.ordenaLista(lista).iterator();
											
											while (it.hasNext()) {
												itemEstrutUsuario = (ItemEstrutUsuarioIettus) it.next();
												
												String nome = "";
												
												if(itemEstrutUsuario.getUsuarioUsu() != null)
													nome = itemEstrutUsuario.getUsuarioUsu().getNomeUsuSent();
												else
													nome = itemEstrutUsuario.getSisAtributoSatb().getDescricaoSatb();
									%>		
												<tr class="linha_subtitulo2">
													<td width="2%">
									<%
													/* Só é possível alterar quando codIett e codIettOrigem forem iguais, e quando tipo de permissão diferente de F (Função de Acompanhamento) */
													if (itemEstrutUsuario.getItemEstruturaIett().getCodIett() == itemEstrutUsuario.getItemEstruturaIettOrigem().getCodIett()
															&& !"F".equals(itemEstrutUsuario.getCodTpPermIettus())){
									%>
														<input type="checkbox" class="form_check_radio" name="excluir" value="<%=itemEstrutUsuario.getCodIettus()%>">
									<%
													}
									%>
													</td>
													<td>
									<%
													if (itemEstrutUsuario.getUsuarioUsu() != null){
														//Imagem para Usuatio INAtivo
													 	String imagem_inativa = "";
														if (!"S".equals(itemEstrutUsuario.getUsuarioUsu().getIndAtivoUsu())){
															imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
														}
														else
															imagem_inativa="<img src=\"" + _pathEcar + "/images/icon_usuario.png\" title=\"Usuário\">";
													 	//Termina "Imagem para Usuario INAtivo"
									
									%>				
													<%=imagem_inativa%>
									<%
													} else {
									%>
														<img src="<%=_pathEcar%>/images/icon_grupo.png" title="Grupo" alt="">
									<%
													}
									%>
													</td>
													
													<td>
									<%
													if (itemEstrutUsuario.getItemEstruturaIett().getCodIett() == itemEstrutUsuario.getItemEstruturaIettOrigem().getCodIett()
															&& !"F".equals(itemEstrutUsuario.getCodTpPermIettus())){
									%>
														<a href="javascript:aoClicarAlterar(document.form,<%=itemEstrutUsuario.getCodIettus()%>)"><%=nome%></a>
									<%
													}else{
														out.print(nome);
													}
									%>
													</td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indLeituraIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndLeituraIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indEdicaoIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndEdicaoIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indExcluirIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndExcluirIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indLeituraParecerIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndLeituraParecerIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indAtivMonitIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndAtivMonitIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indDesatMonitIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndDesatMonitIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indBloqPlanIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndBloqPlanIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indDesblPlanIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndDesblPlanIettus(), "S")%> disabled></td>
													<td align="center"><input type="checkbox" class="form_check_radio" name="indProxNivelIettus<%=itemEstrutUsuario.getCodIettus()%>" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndProxNivelIettus(), "S")%> disabled></td>
													<td>
									<%
													if(!"F".equals(itemEstrutUsuario.getCodTpPermIettus())){
														out.print("Permissão");
													}else{
														out.print(itemEstrutUsuario.getTipoFuncAcompTpfa().getLabelTpfa());
													}
									%>
													</td>
													<td>
									<%
													if (itemEstrutUsuario.getItemEstruturaIett().getCodIett() == itemEstrutUsuario.getItemEstruturaIettOrigem().getCodIett()){
														out.print("Atual");
													}else{
														out.print(itemEstrutUsuario.getItemEstruturaIettOrigem().getNomeIett());
													}
									%>
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
	
</table>




<br><br>
<!-- TITULO -->



<!-- <div id="subconteudo">  -->


	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type="hidden" name="codAba" value='<%=Pagina.getParamStr(request, "codAba")%>'>
	<!--codigos-->
	<input type="hidden" name="codIettus" value="">



	
	
	
	<%@ include file="/include/estadoMenu.jsp"%>
</form>
<!-- </div>  -->
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>