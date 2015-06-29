<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<%--<script language="javascript">
function onloadNav(form) {
	if( form.indEmitePosicaoTpfa[0].checked == true) {
        document.getElementById('emitePosicao').style.display = 'block';
    }
} 
</script>
--%></head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<%--<body onload="javasript:onloadNav(document.form);">

--%>
<body>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<%

boolean ehPesquisa = false;

	try {
		TipoFuncAcompTpfa tipoFuncAcomp = new TipoFuncAcompTpfa();
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
		_disabled = "disabled";
		_readOnly = "readonly";
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		tipoFuncAcomp = (TipoFuncAcompTpfa) lista.get(hidRegistro);
		
		//Excluir as funções filhos, neto **********************
		List funcoesFilho = new ArrayList();
		
		//List funcoesPermitidos = new ArrayList();
	   	//funcoesPermitidos = tipoFuncAcompDao.getTipoFuncAcompPermitidos(funcoesFilho);
	   	
	   	String strReadOnly = "true";
		boolean blVisualizaDesc = true;
		String _pesqdisabled = "disabled";
%>
<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=tipoFuncAcomp.getCodTpfa()%>">
		
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
		
		<%@ include file="form.jsp"%>
		
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir"/>
<%
	} catch (Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
		%>
		<script language="javascript" type="text/javascript">
		document.form.action = "frm_pes.jsp";
		document.form.submit();
		</script>
<%
	}
%>
</form>
</div>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>