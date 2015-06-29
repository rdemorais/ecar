<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.UnidadeOrcamentariaUO" %>
<%@ page import="ecar.dao.UnidadeOrcamentariaDao" %>
<%@ page import="java.util.List" %>
<%@ include file="../../frm_global.jsp"%>

<%
UnidadeOrcamentariaDao unidadeDao = new UnidadeOrcamentariaDao(request);
UnidadeOrcamentariaUO unidade = new UnidadeOrcamentariaUO();
_disabled = "disabled";
_readOnly = "readonly";

String comboAdm = "";
String comboOrc = "";
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="JavaScript" src="../../js/frmPadrao.js"></script>

<script language="JavaScript">
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>

<%
	try {
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		unidade = (UnidadeOrcamentariaUO) lista.get(hidRegistro);
%>

	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=unidade.getCodUo()%>">
	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
	<table class="form">
	<%@ include file="form.jsp"%>
	</table>
	<util:barrabotoes navegacao="" btn1="Alterar" excluir="Excluir"/>

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

<%@ include file="../../include/mensagemAlert.jsp" %>

</html>