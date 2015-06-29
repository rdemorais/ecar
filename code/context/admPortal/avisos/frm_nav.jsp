<%@ page import="ecar.pojo.PopupPpp" %>
<%@ page import="ecar.dao.PopUpDao" %>

<%@ page import="java.util.List" %>

<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%
int hidRichText;
	hidRichText = Pagina.getParamInt(request, "hidRichText");
%> 

<%
PopupPpp popUp = new PopupPpp();
PopUpDao popUpDao = new PopUpDao(request);
_disabled = "disabled";
_readOnly = "readonly";
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadraoAdmPortal.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>

<script language="JavaScript">
</script>

</head>

<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<% 
String linkIncluir = "frm_inc.jsp";
String linkPesquisar = "frm_pes.jsp?hidRichText=1";
%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRichText" value="<%=hidRichText%>">	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	
	<util:linkstop incluir="<%=linkIncluir%>" pesquisar="<%=linkPesquisar%>"/>
	

<%
	try {		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = 0;
		if(!"".equals(Pagina.getParamStr(request, "hidRegistro")))
			hidRegistro = Integer.valueOf(Pagina.getParamStr(request, "hidRegistro")).intValue(); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		popUp = (PopupPpp) lista.get(hidRegistro);		
%>

	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>

	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=popUp.getCodPpp()%>">
	
	<table class="form">
	<%@ include file="form.jsp"%>
	</table>
	
	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir"/>

<%
	} catch (Exception ex) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(ex);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
		<%
	}
%>
	
	
</form>

</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>