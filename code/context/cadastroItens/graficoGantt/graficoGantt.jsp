
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="comum.util.Data"/>
<jsp:directive.page import="ecar.pojo.PontoCriticoPtc"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>

<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
 
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>


<%@page import="ecar.dao.PontoCriticoDao"%><html lang="pt-br">
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
							
							
							<%
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
	
	String labelPontosCriticos = estruturaFuncaoDao.getLabelFuncaoPontosCriticos(itemEstrutura.getEstruturaEtt());
%>

<br><br>
<!-- TITULO -->


	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<!--codigos-->
	<input type="hidden" name="codIettir" value="">

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
	<tr><td class="espacador" colspan="9"><img src="../../images/pixel.gif"></td></tr>
	
	<H1>&nbsp; Gráfico de <%=labelPontosCriticos%></H1>
	
	<tr> 
		<td>
		<%
			request.getSession().removeAttribute("listPontosCriticos");
			PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			List pontosCriticosSolucionados = new ArrayList(pontoCriticoDao.getPontosCriticosAtivosOrdenadosDataLimite(itemEstrutura, false));
		    
			if (pontosCriticosSolucionados != null && pontosCriticosSolucionados.size() > 0){
				
		    	Date dataAtual = Data.getDataAtual();
				String dataBaseStr = Data.getAno(dataAtual) + "/" + 
									(Data.getMes(dataAtual)+1) + "/" +
									Data.getDia(dataAtual);
									
				Date dataBase = new Date(dataBaseStr);    	
				request.getSession().setAttribute("listPontosCriticos", pontosCriticosSolucionados);
			
				 
				%>
				<table>
					<tr>
						<td valign="top" align="center">
							<img src="<%=_pathEcar%>/GraficoGanttPontosCriticos?dataBase=<%=dataBaseStr%>&labelPontosCriticos=<%=labelPontosCriticos%>">
						</td>
					</tr>				
				</table>
			
		<%	
			} else{
					
				%>
					<table width="100%">
					<tr>
						<br><br>					
						<td valign="top" align="center">
							<h1>Não há restrições cadastradas com data limite informada</h1>
						</td>
					</tr>				
					</table>
				
				<%
				}
		%> 	
		</td>
	</tr>
	
	</table>
	<%@include file="../../include/estadoMenu.jsp"%>
						
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