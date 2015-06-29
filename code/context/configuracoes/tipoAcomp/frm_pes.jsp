<%@ include file="/login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.TipoAcompFuncAcompTafc" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.TipoAcompFuncAcompDao" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ include file="/frm_global.jsp"%>

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
<%@ include file="validacao.jsp"%>
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

	<util:linkstop incluir="frm_inc.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>
<%
	TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);
	TipoAcompanhamentoTa tipoAcomp 	   = new TipoAcompanhamentoTa();

	boolean ehPesquisa = true;
	boolean ehIncluir = false;
	
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		tipoAcomp = (TipoAcompanhamentoTa) session.getAttribute("objPesquisa");
	}else{
		tipoAcomp = new TipoAcompanhamentoTa();
  	}
	
	_disabled = "";
	_obrigatorio = "";
	_comboSimNao = "Todos";
	String _pesqdisabled = "disabled";
	String _disabledOrgao = _pesqdisabled;
%>
	<%@ include file="form.jsp"%>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
	
</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>