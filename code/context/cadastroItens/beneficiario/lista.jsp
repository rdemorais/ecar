<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtBenefIettb" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<%@ page import="comum.util.Util" %>

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<html lang="pt-br">
<head> 
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">

function aoClicarConsultar(form, codigo){
	form.codBnf.value = codigo;
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
	EstruturaDao estruturaDao = new EstruturaDao(request);
	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
	ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
	if ( !validaPermissao.permissaoConsultaIETT(  Long.valueOf(Pagina.getParamStr(request, "codIett")), seguranca ) )
	{response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
	}
	
	
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
%>

<br><br>


<form name="form" method="post">

<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="hidAcao" value="">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="codBnf" value="">


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
							<util:arvoreEstruturas 
								itemEstrutura="<%=itemEstrutura%>" 
								contextPath="<%=_pathEcar%>" 
								idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
								ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido") %>"/> 
							<!-- ############### Árvore de Estruturas  ################### -->
							
							<!-- ############### Barra de Links  ################### -->
							<util:barraLinksItemEstrutura 
									estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
									selectedFuncao="<%=codAba%>"
									codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
									contextPath="<%=request.getContextPath()%>"
									idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"
									/>
							<!-- ############### Barra de Links  ################### -->
							<br><br>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_titulo" align="right">
									<td>	
										<%if(permissaoAlterar.booleanValue()){%>
											<input type="button" value="Adicionar" class="botao" onclick="javascript:aoClicarIncluir(document.form)"> 
											<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(document.form)">
										<%}%>
									</td>
								</tr>
							</table>
								<!-- ############### Listagem  ################### -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">	
								<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_subtitulo">
									<td width="2%"><input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)"></td>
									<td>Nome</td>
									<td>Qtde.Prevista</td>
									<td>Coment&aacute;rios</td>
								</tr>
								<tr><td class="espacador" colspan="4"><img src="../../images/pixel.gif"></td></tr>	
								
							<%
									/* Percorre a lista de Beneficiários de ItemEstrutura e imprime na tela */
									if(itemEstrutura.getItemEstrtBenefIettbs() != null ){
										List beneficiarios =itemEstruturaDao.ordenaSet(itemEstrutura.getItemEstrtBenefIettbs(), "this.beneficiarioBnf.nomeBnf", "asc");
										Iterator itBenef = beneficiarios.iterator();
										while(itBenef.hasNext()){
											ItemEstrtBenefIettb ieBenef = (ItemEstrtBenefIettb) itBenef.next();
											
											//Deve exibir o beneficiário quando o indicativo de exclusão estiver nulo ou falso(indicando que não está excluido).  
											if (ieBenef.getIndExclusaoPosHistorico() == null || !ieBenef.getIndExclusaoPosHistorico().booleanValue()) {
												%>
												<tr class="linha_subtitulo2">
													<td width="2%"><input type="checkbox" class="form_check_radio" name="excluir" value="<%=ieBenef.getBeneficiarioBnf().getCodBnf()%>"></td>
													<td>
														<a href="javascript:aoClicarConsultar(document.form,<%=ieBenef.getBeneficiarioBnf().getCodBnf()%>)">
														<%=ieBenef.getBeneficiarioBnf().getNomeBnf()%></a>
													</td>
													<td><%=Util.formataNumeroSemDecimal(ieBenef.getQtdPrevistaIettb().doubleValue())%></td>
													<td><%=ieBenef.getComentarioIettb()%></td>
												</tr>	
												<%
											}
										} 
									}
						%>

							</table>
						</td>
					</tr>
				</table>			
			</td>
		</tr>
	
	</table>
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

<br>
</div>
</form>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>

