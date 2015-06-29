<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ include file="../../login/validaAcesso.jsp"%>
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

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>

<%	try {
		ExercicioDao exercicioDao = new ExercicioDao(request);
		ExercicioExe exercicio;
		
		if(session.getAttribute("objExercicio") != null){
			exercicio = (ExercicioExe) session.getAttribute("objExercicio");
			session.removeAttribute("objExercicio");
		}else{
			exercicio = (ExercicioExe) exercicioDao.buscar(ExercicioExe.class, Long.valueOf(Pagina.getParamStr(request, "codigo")));
	  	}
		
		_disabled = "";
%>
	
		<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
		<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

		<input type="hidden" name="codigo" value="<%=exercicio.getCodExe()%>">

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
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>