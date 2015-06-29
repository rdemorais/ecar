
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.AcompRefItemLimitesArli" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.permissao.ValidaPermissao" %> 
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.pojo.ObjetoEstrutura"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>


<%

try{ 
/* carrega o usuário da session */
UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

/* item pai do item que está sendo cadastrado */  
AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request); 
AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));

ValidaPermissao validaPermissao = new ValidaPermissao();
if ( !validaPermissao.permissaoConsultaIETT( acompReferenciaItem.getItemEstruturaIett().getCodIett() , seguranca ) )
{
	response.sendRedirect( request.getContextPath() +  "/acessoIndevido.jsp" );
}	


List camposVariaveis = new ArrayList();
%>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/mascaraMoeda.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

</head>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 

<body>
<div id="conteudo"> 

<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<%@ include file="botoesAvancaRetrocede.jsp" %>
 <br>
	<util:barraLinksRegAcomp 
		acompReferenciaItem="<%=acompReferenciaItem%>"  
		selectedFuncao="DADOS_GERAIS"
		usuario="<%=usuario%>"
		primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
	/> 
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
		<tr class="linha_titulo" align="left">
			<td>
				Refer&ecirc;ncia: <%=acompReferenciaItem.getAcompReferenciaAref().getNomeAref()%>&nbsp;&nbsp;&nbsp;(<%=acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()%>)
			</td> 
		</tr>
		<tr class="linha_titulo" align="left">
			<td>
				M&ecirc;s/Ano: <%=acompReferenciaItem.getAcompReferenciaAref().getMesAref()%>/<%=acompReferenciaItem.getAcompReferenciaAref().getAnoAref()%>
			</td> 
		</tr>
		<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
</table>

<%
String nivelPrimeiroIettClicadoListaItens = Pagina.getParamStr(request, "nivelIettClicado");

if(!"".equals(nivelPrimeiroIettClicadoListaItens)) {
	request.getSession().setAttribute("nivelPrimeiroIettClicado", nivelPrimeiroIettClicadoListaItens);
}
%>

<util:arvoreEstruturaElabAcomp
	itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" 
	codigoAcomp="<%=Pagina.getParamStr(request, "codAcomp")%>"
	contextPath="<%=_pathEcar%>"
	listaParaArvore="<%=(java.util.List)session.getAttribute("listaParaArvore")%>"  
	nivelPrimeiroIettClicado="<%=(String)session.getAttribute("nivelPrimeiroIettClicado")%>"
	abaAtual="<%=ecar.taglib.util.ArvoreEstruturaElabAcompTag.ABA_DADOS_BASICOS%>"
	primeiroAcomp="<%=Pagina.getParamStr(request, "primeiroAcomp")%>"
/> 
<form name="form" method="post">
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type=hidden name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type=hidden name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">



	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
			<b>Dados Gerais</b>
			</td>
		</tr>
	</table>

	<table name="form" class="form" width="100%">
<%
EstruturaEtt estrutura = acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt();

EstruturaDao estruturaDao = new EstruturaDao(request);
List atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);


if(atributos != null){
	Iterator it = atributos.iterator();
	while(it.hasNext()){
		ObjetoEstrutura atributo = (ObjetoEstrutura) it.next();
		%>
			<util:formItemEstrutura atributo="<%=atributo%>" itemEstrutura="<%=acompReferenciaItem.getItemEstruturaIett()%>" desabilitar="<%=new Boolean(true)%>" seguranca="<%=seguranca%>" exibirBotoes="<%=new Boolean(false)%>" contextPath="<%=request.getContextPath()%>" request="<%=request %>"/>	
		<%
	} 
}
%>

</table>
<table class="barrabotoes" cellpadding="0" cellspacing="0">
	<tr>
		<td class="label" align="left">
		&nbsp;
		</td>
	</tr>
</table>
</form>

</div>
</body>
<%
} catch (ECARException e) {
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>

</html>