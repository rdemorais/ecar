<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo"%>

<%@ page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@ page import="ecar.dao.VisaoDao"%>
<%@ page import="comum.util.Pagina"%>
<%@ page import="java.util.List"%>

<html lang="pt-br">
	<head>
		<%@ include file="/include/meta.jsp"%>
		<%@ include file="/titulo.jsp"%>
		<link href="/css/style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
		<script language="Javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
		<script language="Javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
		<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
	</head>

	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body>

		<%@ include file="/cabecalho.jsp"%>
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
		VisaoDemandasVisDem visaoDemandas = new VisaoDemandasVisDem();
		VisaoDao visaoDemandaDao = new VisaoDao(request);
		
		List lista = (List) session.getAttribute("lista");
		
		int i = 0;
		int hidTotRegistro = lista.size(); //Total de Registros
		int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
		
		//Se o último registro for excluído deve apontar para a última posição.
		if (hidRegistro >= hidTotRegistro)
			hidRegistro = hidTotRegistro - 1;
		
		visaoDemandas = (VisaoDemandasVisDem) visaoDemandaDao.buscar(VisaoDemandasVisDem.class, ((VisaoDemandasVisDem) lista.get(hidRegistro)).getCodVisao());
		visaoDemandas.getVisaoSituacaoDemandas().size();
		
		_disabled = "disabled";
		_readOnly = "readOnly";
		
		String strReadOnly = "true";
		String _pesqdisabled = "disabled";
		boolean blVisualizaDesc = true;
%>

				<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp" />
	
				<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro + 1%>" total="<%=hidTotRegistro%>" />
	
				<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
				<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
				<input type="hidden" name="codigo" value="<%=visaoDemandas.getCodVisao()%>">

				<%@ include file="form.jsp"%>
	
				<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro + 1%>" total="<%=hidTotRegistro%>" />
	
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