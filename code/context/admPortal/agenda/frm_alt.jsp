<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.AgendaOcorrenciaAgeo" %>
<%@ page import="ecar.dao.AgendaOcorrenciaDao" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%
 	String hidAcao = Pagina.getParamStr(request, "hidAcao");
 	String tipoAgenda = "P";
	String operacao="alterar";//opercao usada nos scripts
	
	if (hidAcao.trim().length()==0)
		hidAcao=operacao;
	
    boolean ALTERAR_OCORRENCIA = hidAcao.equalsIgnoreCase("ALTERAR_OCORRENCIAS");

	session.removeAttribute("objPesquisa");
%>

<html lang="pt-br">
<head>
<%@ include file="../../cadastroItens/agenda/funcoes.jsp" %>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javaScript" src="../../js/html2xhtml.js"></script>
<script language="javaScript" src="../../js/richtext.js"></script>
<script language="javascript" src="../../js/mascaraDeValores.js"></script>

<% if (!ALTERAR_OCORRENCIA)%> 
	<%@ include file="validacao.jsp" %>
	


<script language="javascript">
<%if (ALTERAR_OCORRENCIA) { %>

function validar(form){

	setHiddenVal('rte');
	form.richText.value = form.richText.value.replace(/>/g, "&gt;").replace(/\n/g, "");
	form.richText.value = form.richText.value.replace(/&gt;/g, ">");
	form.descricaoAgeo.value = form.richText.value;    
    
	if (!validaData(form.dataEventoAgeo,false,true,true)){
		if(form.dataEventoAgeo.value == ""){
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataEventoAgeo.obrigatorio")%>");
		}else{
			alert("<%=_msg.getMensagem("admPortal.agenda.validacao.dataEventoAgeo.invalido")%>");
		}
		return false;
	}

	if(!validaString(form.horaEventoAgeo,'Hora do Evento',true)){
		return(false);
	}
	
	if(!validaNum(form.horaEventoAgeo, "Hora do Evento", false)){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.horaEventoAgeo.invalido")%>");
		form.horaEventoAgeo.focus();
		return(false);
	} 
	
	if(form.horaEventoAgeo.value < 0 || form.horaEventoAgeo.value > 23){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.horaEventoAgeo.invalido")%>");
		form.horaEventoAgeo.focus();
		return(false);
	} 

	if(!validaString(form.minutoEventoAgeo,'Minutos da Hora do Evento',true)){
		return(false);
	}
	
	if(!validaNum(form.minutoEventoAgeo, "Minutos da Hora do Evento", false)){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.minutoEventoAgeo.invalido")%>");
		form.minutoEventoAgeo.focus();
		return(false);
	} 
	
	if(form.minutoEventoAgeo.value < 0 || form.minutoEventoAgeo.value > 59){
		alert("<%=_msg.getMensagem("admPortal.agenda.validacao.minutoEventoAgeo.invalido")%>");
		form.minutoEventoAgeo.focus();
		return(false);
	} 
	
	//if (!validaString(form.eventoAge, "Evento", true)){
	//	return false;
	//}
	
	if (!validaString(form.localAgeo, "Local", true)){
		return false;
	}
	
	if (!validaString(form.descricaoAgeo, "Descrição", true)){
		return false;
	}
	
	if (form.ativo.checked)	form.indAtivo.value = "S";
	else form.indAtivo.value = "N";

	return true;
}

<% }%>
function aoClicarAlterar(form){
	if(validar(form)){
		form.action = "ctrl.jsp";
		
//		if (document.form.eventoAge.disabled)	form.hidAcao.value = "alterar";
//		else form.hidAcao.value = "ALTERAR_OCORRENCIAS";
		
		form.submit();
	}
}	

function aoClicarVoltar(form) {
	form.hidAcao.value = "";
	form.action = "frm_pes.jsp";
	form.submit();
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
<body onload="javascript:onLoad(document.form);">

<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>

<div id="conteudo">

<form name="form" method="post">
	<input type="hidden" name="hidAcao" value="<%=hidAcao %>">
	<input type="hidden" name="indAtivo" value="">
	<input type="hidden" name="codAgeo" value="<%=Pagina.getParamStr(request, "codAgeo")%>">

	<!-- TITULO -->
	<%@ include file="/titulo_tela.jsp"%>
 
	<util:linkstop incluir="frm_inc.jsp" pesquisar="frm_pes.jsp"/>
	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
try{

   
	AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
	AgendaOcorrenciaDao agendaOCDao = new AgendaOcorrenciaDao(request);
	
	if(session.getAttribute("objAgendaOC") != null){
		agendaOC = (AgendaOcorrenciaAgeo) session.getAttribute("objAgendaOC");
		session.removeAttribute("objAgendaOC");
	}else{
		agendaOC = (AgendaOcorrenciaAgeo) agendaOCDao.buscar(AgendaOcorrenciaAgeo.class, Long.valueOf(Pagina.getParamStr(request, "codAgeo")));
	}
	
	if (agendaOC!=null)
		session.setAttribute("objAgenda", agendaOC.getAgendaAge());
	if (ALTERAR_OCORRENCIA){
		_disabled = "disabled";
		_readOnly = "readonly";
	}
%>

	<table class="form"> 
		<%if (ALTERAR_OCORRENCIA) {%>
			<%@ include file="frm_alt_OC.jsp" %>
		<%} else{  %>
			<%@ include file="../../cadastroItens/agenda/form.jsp" %>
		<%} %>
	</table>

	<util:barrabotoes alterar="Gravar" voltar="Cancelar"/>

<%
}catch(ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	
</form>
<% /* controla o estado do menu (aberto ou fechado)*/ %>
<%@ include file="../../include/estadoMenu.jsp"%>
</div>

</body>
<%@ include file="../../include/mensagemAlert.jsp"%>
</html>