<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.RegApontamentoRegda" %>
<%@ page import="ecar.dao.RegApontamentoDao" %>

<%@ page import="comum.util.Data"%>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%> 

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validar(form){
	if(!validaString(form.infoRegda,'Texto',true)){
		return(false);
	}

	return(true);
}
function aoClicarGravar(form){
	if(validar(form)){
		form.hidAcao.value = "incluir";
		form.action = "apt_ctrl.jsp"
		form.submit();
	}
}	
function aoClicarCancelar(form){
	form.reset();
	focoInicial(form);
}
function onLoad(form) {
	aoClicarCancelar(form);
}
function aoClicarVoltar(form) {
	form.hidAcao.value = "";
	form.action = "frm_con.jsp";
	form.submit();
}
function aoClicarDemandas(form) {
	form.action = "lista.jsp";
	form.submit();
}
function aoClicarApontamento(form) {
	form.action = "frm_con.jsp";
	form.submit();
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type=hidden name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type=hidden name="hidAcao" value="">
	
	<%//Campos de ordenação da listagem de Demandas%>
	<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
	<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">
	
	<!-- TITULO -->
	<h1>Associação de Demanda</h1>
	
	<div id="linkstop">
		<a href="javascript:aoClicarDemandas(form);">Demandas</a>
		&nbsp;|&nbsp;
		<a href="javascript:aoClicarApontamento(form);">Apontamentos</a>
	</div>	

<%
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);
	RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
	
	String acao = "incluir";
%>
	
	<%@ include file="form_consulta.jsp"%>
		
	<br>
	
	<h1>Apontamento</h1>
	
	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	
<%
	RegApontamentoRegda regApontamento;
	
	if (session.getAttribute("objRegApontamento") != null) {
		regApontamento = (RegApontamentoRegda) session.getAttribute("objRegApontamento");
		session.removeAttribute("objRegApontamento");
	} else {
		regApontamento = new RegApontamentoRegda();
	}
%>

	<table class="form">
		<%@ include file="apt_form.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>
	
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>