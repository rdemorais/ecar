<jsp:directive.page import="org.apache.log4j.Logger"/>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>


<html lang="pt-br">
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/frmPadrao.js"></script>
<%@ include file="validacao.jsp"%>

</head>
<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%
	boolean ehPesquisa = false;
	boolean ehIncluir = false;

	try {
		TipoAcompanhamentoTa tipoAcomp = new TipoAcompanhamentoTa();
		TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);

		_disabled = "disabled";
		_readOnly = "readonly";
		
		String _disabledOrgao = _disabled;
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		tipoAcomp = (TipoAcompanhamentoTa) lista.get(hidRegistro);
		String _pesqdisabled = "disabled";
		
%>
 
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
			
		<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
		<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
		<input type="hidden" name="codigo" value="<%=tipoAcomp.getCodTa()%>">
		
			<%@ include file="form.jsp"%>

<%
	} catch (Exception e) {
		Logger.getLogger(this.getClass()).error(e);
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>