<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>

<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>

<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.dao.SitDemandaDao" %>

<%@ page import="comum.util.Data"%>

<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");

RegDemandaRegd regDemanda = new RegDemandaRegd();
RegDemandaDao regDemandaDao = new RegDemandaDao(request);
SitDemandaDao sitDemandaDao = new SitDemandaDao(request);

if(session.getAttribute("objRegDemanda") != null){
	regDemanda = (RegDemandaRegd) session.getAttribute("objRegDemanda");
	session.removeAttribute("objRegDemanda");
}else{
	regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf (Pagina.getParam(request, "codRegd")));
}
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
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
<script language="javascript">

function ativarCamposEstrutura(){
	if (document.form.descricaoRegd!= null) {
		document.form.descricaoRegd.disabled=false;
	}
	
	if (document.form.dataLimiteRegd!= null) {
		document.form.dataLimiteRegd.disabled=false;
	} 
	 
	if (document.form.orgaoOrg!= null) {
		document.form.orgaoOrg.disabled=false;
	} 

	if (document.form.sitDemandaSitd!= null) {
		document.form.sitDemandaSitd.disabled=false;
	} 

	if (document.form.prioridadePrior!= null) {
		document.form.prioridadePrior.disabled=false;
	} 

	if (document.form.indAtivoRegd!= null) {
		document.form.indAtivoRegd.disabled=false;
	} 

	if (document.form.numeroDocOrigemRegd!= null) {
		document.form.numeroDocOrigemRegd.disabled=false;
	} 

	if (document.form.regDemandaRegd!= null ) {
		document.form.regDemandaRegd.disabled=false;
	} 
	
	if (document.form.nomeSolicitanteRegd!= null) {
		document.form.nomeSolicitanteRegd.disabled=false;
	} 
	
	if (document.form.dataSolicitacaoRegd!= null) {
		document.form.dataSolicitacaoRegd.disabled=false;
	} 
	
	if (document.form.observacaoRegd!= null) {
		document.form.observacaoRegd.disabled=false;
	} 
	
	if (document.form.codRegd!= null) {
		document.form.codRegd.disabled=false;
	}
	
	if (document.form.indAtivoRegd!= null) {
		document.form.indAtivoRegd.disabled=false;
	} 

}


function aoClicarAlterar(form){
	if(validar(form)){
		form.hidAcao.value = "classificar";
		ativarCamposEstrutura();/*Ativar os campos que estão bloqueados na estrutura*/
		form.action = "ctrl.jsp"
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
	form.action = "lista.jsp";
	form.submit();
}
</script>

<%String acao = "classificar";%>
<%@ include file="funcoes.jsp"%>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{

	//Guarda a pagina que chamou a funcionalidade 
	int hidNumPagina = Pagina.getParamInt(request, "hidNumPagina");
	int hidTotPagina = Pagina.getParamInt(request, "hidTotPagina");
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="iniSituacao" value="">
	<input type="hidden" name="hidNumPagina" value="<%=hidNumPagina%>">
	<input type="hidden" name="hidTotPagina" value="<%=hidTotPagina%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%

	_disabled = "disabled";
	_readOnly = "readonly";
	_obrigatorio = "";
%>
	<script>
		form.iniSituacao.value = "<%=regDemanda.getSitDemandaSitd().getCodSitd()%>";
	</script>
	
	
	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
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