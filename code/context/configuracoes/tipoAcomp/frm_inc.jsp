<%@ include file="/frm_global.jsp"%>
<%@ include file="/login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="org.apache.log4j.Logger"%>
<%
	session.removeAttribute("objPesquisa");
	boolean ehPesquisa = false;
	boolean ehIncluir = true;
%>
<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js"></script>

<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/numero.js"></script>
<%@ include file="validacao.jsp" %>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%
	TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);
	TipoAcompanhamentoTa tipoAcomp 	   = new TipoAcompanhamentoTa();
	
	_disabled = "";
	String _pesqdisabled = "";
	
	String _disabledOrgao = _pesqdisabled;
%>

	<util:linkstop pesquisar="frm_pes.jsp"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	<%@ include file="form.jsp" %>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
</form>

</div>
<%
} catch (Exception e){
	Logger.getLogger(this.getClass()).error(e);
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>