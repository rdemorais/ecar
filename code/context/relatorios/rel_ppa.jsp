<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.login.SegurancaECAR" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.Set" %>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="ecar.dao.ItemEstruturaCriterioDao" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.pojo.CriterioCri" %>
<%@ page import="ecar.pojo.SituacaoSit" %>
<%@ page import="ecar.dao.SituacaoDao" %>
<%@ page import="comum.util.Util" %>
<%@ page import="comum.util.Data" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/focoInicial.js"></script>
<script language="javascript" src="../js/validacoes.js"></script>
<script>
function aoClicarBtn3(form){
	if(validarDados(form)){
		form.target = "_blank";
		form.hidAcao.value = "imprimir";
		form.action = "ctrl_ppa.jsp";
		form.submit();
		form.target = "";
	}
}
function validarDados(form){
	if(!validaAno(form.periodoIni.value)){
		form.periodoIni.focus();
		return false;
	}
	else if(!validaAno(form.periodoFim.value)){
		form.periodoFim.focus();
		return false;
	}
	else if(form.periodoFim.value < form.periodoIni.value){
		alert("Ciclo inicial não pode ser maior que final.");
		return false;
	}
	else if(!isInteger(form.paginaInicial.value)){
		alert("Número inválido para página inicial.");
		form.paginaInicial.focus();
		return false;
	}
		return true;
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
		<%
		List itensBarra = new ArrayList();
		itensBarra.add("Apêndice 2");
		itensBarra.add("Anexo");
		int index = (Pagina.getParamStr(request, "proximaAba")) == "" ? 0 : Integer.valueOf(Pagina.getParamStr(request, "proximaAba")).intValue();
		%>
		<util:barraLinksRelatorioItens
			itensBarra="<%=itensBarra%>"
			chamarPagina="rel_ppa.jsp"
			indexAtivo="<%=index%>"
		/>
		<input type="hidden" name="hidAcao" value="">
		<util:barrabotoes btn3="Gerar Relatório"/>


		<%
		String indTipoRelatorio = "";
		if(index == 0)
			indTipoRelatorio = "apendice2";
		else if(index == 1)
			indTipoRelatorio = "apendice3";
		
		%>
		<input type="hidden" name="indTipoRelatorio" value="<%=indTipoRelatorio%>">	

		<table class="form" width="100%">
		<tr>
			<td class="label" colspan="2">&nbsp;</td>
		</tr>
		<tr>
		<%
			/* 
			Para pegar os valores default, segue a seguinte regra:
			ano1 = ano atual / 4, despreza o resto da divisão, multiplica o resulado por 4
			ano2 = ano1 + 3
			*/
		
			int anoAtual = Data.getAno(Data.getDataAtual());
			int ano1 = Math.abs(anoAtual / 4) * 4;
			int ano2 = ano1 + 3;
			
		%>
			<td class="label" nowrap="nowrap">Ciclo PPA:</td>
			<td>
				<select name="periodoIni">
				<%
				for(int i = 2000; i<=2020; i++){
				%>
					<option value="<%=i%>" <%=(i == ano1) ? "selected" : ""%>><%=i%></option>
				<%
				}
				%>
				</select>
				à
				<select name="periodoFim">
				<%
				for(int i = 2000; i<=2020; i++){
				%>
					<option value="<%=i%>" <%=(i == ano2) ? "selected" : ""%>><%=i%></option>
				<%
				}
				%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label" nowrap="nowrap">Página Inicial:</td>
			<td>
				<input type="text" name="paginaInicial" value="1" maxlength="4" size="4">
			</td>
		</tr>
		<%
		if(index == 0) { //Apendice 2
		%>
		<tr>
			<td colspan="2">
				<table id="tipoValor">
					<tr>
						<td class="label" nowrap="nowrap" valign="top">Contabilizar:</td>
						<td>
							<input type="radio" class="form_check_radio" name="indTipoValor" value="A" checked>Valores Aprovados
							<br>
							<input type="radio" class="form_check_radio" name="indTipoValor" value="R" checked>Valores Atualizados
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<td colspan="2">
				<table id="tipoValor">
					<tr>
						<td class="label" nowrap="nowrap" valign="top">Exibir Totalização:</td>
						<td>
							<input type="radio" class="form_check_radio" name="indMostrarTotalizador" value="S">Sim
							<input type="radio" class="form_check_radio" name="indMostrarTotalizador" value="N" checked>Não
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="label" colspan="2">&nbsp;</td>
		</tr>
		</table>

		<util:barrabotoes btn3="Gerar Relatório"/>

		<%@ include file="../include/estadoMenu.jsp"%>
	</form>

</div>

</body>
<%@ include file="../include/mensagemAlert.jsp"%>
</html>