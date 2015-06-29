<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.SubAreaSare" %>
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
	if(!validaNum(form.codigoIdentSare, "Código", false)){
		alert("<%=_msg.getMensagem("subArea.validacao.codigoIdentSare.invalido")%>");
		form.codigoIdentSare.focus();
		return(false);
	}
	if(!validaString(form.nomeSare,"Nome",true)){
		return(false);
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
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

	<%
	SubAreaSare subArea = new SubAreaSare();
	
	if(session.getAttribute("objSubArea") != null){
		subArea = (SubAreaSare) session.getAttribute("objSubArea");
		session.removeAttribute("objSubArea");
	}else{
		subArea = new SubAreaSare();
  	}
	
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
<%@ include file="../../include/mensagemAlert.jsp" %>
</html>