<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.ConfigExecFinanCef" %>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>
<script language="javascript" src="../../js/numero.js"></script>

<script language="javascript">
function validarPesquisar(form){
	/*if(form.numCaracteresCef.value != ""){
		if(!validaNum(form.numCaracteresCef, "Número de Posições", true)){
			return(false);
		}
	}
	if(form.seqApresentacaoCef.value != ""){
		if(!validaNum(form.seqApresentacaoCef, "Número de Posições", true)){
			return(false);
		}
	}*/
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
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>
	
	<%
	ConfigExecFinanCef configExecFinan;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		configExecFinan = (ConfigExecFinanCef) session.getAttribute("objPesquisa");
	}else{
		configExecFinan = new ConfigExecFinanCef();
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