<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.Cor" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<!-- Para uso do componente de cor -->
<script language="javascript" src="<%=_pathEcar%>/js/ColorPicker2.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/PopupWindow.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/AnchorPosition.js" type="text/javascript"></script>

<script language="javascript">

/* Para uso da paleta de cores */
var field = "";
// Create a new ColorPicker object using Window Popup
var cp = new ColorPicker('window');

function pickColor(color) {
	field.value = color;
}

function selecionarCor(campo, pick){
	field = document.getElementById(campo);
	cp.select(field, pick);		
}

function validarPesquisar(form){
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%

boolean ehPesquisa = true;

try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%
	Cor cor;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		/* deve recuperar o que foi digitado se o objeto existe na sessao e o resultado da pesquisa foi falso */
		cor = (Cor) session.getAttribute("objPesquisa");
	}else{
		cor = new Cor();
  	}
	_disabled = "";
	_obrigatorio = "";
	%>

	<table class="form">
	<%@ include file="form.jsp"%>
	</table>

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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>