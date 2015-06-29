<%//Campos de ordenação da listagem%>
<input type="hidden" name="aptCampo" value="<%=Pagina.getParam(request, "aptCampo")%>">
<input type="hidden" name="aptOrdem" value="<%=Pagina.getParam(request, "aptOrdem")%>">
<input type="hidden" name="aptRefazPesquisa" value="">

<input type="hidden" name="codRegd" value="<%=regDemanda.getCodRegd()%>">
<input type="hidden" name="codRegda" value="<%=regApontamento.getCodRegda()%>">

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<%
	String data = "";
	String usuario = "";
	if ("incluir".equals(acao)) {
		data = Pagina.trocaNullData(Data.getDataAtual());
		usuario = ((ecar.login.SegurancaECAR) session.getAttribute("seguranca")).getUsuario().getNomeUsuSent();
	} else {
		data = Pagina.trocaNullData(regApontamento.getDataRegda());
		usuario = regApontamento.getUsuarioUsu().getNomeUsuSent();
	}
%>
	
	<tr>
		<td class="label">Data do Apontamento</td>
		<td><%=data%></td>
	</tr>
	<tr>
		<td class="label">Usuário</td>
		<td><%=usuario%></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Texto</td>
		<td><textarea name="infoRegda" rows="3" cols="55" 
			onkeyup="return validaTamanhoLimite(this, 200);" 
			onkeydown="return validaTamanhoLimite(this, 200);"
			onblur="return validaTamanhoLimite(this, 200);" <%=_readOnly%>><%=Pagina.trocaNull(regApontamento.getInfoRegda()).replace("\r\n","<br>")%></textarea></td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
