<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.FonteRecursoFonr" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ include file="../../frm_global.jsp"%>

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

function validarAlterar(form){
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

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%

	boolean ehPesquisa = false;

	try {
		FonteRecursoDao fonteRecursoDao = new FonteRecursoDao(request);
		FonteRecursoFonr fonteRecurso = (FonteRecursoFonr) fonteRecursoDao.buscar(FonteRecursoFonr.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
		_disabled = "";
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	

		<input type="hidden" name="codigo" value="<%=fonteRecurso.getCodFonr()%>">
	
		<table class="form">
			<%@ include file="form.jsp"%>
		</table>
	
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
	
<%
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		/* redireciona para o controle */
		%>
		<script language="javascript">
		document.form.action = "ctrl.jsp";
		document.form.submit();
		</script>
		<%
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>

</form>

</div>

</body>
</html>