<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.UnidadeOrcamentariaUO" %> 
<%@ include file="../../frm_global.jsp"%>

<%session.removeAttribute("objPesquisa");%>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript">

function validarGravar(form){ 
	if(form.orgaoOrg.value == ""){
		alert("<%=_msg.getMensagem("unidadeOrcamentaria.validacao.orgaoOrg.obrigatorio")%>");
		form.orgaoOrg.focus();
		return(false);
	}
	if(!validaNum(form.codigoIdentUo, "Código", false)){
		alert("<%=_msg.getMensagem("unidadeOrcamentaria.validacao.codigoIdentUo.invalido")%>");
		form.codigoIdentUo.focus();
		return(false);
	}
	if(!validaString(form.descricaoUo, "Nome Unidade", true)){
		return(false);
	}
	if(!validaString(form.siglaUo, "Sigla", true)){
		return(false);
	}
	
	return(true);
}

function limpar(form){

	form.codUsu.value = "";
	form.nomeUsu.value = "";
}

function getDadosPesquisa(codigo, descricao){
	document.form.codUsu.value = codigo;
	document.form.nomeUsu.value = descricao;
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">
<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%
	UnidadeOrcamentariaUO unidade;
	
	if(session.getAttribute("objUnidadeOrcamentaria") != null){
		unidade = (UnidadeOrcamentariaUO) session.getAttribute("objUnidadeOrcamentaria");
		session.removeAttribute("objUnidadeOrcamentaria");
	}else{
		unidade = new UnidadeOrcamentariaUO();
  	}
	
	_disabled = "";

	String comboAdm = "";
	String comboOrc = "";
	%>
	<table class="form">
	<%@ include file="form.jsp"%>
	</table>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
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