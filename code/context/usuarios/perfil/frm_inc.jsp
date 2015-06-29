<%@ include file="../../login/validaAcesso.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.PerfilPfl" %>
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
<script language="javascript">
function validarGravar(form){
	if(!validaString(form.descricaoPfl,'Descrição',true)){
		return(false);
	}
	
	if(form.nivelPfl.value == ""){
		alert("<%=_msg.getMensagem("perfil.validacao.nivelPfl.obrigatorio")%>");
		form.nivelPfl.focus();
		return(false);
	}
	
	if(form.indAreaReservadaPfl[0].checked == false && form.indAreaReservadaPfl[1].checked == false){
		alert("<%=_msg.getMensagem("perfil.validacao.indAreaReservadaPfl.obrigatorio")%>");
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
	<h1>Perfil</h1>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

<%
	PerfilPfl perfil = new PerfilPfl();
	_disabled = "";
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