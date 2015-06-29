<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="comum.util.Util" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript" src="../js/datas.js"></script>

<script>
function aoClicarBtn3(form){
	if(validarDados(form)){
		form.target = "_blank";
		form.hidAcao.value = "imprimir";
		form.action = "ctrl_acessoUsuario.jsp";
		form.submit();
		form.target = "";
	}
}
function validarDados(form){
	if (validaData(document.form.dataInicio, true, true, true) == false){
		return false;
	}
	if (validaData(document.form.dataFim, true, true, true) == false){
		return false;
	}
	if (DataMenor(document.form.dataInicio.value, document.form.dataFim.value) == false
			&& document.form.dataInicio.value != document.form.dataFim.value){
		alert('A data final deve ser posterior ou igual a data inicial');
		return false;
	}

	return true;
}
function retornoUsu(codigo, descricao){
	document.form.nomeUsu.value = descricao;
	document.form.usu.value = descricao;
	document.form.codUsu.value = codigo;
}
function pesquisar(){
	popup_pesquisa('ecar.popup.PopUpUsuario', 'retornoUsu');
}
function limpaCampos(){
	document.form.nomeUsu.value = '';
	document.form.usu.value = '';
	document.form.codUsu.value = '';
}
</script>
</head>

<body onload="focoInicial(document.form);">

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

	<br><br>

	<form name="form" method="post">
		
		<util:barrabotoes btn3="Gerar Relatório"/>

		<input type="hidden" name="hidAcao" value="">	
		
		<table class="form" width="100%">
		<tr>
			<td class="label" colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td class="label">* Ciclo de Acesso</td>
			<td>
				<%
				Calendar calendarDataInicio = Calendar.getInstance();
				calendarDataInicio.add(Calendar.MONTH, -1);
				calendarDataInicio.set(Calendar.DAY_OF_MONTH, 1);
				
				%>
				<input type="text" name="dataInicio" size="15" value="<%=Data.parseDate(calendarDataInicio.getTime())%>" maxlength="10" onkeyup="mascaraData(event, this);">
				<img class="posicao" title="Selecione a data" src="../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataInicio,'','../calendario/calendario.jsp')">
				&nbsp;até&nbsp;
				<input type="text" name="dataFim" size="15" value="<%=Data.parseDate(Data.getDataAtual())%>" maxlength="10" onkeyup="mascaraData(event, this);">
				<img class="posicao" title="Selecione a data" src="../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataFim, '','../calendario/calendario.jsp')">
			</td>
		</tr>
		<tr>
			<td class="label">* Ordenar por</td>
			<td>
				<input type="radio" class="form_check_radio" name="ordenacao" value="dataHora" checked>Data/Hora Decrescente
				&nbsp;
				<input type="radio" class="form_check_radio" name="ordenacao" value="usuario">Usuário Alfabeticamente
			</td>
		</tr>
		<tr>
			<td class="label">Usuário</td>
			<td>
			
				<input type="text" disabled name="usu" size="50" value="">
				<input type="hidden" name="codUsu" value="">
				<input type="hidden" name="nomeUsu" value="">
				&nbsp;&nbsp;
				<input type="button" name="buttonPesquisar" value="Pesquisar" class="botao" onclick="pesquisar();">
	            &nbsp;
	            <input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limpaCampos();">
	
			</td>
		</tr>
		<tr>
			<td class="label" colspan="2">&nbsp;</td>
		</tr>
		</table>

		<util:barrabotoes btn3="Gerar Relatório" />

		<%@ include file="../include/estadoMenu.jsp"%>
	</form>

</div>
</body>
<%@ include file="../include/mensagemAlert.jsp"%>
</html>