<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.TextosSiteTxt" %>
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
<script language="javascript" src="../../js/email.js"></script>

<script language="javascript">

function validarGravar(form){
	if(form.empresaEmp.value == ""){
		alert("<%=_msg.getMensagem("textoSite.validacao.empresaEmp.obrigatorio")%>");
		form.empresaEmp.focus();
		return(false);		
	}

	if(form.idiomaIdm.value == ""){
		alert("<%=_msg.getMensagem("textoSite.validacao.idiomaIdm.obrigatorio")%>");
		form.idiomaEmp.focus();
		return(false);		
	}

	if(!validaString(form.descricaoUsoTxts, "Descrição", true)){
		return false;		
	}	

	if(!validaString(form.textoTxts, "Texto", true)){
		return false;		
	}	
	
	if( form.emailResponsavelTxts.value != '' ) {
		if( !validaEmail(form.emailResponsavelTxts.value) ) {
			alert('<%=_msg.getMensagem("textoSite.validacao.emailResponsavel.invalido")%>');
			form.emailResponsavelTxts.focus();
			return false;
		}
	}
		
	return true;
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
	TextosSiteTxt textoSite = new TextosSiteTxt();
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