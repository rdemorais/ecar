<%@ include file="../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>

<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.Collections" %>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>

<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="comum.util.Data"%>
<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.EfItemEstRealizadoEfier" %>

<%@ include file="../frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>

<script language="javascript" src="../js/datas.js"></script>
<script language="javascript" src="../js/numero.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript">

function aoClicarAlterar(form){
	if(validar(form)){
		form.hidAcao.value = "alterar";
		form.action = "ctrl.jsp";
		form.submit();
	}
}
function aoClicarVoltar(form){
	form.action = "lista.jsp";
	form.submit();
}
<%@ include file="validacao.jsp" %>

</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try{

	String codEfier = Pagina.getParamStr(request, "codEfier");
	ItemEstruturaRealizadoDao realizadoDao = new ItemEstruturaRealizadoDao(request);
	EfItemEstRealizadoEfier efier = (EfItemEstRealizadoEfier) realizadoDao.buscar(EfItemEstRealizadoEfier.class, Long.valueOf(codEfier));
	boolean pesquisa = false;
	boolean alteracao = true;
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- Hiddens do Filtro... -->
	<input type="hidden" name="filtroConta" value="<%=Pagina.getParamStr(request, "filtroConta")%>">
	<input type="hidden" name="filtroAno" value="<%=Pagina.getParamStr(request, "filtroAno")%>">
	<input type="hidden" name="filtroMes" value="<%=Pagina.getParamStr(request, "filtroMes")%>">
	<input type="hidden" name="filtroCodSistema" value="<%=Pagina.getParamStr(request, "filtroCodSistema")%>">
	<input type="hidden" name="nomeSistemaFiltrado" value="<%=Pagina.getParamStr(request, "nomeSistemaFiltrado")%>">

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

	
	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
</form>

</div>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>