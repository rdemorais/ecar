<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>

<%@ page import="java.util.Iterator" %>  
<%@ page import="java.util.List" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.dao.PeriodoRevisaoPrevDao" %>
<%@ page import="comum.database.Dao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@page import="ecar.pojo.EstruturaEtt"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%> 
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/tableSort.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">

function aoClicarConsultar(form, codigo){
	form.codIettrev.value = codigo;
	form.hidAcao.value = 'alterar';
	document.form.action = "frm_con.jsp";
	document.form.submit();
} 

</script>
 
</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body> 
<div id="conteudo">

<% 
try{
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")) , seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
	PeriodoRevisaoPrevDao prevDao = new PeriodoRevisaoPrevDao(request);
	String codAba = Pagina.getParamStr(request, "codAba");
	
	
	
%>
<div id="tituloTelaCadastro">
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%> 
</div>
<%
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

<form name="form" method="post">

<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="hidAcao" value="">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="codIettrev" value="">
 
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
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
								ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/>
							<!-- ############### Árvore de Estruturas  ################### -->
							
							<!-- ############### Barra de Links  ################### -->
							<util:barraLinksItemEstrutura 
												estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
												selectedFuncao="<%=codAba%>" 
												codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>" contextPath="<%=request.getContextPath()%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
							<!-- ############### Barra de Links  ################### -->
							<br><br>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_titulo" align="right">
									<td  colspan="3">
									<%if(prevDao.existePeriodo(Data.getDataAtual())){%>
										<%if(permissaoAlterar.booleanValue()){%>
										<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
										<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
										<%}%>
									<%}%>
									</td>
								</tr>
							</table>
								<!-- ############### Listagem  ################### -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<thead> 
										<tr><td class="espacador" colspan=5><img src="../../images/pixel.gif"></td></tr>
										<tr class="linha_subtitulo">
											<td width="3%">
												<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
											</td>
											<td width="9%">
												<a href="#" onclick="this.blur(); return sortTable('corpo',  1, true);" title="Data">Data</a>
											</td>
											<td width="44%">
												<a href="#" onclick="this.blur(); return sortTable('corpo',  2, true);" title="Justificativa">Justificativa</a>
											</td>
											<td width="44%">
												<a href="#" onclick="this.blur(); return sortTable('corpo',  3, true);" title="Usuário">Usuário</a>
											</td>
										</tr>
										<tr><td class="espacador" colspan="5"><img src="../../images/pixel.gif"></td></tr>	
								</thead>
								<tbody id="corpo">	
								<%
									if(itemEstrutura.getItemEstruturarevisaoIettrevs() != null ){
										List acoes = itemEstruturaDao.ordenaSet(itemEstrutura.getItemEstruturarevisaoIettrevs(), "this.dataInclusaoIettrev", Dao.ORDEM_DESC);
										Iterator itRevisao = acoes.iterator();
										while(itRevisao.hasNext()){
											ItemEstruturarevisaoIettrev ieRevisao = (ItemEstruturarevisaoIettrev) itRevisao.next();
											%>
											<tr class="linha_subtitulo2">
												<td>
											<%if ((prevDao.estaNoPeriodoAtual(ieRevisao.getDataInclusaoIettrev()))){%>
												<%if ((ieRevisao.getUsuarioUsuByCodUsuIncIettrev().equals(seguranca.getUsuario()))) { %>
													<input type="checkbox" class="form_check_radio" name="excluir" value="<%=ieRevisao.getCodIettrev()%>">
												<%}else{%>
													<img src="../../images/cadeado.gif" title="Usuário não autorizado">
												<%}%>
											<%}else{%>
												<img src="../../images/cadeado.gif" title="Revisão fora do Ciclo Atual">
											<%}%>
											
												</td>
												<td>						
													<a href="javascript:aoClicarConsultar(document.form,<%=ieRevisao.getCodIettrev()%>)">
													<%=Pagina.trocaNullData(ieRevisao.getDataInclusaoIettrev())%></a>
												</td>
										<%
											String justificativa = ieRevisao.getJustificativaIettrev().replaceAll("\n","<br>");
										%>
												<td><%=justificativa%></td>
												<td><%=ieRevisao.getUsuarioUsuByCodUsuIncIettrev().getNomeUsuSent()%></td>
											</tr>	
											<%
										} 
									}
								%>
									</tbody>
							</table>

						
						</td>
					</tr>
				</table>			
			</div>
			
		</td>
	</tr>
	
</table>
</form>
</div>
</body>
	<%
} catch ( ECARException e ){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	<!-- ############### Listagem  ################### -->


<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
