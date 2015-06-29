<%@ page import="ecar.pojo.EfItemEstRealizadoEfier" %>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/popUp.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript">

function aoClicarBtn1(form){
	form.hidAcao.value = "pesquisar";
	form.conta.value = pegarConta(form);
	form.action = "ctrl.jsp";
	form.submit();
}

function aoClicarBtn2(form){
	form.reset();
	mostrarEsconder(form);
}

function aoClicarBtn3(form){
	form.action = "frm_inc.jsp?tela=pesquisa";
	form.submit();
}
<%@ include file="validacao.jsp" %>

</script>
</head>

<body onload="focoInicial(document.form);">

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<%
try{

	ItemEstruturaRealizadoDao realizadoDao = new ItemEstruturaRealizadoDao(request);
	EfItemEstRealizadoEfier efier = new EfItemEstRealizadoEfier();
	
	long anoLong = Pagina.getParamLong(request, "filtroAno");
	long mesLong = Pagina.getParamLong(request, "filtroMes");

	if(anoLong != 0)
		efier.setAnoReferenciaEfier(Long.valueOf(anoLong));
		
	if(mesLong != 0)
		efier.setMesReferenciaEfier(Long.valueOf(mesLong));
	efier.setContaSistemaOrcEfier(Pagina.getParamStr(request, "filtroConta"));
	
	boolean pesquisa = true;
	_obrigatorio = "";
	boolean alteracao = false;
%>
<%@ include file="/titulo_tela.jsp" %>

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="conta" value="">
	<util:barrabotoes btn1="Buscar" btn2="Cancelar" btn3="Incluir Lançamento"/>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes btn1="Buscar" btn2="Cancelar" btn3="Incluir Lançamento"/>

	<script language="javascript">
		mostrarEsconder(form);
	</script>
	
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