<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrutLocalIettl" %>
<%@ page import="ecar.dao.ItemEstrutLocalDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<% 
session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/frmPadraoItemEstrut.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/listaItensCadastro.js" type="text/javascript"></script>		 
<script language="javascript" src="<%=_pathEcar%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">jQuery.noConflict();</script>		
<script language="javascript" src="<%=_pathEcar%>/js/cookie.js"></script>

<script language="javascript">
function aoClicarListar(form){

	if (form.codLgp.value != "") {
		form.action = "frm_inc.jsp";
		form.submit();
	} else {
		alert ('<%=_msg.getMensagem("localItem.abragencia.vazia")%>');
	}
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
							
							<div id="subconteudo">


								<input type="hidden" name="hidAcao" value="">
								<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
								<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
									
							
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_titulo" align="center">
									<td> Abrag&ecirc;ncia
										<combo:ComboTag 
												nome="codLgp"
												objeto="ecar.pojo.LocalGrupoLgp"
												label="identificacaoLgp"
												value="codLgp"
												filters="indAtivoLgp=S"
												order="identificacaoLgp"
										/>
									</td>
								</tr>
								</table>
								
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_subtitulo" align="center">
									<td>
										Para inserir um outro local relativo a outra abrangência,<br>
										é necessário excluir todos os locais já relacionados.<br>
										Somente é possivel uma abrangência por item.
									</td>
								</tr>
								<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
								<tr class="linha_subtitulo2" align="center">
									<td>
										<input type="button" class="botao" value="Listar" onclick="javascript:aoClicarListar(form);">
									</td>
								</tr>
								</table>
								<%@ include file="../../include/estadoMenu.jsp"%>
							
							
							</div>
						
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