<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.VisaoDemandasVisDem" %>
<%@ page import="comum.util.Pagina" %>


<%@page import="ecar.pojo.VisaoDemandasVisDem"%><html lang="pt-br">
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
</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">
<%

boolean ehPesquisa = true;
boolean ehIncluir = false;

try{

%>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

	<%
	VisaoDemandasVisDem visaoDemandas;
	if(session.getAttribute("objPesquisa") != null && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		visaoDemandas = (VisaoDemandasVisDem) session.getAttribute("objPesquisa");
	}else{
		visaoDemandas = new VisaoDemandasVisDem();
  	}
	_disabled = "";
	_obrigatorio = "";
    _comboSimNao = "Todos";
    String strReadOnly = "false";
	boolean blVisualizaDesc = false;
	String _pesqdisabled = "disabled";
%>
	<%@ include file="form.jsp"%>
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