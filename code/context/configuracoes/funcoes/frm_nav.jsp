<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="java.util.List" %>
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="JavaScript" src="<%=_pathEcar%>/js/frmPadrao.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js"></script>
</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">
<%

boolean ehPesquisa = false;

try {
	FuncaoFun funcao = new FuncaoFun();
	_disabled = "disabled";
	_readOnly = "readonly";
	
	List lista = (List) session.getAttribute("lista");
	
	int i = 0;
	int hidTotRegistro = lista.size(); //Total de Registros
	int hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
	
	//Se o último registro for excluído deve apontar para a última posição.
	if (hidRegistro >= hidTotRegistro)
		hidRegistro = hidTotRegistro - 1;
	
	funcao = (FuncaoFun) lista.get(hidRegistro);
	
	String strReadOnly = "true";
	boolean blVisualizaDesc = true;
	String _pesqdisabled = "disabled";
%>
<form name="form" method="post" action="frm_nav.jsp">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
	<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
	<input type="hidden" name="codigo" value="<%=funcao.getCodFun()%>">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro+1%>" total="<%=hidTotRegistro%>"/>
		
		<%@ include file="form.jsp"%>
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
<%@ include file="/include/mensagemAlert.jsp"%>
</html>