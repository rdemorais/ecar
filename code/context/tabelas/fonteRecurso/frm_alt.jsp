<%@ include file="../../login/validaAcesso.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.RecursoDao" %>
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
	if(!validaString(form.nomeRec,'Nome',true)){
		return(false);
	}
	
	if(!validaString(form.siglaRec,'Sigla', true)){
		return(false);
	}
	
	if(form.indOrcamentoRec[0].checked == false && form.indOrcamentoRec[1].checked == false){
		alert("<%=_msg.getMensagem("recurso.validacao.indOrcamentoRec.obrigatorio")%>");
		return(false);
	}
	
	if(!validaData(form.dataIniValidadeRec,false,true,true)){
		if(validaString(form.dataIniValidadeRec.value) == ""){
			alert("<%=_msg.getMensagem("recurso.validacao.dataIniValidadeRec.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("recurso.validacao.dataIniValidadeRec.invalido")%>");
		}
		return(false);
	}

	if(!validaData(form.dataFimValidadeRec,false,true,true)){
		if(validaString(form.dataFimValidadeRec.value) == ""){
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

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%

	boolean ehPesquisa = false;

	try {
		RecursoDao recursoDao = new RecursoDao(request);
		RecursoRec recurso = (RecursoRec) recursoDao.buscar(RecursoRec.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
		_disabled = "";
%>

		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>
		
		<input type="hidden" name="codigo" value="<%=recurso.getCodRec()%>">

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