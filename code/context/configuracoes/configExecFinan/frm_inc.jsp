<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.ConfigExecFinanCef" %>
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
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/numero.js"></script>

<script language="javascript">

function validarGravar(form){ 
	if(!validaString(form.nomeCef,"Nome",true)){
		return(false);
	}
	if(!validaString(form.labelCef,"Label para utilizar nas telas",true)){
		return(false);
	}
	if(!validaString(form.numCaracteresCef,"Número de Posições",true)||!validaNum(form.numCaracteresCef,"Número de Posições",true)){
		return(false);
	}
	if(form.configTipoDadoCtd.value == ""){
		alert("<%=_msg.getMensagem("configExecFinan.validacao.configTipoDadoCtd.obrigatorio")%>");
		form.configTipoDadoCtd.focus();
		return(false);	
	}
	if(form.configSisExecFinanCsefv.value == ""){
		alert("<%=_msg.getMensagem("configExecFinan.validacao.configSisExecFinanCsefv.obrigatorio")%>");
		form.configSisExecFinanCsefv.focus();
		return(false);	
	}
	if(!validaString(form.seqApresentacaoCef, "Seqüência de Apresentação", true)||!validaNum(form.seqApresentacaoCef, "Seqüência de Apresentação", true)){
		return(false);
	}
	return(true);
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">
<%
try{
%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%
	ConfigExecFinanCef configExecFinan = new ConfigExecFinanCef();
	_disabled = "";
	%>

	<table class="form">
	<%@ include file="form.jsp"%>

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
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>