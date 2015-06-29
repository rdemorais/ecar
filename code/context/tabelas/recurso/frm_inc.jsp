<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="comum.util.Data" %> 
<%@ include file="../../frm_global.jsp"%>

<%
session.removeAttribute("objPesquisa");

boolean ehPesquisa = false;

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
	if(!validaString(form.nomeFonr,'Nome',true)){
		return(false);
	}
	
	if(!validaString(form.siglaFonr, "Sigla", true)){
		return false;
	}
	
	if(form.indOrcamentoFonr[0].checked == false && form.indOrcamentoFonr[1].checked == false){
		alert("<%=_msg.getMensagem("fonteRecurso.validacao.indOrcamentoFonr.obrigatorio")%>");
		return(false);
	}
	
	if(form.configSisExecFinanCsef.value == ""){
		alert("<%=_msg.getMensagem("fonteRecurso.validacao.configSisExecFinanCsef.obrigatorio")%>");
		form.configSisExecFinanCsef.focus();
		return(false);
	}
	
	if(!validaData(form.dataIniValidadeFonr,false,true,true)){
		if(form.dataIniValidadeFonr.value == ""){
			alert("<%=_msg.getMensagem("fonteRecurso.validacao.dataIniValidadeFonr.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("fonteRecurso.validacao.dataIniValidadeFonr.invalido")%>");
		}
		return(false);
	}

	if(!validaData(form.dataFimValidadeFonr,false,true,true)){
		if(form.dataFimValidadeFonr.value == ""){
			alert("<%=_msg.getMensagem("fonteRecurso.validacao.dataFimValidadeFonr.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("fonteRecurso.validacao.dataFimValidadeFonr.invalido")%>");
		}
		return(false);
	}
	
	var dtIni;
	var dtFim;
	
	dtIni = dataInversa(form.dataIniValidadeFonr.value);
	dtFim = dataInversa(form.dataFimValidadeFonr.value);
	
	if (dtIni > dtFim){
		alert("<%=_msg.getMensagem("fonteRecurso.validacao.dataFimValidadeFonr.dataFinalMenorQueDataInicial")%>");
		form.dataFimValidadeFonr.focus();
		return(false)
	}
	
	if(!isInteger(form.sequenciaFonr.value)){
		alert("<%=_msg.getMensagem("fonteRecurso.validacao.sequenciaFonr.invalido")%>");
		form.sequenciaFonr.focus();
		return false;
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
	FonteRecursoFonr fonteRecurso = new FonteRecursoFonr();
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