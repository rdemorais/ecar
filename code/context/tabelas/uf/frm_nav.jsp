<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.Uf" %>
<%@ page import="java.util.List" %>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="JavaScript" src="../../js/focoInicial.js"></script>
<script language="JavaScript" src="../../js/frmPadrao.js"></script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<body>

<div id="conteudo">

<form name="form" method="post" action="frm_nav.jsp">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%
	try {
		Uf uf = new Uf();
		_disabled = "disabled";
		_readOnly = "readonly";
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		/*
		 * quando vem de frm_pes, hidRegistro nao existe, devolve zero, começa no inicio da lista
		 * quando vem de frm_alt, obtem o registro que estava antes
		 * quando vem de frm_nav, obtem o registro que foi alterado pelo javascript em form_padrao
		 */
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		uf = (Uf) lista.get(hidRegistro);
%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>

	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=uf.getCodUf()%>">
	
	<table class="form">
	<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes navegacao="" btn1="Alterar" excluir="Excluir"/>

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
	
</form>
</div>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>