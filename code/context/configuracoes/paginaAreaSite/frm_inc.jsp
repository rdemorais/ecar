<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.PaginaAreaSitePa" %>
<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");

boolean ehPesquisa = false;

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
	if(!validaString(form.nomePas, "Nome", true)){
		return(false);
	}
	if(!validaString(form.textoPas, "Texto", true)){
		return(false);
	}
	if(form.indCapaPas[0].checked == false && form.indCapaPas[1].checked == false){
		alert("<%=_msg.getMensagem("paginaAreaSite.validacao.indCapaPas.obrigatorio")%>");
		return(false);
	}
	if(!validaString(form.urlPas, "URL", true)){
		return(false);
	}
	if(!validaString(form.corPas, "Cor", true)){
		return(false);
	}
	if(!validaString(form.seqApresentacaoPas, "Seqüência", true)||!validaNum(form.seqApresentacaoPas, "Seqüência", true)){
		return(false);
	}
	return(true);
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
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%
	PaginaAreaSitePa paginaAreaSite = new PaginaAreaSitePa();
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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>