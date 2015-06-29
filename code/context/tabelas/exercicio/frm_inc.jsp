<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="comum.util.Data" %>
<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPadrao.js"></script>

<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript">
function validarGravar(form){
	if(!validaString(form.descricaoExe,'Descrição',true)){
		return(false);
	}
	
	if(!validaData(form.dataInicialExe,false,true,true)){
		if(form.dataInicialExe.value == ""){
			alert("<%=_msg.getMensagem("exercicio.validacao.dataInicialExe.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("exercicio.validacao.dataInicialExe.invalido")%>");
		}
		return(false);
	} else {
		if (getDia(form.dataInicialExe.value) != '01') {
			alert("<%=_msg.getMensagem("exercicio.validacao.dataInicialExe.diaInvalido")%>");
			return(false);
		}
	}

	if(!validaData(form.dataFinalExe,false,true,true)){
		if(form.dataFinalExe.value == ""){
			alert("<%=_msg.getMensagem("exercicio.validacao.dataFinalExe.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("exercicio.validacao.dataFinalExe.invalido")%>");
		}
		return(false);
	} else {
		if (parseInt(getDia(form.dataFinalExe.value)) != getUltimoDiaMes(getMes(form.dataFinalExe.value), getAno(form.dataFinalExe.value)) ) {
			alert("<%=_msg.getMensagem("exercicio.validacao.dataFinalExe.diaInvalido")%>");
			return(false);
		}
	}
	
	var dtIni;
	var dtFim;
	
	dtIni = dataInversa(form.dataInicialExe.value);
	dtFim = dataInversa(form.dataFinalExe.value);
	
	if (dtIni > dtFim){
		alert("<%=_msg.getMensagem("exercicio.validacao.dataFinalExe.dataFinalMenorQueDataInicial")%>");
		form.dataFinalExe.focus();
		return(false)
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
	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>

<%
	ExercicioExe exercicio = new ExercicioExe();
	
	if(session.getAttribute("objExercicio") != null){
		exercicio = (ExercicioExe) session.getAttribute("objExercicio");
		session.removeAttribute("objExercicio");
	}else{
		exercicio = new ExercicioExe();
  	}
	
	_disabled = "";
%>

	<table class="form">
		<%@ include file="form.jsp"%>
	</table>

	<util:barrabotoes incluir="Gravar" cancelar="Cancelar"/>
	
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