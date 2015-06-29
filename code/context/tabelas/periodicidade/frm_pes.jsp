<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.PeriodicidadePrdc" %>
<%@ page import="comum.util.Pagina" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validarPesquisar(form){
	if(Trim(form.numMesesPrdc.value) != ""){
		if(!validaNum(form.numMesesPrdc, "Número de Meses", false)){
			alert("<%=_msg.getMensagem("periodicidade.validacao.numMesesPrdc.invalido")%>");
			form.numMesesPrdc.focus();
			return(false);
		}
		
		if(form.numMesesPrdc.value < 1){
			alert("<%=_msg.getMensagem("periodicidade.validacao.numMesesPrdc.invalido")%>");
			form.numMesesPrdc.focus();
			return(false);
		} 
	}
	
	return(true);
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
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<%
	PeriodicidadePrdc periodicidade;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		periodicidade = (PeriodicidadePrdc) session.getAttribute("objPesquisa");
	}else{
		periodicidade = new PeriodicidadePrdc();
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