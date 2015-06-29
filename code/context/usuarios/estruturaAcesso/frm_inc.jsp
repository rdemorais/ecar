<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>


<%@ page import="org.apache.log4j.Logger"%>

<%
	final String CONSULTA = "1";
	final String ALTERA   = "2";
	
	String _action = Pagina.getParamStr(request,"hidAcao");
	
	if(_action == null || "".equals(_action)) _action = CONSULTA;
	
	if(CONSULTA.equals(_action))  _disabled = "disabled";
 %>
<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
<%@ include file="validacoes.jsp" %>
</script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="frm_inc.jsp">
	<input type="hidden" name="hidAcao" value="<%=_action%>">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
<%
try{
	if(CONSULTA.equals(_action)){
%>
	<util:barrabotoes alterar="Alterar" cancelar="Cancelar"/>
<%
	} 
	else{
%>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
<%	
	}
%>
	<%@ include file="form.jsp" %>
<%
	if(CONSULTA.equals(_action)){
%>
	<util:barrabotoes alterar="Alterar" cancelar="Cancelar"/>
<%
	} 
	else{
%>
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
<%	
	}
}
catch(Exception e){
	Logger.getLogger(this.getClass()).error(e);
	e.printStackTrace();
}
%>	
	
</form>
</div>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>