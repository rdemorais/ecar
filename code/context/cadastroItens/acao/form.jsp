<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<table name="form" class="form">
<input type="hidden" name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type="hidden" name="codEttSelecionado" value="<%=itemEstrutura.getEstruturaEtt().getCodEtt()%>">
<input type="hidden" name="codIetta" value="<%=ieAcao.getCodIetta()%>">
<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type="hidden" name="hidAcao" value="">
<input type="hidden" name="Usuario" value="<%=usuario.getCodUsu().toString()%>">

<tr>
			<td colspan=2>
				<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
			</td>
	</tr>
	<tr>
		<td class="label">* Data</td>
		<td>		 
			<input type="text" name="dataIetta" size="15" <%=_disabled%> maxlength="10"  value="<%=Pagina.trocaNullData(ieAcao.getDataIetta())%>"  onkeyup="mascaraData(event, this);">
			<c:if test="<%=_disabled.equals("")%>">
				<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataIetta, '')">
			</c:if>
		</td>
</tr>		
<tr>
	<td class="label">* Descri&ccedil;&atilde;o</td>
	<td>
		<textarea name="descricaoIetta" <%=_readOnly%> cols=60 rows=4 
			onkeyup="return validaTamanhoLimite(this, 2000);"
			onkeydown="return validaTamanhoLimite(this, 2000);"
			onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(ieAcao.getDescricaoIetta())%></textarea>
	</td>
</tr>	
</table>		
<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
