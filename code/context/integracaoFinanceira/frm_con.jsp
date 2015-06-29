<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.dao.ItemEstrutFisicoDao" %>
<%@ page import="ecar.pojo.EfItemEstRealizadoEfier" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>

<script language="javascript">
function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();	
}

function aoClicarBtn1(form){
	form.action = "frm_alt.jsp";
	form.submit();	
}
<%@ include file="validacao.jsp" %>

</script>

</head>

<body>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<%
try{
	String codEfier = Pagina.getParamStr(request, "codEfier");
	ItemEstruturaRealizadoDao realizadoDao = new ItemEstruturaRealizadoDao(request);
	EfItemEstRealizadoEfier efier = (EfItemEstRealizadoEfier) realizadoDao.buscar(EfItemEstRealizadoEfier.class, Long.valueOf(codEfier));
	
	_disabled = "disabled";

	//TODO: Verificar permissão de alterar...	
	Boolean permissaoAlterar = new Boolean(true);	
	boolean pesquisa = false;
	boolean alteracao = false;
%>

<!-- TITULO -->
<%@ include file="/titulo_tela.jsp"%>

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<!-- Hiddens do Filtro... -->
	<input type="hidden" name="filtroConta" value="<%=Pagina.getParamStr(request, "filtroConta")%>">
	<input type="hidden" name="filtroAno" value="<%=Pagina.getParamStr(request, "filtroAno")%>">
	<input type="hidden" name="filtroMes" value="<%=Pagina.getParamStr(request, "filtroMes")%>">
	<input type="hidden" name="filtroCodSistema" value="<%=Pagina.getParamStr(request, "filtroCodSistema")%>">
	<input type="hidden" name="nomeSistemaFiltrado" value="<%=Pagina.getParamStr(request, "nomeSistemaFiltrado")%>">

	<util:barrabotoes btn1="Alterar" voltar="Voltar"/>
	<table class="form">
		<%@ include file="form.jsp"%>
	</table>
	<util:barrabotoes btn1="Alterar" exibirBtn1="<%=permissaoAlterar%>" voltar="Voltar"/>
	
</form>


<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, e.getMessageKey());
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

</div>

</body>
<%@ include file="../include/mensagemAlert.jsp"%>
</html>