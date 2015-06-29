<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.portal.CapaContato" %>

<%

CapaContato capaContato = new CapaContato(request);

%>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/email.js"></script>

<script language="JavaScript">

function limpar() {		
	document.form.action="contato.jsp";
	document.form.submit();
}

function validar() {

	if (document.form.areaContato.value == "") {
		alert('<%=_msg.getMensagem("portal.contato.areacontato.obrigatorio")%>');
		document.form.areaContato.focus();
		return false;
	}	
	
	if (document.form.email.value == "") {
		alert('<%=_msg.getMensagem("portal.contato.email.obrigatorio")%>');
		document.form.email.focus();
		return false;
	}
	
	if (!validaEmail(document.form.email.value)) {
		alert('<%=_msg.getMensagem("portal.contato.email.invalido")%>');
		document.form.email.focus();
		return false;
	}
		
	if (document.form.mensagem.value.length < 10) {
		alert('<%=_msg.getMensagem("portal.contato.mensagem.invalida")%>');
		document.form.mensagem.focus();
		return false;
	}
	return true;
}
</script>


<html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<body>
<%@ include file="/cabecalho.jsp" %>
<%
try{
%>
<form name="form" method="post" action="ctrl_contato.jsp" onsubmit="javascript:return validar();">
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td>Capa > Contato </td>
			<td align="right">&lt;&lt;<a href="javascript:history.back();"> Voltar</a></td>
		</tr>
	</table>
	<h1 class="interno">Contato</h1>
	<table cellspacing="0" class="formulario">
		<tr>
		  <td class="form_grupo">
		  	<table cellspacing="0">
				<tr>
					<td width="150" class="form_label"><b>Envie sua mensagem</b></td>
					<td >&nbsp;</td>
				</tr>
				<tr>
				  <td class="form_label">&Aacute;rea a ser contatada </td>
				  <td >
						<combo:ComboTag style="form_campo" 
								nome="areaContato"
								objeto="ecar.pojo.ContatoAreaCtta" 
								label="nomeCtta" 
								value="codCtta" 
								order="nomeCtta"
								colecao="<%=capaContato.getListAreasContatoOrdenada()%>"	
								option=""
						/>		
				  </td>
			  </tr>
				<tr>
				  <td class="form_label">Seu nome</td>
				  <td ><input name="nome" class="form_campo" type="text" size="50" 
				  		value="<%=((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario().getNomeUsuSent()%>" readonly="true"></td>
			  </tr>
				<tr>
				  <td class="form_label">Seu e-mail</td>
				  <td ><input name="email" class="form_campo" type="text" size="50" maxlength="50"></td>
			  </tr>
				<tr>
				  <td class="form_label">Mensagem</td>
				  <td ><textarea name="mensagem" class="form_campo" cols="45" rows="5"></textarea></td>
			  </tr>
			</table>
		  </td>
		</tr>
		<tr>
		  <td class="form_barra_botao">
		  	<table cellspacing="0">
				<tr>
				  <td width="150">&nbsp;</td>
					<td>
						<input type="submit" value="Enviar" class="form_botao">
				 	 	<input type="button" value="Limpar" class="form_botao" name="btnLimpar" onclick="javascript:limpar();">				  
				 	</td>
				</tr>
			</table>
		  </td>
		</tr>
	</table>
	<hr>
</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>
