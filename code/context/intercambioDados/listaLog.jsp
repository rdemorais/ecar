<%@page import="comum.util.ConstantesECAR"%>
<%@page import="ecar.dao.intercambioDados.LogIntercambioDadosDao"%>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>



<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<%@ include file="../include/exibirAguarde.jsp"%>
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="../js/destaqueLinha.js"></script>
<script language="javascript">

	function disparaAcao(acao){
		document.form.hidAcao.value=acao;
		document.form.action = "ctrl.jsp";
		document.form.submit();
	}
	
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:destacaLinhaDisplayTag('displayTag');" >

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post" action="ctrl.jsp">
	<input type="hidden" name="hidAcao" value="">
		
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
	<% String url = "detalheLog.jsp?voltar="+request.getParameter("d-2423786-p");%>

<%
	LogIntercambioDadosDao logDao = new LogIntercambioDadosDao(request);
%>	

		<display:table id="displayTag" name="<%=logDao.listarLog()%>"  pagesize="<%=configuracao.getNuItensExibidosPaginacao() %>" export="true" >
			<display:column property="dataHoraProcessamentoString" title="Processamento" sortable="true" style="text-align: center" href="<%=url%>" paramId="codLid" paramProperty="codLid" />
			<display:column property="descSituacaoProcessamentoLid" title="Situação" sortable="true" />
			<display:column property="perfilLog.nomeTipoServicoPflid" title="Serviço" sortable="true" />
			<display:column property="perfilLog.nomeSistemaOrigemPflid" title="Sistema Origem" sortable="true" />
			<display:column property="perfilLog.tipoFuncionalidade" title="Tipo de Funcionalidade" sortable="true" />
			<display:column property="perfilLog.tipoTecnologia" title="Tecnologia de Comunicação" sortable="true" />
			<display:column property="usuarioUsu.nomeUsu" title="Responsável" sortable="true" />
		</display:table>

</form>

</div>
</body>
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../include/ocultarAguarde.jsp"%>
<%@ include file="../include/estadoMenu.jsp"%>
<%@ include file="../include/mensagemAlert.jsp" %>

</html>