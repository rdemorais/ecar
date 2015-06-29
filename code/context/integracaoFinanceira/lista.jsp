<%@ page import="ecar.dao.ItemEstruturaRealizadoDao" %>
<%@ page import="ecar.pojo.EfItemEstRealizadoEfier" %>
<%@ page import="comum.util.Util" %>
<%@ page import="ecar.pojo.ConfigSisExecFinanCsef" %>
<%@ page import="ecar.dao.ConfigSisExecFinanDao" %>
<%@ include file="../login/validaAcesso.jsp"%>
<%@ include file="../frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../js/menu_retratil.js"></script>
<script language="javascript" src="../js/frmPadraoItemEstrut.js"></script>

<script language="javascript" src="../js/validacoes.js"></script>
<script language="javascript">
function aoClicarAlterar(form, codEfier){
	form.codEfier.value = codEfier;
	form.action = "frm_alt.jsp";
	form.submit();
} 

function aoClicarConsultar(form, codEfier){
	form.codEfier.value = codEfier;
	form.action = "frm_con.jsp";
	form.submit();
}

function aoClicarIncluir(form){
	form.action = "frm_inc.jsp";
	form.submit();
}

function aoClicarPesquisar(form){
	form.action = "frm_pes.jsp";
	form.submit();
}

function aoClicarExcluir(){
	if(validarExclusao()){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "ctrl.jsp";
		form.submit();	
	}
}
function validarExclusao(){
	var algumMarcado = false;
	var numChecks = 0;

    numChecks = verCheckExc(form, "excluir");
	
    if (numChecks > 0) {
		if(numChecks > 1){
			for(i = 0; i < form.excluir.length; i++)
				if(form.excluir[i].checked)
					algumMarcado = true;	
		} else {
			algumMarcado = form.excluir.checked;
		}
	}
	
	if(!algumMarcado){
		alert("Pelo menos um registro deve ser selecionado.");
		return false;
	} else {
		return true;
	}
}
function verCheckExc(form, nome){
	var el = document.getElementsByTagName("INPUT");
	var i = 0, n = 0;
	while (i < el.length)
	{		
		if(el.item(i) != null){
			if (el.item(i).type == "checkbox" && el.item(i).name == nome){
				n++;
			}
		}
		i++;
	}
	
	return n;
}
</script>

</head>

<body>

<%@ include file="../cabecalho.jsp" %>
<%@ include file="../divmenu.jsp"%>

<div id="conteudo">

<%
try{

	String[] labelsValores = new String[6];
	labelsValores[0] = configura.getFinanceiroDescValor1Cfg();
	labelsValores[1] = configura.getFinanceiroDescValor2Cfg();
	labelsValores[2] = configura.getFinanceiroDescValor3Cfg();
	labelsValores[3] = configura.getFinanceiroDescValor4Cfg();
	labelsValores[4] = configura.getFinanceiroDescValor5Cfg();
	labelsValores[5] = configura.getFinanceiroDescValor6Cfg();
	int numeroColunas = 5; //Checkbox, conta , Sistema, versão e mes/Ano
	for(int i = 0; i < 6; i++){
		if(!"".equals(labelsValores[i].trim())){
			numeroColunas++;
		}
	}

	ItemEstruturaRealizadoDao realizadoDao = new ItemEstruturaRealizadoDao(request);
	List efiers = realizadoDao.getItemEstRealizadoEfier(Pagina.getParamStr(request, "filtroConta"),Pagina.getParamStr(request, "filtroMes"), Pagina.getParamStr(request, "filtroAno"), Pagina.getParamStr(request, "filtroCodSistema"), "S");
%>

<%@ include file="/titulo_tela.jsp" %>

<br><br>

<div id="subconteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="codEfier" value="">
	
	<!-- Hiddens do Filtro... -->
	<input type="hidden" name="filtroConta" value="<%=Pagina.getParamStr(request, "filtroConta")%>">
	<input type="hidden" name="filtroAno" value="<%=Pagina.getParamStr(request, "filtroAno")%>">
	<input type="hidden" name="filtroMes" value="<%=Pagina.getParamStr(request, "filtroMes")%>">
	<input type="hidden" name="filtroCodSistema" value="<%=Pagina.getParamStr(request, "filtroCodSistema")%>">
	<input type="hidden" name="nomeSistemaFiltrado" value="<%=Pagina.getParamStr(request, "nomeSistemaFiltrado")%>">


<%
	String filtro = "";
	
	if(!"".equals(Pagina.getParamStr(request, "nomeSistemaFiltrado"))){
		filtro = "Filtro informado: Sistema (" + Pagina.getParamStr(request, "nomeSistemaFiltrado") + ") ";
	}
	if(!"".equals(Pagina.getParamStr(request, "filtroMes"))){
		if("".equals(filtro))
			filtro = "Filtro informado: ";
		filtro += "Mês (" + Pagina.getParamStr(request, "filtroMes") + ") ";
	}
	if(!"".equals(Pagina.getParamStr(request, "filtroAno"))){
		if("".equals(filtro))
			filtro = "Filtro informado: ";
		filtro += "Ano (" + Pagina.getParamStr(request, "filtroAno") + ") ";
	}
	if(!"".equals(Pagina.getParamStr(request, "filtroConta"))){
		if("".equals(filtro))
			filtro = "Filtro informado: ";
		filtro += "Conta (" + Pagina.getParamStr(request, "filtroConta") + ") ";
	}
	
	out.println(filtro);
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador"><img src="../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" align="right">
		<td>
			<input type="button" value="Incluir Lançamento" class="botao" onclick="javascript:aoClicarIncluir(form)">
			<input type="button" value="Excluir" class="botao" onclick="javascript:aoClicarExcluir(form)">
			<input type="button" value="Pesquisar" class="botao" onclick="javascript:aoClicarPesquisar(form)">
		</td>
	</tr>
</table>
	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan="<%=numeroColunas%>"><img src="../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="10px">
			<input type=checkBox class="form_check_radio" name=todos onclick="javascript:selectAll(document.form)">&nbsp;
		</td>
		<td>Conta</td>
		<td>Sistema</td>
		<td>Versão</td>
		<td>Mês/Ano</td>
		<%
		for(int i = 0; i < 6; i++){
			if(!"".equals(labelsValores[i].trim())){
		%>
			<td><%=labelsValores[i]%></td>
		<%
			}
		}
		%>
	</tr>
	
	<%
	if(efiers != null && !efiers.isEmpty()){
		Iterator itEfier = efiers.iterator();
		while(itEfier.hasNext()){
			EfItemEstRealizadoEfier efier = (EfItemEstRealizadoEfier) itEfier.next();
			
			String versao = "";
			String sistema = "";
			if(efier.getConfigSisExecFinanCsefv() != null && efier.getConfigSisExecFinanCsefv().getVersaoCsefv() != null){
				versao = efier.getConfigSisExecFinanCsefv().getVersaoCsefv().toString();
				if(efier.getConfigSisExecFinanCsefv().getConfigSisExecFinanCsef() != null){
					sistema = efier.getConfigSisExecFinanCsefv().getConfigSisExecFinanCsef().getNomeCsef();
				}
			}
			String conta = efier.getContaSistemaOrcEfier();
			long mesLong = efier.getMesReferenciaEfier().longValue();
			String mesAno = ((mesLong < 10) ? "0" + String.valueOf(mesLong) : String.valueOf(mesLong)) + "/" + efier.getAnoReferenciaEfier();
			String[] valores = new String[6];
			valores[0] = Pagina.trocaNullMoeda(efier.getValor1Efier());
			valores[1] = Pagina.trocaNullMoeda(efier.getValor2Efier());
			valores[2] = Pagina.trocaNullMoeda(efier.getValor3Efier());
			valores[3] = Pagina.trocaNullMoeda(efier.getValor4Efier());
			valores[4] = Pagina.trocaNullMoeda(efier.getValor5Efier());
			valores[5] = Pagina.trocaNullMoeda(efier.getValor6Efier());
			
		%>
			<tr class="linha_subtitulo2"> 
				<td width="40px">
					<input type="checkbox" class="form_check_radio" name="excluir" value="<%=efier.getCodEfier()%>">
					<a href="javascript:aoClicarAlterar(document.form,<%=efier.getCodEfier()%>)">
					<img src="../images/icon_alterar.png" border="0"></a>
				</td>
				<td>	
					<a href="javascript:aoClicarConsultar(document.form,<%=efier.getCodEfier()%>)">
					<%=conta%>
					</a>
				</td>
				
				<td><%=sistema%></td>
	
				<td><%=versao%></td>
				
				<td><%=mesAno%></td>
				<%
				for(int i = 0; i < 6; i++){
					if(realizadoDao.getVerificarMostrarValorByPosicaoCfg(i)){
				%>
					<td><%=valores[i]%></td>
				<%
					}
				}
				%>
			</tr>	
		<%
		}
	}
	else{
	%>
	<tr><td class="linha_subtitulo" colspan="<%=numeroColunas%>"><br>Não existem registros para visualização<br></td></tr>
	<%
	}
	%>
	<tr><td class="espacador" colspan="<%=numeroColunas%>"><img src="../images/pixel.gif"></td></tr>
</table>
	
	<%@ include file="../include/estadoMenu.jsp"%>
</form>

</div>
<%
} catch(ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
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