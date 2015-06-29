<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
<jsp:directive.page import="ecar.pojo.RegApontamentoRegda"/><%//Campos de ordenação da listagem%>
<input type="hidden" name="aptCampo" value="<%=Pagina.getParam(request, "aptCampo")%>">
<input type="hidden" name="aptOrdem" value="<%=Pagina.getParam(request, "aptOrdem")%>">
<input type="hidden" name="aptRefazPesquisa" value="">

<input type="hidden" name="codRegd" value="<%=regDemanda.getCodRegd()%>">
<input type="hidden" name="codRegda" value="<%=regApontamento.getCodRegda()%>">



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
		<td class="label">Data do Encaminhamento</td>
		<td><%=data%></td>
	</tr>
	<tr>
		<td class="label">Usuário</td>
		<td><%=usuario%></td>
	</tr>
	<tr>
		<td class="label"><%=_obrigatorio%> Comentário</td>
		
		<td><textarea name="infoRegda" rows="20" cols="80" 
			onkeyup="return validaTamanhoLimite(this, <%= RegApontamentoRegda.NUMERO_MAXIMO_CARACTERES_APONTAMENTO %>);" 
			onkeydown="return validaTamanhoLimite(this, <%= RegApontamentoRegda.NUMERO_MAXIMO_CARACTERES_APONTAMENTO %>);" 
			onblur="return validaTamanhoLimite(this, <%= RegApontamentoRegda.NUMERO_MAXIMO_CARACTERES_APONTAMENTO %>);" 
			<%=_readOnly%> ><%=Pagina.trocaNull(regApontamento.getInfoRegda())%></textarea></td>
	</tr>
	<tr><td class="label">&nbsp;</td></tr>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
