<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="ecar.pojo.RecursoRec" %>
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
	
	if(!validaString(form.nomeRec,'Nome',true)){
		return(false);
	}

	if(!validaString(form.siglaRec,'Sigla',true)){
		return(false);
	}

	if(form.indOrcamentoRec[0].checked == false && form.indOrcamentoRec[1].checked == false){
		alert("<%=_msg.getMensagem("recurso.validacao.indOrcamentoRec.obrigatorio")%>");
		return(false);
	}
	
	if(!validaData(form.dataIniValidadeRec,false,true,true)){
		if(form.dataIniValidadeRec.value == ""){
			alert("<%=_msg.getMensagem("recurso.validacao.dataIniValidadeRec.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("recurso.validacao.dataIniValidadeRec.invalido")%>");
		}
		return(false);
	}

	if(!validaData(form.dataFimValidadeRec,false,true,true)){
		if(form.dataFimValidadeRec.value == ""){
			alert("<%=_msg.getMensagem("recurso.validacao.dataFimValidadeRec.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("recurso.validacao.dataFimValidadeRec.invalido")%>");
		}
		return(false);
	}
	
	var dtIni;
	var dtFim;
	
	dtIni = dataInversa(form.dataIniValidadeRec.value);
	dtFim = dataInversa(form.dataFimValidadeRec.value);
	
	if (dtIni > dtFim){
		alert("<%=_msg.getMensagem("recurso.validacao.dataFimValidadeRec.dataFinalMenorQueDataInicial")%>");
		form.dataFimValidadeRec.focus();
		return(false)
	}

	if(!isInteger(form.sequenciaRec.value)){
		alert("<%=_msg.getMensagem("recurso.validacao.sequenciaRec.invalido")%>");
		form.sequenciaRec.focus();
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
	RecursoRec recurso = new RecursoRec();
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