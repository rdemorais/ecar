<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ItemEstrutUsuarioIettus" %>
<%@ page import="ecar.dao.ItemEstrutUsuarioDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadraoItemEstrut.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/popUp.js" type="text/javascript"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript" type="text/javascript">
<%@ include file="validacao.jsp"%>
/*
 * Seleciona os dados da janela de pesquisa
 * E como uma interface que deve ser implementada para receber dados de uma janela de pesquisa
 * Sempre deve receber um código e uma descricao
 */
function getDadosPesquisa(codigo, descricao){
	document.form.codUsu.value = codigo;
	document.form.nomeUsu.value = descricao;
}
function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	 
}
</script>

</head>

<body onload="focoInicial(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<%
try{
	
	boolean ehInclusao = true;
	
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

<form name="form" method="post" action="">

<input type="hidden" name="hidAcao" value="">

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
							<util:arvoreEstruturas itemEstrutura="<%=itemEstrutura%>" contextPath="<%=_pathEcar%>" seguranca="<%=seguranca%>" idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>" ultimoIdLinhaExpandido="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"/> 
	
							<util:barraLinksItemEstrutura 
									estrutura="<%=itemEstrutura.getEstruturaEtt()%>" 
									selectedFuncao="<%=codAba%>" 
									codItemEstrutura="<%=itemEstrutura.getCodIett().toString()%>"
									contextPath="<%=request.getContextPath()%>"
									idLinhaCadastro="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado") %>"/>
							<br><br>
							
													<%
							EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
							EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
							
							estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
						%>
																		
						<!-- TITULO -->
						
						
							
							<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
						
						<%
							ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
							_disabled = "";
							
							
							
							
						%>
						
							<%@ include file="form.jsp"%>
						
							<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
												
												</td>
											</tr>
										</table>			
									</div>
									
								</td>
							</tr>
	
</table>




	
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>