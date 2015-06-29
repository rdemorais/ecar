<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="comum.util.Data" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript">
function validarPesquisar(form){
	if(!validaData(form.dataInicialExe,false,true,false)){
		alert("<%=_msg.getMensagem("exercicio.validacao.dataInicialExe.invalido")%>");
		return(false);
	}

	if(!validaData(form.dataFinalExe,false,true,false)){
		alert("<%=_msg.getMensagem("exercicio.validacao.dataFinalExe.invalido")%>");
		return(false);
	}

	if(form.dataInicialExe.value != "" && form.dataFinalExe.value != "")
	{
		var dtIni;
		var dtFim;
		
		dtIni = dataInversa(form.dataInicialExe.value);
		dtFim = dataInversa(form.dataFinalExe.value);
		
		if (dtIni >= dtFim){
			alert("<%=_msg.getMensagem("exercicio.validacao.dataFinalExe.dataFinalMenorQueDataInicial")%>");
			form.dataFinalExe.focus();
			return(false)
		}
	}
	
	return(true);
}
</script>

</head>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<!-- sempre colocar o foco inicial no primeiro campo -->
<body onload="javascript:onLoad(document.form);">

<%
try{
%>
<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>

<%
	ExercicioExe exercicio;
	if(session.getAttribute("objPesquisa") != null  && "false".equals(Pagina.getParamStr(request, "hidPesquisou"))){
		exercicio = (ExercicioExe) session.getAttribute("objPesquisa");
	}else{
		exercicio = new ExercicioExe();
  	}
	_disabled = "";
	_obrigatorio = "";
%>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes pesquisar="Pesquisar" cancelar="Cancelar"/>	
	
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