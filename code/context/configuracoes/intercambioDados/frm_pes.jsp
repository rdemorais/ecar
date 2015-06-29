<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="comum.util.Pagina" %>


<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link href="<%=_pathEcar%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript">
function validarPesquisar(form){
	return(true);
}


function caminhoRetorno(){
	return "frm_pes.jsp";
}

function iniciarProcesso(hidAcao){
	document.form.hidAcao.value=hidAcao;
	document.form.action = "ctrl.jsp";
	
	document.form.submit();	
}


</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">
<%

boolean ehPesquisa = true;
boolean exibirDadosManutencao = false;
_obrigatorio = "";

%>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="jspOrigem" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="javascript:iniciarProcesso('iniciarIncluir');" pesquisar="javascript:iniciarProcesso('iniciarPesquisa');"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%@ include file="form.jsp"%>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
</form>

</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>