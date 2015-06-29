<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>

<%@ page import="comum.util.Data" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>

<script language="javascript">
function validarPesquisar(form){
	if(form.dataDe.value != "" || form.dataLimite.value != ""){
		if (!validaData(form.dataDe,false,true,true)){
			if(form.dataDe.value == ""){
				alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataDeAte.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataDe.invalido")%>");
			}
			return false;
		}
		
		if (!validaData(form.dataLimite,false,true,true)){
			if(form.dataLimite.value == ""){
				alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataDeAte.obrigatorio")%>");
			}else{
				alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataLimite.invalido")%>");
			}
			return false;
		}
		
		var dtIni;
		var dtLim;
			
		dtIni = dataInversa(form.dataDe.value);
		dtLim = dataInversa(form.dataLimite.value);
			
		if (dtIni > dtLim){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataAte.dataFimMenorQueDataInicio")%>");
			form.dataLimite.focus();
			return(false)
		}
		
		if (Trim(form.argumento.value) != ""){
			if (Trim(form.argumento.value).length < 3){
				alert("<%=_msg.getMensagem("admPortal.agenda.validacao.argumento.tamanho")%>");
				form.argumento.focus();
				return false;
			}	
		}
	}else{
		if (Trim(form.argumento.value) == ""){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.pesquisa")%>");
			form.dataDe.focus();
			return false;
		}else{
			if (Trim(form.argumento.value).length < 3){
				alert("<%=_msg.getMensagem("admPortal.agenda.validacao.argumento.tamanho")%>");
				form.argumento.focus();
				return false;
			}	
		}
	}

	return(true);
}

function aoClicarPesquisar(form){
	if(validarPesquisar(form)){
		form.hidPesquisou.value = "true";
		form.hidAcao.value = "pesquisar";
		form.action = "frm_pes.jsp"
		form.submit();
	}
}

function aoClicarExcluir(form){
	if(validaRadioSelecionado(form, "codAgeo")){
		if(confirm("Confirma a exclusão?")){
			var selecionado = verificaValorSelecionado(form, "codAgeo");
			
			//padrão do hidExcluir é único, pois exlui somente um registro em AgendaOcorrencia
			if(eval("form.hidTamAgenda" + selecionado + ".value") > 1){
				if(confirm("<%=_msg.getMensagem("admPortal.agenda.validacao.excluirTodos")%>")){
					form.hidExcluir.value = "todos"; 
					//apagar todas as AgendaOcorrencia e Agenda
				}	
			}else{
				form.hidExcluir.value = "todos"; 
				//como é só uma ocorrência apagaria todos os registros
			}
			form.hidAcao.value = "excluir";
			form.action = "ctrl.jsp";
			form.submit();
		}
	}else{
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.clicarExcluir")%>");
	}
}	

/* este botao extra ficara convencionado como ir para a tela de alteracao
 * a partir da tela de navegação
 */
function aoClicarBtn1(form){
	if(validaRadioSelecionado(form, "codAgeo")){
		form.hidAcao.value = "";
		form.action = "frm_alt.jsp";
		form.submit();
	}else{
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.clicarEditar")%>");
	}
}

function aoClicarBtn2(form){
	if(validaRadioSelecionado(form, "codAgeo")){
		form.hidAcao.value = "ALTERAR_OCORRENCIAS";
		form.action = "frm_alt.jsp";
		form.submit();
	}else{
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.clicarEditar")%>");
	}
}

function onLoad(form) {
	aoClicarCancelar(form);
}

function aoClicarCancelar(form){
	form.reset();
	focoInicial(form);
}
</script>

</head>

<!-- sempre colocar o foco inicial no primeiro campo -->
<!--<body onload="javascript:onLoad(document.form);">-->
<body>

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<%
String hidPesquisou = Pagina.getParamStr(request, "hidPesquisou");
%>

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="">
	<input type="hidden" name="hidPesquisou" value="false">
	<input type="hidden" name="hidExcluir" value="unico">
	
	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr><td class="label">&nbsp;</td></tr>
	</table>
	
	<table class="form">
		<tr>
			<td class="label">Data de:</td>
			<td colspan=2>
				<input type="text" name="dataDe" size="14" maxlength="10" value="<%="".equals(Pagina.trocaNullData(Pagina.getParamDataBanco(request, "dataDe")))?Data.parseDate(Data.getDataAtual()):Pagina.getParamStr(request, "dataDe")%>" onkeyup="mascaraData(event, this);">
				 <img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataDe, '')">
				até:
				<input type="text" name="dataLimite" size="14" maxlength="10" value="<%="".equals(Pagina.trocaNullData(Pagina.getParamDataBanco(request, "dataLimite")))?"31/12/2099":Pagina.getParamStr(request, "dataLimite")%>" onkeyup="mascaraData(event, this);">
				 <img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataLimite, '')">
				ou
			</td>
		</tr>
		<tr>
			<td class="label">Argumento:</td>
			<td>
				<input type="text" name="argumento" size="50" maxlength="50" value="<%=Pagina.trocaNull(Pagina.getParamStr(request, "argumento"))%>">
			</td>
			<td>
				<input name="btnPesquisar" class="botao" type="button" value="Listar" onclick="aoClicarPesquisar(form);">
			</td>
		</tr>
	</table>
	
	<table class="barrabotoes" cellpadding="0" cellspacing="0">
		<tr><td class="label">&nbsp;</td></tr>
	</table>
	
	<br><br>
<%
if("true".equals(hidPesquisou)){
	try{
		AgendaOcorrenciaDao agendaOCDao = new AgendaOcorrenciaDao(request);
		List lista =  agendaOCDao.pesquisar(request);
		Iterator it = lista.iterator();
%>
		<table class="form">		
<%
		if(it.hasNext()){
%>
			<tr class="linha_titulo"><td>Eventos:</td></tr>
<%
			while(it.hasNext()){
				AgendaOcorrenciaAgeo agendaOC = (AgendaOcorrenciaAgeo) it.next();
%>
				<tr>
					<td>
						<input type="hidden" name="hidTamAgenda<%=agendaOC.getCodAgeo()%>" value="<%=agendaOC.getAgendaAge().getAgendaOcorrenciaAgeos().size()%>">
						<input type="radio" class="form_check_radio" name="codAgeo" value="<%=agendaOC.getCodAgeo()%>">
						<%=Data.parseDate(agendaOC.getDataEventoAgeo())%> - <%=agendaOC.getAgendaAge().getEventoAge()%>
					</td>
				</tr>
<%
			}
%>
			</table>
			<util:barrabotoes btn1="Editar" btn2="Editar Ocorrências" excluir="Excluir"/>
<%
		}else{
%>		
			<tr><td>Nenhum registro encontrado</td></tr>
			</table>
<%
		}
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
}
%>	

</form>
<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>
