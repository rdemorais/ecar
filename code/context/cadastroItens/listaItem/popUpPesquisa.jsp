<%@ page import="comum.database.Dao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="java.util.List" %>


<%@ include file="../../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/frmPesquisa.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script>
function clickEnter(event, form){	
	if (event.keyCode == 13) {
		var estruturas = document.getElementsByName('itemEstrutura');
		var marcou = false;	

		for ( var i = 0; i < estruturas.length; i++) {
			
			if (estruturas[i].checked){
				marcou = true;
				break;
			}
		}

		if (marcou) {
			form.action='ctrl.jsp'
			form.submit();
		} else {
			alert('O campo estrutura é obrigatório.');
			return(false); 
		}	
	} 					
}

function validar (form){
	var estruturas = document.getElementsByName('itemEstrutura');
	var marcou = false;	

	
	for ( var i = 0; i < estruturas.length; i++) {
		
		if (estruturas[i].checked){
			marcou = true;
			break;
		}
	}

	if (marcou) {
		form.action='ctrl.jsp';
		form.submit();
	} else {
		alert('O campo estrutura é obrigatório.');
	}
	
}

function cargaInicial (msg){
	alert (msg);
}

function desabilitar() {
	if(form.action.length==0)
		opener.habilitar();
}

</script>

</head>

<c:set value="<%=request.getParameter("msg")%>" var="msgConfirmacao"/>
<c:choose>
	<c:when test="${msgConfirmacao == null}">
		<body class="corpo_popup" onunload="desabilitar();">
	</c:when>
	<c:otherwise>
		<body class="corpo_popup" onload="cargaInicial('${msgConfirmacao}');" onunload="desabilitar();">
	</c:otherwise>
</c:choose>


<%
try {
session.setAttribute("titulo","Pesquisa de Estrutura");
EstruturaDao estruturaDao = new EstruturaDao(request);
List listaEstruturas = estruturaDao.getEstruturasReaisOuVirtuais(false, null);
String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
%>

<%@ include file="/titulo_tela.jsp"%><br>

<form name="form" method="post" action="">
	
	<input type="hidden" name="hidAcao" value="pesquisar" >
	<input type="hidden" name="ultimoIdLinhaDetalhado" id="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
	<table class="form">
		<tr>
			<td width="30%" class="label">Nome do Item da Estrutura: </td>
			<td width="65%"><input type="text" name="nomePesquisa" id="nomePesquisa" size="55" maxlength="50" onkeypress="return clickEnter(event, this.form);"> </td>
			<td width="5%"><input type="button" name="btnOk" value="Ok" onclick='javascript:validar(this.form);'/></td>
		</tr>
		<tr><td class="espacador" colspan=3><img src="../../images/pixel.gif"></td></tr>
		<tr>
			<td width="30%" class="label" style="vertical-align: top;">Estrutura: </td>
			<td width="70%" colspan="2">
				<util:estruturaHierarquica lista="<%=listaEstruturas%>" temCheckBox="true" nomeCheckBox="itemEstrutura"/>
			</td>
		</tr>
	</table>
	<c:if test="<%=new Boolean (request.getParameter("retorno")).booleanValue()%>">
		<%@ include file="popUpRetorno.jsp"%>
	</c:if>
</form>
<%} catch (Exception e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
%>
	<%@ include file="/excecao.jsp"%>
<%}%>
</body>
</html>