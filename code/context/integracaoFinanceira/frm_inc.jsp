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
<script language="javascript" src="../js/numero.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript">

function aoClicarGravar(form){
	if(validarMult(form) == true){
		form.hidAcao.value = "incluirMultiplos";
		form.action = "ctrl.jsp";
		form.submit();
	}
}
function aoClicarVoltar(form){
<%
	if("pesquisa".equals(Pagina.getParamStr(request, "tela"))){
%>
	form.action = "frm_pes.jsp";
<%
	}
	else {
%>
	form.action = "lista.jsp";
<%
	}
%>
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
	EfItemEstRealizadoEfier efier = (EfItemEstRealizadoEfier) request.getSession().getAttribute("efier");
	request.getSession().removeAttribute("efier");
	if(efier == null){
		efier = new EfItemEstRealizadoEfier();
		String incMes = Pagina.getParamStr(request, "incMes");
		String incAno = Pagina.getParamStr(request, "incAno");

		if(!"".equals(incMes))
			efier.setMesReferenciaEfier(Long.valueOf(incMes));
			
		if(!"".equals(incAno))
			efier.setAnoReferenciaEfier(Long.valueOf(incAno));
	}
	boolean pesquisa = false;
	boolean alteracao = false;
%>
<%@ include file="/titulo_tela.jsp" %>

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="telaInclusao" value="S">

	<!-- Hiddens do Filtro... -->
	<input type="hidden" name="filtroConta" value="<%=Pagina.getParamStr(request, "filtroConta")%>">
	<input type="hidden" name="filtroAno" value="<%=Pagina.getParamStr(request, "filtroAno")%>">
	<input type="hidden" name="filtroMes" value="<%=Pagina.getParamStr(request, "filtroMes")%>">
	<input type="hidden" name="filtroCodSistema" value="<%=Pagina.getParamStr(request, "filtroCodSistema")%>">
	<input type="hidden" name="nomeSistemaFiltrado" value="<%=Pagina.getParamStr(request, "nomeSistemaFiltrado")%>">


	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>

	<table class="form">
		<%@ include file="frm_inc_mult.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" voltar="Cancelar"/>

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