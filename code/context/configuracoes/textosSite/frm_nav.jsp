<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.TextosSiteTxt" %>
<%@ page import="ecar.dao.TextosSiteDao" %>
<%@ page import="java.util.List" %>
<%@ include file="../../frm_global.jsp"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="JavaScript" src="../../js/frmPadrao.js"></script>

</head>

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<h1>Texto do Sistema</h1>

<%
	try {
		TextosSiteTxt textoSite = new TextosSiteTxt();
		_disabled = "disabled";
		_readOnly = "readonly";
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		textoSite = (TextosSiteTxt) lista.get(hidRegistro);
%>
 
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
			
		<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
		<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">

		<input type="hidden" name="codigoTxts" value="<%=textoSite.getCodTxtS()%>">
		<input type="hidden" name="codigoEmp" value="<%=textoSite.getEmpresaEmp().getCodEmp()%>">
		<input type="hidden" name="codigoIdm" value="<%=textoSite.getIdiomaIdm().getCodIdm()%>">				
		
		<table class="form">
		<%@ include file="form.jsp"%>
		</table>
<%
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
	}
%>
	
	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir"/>
	
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>