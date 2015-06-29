<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

	<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
	<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
	<input type="hidden" name="periodo" 	  value="<%=Pagina.getParamStr(request, "periodo")%>">
	<input type="hidden" name="hidAcao"       value="<%=Pagina.getParamStr(request, "hidAcao")%>">
	<input type="hidden" name="codIett"       value="<%=Pagina.getParamStr(request, "codIett")%>">
	<input type="hidden" name="codIetta"      value="<%=Pagina.getParamStr(request, "codIetta")%>">
	<input type="hidden" name="codAba"        value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="Usuario"       value="<%=usuario.getCodUsu().toString()%>">
	<%if(estruturaFuncao != null) { %>
	<input type="hidden" name="labelEttf"     value="<%=estruturaFuncao.getLabelEttf()%>">
	<%}%>
	<table name="form" class="form">
		<tr>
			<td colspan="2">
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
		</tr>
		<tr>
			<td class="label">* Data</td>
			<td>
				<input type="text" name="dataIetta" size="15" <%=_disabled%> maxlength="10" value="<%=Pagina.trocaNullData(ieAcao.getDataIetta())%>" onkeyup="mascaraData(event, this);">
				<c:if test="<%=_disabled.equals("")%>">
					<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataIetta, '')">
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="label">* Descri&ccedil;&atilde;o
		</td>
			<td>
				<textarea name="descricaoIetta" <%=_readOnly%> cols="60" rows="4"
					onkeyup="return validaTamanhoLimite(this, 2000);"
					onkeydown="return validaTamanhoLimite(this, 2000);"
					onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(ieAcao.getDescricaoIetta())%></textarea>
			</td>
		</tr>
	</table>
	<% /* Controla o estado do Menu (aberto ou fechado) */%>
	<%@ include file="/include/estadoMenu.jsp"%>